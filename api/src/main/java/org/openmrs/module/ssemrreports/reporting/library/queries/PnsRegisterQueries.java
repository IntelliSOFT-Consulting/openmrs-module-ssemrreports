package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PnsRegisterQueries {
	
	public static String getPnsRegisterBaseQuery(List<Integer> encounters) {
		String encountersList = StringUtils.join(encounters, ',');
		String sql = "SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + "  INNER JOIN encounter_type et ON et.encounter_type_id=e.encounter_type "
		        + "  WHERE p.voided=0 AND e.voided=0 AND et.retired=0 "
				+ "  AND e.encounter_datetime <= :endDate "
				+ "  AND et.encounter_type_id IN(" + encountersList + ")";
		
		return sql;
	}
}
