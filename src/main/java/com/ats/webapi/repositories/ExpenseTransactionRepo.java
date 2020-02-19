package com.ats.webapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.bill.ExpenseTransaction;

public interface ExpenseTransactionRepo extends JpaRepository<ExpenseTransaction, Integer>{
	
	
	@Transactional
	@Modifying
	@Query(" UPDATE ExpenseTransaction SET bill_close=:flag, ex_var1=:billIds  WHERE exp_id =:expId")
 	int updateExpenseBillSettle(@Param("expId") int expId,@Param("billIds") String billIds,@Param("flag") int flag);
 

}
