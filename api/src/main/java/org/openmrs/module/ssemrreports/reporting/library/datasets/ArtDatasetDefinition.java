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

@Component
public class ArtDatasetDefinition extends SSEMRBaseDataSet {
	
	private final SSEMRCommonDimension dimension;
	
	private final SSEMRGeneralIndicator indicator;
	
	private final ArtCohortQueries artCohortQueries;
	
	private final CommonCohortQueries commonCohortQueries;
	
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
		String mappings = "startDate=${startDate},endDate=${endDate+23h},location=${location}";
		String mappings1 = "endDate=${endDate+23h},location=${location}";
		dsd.setName("ART");
		dsd.setDescription("ART dataset");
		dsd.addParameters(getParameters());
		dsd.addDimension("gender", map(dimension.gender(), ""));
		dsd.addDimension("type", map(dimension.getCitizenType(), ""));
		dsd.addDimension("age", map(dimension.age(), "effectiveDate=${endDate}"));
		dsd.addDimension("other", map(dimension.getGeneralDisags(), mappings));
		
		/*		Program artProgram = SSEMRReportUtils.getProgram(SharedReportConstants.ART_PROGRAM_UUID);
				Concept programOutcome = SSEMRReportUtils.getConcept("5240AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				Concept programOutcomeDied = SSEMRReportUtils.getConcept(SharedReportConstants.DIED);
				Concept cervicalcancer15 = SSEMRReportUtils.getConcept("165617AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				Concept cervicalcancer16 = SSEMRReportUtils.getConcept("143264AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				Concept NO = SSEMRReportUtils.getConcept(SharedReportConstants.NO);
				Concept answer_16 = SSEMRReportUtils.getConcept("1534AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				EncounterType encounterTypeForART_15_1 = SSEMRReportUtils.getEncounterType("f402f44a-d010-11ec-9d64-0242ac120002");
				EncounterType encounterTypeForART_15_2 = SSEMRReportUtils.getEncounterType("6406d528-cbd8-11ec-9d64-0242ac120002");
				EncounterType encounterTypeForART_16 = SSEMRReportUtils.getEncounterType("6406d528-cbd8-11ec-9d64-0242ac120002");
				
				addRow(
				    dsd,
				    "1",
				    "Number of adults and children currently on ART during the reporting period",
				    map(indicator.getIndicator("Number of adults and children currently on ART during the reporting period",
				        map(artCohortQueries.getART1CohortDefinition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_Dimensions());
				addRow(
				    dsd,
				    "2",
				    "Number of  adults and children newly initiated on ART during the reporting period",
				    map(indicator.getIndicator("Number of  adults and children newly initiated on ART during the reporting period",
				        map(artCohortQueries.getART2CohortDefinition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "3",
				    "Number of  adults and children  who are currently  lost to follow up during the reporting period",
				    map(indicator.getIndicator(
				        "Number of  adults and children  who are currently  lost to follow up during the reporting period",
				        map(commonCohortQueries.getPatientsEverInProgramWithOutcomes(artProgram.getProgramId(),
				            programOutcome.getConceptId()), mappings1)), mappings1), EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "4",
				    "Number of  adults and children on ART who were reinitiated post Lost to follow up during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of  adults and children on ART who were reinitiated post Lost to follow up during the reporting period",
				                map(commonCohortQueries.getPatientsEverInProgramWithOutcomesAndNewlyEnrolled(
				                    artProgram.getProgramId(), programOutcome.getConceptId()), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "5",
				    "Number of adults and children currently  on ART with detectable Viral Load >400 copies/ml during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of adults and children currently  on ART with detectable Viral Load >400 copies/ml during the reporting period",
				                map(artCohortQueries.getPatientsOnArtWithDetectableViralLoadR5(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "6",
				    "Number of adults and children currently  on ART with detectable Viral Load < 400 copies/ml during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of adults and children currently  on ART with detectable Viral Load < 400 copies/ml during the reporting period",
				                map(artCohortQueries.getPatientsOnArtWithDetectableViralLoadR6(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "7",
				    "Number of HIV+ persons on ART with initial CD4 cell count less than 200 cells/µL during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of HIV+ persons on ART with initial CD4 cell count less than 200 cells/µL during the reporting period",
				                map(artCohortQueries.getPatientsOnArtWithDetectableViralLoadR7(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "8",
				    "Number of HIV+ persons on ART with initial CD4 cell count less than 200 cells/µL during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of HIV+ persons on ART with initial CD4 cell count less than 200 cells/µL during the reporting period",
				                map(artCohortQueries.getPatientsOnArtWithDetectableViralLoadR8(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "9",
				    "Number of HIV positive adults and children currently in HIV care who were screened for TB during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of HIV positive adults and children currently in HIV care who were screened for TB during the reporting period",
				                map(artCohortQueries.getART_9Definition(SSEMRReportUtils.getEncounterType(
				                    "f402f44a-d010-11ec-9d64-0242ac120002").getEncounterTypeId()), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "10",
				    "Number of TB incident cases among HIV positive adults and children on ART during the reporting period",
				    map(indicator.getIndicator(
				        "Number of TB incident cases among HIV positive adults and children on ART during the reporting period",
				        map(artCohortQueries.getART_10Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				
				addRow(
				    dsd,
				    "11",
				    "Number of active TB  cases among adults and children newly enrolled in HIV care during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of active TB  cases among adults and children newly enrolled in HIV care during the reporting period",
				                map(artCohortQueries.getART_9Definition(SSEMRReportUtils.getEncounterType(
				                    "6406d528-cbd8-11ec-9d64-0242ac120002").getEncounterTypeId()), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "12",
				    "Number of adults and children currently in HIV care eligible for Cotrimoxazole during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of adults and children currently in HIV care eligible for Cotrimoxazole during the reporting period",
				                map(artCohortQueries.getART_12_13_Definitions("1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), mappings)),
				        mappings), EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "13",
				    "Number of patients currently in HIV care who received Cotrimoxazole during the reporting period",
				    map(indicator.getIndicator(
				        "Number of patients currently in HIV care who received Cotrimoxazole during the reporting period",
				        map(artCohortQueries.getART_12_13_Definitions("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "14",
				    "Number of  adults and children who died while on ART during the reporting period",
				    map(indicator.getIndicator(
				        "Number of  adults and children who died while on ART during the reporting period",
				        map(commonCohortQueries.getPatientsEverInProgramWithOutcomes(artProgram.getProgramId(),
				            programOutcomeDied.getConceptId()), mappings1)), mappings1),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "15",
				    "Number of registered HIV+ female patients currently in HIV care eligible for pre-cervical cancer screening  during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of registered HIV+ female patients currently in HIV care eligible for pre-cervical cancer screening  during the reporting period",
				                map(artCohortQueries.getART_15_16Definition(
				                    Arrays.asList(encounterTypeForART_15_1.getEncounterTypeId(),
				                        encounterTypeForART_15_2.getEncounterTypeId()), cervicalcancer15.getConceptId(),
				                    NO.getConceptId()), mappings)), mappings), EmrDimensionReferences.getART_15_16_Dimensions());
				addRow(
				    dsd,
				    "16",
				    "Number of registered HIV+ female patients currently in HIV care who were screened  for pre-cervical cancer  during the reporting period",
				    map(indicator
				            .getIndicator(
				                "Number of registered HIV+ female patients currently in HIV care who were screened  for pre-cervical cancer  during the reporting period",
				                map(artCohortQueries.getART_15_16Definition(
				                    Arrays.asList(encounterTypeForART_16.getEncounterTypeId()), cervicalcancer16.getConceptId(),
				                    answer_16.getConceptId()), mappings)), mappings), EmrDimensionReferences
				            .getART_15_16_Dimensions());
				dsd.addColumn(
				    "17M",
				    "Number of adults and children on ART presenting one or more adverse drug event  during the reporting period male",
				    map(indicator
				            .getIndicator(
				                "Number of adults and children on ART presenting one or more adverse drug event  during the reporting period male",
				                map(commonCohortQueries.getNumberPatientsSeenPerEncounter(Arrays.asList(SSEMRReportUtils
				                        .getEncounterType(SharedReportConstants.ADVERSE_DRUG_REACTION_ENCOUNTER_TYPE_UUID)
				                        .getEncounterTypeId())), mappings)), mappings), "gender=M");
				dsd.addColumn(
				    "17F",
				    "Number of adults and children on ART presenting one or more adverse drug event  during the reporting period female",
				    map(indicator
				            .getIndicator(
				                "Number of adults and children on ART presenting one or more adverse drug event  during the reporting period female",
				                map(commonCohortQueries.getNumberPatientsSeenPerEncounter(Arrays.asList(SSEMRReportUtils
				                        .getEncounterType(SharedReportConstants.ADVERSE_DRUG_REACTION_ENCOUNTER_TYPE_UUID)
				                        .getEncounterTypeId())), mappings)), mappings), "gender=F");
				
				addRow(
				    dsd,
				    "TPT_1",
				    "Number of TB patients with an HIV status during the reporting period",
				    map(indicator.getIndicator("Number of TB patients with an HIV status during the reporting period",
				        map(artCohortQueries.getART_TPT_1_Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "TPT_2",
				    "Number of HIV positive TB patients during the reporting period",
				    map(indicator.getIndicator("Number of HIV positive TB patients during the reporting period",
				        map(artCohortQueries.getART_TPT_2_Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "TPT_3",
				    "Number of TB patients on ART during the reporting period",
				    map(indicator.getIndicator("Number of TB patients on ART during the reporting period",
				        map(artCohortQueries.getART_TPT_3_Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "TPT_4",
				    "Number of patients on CPT during the reporting period",
				    map(indicator.getIndicator("Number of patients on CPT during the reporting period",
				        map(artCohortQueries.getART_TPT_4_Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				addRow(
				    dsd,
				    "TPT_5",
				    "Number of TB patients previously on TPT / IPT during the reporting period",
				    map(indicator.getIndicator("Number of TB patients previously on TPT / IPT during the reporting period",
				        map(artCohortQueries.getART_TPT_5_Definition(), mappings)), mappings),
				    EmrDimensionReferences.getART_1_14_Dimensions());
				*/
		return dsd;
	}
}
