package org.openmrs.module.ssemrreports.reporting.library.queries;

public class HivSelfTestingQueries {
	
	public static String getAllPatientsByPurposeOfDistributionAndFacilityBased() {
		
		String query = "SELECT p.patient_id FROM patient p " + "  INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + "  WHERE p.voided=0 AND e.voided=0 " + "  AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + "  AND e.location_id=:location";
		return query;
	}
	
}
