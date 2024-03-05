package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.Location;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class DistrictDatasetDefinition extends SSEMRBaseDataSet {
	
	protected String getAddressQuery(int mflCodeId) {
		
		return "SELECT l.county_district AS district, la.value_reference AS mfl_code FROM location l INNER JOIN location_attribute la ON l.location_id=la.location_id "
		        + " INNER JOIN location_attribute_type lat ON la.attribute_type_id=lat.location_attribute_type_id "
		        + " WHERE l.retired=0 AND lat.retired=0 AND la.voided=0 AND la.attribute_type_id="
		        + mflCodeId;
	}
	
	public DataSetDefinition getAddressDataset(int mflCodeId) {
		SqlDataSetDefinition dataSet = new SqlDataSetDefinition();
		dataSet.addParameter(new Parameter("location", "Location", Location.class));
		dataSet.setSqlQuery(getAddressQuery(mflCodeId));
		
		return dataSet;
	}
}
