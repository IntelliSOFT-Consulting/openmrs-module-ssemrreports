package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.stereotype.Component;

@Component
public class CommonCohortQueries {
	
	public CohortDefinition getPatientsWithObs(int encounterId, List<Integer> questions, List<Integer> answers) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObs(encounterId, questions, answers));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWithObs(List<Integer> questions, List<Integer> answers) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations without considering encounter");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObs(questions, answers));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWithObs(List<Integer> questions) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations without regardless of the answers");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObs(questions));
		
		return cd;
	}
	
	public CohortDefinition getNumberPatientsSeenPerEncounter(List<Integer> encounterIds) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients sen per the encounter");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.getBasePatientsBasedOnEncounter(encounterIds));
		
		return cd;
	}
	
	public CohortDefinition getPatientsEnrolledIntoProgram(int programId) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients enrolled into program");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsInProgram(programId));
		
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
	
	public CohortDefinition getPatientsInProgramWithNullOutcomes(int programId) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients enrolled into program with null outcomes");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsInProgramWithNullOutcomes(programId));
		
		return cd;
	}
	
	public CohortDefinition getPatientsInProgramWithOutcomes(int programId, int conceptOutcomeId) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients enrolled into program with  outcomes");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsInProgramWithOutcomes(programId, conceptOutcomeId));
		
		return cd;
	}
	
	public CohortDefinition getPatientsEverInProgramWithOutcomes(int programId, int conceptOutcomeId) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients enrolled into program with  outcomes ever enrolled");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.getPatientsEverInProgramWithOutcomes(programId, conceptOutcomeId));
		
		return cd;
	}
	
	public CohortDefinition getPatientsWithObsByEndDate(List<Integer> questions, List<Integer> answers) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations without considering encounter by reporting end date");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObsByEndDate(questions, answers));
		
		return cd;
	}
	
	public CohortDefinition getPatientsEverInProgramWithOutcomesAndNewlyEnrolled(int programId, int conceptOutcomeId) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients enrolled into program with  outcomes ever enrolled new after LTFU");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newInProgram", SsemrReportUtils.map(getPatientsEnrolledIntoProgram(programId),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("LTFU", SsemrReportUtils.map(getPatientsEverInProgramWithOutcomes(programId, conceptOutcomeId),
		    "endDate=${startDate-1d},location=${location}"));
		cd.setCompositionString("newInProgram AND LTFU");
		return cd;
	}
	
	public CohortDefinition getPatientsHavingEncounterAndObservationsIntersected(List<Integer> encounterIds,
	        List<Integer> questions, List<Integer> answers) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get patients with encounters intersected with observations");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("hasEncounter", SsemrReportUtils.map(getNumberPatientsSeenPerEncounter(encounterIds),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("hasObs", SsemrReportUtils.map(getPatientsWithObs(questions, answers),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		
		cd.setCompositionString("hasEncounter AND hasObs");
		return cd;
		
	}
	
	public CohortDefinition getPatientsHavingEncounterAndObservationsIntersected(List<Integer> encounterIds,
	        List<Integer> questions1, List<Integer> answers1, List<Integer> questions2, List<Integer> answers2) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get patients with encounters intersected with observations with multiple options");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("hasEncounter", SsemrReportUtils.map(getNumberPatientsSeenPerEncounter(encounterIds),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("Obs1", SsemrReportUtils.map(getPatientsWithObs(questions1, answers1),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("Obs2", SsemrReportUtils.map(getPatientsWithObs(questions2, answers2),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		
		cd.setCompositionString("hasEncounter AND Obs1 AND Obs2");
		return cd;
		
	}
	
	public CohortDefinition hasAnyEncounterFilled() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with at least an encounter on a given visit");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.hasAnyEncounter());
		return cd;
	}
	
	public CohortDefinition hasObsIntersectedFromSameObsGroup(int encounterTypeId, List<Integer> question1,
	        List<Integer> ans1, List<Integer> question2, List<Integer> ans2) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get patients with encounters intersected with observations with multiple options forming one obs group");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		cd.setQuery(CommonQueries.hasObsIntersectedFromSameObsGroup(encounterTypeId, question1, ans1, question2, ans2));
		return cd;
	}
	
}
