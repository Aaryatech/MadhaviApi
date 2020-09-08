package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.CustListBtwenDate;

public interface CustListBtwnDateRepo extends JpaRepository<CustListBtwenDate, Integer> {

	@Query(value="SELECT\n" + 
			"	\n" + 
			"     DISTINCT cust.cust_id, \n" + 
			"    cust.cust_name,\n" + 
			"    cust.phone_number\n" + 
			"FROM\n" + 
			"     t_sell_bill_header head,\n" + 
			"     m_customer cust\n" + 
			"WHERE\n" + 
			"    cust.cust_id=head.cust_id AND\n" + 
			"    head.bill_date BETWEEN :fromDate AND :toDate AND\n" + 
			"    cust.del_status=0 AND\n" + 
			"    head.del_status=0 AND\n" + 
			"    head.fr_id=:frId", nativeQuery=true)
	public List<CustListBtwenDate> getCustomersBtenDates(@Param("fromDate") String fromDate ,@Param("toDate") String toDate, 
			@Param("frId") int frId);
}
