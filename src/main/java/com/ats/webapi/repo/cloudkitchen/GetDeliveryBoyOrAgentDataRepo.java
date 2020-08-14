package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.GetDeliveryBoyOrAgentData;

public interface GetDeliveryBoyOrAgentDataRepo  extends JpaRepository<GetDeliveryBoyOrAgentData, Integer> {

	@Query(value="SELECT UUID() AS u_id, e.fr_emp_id AS id, e.fr_emp_name AS NAME, e.fr_emp_contact AS mobile, e.fr_id, '' AS city_ids, "
			+ "0 AS is_agent FROM m_fr_emp e WHERE e.designation = 4 AND e.fr_id = :frId ORDER BY e.fr_emp_name",nativeQuery=true)
	List<GetDeliveryBoyOrAgentData> getDeliveryBoyListByFr(@Param("frId") int frId);

	@Query(value="SELECT UUID() AS u_id, a.agent_id AS id, a.agent_name AS name, a.mobile_no AS mobile, a.franchise_id AS fr_id, "
			+ "a.area_ids AS city_ids, 1 AS is_agent FROM mn_agent a WHERE FIND_IN_SET(:frId, a.franchise_id) "
			+ "AND FIND_IN_SET( ( SELECT city_id FROM mn_cust_address WHERE cust_address_id = :custAddId ), a.area_ids ) ORDER BY a.agent_name",nativeQuery=true)
	List<GetDeliveryBoyOrAgentData> getAgentListByFr(@Param("frId") int frId, @Param("custAddId") int custAddId);

}
