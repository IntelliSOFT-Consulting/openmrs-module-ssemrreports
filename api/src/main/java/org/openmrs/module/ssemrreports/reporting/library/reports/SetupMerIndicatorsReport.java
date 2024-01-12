package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupMerIndicatorsReport extends SSEMRDataExportManager {
	
	private final MerIndicatorsDatasetDefinition MerIndicatorsDatasetDefinition;
	
	@Autowired
	public SetupMerIndicatorsReport(
	    org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition) {
		MerIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "44c5ff2c-c264-4866-ac9f-64e8dd1d8132";
	}
	
	@Override
	public String getUuid() {
		return "9810dedc-f508-4ed5-9b86-2355b1cb3ca7";
	}
	
	@Override
	public String getName() {
		return "MER Indicators Report";
	}
	
	@Override
	public String getDescription() {
		return "MER Indicators Reports";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(MerIndicatorsDatasetDefinition.getParameters());
		rd.addDataSetDefinition("TxC", SSEMRReportUtils.map(MerIndicatorsDatasetDefinition.getTxCurrDataset(), mappings));
		//rd.addDataSetDefinition("TxN", SSEMRReportUtils.map(MerIndicatorsDatasetDefinition.getTxNewDataset(), mappings));
		//rd.addDataSetDefinition("TxM", SSEMRReportUtils.map(MerIndicatorsDatasetDefinition.getTxMlDataset(), mappings));
		//rd.addDataSetDefinition("TxR", SSEMRReportUtils.map(MerIndicatorsDatasetDefinition.getTxRttDataset(), mappings));
		//rd.addDataSetDefinition("TxP", SSEMRReportUtils.map(MerIndicatorsDatasetDefinition.getTxPvlsDataset(), mappings));
		return rd;
	}
	
	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		ReportDesign reportDesign = null;
		try {
			reportDesign = createXlsReportDesign(reportDefinition, "mer_indicators.xls", "MER Indicators Report",
			    getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
