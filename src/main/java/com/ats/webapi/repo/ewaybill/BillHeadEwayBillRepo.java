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
			"CASE WHEN m_franchisee.is_same_state=0 THEN t_bill_header.igst_sum ELSE 0 END as igst_sum,"
			+ ""
			+ "          t_bill_header.party_name,t_bill_header.party_gstin,t_bill_header.party_address, " + 
			"            t_bill_header.ex_varchar3,t_bill_header.ex_varchar4,t_bill_header.ex_varchar5, " + 
			" t_bill_header.is_tally_sync as ewb_no " +
			"FROM t_bill_header,m_franchisee WHERE t_bill_header.fr_id=m_franchisee.fr_id and t_bill_header.bill_no IN(:billIdList)",nativeQuery=true)
	public List<BillHeadEwayBill>  getBillHeaderForEwayBill(@Param("billIdList") List<String> billIdList);

	 
	@Query(value="	SELECT t_credit_note_header.crn_id as bill_no,t_credit_note_header.crn_taxable_amt as taxable_amt ,t_credit_note_header.crn_grand_total as grand_total,\n" + 
			"	t_credit_note_header.crn_no as invoice_no,\n" + 
			"	t_credit_note_header.crn_date as bill_date,\n" + 
			"\n" + 
			"	CASE WHEN m_franchisee.is_same_state=1 THEN t_credit_note_header.crn_total_tax/2 ELSE 0 END as sgst_sum,\n" + 
			"	CASE WHEN m_franchisee.is_same_state=1 THEN t_credit_note_header.crn_total_tax/2 ELSE 0 END as cgst_sum,\n" + 
			"	CASE WHEN m_franchisee.is_same_state=0 THEN t_credit_note_header.crn_total_tax ELSE 0 END as igst_sum,\n" + 
			"\n" + 
			"	m_franchisee.fr_code,m_franchisee.fr_id\n" + 
			"	FROM m_franchisee,t_credit_note_header\n" + 
			"	WHERE t_credit_note_header.fr_id=m_franchisee.fr_id AND t_credit_note_header.crn_id IN (:crnIdList)",nativeQuery=true)
	public List<BillHeadEwayBill>  getCreditNoteHeaderForEwayBill(@Param("crnIdList") List<String> crnIdList);
	
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
