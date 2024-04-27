package org.openmrs.module.ssemrreports.reporting.library.columns;

import org.openmrs.module.ssemrreports.reporting.library.datasets.SsemrBaseDataSet;

import java.util.Arrays;
import java.util.List;

public class ShareDatasetColumns {
	
	public static List<SsemrBaseDataSet.ColumnParameters> getMerGenderAndAgeColumns() {
		SsemrBaseDataSet.ColumnParameters unknownM = new SsemrBaseDataSet.ColumnParameters("unknownM", "Unknown age male",
		        "gender=M|age=UK", "UNKM");
		SsemrBaseDataSet.ColumnParameters under1M = new SsemrBaseDataSet.ColumnParameters("under1M", "under 1 year male",
		        "gender=M|age=<1", "M1");
		SsemrBaseDataSet.ColumnParameters oneTo4M = new SsemrBaseDataSet.ColumnParameters("oneTo4M", "1 - 4 years male",
		        "gender=M|age=1-4", "M2");
		SsemrBaseDataSet.ColumnParameters fiveTo9M = new SsemrBaseDataSet.ColumnParameters("fiveTo9M", "5 - 9 years male",
		        "gender=M|age=5-9", "M3");
		SsemrBaseDataSet.ColumnParameters tenTo14M = new SsemrBaseDataSet.ColumnParameters("tenTo14M", "10 - 14 male",
		        "gender=M|age=10-14", "M4");
		SsemrBaseDataSet.ColumnParameters fifteenTo19M = new SsemrBaseDataSet.ColumnParameters("fifteenTo19M",
		        "15 - 19 male", "gender=M|age=15-19", "M5");
		SsemrBaseDataSet.ColumnParameters twentyTo24M = new SsemrBaseDataSet.ColumnParameters("twentyTo24M", "20 - 24 male",
		        "gender=M|age=20-24", "M6");
		SsemrBaseDataSet.ColumnParameters twenty5To29M = new SsemrBaseDataSet.ColumnParameters("twenty4To29M",
		        "25 - 29 male", "gender=M|age=25-29", "M7");
		SsemrBaseDataSet.ColumnParameters thirtyTo34M = new SsemrBaseDataSet.ColumnParameters("thirtyTo34M", "30 - 34 male",
		        "gender=M|age=30-34", "M8");
		SsemrBaseDataSet.ColumnParameters thirty5To39M = new SsemrBaseDataSet.ColumnParameters("thirty5To39M",
		        "35 - 39 male", "gender=M|age=35-39", "M9");
		SsemrBaseDataSet.ColumnParameters fortyTo44M = new SsemrBaseDataSet.ColumnParameters("fortyTo44M", "40 - 44 male",
		        "gender=M|age=40-44", "M10");
		SsemrBaseDataSet.ColumnParameters forty5To49M = new SsemrBaseDataSet.ColumnParameters("forty5To49M", "45 - 49 male",
		        "gender=M|age=45-49", "M11");
		// 50-54, 55-59, 60-64, 65+ male
		SsemrBaseDataSet.ColumnParameters fiftyTo54M = new SsemrBaseDataSet.ColumnParameters("fiftyTo54M", "50 - 54 male",
		        "gender=M|age=50-54", "M12");
		SsemrBaseDataSet.ColumnParameters fifty5To59M = new SsemrBaseDataSet.ColumnParameters("fifty5To59M", "55 - 59 male",
		        "gender=M|age=55-59", "M13");
		SsemrBaseDataSet.ColumnParameters sixtyTo64M = new SsemrBaseDataSet.ColumnParameters("sixtyTo64M", "60 - 64 male",
		        "gender=M|age=60-64", "M14");
		SsemrBaseDataSet.ColumnParameters above65M = new SsemrBaseDataSet.ColumnParameters("above65M", "65+ male",
		        "gender=M|age=65+", "M15");
		
		SsemrBaseDataSet.ColumnParameters unknownF = new SsemrBaseDataSet.ColumnParameters("unknownF", "Unknown age female",
		        "gender=F|age=UK", "UNKF");
		SsemrBaseDataSet.ColumnParameters under1F = new SsemrBaseDataSet.ColumnParameters("under1F", "under 1 year female",
		        "gender=F|age=<1", "F1");
		SsemrBaseDataSet.ColumnParameters oneTo4F = new SsemrBaseDataSet.ColumnParameters("oneTo4F", "1 - 4 years female",
		        "gender=F|age=1-4", "F2");
		SsemrBaseDataSet.ColumnParameters fiveTo9F = new SsemrBaseDataSet.ColumnParameters("fiveTo9F", "5 - 9 years female",
		        "gender=F|age=5-9", "F3");
		SsemrBaseDataSet.ColumnParameters tenTo14F = new SsemrBaseDataSet.ColumnParameters("tenTo14F", "10 - 14 female",
		        "gender=F|age=10-14", "F4");
		SsemrBaseDataSet.ColumnParameters fifteenTo19F = new SsemrBaseDataSet.ColumnParameters("fifteenTo19F",
		        "15 - 19 female", "gender=F|age=15-19", "F5");
		SsemrBaseDataSet.ColumnParameters twentyTo24F = new SsemrBaseDataSet.ColumnParameters("twentyTo24F",
		        "20 - 24 female", "gender=F|age=20-24", "F6");
		SsemrBaseDataSet.ColumnParameters twenty5To29F = new SsemrBaseDataSet.ColumnParameters("twenty4To29F",
		        "25 - 29 female", "gender=F|age=25-29", "F7");
		SsemrBaseDataSet.ColumnParameters thirtyTo34F = new SsemrBaseDataSet.ColumnParameters("thirtyTo34F",
		        "30 - 34 female", "gender=F|age=30-34", "F8");
		SsemrBaseDataSet.ColumnParameters thirty5To39F = new SsemrBaseDataSet.ColumnParameters("thirty5To39F",
		        "35 - 39 female", "gender=F|age=35-39", "F9");
		SsemrBaseDataSet.ColumnParameters fortyTo44F = new SsemrBaseDataSet.ColumnParameters("fortyTo44F", "40 - 44 female",
		        "gender=F|age=40-44", "F10");
		SsemrBaseDataSet.ColumnParameters forty5To49F = new SsemrBaseDataSet.ColumnParameters("forty5To49F",
		        "45 - 49 female", "gender=F|age=45-49", "F11");
		// 50-54, 55-59, 60-64, 65+ female
		SsemrBaseDataSet.ColumnParameters fiftyTo54F = new SsemrBaseDataSet.ColumnParameters("fiftyTo54F", "50 - 54 female",
		        "gender=F|age=50-54", "F12");
		SsemrBaseDataSet.ColumnParameters fifty5To59F = new SsemrBaseDataSet.ColumnParameters("fifty5To59F",
		        "55 - 59 female", "gender=F|age=55-59", "F13");
		SsemrBaseDataSet.ColumnParameters sixtyTo64F = new SsemrBaseDataSet.ColumnParameters("sixtyTo64F", "60 - 64 female",
		        "gender=F|age=60-64", "F14");
		SsemrBaseDataSet.ColumnParameters above65F = new SsemrBaseDataSet.ColumnParameters("above65F", "65+ female",
		        "gender=F|age=65+", "F15");
		SsemrBaseDataSet.ColumnParameters unknown = new SsemrBaseDataSet.ColumnParameters("unknown", "Unknown age",
		        "age=UK", "UNK");
		SsemrBaseDataSet.ColumnParameters total = new SsemrBaseDataSet.ColumnParameters("total", "All total", "", "TALL");
		
		return Arrays.asList(unknownM, under1M, oneTo4M, fiveTo9M, tenTo14M, fifteenTo19M, twentyTo24M, twenty5To29M,
		    thirtyTo34M, thirty5To39M, fortyTo44M, forty5To49M, fiftyTo54M, fifty5To59M, sixtyTo64M, above65M, unknownF,
		    under1F, oneTo4F, fiveTo9F, tenTo14F, fifteenTo19F, twentyTo24F, twenty5To29F, thirtyTo34F, thirty5To39F,
		    fortyTo44F, forty5To49F, fiftyTo54F, fifty5To59F, sixtyTo64F, above65F, unknown, total);
	}
	
