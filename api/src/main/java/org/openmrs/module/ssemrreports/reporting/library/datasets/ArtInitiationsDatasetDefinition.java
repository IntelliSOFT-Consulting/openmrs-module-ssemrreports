package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.springframework.stereotype.Component;

@Component
public class ArtInitiationsDatasetDefinition extends SsemrBaseDataSet {
	
	public DataSetDefinition constructArtInitiationsDatasetDefinition() {
		SqlDataSetDefinition sqlDsd = new SqlDataSetDefinition();
		sqlDsd.setName("ART Initiations");
		sqlDsd.setSqlQuery("SELECT p.person_name_long as person_name, p.age, p.birthdate, e.date_first_tested_positive, e.encounter_datetime FROM ssemr_etl.ssemr_flat_encounter_hiv_care_enrolment e INNER JOIN ssemr_etl.mamba_dim_person p ON e.client_id = p.person_id ORDER BY e.encounter_datetime DESC LIMIT 10");
		return sqlDsd;
	}
}
