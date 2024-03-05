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
	
	public static String getPatientsEligibleForTPT() {
		String query = "SELECT tb.client_id FROM ssemr_etl.ssemr_flat_encounter_tb_screening_form_children tb "
		        + "LEFT JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fp ON fp.client_id = tb.client_id"
		        + "WHERE (tb.tb_screening_fever = 'True' OR tb.current_cough = 'True' OR tb.tb_screening_weight_loss = 'True' OR tb.close_contact_history_with_tb_patients = 'True')"
		        + "AND tb.encounter_datetime BETWEEN :startDate AND :endDate "
		        + "GROUP BY tb.client_id HAVING NOT MID(MAX(CONCAT(fp.encounter_datetime, fp.on_tb_treatment)), 20) = 'True';";
		
		return query;
	}
	
}
