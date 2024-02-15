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
import org.openmrs.module.ssemrreports.reporting.library.cohorts.OpdReportCohortQueries;
import org.openmrs.module.ssemrreports.reporting.library.cohorts.SharedCohortQueries;
import org.openmrs.module.ssemrreports.reporting.utils.SsemrReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SsemrCommonDimension {
	
	private final SharedCohortQueries sharedCohortQueries;
	
	private final OpdReportCohortQueries opdReportCohortQueries;
	
	@Autowired
	public SsemrCommonDimension(SharedCohortQueries sharedCohortQueries, OpdReportCohortQueries opdReportCohortQueries) {
		this.sharedCohortQueries = sharedCohortQueries;
		this.opdReportCohortQueries = opdReportCohortQueries;
	}
	
	/**
	 * Gender dimension
	 * 
	 * @return the {@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension gender() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("gender");
		dim.addCohortDefinition("M", SsemrReportUtils.map(sharedCohortQueries.maleCohort(), ""));
		dim.addCohortDefinition("F", SsemrReportUtils.map(sharedCohortQueries.femaleCohort(), ""));
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
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(0, 0), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("1-4",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(1, 4), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("5-9",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(5, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("10-14",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(10, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-19",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(15, 19), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("20-24",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(20, 24), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("25-29",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(25, 29), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30-34",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(30, 34), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("35-39",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(35, 39), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("40-44",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(40, 44), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("45-49",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(45, 49), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("50+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(50, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("5+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(5, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("0-5",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(0, 5), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("6-14",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(6, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-29",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(15, 29), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(30, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("<15",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(0, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("30-39",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(30, 39), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("45+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(45, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("0-14",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(0, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("1-9",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(1, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("40-49",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(40, 49), "effectiveDate=${effectiveDate}"));
		
		dim.addCohortDefinition("10-12",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(10, 12), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("13-14",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(13, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("50-54",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(50, 54), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("55-59",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(55, 59), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("60-64",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(60, 64), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("65-69",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(65, 69), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("70-74",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(70, 74), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("75-79",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(75, 79), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("80-84",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(80, 84), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("85+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(85, 200), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("UK", SsemrReportUtils.map(sharedCohortQueries.createUnknownAgeCohort(), ""));
		dim.addCohortDefinition("60+",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(60, 200), "effectiveDate=${effectiveDate}"));
		
		// regimen age-groups dimensions
		dim.addCohortDefinition("0-9",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(0, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-49",
		    SsemrReportUtils.map(sharedCohortQueries.createXtoYAgeCohort(15, 49), "effectiveDate=${effectiveDate}"));
		return dim;
	}
	
	public CohortDefinitionDimension getAttendanceType() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Patient attendance type dimension");
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addParameter(new Parameter("location", "Location", Location.class));
		dim.addCohortDefinition("N", SsemrReportUtils.map(
		    opdReportCohortQueries.getNewReattendanceCases(SharedReportConstants.NEW_ATTENDANCES),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		dim.addCohortDefinition("R", SsemrReportUtils.map(
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
		    SsemrReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "N",
		    SsemrReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivNegative.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "U",
		    SsemrReportUtils.map(
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
		dim.addCohortDefinition("C", SsemrReportUtils.map(sharedCohortQueries.getCitizenType("Citizen"), ""));
		dim.addCohortDefinition("CN", SsemrReportUtils.map(sharedCohortQueries.getCitizenType("Non-citizen"), ""));
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
		    SsemrReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		dim.addCohortDefinition(
		    "PRG",
		    SsemrReportUtils.map(
		        sharedCohortQueries.getPatientsWithObsByEndDate(Arrays.asList(hivStatus.getConceptId()),
		            Arrays.asList(hivPositive.getConceptId())), "endDate=${endDate},location=${location}"));
		return dim;
	}
}
