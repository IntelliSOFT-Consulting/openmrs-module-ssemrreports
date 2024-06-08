/**
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
package org.openmrs.module.ssemrreports.reporting.library.data.converter;

import org.openmrs.PersonAttribute;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.ssemrreports.reporting.utils.constants.reports.shared.SharedReportConstants;

public class PersonAttributeDataConverter implements DataConverter {
	
	@Override
	public Object convert(Object obj) {
		if (obj == null) {
			return "";
		}
		
		PersonAttribute attribute = (PersonAttribute) obj;
		String value = attribute.getValue();
		
		// Check if the attribute is a phone number
		if (isPhoneNumberAttribute(attribute)) {
			return convertPhoneNumber(value);
		}
		
		return value;
	}
	
	@Override
	public Class<?> getInputDataType() {
		return PersonAttribute.class;
	}
	
	@Override
	public Class<?> getDataType() {
		return String.class;
	}
	
	private boolean isPhoneNumberAttribute(PersonAttribute attribute) {
		return SharedReportConstants.PHONE_NUMBER_ATTRIBUTE_TYPE_UUID.equals(attribute.getAttributeType().getUuid());
	}
	
	private String convertPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			return "";
		}
		// Wrap the phone number in quotes to ensure it is treated as text
		return "\"" + phoneNumber + "\"";
	}
}
