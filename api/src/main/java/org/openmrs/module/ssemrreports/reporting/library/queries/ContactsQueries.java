package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ContactsQueries {
	
	public static String getContactsWithUnknownHivStatusTested() {
		
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_family_history "
		        + " WHERE hiv_status = 'Don''t know' and date_hiv_tested is not null and encounter_datetime between :startDate "
		        + " and :endDate and location_id =21;";
		
		return query;
	}
}
