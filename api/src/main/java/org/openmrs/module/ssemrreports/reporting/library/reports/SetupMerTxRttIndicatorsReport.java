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
public class SetupMerTxRttIndicatorsReport extends SSEMRDataExportManager {
	
	private final MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition;
	
	@Autowired
	public SetupMerTxRttIndicatorsReport(MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition) {
		this.merIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "dda14f75-74ea-4b79-8c7c-833009a0c486";
	}
	
	@Override
	public String getUuid() {
		return "d4bacb92-1619-4e22-a5aa-0b143ae2597d";
	}
	
	@Override
	public String getName() {
		return "Tx RTT MER Indicators Report";
	}
	
	@Override
	public String getDescription() {
		return "Tx RTT MER Indicators Reports";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(merIndicatorsDatasetDefinition.getParameters());
		rd.addDataSetDefinition("TxR", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxRttDataset(), mappings));
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
			reportDesign = createXlsReportDesign(reportDefinition, "tx_rtt_mer_indicators.xls",
			    "Tx RTT MER Indicators Report", getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
