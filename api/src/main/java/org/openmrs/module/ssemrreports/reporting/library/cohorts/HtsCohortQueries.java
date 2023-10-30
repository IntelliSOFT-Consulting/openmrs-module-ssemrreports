package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtsCohortQueries {
	
	private final CommonCohortQueries commonCohortQueries;
	
	private final SharedCohortQueries sharedCohortQueries;
	
	@Autowired
	public HtsCohortQueries(CommonCohortQueries commonCohortQueries, SharedCohortQueries sharedCohortQueries) {
		this.commonCohortQueries = commonCohortQueries;
		this.sharedCohortQueries = sharedCohortQueries;
	}
	
	public CohortDefinition getPatientsWithEncounterAndNoObs(List<EncounterType> encounters, TimeQualifier timeQualifier,
	        List<Integer> questions) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.addSearch("DateObs", SSEMRReportUtils.map(commonCohortQueries.getPatientsWithObs(questions),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("hasEncounter", SSEMRReportUtils.map(sharedCohortQueries.hasEncounters(encounters, timeQualifier),
		    "onOrAfter=${startDate},onOrBefore=${endDate},locationList=${location}"));
		cd.setCompositionString("hasEncounter AND NOT DateObs");
		return cd;
	}
}
