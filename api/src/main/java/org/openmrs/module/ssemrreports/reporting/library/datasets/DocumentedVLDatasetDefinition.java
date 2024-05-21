package org.openmrs.module.ssemrreports.reporting.library.datasets;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.calculation.BomaAddressCalculation;
import org.openmrs.module.ssemrreports.reporting.calculation.LandmarkAddressCalculation;
import org.openmrs.module.ssemrreports.reporting.calculation.PayamAddressCalculation;
import org.openmrs.module.ssemrreports.reporting.converter.CalculationResultConverter;
import org.openmrs.module.ssemrreports.reporting.library.data.converter.PersonAttributeDataConverter;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.BreastFeedingDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.COVNameDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.CalculationDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.LinkedToCOVDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.PregnantDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.StatusDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.VLResultAfter14DaysDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.DaysVLPendingDataDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.stereotype.Component;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.VLResultsDocumentedDateDataDefinition;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.DateVLSampleCollectedDataDefinition;

@Component
public class DocumentedVLDatasetDefinition extends SsemrBaseDataSet {
	
	private DataDefinition personPayamAddress() {
		CalculationDataDefinition cd = new CalculationDataDefinition("payam", new PayamAddressCalculation());
		return cd;
	}
	
	private DataDefinition personBomaAddress() {
		CalculationDataDefinition cd = new CalculationDataDefinition("boma", new BomaAddressCalculation());
		return cd;
	}
	
	private DataDefinition personLandmarkAddress() {
		CalculationDataDefinition cd = new CalculationDataDefinition("landmark", new LandmarkAddressCalculation());
		return cd;
	}
	
	public DataSetDefinition constructDocumentedVLDatasetDefinition() {
		
		String DATE_FORMAT = "dd-MMM-yyyy";
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.setName("DVLD");
		dsd.addParameters(getParameters());
		dsd.setDescription("Line list for clients with documented viral load result, high viral load, and viral load test");
		dsd.addSortCriteria("Psn", SortCriteria.SortDirection.ASC);
		dsd.addParameter(new Parameter("location", "Location", Location.class));
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		DataConverter nameFormatter = new ObjectFormatter("{familyName} {givenName} {middleName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		
		PatientIdentifierType openmrsID = Context.getPatientService().getPatientIdentifierTypeByUuid(
		    SharedReportConstants.UNIQUE_ART_NUMBER_TYPE_UUID);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        openmrsID.getName(), openmrsID), identifierFormatter);
		
		PersonAttributeType phoneNumber = Context.getPersonService().getPersonAttributeTypeByUuid(
		    SharedReportConstants.PHONE_NUMBER_ATTRIBUTE_TYPE_UUID);
		
		COVNameDataDefinition covNameDataDefinition = new COVNameDataDefinition();
		covNameDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		LinkedToCOVDataDefinition linkedToCOVDataDefinition = new LinkedToCOVDataDefinition();
		linkedToCOVDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		StatusDataDefinition statusDataDefinition = new StatusDataDefinition();
		statusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		PregnantDataDefinition pregnantDataDefinition = new PregnantDataDefinition();
		pregnantDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		BreastFeedingDataDefinition breastfeedingDataDefinition = new BreastFeedingDataDefinition();
		breastfeedingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		DaysVLPendingDataDefinition daysVLPendingDataDefinition = new DaysVLPendingDataDefinition();
		daysVLPendingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		VLResultAfter14DaysDataDefinition vlResultAfter14DaysDataDefinition = new VLResultAfter14DaysDataDefinition();
		vlResultAfter14DaysDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		VLResultsDocumentedDateDataDefinition vlResultsDocumentedDateDataDefinition = new VLResultsDocumentedDateDataDefinition();
		vlResultsDocumentedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		DateVLSampleCollectedDataDefinition dateVLSampleCollectedDataDefinition = new DateVLSampleCollectedDataDefinition();
		dateVLSampleCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Identifier", identifierDef, (String) null);
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Age", new AgeDataDefinition(), "", null);
		dsd.addColumn("Gender", new GenderDataDefinition(), "", null);
		dsd.addColumn("Telephone", new PersonAttributeDataDefinition("Phone Number", phoneNumber), "",
		    new PersonAttributeDataConverter());
		dsd.addColumn("Pregnant", pregnantDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Breastfeeding", breastfeedingDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Date VL sample collected", dateVLSampleCollectedDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Date VL results documented", vlResultsDocumentedDateDataDefinition, "endDate=${endDate}");
		dsd.addColumn("VL Result", daysVLPendingDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Days VL Results Pending", vlResultAfter14DaysDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Payam", personPayamAddress(), "", new CalculationResultConverter());
		dsd.addColumn("Boma", personBomaAddress(), "", new CalculationResultConverter());
		dsd.addColumn("Landmark", personLandmarkAddress(), "", new CalculationResultConverter());
		dsd.addColumn("Name of COV", covNameDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Linked to COV (Y/N)", linkedToCOVDataDefinition, "endDate=${endDate}");
		
		return dsd;
	}
}
