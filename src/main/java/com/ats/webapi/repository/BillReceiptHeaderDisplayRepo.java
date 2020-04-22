package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.bill.BillReceiptHeaderDisplay;

public interface BillReceiptHeaderDisplayRepo extends JpaRepository<BillReceiptHeaderDisplay, Integer> {

	@Query(value = "SELECT h.bill_receipt_id,h.receipt_no,DATE_FORMAT(h.receipt_date, '%d-%m-%Y') as receipt_date,h.fr_id,CONCAT(f.fr_name,'-',f.fr_code) as fr_name, h.exp_id,e.remark,h.exp_amt,e.exp_date,e.chalan_no FROM t_bill_receipt_header h, m_expense e,m_franchisee f WHERE h.exp_id=e.exp_id AND h.fr_id=e.fr_id AND e.fr_id=f.fr_id AND h.receipt_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frId)", nativeQuery = true)
	public List<BillReceiptHeaderDisplay> getBillReceiptHeaders(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("frId") List<String> frId);

}
