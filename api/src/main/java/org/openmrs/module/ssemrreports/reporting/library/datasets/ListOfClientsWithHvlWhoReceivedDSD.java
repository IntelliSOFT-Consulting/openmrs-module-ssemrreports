package org.openmrs.module.ssemrreports.reporting.library.datasets;

import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ListOfClientsWithHvlWhoReceivedDSD extends SsemrBaseDataSet {
	
	/**
	 * Clients with HVL, who received EAC1 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac1Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC1 session");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT "
		                + "   vl.client_id,p.person_name_short as individual_name, "
		                + "   p.age,p.gender, mpi.identifier as uan,"
		                + "   f.pregnant,f.breastfeeding,"
		                + "   lvl.vl_results as last_vl,vl.encounter_datetime as date_of_eac,"
		                + "   addr.address3 as payam,addr.address4 as boma,addr.address6 as landmark, pa.value as tel,"
		                + "   CASE cl.client_id WHEN null THEN 'N' ELSE 'Y' END as linked,cl.name_of_cov_assigned as name_of_chw,"
		                + "   first_eac.eac_date as first_eac_date,second_eac.eac_date as second_eac_date,third_eac.eac_date as third_eac_date"
		                + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load vl "
		                + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = vl.client_id"
		                + " INNER JOIN ssemr_etl.mamba_dim_patient_identifier mpi on mpi.patient_id = vl.client_id AND mpi.identifier_type = 5"
		                + " LEFT JOIN (select c.client_id,c.patient_breastfeeding as breastfeeding,c.client_pregnant as pregnant, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c"
		                + "     ) f ON f.client_id = vl.client_id and f.seqnum = 1"
		                + " LEFT JOIN ssemr_etl.mamba_dim_person_address addr on addr.person_id = vl.client_id "
		                + " LEFT JOIN openmrs.person_attribute pa on pa.person_id = vl.client_id and pa.person_attribute_type_id = (select person_attribute_type_id from person_attribute_type where uuid = '14d4f066-15f5-102d-96e4-000c29c2a5d7') "
		                + " LEFT JOIN (select c.client_id,c.name_of_cov_assigned , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_community_linkage c"
		                + "     ) cl ON cl.client_id = vl.client_id and cl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,c.vl_results , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c WHERE DATE(c.encounter_datetime) <=:endDate"
		                + "     ) lvl ON lvl.client_id = vl.client_id and lvl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'First EAC Session'"
		                + "     ) first_eac ON first_eac.client_id = vl.client_id and first_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Second EAC Session'"
		                + "     ) second_eac ON second_eac.client_id = vl.client_id and second_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Third EAC Session'"
		                + "     ) third_eac ON third_eac.client_id = vl.client_id and third_eac.seqnum = 1"
		                + " WHERE vl.eac_session ='First EAC Session' AND vl.encounter_datetime BETWEEN :startDate AND :endDate");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received EAC2 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac2Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC2 session");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT "
		                + "   vl.client_id,p.person_name_short as individual_name, "
		                + "   p.age,p.gender, mpi.identifier as uan,"
		                + "   f.pregnant,f.breastfeeding,"
		                + "   lvl.vl_results as last_vl,vl.encounter_datetime as date_of_eac,"
		                + "   addr.address3 as payam,addr.address4 as boma,addr.address6 as landmark,'' as tel,"
		                + "   CASE cl.client_id WHEN null THEN 'N' ELSE 'Y' END as linked,cl.name_of_cov_assigned as name_of_chw,"
		                + "   first_eac.eac_date as first_eac_date,second_eac.eac_date as second_eac_date,third_eac.eac_date as third_eac_date"
		                + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load vl"
		                + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = vl.client_id"
		                + " INNER JOIN ssemr_etl.mamba_dim_patient_identifier mpi on mpi.patient_id = vl.client_id AND mpi.identifier_type = 5"
		                + " LEFT JOIN (select c.client_id,c.patient_breastfeeding as breastfeeding,c.client_pregnant as pregnant, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c"
		                + "     ) f ON f.client_id = vl.client_id and f.seqnum = 1"
		                + " LEFT JOIN ssemr_etl.mamba_dim_person_address addr on addr.person_id = vl.client_id "
		                + " LEFT JOIN (select c.client_id,c.name_of_cov_assigned , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_community_linkage c"
		                + "     ) cl ON cl.client_id = vl.client_id and cl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,c.vl_results , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c WHERE DATE(c.encounter_datetime) <=:endDate"
		                + "     ) lvl ON lvl.client_id = vl.client_id and lvl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'First EAC Session'"
		                + "     ) first_eac ON first_eac.client_id = vl.client_id and first_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Second EAC Session'"
		                + "     ) second_eac ON second_eac.client_id = vl.client_id and second_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Third EAC Session'"
		                + "     ) third_eac ON third_eac.client_id = vl.client_id and third_eac.seqnum = 1"
		                + " WHERE vl.eac_session ='Second EAC Session' AND vl.encounter_datetime BETWEEN :startDate AND :endDate");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received EAC3 session
	 * 
	 * @return
	 */
	public DataSetDefinition getEac3Session() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received EAC3 session");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT "
		                + "   vl.client_id,p.person_name_short as individual_name, "
		                + "   p.age,p.gender, mpi.identifier as uan,"
		                + "   f.pregnant,f.breastfeeding,"
		                + "   lvl.vl_results as last_vl,vl.encounter_datetime as date_of_eac,"
		                + "   addr.address3 as payam,addr.address4 as boma,addr.address6 as landmark,'' as tel,"
		                + "   CASE cl.client_id WHEN null THEN 'N' ELSE 'Y' END as linked,cl.name_of_cov_assigned as name_of_chw,"
		                + "   first_eac.eac_date as first_eac_date,second_eac.eac_date as second_eac_date,third_eac.eac_date as third_eac_date"
		                + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load vl"
		                + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = vl.client_id"
		                + " INNER JOIN ssemr_etl.mamba_dim_patient_identifier mpi on mpi.patient_id = vl.client_id AND mpi.identifier_type = 5"
		                + " LEFT JOIN (select c.client_id,c.patient_breastfeeding as breastfeeding,c.client_pregnant as pregnant, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c"
		                + "     ) f ON f.client_id = vl.client_id and f.seqnum = 1"
		                + " LEFT JOIN ssemr_etl.mamba_dim_person_address addr on addr.person_id = vl.client_id "
		                + " LEFT JOIN (select c.client_id,c.name_of_cov_assigned , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_community_linkage c"
		                + "     ) cl ON cl.client_id = vl.client_id and cl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,c.vl_results , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c WHERE DATE(c.encounter_datetime) <=:endDate"
		                + "     ) lvl ON lvl.client_id = vl.client_id and lvl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'First EAC Session'"
		                + "     ) first_eac ON first_eac.client_id = vl.client_id and first_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Second EAC Session'"
		                + "     ) second_eac ON second_eac.client_id = vl.client_id and second_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Third EAC Session'"
		                + "     ) third_eac ON third_eac.client_id = vl.client_id and third_eac.seqnum = 1"
		                + " WHERE vl.eac_session ='Third EAC Session' AND vl.encounter_datetime BETWEEN :startDate AND :endDate");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, who received extended EAC session
	 * 
	 * @return
	 */
	public DataSetDefinition getExtendedEacSession() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received Extended EAC session");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT "
		                + "   vl.client_id,p.person_name_short as individual_name, "
		                + "   p.age,p.gender, mpi.identifier as uan,"
		                + "   f.pregnant,f.breastfeeding,"
		                + "   lvl.vl_results as last_vl,vl.encounter_datetime as date_of_eac,"
		                + "   addr.address3 as payam,addr.address4 as boma,addr.address6 as landmark,'' as tel,"
		                + "   CASE cl.client_id WHEN null THEN 'N' ELSE 'Y' END as linked,cl.name_of_cov_assigned as name_of_chw,"
		                + "   first_eac.eac_date as first_eac_date,second_eac.eac_date as second_eac_date,third_eac.eac_date as third_eac_date"
		                + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load vl"
		                + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = vl.client_id"
		                + " INNER JOIN ssemr_etl.mamba_dim_patient_identifier mpi on mpi.patient_id = vl.client_id AND mpi.identifier_type = 5"
		                + " LEFT JOIN (select c.client_id,c.patient_breastfeeding as breastfeeding,c.client_pregnant as pregnant, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c"
		                + "     ) f ON f.client_id = vl.client_id and f.seqnum = 1"
		                + " LEFT JOIN ssemr_etl.mamba_dim_person_address addr on addr.person_id = vl.client_id "
		                + " LEFT JOIN (select c.client_id,c.name_of_cov_assigned , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_community_linkage c"
		                + "     ) cl ON cl.client_id = vl.client_id and cl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,c.vl_results , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c WHERE DATE(c.encounter_datetime) <=:endDate"
		                + "     ) lvl ON lvl.client_id = vl.client_id and lvl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'First EAC Session'"
		                + "     ) first_eac ON first_eac.client_id = vl.client_id and first_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Second EAC Session'"
		                + "     ) second_eac ON second_eac.client_id = vl.client_id and second_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Third EAC Session'"
		                + "     ) third_eac ON third_eac.client_id = vl.client_id and third_eac.seqnum = 1"
		                + " WHERE vl.eac_session ='Extended session of EAC' AND vl.encounter_datetime BETWEEN :startDate AND :endDate");
		return sqlDataSetDefinition;
	}
	
	/***
	 * Clients with HVL, Who received repeat test after EAC
	 * 
	 * @return
	 */
	public DataSetDefinition getRepeatTestAfterEacSession() {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName("Clients with HVL, who received Repeat test after EAC session");
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition
		        .setSqlQuery("SELECT "
		                + "   vl.client_id,p.person_name_short as individual_name, "
		                + "   p.age,p.gender, mpi.identifier as uan,"
		                + "   f.pregnant,f.breastfeeding,"
		                + "   lvl.vl_results as last_vl,vl.encounter_datetime as date_of_eac,"
		                + "   addr.address3 as payam,addr.address4 as boma,addr.address6 as landmark, pa.value as tel,"
		                + "   CASE cl.client_id WHEN null THEN 'N' ELSE 'Y' END as linked,cl.name_of_cov_assigned as name_of_chw,"
		                + "   first_eac.eac_date as first_eac_date,second_eac.eac_date as second_eac_date,third_eac.eac_date as third_eac_date, "
		                + "   vl.repeat_vl_sample_date as date_of_repeat_vl, "
		                + "   vl.repeat_vl_result as repeat_vl_results "
		                + " FROM ssemr_etl.ssemr_flat_encounter_high_viral_load vl"
		                + " INNER JOIN ssemr_etl.mamba_dim_person p on p.person_id = vl.client_id"
		                + " INNER JOIN ssemr_etl.mamba_dim_patient_identifier mpi on mpi.patient_id = vl.client_id AND mpi.identifier_type = 5"
		                + " LEFT JOIN (select c.client_id,c.patient_breastfeeding as breastfeeding,c.client_pregnant as pregnant, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c"
		                + "     ) f ON f.client_id = vl.client_id and f.seqnum = 1"
		                + " LEFT JOIN ssemr_etl.mamba_dim_person_address addr on addr.person_id = vl.client_id "
		                + " LEFT JOIN openmrs.person_attribute pa on pa.person_id = vl.client_id and pa.person_attribute_type_id = (select person_attribute_type_id from person_attribute_type where uuid = '14d4f066-15f5-102d-96e4-000c29c2a5d7') "
		                + " LEFT JOIN (select c.client_id,c.name_of_cov_assigned , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_community_linkage c"
		                + "     ) cl ON cl.client_id = vl.client_id and cl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,c.vl_results , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_hiv_care_follow_up c WHERE DATE(c.encounter_datetime) <=:endDate"
		                + "     ) lvl ON lvl.client_id = vl.client_id and lvl.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'First EAC Session'"
		                + "     ) first_eac ON first_eac.client_id = vl.client_id and first_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date , row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Second EAC Session'"
		                + "     ) second_eac ON second_eac.client_id = vl.client_id and second_eac.seqnum = 1"
		                + " LEFT JOIN (select c.client_id,DATE(c.encounter_datetime) as eac_date, row_number() over (partition by c.client_id order by DATE(c.encounter_datetime) desc) as seqnum"
		                + "      from ssemr_etl.ssemr_flat_encounter_high_viral_load c WHERE DATE(c.encounter_datetime) <=:endDate AND c.eac_session = 'Repeat test after EAC'"
		                + "     ) third_eac ON third_eac.client_id = vl.client_id and third_eac.seqnum = 1"
		                + " WHERE vl.repeat_vl_sample_date IS NOT NULL AND vl.encounter_datetime BETWEEN :startDate AND :endDate");
		return sqlDataSetDefinition;
	}
	
}
