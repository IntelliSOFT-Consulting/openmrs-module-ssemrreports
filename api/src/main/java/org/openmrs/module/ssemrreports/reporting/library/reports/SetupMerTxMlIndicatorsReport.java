package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
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
public class SetupMerTxMlIndicatorsReport extends SSEMRDataExportManager {
	
	private final MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition;
	
	@Autowired
	public SetupMerTxMlIndicatorsReport(MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition) {
		this.merIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "135b38f7-f7c5-4e84-ac4d-5c8fcb4ece58";
	}
	
	@Override
	public String getUuid() {
		return "135b38f7-f7c5-4e84-ac4d-5c8fcb4ece58";
	}
	
	@Override
	public String getName() {
		return "Tx ML MER Indicators Report";
	}
	
	@Override
	public String getDescription() {
		return "Tx ML MER Indicators Reports";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(merIndicatorsDatasetDefinition.getParameters());
		rd.addDataSetDefinition("TxM", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings));
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
			reportDesign = createXlsReportDesign(reportDefinition, "tx_ml_mer_indicators.xls",
			    "Tx ML MER Indicators Report", getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
