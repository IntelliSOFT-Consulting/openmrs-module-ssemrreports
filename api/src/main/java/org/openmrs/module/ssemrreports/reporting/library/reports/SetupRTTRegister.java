package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.RTTDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupRTTRegister extends SSEMRDataExportManager {
	
	private final RTTDatasetDefinition rttDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupRTTRegister(RTTDatasetDefinition rttDatasetDefinition, BaseCohortQueries baseCohortQueries) {
		this.rttDatasetDefinition = rttDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.RTT_LIST_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.RTT_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Patient who are RTT on Date";
	}
	
	@Override
	public String getDescription() {
		return "Patients who are on RTT";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(rttDatasetDefinition.getParameters());
		rd.addDataSetDefinition("RTT", Mapped.mapStraightThrough(rttDatasetDefinition.constructRTTDatasetDefinition()));
		rd.setBaseCohortDefinition(SSEMRReportUtils.map(baseCohortQueries.getPatientsWhoAreRTT(),
		    "startDate=${startDate},endDate=${endDate+23h},location=${location}"));
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
			reportDesign = createXlsReportDesign(reportDefinition, "rttRegister.xls", "Report for RTT clients",
			    getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:2,dataset:RTT");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
