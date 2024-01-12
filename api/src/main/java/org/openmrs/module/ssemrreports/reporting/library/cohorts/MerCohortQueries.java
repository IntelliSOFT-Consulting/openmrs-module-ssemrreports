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
}
