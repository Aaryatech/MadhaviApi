package com.ats.webapi.repo.ewaybill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.ewaybill.BillHeadEwayBill;

public interface BillHeadEwayBillRepo extends JpaRepository<BillHeadEwayBill, Integer>{
	
	
	@Query(value="SELECT t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date, t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt, t_bill_header.grand_total, \n" + 
			"CASE WHEN m_franchisee.is_same_state=1 THEN t_bill_header.sgst_sum ELSE 0 END as sgst_sum,\n" + 
			"CASE WHEN m_franchisee.is_same_state=1 THEN t_bill_header.cgst_sum ELSE 0 END as cgst_sum,\n" + 
			"CASE WHEN m_franchisee.is_same_state=0 THEN t_bill_header.igst_sum ELSE 0 END as igst_sum\n" + 
			"FROM t_bill_header,m_franchisee WHERE t_bill_header.fr_id=m_franchisee.fr_id and t_bill_header.bill_no IN(:billIdList)",nativeQuery=true)
	public List<BillHeadEwayBill>  getBillHeaderForEwayBill(@Param("billIdList") List<String> billIdList);

	
	
	/*
	 * @Query(value=" SELECT " +
	 * "	  t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date, "
	 * + "	 t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt, "
	 * + "	  t_bill_header.grand_total, " +
	 * "	  t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum FROM"
	 * + "	  t_bill_header WHERE t_bill_header.bill_no IN(:billIdList)",nativeQuery
	 * =true) public List<BillHeadEwayBill>
	 * getBillHeaderForEwayBill(@Param("billIdList") List<String> billIdList);
	 */
	/*
	 SELECT t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date, t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt, t_bill_header.grand_total, 

CASE WHEN m_franchisee.is_same_state=1 THEN t_bill_header.sgst_sum ELSE 0 END as sgst_sum,
CASE WHEN m_franchisee.is_same_state=1 THEN t_bill_header.cgst_sum ELSE 0 END as cgst_sum,
CASE WHEN m_franchisee.is_same_state=0 THEN t_bill_header.igst_sum ELSE 0 END as igst_sum


FROM t_bill_header,m_franchisee WHERE t_bill_header.fr_id=m_franchisee.fr_id
	 */
}
