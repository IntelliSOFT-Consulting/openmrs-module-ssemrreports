package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.MerCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SsemrCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SsemrGeneralIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getDispensationColumnsGenderAndAge;
import static org.openmrs.module.ssemrreports.reporting.library.columns.ShareDatasetColumns.getMerGenderAndAgeColumns;
import static org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils.map;

@Component
public class MerIndicatorsDatasetDefinition extends SsemrBaseDataSet {
	
	private final SsemrCommonDimension dimension;
	
	private final MerCohortQueries merCohortQueries;
	
	private final SsemrGeneralIndicator indicator;
	
	@Autowired
	public MerIndicatorsDatasetDefinition(SsemrCommonDimension dimension, MerCohortQueries merCohortQueries,
	    SsemrGeneralIndicator indicator) {
		this.dimension = dimension;
		this.merCohortQueries = merCohortQueries;
		this.indicator = indicator;
	}
	
	public DataSetDefinition getTxCurrDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate}";
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
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
		                map(merCohortQueries.getTxMlIitL3mCohorts(), mappings)), mappings),
		    getDispensationColumnsGenderAndAge());
		
		addRow(
		    dsd,
		    "MDD35",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - 3-5 months of ARVs (MMD) dispensed to client",
		                map(merCohortQueries.getTxMlIit3To5mCohorts(), mappings)), mappings),
		    getDispensationColumnsGenderAndAge());
		
		addRow(
		    dsd,
		    "MMDD6",
		    "Number of adults and children currently receiving antiretroviral therapy (ART) - 6 or more months of ARVs (MMD) dispensed to clients",
		    map(indicator
		            .getIndicator(
		                "Number of adults and children currently receiving antiretroviral therapy (ART) - 6 or more months of ARVs (MMD) dispensed to clients",
		                map(merCohortQueries.getTxMlIitM6mCohorts(), mappings)), mappings),
		    getDispensationColumnsGenderAndAge());
		return dsd;
	}
	
	public DataSetDefinition getTxNewDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate}";
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
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate}";
		dsd.setName("TxM");
		
		addRow(
		    dsd,
		    "MLA",
		    "ART patients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no clinical contact for  greater than 28 days since their last expected contact or ARV pick up",
		    map(indicator
		            .getIndicator(
		                "ART patients (who were on ART at the beginning of the quarterly reporting period or initiated treatment during the reporting period) and then had no clinical contact for  greater than 28 days since their last expected contact or ARV pick up",
		                map(merCohortQueries
		                        .getArtPatientsAtTheBeginningAndHaveClinicalContactGreaterThan28DaysSinceLastExpectedContactCohorts(),
		                    mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "MLB",
		    "Clients traced and brought back by HF effort or self returned from those who missed great than 28 days in the reporting period (Re-started)",
		    map(indicator
		            .getIndicator(
		                "Clients traced and brought back by HF effort or self returned from those who missed great than 28 days in the reporting period (Re-started)",
		                map(merCohortQueries
		                        .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterCohorts(),
		                    mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "MLC3",
		    "Number of ART clients On treatment for  <3 months when LTFU/IIT",
		    map(indicator.getIndicator(
		        "Number of ART clients On treatment for  <3 months when LTFU/IIT",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitL3mCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "MLC35",
		    "On treatment for 3-5 months when LTFU/IIT",
		    map(indicator.getIndicator(
		        "On treatment for 3-5 months when LTFU/IIT",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIit3To5mCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "MLC6",
		    "On treatment for 6+ months when LTFU/IIT",
		    map(indicator.getIndicator(
		        "On treatment for 6+ months when LTFU/IIT",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterIitM6mCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "MLCD",
		    "Identified as Died",
		    map(indicator.getIndicator(
		        "Identified as Died",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterDiedCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "MLTO",
		    "Identified as Self transfer (Transfer out)",
		    map(indicator.getIndicator(
		        "Identified as Self transfer (Transfer out)",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterSelfTransferOutCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RST",
		    "Reached but Refused or Stopped treatmnet",
		    map(indicator.getIndicator(
		        "Reached but Refused or Stopped treatmnet",
		        map(merCohortQueries
		                .getPatientOutcomeClientsTracedAndBroughtBackByHfEffortsOrSelfReturned28DaysLaterRefusedStoppedTreatmentCohorts(),
		            mappings)), mappings), getMerGenderAndAgeColumns());
		//cause of death
		addRow(
		    dsd,
		    "COD1",
		    "Cause of death -TB",
		    map(indicator.getIndicator("Cause of death -TB",
		        map(merCohortQueries.getTxMlCauseOfDeathCohorts("TB"), mappings)), mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "COD2",
		    "Cause of death -Cancer",
		    map(indicator.getIndicator("Cause of death -Cancer",
		        map(merCohortQueries.getTxMlCauseOfDeathCohorts("Cancer"), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "COD3",
		    "Cause of death -Other infectious and parasitic disease",
		    map(indicator.getIndicator("Cause of death -Other infectious and parasitic disease",
		        map(merCohortQueries.getTxMlCauseOfDeathCohorts("Other infectious and parasitic disease"), mappings)),
		        mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "COD4",
		    "Cause of death - Non-natural causes (accident/war)",
		    map(indicator.getIndicator("Cause of death - Non-natural causes (accident/war)",
		        map(merCohortQueries.getTxMlCauseOfDeathCohorts("Non-natural causes (accident/war)"), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "COD5",
		    "Cause of death - Unknown Cause",
		    map(indicator.getIndicator("Cause of death - Unknown Cause",
		        map(merCohortQueries.getTxMlCauseOfDeathCohorts("Unknown Cause"), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		return dsd;
	}
	
	public DataSetDefinition getTxRttDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate}";
		dsd.setName("TxR");
		
		addRow(
		    dsd,
		    "RTT1",
		    "Clients traced and brought back to care (Re-started) from those who were lost in the previous quarters (I.e from those who were not active at the beginning of this reporting period)",
		    map(indicator
		            .getIndicator(
		                "Clients traced and brought back to care (Re-started) from those who were lost in the previous quarters (I.e from those who were not active at the beginning of this reporting period)",
		                map(merCohortQueries.getClientsTracedBroughtBackToCareRestartedCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "RTT21",
		    "How long were people off ARVs - 28 days-3 months",
		    map(indicator.getIndicator("How long were people off ARVs - 28 days-3 months",
		        map(merCohortQueries.getHowLongWerePeopleOffArvs28DaysTo3MonthsFromLastTcaCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTT22",
		    "How long were people off ARVs - 3 -6 months",
		    map(indicator.getIndicator("How long were people off ARVs - 3 -6 months",
		        map(merCohortQueries.getHowLongWerePeopleOffArvs3To6MonthsFromLastTcaCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTT23",
		    "How long were people off ARVs - 6-12 months ",
		    map(indicator.getIndicator("How long were people off ARVs - 6-12 months ",
		        map(merCohortQueries.getHowLongWerePeopleOffArvs6To12MonthsFromLastTcaCohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTT4",
		    "CD4 results - CD4: <200",
		    map(indicator.getIndicator("CD4 results - CD4: <200",
		        map(merCohortQueries.getHowLongWerePeopleOffMonthsFromLastTcaWithCd4LessThan200Cohorts(), mappings)),
		        mappings), getMerGenderAndAgeColumns());
		addRow(
		    dsd,
		    "RTT5",
		    "CD4 results - CD4: ≥200",
		    map(indicator.getIndicator("CD4 results - CD4: ≥200",
		        map(merCohortQueries.getHowLongWerePeopleOffFromLastTcaWithCd4GreaterOrEqual200Cohorts(), mappings)),
		        mappings), getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTT6",
		    "CD4 results - Unknown CD4",
		    map(indicator.getIndicator("CD4 results - Unknown CD4",
		        map(merCohortQueries.getHowLongWerePeopleOffFromLastTcaWithUnknownCd4Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		addRow(
		    dsd,
		    "RTT7",
		    "CD4 results - Not Eligible for CD4",
		    map(indicator.getIndicator("CD4 results - Not Eligible for CD4",
		        map(merCohortQueries.getHowLongWerePeopleOffFromLastTcaNotEligibleForCd4Cohorts(), mappings)), mappings),
		    getMerGenderAndAgeColumns());
		
		return dsd;
	}
	
	public DataSetDefinition getTxPvlsDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		String mappings = "startDate=${startDate},endDate=${endDate}";
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
		    "ART patients with high VL results (>=1,000 copies/ml) documented in the ART register/file ",
		    map(indicator.getIndicator(
		        "ART patients with high VL results (>=1000 copies/ml) documented in the ART register/file  ",
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
