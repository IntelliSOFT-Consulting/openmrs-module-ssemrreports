package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
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
	 * Cumulative number of patients ever started on ART at this facility at the end of the previous
	 * reporting period end of previous reporting period = start date - 1 day
	 * 
	 * @return
	 */
	public CohortDefinition getCumulativeEverOnARTAtThisFacilityCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n"
		        + "    client_id\n"
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment\n"
		        + "where date(art_start_date) <= date_sub(date(:startDate), interval 1 day) and date(art_start_date) <= date_sub(date(:startDate), interval 1 day)\n"
		        + " \n" + // we should add other pointers to start of art
		        "and (transferred_in is null or transferred_in = 'False')";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Cumulative patients started on ART at the end of the previous reporting period");
		
		return cd;
	}
	
	/**
	 * Cumulative number of patients ever started on ART at this facility at the end of the
	 * reporting period end of previous reporting period = start date - 1 day
	 * 
	 * @return
	 */
	public CohortDefinition getCumulativeEverOnARTAtThisFacilityAtEndOfReportingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment\n"
		        + "where encounter_datetime <= date(:endDate) and date(art_start_date) <= date(:endDate) \n"
		        + "and (transferred_in is not null or transferred_in = 'False')";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Cumulative patients started on ART at the end of the reporting period");
		
		return cd;
	}
	
	/**
	 * New persons started on ART at this facility during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition getNewOnARTCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n"
		        + "    client_id\n"
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment\n"
		        + "where date(art_start_date) between date(:startDate) and date(:endDate) and (transferred_in is null or transferred_in = 'False') "
		        + " having min(date(encounter_datetime)) between date(:startDate) and date(:endDate);";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("New persons started on ART during the reporting period");
		
		return cd;
	}
	
	/**
	 * current on ART
	 * 
	 * @return
	 */
	public CohortDefinition currentOnARTCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment\n"
		        + "where art_regimen is not null\n" + "  and transferred_in is not null "
		        + " having min(date(encounter_datetime)) between date(:startDate) and date(:endDate);";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription(" Current on ART during the reporting period");
		
		return cd;
	}
	
	/**
	 * Women who are pregnant during the reporting period This is currently computed using the edd
	 * variable in the followup visit form One is pregnant if edd variable has value and is on or
	 * after the reporting end date
	 * 
	 * @return
	 */
	public CohortDefinition getPregnantWomenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.client_pregnant = 'True' ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Pregnant women during the reporting period");
		
		return cd;
	}
	
	public CohortDefinition getBreastfeedingWomenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and (f.patient_breastfeeding is not null and f.patient_breastfeeding = 'True') ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Breastfeeding women during the reporting period");
		
		return cd;
	}
	
	public CohortDefinition getPatientsOnTLDCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen = 'TDF+3TC+DTG' ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on TLD during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnDTGRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and (f.art_regimen != 'TDF+3TC+DTG' and f.art_regimen like '%DTG%' )";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on DTG related regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnRegimenCohortDefinition(List<String> regimenList) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen in (:artRegimen) ";

		List<String> newList = new ArrayList<>();
		for (int i =0 ; i < regimenList.size(); i++) {
			newList.add("'" + regimenList.get(i) + "'"); // pad the string with "'" for use in sql query
		}
		String regimenString = "";
		if (!newList.isEmpty()) {
			regimenString = StringUtils.join(newList, ",");
		}

		qry.replace(":artRegimen", regimenString);

		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on related regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnRegimenCohortDefinition(String regimenName) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen = ':artRegimen' ";
		
		qry.replace(":artRegimen", regimenName);
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on a regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnFirstLineRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n"
		        + "    e.client_id\n"
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen in ('1a = AZT+3TC+EFV', '1b = AZT+3TC+NVP', '1c = TDF/3TC+DTG', "
		        + "'1d = ABC/3TC (600/300) +DTG', '1e = AZT/3TC+DTG','1f  = TDF+3TC+EFV','1g = TDF+3TC+NVP','1h = TDF +FTC+ EFV',"
		        + "'1j  = TDF+FTC+NVP', '4a = AZT+3TC+NVP', '4b = AZT+3TC+EFV', '4c = ABC/3TC (120/60)+LPV/r', '4d = ABC/3TC (120/60) +DTG', '4f =  ABC+3TC + NVP', '4g =  ABC/3TC (120/60) +EFV (200mg)', "
		        + "'4h =  TDF +3TC + EFV', '4i = ABC+3TC + LPV/r','4j = AZT +3TC(60/30)  + LPV/r','4k = TDF +3TC + NVP','4l  = ABC +3TC + AZT') ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on a regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnSecondLineRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n" + "    e.client_id\n" + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen in ('5a =AZT/3TC+LPV/r',\n" + "\t\t'5b =AZT +3TC + RAL',\n"
		        + "\t\t'5c =ABC +3TC(120/60)+ RAL',\n" + "\t\t'5d = AZT +3TC + ATV/r',\n" + "\t\t'5e =ABC +3TC + ATV/r',\n"
		        + "\t\t'5f = TDF + 3TC +ATV/r',\n" + "\t\t'5g = AZT/3TC+DTG',\n" + "\t\t'5h =ABC/3TC+DTG',\n"
		        + "\t\t'5i= ABC/3TC + LPV/r',\n" + "\t\t'2a = AZT/3TC + DTG'\n" + "\t\t'2b = ABC/3TC+DTG',\n"
		        + "\t\t'2c = TDF+3TC+LPV/r',\n" + "\t\t'2d = TDF+3TC+ATV/r',\n" + "\t\t'2e = TDF-FTC-LPV/r',\n"
		        + "\t\t'2f =  TDF-FTC-ATV/r',\n" + "\t\t'2g= AZT+3TC+LPV/r',\n" + "\t\t'2h= AZT+3TC+ATV/r',\n"
		        + "\t\t'2i ABC +3TC + LPV/r',\n" + "\t\t'2J ABC +3TC + ATV/r',\n" + "\t\t'2k   TDF/3TC/DTG') ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on a regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getAgeAtStartOfART(int minAge, int maxAge, String sex) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n"
		        + "\tfrom (\n"
		        + "\tselect f.encounter_datetime, f.client_id, p.gender, timestampdiff(YEAR, p.birthdate, e.art_start_date) ageAtArtStart, fup.ltfu_date, f.days_dispensed,\n"
		        + "\t       date_add(f.encounter_datetime, interval (case days_dispensed when '30 days' then 30 when '60 days' then 60 when '90 days' then 90 when '180 days' then 180 else 0 end) DAY) nextDrugApptDate\n"
		        + "\tfrom ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "\t inner join ssemr_etl.mamba_dim_person p on p.person_id = e.client_id\n"
		        + "\t    inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f on f.client_id = e.client_id\n"
		        + "\t left join ssemr_etl.ssemr_flat_encounter_end_of_follow_up fup on e.client_id = fup.client_id\n"
		        + "\t where (fup.date_of_death is null or fup.date_of_death > date(:endDate))\n"
		        + "\t and (fup.ltfu_date is null or fup.ltfu_date not between date(:startDate) and date(:endDate))\n"
		        + "\t group by e.client_id\n" + "\t having date_add(nextDrugApptDate, interval 30 day) > date(:endDate)\n"
		        + "\t ) a \n" + "where  ageAtArtStart between " + minAge + "  and " + maxAge + " and gender = '" + sex + "'";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients of a given age group and sex");
		return cd;
	}
	
	// ----- --- viral load sample collection
	
	public CohortDefinition getVLSampleCollectionCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_of_sample_collection) between date(:startDate) and date(:endDate)\n" + "";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL sample collected during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLSampleCollectionForPregnantCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_of_sample_collection) between date(:startDate) and date(:endDate)\n"
		        + "and patient_pregnant = 'True' ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL sample collected for the pregnant during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLSampleCollectionForBreastfeedingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_of_sample_collection) between date(:startDate) and date(:endDate)\n"
		        + "and patient_breastfeeding = 'True' ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL sample collected for the breastfeeding during the reporting period");
		return cd;
	}
	
	// ----- --- viral load results
	
	public CohortDefinition getVLResultsCohortDefinition(int minVal, int maxVal) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_results_dispatched) between date(:startDate) and date(:endDate) and value between "
		        + minVal + " and " + maxVal;
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL results received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLResultsForPregnantCohortDefinition(int minVal, int maxVal) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_results_dispatched) between date(:startDate) and date(:endDate)\n"
		        + "and patient_pregnant = 'True' and value between " + minVal + " and " + maxVal;
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL results for the pregnant received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLResultsForBreastfeedingCohortDefinition(int minVal, int maxVal) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select client_id\n" + "from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request\n"
		        + "where date(date_results_dispatched) between date(:startDate) and date(:endDate)\n"
		        + "and patient_breastfeeding = 'True' and value between " + minVal + " and " + maxVal;
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("VL results for the breastfeeding received during the reporting period");
		return cd;
	}
	
	/*select client_id, value,patient_pregnant,patient_breastfeeding
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_of_sample_collection) between '2023-10-01' and '2023-12-01';

	-- sample collection for the pregnant
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_of_sample_collection) between '2023-10-01' and '2023-12-01'
	and patient_pregnant = 'True';

	-- sample collection for the breastfeeding
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_of_sample_collection) between '2023-10-01' and '2023-12-01'
	and patient_breastfeeding = 'True';

	-- vl results for the pregnant and results <1000
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_results_dispatched) between '2023-10-01' and '2023-12-01'
	and patient_pregnant = 'True' and value < 1000;

	-- vl results for the pregnant and results >=1000
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_results_dispatched) between '2023-10-01' and '2023-12-01'
	and patient_pregnant = 'True' and value >= 1000;

	-- vl results for the breastfeeding and results <1000
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_results_dispatched) between '2023-10-01' and '2023-12-01'
	and patient_breastfeeding = 'True' and value < 1000;

	-- vl results for the breastfeeding and results >=1000
	select client_id
	from ssemr_etl.ssemr_flat_encounter_vl_laboratory_request
	where date(date_results_dispatched) between '2023-10-01' and '2023-12-01'
	and patient_breastfeeding = 'True' and value >= 1000;*/
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
	
	/**
	 * Patients who are current on art and on 1st-line
	 * 
	 * @return
	 */
	public CohortDefinition getCurrentOnArtOnFirstLineRegimen(int minAge, int maxAge, String sex) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients currently on ART and are on first line regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("currentOnArt",
		    SSEMRReportUtils.map(getAgeAtStartOfART(minAge, maxAge, sex), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("onFirstLineRegimen", SSEMRReportUtils.map(getPatientsOnFirstLineRegimenCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentOnArt AND onFirstLineRegimen");
		return cd;
	}
	
	/**
	 * Patients who are current on art and on 2nd-line
	 * 
	 * @return
	 */
	public CohortDefinition getCurrentOnArtOnSecondLineRegimen(int minAge, int maxAge, String sex) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients currently on ART and are on second line regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("currentOnArt",
		    SSEMRReportUtils.map(getAgeAtStartOfART(minAge, maxAge, sex), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("onSecondLineRegimen", SSEMRReportUtils.map(getPatientsOnSecondLineRegimenCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentOnArt AND onSecondLineRegimen");
		return cd;
	}
	
	/**
	 * Patients who are newly initiated on ART and are pregnant as at their last clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition getNewOnArtAndPregnant() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on ART and pregnant");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt",
		    SSEMRReportUtils.map(getNewOnARTCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pregnant",
		    SSEMRReportUtils.map(getPregnantWomenCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("newlyStartedOnArt AND pregnant");
		return cd;
	}
	
	/**
	 * Patients who are newly initiated on ART and are breastfeeding as at their last clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition getNewOnArtAndBreastfeeding() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on ART and breastfeeding");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt",
		    SSEMRReportUtils.map(getNewOnARTCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("breastfeeding",
		    SSEMRReportUtils.map(getBreastfeedingWomenCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("newlyStartedOnArt AND breastfeeding");
		return cd;
	}
	
	public CohortDefinition getNewOnTLDRegimen() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on TLD regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt",
		    SSEMRReportUtils.map(getNewOnARTCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("onTLDRegimen",
		    SSEMRReportUtils.map(getPatientsOnTLDCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("newlyStartedOnArt AND onTLDRegimen");
		return cd;
	}
	
	public CohortDefinition getNewOnOtherDTGBasedRegimen() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on TLD regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		//cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt",
		    SSEMRReportUtils.map(getNewOnARTCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("onOtherDTGRegimen",
		    SSEMRReportUtils.map(getPatientsOnDTGRegimenCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("newlyStartedOnArt AND onOtherDTGRegimen");
		return cd;
	}
	
}
