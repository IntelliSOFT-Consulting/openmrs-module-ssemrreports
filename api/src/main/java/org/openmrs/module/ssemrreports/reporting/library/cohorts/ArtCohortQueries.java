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
import org.openmrs.module.ssemrreports.reporting.library.queries.ArtQueries;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.ssemrreports.reporting.library.queries.MerQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.art.ArtReportsConstants;
import org.springframework.stereotype.Component;

@Component
public class ArtCohortQueries {
	
	private final MerCohortQueries merCohortQueries;
	
	String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public ArtCohortQueries(MerCohortQueries merCohortQueries) {
		this.merCohortQueries = merCohortQueries;
	}
	
	/**
	 * Cumulative number of patients ever started on ART at this facility at the end of the previous
	 * reporting period end of previous reporting period = start date - 1 day
	 * 
	 * @return
	 */
	public CohortDefinition getCumulativeEverOnARTAtThisFacilityInPreviousReportingPeriodCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sql = "SELECT su1.client_id FROM("
		
		+ "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <= :endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location " + ")su1";
		cd.setQuery(sql);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Cumulative patients started on ART at the end of the previous reporting period");
		
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Cummulative without IIT");
		comp.addParameter(new Parameter("startDate", "Start Date", Date.class));
		comp.addParameter(new Parameter("endDate", "End Date", Date.class));
		comp.addParameter(new Parameter("location", "Location", Location.class));
		
		comp.addSearch("CUMM", SsemrReportUtils.map(cd, "startDate=${startDate},endDate=${endDate},location=${location}"));
		comp.addSearch("IIT",
		    SsemrReportUtils.map(merCohortQueries.getIITPatientsOverPeriod(), "endDate=${endDate},location=${location}"));
		
