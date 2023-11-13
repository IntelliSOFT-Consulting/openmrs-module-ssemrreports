package org.openmrs.module.ssemrreports.reporting.library.datasets;

import java.util.Arrays;
import java.util.List;

import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.CommonCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.dimension.SSEMRCommonDimension;
import org.openmrs.module.ssemrreports.reporting.library.indicator.SSEMRGeneralIndicator;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TbScreeningDatasetDefinition extends SSEMRBaseDataSet {
	
	private final CommonCohortQueries commonCohortQueries;
	
	private final SSEMRCommonDimension dimension;
	
	private final SSEMRGeneralIndicator indicator;
	
	@Autowired
	public TbScreeningDatasetDefinition(CommonCohortQueries commonCohortQueries, SSEMRCommonDimension dimension,
	    SSEMRGeneralIndicator indicator) {
		this.commonCohortQueries = commonCohortQueries;
		this.dimension = dimension;
		this.indicator = indicator;
	}
	
	public DataSetDefinition constructTbScreeningRegisterDefinition() {
		
		String DATE_FORMAT = "dd-MMM-yyyy";
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.setName("TBSCR");
		dsd.addParameters(getParameters());
		dsd.setDescription("TB screening register details");
		dsd.addSortCriteria("Psn", SortCriteria.SortDirection.ASC);
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		
		//PersonAttributeType contactAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(
		//    SharedReportConstants.PHONE_NUMBER_ATTRIBUTE_TYPE_UUID);
		
		//DataDefinition conctactDef = new ConvertedPersonDataDefinition("contact", new PersonAttributeDataDefinition(
		//        contactAttributeType.getName(), contactAttributeType), null);
		
		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		// dsd.addColumn("Psn", new PatientIdDataDefinition(), "", new ObjectCounterConverter());
		// dsd.addColumn("Oids", new PatientObjectDataDefinition(), "", new OtherIdentifierConverter());
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("DOB", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
		// dsd.addColumn("CDTAILS", conctactDef, "");
		
		return dsd;
		
	}
	
	public DataSetDefinition constructTheAggregatePartOfTheScreeningRegister() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dsd.setName("TBS");
		dsd.addParameters(getParameters());
		dsd.addDimension("gender", SSEMRReportUtils.map(dimension.gender(), ""));
		dsd.addDimension("age", SSEMRReportUtils.map(dimension.age(), "effectiveDate=${endDate}"));
		
		//		addRow(
		//		    dsd,
		//		    "TBNPS",
		//		    "Number of people screened for TB",
		//		    SSEMRReportUtils.map(
		//		        indicator.getIndicator("Number of people screened for TB", SSEMRReportUtils.map(
		//		            commonCohortQueries.getNumberPatientsSeenPerEncounter(Arrays.asList(SSEMRReportUtils.getEncounterType(
		//		                SharedReportConstants.TB_SCREENING_ENCOUNTER_TYPE_UUID).getEncounterTypeId())), mappings)), mappings),
		//		    getGenderAgeColumns());
		return dsd;
	}
	
	private List<ColumnParameters> getGenderAgeColumns() {
		ColumnParameters male1To4 = new ColumnParameters("male1To4", "Male patients 1-4", "gender=M|age=1-4", "01");
		ColumnParameters male5To9 = new ColumnParameters("male5To9", "Male patients 5-9", "gender=M|age=5-9", "02");
		ColumnParameters male10To14 = new ColumnParameters("male10To14", "Male patients 10-14 years", "gender=M|age=10-14",
		        "04");
		ColumnParameters male15To19 = new ColumnParameters("male15To19", "Male patients 15-19 years", "gender=M|age=15-19",
		        "05");
		ColumnParameters male20To24 = new ColumnParameters("male20To24", "Male patients 20-24 years", "gender=M|age=20-24",
		        "06");
		ColumnParameters male25To29 = new ColumnParameters("male25To29", "Male patients 25-29 years", "gender=M|age=25-29",
		        "07");
		ColumnParameters male30To34 = new ColumnParameters("male30To34", "Male patients 30-34 years", "gender=M|age=30-34",
		        "08");
		ColumnParameters male35To39 = new ColumnParameters("male35To39", "Male patients 35-39 years", "gender=M|age=35-39",
		        "09");
		ColumnParameters male40To44 = new ColumnParameters("male40To44", "Male patients 40-44 years", "gender=M|age=40-44",
		        "10");
		ColumnParameters male45To49 = new ColumnParameters("male45To49", "Male patients 45-49 years", "gender=M|age=45-49",
		        "11");
		ColumnParameters male50Plus = new ColumnParameters("male50Plus", "Male patients 50+ years", "gender=M|age=50+", "12");
		ColumnParameters maleTotals = new ColumnParameters("maleTotals", "Total Male patients", "gender=M", "13");
		
		ColumnParameters female1To4 = new ColumnParameters("female1To4", "Male patients 1-4", "gender=F|age=1-4", "14");
		ColumnParameters female5To9 = new ColumnParameters("female5To9", "Male patients 5-9", "gender=F|age=5-9", "15");
		ColumnParameters female10To14 = new ColumnParameters("female10To14", "Female patients 10-14 years",
		        "gender=F|age=10-14", "16");
		ColumnParameters female15To19 = new ColumnParameters("female15To19", "Female patients 15-19 years",
		        "gender=F|age=15-19", "17");
		ColumnParameters female20To24 = new ColumnParameters("female20To24", "Female patients 20-24 years",
		        "gender=F|age=20-24", "18");
		ColumnParameters female25To29 = new ColumnParameters("female25To29", "Female patients 25-29 years",
		        "gender=F|age=25-29", "19");
		ColumnParameters female30To34 = new ColumnParameters("female30To34", "Female patients 30-34 years",
		        "gender=F|age=30-34", "20");
		ColumnParameters female35To39 = new ColumnParameters("female35To39", "Female patients 35-39 years",
		        "gender=F|age=35-39", "21");
		ColumnParameters female40To44 = new ColumnParameters("female40To44", "Female patients 40-44 years",
		        "gender=F|age=40-44", "22");
		ColumnParameters female45To49 = new ColumnParameters("female45To49", "Female patients 45-49 years",
		        "gender=F|age=45-49", "23");
		ColumnParameters female50Plus = new ColumnParameters("female50Plus", "Female patients 50+ years",
		        "gender=F|age=50+", "24");
		ColumnParameters femaleTotals = new ColumnParameters("femaleTotals", "Total Female patients", "gender=F", "25");
		ColumnParameters allTotals = new ColumnParameters("allTotals", "Total patients", "", "26");
		
		return Arrays.asList(male1To4, male5To9, male10To14, male15To19, male20To24, male25To29, male30To34, male35To39,
		    male40To44, male45To49, male50Plus, maleTotals, female1To4, female5To9, female10To14, female15To19,
		    female20To24, female25To29, female30To34, female35To39, female40To44, female45To49, female50Plus, femaleTotals,
		    allTotals);
	}
}
