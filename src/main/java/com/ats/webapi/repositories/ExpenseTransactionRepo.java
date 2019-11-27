package com.ats.webapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.bill.ExpenseTransaction;

public interface ExpenseTransactionRepo extends JpaRepository<ExpenseTransaction, Integer>{
	
	
	

}
