package org.openmrs.module.ssemrreports.reporting.library.reports;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openmrs.LocationAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.ssemrreports.manager.SSEMRDataExportManager;
import org.openmrs.module.ssemrreports.reporting.library.datasets.DistrictDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.datasets.HtsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.library.datasets.InventoryItemsDatasetDefinition;
import org.openmrs.module.ssemrreports.reporting.utils.SSEMRReportUtils;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;
import org.openmrs.module.ssemrreports.reporting.utils.constants.templates.shared.SharedTemplatesConstants;
import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupHtsMonthlyReport extends SSEMRDataExportManager {
	
	private final DistrictDatasetDefinition districtDatasetDefinition;
	
	private final HtsDatasetDefinition htsDatasetDefinition;
	
	private final InventoryItemsDatasetDefinition inventoryItemsDatasetDefinition;
	
	@Autowired
	public SetupHtsMonthlyReport(DistrictDatasetDefinition districtDatasetDefinition,
	    HtsDatasetDefinition htsDatasetDefinition, InventoryItemsDatasetDefinition inventoryItemsDatasetDefinition) {
		this.districtDatasetDefinition = districtDatasetDefinition;
		this.htsDatasetDefinition = htsDatasetDefinition;
		this.inventoryItemsDatasetDefinition = inventoryItemsDatasetDefinition;
	}
	
	@Override
	public String getExcelDesignUuid() {
		return SharedTemplatesConstants.HTS_REPORT_TEMPLATE;
	}
	
	@Override
	public String getUuid() {
		return SharedReportConstants.HTS_REPORT_UUID;
	}
	
	@Override
	public String getName() {
		return "HTS monthly report";
	}
	
	@Override
	public String getDescription() {
		return "HTS monthly report within period";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		LocationAttributeType mflCodeAttributeType = Context.getLocationService().getLocationAttributeTypeByUuid(
		    "8a845a89-6aa5-4111-81d3-0af31c45c002");
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(districtDatasetDefinition.getParameters());
		rd.addDataSetDefinition("DT", Mapped.mapStraightThrough(districtDatasetDefinition
		        .getAddressDataset(mflCodeAttributeType.getLocationAttributeTypeId())));
		rd.addDataSetDefinition("HTS", SSEMRReportUtils.map(htsDatasetDefinition.constructHtsDataset(),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T1DET", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "90A2A098-7E6C-4819-90F3-A60C80C26B4D"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T1BIO", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "582F4261-3215-4822-A0BC-A428A54B0A75"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T1ICT", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "01C98C86-E495-40BF-B8FB-20057092D4D4"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T1KHB", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "FBE3028E-E61A-4544-AA53-BCCBA39BB49B"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T1KHB", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "FBE3028E-E61A-4544-AA53-BCCBA39BB49B"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T2UNI", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "f7aa65bc-054d-48a7-9b94-a601f5846513"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T2FR", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "81a15e96-a60d-4345-b841-807aca604cf7"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T2BIO", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "bc3ac95d-019c-42ec-a43f-da3d4e225bdc"),
		    "endDate=${endDate},location=${location}"));
		rd.addDataSetDefinition("T2IMM", SSEMRReportUtils.map(
		    inventoryItemsDatasetDefinition.getInventoryItems("distribution", "289f8d64-aced-4e87-8fe1-e10836da3d3a"),
		    "endDate=${endDate},location=${location}"));
		return rd;
	}
	
	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		ReportDesign reportDesign = null;
		try {
			reportDesign = createXlsReportDesign(reportDefinition, "htsReport.xls", "HTS monthly report",
			    getExcelDesignUuid(), null);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
