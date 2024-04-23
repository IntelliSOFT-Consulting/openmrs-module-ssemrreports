package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.DocumentedVLDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupDocumentedVLRegister extends SSEMRDataExportManager {
	
	private final DocumentedVLDatasetDefinition documentedVlDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupDocumentedVLRegister(DocumentedVLDatasetDefinition documentedVlDatasetDefinition,
	    BaseCohortQueries baseCohortQueries) {
		this.documentedVlDatasetDefinition = documentedVlDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.PATIENTS_DOCUMENTED_VL_LIST_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.PATIENTS_DOCUMENTED_VL_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Report for patients with documented VL tests";
	}
	
	@Override
	public String getDescription() {
		return "List of clients whose sample have been collected for and documented VL test in the reporting period";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(documentedVlDatasetDefinition.getParameters());
		rd.addDataSetDefinition("DVLD",
		    Mapped.mapStraightThrough(documentedVlDatasetDefinition.constructDocumentedVLDatasetDefinition()));
		rd.setBaseCohortDefinition(SSEMRReportUtils.map(baseCohortQueries.getPatientsWhoHaveDocumentedVL(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "documented_vl_register.xls",
			    "Report for patients with documented VL tests", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:4,dataset:DVLD");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
