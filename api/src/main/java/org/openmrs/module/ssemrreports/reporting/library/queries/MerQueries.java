package org.openmrs.module.ssemrreports.reporting.library.queries;

public class MerQueries {
	
	public static String getTxCurrQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
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
	public static String getTxNewWithCd4LessThan200Query() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxNewWithCd4MoreThanOrEqualTo200Query() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxNewWithUnknownCd4Query() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	//Tx ML
	public static String getTxMlDiedQuery() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
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
	public static String getTxPvlsAllQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxPvlPregnantQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
	
	public static String getTxPvlBreastfeedingQueries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce";
	}
}
