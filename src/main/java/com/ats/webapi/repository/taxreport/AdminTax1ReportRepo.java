package com.ats.webapi.repository.taxreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.taxreport.AdminTax1Report;

public interface AdminTax1ReportRepo extends JpaRepository<AdminTax1Report, Integer>{

	//Anmol--26-02-2020-- FR_Bills And B2B---Tax slab wise--
		@Query(value = "SELECT\n" + 
				"    t_bill_detail.bill_detail_no,\n" + 
				"    t_bill_header.invoice_no,\n" + 
				"    t_bill_header.bill_date,\n" + 
				"    CONCAT(\n" + 
				"        m_franchisee.fr_name,\n" + 
				"        '-',\n" + 
				"        m_franchisee.fr_code\n" + 
				"    ) AS fr_name,\n" + 
				"    m_franchisee.fr_gst_no,\n" + 
				"    t_bill_detail.bill_no,\n" + 
				"    t_bill_detail.cgst_per,\n" + 
				"    t_bill_detail.sgst_per,\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per AS tax_per,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.taxable_amt),\n" + 
				"        2\n" + 
				"    ) AS taxable_amt,\n" + 
				"    ROUND(SUM(t_bill_detail.cgst_rs),\n" + 
				"    2) AS cgst_amt,\n" + 
				"    ROUND(SUM(t_bill_detail.sgst_rs),\n" + 
				"    2) AS sgst_amt,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.total_tax),\n" + 
				"        2\n" + 
				"    ) AS total_tax,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.grand_total),\n" + 
				"        2\n" + 
				"    ) AS grand_total,\n" + 
				"    t_bill_header.ex_varchar3 AS bill_to_name,\n" + 
				"    t_bill_header.ex_varchar4 AS bill_to_gst,\n" + 
				"    t_bill_header.party_name AS ship_to_name,\n" + 
				"    t_bill_header.party_gstin AS ship_to_gst\n" + 
				"FROM\n" + 
				"    t_bill_detail,\n" + 
				"    t_bill_header,\n" + 
				"    m_franchisee\n" + 
				"WHERE\n" + 
				"    t_bill_header.ex_varchar2 IN(0) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar4 !=''\n" + 
				"GROUP BY\n" + 
				"    t_bill_detail.bill_no,\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per\n" + 
				"ORDER BY\n" + 
				"    t_bill_detail.bill_no,\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per", nativeQuery = true)
		List<AdminTax1Report> getAdminTaxSlabFrBillB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	 
		//Anmol--26-02-2020--Fr_Bills--B2C -- Tax slab wise
		@Query(value = "SELECT\n" + 
				"    t_bill_detail.bill_detail_no,\n" + 
				"    t_bill_header.invoice_no,\n" + 
				"    t_bill_header.bill_date,\n" + 
				"    CONCAT(\n" + 
				"        m_franchisee.fr_name,\n" + 
				"        '-',\n" + 
				"        m_franchisee.fr_code\n" + 
				"    ) AS fr_name,\n" + 
				"    m_franchisee.fr_gst_no,\n" + 
				"    t_bill_detail.bill_no,\n" + 
				"    t_bill_detail.cgst_per,\n" + 
				"    t_bill_detail.sgst_per,\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per AS tax_per,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.taxable_amt),\n" + 
				"        2\n" + 
				"    ) AS taxable_amt,\n" + 
				"    ROUND(SUM(t_bill_detail.cgst_rs),\n" + 
				"    2) AS cgst_amt,\n" + 
				"    ROUND(SUM(t_bill_detail.sgst_rs),\n" + 
				"    2) AS sgst_amt,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.total_tax),\n" + 
				"        2\n" + 
				"    ) AS total_tax,\n" + 
				"    ROUND(\n" + 
				"        SUM(t_bill_detail.grand_total),\n" + 
				"        2\n" + 
				"    ) AS grand_total,\n" + 
				"    t_bill_header.ex_varchar3 AS bill_to_name,\n" + 
				"    t_bill_header.ex_varchar4 AS bill_to_gst,\n" + 
				"    t_bill_header.party_name AS ship_to_name,\n" + 
				"    t_bill_header.party_gstin AS ship_to_gst\n" + 
				"FROM\n" + 
				"    t_bill_detail,\n" + 
				"    t_bill_header,\n" + 
				"    m_franchisee\n" + 
				"WHERE\n" + 
				"    t_bill_header.ex_varchar2 IN(0) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar4 =''\n" + 
				"GROUP BY\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per\n" + 
				"ORDER BY\n" + 
				"    t_bill_detail.cgst_per + t_bill_detail.sgst_per", nativeQuery = true)
		List<AdminTax1Report> getAdminTaxSlabFrBillB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
		
