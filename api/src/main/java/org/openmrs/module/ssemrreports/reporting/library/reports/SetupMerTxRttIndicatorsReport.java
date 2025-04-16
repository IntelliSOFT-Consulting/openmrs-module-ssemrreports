package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.Location;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.MerIndicatorsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SetupMerTxRttIndicatorsReport extends SsemrDataExportManager {
	
	private final MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupMerTxRttIndicatorsReport(MerIndicatorsDatasetDefinition merIndicatorsDatasetDefinition,
	    BaseCohortQueries baseCohortQueries) {
		this.merIndicatorsDatasetDefinition = merIndicatorsDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
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
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		String mappings1 = "startDate=${startDate},endDate=${startDate+1m-1d},location=${location}";
		String mappings2 = "startDate=${startDate+1m},endDate=${startDate+2m-1d},location=${location}";
		String mappings3 = "startDate=${startDate+2m},endDate=${endDate},location=${location}";
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameter(new Parameter("startDate", "StartDate", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.addParameter(new Parameter("location", "Location", Location.class));
		rd.addDataSetDefinition("TxR", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxRttDataset(), mappings));
		rd.addDataSetDefinition("TxR1", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxRttDataset(), mappings1));
		rd.addDataSetDefinition("TxR2", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxRttDataset(), mappings2));
		rd.addDataSetDefinition("TxR3", SsemrReportUtils.map(merIndicatorsDatasetDefinition.getTxRttDataset(), mappings3));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(baseCohortQueries.getAccurateClientsOnArtPerFacility(),
		    "endDate=${endDate},location=${location}"));
		
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
