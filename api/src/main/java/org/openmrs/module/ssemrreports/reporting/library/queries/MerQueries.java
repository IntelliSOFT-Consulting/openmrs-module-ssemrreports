package org.openmrs.module.ssemrreports.reporting.library.queries;

public class MerQueries {
	
	/***
	 * pick-up should not be counted. ART Start Date: ssemr_flat_encounter_hiv_care_enrolment
	 * (art_start_date) Next Drug Pickup: ssemr_flat_encounter_hiv_care_follow_up
	 * (encounter_datetime + art_regimen_no_of_days_dispensed + 28 ) Pregnancy Status:
	 * ssemr_flat_encounter_hiv_care_follow_up (client_pregnant) Breastfeeding Status:
	 * ssemr_flat_encounter_hiv_care_follow_up (client_breastfeeding) Died:
	 * ssemr_flat_encounter_end_of_follow_up (death), Stopped treatment:
	 * ssemr_flat_encounter_art_interruption(date_of_treatment_interruption) transferred out:
	 * ssemr_flat_encounter_end_of_follow_up(transfer_out) experienced interruption in treatment
	 * (IIT): ssemr_flat_encounter_art_interruption(date_of_treatment_interruption)
	 */
	//TX Curr query formulations
	public static String getPatientsWhoInitiatedArtDuringReportingPeriod() {
		return "SELECT agg.client_id AS client_id FROM ("
		
		+ "SELECT su1.client_id FROM("
		
		+ "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date  <=:endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date <=:endDate AND location_id=:location " + ")su1"
		        
		        + " WHERE su1.client_id NOT IN("
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.death ='Yes' " + " AND DATE(efu.date_of_death) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.ltfu ='Yes' " + " AND DATE(efu.ltfu_date) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.client_refused_treatment ='Yes' "
		        + " AND DATE(efu.date_client_refused_treatment) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_of_treatment_interruption IS NOT NULL "
		        + " AND DATE(ai.date_of_treatment_interruption) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out ='Yes' AND efu.transfer_out_date IS NOT NULL "
		        + " AND DATE(efu.transfer_out_date) BETWEEN :startDate AND :endDate " + ") " + ")agg";
	}
	
	public static String getArtPatientsWhoRestartedTreatmentInPeriod() {
		return " SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_restarted IS NOT NULL " + " AND ai.date_restarted BETWEEN :startDate AND :endDate ";
	}
	
	//end TX curr formulations
	
	//Tx new cohort queries
	public static String getTxNewTotals() {
		return "SELECT agg.client_id AS client_id FROM ("
		        
		        + "SELECT su1.client_id FROM("
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date BETWEEN :startDate AND :endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_adult_and_adolescent_intake "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date BETWEEN :startDate AND :endDate AND location_id=:location "
		        
		        + "UNION "
		        
		        + "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_pediatric_intake_report "
		        + " WHERE art_start_date IS NOT NULL AND art_start_date BETWEEN :startDate AND :endDate AND location_id=:location "
		        + ")su1"
		        
		        + " WHERE su1.client_id NOT IN("
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.ltfu ='Yes' " + " AND DATE(efu.ltfu_date) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_of_treatment_interruption IS NOT NULL "
		        + " AND DATE(ai.date_of_treatment_interruption) BETWEEN :startDate AND :endDate " + " UNION "
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out ='Yes' AND efu.transfer_out_date IS NOT NULL "
		        + " AND DATE(efu.transfer_out_date) BETWEEN :startDate AND :endDate " + ") " + ")agg";
	}
	
	public static String getClientsWithCd4LessThan200Query() {
		return
		
		" SELECT t1.client_id FROM ("
		        + "SELECT f.client_id AS client_id,MAX(f.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f"
		        + " GROUP BY f.client_id) t1"
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t2 ON t1.client_id=t2.client_id"
		        + "	WHERE t2.cd4 IS NOT NULL AND t2.cd4 < 200 AND t1.encounter_datetime=t2.encounter_datetime AND t2.encounter_datetime <= :endDate AND t2.location_id=:location ";
	}
	
	public static String getClientsWithCd4MoreThanOrEqualTo200Query() {
		return " SELECT t1.client_id FROM ("
		        + "SELECT f.client_id AS client_id,MAX(f.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f"
		        + " GROUP BY f.client_id) t1"
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t2 ON t1.client_id=t2.client_id"
		        + "	WHERE t2.cd4 IS NOT NULL AND t2.cd4 >= 200 AND t1.encounter_datetime=t2.encounter_datetime AND t2.encounter_datetime <= :endDate AND t2.location_id=:location ";
		
	}
	
	public static String getClientsWithUnknownCd4Query() {
		return " SELECT t1.client_id FROM ("
		        + "SELECT f.client_id AS client_id,MAX(f.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up f"
		        + " GROUP BY f.client_id) t1"
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t2 ON t1.client_id=t2.client_id"
		        + "	WHERE t2.cd4 IS NULL AND t1.encounter_datetime=t2.encounter_datetime AND t2.encounter_datetime <= :endDate AND t2.location_id=:location ";
		
	}
	
	//Tx ML
	
