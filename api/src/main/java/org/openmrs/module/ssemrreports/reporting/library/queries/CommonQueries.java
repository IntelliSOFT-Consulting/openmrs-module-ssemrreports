package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class CommonQueries {
	
	public static String getPatientsInProgram(int programId) {
		
		String query = "SELECT p.patient_id FROM patient p "
		        + "INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + "INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + "WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + "AND pp.date_enrolled BETWEEN :startDate AND :endDate " + "AND pg.program_id=" + programId;
		
		return query;
	}
	
	public static String getBasePatientsBasedOnEncounter(List<Integer> encounterIds) {
		String inputs = StringUtils.join(encounterIds, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " WHERE p.voided=0 AND e.voided=0 AND e.encounter_type IN(" + inputs + ")"
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate";
		return query;
	}
	
	public static String getValueNumericValueCountsBasedOnQuestion(int question) {
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND o.concept_id=" + question
		        + " AND o.value_numeric IS NOT NULL " + " AND e.encounter_datetime BETWEEN :startDate AND :endDate ";
		return query;
	}
	
	public static String hasObs(Integer encounterId, List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE e.encounter_type=" + encounterId
		        + " AND p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND o.concept_id IN(" + request + ")"
		        + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String hasObs(List<Integer> encounterIds, List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String types = StringUtils.join(encounterIds, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE e.encounter_type IN(" + types + ")"
		        + " AND p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND o.concept_id IN(" + request + ")"
		        + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String hasObs(List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND o.concept_id IN(" + request + ")"
		        + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String hasObsIntersectedFromSameObsGroup(int encounterTypeId, List<Integer> question1, List<Integer> ans1,
	        List<Integer> question2, List<Integer> ans2) {
		String request1 = StringUtils.join(question1, ',');
		String response1 = StringUtils.join(ans1, ',');
		String request2 = StringUtils.join(question2, ',');
		String response2 = StringUtils.join(ans2, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " INNER JOIN obs oo ON o.obs_group_id=oo.obs_group_id " + " WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND e.encounter_type=" + encounterTypeId
		        + " AND o.concept_id IN(" + request1 + ")" + " AND o.value_coded IN(" + response1 + ") "
		        + " AND oo.concept_id IN(" + request2 + ")" + " AND oo.value_coded IN(" + response2 + ")";
		
		return query;
	}
	
	public static String getPatientsInProgramWithNullOutcomes(int programId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + " AND pp.date_enrolled BETWEEN :startDate AND :endDate " + " AND pg.program_id=" + programId
		        + " AND pp.outcome_concept_id IS NULL ";
		
		return query;
	}
	
	public static String getPatientsInProgramWithOutcomes(int programId, int outcomeConceptId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + " AND pp.date_enrolled BETWEEN :startDate AND :endDate " + " AND pg.program_id=" + programId
		        + " AND pp.outcome_concept_id=" + outcomeConceptId;
		
		return query;
	}
	
	public static String getMissedAppointmentPatientSetByDays(int days) {
		String query = "SELECT patient_id, MAX(start_date_time) AS latest_appointment FROM patient_appointment "
		        + " WHERE voided = 0 AND status = 'Missed' AND start_date_time BETWEEN :startDate AND :endDate "
		        + " GROUP BY patient_id, start_date_time HAVING DATEDIFF(CURDATE(), MAX(start_date_time)) >" + days;
		return query;
	}
	
	public static String hasObsByEndDate(List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 " + " AND e.encounter_datetime <=:endDate "
		        + " AND o.concept_id IN(" + request + ")" + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String getPatientsEverInProgramWithOutcomes(int programId, int outcomeConceptId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 " + " AND pp.date_enrolled <= :endDate "
		        + " AND pg.program_id=" + programId + " AND pp.outcome_concept_id=" + outcomeConceptId;
		
		return query;
	}
	
	public static String getPatientsInProgramByEndOfReportingPeriod(int programId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + "INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + "INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + "WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 " + "AND pp.date_enrolled <= :endDate "
		        + "AND pg.program_id=" + programId;
		
		return query;
	}
	
	public static String hasObs(List<Integer> question) {
		String request = StringUtils.join(question, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND o.concept_id IN(" + request + ")";
		
		return query;
	}
	
	public static String hasAnyEncounter() {
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id " + " WHERE "
		        + " p.voided=0 AND e.voided=0 " + " AND e.encounter_datetime BETWEEN :startDate AND :endDate ";
		return query;
	}
	
	public static String getObsCodedResultsInObsGroup(int firstQuestion, int firstAnswer, int secondQuestion) {
		String query = "" + " SELECT p.patient_id,o1.value_coded FROM patient p "
		        + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs ob  ON e.encounter_id=ob.encounter_id "
		        + " INNER JOIN obs o1 ON ob.obs_group_id=o1.obs_group_id " + " WHERE ob.concept_id=" + firstQuestion
		        + " AND ob.value_coded=" + firstAnswer + " AND  ob.obs_group_id IS  NOT  NULL "
		        + " AND o1.obs_group_id IS NOT NULL " + " AND o1.concept_id=" + secondQuestion
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND p.voided = 0 AND e.voided=0 AND ob.voided=0 AND o1.voided = 0 ";
		
		return query;
	}
	
	public static String getObsDateResultsInObsGroup(int firstQuestion, int firstAnswer, int secondQuestion) {
		String query = "" + " SELECT p.patient_id,o1.obs_datetime FROM patient p "
		        + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs ob  ON e.encounter_id=ob.encounter_id "
		        + " INNER JOIN obs o1 ON ob.obs_group_id=o1.obs_group_id " + " WHERE ob.concept_id=" + firstQuestion
		        + " AND ob.value_coded=" + firstAnswer + " AND  ob.obs_group_id IS  NOT  NULL "
		        + " AND o1.obs_group_id IS NOT NULL " + " AND o1.concept_id=" + secondQuestion
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND p.voided = 0 AND e.voided=0 AND ob.voided=0 AND o1.voided = 0 ";
		
		return query;
	}
	
	public static String getItemTransactionDetails(String operationType, String itemUuid) {
		String sql = "SELECT ii.name AS name,(SUM(it.quantity)/3) AS quantity FROM inv_stock_operation iso "
		        + " INNER JOIN inv_stock_operation_type isot ON iso.operation_type_id = isot.stock_operation_type_id "
		        + " INNER JOIN inv_stockroom isr ON iso.source_id = isr.stockroom_id "
		        + " INNER JOIN inv_transaction it ON it.operation_id=iso.stock_operation_id "
		        + " INNER JOIN inv_item ii ON ii.item_id = it.item_id "
		        + " WHERE isot.operation_type = '"
		        + operationType
		        + "'"
		        + " AND ii.uuid = '"
		        + itemUuid
		        + "'"
		        + " AND iso.retired   =0 AND isot.retired  =0 AND isr.retired = 0 "
		        + " AND iso.operation_date BETWEEN DATE_ADD(DATE_ADD(:endDate, INTERVAL -1 MONTH), INTERVAL -3 MONTH) AND  DATE_ADD(:endDate, INTERVAL -1 MONTH) "
		        + " GROUP BY ii.name";
		return sql;
	}
	
	public static String getFacilityUsage(List<Integer> screeningEncounters, List<Integer> consultEncounters,
	        List<Integer> pharmacyEncounters) {
		String screenings = StringUtils.join(screeningEncounters, ',');
		String consults = StringUtils.join(consultEncounters, ',');
		String pharmacies = StringUtils.join(pharmacyEncounters, ',');
		
		String sql = "select v.selected_date as `date`,  "
		        + "       count(distinct p.patient_id) as `registered`,  "
		        + "       count(distinct screening.encounter_id) as `screening`,  "
		        + "       count(distinct consult.encounter_id) as `consult`,  "
		        + "       count(distinct pharmacy.encounter_id) as `pharmacy`  "
		        + "from  "
		        + "(select adddate('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) selected_date from  "
		        + " (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,  "
		        + " (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,  "
		        + " (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,  "
		        + " (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,  "
		        + " (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v  "
		        + "left  join payment p on DATE(p.date_created)=date(v.selected_date)  " + "left join (  "
		        + "    select e.encounter_id, e.date_created  " + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + screenings
		        + ")  "
		        + "        and e.voided=0  "
		        + "    )screening on DATE(screening.date_created) = date(v.selected_date)  "
		        + "left join (  "
		        + "    select e.encounter_id, e.date_created  "
		        + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + consults
		        + ")  "
		        + "        and e.voided=0  "
		        + "    )consult on DATE(consult.date_created) = date(v.selected_date)  "
		        + "left join (  "
		        + "    select e.encounter_id, e.date_created  "
		        + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + pharmacies
		        + ")  "
		        + "        and e.voided=0  "
		        + "    )pharmacy on DATE(pharmacy.date_created) = date(v.selected_date)  "
		        + "where v.selected_date between :startDate  and :endDate  " + "group by v.selected_date";
		
		return sql;
	}
	
	public static String getPatientsWithAppointments() {
		String query = "select t.patient_id from (select patient_id, appointment_service_id from openmrs.patient_appointment fp "
		        + " where fp.status = 'Scheduled' or fp.status = 'Missed' and fp.start_date_time BETWEEN :startDate AND :endDate"
		        + " group by patient_id, appointment_service_id) t";
		
		return query;
	}
	
	public static String getPatientsWithHighVL() {
		String query = "SELECT DISTINCT f.client_id " + "FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + "LEFT JOIN ssemr_etl.mamba_dim_person p ON p.person_id = f.client_id "
		        + "LEFT JOIN ssemr_etl.ssemr_flat_encounter_high_viral_load hv ON hv.client_id = f.client_id " + "WHERE ("
		        + "    (f.viral_load_value IS NOT NULL AND f.viral_load_value >= 1000) "
		        + "    OR (hv.adherence_date IS NOT NULL) " + "    OR (hv.second_eac_session_date IS NOT NULL) "
		        + "    OR (hv.third_eac_session_date IS NOT NULL) " + "    OR (hv.repeat_vl_result IS NOT NULL) " + ") "
		        + "AND DATE(f.encounter_datetime) BETWEEN :startDate AND :endDate " + "ORDER BY f.client_id ASC;";
		
		return query;
	}
	
	public static String getAllPatients() {
		String query = "SELECT DISTINCT p.person_id FROM ssemr_etl.mamba_dim_person p "
		        + "JOIN ssemr_etl.mamba_dim_patient_identifier id ON id.patient_id = p.person_id " + "WHERE p.voided = 0 "
		        + "AND p.date_created <= :endDate " + "ORDER BY p.date_created DESC";
		
		return query;
	}
	
	public static String getPatientsEligibleForVL() {
		String query = "select distinct fp.client_id from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fp "
		        + "join ssemr_etl.mamba_dim_person mp on fp.client_id = mp.person_id "
		        + "left join ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment en ON en.client_id = fp.client_id "
		        + "left join ssemr_etl.ssemr_flat_encounter_personal_family_tx_history pfh on pfh.client_id = fp.client_id "
		        + "left join ssemr_etl.ssemr_flat_encounter_vl_laboratory_request vlr on vlr.client_id = fp.client_id "
		        + "left join ssemr_etl.ssemr_flat_encounter_high_viral_load hvl on hvl.client_id = fp.client_id "
		        + "left join ssemr_etl.ssemr_flat_encounter_end_of_follow_up fup on fup.client_id = fp.client_id "
		        + "left join ( "
		        + "    SELECT p.patient_id, p.status, p.start_date_time "
		        + "    FROM openmrs.patient_appointment p "
		        + "    JOIN ( "
		        + "        SELECT patient_id, MAX(start_date_time) AS max_start_date_time "
		        + "        FROM openmrs.patient_appointment "
		        + "        GROUP BY patient_id "
		        + "    ) AS latest_appt ON p.patient_id = latest_appt.patient_id "
		        + "    AND p.start_date_time = latest_appt.max_start_date_time "
		        + ") appt ON appt.patient_id = fp.client_id "
		        + "where ("
		        
		        // Criteria 1: Adult suppressed VL
		        + "(mp.age > 18 AND pfh.art_start_date IS NOT NULL "
		        + " AND TIMESTAMPDIFF(MONTH, pfh.art_start_date, :endDate) >= 6 "
		        + " AND fp.client_pmtct = 'No' "
		        + " AND (fp.viral_load_value < 1000 OR fp.vl_results = 'Below Detectable (BDL)') "
		        + " AND ((EXISTS ("
		        + "     SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up prev "
		        + "     WHERE prev.client_id = fp.client_id "
		        + "     AND prev.date_vl_sample_collected < fp.date_vl_sample_collected "
		        + "     AND (prev.viral_load_value < 1000 OR prev.vl_results = 'Below Detectable (BDL)') "
		        + ") AND TIMESTAMPDIFF(MONTH, fp.date_vl_sample_collected, :endDate) >= 12) "
		        + " OR (NOT EXISTS ("
		        + "     SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up prev "
		        + "     WHERE prev.client_id = fp.client_id "
		        + "     AND prev.date_vl_sample_collected < fp.date_vl_sample_collected "
		        + "     AND (prev.viral_load_value < 1000 OR prev.vl_results = 'Below Detectable (BDL)') "
		        + ") AND TIMESTAMPDIFF(MONTH, fp.date_vl_sample_collected, :endDate) >= 6)) "
		        + ") "
		        
		        // Criteria 2: Adult newly on ART, no VL
		        + "or (mp.age > 18 AND pfh.art_start_date IS NOT NULL "
		        + " AND NOT EXISTS ("
		        + "     SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up v2 "
		        + "     WHERE v2.client_id = fp.client_id AND v2.date_vl_sample_collected IS NOT NULL) "
		        + " AND TIMESTAMPDIFF(MONTH, pfh.art_start_date, :endDate) >= 6) "
		        
		        // Criteria 3: Child/adolescent, based on ART or sample date
		        + "or (mp.age <= 18 AND pfh.art_start_date IS NOT NULL "
		        + " AND ((TIMESTAMPDIFF(MONTH, pfh.art_start_date, :endDate) >= 6) "
		        + " OR (vlr.date_of_sample_collection IS NOT NULL "
		        + " AND TIMESTAMPDIFF(MONTH, vlr.date_of_sample_collection, :endDate) >= 6))) "
		        
		        // Criteria 4: Pregnant newly on ART
		        + "or (fp.client_pmtct = 'Yes' AND pfh.art_start_date IS NOT NULL "
		        + " AND ((vlr.date_of_sample_collection IS NOT NULL "
		        + " AND TIMESTAMPDIFF(MONTH, vlr.date_of_sample_collection, :endDate) >= 3) "
		        + " OR (vlr.date_of_sample_collection IS NULL "
		        + " AND TIMESTAMPDIFF(MONTH, fp.encounter_datetime, :endDate) >= 3))) "
		        
		        // Criteria 5: Pregnant, already on ART
		        + "or (fp.client_pregnant = 'Yes' AND pfh.art_start_date IS NOT NULL "
		        + " AND TIMESTAMPDIFF(MONTH, pfh.art_start_date, :endDate) >= 6 "
		        + " AND fp.encounter_datetime <= :endDate) "
		        
		        // Criteria 6: Post EAC
		        + "or (hvl.third_eac_session_date IS NOT NULL "
		        + " AND TIMESTAMPDIFF(MONTH, hvl.third_eac_session_date, :endDate) >= 1)) "
		        
		        + "and fp.encounter_datetime <= :endDate " + "and (fup.death IS NULL OR fup.death != 'Yes') "
		        + "and (fup.transfer_out IS NULL OR fup.transfer_out != 'Yes') "
		        + "and (fup.client_refused_treatment IS NULL OR fup.client_refused_treatment != 'Yes')"
		        + "and (appt.status IS NULL OR appt.status != 'Missed' OR DATEDIFF(:endDate, appt.start_date_time) <= 28)";
		
		return query;
	}
	
	public static String getIITPatients() {
		String query = "SELECT t.patient_id FROM (SELECT p.patient_id, p.status, p.start_date_time, DATEDIFF(CURDATE(), p.start_date_time) AS date_diff "
		        + "FROM openmrs.patient_appointment p JOIN (SELECT patient_id,  MAX(start_date_time) AS max_start_date_time "
		        + "FROM openmrs.patient_appointment WHERE location_id =:location GROUP BY patient_id) AS latest_appt ON p.patient_id = latest_appt.patient_id "
		        + "AND p.start_date_time = latest_appt.max_start_date_time LEFT JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up e "
		        + "ON e.client_id = p.patient_id WHERE p.status = 'Missed' AND DATE(e.encounter_datetime) <= DATE(:endDate) "
		        + "AND DATEDIFF(CURDATE(), p.start_date_time) > 28 ORDER BY  p.patient_id ASC) AS t "
		        + "WHERE NOT EXISTS (SELECT 1 FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up f WHERE f.client_id = t.patient_id AND ("
		        + "(f.death = 'Yes' AND f.date_of_death IS NOT NULL) OR "
		        + "(f.transfer_out = 'Yes' AND f.transfer_out_date IS NOT NULL)" + "));";
		
		return query;
	}
	
	public static String getDocumentedVLPatients() {
		String query = "SELECT client_id  FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up where viral_load_test_done = 'Yes' and encounter_datetime "
		        + " between :startDate and :endDate and location_id=:location group by client_id";
		
		return query;
	}
	
	public static String getRTTPatients() {
		String query = "SELECT client_id  FROM ssemr_etl.ssemr_flat_encounter_art_interruption WHERE art_treatment_restarted = 'Yes' and encounter_datetime "
		        + " between :startDate and :endDate and location_id=:location GROUP by client_id";
		
		return query;
	}
	
	public static String getClientsWithinAgeLimit(Integer minAge, Integer maxAge) {
		String query = "SELECT mdp.person_id FROM ssemr_etl.mamba_dim_person mdp WHERE mdp.voided=0 "
		        + " AND TIMESTAMPDIFF(YEAR, mdp.birthdate, :effectiveDate) BETWEEN %d AND %d";
		
		return String.format(query, minAge, maxAge);
	}
	
	public static String getClientGender(String option) {
		String query = "SELECT mdp.person_id FROM ssemr_etl.mamba_dim_person mdp WHERE mdp.voided=0 " + " AND mdp.gender='"
		        + option + "'";
		
		return query;
	}
	
	public static String getDeadClients() {
		String query = "SELECT mdp.person_id FROM ssemr_etl.mamba_dim_person mdp WHERE"
		        + " mdp.dead= 1 AND mdp.death_date IS NOT NULL AND mdp.death_date BETWEEN :startDate AND :endDate";
		
		return query;
	}
	
	public static String getTbScreenedClients() {
		String query = "select z.client_id from (SELECT f.client_id, mp.person_name_long, f.encounter_datetime, f.tb_status FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " LEFT JOIN ssemr_etl.mamba_dim_person mp ON mp.person_id = f.client_id WHERE f.encounter_datetime = (SELECT MAX(f2.encounter_datetime) "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f2 WHERE f2.client_id = f.client_id AND f2.location_id = :location AND f2.encounter_datetime BETWEEN :startDate AND :endDate) "
		        + " AND f.location_id = :location AND f.encounter_datetime BETWEEN :startDate AND :endDate AND (f.tb_status = 'No Signs' OR f.tb_status = 'Pr TB - Presumptive TB')) as z";
		
		return query;
	}
	
	public static String getTbTreatmentClients() {
		String query = "select z.client_id from (SELECT f.client_id, mp.person_name_long, f.encounter_datetime, f.on_tb_treatment FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f "
		        + " LEFT JOIN ssemr_etl.mamba_dim_person mp ON mp.person_id = f.client_id WHERE f.encounter_datetime = (SELECT MAX(f2.encounter_datetime) "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f2 WHERE f2.client_id = f.client_id AND f2.location_id = :location AND f2.encounter_datetime BETWEEN :startDate AND :endDate) "
		        + " AND f.location_id = :location AND f.encounter_datetime BETWEEN :startDate AND :endDate AND f.on_tb_treatment = 'Yes') as z";
		
		return query;
	}
	
	public static String getClientsOnArtPerFacility() {
		String sql = "SELECT su1.client_id FROM("
		
		+ "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location " + ")su1";
		return sql;
	}
	
	public static String getOnlyPatientsWithBirthdateAndGender() {
		return "SELECT person_id FROM ssemr_etl.mamba_dim_person WHERE birthdate IS NOT NULL AND gender IN('M','F')";
	}
	
	public static String getClientsWithArtDateAndDateLost(int lower, int higher) {
		String sql = "SELECT fn2.client_id FROM ( "
		        + " SELECT su1.client_id AS client_id,su1.art_start_date,fn1.ltfu_date, TIMESTAMPDIFF(DAY, su1.art_start_date,fn1.ltfu_date) FROM( "
		        
		        + " SELECT client_id, MIN(art_start_date) AS art_start_date FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate  GROUP BY client_id "
		        
		        + " UNION "
		        
		        + " SELECT client_id,  MIN(art_start_date) AS art_start_date FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate  GROUP BY client_id "
		        
		        + " UNION "
		        
		        + " SELECT client_id,  MIN(art_start_date) AS art_start_date FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate  GROUP BY client_id "
		        
		        + " )su1 "
		        + " INNER JOIN ( "
		        + " SELECT fn.client_id,DATE(DATE_ADD(DATE_ADD(fn.encounter_datetime, interval CAST(f1.art_regimen_no_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 30 DAY)) AS ltfu_date FROM ( "
		        + " SELECT fu.client_id AS client_id,MAX(fu.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime <= :endDate AND fu.art_regimen_no_of_days_dispensed IS NOT NULL GROUP BY fu.client_id)fn "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f1 "
		        + " WHERE f1.encounter_datetime=fn.encounter_datetime " + " )fn1 " + " ON su1.client_id=fn1.client_id)fn2 "
		        + " WHERE TIMESTAMPDIFF(DAY, fn2.art_start_date, fn2.ltfu_date) >=" + lower
		        + " AND TIMESTAMPDIFF(day, fn2.art_start_date, fn2.ltfu_date) <" + higher;
		
		return sql;
	}
}
