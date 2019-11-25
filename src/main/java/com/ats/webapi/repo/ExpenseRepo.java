package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
 import com.ats.webapi.model.bill.Expense;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

	
	@Query(value = "SELECT * from m_expense where   m_expense.del_status=:0  AND m_expense.fr_id=:frId AND m_expense.exp_type=:type AND m_expense.exp_date BETWEEN :fromDate AND :toDate ", nativeQuery = true)
 	List<Expense> getExpenseList(int frId, int type, String fromDate, String toDate);
 	
	@Query(value = "SELECT * from m_expense where  m_expense.del_status=:0  AND m_expense.exp_type=:type AND m_expense.exp_date BETWEEN :fromDate AND :toDate ", nativeQuery = true)
	List<Expense> getAllExpenseList(int type, String fromDate, String toDate);
	

}
