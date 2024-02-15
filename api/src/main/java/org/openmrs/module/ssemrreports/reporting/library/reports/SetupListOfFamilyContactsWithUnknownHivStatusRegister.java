package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfFamilyContactsDSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class SetupListOfFamilyContactsWithUnknownHivStatusRegister extends SSEMRDataExportManager {
	
	private final ListOfFamilyContactsDSD listOfFamilyContactsDSD;
	
	@Autowired
	public SetupListOfFamilyContactsWithUnknownHivStatusRegister(ListOfFamilyContactsDSD listOfFamilyContactsDSD) {
		this.listOfFamilyContactsDSD = listOfFamilyContactsDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "26e56aae-938c-4514-9c93-bb1e8d86d253";
	}
	
	@Override
	public String getUuid() {
		return "a557238b-650c-4f63-a633-5e4c9fa65940";
	}
	
	@Override
	public String getName() {
		return "List of Family contacts with unknown HIV status";
	}
	
	@Override
	public String getDescription() {
		return "List of Family contacts with unknown HIV status report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(listOfFamilyContactsDSD.getParameters());
		rd.addDataSetDefinition("FC1", Mapped.mapStraightThrough(listOfFamilyContactsDSD.getWithUnknownHivStatus()));
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
			reportDesign = createXlsReportDesign(reportDefinition, "fc1.xls",
			    "List of Family contacts with unknown HIV status", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:FC1");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
