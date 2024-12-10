package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.library.queries.PnsRegisterQueries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.stereotype.Component;

@Component
public class BaseCohortQueries {
	
	public CohortDefinition getPatientsWhoQualifiesForAgivenEncounter(List<Integer> encounterIds) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Base patients who will constitute any set");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.getBasePatientsBasedOnEncounter(encounterIds));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWhoMissedAppointmentByDays(int days) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Missed appointment patient set");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getMissedAppointmentPatientSetByDays(days));
		return cd;
	}
	
	public CohortDefinition getPatientsEnrolledIntoProgramByEndOfReportingPeriod(int programId) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients enrolled into program by end of reporting period");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsInProgramByEndOfReportingPeriod(programId));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWhoHaveEncountersForPnsReports(List<Integer> list) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with encounters for PNS reports");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(PnsRegisterQueries.getPnsRegisterBaseQuery(list));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWithTodaysAppointments() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Todays appointment patient set");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsWithAppointments());
		return cd;
	}
	
	public CohortDefinition getPatientsWhoHaveHighVL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Line list of clients with high viral load, EAC sessions and Repeat viral load");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsWithHighVL());
		
		return cd;
	}
	
	public CohortDefinition getAllClients() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("All Clients");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.setQuery(CommonQueries.getAllPatients());
		
		return cd;
	}
	
	public CohortDefinition getPatientsEligibleForVLTesting() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Line list of clients eligible for viral load");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsEligibleForVL());
		
		return cd;
	}
	
	public CohortDefinition getPatientsWhoAreIIT() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("IIT patients patient set");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getIITPatients());
		
		return cd;
	}
	
	public CohortDefinition getPatientsWhoHaveDocumentedVL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Line list for clients with documented viral load result, high viral load, and viral load test");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getDocumentedVLPatients());
		
		return cd;
	}
	
	public CohortDefinition getPatientsWhoAreRTT() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("RTT patients patient set");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getRTTPatients());
		
		return cd;
	}
	
	public CohortDefinition getPatientsInTbTreatment() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get patients who are in tb treatment");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getTbTreatmentClients());
		
		return cd;
	}
	
	public CohortDefinition getPatientsScreenedForTbTreatment() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get patients who are tb screened");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getTbScreenedClients());
		
		return cd;
	}
	
	public CohortDefinition getClientsOnArtPerFacility() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get total clients who are on ART from a specific facility");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getClientsOnArtPerFacility());
		
		return cd;
	}
	
	public CohortDefinition getClientsWithBirthdateAndGender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get total clients who are on ART from a specific facility who have birthdate and gender captured correctly");
		cd.setQuery(CommonQueries.getOnlyPatientsWithBirthdateAndGender());
		
		return cd;
	}
	
	public CohortDefinition getAccurateClientsOnArtPerFacility() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get total clients who are on ART from a specific facility intersected with person table for gender and DOB");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(getClientsOnArtPerFacility(), "endDate=${endDate},location=${location}"));
		cd.addSearch("T2", SsemrReportUtils.map(getClientsWithBirthdateAndGender(), ""));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
}
