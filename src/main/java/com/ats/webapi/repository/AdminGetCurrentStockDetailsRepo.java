package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.AdminGetCurrentStockDetails;

public interface AdminGetCurrentStockDetailsRepo  extends JpaRepository<AdminGetCurrentStockDetails, Integer> {
	
	@Query(value = "SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.opening_stock_detail_id, 0) AS stock_detail_id,\r\n" + 
			"    a.fr_id,\r\n" + 
			"    a.id AS item_id,\r\n" + 
			"    a.item_name,\r\n" + 
			"    COALESCE(t1.reg_opening_stock, 0) AS reg_opening_stock,\r\n" + 
			"    COALESCE(t1.sp_opening_stock, 0) AS sp_opening_stock,\r\n" + 
			"    COALESCE(t2.reg, 0) AS reg_total_purchase,\r\n" + 
			"    COALESCE(t1.sp_total_purchase, 0) AS sp_total_purchase,\r\n" + 
			"    COALESCE(t3.grngvn, 0) AS reg_total_grn_gvn,\r\n" + 
			"    COALESCE(t4.sell_qty, 0) reg_total_sell,\r\n" + 
			"    COALESCE(t1.opening_stock_header_id, 0) AS stock_header_id,\r\n" + 
			"    (\r\n" + 
			"        (\r\n" + 
			"            COALESCE(t1.reg_opening_stock, 0) + COALESCE(t2.reg, 0) + COALESCE(t5.crn, 0)\r\n" + 
			"        ) -(\r\n" + 
			"            COALESCE(t3.grngvn, 0) + COALESCE(t4.sell_qty, 0)\r\n" + 
			"        )\r\n" + 
			"    ) AS current_reg_stock,\r\n" + 
			"    0 AS current_sp_stock,\r\n" + 
			"    0 AS re_order_qty,\r\n" + 
			"    COALESCE(t5.crn, 0) AS sell_credit_note\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        i.*,\r\n" + 
			"        f.fr_id\r\n" + 
			"    FROM\r\n" + 
			"        m_item i,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        i.id IN(:itemIds) AND f.fr_id IN(:frIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        i.id,\r\n" + 
			"        f.fr_id\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.*,\r\n" + 
			"        h.fr_id\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_opening_stock_detail d,\r\n" + 
			"        m_fr_opening_stock_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.opening_stock_header_id = d.opening_stock_header_id AND h.fr_id IN(:frIds) AND h.cat_id =:catId AND h.month =:month AND h.year =:year AND d.item_id IN(:itemIds)\r\n" + 
			") t1\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = a.id AND t1.fr_id = a.fr_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.bill_detail_no,\r\n" + 
			"        SUM(d.bill_qty) AS reg,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        d.item_id\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIds) AND h.status = 2 AND h.bill_no = d.bill_no AND d.item_id IN(:itemIds) AND h.is_dairy_mart = 1\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        d.item_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t2.fr_id AND a.id = t2.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        item_id,\r\n" + 
			"        fr_id,\r\n" + 
			"        SUM(t_grn_gvn.grn_gvn_qty) AS grngvn\r\n" + 
			"    FROM\r\n" + 
			"        t_grn_gvn\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frIds) AND item_id IN(:itemIds) AND grn_gvn_date BETWEEN :fromDate AND :toDate\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t3.fr_id AND a.id = t3.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.item_id,a.rm_id,\r\n" + 
			"        b.fr_id,\r\n" + 
			"        ROUND(\r\n" + 
			"            (\r\n" + 
			"                COALESCE(\r\n" + 
			"                    SUM(\r\n" + 
			"                        (b.sell_qty * a.rm_qty) / a.no_pieces_per_item\r\n" + 
			"                    ),\r\n" + 
			"                    0\r\n" + 
			"                )\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS sell_qty\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            item_id,\r\n" + 
			"            rm_id,\r\n" + 
			"            rm_qty,\r\n" + 
			"            no_pieces_per_item,\r\n" + 
			"            m_franchisee.fr_id\r\n" + 
			"        FROM\r\n" + 
			"            m_item_detail,\r\n" + 
			"            m_franchisee\r\n" + 
			"        WHERE\r\n" + 
			"            rm_id IN(:itemIds) AND m_item_detail.del_status = 0 AND m_franchisee.fr_id IN(:frIds)\r\n" + 
			"        GROUP BY\r\n" + 
			"            m_item_detail.item_id,\r\n" + 
			"            m_franchisee.fr_id\r\n" + 
			"    ) a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t_sell_bill_header.fr_id,\r\n" + 
			"        t_sell_bill_detail.item_id,\r\n" + 
			"        COALESCE(\r\n" + 
			"            SUM(\r\n" + 
			"                CASE WHEN bill_stock_type = 1 THEN qty ELSE 0\r\n" + 
			"            END\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			") AS sell_qty\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_detail,\r\n" + 
			"    t_sell_bill_header\r\n" + 
			"WHERE\r\n" + 
			"    t_sell_bill_header.fr_id IN(:frIds) AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.item_id IN(\r\n" + 
			"    SELECT\r\n" + 
			"        item_id\r\n" + 
			"    FROM\r\n" + 
			"        m_item_detail\r\n" + 
			"    WHERE\r\n" + 
			"        rm_id IN(:itemIds) AND del_status = 0\r\n" + 
			") AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no\r\n" + 
			"GROUP BY\r\n" + 
			"    t_sell_bill_header.fr_id,\r\n" + 
			"    t_sell_bill_detail.item_id\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"    a.item_id = b.item_id AND a.fr_id = b.fr_id\r\n" + 
			"GROUP BY\r\n" + 
			"    b.fr_id,\r\n" + 
			"    a.rm_id\r\n" + 
			"    ) t4\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t4.fr_id AND a.id = t4.rm_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        p.ex_int1 AS fr_id,\r\n" + 
			"        p.item_id,\r\n" + 
			"        COALESCE((SUM(p.crn_qty)),\r\n" + 
			"        0) AS crn\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos p\r\n" + 
			"    WHERE\r\n" + 
			"        p.is_stockable = 1 AND p.ex_int1 IN(:frIds) AND p.crn_date BETWEEN :fromDate AND :toDate AND p.item_id IN(:itemIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        p.ex_int1,\r\n" + 
			"        p.item_id\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t5.fr_id AND a.id = t5.item_id", nativeQuery = true)
	List<AdminGetCurrentStockDetails> getAdminCurrStock(@Param("catId") int catId, @Param("month") int month, @Param("year") int year ,@Param("frIds") List<Integer> frIds, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("itemIds") List<Integer> itemIds);
	
	
	@Query(value = "SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.opening_stock_detail_id, 0) AS stock_detail_id,\r\n" + 
			"    a.fr_id,\r\n" + 
			"    a.id AS item_id,\r\n" + 
			"    a.item_name,\r\n" + 
			"    COALESCE(t1.reg_opening_stock, 0) AS reg_opening_stock,\r\n" + 
			"    COALESCE(t1.sp_opening_stock, 0) AS sp_opening_stock,\r\n" + 
			"    COALESCE(t2.reg, 0) AS reg_total_purchase,\r\n" + 
			"    COALESCE(t1.sp_total_purchase, 0) AS sp_total_purchase,\r\n" + 
			"    COALESCE(t3.grngvn, 0) AS reg_total_grn_gvn,\r\n" + 
			"    COALESCE(t4.sell_qty, 0) reg_total_sell,\r\n" + 
			"    COALESCE(t1.opening_stock_header_id, 0) AS stock_header_id,\r\n" + 
			"    (\r\n" + 
			"        (\r\n" + 
			"            COALESCE(t1.reg_opening_stock, 0) + COALESCE(t2.reg, 0) + COALESCE(t5.crn, 0)\r\n" + 
			"        ) -(\r\n" + 
			"            COALESCE(t3.grngvn, 0) + COALESCE(t4.sell_qty, 0)\r\n" + 
			"        )\r\n" + 
			"    ) AS current_reg_stock,\r\n" + 
			"    0 AS current_sp_stock,\r\n" + 
			"    0 AS re_order_qty,\r\n" + 
			"    COALESCE(t5.crn, 0) AS sell_credit_note\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        i.*,\r\n" + 
			"        f.fr_id\r\n" + 
			"    FROM\r\n" + 
			"        m_item i,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        i.id IN(:itemIds) AND f.fr_id IN(:frIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        i.id,\r\n" + 
			"        f.fr_id\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.*,\r\n" + 
			"        h.fr_id\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_opening_stock_detail d,\r\n" + 
			"        m_fr_opening_stock_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.opening_stock_header_id = d.opening_stock_header_id AND h.fr_id IN(:frIds) AND h.cat_id =:catId AND h.month =:month AND h.year =:year AND d.item_id IN(:itemIds)\r\n" + 
			") t1\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = a.id AND t1.fr_id = a.fr_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.bill_detail_no,\r\n" + 
			"        SUM(d.bill_qty) AS reg,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        d.item_id\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIds) AND h.status = 2 AND h.bill_no = d.bill_no AND d.item_id IN(:itemIds) AND h.is_dairy_mart = 1\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id,\r\n" + 
			"        d.item_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t2.fr_id AND a.id = t2.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        item_id,\r\n" + 
			"        fr_id,\r\n" + 
			"        SUM(t_grn_gvn.grn_gvn_qty) AS grngvn\r\n" + 
			"    FROM\r\n" + 
			"        t_grn_gvn\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frIds) AND item_id IN(:itemIds) AND grn_gvn_date BETWEEN :fromDate AND :toDate\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t3.fr_id AND a.id = t3.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.item_id,\r\n" + 
			"        b.fr_id,\r\n" + 
			"        ROUND(\r\n" + 
			"            (\r\n" + 
			"                COALESCE(\r\n" + 
			"                    SUM(\r\n" + 
			"                        (b.sell_qty * a.rm_qty) / a.no_pieces_per_item\r\n" + 
			"                    ),\r\n" + 
			"                    0\r\n" + 
			"                )\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS sell_qty\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            item_id,\r\n" + 
			"            rm_id,\r\n" + 
			"            rm_qty,\r\n" + 
			"            no_pieces_per_item,\r\n" + 
			"            m_franchisee.fr_id\r\n" + 
			"        FROM\r\n" + 
			"            m_item_detail,\r\n" + 
			"            m_franchisee\r\n" + 
			"        WHERE\r\n" + 
			"            rm_id IN(:itemIds) AND m_item_detail.del_status = 0 AND m_franchisee.fr_id IN(:frIds)\r\n" + 
			"        GROUP BY\r\n" + 
			"            m_item_detail.item_id,\r\n" + 
			"            m_franchisee.fr_id\r\n" + 
			"    ) a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t_sell_bill_header.fr_id,\r\n" + 
			"        t_sell_bill_detail.item_id,\r\n" + 
			"        COALESCE(\r\n" + 
			"            SUM(\r\n" + 
			"                CASE WHEN bill_stock_type = 1 THEN qty ELSE 0\r\n" + 
			"            END\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			") AS sell_qty\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_detail,\r\n" + 
			"    t_sell_bill_header\r\n" + 
			"WHERE\r\n" + 
			"    t_sell_bill_header.fr_id IN(:frIds) AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.item_id IN(\r\n" + 
			"    SELECT\r\n" + 
			"        item_id\r\n" + 
			"    FROM\r\n" + 
			"        m_item_detail\r\n" + 
			"    WHERE\r\n" + 
			"        rm_id IN(:itemIds) AND del_status = 0\r\n" + 
			") AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no\r\n" + 
			"GROUP BY\r\n" + 
			"    t_sell_bill_header.fr_id,\r\n" + 
			"    t_sell_bill_detail.item_id\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"    a.item_id = b.item_id AND a.fr_id = b.fr_id\r\n" + 
			"GROUP BY\r\n" + 
			"    b.fr_id,\r\n" + 
			"    a.item_id\r\n" + 
			"    ) t4\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t4.fr_id AND a.id = t4.item_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        p.ex_int1 AS fr_id,\r\n" + 
			"        p.item_id,\r\n" + 
			"        COALESCE((SUM(p.crn_qty)),\r\n" + 
			"        0) AS crn\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos p\r\n" + 
			"    WHERE\r\n" + 
			"        p.is_stockable = 1 AND p.ex_int1 IN(:frIds) AND p.crn_date BETWEEN :fromDate AND :toDate AND p.item_id IN(:itemIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        p.ex_int1,\r\n" + 
			"        p.item_id\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = t5.fr_id AND a.id = t5.item_id", nativeQuery = true)
	List<AdminGetCurrentStockDetails> getAdminCurrStock(@Param("catId") int catId);
	
	
	
	

}
