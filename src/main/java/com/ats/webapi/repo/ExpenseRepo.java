package com.ats.webapi.repo;

import java.util.List;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.bill.Expense;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

	
	@Query(value = "SELECT * from m_expense where   m_expense.del_status=0  AND m_expense.fr_id=:frId AND m_expense.exp_type=:type AND m_expense.exp_date BETWEEN :fromDate AND :toDate  ", nativeQuery = true)
 	List<Expense> getExpenseList(@Param("frId") int frId,@Param("type") int type,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
 	
	@Query(value = "SELECT * from m_expense where  m_expense.del_status=0   AND m_expense.exp_date BETWEEN :fromDate AND :toDate AND exp_type=:type", nativeQuery = true)
	List<Expense> getAllExpenseList(@Param("type") int type,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	@Query(value = "SELECT * from m_expense where  m_expense.del_status=0 ", nativeQuery = true)
	List<Expense> getAllExpenseList();
	
	
	
	
	@Query(value = "SELECT * from m_expense where   m_expense.del_status=0  AND m_expense.fr_id=:frId", nativeQuery = true)
 	List<Expense> getExpenseList(@Param("frId") int frId);
	
	
	

	@Transactional
	@Modifying
	@Query(" UPDATE Expense SET del_status=1 WHERE exp_id=:suppId")
	int deleteExpense(@Param("suppId")int suppId);

	 Expense findByExpId(int expId);

}
