package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.CustomerSaleReport;

public interface CustomerSaleReportRepo extends JpaRepository<CustomerSaleReport,Integer> {

	@Query(value="SELECT c.*,SUM(h.payable_amt) as payable_amt,SUM(h.discount_amt) AS disc_amt,SUM(h.ext_float3) AS wallet,"
			+ "SUM(h.ext_float2) AS extra_ch FROM m_customer c,t_sell_bill_header h WHERE c.del_status=0 AND "
			+ "c.added_from_type IN(:type) AND h.cust_id=c.cust_id AND h.del_status=0 AND "
			+ "h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frId) GROUP BY h.cust_id ORDER BY payable_amt desc",nativeQuery=true)
	List<CustomerSaleReport> getCustByAddedTypeAndFr(@Param("type") List<Integer> type,@Param("frId") List<Integer> frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
}
