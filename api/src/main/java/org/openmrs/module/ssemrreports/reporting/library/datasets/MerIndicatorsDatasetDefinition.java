package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.ArtCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils.map;

@Component
public class MerIndicatorsDatasetDefinition extends SSEMRBaseDataSet{
    private final SSEMRCommonDimension dimension;
    private final ArtCohortQueries artCohortQueries;

    @Autowired
    public MerIndicatorsDatasetDefinition(SSEMRCommonDimension dimension, ArtCohortQueries artCohortQueries) {
        this.dimension = dimension;
        this.artCohortQueries = artCohortQueries;
    }

    public DataSetDefinition getTxCurrDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addDimension("gender", map(dimension.gender(), ""));
        dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
        String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
        dsd.setName("TxCurr");
        return dsd;
    }
    public DataSetDefinition getTxNewDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addDimension("gender", map(dimension.gender(), ""));
        dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
        String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
        dsd.setName("TxNew");
        return dsd;
    }
    public DataSetDefinition getTxMlDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addDimension("gender", map(dimension.gender(), ""));
        dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
        String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
        dsd.setName("TxMl");
        return dsd;
    }
    public DataSetDefinition getTxRttDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addDimension("gender", map(dimension.gender(), ""));
        dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
        String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
        dsd.setName("TxRtt");
        return dsd;
    }
    public DataSetDefinition getTxPvlsDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addDimension("gender", map(dimension.gender(), ""));
        dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
        String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
        dsd.setName("TxPvls");
        return dsd;
    }
}
