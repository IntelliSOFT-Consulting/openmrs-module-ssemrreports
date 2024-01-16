package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.MerQueries;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MerCohortQueries {
	
	public CohortDefinition getTxCurrCohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxCurr Cohorts");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxCurrQuery());
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
	public CohortDefinition getTxNewWithCd4LessThan200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - CD4 < 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxNewWithCd4LessThan200Query());
		return cd;
	}
	
	public CohortDefinition getTxNewWithCd4GreaterThanOrEqualTo200Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - CD4 ≥ 200");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxNewWithCd4MoreThanOrEqualTo200Query());
		return cd;
	}
	
	public CohortDefinition getTxNewWithUnknownCd4Cohorts() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("TxNew Cohorts - Unknown CD4");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Facility", Location.class));
		cd.setQuery(MerQueries.getTxNewWithUnknownCd4Query());
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
	
}
