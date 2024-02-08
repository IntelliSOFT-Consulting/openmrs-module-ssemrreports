package org.openmrs.module.ssemrreports.reporting.library.queries;

public class MerQueries {
	
	/***
	 * The following should be INCLUDED in TX_CURR calculation: • Patients on ART who initiated or
	 * transferred-in during the reporting period (Month/Quarter). • Patients that pick up 3 or more
	 * months of anti-retroviral drugs at one visit (i.e., multi- month dispensation) should also be
	 * counted if they have received enough ARVs to last to the end of the reporting period at a
	 * minimum. • However, if it is determined that a patient has died, they should immediately be
	 * removed from the TX_CURR results. • Pregnant women with HIV who are eligible for and are
	 * receiving antiretroviral drugs for their own treatment should be counted. Pregnant women with
	 * HIV initiating lifelong ART through PMTCT will count as “current” on ART under this
	 * indicator. These include pregnant women with HIV who have newly initiated ART during the
	 * current pregnancy and pregnant women with HIV who are already on ART at the beginning of the
	 * current pregnancy. Individuals excluded from the current on ART (TX_CURR) count are patients
	 * who died, stopped treatment, transferred out, or experienced interruption in treatment (IIT).
	 * Patients who have not received ARVs within 4 weeks (i.e., 28 days) of their last missed drug
	 * pick-up should not be counted.
	 */
	//TX Curr query formulations
	public static String getPatientsWhoInitiatedArtDuringReportingPeriod() {
		return "SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE hce.art_start_date BETWEEN :startDate AND :endDate " + "	AND hce.art_start_date IS NOT NULL "
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0";
	}
	
	public static String getPatientsWhoTransferredInDuringReportingPeriod() {
		return "SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE hce.date_tranferred_in BETWEEN :startDate AND :endDate "
		        + "	AND hce.date_tranferred_in IS NOT NULL "
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0";
	}
	
	//end TX curr formulations
	public static String getLessThan3MonthsQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getQuarterlyDispensationQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getSemiAnnualDispensationQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	//Tx new cohort queries
	public static String getTxNewTotals() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getClientsWithCd4LessThan200Query() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en " + "	WHERE en.cd4 < 200 "
		        + "	AND en.encounter_datetime BETWEEN :startDate AND :endDate " + "	UNION "
		        + "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu" + "	WHERE fu.cd4 < 200 "
		        + "	AND fu.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getClientsWithCd4MoreThanOrEqualTo200Query() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en " + "	WHERE en.cd4 >= 200 "
		        + "	AND en.encounter_datetime BETWEEN :startDate AND :endDate " + "	UNION "
		        + "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu" + "	WHERE fu.cd4 >= 200 "
		        + "	AND fu.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getClientsWithUnknownCd4Query() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en " + "	WHERE en.cd4 IS NULL"
		        + "	UNION " + "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu"
		        + "	WHERE fu.cd4 IS NULL ";
	}
	
	//Tx ML
	public static String getTxMlDiedQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment fu "
		        + "	WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getTxMlIitL3mQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxMlIitL3To5mQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxMlIitM6mQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxMlTransferOutQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxMlRefusedStoppedTreatmentQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	//TX RTT
	public static String getTxRttWithCd4LessThan200Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxRttWithCd4GreaterOrEqual200Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxRttWithUnknownCd4Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxRttNotEligibleForCd4Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	//TX PVLS
	public static String getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterQueries() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_high_viral_load vl" + " ON en.client_id=vl.client_id"
		        + "	WHERE vl.recent_vl IS NOT NULL " + " AND vl.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterQueries() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_high_viral_load vl" + " ON en.client_id=vl.client_id"
		        + "	WHERE vl.recent_vl IS NOT NULL " + "	AND vl.recent_vl >= 1000 "
		        + " AND vl.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterQueries() {
		return "SELECT en.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_high_viral_load vl" + " ON en.client_id=vl.client_id"
		        + "	WHERE vl.recent_vl IS NOT NULL " + "	AND vl.recent_vl < 1000 "
		        + " AND vl.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getPregnantQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getBreastfeedingQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
}
