package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.library.queries.OpdQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class OpdReportCohortQueries {
	
	public CohortDefinition getNewReattendanceCases(String type) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Type of visit attended by the patients - new or re-attendance");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(OpdQueries.getPatientsWithVisitType(SharedReportConstants.DIAGNOSIS_ENCOUNTER_VISIT_TYPE_UUID, type));
		return cd;
	}
	
	public CohortDefinition getDiagnosisBasedOnIcd11Code(String icd11Code) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Patient diagnosis based on the ICD 11 Code");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(OpdQueries.getPatientsWithEncounterDiagnosisBasedOnIcd11Code(icd11Code));
		return cd;
	}
}
