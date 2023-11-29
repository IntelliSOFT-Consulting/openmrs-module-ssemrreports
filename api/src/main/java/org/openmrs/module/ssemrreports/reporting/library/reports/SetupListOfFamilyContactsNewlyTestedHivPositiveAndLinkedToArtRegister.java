package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfFamilyContactsDSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class SetupListOfFamilyContactsNewlyTestedHivPositiveAndLinkedToArtRegister extends SSEMRDataExportManager {
	
	private final ListOfFamilyContactsDSD listOfFamilyContactsDSD;
	
	@Autowired
	public SetupListOfFamilyContactsNewlyTestedHivPositiveAndLinkedToArtRegister(
	    ListOfFamilyContactsDSD listOfFamilyContactsDSD) {
		this.listOfFamilyContactsDSD = listOfFamilyContactsDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "109b9f77-95a8-49f5-b088-af233f83d2b1";
	}
	
	@Override
	public String getUuid() {
		return "4226d249-6f4b-42c1-977c-ce55cdce844a";
	}
	
	@Override
	public String getName() {
		return "List of Family contacts newly tested HIV Positive and linked to ART";
	}
	
	@Override
	public String getDescription() {
		return "List of Family contacts newly tested HIV +ve and linked to ART report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(listOfFamilyContactsDSD.getParameters());
		rd.addDataSetDefinition("FC5",
		    Mapped.mapStraightThrough(listOfFamilyContactsDSD.getNewlyTestedHivPositiveAndLinkedToArt()));
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
			reportDesign = createXlsReportDesign(reportDefinition, "fc5.xls",
			    "List of Family contacts newly tested HIV Positive and linked to ART", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:2,dataset:FC5");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
