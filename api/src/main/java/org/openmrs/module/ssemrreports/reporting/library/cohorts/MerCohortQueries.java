package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.library.queries.MerQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
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
		cd.setName("TxCurr Cohorts excluding interruptions");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getPatientsWhoInitiatedArtDuringReportingPeriod());
		
		SqlCohortDefinition cd1 = new SqlCohortDefinition();
		cd1.setName("ART restarted patients within the period ");
		cd1.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd1.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd1.addParameter(new Parameter("location", "Location", Location.class));
		cd1.setQuery(MerQueries.getArtPatientsWhoRestartedTreatmentInPeriod());
		
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("TXCURR TOTAL");
		comp.addParameter(new Parameter("startDate", "Start Date", Date.class));
		comp.addParameter(new Parameter("endDate", "End Date", Date.class));
		comp.addParameter(new Parameter("location", "Location", Location.class));
		comp.addSearch("Tx1", SsemrReportUtils.map(cd, "startDate=${startDate},endDate=${endDate},location=${location}"));
		comp.addSearch("Tx2", SsemrReportUtils.map(cd1, "startDate=${startDate},endDate=${endDate},location=${location}"));
		comp.setCompositionString("Tx1 OR Tx2");
		return comp;
	}
	
	//TX new  cohort queries
	public CohortDefinition getTxNewCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Totals");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxNewTotals());
		return cd;
	}
	
	public CohortDefinition getCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getCd4GreaterThanOrEqualTo200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithUnknownCd4Query());
		return cd;
	}
	
	public CohortDefinition getTxNewBreastfeedingCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients who are breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW",
		    SsemrReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SsemrReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND B");
		return cd;
	}
	
	public CohortDefinition getTxNewCd4LessThan200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4LessThan200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW",
		    SsemrReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C1", SsemrReportUtils.map(getCd4LessThan200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C1");
		return cd;
	}
	
	public CohortDefinition getTxNewWithCd4GreaterThanOrEqualTo200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4GreaterThanOrEqualTo200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW",
		    SsemrReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C2", SsemrReportUtils.map(getCd4GreaterThanOrEqualTo200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C2");
		return cd;
	}
	
	public CohortDefinition getTxWithUnknownCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients WithUnknownCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW",
		    SsemrReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C3", SsemrReportUtils.map(getWithUnknownCd4Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C3");
		return cd;
	}
	
	//TX_ML cohort queries
	public CohortDefinition getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContact");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContact());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLater");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2", SsemrReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlDiedCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Identified as Died");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getDeadClientsQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterDiedCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterDiedCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2",
		    SsemrReportUtils.map(getTxMlDiedCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlIitL3mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for <3 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxMlIitL3mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitmCohorts(
	        int l, int h) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitL3mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(CommonQueries.getClientsWithArtDateAndDateLost(l, h));
		return cd;
	}
	
	public CohortDefinition getTxMlIit3To5mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 3-5 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxMlIitL3To5mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIit3To5mCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIit3To5mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2",
		    SsemrReportUtils.map(getTxMlIit3To5mCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlIitM6mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 6+ months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxMlIitM6mQuery());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitM6mCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitM6mCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2",
		    SsemrReportUtils.map(getTxMlIitM6mCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlSelfTransferOutCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Self Transfer Out");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTransferOutQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterSelfTransferOutCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterSelfTransferOutCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2", SsemrReportUtils.map(getTxMlSelfTransferOutCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlRefusedStoppedTreatmentCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Refused (Stopped) Treatment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getStoppedTreatmentQueries());
		return cd;
	}
	
	public CohortDefinition getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterRefusedStoppedTreatmentCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxMl Cohorts - getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterRefusedStoppedTreatmentCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(
		    getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2", SsemrReportUtils.map(getTxMlRefusedStoppedTreatmentCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T1 AND T2");
		return cd;
	}
	
	public CohortDefinition getTxMlCauseOfDeathCohorts(String cause) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Cause of death " + cause);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxMlCauseOfDeathQueries(cause));
		return cd;
	}
	
	//TX RTT cohort Queries
	public CohortDefinition getClientsTracedBroughtBackToCareRestartedCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getClientsTracedBroughtBackToCareRestarted");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsTracedBroughtBackToCareRestarted());
		return cd;
	}
	
	public CohortDefinition getClientsWhoLTFUatTheBeginningOfTheReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getClientsWhoLTFUatTheBeginningOfTheReportingPeriod");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getArtPatientsNotActiveAtBeginningOfReportingPeriod());
		return cd;
	}
	
	/**
	 * Clients traced and brought back to care (Re-started) from those who were lost in the previous
	 * quarters (I.e from those who were not active at the beginning of this reporting period)
	 */
	public CohortDefinition getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxRTT Cohorts - getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T1", SsemrReportUtils.map(getClientsTracedBroughtBackToCareRestartedCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T2", SsemrReportUtils.map(getClientsWhoLTFUatTheBeginningOfTheReportingPeriod(),
		    "startDate=${startDate},location=${location}"));
		cd.setCompositionString("T1");
		return cd;
	}
	
	//How long were people off ARVs - n days
	public CohortDefinition getHowLongWerePeopleOffArvsNdaysCohorts(int l, int h) {
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("How long were people off ARVs - Combined with base");
		comp.addParameter(new Parameter("startDate", "Start Date", Date.class));
		comp.addParameter(new Parameter("endDate", "End Date", Date.class));
		comp.addParameter(new Parameter("location", "Location", Location.class));
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getHowLongWerePeopleOffArvsNdaysCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getHowLongWerePeopleOffArvQuery(l, h));
		
		comp.addSearch("BASE", SsemrReportUtils.map(
		    getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		comp.addSearch("LMT", SsemrReportUtils.map(cd, "startDate=${startDate},endDate=${endDate},location=${location}"));
		comp.setCompositionString("LMT");
		return comp;
	}
	
	//LTFU patients
	public CohortDefinition getLTFUCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - getLTFUCohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getHowLongWerePeopleOffArvAfterLTFUQuery());
		return cd;
	}
	
	public CohortDefinition getTxRttWithCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffMonthsFromLastTcaWithCd4LessThan200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffMonthsFromLastTcaWithCd4LessThan200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T0", SsemrReportUtils.map(
		    getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T1", SsemrReportUtils.map(getTxRttWithCd4LessThan200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttWithCd4GreaterOrEqual200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffFromLastTcaWithCd4GreaterOrEqual200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffFromLastTcaWithCd4GreaterOrEqual200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T0", SsemrReportUtils.map(
		    getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T1", SsemrReportUtils.map(getTxRttWithCd4GreaterOrEqual200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	public CohortDefinition getTxRttWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getClientsWithUnknownCd4Query());
		return cd;
	}
	
	public CohortDefinition getHowLongWerePeopleOffFromLastTcaWithUnknownCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx RTT getHowLongWerePeopleOffFromLastTcaWithUnknownCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("T0", SsemrReportUtils.map(
		    getClientsTracedBroughtBackToCareRestartedCohortsNotActiveAtTheBeginningOfThisReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("T1", SsemrReportUtils.map(getTxRttWithUnknownCd4Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("T0 AND T1");
		return cd;
	}
	
	//TX PVLS Cohorts
	public CohortDefinition getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented greater or equal to 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - All patients with VL results documented less than 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingWithDocumentedVlResultsCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("P", SsemrReportUtils.map(getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SsemrReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("P AND B");
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingWithDocumentedVlResultsGreatorThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("P", SsemrReportUtils.map(
		    getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SsemrReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("P AND B");
		return cd;
	}
	
	public CohortDefinition getTxPvlsPregnantWithDocumentedVlResultsCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant with documented VL result");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("PV", SsemrReportUtils.map(getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("PR",
		    SsemrReportUtils.map(getPregnantCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("PV AND PR");
		return cd;
	}
	
	public CohortDefinition getTxPvlsPregnantWithDocumentedVlResultsGreatorThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant with documented VL result > 1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("PV", SsemrReportUtils.map(
		    getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("PR",
		    SsemrReportUtils.map(getPregnantCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("PV AND PR");
		return cd;
	}
	
	//cut across queries
	public CohortDefinition getPregnantCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Pregnant");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getPregnantQueries());
		return cd;
	}
	
	public CohortDefinition getBreastfeedingCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getBreastfeedingQueries());
		return cd;
	}
	
	public CohortDefinition getDeadClientsCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Dead clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getDeadClientsQueries());
		return cd;
	}
	
	public CohortDefinition getStoppedTreatmentCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Stopped treatment clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getStoppedTreatmentQueries());
		return cd;
	}
	
	public CohortDefinition getTransferOutCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Transfer out clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getTransferOutQueries());
		return cd;
	}
	
	public CohortDefinition getInterruptionCohort() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Interrupted clients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(MerQueries.getInterruptionQueries());
		return cd;
	}
	
	public CohortDefinition getAllExclusionCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		String mapping = "startDate=${startDate},endDate=${endDate},location=${location}";
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("D", SsemrReportUtils.map(getDeadClientsCohort(), mapping));
		cd.addSearch("S", SsemrReportUtils.map(getStoppedTreatmentCohort(), mapping));
		cd.addSearch("TO", SsemrReportUtils.map(getTransferOutCohort(), mapping));
		cd.addSearch("ITT", SsemrReportUtils.map(getInterruptionCohort(), mapping));
		cd.setCompositionString("D OR S OR TO OR ITT");
		return cd;
	}
	
	//ad cohort definitions for the pregnant and breastfeeding women who have suppressed VL
	public CohortDefinition getTxPvlsPregnantWithSuppressedVlResultsLessThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant with suppressed VL result - <1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("SUP", SsemrReportUtils.map(
		    getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("P",
		    SsemrReportUtils.map(getPregnantCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("SUP AND P");
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingWithSuppressedVlResultsLessThan1000Cohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding with suppressed VL result - <1000");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("SUP", SsemrReportUtils.map(
		    getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SsemrReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("SUP AND B");
		return cd;
	}
	
}
