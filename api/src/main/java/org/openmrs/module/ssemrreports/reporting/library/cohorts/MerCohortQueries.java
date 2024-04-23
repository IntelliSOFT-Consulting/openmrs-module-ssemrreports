package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.MerQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MerCohortQueries {
	
	private final SharedCohortQueries sharedCohortQueries;
	
	@Autowired
	public MerCohortQueries(SharedCohortQueries sharedCohortQueries) {
		this.sharedCohortQueries = sharedCohortQueries;
	}
	
	public CohortDefinition getTxCurrCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxCurr Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getPatientsWhoInitiatedArtDuringReportingPeriod());
		return cd;
	}
	
	//TX new  cohort queries
	public CohortDefinition getTxNewCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Totals");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxNewTotals());
		return cd;
	}
	
	public CohortDefinition getCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getCd4GreaterThanOrEqualTo200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithUnknownCd4Query());
		return cd;
	}
	
	public CohortDefinition getTxNewBreastfeedingCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients who are breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("NEW", SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("B", SSEMRReportUtils.map(getBreastfeedingCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("NEW AND B");
		return cd;
	}
	
	public CohortDefinition getTxNewCd4LessThan200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4LessThan200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("NEW", SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("C1", SSEMRReportUtils.map(getCd4LessThan200Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("NEW AND C1");
		return cd;
	}
	
	public CohortDefinition getTxNewWithCd4GreaterThanOrEqualTo200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4GreaterThanOrEqualTo200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("NEW", SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("C2",
		    SSEMRReportUtils.map(getCd4GreaterThanOrEqualTo200Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("NEW AND C2");
		return cd;
	}
	
	public CohortDefinition getTxWithUnknownCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients WithUnknownCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("NEW", SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("C3", SSEMRReportUtils.map(getWithUnknownCd4Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("NEW AND C3");
		return cd;
	}
	
	//TX_ML cohort queries
	public CohortDefinition getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContact");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContact());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLater");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlDiedCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Identified as Died");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getDeadClientsQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterDiedCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterDiedCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2", SSEMRReportUtils.map(getTxMlDiedCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlIitL3mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for <3 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxMlIitL3mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitL3mCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitL3mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2", SSEMRReportUtils.map(getTxMlIitL3mCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlIit3To5mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 3-5 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxMlIitL3To5mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIit3To5mCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIit3To5mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2", SSEMRReportUtils.map(getTxMlIit3To5mCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlIitM6mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 6+ months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxMlIitM6mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitM6mCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitM6mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2", SSEMRReportUtils.map(getTxMlIitM6mCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlSelfTransferOutCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Self Transfer Out");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTransferOutQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterSelfTransferOutCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterSelfTransferOutCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2",
		    SSEMRReportUtils.map(getTxMlSelfTransferOutCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlRefusedStoppedTreatmentCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Refused (Stopped) Treatment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getStoppedTreatmentQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterRefusedStoppedTreatmentCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterRefusedStoppedTreatmentCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T1", SSEMRReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T2",
		    SSEMRReportUtils.map(getTxMlRefusedStoppedTreatmentCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlCauseOfDeathCohorts(String cause) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Refused (Stopped) Treatment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxMlCauseOfDeathQueries(cause));
		return cd;
	}
	
	//TX RTT cohort Queries
	public CohortDefinition getClientsTracedBroughtBackToCareRestartedCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getClientsTracedBroughtBackToCareRestarted");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsTracedBroughtBackToCareRestarted());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffArvs28DaysTo3MonthsCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getHowLongWerePeopleOffArvs28DaysTo3Months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getHowLongWerePeopleOffArvs28DaysTo3MonthsQuery());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffArvs3To6MonthsQueryCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getHowLongWerePeopleOffArvs3To6MonthsQuery");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getHowLongWerePeopleOffArvs3To6MonthsQuery());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffArvs6To12MonthsQueryCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getHowLongWerePeopleOffArvs6To12MonthsQuery");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getHowLongWerePeopleOffArvs6To12MonthsQuery());
		return cd;
	}
	
	//Combine the lost patients with the base query that they actually restarted treatment
	public CohortDefinition getHowLongWerePeopleOffArvs28DaysTo3MonthsFromLastTcaCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffArvs28DaysTo3MonthsFromLastTcaCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1", SSEMRReportUtils.map(getHowLongWerePeopleOffArvs28DaysTo3MonthsCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffArvs3To6MonthsFromLastTcaCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffArvs3To6MonthsFromLastTcaCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1", SSEMRReportUtils.map(getHowLongWerePeopleOffArvs3To6MonthsQueryCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffArvs6To12MonthsFromLastTcaCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffArvs6To12MonthsFromLastTcaCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1", SSEMRReportUtils.map(getHowLongWerePeopleOffArvs6To12MonthsQueryCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttWithCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffMonthsFromLastTcaWithCd4LessThan200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffMonthsFromLastTcaWithCd4LessThan200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1",
		    SSEMRReportUtils.map(getTxRttWithCd4LessThan200Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttWithCd4GreaterOrEqual200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffFromLastTcaWithCd4GreaterOrEqual200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffFromLastTcaWithCd4GreaterOrEqual200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1",
		    SSEMRReportUtils.map(getTxRttWithCd4GreaterOrEqual200Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getClientsWithUnknownCd4Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffFromLastTcaWithUnknownCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffFromLastTcaWithUnknownCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1",
		    SSEMRReportUtils.map(getTxRttWithUnknownCd4Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttNotEligibleForCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxRttNotEligibleForCd4Queries());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffFromLastTcaNotEligibleForCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffFromLastTcaNotEligibleForCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("T0", SSEMRReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("T1",
		    SSEMRReportUtils.map(getTxRttNotEligibleForCd4Cohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	//TX PVLS Cohorts
	public CohortDefinition getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented greater or equal to 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented less than 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingWithDocumentedVlResultsCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("P", SSEMRReportUtils.map(getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("B", SSEMRReportUtils.map(getBreastfeedingCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("P AND B");
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingWithDocumentedVlResultsGreatorThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("P", SSEMRReportUtils.map(
		    getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("B", SSEMRReportUtils.map(getBreastfeedingCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("P AND B");
		return cd;
	}
	
	public CohortDefinition getTxPvlsPregnantWithDocumentedVlResultsCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("PV", SSEMRReportUtils.map(getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("PR", SSEMRReportUtils.map(getPregnantCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("PV AND PR");
		return cd;
	}
	
	public CohortDefinition getTxPvlsPregnantWithDocumentedVlResultsGreatorThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant with documented VL result > 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("PV", SSEMRReportUtils.map(
		    getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("PR", SSEMRReportUtils.map(getPregnantCohorts(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("PV AND PR");
		return cd;
	}
	
	//cut across queries
	public CohortDefinition getPregnantCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Pregnant");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getPregnantQueries());
		return cd;
	}
	
	public CohortDefinition getBreastfeedingCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getBreastfeedingQueries());
		return cd;
	}
	
	public CohortDefinition getDeadClientsCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Dead clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getDeadClientsQueries());
		return cd;
	}
	
	public CohortDefinition getStoppedTreatmentCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Stopped treatment clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getStoppedTreatmentQueries());
		return cd;
	}
	
	public CohortDefinition getTransferOutCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Transfer out clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getTransferOutQueries());
		return cd;
	}
	
	public CohortDefinition getInterruptionCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Interrupted clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery(MerQueries.getInterruptionQueries());
		return cd;
	}
	
	public CohortDefinition getAllExclusionCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		String mapping = "startDate=${startDate},endDate=${endDate}";
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("D", SSEMRReportUtils.map(getDeadClientsCohort(), mapping));
		cd.addSearch("S", SSEMRReportUtils.map(getStoppedTreatmentCohort(), mapping));
		cd.addSearch("TO", SSEMRReportUtils.map(getTransferOutCohort(), mapping));
		cd.addSearch("ITT", SSEMRReportUtils.map(getInterruptionCohort(), mapping));
		cd.setCompositionString("D OR S OR TO OR ITT");
		return cd;
	}
	
}
