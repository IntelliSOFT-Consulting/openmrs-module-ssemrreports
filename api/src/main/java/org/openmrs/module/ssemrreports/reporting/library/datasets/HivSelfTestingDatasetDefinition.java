package org.openmrs.module.ssemrreports.reporting.library.datasets;

import static org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils.map;

import org.openmrs.module.ssemrreports.reporting.library.cohorts.HivSelfTestingCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.dimension.EmrDimensionReferences;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HivSelfTestingDatasetDefinition extends SSEMRBaseDataSet {
	
	private final SSEMRCommonDimension dimension;
	
	private final SSEMRGeneralIndicator indicator;
	
	private final HivSelfTestingCohortQueries hivSelfTestingCohortQueries;
	
	@Autowired
	public HivSelfTestingDatasetDefinition(SSEMRCommonDimension dimension, SSEMRGeneralIndicator indicator,
	    HivSelfTestingCohortQueries hivSelfTestingCohortQueries) {
		this.dimension = dimension;
		this.indicator = indicator;
		this.hivSelfTestingCohortQueries = hivSelfTestingCohortQueries;
	}
	
	public DataSetDefinition constructHivSelfTestingDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		dsd.setName("HIV SELF TESTING");
		dsd.addParameters(getParameters());
		
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		
		addRow(
		    dsd,
		    "PDFB",
		    "Purpose of Distribution - Facility Based  by age and gender",
		    map(indicator.getIndicator("Purpose of Distribution - Facility Based  by age and gender",
		        map(hivSelfTestingCohortQueries.getAllPatientsByPurposeOfDistributionAndFacilityBased(), mappings)),
		        mappings), EmrDimensionReferences.getHivSelfTestingGenderAndAgeDisaggregation());
		
		return dsd;
	}
}
