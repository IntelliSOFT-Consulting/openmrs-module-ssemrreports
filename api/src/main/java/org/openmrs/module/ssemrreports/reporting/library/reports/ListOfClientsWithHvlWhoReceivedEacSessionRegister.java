package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfClientsWithHvlWhoReceivedDSD;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class ListOfClientsWithHvlWhoReceivedEacSessionRegister extends SsemrDataExportManager {
	
	private final ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD;
	
	@Autowired
	public ListOfClientsWithHvlWhoReceivedEacSessionRegister(
	    ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD) {
		this.listOfClientsWithHvlWhoReceivedDSD = listOfClientsWithHvlWhoReceivedDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "55ed0bc6-acf8-495e-8141-3389fcd9d6be";
	}
	
	@Override
	public String getUuid() {
		return "271e7124-d5a3-4957-b7a5-eb81d5755d45";
	}
	
	@Override
	public String getName() {
		return "Clients with HVL who received EAC session";
	}
	
	@Override
	public String getDescription() {
		return "List of Clients with HVL, who received EAC session report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		String param = "startDate=${startDate},endDate=${endDate+23h}";
		ReportDefinition rd = new ReportDefinition();
		rd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		//rd.addParameter(new Parameter("location", "Location", Location.class));
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addDataSetDefinition("EAC", SsemrReportUtils.map(listOfClientsWithHvlWhoReceivedDSD.getEac1Session(), param));
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
			reportDesign = createXlsReportDesign(reportDefinition, "EAC1.xls", "Clients with HVL, who received EAC session",
			    getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:EAC");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
