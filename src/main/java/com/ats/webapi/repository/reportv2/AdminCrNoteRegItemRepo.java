package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.AdminCrNoteRegItem;
import com.ats.webapi.model.reportv2.CrNoteRegItem;

public interface AdminCrNoteRegItemRepo extends JpaRepository<AdminCrNoteRegItem, Integer> {

	
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_header.crn_id,\r\n" + 
			"    t_credit_note_header.crn_date,\r\n" + 
			"    t_bill_header.invoice_no,\r\n" + 
			"    t_credit_note_details.crnd_id,\r\n" + 
			"    t_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    t_credit_note_header.crn_no AS fr_code,\r\n" + 
			"    m_franchisee.fr_gst_no,\r\n" + 
			"    t_credit_note_details.hsn_code,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_qty\r\n" + 
			"    ) crn_qty,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.taxable_amt\r\n" + 
			"    ) crn_taxable,\r\n" + 
			"    t_credit_note_details.cgst_per,\r\n" + 
			"    t_credit_note_details.sgst_per,\r\n" + 
			"    t_credit_note_details.igst_per,\r\n" + 
			"    SUM(t_credit_note_details.sgst_rs) AS sgst_amt,\r\n" + 
			"    SUM(t_credit_note_details.cgst_rs) AS cgst_amt,\r\n" + 
			"    SUM(t_credit_note_details.igst_rs) AS igst_amt,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_amt\r\n" + 
			"    ) AS crn_amt,\r\n" + 
			"    t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
			"    t_bill_header.ex_varchar4 AS bill_to_gst\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_header,\r\n" + 
			"    t_credit_note_details,\r\n" + 
			"    t_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id = m_franchisee.fr_id AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.ex_varchar2=0 AND t_bill_header.ex_varchar4 != '' AND t_credit_note_header.ex_int2!=1 \r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_details.crn_id,\r\n" + 
			"    t_credit_note_details.cgst_per + t_credit_note_details.sgst_per\r\n" + 
			"ORDER BY\r\n" + 
			"    t_credit_note_header.crn_no", nativeQuery = true)

	List<AdminCrNoteRegItem> getCrNoteRegItemDoneB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_header.crn_id,\r\n" + 
			"    t_credit_note_header.crn_date,\r\n" + 
			"    t_bill_header.invoice_no,\r\n" + 
			"    t_credit_note_details.crnd_id,\r\n" + 
			"    t_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    t_credit_note_header.crn_no AS fr_code,\r\n" + 
			"    m_franchisee.fr_gst_no,\r\n" + 
			"    t_credit_note_details.hsn_code,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_qty\r\n" + 
			"    ) crn_qty,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.taxable_amt\r\n" + 
			"    ) crn_taxable,\r\n" + 
			"    t_credit_note_details.cgst_per,\r\n" + 
			"    t_credit_note_details.sgst_per,\r\n" + 
			"    t_credit_note_details.igst_per,\r\n" + 
			"    SUM(t_credit_note_details.sgst_rs) AS sgst_amt,\r\n" + 
			"    SUM(t_credit_note_details.cgst_rs) AS cgst_amt,\r\n" + 
			"    SUM(t_credit_note_details.igst_rs) AS igst_amt,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_amt\r\n" + 
			"    ) AS crn_amt,\r\n" + 
			"    t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
			"    t_bill_header.ex_varchar4 AS bill_to_gst\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_header,\r\n" + 
			"    t_credit_note_details,\r\n" + 
			"    t_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id = m_franchisee.fr_id AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.ex_varchar2=0 AND t_bill_header.ex_varchar4 = '' AND t_credit_note_header.ex_int2!=1 \r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_details.cgst_per + t_credit_note_details.sgst_per\r\n" + 
			"ORDER BY\r\n" + 
			"    t_credit_note_details.cgst_per + t_credit_note_details.sgst_per", nativeQuery = true)

	List<AdminCrNoteRegItem> getCrNoteRegItemDoneB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crn_id,\r\n" + 
			"    t_credit_note_pos.crn_date,\r\n" + 
			"    t_sell_bill_header.invoice_no,\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crnd_id,\r\n" + 
			"    t_sell_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    t_credit_note_pos.crn_no AS fr_code,\r\n" + 
			"    m_franchisee.fr_gst_no,\r\n" + 
			"    0 AS hsn_code,\r\n" + 
			"    SUM(t_credit_note_pos.crn_qty) crn_qty,\r\n" + 
			"    SUM(t_credit_note_pos.taxable_amt) crn_taxable,\r\n" + 
			"    t_credit_note_pos.cgst_per,\r\n" + 
			"    t_credit_note_pos.sgst_per,\r\n" + 
			"    t_credit_note_pos.igst_per,\r\n" + 
			"    SUM(t_credit_note_pos.sgst_amt) AS sgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.cgst_per) AS cgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.igst_amt) AS igst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.grand_total) AS crn_amt,\r\n" + 
			"    t_sell_bill_header.user_name AS bill_to_name,\r\n" + 
			"    t_sell_bill_header.user_gst_no AS bill_to_gst\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos,\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.ex_int1 = m_franchisee.fr_id AND t_sell_bill_header.sell_bill_no = t_credit_note_pos.bill_no AND t_sell_bill_header.user_gst_no != ''\r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_pos.crn_detail_no,\r\n" + 
			"    t_credit_note_pos.cgst_per + t_credit_note_pos.sgst_per\r\n" + 
			"ORDER BY\r\n" + 
			"    t_credit_note_pos.crn_no", nativeQuery = true)

	List<AdminCrNoteRegItem> getCompCrNoteB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crn_id,\r\n" + 
			"    t_credit_note_pos.crn_date,\r\n" + 
			"    t_sell_bill_header.invoice_no,\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crnd_id,\r\n" + 
			"    t_sell_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    t_credit_note_pos.crn_no AS fr_code,\r\n" + 
			"    m_franchisee.fr_gst_no,\r\n" + 
			"    0 AS hsn_code,\r\n" + 
			"    SUM(t_credit_note_pos.crn_qty) crn_qty,\r\n" + 
			"    SUM(t_credit_note_pos.taxable_amt) crn_taxable,\r\n" + 
			"    t_credit_note_pos.cgst_per,\r\n" + 
			"    t_credit_note_pos.sgst_per,\r\n" + 
			"    t_credit_note_pos.igst_per,\r\n" + 
			"    SUM(t_credit_note_pos.sgst_amt) AS sgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.cgst_per) AS cgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.igst_amt) AS igst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.grand_total) AS crn_amt,\r\n" + 
			"    t_sell_bill_header.user_name AS bill_to_name,\r\n" + 
			"    t_sell_bill_header.user_gst_no AS bill_to_gst\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos,\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.ex_int1 = m_franchisee.fr_id AND t_sell_bill_header.sell_bill_no = t_credit_note_pos.bill_no AND t_sell_bill_header.user_gst_no = ''\r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_pos.ex_int1,\r\n" + 
			"    t_credit_note_pos.cgst_per + t_credit_note_pos.sgst_per\r\n" + 
			"ORDER BY\r\n" + 
			"    t_credit_note_pos.ex_int1", nativeQuery = true)

	List<AdminCrNoteRegItem> getCompCrNoteB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	//Anmol-- 10-03-2020---
	@Query(value = "SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_credit_note_header.crn_id,\r\n" + 
			"        t_credit_note_header.crn_date,\r\n" + 
			"        t_bill_header.invoice_no,\r\n" + 
			"        t_credit_note_details.crnd_id,\r\n" + 
			"        t_bill_header.bill_date,\r\n" + 
			"        m_franchisee.fr_name,\r\n" + 
			"        t_credit_note_header.crn_no AS fr_code,\r\n" + 
			"        m_franchisee.fr_gst_no,\r\n" + 
			"        t_credit_note_details.hsn_code,\r\n" + 
			"        SUM(\r\n" + 
			"            t_credit_note_details.grn_gvn_qty\r\n" + 
			"        ) crn_qty,\r\n" + 
			"        SUM(\r\n" + 
			"            t_credit_note_details.taxable_amt\r\n" + 
			"        ) crn_taxable,\r\n" + 
			"        t_credit_note_details.cgst_per,\r\n" + 
			"        t_credit_note_details.sgst_per,\r\n" + 
			"        t_credit_note_details.igst_per,\r\n" + 
			"        SUM(t_credit_note_details.sgst_rs) AS sgst_amt,\r\n" + 
			"        SUM(t_credit_note_details.cgst_rs) AS cgst_amt,\r\n" + 
			"        SUM(t_credit_note_details.igst_rs) AS igst_amt,\r\n" + 
			"        SUM(\r\n" + 
			"            t_credit_note_details.grn_gvn_amt\r\n" + 
			"        ) AS crn_amt,\r\n" + 
			"        t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
			"        t_bill_header.ex_varchar4 AS bill_to_gst\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_header,\r\n" + 
			"        t_credit_note_details,\r\n" + 
			"        t_bill_header,\r\n" + 
			"        m_franchisee\r\n" + 
			"    WHERE\r\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id = m_franchisee.fr_id AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.ex_varchar2 = 0 AND t_bill_header.ex_varchar4 != '' AND t_credit_note_header.ex_int2!=1 \r\n" + 
			"    GROUP BY\r\n" + 
			"        t_credit_note_details.crn_id,\r\n" + 
			"        t_credit_note_details.cgst_per + t_credit_note_details.sgst_per\r\n" + 
			"    UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crn_id,\r\n" + 
			"    t_credit_note_pos.crn_date,\r\n" + 
			"    t_sell_bill_header.invoice_no,\r\n" + 
			"    t_credit_note_pos.crn_detail_no AS crnd_id,\r\n" + 
			"    t_sell_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    t_credit_note_pos.crn_no AS fr_code,\r\n" + 
			"    m_franchisee.fr_gst_no,\r\n" + 
			"    0 AS hsn_code,\r\n" + 
			"    SUM(t_credit_note_pos.crn_qty) crn_qty,\r\n" + 
			"    SUM(t_credit_note_pos.taxable_amt) crn_taxable,\r\n" + 
			"    t_credit_note_pos.cgst_per,\r\n" + 
			"    t_credit_note_pos.sgst_per,\r\n" + 
			"    t_credit_note_pos.igst_per,\r\n" + 
			"    SUM(t_credit_note_pos.sgst_amt) AS sgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.cgst_per) AS cgst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.igst_amt) AS igst_amt,\r\n" + 
			"    SUM(t_credit_note_pos.grand_total) AS crn_amt,\r\n" + 
			"    t_sell_bill_header.user_name AS bill_to_name,\r\n" + 
			"    t_sell_bill_header.user_gst_no AS bill_to_gst\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos,\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.ex_int1 = m_franchisee.fr_id AND t_sell_bill_header.sell_bill_no = t_credit_note_pos.bill_no AND t_sell_bill_header.user_gst_no != '' AND m_franchisee.kg_1 = 1 \r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_pos.crn_detail_no,\r\n" + 
			"    t_credit_note_pos.cgst_per + t_credit_note_pos.sgst_per\r\n" + 
			"\r\n" + 
			") t1\r\n" + 
			"ORDER BY\r\n" + 
			"    t1.fr_code", nativeQuery = true)

	List<AdminCrNoteRegItem> getAllB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	
	//Anmol-- 10-03-2020---
		@Query(value = "SELECT\r\n" + 
				"    *\r\n" + 
				"FROM\r\n" + 
				"    (\r\n" + 
				"    SELECT\r\n" + 
				"        t_credit_note_header.crn_id,\r\n" + 
				"        t_credit_note_header.crn_date,\r\n" + 
				"        t_bill_header.invoice_no,\r\n" + 
				"        t_credit_note_details.crnd_id,\r\n" + 
				"        t_bill_header.bill_date,\r\n" + 
				"        m_franchisee.fr_name,\r\n" + 
				"        t_credit_note_header.crn_no AS fr_code,\r\n" + 
				"        m_franchisee.fr_gst_no,\r\n" + 
				"        t_credit_note_details.hsn_code,\r\n" + 
				"        SUM(\r\n" + 
				"            t_credit_note_details.grn_gvn_qty\r\n" + 
				"        ) crn_qty,\r\n" + 
				"        SUM(\r\n" + 
				"            t_credit_note_details.taxable_amt\r\n" + 
				"        ) crn_taxable,\r\n" + 
				"        t_credit_note_details.cgst_per,\r\n" + 
				"        t_credit_note_details.sgst_per,\r\n" + 
				"        t_credit_note_details.igst_per,\r\n" + 
				"        SUM(t_credit_note_details.sgst_rs) AS sgst_amt,\r\n" + 
				"        SUM(t_credit_note_details.cgst_rs) AS cgst_amt,\r\n" + 
				"        SUM(t_credit_note_details.igst_rs) AS igst_amt,\r\n" + 
				"        SUM(\r\n" + 
				"            t_credit_note_details.grn_gvn_amt\r\n" + 
				"        ) AS crn_amt,\r\n" + 
				"        t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
				"        t_bill_header.ex_varchar4 AS bill_to_gst\r\n" + 
				"    FROM\r\n" + 
				"        t_credit_note_header,\r\n" + 
				"        t_credit_note_details,\r\n" + 
				"        t_bill_header,\r\n" + 
				"        m_franchisee\r\n" + 
				"    WHERE\r\n" + 
				"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id = m_franchisee.fr_id AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.ex_varchar2 = 0 AND t_bill_header.ex_varchar4 = '' AND t_credit_note_header.ex_int2!=1 \r\n" + 
				"    GROUP BY\r\n" + 
				"        t_credit_note_details.cgst_per + t_credit_note_details.sgst_per\r\n" + 
				"    UNION ALL\r\n" + 
				"SELECT\r\n" + 
				"    t_credit_note_pos.crn_detail_no AS crn_id,\r\n" + 
				"    t_credit_note_pos.crn_date,\r\n" + 
				"    t_sell_bill_header.invoice_no,\r\n" + 
				"    t_credit_note_pos.crn_detail_no AS crnd_id,\r\n" + 
				"    t_sell_bill_header.bill_date,\r\n" + 
				"    m_franchisee.fr_name,\r\n" + 
				"    t_credit_note_pos.crn_no AS fr_code,\r\n" + 
				"    m_franchisee.fr_gst_no,\r\n" + 
				"    0 AS hsn_code,\r\n" + 
				"    SUM(t_credit_note_pos.crn_qty) crn_qty,\r\n" + 
				"    SUM(t_credit_note_pos.taxable_amt) crn_taxable,\r\n" + 
				"    t_credit_note_pos.cgst_per,\r\n" + 
				"    t_credit_note_pos.sgst_per,\r\n" + 
				"    t_credit_note_pos.igst_per,\r\n" + 
				"    SUM(t_credit_note_pos.sgst_amt) AS sgst_amt,\r\n" + 
				"    SUM(t_credit_note_pos.cgst_per) AS cgst_amt,\r\n" + 
				"    SUM(t_credit_note_pos.igst_amt) AS igst_amt,\r\n" + 
				"    SUM(t_credit_note_pos.grand_total) AS crn_amt,\r\n" + 
				"    t_sell_bill_header.user_name AS bill_to_name,\r\n" + 
				"    t_sell_bill_header.user_gst_no AS bill_to_gst\r\n" + 
				"FROM\r\n" + 
				"    t_credit_note_pos,\r\n" + 
				"    t_sell_bill_header,\r\n" + 
				"    m_franchisee\r\n" + 
				"WHERE\r\n" + 
				"    t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.ex_int1 = m_franchisee.fr_id AND t_sell_bill_header.sell_bill_no = t_credit_note_pos.bill_no AND t_sell_bill_header.user_gst_no = '' AND m_franchisee.kg_1 = 1\r\n" + 
				"GROUP BY\r\n" + 
				"    t_credit_note_pos.ex_int1,\r\n" + 
				"    t_credit_note_pos.cgst_per + t_credit_note_pos.sgst_per\r\n" + 
				") t1\r\n" + 
				"ORDER BY\r\n" + 
				"    t1.cgst_per + t1.sgst_per", nativeQuery = true)

		List<AdminCrNoteRegItem> getAllB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
}
