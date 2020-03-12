package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.HSNItemWiseReport;
import com.ats.webapi.model.reportv2.HSNWiseReport;

public interface HSNItemWiseReportRepo extends JpaRepository<HSNItemWiseReport, Integer> {

	
	@Query(value = "SELECT\r\n" + 
			"    item_id,\r\n" + 
			"    item_name,\r\n" + 
			"    item_uom,\r\n" + 
			"    item_hsncd,\r\n" + 
			"    item_tax1,\r\n" + 
			"    item_tax2,\r\n" + 
			"    SUM(bill_qty) AS bill_qty,\r\n" + 
			"    SUM(taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(sgst_rs) AS sgst_rs,\r\n" + 
			"    cat_id,\r\n" + 
			"    sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_bill_detail.item_id,\r\n" + 
			"        m_item.item_name,\r\n" + 
			"        m_item_sup.item_uom,\r\n" + 
			"        t_bill_detail.hsn_code AS id,\r\n" + 
			"        t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
			"        t_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"        t_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"        SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
			"        SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"        SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"        SUM(t_bill_detail.sgst_rs) AS sgst_rs,\r\n" + 
			"        m_item.item_grp1 AS cat_id,\r\n" + 
			"        m_item.item_grp2 AS sub_cat_id\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header,\r\n" + 
			"        t_bill_detail,\r\n" + 
			"        m_item,\r\n" + 
			"        m_item_sup\r\n" + 
			"    WHERE\r\n" + 
			"        t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.ex_varchar2 = 0 AND t_bill_detail.item_id = m_item.id AND m_item.id = m_item_sup.item_id AND t_bill_detail.item_id = m_item_sup.item_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_bill_detail.item_id\r\n" + 
			"    UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS id,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    d.sgst_per AS item_tax1,\r\n" + 
			"    d.cgst_per AS item_tax2,\r\n" + 
			"    SUM(d.qty) AS bill_qty,\r\n" + 
			"    SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(d.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(d.sgst_rs) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_franchisee f,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			") t1\r\n" + 
			"GROUP BY\r\n" + 
			"    item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    item_hsncd,\r\n" + 
			"    cat_id,\r\n" + 
			"    sub_cat_id,\r\n" + 
			"    item_name", nativeQuery = true)

	List<HSNItemWiseReport> getAdminReportAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_bill_detail.item_id,\r\n" + 
			"    m_item.item_name,\r\n" + 
			"    m_item_sup.item_uom,\r\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
			"    t_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"    t_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs,\r\n" + 
			"    m_item.item_grp1 as cat_id,\r\n" + 
			"    m_item.item_grp2 as sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header,\r\n" + 
			"    t_bill_detail,\r\n" + 
			"    m_item,\r\n" + 
			"    m_item_sup\r\n" + 
			"WHERE\r\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.ex_varchar2 = 0 AND t_bill_detail.item_id = m_item.id AND m_item.id = m_item_sup.item_id AND t_bill_detail.item_id = m_item_sup.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    t_bill_detail.hsn_code,\r\n" + 
			"    m_item.item_grp1,\r\n" + 
			"    m_item.item_grp2,\r\n" + 
			"    m_item.item_name", nativeQuery = true)

	List<HSNItemWiseReport> getAdminReportBillWiseFrWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	
	@Query(value = "SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    d.sgst_per AS item_tax1,\r\n" + 
			"    d.cgst_per AS item_tax2,\r\n" + 
			"    SUM(d.qty) AS bill_qty,\r\n" + 
			"    SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(d.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(d.sgst_rs) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_franchisee f,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    d.ext_var1,\r\n" + 
			"    i.item_grp1,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    i.item_name", nativeQuery = true)

	List<HSNItemWiseReport> getAdminReportBillWiseCompOutletWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	
	@Query(value = "SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    d.sgst_per AS item_tax1,\r\n" + 
			"    d.cgst_per AS item_tax2,\r\n" + 
			"    SUM(d.qty) AS bill_qty,\r\n" + 
			"    SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(d.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(d.sgst_rs) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_franchisee f,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id AND h.fr_id = :frId\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    d.ext_var1,\r\n" + 
			"    i.item_grp1,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    i.item_name", nativeQuery = true)

	List<HSNItemWiseReport> getOPSReportBillWiseCompOutletWiseFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("frId") int frId);

	
	
	//------------CRN-----------------------------
	
	@Query(value = "SELECT\r\n" + 
			"    item_id,\r\n" + 
			"    item_name,\r\n" + 
			"    item_uom,\r\n" + 
			"    item_hsncd,\r\n" + 
			"    item_tax1,\r\n" + 
			"    item_tax2,\r\n" + 
			"    SUM(taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(sgst_rs) AS sgst_rs,\r\n" + 
			"    bill_qty,\r\n" + 
			"    cat_id,\r\n" + 
			"    sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_credit_note_details.item_id,\r\n" + 
			"        m_item.item_name,\r\n" + 
			"        m_item_sup.item_uom,\r\n" + 
			"        t_credit_note_details.hsn_code AS item_hsncd,\r\n" + 
			"        t_credit_note_details.sgst_per AS item_tax1,\r\n" + 
			"        t_credit_note_details.cgst_per AS item_tax2,\r\n" + 
			"        SUM(\r\n" + 
			"            t_credit_note_details.grn_gvn_qty\r\n" + 
			"        ) AS bill_qty,\r\n" + 
			"        SUM(\r\n" + 
			"            t_credit_note_details.taxable_amt\r\n" + 
			"        ) AS taxable_amt,\r\n" + 
			"        SUM(t_credit_note_details.cgst_rs) AS cgst_rs,\r\n" + 
			"        SUM(t_credit_note_details.sgst_rs) AS sgst_rs,\r\n" + 
			"        m_item.item_grp1 AS cat_id,\r\n" + 
			"        m_item.item_grp2 AS sub_cat_id\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_details,\r\n" + 
			"        t_credit_note_header,\r\n" + 
			"        m_item,\r\n" + 
			"        m_item_sup\r\n" + 
			"    WHERE\r\n" + 
			"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_details.item_id = m_item.id AND m_item.id = m_item_sup.item_id AND t_credit_note_details.item_id = m_item_sup.item_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_credit_note_details.item_id\r\n" + 
			"    UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    c.sgst_per AS item_tax1,\r\n" + 
			"    c.cgst_per AS item_tax2,\r\n" + 
			"    SUM(c.crn_qty) AS bill_qty,\r\n" + 
			"    SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(c.cgst_amt) AS cgst_rs,\r\n" + 
			"    SUM(c.sgst_amt) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos c,\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    c.bill_no = h.sell_bill_no AND h.sell_bill_no = d.sell_bill_no AND c.crn_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			") t1\r\n" + 
			"GROUP BY\r\n" + 
			"    item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    item_hsncd,\r\n" + 
			"    cat_id,\r\n" + 
			"    sub_cat_id,\r\n" + 
			"    item_name", nativeQuery = true)

	List<HSNItemWiseReport> getAdminReportCRNAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_details.item_id,\r\n" + 
			"    m_item.item_name,\r\n" + 
			"    m_item_sup.item_uom,\r\n" + 
			"    t_credit_note_details.hsn_code AS item_hsncd,\r\n" + 
			"    t_credit_note_details.sgst_per AS item_tax1,\r\n" + 
			"    t_credit_note_details.cgst_per AS item_tax2,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_qty\r\n" + 
			"    ) AS bill_qty,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.taxable_amt\r\n" + 
			"    ) AS taxable_amt,\r\n" + 
			"    SUM(t_credit_note_details.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_credit_note_details.sgst_rs) AS sgst_rs,\r\n" + 
			"    m_item.item_grp1 AS cat_id,\r\n" + 
			"    m_item.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_details,\r\n" + 
			"    t_credit_note_header,\r\n" + 
			"    m_item,\r\n" + 
			"    m_item_sup\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_details.item_id = m_item.id AND m_item.id = m_item_sup.item_id AND t_credit_note_details.item_id = m_item_sup.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    t_credit_note_details.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    t_credit_note_details.hsn_code,\r\n" + 
			"    m_item.item_grp1,\r\n" + 
			"    m_item.item_grp2,\r\n" + 
			"    m_item.item_name", nativeQuery = true)
	List<HSNItemWiseReport> getAdminReportHsnCrFrWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    c.sgst_per AS item_tax1,\r\n" + 
			"    c.cgst_per AS item_tax2,\r\n" + 
			"    SUM(c.crn_qty) AS bill_qty,\r\n" + 
			"    SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(c.cgst_amt) AS cgst_rs,\r\n" + 
			"    SUM(c.sgst_amt) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos c,\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    c.bill_no = h.sell_bill_no AND h.sell_bill_no = d.sell_bill_no AND c.crn_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    d.ext_var1,\r\n" + 
			"    i.item_grp1,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    i.item_name", nativeQuery = true)
	List<HSNItemWiseReport> getAdminReportHsnCrCompOutletWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	
	@Query(value = "SELECT\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_uom,\r\n" + 
			"    d.ext_var1 AS item_hsncd,\r\n" + 
			"    c.sgst_per AS item_tax1,\r\n" + 
			"    c.cgst_per AS item_tax2,\r\n" + 
			"    SUM(c.crn_qty) AS bill_qty,\r\n" + 
			"    SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(c.cgst_amt) AS cgst_rs,\r\n" + 
			"    SUM(c.sgst_amt) AS sgst_rs,\r\n" + 
			"    i.item_grp1 AS cat_id,\r\n" + 
			"    i.item_grp2 AS sub_cat_id\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos c,\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    c.bill_no = h.sell_bill_no AND h.sell_bill_no = d.sell_bill_no AND c.crn_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND d.item_id = i.id AND i.id = s.item_id AND d.item_id = s.item_id AND c.ex_int1 = :frId\r\n" + 
			"GROUP BY\r\n" + 
			"    d.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    d.ext_var1,\r\n" + 
			"    i.item_grp1,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    i.item_name", nativeQuery = true)
	List<HSNItemWiseReport> getOPSReportHsnCrCompOutletWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate , @Param("frId") int frId);

	
}
