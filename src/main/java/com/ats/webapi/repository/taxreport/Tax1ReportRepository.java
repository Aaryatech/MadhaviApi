package com.ats.webapi.repository.taxreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.taxreport.Tax1Report;

@Repository
public interface Tax1ReportRepository extends JpaRepository<Tax1Report, Integer> {

	@Query(value = "SELECT\n" + 
			"    t_bill_detail.bill_detail_no,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    t_bill_detail.bill_no,\n" + 
			"    t_bill_detail.cgst_per,\n" + 
			"    t_bill_detail.sgst_per,\n" + 
			"    t_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_bill_detail.taxable_amt),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt,\n" + 
			"    ROUND(SUM(t_bill_detail.cgst_rs),\n" + 
			"    2) AS cgst_amt,\n" + 
			"    ROUND(SUM(t_bill_detail.sgst_rs),\n" + 
			"    2) AS sgst_amt,\n" + 
			"    ROUND(SUM(t_bill_detail.total_tax),\n" + 
			"    2) AS total_tax,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_bill_detail.grand_total),\n" + 
			"        2\n" + 
			"    ) AS grand_total\n" + 
			"FROM\n" + 
			"    t_bill_detail,\n" + 
			"    t_bill_header,\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"      t_bill_header.ex_varchar1 IN(:temp) AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id\n" + 
			"GROUP BY\n" + 
			"    t_bill_detail.cgst_per + sgst_per,\n" + 
			"    bill_no\n" + 
			"ORDER BY\n" + 
			"    t_bill_detail.bill_no,\n" + 
			"    tax_per", nativeQuery = true)
	List<Tax1Report> getTax1Report12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	@Query(value = "SELECT\n" + 
			"    t_sell_bill_detail.sell_bill_detail_no as bill_detail_no,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    t_sell_bill_detail.sell_bill_no as bill_no,\n" + 
			"    t_sell_bill_detail.cgst_per,\n" + 
			"    t_sell_bill_detail.sgst_per,\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt,\n" + 
			"    ROUND(SUM(t_sell_bill_detail.cgst_rs),\n" + 
			"    2) AS cgst_amt,\n" + 
			"    ROUND(SUM(t_sell_bill_detail.sgst_rs),\n" + 
			"    2) AS sgst_amt,\n" + 
			"    ROUND(SUM(t_sell_bill_detail.total_tax),\n" + 
			"    2) AS total_tax,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.grand_total),\n" + 
			"        2\n" + 
			"    ) AS grand_total\n" + 
			"FROM\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_sell_bill_header.fr_id\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per,\n" + 
			"    bill_no\n" + 
			"ORDER BY\n" + 
			"    t_sell_bill_detail.sell_bill_no,\n" + 
			"    tax_per", nativeQuery = true)
	List<Tax1Report> getTax1Report3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	

	@Query(value = "SELECT\n" + 
			"    a.bill_detail_no,\n" + 
			"    a.invoice_no,\n" + 
			"    a.bill_date,\n" + 
			"    a.fr_name,\n" + 
			"    a.fr_gst_no,\n" + 
			"    a.bill_no,\n" + 
			"    a.cgst_per,\n" + 
			"    a.sgst_per,\n" + 
			"    a.tax_per,\n" + 
			"    a.taxable_amt,\n" + 
			"    a.cgst_amt,\n" + 
			"    a.sgst_amt,\n" + 
			"    a.total_tax,\n" + 
			"    a.grand_total\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_bill_detail.bill_detail_no,\n" + 
			"        t_bill_header.invoice_no,\n" + 
			"        t_bill_header.bill_date,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        t_bill_detail.bill_no,\n" + 
			"        t_bill_detail.cgst_per,\n" + 
			"        t_bill_detail.sgst_per,\n" + 
			"        t_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"        ROUND(\n" + 
			"            SUM(t_bill_detail.taxable_amt),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt,\n" + 
			"        ROUND(SUM(t_bill_detail.cgst_rs),\n" + 
			"        2) AS cgst_amt,\n" + 
			"        ROUND(SUM(t_bill_detail.sgst_rs),\n" + 
			"        2) AS sgst_amt,\n" + 
			"        ROUND(\n" + 
			"            SUM(t_bill_detail.total_tax),\n" + 
			"            2\n" + 
			"        ) AS total_tax,\n" + 
			"        ROUND(\n" + 
			"            SUM(t_bill_detail.grand_total),\n" + 
			"            2\n" + 
			"        ) AS grand_total\n" + 
			"    FROM\n" + 
			"        t_bill_detail,\n" + 
			"        t_bill_header,\n" + 
			"        m_franchisee\n" + 
			"    WHERE\n" + 
			"      t_bill_header.ex_varchar1 IN(:temp)  AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id\n" + 
			"    GROUP BY\n" + 
			"        t_bill_detail.cgst_per + sgst_per,\n" + 
			"        bill_no\n" + 
			"    UNION ALL\n" + 
			"SELECT\n" + 
			"    t_sell_bill_detail.sell_bill_detail_no AS bill_detail_no,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    t_sell_bill_detail.sell_bill_no AS bill_no,\n" + 
			"    t_sell_bill_detail.cgst_per,\n" + 
			"    t_sell_bill_detail.sgst_per,\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt,\n" + 
			"    + ROUND(\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.total_tax),\n" + 
			"        2\n" + 
			"    ) AS total_tax,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.grand_total),\n" + 
			"        2\n" + 
			"    ) AS grand_total\n" + 
			"FROM\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_sell_bill_header.fr_id\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per,\n" + 
			"    bill_no\n" + 
 			") a\n" + 
			"ORDER BY\n" + 
			"    a.bill_no,\n" + 
			"    a.tax_per", nativeQuery = true)
	List<Tax1Report> getTax1ReportAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer>  temp);
 
	
	
	//spec fr
	
	@Query(value = "select t_bill_detail.bill_detail_no,t_bill_header.invoice_no,t_bill_header.bill_date,m_franchisee.fr_name,m_franchisee.fr_gst_no, t_bill_detail.bill_no, t_bill_detail.cgst_per,t_bill_detail.sgst_per,t_bill_detail.cgst_per+sgst_per as tax_per,ROUND(SUM(t_bill_detail.taxable_amt),2) as taxable_amt,ROUND(SUM(t_bill_detail.cgst_rs),2) as cgst_amt,ROUND(SUM(t_bill_detail.sgst_rs),2) as sgst_amt,ROUND(SUM(t_bill_detail.total_tax),2) as total_tax,ROUND(SUM(t_bill_detail.grand_total),2) as grand_total from  t_bill_detail,t_bill_header,m_franchisee where t_bill_header.bill_no=t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id=t_bill_header.fr_id AND m_franchisee.fr_id=:frId group by t_bill_detail. cgst_per+sgst_per,bill_no order by t_bill_detail.bill_no,tax_per", nativeQuery = true)
	List<Tax1Report> getTax1ReportByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

}
