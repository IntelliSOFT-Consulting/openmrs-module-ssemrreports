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
}
