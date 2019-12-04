package com.ats.webapi.repo.posdashboard;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.posdashboard.SellBillHeaderDashCounts;
 
public interface SellBillHeaderDashCountsRepo extends JpaRepository<SellBillHeaderDashCounts,String> {
	
	@Query(value = "SELECT\n" + 
			"    UUID() AS uid, SUM(t_sell_bill_header.grand_total) AS sell_amt,\n" + 
			"    SUM(\n" + 
			"        t_sell_bill_header.disc_amt_item + t_sell_bill_header.discount_amt\n" + 
			"    ) AS disc_amt,\n" + 
			"    COUNT(\n" + 
			"        t_sell_bill_header.sell_bill_no\n" + 
			"    ) AS no_bill_gen,\n" + 
			"    SUM(t_sell_bill_header.advance_amt) AS advance_amt,\n" + 
			"    SUM(t_sell_bill_header.grand_total) AS credit_amt,\n" + 
			"    SUM(0) AS profit_amt\n" + 
			"FROM\n" + 
			"    t_sell_bill_header\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.del_status = 0 AND t_sell_bill_header.fr_id =:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate ", nativeQuery = true)
	SellBillHeaderDashCounts getDataFordash(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("frId") int  frId);
	
	
	

}
