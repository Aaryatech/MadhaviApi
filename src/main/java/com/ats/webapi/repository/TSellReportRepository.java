package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.TSellReport; 

public interface TSellReportRepository extends JpaRepository<TSellReport, Integer>{
	
	@Query(value="SELECT\r\n" + 
			"    t1.item_id,\r\n" + 
			"    t1.item_name,\r\n" + 
			"    t1.hsn_no,\r\n" + 
			"    COALESCE(t1.cgst - t2.cgst, t1.cgst) AS cgst,\r\n" + 
			"    COALESCE(t1.sgst - t2.sgst, t1.sgst) AS sgst,\r\n" + 
			"    COALESCE(t1.igst - t2.igst, t1.igst) AS igst,\r\n" + 
			"    COALESCE(\r\n" + 
			"        t1.total_tax - t2.total_tax,\r\n" + 
			"        t1.total_tax\r\n" + 
			"    ) AS total_tax,\r\n" + 
			"    COALESCE(\r\n" + 
			"        t1.taxable_amt - t2.taxable_amt,\r\n" + 
			"        t1.taxable_amt\r\n" + 
			"    ) AS taxable_amt,\r\n" + 
			"    COALESCE(\r\n" + 
			"        t1.grand_total - t2.grand_total,\r\n" + 
			"        t1.grand_total\r\n" + 
			"    ) AS grand_total\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        d.item_id,\r\n" + 
			"        i.item_name,\r\n" + 
			"        s.item_hsncd AS hsn_no,\r\n" + 
			"        SUM(d.cgst_rs) AS cgst,\r\n" + 
			"        SUM(d.sgst_rs) AS sgst,\r\n" + 
			"        SUM(d.igst_rs) AS igst,\r\n" + 
			"        SUM(d.total_tax) AS total_tax,\r\n" + 
			"        SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
			"        SUM(d.grand_total) AS grand_total\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        m_item i,\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_item_sup s\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND d.item_id = i.id AND d.item_id = s.item_id AND h.fr_id = :frId AND h.del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        hsn_no\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        c.item_id,\r\n" + 
			"        i.item_name,\r\n" + 
			"        s.item_hsncd AS hsn_no,\r\n" + 
			"        SUM(c.cgst_amt) AS cgst,\r\n" + 
			"        SUM(c.sgst_amt) AS sgst,\r\n" + 
			"        SUM(c.igst_amt) AS igst,\r\n" + 
			"        SUM(c.sgst_amt + c.sgst_amt) AS total_tax,\r\n" + 
			"        SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
			"        SUM(c.grand_total) AS grand_total\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos c,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_item_sup s\r\n" + 
			"    WHERE\r\n" + 
			"        c.crn_date BETWEEN :fromDate AND :toDate AND c.item_id = i.id AND c.item_id = s.item_id AND c.ex_int1 = :frId AND c.del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        hsn_no\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.hsn_no = t2.hsn_no\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    c.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    s.item_hsncd AS hsn_no,\r\n" + 
			"    SUM(c.cgst_amt) AS cgst,\r\n" + 
			"    SUM(c.sgst_amt) AS sgst,\r\n" + 
			"    SUM(c.igst_amt) AS igst,\r\n" + 
			"    SUM(c.sgst_amt + c.sgst_amt) AS total_tax,\r\n" + 
			"    SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(c.grand_total) AS grand_total\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_pos c,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    c.crn_date BETWEEN :fromDate AND :toDate AND c.item_id = i.id AND c.item_id = s.item_id AND c.ex_int1 = :frId AND c.del_status=0 AND s.item_hsncd NOT IN(\r\n" + 
			"    SELECT\r\n" + 
			"        s.item_hsncd AS hsn_no\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        m_item i,\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_item_sup s\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND d.item_id = i.id AND d.item_id = s.item_id AND h.fr_id = :frId AND h.del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        hsn_no\r\n" + 
			")\r\n" + 
			"GROUP BY\r\n" + 
			"    hsn_no",nativeQuery=true)
	List<TSellReport> hsnCodeWiseReport( @Param("frId")int frId,@Param("fromDate")String fromDate,@Param("toDate")String toDate);

}
