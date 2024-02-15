package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.Location;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfClientsWithHvlWhoReceivedDSD;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Component
public class ListClientsWithHvlWhoReceivedExtendedEacSessionRegister extends SSEMRDataExportManager {
	
	private final ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD;
	
	@Autowired
	public ListClientsWithHvlWhoReceivedExtendedEacSessionRegister(
	    ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD) {
		this.listOfClientsWithHvlWhoReceivedDSD = listOfClientsWithHvlWhoReceivedDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "df8a46ef-5280-409a-8079-4b3c62adbfff";
	}
	
	@Override
	public String getUuid() {
		return "592c36ef-60d4-4ca7-9c50-7fe9923c9c42";
	}
	
	@Override
	public String getName() {
		return "Clients with HVL who received extended EAC session";
	}
	
	@Override
	public String getDescription() {
		return "List of Clients with HVL, who received extended EAC session report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		String param = "startDate=${startDate},endDate=${endDate},location=${location}";
		ReportDefinition rd = new ReportDefinition();
		rd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.addParameter(new Parameter("location", "Location", Location.class));
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addDataSetDefinition("EACX",
		    SSEMRReportUtils.map(listOfClientsWithHvlWhoReceivedDSD.getExtendedEacSession(), param));
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
			reportDesign = createXlsReportDesign(reportDefinition, "EACX.xls",
			    "Clients with HVL, who received extended EAC session", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:EACX");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
