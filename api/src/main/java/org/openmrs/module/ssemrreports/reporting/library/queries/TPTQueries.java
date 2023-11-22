package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TPTQueries {
	
	public static String getPatientsCompletedTPT() {
		String query = "select t.client_id from (SELECT client_id, MAX(CONCAT(encounter_datetime, tb_status)) AS tb_status, "
		        + " DATEDIFF(CURDATE(), MAX(encounter_datetime)) AS duration_tpt, encounter_datetime, location_id "
		        + " FROM ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up "
		        + " group by client_id, encounter_datetime, location_id "
		        + " HAVING tb_status is not null and duration_tpt >= 182 "
		        + "AND encounter_datetime BETWEEN :startDate AND :endDate and location_id=:location) as t;";
		
		return query;
	}
	
}