		//Anmol--26-02-2020-- Company Outlet_Bills And B2B---Tax slab wise--
			@Query(value = "SELECT\n" + 
					"    d.sell_bill_detail_no AS bill_detail_no,\n" + 
					"    h.invoice_no,\n" + 
					"    h.bill_date,\n" + 
					"    CONCAT(f.fr_name, '-', f.fr_code) AS fr_name,\n" + 
					"    f.fr_gst_no,\n" + 
					"    d.sell_bill_no AS bill_no,\n" + 
					"    d.cgst_per,\n" + 
					"    d.sgst_per,\n" + 
					"    d.cgst_per + d.sgst_per AS tax_per,\n" + 
					"    ROUND(SUM(d.taxable_amt),\n" + 
					"    2) AS taxable_amt,\n" + 
					"    ROUND(SUM(d.cgst_rs),\n" + 
					"    2) AS cgst_amt,\n" + 
					"    ROUND(SUM(d.sgst_rs),\n" + 
					"    2) AS sgst_amt,\n" + 
					"    ROUND(SUM(d.total_tax),\n" + 
					"    2) AS total_tax,\n" + 
					"    ROUND(SUM(d.ext_float1),\n" + 
					"    2) AS grand_total,\n" + 
					"    h.user_name AS bill_to_name,\n" + 
					"    h.user_gst_no AS bill_to_gst,\n" + 
					"    h.user_name AS ship_to_name,\n" + 
					"    h.user_gst_no AS ship_to_gst\n" + 
					"FROM\n" + 
					"    t_sell_bill_detail d,\n" + 
					"    t_sell_bill_header h,\n" + 
					"    m_franchisee f\n" + 
					"WHERE\n" + 
					"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id AND h.user_gst_no != '' AND h.del_status=0\n" + 
					"GROUP BY\n" + 
					"    d.sell_bill_no,\n" + 
					"    d.cgst_per + d.sgst_per\n" + 
					"ORDER BY\n" + 
					"    d.sell_bill_no,\n" + 
					"    d.cgst_per + d.sgst_per", nativeQuery = true)
			List<AdminTax1Report> getAdminTaxSlabCompBillB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	 
			
			//Anmol--26-02-2020--Company Outlet_Bills--B2C -- Tax slab wise
			@Query(value = "SELECT\n" + 
					"    d.sell_bill_detail_no AS bill_detail_no,\n" + 
					"    h.invoice_no,\n" + 
					"    h.bill_date,\n" + 
					"    CONCAT(f.fr_name, '-', f.fr_code) AS fr_name,\n" + 
					"    f.fr_gst_no,\n" + 
					"    d.sell_bill_no AS bill_no,\n" + 
					"    d.cgst_per,\n" + 
					"    d.sgst_per,\n" + 
					"    d.cgst_per + d.sgst_per AS tax_per,\n" + 
					"    ROUND(SUM(d.taxable_amt),\n" + 
					"    2) AS taxable_amt,\n" + 
					"    ROUND(SUM(d.cgst_rs),\n" + 
					"    2) AS cgst_amt,\n" + 
					"    ROUND(SUM(d.sgst_rs),\n" + 
					"    2) AS sgst_amt,\n" + 
					"    ROUND(SUM(d.total_tax),\n" + 
					"    2) AS total_tax,\n" + 
					"    ROUND(SUM(d.ext_float1),\n" + 
					"    2) AS grand_total,\n" + 
					"    h.user_name AS bill_to_name,\n" + 
					"    h.user_gst_no AS bill_to_gst,\n" + 
					"    h.user_name AS ship_to_name,\n" + 
					"    h.user_gst_no AS ship_to_gst\n" + 
					"FROM\n" + 
					"    t_sell_bill_detail d,\n" + 
					"    t_sell_bill_header h,\n" + 
					"    m_franchisee f\n" + 
					"WHERE\n" + 
					"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id AND h.user_gst_no = '' AND h.del_status=0\n" + 
					"GROUP BY\n" + 
					"    h.fr_id,\n" + 
					"    d.cgst_per + d.sgst_per\n" + 
					"ORDER BY\n" + 
					"    h.fr_id,\n" + 
					"    d.cgst_per + d.sgst_per", nativeQuery = true)
			List<AdminTax1Report> getAdminTaxSlabCompBillB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
}