	public static List<SsemrBaseDataSet.ColumnParameters> getDispensationColumnsGenderAndAge() {
		SsemrBaseDataSet.ColumnParameters under15M = new SsemrBaseDataSet.ColumnParameters("under15M",
		        "under 15 years male", "gender=M|age=<15", "01");
		
		SsemrBaseDataSet.ColumnParameters plus15M = new SsemrBaseDataSet.ColumnParameters("plus15M",
		        "more than 15 years male", "gender=M|age=15+", "02");
		
		SsemrBaseDataSet.ColumnParameters unkM = new SsemrBaseDataSet.ColumnParameters("unkM3", "unknown age male",
		        "gender=M|age=UK", "03");
		
		SsemrBaseDataSet.ColumnParameters under15F = new SsemrBaseDataSet.ColumnParameters("under15F",
		        "under 15 years female", "gender=F|age=<15", "04");
		SsemrBaseDataSet.ColumnParameters plus15F = new SsemrBaseDataSet.ColumnParameters("plus15F",
		        "more than 15 years female", "gender=F|age=15+", "05");
		
		SsemrBaseDataSet.ColumnParameters unkF = new SsemrBaseDataSet.ColumnParameters("unkF3", "unknown age female",
		        "gender=F|age=UK", "06");
		
		SsemrBaseDataSet.ColumnParameters all3 = new SsemrBaseDataSet.ColumnParameters("all3", "All dispensation", "", "07");
		
		return Arrays.asList(under15M, plus15M, unkM, under15F, plus15F, unkF, all3);
	}
	
}
