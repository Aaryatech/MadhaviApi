package com.ats.webapi.repo.cloudkitchen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.SellBillDataForPrint;

public interface SellBillDataForPrintRepo extends JpaRepository<SellBillDataForPrint, Integer> {

	@Query(value="SELECT\r\n" + 
			"    *,\r\n" + 
			"    oh.is_agent,\r\n" + 
			"    CASE WHEN oh.is_agent = 1 THEN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.agent_name\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.agent_id = oh.order_delivered_by\r\n" + 
			") ELSE(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_name\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.fr_emp_id = oh.order_delivered_by\r\n" + 
			")\r\n" + 
			"END AS del_boy_name,\r\n" + 
			"CASE WHEN oh.is_agent = 1 THEN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.mobile_no\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.agent_id = oh.order_delivered_by\r\n" + 
			") ELSE(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_contact\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.fr_emp_id = oh.order_delivered_by\r\n" + 
			")\r\n" + 
			"END AS del_boy_mobile, c.cust_name,\r\n" + 
			"c.gst_no, e.fr_emp_name AS emp_name \r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header bh,\r\n" + 
			"    tn_order_header oh,  m_customer c,  m_fr_emp e \r\n" + 
			"WHERE\r\n" + 
			"    bh.ext_int2 = :orderId AND bh.ext_int2 = oh.order_id AND bh.cust_id=c.cust_id AND oh.cust_id=c.cust_id AND bh.ext_int1=e.fr_emp_id ",nativeQuery=true)
	SellBillDataForPrint getBillHeaderByOrderId(@Param("orderId") int orderId);
	
}
