package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.ContactsCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ContactsWithUnknownHivStatusTestedDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupContactsWithUnknownHivStatusTestedRegister extends SsemrDataExportManager {
	
	private final ContactsWithUnknownHivStatusTestedDatasetDefinition contactsWithUnknownHivStatusTestedDatasetDefinition;
	
	private final ContactsCohortQueries contactsCohortQueries;
	
	@Autowired
	public SetupContactsWithUnknownHivStatusTestedRegister(
	    ContactsWithUnknownHivStatusTestedDatasetDefinition contactsWithUnknownHivStatusTestedDatasetDefinition,
	    ContactsCohortQueries contactsCohortQueries) {
		this.contactsWithUnknownHivStatusTestedDatasetDefinition = contactsWithUnknownHivStatusTestedDatasetDefinition;
		this.contactsCohortQueries = contactsCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.CONTACTS_WITH_UNKNOWN_HIV_STATUS_TESTED_LIST_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.CONTACTS_WITH_UNKNOWN_HIV_STATUS_TESTED_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Listed family members, who have taken HIV test and received HIV results at the time of listing";
	}
	
	@Override
	public String getDescription() {
		return "Listed family members, who have taken HIV test and received HIV results at the time of listing";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(contactsWithUnknownHivStatusTestedDatasetDefinition.getParameters());
		rd.addDataSetDefinition("CUHST", Mapped.mapStraightThrough(contactsWithUnknownHivStatusTestedDatasetDefinition
		        .constructContactsWithUnknownHivStatusDatasetDefinition()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(contactsCohortQueries.getPatientsWhoHaveUnknownHivStatusTested(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "contactsUnknownHivStatusTestedRegister.xls",
			    "List the family members whose HIV result is not known and have been tested at the time of listing",
			    getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:2,dataset:CUHST");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