	/***
	 * ART patients (who were on ART at the beginning of the quarterly reporting period or initiated
	 * treatment during the reporting period) and then had no clinical contact for greater than 28
	 * days since their last expected contact or ARV pick up
	 * 
	 * @return
	 */
	public static String getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContact() {
		return "SELECT q2.client_id FROM ( "
		        + " SELECT q1.client_id,q1.follow_up_date,t2.art_regimen_no_of_days_dispensed FROM ( "
		        + " SELECT t1.client_id AS client_id, MAX(t1.encounter_datetime) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t1 "
		        + " WHERE t1.encounter_datetime <= :endDate AND t1.location_id=:location "
		        + " GROUP BY t1.client_id) q1 "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up t2 ON q1.client_id=t2.client_id "
		        + " WHERE q1.follow_up_date = t2.encounter_datetime "
		        + " AND DATE(DATE_ADD(DATE_ADD(q1.follow_up_date, interval CAST(t2.art_regimen_no_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY)) <= :endDate)q2";
	}
	
	public static String getTxMlIitL3mQuery() {
		return "SELECT fn.client_id FROM("
		        + " SELECT fu.client_id AS client_id, MAX(fu.encounter_datetime) AS encounter_datetime "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu WHERE fu.encounter_datetime <= :endDate "
		        + " AND fu.art_regimen_no_of_days_dispensed IS NOT NULL" + " GROUP BY fu.client_id)fn "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 "
		        + " ON fu1.client_id = fn.client_id AND fu1.encounter_datetime=fn.encounter_datetime "
		        + " WHERE CAST(fu1.art_regimen_no_of_days_dispensed AS UNSIGNED) < 90 ";
	}
	
	public static String getTxMlIitL3To5mQuery() {
		return " SELECT fn.client_id FROM("
		        + " SELECT fu.client_id AS client_id, MAX(fu.encounter_datetime) AS encounter_datetime "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu WHERE fu.encounter_datetime <= :endDate "
		        + " AND fu.art_regimen_no_of_days_dispensed IS NOT NULL"
		        + " GROUP BY fu.client_id)fn "
		        
		        + " INNER JOIN "
		        + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 "
		        + " ON fu1.client_id = fn.client_id AND fu1.encounter_datetime=fn.encounter_datetime "
		        + " WHERE CAST(fu1.art_regimen_no_of_days_dispensed AS UNSIGNED) > 89 AND CAST(fu1.art_regimen_no_of_days_dispensed AS UNSIGNED) < 180 ";
		
	}
	
	public static String getTxMlIitM6mQuery() {
		return " SELECT fn.client_id FROM("
		        + " SELECT fu.client_id AS client_id, MAX(fu.encounter_datetime) AS encounter_datetime "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu WHERE fu.encounter_datetime <= :endDate "
		        + " AND fu.art_regimen_no_of_days_dispensed IS NOT NULL" + " GROUP BY fu.client_id)fn "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 "
		        + " ON fu1.client_id = fn.client_id AND fu1.encounter_datetime=fn.encounter_datetime "
		        + " WHERE fu1.art_regimen_no_of_days_dispensed like '180%' ";
	}
	
	public static String getTxMlCauseOfDeathQueries(String cause) {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up fu "
		        + "	WHERE fu.death IS NOT NULL " + "	AND fu.cause_of_death IS NOT NULL "
		        + " AND fu.date_of_death IS NOT NULL " + " AND fu.death = 'Yes' "
		        + " AND fu.date_of_death BETWEEN :startDate AND :endDate" + " AND  fu.cause_of_death='" + cause + "'";
	}
	
	//TX RTT
	public static String getClientsTracedBroughtBackToCareRestarted() {
		return "SELECT client_id FROM("
		        + " SELECT fu.client_id AS client_id, MAX(fu.date_restarted) AS date_restarted FROM ssemr_etl.ssemr_flat_encounter_art_interruption fu "
		        + "    WHERE fu.art_treatment_restarted='Yes' AND fu.date_restarted BETWEEN :startDate AND  :endDate AND location_id=:location "
		        + " GROUP BY fu.client_id" + ")fn";
	}
	
	public static String getHowLongWerePeopleOffArvQuery(int l, int h) {
		return "SELECT f2.client_id FROM ( "
		        + " SELECT f1.client_id, MAX(date_restarted) AS date_restarted FROM ssemr_etl.ssemr_flat_encounter_art_interruption f1 "
		        + " WHERE f1.date_restarted IS NOT NULL AND f1.date_restarted BETWEEN :startDate AND :endDate AND f1.location_id=:location "
		        + " GROUP BY f1.client_id) f2 "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_art_interruption f3 "
		        + " ON f2.client_id=f3.client_id WHERE f2.date_restarted=f3.date_restarted AND f3.date_of_treatment_interruption IS NOT NULL "
		        + " AND  DATEDIFF(f2.date_restarted, f3.date_of_treatment_interruption) BETWEEN " + l + " AND " + h;
	}
	
