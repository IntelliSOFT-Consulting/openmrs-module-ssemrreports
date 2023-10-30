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
package org.openmrs.module.ssemrreports.reporting.library.cohorts;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.EncounterCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.ssemrreports.reporting.library.queries.ArtQueries;
import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.springframework.stereotype.Component;

@Component
public class SharedCohortQueries {
	
	/**
	 * Patients who are female
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition femaleCohort() {
		GenderCohortDefinition cohort = new GenderCohortDefinition();
		cohort.setName("femaleCohort");
		cohort.setFemaleIncluded(true);
		cohort.setMaleIncluded(false);
		return cohort;
	}
	
	/**
	 * Patients who are male
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition maleCohort() {
		GenderCohortDefinition cohort = new GenderCohortDefinition();
		cohort.setName("maleCohort");
		cohort.setMaleIncluded(true);
		cohort.setFemaleIncluded(false);
		return cohort;
	}
	
	/**
	 * Get patients dimesion age
	 * 
	 * @param minAge
	 * @param maxAge
	 * @return
	 */
	public CohortDefinition createXtoYAgeCohort(Integer minAge, Integer maxAge) {
		AgeCohortDefinition xToYCohort = new AgeCohortDefinition();
		xToYCohort.setName("age");
		if (minAge != null) {
			xToYCohort.setMinAge(minAge);
		}
		if (maxAge != null) {
			xToYCohort.setMaxAge(maxAge);
		}
		xToYCohort.addParameter(new Parameter("effectiveDate", "effectiveDate", Date.class));
		return xToYCohort;
	}
	
	public CohortDefinition hasObs(Concept question, List<EncounterType> encounterTypes, List<Concept> answers) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("Patient has at least observations recorded on a question");
		cd.addParameter(new Parameter("onOrBefore", "End Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("locationList", "Location", Location.class));
		cd.setQuestion(question);
		cd.setOperator(SetComparator.IN);
		if (encounterTypes.size() > 0) {
			cd.setEncounterTypeList(encounterTypes);
		}
		if (answers.size() > 0) {
			cd.setValueList(answers);
		}
		return cd;
	}
	
	public CohortDefinition hasEncounters(List<EncounterType> encounterTypes, TimeQualifier timeQualifier) {
		EncounterCohortDefinition cd = new EncounterCohortDefinition();
		cd.setName("Has encounter on a location and date");
		cd.addParameter(new Parameter("onOrBefore", "End Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("locationList", "Location", Location.class));
		cd.setEncounterTypeList(encounterTypes);
		cd.setTimeQualifier(timeQualifier);
		return cd;
		
	}
	
	/**
	 * Person with Unknown age, the birthdate column is null
	 * 
	 * @return CohortDefinition
	 */
	public CohortDefinition createUnknownAgeCohort() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get number of clients with no birth date assigned");
		sql.setQuery("SELECT p.patient_id FROM patient p JOIN person pr ON p.patient_id = pr.person_id WHERE pr.birthdate IS NULL");
		return sql;
	}
	
	public CohortDefinition getPatientsWithObsByEndDate(List<Integer> questions, List<Integer> answers) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations without considering encounter by reporting end date");
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObsByEndDate(questions, answers));
		
		return cd;
	}
	
	public CohortDefinition getCitizenType(String response) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get citizen type");
		sql.setQuery(ArtQueries.getCitizenAndNonCitizensQuery(response));
		return sql;
	}
	
	public CohortDefinition getPatientsWithObs(int encounterId, List<Integer> questions, List<Integer> answers) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients with coded observations");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.hasObs(encounterId, questions, answers));
		
		return cd;
	}
	
	public CohortDefinition getNumberPatientsSeenPerEncounter(List<Integer> encounterIds) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Number of patients sen per the encounter");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.addParameter(new Parameter("location", "location", Location.class));
		
		cd.setQuery(CommonQueries.getBasePatientsBasedOnEncounter(encounterIds));
		
		return cd;
	}
	
}
