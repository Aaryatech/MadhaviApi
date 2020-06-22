package com.ats.webapi.repository.tally;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
			"     COALESCE(b.cgst, 0) AS cgst,\r\n" + 
			"    COALESCE(b.sgst, 0) AS sgst,\r\n" + 
			"    COALESCE(b.igst, 0) AS igst,\r\n" + 
			"    a.other_ledger,\r\n" + 
			"    0 as rate_total,\r\n" +
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
			"        SUM(d.grand_total) AS grand_total," +
			" SUM(d.cgst_rs) AS cgst,\r\n" + 
			"        SUM(d.sgst_rs) AS sgst,\r\n" + 
			"        SUM(d.igst_rs) AS igst "+
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
	
	
	@Query(value="SELECT\r\n" + 
			"    UUID() AS id, a.fr_id, a.bill_no, DATE_FORMAT(a.date, '%d-%m-%Y') AS DATE,\r\n" + 
			"    a.e_way_bill_no,\r\n" + 
			"    DATE_FORMAT(a.e_way_bill_date, '%d-%m-%Y') AS e_way_bill_date,\r\n" + 
			"    COALESCE(a.customer_name, '') AS customer_name,\r\n" + 
			"    COALESCE(a.gst_no, '') AS gst_no,\r\n" + 
			"    COALESCE(a.address, '') AS address,\r\n" + 
			"    a.state,\r\n" + 
			"    a.state_code,\r\n" + 
			"    COALESCE(a.ship_to_customer_name, '') AS ship_to_customer_name,\r\n" + 
			"    COALESCE(a.ship_to_gst_no, '') AS ship_to_gst_no,\r\n" + 
			"    COALESCE(a.ship_to_address, '') AS ship_to_address,\r\n" + 
			"    a.ship_to_state,\r\n" + 
			"    a.ship_to_state_code,\r\n" + 
			"    a.product_name,\r\n" + 
			"    a.part_no,\r\n" + 
			"    ROUND(a.qty, 3) AS qty,\r\n" + 
			"    a.unit,\r\n" + 
			"    a.hsn,\r\n" + 
			"    a.gst_per,\r\n" + 
			"    ROUND(a.rate, 2) AS rate,\r\n" + 
			"    a.discount,\r\n" + 
			"    ROUND(b.amount, 2) AS amount,\r\n" + 
			"    COALESCE(ROUND(b.cgst, 2),\r\n" + 
			"    0) AS cgst,\r\n" + 
			"    COALESCE(ROUND(b.sgst, 2),\r\n" + 
			"    0) AS sgst,\r\n" + 
			"    COALESCE(ROUND(b.igst, 2),\r\n" + 
			"    0) AS igst,\r\n" + 
			"    a.other_ledger,\r\n" + 
			"    COALESCE(b.tot, 0) AS rate_total,\r\n" + 
			"    0 AS round_off,\r\n" + 
			"    ROUND(\r\n" + 
			"        COALESCE(b.tot, 0) + COALESCE(ROUND(b.cgst, 2),\r\n" + 
			"        0) + COALESCE(ROUND(b.sgst, 2),\r\n" + 
			"        0) - COALESCE(ROUND(b.amount, 2),\r\n" + 
			"        0),\r\n" + 
			"        2\r\n" + 
			"    ) AS total_amount\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_no AS bill_id,\r\n" + 
			"        h.invoice_no AS bill_no,\r\n" + 
			"        h.bill_date AS DATE,\r\n" + 
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
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d,\r\n" + 
			"        m_company c,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_item_sup sup\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_no = d.bill_no AND h.del_status = 0 AND d.del_status = 0 AND d.item_id = i.id AND i.id = sup.item_id AND c.comp_id = 1 AND h.tally_sync = 0 AND h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.bill_no\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.bill_no,\r\n" + 
			"        h.invoice_no,\r\n" + 
			"        SUM(d.grand_total) AS grand_total,\r\n" + 
			"        SUM(d.cgst_rs) AS cgst,\r\n" + 
			"        SUM(d.sgst_rs) AS sgst,\r\n" + 
			"        SUM(d.igst_rs) AS igst,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(\r\n" + 
			"                ROUND(d.bill_qty, 3) * ROUND(d.base_rate, 2),\r\n" + 
			"                2\r\n" + 
			"            )\r\n" + 
			"        ) AS tot,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(\r\n" + 
			"                ROUND(\r\n" + 
			"                    ROUND(d.bill_qty, 3) * ROUND(d.base_rate, 2),\r\n" + 
			"                    2\r\n" + 
			"                ) *(d.disc_per / 100),\r\n" + 
			"                2\r\n" + 
			"            )\r\n" + 
			"        ) AS amount\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_detail d,\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_no = d.bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.bill_no\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"    a.bill_id = b.bill_no\r\n" + 
			"UNION ALL\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            UUID() AS id, t1.fr_id, t5.invoice AS bill_no, DATE_FORMAT(t1.date, '%d-%m-%Y') AS DATE,\r\n" + 
			"            '' AS e_way_bill_no,\r\n" + 
			"            '' AS e_way_bill_date,\r\n" + 
			"            'Cash' AS ship_to_customer_name,\r\n" + 
			"            '' AS ship_to_gst_no,\r\n" + 
			"            '' AS ship_to_address,\r\n" + 
			"            t3.state,\r\n" + 
			"            t3.state_code,\r\n" + 
			"            'Cash' AS customer_name,\r\n" + 
			"            '' AS gst_no,\r\n" + 
			"            '' AS address,\r\n" + 
			"            t3.state AS ship_to_state,\r\n" + 
			"            t3.state_code AS ship_to_state_code,\r\n" + 
			"            t2.item_name AS product_name,\r\n" + 
			"            0 AS part_no,\r\n" + 
			"            COALESCE(ROUND(t1.qty, 3),\r\n" + 
			"            0) AS qty,\r\n" + 
			"            t4.item_uom AS unit,\r\n" + 
			"            t1.hsn,\r\n" + 
			"            COALESCE(t1.gst_per, 0) AS gst_per,\r\n" + 
			"            COALESCE(ROUND(t1.rate, 2),\r\n" + 
			"            0) AS rate,\r\n" + 
			"            COALESCE(t1.discount, 0) AS discount,\r\n" + 
			"            COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"            0) AS amount,\r\n" + 
			"            COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"            0) AS cgst,\r\n" + 
			"            COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"            0) AS sgst,\r\n" + 
			"            COALESCE(ROUND(t6.igst_total, 2),\r\n" + 
			"            0) AS igst,\r\n" + 
			"            0 AS other_ledger,\r\n" + 
			"            COALESCE(t7.tot, 0) AS rate_total,\r\n" + 
			"            0 AS round_off,\r\n" + 
			"            ROUND(\r\n" + 
			"                COALESCE(t7.tot, 0) + COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"                0) + COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"                0) - COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"                0),\r\n" + 
			"                2\r\n" + 
			"            ) AS total_amount\r\n" + 
			"        FROM\r\n" + 
			"            (\r\n" + 
			"            SELECT\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.sell_bill_no AS bill_id,\r\n" + 
			"                h.invoice_no AS bill_no,\r\n" + 
			"                h.bill_date AS DATE,\r\n" + 
			"                '' AS e_way_bill_no,\r\n" + 
			"                '' AS e_way_bill_date,\r\n" + 
			"                '' AS ship_to_customer_name,\r\n" + 
			"                '' AS ship_to_gst_no,\r\n" + 
			"                '' AS ship_to_address,\r\n" + 
			"                '' AS customer_name,\r\n" + 
			"                '' AS gst_no,\r\n" + 
			"                '' AS address,\r\n" + 
			"                0 AS part_no,\r\n" + 
			"                SUM(d.qty) AS qty,\r\n" + 
			"                d.ext_var1 AS hsn,\r\n" + 
			"                d.cgst_per + d.sgst_per AS gst_per,\r\n" + 
			"                ROUND(d.mrp_base_rate, 2) AS rate,\r\n" + 
			"                0 AS discount,\r\n" + 
			"                SUM(d.disc_amt) AS amount,\r\n" + 
			"                SUM(d.cgst_rs) AS cgst,\r\n" + 
			"                SUM(d.sgst_rs) AS sgst,\r\n" + 
			"                SUM(d.igst_rs) AS igst,\r\n" + 
			"                0 AS other_ledger,\r\n" + 
			"                d.item_id,\r\n" + 
			"                1 AS temp\r\n" + 
			"            FROM\r\n" + 
			"                t_sell_bill_header h,\r\n" + 
			"                t_sell_bill_detail d,\r\n" + 
			"                m_franchisee f\r\n" + 
			"            WHERE\r\n" + 
			"                h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.fr_id = f.fr_id AND f.del_status = 0 AND f.kg_1 = 1 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.payment_mode IN(1) AND h.status != 3\r\n" + 
			"            GROUP BY\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.bill_date,\r\n" + 
			"                d.item_id\r\n" + 
			"            ORDER BY\r\n" + 
			"                h.bill_date,\r\n" + 
			"                h.fr_id\r\n" + 
			"        ) t1\r\n" + 
			"    LEFT JOIN(\r\n" + 
			"        SELECT\r\n" + 
			"            id,\r\n" + 
			"            item_name\r\n" + 
			"        FROM\r\n" + 
			"            m_item\r\n" + 
			"    ) t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        comp_id,\r\n" + 
			"        state,\r\n" + 
			"        state_code\r\n" + 
			"    FROM\r\n" + 
			"        m_company\r\n" + 
			"    WHERE\r\n" + 
			"        comp_id = 1\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.temp = 1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        s.item_id,\r\n" + 
			"        s.item_uom\r\n" + 
			"    FROM\r\n" + 
			"        m_item_sup s\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t4.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.*,\r\n" + 
			"        CONCAT(\r\n" + 
			"            MIN(h.invoice_no),\r\n" + 
			"            '-',\r\n" + 
			"            MAX(h.invoice_no),\r\n" + 
			"            '-C'\r\n" + 
			"        ) AS invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND f.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.bill_date\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t5.fr_id AND t1.date = t5.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        SUM(\r\n" + 
			"            (sd.qty * sd.mrp_base_rate) +(\r\n" + 
			"                sd.cgst_rs + sd.sgst_rs - sd.disc_amt\r\n" + 
			"            )\r\n" + 
			"        ) AS grand_total,\r\n" + 
			"        SUM(sd.cgst_rs) AS cgst_total,\r\n" + 
			"        SUM(sd.sgst_rs) AS sgst_total,\r\n" + 
			"        SUM(sd.igst_rs) AS igst_total,\r\n" + 
			"        SUM(sd.disc_amt) AS amount\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail sd\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = sd.sell_bill_no AND sd.del_status = 0 AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.payment_mode IN(1) AND h.status != 3\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t6.fr_id AND t1.date = t6.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.fr_id,\r\n" + 
			"        t.bill_date,\r\n" + 
			"        SUM(ROUND(r * q, 2)) AS tot\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.fr_id,\r\n" + 
			"            h.bill_date,\r\n" + 
			"            CAST(\r\n" + 
			"                d.mrp_base_rate AS DECIMAL(10, 2)\r\n" + 
			"            ) AS r,\r\n" + 
			"            CAST(SUM(qty) AS DECIMAL(10, 3)) AS q\r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d\r\n" + 
			"        WHERE\r\n" + 
			"            h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND d.del_status = 0 AND h.payment_mode IN(1) AND h.status != 3\r\n" + 
			"        GROUP BY\r\n" + 
			"            h.bill_date,\r\n" + 
			"            d.item_id,\r\n" + 
			"            h.fr_id\r\n" + 
			"    ) t\r\n" + 
			"GROUP BY\r\n" + 
			"    fr_id,\r\n" + 
			"    bill_date\r\n" + 
			") t7\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t7.fr_id AND t1.date = t7.bill_date) t\r\n" + 
			"        ORDER BY\r\n" + 
			"            DATE,\r\n" + 
			"            fr_id\r\n" + 
			"    )\r\n" + 
			"UNION ALL\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            UUID() AS id, t1.fr_id, t5.invoice AS bill_no, DATE_FORMAT(t1.date, '%d-%m-%Y') AS DATE,\r\n" + 
			"            '' AS e_way_bill_no,\r\n" + 
			"            '' AS e_way_bill_date,\r\n" + 
			"            'E-Payment' AS ship_to_customer_name,\r\n" + 
			"            '' AS ship_to_gst_no,\r\n" + 
			"            '' AS ship_to_address,\r\n" + 
			"            t3.state,\r\n" + 
			"            t3.state_code,\r\n" + 
			"            'E-Payment' AS customer_name,\r\n" + 
			"            '' AS gst_no,\r\n" + 
			"            '' AS address,\r\n" + 
			"            t3.state AS ship_to_state,\r\n" + 
			"            t3.state_code AS ship_to_state_code,\r\n" + 
			"            t2.item_name AS product_name,\r\n" + 
			"            0 AS part_no,\r\n" + 
			"            COALESCE(ROUND(t1.qty, 3),\r\n" + 
			"            0) AS qty,\r\n" + 
			"            t4.item_uom AS unit,\r\n" + 
			"            t1.hsn,\r\n" + 
			"            COALESCE(t1.gst_per, 0) AS gst_per,\r\n" + 
			"            COALESCE(ROUND(t1.rate, 2),\r\n" + 
			"            0) AS rate,\r\n" + 
			"            COALESCE(t1.discount, 0) AS discount,\r\n" + 
			"            COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"            0) AS amount,\r\n" + 
			"            COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"            0) AS cgst,\r\n" + 
			"            COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"            0) AS sgst,\r\n" + 
			"            COALESCE(ROUND(t6.igst_total, 2),\r\n" + 
			"            0) AS igst,\r\n" + 
			"            0 AS other_ledger,\r\n" + 
			"            COALESCE(t7.tot, 0) AS rate_total,\r\n" + 
			"            0 AS round_off,\r\n" + 
			"            ROUND(\r\n" + 
			"                COALESCE(t7.tot, 0) + COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"                0) + COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"                0) - COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"                0),\r\n" + 
			"                2\r\n" + 
			"            ) AS total_amount\r\n" + 
			"        FROM\r\n" + 
			"            (\r\n" + 
			"            SELECT\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.sell_bill_no AS bill_id,\r\n" + 
			"                h.invoice_no AS bill_no,\r\n" + 
			"                h.bill_date AS DATE,\r\n" + 
			"                '' AS e_way_bill_no,\r\n" + 
			"                '' AS e_way_bill_date,\r\n" + 
			"                '' AS ship_to_customer_name,\r\n" + 
			"                '' AS ship_to_gst_no,\r\n" + 
			"                '' AS ship_to_address,\r\n" + 
			"                '' AS customer_name,\r\n" + 
			"                '' AS gst_no,\r\n" + 
			"                '' AS address,\r\n" + 
			"                0 AS part_no,\r\n" + 
			"                SUM(d.qty) AS qty,\r\n" + 
			"                d.ext_var1 AS hsn,\r\n" + 
			"                d.cgst_per + d.sgst_per AS gst_per,\r\n" + 
			"                ROUND(d.mrp_base_rate, 2) AS rate,\r\n" + 
			"                0 AS discount,\r\n" + 
			"                SUM(d.disc_amt) AS amount,\r\n" + 
			"                SUM(d.cgst_rs) AS cgst,\r\n" + 
			"                SUM(d.sgst_rs) AS sgst,\r\n" + 
			"                SUM(d.igst_rs) AS igst,\r\n" + 
			"                0 AS other_ledger,\r\n" + 
			"                d.item_id,\r\n" + 
			"                1 AS temp\r\n" + 
			"            FROM\r\n" + 
			"                t_sell_bill_header h,\r\n" + 
			"                t_sell_bill_detail d,\r\n" + 
			"                m_franchisee f\r\n" + 
			"            WHERE\r\n" + 
			"                h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.fr_id = f.fr_id AND f.del_status = 0 AND f.kg_1 = 1 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.payment_mode IN(2, 3) AND h.status != 3\r\n" + 
			"            GROUP BY\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.bill_date,\r\n" + 
			"                d.item_id\r\n" + 
			"            ORDER BY\r\n" + 
			"                h.bill_date,\r\n" + 
			"                h.fr_id\r\n" + 
			"        ) t1\r\n" + 
			"    LEFT JOIN(\r\n" + 
			"        SELECT\r\n" + 
			"            id,\r\n" + 
			"            item_name\r\n" + 
			"        FROM\r\n" + 
			"            m_item\r\n" + 
			"    ) t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        comp_id,\r\n" + 
			"        state,\r\n" + 
			"        state_code\r\n" + 
			"    FROM\r\n" + 
			"        m_company\r\n" + 
			"    WHERE\r\n" + 
			"        comp_id = 1\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.temp = 1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        s.item_id,\r\n" + 
			"        s.item_uom\r\n" + 
			"    FROM\r\n" + 
			"        m_item_sup s\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t4.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.*,\r\n" + 
			"        CONCAT(\r\n" + 
			"            MIN(h.invoice_no),\r\n" + 
			"            '-',\r\n" + 
			"            MAX(h.invoice_no),\r\n" + 
			"            '-E'\r\n" + 
			"        ) AS invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND f.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.bill_date\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t5.fr_id AND t1.date = t5.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        SUM(\r\n" + 
			"            (sd.qty * sd.mrp_base_rate) +(\r\n" + 
			"                sd.cgst_rs + sd.sgst_rs - sd.disc_amt\r\n" + 
			"            )\r\n" + 
			"        ) AS grand_total,\r\n" + 
			"        SUM(sd.cgst_rs) AS cgst_total,\r\n" + 
			"        SUM(sd.sgst_rs) AS sgst_total,\r\n" + 
			"        SUM(sd.igst_rs) AS igst_total,\r\n" + 
			"        SUM(sd.disc_amt) AS amount\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail sd\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = sd.sell_bill_no AND sd.del_status = 0 AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.payment_mode IN(2, 3) AND h.status != 3\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t6.fr_id AND t1.date = t6.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.fr_id,\r\n" + 
			"        t.bill_date,\r\n" + 
			"        SUM(ROUND(r * q, 2)) AS tot\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.fr_id,\r\n" + 
			"            h.bill_date,\r\n" + 
			"            CAST(\r\n" + 
			"                d.mrp_base_rate AS DECIMAL(10, 2)\r\n" + 
			"            ) AS r,\r\n" + 
			"            CAST(SUM(qty) AS DECIMAL(10, 3)) AS q\r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d\r\n" + 
			"        WHERE\r\n" + 
			"            h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND d.del_status = 0 AND h.payment_mode IN(2, 3) AND h.status != 3\r\n" + 
			"        GROUP BY\r\n" + 
			"            h.bill_date,\r\n" + 
			"            d.item_id,\r\n" + 
			"            h.fr_id\r\n" + 
			"    ) t\r\n" + 
			"GROUP BY\r\n" + 
			"    fr_id,\r\n" + 
			"    bill_date\r\n" + 
			") t7\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t7.fr_id AND t1.date = t7.bill_date) t\r\n" + 
			"        ORDER BY\r\n" + 
			"            DATE,\r\n" + 
			"            fr_id\r\n" + 
			"    )\r\n" + 
			"UNION ALL\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            UUID() AS id, t1.fr_id, t5.invoice AS bill_no, DATE_FORMAT(t1.date, '%d-%m-%Y') AS DATE,\r\n" + 
			"            '' AS e_way_bill_no,\r\n" + 
			"            '' AS e_way_bill_date,\r\n" + 
			"            '' AS ship_to_customer_name,\r\n" + 
			"            '' AS ship_to_gst_no,\r\n" + 
			"            '' AS ship_to_address,\r\n" + 
			"            t3.state,\r\n" + 
			"            t3.state_code,\r\n" + 
			"            '' AS customer_name,\r\n" + 
			"            '' AS gst_no,\r\n" + 
			"            '' AS address,\r\n" + 
			"            t3.state AS ship_to_state,\r\n" + 
			"            t3.state_code AS ship_to_state_code,\r\n" + 
			"            t2.item_name AS product_name,\r\n" + 
			"            0 AS part_no,\r\n" + 
			"            COALESCE(ROUND(t1.qty, 3),\r\n" + 
			"            0) AS qty,\r\n" + 
			"            t4.item_uom AS unit,\r\n" + 
			"            t1.hsn,\r\n" + 
			"            COALESCE(t1.gst_per, 0) AS gst_per,\r\n" + 
			"            COALESCE(ROUND(t1.rate, 2),\r\n" + 
			"            0) AS rate,\r\n" + 
			"            COALESCE(t1.discount, 0) AS discount,\r\n" + 
			"            COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"            0) AS amount,\r\n" + 
			"            COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"            0) AS cgst,\r\n" + 
			"            COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"            0) AS sgst,\r\n" + 
			"            COALESCE(ROUND(t6.igst_total, 2),\r\n" + 
			"            0) AS igst,\r\n" + 
			"            0 AS other_ledger,\r\n" + 
			"            COALESCE(t7.tot, 0) AS rate_total,\r\n" + 
			"            0 AS round_off,\r\n" + 
			"            ROUND(\r\n" + 
			"                COALESCE(t7.tot, 0) + COALESCE(ROUND(t6.cgst_total, 2),\r\n" + 
			"                0) + COALESCE(ROUND(t6.sgst_total, 2),\r\n" + 
			"                0) - COALESCE(ROUND(t6.amount, 2),\r\n" + 
			"                0),\r\n" + 
			"                2\r\n" + 
			"            ) AS total_amount\r\n" + 
			"        FROM\r\n" + 
			"            (\r\n" + 
			"            SELECT\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.sell_bill_no AS bill_id,\r\n" + 
			"                h.invoice_no AS bill_no,\r\n" + 
			"                h.bill_date AS DATE,\r\n" + 
			"                '' AS e_way_bill_no,\r\n" + 
			"                '' AS e_way_bill_date,\r\n" + 
			"                'Credit' AS ship_to_customer_name,\r\n" + 
			"                '' AS ship_to_gst_no,\r\n" + 
			"                '' AS ship_to_address,\r\n" + 
			"                'Credit' AS customer_name,\r\n" + 
			"                '' AS gst_no,\r\n" + 
			"                '' AS address,\r\n" + 
			"                0 AS part_no,\r\n" + 
			"                SUM(d.qty) AS qty,\r\n" + 
			"                d.ext_var1 AS hsn,\r\n" + 
			"                d.cgst_per + d.sgst_per AS gst_per,\r\n" + 
			"                ROUND(d.mrp_base_rate, 2) AS rate,\r\n" + 
			"                0 AS discount,\r\n" + 
			"                SUM(d.disc_amt) AS amount,\r\n" + 
			"                SUM(d.cgst_rs) AS cgst,\r\n" + 
			"                SUM(d.sgst_rs) AS sgst,\r\n" + 
			"                SUM(d.igst_rs) AS igst,\r\n" + 
			"                0 AS other_ledger,\r\n" + 
			"                d.item_id,\r\n" + 
			"                1 AS temp\r\n" + 
			"            FROM\r\n" + 
			"                t_sell_bill_header h,\r\n" + 
			"                t_sell_bill_detail d,\r\n" + 
			"                m_franchisee f\r\n" + 
			"            WHERE\r\n" + 
			"                h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.fr_id = f.fr_id AND f.del_status = 0 AND f.kg_1 = 1 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.status = 3\r\n" + 
			"            GROUP BY\r\n" + 
			"                h.fr_id,\r\n" + 
			"                h.bill_date,\r\n" + 
			"                d.item_id\r\n" + 
			"            ORDER BY\r\n" + 
			"                h.bill_date,\r\n" + 
			"                h.fr_id\r\n" + 
			"        ) t1\r\n" + 
			"    LEFT JOIN(\r\n" + 
			"        SELECT\r\n" + 
			"            id,\r\n" + 
			"            item_name\r\n" + 
			"        FROM\r\n" + 
			"            m_item\r\n" + 
			"    ) t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        comp_id,\r\n" + 
			"        state,\r\n" + 
			"        state_code\r\n" + 
			"    FROM\r\n" + 
			"        m_company\r\n" + 
			"    WHERE\r\n" + 
			"        comp_id = 1\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.temp = 1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        s.item_id,\r\n" + 
			"        s.item_uom\r\n" + 
			"    FROM\r\n" + 
			"        m_item_sup s\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t4.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.*,\r\n" + 
			"        CONCAT(\r\n" + 
			"            MIN(h.invoice_no),\r\n" + 
			"            '-',\r\n" + 
			"            MAX(h.invoice_no),\r\n" + 
			"            '-P'\r\n" + 
			"        ) AS invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND d.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND f.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        h.bill_date\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t5.fr_id AND t1.date = t5.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        SUM(\r\n" + 
			"            (sd.qty * sd.mrp_base_rate) +(\r\n" + 
			"                sd.cgst_rs + sd.sgst_rs - sd.disc_amt\r\n" + 
			"            )\r\n" + 
			"        ) AS grand_total,\r\n" + 
			"        SUM(sd.cgst_rs) AS cgst_total,\r\n" + 
			"        SUM(sd.sgst_rs) AS sgst_total,\r\n" + 
			"        SUM(sd.igst_rs) AS igst_total,\r\n" + 
			"        SUM(sd.disc_amt) AS amount\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        t_sell_bill_detail sd\r\n" + 
			"    WHERE\r\n" + 
			"        h.sell_bill_no = sd.sell_bill_no AND sd.del_status = 0 AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.status = 3\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.bill_date\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t6.fr_id AND t1.date = t6.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.fr_id,\r\n" + 
			"        t.bill_date,\r\n" + 
			"        SUM(ROUND(r * q, 2)) AS tot\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.fr_id,\r\n" + 
			"            h.bill_date,\r\n" + 
			"            CAST(\r\n" + 
			"                d.mrp_base_rate AS DECIMAL(10, 2)\r\n" + 
			"            ) AS r,\r\n" + 
			"            CAST(SUM(qty) AS DECIMAL(10, 3)) AS q\r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d\r\n" + 
			"        WHERE\r\n" + 
			"            h.sell_bill_no = d.sell_bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND d.del_status = 0 AND h.status = 3\r\n" + 
			"        GROUP BY\r\n" + 
			"            h.bill_date,\r\n" + 
			"            d.item_id,\r\n" + 
			"            h.fr_id\r\n" + 
			"    ) t\r\n" + 
			"GROUP BY\r\n" + 
			"    fr_id,\r\n" + 
			"    bill_date\r\n" + 
			") t7\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t7.fr_id AND t1.date = t7.bill_date) t\r\n" + 
			"        ORDER BY\r\n" + 
			"            DATE,\r\n" + 
			"            fr_id\r\n" + 
			"    )\r\n" + 
			"ORDER BY\r\n" + 
			"    fr_id,\r\n" + 
			"    bill_no,\r\n" + 
			"    DATE\r\n" + 
			"DESC\r\n" + 
			"    ",nativeQuery=true)
	List<SalesInvoices> getTallySyncDataByDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
