package com.ats.webapi.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.TransactionDetail;
@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Serializable>{
	
	@Query(value="SELECT\n" + 
			"    t_transaction_detail.tr_id,\n" + 
			"    t_transaction_detail.sell_bill_no,\n" + 
			"    t_transaction_detail.transaction_date,\n" + 
			"    t_transaction_detail.pay_mode,\n" + 
			"    t_transaction_detail.cash_amt,\n" + 
			"    t_transaction_detail.card_amt,\n" + 
			"    t_transaction_detail.e_pay_amt,\n" + 
			"    t_transaction_detail.e_pay_type,t_transaction_detail.disc_type,t_transaction_detail.del_status,t_transaction_detail.ex_int1,t_transaction_detail.ex_int2,t_sell_bill_header.invoice_no as ex_var1,\n" + 
			"    t_sell_bill_header.bill_date as ex_var2,t_transaction_detail.ex_float1,t_transaction_detail.ex_float2\n" + 
			"FROM\n" + 
			"    t_transaction_detail,\n" + 
			"    t_sell_bill_header\n" + 
			"WHERE\n" + 
			"    t_transaction_detail.del_status = 0 AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.cust_id =:custId AND t_sell_bill_header.fr_id =:frId\n" + 
			"ORDER BY\n" + 
			"    t_sell_bill_header.sell_bill_no\n" + 
			"DESC\n" + 
			"LIMIT 50",nativeQuery=true)
	List<TransactionDetail> getCustBillsTransaction(@Param("custId") int custId,@Param("frId") int frId);

	

}
