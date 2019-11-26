package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.bill.BillTransaction;

public interface BillTransactionRepo extends JpaRepository<BillTransaction, Integer>{

	List<BillTransaction> findByFrIdInAndDelStatus(List<String> frIdList, int i);
	
	List<BillTransaction> findByDelStatus( int i);
	
	

	
	@Transactional
	@Modifying
	@Query(" UPDATE BillTransaction SET is_closed=1 WHERE bill_trans_id =:tranId")
	int closeBill(@Param("tranId")int tranId);
 
}
