package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfClientsWithHvlWhoReceivedDSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class ListOfClientsWithHvlWhoReceivedEac3SessionRegister extends SSEMRDataExportManager {
	
	private final ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD;
	
	@Autowired
	public ListOfClientsWithHvlWhoReceivedEac3SessionRegister(
	    ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD) {
		this.listOfClientsWithHvlWhoReceivedDSD = listOfClientsWithHvlWhoReceivedDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "c2e19f3e-b268-46f4-b308-799cbecfcad9";
	}
	
	@Override
	public String getUuid() {
		return "fabe1a4f-564c-45cd-9334-0e607f111847";
	}
	
	@Override
	public String getName() {
		return "Clients with HVL, who received EAC3 session";
	}
	
	@Override
	public String getDescription() {
		return "List of Clients with HVL, who received EAC3 session report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addDataSetDefinition("EAC3", Mapped.mapStraightThrough(listOfClientsWithHvlWhoReceivedDSD.getEac3Session()));
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
			reportDesign = createXlsReportDesign(reportDefinition, "EAC3.xls",
			    "Clients with HVL, who received EAC3 session", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:EAC3");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
