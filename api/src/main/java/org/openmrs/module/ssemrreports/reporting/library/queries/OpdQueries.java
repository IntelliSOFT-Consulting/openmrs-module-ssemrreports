package org.openmrs.module.ssemrreports.reporting.library.queries;

public class OpdQueries {
	
	public static String getPatientsWithVisitType(String type, String value) {
		String query = "SELECT p.patient_id FROM patient p " + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN encounter_diagnosis ed ON e.encounter_id=ed.encounter_id "
		        + " INNER JOIN diagnosis_attribute da ON da.diagnosis_id=ed.diagnosis_id "
		        + " INNER JOIN diagnosis_attribute_type dat ON dat.diagnosis_attribute_type_id=da.attribute_type_id "
		        + " WHERE 	e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND p.voided = 0 AND dat.retired = 0 AND da.voided = 0 AND da.voided = 0 " + " AND dat.uuid=" + "'"
		        + type + "'" + " AND da.value_reference=" + "'" + value + "'";
		
		return query;
	}
	
	public static String getPatientsWithEncounterDiagnosisBasedOnIcd11Code(String icd11Code) {
		String query = " SELECT patient_id FROM( "
		        + " SELECT p.patient_id AS patient_id, substring_index(ed.diagnosis_non_coded, '-', 1) AS icd11code FROM patient p "
		        + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN encounter_diagnosis ed ON e.encounter_id=ed.encounter_id "
		        + " WHERE ed.diagnosis_non_coded like '%-%' " + " AND p.voided = 0 " + " AND e.voided = 0 "
		        + " AND ed.voided = 0 "
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate) tx " + " WHERE icd11code =" + "'" + icd11Code
		        + "'";
		return query;
	}
}
