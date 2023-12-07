package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openmrs.LocationAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ArtDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.datasets.DistrictDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.datasets.SSEMRBaseDataSet;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupArtMonthlyReport extends SSEMRDataExportManager {
	
	private final DistrictDatasetDefinition districtDatasetDefinition;
	
	private final ArtDatasetDefinition artDatasetDefinition;
	
	private final BaseCohortQueries baseCohortQueries;
	
	@Autowired
	public SetupArtMonthlyReport(DistrictDatasetDefinition districtDatasetDefinition,
	    ArtDatasetDefinition artDatasetDefinition, BaseCohortQueries baseCohortQueries) {
		this.districtDatasetDefinition = districtDatasetDefinition;
		this.artDatasetDefinition = artDatasetDefinition;
		this.baseCohortQueries = baseCohortQueries;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "0d2927d8-68c7-11ed-9948-276d11812449";
	}
	
	@Override
	public String getUuid() {
		return "fe2cc6a4-68c6-11ed-b5b2-bb32e6a06527";
	}
	
	@Override
	public String getName() {
		return "ART Monthly Report";
	}
	
	@Override
	public String getDescription() {
		return "ART Monthly Report Implementation";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		// LocationAttributeType mflCodeAttributeType = Context.getLocationService().getLocationAttributeTypeByUuid(
		//    "8a845a89-6aa5-4111-81d3-0af31c45c002");
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		//rd.addParameters(districtDatasetDefinition.getParameters());
		//rd.addDataSetDefinition("DT", Mapped.mapStraightThrough(districtDatasetDefinition
		//        .getAddressDataset(mflCodeAttributeType.getLocationAttributeTypeId())));
		rd.addDataSetDefinition("CummAndNewOnArt", Mapped.mapStraightThrough(artDatasetDefinition.getArtDataset()));
		rd.addDataSetDefinition("ageAtStart",
		    Mapped.mapStraightThrough(artDatasetDefinition.getTxCurrForAgeAtEnrolmentDataset()));
		rd.addDataSetDefinition("currentOnArtByRegimen",
		    Mapped.mapStraightThrough(artDatasetDefinition.getTxCurrByRegimenDataset()));
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
			reportDesign = createXlsReportDesign(reportDefinition, "art_monthly.xls", "ART Monthly Report",
			    getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
