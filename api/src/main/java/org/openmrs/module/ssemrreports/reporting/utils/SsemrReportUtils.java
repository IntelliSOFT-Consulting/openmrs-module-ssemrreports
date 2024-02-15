/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ssemrreports.reporting.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.patient.definition.EncountersForPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.SqlPatientDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ObsForPersonDataDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.openmrs.module.reporting.evaluation.parameter.ParameterizableUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;

public class SsemrReportUtils {
	
	/**
	 * @param parameterizable
	 * @param mappings
	 * @param <T>
	 * @return
	 */
	public static <T extends Parameterizable> Mapped<T> map(T parameterizable, String mappings) {
		if (parameterizable == null) {
			throw new IllegalArgumentException("Parameterizable cannot be null");
		}
		String m = mappings != null ? mappings : ""; // probably not necessary, just to be safe
		return new Mapped<T>(parameterizable, ParameterizableUtil.createParameterMappings(m));
	}
	
	public static Program getProgram(String lookup) {
		Program program = Context.getProgramWorkflowService().getProgramByUuid(lookup);
		if (program == null) {
			program = Context.getProgramWorkflowService().getProgramByName(lookup);
		}
		if (program == null) {
			for (Program p : Context.getProgramWorkflowService().getAllPrograms()) {
				if (p.getName().equalsIgnoreCase(lookup)) {
					program = p;
				}
			}
		}
		if (program == null) {
			try {
				program = Context.getProgramWorkflowService().getProgram(Integer.parseInt(lookup));
			}
			catch (Exception e) {
				// DO NOTHING
			}
		}
		if (program == null) {
			throw new RuntimeException("Unable to find program using key: " + lookup);
		}
		
		return program;
	}
	
	public static EncounterType getEncounterType(String lookup) {
		EncounterType encounterType = Context.getEncounterService().getEncounterTypeByUuid(lookup);
		
		if (encounterType == null) {
			throw new RuntimeException("Unable to find encounter using key: " + lookup);
		}
		
		return encounterType;
	}
	
	/**
	 * Purges a Report Definition from the database
	 * 
	 * @param reportManager the Report Definition
	 */
	public static void purgeReportDefinition(ReportManager reportManager) {
		ReportDefinition findDefinition = null;
		if (reportManager != null) {
			findDefinition = findReportDefinition(reportManager.getUuid());
		}
		ReportDefinitionService reportService = (ReportDefinitionService) Context.getService(ReportDefinitionService.class);
		if (findDefinition != null) {
			reportService.purgeDefinition(findDefinition);
			
			// Purge Global property used to track version of report definition
			String gpName = "reporting.reportManager." + reportManager.getUuid() + ".version";
			GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(gpName);
			if (gp != null) {
				Context.getAdministrationService().purgeGlobalProperty(gp);
			}
		}
	}
	
	/**
	 * Returns the Report Definition matching the provided uuid.
	 * 
	 * @param uuid Report Uuid
	 * @throws RuntimeException a RuntimeException if the Report Definition can't be found
	 * @return Report Definition object
	 */
	public static ReportDefinition findReportDefinition(String uuid) {
		ReportDefinitionService reportService = (ReportDefinitionService) Context.getService(ReportDefinitionService.class);
		return reportService.getDefinitionByUuid(uuid);
	}
	
	/**
	 * Setup a Report Definition in a database
	 * 
	 * @param reportManager the Report Definition
	 */
	public static void setupReportDefinition(ReportManager reportManager) {
		if (reportManager != null) {
			ReportManagerUtil.setupReport(reportManager);
		}
	}
	
	public static String formatDate(Date date, String format) {
		
		Format formatter = new SimpleDateFormat(format);
		
		return formatter.format(date);
	}
	
	public static String formatDates(Date date) {
		
		Format formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(date);
		
		return s;
		
	}
	
	public static DataDefinition getFirstOrLastObservationForPatient(Concept question, TimeQualifier which) {
		ObsForPersonDataDefinition definition = new ObsForPersonDataDefinition();
		definition.setName("Observations for the patient per given attributes");
		definition.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		definition.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		definition.setQuestion(question);
		definition.setWhich(which);
		return definition;
	}
	
	public static List<Obs> obsListPerPerson(Obs obs, Concept question) {
		return Context.getObsService().getObservationsByPersonAndConcept(obs.getPerson(), question);
	}
	
	public static DataDefinition getLastEncounterForPatient(EncounterType encounterType) {
		EncountersForPatientDataDefinition def = new EncountersForPatientDataDefinition();
		def.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		def.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		def.addParameter(new Parameter("locationList", "Location", Location.class));
		if (encounterType != null) {
			def.setName("last encounter of type " + encounterType.getName());
			def.addType(encounterType);
		} else {
			def.setName("last encounter of any type");
		}
		def.setWhich(TimeQualifier.LAST);
		return def;
	}
	
	public static DataDefinition getObsForChildren(Integer conceptId) {
		SqlPatientDataDefinition definition = new SqlPatientDataDefinition();
		definition.setName("Obs for children indicators");
		definition.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		definition.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		definition
		        .setSql("SELECT p.patient_id,o.value_coded FROM patient p "
		                + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
		                + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		                + " INNER JOIN person pe ON p.patient_id=pe.person_id "
		                + " WHERE p.voided= 0 AND e.voided= 0 AND o.voided=0 AND e.encounter_datetime BETWEEN :onOrAfter AND :onOrBefore "
		                + " AND TIMESTAMPDIFF(YEAR, pe.birthdate, :onOrBefore) < 15 AND o.concept_id =" + conceptId);
		
		return definition;
	}
	
	public static Program getProgramByIdOrUuid(String tbProgramUuid) {
		return null;
	}
	
	public static Concept getConcept(String conceptUuid) {
		return null;
	}
}
