package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.HighVLAndEACDatasetDefinition;
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
public class SetupHighVLAndEACRegister extends SSEMRDataExportManager {
	
	private final HighVLAndEACDatasetDefinition highVLAndEACDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupHighVLAndEACRegister(HighVLAndEACDatasetDefinition highVLAndEACDatasetDefinition,
	    BaseCohortQueries baseCohortQueries) {
		this.highVLAndEACDatasetDefinition = highVLAndEACDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.HIGH_VL_AND_EAC_LIST_UUID_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.HIGH_VL_AND_EAC_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Patient high VL and EAC list on Date";
	}
	
	@Override
	public String getDescription() {
		return "Patients with high VL and EAC";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(highVLAndEACDatasetDefinition.getParameters());
		rd.addDataSetDefinition("HVLEAC",
		    Mapped.mapStraightThrough(highVLAndEACDatasetDefinition.constructHighVLAndEACDatasetDefinition()));
		rd.setBaseCohortDefinition(SSEMRReportUtils.map(baseCohortQueries.getPatientsWhoHaveHighVLAndEAC(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "hvlAndEACRegister.xls",
			    "Report for listing High VL and EAC clients", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:4,dataset:HVLEAC");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
