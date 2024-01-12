package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.ArtCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.MerCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils.map;

@Component
public class MerIndicatorsDatasetDefinition extends SSEMRBaseDataSet {
	
	private final SSEMRCommonDimension dimension;
	
	private final MerCohortQueries merCohortQueries;
	
	private final SSEMRGeneralIndicator indicator;
	
	@Autowired
	public MerIndicatorsDatasetDefinition(SSEMRCommonDimension dimension, MerCohortQueries merCohortQueries,
	    SSEMRGeneralIndicator indicator) {
		this.dimension = dimension;
		this.merCohortQueries = merCohortQueries;
		this.indicator = indicator;
	}
	
	public DataSetDefinition getTxCurrDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		dsd.addDimension("disp", map(dimension.getDispensingQuantityDimension(), mappings));
		dsd.setName("TxC");
		addRow(
		    dsd,
		    "TxCurr-ALL",
		    "Number of adults and children currently receiving antiretroviral therapy (ART)",
		    map(indicator.getIndicator("Number of adults and children currently receiving antiretroviral therapy (ART)",
		        map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings), getTxCurrColumns());
		
		addRow(
		    dsd,
		    "TxCurr-3-NMDD",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - <3 months of ARVs (not MMD) dispensed to client",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - <3 months of ARVs (not MMD) dispensed to client",
		                map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings),
		    getDispensationColumnsLessThan3Months());
		
