package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class CommonQueries {
	
	public static String getPatientsInProgram(int programId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + "INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + "INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + "WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + "AND pp.date_enrolled BETWEEN :startDate AND :endDate " + "AND pp.location_id= :location "
		        + "AND pg.program_id=" + programId;
		
		return query;
	}
	
	public static String getBasePatientsBasedOnEncounter(List<Integer> encounterIds) {
		String inputs = StringUtils.join(encounterIds, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " WHERE p.voided=0 AND e.voided=0 AND e.encounter_type IN(" + inputs + ")"
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location";
		return query;
	}
	
	public static String getValueNumericValueCountsBasedOnQuestion(int question) {
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND o.concept_id=" + question
		        + " AND o.value_numeric IS NOT NULL "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location";
		return query;
	}
	
	public static String hasObs(Integer encounterId, List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE e.encounter_type=" + encounterId
		        + " AND p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location "
		        + " AND o.concept_id IN(" + request + ")" + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String hasObs(List<Integer> encounterIds, List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String types = StringUtils.join(encounterIds, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE e.encounter_type IN(" + types + ")"
		        + " AND p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location "
		        + " AND o.concept_id IN(" + request + ")" + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String hasObs(List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location "
		        + " AND o.concept_id IN(" + request + ")" + " AND o.value_coded IN(" + response + ")";
		
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
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location "
		        + " AND e.encounter_type=" + encounterTypeId + " AND o.concept_id IN(" + request1 + ")"
		        + " AND o.value_coded IN(" + response1 + ") " + " AND oo.concept_id IN(" + request2 + ")"
		        + " AND oo.value_coded IN(" + response2 + ")";
		
		return query;
	}
	
	public static String getPatientsInProgramWithNullOutcomes(int programId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + " AND pp.date_enrolled BETWEEN :startDate AND :endDate " + "AND pp.location_id= :location "
		        + " AND pg.program_id=" + programId + " AND pp.outcome_concept_id IS NULL ";
		
		return query;
	}
	
	public static String getPatientsInProgramWithOutcomes(int programId, int outcomeConceptId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 "
		        + " AND pp.date_enrolled BETWEEN :startDate AND :endDate " + "AND pp.location_id= :location "
		        + " AND pg.program_id=" + programId + " AND pp.outcome_concept_id=" + outcomeConceptId;
		
		return query;
	}
	
	public static String getMissedAppointmentPatientSetByDays(int days) {
		String qyery = "SELECT patient_id FROM ("
		        + " SELECT p.patient_id AS patient_id, MAX(ats.end_date) AS dVisit FROM patient p "
		        + " INNER JOIN appointmentscheduling_appointment aa ON p.patient_id=aa.patient_id "
		        + " INNER JOIN appointmentscheduling_time_slot ats ON aa.time_slot_id=ats.time_slot_id "
		        + " INNER JOIN appointmentscheduling_appointment_block aab ON aab.appointment_block_id=ats.appointment_block_id"
		        + " WHERE p.voided = 0 AND aa.voided = 0 AND ats.voided = 0 " + " AND aa.status = 'SCHEDULED' "
		        + " AND aa.date_created BETWEEN :startDate AND :endDate " + " AND aab.location_id= :location "
		        + " GROUP BY p.patient_id " + " ) tbl " + " WHERE DATEDIFF(CURDATE(), tbl.dVisit) >" + days;
		return qyery;
	}
	
	public static String hasObsByEndDate(List<Integer> question, List<Integer> ans) {
		String request = StringUtils.join(question, ',');
		String response = StringUtils.join(ans, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime <=:endDate AND e.location_id=:location " + " AND o.concept_id IN(" + request
		        + ")" + " AND o.value_coded IN(" + response + ")";
		
		return query;
	}
	
	public static String getPatientsEverInProgramWithOutcomes(int programId, int outcomeConceptId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + " INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + " INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + " WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 " + " AND pp.date_enrolled <= :endDate "
		        + "AND pp.location_id= :location " + " AND pg.program_id=" + programId + " AND pp.outcome_concept_id="
		        + outcomeConceptId;
		
		return query;
	}
	
	public static String getPatientsInProgramByEndOfReportingPeriod(int programId) {
		
		String query = "SELECT p.patient_id " + "FROM patient p "
		        + "INNER JOIN patient_program pp ON p.patient_id = pp.patient_id "
		        + "INNER JOIN program pg ON pg.program_id=pp.program_id "
		        + "WHERE p.voided=0 AND pp.voided=0 and pg.retired=0 " + "AND pp.date_enrolled <= :endDate "
		        + "AND pp.location_id= :location " + "AND pg.program_id=" + programId;
		
		return query;
	}
	
	public static String hasObs(List<Integer> question) {
		String request = StringUtils.join(question, ',');
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id WHERE "
		        + " p.voided=0 AND e.voided=0 AND o.voided =0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location "
		        + " AND o.concept_id IN(" + request + ")";
		
		return query;
	}
	
	public static String hasAnyEncounter() {
		String query = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id " + " WHERE "
		        + " p.voided=0 AND e.voided=0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND e.location_id=:location ";
		return query;
	}
	
	public static String getObsCodedResultsInObsGroup(int firstQuestion, int firstAnswer, int secondQuestion) {
		String query = "" + " SELECT p.patient_id,o1.value_coded FROM patient p "
		        + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs ob  ON e.encounter_id=ob.encounter_id "
		        + " INNER JOIN obs o1 ON ob.obs_group_id=o1.obs_group_id " + " WHERE ob.concept_id=" + firstQuestion
		        + " AND ob.value_coded=" + firstAnswer + " AND  ob.obs_group_id IS  NOT  NULL "
		        + " AND o1.obs_group_id IS NOT NULL " + " AND o1.concept_id=" + secondQuestion
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND e.location_id=:location "
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
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND e.location_id=:location "
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
		        + " AND isr.location_id=:location "
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
		        + "left  join payment p on DATE(p.date_created)=date(v.selected_date) and p.location_id=:location  "
		        + "left join (  " + "    select e.encounter_id, e.date_created  " + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + screenings
		        + ")  "
		        + "        and e.location_id=:location  "
		        + "        and e.voided=0  "
		        + "    )screening on DATE(screening.date_created) = date(v.selected_date)  "
		        + "left join (  "
		        + "    select e.encounter_id, e.date_created  "
		        + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + consults
		        + ")  "
		        + "        and e.location_id=:location  "
		        + "        and e.voided=0  "
		        + "    )consult on DATE(consult.date_created) = date(v.selected_date)  "
		        + "left join (  "
		        + "    select e.encounter_id, e.date_created  "
		        + "    from encounter e  "
		        + "    where e.encounter_type IN ("
		        + pharmacies
		        + ")  "
		        + "        and e.location_id=:location  "
		        + "        and e.voided=0  "
		        + "    )pharmacy on DATE(pharmacy.date_created) = date(v.selected_date)  "
		        + "where v.selected_date between :startDate  and :endDate  " + "group by v.selected_date";
		
		return sql;
	}
	
	public static String getPatientsWithAppointments() {
		String query = "SELECT patient_id FROM ssemr.patient_appointment fp where fp.status = 'Scheduled' and "
		        + " fp.start_date_time BETWEEN :startDate AND :endDate and fp.location_id=:location ";
		
		return query;
	}
	
	public static String getPatientsWithHighVL() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up WHERE "
		        + " (SELECT MAX(concat(encounter_datetime, vl_results)) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up) >= 1000 "
		        + " AND encounter_datetime BETWEEN :startDate AND :endDate AND location_id=:location ";
		
		return query;
	}
	
	public static String getPatientsWithHighVLAndEAC() {
		String query = "SELECT t.client_id FROM (SELECT client_id, encounter_datetime, location_id, "
		        + " mid(max(concat(date(encounter_datetime), recent_vl)), 11) as last_vl_result, "
		        + " mid(max(concat(date(encounter_datetime), first_eac_tools)), 11) as last_eac_tools "
		        + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load GROUP BY client_id, encounter_datetime, location_id "
		        + " HAVING last_eac_tools IS NOT NULL AND last_vl_result > 1000 "
		        + " AND encounter_datetime BETWEEN :startDate AND :endDate AND location_id=:location) t; ";
		
		return query;
	}
	
	public static String getPatientsWithHighVLAndRepeatTestAfterEAC() {
		String query = "SELECT t.client_id FROM  (SELECT client_id, mid(max(concat(date(encounter_datetime), "
		        + " recent_vl)), 11) as last_vl_result, encounter_datetime, max(location_id) as location_id,"
		        + " mid(max(concat(date(encounter_datetime), first_eac_tools)), 11) as last_eac_tools, "
		        + " mid(max(concat(date(encounter_datetime), repeat_vl_date)), 11) as last_repeat_vl_date "
		        + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load GROUP BY client_id, encounter_datetime "
		        + " HAVING last_eac_tools IS NOT NULL AND last_vl_result > 1000 AND last_repeat_vl_date "
		        + " AND encounter_datetime BETWEEN :startDate AND :endDate AND location_id=:location) t; ";
		
		return query;
	}
	
	public static String getSupressedPatientsWithHVL() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_high_viral_load "
		        + " WHERE (SELECT MAX(concat(encounter_datetime, repeat_vl_result)) FROM ssemr_etl.ssemr_flat_encounter_high_viral_load) < 1000 "
		        + " AND encounter_datetime BETWEEN :startDate AND :endDate AND location_id=:location;";
		
		return query;
	}
	
	public static String getPatientsEligibleForVL() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " where encounter_datetime between :startDate and :endDate and location_id =:location"
		        + " GROUP BY client_id HAVING DATEDIFF( CURDATE(),MAX(SUBSTRING_INDEX(CONCAT(encounter_datetime, date_vl_sample_collected), ' ', -1))) > 90;";
		
		return query;
	}
	
	public static String getMissedAppointments() {
		String query = "select patient_id from patient_appointment "
		        + " where status = 'Missed' and start_date_time between :startDate "
		        + " and :endDate and location_id =:location and DATEDIFF(CURDATE(), start_date_time) <= 28;";
		
		return query;
	}
	
	public static String getIITPatients() {
		String query = "select p.patient_id, p.start_date_time, DATEDIFF(CURDATE(), start_date_time) "
		        + " from patient_appointment p left join encounter e on e.patient_id = p.patient_id "
		        + " where p.status = 'Missed' and p.start_date_time between :startDate and :endDate and "
		        + " p.location_id =:location and DATEDIFF(CURDATE(), p.start_date_time) >= 28 "
		        + " and (select datediff(CURDATE(), max(e.date_created))) < 28";
		
		return query;
	}
	
	public static String getDocumentedVLPatients() {
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + "where date_vl_sample_collected between :startDate and :endDate and location_id =:location "
		        + "and vl_results is not null group by client_id;";
		
		return query;
	}
	
}
