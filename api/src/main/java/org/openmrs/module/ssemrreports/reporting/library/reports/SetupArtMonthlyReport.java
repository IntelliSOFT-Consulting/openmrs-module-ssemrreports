package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.ssemrreports.manager.SsemrDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.BaseCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.datasets.ArtDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.datasets.DistrictDatasetDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;

@Component
public class SetupArtMonthlyReport extends SsemrDataExportManager {
	
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
	
	/**
	 * Constructs and returns the report definition for the ART Monthly Report.
	 * <p>
	 * This method creates a new ReportDefinition and initializes it with the report's unique
	 * identifier, name, and description. It adds parameters from the ART dataset definition and
	 * registers datasets for cumulative and new ART patients, current ART patients by age and
	 * regimen, viral load data, and tuberculosis status.
	 * </p>
	 * 
	 * @return the configured ReportDefinition for the ART Monthly Report
	 */
	@Override
	public ReportDefinition constructReportDefinition() {
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(artDatasetDefinition.getParameters());
		rd.addDataSetDefinition("CummAndNewOnArt", SsemrReportUtils.map(artDatasetDefinition.getArtDataset(), mappings));
		rd.addDataSetDefinition("currentOnArtByAge",
		    Mapped.mapStraightThrough(artDatasetDefinition.getTxCurrForAgeAtEnrolmentDataset()));
		rd.addDataSetDefinition("currentOnArtByRegimen",
		    Mapped.mapStraightThrough(artDatasetDefinition.getTxCurrByRegimenDataset()));
		rd.addDataSetDefinition("viralLoad", Mapped.mapStraightThrough(artDatasetDefinition.getViralLoadDataset()));
		rd.setBaseCohortDefinition(SsemrReportUtils.map(baseCohortQueries.getAccurateClientsOnArtPerFacility(),
		    "endDate=${endDate},location=${location}"));
		
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
