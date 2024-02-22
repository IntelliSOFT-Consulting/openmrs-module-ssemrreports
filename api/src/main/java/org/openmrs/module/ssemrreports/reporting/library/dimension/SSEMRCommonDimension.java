/*
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.ssemrreports.reporting.library.dimension;

import java.util.Arrays;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.MerCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.OpdReportCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.SharedCohortQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SSEMRCommonDimension {
	
	private final SharedCohortQueries sharedCohortQueries;
	
	private final OpdReportCohortQueries opdReportCohortQueries;
	
	private final MerCohortQueries merCohortQueries;
	
	@Autowired
	public SSEMRCommonDimension(SharedCohortQueries sharedCohortQueries, OpdReportCohortQueries opdReportCohortQueries,
	    MerCohortQueries merCohortQueries) {
		this.sharedCohortQueries = sharedCohortQueries;
		this.opdReportCohortQueries = opdReportCohortQueries;
		this.merCohortQueries = merCohortQueries;
	}
	
	/**
	 * Gender dimension
	 * 
	 * @return the {@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension gender() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("gender");
		dim.addCohortDefinition("M", SSEMRReportUtils.map(sharedCohortQueries.getClientGender("M"), ""));
		dim.addCohortDefinition("F", SSEMRReportUtils.map(sharedCohortQueries.getClientGender("F"), ""));
		return dim;
	}
	
	/**
	 * Age dimension
	 * 
	 * @return the {@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension age() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("age");
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.addCohortDefinition("<1",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(0, 0), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("1-4",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(1, 4), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("5-9",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(5, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("10-14",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(10, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-19",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(15, 19), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("20-24",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(20, 24), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("25-29",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(25, 29), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30-34",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(30, 34), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("35-39",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(35, 39), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("40-44",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(40, 44), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("45-49",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(45, 49), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("50+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(50, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("5+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(5, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("0-5",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(0, 5), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("6-14",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(6, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-29",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(15, 29), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(30, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("<15",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(0, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30-39",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(30, 39), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("45+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(45, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("0-14",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(0, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("1-9",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(1, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("40-49",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(40, 49), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("10-12",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(10, 12), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("13-14",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(13, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("50-54",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(50, 54), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("55-59",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(55, 59), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("60-64",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(60, 64), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("65-69",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(65, 69), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("70-74",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(70, 74), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("75-79",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(75, 79), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("80-84",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(80, 84), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("85+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(85, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("UK", SSEMRReportUtils.map(sharedCohortQueries.createUnknownAgeCohort(), ""));
		dim.addCohortDefinition("60+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(60, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("65+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(65, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15+",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(15, 200), "effectiveDate=${effectiveDate}"));
		
		// regimen age-groups dimensions
		dim.addCohortDefinition("0-9",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(0, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-49",
		    SSEMRReportUtils.map(sharedCohortQueries.getClientAge(15, 49), "effectiveDate=${effectiveDate}"));
		return dim;
	}
	
	public CohortDefinitionDimension getAttendanceType() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Patient attendance type dimension");
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addParameter(new Parameter("location", "Location", Location.class));
		dim.addCohortDefinition("N", SSEMRReportUtils.map(
		    opdReportCohortQueries.getNewReattendanceCases(SharedReportConstants.NEW_ATTENDANCES),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		dim.addCohortDefinition("R", SSEMRReportUtils.map(
		    opdReportCohortQueries.getNewReattendanceCases(SharedReportConstants.REPEAT_ATTENDANCES),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		return dim;
	}
	
	public CohortDefinitionDimension getHivStatus() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("HIV status for the patient dim");
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addParameter(new Parameter("location", "Location", Location.class));
		Concept hivStatus = Context.getConceptService().getConceptByUuid(SharedReportConstants.HIV_STATUS);
		Concept hivPositive = Context.getConceptService().getConceptByUuid(
		    SharedReportConstants.HIV_STATUS_POSITIVE_CONCEPT_UUID);
		Concept hivNegative = Context.getConceptService().getConceptByUuid(
		    SharedReportConstants.HIV_STATUS_NEGATIVE_CONCEPT_UUID);
		Concept hivUnknown = Context.getConceptService().getConceptByUuid(
		    SharedReportConstants.HIV_STATUS_UNKNOWN_CONCEPT_UUID);
		dim.addCohortDefinition(
		    "P",
		    SSEMRReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "N",
		    SSEMRReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivNegative.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "U",
		    SSEMRReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivUnknown.getConceptId())), "endDate=${endDate},location=${location}"));
		
		return dim;
	}
	
	public CohortDefinitionDimension getNumberOfPatientsWithAdverseEvents() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		String mappings = "startDate=${startDate},endDate=${endDate},location=${location}";
		dim.setName("Number of VMMC clients with adverse events  dim");
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("location", "Location", Location.class));
		
		return dim;
	}
	
	public CohortDefinitionDimension getCitizenType() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Get citizen type dim");
		dim.addCohortDefinition("C", SSEMRReportUtils.map(sharedCohortQueries.getCitizenType("Citizen"), ""));
		dim.addCohortDefinition("CN", SSEMRReportUtils.map(sharedCohortQueries.getCitizenType("Non-citizen"), ""));
		return dim;
	}
	
	public CohortDefinitionDimension getGeneralDisags() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Other dissggregstions needed for the ART monthly report");
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("location", "Location", Location.class));
		Concept hivStatus = Context.getConceptService().getConceptByUuid(SharedReportConstants.HIV_STATUS);
		Concept hivPositive = Context.getConceptService().getConceptByUuid(
		    SharedReportConstants.HIV_STATUS_POSITIVE_CONCEPT_UUID);
		dim.addCohortDefinition(
		    "PLWD",
		    SSEMRReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "PRG",
		    SSEMRReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		return dim;
	}
	
	public CohortDefinitionDimension getDispensingQuantityDimension() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("ARV Dispensing quantity dimension");
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		CohortDefinition less3m = merCohortQueries.getTxMlIitL3mCohorts();
		CohortDefinition threeTo5m = merCohortQueries.getTxMlIit3To5mCohorts();
		CohortDefinition more6m = merCohortQueries.getTxMlIitM6mCohorts();
		dim.addCohortDefinition("<3m", SSEMRReportUtils.map(less3m, "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("3-5m", SSEMRReportUtils.map(threeTo5m, "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition(">6m", SSEMRReportUtils.map(more6m, "startDate=${startDate},endDate=${endDate}"));
		return dim;
	}
}
