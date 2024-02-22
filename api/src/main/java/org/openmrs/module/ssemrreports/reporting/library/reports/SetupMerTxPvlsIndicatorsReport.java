package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.Location;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SetupMerTxPvlsIndicatorsReport extends SSEMRDataExportManager {
	
	private final MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition;
	
	@Autowired
	public SetupMerTxPvlsIndicatorsReport(MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition) {
		this.merIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "edaf9845-ec1b-4918-a055-fe315afe9ed7";
	}
	
	@Override
	public String getUuid() {
		return "6ba23385-a11a-4dc0-bcec-dbb02f93e43a";
	}
	
	@Override
	public String getName() {
		return "TX PVLS MER Indicators Report";
	}
	
	@Override
	public String getDescription() {
		return "TX PVLS  MER Indicators Reports";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		String mappings = "startDate=${endDate-12m+1d},endDate=${endDate+23h}";
		String mappingsQ4 = "startDate=${endDate-3m+1d},endDate=${endDate+23h}";
		String mappingsQ3 = "startDate=${endDate-6m+1d},endDate=${endDate-3m+23h}";
		String mappingsQ2 = "startDate=${endDate-9m+1d},endDate=${endDate-6m+23h}";
		String mappingsQ1 = "startDate=${endDate-12m+1d},endDate=${endDate-9m+23h}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.addDataSetDefinition("TxP", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxPvlsDataset(), mappings));
		rd.addDataSetDefinition("TxPQ1", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxPvlsDataset(), mappingsQ1));
		rd.addDataSetDefinition("TxPQ2", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxPvlsDataset(), mappingsQ2));
		rd.addDataSetDefinition("TxPQ3", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxPvlsDataset(), mappingsQ3));
		rd.addDataSetDefinition("TxPQ4", SSEMRReportUtils.map(merIndicatorsDatasetDefinition.getTxPvlsDataset(), mappingsQ4));
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
			reportDesign = createXlsReportDesign(reportDefinition, "tx_pvls_mer_indicators.xls",
			    "TX PVLS MER Indicators Report", getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
