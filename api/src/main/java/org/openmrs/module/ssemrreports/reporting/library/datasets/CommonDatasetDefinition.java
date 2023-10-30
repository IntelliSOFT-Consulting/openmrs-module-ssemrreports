package org.openmrs.module.ssemrreports.reporting.library.datasets;

import java.util.Date;

import org.openmrs.module.ssemrreports.reporting.library.queries.CommonQueries;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.patient.definition.SqlPatientDataDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class CommonDatasetDefinition {
	
	public static DataDefinition getObsCodedResultsInObsGroupDataset(int firstQuestion, int firstAnswer, int secondQuestion) {
		SqlPatientDataDefinition dfn = new SqlPatientDataDefinition();
		dfn.setName("Get patient with their respective coded values sharing obs group");
		dfn.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dfn.addParameter(new Parameter("endDate", "End Date", Date.class));
		dfn.setQuery(CommonQueries.getObsCodedResultsInObsGroup(firstQuestion, firstAnswer, secondQuestion));
		return dfn;
	}
	
	public static DataDefinition getObsDateResultsInObsGroupDataset(int firstQuestion, int firstAnswer, int secondQuestion) {
		SqlPatientDataDefinition dfn = new SqlPatientDataDefinition();
		dfn.setName("Get patient with their respective obs datetime sharing obs group");
		dfn.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dfn.addParameter(new Parameter("endDate", "End Date", Date.class));
		dfn.setQuery(CommonQueries.getObsDateResultsInObsGroup(firstQuestion, firstAnswer, secondQuestion));
		return dfn;
	}
}
