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
public class setupMerTxCurrTxNewIndicatorsReport extends SsemrDataExportManager {
	
	private final MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition;
	
	@Autowired
	public setupMerTxCurrTxNewIndicatorsReport(
	    org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition) {
		this.merIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
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
		return "Tx CURR and NEW MER Indicators Report";
	}
	
	@Override
	public String getDescription() {
		return "Tx CURR and NEW MER Indicators Reports";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		String mappingsQuaterly = "startDate=${startDate},endDate=${endDate+23h}";
		String mappingsFirstMonth = "startDate=${startDate},endDate=${endDate-2m+23h}";
		String mappingsSecondMonth = "startDate=${startDate},endDate=${endDate-1m+23h}";
		String mappingsThirdMonth = "startDate=${endDate-1m},endDate=${endDate+23h}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.addDataSetDefinition("TxC",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxCurrDataset(), mappingsQuaterly));
		rd.addDataSetDefinition("TxC1",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxCurrDataset(), mappingsFirstMonth));
		rd.addDataSetDefinition("TxC2",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxCurrDataset(), mappingsSecondMonth));
		rd.addDataSetDefinition("TxC3",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxCurrDataset(), mappingsThirdMonth));
		
		//Tx new totals for 1,2 and 3 month
		rd.addDataSetDefinition("TxN",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxNewDataset(), mappingsQuaterly));
		rd.addDataSetDefinition("TxN1",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxNewDataset(), mappingsFirstMonth));
		rd.addDataSetDefinition("TxN2",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxNewDataset(), mappingsSecondMonth));
		rd.addDataSetDefinition("TxN3",
		    SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxNewDataset(), mappingsThirdMonth));
		
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
			reportDesign = createXlsReportDesign(reportDefinition, "tx_new_curr_mer_indicators.xls",
			    "Tx CURR AND NEW MER Indicators Report", getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
	
}
