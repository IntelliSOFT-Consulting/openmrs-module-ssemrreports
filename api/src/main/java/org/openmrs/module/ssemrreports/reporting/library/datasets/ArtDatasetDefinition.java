package org.openmrs.module.ssemrreports.reporting.library.datasets;

import static org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils.map;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.ArtCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.CommonCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArtDatasetDefinition extends SSEMRBaseDataSet {
	
	private final SSEMRCommonDimension dimension;
	
	private final SSEMRGeneralIndicator indicator;
	
	private final ArtCohortQueries artCohortQueries;
	
	private final CommonCohortQueries commonCohortQueries;
	
	SSEMRBaseDataSet.ColumnParameters subTotalMales = new SSEMRBaseDataSet.ColumnParameters(null, "Male", "gender=M", "");
	
	SSEMRBaseDataSet.ColumnParameters subTotalFemales = new SSEMRBaseDataSet.ColumnParameters(null, "Male", "gender=F", "");
	
	SSEMRBaseDataSet.ColumnParameters maleInfants = new SSEMRBaseDataSet.ColumnParameters(null, "<1, Male",
	        "gender=M|age=<1", "");
	
	SSEMRBaseDataSet.ColumnParameters femaleInfants = new SSEMRBaseDataSet.ColumnParameters(null, "<1, Female",
	        "gender=F|age=<1", "");
	
	SSEMRBaseDataSet.ColumnParameters male_1_to_4 = new SSEMRBaseDataSet.ColumnParameters(null, "1-4, Male",
	        "gender=M|age=1-4", "");
	
	SSEMRBaseDataSet.ColumnParameters female_1_to_4 = new SSEMRBaseDataSet.ColumnParameters(null, "1-4, Female",
	        "gender=F|age=1-4", "");
	
	SSEMRBaseDataSet.ColumnParameters male_5_to_9 = new SSEMRBaseDataSet.ColumnParameters(null, "5-9, Male",
	        "gender=M|age=5-9", "");
	
	SSEMRBaseDataSet.ColumnParameters female_5_to_9 = new SSEMRBaseDataSet.ColumnParameters(null, "5-9, Female",
	        "gender=F|age=5-9", "");
	
	SSEMRBaseDataSet.ColumnParameters male_10_to_14 = new SSEMRBaseDataSet.ColumnParameters(null, "10-14, Male",
	        "gender=M|age=10-14", "");
	
	SSEMRBaseDataSet.ColumnParameters female_10_to_14 = new SSEMRBaseDataSet.ColumnParameters(null, "10-14, Female",
	        "gender=F|age=10-14", "");
	
	SSEMRBaseDataSet.ColumnParameters male_15_to_19 = new SSEMRBaseDataSet.ColumnParameters(null, "15-19, Male",
	        "gender=M|age=15-19", "");
	
	SSEMRBaseDataSet.ColumnParameters female_15_to_19 = new SSEMRBaseDataSet.ColumnParameters(null, "15-19, Female",
	        "gender=F|age=15-19", "");
	
	SSEMRBaseDataSet.ColumnParameters male_20_to_24 = new SSEMRBaseDataSet.ColumnParameters(null, "20-24, Male",
	        "gender=M|age=20-24", "");
	
	SSEMRBaseDataSet.ColumnParameters female_20_to_24 = new SSEMRBaseDataSet.ColumnParameters(null, "20-24, Female",
	        "gender=F|age=20-24", "");
	
	SSEMRBaseDataSet.ColumnParameters male_25_to_29 = new SSEMRBaseDataSet.ColumnParameters(null, "25-29, Male",
	        "gender=M|age=25-29", "");
	
	SSEMRBaseDataSet.ColumnParameters female_25_to_29 = new SSEMRBaseDataSet.ColumnParameters(null, "25-29, Female",
	        "gender=F|age=25-29", "");
	
	SSEMRBaseDataSet.ColumnParameters male_30_to_34 = new SSEMRBaseDataSet.ColumnParameters(null, "30-34, Male",
	        "gender=M|age=30-34", "");
	
	SSEMRBaseDataSet.ColumnParameters female_30_to_34 = new SSEMRBaseDataSet.ColumnParameters(null, "30-34, Female",
	        "gender=F|age=30-34", "");
	
	SSEMRBaseDataSet.ColumnParameters male_35_to_39 = new SSEMRBaseDataSet.ColumnParameters(null, "35-39, Male",
	        "gender=M|age=35-39", "");
	
	SSEMRBaseDataSet.ColumnParameters female_35_to_39 = new SSEMRBaseDataSet.ColumnParameters(null, "35-39, Female",
	        "gender=F|age=35-39", "");
	
	SSEMRBaseDataSet.ColumnParameters male_40_to_44 = new SSEMRBaseDataSet.ColumnParameters(null, "40-44, Male",
	        "gender=M|age=40-44", "");
	
	SSEMRBaseDataSet.ColumnParameters female_40_to_44 = new SSEMRBaseDataSet.ColumnParameters(null, "40-44, Female",
	        "gender=F|age=40-44", "");
	
	SSEMRBaseDataSet.ColumnParameters male_45_to_49 = new SSEMRBaseDataSet.ColumnParameters(null, "45-49, Male",
	        "gender=M|age=45-49", "");
	
	SSEMRBaseDataSet.ColumnParameters female_45_to_49 = new SSEMRBaseDataSet.ColumnParameters(null, "45-49, Female",
	        "gender=F|age=45-49", "");
	
	SSEMRBaseDataSet.ColumnParameters male_50_plus = new SSEMRBaseDataSet.ColumnParameters(null, "50+, Male",
	        "gender=M|age=50+", "");
	
	SSEMRBaseDataSet.ColumnParameters female_50_plus = new SSEMRBaseDataSet.ColumnParameters(null, "50+, Female",
	        "gender=F|age=50+", "");
	
	SSEMRBaseDataSet.ColumnParameters colTotal = new SSEMRBaseDataSet.ColumnParameters(null, "Total", null, "");
	
	List<ColumnParameters> allAgeDisaggregation = Arrays.asList(femaleInfants, maleInfants, female_1_to_4, male_1_to_4,
	    female_5_to_9, male_5_to_9, female_10_to_14, male_10_to_14, female_15_to_19, male_15_to_19, female_20_to_24,
	    male_20_to_24, female_25_to_29, male_25_to_29, female_30_to_34, male_30_to_34, female_35_to_39, male_35_to_39,
	    female_40_to_44, male_40_to_44, female_45_to_49, male_45_to_49, female_50_plus, male_50_plus, subTotalFemales,
	    subTotalMales, colTotal);
	
	List<ColumnParameters> pbfDisaggregation = Arrays.asList(female_10_to_14, female_15_to_19, female_20_to_24,
	    female_25_to_29, female_30_to_34, female_35_to_39, female_40_to_44, female_45_to_49, female_50_plus,
	    subTotalFemales, subTotalMales, colTotal);
	
	@Autowired
	public ArtDatasetDefinition(SSEMRCommonDimension dimension, SSEMRGeneralIndicator indicator,
	    ArtCohortQueries artCohortQueries, CommonCohortQueries commonCohortQueries) {
		this.dimension = dimension;
		this.indicator = indicator;
		this.artCohortQueries = artCohortQueries;
		this.commonCohortQueries = commonCohortQueries;
	}
	
	public DataSetDefinition getArtDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate}";
		String mappings1 = "endDate=${endDate+23h},location=${location}";
		dsd.setName("CummAndNewOnArt");
		dsd.setDescription("ART dataset");
		dsd.addParameters(getParameters());
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		addRow(
		    dsd,
		    "1",
		    "Cumulative no ever started on ART at this facility",
		    map(indicator.getIndicator("Cumulative no ever started on ART at this facility",
		        map(artCohortQueries.getCumulativeEverOnARTAtThisFacilityCohortDefinition(), mappings)), mappings),
		    allAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
		        "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));
		
		addRow(
		    dsd,
		    "2",
		    "New persons started on ART during the reporting period",
		    map(indicator.getIndicator("New persons started on ART during the reporting period",
		        map(artCohortQueries.getNewOnARTCohortDefinition(), mappings)), mappings), allAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
		        "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));
		
		addRow(
		    dsd,
		    "3",
		    "Number of pregnant women among the new cases during the reporting period",
		    map(indicator.getIndicator("Number of pregnant women among the new cases during the reporting period",
		        map(artCohortQueries.getNewOnArtAndPregnant(), mappings)), mappings), pbfDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
		
		addRow(
		    dsd,
		    "4",
		    "Number of breastfeeding women among the new cases during the reporting period",
		    map(indicator.getIndicator("Number of breastfeeding women among the new cases during the reporting period",
		        map(artCohortQueries.getNewOnArtAndBreastfeeding(), mappings)), mappings), pbfDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
		
		addRow(
		    dsd,
		    "5",
		    "Started on TLD among new cases during the reporting period",
		    map(indicator.getIndicator("Started on TLD among new cases during the reporting period",
		        map(artCohortQueries.getNewOnTLDRegimen(), mappings)), mappings), allAgeDisaggregation, Arrays.asList("01",
		        "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
		        "20", "21", "22", "23", "24", "25", "26", "27"));
		
		addRow(
		    dsd,
		    "6",
		    "Started on other DTG based regimen among new cases during the reporting period",
		    map(indicator.getIndicator("Started on other DTG based regimen among new cases during the reporting period",
		        map(artCohortQueries.getNewOnOtherDTGBasedRegimen(), mappings)), mappings), allAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
		        "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));
		
		addRow(
		    dsd,
		    "7",
		    "Cumulative no ever started on ART at this facility",
		    map(indicator.getIndicator("Cumulative no ever started on ART at this facility",
		        map(artCohortQueries.getCumulativeEverOnARTAtThisFacilityAtEndOfReportingCohortDefinition(), mappings)),
		        mappings), allAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
		        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"));
		return dsd;
	}
}
