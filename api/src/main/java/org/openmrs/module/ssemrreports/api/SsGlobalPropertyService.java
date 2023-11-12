package org.openmrs.module.ssemrreports.api;

import java.util.List;

import org.openmrs.GlobalProperty;
import org.openmrs.api.OpenmrsService;

/** The class is used to perform operations in the {@link GlobalProperty} data. */
public interface SsGlobalPropertyService extends OpenmrsService {
	
	/**
	 * Removes one or several {@link GlobalProperty}(s) with the given pattern name.
	 * 
	 * @param patternName
	 * @return removed global property(s)
	 */
	List<GlobalProperty> removeSsGlobalPropertiesEntries(String patternName);
}
