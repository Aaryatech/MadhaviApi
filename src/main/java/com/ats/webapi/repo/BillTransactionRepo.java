package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.bill.BillTransaction;
 
public interface BillTransactionRepo extends JpaRepository<BillTransaction, Integer>{

	
	
	List<BillTransaction> findByDelStatusAndIsClosed( int i,int j);
	
	
	@Query(value="SELECT * FROM t_bill_transcation where t_bill_transcation.fr_id IN(:frIdList)  AND t_bill_transcation.del_status=0 AND t_bill_transcation.is_closed=:temp ",nativeQuery=true)
	public List<BillTransaction> getAllTrancaction (@Param("frIdList") List<String> frIdList,@Param("temp") int temp);
	
	@Transactional
	@Modifying
	@Query(" UPDATE BillTransaction SET is_closed=1 WHERE bill_trans_id =:tranId")
	int closeBill(@Param("tranId")int tranId);
	
	
	
	@Transactional
	@Modifying
	@Query(" UPDATE BillTransaction SET pending_amt=:pendingAmt,paid_amt=:paidAmt,is_closed=:flag  WHERE bill_head_id =:billHeadId")
 	int upDateBillAmt(@Param("pendingAmt") String pendingAmt,@Param("paidAmt")  String paidAmt,@Param("billHeadId") int billHeadId,@Param("flag") int flag);
 
}
