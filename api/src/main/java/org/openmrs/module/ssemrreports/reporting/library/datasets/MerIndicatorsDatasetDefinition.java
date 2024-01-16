package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.MerCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getDispensationColumns3To5Months;
import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getDispensationColumnsLessThan3Months;
import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getDispensationColumnsMoreThan6Months;
import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getMerGenderAndAgeColumns;
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
		        map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		
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
		dsd.setName("TxN");
		addRow(
		    dsd,
		    "TxNCD4L200",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4: < 200",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4: < 200",
		        map(merCohortQueries.getTxNewWithCd4LessThan200Cohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxNCD4GT200",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4 ≥ 200",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4 ≥ 200",
		        map(merCohortQueries.getTxNewWithCd4GreaterThanOrEqualTo200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxNUCD4",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Unknown CD4",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Unknown CD4",
		        map(merCohortQueries.getTxNewWithUnknownCd4Cohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		return dsd;
	}
	
	public DataSetDefinition getTxMlDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxM");
		addRow(
		    dsd,
		    "TxMD",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - Died",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - Died",
		                map(merCohortQueries.getTxMlDiedCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxMIITL3M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for <3 months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for <3 months",
		                map(merCohortQueries.getTxMlIitL3mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxMIIT3T5M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for 3-5 months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for 3-5 months",
		                map(merCohortQueries.getTxMlIit3To5mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxMIITM65M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for 6+ months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for 6+ months",
		                map(merCohortQueries.getTxMlIitM6mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxMIITTO",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - Transferred Out ",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - Transferred Out ",
		                map(merCohortQueries.getTxMlTransferOutCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "TxMRST",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - Refused (Stopped) Treatment",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - Refused (Stopped) Treatment",
		                map(merCohortQueries.getTxMlRefusedStoppedTreatmentCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		return dsd;
	}
	
	public DataSetDefinition getTxRttDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxR");
		addRow(
		    dsd,
		    "TxRCD4L200",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: <200",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: <200",
		                map(merCohortQueries.getTxRttWithCd4LessThan200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxRCD4GE200",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: ≥200",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: ≥200",
		                map(merCohortQueries.getTxRttWithCd4GreaterOrEqual200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxRUKCD4",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Unknown CD4",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Unknown CD4",
		                map(merCohortQueries.getTxRttWithUnknownCd4Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "TxRNECD4",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Not Eligible for CD4",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Not Eligible for CD4",
		                map(merCohortQueries.getTxRttNotEligibleForCd4Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		return dsd;
	}
	
	public DataSetDefinition getTxPvlsDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxP");
		
		addRow(
		    dsd,
		    "TxPALL",
		    "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months",
		    map(indicator
		            .getIndicator(
		                "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months",
		                map(merCohortQueries.getTxPvlsAllCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "TxPP",
		    "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months - Pregnant",
		    map(indicator
		            .getIndicator(
		                "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months - Pregnant",
		                map(merCohortQueries.getTxPvlPregnantCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "TxPB",
		    "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months - Breastfeeding",
		    map(indicator
		            .getIndicator(
		                "Percentage of ART clients with a suppressed viral load (VL) result (<1000 copies/ml) documented in the medical or laboratory records/laboratory information systems (LIS) within the past 12 months - Breastfeeding",
		                map(merCohortQueries.getTxPvlBreastfeedingCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		return dsd;
	}
	
}