		addRow(
		    dsd,
		    "TxCurr-3-5-MDD",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		                map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings), getDispensationColumns3To5Months());
		
		addRow(
		    dsd,
		    "TxCurr-6-M-MDD",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - 6 or more months of ARVs (MMD) dispensed to clients",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - 6 or more months of ARVs (MMD) dispensed to clients",
		                map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings),
		    getDispensationColumnsMoreThan6Months());
		return dsd;
	}
	
	public DataSetDefinition getTxNewDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxNew");
		return dsd;
	}
	
	public DataSetDefinition getTxMlDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxMl");
		return dsd;
	}
	
	public DataSetDefinition getTxRttDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxRtt");
		return dsd;
	}
	
	public DataSetDefinition getTxPvlsDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxPvls");
		return dsd;
	}
	
	private List<ColumnParameters> getTxCurrColumns() {
		ColumnParameters unknownM = new ColumnParameters("unknownM", "Unknown age male", "gender=M|age=UK", "UNKM");
		ColumnParameters under1M = new ColumnParameters("under1M", "under 1 year male", "gender=M|age=<1", "M1");
		ColumnParameters oneTo4M = new ColumnParameters("oneTo4M", "1 - 4 years male", "gender=M|age=1-4", "M2");
		ColumnParameters fiveTo9M = new ColumnParameters("fiveTo9M", "5 - 9 years male", "gender=M|age=5-9", "M3");
		ColumnParameters tenTo14M = new ColumnParameters("tenTo14M", "10 - 14 male", "gender=M|age=10-14", "M4");
		ColumnParameters fifteenTo19M = new ColumnParameters("fifteenTo19M", "15 - 19 male", "gender=M|age=15-19", "M5");
		ColumnParameters twentyTo24M = new ColumnParameters("twentyTo24M", "20 - 24 male", "gender=M|age=20-24", "M6");
		ColumnParameters twenty5To29M = new ColumnParameters("twenty4To29M", "25 - 29 male", "gender=M|age=25-29", "M7");
		ColumnParameters thirtyTo34M = new ColumnParameters("thirtyTo34M", "30 - 34 male", "gender=M|age=30-34", "M8");
		ColumnParameters thirty5To39M = new ColumnParameters("thirty5To39M", "35 - 39 male", "gender=M|age=35-39", "M9");
		ColumnParameters fortyTo44M = new ColumnParameters("fortyTo44M", "40 - 44 male", "gender=M|age=40-44", "M10");
		ColumnParameters forty5To49M = new ColumnParameters("forty5To49M", "45 - 49 male", "gender=M|age=45-49", "M11");
		// 50-54, 55-59, 60-64, 65+ male
		ColumnParameters fiftyTo54M = new ColumnParameters("fiftyTo54M", "50 - 54 male", "gender=M|age=50-54", "M12");
		ColumnParameters fifty5To59M = new ColumnParameters("fifty5To59M", "55 - 59 male", "gender=M|age=55-59", "M13");
		ColumnParameters sixtyTo64M = new ColumnParameters("sixtyTo64M", "60 - 64 male", "gender=M|age=60-64", "M14");
		ColumnParameters above65M = new ColumnParameters("above65M", "65+ male", "gender=M|age=65+", "M15");
		
		ColumnParameters unknownF = new ColumnParameters("unknownF", "Unknown age female", "gender=F|age=UK", "UNKF");
		ColumnParameters under1F = new ColumnParameters("under1F", "under 1 year female", "gender=F|age=<1", "F1");
		ColumnParameters oneTo4F = new ColumnParameters("oneTo4F", "1 - 4 years female", "gender=F|age=1-4", "F2");
		ColumnParameters fiveTo9F = new ColumnParameters("fiveTo9F", "5 - 9 years female", "gender=F|age=5-9", "F3");
		ColumnParameters tenTo14F = new ColumnParameters("tenTo14F", "10 - 14 female", "gender=F|age=10-14", "F4");
		ColumnParameters fifteenTo19F = new ColumnParameters("fifteenTo19F", "15 - 19 female", "gender=F|age=15-19", "F5");
		ColumnParameters twentyTo24F = new ColumnParameters("twentyTo24F", "20 - 24 female", "gender=F|age=20-24", "F6");
		ColumnParameters twenty5To29F = new ColumnParameters("twenty4To29F", "25 - 29 female", "gender=F|age=25-29", "F7");
		ColumnParameters thirtyTo34F = new ColumnParameters("thirtyTo34F", "30 - 34 female", "gender=F|age=30-34", "F8");
		ColumnParameters thirty5To39F = new ColumnParameters("thirty5To39F", "35 - 39 female", "gender=F|age=35-39", "F9");
		ColumnParameters fortyTo44F = new ColumnParameters("fortyTo44F", "40 - 44 female", "gender=F|age=40-44", "F10");
		ColumnParameters forty5To49F = new ColumnParameters("forty5To49F", "45 - 49 female", "gender=F|age=45-49", "F11");
		// 50-54, 55-59, 60-64, 65+ female
		ColumnParameters fiftyTo54F = new ColumnParameters("fiftyTo54F", "50 - 54 female", "gender=F|age=50-54", "F12");
		ColumnParameters fifty5To59F = new ColumnParameters("fifty5To59F", "55 - 59 female", "gender=F|age=55-59", "F13");
		ColumnParameters sixtyTo64F = new ColumnParameters("sixtyTo64F", "60 - 64 female", "gender=F|age=60-64", "F14");
		ColumnParameters above65F = new ColumnParameters("above65F", "65+ female", "gender=F|age=65+", "F15");
		ColumnParameters unknown = new ColumnParameters("unknown", "Unknown age", "age=UK", "UNK");
		
		return Arrays.asList(unknownM, under1M, oneTo4M, fiveTo9M, tenTo14M, fifteenTo19M, twentyTo24M, twenty5To29M,
		    thirtyTo34M, thirty5To39M, fortyTo44M, forty5To49M, fiftyTo54M, fifty5To59M, sixtyTo64M, above65M, unknownF,
		    under1F, oneTo4F, fiveTo9F, tenTo14F, fifteenTo19F, twentyTo24F, twenty5To29F, thirtyTo34F, thirty5To39F,
		    fortyTo44F, forty5To49F, fiftyTo54F, fifty5To59F, sixtyTo64F, above65F, unknown);
	}
	
	private List<ColumnParameters> getDispensationColumnsLessThan3Months() {
		ColumnParameters under15M = new ColumnParameters("under15M", "under 15 years male", "gender=M|age=<15|disp=<3m",
		        "under15M");
		ColumnParameters plus15M = new ColumnParameters("plus15M", "more than 15 years male", "gender=M|age=15+|disp=<3m",
		        "plus15M");
		ColumnParameters unkM = new ColumnParameters("unkM", "more than 15 years male", "gender=M|age=UK|disp=<3m", "unkM");
		ColumnParameters under15F = new ColumnParameters("under15F", "under 15 years male", "gender=F|age=<15|disp=<3m",
		        "under15F");
		ColumnParameters plus15F = new ColumnParameters("plus15F", "more than 15 years male", "gender=F|age=15+|disp=<3m",
		        "plus15F");
		ColumnParameters unkF = new ColumnParameters("unkF", "more than 15 years male", "gender=F|age=UK|disp=<3m", "unkF");
		
		return Arrays.asList(under15M, plus15M, unkM, under15F, plus15F, unkF);
	}
	
	private List<ColumnParameters> getDispensationColumns3To5Months() {
		ColumnParameters under15M = new ColumnParameters("under15M", "under 15 years male", "gender=M|age=<15|disp=3-5m",
		        "under15M");
		ColumnParameters plus15M = new ColumnParameters("plus15M", "more than 15 years male", "gender=M|age=15+|disp=3-5m",
		        "plus15M");
		ColumnParameters unkM = new ColumnParameters("unkM", "more than 15 years male", "gender=M|age=UK|disp=3-5m", "unkM");
		ColumnParameters under15F = new ColumnParameters("under15F", "under 15 years male", "gender=F|age=<15|disp=3-5m",
		        "under15F");
		ColumnParameters plus15F = new ColumnParameters("plus15F", "more than 15 years male", "gender=F|age=15+|disp=3-5m",
		        "plus15F");
		ColumnParameters unkF = new ColumnParameters("unkF", "more than 15 years male", "gender=F|age=UK|disp=3-5m", "unkF");
		
		return Arrays.asList(under15M, plus15M, unkM, under15F, plus15F, unkF);
	}
	
	private List<ColumnParameters> getDispensationColumnsMoreThan6Months() {
		ColumnParameters under15M = new ColumnParameters("under15M", "under 15 years male", "gender=M|age=<15|disp=>6m",
		        "under15M");
		ColumnParameters plus15M = new ColumnParameters("plus15M", "more than 15 years male", "gender=M|age=15+|disp=>6m",
		        "plus15M");
		ColumnParameters unkM = new ColumnParameters("unkM", "more than 15 years male", "gender=M|age=UK|disp=>6m", "unkM");
		ColumnParameters under15F = new ColumnParameters("under15F", "under 15 years male", "gender=F|age=<15|disp=>6m",
		        "under15F");
		ColumnParameters plus15F = new ColumnParameters("plus15F", "more than 15 years male", "gender=F|age=15+|disp=>6m",
		        "plus15F");
		ColumnParameters unkF = new ColumnParameters("unkF", "more than 15 years male", "gender=F|age=UK|disp=>6m", "unkF");
		
		return Arrays.asList(under15M, plus15M, unkM, under15F, plus15F, unkF);
	}
}
