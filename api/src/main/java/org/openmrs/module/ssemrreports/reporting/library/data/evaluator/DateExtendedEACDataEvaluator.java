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
import org.openmrs.module.ssemrreports.reporting.library.data.definition.DateExtendedEACDataDefinition;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Evaluates Date Extended EAC Data Definition
 */
@Handler(supports = DateExtendedEACDataDefinition.class, order = 50)
public class DateExtendedEACDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "SELECT lr.client_id, DATE_FORMAT(CONCAT(MID(MAX(CONCAT(fp.encounter_datetime, lr.last_vl_date_repeat)), 20)), '%Y-%m-%d')  "
		        + " as vl_repeat_date FROM ssemr_etl.ssemr_flat_encounter_vl_laboratory_request lr "
		        + " left join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fp on lr.client_id = fp.client_id "
		        + " where fp.encounter_datetime <= DATE(:endDate) and lr.last_vl_date_repeat is not null "
		        + " group by fp.client_id, lr.last_vl_date_repeat;";
		
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
