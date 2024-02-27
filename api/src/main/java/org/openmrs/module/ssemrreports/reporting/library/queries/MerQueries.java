package org.openmrs.module.ssemrreports.reporting.library.queries;

public class MerQueries {
	
	/***
	 * pick-up should not be counted. ART Start Date: ssemr_flat_encounter_hiv_care_enrolment
	 * (art_start_date) Next Drug Pickup: ssemr_flat_encounter_hiv_care_follow_up
	 * (encounter_datetime + number_of_days_dispensed + 28 ) Pregnancy Status:
	 * ssemr_flat_encounter_hiv_care_follow_up (client_pregnant) Breastfeeding Status:
	 * ssemr_flat_encounter_hiv_care_follow_up (patient_breastfeeding) Died:
	 * ssemr_flat_encounter_end_of_follow_up (death), Stopped treatment:
	 * ssemr_flat_encounter_art_interruption(date_of_treatment_interruption) transferred out:
	 * ssemr_flat_encounter_end_of_follow_up(transfer_out) experienced interruption in treatment
	 * (IIT): ssemr_flat_encounter_art_interruption(date_of_treatment_interruption)
	 */
	//TX Curr query formulations
	public static String getPatientsWhoInitiatedArtDuringReportingPeriod() {
		return "SELECT agg.client_id AS client_id FROM ("
		        + " SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE DATE(hce.art_start_date) BETWEEN :startDate AND :endDate "
		        + "	AND hce.art_start_date IS NOT NULL"
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0"
		        + " UNION "
		        + " SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE DATE_ADD(DATE_ADD(DATE(fu.encounter_datetime), INTERVAL CAST(fu.number_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY) BETWEEN :startDate AND :endDate "
		        + " UNION "
		        + "SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE hce.date_tranferred_in BETWEEN :startDate AND :endDate  "
		        + "	AND hce.date_tranferred_in IS NOT NULL "
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0) agg WHERE client_id NOT IN("
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.death IS NOT NULL AND efu.date_of_death IS NOT NULL"
		        + " AND DATE(efu.date_of_death) BETWEEN :startDate AND :endDate "
		        + " UNION "
		        + " SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_of_treatment_interruption IS NOT NULL AND ai.date_of_treatment_interruption IS NOT NULL"
		        + " AND DATE(ai.date_of_treatment_interruption) BETWEEN :startDate AND :endDate "
		        + " UNION "
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out IS NOT NULL AND efu.transfer_out_date IS NOT NULL "
		        + " AND DATE(efu.transfer_out_date) BETWEEN :startDate AND :endDate "
		        + " UNION "
		        + " SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE "
		        + " DATE_ADD(DATE_ADD(DATE(fu.encounter_datetime), INTERVAL CAST(fu.number_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY) < :endDate"
		        + ")";
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
		return "SELECT agg.client_id AS client_id FROM ("
		        + " SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE DATE(hce.art_start_date) BETWEEN :startDate AND :endDate "
		        + "	AND hce.art_start_date IS NULL "
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0"
		        + ") agg WHERE client_id NOT IN("
		        
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.death IS NOT NULL AND efu.date_of_death IS NOT NULL"
		        + " AND DATE(efu.date_of_death) BETWEEN :startDate AND :endDate"
		        + " UNION "
		        + "SELECT hce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment hce "
		        + "	INNER JOIN ssemr_etl.mamba_dim_person mdp ON hce.client_id=mdp.person_id "
		        + "	WHERE hce.date_tranferred_in BETWEEN :startDate AND :endDate"
		        + "	AND hce.date_tranferred_in IS NOT NULL "
		        + "	AND mdp.dead= 0 AND mdp.death_date IS NULL AND mdp.voided=0 "
		        + " UNION "
		        + " SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_of_treatment_interruption IS NOT NULL AND ai.date_of_treatment_interruption IS NOT NULL"
		        + " AND DATE(ai.date_of_treatment_interruption) BETWEEN :startDate AND :endDate"
		        + " UNION "
		        + " SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out IS NOT NULL AND efu.transfer_out_date IS NOT NULL "
		        + " AND DATE(efu.transfer_out_date) BETWEEN :startDate AND :endDate "
		        + " UNION "
		        + " SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE "
		        + " DATE_ADD(DATE_ADD(DATE(fu.encounter_datetime), INTERVAL CAST(fu.number_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY) < :endDate "
		        + ")";
	}
	
	public static String getClientsWithCd4LessThan200Query() {
		return "SELECT client_id FROM("
		        + "SELECT fu.client_id,MAX(fu.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu"
		        + "	WHERE fu.cd4 IS NOT NULL AND fu.cd4 < 200 "
		        + "	AND fu.encounter_datetime BETWEEN :startDate AND :endDate " + " GROUP BY fu.client_id) fn";
	}
	
	public static String getClientsWithCd4MoreThanOrEqualTo200Query() {
		return "SELECT client_id FROM("
		        + "SELECT fu.client_id,MAX(fu.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu"
		        + "	WHERE fu.cd4 IS NOT NULL AND fu.cd4 >= 200 "
		        + "	AND fu.encounter_datetime BETWEEN :startDate AND :endDate " + " GROUP BY fu.client_id) fn";
	}
	
