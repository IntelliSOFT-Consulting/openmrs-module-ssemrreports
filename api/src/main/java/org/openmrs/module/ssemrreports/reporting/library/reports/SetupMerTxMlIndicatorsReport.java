package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SetupMerTxMlIndicatorsReport extends SsemrDataExportManager {
	
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
		String mappings0 = "startDate=${startDate},endDate=${endDate+23h}";
		String mappings1 = "startDate=${startDate},endDate=${startDate+1m-1d+23h}";
		String mappings2 = "startDate=${startDate+1m},endDate=${startDate+2m-1d+23h}";
		String mappings3 = "startDate=${startDate+2m},endDate=${startDate+3m-1d+23h}";
		String mappings4 = "startDate=${startDate+3m},endDate=${endDate+23h}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.addDataSetDefinition("TxM", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings0));
		rd.addDataSetDefinition("TxM1", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings1));
		rd.addDataSetDefinition("TxM2", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings2));
		rd.addDataSetDefinition("TxM3", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings3));
		rd.addDataSetDefinition("TxM4", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxMlDataset(), mappings4));
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