	public static String getHowLongWerePeopleOffArvAfterLTFUQuery() {
		return "SELECT client_id FROM ( "
		        + " SELECT fu.patient_id AS client_id, Date_add(MAX(fu.start_date_time), interval 28 day) AS follow_up_date FROM openmrs.patient_appointment fu "
		        + " WHERE fu.date_created <= :endDate AND location_id=:location " + " GROUP BY client_id)fn";
	}
	
	public static String getTxRttNotEligibleForCd4Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_personal_family_tx_history shce "
		        + " WHERE shce.art_start_date IS NULL " + " AND shce.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " UNION " + "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up fu "
		        + " WHERE fu.ltfu_date IS NULL " + " AND fu.encounter_datetime BETWEEN :startDate AND :endDate ";
	}
	
	//TX PVLS
	public static String getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterQueries() {
		String sql = "SELECT su.client_id FROM( "
		        + " SELECT fu.client_id,fu.date_vl_results_received AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE  fu.date_vl_results_received BETWEEN :startDate AND :endDate "
		        + " AND fu.viral_load_test_done='Yes' AND (fu.vl_results='Below Detectable (BDL)' OR fu.viral_load_value IS NOT NULL))su";
		
		return sql;
	}
	
	public static String getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterQueries() {
		String sql = "SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN :startDate AND :endDate " + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received" + " AND fu2.viral_load_value >= 1000";
		return sql;
	}
	
	public static String getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterQueries() {
		return "SELECT su1.client_id FROM( "
		        + " SELECT en.client_id,MAX(en.date_vl_results_received) AS date_vl_results_received FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " WHERE (en.viral_load_value IS NOT NULL OR en.vl_results IS NOT NULL) AND en.viral_load_test_done='Yes'"
		        + " AND DATE(en.date_vl_results_received) BETWEEN :startDate AND :endDate " + " GROUP BY en.client_id)su1 "
		        
		        + " INNER JOIN " + " ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu2 ON fu2.client_id=su1.client_id "
		        + " AND su1.date_vl_results_received=fu2.date_vl_results_received"
		        + " AND (fu2.viral_load_value < 1000 OR fu2.vl_results = 'Below Detectable (BDL)')";
	}
	
	public static String getPregnantQueries() {
		return "SELECT su.client_id FROM( "
		        + " SELECT fu.client_id,fu.encounter_datetime AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.client_pregnant IS NOT NULL AND fu.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND fu.client_pregnant='Yes')su ";
	}
	
	public static String getBreastfeedingQueries() {
		return "SELECT su1.client_id FROM( "
		        + " SELECT fu.client_id,fu.encounter_datetime AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.client_breastfeeding IS NOT NULL AND DATE(fu.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + " AND fu.client_breastfeeding='Yes')su1 ";
	}
	
	public static String getDeadClientsQueries() {
		return "SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.death IS NOT NULL AND efu.date_of_death IS NOT NULL AND efu.death='Yes' AND efu.date_of_death BETWEEN :startDate AND :endDate";
	}
	
	public static String getStoppedTreatmentQueries() {
		return "SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_restarted IS NULL" + " AND DATE(ai.encounter_datetime) BETWEEN :startDate AND :endDate";
	}
	
	public static String getTransferOutQueries() {
		return "SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out IS NOT NULL AND efu.transfer_out='Yes' AND efu.encounter_datetime BETWEEN :startDate AND :endDate";
	}
	
	public static String getInterruptionQueries() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption fu "
		        + " WHERE "
		        + " fu.date_of_treatment_interruption IS NOT NULL AND fu.encounter_datetime BETWEEN :startDate AND :endDate ";
	}
	
	public static String getArtPatientsNotActiveAtBeginningOfReportingPeriod() {
		return "SELECT fn1.client_id FROM ("
		        
		        + " SELECT client_id,follow_up_date FROM ( "
		        
		        + " SELECT fu.patient_id AS client_id, MAX(fu.start_date_time) AS follow_up_date FROM openmrs.patient_appointment fu "
		        + " WHERE fu.date_created <= DATE_ADD(:startDate, interval -1 DAY) AND location_id=:location "
		        + " GROUP BY fu.patient_id) fn "
		        
		        + ") fn1 "
		        + " WHERE DATE_ADD(fn1.follow_up_date, INTERVAL 28 DAY) <= DATE_ADD( :startDate, INTERVAL -1 DAY) ";
	}
	
	public static String getIITPatients() {
		return " SELECT t2.client_id AS client_id FROM ( "
		        + " SELECT t1.client_id AS client_id, t1.encounter_datetime AS encounter_date, fu1.art_regimen_no_of_pills_dispensed AS pills_dispensed FROM( "
		        + " SELECT fu.client_id AS client_id, MAX(fu.encounter_datetime) AS encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.number_of_pills_dispensed IS NOT NULL AND fu.encounter_datetime < :endDate AND location_id=:location "
		        + " GROUP BY fu.client_id) t1 "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu1 ON t1.client_id=fu1.client_id "
		        + " WHERE t1.encounter_datetime=fu1.encounter_datetime ) t2 WHERE DATE_ADD(DATE(DATE_ADD(t2.encounter_date, interval CAST(t2.pills_dispensed AS UNSIGNED) DAY)), interval 28 DAY) < DATE(:endDate)";
		
	}
}
