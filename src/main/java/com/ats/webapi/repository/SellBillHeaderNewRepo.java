package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.SellBillHeaderNew;

public interface SellBillHeaderNewRepo extends JpaRepository<SellBillHeaderNew,Integer>{

	@Query(value =  "  SELECT\r\n" + 
			"    UUID() AS id, t_sell_bill_header.sell_bill_no, t_sell_bill_header.bill_type,t_sell_bill_header.discount_amt, t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date, t_sell_bill_header.taxable_amt, t_sell_bill_header.total_tax, t_sell_bill_header.grand_total,(\r\n" + 
			"        t_transaction_detail.cash_amt + t_transaction_detail.card_amt + t_transaction_detail.e_pay_amt\r\n" + 
			"    ) paid_amt,\r\n" + 
			"    t_sell_bill_header.remaining_amt,\r\n" + 
			"    CONCAT(\r\n" + 
			"        t_transaction_detail.cash_amt,\r\n" + 
			"        '-cash ,',\r\n" + 
			"        t_transaction_detail.card_amt,\r\n" + 
			"        '-card ,',\r\n" + 
			"        t_transaction_detail.e_pay_amt,\r\n" + 
			"        ' -E-pay'\r\n" + 
			"    ) AS payment_mode,\r\n" + 
			"    t_sell_bill_header.discount_per,\r\n" + 
			"    t_sell_bill_header.payable_amt,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    m_customer.cust_id,\r\n" + 
			"    m_customer.cust_name,\r\n" + 
			"    m_customer.phone_number,\r\n" + 
			"    m_customer.gst_no,\r\n" + 
			"    m_customer.address,\r\n" + 
			"    t_transaction_detail.cash_amt as cash,\r\n" + 
			"    t_transaction_detail.card_amt as card,\r\n" + 
			"    t_transaction_detail.e_pay_amt as e_pay\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    t_transaction_detail,\r\n" + 
			"    m_franchisee,\r\n" + 
			"    m_customer\r\n" + 
			"WHERE\r\n" + 
			"    m_franchisee.fr_id = t_sell_bill_header.fr_id AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.fr_id IN(:frId) AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    AND m_customer.cust_id=t_sell_bill_header.cust_id AND t_sell_bill_header.cust_id IN(:custId)  ORDER BY t_sell_bill_header.sell_bill_no DESC", nativeQuery = true)
	
	List<SellBillHeaderNew> getFrSellBillHeader(@Param("fromDate") String fromDate ,@Param("toDate") String toDate ,@Param("frId") List<String> frId,@Param("custId") List<String> custId);
	
	
	@Query(value =  "  SELECT\r\n" + 
			"    UUID() AS id, t_sell_bill_header.sell_bill_no, t_sell_bill_header.bill_type, t_sell_bill_header.discount_amt, t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date, t_sell_bill_header.taxable_amt, t_sell_bill_header.total_tax, t_sell_bill_header.grand_total,(\r\n" + 
			"        t_transaction_detail.cash_amt + t_transaction_detail.card_amt + t_transaction_detail.e_pay_amt\r\n" + 
			"    ) paid_amt,\r\n" + 
			"    t_sell_bill_header.remaining_amt,\r\n" + 
			"    CONCAT(\r\n" + 
			"        t_transaction_detail.cash_amt,\r\n" + 
			"        '-cash ,',\r\n" + 
			"        t_transaction_detail.card_amt,\r\n" + 
			"        '-card ,',\r\n" + 
			"        t_transaction_detail.e_pay_amt,\r\n" + 
			"        ' -E-pay'\r\n" + 
			"    ) AS payment_mode,\r\n" + 
			"    t_sell_bill_header.discount_per,\r\n" + 
			"    t_sell_bill_header.payable_amt,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    m_customer.cust_id,\r\n" + 
			"    m_customer.cust_name,\r\n" + 
			"    m_customer.phone_number,\r\n" + 
			"    m_customer.gst_no,\r\n" + 
			"    m_customer.address,\r\n" + 
			"    t_transaction_detail.cash_amt as cash,\r\n" + 
			"    t_transaction_detail.card_amt as card,\r\n" + 
			"    t_transaction_detail.e_pay_amt as e_pay\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    t_transaction_detail,\r\n" + 
			"    m_franchisee,\r\n" + 
			"    m_customer\r\n" + 
			"WHERE\r\n" + 
			"    m_franchisee.fr_id = t_sell_bill_header.fr_id AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.fr_id IN(:frId) AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    AND m_customer.cust_id=t_sell_bill_header.cust_id ORDER BY t_sell_bill_header.sell_bill_no DESC", nativeQuery = true)
	
	List<SellBillHeaderNew> getFrSellBillHeaderAllCust(@Param("fromDate") String fromDate ,@Param("toDate") String toDate ,@Param("frId") List<String> frId);


