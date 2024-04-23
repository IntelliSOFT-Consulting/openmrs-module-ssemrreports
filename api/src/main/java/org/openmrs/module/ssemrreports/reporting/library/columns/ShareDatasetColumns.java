package org.openmrs.module.ssemrreports.reporting.library.columns;

import org.openmrs.module.ssemrreports.reporting.library.datasets.SSEMRBaseDataSet;

import java.util.Arrays;
import java.util.List;

public class ShareDatasetColumns {
	
	public static List<SSEMRBaseDataSet.ColumnParameters> getMerGenderAndAgeColumns() {
		SSEMRBaseDataSet.ColumnParameters unknownM = new SSEMRBaseDataSet.ColumnParameters("unknownM", "Unknown age male",
		        "gender=M|age=UK", "UNKM");
		SSEMRBaseDataSet.ColumnParameters under1M = new SSEMRBaseDataSet.ColumnParameters("under1M", "under 1 year male",
		        "gender=M|age=<1", "M1");
		SSEMRBaseDataSet.ColumnParameters oneTo4M = new SSEMRBaseDataSet.ColumnParameters("oneTo4M", "1 - 4 years male",
		        "gender=M|age=1-4", "M2");
		SSEMRBaseDataSet.ColumnParameters fiveTo9M = new SSEMRBaseDataSet.ColumnParameters("fiveTo9M", "5 - 9 years male",
		        "gender=M|age=5-9", "M3");
		SSEMRBaseDataSet.ColumnParameters tenTo14M = new SSEMRBaseDataSet.ColumnParameters("tenTo14M", "10 - 14 male",
		        "gender=M|age=10-14", "M4");
		SSEMRBaseDataSet.ColumnParameters fifteenTo19M = new SSEMRBaseDataSet.ColumnParameters("fifteenTo19M",
		        "15 - 19 male", "gender=M|age=15-19", "M5");
		SSEMRBaseDataSet.ColumnParameters twentyTo24M = new SSEMRBaseDataSet.ColumnParameters("twentyTo24M", "20 - 24 male",
		        "gender=M|age=20-24", "M6");
		SSEMRBaseDataSet.ColumnParameters twenty5To29M = new SSEMRBaseDataSet.ColumnParameters("twenty4To29M",
		        "25 - 29 male", "gender=M|age=25-29", "M7");
		SSEMRBaseDataSet.ColumnParameters thirtyTo34M = new SSEMRBaseDataSet.ColumnParameters("thirtyTo34M", "30 - 34 male",
		        "gender=M|age=30-34", "M8");
		SSEMRBaseDataSet.ColumnParameters thirty5To39M = new SSEMRBaseDataSet.ColumnParameters("thirty5To39M",
		        "35 - 39 male", "gender=M|age=35-39", "M9");
		SSEMRBaseDataSet.ColumnParameters fortyTo44M = new SSEMRBaseDataSet.ColumnParameters("fortyTo44M", "40 - 44 male",
		        "gender=M|age=40-44", "M10");
		SSEMRBaseDataSet.ColumnParameters forty5To49M = new SSEMRBaseDataSet.ColumnParameters("forty5To49M", "45 - 49 male",
		        "gender=M|age=45-49", "M11");
		// 50-54, 55-59, 60-64, 65+ male
		SSEMRBaseDataSet.ColumnParameters fiftyTo54M = new SSEMRBaseDataSet.ColumnParameters("fiftyTo54M", "50 - 54 male",
		        "gender=M|age=50-54", "M12");
		SSEMRBaseDataSet.ColumnParameters fifty5To59M = new SSEMRBaseDataSet.ColumnParameters("fifty5To59M", "55 - 59 male",
		        "gender=M|age=55-59", "M13");
		SSEMRBaseDataSet.ColumnParameters sixtyTo64M = new SSEMRBaseDataSet.ColumnParameters("sixtyTo64M", "60 - 64 male",
		        "gender=M|age=60-64", "M14");
		SSEMRBaseDataSet.ColumnParameters above65M = new SSEMRBaseDataSet.ColumnParameters("above65M", "65+ male",
		        "gender=M|age=65+", "M15");
		
		SSEMRBaseDataSet.ColumnParameters unknownF = new SSEMRBaseDataSet.ColumnParameters("unknownF", "Unknown age female",
		        "gender=F|age=UK", "UNKF");
		SSEMRBaseDataSet.ColumnParameters under1F = new SSEMRBaseDataSet.ColumnParameters("under1F", "under 1 year female",
		        "gender=F|age=<1", "F1");
		SSEMRBaseDataSet.ColumnParameters oneTo4F = new SSEMRBaseDataSet.ColumnParameters("oneTo4F", "1 - 4 years female",
		        "gender=F|age=1-4", "F2");
		SSEMRBaseDataSet.ColumnParameters fiveTo9F = new SSEMRBaseDataSet.ColumnParameters("fiveTo9F", "5 - 9 years female",
		        "gender=F|age=5-9", "F3");
		SSEMRBaseDataSet.ColumnParameters tenTo14F = new SSEMRBaseDataSet.ColumnParameters("tenTo14F", "10 - 14 female",
		        "gender=F|age=10-14", "F4");
		SSEMRBaseDataSet.ColumnParameters fifteenTo19F = new SSEMRBaseDataSet.ColumnParameters("fifteenTo19F",
		        "15 - 19 female", "gender=F|age=15-19", "F5");
		SSEMRBaseDataSet.ColumnParameters twentyTo24F = new SSEMRBaseDataSet.ColumnParameters("twentyTo24F",
		        "20 - 24 female", "gender=F|age=20-24", "F6");
		SSEMRBaseDataSet.ColumnParameters twenty5To29F = new SSEMRBaseDataSet.ColumnParameters("twenty4To29F",
		        "25 - 29 female", "gender=F|age=25-29", "F7");
		SSEMRBaseDataSet.ColumnParameters thirtyTo34F = new SSEMRBaseDataSet.ColumnParameters("thirtyTo34F",
		        "30 - 34 female", "gender=F|age=30-34", "F8");
		SSEMRBaseDataSet.ColumnParameters thirty5To39F = new SSEMRBaseDataSet.ColumnParameters("thirty5To39F",
		        "35 - 39 female", "gender=F|age=35-39", "F9");
		SSEMRBaseDataSet.ColumnParameters fortyTo44F = new SSEMRBaseDataSet.ColumnParameters("fortyTo44F", "40 - 44 female",
		        "gender=F|age=40-44", "F10");
		SSEMRBaseDataSet.ColumnParameters forty5To49F = new SSEMRBaseDataSet.ColumnParameters("forty5To49F",
		        "45 - 49 female", "gender=F|age=45-49", "F11");
		// 50-54, 55-59, 60-64, 65+ female
		SSEMRBaseDataSet.ColumnParameters fiftyTo54F = new SSEMRBaseDataSet.ColumnParameters("fiftyTo54F", "50 - 54 female",
		        "gender=F|age=50-54", "F12");
		SSEMRBaseDataSet.ColumnParameters fifty5To59F = new SSEMRBaseDataSet.ColumnParameters("fifty5To59F",
		        "55 - 59 female", "gender=F|age=55-59", "F13");
		SSEMRBaseDataSet.ColumnParameters sixtyTo64F = new SSEMRBaseDataSet.ColumnParameters("sixtyTo64F", "60 - 64 female",
		        "gender=F|age=60-64", "F14");
		SSEMRBaseDataSet.ColumnParameters above65F = new SSEMRBaseDataSet.ColumnParameters("above65F", "65+ female",
		        "gender=F|age=65+", "F15");
		SSEMRBaseDataSet.ColumnParameters unknown = new SSEMRBaseDataSet.ColumnParameters("unknown", "Unknown age",
		        "age=UK", "UNK");
		SSEMRBaseDataSet.ColumnParameters total = new SSEMRBaseDataSet.ColumnParameters("total", "All total", "", "TALL");
		
		return Arrays.asList(unknownM, under1M, oneTo4M, fiveTo9M, tenTo14M, fifteenTo19M, twentyTo24M, twenty5To29M,
		    thirtyTo34M, thirty5To39M, fortyTo44M, forty5To49M, fiftyTo54M, fifty5To59M, sixtyTo64M, above65M, unknownF,
		    under1F, oneTo4F, fiveTo9F, tenTo14F, fifteenTo19F, twentyTo24F, twenty5To29F, thirtyTo34F, thirty5To39F,
		    fortyTo44F, forty5To49F, fiftyTo54F, fifty5To59F, sixtyTo64F, above65F, unknown, total);
	}
	
