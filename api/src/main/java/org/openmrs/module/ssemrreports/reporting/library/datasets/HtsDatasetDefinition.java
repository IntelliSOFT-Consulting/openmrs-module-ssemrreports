package org.openmrs.module.ssemrreports.reporting.library.datasets;

import static org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils.map;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.CommonCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.HtsCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SsemrCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SsemrGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtsDatasetDefinition extends SsemrBaseDataSet {
	
	private final SsemrCommonDimension dimension;
	
	private final SsemrGeneralIndicator indicator;
	
	private final CommonCohortQueries commonCohortQueries;
	
	private final HtsCohortQueries htsCohortQueries;
	
	@Autowired
	public HtsDatasetDefinition(SsemrCommonDimension dimension, SsemrGeneralIndicator indicator,
	    CommonCohortQueries commonCohortQueries, HtsCohortQueries htsCohortQueries) {
		this.dimension = dimension;
		this.indicator = indicator;
		this.commonCohortQueries = commonCohortQueries;
		this.htsCohortQueries = htsCohortQueries;
	}
	
	public DataSetDefinition constructHtsDataset() {
		
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		dsd.setName("HTS");
		dsd.addParameters(getParameters());
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("type", map(dimension.getCitizenType(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		
		//LAB LOGISTICS DATA
		return dsd;
		
	}
}
