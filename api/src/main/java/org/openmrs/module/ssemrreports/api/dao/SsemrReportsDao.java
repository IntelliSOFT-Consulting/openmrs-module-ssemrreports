/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ssemrreports.api.dao;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.ssemrreports.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ssemrreports.SsemrReportsDao")
public class SsemrReportsDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	public String getSerializedObjectByReportDesignUUID(String uuid) {
		List<String> list = sessionFactory
		        .getCurrentSession()
		        .createSQLQuery(
		            "select concat(report_definition_uuid, '') as uuid from reporting_report_design where reporting_report_design.uuid = ?")
		        .setString(1, uuid).list();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public void purgeReportDesign(String designUuid, String serializedObjectUuid) {
		final DbSession session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.createSQLQuery(
		    "delete from reporting_report_design_resource " + "where reporting_report_design_resource.report_design_id = ("
		            + "select id from reporting_report_design where reporting_report_design.uuid = ?)")
		        .setString(0, designUuid).executeUpdate();
		session.createSQLQuery("delete from reporting_report_design where reporting_report_design.uuid = ?")
		        .setString(0, designUuid).executeUpdate();
		session.createSQLQuery("delete from serialized_object where uuid = ?").setString(0, serializedObjectUuid)
		        .executeUpdate();
		transaction.commit();
	}
}
