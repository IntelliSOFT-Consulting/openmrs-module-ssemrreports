package org.openmrs.module.ssemrreports.reporting.library.queries;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ContactsQueries {
	
	public static String getContactsWithUnknownHivStatus() {
		
		String query = "SELECT client_id FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment "
		        + " WHERE family_member_names != '' AND family_member_names IS NOT NULL "
		        + " AND family_member_hiv_status = 'Don''t Know' and encounter_datetime between :startDate and "
		        + " :endDate and location_id =:location group by client_id";
		
		return query;
	}
}
