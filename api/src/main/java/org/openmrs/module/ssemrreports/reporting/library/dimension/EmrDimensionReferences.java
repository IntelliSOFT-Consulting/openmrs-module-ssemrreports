package org.openmrs.module.ssemrreports.reporting.library.dimension;

import java.util.Arrays;
import java.util.List;

import org.openmrs.module.ssemrreports.reporting.library.datasets.SsemrBaseDataSet.ColumnParameters;

public class EmrDimensionReferences {
	
	public static List<ColumnParameters> getNotifiableDiseasesDimension() {
		ColumnParameters ndNaUnder1M = new ColumnParameters("ndNaUnder1M", "New under 1 year Male",
		        "gender=M|age=<1|visit=N", "01");
		ColumnParameters ndNa1To4M = new ColumnParameters("ndNa1To4M", "New 1-4 years Male", "gender=M|age=1-4|visit=N",
		        "02");
		ColumnParameters ndNa5PlusM = new ColumnParameters("ndNa5PlusM", "New 5 years and above Male",
		        "gender=M|age=5+|visit=N", "03");
		ColumnParameters ndNaTotalM = new ColumnParameters("ndNaTotalM", "New total male", "gender=M|visit=N", "04");
		
		ColumnParameters ndNaUnder1F = new ColumnParameters("ndNaUnder1F", "New under 1 year Female",
		        "gender=F|age=<1|visit=N", "05");
		ColumnParameters ndNa1To4F = new ColumnParameters("ndNa1To4F", "New 1-4 years Female", "gender=F|age=1-4|visit=N",
		        "06");
		ColumnParameters ndNa5PlusF = new ColumnParameters("ndNa5PlusF", "New 5 years and above Female",
		        "gender=F|age=5+|visit=N", "07");
		ColumnParameters ndNa5TotalF = new ColumnParameters("ndNa5TotalF", "New total Female", "gender=F|visit=N", "08");
		
		ColumnParameters ndRaUnder1M = new ColumnParameters("ndRaUnder1M", "Re-attendance under 1 year Male",
		        "gender=M|age=<1|visit=R", "09");
		ColumnParameters ndRa1To4M = new ColumnParameters("ndRa1To4M", "Re-attendance 1-4 years Male",
		        "gender=M|age=1-4|visit=R", "10");
		ColumnParameters ndRa5PlusM = new ColumnParameters("ndRa5PlusM", "Re-attendance 5 years and above Male",
		        "gender=M|age=5+|visit=R", "11");
		ColumnParameters ndRaTotalM = new ColumnParameters("ndRaTotalM", "Re-attendance total male", "gender=M|visit=R",
		        "12");
		
		ColumnParameters ndRaUnder1F = new ColumnParameters("ndRaUnder1F", "Re-attendance under 1 year Female",
		        "gender=F|age=<1|visit=R", "13");
		ColumnParameters ndRa1To4F = new ColumnParameters("ndRa1To4F", "Re-attendance 1-4 years Female",
		        "gender=F|age=1-4|visit=R", "14");
		ColumnParameters ndRa5PlusF = new ColumnParameters("ndRa5PlusF", "Re-attendance 5 years and above Female",
		        "gender=F|age=5+|visit=R", "15");
		ColumnParameters ndRa5TotalF = new ColumnParameters("ndRa5TotalF", "Re-attendance total Female", "gender=F|visit=R",
		        "16");
		
		return Arrays.asList(ndNaUnder1M, ndNa1To4M, ndNa5PlusM, ndNaTotalM, ndNaUnder1F, ndNa1To4F, ndNa5PlusF,
		    ndNa5TotalF, ndRaUnder1M, ndRa1To4M, ndRa5PlusM, ndRaTotalM, ndRaUnder1F, ndRa1To4F, ndRa5PlusF, ndRa5TotalF);
	}
	
	public static List<ColumnParameters> getSexuallyTransmittedInfectionsDimensions() {
		ColumnParameters stiNa0To5M = new ColumnParameters("stiNa0To5M", "New 0-5 years Male", "gender=M|age=0-5|visit=N",
		        "01");
		ColumnParameters stiNa6To14M = new ColumnParameters("stiNa6To14M", "New 6-14 years Male",
		        "gender=M|age=6-14|visit=N", "02");
		ColumnParameters stiNa15To29M = new ColumnParameters("stiNa15To29M", "New 15-29 years Male",
		        "gender=M|age=15-29|visit=N", "03");
		ColumnParameters stiNa30PlusM = new ColumnParameters("stiNa30PlusM", "New 30+ years Male",
		        "gender=M|age=30+|visit=N", "04");
		ColumnParameters stiNaTotalM = new ColumnParameters("stiNaTotalM", "New total male", "gender=M|visit=N", "05");
		
		ColumnParameters stiNa0To5F = new ColumnParameters("stiNa0To5F", "New 0-5 years Female", "gender=F|age=0-5|visit=N",
		        "06");
		ColumnParameters stiNa6To14F = new ColumnParameters("stiNa6To14F", "New 6-14 years Female",
		        "gender=F|age=6-14|visit=N", "07");
		ColumnParameters stiNa15To29F = new ColumnParameters("stiNa15To29F", "New 15-29 years Female",
		        "gender=F|age=15-29|visit=N", "08");
		ColumnParameters stiNa30PlusF = new ColumnParameters("stiNa30PlusF", "New 30+ years Female",
		        "gender=F|age=30+|visit=N", "09");
		ColumnParameters stiNa5TotalF = new ColumnParameters("stiNa5TotalF", "New total Female", "gender=F|visit=N", "10");
		
		ColumnParameters stiRa0To14M = new ColumnParameters("stiRa0To14M", "Re-attendance 0-14 year Male",
		        "gender=M|age=0-14|visit=R", "11");
		ColumnParameters stiRa15To29M = new ColumnParameters("stiRa15To29M", "Re-attendance 15-29 years Male",
		        "gender=M|age=15-29|visit=R", "12");
		ColumnParameters stiRa30PlusM = new ColumnParameters("stiRa30PlusM", "Re-attendance 30+ years Male",
		        "gender=M|age=30+|visit=R", "13");
		ColumnParameters stiRaTotalM = new ColumnParameters("stiRaTotalM", "Re-attendance total male", "gender=M|visit=R",
		        "14");
		
		ColumnParameters stiRa0To14F = new ColumnParameters("stiRa0To14F", "Re-attendance 0-14 year Female",
		        "gender=F|age=0-14|visit=R", "15");
		ColumnParameters stiRa15To29F = new ColumnParameters("stiRa15To29F", "Re-attendance 15-29 years Female",
		        "gender=F|age=15-29|visit=R", "16");
		ColumnParameters stiRa30PlusF = new ColumnParameters("stiRa30PlusF", "Re-attendance 30+ years Female",
		        "gender=F|age=30+|visit=R", "17");
		ColumnParameters stiRaTotalF = new ColumnParameters("stiRaTotalF", "Re-attendance total Female", "gender=F|visit=R",
		        "18");
		
		return Arrays.asList(stiNa0To5M, stiNa6To14M, stiNa15To29M, stiNa30PlusM, stiNaTotalM, stiNa0To5F, stiNa6To14F,
		    stiNa15To29F, stiNa30PlusF, stiNa5TotalF, stiRa0To14M, stiRa15To29M, stiRa30PlusM, stiRaTotalM, stiRa0To14F,
		    stiRa15To29F, stiRa30PlusF, stiRaTotalF);
	}
	
	public static List<ColumnParameters> getOtherDiseasesOrConditionsDimension() {
		ColumnParameters odNa0T014M = new ColumnParameters("odNa0T014M", "New 0-14 years male", "gender=M|age=0-14|visit=N",
		        "01");
		ColumnParameters odNa15T029M = new ColumnParameters("odNa15T029M", "New 15-29 years male",
		        "gender=M|age=15-29|visit=N", "02");
		ColumnParameters odNa30PlusM = new ColumnParameters("odNa30PlusM", "New 30+ years male", "gender=M|age=30+|visit=N",
		        "03");
		ColumnParameters odNaTotalM = new ColumnParameters("odNaTotalM", "New total male", "gender=M|visit=N", "04");
		
		ColumnParameters odNa0T014F = new ColumnParameters("odNa0T014F", "New 0-14 years female",
		        "gender=F|age=0-14|visit=N", "05");
		ColumnParameters odNa15T029F = new ColumnParameters("odNa15T029F", "New 15-29 years female",
		        "gender=F|age=15-29|visit=N", "06");
		ColumnParameters odNa30PlusF = new ColumnParameters("odNa30PlusF", "New 30+ years female",
		        "gender=F|age=30+|visit=N", "07");
		ColumnParameters odNaTotalF = new ColumnParameters("odNaTotalF", "New total female", "gender=F|visit=N", "08");
		
		ColumnParameters odRa0T014M = new ColumnParameters("odRa0T014M", "Re-attendance 0-14 years male",
		        "gender=M|age=0-14|visit=R", "09");
		ColumnParameters odRa15T029M = new ColumnParameters("odRa15T029M", "Re-attendance 15-29 years male",
		        "gender=M|age=15-29|visit=R", "10");
		ColumnParameters odRa30PlusM = new ColumnParameters("odRa30PlusM", "Re-attendance 30+ years male",
		        "gender=M|age=30+|visit=R", "11");
		ColumnParameters odRaTotalM = new ColumnParameters("odRaTotalM", "Re-attendance total male", "gender=M|visit=R",
		        "12");
		
		ColumnParameters odRa0T014F = new ColumnParameters("odRa0T014F", "Re-attendance 0-14 years female",
		        "gender=F|age=0-14|visit=R", "13");
		ColumnParameters odRa15T029F = new ColumnParameters("odRa15T029F", "Re-attendance 15-29 years female",
		        "gender=F|age=15-29|visit=R", "14");
		ColumnParameters odRa30PlusF = new ColumnParameters("odRa30PlusF", "Re-attendance 30+ years female",
		        "gender=F|age=30+|visit=R", "15");
		ColumnParameters odRaTotalF = new ColumnParameters("odRaTotalF", "Re-attendance total female", "gender=F|visit=R",
		        "16");
		
		return Arrays.asList(odNa0T014M, odNa15T029M, odNa30PlusM, odNaTotalM, odNa0T014F, odNa15T029F, odNa30PlusF,
		    odNaTotalF, odRa0T014M, odRa15T029M, odRa30PlusM, odRaTotalM, odRa0T014F, odRa15T029F, odRa30PlusF, odRaTotalF);
	}
	
