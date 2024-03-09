package org.openmrs.module.ssemrreports.reporting.utils.constants.reports.art;

import java.util.Arrays;
import java.util.List;

public class ArtReportsConstants {
	
	public static final String ART_MONTHLY_REPORT_UUID = "7e6af78e-9f9d-47d0-8dbb-97a213313c3f";
	
	public static final String MISSED_APPOINTMENTS_REPORT_UUID = "17a9d6ce-7abe-42d2-a677-53195e22c46b";
	
	public static final String APPOINTMENTS_DUE_REPORT_UUID = "948300f6-a0e1-47d6-a019-9d6e2ec122b2";
	
	public static final String CLIENTS_WITH_HIGH_VL_REPORT_UUID = "172a036b-b5a5-41e8-bf2d-e60c17f68bd4";
	
	public static final String CLIENTS_WITH_HIGH_VL_EAC_REPORT_UUID = "5c763108-d878-4a04-a6c2-1f298f5f54df";
	
	public static final List<String> adultFirstLineRegimen = Arrays.asList("1a = AZT/3TC + EFV", "1b = AZT/3TC/NVP",
	    "1c = TDF/3TC/DTG", "1d = ABC/3TC (600/300) /DTG", "1e = AZT/3TC + DTG", "1f = TDF/3TC/EFV", "1g = TDF/3TC + NVP",
	    "1h = TDF/FTC/ EFV", "1j  = TDF/FTC + NVP");
	
	public static final List<String> adultSecondLineRegimen = Arrays.asList("2a = AZT/3TC + DTG", "2b = ABC/3TC + DTG",
	    "2c = TDF+3TC + LPV/r", "2d = TDF/3TC + ATV/r", "2e = TDF/FTC-LPV/r", "2f = TDF/FTC-ATV/r", "2g = AZT/3TC + LPV/r",
	    "2h = AZT/3TC + ATV/r", "2i = ABC +3TC + LPV/r", "2j = ABC +3TC + ATV/r", "2k =  TDF/3TC/DTG");
	
	public static final List<String> childFirstLineRegimen = Arrays.asList("4a = AZT/3TC/NVP", "4b = AZT/3TC + EFV",
	    "4c = ABC/3TC (120/60) + LPV/r", "4d = ABC/3TC (120/60) + DTG50", "4f = ABC/3TC + NVP",
	    "4g = ABC/3TC (120/60) + EFV (200mg)", "4h = TDF/3TC/EFV", "4i  = ABC/3TC + LPV/r", "4j = AZT/3TC (60/30) + LPV/r",
	    "4k = TDF/3TC + NVP", "4l = ABC/3TC + AZT", "4e = ABC/3TC(120/60mg)+DTG10");
	
	public static final List<String> childSecondLineRegimen = Arrays.asList("5a = AZT/3TC+LPV/r", "5b = AZT/3TC + RAL",
	    "5c = ABC/3TC (120/60) + RAL", "5d = AZT/3TC + ATV/r", "5e = ABC/3TC + ATV/r", "5f = TDF/ 3TC + ATV/r",
	    "5g = AZT/3TC + DTG50", "5h = ABC/3TC + DTG50", "5i = ABC/3TC + LPV/r");
}
