package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.LTFUDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupLtfuRegister extends SsemrDataExportManager {
	
	private final LTFUDatasetDefinition ltfuDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupLtfuRegister(LTFUDatasetDefinition ltfuDatasetDefinition, BaseCohortQueries baseCohortQueries) {
		this.ltfuDatasetDefinition = ltfuDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.LTFU_LIST_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.LTFU_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Report for Lost to follow up";
	}
	
	@Override
	public String getDescription() {
		return "Patient who missed appointment for over 90 days";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(ltfuDatasetDefinition.getParameters());
		rd.addDataSetDefinition("LTFUR",
		    Mapped.mapStraightThrough(ltfuDatasetDefinition.constructLtfuAppointmentRegisterDefinition()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(baseCohortQueries.getPatientsWhoMissedAppointmentByDays(90),
		    "startDate=${startDate},endDate=${endDate+23h},location=${location}"));
		return rd;
	}
	
	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		ReportDesign reportDesign;
		try {
			reportDesign = createXlsReportDesign(reportDefinition, "ltfu_register.xls", "Report for Lost to follow up",
			    getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:4,dataset:LTFUR");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Collections.singletonList(reportDesign);
	}
}
