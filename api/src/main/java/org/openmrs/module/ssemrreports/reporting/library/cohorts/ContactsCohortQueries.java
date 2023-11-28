package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.ContactsQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.stereotype.Component;

@Component
public class ContactsCohortQueries {
	
	public CohortDefinition getPatientsWhoHaveUnknownHivStatus() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("List of Family contacts with unknown HIV status");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(ContactsQueries.getContactsWithUnknownHivStatus());
		
		return cd;
	}
}
