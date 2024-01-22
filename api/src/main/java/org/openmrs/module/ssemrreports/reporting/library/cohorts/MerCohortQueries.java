package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.MerQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MerCohortQueries {
	
	public CohortDefinition getPatientsWhoInitiatedArtDuringReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Patients who Initiated ART during reporting period");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getPatientsWhoInitiatedArtDuringReportingPeriod());
		return cd;
	}
	
	public CohortDefinition getPatientsWhoTransferredInDuringReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Patients who transferred in during reporting period");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getPatientsWhoTransferredInDuringReportingPeriod());
		return cd;
	}
	
	public CohortDefinition getTxCurrCohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("TxCurr Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("TXC1", SSEMRReportUtils.map(getPatientsWhoInitiatedArtDuringReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("TXC2", SSEMRReportUtils.map(getPatientsWhoTransferredInDuringReportingPeriod(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		
		cd.setCompositionString("TXC1 OR TXC2");
		return cd;
	}
	
	public CohortDefinition lessThan3MonthsDispensationComposition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("less 3 months Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getLessThan3MonthsQuery());
		return cd;
	}
	
	public CohortDefinition quarterlyDispensationComposition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("quarterly Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getQuarterlyDispensationQuery());
		return cd;
	}
	
	public CohortDefinition semiAnnualDispensationComposition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("semi annual Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getSemiAnnualDispensationQuery());
		return cd;
	}
	
	//TX new  cohort queries
	public CohortDefinition getTxNewCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Totals");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxNewTotals());
		return cd;
	}
	
	public CohortDefinition getCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getCd4GreaterThanOrEqualTo200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - CD4 ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getClientsWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getClientsWithUnknownCd4Query());
		return cd;
	}
	
	public CohortDefinition getTxNewBreastfeedingCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients who are breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("NEW",
		    SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SSEMRReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND B");
		return cd;
	}
	
	public CohortDefinition getTxNewCd4LessThan200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4LessThan200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("NEW",
		    SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C1", SSEMRReportUtils.map(getCd4LessThan200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C1");
		return cd;
	}
	
	public CohortDefinition getTxNewWithCd4GreaterThanOrEqualTo200Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients Cd4GreaterThanOrEqualTo200Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("NEW",
		    SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C2", SSEMRReportUtils.map(getCd4GreaterThanOrEqualTo200Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C2");
		return cd;
	}
	
	public CohortDefinition getTxWithUnknownCd4Cohorts() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx new clients WithUnknownCd4Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("NEW",
		    SSEMRReportUtils.map(getTxNewCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("C3", SSEMRReportUtils.map(getWithUnknownCd4Cohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND C3");
		return cd;
	}
	
	//TX_ML cohort queries
	public CohortDefinition getTxMlDiedCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Died");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlDiedQuery());
		return cd;
	}
	
	public CohortDefinition getTxMlIitL3mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for <3 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlIitL3mQuery());
		return cd;
	}
	
	public CohortDefinition getTxMlIit3To5mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 3-5 months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlIitL3To5mQuery());
		return cd;
	}
	
	public CohortDefinition getTxMlIitM6mCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - IIT After being on Treatment for 6+ months");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlIitM6mQuery());
		return cd;
	}
	
	public CohortDefinition getTxMlTransferOutCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Transfer Out");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlTransferOutQueries());
		return cd;
	}
	
	public CohortDefinition getTxMlRefusedStoppedTreatmentCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxMl Cohorts - Refused (Stopped) Treatment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxMlRefusedStoppedTreatmentQueries());
		return cd;
	}
	
	//TX RTT cohort Queries
	public CohortDefinition getTxRttWithCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxRttWithCd4LessThan200Queries());
		return cd;
	}
	
	public CohortDefinition getTxRttWithCd4GreaterOrEqual200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - CD4: ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxRttWithCd4GreaterOrEqual200Queries());
		return cd;
	}
	
	public CohortDefinition getTxRttWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxRttWithUnknownCd4Queries());
		return cd;
	}
	
	public CohortDefinition getTxRttNotEligibleForCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxRTT Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxRttNotEligibleForCd4Queries());
		return cd;
	}
	
	//TX PVLS Cohorts
	public CohortDefinition getTxPvlsAllCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxPVLS Cohorts - ALL");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxPvlsAllQueries());
		return cd;
	}
	
	public CohortDefinition getPregnantCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Pregnant");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getPregnantQueries());
		return cd;
	}
	
	public CohortDefinition getBreastfeedingCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Cohorts - Breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getBreastfeedingQueries());
		return cd;
	}
	
	public CohortDefinition getTxPvlsBreastfeedingCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are breastfeeding");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("P",
		    SSEMRReportUtils.map(getTxPvlsAllCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("B", SSEMRReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("P AND B");
		return cd;
	}
	
	public CohortDefinition getTxPvlsPregnantCohort() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Tx pvls clients who are pregnant");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.addSearch("PV",
		    SSEMRReportUtils.map(getTxPvlsAllCohorts(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("PR", SSEMRReportUtils.map(getBreastfeedingCohorts(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("PV AND PR");
		return cd;
	}
	
}
