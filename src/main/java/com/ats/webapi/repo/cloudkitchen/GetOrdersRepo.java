package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.GetOrders;

public interface GetOrdersRepo extends JpaRepository<GetOrders, Integer> {

	@Query(value="SELECT\r\n" + 
			"    h.order_id,\r\n" + 
			"    h.order_no,\r\n" + 
			"    h.order_date,\r\n" + 
			"    h.fr_id,\r\n" + 
			"    h.cust_id,\r\n" + 
			"    c.cust_name,\r\n" + 
			"    h.status,\r\n" + 
			"    h.order_status,\r\n" + 
			"    h.order_platform,\r\n" + 
			"    h.delivery_date,\r\n" + 
			"    h.delivery_time,\r\n" + 
			"    h.whatsapp_no,\r\n" + 
			"    h.total_amt, h.delivery_km AS km \r\n" + 
			"FROM\r\n" + 
			"    tn_order_header h,\r\n" + 
			"    mn_customer c\r\n" + 
			"WHERE\r\n" + 
			"    h.del_status = 0 AND c.del_status = 0 AND h.cust_id = c.cust_id AND h.fr_id=:frId AND h.delivery_date=:delDate \r\n" + 
			"ORDER BY\r\n" + 
			"    h.status",nativeQuery=true)
	List<GetOrders> getAllOrdersByFr(@Param("frId") int frId, @Param("delDate") String delDate);

	
}
