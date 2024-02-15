package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.Location;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ListOfFamilyContactsDSD extends SSEMRBaseDataSet {
	
	public DataSetDefinition getWithUnknownHivStatus() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("location", "Location", Location.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.encounter_datetime,fh.marital_status,fh.occupation,fh.occupation_other,"
		                + " fh.individual_name, fh.age,fh.sex, fh.relationship,fh.phone_number,fh.known_hiv_status_during_mapping,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art,fh.unique_art_or_hie_number "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh"
		                + " INNER JOIN ssemr_etl.ssemr_flat_index_mapping_and_testing t ON fh.client_id=t.client_id"
		                + " WHERE t.f_member_hiv_status_during_mapping ='Unknown' AND DATE(fh.encounter_datetime) BETWEEN :startDate AND :endDate AND fh.location_id=:location");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getWithUnknownHivStatusTested() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status tested");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("location", "Location", Location.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.encounter_datetime,fh.marital_status,fh.occupation,fh.occupation_other,"
		                + " fh.individual_name, fh.age,fh.sex, fh.relationship,fh.phone_number,fh.known_hiv_status_during_mapping,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art,fh.unique_art_or_hie_number "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh"
		                + " INNER JOIN ssemr_etl.ssemr_flat_index_mapping_and_testing t ON fh.client_id=t.client_id"
		                + " WHERE t.f_member_hiv_status_during_mapping ='Unknown' AND t.f_member_hts_tested = 'Yes' AND DATE(fh.encounter_datetime) BETWEEN :startDate "
		                + " AND :endDate AND fh.location_id=:location");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getTestedPositive() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts newly tested positive");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("location", "Location", Location.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.encounter_datetime,fh.marital_status,fh.occupation,fh.occupation_other,"
		                + " fh.individual_name, fh.age,fh.sex, fh.relationship,fh.phone_number,fh.known_hiv_status_during_mapping,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art,fh.unique_art_or_hie_number "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh"
		                + " INNER JOIN ssemr_etl.ssemr_flat_index_mapping_and_testing t ON fh.client_id=t.client_id"
		                + " WHERE t.f_member_hiv_status_during_mapping ='Positive' AND DATE(fh.encounter_datetime) BETWEEN :startDate "
		                + " AND :endDate AND fh.location_id=:location");
		
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getKnownHivPositiveAtStartOfArt() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts known HIV positive at start of ART");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("location", "Location", Location.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.encounter_datetime,fh.marital_status,fh.occupation,fh.occupation_other,"
		                + " fh.individual_name, fh.age,fh.sex, fh.relationship,fh.phone_number,fh.known_hiv_status_during_mapping,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art,fh.unique_art_or_hie_number "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh"
		                + " INNER JOIN ssemr_etl.ssemr_flat_index_mapping_and_testing t ON fh.client_id=t.client_id"
		                + " WHERE t.f_member_hiv_status_during_mapping ='Positive' AND t.f_member_on_art = 'Yes' AND  DATE(fh.encounter_datetime) BETWEEN :startDate "
		                + " AND :endDate AND fh.location_id=:location AND fh.on_art IS NOT NULL");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getNewlyTestedHivPositiveAndLinkedToArt() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts newly tested HIV positive and linked to ART");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("location", "Location", Location.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT fh.encounter_datetime,fh.marital_status,fh.occupation,fh.occupation_other,"
		                + " fh.individual_name, fh.age,fh.sex, fh.relationship,fh.phone_number,fh.known_hiv_status_during_mapping,"
		                + " fh.hiv_status,fh.date_hiv_tested,fh.result_of_hts,fh.on_art,fh.unique_art_or_hie_number "
		                + " FROM ssemr_etl.ssemr_flat_encounter_family_history fh"
		                + " INNER JOIN ssemr_etl.ssemr_flat_index_mapping_and_testing t ON fh.client_id=t.client_id"
		                + " WHERE t.f_member_hts_testing_results ='Positive' AND t.f_member_hts_tested = 'Yes' AND t.f_member_on_art= 'Yes' AND DATE(fh.encounter_datetime) BETWEEN :startDate "
		                + " AND :endDate AND fh.location_id=:location AND fh.result_of_hts IS NOT NULL");
		return sqlDataSetDefinition;
	}
}