	public static List<SSEMRBaseDataSet.ColumnParameters> getDispensationColumnsGenderAndAge() {
		SSEMRBaseDataSet.ColumnParameters under15M = new SSEMRBaseDataSet.ColumnParameters("under15M",
		        "under 15 years male", "gender=M|age=<15", "01");
		
		SSEMRBaseDataSet.ColumnParameters plus15M = new SSEMRBaseDataSet.ColumnParameters("plus15M",
		        "more than 15 years male", "gender=M|age=15+", "02");
		
		SSEMRBaseDataSet.ColumnParameters unkM = new SSEMRBaseDataSet.ColumnParameters("unkM3", "unknown age male",
		        "gender=M|age=UK", "03");
		
		SSEMRBaseDataSet.ColumnParameters under15F = new SSEMRBaseDataSet.ColumnParameters("under15F",
		        "under 15 years female", "gender=F|age=<15", "04");
		SSEMRBaseDataSet.ColumnParameters plus15F = new SSEMRBaseDataSet.ColumnParameters("plus15F",
		        "more than 15 years female", "gender=F|age=15+", "05");
		
		SSEMRBaseDataSet.ColumnParameters unkF = new SSEMRBaseDataSet.ColumnParameters("unkF3", "unknown age female",
		        "gender=F|age=UK", "06");
		
		SSEMRBaseDataSet.ColumnParameters all3 = new SSEMRBaseDataSet.ColumnParameters("all3", "All dispensation", "", "07");
		
		return Arrays.asList(under15M, plus15M, unkM, under15F, plus15F, unkF, all3);
	}
	
}
