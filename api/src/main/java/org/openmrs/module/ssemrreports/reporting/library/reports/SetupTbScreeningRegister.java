package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.TbScreeningDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupTbScreeningRegister extends SsemrDataExportManager {
	
	private final TbScreeningDatasetDefinition tbScreeningDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupTbScreeningRegister(BaseCohortQueries baseCohortQueries,
	    TbScreeningDatasetDefinition tbScreeningDatasetDefinition) {
		this.baseCohortQueries = baseCohortQueries;
		this.tbScreeningDatasetDefinition = tbScreeningDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "02B0E667-5845-4902-B113-17A9FCEE7A9F";
	}
	
	@Override
	public String getUuid() {
		return "F4987BA7-FF1B-4E43-88A3-6500E0C808D1";
	}
	
	@Override
	public String getName() {
		return "Line list for TB Screening";
	}
	
	@Override
	public String getDescription() {
		return "Line list for TB Screening";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(tbScreeningDatasetDefinition.getParameters());
		rd.addDataSetDefinition("TBS",
		    Mapped.mapStraightThrough(tbScreeningDatasetDefinition.constructTbDatasetDefinition()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(baseCohortQueries.getPatientsScreenedForTbTreatment(),
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
			reportDesign = createXlsReportDesign(reportDefinition, "tb_screening_register.xls",
			    "Line list for TB Screening", getExcelDesignUuid(), null);
			Properties props = new Properties();
			props.put("repeatingSections", "sheet:1,row:4,dataset:TBS");
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException("Error creating report design", e);
		}
		
		return Arrays.asList(reportDesign);
	}
}
