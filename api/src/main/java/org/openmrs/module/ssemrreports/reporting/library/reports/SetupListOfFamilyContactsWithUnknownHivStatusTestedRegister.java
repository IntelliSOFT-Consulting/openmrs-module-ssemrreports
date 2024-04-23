package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfFamilyContactsDSD;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class SetupListOfFamilyContactsWithUnknownHivStatusTestedRegister extends SsemrDataExportManager {
	
	private final ListOfFamilyContactsDSD listOfFamilyContactsDSD;
	
	@Autowired
	public SetupListOfFamilyContactsWithUnknownHivStatusTestedRegister(ListOfFamilyContactsDSD listOfFamilyContactsDSD) {
		this.listOfFamilyContactsDSD = listOfFamilyContactsDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "1baaa1bf-a6d2-48e6-88a4-780e0387d751";
	}
	
	@Override
	public String getUuid() {
		return "ab7467f6-0ff3-4c03-be48-4ff01a19f329";
	}
	
	@Override
	public String getName() {
		return "List of Family contacts with unknown HIV status tested";
	}
	
	@Override
	public String getDescription() {
		return "List of Family contacts with unknown HIV status tested report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		String param = "startDate=${startDate},endDate=${endDate+23h}";
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(listOfFamilyContactsDSD.getParameters());
		rd.addDataSetDefinition("FC2", SSEMRReportUtils.map(listOfFamilyContactsDSD.getWithUnknownHivStatusTested(), param));
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
			reportDesign = createXlsReportDesign(reportDefinition, "fc2.xls",
			    "List of Family contacts with unknown HIV status tested", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:FC2");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
