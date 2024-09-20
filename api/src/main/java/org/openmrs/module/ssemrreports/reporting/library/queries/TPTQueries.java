package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TPTQueries {
	
	public static String getPatientsCompletedTPT() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " WHERE inh = 'True' AND encounter_datetime BETWEEN :startDate AND :endDate "
		        + " GROUP BY client_id HAVING TIMESTAMPDIFF(MONTH, MAX(encounter_datetime), CURDATE()) > 6;";
		
		return query;
	}
	
	public static String getPatientsTakingTPT() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " WHERE inh = 'True' and started_on_tb_regimen = 'False' AND encounter_datetime BETWEEN :startDate AND :endDate"
		        + " GROUP BY client_id HAVING TIMESTAMPDIFF(MONTH, MAX(encounter_datetime), CURDATE()) < 6;";
		
		return query;
	}
	
	// to add eligible for tpt and date eligible for tpt and inh details
	public static String getPatientsEligibleForTPT() {
		String query = "select t.client_id from(SELECT client_id,MID(MAX(concat(encounter_datetime, eligible_for_tpt)),20) as eligible_for_tpt "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f WHERE DATE(encounter_datetime) BETWEEN :startDate AND :endDate and "
		        + " location_id=:location and eligible_for_tpt = 'Yes' GROUP BY f.client_id) as t";
		
		return query;
	}
	
}
