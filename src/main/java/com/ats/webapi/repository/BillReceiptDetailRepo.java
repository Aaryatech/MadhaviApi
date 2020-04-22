package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.AllFrIdName;
import com.ats.webapi.model.bill.BillReceiptDetail;

public interface BillReceiptDetailRepo extends JpaRepository<BillReceiptDetail, Integer>{
	
	List<BillReceiptDetail> findAllByBillReceiptId(int i);
	

	@Query(value=" SELECT d.bill_receipt_detail_id, d.bill_receipt_id, d.bill_no, d.invoice_no, d.bill_amt, d.paid_amt, d.ex_int1, d.ex_int2, h.receipt_no as ex_var1, DATE_FORMAT(h.receipt_date, '%d-%m-%Y') as ex_var2, d.ex_float1, d.ex_float2 FROM t_bill_receipt_header h, t_bill_receipt_detail d WHERE h.exp_id = :expId AND h.bill_receipt_id = d.bill_receipt_id",nativeQuery=true)
	List<BillReceiptDetail> getBillReceiptDetailByExpId(@Param("expId")int expId);
	
}
