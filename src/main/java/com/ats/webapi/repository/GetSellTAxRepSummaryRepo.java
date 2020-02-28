package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.GetRepTaxSell;
import com.ats.webapi.model.report.GetSellTaxRepSummary;

public interface GetSellTAxRepSummaryRepo extends JpaRepository<GetSellTaxRepSummary, Integer> {	
	
	
	@Query(value="SELECT\n" + 
			"    t1.user_gst_no,\n" + 
			"    t1.bill_date,\n" + 
			"    t1.sell_bill_detail_no,\n" + 
			"    t1.sell_bill_no,\n" + 
			"    t1.tax_per,\n" + 
			"    t1.tax_amount,\n" + 
			"    t1.cgst,\n" + 
			"    t1.sgst,\n" + 
			"    t1.igst,\n" + 
			"    t1.fr_id,\n" + 
			"    t1.fr_name,\n" + 
			"    t1.bill_amount,\n" + 
			"    t2.from_bill,\n" + 
			"    t2.to_bill\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        1 as id,\n" + 
			"        h.user_gst_no,\n" + 
			"        h.bill_date,\n" + 
			"        d.sell_bill_detail_no,\n" + 
			"        h.invoice_no AS sell_bill_no,\n" + 
			"        d.cgst_per + d.sgst_per AS tax_per,\n" + 
			"        SUM(d.taxable_amt) AS tax_amount,\n" + 
			"        SUM(d.cgst_rs) AS cgst,\n" + 
			"        SUM(d.sgst_rs) AS sgst,\n" + 
			"        SUM(d.igst_rs) AS igst,\n" + 
			"        h.fr_id,\n" + 
			"        f.fr_name,\n" + 
			"        SUM(d.grand_total) AS bill_amount\n" + 
			"       \n" + 
			"    FROM\n" + 
			"        t_sell_bill_detail d,\n" + 
			"        t_sell_bill_header h,\n" + 
			"        m_franchisee f\n" + 
			"    WHERE\n" + 
			"        h.fr_id IN(:frId) AND h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id\n" + 
			"    GROUP BY\n" + 
			"        (d.cgst_per + d.sgst_per),\n" + 
			"        h.fr_id\n" + 
			"    ORDER BY\n" + 
			"         d.cgst_per + d.sgst_per\n" + 
			"  \n" + 
			") t1 left JOIN(\n" + 
			"        SELECT\n" + 
			"        MIN(hs.invoice_no) AS from_bill,\n" + 
			"       MAX(hs.invoice_no) AS to_bill\n" + 
			"    FROM\n" + 
			"        t_sell_bill_header hs\n" + 
			"   \n" + 
			"    WHERE\n" + 
			"        hs.del_status = 0 AND hs.bill_date BETWEEN :fromDate AND :toDate AND hs.fr_id = :frId\n" + 
			" ) t2 on t1.id=1",nativeQuery=true)
	List<GetSellTaxRepSummary> getTaxSellSummaryReport(@Param("fromDate")String fromDate, @Param("toDate")String toDate, @Param("frId")List<String> frId); 

}
