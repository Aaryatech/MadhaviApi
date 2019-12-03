package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.SellBillHeader;
 
@Repository
public interface SellBillHeaderRepository extends JpaRepository<SellBillHeader, Long>{
	
	@SuppressWarnings("unchecked")
	public SellBillHeader save (SellBillHeader sellBillHeaderList);
	
	
	@Query(value="select * from t_sell_bill_header where  t_sell_bill_header.cust_id=:custId AND t_sell_bill_header.status=3 AND t_sell_bill_header.del_status=0 AND t_sell_bill_header.fr_id=:frId",nativeQuery=true)
	List<SellBillHeader> getSellBillHeader(@Param("custId") int custId,@Param("frId") int frId);

	
	@Transactional
	@Modifying
	@Query(" UPDATE SellBillHeader SET remaining_amt=:pendingAmt,paid_amt=:paidAmt,status=:flag  WHERE sell_bill_no =:sellBillNo")
 	int upDateBillAmt(@Param("pendingAmt") String pendingAmt,@Param("paidAmt")  String paidAmt,@Param("sellBillNo") int sellBillNo,@Param("flag") int flag);
}