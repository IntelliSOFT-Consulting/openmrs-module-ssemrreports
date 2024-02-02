package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.Location;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.MerCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addParameter(new Parameter("location", "Facility", Location.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		dsd.addDimension("disp", map(dimension.getDispensingQuantityDimension(), mappings));
		dsd.setName("TxC");
		addRow(
		    dsd,
		    "ALL",
		    "Number of adults and children currently receiving antiretroviral therapy (ART)",
		    map(indicator.getIndicator("Number of adults and children currently receiving antiretroviral therapy (ART)",
		        map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "NMDD",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - <3 months of ARVs (not MMD) dispensed to client",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - <3 months of ARVs (not MMD) dispensed to client",
		                map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings),
		    getDispensationColumnsLessThan3Months());
		
		addRow(
		    dsd,
		    "MDD35",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		                map(merCohortQueries.getTxCurrCohorts(), mappings)), mappings), getDispensationColumns3To5Months());
		
		addRow(
		    dsd,
		    "MMDD6",
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
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addParameter(new Parameter("location", "Facility", Location.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxN");
		addRow(
		    dsd,
		    "T",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - All",
		    map(indicator.getIndicator("Number of adults and children newly enrolled on antiretroviral therapy (ART) - All",
		        map(merCohortQueries.getTxNewCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "B",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Breastfeeding",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Breastfeeding",
		        map(merCohortQueries.getTxNewBreastfeedingCohort(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "CD4L200",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4: < 200",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4: < 200",
		        map(merCohortQueries.getTxNewCd4LessThan200Cohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "CD4GT200",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4 ≥ 200",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - CD4 ≥ 200",
		        map(merCohortQueries.getTxNewWithCd4GreaterThanOrEqualTo200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "UCD4",
		    "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Unknown CD4",
		    map(indicator.getIndicator(
		        "Number of adults and children newly enrolled on antiretroviral therapy (ART) - Unknown CD4",
		        map(merCohortQueries.getTxWithUnknownCd4Cohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		return dsd;
	}
	
	public DataSetDefinition getTxMlDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addParameter(new Parameter("location", "Facility", Location.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxM");
		addRow(
		    dsd,
		    "D",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - Died",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - Died",
		                map(merCohortQueries.getTxMlDiedCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "IITL3M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for <3 months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for <3 months",
		                map(merCohortQueries.getTxMlIitL3mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "IIT3T5M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for 3-5 months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for 3-5 months",
		                map(merCohortQueries.getTxMlIit3To5mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "IITM65M",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - IIT After being on Treatment for 6+ months",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - IIT After being on Treatment for 6+ months",
		                map(merCohortQueries.getTxMlIitM6mCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "IITTO",
		    "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		            + "clinical contact since their last expected contact - Transferred Out ",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no "
		                        + "clinical contact since their last expected contact - Transferred Out ",
		                map(merCohortQueries.getTxMlTransferOutCohorts(), mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RST",
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
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addParameter(new Parameter("location", "Facility", Location.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxR");
		addRow(
		    dsd,
		    "RCD4L200",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: <200",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: <200",
		                map(merCohortQueries.getTxRttWithCd4LessThan200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "RCD4GE200",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: ≥200",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - CD4: ≥200",
		                map(merCohortQueries.getTxRttWithCd4GreaterOrEqual200Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "RUKCD4",
		    "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Unknown CD4",
		    map(indicator
		            .getIndicator(
		                "Number of ART clients who experienced an interruption in treatment (IIT) during any previous reporting period, who successfully restarted ARVs within the reporting period and remained on treatment until the end of the reporting period - Unknown CD4",
		                map(merCohortQueries.getTxRttWithUnknownCd4Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RNECD4",
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
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addParameter(new Parameter("location", "Facility", Location.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TxP");
		
		addRow(
		    dsd,
		    "DEN",
		    "of ART patients with a VL result documented in ART register/file ",
		    map(indicator.getIndicator("of ART patients with a VL result documented in ART register/file ",
		        map(merCohortQueries.getTxPvlsArtPatientsWithVlResultDocumentedInArtRegisterCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTN",
		    "ART patients with high VL results (>1,000 copies/ml) documented in the ART register/file ",
		    map(indicator.getIndicator(
		        "ART patients with high VL results (>1,000 copies/ml) documented in the ART register/file  ",
		        map(merCohortQueries.getTxPvlsArtPatientsWithVlGreaterOrEqual1000ResultDocumentedInArtRegisterCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "SUP",
		    "ART patients with supressed VL results (<1,000 copies/ml) documented in the ART register/file",
		    map(indicator
		            .getIndicator(
		                "ART patients with supressed VL results (<1,000 copies/ml) documented in the ART register/file",
		                map(merCohortQueries.getTxPvlsArtPatientsWithVlLessThan1000ResultDocumentedInArtRegisterCohorts(),
		                    mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "PPDEN",
		    "Pregnant women with documented VL result",
		    map(indicator.getIndicator("Pregnant women with documented VL result",
		        map(merCohortQueries.getTxPvlsPregnantWithDocumentedVlResultsCohort(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "PPRTN",
		    "Pregnant women High VL results (>1,000 copies/ml)",
		    map(indicator.getIndicator("Pregnant women High VL results (>1,000 copies/ml)",
		        map(merCohortQueries.getTxPvlsPregnantWithDocumentedVlResultsGreatorThan1000Cohort(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "BDEN",
		    "Breastfeeding women with documented VL result",
		    map(indicator.getIndicator("Breastfeeding women with documented VL result",
		        map(merCohortQueries.getTxPvlsBreastfeedingWithDocumentedVlResultsCohort(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "BRTN",
		    "Breastfeeding women High VL results (>1,000 copies/ml) ",
		    map(indicator.getIndicator("Breastfeeding women High VL results (>1,000 copies/ml) ",
		        map(merCohortQueries.getTxPvlsBreastfeedingWithDocumentedVlResultsGreatorThan1000Cohort(), mappings)),
		        mappings), getMerGenderAndAgeColumns());
		return dsd;
	}
	
}