	public static List<ColumnParameters> getCounseledSmcReportDimensions() {
		ColumnParameters numberOfClientsCounselledUnder1YearN = new ColumnParameters("numberOfClientsCounselledUnder1YearN",
		        "Number Of Clients Counselled Under 1 Year HIV -ve", "age=<1|status=N", "01");
		ColumnParameters numberOfClientsCounselledUnder1YearP = new ColumnParameters("numberOfClientsCounselledUnder1YearP",
		        "Number Of Clients Counselled Under 1 Year HIV +ve", "age=<1|status=P", "02");
		ColumnParameters numberOfClientsCounselledUnder1YearU = new ColumnParameters("numberOfClientsCounselledUnder1YearU",
		        "Number Of Clients Counselled Under 1 Year HIV unknown", "age=<1|status=U", "03");
		
		ColumnParameters numberOfClientsCounselled1To9YearN = new ColumnParameters("numberOfClientsCounselled1To9YearN",
		        "Number Of Clients Counselled 1-9 Years HIV -ve", "age=1-9|status=N", "04");
		ColumnParameters numberOfClientsCounselled1To9YearP = new ColumnParameters("numberOfClientsCounselled1To9YearP",
		        "Number Of Clients Counselled 1-9 Years HIV +ve", "age=1-9|status=P", "05");
		ColumnParameters numberOfClientsCounselled1To9YearU = new ColumnParameters("numberOfClientsCounselled1To9YearU",
		        "Number Of Clients Counselled 1-9 Years HIV unknown", "age=1-9|status=U", "06");
		
		ColumnParameters numberOfClientsCounselled10To14YearN = new ColumnParameters("numberOfClientsCounselled10To14YearN",
		        "Number Of Clients Counselled 10-14 Years HIV -ve", "age=10-14|status=N", "07");
		ColumnParameters numberOfClientsCounselled10To14YearP = new ColumnParameters("numberOfClientsCounselled10To14YearP",
		        "Number Of Clients Counselled 10-14 Years HIV +ve", "age=10-14|status=P", "08");
		ColumnParameters numberOfClientsCounselled10To14YearU = new ColumnParameters("numberOfClientsCounselled10To14YearU",
		        "Number Of Clients Counselled 10-14 Years HIV unknown", "age=10-14|status=U", "09");
		
		ColumnParameters numberOfClientsCounselled15To19YearN = new ColumnParameters("numberOfClientsCounselled15To19YearN",
		        "Number Of Clients Counselled 15-19 Years HIV -ve", "age=15-19|status=N", "10");
		ColumnParameters numberOfClientsCounselled15To19YearP = new ColumnParameters("numberOfClientsCounselled15To19YearP",
		        "Number Of Clients Counselled 15-19 Years HIV +ve", "age=15-19|status=P", "11");
		ColumnParameters numberOfClientsCounselled15To19YearU = new ColumnParameters("numberOfClientsCounselled15To19YearU",
		        "Number Of Clients Counselled 15-19 Years HIV unknown", "age=15-19|status=U", "12");
		
		ColumnParameters numberOfClientsCounselled20To24YearN = new ColumnParameters("numberOfClientsCounselled20To24YearN",
		        "Number Of Clients Counselled 20-24 Years HIV -ve", "age=20-24|status=N", "13");
		ColumnParameters numberOfClientsCounselled20To24YearP = new ColumnParameters("numberOfClientsCounselled20To24YearP",
		        "Number Of Clients Counselled 20-24 Years HIV +ve", "age=20-24|status=P", "14");
		ColumnParameters numberOfClientsCounselled20To24YearU = new ColumnParameters("numberOfClientsCounselled20To24YearU",
		        "Number Of Clients Counselled 20-24 Years HIV unknown", "age=20-24|status=U", "15");
		
		ColumnParameters numberOfClientsCounselled25To29YearN = new ColumnParameters("numberOfClientsCounselled25To29YearN",
		        "Number Of Clients Counselled 25-29 Years HIV -ve", "age=25-29|status=N", "16");
		ColumnParameters numberOfClientsCounselled25To29YearP = new ColumnParameters("numberOfClientsCounselled25To29YearP",
		        "Number Of Clients Counselled 25-29 Years HIV +ve", "age=25-29|status=P", "17");
		ColumnParameters numberOfClientsCounselled25To29YearU = new ColumnParameters("numberOfClientsCounselled25To29YearU",
		        "Number Of Clients Counselled 25-29 Years HIV unknown", "age=25-29|status=U", "18");
		
		ColumnParameters numberOfClientsCounselled30To39YearN = new ColumnParameters("numberOfClientsCounselled30To39YearN",
		        "Number Of Clients Counselled 30-39 Years HIV -ve", "age=30-39|status=N", "19");
		ColumnParameters numberOfClientsCounselled30To39YearP = new ColumnParameters("numberOfClientsCounselled30To39YearP",
		        "Number Of Clients Counselled 30-39 Years HIV +ve", "age=30-39|status=P", "20");
		ColumnParameters numberOfClientsCounselled30To39YearU = new ColumnParameters("numberOfClientsCounselled30To39YearU",
		        "Number Of Clients Counselled 30-39 Years HIV unknown", "age=30-39|status=U", "21");
		
		ColumnParameters numberOfClientsCounselled40To49YearN = new ColumnParameters("numberOfClientsCounselled40To49YearN",
		        "Number Of Clients Counselled 40-49 Years HIV -ve", "age=40-49|status=N", "22");
		ColumnParameters numberOfClientsCounselled40To49YearP = new ColumnParameters("numberOfClientsCounselled40To49YearP",
		        "Number Of Clients Counselled 40-49 Years HIV +ve", "age=40-49|status=P", "23");
		ColumnParameters numberOfClientsCounselled40To49YearU = new ColumnParameters("numberOfClientsCounselled40To49YearU",
		        "Number Of Clients Counselled 40-49 Years HIV unknown", "age=40-49|status=U", "24");
		
		ColumnParameters numberOfClientsCounselled50PlusYearsN = new ColumnParameters(
		        "numberOfClientsCounselled50PlusYearN", "Number Of Clients Counselled 50+ Years HIV -ve",
		        "age=50+|status=N", "25");
		ColumnParameters numberOfClientsCounselled50PlusYearP = new ColumnParameters("numberOfClientsCounselled50PlusYearP",
		        "Number Of Clients Counselled 50 Years HIV +ve", "age=50+|status=P", "26");
		ColumnParameters numberOfClientsCounselled50PlusYearsU = new ColumnParameters(
		        "numberOfClientsCounselled50PlusYearsU", "Number Of Clients Counselled 50+ Years HIV unknown",
		        "age=50+|status=U", "27");
		
		return Arrays.asList(numberOfClientsCounselledUnder1YearN, numberOfClientsCounselledUnder1YearP,
		    numberOfClientsCounselledUnder1YearU, numberOfClientsCounselled1To9YearN, numberOfClientsCounselled1To9YearP,
		    numberOfClientsCounselled1To9YearU, numberOfClientsCounselled10To14YearN, numberOfClientsCounselled10To14YearP,
		    numberOfClientsCounselled10To14YearU, numberOfClientsCounselled15To19YearN,
		    numberOfClientsCounselled15To19YearP, numberOfClientsCounselled15To19YearU,
		    numberOfClientsCounselled20To24YearN, numberOfClientsCounselled20To24YearP,
		    numberOfClientsCounselled20To24YearU, numberOfClientsCounselled25To29YearN,
		    numberOfClientsCounselled25To29YearP, numberOfClientsCounselled25To29YearU,
		    numberOfClientsCounselled30To39YearN, numberOfClientsCounselled30To39YearP,
		    numberOfClientsCounselled30To39YearU, numberOfClientsCounselled40To49YearN,
		    numberOfClientsCounselled40To49YearP, numberOfClientsCounselled40To49YearU,
		    numberOfClientsCounselled50PlusYearsN, numberOfClientsCounselled50PlusYearP,
		    numberOfClientsCounselled50PlusYearsU);
	}
	
