package com.ats.webapi.repository.tally;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.webapi.model.tally.SalesInvoices;

public interface SalesInvoicesRepo extends JpaRepository<SalesInvoices, Long>{
	
	@Query(value="SELECT\r\n" + 
			"    a.bill_no,\r\n" + 
			"    a.date,\r\n" + 
			"    a.e_way_bill_no,\r\n" + 
			"    a.e_way_bill_date,\r\n" + 
			"    a.customer_name,\r\n" + 
			"    a.gst_no,\r\n" + 
			"    a.address,\r\n" + 
			"    a.state,\r\n" + 
			"    a.state_code,\r\n" + 
			"    a.ship_to_customer_name,\r\n" + 
			"    a.ship_to_gst_no,\r\n" + 
			"    a.ship_to_address,\r\n" + 
			"    a.ship_to_state,\r\n" + 
			"    a.ship_to_state_code,\r\n" + 
			"    a.product_name,\r\n" + 
			"    a.part_no,\r\n" + 
			"    a.qty,\r\n" + 
			"    a.unit,\r\n" + 
			"    a.hsn,\r\n" + 
			"    a.gst_per,\r\n" + 
			"    a.rate,\r\n" + 
			"    a.discount,\r\n" + 
			"    a.amount,\r\n" + 
			"    a.cgst,\r\n" + 
			"    a.sgst,\r\n" + 
			"    a.igst,\r\n" + 
			"    a.other_ledger,\r\n" + 
			"    ROUND(COALESCE((b.grand_total),\r\n" + 
			"    0),\r\n" + 
			"    0) - ROUND(COALESCE((b.grand_total),\r\n" + 
			"    0),\r\n" + 
			"    2) AS round_off,\r\n" + 
			"    ROUND(COALESCE((b.grand_total),\r\n" + 
			"    0),\r\n" + 
			"    0) AS total_amount\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.bill_no as bill_id,\r\n" + 
			"        h.invoice_no AS bill_no,\r\n" + 
			"        h.bill_date AS date,\r\n" + 
			"        h.is_tally_sync AS e_way_bill_no,\r\n" + 
			"        h.bill_date AS e_way_bill_date,\r\n" + 
			"        h.party_name AS ship_to_customer_name,\r\n" + 
			"        h.party_gstin AS ship_to_gst_no,\r\n" + 
			"        h.party_address AS ship_to_address,\r\n" + 
			"        c.state,\r\n" + 
			"        c.state_code,\r\n" + 
			"        h.ex_varchar3 AS customer_name,\r\n" + 
			"        h.ex_varchar4 AS gst_no,\r\n" + 
			"        h.ex_varchar5 AS address,\r\n" + 
			"        c.state AS ship_to_state,\r\n" + 
			"        c.state_code AS ship_to_state_code,\r\n" + 
			"        i.item_name AS product_name,\r\n" + 
			"        0 AS part_no,\r\n" + 
			"        d.bill_qty AS qty,\r\n" + 
			"        sup.item_uom AS unit,\r\n" + 
			"        d.hsn_code AS hsn,\r\n" + 
			"        d.cgst_per + d.sgst_per AS gst_per,\r\n" + 
			"        d.base_rate AS rate,\r\n" + 
			"        d.disc_per AS discount,\r\n" + 
			"        d.remark AS amount,\r\n" + 
			"        d.cgst_rs AS cgst,\r\n" + 
			"        d.sgst_rs AS sgst,\r\n" + 
			"        d.igst_rs AS igst,\r\n" + 
			"        0 AS other_ledger\r\n" + 
			"        \r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d,\r\n" + 
			"        m_company c,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_item_sup sup\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_no = d.bill_no AND h.del_status = 0 AND d.del_status = 0 AND d.item_id = i.id AND i.id = sup.item_id AND c.comp_id = 1 AND h.tally_sync = 0\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.bill_no\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.bill_no,\r\n" + 
			"        SUM(d.grand_total) AS grand_total\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        d.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.bill_no\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"    a.bill_id = b.bill_no \r\n" + 
			"ORDER BY bill_no",nativeQuery=true)
	List<SalesInvoices> getTallySyncData();

}