	public static String getClientsWithUnknownCd4Query() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu" + "	WHERE fu.cd4 IS NULL ";
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
		return "SELECT fn1.client_id FROM ("
		        + " SELECT client_id,follow_up_date FROM ( "
		        + " SELECT fu.client_id AS client_id, MAX(fu.follow_up_date) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate " + " GROUP BY fu.client_id) fn ) fn1 "
		        + " WHERE " + " DATE_ADD(fn1.follow_up_date, INTERVAL 28 DAY) < :endDate AND "
		        + " DATE_ADD(fn1.follow_up_date, INTERVAL 28 DAY) >= DATE_ADD( :startDate, INTERVAL -1 DAY) ";
	}
	
	/***
	 * # of clients traced and brought back by HF effort or self returned from those who missed
	 * great than 28 days in the reporting period (Re-started)
	 * 
	 * @return
	 */
	public static String getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLater() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment fu "
		        + "	WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate ";
	}
	
	public static String getTxMlIitL3mQuery() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + "	WHERE CAST(fu.days_dispensed AS UNSIGNED) < 90 ";
	}
	
	public static String getTxMlIitL3To5mQuery() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + "	WHERE CAST(fu.days_dispensed AS UNSIGNED) BETWEEN 90 AND 150 ";
	}
	
	public static String getTxMlIitM6mQuery() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + "	WHERE CAST(fu.days_dispensed AS UNSIGNED) >= 180 ";
	}
	
	public static String getTxMlCauseOfDeathQueries(String cause) {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up fu "
		        + "	WHERE fu.cause_of_death IS NOT NULL AND  fu.cause_of_death='" + cause + "'";
	}
	
	//TX RTT
	public static String getClientsTracedBroughtBackToCareRestarted() {
		return "SELECT fu.client_id, MAX(fu.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_art_interruption fu "
		        + "	WHERE fu.date_restarted IS NOT NULL AND fu.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " GROUP BY fu.client_id";
	}
	
	public static String getHowLongWerePeopleOffArvs28DaysTo3MonthsQuery() {
		return "SELECT client_id FROM ( "
		        + " SELECT fu.client_id AS client_id, MAX(fu.follow_up_date) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " GROUP BY fu.client_id) fn WHERE DATEDIFF(fn.follow_up_date, :endDate) BETWEEN 28 AND 89 ";
	}
	
	public static String getHowLongWerePeopleOffArvs3To6MonthsQuery() {
		return "SELECT client_id FROM ( "
		        + " SELECT fu.client_id AS client_id, MAX(fu.follow_up_date) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " GROUP BY fu.client_id) fn WHERE DATEDIFF(fn.follow_up_date, :endDate) BETWEEN 90 AND 179 ";
	}
	
	public static String getHowLongWerePeopleOffArvs6To12MonthsQuery() {
		return "SELECT client_id FROM ( "
		        + " SELECT fu.client_id AS client_id, MAX(fu.follow_up_date) AS follow_up_date FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " GROUP BY fu.client_id) fn WHERE DATEDIFF(fn.follow_up_date, :endDate) BETWEEN 180 AND 365 ";
	}
	
	public static String getTxRttNotEligibleForCd4Queries() {
		return "SELECT shce.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment shce "
		        + " WHERE shce.art_start_date IS NULL " + " AND shce.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " UNION " + "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.lost_to_follow_up IS NULL " + " AND fu.encounter_datetime BETWEEN :startDate AND :endDate ";
	}
	
	//TX PVLS
	public static String getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterQueries() {
		return "SELECT client_id FROM ( "
		        + " SELECT en.client_id,MAX(vl.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up vl " + " ON en.client_id=vl.client_id "
		        + " WHERE vl.vl_results IS NOT NULL " + " AND DATE(vl.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + " GROUP BY en.client_id) viral_load ";
	}
	
	public static String getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterQueries() {
		return "SELECT client_id FROM ( "
		        + " SELECT en.client_id,MAX(vl.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up vl " + " ON en.client_id=vl.client_id "
		        + " WHERE vl.vl_results IS NOT NULL AND vl.vl_results >= 1000 "
		        + " AND DATE(vl.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + " GROUP BY en.client_id) viral_load ";
	}
	
	public static String getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterQueries() {
		return "SELECT client_id FROM ( "
		        + " SELECT en.client_id,MAX(vl.encounter_datetime) FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up en "
		        + " INNER JOIN ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up vl " + " ON en.client_id=vl.client_id "
		        + " WHERE vl.vl_results IS NOT NULL AND vl.vl_results < 1000 "
		        + " AND DATE(vl.encounter_datetime) BETWEEN :startDate AND :endDate "
		        + " GROUP BY en.client_id) viral_load ";
	}
	
	public static String getPregnantQueries() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.client_pregnant IS NOT NULL AND fu.encounter_datetime BETWEEN :startDate AND :endDate ";
	}
	
	public static String getBreastfeedingQueries() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE fu.patient_breastfeeding IS NOT NULL AND DATE(fu.encounter_datetime) BETWEEN :startDate AND :endDate ";
	}
	
	public static String getDeadClientsQueries() {
		return "SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.death IS NOT NULL ";
	}
	
	public static String getStoppedTreatmentQueries() {
		return "SELECT ai.client_id FROM ssemr_etl.ssemr_flat_encounter_art_interruption ai "
		        + " WHERE ai.date_restarted IS NULL" + " AND DATE(ai.encounter_datetime) BETWEEN :startDate AND :endDate";
	}
	
	public static String getTransferOutQueries() {
		return "SELECT efu.client_id FROM ssemr_etl.ssemr_flat_encounter_end_of_follow_up efu "
		        + " WHERE efu.transfer_out IS NOT NULL "
		        + " AND DATE(efu.encounter_datetime) BETWEEN :startDate AND :endDate ";
	}
	
	public static String getInterruptionQueries() {
		return "SELECT fu.client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up fu "
		        + " WHERE "
		        + " DATE_ADD(DATE_ADD(DATE(fu.encounter_datetime), INTERVAL CAST(fu.number_of_days_dispensed AS UNSIGNED) DAY), INTERVAL 28 DAY) < :endDate ";
	}
}
