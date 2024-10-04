package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.data.converter.PersonAttributeDataConverter;
import org.openmrs.module.ssemrreports.reporting.library.data.definition.*;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;

import java.util.Date;

public class AllClientsDatasetDefinition extends SsemrBaseDataSet {
	
	public DataSetDefinition constructAllClientsDatasetDefinition() {
		
		String DATE_FORMAT = "dd-MMM-yyyy";
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.setName("ALLCLIENTS");
		dsd.addParameters(getParameters());
		dsd.setDescription("All Clients");
		dsd.addSortCriteria("Psn", SortCriteria.SortDirection.ASC);
		dsd.addParameter(new Parameter("location", "Location", Location.class));
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		DataConverter nameFormatter = new ObjectFormatter("{givenName} {middleName} {familyName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		
		PatientIdentifierType openmrsID = Context.getPatientService().getPatientIdentifierTypeByUuid(
		    SharedReportConstants.UNIQUE_ART_NUMBER_TYPE_UUID);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        openmrsID.getName(), openmrsID), identifierFormatter);
		
		PersonAttributeType phoneNumber = Context.getPersonService().getPersonAttributeTypeByUuid(
		    SharedReportConstants.PHONE_NUMBER_ATTRIBUTE_TYPE_UUID);
		
		PersonAttributeType alternativePhoneNumber = Context.getPersonService().getPersonAttributeTypeByUuid(
		    SharedReportConstants.ALTERNATIVE_PHONE_NUMBER_ATTRIBUTE_TYPE_UUID);
		
		ETLArtStartDateDataDefinition etlArtStartDateDataDefinition = new ETLArtStartDateDataDefinition();
		etlArtStartDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		StatusDataDefinition statusDataDefinition = new StatusDataDefinition();
		statusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		RegimenDataDefinition regimenDataDefinition = new RegimenDataDefinition();
		regimenDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		ArtStartDateDataDefinition artStartDateDataDefinition = new ArtStartDateDataDefinition();
		artStartDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Identifier", identifierDef, (String) null);
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Age", new AgeDataDefinition(), "", null);
		dsd.addColumn("Gender", new GenderDataDefinition(), "", null);
		dsd.addColumn("Status", statusDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Telephone", new PersonAttributeDataDefinition("Phone Number", phoneNumber), "",
		    new PersonAttributeDataConverter());
		dsd.addColumn("Alternative telephone", new PersonAttributeDataDefinition("Alternative Phone Number",
		        alternativePhoneNumber), "", new PersonAttributeDataConverter());
		dsd.addColumn("Date of ART initiation", etlArtStartDateDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Regimen", regimenDataDefinition, "endDate=${endDate}");
		dsd.addColumn("ART Start Date", artStartDateDataDefinition, "endDate=${endDate}");
		return dsd;
	}
	
}
