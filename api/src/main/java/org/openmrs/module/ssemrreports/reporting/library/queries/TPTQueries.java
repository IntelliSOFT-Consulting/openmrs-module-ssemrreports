package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TPTQueries {
	
	public static String getPatientsTakingTPT() {
		String query = "select t.client_id from (SELECT client_id, MAX(concat(encounter_datetime, tb_status)) as tb_status, "
		        + " encounter_datetime, location_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " WHERE tb_status is not null AND encounter_datetime BETWEEN :startDate AND :endDate AND location_id=:location "
		        + " group by client_id, encounter_datetime, location_id) as t";
    return query; 
  }

	public static String getPatientsEligibleForTPT() {
		String query = "select tb.client_id from ssemr_etl.ssemr_flat_encounter_tb_screening_form_children tb "
		        + " left join ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fp on fp.client_id = tb.client_id "
		        + " WHERE  (tb_screening_fever = 'True') +  (current_cough = 'True') + (tb_screening_weight_loss = 'True') + (close_contact_history_with_tb_patients = 'True') >= 1 "
		        + " and tb_status is not null and tb.encounter_datetime between :startDate and :endDate and tb.location_id =:location";
		
		return query;
	}
	
}
