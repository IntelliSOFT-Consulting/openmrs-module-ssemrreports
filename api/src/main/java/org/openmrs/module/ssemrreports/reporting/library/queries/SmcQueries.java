package org.openmrs.module.ssemrreports.reporting.library.queries;

public class SmcQueries {
	
	public static String getAdverseReactionEvents(int parentEncounterTypeId, int parentConceptQuestion,
	        int parentConceptCodedAnswer, int childConceptQuestion, int childConceptCodedAnswer) {
		return "SELECT pr.patient_id FROM("
		        + " SELECT p1.patient_id,o1.obs_id FROM patient p1 INNER JOIN encounter e1 ON p1.patient_id=e1.patient_id "
		        + " INNER JOIN obs o1 ON e1.encounter_id=o1.encounter_id "
		        + " WHERE p1.voided = 0 AND e1.voided = 0 AND o1.voided = 0 " + " AND e1.encounter_type="
		        + parentEncounterTypeId + " AND o1.concept_id=" + parentConceptQuestion + " AND o1.value_coded="
		        + parentConceptCodedAnswer
		        + " AND e1.encounter_datetime BETWEEN :startDate AND :endDate " + ") pr "
		        + " INNER JOIN obs ob ON pr.obs_id=ob.obs_group_id " + " WHERE ob.voided=0 AND ob.concept_id="
		        + childConceptQuestion + " AND ob.value_coded=" + childConceptCodedAnswer;
		
	}
}
