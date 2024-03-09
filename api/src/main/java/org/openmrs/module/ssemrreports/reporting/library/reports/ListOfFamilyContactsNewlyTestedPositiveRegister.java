package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfFamilyContactsDSD;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class ListOfFamilyContactsNewlyTestedPositiveRegister extends SSEMRDataExportManager {
	
	private final ListOfFamilyContactsDSD listOfFamilyContactsDSD;
	
	@Autowired
	public ListOfFamilyContactsNewlyTestedPositiveRegister(ListOfFamilyContactsDSD listOfFamilyContactsDSD) {
		this.listOfFamilyContactsDSD = listOfFamilyContactsDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "96cd742c-9044-4a8c-a4ea-2bc16c6e78f8";
	}
	
	@Override
	public String getUuid() {
		return "ddb48a05-a272-4c21-9fcb-e31644d74962";
	}
	
	@Override
	public String getName() {
		return "List of Family contacts newly tested positive";
	}
	
	@Override
	public String getDescription() {
		return "List of Family contacts newly tested +ve report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		String param = "startDate=${startDate},endDate=${endDate+23h}";
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(listOfFamilyContactsDSD.getParameters());
		rd.addDataSetDefinition("FC3", SSEMRReportUtils.map(listOfFamilyContactsDSD.getTestedPositive(), param));
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
			reportDesign = createXlsReportDesign(reportDefinition, "fc3.xls",
			    "List of Family contacts newly tested positive", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:FC3");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
