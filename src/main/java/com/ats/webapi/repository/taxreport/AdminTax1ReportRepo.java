package com.ats.webapi.repository.taxreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.taxreport.AdminTax1Report;

public interface AdminTax1ReportRepo extends JpaRepository<AdminTax1Report, Integer>{

	
	//Anmol--9-03-2020-- ALL And B2B---Tax slab wise--
			@Query(value = "SELECT\r\n" + 
					"    *\r\n" + 
					"FROM\r\n" + 
					"    (\r\n" + 
					"    SELECT\r\n" + 
					"        t_bill_detail.bill_detail_no,\r\n" + 
					"        t_bill_header.invoice_no,\r\n" + 
					"        t_bill_header.bill_date,\r\n" + 
					"        CONCAT(\r\n" + 
					"            m_franchisee.fr_name,\r\n" + 
					"            '-',\r\n" + 
					"            m_franchisee.fr_code\r\n" + 
					"        ) AS fr_name,\r\n" + 
					"        m_franchisee.fr_gst_no,\r\n" + 
					"        t_bill_detail.bill_no,\r\n" + 
					"        t_bill_detail.cgst_per,\r\n" + 
					"        t_bill_detail.sgst_per,\r\n" + 
					"        t_bill_detail.cgst_per + t_bill_detail.sgst_per AS tax_per,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.taxable_amt),\r\n" + 
					"            2\r\n" + 
					"        ) AS taxable_amt,\r\n" + 
					"        ROUND(SUM(t_bill_detail.cgst_rs),\r\n" + 
					"        2) AS cgst_amt,\r\n" + 
					"        ROUND(SUM(t_bill_detail.sgst_rs),\r\n" + 
					"        2) AS sgst_amt,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.total_tax),\r\n" + 
					"            2\r\n" + 
					"        ) AS total_tax,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.grand_total),\r\n" + 
					"            2\r\n" + 
					"        ) AS grand_total,\r\n" + 
					"        t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
					"        t_bill_header.ex_varchar4 AS bill_to_gst,\r\n" + 
					"        t_bill_header.party_name AS ship_to_name,\r\n" + 
					"        t_bill_header.party_gstin AS ship_to_gst\r\n" + 
					"    FROM\r\n" + 
					"        t_bill_detail,\r\n" + 
					"        t_bill_header,\r\n" + 
					"        m_franchisee\r\n" + 
					"    WHERE\r\n" + 
					"        t_bill_header.ex_varchar2 IN(0) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar4 != ''\r\n" + 
					"    GROUP BY\r\n" + 
					"        t_bill_detail.bill_no,\r\n" + 
					"        t_bill_detail.cgst_per + t_bill_detail.sgst_per\r\n" + 
					"    UNION ALL\r\n" + 
					"SELECT\r\n" + 
					"    d.sell_bill_detail_no AS bill_detail_no,\r\n" + 
					"    h.invoice_no,\r\n" + 
					"    h.bill_date,\r\n" + 
					"    CONCAT(f.fr_name, '-', f.fr_code) AS fr_name,\r\n" + 
					"    f.fr_gst_no,\r\n" + 
					"    d.sell_bill_no AS bill_no,\r\n" + 
					"    d.cgst_per,\r\n" + 
					"    d.sgst_per,\r\n" + 
					"    d.cgst_per + d.sgst_per AS tax_per,\r\n" + 
					"    ROUND(SUM(d.taxable_amt),\r\n" + 
					"    2) AS taxable_amt,\r\n" + 
					"    ROUND(SUM(d.cgst_rs),\r\n" + 
					"    2) AS cgst_amt,\r\n" + 
					"    ROUND(SUM(d.sgst_rs),\r\n" + 
					"    2) AS sgst_amt,\r\n" + 
					"    ROUND(SUM(d.total_tax),\r\n" + 
					"    2) AS total_tax,\r\n" + 
					"    ROUND(SUM(d.ext_float1),\r\n" + 
					"    2) AS grand_total,\r\n" + 
					"    h.user_name AS bill_to_name,\r\n" + 
					"    h.user_gst_no AS bill_to_gst,\r\n" + 
					"    h.user_name AS ship_to_name,\r\n" + 
					"    h.user_gst_no AS ship_to_gst\r\n" + 
					"FROM\r\n" + 
					"    t_sell_bill_detail d,\r\n" + 
					"    t_sell_bill_header h,\r\n" + 
					"    m_franchisee f\r\n" + 
					"WHERE\r\n" + 
					"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id AND h.user_gst_no != '' AND h.del_status = 0 AND f.kg_1=1 \r\n" + 
					"GROUP BY\r\n" + 
					"    d.sell_bill_no,\r\n" + 
					"    d.cgst_per + d.sgst_per\r\n" + 
					") t1\r\n" + 
					"ORDER BY\r\n" + 
					"    t1.bill_no,\r\n" + 
					"    t1.cgst_per + t1.sgst_per", nativeQuery = true)
			List<AdminTax1Report> getAdminTaxSlabAllB2B(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		 
	
	
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
				"    t_bill_header.ex_varchar2 IN(0) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar4 =''  \n" + 
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
					"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id AND h.user_gst_no = '' AND h.del_status=0 AND f.kg_1=1 \n" + 
					"GROUP BY\n" + 
					"    h.fr_id,\n" + 
					"    d.cgst_per + d.sgst_per\n" + 
					"ORDER BY\n" + 
					"    h.fr_id,\n" + 
					"    d.cgst_per + d.sgst_per", nativeQuery = true)
			List<AdminTax1Report> getAdminTaxSlabCompBillB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
			
			
			//Anmol--9-03-2020--ALL--B2C -- Tax slab wise
			@Query(value = "SELECT\r\n" + 
					"    *\r\n" + 
					"FROM\r\n" + 
					"    (\r\n" + 
					"    SELECT\r\n" + 
					"        t_bill_header.fr_id,\r\n" + 
					"        t_bill_detail.bill_detail_no,\r\n" + 
					"        t_bill_header.invoice_no,\r\n" + 
					"        t_bill_header.bill_date,\r\n" + 
					"        'Madhvi Dairy Private Limited - Factory End' AS fr_name,\r\n" + 
					"        m_franchisee.fr_gst_no,\r\n" + 
					"        t_bill_detail.bill_no,\r\n" + 
					"        t_bill_detail.cgst_per,\r\n" + 
					"        t_bill_detail.sgst_per,\r\n" + 
					"        t_bill_detail.cgst_per + t_bill_detail.sgst_per AS tax_per,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.taxable_amt),\r\n" + 
					"            2\r\n" + 
					"        ) AS taxable_amt,\r\n" + 
					"        ROUND(SUM(t_bill_detail.cgst_rs),\r\n" + 
					"        2) AS cgst_amt,\r\n" + 
					"        ROUND(SUM(t_bill_detail.sgst_rs),\r\n" + 
					"        2) AS sgst_amt,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.total_tax),\r\n" + 
					"            2\r\n" + 
					"        ) AS total_tax,\r\n" + 
					"        ROUND(\r\n" + 
					"            SUM(t_bill_detail.grand_total),\r\n" + 
					"            2\r\n" + 
					"        ) AS grand_total,\r\n" + 
					"        t_bill_header.ex_varchar3 AS bill_to_name,\r\n" + 
					"        t_bill_header.ex_varchar4 AS bill_to_gst,\r\n" + 
					"        t_bill_header.party_name AS ship_to_name,\r\n" + 
					"        t_bill_header.party_gstin AS ship_to_gst\r\n" + 
					"    FROM\r\n" + 
					"        t_bill_detail,\r\n" + 
					"        t_bill_header,\r\n" + 
					"        m_franchisee\r\n" + 
					"    WHERE\r\n" + 
					"        t_bill_header.ex_varchar2 IN(0) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar4 = ''\r\n" + 
					"    GROUP BY\r\n" + 
					"        t_bill_detail.cgst_per + t_bill_detail.sgst_per\r\n" + 
					"    UNION ALL\r\n" + 
					"SELECT\r\n" + 
					"    hh.fr_id,\r\n" + 
					"    d.sell_bill_detail_no AS bill_detail_no,\r\n" + 
					"    hh.invoice_no,\r\n" + 
					"    hh.bill_date,\r\n" + 
					"    CONCAT(f.fr_name, '-', f.fr_code) AS fr_name,\r\n" + 
					"    f.fr_gst_no,\r\n" + 
					"    d.sell_bill_no AS bill_no,\r\n" + 
					"    d.cgst_per,\r\n" + 
					"    d.sgst_per,\r\n" + 
					"    d.cgst_per + d.sgst_per AS tax_per,\r\n" + 
					"    ROUND(SUM(d.taxable_amt),\r\n" + 
					"    2) AS taxable_amt,\r\n" + 
					"    ROUND(SUM(d.cgst_rs),\r\n" + 
					"    2) AS cgst_amt,\r\n" + 
					"    ROUND(SUM(d.sgst_rs),\r\n" + 
					"    2) AS sgst_amt,\r\n" + 
					"    ROUND(SUM(d.total_tax),\r\n" + 
					"    2) AS total_tax,\r\n" + 
					"    ROUND(SUM(d.ext_float1),\r\n" + 
					"    2) AS grand_total,\r\n" + 
					"    hh.user_name AS bill_to_name,\r\n" + 
					"    hh.user_gst_no AS bill_to_gst,\r\n" + 
					"    hh.user_name AS ship_to_name,\r\n" + 
					"    hh.user_gst_no AS ship_to_gst\r\n" + 
					"FROM\r\n" + 
					"    t_sell_bill_detail d,\r\n" + 
					"    t_sell_bill_header hh,\r\n" + 
					"    m_franchisee f\r\n" + 
					"WHERE\r\n" + 
					"    hh.sell_bill_no = d.sell_bill_no AND hh.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = hh.fr_id AND hh.user_gst_no = '' AND hh.del_status = 0 AND f.kg_1 = 1\r\n" + 
					"GROUP BY\r\n" + 
					"    hh.fr_id,\r\n" + 
					"    d.cgst_per + d.sgst_per\r\n" + 
					") t1\r\n" + 
					"ORDER BY\r\n" + 
					"    t1.fr_id,\r\n" + 
					"    t1.cgst_per + t1.sgst_per", nativeQuery = true)
			List<AdminTax1Report> getAdminTaxSlabAllB2C(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
			
}
