package com.ats.webapi.repository.tally;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.tally.CreditNoteInvoices;

@Repository
public interface CreditNoteInvoicesRepo extends JpaRepository<CreditNoteInvoices, Long>{
	
	@Query(value="SELECT\r\n" + 
			"    UUID() AS id, t1.fr_id, t1.crn_id, t1.crn_no, DATE_FORMAT(t1.crn_date, '%d-%m-%Y') AS DATE,\r\n" + 
			"    t1.bill_no,\r\n" + 
			"    DATE_FORMAT(t1.bill_date, '%d-%m-%Y') AS bill_date,\r\n" + 
			"    t1.e_way_bill_no,\r\n" + 
			"    DATE_FORMAT(t1.e_way_bill_date, '%d-%m-%Y') AS e_way_bill_date,\r\n" + 
			"    COALESCE(t1.customer_name, '') AS customer_name,\r\n" + 
			"    COALESCE(t1.gst_no, '') AS gst_no,\r\n" + 
			"    COALESCE(t1.address, '') AS address,\r\n" + 
			"    t1.state,\r\n" + 
			"    t1.state_code,\r\n" + 
			"    t1.ship_to_customer_name,\r\n" + 
			"    t1.ship_to_gst_no,\r\n" + 
			"    t1.ship_to_address,\r\n" + 
			"    t1.ship_to_state,\r\n" + 
			"    t1.ship_to_state_code,\r\n" + 
			"    t1.product_name,\r\n" + 
			"    t1.part_no,\r\n" + 
			"    ROUND(t1.qty, 2) AS qty,\r\n" + 
			"    t1.unit,\r\n" + 
			"    t1.hsn,\r\n" + 
			"    t1.gst_per,\r\n" + 
			"    ROUND(t1.rate, 2) AS rate,\r\n" + 
			"    0 AS discount,\r\n" + 
			"    0 AS amount,\r\n" + 
			"    COALESCE(ROUND(t2.cgst, 2),\r\n" + 
			"    0) AS cgst,\r\n" + 
			"    COALESCE(ROUND(t2.sgst, 2),\r\n" + 
			"    0) AS sgst,\r\n" + 
			"    COALESCE(ROUND(t2.igst, 2),\r\n" + 
			"    0) AS igst,\r\n" + 
			"    t1.other_ledger,\r\n" + 
			"    COALESCE(t2.tot, 0) AS rate_total,\r\n" + 
			"    t1.ret_per,\r\n" + 
			"    0 AS round_off,\r\n" + 
			"    ROUND(\r\n" + 
			"        COALESCE(t2.tot, 0) + COALESCE(ROUND(t2.cgst, 2),\r\n" + 
			"        0) + COALESCE(ROUND(t2.sgst, 2),\r\n" + 
			"        0)\r\n" + 
			"    ) AS total_amount\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.crn_id,\r\n" + 
			"        h.crn_no,\r\n" + 
			"        h.crn_date,\r\n" + 
			"        h.ex_int1 AS bill_id,\r\n" + 
			"        h.ex_varchar1 AS bill_no,\r\n" + 
			"        bh.bill_date,\r\n" + 
			"        bh.is_tally_sync AS e_way_bill_no,\r\n" + 
			"        bh.bill_date AS e_way_bill_date,\r\n" + 
			"        '' AS ship_to_customer_name,\r\n" + 
			"        '' AS ship_to_gst_no,\r\n" + 
			"        '' AS ship_to_address,\r\n" + 
			"        c.state,\r\n" + 
			"        c.state_code,\r\n" + 
			"        bh.ex_varchar3 AS customer_name,\r\n" + 
			"        bh.ex_varchar4 AS gst_no,\r\n" + 
			"        bh.ex_varchar5 AS address,\r\n" + 
			"        c.state AS ship_to_state,\r\n" + 
			"        c.state_code AS ship_to_state_code,\r\n" + 
			"        d.item_id,\r\n" + 
			"        i.item_name AS product_name,\r\n" + 
			"        0 AS part_no,\r\n" + 
			"        d.grn_gvn_qty AS qty,\r\n" + 
			"        s.item_uom AS unit,\r\n" + 
			"        d.hsn_code AS hsn,\r\n" + 
			"        d.cgst_per + d.sgst_per AS gst_per,\r\n" + 
			"        (d.taxable_amt / d.grn_gvn_qty) AS rate,\r\n" + 
			"        0 AS discount,\r\n" + 
			"        0 AS amount,\r\n" + 
			"        d.cgst_rs AS cgst,\r\n" + 
			"        d.sgst_rs AS sgst,\r\n" + 
			"        d.igst_rs AS igst,\r\n" + 
			"        0 AS other_ledger,\r\n" + 
			"        d.taxable_amt,\r\n" + 
			"        d.grn_type AS ret_per\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_header h,\r\n" + 
			"        t_credit_note_details d,\r\n" + 
			"        m_item i,\r\n" + 
			"        t_bill_header bh,\r\n" + 
			"        m_company c,\r\n" + 
			"        m_item_sup s\r\n" + 
			"    WHERE\r\n" + 
			"        d.del_status = 0 AND h.crn_id = d.crn_id AND i.id = d.item_id AND h.ex_int1 = bh.bill_no AND bh.del_status = 0 AND d.item_id = s.item_id AND c.comp_id = 1 AND h.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.crn_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.crn_id,\r\n" + 
			"        SUM(d.cgst_rs) AS cgst,\r\n" + 
			"        SUM(d.sgst_rs) AS sgst,\r\n" + 
			"        SUM(d.igst_rs) AS igst,\r\n" + 
			"        SUM(ROUND(d.taxable_amt, 2)) AS tot1,\r\n" + 
			"        ROUND(\r\n" + 
			"            SUM(\r\n" + 
			"                ROUND(d.grn_gvn_qty, 3) * ROUND(\r\n" + 
			"                    (d.taxable_amt / d.grn_gvn_qty),\r\n" + 
			"                    2\r\n" + 
			"                )\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS tot\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_header h,\r\n" + 
			"        t_credit_note_details d\r\n" + 
			"    WHERE\r\n" + 
			"        d.del_status = 0 AND h.crn_id = d.crn_id AND h.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.crn_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.crn_id = t2.crn_id\r\n" + 
			"UNION ALL\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t1.fr_id, t1.crn_id, t1.crn_no, DATE_FORMAT(t1.crn_date, '%d-%m-%Y') AS DATE,\r\n" + 
			"        t1.bill_no,\r\n" + 
			"        DATE_FORMAT(t1.bill_date, '%d-%m-%Y') AS bill_date,\r\n" + 
			"        t1.e_way_bill_no,\r\n" + 
			"        t1.e_way_bill_date,\r\n" + 
			"        COALESCE(t1.customer_name, '') AS customer_name,\r\n" + 
			"        COALESCE(t1.gst_no, '') AS gst_no,\r\n" + 
			"        COALESCE(t1.address, '') AS address,\r\n" + 
			"        t1.state,\r\n" + 
			"        t1.state_code,\r\n" + 
			"        t1.ship_to_customer_name,\r\n" + 
			"        t1.ship_to_gst_no,\r\n" + 
			"        t1.ship_to_address,\r\n" + 
			"        t1.ship_to_state,\r\n" + 
			"        t1.ship_to_state_code,\r\n" + 
			"        t1.product_name,\r\n" + 
			"        t1.part_no,\r\n" + 
			"        ROUND(t1.qty, 3) AS qty,\r\n" + 
			"        t1.unit,\r\n" + 
			"        t1.hsn,\r\n" + 
			"        t1.gst_per,\r\n" + 
			"        ROUND(t1.rate, 2) AS rate,\r\n" + 
			"        t1.discount,\r\n" + 
			"        0 AS amount,\r\n" + 
			"        COALESCE(ROUND(t2.cgst, 2),\r\n" + 
			"        0) AS cgst,\r\n" + 
			"        COALESCE(ROUND(t2.sgst, 2),\r\n" + 
			"        0) AS sgst,\r\n" + 
			"        COALESCE(ROUND(t2.igst, 2),\r\n" + 
			"        0) AS igst,\r\n" + 
			"        t1.other_ledger,\r\n" + 
			"        COALESCE(t2.tot, 0) AS rate_total,\r\n" + 
			"        COALESCE(t1.ret_per, 0) AS ret_per,\r\n" + 
			"        0 AS round_off,\r\n" + 
			"        ROUND(\r\n" + 
			"            COALESCE(t2.tot, 0) + COALESCE(ROUND(t2.cgst, 2),\r\n" + 
			"            0) + COALESCE(ROUND(t2.sgst, 2),\r\n" + 
			"            0)\r\n" + 
			"        ) AS total_amount\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            c.ex_int1 AS fr_id,\r\n" + 
			"            c.crn_no AS crn_id,\r\n" + 
			"            c.crn_invoice_no AS crn_no,\r\n" + 
			"            c.crn_date,\r\n" + 
			"            c.bill_no AS bill_id,\r\n" + 
			"            c.bill_invoice AS bill_no,\r\n" + 
			"            h.bill_date,\r\n" + 
			"            '' AS e_way_bill_no,\r\n" + 
			"            '' AS e_way_bill_date,\r\n" + 
			"            '' AS ship_to_customer_name,\r\n" + 
			"            '' AS ship_to_gst_no,\r\n" + 
			"            '' AS ship_to_address,\r\n" + 
			"            cp.state,\r\n" + 
			"            cp.state_code,\r\n" + 
			"            '' AS customer_name,\r\n" + 
			"            '' AS gst_no,\r\n" + 
			"            '' AS address,\r\n" + 
			"            cp.state AS ship_to_state,\r\n" + 
			"            cp.state_code AS ship_to_state_code,\r\n" + 
			"            i.item_name AS product_name,\r\n" + 
			"            c.item_id,\r\n" + 
			"            0 AS part_no,\r\n" + 
			"            c.crn_qty AS qty,\r\n" + 
			"            s.item_uom AS unit,\r\n" + 
			"            s.item_hsncd AS hsn,\r\n" + 
			"            c.cgst_per + c.sgst_per AS gst_per,\r\n" + 
			"            COALESCE(c.taxable_amt, 0) / COALESCE(c.crn_qty, 0) AS rate,\r\n" + 
			"            c.return_per AS ret_per,\r\n" + 
			"            0 AS discount,\r\n" + 
			"            0 AS amount,\r\n" + 
			"            c.cgst_amt AS cgst,\r\n" + 
			"            c.sgst_amt AS sgst,\r\n" + 
			"            c.igst_amt AS igst,\r\n" + 
			"            0 AS other_ledger,\r\n" + 
			"            d.taxable_amt,\r\n" + 
			"            d.disc_amt,\r\n" + 
			"            d.disc_per,\r\n" + 
			"            c.taxable_amt AS crn_taxable\r\n" + 
			"        FROM\r\n" + 
			"            t_credit_note_pos c,\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d,\r\n" + 
			"            m_company cp,\r\n" + 
			"            m_item i,\r\n" + 
			"            m_item_sup s,\r\n" + 
			"            m_franchisee f\r\n" + 
			"        WHERE\r\n" + 
			"            c.bill_no = h.sell_bill_no AND c.del_status = 0 AND h.del_status = 0 AND cp.comp_id = 1 AND c.item_id = i.id AND c.item_id = s.item_id AND f.fr_id = c.ex_int1 AND f.kg_1 = 1 AND h.sell_bill_no = d.sell_bill_no AND c.bill_detail_no = d.sell_bill_detail_no AND c.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    ) t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        c.crn_detail_no,\r\n" + 
			"        c.crn_no,\r\n" + 
			"        SUM(c.cgst_amt) AS cgst,\r\n" + 
			"        SUM(c.sgst_amt) AS sgst,\r\n" + 
			"        SUM(c.igst_amt) AS igst,\r\n" + 
			"        SUM(ROUND(c.taxable_amt, 2)) AS tot1,\r\n" + 
			"        ROUND(\r\n" + 
			"            SUM(\r\n" + 
			"                ROUND(c.crn_qty, 3) * ROUND((c.taxable_amt / c.crn_qty),\r\n" + 
			"                2)\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS tot\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos c\r\n" + 
			"    WHERE\r\n" + 
			"        c.del_status = 0 AND c.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        c.crn_no\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.crn_id = t2.crn_no)\r\n" + 
			"    ORDER BY\r\n" + 
			"        DATE,\r\n" + 
			"        crn_no",nativeQuery=true)
	List<CreditNoteInvoices> getTallyData(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	

}
