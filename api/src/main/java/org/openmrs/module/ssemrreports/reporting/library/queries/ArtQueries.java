package org.openmrs.module.ssemrreports.reporting.library.queries;

public class ArtQueries {
	
	public static String getCitizenAndNonCitizensQuery(String response) {
		String sql = "SELECT p.patient_id FROM patient p " + " INNER JOIN person_attribute pa ON p.patient_id=pa.person_id "
		        + " INNER JOIN person_attribute_type pat ON pat.person_attribute_type_id=pa.person_attribute_type_id "
		        + " WHERE p.voided = 0 AND pa.voided = 0 AND pat.retired = 0 "
		        + " AND pat.uuid='60EA1904-2D15-486C-BAEA-2BC78DD498F2' " + " AND pa.value='" + response + "'";
		
		return sql;
	}
	
	public static String getPatientsOnArtWithDetectableViralLoadR5(int encounterType, int conceptQuestion, int copies) {
		String sql = "SELECT p.patient_id FROM patient p " + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " WHERE p.voided= 0 AND e.voided=0 AND o.voided=0 " + " AND e.encounter_type=" + encounterType
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND o.concept_id=" + conceptQuestion + " AND o.value_numeric IS NOT NULL " + " AND o.value_numeric > "
		        + copies;
		
		return sql;
	}
	
	public static String getPatientsOnArtWithDetectableViralLoadR6(int encounterType, int conceptQuestion, int copies) {
		String sql = "SELECT p.patient_id FROM patient p " + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " WHERE p.voided= 0 AND e.voided=0 AND o.voided=0 " + " AND e.encounter_type=" + encounterType
		        + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND o.concept_id=" + conceptQuestion + " AND o.value_numeric IS NOT NULL " + " AND o.value_numeric < "
		        + copies;
		
		return sql;
	}
}
