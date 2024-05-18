package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.TPTCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.CompletedTPTDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class SetupCompletedTPTRegister extends SsemrDataExportManager {
	
	private final CompletedTPTDatasetDefinition completedTPTDatasetDefinition;
	
	private final TPTCohortQueries tptCohortQueries;
	
	@Autowired
	public SetupCompletedTPTRegister(CompletedTPTDatasetDefinition completedTPTDatasetDefinition,
	    TPTCohortQueries tptCohortQueries) {
		this.completedTPTDatasetDefinition = completedTPTDatasetDefinition;
		this.tptCohortQueries = tptCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.COMPLETED_TPT_LIST_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.COMPLETED_TPT_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Patient completed TPT list on Date";
	}
	
	@Override
	public String getDescription() {
		return "Patients who completed TPT";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(completedTPTDatasetDefinition.getParameters());
		rd.addDataSetDefinition("CTPT",
		    Mapped.mapStraightThrough(completedTPTDatasetDefinition.constructCompletedTPTDatasetDefinition()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(tptCohortQueries.getPatientsWhoAHaveCompletedTPT(),
		    "startDate=${startDate},endDate=${endDate+23h}"));
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
			reportDesign = createXlsReportDesign(reportDefinition, "completedTPTRegister.xls",
			    "Report for clients who completed TPT", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:2,dataset:CTPT");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
