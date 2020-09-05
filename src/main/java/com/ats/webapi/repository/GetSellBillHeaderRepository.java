package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetSellBillHeader;

public interface GetSellBillHeaderRepository extends JpaRepository<GetSellBillHeader,Integer>{

	@Query(value =  "  SELECT\r\n" + 
			"        UUID() as id,\r\n" + 
			"        t_sell_bill_header.sell_bill_no,\r\n" + 
			"        t_sell_bill_header.bill_type,\r\n" + 
			"        t_sell_bill_header.invoice_no,\r\n" + 
			"        t_sell_bill_header.bill_date,\r\n" + 
			"        t_sell_bill_header.taxable_amt,\r\n" + 
			"        t_sell_bill_header.total_tax,\r\n" + 
			"        t_sell_bill_header.grand_total,\r\n" + 
			"        (t_transaction_detail.cash_amt+t_transaction_detail.card_amt+t_transaction_detail.e_pay_amt) paid_amt,\r\n" + 
			"        t_sell_bill_header.remaining_amt,\r\n" + 
			"       concat(t_transaction_detail.cash_amt,'-cash ,',t_transaction_detail.card_amt,'-card ,',t_transaction_detail.e_pay_amt,' -E-pay')  as payment_mode,\r\n" + 
			"        t_sell_bill_header.discount_per,\r\n" + 
			"        t_sell_bill_header.payable_amt,\r\n" + 
			"        m_franchisee.fr_name \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,t_transaction_detail,\r\n" + 
			"        m_franchisee \r\n" + 
			"    WHERE\r\n" + 
			"        m_franchisee.fr_id=t_sell_bill_header.fr_id and t_sell_bill_header.del_status=0 \r\n" + 
			"        AND t_sell_bill_header.fr_id IN(\r\n" + 
			"           :frId\r\n" + 
			"        ) and t_transaction_detail.sell_bill_no=t_sell_bill_header.sell_bill_no \r\n" + 
			"        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
	
	List<GetSellBillHeader> getFrSellBillHeader(@Param("fromDate") String fromDate ,@Param("toDate") String toDate ,@Param("frId") List<String> frId);
	
	
	
	
}
