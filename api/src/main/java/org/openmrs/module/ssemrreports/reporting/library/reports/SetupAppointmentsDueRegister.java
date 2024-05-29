package org.openmrs.module.ssemrreports.reporting.library.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.AppointmentsDueDatasetDefinition;
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
public class SetupAppointmentsDueRegister extends SsemrDataExportManager {
	
	private final AppointmentsDueDatasetDefinition appointmentsDueDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupAppointmentsDueRegister(AppointmentsDueDatasetDefinition appointmentsDueDatasetDefinition,
	    BaseCohortQueries baseCohortQueries) {
		this.appointmentsDueDatasetDefinition = appointmentsDueDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.APPOINTMENTS_DUE_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.APPOINTMENTS_DUE_LIST_UUID;
	}
	
	@Override
	public String getName() {
		return "Line list for clients due for appointment and missed appointment";
	}
	
	@Override
	public String getDescription() {
		return "Line list for clients due for appointment and missed appointment";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(appointmentsDueDatasetDefinition.getParameters());
		rd.addDataSetDefinition("APPDUE",
		    Mapped.mapStraightThrough(appointmentsDueDatasetDefinition.constructAppointmentsDueDatasetDefinition()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(baseCohortQueries.getPatientsWithTodaysAppointments(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "appointments_due_register.xls",
			    "Report for appointments due", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:2,dataset:APPDUE");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
