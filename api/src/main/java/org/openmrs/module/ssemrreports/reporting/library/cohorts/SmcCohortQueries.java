package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Arrays;
import java.util.Date;

import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmcCohortQueries {
	
	private final SharedCohortQueries sharedCohortQueries;
	
	@Autowired
	public SmcCohortQueries(SharedCohortQueries sharedCohortQueries) {
		this.sharedCohortQueries = sharedCohortQueries;
	}
	
	public CohortDefinition getNumberOfClientsCounselledByEncounter(EncounterType smcEncounterType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Combined query for the counselled and HIV status");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("C", SsemrReportUtils.map(
		    sharedCohortQueries.hasEncounters(Arrays.asList(smcEncounterType), TimeQualifier.ANY),
		    "onOrBefore=${endDate},onOrAfter=${startDate},locationList=${location}"));
		cd.setCompositionString("C");
		return cd;
	}
}
