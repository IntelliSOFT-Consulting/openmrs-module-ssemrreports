/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ssemrreports.reporting.library.data.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.VLDueDateDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Date;

/**
 * VL Due Date Data Definition
 */
@Handler(supports = VLDueDateDataDefinition.class, order = 50)
public class VLDueDateDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "WITH LatestFP AS ( "
		        + "    SELECT f.*, ROW_NUMBER() OVER(PARTITION BY f.client_id "
		        + "        ORDER BY "
		        + "            CASE WHEN f.date_vl_sample_collected IS NOT NULL THEN 1 ELSE 2 END, "
		        + "            f.date_vl_sample_collected DESC, "
		        + "            f.encounter_datetime DESC "
		        + "    ) as rn "
		        + "    FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + "    WHERE f.encounter_datetime <= :endDate "
		        + "), "
		        + "LatestHVL AS ( "
		        + "    SELECT h.*, ROW_NUMBER() OVER(PARTITION BY h.client_id ORDER BY h.encounter_datetime DESC) as rn "
		        + "    FROM ssemr_etl.ssemr_flat_encounter_high_viral_load h "
		        + "    WHERE h.encounter_datetime <= :endDate "
		        + "), "
		        + "patients_with_pending_vl AS ( "
		        + "  SELECT DISTINCT fp.client_id "
		        + "  FROM LatestFP fp "
		        + "  LEFT JOIN LatestHVL hvl ON fp.client_id = hvl.client_id AND hvl.rn = 1 "
		        + "  WHERE fp.rn = 1 "
		        + "  AND ((fp.date_vl_sample_collected IS NOT NULL AND fp.date_vl_results_received IS NULL) "
		        + "       OR (hvl.repeat_vl_sample_date IS NOT NULL AND hvl.repeat_vl_result_date IS NULL)) "
		        + "), "
		        + "patients_in_hvl_cohort AS ( "
		        + "  SELECT DISTINCT fp.client_id "
		        + "  FROM LatestFP fp "
		        + "  LEFT JOIN LatestHVL hvl ON fp.client_id = hvl.client_id AND hvl.rn = 1 "
		        + "  WHERE fp.rn = 1 "
		        + "  AND hvl.third_eac_session_date IS NULL "
		        + "  AND (fp.viral_load_value >= 1000 OR hvl.repeat_vl_value >= 1000) "
		        + "), "
		        
		        + "calculated_due_dates AS ( "
		        + "  SELECT "
		        + "    p.person_id AS client_id, "
		        + "    DATE_FORMAT("
		        + "      CASE "
		        + "        WHEN hvl.encounter_datetime > fp.encounter_datetime THEN "
		        + "          CASE "
		        + "            WHEN hvl.third_eac_session_date IS NOT NULL AND hvl.repeat_vl_results IS NULL THEN DATE_ADD(hvl.third_eac_session_date, INTERVAL 1 MONTH) "
		        + "            WHEN hvl.repeat_vl_sample_date IS NOT NULL AND (hvl.repeat_vl_value < 1000 OR hvl.repeat_vl_results = 'Below Detectable (BDL)') THEN DATE_ADD(hvl.repeat_vl_sample_date, INTERVAL 6 MONTH) "
		        + "            ELSE DATE_ADD(hvl.encounter_datetime, INTERVAL 1 MONTH) "
		        + "          END "
		        + "        ELSE "
		        + "          CASE "
		        + "            WHEN (mp.age > 18 AND pfh.art_start_date IS NOT NULL AND (fp.client_pmtct = 'No' OR fp.client_pmtct IS NULL) AND (fp.viral_load_value < 1000 OR fp.vl_results = 'Below Detectable (BDL)') AND EXISTS (SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up prev WHERE prev.client_id = fp.client_id AND prev.date_vl_sample_collected < fp.date_vl_sample_collected AND (prev.viral_load_value < 1000 OR prev.vl_results = 'Below Detectable (BDL)'))) THEN DATE_ADD(fp.date_vl_sample_collected, INTERVAL 12 MONTH) "
		        + "            WHEN (mp.age > 18 AND pfh.art_start_date IS NOT NULL AND (fp.client_pmtct = 'No' OR fp.client_pmtct IS NULL) AND (fp.viral_load_value < 1000 OR fp.vl_results = 'Below Detectable (BDL)') AND NOT EXISTS (SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up prev WHERE prev.client_id = fp.client_id AND prev.date_vl_sample_collected < fp.date_vl_sample_collected AND (prev.viral_load_value < 1000 OR prev.vl_results = 'Below Detectable (BDL)'))) THEN DATE_ADD(fp.date_vl_sample_collected, INTERVAL 6 MONTH) "
		        + "            WHEN (mp.age > 18 AND pfh.art_start_date IS NOT NULL AND NOT EXISTS (SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up v2 WHERE v2.client_id = fp.client_id AND v2.date_vl_sample_collected IS NOT NULL)) THEN DATE_ADD(pfh.art_start_date, INTERVAL 6 MONTH) "
		        + "            WHEN hvl.third_eac_session_date IS NOT NULL AND (hvl.repeat_vl_results IS NULL) THEN DATE_ADD(hvl.third_eac_session_date, INTERVAL 1 MONTH) "
		        + "            WHEN (mp.age <= 18 AND hvl.repeat_vl_sample_date IS NOT NULL) AND (fp.client_pmtct = 'No' OR fp.client_pmtct IS NULL) AND (hvl.repeat_vl_value IS NULL AND hvl.repeat_vl_results IS NULL) THEN DATE_ADD(hvl.third_eac_session_date, INTERVAL 1 MONTH) "
		        + "            WHEN hvl.repeat_vl_sample_date IS NOT NULL AND (hvl.repeat_vl_value < 1000 OR hvl.repeat_vl_results = 'Below Detectable (BDL)') THEN DATE_ADD(hvl.repeat_vl_sample_date, INTERVAL 6 MONTH) "
		        + "            WHEN (mp.age <= 18 AND pfh.art_start_date IS NOT NULL) AND (fp.client_pmtct = 'No' OR fp.client_pmtct IS NULL) AND (fp.date_vl_sample_collected IS NULL AND fp.vl_results IS NULL) AND hvl.repeat_vl_sample_date IS NULL THEN DATE_ADD(pfh.art_start_date, INTERVAL 6 MONTH) "
		        + "            WHEN (mp.age <= 18 AND fp.date_vl_sample_collected IS NOT NULL) AND (fp.client_pmtct = 'No' OR fp.client_pmtct IS NULL) AND hvl.repeat_vl_sample_date IS NULL THEN DATE_ADD(fp.date_vl_sample_collected, INTERVAL 6 MONTH) "
		        + "            WHEN (fp.client_pmtct = 'Yes' AND fp.date_vl_sample_collected IS NOT NULL) AND (fp.viral_load_value < 1000 OR fp.vl_results = 'Below Detectable (BDL)') THEN DATE_ADD(fp.date_vl_sample_collected, INTERVAL 3 MONTH) "
		        + "            WHEN (fp.client_pmtct = 'Yes' AND fp.date_vl_sample_collected IS NULL) THEN DATE_ADD(fp.encounter_datetime, INTERVAL 3 MONTH) "
		        + "            WHEN (fp.client_pregnant = 'Yes' AND pfh.art_start_date IS NOT NULL) THEN fp.encounter_datetime "
		        + "            WHEN pfh.art_start_date IS NOT NULL AND (hvl.repeat_vl_result_date IS NULL AND fp.date_vl_sample_collected IS NULL) THEN DATE_ADD(pfh.art_start_date, INTERVAL 6 MONTH) "
		        + "            ELSE NULL "
		        + "          END "
		        + "      END, "
		        + "      '%d-%m-%Y'"
		        + "    ) AS eligibility_date "
		        + "  FROM ssemr_etl.mamba_dim_person p "
		        + "  JOIN ssemr_etl.mamba_dim_person mp ON p.person_id = mp.person_id "
		        + "  LEFT JOIN LatestFP fp ON p.person_id = fp.client_id AND fp.rn = 1 "
		        + "  LEFT JOIN LatestHVL hvl ON p.person_id = hvl.client_id AND hvl.rn = 1 "
		        + "  LEFT JOIN ssemr_etl.ssemr_flat_encounter_personal_family_tx_history pfh ON p.person_id = pfh.client_id "
		        + "  WHERE pfh.art_start_date IS NOT NULL " + ") " + "SELECT " + "  calc.client_id, " + "  CASE "
		        + "    WHEN p_pending.client_id IS NOT NULL THEN 'Pending Results' "
		        + "    WHEN p_hvl.client_id IS NOT NULL THEN 'Pending EAC 3' " + "    ELSE calc.eligibility_date "
		        + "  END AS final_vl_due_date_or_status " + "FROM calculated_due_dates calc "
		        + "LEFT JOIN patients_with_pending_vl p_pending ON calc.client_id = p_pending.client_id "
		        + "LEFT JOIN patients_in_hvl_cohort p_hvl ON calc.client_id = p_hvl.client_id";
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.append(qry);
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		queryBuilder.addParameter("endDate", endDate);
		queryBuilder.addParameter("startDate", startDate);
		
		Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
		c.setData(data);
		return c;
		
	}
}
