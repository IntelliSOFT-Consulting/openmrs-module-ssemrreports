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

import java.util.Date;
import java.util.Map;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.DaysVLPendingDataDefinition;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Evaluates Days VL Pending Data Definition
 */
@Handler(supports = DaysVLPendingDataDefinition.class, order = 50)
public class DaysVLPendingDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "SELECT fup.client_id, " + "CASE " + "  WHEN fup.date_vl_results_received IS NOT NULL "
		        + "  THEN DATEDIFF(DATE(fup.date_vl_results_received), DATE(fup.date_vl_sample_collected)) "
		        + "  ELSE DATEDIFF(:endDate, DATE(fup.date_vl_sample_collected)) " + "END AS days_difference "
		        + "FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fup " + "JOIN ( "
		        + "  SELECT client_id, MAX(date_vl_sample_collected) AS latest_sample_date "
		        + "  FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + "  WHERE date_vl_sample_collected IS NOT NULL AND encounter_datetime <= :endDate "
		        + "  GROUP BY client_id " + ") AS latest "
		        + "ON fup.client_id = latest.client_id AND fup.date_vl_sample_collected = latest.latest_sample_date;";
		
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
