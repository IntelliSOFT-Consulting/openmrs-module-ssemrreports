package org.openmrs.module.ssemrreports.reporting.library.data.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.SuspectedVirologicalFailureDataDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Virological Failure Data Definition
 */
@Handler(supports = SuspectedVirologicalFailureDataDefinition.class, order = 50)
public class SuspectedVirologicalFailureDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "SELECT client_id, " + "CASE "
		        + "WHEN SUM(CASE WHEN viral_load_value >= 1000 THEN 1 ELSE 0 END) >= 2 THEN 'Yes' " + "ELSE 'No' "
		        + "END AS result " + "FROM ( " + "  SELECT client_id, viral_load_value "
		        + "  FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + "  WHERE DATE(encounter_datetime) <= DATE(:endDate) " + "  ORDER BY client_id, encounter_datetime DESC "
		        + "  LIMIT 2 " + ") AS last_two_encounters " + "GROUP BY client_id;";
		
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
