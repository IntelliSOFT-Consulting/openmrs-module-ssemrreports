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
		String query = "select t.client_id from(SELECT f.client_id, mp.person_name_long, f.encounter_datetime, f.eligible_for_tpt FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + "LEFT JOIN ssemr_etl.mamba_dim_person mp ON mp.person_id = f.client_id WHERE f.encounter_datetime = (SELECT MAX(f2.encounter_datetime) "
		        + "FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f2  WHERE f2.client_id = f.client_id AND f2.location_id = :location "
		        + "AND f2.encounter_datetime BETWEEN :startDate AND :endDate) AND f.location_id = :location AND f.encounter_datetime BETWEEN :startDate AND :endDate "
		        + "AND f.eligible_for_tpt = 'Yes') as t";
		
		return query;
	}
	
}