	public static List<ColumnParameters> getCounseledOutcomesSmcReportDimensions() {
		ColumnParameters numberOfClientsCounselledOutcomesAgreeUnder1YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgreeUnder1YearA",
		        "Number Of Clients Counselled  outcomes agree Under 1 year", "age=<1|outcome=AG", "01");
		ColumnParameters numberOfClientsCounselledOutcomesRefusedUnder1YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefusedUnder1YearDED",
		        "Number Of Clients Counselled Refused Under 1 Year", "age=<1|outcome=DEC", "02");
		ColumnParameters numberOfClientsCounselledDecisionDeferredUnder1YearDD = new ColumnParameters(
		        "numberOfClientsCounselledDecisionDeferredUnder1YearDD",
		        "Number Of Clients Counselled decision deferred Under 1 Year", "age=<1|outcome=DD", "03");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree1To9YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree1To9YearA",
		        "Number Of Clients Counselled outcomes agreed 1-9 Years ", "age=1-9|outcome=AG", "04");
		ColumnParameters numberOfClientsCounselledOutcomesRefused1To9YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused1To9YearDEC",
		        "Number Of Clients Counselled outcomes refused 1-9 Years", "age=1-9|outcome=DEC", "05");
		ColumnParameters numberOfClientsCounselledDecisionDeferred1To9YearDD = new ColumnParameters(
		        "numberOfClientsCounselledDecisionDeferred1To9YearDD",
		        "Number Of Clients Counselled outcomes decision deferred 1-9 Years HIV unknown", "age=1-9|outcome=DD", "06");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree10To14YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree10To14YearA",
		        "Number Of Clients Counselled outcomes agree 10-14 Years", "age=10-14|outcome=AG", "07");
		ColumnParameters numberOfClientsCounselledOutcomesRefused10To14YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused10To14YearDEC",
		        "Number Of Clients Counselled outcomes refused 10-14 Years", "age=10-14|outcome=DEC", "08");
		ColumnParameters numberOfClientsCounselledDecisionDeferred10To14YearDD = new ColumnParameters(
		        "numberOfClientsCounselledDecisionDeferred10To14YearDD",
		        "Number Of Clients Counselled decision deferred 10-14 Years", "age=10-14|outcome=DD", "09");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree15To19YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree15To19YearA",
		        "Number Of Clients Counselled outcomes agree 15-19 Years", "age=15-19|outcome=AG", "10");
		ColumnParameters numberOfClientsCounselledOutcomesRefused15To19YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused15To19YearDEC",
		        "Number Of Clients Counselled outcomes refused 15-19 Years", "age=15-19|outcome=DEC", "11");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred15To19YearDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred15To19YearDD",
		        "Number Of Clients Counselled decision deferred 15-19 Years", "age=15-19|outcome=DD", "12");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree20To24YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree20To24YearA",
		        "Number Of Clients Counselled Outcomes agree 20-24 Years HIV -ve", "age=20-24|outcome=AG", "13");
		ColumnParameters numberOfClientsCounselledOutcomesRefused20To24YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused20To24YearDEC",
		        "Number Of Clients Counselled outcomes refused 20-24 Years", "age=20-24|outcome=DEC", "14");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred20To24YearDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred20To24YearDD",
		        "Number Of Clients Counselled decision deferred 20-24 Years", "age=20-24|outcome=DD", "15");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree25To29YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree25To29YearA",
		        "Number Of Clients Counselled Outcomes agree 25-29 Years", "age=25-29|outcome=AG", "16");
		ColumnParameters numberOfClientsCounselledOutcomesRefused25To29YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused25To29YearDEC",
		        "Number Of Clients Counselled  outcomes refused 25-29 Years", "age=25-29|outcome=DEC", "17");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred25To29YearDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred25To29YearDD",
		        "Number Of Clients Counselled Outcomes decision deferred  25-29 Years", "age=25-29|outcome=DD", "18");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree30To39YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree30To39YearA",
		        "Number Of Clients Counselled Outcomes agree 30-39 Years", "age=30-39|outcome=AG", "19");
		ColumnParameters numberOfClientsCounselledOutcomesRefused30To39YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused30To39YearDEC",
		        "Number Of Clients Counselled outcomes refused 30-39 Years", "age=30-39|outcome=DEC", "20");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred30To39YearDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred30To39YearDD",
		        "Number Of Clients Counselled Outcomes decision deferred 30-39 Years", "age=30-39|outcome=DD", "21");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree40To49YearA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree40To49YearA",
		        "Number Of Clients Counselled Outcomes agree 40-49 Years HIV -ve", "age=40-49|outcome=AG", "22");
		ColumnParameters numberOfClientsCounselledOutcomesRefused40To49YearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused40To49YearDEC",
		        "Number Of Clients Counselled outcomes refused 40-49 Years", "age=40-49|outcome=DEC", "23");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred40To49YearDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred40To49YearDD",
		        "Number Of Clients Counselled Outcomes decision deferred  40-49 Years", "age=40-49|outcome=DD", "24");
		
		ColumnParameters numberOfClientsCounselledOutcomesAgree50PlusYearsA = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesAgree50PlusYearsA",
		        "Number Of Clients Counselled Outcomes agree 50+ Years", "age=50+|outcome=AG", "25");
		ColumnParameters numberOfClientsCounselledOutcomesRefused50PlusYearDEC = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesRefused50PlusYearDEC",
		        "Number Of Clients Counselled outcomes refused 50 Years", "age=50+|outcome=DEC", "26");
		ColumnParameters numberOfClientsCounselledOutcomesDecisionDeferred50PlusYearsDD = new ColumnParameters(
		        "numberOfClientsCounselledOutcomesDecisionDeferred50PlusYearsDD",
		        "Number Of Clients Counselled Outcomes decision deferred 50+ Years", "age=50+|outcome=DD", "27");
		
		return Arrays.asList(numberOfClientsCounselledOutcomesAgreeUnder1YearA,
		    numberOfClientsCounselledOutcomesRefusedUnder1YearDEC, numberOfClientsCounselledDecisionDeferredUnder1YearDD,
		    numberOfClientsCounselledOutcomesAgree1To9YearA, numberOfClientsCounselledOutcomesRefused1To9YearDEC,
		    numberOfClientsCounselledDecisionDeferred1To9YearDD, numberOfClientsCounselledOutcomesAgree10To14YearA,
		    numberOfClientsCounselledOutcomesRefused10To14YearDEC, numberOfClientsCounselledDecisionDeferred10To14YearDD,
		    numberOfClientsCounselledOutcomesAgree15To19YearA, numberOfClientsCounselledOutcomesRefused15To19YearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred15To19YearDD,
		    numberOfClientsCounselledOutcomesAgree20To24YearA, numberOfClientsCounselledOutcomesRefused20To24YearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred20To24YearDD,
		    numberOfClientsCounselledOutcomesAgree25To29YearA, numberOfClientsCounselledOutcomesRefused25To29YearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred25To29YearDD,
		    numberOfClientsCounselledOutcomesAgree30To39YearA, numberOfClientsCounselledOutcomesRefused30To39YearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred30To39YearDD,
		    numberOfClientsCounselledOutcomesAgree40To49YearA, numberOfClientsCounselledOutcomesRefused40To49YearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred40To49YearDD,
		    numberOfClientsCounselledOutcomesAgree50PlusYearsA, numberOfClientsCounselledOutcomesRefused50PlusYearDEC,
		    numberOfClientsCounselledOutcomesDecisionDeferred50PlusYearsDD);
	}
	
	public static List<ColumnParameters> getCounseledReviewSmcReportDimensions() {
		ColumnParameters NumberOfSmcClientsWWhoCameForReview48HoursUnder1Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview48HoursUnder1Year",
		        "Number of VMMC clients who came for review 48 hours Under 1 year", "age=<1|review=48", "01");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview7DaysUnder1Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview7DaysUnder1Year",
		        "Number of VMMC clients who came for review 7 days Under 1 year", "age=<1|review=7", "02");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview42DaysUnder1Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview42DaysUnder1Year",
		        "Number of VMMC clients who came for review 42 days Under 1 year", "age=<1|review=42", "03");
		
		ColumnParameters NumberOfSmcClientsWWhoCameForReview48Hours1To9Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview48Hours1To9Year",
		        "Number of VMMC clients who came for review 48 hours 1-9 Years ", "age=1-9|review=48", "04");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview7Days1To9Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview7Days1To9Year",
		        "Number of VMMC clients who came for review 7 days 1-9 Years", "age=1-9|review=7", "05");
		
		ColumnParameters NumberOfSmcClientsWWhoCameForReview42Days1To9Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview42Days1To9Year",
		        "Number of VMMC clients who came for review 42 days 1-9 Years", "age=1-9|review=42", "06");
		
		ColumnParameters NumberOfSmcClientsWWhoCameForReview48Hours10To14Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview48Hours10To14Year",
		        "Number of VMMC clients who came for review 48 hours 10-14 Years", "age=10-14|review=48", "07");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview7Days10To14Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview7Days10To14Year",
		        "Number of VMMC clients who came for review 7 days 10-14 Years", "age=10-14|review=7", "08");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview42Days10To14Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview42Days10To14Year",
		        "Number of VMMC clients who came for review 42 days 10-14 Years", "age=10-14|review=42", "09");
		
		ColumnParameters NumberOfSmcClientsWWhoCameForReview48Hours15To19Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview48Hours15To19Year",
		        "Number of VMMC clients who came for review 48 hours 15-19 Years", "age=15-19|review=48", "10");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview7Days15To19Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview7Days15To19Year",
		        "Number of VMMC clients who came for review 7 days 15-19 Years", "age=15-19|review=7", "11");
		ColumnParameters NumberOfSmcClientsWWhoCameForReview42Days15To19Year = new ColumnParameters(
		        "NumberOfSmcClientsWWhoCameForReview42Days15To19Year",
		        "Number of VMMC clients who came for review 42 days 15-19 Years", "age=15-19|review=42", "12");
		
		ColumnParameters numberOfSmcClientsWWhoCameForReview48Hours20To24Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview48Hours20To24Years",
		        "Number of VMMC clients who came for review 48 hours 20-24 Years", "age=20-24|review=48", "13");
		ColumnParameters numberOfSmcClientsWWhoCameForReview7Days20To24Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview7Days20To24Years",
		        "Number of VMMC clients who came for review 7 days 20-24 Years", "age=20-24|review=7", "14");
		ColumnParameters numberOfSmcClientsWWhoCameForReview42Days20To24Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview42Days0To24Years",
		        "Number of VMMC clients who came for review 42 days 20-24 Years", "age=20-24|review=42", "15");
		
		ColumnParameters numberOfSmcClientsWWhoCameForReview48Hours25To29Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview48Hours25To29Years",
		        "Number of VMMC clients who came for review 48 hours 25-29 Years", "age=25-29|review=48", "16");
		ColumnParameters numberOfSmcClientsWWhoCameForReview7Days25To29Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview7Days25To29Years",
		        "Number of VMMC clients who came for review 7 days 25-29 Years", "age=25-29|review=7", "17");
		ColumnParameters numberOfSmcClientsWWhoCameForReview42Days25To29Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview42Days25To29Years",
		        "Number of VMMC clients who came for review 42 days  25-29 Years", "age=25-29|review=42", "18");
		
		ColumnParameters numberOfSmcClientsWhoCameForReview48Hours30To39Years = new ColumnParameters(
		        "numberOfSmcClientsWhoCameForReview48Hours30To39Years",
		        "Number of VMMC clients who came for review 48 hours 30-39 Years", "age=30-39|review=42", "19");
		ColumnParameters numberOfSmcClientsWWhoCameForReview7Days30To39Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview7Days30To39Years",
		        "Number of VMMC clients who came for review 7 days 30-39 Years", "age=30-39|review=7", "20");
		ColumnParameters numberOfSmcClientsWWhoCameForReview42Days30To39Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview42Days30To39Years",
		        "Number of VMMC clients who came for review 42 days 30-39 Years", "age=30-39|review=42", "21");
		
		ColumnParameters numberOfSmcClientsWhoCameForReview48Hours40To49Years = new ColumnParameters(
		        "numberOfSmcClientsWhoCameForReview48Hours40To49Years",
		        "Number of VMMC clients who came for review 48 hours 40-49 Years", "age=40-49|review=48", "22");
		ColumnParameters numberOfSmcClientsWWhoCameForReview7Days40To49Year = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview7Days40To49Year",
		        "Number of VMMC clients who came for review 7 days 40-49 Years", "age=40-49|review=7", "23");
		ColumnParameters numberOfSmcClientsWWhoCameForReview42Days40To49Years = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview42Days40To49Years",
		        "Number of VMMC clients who came for review 42 days 40-49 Years", "age=40-49|review=42", "24");
		
		ColumnParameters numberOfSmcClientsWhoCameForReview48Hours50PlusYears = new ColumnParameters(
		        "numberOfSmcClientsWhoCameForReview48Hours50PlusYears",
		        "Number of VMMC clients who came for review 48 hours 50+ Years", "age=50+|review=48", "25");
		ColumnParameters numberOfSmcClientsWWhoCameForReview7Days50PlusYear = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview7Days50PlusYear",
		        "Number of VMMC clients who came for review 7 days 50 Years", "age=50+|review=7", "26");
		ColumnParameters numberOfSmcClientsWWhoCameForReview42Days50PlusYears = new ColumnParameters(
		        "numberOfSmcClientsWWhoCameForReview42Days50PlusYears",
		        "Number of VMMC clients who came for review 42 days 50+ Years", "age=50+|review=42", "27");
		
		return Arrays.asList(NumberOfSmcClientsWWhoCameForReview48HoursUnder1Year,
		    NumberOfSmcClientsWWhoCameForReview7DaysUnder1Year, NumberOfSmcClientsWWhoCameForReview42DaysUnder1Year,
		    NumberOfSmcClientsWWhoCameForReview48Hours1To9Year, NumberOfSmcClientsWWhoCameForReview42Days1To9Year,
		    NumberOfSmcClientsWWhoCameForReview48Hours10To14Year, NumberOfSmcClientsWWhoCameForReview7Days1To9Year,
		    NumberOfSmcClientsWWhoCameForReview7Days10To14Year, NumberOfSmcClientsWWhoCameForReview42Days10To14Year,
		    NumberOfSmcClientsWWhoCameForReview48Hours15To19Year, NumberOfSmcClientsWWhoCameForReview7Days15To19Year,
		    NumberOfSmcClientsWWhoCameForReview42Days15To19Year, numberOfSmcClientsWWhoCameForReview48Hours20To24Years,
		    numberOfSmcClientsWWhoCameForReview7Days20To24Years, numberOfSmcClientsWWhoCameForReview42Days20To24Years,
		    numberOfSmcClientsWWhoCameForReview48Hours25To29Years, numberOfSmcClientsWWhoCameForReview7Days25To29Years,
		    numberOfSmcClientsWWhoCameForReview42Days25To29Years, numberOfSmcClientsWhoCameForReview48Hours30To39Years,
		    numberOfSmcClientsWWhoCameForReview7Days30To39Years, numberOfSmcClientsWWhoCameForReview42Days30To39Years,
		    numberOfSmcClientsWhoCameForReview48Hours40To49Years, numberOfSmcClientsWWhoCameForReview7Days40To49Year,
		    numberOfSmcClientsWWhoCameForReview42Days40To49Years, numberOfSmcClientsWhoCameForReview48Hours50PlusYears,
		    numberOfSmcClientsWWhoCameForReview7Days50PlusYear, numberOfSmcClientsWWhoCameForReview42Days50PlusYears);
	}
	
	public static List<ColumnParameters> getNumberAndDegreeOfAdverseEventsSmcReportDimensions() {
		ColumnParameters NumberOfAnaesthesiaReactionUnder1YearMIL = new ColumnParameters(
		        "NumberOfAnaesthesiaReactionUnder1YearMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction Under 1 year MIL", "age=<1|adEv=ARMIL", "01");
		ColumnParameters NumberOfAnaesthesiaReactionUnder1YearMOD = new ColumnParameters(
		        "NumberOfAnaesthesiaReactionUnder1YearMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction Under 1 year MOD", "age=<1|adEv=ARMOD", "02");
		ColumnParameters NumberOfAnaesthesiaReactionUnder1YearSEV = new ColumnParameters(
		        "NumberOfAnaesthesiaReactionUnder1YearSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction Under 1 year SEV", "age=<1|adEv=ARSEV", "03");
		ColumnParameters NumberOfAnaesthesiaReaction1To9YearMIL = new ColumnParameters(
		        "NumberOfAnaesthesiaReaction1To9YearMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 1-9 Years MIL", "age=1-9|adEv=ARMIL", "04");
		ColumnParameters NumberOfAnaesthesiaReaction1To9YearMOD = new ColumnParameters(
		        "NumberOfAnaesthesiaReaction1To9YearMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 1-9 Years MOD", "age=1-9|adEv=ARMOD", "05");
		ColumnParameters NumberOfAnaesthesiaReaction1To9YearSEV = new ColumnParameters(
		        "NumberOfAnaesthesiaReaction1To9YearSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 1-9 Years SEV", "age=1-9|adEv=ARSEV", "06");
		ColumnParameters numberOfAnaesthesiaReactions10To14YearMIL = new ColumnParameters(
		        "NumberOfAnaesthesiaReactions10To14YearMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 10-14 Years", "age=10-14|adEv=ARMIL", "07");
		ColumnParameters numberOfAnaesthesiaReactions10To14YearMOD = new ColumnParameters(
		        "NumberOfAnaesthesiaReactions10To14YearMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction  10-14 Years MOD", "age=10-14|adEv=ARMOD", "08");
		ColumnParameters numberOfAnaesthesiaReactions10To14YearSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions10To14YearSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 10-14 Years SEV", "age=10-14|adEv=ARSEV", "09");
		ColumnParameters numberOfAnaesthesiaReactions15To19YearMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions15To19YearMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 15-19 Years", "age=15-19|adEv=ARMIL", "10");
		ColumnParameters numberOfAnaesthesiaReactions15To19YearMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions15To19YearMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 15-19 Years MOD", "age=15-19|adEv=ARMOD", "11");
		ColumnParameters numberOfAnaesthesiaReactions15To19YearSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions15To19YearSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 15-19 Years SEV", "age=15-19|adEv=ARSEV", "12");
		ColumnParameters numberOfAnaesthesiaReactions20To24YearsMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions20To24YearsMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 20-24 Years MIL", "age=20-24|adEv=ARMIL", "13");
		ColumnParameters numberOfAnaesthesiaReactions20To24YearsMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions20To24YearsMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 20-24 Years MOD", "age=20-24|adEv=ARMOD", "14");
		ColumnParameters numberOfAnaesthesiaReactions20To24YearsSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions20To24YearsSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 20-24 Years SEV", "age=20-24|adEv=ARSEV", "15");
		ColumnParameters numberOfAnaesthesiaReactions25To29YearsMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions25To29YearsMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 25-29 Years MIL", "age=25-29|adEv=ARMIL", "16");
		ColumnParameters numberOfAnaesthesiaReactions25To29YearsMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions25To29YearsMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 25-29 Years MOD", "age=25-29|adEv=ARMOD", "17");
		ColumnParameters numberOfAnaesthesiaReactions25To29YearsSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions25To29YearsSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction  25-29 Years SEV", "age=25-29|adEv=ARSEV", "18");
		
		ColumnParameters numberOfAnaesthesiaReactions30To39YearsMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions30To39YearsMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 30-39 Years", "age=30-39|adEv=ARMIL", "19");
		ColumnParameters numberOfAnaesthesiaReactions30To39YearsMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions30To39YearsMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 30-39 Years", "age=30-39|adEv=ARMOD", "20");
		ColumnParameters numberOfAnaesthesiaReactions30To39YearsSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions30To39YearsSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 30-39 Years", "age=30-39|adEv=ARSEV", "21");
		ColumnParameters numberOfAnaesthesiaReactions40To49YearsMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions40To49YearsMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 40-49 Years", "age=40-49|adEv=ARMIL", "22");
		ColumnParameters numberOfAnaesthesiaReactions40To49YearsMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions40To49YearsMOD",
		        "Number and degree of Adverse Events Anaesthesia Reaction 40-49 Years", "age=40-49|adEv=ARMOD", "23");
		ColumnParameters numberOfAnaesthesiaReactions40To49YearsSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions40To49YearsSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 40-49 Years", "age=40-49|adEv=ARSEV", "24");
		
		ColumnParameters numberOfAnaesthesiaReactions50PlusYearsMIL = new ColumnParameters(
		        "numberOfAnaesthesiaReactions50PlusYearsMIL",
		        "Number and degree of Adverse Events Anaesthesia Reaction 50+ Years MIL", "age=50+|adEv=ARMIL", "25");
		ColumnParameters numberOfAnaesthesiaReactions50PlusYearsMOD = new ColumnParameters(
		        "numberOfAnaesthesiaReactions50PlusYearsMOD",
		        "Number and degree of Adverse Events Anaesthesia Reactions 50 Years MOD", "age=50+|adEv=ARMOD", "26");
		ColumnParameters numberOfAnaesthesiaReactions50PlusYearsSEV = new ColumnParameters(
		        "numberOfAnaesthesiaReactions50PlusYearsSEV",
		        "Number and degree of Adverse Events Anaesthesia Reaction 50+ Years SEV", "age=50+|adEv=ARSEV", "27");
		
		return Arrays.asList(NumberOfAnaesthesiaReactionUnder1YearMIL, NumberOfAnaesthesiaReactionUnder1YearMOD,
		    NumberOfAnaesthesiaReactionUnder1YearSEV, NumberOfAnaesthesiaReaction1To9YearMIL,
		    NumberOfAnaesthesiaReaction1To9YearMOD, NumberOfAnaesthesiaReaction1To9YearSEV,
		    numberOfAnaesthesiaReactions10To14YearMIL, numberOfAnaesthesiaReactions10To14YearMOD,
		    numberOfAnaesthesiaReactions10To14YearSEV, numberOfAnaesthesiaReactions15To19YearMIL,
		    numberOfAnaesthesiaReactions15To19YearMOD, numberOfAnaesthesiaReactions15To19YearSEV,
		    numberOfAnaesthesiaReactions20To24YearsMIL, numberOfAnaesthesiaReactions20To24YearsMOD,
		    numberOfAnaesthesiaReactions20To24YearsSEV, numberOfAnaesthesiaReactions25To29YearsMIL,
		    numberOfAnaesthesiaReactions25To29YearsMOD, numberOfAnaesthesiaReactions25To29YearsSEV,
		    numberOfAnaesthesiaReactions30To39YearsMIL, numberOfAnaesthesiaReactions30To39YearsMOD,
		    numberOfAnaesthesiaReactions30To39YearsSEV, numberOfAnaesthesiaReactions40To49YearsMIL,
		    numberOfAnaesthesiaReactions40To49YearsMOD, numberOfAnaesthesiaReactions40To49YearsSEV,
		    numberOfAnaesthesiaReactions50PlusYearsMIL, numberOfAnaesthesiaReactions50PlusYearsMOD,
		    numberOfAnaesthesiaReactions50PlusYearsSEV);
	}
	
	public static List<ColumnParameters> getNumberAndDegreeOfAdverseEventsSmcReportDimensions1() {
		ColumnParameters numberOfAnaesthesiaBleedingUnder1YearMIL = new ColumnParameters(
		        "numberOfAnaesthesiaBleedingUnder1YearMIL", "Number and degree of Adverse Events Bleeding Under 1 year MIL",
		        "age=<1|adEv=BMIL", "01");
		ColumnParameters numberOfAnaesthesiaBleedingUnder1YearMOD = new ColumnParameters(
		        "numberOfAnaesthesiaBleedingUnder1YearMOD", "Number and degree of Adverse Events Bleeding Under 1 year MOD",
		        "age=<1|adEv=BMOD", "02");
		ColumnParameters numberOfAnaesthesiaBleedingUnder1YearSEV = new ColumnParameters(
		        "NumberOfAnaesthesiaReactionUnder1YearSEV", "Number and degree of Adverse Events Bleeding Under 1 year SEV",
		        "age=<1|adEv=BSEV", "03");
		ColumnParameters numberOfBleeding1To9YearMIL = new ColumnParameters("numberOfBleeding1To9YearMIL",
		        "Number and degree of Adverse Events Bleeding 1-9 Years MIL", "age=1-9|adEv=BMIL", "04");
		ColumnParameters numberOfBleeding11To9YearMOD = new ColumnParameters("numberOfBleeding11To9YearMOD",
		        "Number and degree of Adverse Events Bleeding 1-9 Years MOD", "age=1-9|adEv=BMOD", "05");
		ColumnParameters numberOfBleeding11To9YearSEV = new ColumnParameters("numberOfBleeding11To9YearSEV",
		        "Number and degree of Adverse Events Bleeding 1-9 Years SEV", "age=1-9|adEv=BSEV", "06");
		ColumnParameters numberOfBleeding110To14YearMIL = new ColumnParameters("numberOfBleeding110To14YearMIL",
		        "Number and degree of Adverse Events Bleeding 10-14 Years MIL", "age=10-14|adEv=BMIL", "07");
		ColumnParameters numberOfBleeding110To14YearMOD = new ColumnParameters("numberOfBleeding110To14YearMOD",
		        "Number and degree of Adverse Events Bleeding  10-14 Years MOD", "age=10-14|adEv=BMOD", "08");
		ColumnParameters numberOfBleeding110To14YearSEV = new ColumnParameters("numberOfBleeding110To14YearSEV",
		        "Number and degree of Adverse Events Bleeding 10-14 Years SEV", "age=10-14|adEv=BSEV", "09");
		ColumnParameters numberOfBleeding115To19YearMIL = new ColumnParameters("numberOfBleeding115To19YearMIL",
		        "Number and degree of Adverse Events Bleeding 15-19 Years MIL", "age=15-19|adEv=BMIL", "10");
		ColumnParameters numberOfBleeding115To19YearMOD = new ColumnParameters("numberOfBleeding115To19YearMOD",
		        "Number and degree of Adverse Events Bleeding 15-19 Years MOD", "age=15-19|adEv=BMOD", "11");
		ColumnParameters numberOfBleeding115To19YearSEV = new ColumnParameters("numberOfBleeding115To19YearSEV",
		        "Number and degree of Adverse Events Bleeding 15-19 Years SEV", "age=15-19|adEv=BSEV", "12");
		ColumnParameters numberOfBleeding120To24YearsMIL = new ColumnParameters("numberOfBleeding120To24YearsMIL",
		        "Number and degree of Adverse Events Bleeding 20-24 Years MIL", "age=20-24|adEv=BMIL", "13");
		ColumnParameters numberOfBleeding120To24YearsMOD = new ColumnParameters("numberOfBleeding120To24YearsMOD",
		        "Number and degree of Adverse Events Bleeding 20-24 Years MOD", "age=20-24|adEv=BMOD", "14");
		ColumnParameters numberOfBleeding120To24YearsSEV = new ColumnParameters("numberOfBleeding120To24YearsSEV",
		        "Number and degree of Adverse Events Bleeding 20-24 Years SEV", "age=20-24|adEv=BSEV", "15");
		ColumnParameters numberOfBleeding125To29YearsMIL = new ColumnParameters("numberOfBleeding125To29YearsMIL",
		        "Number and degree of Adverse Events Bleeding 25-29 Years MIL", "age=25-29|adEv=BMIL", "16");
		ColumnParameters numberOfBleeding125To29YearsMOD = new ColumnParameters("numberOfBleeding125To29YearsMOD",
		        "Number and degree of Adverse Events Bleeding 25-29 Years MOD", "age=25-29|adEv=BMOD", "17");
		ColumnParameters numberOfBleeding125To29YearsSEV = new ColumnParameters("numberOfBleeding125To29YearsSEV",
		        "Number and degree of Adverse Events Bleeding  25-29 Years SEV", "age=25-29|adEv=BSEV", "18");
		
		ColumnParameters numberOfBleeding130To39YearsMIL = new ColumnParameters("numberOfBleeding130To39YearsMIL",
		        "Number and degree of Adverse Events Bleeding 30-39 Years MIL", "age=30-39|adEv=BMIL", "19");
		ColumnParameters numberOfBleeding130To39YearsMOD = new ColumnParameters("numberOfBleeding130To39YearsMOD",
		        "Number and degree of Adverse Events Bleeding 30-39 Years MOD", "age=30-39|adEv=BMOD", "20");
		ColumnParameters numberOfBleeding130To39YearsSEV = new ColumnParameters("numberOfBleeding130To39YearsSEV",
		        "Number and degree of Adverse Events Bleeding 30-39 Years SEV", "age=30-39|adEv=BSEV", "21");
		ColumnParameters numberOfBleeding140To49YearsMIL = new ColumnParameters("numberOfBleeding140To49YearsMIL",
		        "Number and degree of Adverse Events Bleeding 40-49 Years MIL", "age=40-49|adEv=BMIL", "22");
		ColumnParameters numberOfBleeding140To49YearsMOD = new ColumnParameters("numberOfBleeding140To49YearsMOD",
		        "Number and degree of Adverse Events Bleeding 40-49 Years MOD", "age=40-49|adEv=BMOD", "23");
		ColumnParameters numberOfBleeding140To49YearsSEV = new ColumnParameters("numberOfBleeding140To49YearsSEV",
		        "Number and degree of Adverse Events Bleeding 40-49 Years SEV", "age=40-49|adEv=BSEV", "24");
		
		ColumnParameters numberOfBleeding150PlusYearsMIL = new ColumnParameters("numberOfBleeding150PlusYearsMIL",
		        "Number and degree of Adverse Events Bleeding 50+ Years MIL", "age=50+|adEv=BMIL", "25");
		ColumnParameters numberOfBleeding150PlusYearsMOD = new ColumnParameters("numberOfBleeding150PlusYearsMOD",
		        "Number and degree of Adverse Events Bleeding 50 Years MOD", "age=50+|adEv=BMOD", "26");
		ColumnParameters numberOfBleeding150PlusYearsSEV = new ColumnParameters("numberOfBleeding150PlusYearsSEV",
		        "Number and degree of Adverse Events Bleeding 50+ Years SEV", "age=50+|adEv=BSEV", "27");
		
		return Arrays.asList(numberOfAnaesthesiaBleedingUnder1YearMIL, numberOfAnaesthesiaBleedingUnder1YearMOD,
		    numberOfAnaesthesiaBleedingUnder1YearSEV, numberOfBleeding1To9YearMIL, numberOfBleeding11To9YearMOD,
		    numberOfBleeding11To9YearSEV, numberOfBleeding110To14YearMIL, numberOfBleeding110To14YearMOD,
		    numberOfBleeding110To14YearSEV, numberOfBleeding115To19YearMIL, numberOfBleeding115To19YearMOD,
		    numberOfBleeding115To19YearSEV, numberOfBleeding120To24YearsMIL, numberOfBleeding120To24YearsMOD,
		    numberOfBleeding120To24YearsSEV, numberOfBleeding125To29YearsMIL, numberOfBleeding125To29YearsMOD,
		    numberOfBleeding125To29YearsSEV, numberOfBleeding130To39YearsMIL, numberOfBleeding130To39YearsMOD,
		    numberOfBleeding130To39YearsSEV, numberOfBleeding140To49YearsMIL, numberOfBleeding140To49YearsMOD,
		    numberOfBleeding140To49YearsSEV, numberOfBleeding150PlusYearsMIL, numberOfBleeding150PlusYearsMOD,
		    numberOfBleeding150PlusYearsSEV);
	}
	
	public static List<ColumnParameters> getNumberAndDegreeOfAdverseEventsSmcReportDimensions2() {
		ColumnParameters numberOfHaematomaUnder1YearMIL = new ColumnParameters("numberOfHaematomaUnder1YearMIL",
		        "Number and degree of Adverse Events Haematoma Under 1 year MIL", "age=<1|adEv=HMIL", "01");
		ColumnParameters numberOfHaematomaUnder1YearMOD = new ColumnParameters("numberOfHaematomaUnder1YearMOD",
		        "Number and degree of Adverse Events Haematoma Under 1 year MOD", "age=<1|adEv=HMOD", "02");
		ColumnParameters numberOfHaematomaUnder1YearSEV = new ColumnParameters("numberOfHaematomaUnder1YearSEV",
		        "Number and degree of Adverse Events Haematoma Under 1 year SEV", "age=<1|adEv=HSEV", "03");
		ColumnParameters numberOfHaematoma1To9YearMIL = new ColumnParameters("numberOfHaematoma1To9YearMIL",
		        "Number and degree of Adverse Events Haematoma 1-9 Years MIL", "age=1-9|adEv=HMIL", "04");
		ColumnParameters numberOfHaematoma1To9YearMOD = new ColumnParameters("numberOfHaematoma1To9YearMOD",
		        "Number and degree of Adverse Events Haematoma 1-9 Years MOD", "age=1-9|adEv=HMOD", "05");
		ColumnParameters numberOfHaematoma1To9YearSEV = new ColumnParameters("numberOfHaematoma1To9YearSEV",
		        "Number and degree of Adverse Events Haematoma 1-9 Years SEV", "age=1-9|adEv=HSEV", "06");
		ColumnParameters numberOfHaematoma10To14YearMIL = new ColumnParameters("numberOfHaematoma10To14YearMIL",
		        "Number and degree of Adverse Events Haematoma 10-14 Years MIL", "age=10-14|adEv=HMIL", "07");
		ColumnParameters numberOfHaematoma10To14YearMOD = new ColumnParameters("numberOfHaematoma10To14YearMOD",
		        "Number and degree of Adverse Events Haematoma  10-14 Years MOD", "age=10-14|adEv=HMOD", "08");
		ColumnParameters numberOfHaematoma10To14YearSEV = new ColumnParameters("numberOfHaematoma10To14YearSEV",
		        "Number and degree of Adverse Events Haematoma 10-14 Years SEV", "age=10-14|adEv=HSEV", "09");
		ColumnParameters numberOfHaematoma15To19YearMIL = new ColumnParameters("numberOfHaematoma15To19YearMIL",
		        "Number and degree of Adverse Events Haematoma 15-19 Years MIL", "age=15-19|adEv=HMIL", "10");
		ColumnParameters numberOfHaematoma15To19YearMOD = new ColumnParameters("numberOfHaematoma15To19YearMOD",
		        "Number and degree of Adverse Events Haematoma 15-19 Years MOD", "age=15-19|adEv=HMOD", "11");
		ColumnParameters numberOfHaematoma15To19YearSEV = new ColumnParameters("numberOfHaematoma15To19YearSEV",
		        "Number and degree of Adverse Events Haematoma 15-19 Years SEV", "age=15-19|adEv=HSEV", "12");
		ColumnParameters numberOfHaematoma20To24YearsMIL = new ColumnParameters("numberOfHaematoma20To24YearsMIL",
		        "Number and degree of Adverse Events Haematoma 20-24 Years MIL", "age=20-24|adEv=HMIL", "13");
		ColumnParameters numberOfHaematoma20To24YearsMOD = new ColumnParameters("numberOfHaematoma20To24YearsMOD",
		        "Number and degree of Adverse Events Haematoma 20-24 Years MOD", "age=20-24|adEv=HMOD", "14");
		ColumnParameters numberOfHaematoma20To24YearsSEV = new ColumnParameters("numberOfHaematoma20To24YearsSEV",
		        "Number and degree of Adverse Events Haematoma 20-24 Years SEV", "age=20-24|adEv=HSEV", "15");
		ColumnParameters numberOfHaematoma25To29YearsMIL = new ColumnParameters("numberOfHaematoma25To29YearsMIL",
		        "Number and degree of Adverse Events Haematoma 25-29 Years MIL", "age=25-29|adEv=HMIL", "16");
		ColumnParameters numberOfHaematoma25To29YearsMOD = new ColumnParameters("numberOfHaematoma25To29YearsMOD",
		        "Number and degree of Adverse Events Haematoma 25-29 Years MOD", "age=25-29|adEv=HMOD", "17");
		ColumnParameters numberOfHaematoma25To29YearsSEV = new ColumnParameters("numberOfHaematoma25To29YearsSEV",
		        "Number and degree of Adverse Events Haematoma  25-29 Years SEV", "age=25-29|adEv=HSEV", "18");
		
		ColumnParameters numberOfHaematoma30To39YearsMIL = new ColumnParameters("numberOfHaematoma30To39YearsMIL",
		        "Number and degree of Adverse Events Haematoma 30-39 Years MIL", "age=30-39|adEv=HMIL", "19");
		ColumnParameters numberOfHaematoma30To39YearsMOD = new ColumnParameters("numberOfHaematoma30To39YearsMOD",
		        "Number and degree of Adverse Events Haematoma 30-39 Years MOD", "age=30-39|adEv=HMOD", "20");
		ColumnParameters numberOfHaematoma30To39YearsSEV = new ColumnParameters("numberOfHaematoma30To39YearsSEV",
		        "Number and degree of Adverse Events Haematoma 30-39 Years SEV", "age=30-39|adEv=HSEV", "21");
		ColumnParameters numberOfHaematoma40To49YearsMIL = new ColumnParameters("numberOfHaematoma40To49YearsMIL",
		        "Number and degree of Adverse Events Haematoma 40-49 Years MIL", "age=40-49|adEv=HMIL", "22");
		ColumnParameters numberOfHaematoma40To49YearsMOD = new ColumnParameters("numberOfHaematoma40To49YearsMOD",
		        "Number and degree of Adverse Events Haematoma 40-49 Years MOD", "age=40-49|adEv=HMOD", "23");
		ColumnParameters numberOfHaematoma40To49YearsSEV = new ColumnParameters("numberOfHaematoma40To49YearsSEV",
		        "Number and degree of Adverse Events Haematoma 40-49 Years SEv", "age=40-49|adEv=HSEV", "24");
		
		ColumnParameters numberOfHaematoma50PlusYearsMIL = new ColumnParameters("numberOfHaematoma50PlusYearsMIL",
		        "Number and degree of Adverse Events Haematoma 50+ Years MIL", "age=50+|adEv=HMIL", "25");
		ColumnParameters numberOfHaematoma50PlusYearsMOD = new ColumnParameters("numberOfHaematoma50PlusYearsMOD",
		        "Number and degree of Adverse Events Haematoma 50 Years MOD", "age=50+|adEv=HMOD", "26");
		ColumnParameters numberOfHaematoma50PlusYearsSEV = new ColumnParameters("numberOfHaematoma50PlusYearsSEV",
		        "Number and degree of Adverse Events Haematoma 50+ Years SEV", "age=50+|adEv=HSEV", "27");
		
		return Arrays.asList(numberOfHaematomaUnder1YearMIL, numberOfHaematomaUnder1YearMOD, numberOfHaematomaUnder1YearSEV,
		    numberOfHaematoma1To9YearMIL, numberOfHaematoma1To9YearMOD, numberOfHaematoma1To9YearSEV,
		    numberOfHaematoma10To14YearMIL, numberOfHaematoma10To14YearMOD, numberOfHaematoma10To14YearSEV,
		    numberOfHaematoma15To19YearMIL, numberOfHaematoma15To19YearMOD, numberOfHaematoma15To19YearSEV,
		    numberOfHaematoma20To24YearsMIL, numberOfHaematoma20To24YearsMOD, numberOfHaematoma20To24YearsSEV,
		    numberOfHaematoma25To29YearsMIL, numberOfHaematoma25To29YearsMOD, numberOfHaematoma25To29YearsSEV,
		    numberOfHaematoma30To39YearsMIL, numberOfHaematoma30To39YearsMOD, numberOfHaematoma30To39YearsSEV,
		    numberOfHaematoma40To49YearsMIL, numberOfHaematoma40To49YearsMOD, numberOfHaematoma40To49YearsSEV,
		    numberOfHaematoma50PlusYearsMIL, numberOfHaematoma50PlusYearsMOD, numberOfHaematoma50PlusYearsSEV);
	}
	
	public static List<ColumnParameters> getNumberAndDegreeOfAdverseEventsSmcReportDimensions3() {
		ColumnParameters numberOfInfectionUnder1YearMIL = new ColumnParameters("numberOfInfectionUnder1YearMIL",
		        "Number and degree of Adverse Events Infection Under 1 year MIL", "age=<1|adEv=IMIL", "01");
		ColumnParameters numberOfInfectionUnder1YearMOD = new ColumnParameters("numberOfInfectionUnder1YearMOD",
		        "Number and degree of Adverse Events Infection Under 1 year MOD", "age=<1|adEv=IMOD", "02");
		ColumnParameters numberOfInfectionUnder1YearSEV = new ColumnParameters("numberOfInfectionUnder1YearSEV",
		        "Number and degree of Adverse Events Infection Under 1 year SEV", "age=<1|adEv=ISEV", "03");
		ColumnParameters numberOfInfection1To9YearMIL = new ColumnParameters("numberOfInfection1To9YearMIL",
		        "Number and degree of Adverse Events Infection 1-9 Years MIL", "age=1-9|adEv=IMIL", "04");
		ColumnParameters numberOfInfection1To9YearMOD = new ColumnParameters("numberOfInfection1To9YearMOD",
		        "Number and degree of Adverse Events Infection 1-9 Years MOD", "age=1-9|adEv=IMOD", "05");
		ColumnParameters numberOfInfection1To9YearSEV = new ColumnParameters("numberOfInfection1To9YearSEV",
		        "Number and degree of Adverse Events Infection 1-9 Years SEV", "age=1-9|adEv=ISEV", "06");
		ColumnParameters numberOfInfection10To14YearMIL = new ColumnParameters("numberOfInfection10To14YearMIL",
		        "Number and degree of Adverse Events Infection 10-14 Years", "age=10-14|adEv=IMIL", "07");
		ColumnParameters numberOfInfection10To14YearMOD = new ColumnParameters("numberOfInfection10To14YearMOD",
		        "Number and degree of Adverse Events Infection  10-14 Years MOD", "age=10-14|adEv=IMOD", "08");
		ColumnParameters numberOfInfection10To14YearSEV = new ColumnParameters("numberOfInfection10To14YearSEV",
		        "Number and degree of Adverse Events Infection 10-14 Years SEV", "age=10-14|adEv=ISEV", "09");
		ColumnParameters numberOfInfection15To19YearMIL = new ColumnParameters("numberOfInfection15To19YearMIL",
		        "Number and degree of Adverse Events Infection 15-19 Years", "age=15-19|adEv=IMIL", "10");
		ColumnParameters numberOfInfection15To19YearMOD = new ColumnParameters("numberOfInfection15To19YearMOD",
		        "Number and degree of Adverse Events Infection 15-19 Years MOD", "age=15-19|adEv=IMOD", "11");
		ColumnParameters numberOfInfection15To19YearSEV = new ColumnParameters("numberOfInfection15To19YearSEV",
		        "Number and degree of Adverse Events Infection 15-19 Years SEV", "age=15-19|adEv=ISEV", "12");
		ColumnParameters numberOfInfection20To24YearsMIL = new ColumnParameters("numberOfInfection20To24YearsMIL",
		        "Number and degree of Adverse Events Infection20-24 Years MIL", "age=20-24|adEv=IMIL", "13");
		ColumnParameters numberOfInfection20To24YearsMOD = new ColumnParameters("numberOfInfection20To24YearsMOD",
		        "Number and degree of Adverse Events Infection 20-24 Years MOD", "age=20-24|adEv=IMOD", "14");
		ColumnParameters numberOfInfection20To24YearsSEV = new ColumnParameters("numberOfInfection20To24YearsSEV",
		        "Number and degree of Adverse Events Infection 20-24 Years SEV", "age=20-24|adEv=ISEV", "15");
		ColumnParameters numberOfInfection25To29YearsMIL = new ColumnParameters("numberOfInfection25To29YearsMIL",
		        "Number and degree of Adverse Events Infection 25-29 Years MIL", "age=25-29|adEv=IMIL", "16");
		ColumnParameters numberOfInfection25To29YearsMOD = new ColumnParameters("numberOfInfection25To29YearsMOD",
		        "Number and degree of Adverse Events Infection 25-29 Years MOD", "age=25-29|adEv=IMOD", "17");
		ColumnParameters numberOfInfection25To29YearsSEV = new ColumnParameters("numberOfInfection25To29YearsSEV",
		        "Number and degree of Adverse Events Infection  25-29 Years SEV", "age=25-29|adEv=ISEV", "18");
		
		ColumnParameters numberOfInfection30To39YearsMIL = new ColumnParameters("numberOfInfection30To39YearsMIL",
		        "Number and degree of Adverse Events Infection 30-39 Years MIL", "age=30-39|adEv=IMIL", "19");
		ColumnParameters numberOfInfection30To39YearsMOD = new ColumnParameters("numberOfInfection30To39YearsMOD",
		        "Number and degree of Adverse Events Infection 30-39 Years MOD", "age=30-39|adEv=IMOD", "20");
		ColumnParameters numberOfInfection30To39YearsSEV = new ColumnParameters("numberOfInfection30To39YearsSEV",
		        "Number and degree of Adverse Events Infection 30-39 Years SEV", "age=30-39|adEv=ISEV", "21");
		ColumnParameters numberOfInfection40To49YearsMIL = new ColumnParameters("numberOfInfection40To49YearsMIL",
		        "Number and degree of Adverse Events Infection 40-49 Years MIL", "age=40-49|adEv=IMIL", "22");
		ColumnParameters numberOfInfection40To49YearsMOD = new ColumnParameters("numberOfInfection40To49YearsMOD",
		        "Number and degree of Adverse Events Infection 40-49 Years MOD", "age=40-49|adEv=IMOD", "23");
		ColumnParameters numberOfInfection40To49YearsSEV = new ColumnParameters("numberOfInfection40To49YearsSEV",
		        "Number and degree of Adverse Events Infection 40-49 Years SEV", "age=40-49|adEv=ISEV", "24");
		
		ColumnParameters numberOfInfection50PlusYearsMIL = new ColumnParameters("numberOfInfection50PlusYearsMIL",
		        "Number and degree of Adverse Events Infection 50+ Years MIL", "age=50+|adEv=IMIL", "25");
		ColumnParameters numberOfInfection50PlusYearsMOD = new ColumnParameters("numberOfInfection50PlusYearsMOD",
		        "Number and degree of Adverse Events Infection 50 Years MOD", "age=50+|adEv=IMOD", "26");
		ColumnParameters numberOfInfection50PlusYearsSEV = new ColumnParameters("numberOfInfection50PlusYearsSEV",
		        "Number and degree of Adverse Events Infection 50+ Years SEV", "age=50+|adEv=ISEV", "27");
		
		return Arrays.asList(numberOfInfectionUnder1YearMIL, numberOfInfectionUnder1YearMOD, numberOfInfectionUnder1YearSEV,
		    numberOfInfection1To9YearMIL, numberOfInfection1To9YearMOD, numberOfInfection1To9YearSEV,
		    numberOfInfection10To14YearMIL, numberOfInfection10To14YearMOD, numberOfInfection10To14YearSEV,
		    numberOfInfection15To19YearMIL, numberOfInfection15To19YearMOD, numberOfInfection15To19YearSEV,
		    numberOfInfection20To24YearsMIL, numberOfInfection20To24YearsMOD, numberOfInfection20To24YearsSEV,
		    numberOfInfection25To29YearsMIL, numberOfInfection25To29YearsMOD, numberOfInfection25To29YearsSEV,
		    numberOfInfection30To39YearsMIL, numberOfInfection30To39YearsMOD, numberOfInfection30To39YearsSEV,
		    numberOfInfection40To49YearsMIL, numberOfInfection40To49YearsMOD, numberOfInfection40To49YearsSEV,
		    numberOfInfection50PlusYearsMIL, numberOfInfection50PlusYearsMOD, numberOfInfection50PlusYearsSEV);
	}
	
	public static List<ColumnParameters> getART_1_Dimensions() {
		//male <1 year old
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period Under 1 year C Male",
		        "age=<1|gender=M|type=C", "01");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period Under 1 year CN Male",
		        "age=<1|gender=M|type=CN", "02");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 1 to 4 years C Male",
		        "age=1-4|gender=M|type=C", "03");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 1 to 4 years CN Male",
		        "age=1-4|gender=M|type=CN", "04");
		
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 5 to 9 years C Male",
		        "age=5-9|gender=M|type=C", "05");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 5 to 9 years CN Male",
		        "age=5-9|gender=M|type=CN", "06");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 10 to 12 years C Male",
		        "age=10-12|gender=M|type=C", "07");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 10 to 12 years CN Male",
		        "age=10-12|gender=M|type=CN", "08");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 13 to 14 years C Male",
		        "age=13-14|gender=M|type=C", "09");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 13 to 14 years CN Male",
		        "age=13-14|gender=M|type=CN", "10");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 15 to 19 years C Male",
		        "age=15-19|gender=M|type=C", "11");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 15 to 19 years CN Male",
		        "age=15-19|gender=M|type=CN", "12");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 20 to 24 years C Male",
		        "age=20-24|gender=M|type=C", "13");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 20 to 24 years CN Male",
		        "age=20-24|gender=M|type=CN", "14");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 25 to 29 years C Male",
		        "age=25-29|gender=M|type=C", "15");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 25 to 29 years CN Male",
		        "age=25-29|gender=M|type=CN", "16");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 30 to 34 years C Male",
		        "age=30-34|gender=M|type=C", "17");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 30 to 34 years CN Male",
		        "age=30-34|gender=M|type=CN", "18");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 35 to 39 years C Male",
		        "age=35-39|gender=M|type=C", "19");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 35 to 39 years CN Male",
		        "age=35-39|gender=M|type=CN", "20");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 40 to 44 years C Male",
		        "age=40-44|gender=M|type=C", "21");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 40 to 44 years CN Male",
		        "age=40-44|gender=M|type=CN", "22");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 45 to 49 years C Male",
		        "age=45-49|gender=M|type=C", "23");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 45 to 49 years CN Male",
		        "age=45-49|gender=M|type=CN", "24");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 50 to 54 years C Male",
		        "age=50-54|gender=M|type=C", "25");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 50 to 54 years CN Male",
		        "age=50-54|gender=M|type=CN", "26");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 55 to 59 years C Male",
		        "age=55-59|gender=M|type=C", "27");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 55 to 59years CN Male",
		        "age=55-59|gender=M|type=CN", "28");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 60 to 64 years C Male",
		        "age=60-64|gender=M|type=C", "29");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 60 to 64years CN Male",
		        "age=60-64|gender=M|type=CN", "30");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 65 to 69 years C Male",
		        "age=65-69|gender=M|type=C", "31");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 65 to 69 years CN Male",
		        "age=65-69|gender=M|type=CN", "32");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 70 to 74 years C Male",
		        "age=70-74|gender=M|type=C", "33");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 70 to 74 years CN Male",
		        "age=70-74|gender=M|type=CN", "34");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 75 to 79 years C Male",
		        "age=75-79|gender=M|type=C", "35");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 75 to 79 years CN Male",
		        "age=75-79|gender=M|type=CN", "36");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 80 to 84 years C Male",
		        "age=80-84|gender=M|type=C", "37");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 80 to 84 years CN Male",
		        "age=80-84|gender=M|type=CN", "38");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 85+ years C Male",
		        "age=85+|gender=M|type=C", "39");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period 85+ years CN Male",
		        "age=85+|gender=M|type=CN", "40");
		
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncMale",
		        "Number of  adults and children newly initiated on ART during the reporting period unknown years C Male",
		        "age=UK|gender=M|type=C", "41");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period unknown years CN Male",
		        "age=UK|gender=M|type=CN", "42");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcMale",
		        "Number of  adults and children newly initiated on ART during the reporting period totals C Male",
		        "gender=M|type=C", "43");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnMale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnMale",
		        "Number of  adults and children newly initiated on ART during the reporting period totals CN Male",
		        "gender=M|type=CN", "44");
		
		ColumnParameters NumberOfAdultsAndCildrenPlwdMale = new ColumnParameters("NumberOfAdultsAndCildrenPlwdMale",
		        "Number of  adults and children PLWD Male ", "gender=M|other=PLWD", "45");
		
		//female
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period Under 1 year C Female",
		        "age=<1|gender=F|type=C", "46");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period Under 1 year CN Female",
		        "age=<1|gender=F|type=CN", "47");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 1 to 4 years C Female",
		        "age=1-4|gender=F|type=C", "48");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 1 to 4 years CN Female",
		        "age=1-4|gender=F|type=CN", "49");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 5 to 9 years C female",
		        "age=5-9|gender=F|type=C", "50");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 5 to 9 years CN female",
		        "age=5-9|gender=F|type=CN", "51");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 10 to 12 years C female",
		        "age=10-12|gender=F|type=C", "52");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 10 to 12 years CN female",
		        "age=10-12|gender=F|type=CN", "53");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 13 to 14 years C female",
		        "age=13-14|gender=F|type=C", "54");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 13 to 14 years CN female",
		        "age=13-14|gender=F|type=CN", "55");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 15 to 19 years C female",
		        "age=15-19|gender=F|type=C", "56");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 15 to 19 years CN female",
		        "age=15-19|gender=F|type=CN", "57");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 20 to 24 years C female",
		        "age=20-24|gender=F|type=C", "58");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 20 to 24 years CN female",
		        "age=20-24|gender=F|type=CN", "59");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 25 to 29 years C female",
		        "age=25-29|gender=F|type=C", "60");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 25 to 29 years CN female",
		        "age=25-29|gender=F|type=CN", "61");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 30 to 34 years C female",
		        "age=30-34|gender=F|type=C", "62");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 30 to 34 years CN female",
		        "age=30-34|gender=F|type=CN", "63");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 35 to 39 years C female",
		        "age=35-39|gender=F|type=C", "64");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 35 to 39 years CN female",
		        "age=35-39|gender=F|type=CN", "65");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 40 to 44 years C female",
		        "age=40-44|gender=F|type=C", "66");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 40 to 44 years CN female",
		        "age=40-44|gender=F|type=CN", "67");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 45 to 49 years C female",
		        "age=45-49|gender=F|type=C", "68");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 45 to 49 years CN female",
		        "age=45-49|gender=F|type=CN", "69");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 50 to 54 years C female",
		        "age=50-54|gender=F|type=C", "70");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 50 to 54 years CN female",
		        "age=50-54|gender=F|type=CN", "71");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 55 to 59 years C female",
		        "age=55-59|gender=F|type=C", "72");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 55 to 59years CN female",
		        "age=55-59|gender=F|type=CN", "73");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 60 to 64 years C female",
		        "age=60-64|gender=F|type=C", "74");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 60 to 64years CN female",
		        "age=60-64|gender=F|type=CN", "75");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 65 to 69 years C female",
		        "age=65-69|gender=F|type=C", "76");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 65 to 69 years CN female",
		        "age=65-69|gender=F|type=CN", "77");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 70 to 74 years C female",
		        "age=70-74|gender=F|type=C", "78");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 70 to 74 years CN female",
		        "age=70-74|gender=F|type=CN", "79");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 75 to 79 years C female",
		        "age=75-79|gender=F|type=C", "80");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 75 to 79 years CN female",
		        "age=75-79|gender=F|type=CN", "81");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 80 to 84 years C female",
		        "age=80-84|gender=F|type=C", "82");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 80 to 84 years CN female",
		        "age=80-84|gender=F|type=CN", "83");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 85+ years C female",
		        "age=85+|gender=F|type=C", "84");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period 85+ years CN female",
		        "age=85+|gender=F|type=CN", "85");
		
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period unknown years C female",
		        "age=UK|gender=F|type=C", "86");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period unknown years CN female",
		        "age=UK|gender=F|type=CN", "87");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period totals C female",
		        "gender=F|type=C", "88");
		ColumnParameters NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnFemale = new ColumnParameters(
		        "NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnFemale",
		        "Number of  adults and children newly initiated on ART during the reporting period totals CN female",
		        "gender=F|type=CN", "89");
		
		ColumnParameters NumberOfAdultsAndCildrenPlwdFemale = new ColumnParameters("NumberOfAdultsAndCildrenPlwdFemale",
		        "Number of  adults and children PLWD female ", "gender=F|other=PLWD", "90");
		ColumnParameters NumberOfAdultsAndCildrenPregnantFemale = new ColumnParameters(
		        "NumberOfAdultsAndCildrenPregnantFemale", "Number of  adults and children pregnant female ",
		        "gender=F|other=PRG", "91");
		
		//add the columns to the array
		return Arrays.asList(NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnMale, NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnMale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcMale, NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnMale,
		    NumberOfAdultsAndCildrenPlwdMale,
		    
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnder1cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArt1To4cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr1To4cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr5To9cnnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr10To12cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr13To14cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr15To19cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr20To24cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr25To29cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr30To34cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr35To39cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr40To44cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr45To49cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr50To54cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr55To59cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr60To64cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr65To69cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr70To74cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr75To79cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr80To84cnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnAr85PluscnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtUnknowncFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArtunknowncnFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalcFemale,
		    NumberOfAdultsAndChildrenNewlyInitiatedOnArTotalscnFemale, NumberOfAdultsAndCildrenPlwdFemale,
		    NumberOfAdultsAndCildrenPregnantFemale);
		
	}
	
	public static List<ColumnParameters> getART_1_14_Dimensions() {
		
		ColumnParameters onArtUnder1cMale = new ColumnParameters("OnArtUnder1cMale",
		        "Number on ART during the reporting period Under 1 year C Male", "age=<1|gender=M|type=C", "01");
		ColumnParameters onArtUnder1cnMale = new ColumnParameters("OnArtUnder1cnMale",
		        "Number on ART during the reporting period Under 1 year CN Male", "age=<1|gender=M|type=CN", "02");
		ColumnParameters onArt1To4cMale = new ColumnParameters("onArt1To4cMale",
		        "Number on ART during the reporting period 1 to 4 years C Male", "age=1-4|gender=M|type=C", "03");
		ColumnParameters onAr1To4cnMale = new ColumnParameters("onAr1To4cnMale",
		        "Number on ART during the reporting period 1 to 4 years CN Male", "age=1-4|gender=M|type=CN", "04");
		
		ColumnParameters onAr5To9cnMale = new ColumnParameters("onAr5To9cnMale",
		        "Number on ART during the reporting period 5 to 9 years C Male", "age=5-9|gender=M|type=C", "05");
		ColumnParameters onAr5To9cnnMale = new ColumnParameters("onAr5To9cnnMale",
		        "Number on ART during the reporting period 5 to 9 years CN Male", "age=5-9|gender=M|type=CN", "06");
		ColumnParameters onAr10To12cMale = new ColumnParameters("onAr10To12cMale",
		        "Number on ART during the reporting period 10 to 12 years C Male", "age=10-12|gender=M|type=C", "07");
		ColumnParameters onAr10To12cnMale = new ColumnParameters("onAr10To12cnMale",
		        "Number on ART during the reporting period 10 to 12 years CN Male", "age=10-12|gender=M|type=CN", "08");
		ColumnParameters onAr13To14cMale = new ColumnParameters("onAr13To14cMale",
		        "Number on ART during the reporting period 13 to 14 years C Male", "age=13-14|gender=M|type=C", "09");
		ColumnParameters onAr13To14cnMale = new ColumnParameters("onAr13To14cnMale",
		        "Number on ART during the reporting period 13 to 14 years CN Male", "age=13-14|gender=M|type=CN", "10");
		ColumnParameters onAr15To19cMale = new ColumnParameters("onAr15To19cMale",
		        "Number on ART during the reporting period 15 to 19 years C Male", "age=15-19|gender=M|type=C", "11");
		ColumnParameters onAr15To19cnMale = new ColumnParameters("onAr15To19cnMale",
		        "Number  on ART during the reporting period 15 to 19 years CN Male", "age=15-19|gender=M|type=CN", "12");
		ColumnParameters onAr20To24cMale = new ColumnParameters("onAr20To24cMale",
		        "Number  on ART during the reporting period 20 to 24 years C Male", "age=20-24|gender=M|type=C", "13");
		ColumnParameters onAr20To24cnMale = new ColumnParameters("onAr20To24cnMale",
		        "Number on ART during the reporting period 20 to 24 years CN Male", "age=20-24|gender=M|type=CN", "14");
		ColumnParameters onAr25To29cMale = new ColumnParameters("onAr25To29cMale",
		        "Number on ART during the reporting period 25 to 29 years C Male", "age=25-29|gender=M|type=C", "15");
		ColumnParameters onAr25To29cnMale = new ColumnParameters("onAr25To29cnMale",
		        "Number on ART during the reporting period 25 to 29 years CN Male", "age=25-29|gender=M|type=CN", "16");
		ColumnParameters onAr30To34cMale = new ColumnParameters("onAr30To34cMale",
		        "Number on ART during the reporting period 30 to 34 years C Male", "age=30-34|gender=M|type=C", "17");
		ColumnParameters onAr30To34cnMale = new ColumnParameters("onAr30To34cnMale",
		        "Number on ART during the reporting period 30 to 34 years CN Male", "age=30-34|gender=M|type=CN", "18");
		ColumnParameters onAr35To39cMale = new ColumnParameters("onAr35To39cMale",
		        "Number on ART during the reporting period 35 to 39 years C Male", "age=35-39|gender=M|type=C", "19");
		ColumnParameters onAr35To39cnMale = new ColumnParameters("onAr35To39cnMale",
		        "Number on ART during the reporting period 35 to 39 years CN Male", "age=35-39|gender=M|type=CN", "20");
		ColumnParameters onAr40To44cMale = new ColumnParameters("onAr40To44cMale",
		        "Number on ART during the reporting period 40 to 44 years C Male", "age=40-44|gender=M|type=C", "21");
		ColumnParameters onAr40To44cnMale = new ColumnParameters("onAr40To44cnMale",
		        "Number on ART during the reporting period 40 to 44 years CN Male", "age=40-44|gender=M|type=CN", "22");
		ColumnParameters onAr45To49cMale = new ColumnParameters("onAr45To49cMale",
		        "Number on ART during the reporting period 45 to 49 years C Male", "age=45-49|gender=M|type=C", "23");
		ColumnParameters onAr45To49cnMale = new ColumnParameters("onAr45To49cnMale",
		        "Number on ART during the reporting period 45 to 49 years CN Male", "age=45-49|gender=M|type=CN", "24");
		ColumnParameters onAr50PluscnMale = new ColumnParameters("onAr50PluscnMale",
		        "Number on ART during the reporting period 50+ years C Male", "age=50+|gender=M|type=C", "25");
		ColumnParameters onAr50PluscnnMale = new ColumnParameters("onAr50PluscnnMale",
		        "Number on ART during the reporting period 50+ years CN Male", "age=50+|gender=M|type=CN", "26");
		ColumnParameters onArtUnknowncMale = new ColumnParameters("onArtUnknowncMale",
		        "Number on ART during the reporting period unknown years C Male", "age=UK|gender=M|type=C", "27");
		ColumnParameters onArtunknowncnMale = new ColumnParameters("onArtunknowncnMale",
		        "Number on ART during the reporting period unknown years CN Male", "age=UK|gender=M|type=CN", "28");
		ColumnParameters onArTotalcMale = new ColumnParameters("onArTotalcMale",
		        "Number on ART during the reporting period totals C Male", "gender=M|type=C", "29");
		ColumnParameters onArTotalscnMale = new ColumnParameters("onArTotalscnMale",
		        "Number on ART during the reporting period totals CN Male", "gender=M|type=CN", "30");
		
		ColumnParameters numberOfAdultsAndCildrenPlwdMale = new ColumnParameters("NumberOfAdultsAndCildrenPlwdMale",
		        "Number of  adults and children PLWD Male ", "gender=M|other=PLWD", "31");
		
		//female
		ColumnParameters onArtUnder1cFemale = new ColumnParameters("onArtUnder1cFemale",
		        "Number on ART during the reporting period Under 1 year C female", "age=<1|gender=F|type=C", "32");
		ColumnParameters onArtUnder1cnFemale = new ColumnParameters("onArtUnder1cnFemale",
		        "Number on ART during the reporting period Under 1 year CN female", "age=<1|gender=F|type=CN", "33`");
		ColumnParameters onArt1To4cFemale = new ColumnParameters("onArt1To4cFemale",
		        "Number on ART during the reporting period 1 to 4 years C female", "age=1-4|gender=F|type=C", "34");
		ColumnParameters onAr1To4cnFemale = new ColumnParameters("onAr1To4cnFemale",
		        "Number on ART during the reporting period 1 to 4 years CN female", "age=1-4|gender=F|type=CN", "35");
		
		ColumnParameters onAr5To9cnFemale = new ColumnParameters("onAr5To9cnFemale",
		        "Number on ART during the reporting period 5 to 9 years C female", "age=5-9|gender=F|type=C", "36");
		ColumnParameters onAr5To9cnnFemale = new ColumnParameters("onAr5To9cnnFemale",
		        "Number on ART during the reporting period 5 to 9 years CN female", "age=5-9|gender=F|type=CN", "37");
		ColumnParameters onAr10To12cFemale = new ColumnParameters("onAr10To12cFemale",
		        "Number on ART during the reporting period 10 to 12 years C female", "age=10-12|gender=F|type=C", "38");
		ColumnParameters onAr10To12cnFemale = new ColumnParameters("onAr10To12cnFemale",
		        "Number on ART during the reporting period 10 to 12 years CN female", "age=10-12|gender=F|type=CN", "39");
		ColumnParameters onAr13To14cFemale = new ColumnParameters("onAr13To14cFemale",
		        "Number on ART during the reporting period 13 to 14 years C female", "age=13-14|gender=F|type=C", "40");
		ColumnParameters onAr13To14cnFemale = new ColumnParameters("onAr13To14cnFemale",
		        "Number on ART during the reporting period 13 to 14 years CN female", "age=13-14|gender=F|type=CN", "41");
		ColumnParameters onAr15To19cFemale = new ColumnParameters("onAr15To19cFemale",
		        "Number on ART during the reporting period 15 to 19 years C female", "age=15-19|gender=F|type=C", "42");
		ColumnParameters onAr15To19cnFemale = new ColumnParameters("onAr15To19cnFemale",
		        "Number  on ART during the reporting period 15 to 19 years CN female", "age=15-19|gender=F|type=CN", "43");
		ColumnParameters onAr20To24cFemale = new ColumnParameters("onAr20To24cFemale",
		        "Number  on ART during the reporting period 20 to 24 years C female", "age=20-24|gender=F|type=C", "44");
		ColumnParameters onAr20To24cnFemale = new ColumnParameters("onAr20To24cnFemale",
		        "Number on ART during the reporting period 20 to 24 years CN female", "age=20-24|gender=F|type=CN", "45");
		ColumnParameters onAr25To29cFemale = new ColumnParameters("onAr25To29cFemale",
		        "Number on ART during the reporting period 25 to 29 years C female", "age=25-29|gender=F|type=C", "46");
		ColumnParameters onAr25To29cnFemale = new ColumnParameters("onAr25To29cnFemale",
		        "Number on ART during the reporting period 25 to 29 years CN female", "age=25-29|gender=F|type=CN", "47");
		ColumnParameters onAr30To34cFemale = new ColumnParameters("onAr30To34cFemale",
		        "Number on ART during the reporting period 30 to 34 years C female", "age=30-34|gender=F|type=C", "48");
		ColumnParameters onAr30To34cnFemale = new ColumnParameters("onAr30To34cnFemale",
		        "Number on ART during the reporting period 30 to 34 years CN female", "age=30-34|gender=F|type=CN", "49");
		ColumnParameters onAr35To39cFemale = new ColumnParameters("onAr35To39cFemale",
		        "Number on ART during the reporting period 35 to 39 years C female", "age=35-39|gender=F|type=C", "50");
		ColumnParameters onAr35To39cnFemale = new ColumnParameters("onAr35To39cnFemale",
		        "Number on ART during the reporting period 35 to 39 years CN female", "age=35-39|gender=F|type=CN", "51");
		ColumnParameters onAr40To44cFeale = new ColumnParameters("onAr40To44cFeale",
		        "Number on ART during the reporting period 40 to 44 years C female", "age=40-44|gender=F|type=C", "52");
		ColumnParameters onAr40To44cnFemale = new ColumnParameters("onAr40To44cnFemale",
		        "Number on ART during the reporting period 40 to 44 years CN female", "age=40-44|gender=F|type=CN", "53");
		ColumnParameters onAr45To49cFemale = new ColumnParameters("onAr45To49cFemale",
		        "Number on ART during the reporting period 45 to 49 years C female", "age=45-49|gender=F|type=C", "54");
		ColumnParameters onAr45To49cnFemale = new ColumnParameters("onAr45To49cnFemale",
		        "Number on ART during the reporting period 45 to 49 years CN female", "age=45-49|gender=F|type=CN", "55");
		ColumnParameters onAr50PluscnFemale = new ColumnParameters("onAr50PluscnFemale",
		        "Number on ART during the reporting period 50+ years C female", "age=50+|gender=F|type=C", "56");
		ColumnParameters onAr50PluscnnFemale = new ColumnParameters("onAr50PluscnnFemale",
		        "Number on ART during the reporting period 50+ years CN female", "age=50+|gender=F|type=CN", "57");
		ColumnParameters onArtUnknowncFemale = new ColumnParameters("onArtUnknowncFemale",
		        "Number on ART during the reporting period unknown years C female", "age=UK|gender=F|type=C", "58");
		ColumnParameters onArtunknowncnFemale = new ColumnParameters("onArtunknowncnFemale",
		        "Number on ART during the reporting period unknown years CN female", "age=UK|gender=F|type=CN", "59");
		ColumnParameters onArTotalcFemale = new ColumnParameters("onArTotalcFemale",
		        "Number on ART during the reporting period totals C female", "gender=F|type=C", "60");
		ColumnParameters onArTotalscnFemale = new ColumnParameters("onArTotalscnFemale",
		        "Number on ART during the reporting period totals CN female", "gender=F|type=CN", "61");
		
		ColumnParameters numberOfAdultsAndCildrenPlwdFemale = new ColumnParameters("numberOfAdultsAndCildrenPlwdFemale",
		        "Number of  adults and children PLWD female ", "gender=M|other=PLWD", "62");
		ColumnParameters NumberOfAdultsAndCildrenPregnantFemale = new ColumnParameters(
		        "NumberOfAdultsAndCildrenPregnantFemale", "Number of  adults and children pregnant female ",
		        "gender=F|other=PRG", "63");
		
		return Arrays.asList(onArtUnder1cMale, onArtUnder1cnMale, onArt1To4cMale, onAr1To4cnMale, onAr5To9cnMale,
		    onAr5To9cnnMale, onAr10To12cMale, onAr10To12cnMale, onAr13To14cMale, onAr13To14cnMale, onAr15To19cMale,
		    onAr15To19cnMale, onAr20To24cMale, onAr20To24cnMale, onAr25To29cMale, onAr25To29cnMale, onAr30To34cMale,
		    onAr30To34cnMale, onAr35To39cMale, onAr35To39cnMale, onAr40To44cMale, onAr40To44cnMale, onAr45To49cMale,
		    onAr45To49cnMale, onAr50PluscnMale, onAr50PluscnnMale, onArtUnknowncMale, onArtunknowncnMale, onArTotalcMale,
		    onArTotalscnMale, numberOfAdultsAndCildrenPlwdMale, onArtUnder1cFemale, onArtUnder1cnFemale, onArt1To4cFemale,
		    onAr1To4cnFemale, onAr5To9cnFemale, onAr5To9cnnFemale, onAr10To12cFemale, onAr10To12cnFemale, onAr13To14cFemale,
		    onAr13To14cnFemale, onAr15To19cFemale, onAr15To19cnFemale, onAr20To24cFemale, onAr20To24cnFemale,
		    onAr25To29cFemale, onAr25To29cnFemale, onAr30To34cFemale, onAr30To34cnFemale, onAr35To39cFemale,
		    onAr35To39cnFemale, onAr40To44cFeale, onAr40To44cnFemale, onAr45To49cFemale, onAr45To49cnFemale,
		    onAr50PluscnFemale, onAr50PluscnnFemale, onArtUnknowncFemale, onArtunknowncnFemale, onArTotalcFemale,
		    onArTotalscnFemale, numberOfAdultsAndCildrenPlwdFemale, NumberOfAdultsAndCildrenPregnantFemale);
		
	}
	
	public static List<ColumnParameters> getART_15_16_Dimensions() {
		ColumnParameters onArt10To12Female = new ColumnParameters("onArt10To12Female",
		        "Number of registered HIV+ female patients during the reporting period 10 to 12 female",
		        "age=10-12|gender=F", "01");
		ColumnParameters onArt13To14Female = new ColumnParameters("onArt13To14Female",
		        "Number of registered HIV+ female patients  during the reporting period 13 to 14 female",
		        "age=13-14|gender=F", "02");
		ColumnParameters onArt15To19Female = new ColumnParameters("onArt15To19Female",
		        "Number of registered HIV+ female patients  during the reporting period 15 to 19 female",
		        "age=15-19|gender=F", "03");
		ColumnParameters onArt20To24Female = new ColumnParameters("onArt20To24Female",
		        "Number of registered HIV+ female patients  during the reporting period 20 to 24 female",
		        "age=20-24|gender=F", "04");
		ColumnParameters onArt25To29Female = new ColumnParameters("onArt25To29Female",
		        "Number of registered HIV+ female patients  during the reporting period 25 to 29 female",
		        "age=25-29|gender=F", "05");
		ColumnParameters onArt30To34Female = new ColumnParameters("onArt30To34Female",
		        "Number of registered HIV+ female patients  during the reporting period 30 to 34 female",
		        "age=30-34|gender=F", "06");
		ColumnParameters onArt35To39Female = new ColumnParameters("onArt35To39Female",
		        "Number of registered HIV+ female patients  during the reporting period 35 to 39 female",
		        "age=35-39|gender=F", "07");
		ColumnParameters onArt40To44Female = new ColumnParameters("onArt40To44Female",
		        "Number of registered HIV+ female patients  during the reporting period 40 to 44 female",
		        "age=40-44|gender=F", "08");
		ColumnParameters onArt45To49Female = new ColumnParameters("onArt45To49Female",
		        "Number of registered HIV+ female patients  during the reporting period 45 to 49 female",
		        "age=45-49|gender=F", "09");
		ColumnParameters onArt50PlusFemale = new ColumnParameters("onArt50PlusFemale",
		        "Number of registered HIV+ female patients  during the reporting period 50+ female", "age=50+|gender=F",
		        "10");
		ColumnParameters onArtUnknownFemale = new ColumnParameters("onArtUnknownFemale",
		        "Number of registered HIV+ female patients  during the reporting period unknown female", "age=UK|gender=F",
		        "11");
		ColumnParameters onArtTotalFemale = new ColumnParameters("onArtTotalFemale",
		        "Number of registered HIV+ female patients  during the reporting period total female", "gender=F", "12");
		
		return Arrays.asList(onArt10To12Female, onArt13To14Female, onArt15To19Female, onArt20To24Female, onArt25To29Female,
		    onArt30To34Female, onArt35To39Female, onArt40To44Female, onArt45To49Female, onArt50PlusFemale,
		    onArtUnknownFemale, onArtTotalFemale);
	}
	
	public static List<ColumnParameters> getHtsTypeWithGenderAndAgeDisaggregation() {
		ColumnParameters htnUnder1CitizenMen = new ColumnParameters("htnUnder1CitizenMen", "HTS Under 1 year Citizen Men",
		        "age=<1|type=C|gender=M", "01");
		ColumnParameters htnUnder1CitizenFemale = new ColumnParameters("htnUnder1CitizenFemale",
		        "HTS Under 1 year Citizen Female", "age=<1|type=C|gender=F", "02");
		ColumnParameters htn1To4CitizenMen = new ColumnParameters("htn1To4CitizenMen", "HTS  1 to 4 years Citizen Men",
		        "age=1-4|type=C|gender=M", "03");
		ColumnParameters htn1To4CitizenFemale = new ColumnParameters("htn1To4CitizenFemale",
		        "HTS  1 to 4 years Citizen Female", "age=1-4|type=C|gender=F", "04");
		ColumnParameters htn5To9CitizenMen = new ColumnParameters("htn5To9CitizenMen", "HTS  5 to 9 years Citizen Men",
		        "age=5-9|type=C|gender=M", "05");
		ColumnParameters htn5To9CitizenFemale = new ColumnParameters("htn5To9CitizenFemale",
		        "HTS  5 to 9 years Citizen Female", "age=5-9|type=C|gender=F", "06");
		ColumnParameters htn10To14CitizenMen = new ColumnParameters("htn10To14CitizenMen",
		        "HTS  10 to 14 years Citizen men", "age=10-14|type=C|gender=M", "07");
		ColumnParameters htn10To14CitizenFemale = new ColumnParameters("htn10To14CitizenFemale",
		        "HTS  10 to 14 years Citizen female", "age=10-14|type=C|gender=F", "08");
		ColumnParameters htn15To19CitizenMen = new ColumnParameters("htn15To19CitizenMen",
		        "HTS  15 to 19 years Citizen men", "age=15-19|type=C|gender=M", "09");
		ColumnParameters htn15To19CitizenFemale = new ColumnParameters("htn15To19CitizenFemale",
		        "HTS  15 to 19 years Citizen female", "age=15-19|type=C|gender=F", "10");
		ColumnParameters htn20To24CitizenMen = new ColumnParameters("htn20To24CitizenMen",
		        "HTS  20 to 24 years Citizen men", "age=20-24|type=C|gender=M", "11");
		ColumnParameters htn20To24CitizenFemale = new ColumnParameters("htn20To24CitizenFemale",
		        "HTS  20 to 24 years Citizen female", "age=20-24|type=C|gender=F", "12");
		ColumnParameters htn25To29CitizenMen = new ColumnParameters("htn25To29CitizenMen",
		        "HTS  25 to 29 years Citizen men", "age=25-29|type=C|gender=M", "13");
		ColumnParameters htn25To29CitizenFemale = new ColumnParameters("htn25To29CitizenFemale",
		        "HTS  25 to 29 years Citizen female", "age=25-29|type=C|gender=F", "14");
		ColumnParameters htn30To34CitizenMen = new ColumnParameters("htn30To34CitizenMen",
		        "HTS  30 to 34 years Citizen men", "age=30-34|type=C|gender=M", "15");
		ColumnParameters htn30To34CitizenFemale = new ColumnParameters("htn30To34CitizenFemale",
		        "HTS  30 to 34 years Citizen female", "age=30-34|type=C|gender=F", "16");
		ColumnParameters htn35To39CitizenMen = new ColumnParameters("htn35To39CitizenMen",
		        "HTS  35 to 39 years Citizen men", "age=35-39|type=C|gender=M", "17");
		ColumnParameters htn35To39CitizenFemale = new ColumnParameters("htn35To39CitizenFemale",
		        "HTS  35 to 39 years Citizen female", "age=35-39|type=C|gender=F", "18");
		ColumnParameters htn40To44CitizenMen = new ColumnParameters("htn40To44CitizenMen",
		        "HTS  40 to 44 years Citizen men", "age=40-44|type=C|gender=M", "19");
		ColumnParameters htn40To44CitizenFemale = new ColumnParameters("htn40To44CitizenFemale",
		        "HTS  40 to 44 years Citizen female", "age=40-44|type=C|gender=F", "20");
		ColumnParameters htn45To49CitizenMen = new ColumnParameters("htn45To49CitizenMen",
		        "HTS  45 to 49 years Citizen men", "age=45-49|type=C|gender=M", "21");
		ColumnParameters htn45To49CitizenFemale = new ColumnParameters("htn45To49CitizenFemale",
		        "HTS  45 to 49 years Citizen female", "age=45-49|type=C|gender=F", "22");
		ColumnParameters htn50To54CitizenMen = new ColumnParameters("htn50To54CitizenMen",
		        "HTS  50 to 54 years Citizen men", "age=50-54|type=C|gender=M", "23");
		ColumnParameters htn50To54CitizenFemale = new ColumnParameters("htn50To54CitizenFemale",
		        "HTS  50 to 54 years Citizen female", "age=50-54|type=C|gender=F", "24");
		ColumnParameters htn55To59CitizenMen = new ColumnParameters("htn55To59CitizenMen",
		        "HTS  55 to 59 years Citizen men", "age=55-59|type=C|gender=M", "25");
		ColumnParameters htn55To59CitizenFemale = new ColumnParameters("htn55To59CitizenFemale",
		        "HTS  55 to 59 years Citizen female", "age=55-59|type=C|gender=F", "26");
		ColumnParameters htn60AndAboveCitizenMen = new ColumnParameters("htn60AndAboveCitizenMen",
		        "HTS  60+ years Citizen men", "age=60+|type=C|gender=M", "27");
		ColumnParameters htn60AndAboveCitizenFemale = new ColumnParameters("htn60AndAboveCitizenFemale",
		        "HTS  60+ years Citizen female", "age=60+|type=C|gender=F", "28");
		ColumnParameters htnTotalsCitizenMen = new ColumnParameters("htnTotalsCitizenMen", "HTS total Citizen men",
		        "type=C|gender=M", "29");
		ColumnParameters htnTotalsCitizenFemale = new ColumnParameters("htnTotalsCitizenMen", "HTS total Citizen female",
		        "type=C|gender=F", "30");
		ColumnParameters htnUnder1NonCitizenMen = new ColumnParameters("htnUnder1NonCitizenMen",
		        "HTS Under 1 year Non Citizen men", "age=<1|type=CN|gender=M", "31");
		ColumnParameters htnUnder1NonCitizenFemale = new ColumnParameters("htnUnder1NonCitizenFemale",
		        "HTS Under 1 year Non Citizen female", "age=<1|type=CN|gender=F", "32");
		ColumnParameters htn1To4NonCitizenMen = new ColumnParameters("htn1To4NonCitizenMen",
		        "HTS 1 to 4 years Non Citizen men", "age=1-4|type=CN|gender=M", "33");
		ColumnParameters htn1To4NonCitizenFemale = new ColumnParameters("htn1To4NonCitizenFemale",
		        "HTS 1 to 4 years Non Citizen female", "age=1-4|type=CN|gender=F", "34");
		ColumnParameters htn5To9NonCitizenMen = new ColumnParameters("htn5To9NonCitizenMen",
		        "HTS 5 to 9 years Non Citizen men", "age=5-9|type=CN|gender=M", "35");
		ColumnParameters htn5To9NonCitizenFemale = new ColumnParameters("htn5To9NonCitizenFemale",
		        "HTS 5 to 9 years Non Citizen female", "age=5-9|type=CN|gender=F", "36");
		ColumnParameters htn10To14NonCitizenMen = new ColumnParameters("htn10To14NonCitizenMen",
		        "HTS 10 to 14 years Non Citizen men", "age=10-14|type=CN|gender=M", "37");
		ColumnParameters htn10To14NonCitizenFemale = new ColumnParameters("htn10To14NonCitizenFemale",
		        "HTS 10 to 14 years Non Citizen female", "age=10-14|type=CN|gender=F", "38");
		ColumnParameters htn15To19NonCitizenMen = new ColumnParameters("htn15To19NonCitizenMen",
		        "HTS 15 to 19 years Non Citizen men", "age=15-19|type=CN|gender=M", "39");
		ColumnParameters htn15To19NonCitizenFemale = new ColumnParameters("htn15To19NonCitizenFemale",
		        "HTS 15 to 19 years Non Citizen female", "age=15-19|type=CN|gender=F", "40");
		ColumnParameters htn20To24NonCitizenMen = new ColumnParameters("htn20To24NonCitizenMen",
		        "HTS 20 to 24 years Non Citizen men", "age=20-24|type=CN|gender=M", "41");
		ColumnParameters htn20To24NonCitizenFemale = new ColumnParameters("htn20To24NonCitizenFemale",
		        "HTS 20 to 24 years Non Citizen female", "age=20-24|type=CN|gender=F", "42");
		ColumnParameters htn25To29NonCitizenMen = new ColumnParameters("htn25To29NonCitizenMen",
		        "HTS 25 to 29 years Non Citizen men", "age=25-29|type=CN|gender=M", "43");
		ColumnParameters htn25To29NonCitizenFemale = new ColumnParameters("htn25To29NonCitizenFemale",
		        "HTS 25 to 29 years Non Citizen female", "age=25-29|type=CN|gender=F", "44");
		ColumnParameters htn30To34NonCitizenMen = new ColumnParameters("htn30To34NonCitizenMen",
		        "HTS 30 to 34 years Non Citizen men", "age=30-34|type=CN|gender=M", "45");
		ColumnParameters htn30To34NonCitizenFemale = new ColumnParameters("htn30To34NonCitizenFemale",
		        "HTS 30 to 34 years Non Citizen female", "age=30-34|type=CN|gender=F", "46");
		ColumnParameters htn35To39NonCitizenMen = new ColumnParameters("htn35To39NonCitizenMen",
		        "HTS 35 to 39 years Non Citizen men", "age=35-39|type=CN|gender=M", "47");
		ColumnParameters htn35To39NonCitizenFemale = new ColumnParameters("htn35To39NonCitizenFemale",
		        "HTS 35 to 39 years Non Citizen female", "age=35-39|type=CN|gender=F", "48");
		ColumnParameters htn40To44NonCitizenMen = new ColumnParameters("htn40To44NonCitizenMen",
		        "HTS 40 to 44 years Non Citizen men", "age=40-44|type=CN|gender=M", "49");
		ColumnParameters htn40To44NonCitizenFemale = new ColumnParameters("htn40To44NonCitizenFemale",
		        "HTS 40 to 44 years Non Citizen female", "age=40-44|type=CN|gender=M", "50");
		ColumnParameters htn45To49NonCitizenMen = new ColumnParameters("htn45To49NonCitizenMen",
		        "HTS 45 to 49 years Non Citizen men", "age=45-49|type=CN|gender=M", "51");
		ColumnParameters htn45To49NonCitizenFemale = new ColumnParameters("htn45To49NonCitizenFemale",
		        "HTS 45 to 49 years Non Citizen female", "age=45-49|type=CN|gender=F", "52");
		ColumnParameters htn50To54NonCitizenMen = new ColumnParameters("htn50To54NonCitizenMen",
		        "HTS 50 to 54 years Non Citizen men", "age=50-54|type=CN|gender=M", "53");
		ColumnParameters htn50To54NonCitizenFemale = new ColumnParameters("htn50To54NonCitizenFemale",
		        "HTS 50 to 54 years Non Citizen female", "age=50-54|type=CN|gender=F", "54");
		ColumnParameters htn55To59NonCitizenMen = new ColumnParameters("htn55To59NonCitizenMen",
		        "HTS 55 to 59 years Non Citizen men", "age=55-59|type=CN|gender=M", "55");
		ColumnParameters htn55To59NonCitizenFemale = new ColumnParameters("htn55To59NonCitizenFemale",
		        "HTS 55 to 59 years Non Citizen female", "age=55-59|type=CN|gender=F", "56");
		ColumnParameters htn60AndAboveNonCitizenMen = new ColumnParameters("htn60AndAboveNonCitizenMen",
		        "HTS 60+ years Non Citizen men", "age=60+|type=CN|gender=M", "57");
		ColumnParameters htn60AndAboveNonCitizenFemale = new ColumnParameters("htn60AndAboveNonCitizenFemale",
		        "HTS 60+ years Non Citizen female", "age=60+|type=CN|gender=F", "58");
		ColumnParameters htnTotalsNonCitizenMen = new ColumnParameters("htnTotalsNonCitizenMen",
		        "HTS totals Non Citizen men", "type=CN|gender=M", "59");
		ColumnParameters htnTotalsNonCitizenFemale = new ColumnParameters("htnTotalsNonCitizenFemale",
		        "HTS totals Non Citizen female", "type=CN|gender=F", "60");
		
		return Arrays.asList(htnUnder1CitizenMen, htnUnder1CitizenFemale, htn1To4CitizenMen, htn1To4CitizenFemale,
		    htn5To9CitizenMen, htn5To9CitizenFemale, htn10To14CitizenMen, htn10To14CitizenFemale, htn15To19CitizenMen,
		    htn15To19CitizenFemale, htn20To24CitizenMen, htn20To24CitizenFemale, htn25To29CitizenMen,
		    htn25To29CitizenFemale, htn30To34CitizenMen, htn30To34CitizenFemale, htn35To39CitizenMen,
		    htn35To39CitizenFemale, htn40To44CitizenMen, htn40To44CitizenFemale, htn45To49CitizenMen,
		    htn45To49CitizenFemale, htn50To54CitizenMen, htn50To54CitizenFemale, htn55To59CitizenMen,
		    htn55To59CitizenFemale, htn60AndAboveCitizenMen, htn60AndAboveCitizenFemale, htnTotalsCitizenMen,
		    htnTotalsCitizenFemale, htnUnder1NonCitizenMen, htnUnder1NonCitizenFemale, htn1To4NonCitizenMen,
		    htn1To4NonCitizenFemale, htn5To9NonCitizenMen, htn5To9NonCitizenFemale, htn10To14NonCitizenMen,
		    htn10To14NonCitizenFemale, htn15To19NonCitizenMen, htn15To19NonCitizenFemale, htn20To24NonCitizenMen,
		    htn20To24NonCitizenFemale, htn25To29NonCitizenMen, htn25To29NonCitizenFemale, htn30To34NonCitizenMen,
		    htn30To34NonCitizenFemale, htn35To39NonCitizenMen, htn35To39NonCitizenFemale, htn40To44NonCitizenMen,
		    htn40To44NonCitizenFemale, htn45To49NonCitizenMen, htn45To49NonCitizenFemale, htn50To54NonCitizenMen,
		    htn50To54NonCitizenFemale, htn55To59NonCitizenMen, htn55To59NonCitizenFemale, htn60AndAboveNonCitizenMen,
		    htn60AndAboveNonCitizenFemale, htnTotalsNonCitizenMen, htnTotalsNonCitizenFemale);
	}
	
	public static List<ColumnParameters> getHivSelfTestingGenderAndAgeDisaggregation() {
		ColumnParameters men = new ColumnParameters("men", "HIV Self testing Men", "gender=M", "01");
		ColumnParameters female = new ColumnParameters("female", "HIV Self testing female", "gender=F", "02");
		
		return Arrays.asList(men, female);
	}
}
