package org.openmrs.module.ssemrreports.reporting.library.data.definition;

import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

/**
 * BMI Data Definition
 */
@Caching(strategy = ConfigurationPropertyCachingStrategy.class)
public class BMIDataDefinition extends BaseDataDefinition implements PersonDataDefinition {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * Default Constructor
	 */
	public BMIDataDefinition() {
		super();
	}
	
	/**
	 * Constructor to populate name only
	 */
	public BMIDataDefinition(String name) {
		super(name);
	}
	
	//***** INSTANCE METHODS *****
	
	/**
	 * @see org.openmrs.module.reporting.data.DataDefinition#getDataType()
	 */
	public Class<?> getDataType() {
		return String.class;
	}
}
