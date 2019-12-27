package com.ats.webapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetTotalAmt;

public interface GetTotalAmtRepo extends JpaRepository<GetTotalAmt, Integer> {

	@Query(value = "SELECT UUID() as id, COALESCE((SUM(advance_amt)),0) as total_amt FROM t_adv_order_header WHERE fr_id=:frId AND order_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
	public GetTotalAmt getTotalAmount(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);
	
	
	@Query(value = "SELECT UUID() as id, COALESCE((SUM(d.grand_total-(d.mrp_base_rate*d.qty))),0) as total_amt FROM t_sell_bill_header h,t_sell_bill_detail d WHERE h.sell_bill_no=d.sell_bill_no AND h.fr_id=:frId AND h.bill_date BETWEEN :fromDate AND :toDate ", nativeQuery = true)
	public GetTotalAmt getTotalProfit(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);
	

}