		comp.setCompositionString("CUMM AND NOT IIT");
		return comp;
	}
	
	/**
	 * Cumulative number of patients ever started on ART at this facility at the end of the
	 * reporting period, reporting period = endDate
	 * 
	 * @return
	 */
	public CohortDefinition getCumulativeEverOnARTAtThisFacilityAtEndOfReportingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sql = "SELECT su1.client_id FROM("
		
		+ "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location " + ")su1";
		cd.setQuery(sql);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
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
		cd.setQuery(MerQueries.getTxNewTotals());
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("New persons started on ART during the reporting period");
		
		return cd;
	}
	
	/**
	 * New persons started on ART at this facility during the reporting period And are on CTX
	 * 
	 * @return
	 */
	public CohortDefinition getNewOnARTonCTXCohortDefinition() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("New patients who are on CTX");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW", SsemrReportUtils.map(getNewOnARTCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("CTX", SsemrReportUtils.map(patientsOnCTXTreatmentCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND CTX");
		return cd;
	}
	
	/**
	 * New persons started on ART at this facility during the reporting period And are on Dapsone
	 * 
	 * @return
	 */
	public CohortDefinition getNewOnARTonDapsoneCohortDefinition() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("New patients who are on Dapsone");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW", SsemrReportUtils.map(getNewOnARTCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("DAP", SsemrReportUtils.map(patientsOnDapsoneTreatmentCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEW AND DAP");
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
		cd.setQuery(MerQueries.getPregnantQueries());
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Pregnant women during the reporting period");
		
		return cd;
	}
	
	public CohortDefinition getBreastfeedingWomenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setQuery(MerQueries.getBreastfeedingQueries());
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Breastfeeding women during the reporting period");
		
		return cd;
	}
	
	public CohortDefinition getPatientsOnTLDCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String qry = "SELECT " + " fu.client_id " + " FROM " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime = ( " + " SELECT " + " MAX(f2.encounter_datetime) " + " FROM "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f2 " + " WHERE " + " f2.client_id = fu.client_id "
		        + " AND f2.encounter_datetime BETWEEN :startDate AND :endDate " + " ) "
		        + " AND TRIM(SUBSTRING_INDEX(fu.art_regimen, '=', -1)) = 'TDF/3TC/DTG' ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on TLD during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnDTGRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry =
		
		"SELECT "
		        + " fu.client_id "
		        + " FROM "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE "
		        + " fu.encounter_datetime = ( "
		        + " SELECT "
		        + " MAX(f2.encounter_datetime) "
		        + " FROM "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f2 "
		        + " WHERE "
		        + " f2.client_id = fu.client_id "
		        + " AND f2.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " ) "
		        + " AND TRIM(SUBSTRING_INDEX(fu.art_regimen, '=', -1)) NOT IN('TDF/3TC/DTG') AND TRIM(SUBSTRING_INDEX(fu.art_regimen, '=', -1) like '%DTG%') ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on DTG related regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getPatientsOnRegimenCohortDefinition(List<String> regimenList) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select " + "    e.client_id " + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e "
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id) "
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate)  "
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
		String qry = "SELECT f.client_id "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE f.location_id=:location and f.art_regimen IS NOT NULL AND DATE(f.encounter_datetime) BETWEEN :startDate AND DATE(:endDate) "
		        + " GROUP BY f.client_id "
		        + " HAVING REPLACE(MID(MAX(CONCAT(f.encounter_datetime, f.art_regimen)),20),' ','') = REPLACE(':artRegimen',' ','') ";
		
		qry = qry.replace(":artRegimen", regimenName);
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on a regimen during the reporting period");
		return cd;
	}
	
	/**
	 * Constructs a cohort definition for patients on first-line ART regimens.
	 * <p>
	 * This method builds an SQL query that retrieves client IDs from the patient encounter history
	 * by joining personal/transmission records with HIV care follow-up data. It extracts the most
	 * recent regimen information for each patient and compares it against a concatenation of adult
	 * and child first-line regimens. Records are filtered based on the specified location and end
	 * date, ensuring that only patients on a first-line regimen during the reporting period are
	 * included.
	 * 
	 * @return a cohort definition for patients on first-line ART regimens during the reporting
	 *         period
	 */
	public CohortDefinition getPatientsOnFirstLineRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String regimensString = SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.adultFirstLineRegimen)
		        + SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.childFirstLineRegimen);
		String qry = "SELECT client_id FROM ( " + " SELECT "
		        + " f.client_id , mid(max(CONCAT(f.encounter_datetime,f.art_regimen)),20) AS art_regimen "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE f.location_id=:location AND DATE(f.encounter_datetime) <= DATE(:endDate)   "
		        + " GROUP BY client_id  " + ") c where REPLACE(c.art_regimen,' ','')  " + " IN ( " + regimensString + " ) ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on a regimen during the reporting period");
		return cd;
	}
	
	/**
	 * Creates a cohort definition for patients on second-line ART regimens.
	 * <p>
	 * This method constructs a SQL-based cohort definition that selects patients whose latest ART
	 * regimen, after whitespace normalization, matches one of the predefined adult or child
	 * second-line regimens. The resulting definition is parameterized with a reporting period end
	 * date, a start date (included for completeness), and a location.
	 * </p>
	 * 
	 * @return a CohortDefinition representing patients on second-line ART regimens during the
	 *         reporting period
	 */
	public CohortDefinition getPatientsOnSecondLineRegimenCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String regimensString = SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.adultSecondLineRegimen)
		        + SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.childSecondLineRegimen);
		
		String qry = "SELECT client_id from ( "
		        + " SELECT f.client_id , MID(MAX(CONCAT(f.encounter_datetime,f.art_regimen)),20) AS art_regimen "
		        + "  FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + "  WHERE f.location_id=:location AND DATE(f.encounter_datetime) <= DATE(:endDate)   "
		        + "  GROUP BY client_id  " + ") c WHERE REPLACE(c.art_regimen,' ','') in ( " + regimensString + " ) ; ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on a second line regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getAgeAtStartOfART(int minAge, int maxAge, String sex) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String query = "SELECT DISTINCT(client_id)"
		        + " FROM ( "
		        + " SELECT f.encounter_datetime, f.client_id, p.gender, timestampdiff(YEAR, p.birthdate, e.art_start_date) ageAtArtStart, fup.ltfu_date, f.art_regimen_no_of_days_dispensed, "
		        + " DATE_ADD(f.encounter_datetime, interval (case f.art_regimen_no_of_days_dispensed when '30 Days' then 30 when '60 Days' then 60 when '90 Days' then 90 when '180 Days' then 180 when '180+ Days' then 180 when '150 Days' then 150 else 0 end) DAY) nextDrugApptDate "
		        + " FROM("
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        + " )e"
		        + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = e.client_id "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f on f.client_id = e.client_id "
		        + " LEFT JOIN ssemr_etl.ssemr_flat_encounter_end_of_follow_up fup on e.client_id = fup.client_id "
		        + " WHERE e.location_id=:location AND DATE(f.encounter_datetime) BETWEEN :startDate AND :endDate AND (fup.date_of_death IS NULL OR fup.date_of_death > DATE(:endDate)) "
		        + " AND (fup.ltfu_date is null or fup.ltfu_date NOT BETWEEN DATE(:startDate) AND date(:endDate)) "
		        + " GROUP BY e.client_id, f.encounter_datetime, p.gender, ageAtArtStart,fup.ltfu_date,f.art_regimen_no_of_days_dispensed, nextDrugApptDate "
		        + " HAVING DATE_ADD(nextDrugApptDate, INTERVAL 30 DAY) > DATE(:endDate)) a "
		        + " WHERE  ageAtArtStart BETWEEN " + minAge + "  and " + maxAge + " and gender = '" + sex + "'";
		cd.setQuery(query);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients of a given age group and sex");
		return cd;
	}
	
	public CohortDefinition getAgeAtStartOfARTonFirstLine(int minAge, int maxAge, String sex) {
		
		String regimensString = SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.adultFirstLineRegimen)
		        + SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.childFirstLineRegimen);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String query = "SELECT b.client_id FROM( "
		        + " SELECT DISTINCT(client_id)"
		        + " FROM ( "
		        + " SELECT f.encounter_datetime, f.client_id, p.gender, timestampdiff(YEAR, p.birthdate, :endDate) ageAtArtStart, fup.ltfu_date, f.art_regimen_no_of_days_dispensed, "
		        + " DATE_ADD(f.encounter_datetime, interval (case f.art_regimen_no_of_days_dispensed when '30 Days' then 30 when '60 Days' then 60 when '90 Days' then 90 when '180 Days' then 180 when '180+ Days' then 180 when '150 Days' then 150 else 0 end) DAY) nextDrugApptDate "
		        + " FROM("
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        + " )e"
		        + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = e.client_id "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f on f.client_id = e.client_id "
		        + " LEFT JOIN ssemr_etl.ssemr_flat_encounter_end_of_follow_up fup on e.client_id = fup.client_id "
		        + " WHERE e.location_id=:location AND DATE(f.encounter_datetime) BETWEEN :startDate AND :endDate AND (fup.date_of_death IS NULL OR fup.date_of_death > DATE(:endDate)) "
		        + " AND (fup.ltfu_date is null or fup.ltfu_date NOT BETWEEN DATE(:startDate) AND date(:endDate)) "
		        + " GROUP BY e.client_id, f.encounter_datetime, p.gender, ageAtArtStart,fup.ltfu_date,f.art_regimen_no_of_days_dispensed, nextDrugApptDate "
		        + " HAVING DATE_ADD(nextDrugApptDate, INTERVAL 30 DAY) > DATE(:endDate)) a "
		        + " WHERE  ageAtArtStart BETWEEN " + minAge + "  and " + maxAge + " and gender = '" + sex + "'" + " )b"
		        + " INNER JOIN "
		        
		        + "(SELECT client_id FROM ( " + " SELECT "
		        + " f.client_id , mid(max(CONCAT(f.encounter_datetime,f.art_regimen)),20) AS art_regimen "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE f.location_id=:location AND DATE(f.encounter_datetime) <= DATE(:endDate)   "
		        + " GROUP BY client_id  " + ") c where REPLACE(c.art_regimen,' ','')  " + " IN ( " + regimensString
		        + " ))c " + " ON b.client_id=c.client_id";
		cd.setQuery(query);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients of a given age group and sex");
		return cd;
	}
	
	public CohortDefinition getAgeAtStartOfARTonSecondLine(int minAge, int maxAge, String sex) {
		
		String regimensString = SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.adultSecondLineRegimen)
		        + SsemrReportUtils.concatenateStringAndQuote(ArtReportsConstants.childSecondLineRegimen);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		String query = "SELECT b.client_id FROM( "
		        + " SELECT DISTINCT(client_id)"
		        + " FROM ( "
		        + " SELECT f.encounter_datetime, f.client_id, p.gender, timestampdiff(YEAR, p.birthdate, e.art_start_date) ageAtArtStart, fup.ltfu_date, f.art_regimen_no_of_days_dispensed, "
		        + " DATE_ADD(f.encounter_datetime, interval (case f.art_regimen_no_of_days_dispensed when '30 Days' then 30 when '60 Days' then 60 when '90 Days' then 90 when '180 Days' then 180 when '180+ Days' then 180 when '150 Days' then 150 else 0 end) DAY) nextDrugApptDate "
		        + " FROM("
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + " UNION "
		        
		        + " SELECT client_id,art_start_date,location_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        + " )e"
		        + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = e.client_id "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f on f.client_id = e.client_id "
		        + " LEFT JOIN ssemr_etl.ssemr_flat_encounter_end_of_follow_up fup on e.client_id = fup.client_id "
		        + " WHERE e.location_id=:location AND DATE(f.encounter_datetime) BETWEEN :startDate AND :endDate AND (fup.date_of_death IS NULL OR fup.date_of_death > DATE(:endDate)) "
		        + " AND (fup.ltfu_date is null or fup.ltfu_date NOT BETWEEN DATE(:startDate) AND date(:endDate)) "
		        + " GROUP BY e.client_id, f.encounter_datetime, p.gender, ageAtArtStart,fup.ltfu_date,f.art_regimen_no_of_days_dispensed, nextDrugApptDate "
		        + " HAVING DATE_ADD(nextDrugApptDate, INTERVAL 30 DAY) > DATE(:endDate)) a "
		        + " WHERE  ageAtArtStart BETWEEN " + minAge + "  and " + maxAge + " and gender = '" + sex + "'" + " )b"
		        + " INNER JOIN "
		        
		        + "(SELECT client_id FROM ( " + " SELECT "
		        + " f.client_id , mid(max(CONCAT(f.encounter_datetime,f.art_regimen)),20) AS art_regimen "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE f.location_id=:location AND DATE(f.encounter_datetime) <= DATE(:endDate)   "
		        + " GROUP BY client_id  " + ") c where REPLACE(c.art_regimen,' ','')  " + " IN ( " + regimensString
		        + " ))c " + " ON b.client_id=c.client_id";
		cd.setQuery(query);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients of a given age group and sex");
		return cd;
	}
	
	public CohortDefinition getPregnantPatientsOnRegimenCohortDefinition(String regimenName) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT "
		        + " f.client_id "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE f.location_id=:location AND DATE(f.encounter_datetime) BETWEEN DATE(:startDate) AND DATE(:endDate) "
		        + " AND f.art_regimen = ':artRegimen' and f.client_pregnant = 'Yes' ";
		
		qry.replace(":artRegimen", regimenName);
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Pregnant patients on a regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getBreastFeedingPatientsOnRegimenCohortDefinition(String regimenName) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT "
		        + " f.client_id "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " WHERE DATE(f.encounter_datetime) BETWEEN DATE(:startDate) AND DATE(:endDate) "
		        + " AND f.location_id=:location AND f.art_regimen = ':artRegimen' AND (f.client_breastfeeding IS NOT NULL AND f.client_breastfeeding = 'Yes')";
		
		qry.replace(":artRegimen", regimenName);
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Breastfeeding patients on a regimen during the reporting period");
		return cd;
	}
	
	public CohortDefinition getTBAssessmentStatusCohortDefinition(String assessmentStatus) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n"
		        + "    e.client_id\n"
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + "where date(f.encounter_datetime) between date(:startDate) and date(:endDate) \n"
		        + "  and f.art_regimen = ':assessmentStatus' and (f.client_breastfeeding is not null and f.client_breastfeeding = 'Yes')";
		
		qry.replace(":assessmentStatus", assessmentStatus);
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients assessed for TB during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsCurrentlyOnTBCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select\n"
		        + "    e.client_id\n"
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e\n"
		        + "inner join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f using(client_id)\n"
		        + " where f.on_tb_treatment = 'Yes' and date(f.encounter_datetime) between date(:startDate) and date(:endDate)\n"
		        + " group by f.client_id;";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients treated for TB during the reporting period");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients with a viral load (VL) sample collected during the
	 * reporting period.
	 * <p>
	 * This method constructs a SQL-based cohort definition that retrieves client IDs from the HIV
	 * care follow-up table based on the VL sample collection date falling between the specified
	 * start and end dates for a given location.
	 * </p>
	 * 
	 * @return a SQL cohort definition for VL sample collection during the reporting period
	 */
	
	public CohortDefinition getVLSampleCollectionCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT client_id "
		        + "FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + "WHERE location_id=:location AND DATE(date_vl_sample_collected) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL 1 DAY) ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("VL sample collected during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLSampleCollectionForPregnantCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT client_id "
		        + "FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + "WHERE location_id=:location AND DATE(date_vl_sample_collected) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL 1 DAY) "
		        + " AND client_pregnant = 'Yes' ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("VL sample collected for the pregnant during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLSampleCollectionForBreastfeedingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT client_id "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " WHERE location_id=:location AND DATE(date_vl_sample_collected) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL 1 DAY) "
		        + " AND client_breastfeeding = 'Yes' ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("VL sample collected for the breastfeeding during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsNewlyInitiatedOnTBTreatmentCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		cd.setQuery(ArtQueries.getTxNewTotalsAndOnTbWithStatusTreatment());
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients newly initiated on TB treatment during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsOnArtWithTbStatusWithStatusCohortDefinition(String status) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		cd.setQuery(ArtQueries.getTxNewTotalsAndOnTbWithStatus(status));
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients with TB status recorded by end of reporting period with TB status recorded");
		return cd;
	}
	
	public CohortDefinition patientsOnINHTreatmentCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		
		cd.setQuery(ArtQueries.getTxNewTotalsAndOnTbWithStatusINH());
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("New Patients on INH during the reporting period");
		return cd;
	}
	
	public CohortDefinition getNewOnArtWithTbStatusCohortDefinition(String status) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("New patients who are on ART with TB status");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEWTBSTATUS", SsemrReportUtils.map(patientsOnArtWithTbStatusWithStatusCohortDefinition(status),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEWTBSTATUS");
		return cd;
	}
	
	public CohortDefinition getNewOnArtAndOnTbTreatmentCohortDefinition() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("New patients who are on ART and on TB treatment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW", SsemrReportUtils.map(getNewOnARTCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("NEWANDTxR", SsemrReportUtils.map(patientsNewlyInitiatedOnTBTreatmentCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEWANDTxR");
		return cd;
	}
	
	public CohortDefinition getNewOnArtAndOnInhCohortDefinition() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("New patients who are on ART and on INH");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addSearch("NEW", SsemrReportUtils.map(getNewOnARTCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("NEWANDINH", SsemrReportUtils.map(patientsOnINHTreatmentCohortDefinition(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("NEWANDINH");
		return cd;
	}
	
	public CohortDefinition patientsOnCTXTreatmentCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT su1.client_id FROM( "
		        + " SELECT fu.client_id,MAX(fu.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.on_ctx IS NOT NULL AND DATE(fu.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + "GROUP BY fu.client_id " + ")su1" + " INNER JOIN "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 ON su1.client_id=fu1.client_id "
		        + " WHERE fu1.on_ctx='Yes' AND su1.encounter_datetime=fu1.encounter_datetime ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on CTX during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsOnDapsoneTreatmentCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT su1.client_id FROM( "
		        + " SELECT fu.client_id,MAX(fu.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.on_dapsone IS NOT NULL AND DATE(fu.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + "GROUP BY fu.client_id " + ")su1" + " INNER JOIN "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 ON su1.client_id=fu1.client_id "
		        + " WHERE fu1.on_dapsone='Yes' AND su1.encounter_datetime=fu1.encounter_datetime ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on Dapsone during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsLtfuCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT q2.client_id FROM ( "
		        + " SELECT q1.client_id,q1.follow_up_date,t2.art_regimen_no_of_days_dispensed FROM ( "
		        + " SELECT t1.client_id AS client_id, MAX(t1.encounter_datetime) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t1 "
		        + " WHERE t1.encounter_datetime <= :endDate AND t1.location_id=:location "
		        + " GROUP BY t1.client_id) q1 "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t2 ON q1.client_id=t2.client_id "
		        + " WHERE q1.follow_up_date = t2.encounter_datetime "
		        + " AND DATE(DATE_ADD(DATE_ADD(q1.follow_up_date, interval CAST(t2.art_regimen_no_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY)) <= :endDate)q2";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on ltfu during the reporting period");
		return cd;
	}
	
	public CohortDefinition patientsDeadCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT f.client_id " + "FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up f "
		        + "WHERE f.location_id=:location AND DATE(f.date_of_death) BETWEEN DATE(:startDate) AND DATE(:endDate) ";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients who died during the reporting period");
		return cd;
	}
	
	// ----- --- viral load results
	//Suppression
	public CohortDefinition getVLResultsCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = " SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received"
		        + " AND (fu2.viral_load_value < 1000 OR fu2.vl_results = 'Below Detectable (BDL)')";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Suppression VL results received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLResultsForPregnantCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = " SELECT a.client_id FROM("
		        + " SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received"
		        + " AND (fu2.viral_load_value < 1000 OR fu2.vl_results = 'Below Detectable (BDL)'))a "
		        + "INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up b ON b.client_id=a.client_id"
		        + " WHERE b.client_pregnant = 'Yes'";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Suppression VL results for the pregnant received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getVLResultsForBreastfeedingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = " SELECT a.client_id FROM("
		        + " SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received"
		        + " AND (fu2.viral_load_value < 1000 OR fu2.vl_results = 'Below Detectable (BDL)'))a "
		        + "INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up b ON b.client_id=a.client_id"
		        + " WHERE b.client_breastfeeding = 'Yes'";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("suppression VL results for the breastfeeding received during the reporting period");
		return cd;
	}
	
	//VL Retention queries
	public CohortDefinition getVLRetentionResultsCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received" + " AND fu2.viral_load_value >= 1000";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Retention VL results received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getRetentionVLResultsForPregnantCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT a.client_id FROM("
		        + " SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received" + " AND fu2.viral_load_value >= 1000)a "
		        
		        + "INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up b ON b.client_id=a.client_id"
		        + " WHERE b.client_pregnant = 'Yes'";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Retention VL results and pregnant received during the reporting period");
		return cd;
	}
	
	public CohortDefinition getRetentionVLResultsForBreastFeedingCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "SELECT a.client_id FROM("
		        + " SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN DATE_ADD(:startDate, INTERVAL -1 MONTH) AND DATE_ADD(:startDate, INTERVAL -1 DAY) "
		        + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received" + " AND fu2.viral_load_value >= 1000)a "
		        
		        + "INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up b ON b.client_id=a.client_id"
		        + " WHERE b.client_breastfeeding = 'Yes'";
		
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Retention VL results and breastfeeding received during the reporting period");
		return cd;
	}
	
	/**
	 * Constructs a composite cohort definition of patients who are both currently on ART and on a
	 * first-line regimen.
	 * <p>
	 * This method combines two cohort definitions:
	 * <ul>
	 * <li>a cohort filtered by patients' age at the start of ART and their current ART status (with
	 * a specified gender), and</li>
	 * <li>a cohort of patients on a first-line ART regimen.</li>
	 * </ul>
	 * The two cohorts are intersected using a logical AND. The resulting composite definition is
	 * parameterized with <code>startDate</code>, <code>endDate</code>, and <code>location</code>,
	 * which must be supplied during evaluation.
	 * 
	 * @param minAge the minimum age (inclusive) for the ART initiation age filter
	 * @param maxAge the maximum age (inclusive) for the ART initiation age filter
	 * @param sex the gender used to filter the cohort
	 * @return a composite CohortDefinition representing patients who are currently on ART and are
	 *         on a first-line regimen
	 */
	public CohortDefinition getCurrentOnArtOnFirstLineRegimen(int minAge, int maxAge, String sex) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients currently on ART and are on first line regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("currentOnArtFirstLine", SsemrReportUtils.map(getAgeAtStartOfARTonFirstLine(minAge, maxAge, sex),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("currentOnArtFirstLine");
		return cd;
	}
	
	/**
	 * Constructs a composite cohort definition for patients currently on ART and on a second-line
	 * regimen.
	 * <p>
	 * This method creates a composition cohort that comprises two sub-cohorts:
	 * <ul>
	 * <li>Patients filtered by age at ART initiation within the specified range and sex.</li>
	 * <li>Patients on a second-line antiretroviral regimen.</li>
	 * </ul>
	 * The resulting cohort definition is parameterized with a start date, end date, and location
	 * for querying.
	 * 
	 * @param minAge the minimum age (inclusive) at ART initiation
	 * @param maxAge the maximum age (inclusive) at ART initiation
	 * @param sex the sex to filter patients by
	 * @return a composite cohort definition combining both criteria
	 */
	public CohortDefinition getCurrentOnArtOnSecondLineRegimen(int minAge, int maxAge, String sex) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients currently on ART and are on second line regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("currentOnArtAndOnSecondLineRegimen", SsemrReportUtils.map(
		    getAgeAtStartOfARTonSecondLine(minAge, maxAge, sex),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("currentOnArtAndOnSecondLineRegimen");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients who are both newly initiated on ART and pregnant at
	 * their last clinical visit.
	 * <p>
	 * The composition cohort is created by intersecting the cohort of patients newly started on ART
	 * with the cohort of pregnant women. It requires the parameters <code>startDate</code>,
	 * <code>endDate</code>, and <code>location</code> to define the reporting period and facility
	 * context.
	 * </p>
	 * 
	 * @return a cohort definition representing newly initiated ART patients who are pregnant
	 */
	public CohortDefinition getNewOnArtAndPregnant() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on ART and pregnant");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt", SsemrReportUtils.map(getNewOnARTCohortDefinition(), mappings));
		cd.addSearch("pregnant", SsemrReportUtils.map(getPregnantWomenCohortDefinition(), mappings));
		cd.setCompositionString("newlyStartedOnArt AND pregnant");
		return cd;
	}
	
	/**
	 * Creates a composite cohort definition for patients who were newly initiated on ART and are
	 * breastfeeding at their last clinical visit.
	 * <p>
	 * This cohort definition combines two criteria:
	 * <ul>
	 * <li>Patients who started ART during the reporting period.
	 * <li>Patients identified as breastfeeding based on their most recent clinical visit.
	 * </ul>
	 * It accepts three parameters—startDate, endDate, and location—to specify the reporting period
	 * and facility context.
	 * </p>
	 * 
	 * @return a CohortDefinition representing patients meeting both criteria.
	 */
	public CohortDefinition getNewOnArtAndBreastfeeding() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on ART and breastfeeding");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt", SsemrReportUtils.map(getNewOnARTCohortDefinition(), mappings));
		cd.addSearch("breastfeeding", SsemrReportUtils.map(getBreastfeedingWomenCohortDefinition(), mappings));
		cd.setCompositionString("newlyStartedOnArt AND breastfeeding");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients who are newly initiated on ART and are on the TLD
	 * regimen.
	 * <p>
	 * This composite cohort definition intersects:
	 * <ul>
	 * <li>patients who have newly started on ART (from {@code getNewOnARTCohortDefinition()}), and</li>
	 * <li>patients who are on the TLD regimen (from {@code getPatientsOnTLDCohortDefinition()}).</li>
	 * </ul>
	 * The definition requires "startDate", "endDate", and "location" parameters for query mapping.
	 * 
	 * @return a CohortDefinition representing patients newly started on ART and on the TLD regimen
	 */
	public CohortDefinition getNewOnTLDRegimen() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on TLD regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt", SsemrReportUtils.map(getNewOnARTCohortDefinition(), mappings));
		cd.addSearch("onTLDRegimen", SsemrReportUtils.map(getPatientsOnTLDCohortDefinition(), mappings));
		cd.setCompositionString("newlyStartedOnArt AND onTLDRegimen");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients newly initiated on ART who are receiving a DTG-based
	 * regimen.
	 * <p>
	 * This method creates a composition cohort by intersecting two criteria:
	 * <ul>
	 * <li>Patients newly started on ART during the reporting period.</li>
	 * <li>Patients on a DTG regimen.</li>
	 * </ul>
	 * The resulting cohort is parameterized by a start date, an end date, and a facility location.
	 * 
	 * @return a composition cohort definition for newly initiated patients on a DTG-based regimen
	 */
	public CohortDefinition getNewOnOtherDTGBasedRegimen() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients newly started on TLD regimen");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("newlyStartedOnArt", SsemrReportUtils.map(getNewOnARTCohortDefinition(), mappings));
		cd.addSearch("onOtherDTGRegimen", SsemrReportUtils.map(getPatientsOnDTGRegimenCohortDefinition(), mappings));
		cd.setCompositionString("newlyStartedOnArt AND onOtherDTGRegimen");
		return cd;
	}
	
	/**
	 * Constructs a composition cohort definition for patients who are on ART with no recorded TB
	 * status.
	 * <p>
	 * This method defines a cohort that combines two criteria: patients who have ever been on ART
	 * at the facility and patients for whom TB status is not recorded. It sets dynamic parameters
	 * for the reporting period start date, end date, and facility location to support flexible
	 * filtering based on the provided query mappings.
	 * </p>
	 * 
	 * @return a composition cohort definition representing patients on ART without TB status
	 */
	public CohortDefinition getNoTbStatusCohortDefinition() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Number of patients with NO TB status");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.addSearch("onArt",
		    SsemrReportUtils.map(getCumulativeEverOnARTAtThisFacilityAtEndOfReportingCohortDefinition(), mappings));
		cd.addSearch("noTbStatus", SsemrReportUtils.map(getPatientsWithNoTbCohortDefinition(), mappings));
		cd.setCompositionString("onArt AND noTbStatus");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients with no indications of TB during the reporting
	 * period.
	 * <p>
	 * This cohort definition constructs an SQL query to select patients from the HIV care follow-up
	 * records whose TB status is recorded as "No Signs", filtering the results by location and the
	 * encounter date range defined by the start and end dates. The resulting cohort definition
	 * relies on the parameters named {@code startDate}, {@code endDate}, and {@code location}.
	 * </p>
	 * 
	 * @return the cohort definition for patients with no TB signs during the reporting period
	 */
	public CohortDefinition getPatientsWithNoTbCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and (e.tb_status = 'No Signs' )";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients with no TB status during the reporting period");
		return cd;
	}
	
	/**
	 * Constructs a cohort definition for identifying patients with a presumptive TB status during
	 * the reporting period.
	 * <p>
	 * This method creates a SQL-based cohort definition that retrieves patient IDs from the HIV
	 * care follow-up encounters table for encounters occurring within the specified start and end
	 * dates at a given location, where the TB status equals "Pr TB - Presumptive TB".
	 * </p>
	 * 
	 * @return a cohort definition representing patients with presumptive TB status during the
	 *         reporting period
	 */
	public CohortDefinition getPresumptiveTbStatusCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and (e.tb_status = 'Pr TB - Presumptive TB' )";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients with Presumptive TB Status during the reporting period");
		return cd;
	}
	
	/**
	 * Constructs a cohort definition for patients on INH during the reporting period.
	 * <p>
	 * This method creates a SQL-based cohort that selects patient IDs from the HIV care follow-up
	 * encounters, filtering by location and encounter dates between the specified start and end
	 * dates, and only includes encounters where INH is indicated as 'Yes'.
	 * </p>
	 * 
	 * @return a CohortDefinition identifying patients on INH during the reporting period
	 */
	public CohortDefinition getInhStatusCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and (e.inh = 'Yes' )";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on INH during the reporting period");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients on TB treatment during the reporting period.
	 * <p>
	 * This method constructs a SQL-based cohort definition that filters patients based on their TB
	 * treatment status. It selects patients with encounter records at the specified location and
	 * within the given start and end dates, where the TB status is
	 * "TB Rx - currently on TB treatment" or the on_tb_treatment flag is set to "YES".
	 * </p>
	 * 
	 * @return a CohortDefinition representing patients on TB treatment during the reporting period.
	 */
	public CohortDefinition getTbTreatmentStatusCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and ((e.tb_status = 'TB Rx - currently on TB treatment' ) OR UPPER(e.on_tb_treatment) = 'YES') ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on TB Treatment during the reporting period");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients with no TB screening status recorded during the
	 * reporting period.
	 * <p>
	 * This cohort is defined by a SQL query that retrieves client identifiers from the encounter
	 * data where the TB status is null, and the encounter occurred at the specified location
	 * between the start and end dates.
	 * </p>
	 * 
	 * @return a configured CohortDefinition for patients without TB screening status during the
	 *         reporting period
	 */
	public CohortDefinition getNoTbSCreeningStatusCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and (e.tb_status IS NULL )";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients with No TB Screening Status during the reporting period");
		return cd;
	}
	
	/**
	 * Creates a cohort definition for patients who initiated TB treatment during the reporting
	 * period.
	 * <p>
	 * This definition constructs a SQL-based cohort that selects patients from the HIV care
	 * follow-up encounters where the TB treatment initiation date falls between the specified start
	 * and end dates for a given location.
	 * </p>
	 * 
	 * @return a cohort definition identifying patients who started TB treatment within the
	 *         reporting period
	 */
	public CohortDefinition getTbTreatmentStartedDuringReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select " + "    e.client_id " + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.date_started_tb) between date(:startDate) and date(:endDate) ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients started on TB Treatment during the reporting period");
		return cd;
	}
	
	/**
	 * Returns a cohort definition for patients on TB treatment during the reporting period.
	 * <p>
	 * This method constructs a SQL-based cohort definition that retrieves patients from the HIV
	 * care follow-up encounters where the patient is marked as being on TB treatment. The query
	 * filters encounters by a specified location and a date range defined by the "startDate" and
	 * "endDate" parameters.
	 * </p>
	 * <p>
	 * The returned cohort definition requires the following parameters when evaluated: "startDate"
	 * (Date), "endDate" (Date), and "location" (Location).
	 * </p>
	 * 
	 * @return a cohort definition for patients on TB treatment during the reporting period
	 */
	public CohortDefinition getOnTbTreatmentCohortDefinition() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String qry = "select "
		        + "    e.client_id "
		        + "from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "where e.location_id=:location and date(e.encounter_datetime) between date(:startDate) and date(:endDate)  "
		        + "  and (e.on_tb_treatment = 'Yes') ";
		cd.setQuery(qry);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setDescription("Patients on TB Treatment during the reporting period");
		return cd;
	}
	
}
