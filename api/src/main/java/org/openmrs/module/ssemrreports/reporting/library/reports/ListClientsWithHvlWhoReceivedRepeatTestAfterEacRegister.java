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
public class ListClientsWithHvlWhoReceivedRepeatTestAfterEacRegister extends SsemrDataExportManager {
	
	private final ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD;
	
	@Autowired
	public ListClientsWithHvlWhoReceivedRepeatTestAfterEacRegister(
	    ListOfClientsWithHvlWhoReceivedDSD listOfClientsWithHvlWhoReceivedDSD) {
		this.listOfClientsWithHvlWhoReceivedDSD = listOfClientsWithHvlWhoReceivedDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "6c5c3da3-9bb2-405d-ae7d-bf9796af682e";
	}
	
	@Override
	public String getUuid() {
		return "c6310dbd-948b-4949-bcb1-d8627b41aaf6";
	}
	
	@Override
	public String getName() {
		return "Clients with HVL Who received repeat test after EAC";
	}
	
	@Override
	public String getDescription() {
		return "List of Clients with HVL, Who received repeat test after EAC";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		String param = "startDate=${startDate},endDate=${endDate+23h}";
		ReportDefinition rd = new ReportDefinition();
		rd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		rd.addParameter(new Parameter("endDate", "End Date", Date.class));
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addDataSetDefinition("EACRA",
		    SsemrReportUtils.map(listOfClientsWithHvlWhoReceivedDSD.getRepeatTestAfterEacSession(), param));
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
			reportDesign = createXlsReportDesign(reportDefinition, "EACRA.xls",
			    "Clients with HVL, Who received repeat test after EAC", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:EACRA");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
