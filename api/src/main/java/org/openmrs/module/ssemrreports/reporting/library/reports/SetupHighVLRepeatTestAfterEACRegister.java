package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.HighVLRepeatTestAfterEACDatasetDefinition;
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
public class SetupHighVLRepeatTestAfterEACRegister extends SSEMRDataExportManager {
	
	private final HighVLRepeatTestAfterEACDatasetDefinition highVLRepeatTestAfterEACDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupHighVLRepeatTestAfterEACRegister(
	    HighVLRepeatTestAfterEACDatasetDefinition highVLRepeatTestAfterEACDatasetDefinition,
	    BaseCohortQueries baseCohortQueries) {
		this.highVLRepeatTestAfterEACDatasetDefinition = highVLRepeatTestAfterEACDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.HIGH_VL_AND_REPEAT_TEST_AFTER_EAC_LIST_UUID_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.HIGH_VL_AND_REPEAT_TEST_AFTER_EAC_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Patient high VL who received repeat test after EAC list on Date";
	}
	
	@Override
	public String getDescription() {
		return "Patients with high VL who received repeat test after EAC";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(highVLRepeatTestAfterEACDatasetDefinition.getParameters());
		rd.addDataSetDefinition("HVLEACREPEATTEST", Mapped.mapStraightThrough(highVLRepeatTestAfterEACDatasetDefinition
		        .constructHighVLRepeatTestAfterEACDatasetDefinition()));
		rd.setBaseCohortDefinition(SSEMRReportUtils.map(baseCohortQueries.getPatientsWhoHaveHVLAndRepeatTestAfterEAC(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "hvlAfterEACRegister.xls",
			    "Report for listing High VL who received repeat test after EAC", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:3,dataset:HVLEACREPEATTEST");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
