package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.springframework.stereotype.Component;

@Component
public class ListOfFamilyContactsDSD extends SSEMRBaseDataSet {
	
	public DataSetDefinition getWithUnknownHivStatus() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status");
		sqlDataSetDefinition.setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getWithUnknownHivStatusTested() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts with unknown HIV status tested");
		sqlDataSetDefinition.setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getTestedPositive() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts newly tested positive");
		sqlDataSetDefinition.setSqlQuery("SELECT * FROM ssemr_etl.ssemr_flat_encounter_family_history");
		return sqlDataSetDefinition;
	}
	
	public DataSetDefinition getKnownHivPositiveAtStartOfArt() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("List of Family contacts known HIV positive at start of ART");
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
