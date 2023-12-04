package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.springframework.stereotype.Component;

@Component
public class ListOfClientsWithHvlWhoReceivedDSD extends SSEMRBaseDataSet {
	
	/**
	 * Clients with HVL, who received EAC1 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac1Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC1 session");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.client_id, fh.unique_art_or_hie_number,fh.individual_name,fh.age,fh.sex,"
		                + " fh.marital_status,fh.occupation,fh.occupation_other,fh.relationship,fh.phone_number,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh "
		                + " INNER JOIN ssemr_flat_encounter_high_viral_load vl ON fh.client_id=vl.client_id"
		                + " WHERE CAST(vl.recent_vl, AS INTEGER) > 1000 AND vl.eac_session ='First EAC Session'");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received EAC2 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac2Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC2 session");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.client_id, fh.unique_art_or_hie_number,fh.individual_name,fh.age,fh.sex,"
		                + " fh.marital_status,fh.occupation,fh.occupation_other,fh.relationship,fh.phone_number,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh "
		                + " INNER JOIN ssemr_flat_encounter_high_viral_load vl ON fh.client_id=vl.client_id"
		                + " WHERE CAST(vl.recent_vl, AS INTEGER) > 1000 AND vl.eac_session ='Second EAC Session'");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received EAC3 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac3Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC3 session");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.client_id, fh.unique_art_or_hie_number,fh.individual_name,fh.age,fh.sex,"
		                + " fh.marital_status,fh.occupation,fh.occupation_other,fh.relationship,fh.phone_number,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh "
		                + " INNER JOIN ssemr_flat_encounter_high_viral_load vl ON fh.client_id=vl.client_id"
		                + " WHERE CAST(vl.recent_vl, AS INTEGER) > 1000 AND vl.eac_session ='Third EAC Session'");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received extended EAC session
	 * 
	 * @return
	 */
	public DataSetDefinition getExtendedEacSession() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received Extended EAC session");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.client_id, fh.unique_art_or_hie_number,fh.individual_name,fh.age,fh.sex,"
		                + " fh.marital_status,fh.occupation,fh.occupation_other,fh.relationship,fh.phone_number,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh "
		                + " INNER JOIN ssemr_flat_encounter_high_viral_load vl ON fh.client_id=vl.client_id"
		                + " WHERE CAST(vl.recent_vl, AS INTEGER) > 1000 AND vl.eac_session ='Extended session of EAC'");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, Who received repeat test after EAC
	 * 
	 * @return
	 */
	public DataSetDefinition getRepeatTestAfterEacSession() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received Repeat test after EAC session");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.client_id, fh.unique_art_or_hie_number,fh.individual_name,fh.age,fh.sex,"
		                + " fh.marital_status,fh.occupation,fh.occupation_other,fh.relationship,fh.phone_number,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh "
		                + " INNER JOIN ssemr_flat_encounter_high_viral_load vl ON fh.client_id=vl.client_id"
		                + " WHERE CAST(vl.recent_vl, AS INTEGER) > 1000 AND vl.eac_session ='Repeat test after EAC'");
		return sqlDataSetDefinition;
	}
	
}
