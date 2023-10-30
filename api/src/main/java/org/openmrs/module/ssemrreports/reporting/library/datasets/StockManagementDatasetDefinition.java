package org.openmrs.module.ssemrreports.reporting.library.datasets;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.ssemrreports.reporting.library.queries.StockManagementQueries;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

@Component
public class StockManagementDatasetDefinition extends SSEMRBaseDataSet {
	
	public DataSetDefinition getProductStock() {
		
		SqlDataSetDefinition dataSet = new SqlDataSetDefinition();
		dataSet.setName("Product Stock in inventory");
		dataSet.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dataSet.addParameter(new Parameter("endDate", "End Date", Date.class));
		dataSet.addParameter(new Parameter("location", "Location", Location.class));
		dataSet.setSqlQuery(StockManagementQueries.getProductStockLevels());
		return dataSet;
		
	}
}
