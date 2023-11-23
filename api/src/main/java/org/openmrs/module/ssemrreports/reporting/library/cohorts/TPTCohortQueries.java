package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.library.queries.TPTQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class TPTCohortQueries {
	
	public CohortDefinition getPatientsWhoAreEligibleForTPT() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Patient eligible for TPT testing on Date");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(TPTQueries.getPatientsEligibleForTPT());
		
		return cd;
	}
	
}
