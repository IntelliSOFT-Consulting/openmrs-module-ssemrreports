package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.stereotype.Component;

@Component
public class ArtCohortQueries {
	
	private final SharedCohortQueries sharedCohortQueries;
	
	private final BaseCohortQueries baseCohortQueries;
	
	String labEncounterType = SharedReportConstants.LAB_RESULT_ENCOUNTER_TYPE_UUID;
	
	String labOrderEncounterType = SharedReportConstants.LAB_ORDER_ENCOUNTER_TYPE_UUID;
	
	public ArtCohortQueries(SharedCohortQueries sharedCohortQueries, BaseCohortQueries baseCohortQueries) {
		this.sharedCohortQueries = sharedCohortQueries;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	public CohortDefinition getART1CohortDefinition() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}

	/**
	 * Cumulative number of patients ever started on ART at this facility at the end of the previous reporting period
	 * end of previous reporting period = start date - 1 day
	 * @return
	 */
	public CohortDefinition getCumulativeEverOnARTAtThisFacilityCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" +
				"    patient_id\n" +
				"from ssemr_etl.flat_encounter_hiv_care_enrolment\n" +
				"where visit_date <= date_sub(date(:startDate), interval 1 day)\n" +
				"and art_regimen is not null\n" +
				"and transferred_in_on_art_from_another_treatment_site is not null";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Cumulative patients started on ART at the end of the previous reporting period");

		return cd;
	}

	/**
	 * New persons started on ART at this facility during the reporting period
	 * @return
	 */
	public CohortDefinition getNewOnARTCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" +
				"    patient_id\n" +
				"from ssemr_etl.flat_encounter_hiv_care_enrolment\n" +
				"where visit_date between :startDate and :endDate \n" +
				"  and art_regimen is not null\n" +
				"  and transferred_in_on_art_from_another_treatment_site is not null;";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("New persons started on ART during the reporting period");

		return cd;
	}

	public CohortDefinition getNewOnARTPregnantWomenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" +
				"    patient_id\n" +
				"from ssemr_etl.flat_encounter_hiv_care_enrolment\n" +
				"where visit_date between :startDate and :endDate \n" +
				"  and art_regimen is not null\n" +
				"  and transferred_in_on_art_from_another_treatment_site is not null;";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("New persons started on ART during the reporting period");

		return cd;
	}

	public CohortDefinition getNewOnARTBreastfeedingWomenCohortDefinition() {
		SqlCohortDefinition sql = new SqlCohortDefinition();

		return sql;
	}

	public CohortDefinition getNewOnARTStartedOnTLDCohortDefinition() {
		SqlCohortDefinition sql = new SqlCohortDefinition();

		return sql;
	}

	public CohortDefinition getNewOnARTStartedOnOtherDTGRegimenCohortDefinition() {
		SqlCohortDefinition sql = new SqlCohortDefinition();

		return sql;
	}
	public CohortDefinition getART2CohortDefinition() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}
	
	public CohortDefinition getPatientsOnArtWithDetectableViralLoadR5() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}
	
	public CohortDefinition getPatientsOnArtWithDetectableViralLoadR6() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}
	
	public CohortDefinition getPatientsOnArtWithDetectableViralLoadR7() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}
	
	public CohortDefinition getPatientsOnArtWithDetectableViralLoadR8() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		
		return sql;
	}
	
	public CohortDefinition getHasObsBetweenDatesDefinition(List<Integer> question, List<Integer> ans) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.addParameter(new Parameter("location", "Location", Location.class));
		sql.setName("Get patients with observation recoded with value coded as answer");
		sql.setQuery(CommonQueries.hasObs(question, ans));
		return sql;
	}
	
}
