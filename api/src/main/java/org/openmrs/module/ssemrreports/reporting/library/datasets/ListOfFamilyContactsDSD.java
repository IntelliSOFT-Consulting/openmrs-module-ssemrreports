package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.springframework.stereotype.Component;

@Component
public class ListOfFamilyContactsDSD extends SSEMRBaseDataSet {
	
	public DataSetDefinition getWithUnknownHivStatus() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history WHERE hiv_status ='Don't Know'");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getWithUnknownHivStatusTested() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status tested");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history WHERE hiv_status IS NOT NULL");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getTestedPositive() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts newly tested positive");
		sqlDataSetDefinition
		        .setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history WHERE hiv_status='Positive'");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getKnownHivPositiveAtStartOfArt() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition
		        .setName("List of Family contacts known HIV positive at start of ART WHERE hiv_status='Positive' AND on_art IS NOT NULL");
		sqlDataSetDefinition.setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getNewlyTestedHivPositiveAndLinkedToArt() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts newly tested HIV positive and linked to ART");
		sqlDataSetDefinition.setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history");
		return sqlDataSetDefinition;
	}
}
