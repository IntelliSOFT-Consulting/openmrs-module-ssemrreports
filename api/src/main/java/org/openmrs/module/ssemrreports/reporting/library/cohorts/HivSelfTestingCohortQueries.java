package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.library.queries.HivSelfTestingQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class HivSelfTestingCohortQueries {
	
	public CohortDefinition getAllPatientsByPurposeOfDistributionAndFacilityBased() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.addParameter(new Parameter("location", "Location", Location.class));
		sql.setQuery(HivSelfTestingQueries.getAllPatientsByPurposeOfDistributionAndFacilityBased());
		return sql;
	}
}
