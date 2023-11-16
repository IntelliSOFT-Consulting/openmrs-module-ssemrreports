/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ssemrreports;

import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.ohrimambacore.task.FlattenTableTask;
import org.openmrs.module.ssemrreports.reporting.SSEMRReportInitializer;
import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.Task;
import org.openmrs.scheduler.TaskDefinition;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class SSEMRReportsActivator extends BaseModuleActivator {
	
	private static Log log = LogFactory.getLog(SSEMRReportsActivator.class);
	
	private SSEMRReportInitializer reportsInitializer = new SSEMRReportInitializer();
	
	/**
	 * @see #started()
	 */
	public void started() {
		log.info("MambaETL DB Flattening Task registering..");
		registerTask("MambaETL Task", "MambaETL Task - To Flatten and Prepare Reporting Data.", FlattenTableTask.class,
		    60 * 60 * 12L, true);
		try {
			reportsInitializer.purgeReports();
			// ssGlobalPropertyService.removeSsGlobalPropertiesEntries("ssemrreports");
			reportsInitializer.initializeReports();
		}
		catch (Exception e) {
			throw e;
		}
		
		log.info("Started Botswanaemr Reports");
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown SSEMR Reports");
	}
	
	private static boolean registerTask(String name, String description, Class<? extends Task> clazz, long interval,
	        boolean startOnStartup) {
		try {
			Context.addProxyPrivilege("Manage Scheduler");
			
			TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(name);
			if (taskDef == null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 20);
				taskDef = new TaskDefinition();
				taskDef.setTaskClass(clazz.getCanonicalName());
				taskDef.setStartOnStartup(startOnStartup);
				taskDef.setRepeatInterval(interval);
				taskDef.setStarted(true);
				taskDef.setStartTime(cal.getTime());
				taskDef.setName(name);
				taskDef.setUuid(UUID.randomUUID().toString());
				taskDef.setDescription(description);
				Context.getSchedulerService().scheduleTask(taskDef);
				//Context.getSchedulerService().saveTaskDefinition(taskDef);
			}
			System.out.println("A Task '" + name + "' has been Registered Successfully!");
		}
		catch (SchedulerException ex) {
			log.warn("Unable to register task '" + name + "' with scheduler", ex);
			return false;
		}
		finally {
			Context.removeProxyPrivilege("Manage Scheduler");
		}
		return true;
	}
	
}
