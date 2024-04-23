package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ListOfFamilyContactsDSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class SetupListOfFamilyContactsKnownHivPositiveAtStartOfArtRegister extends SsemrDataExportManager {
	
	private final ListOfFamilyContactsDSD listOfFamilyContactsDSD;
	
	@Autowired
	public SetupListOfFamilyContactsKnownHivPositiveAtStartOfArtRegister(ListOfFamilyContactsDSD listOfFamilyContactsDSD) {
		this.listOfFamilyContactsDSD = listOfFamilyContactsDSD;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "242f5fb1-1e19-4665-a38b-e8d0228726c2";
	}
	
	@Override
	public String getUuid() {
		return "9638c2a2-c624-420a-be4c-1253c94303f1";
	}
	
	@Override
	public String getName() {
		return "List of Family contacts known HIV +ve at start of ART";
	}
	
	@Override
	public String getDescription() {
		return "List of Family contacts known HIV +ve at start of ART  report";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(listOfFamilyContactsDSD.getParameters());
		rd.addDataSetDefinition("FC4", Mapped.mapStraightThrough(listOfFamilyContactsDSD.getKnownHivPositiveAtStartOfArt()));
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
			reportDesign = createXlsReportDesign(reportDefinition, "fc4.xls",
			    "List of Family contacts known HIV positive at start of ART ", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:6,dataset:FC4");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