	@Query(value="SELECT\r\n" + 
			"        UUID() AS id,\r\n" + 
			"        t_sell_bill_header.sell_bill_no,\r\n" + 
			"        t_sell_bill_header.bill_type,\r\n" + 
			"        t_sell_bill_header.discount_amt,\r\n" + 
			"        t_sell_bill_header.invoice_no,\r\n" + 
			"        t_sell_bill_header.bill_date,\r\n" + 
			"        t_sell_bill_header.taxable_amt,\r\n" + 
			"        t_sell_bill_header.total_tax,\r\n" + 
			"        t_sell_bill_header.grand_total,\r\n" + 
			"        (          t_transaction_detail.cash_amt + t_transaction_detail.card_amt + t_transaction_detail.e_pay_amt      ) paid_amt,\r\n" + 
			"        SUM(t_sell_bill_header.remaining_amt) AS remaining_amt,\r\n" + 
			"        CONCAT(          t_transaction_detail.cash_amt,\r\n" + 
			"        '-cash ,',\r\n" + 
			"        t_transaction_detail.card_amt,\r\n" + 
			"        '-card ,',\r\n" + 
			"        t_transaction_detail.e_pay_amt,\r\n" + 
			"        ' -E-pay'      ) AS payment_mode,\r\n" + 
			"        t_sell_bill_header.discount_per,\r\n" + 
			"        t_sell_bill_header.payable_amt,\r\n" + 
			"        m_franchisee.fr_name,\r\n" + 
			"        m_customer.cust_id,\r\n" + 
			"        m_customer.cust_name,\r\n" + 
			"        m_customer.phone_number,\r\n" + 
			"        m_customer.gst_no,\r\n" + 
			"        m_customer.address,\r\n" + 
			"        t_transaction_detail.cash_amt as cash,\r\n" + 
			"        t_transaction_detail.card_amt as card,\r\n" + 
			"        t_transaction_detail.e_pay_amt as e_pay  \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,\r\n" + 
			"        t_transaction_detail,\r\n" + 
			"        m_franchisee,\r\n" + 
			"        m_customer  \r\n" + 
			"    WHERE\r\n" + 
			"        m_franchisee.fr_id = t_sell_bill_header.fr_id  AND\r\n" + 
			"        t_sell_bill_header.remaining_amt>0 AND\r\n" + 
			"        t_sell_bill_header.del_status = 0 \r\n" + 
			"        AND t_sell_bill_header.fr_id IN(:frId) \r\n" + 
			"        AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no \r\n" + 
			"        AND m_customer.cust_id=t_sell_bill_header.cust_id \r\n" + 
			"    GROUP BY m_customer.cust_id\r\n" + 
			"    ORDER BY\r\n" + 
			"        t_sell_bill_header.sell_bill_no DESC",nativeQuery=true)
	List<SellBillHeaderNew> getRemainingAmtAllCust(@Param("frId") List<String> frId);

	
	@Query(value="SELECT\r\n" + 
			"        UUID() AS id,\r\n" + 
			"        t_sell_bill_header.sell_bill_no,\r\n" + 
			"        t_sell_bill_header.bill_type,\r\n" + 
			"        t_sell_bill_header.discount_amt,\r\n" + 
			"        t_sell_bill_header.invoice_no,\r\n" + 
			"        t_sell_bill_header.bill_date,\r\n" + 
			"        t_sell_bill_header.taxable_amt,\r\n" + 
			"        t_sell_bill_header.total_tax,\r\n" + 
			"        t_sell_bill_header.grand_total,\r\n" + 
			"        (          t_transaction_detail.cash_amt + t_transaction_detail.card_amt + t_transaction_detail.e_pay_amt      ) paid_amt,\r\n" + 
			"        t_sell_bill_header.remaining_amt,\r\n" + 
			"        CONCAT(          t_transaction_detail.cash_amt,\r\n" + 
			"        '-cash ,',\r\n" + 
			"        t_transaction_detail.card_amt,\r\n" + 
			"        '-card ,',\r\n" + 
			"        t_transaction_detail.e_pay_amt,\r\n" + 
			"        ' -E-pay'      ) AS payment_mode,\r\n" + 
			"        t_sell_bill_header.discount_per,\r\n" + 
			"        t_sell_bill_header.payable_amt,\r\n" + 
			"        m_franchisee.fr_name,\r\n" + 
			"        m_customer.cust_id,\r\n" + 
			"        m_customer.cust_name,\r\n" + 
			"        m_customer.phone_number,\r\n" + 
			"        m_customer.gst_no,\r\n" + 
			"        m_customer.address,\r\n" + 
			"        t_transaction_detail.cash_amt as cash,\r\n" + 
			"        t_transaction_detail.card_amt as card,\r\n" + 
			"        t_transaction_detail.e_pay_amt as e_pay  \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,\r\n" + 
			"        t_transaction_detail,\r\n" + 
			"        m_franchisee,\r\n" + 
			"        m_customer  \r\n" + 
			"    WHERE\r\n" + 
			"	    t_sell_bill_header.remaining_amt>0 AND\r\n" + 
			"        m_franchisee.fr_id = t_sell_bill_header.fr_id \r\n" + 
			"        AND t_sell_bill_header.del_status = 0 \r\n" + 
			"        AND t_sell_bill_header.fr_id IN(:frId) \r\n" + 
			"        AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no \r\n" + 
			"        AND m_customer.cust_id=t_sell_bill_header.cust_id \r\n" + 
			"        AND t_sell_bill_header.cust_id=:custId \r\n" + 
			"    ORDER BY\r\n" + 
			"        t_sell_bill_header.sell_bill_no DESC",nativeQuery=true)
	List<SellBillHeaderNew> getRemainingAmtByCustId(@Param("custId") int custId, @Param("frId") List<String> frId);
	

}
