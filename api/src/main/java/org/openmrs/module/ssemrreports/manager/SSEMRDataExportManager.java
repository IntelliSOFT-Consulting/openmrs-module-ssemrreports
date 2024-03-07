package org.openmrs.module.ssemrreports.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.ssemrreports.api.SSEMRReportsService;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportDesignResource;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.module.reporting.report.renderer.ExcelTemplateRenderer;
import org.openmrs.util.OpenmrsClassLoader;

/** Excel Data Export Manager for SSEMR reports */
public abstract class SSEMRDataExportManager extends SSEMRReportManager {
	
	/** @return the uuid for the report design for exporting to Excel */
	public abstract String getExcelDesignUuid();
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		ReportDesign excelDesign = ReportManagerUtil.createExcelDesign(getExcelDesignUuid(), reportDefinition);
		l.add(excelDesign);
		return l;
	}
	
	/**
	 * Auto generated method comment
	 * 
	 * @param reportDefinition the reportDesign to set
	 * @param resourceName
	 * @param reportDesignName
	 * @param properties
	 * @return
	 * @throws IOException
	 */
	public ReportDesign createXlsReportDesign(ReportDefinition reportDefinition, String resourceName,
	        String reportDesignName, String excelDesignUuid, Map<? extends Object, ? extends Object> properties)
	        throws IOException {
		
		SSEMRReportsService SSEMRReportsService = Context.getRegisteredComponent("ssemr.SSEMRReportsService",
		    SSEMRReportsService.class);
		if (StringUtils.isNotBlank(excelDesignUuid)) {
			//SSEMRReportsService.purgeReportDesignIfExists(excelDesignUuid);
		}
		
		ReportDesignResource resource = new ReportDesignResource();
		resource.setName(resourceName);
		resource.setExtension("xls");
		InputStream is = OpenmrsClassLoader.getInstance().getResourceAsStream(resourceName);
		resource.setContents(IOUtils.toByteArray(is));
		final ReportDesign design = new ReportDesign();
		design.setName(reportDesignName);
		design.setReportDefinition(reportDefinition);
		design.setRendererType(ExcelTemplateRenderer.class);
		design.addResource(resource);
		if (properties != null) {
			design.getProperties().putAll(properties);
		}
		if (excelDesignUuid != null && excelDesignUuid.length() > 1) {
			design.setUuid(excelDesignUuid);
		}
		resource.setReportDesign(design);
		
		return design;
	}
}
