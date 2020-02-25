package com.ats.webapi.repository.salesreturnreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesvaluereport.SalesReturnValueDao;

public interface SalesReturnValueDaoRepository extends JpaRepository<SalesReturnValueDao, Integer>{

	@Query(value="SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS grand_total\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.bill_no = t_bill_detail.bill_no  AND m_item.item_grp2 = m_cat_sub.sub_cat_id AND m_item.id = t_bill_detail.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grand_total,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS grn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_credit_note_header.is_grn = 0  AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"     m_cat_sub.del_status = 0\n" + 
			"UNION ALL\n" + 
			"SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    m_cat_sub.sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS bill_qty\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no  AND m_sp_cake.sp_id = t_bill_detail.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS bill_qty,\n" + 
			"    0 AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 0 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_sp_cake.sp_id = t_credit_note_details.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"     m_cat_sub.del_status = 0",nativeQuery=true)
	List<SalesReturnValueDao> getSalesReturnValueReport12(@Param("month")String month,@Param("temp") List<Integer> temp);

	
	//Anmol--25-02-2020---
	@Query(value="SELECT\n" + 
			"    t1.id,\n" + 
			"    t1.sub_cat_id,\n" + 
			"    COALESCE((t2.grand_total),\n" + 
			"    0) AS grand_total,\n" + 
			"    COALESCE((t3.grn_qty),\n" + 
			"    0) AS grn_qty,\n" + 
			"    COALESCE((t4.gvn_qty),\n" + 
			"    0) AS gvn_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(:month, sub_cat_id) AS id,\n" + 
			"        sub_cat_id\n" + 
			"    FROM\n" + 
			"        m_cat_sub\n" + 
			"    WHERE\n" + 
			"        m_cat_sub.del_status = 0\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        SUM(d.grand_total) AS grand_total,\n" + 
			"        i.item_grp2\n" + 
			"    FROM\n" + 
			"        t_bill_detail d,\n" + 
			"        t_bill_header h,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(h.bill_date, '%Y-%m') = :month AND h.del_status = 0 AND h.ex_varchar2 IN(:temp) AND h.bill_no = d.bill_no AND i.id = d.item_id AND i.is_stockable = 1\n" + 
			"    GROUP BY\n" + 
			"        i.item_grp2\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.sub_cat_id = t2.item_grp2\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        SUM(d.grn_gvn_amt) AS grn_qty,\n" + 
			"        i.item_grp2\n" + 
			"    FROM\n" + 
			"        t_credit_note_header h,\n" + 
			"        t_credit_note_details d,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        h.crn_id = d.crn_id AND h.is_grn = 1 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month AND i.id = d.item_id AND i.is_stockable = 1\n" + 
			"    GROUP BY\n" + 
			"        i.item_grp2\n" + 
			") t3\n" + 
			"ON\n" + 
			"    t1.sub_cat_id = t3.item_grp2\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        SUM(d.grn_gvn_amt) AS gvn_qty,\n" + 
			"        i.item_grp2\n" + 
			"    FROM\n" + 
			"        t_credit_note_header h,\n" + 
			"        t_credit_note_details d,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        h.crn_id = d.crn_id AND h.is_grn = 0 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month AND i.id = d.item_id AND i.is_stockable = 1\n" + 
			"    GROUP BY\n" + 
			"        i.item_grp2\n" + 
			") t4\n" + 
			"ON\n" + 
			"    t1.sub_cat_id = t4.item_grp2",nativeQuery=true)
	List<SalesReturnValueDao> getAdminSalesReturnValueReport12(@Param("month")String month,@Param("temp") List<Integer> temp);

	
	//Anmol--25-02-2020----
	//Anmol--25-02-2020---
		@Query(value="SELECT\n" + 
				"    t1.id,\n" + 
				"    t1.sub_cat_id,\n" + 
				"    COALESCE((t2.grand_total),\n" + 
				"    0) AS grand_total,\n" + 
				"    COALESCE((t3.grn_qty),\n" + 
				"    0) AS grn_qty,\n" + 
				"    0 AS gvn_qty\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        CONCAT(:month, sub_cat_id) AS id,\n" + 
				"        sub_cat_id\n" + 
				"    FROM\n" + 
				"        m_cat_sub\n" + 
				"    WHERE\n" + 
				"        m_cat_sub.del_status = 0\n" + 
				") t1\n" + 
				"LEFT JOIN(\n" + 
				"    SELECT\n" + 
				"        SUM(d.ext_float1) AS grand_total,\n" + 
				"        i.item_grp2\n" + 
				"    FROM\n" + 
				"        t_sell_bill_header h,\n" + 
				"        t_sell_bill_detail d,\n" + 
				"        m_item i\n" + 
				"    WHERE\n" + 
				"        DATE_FORMAT(h.bill_date, '%Y-%m') =:month AND h.del_status = 0 AND h.sell_bill_no = d.sell_bill_no AND i.id = d.item_id AND i.is_saleable = 1 AND i.del_status = 0\n" + 
				"    GROUP BY\n" + 
				"        i.item_grp2\n" + 
				") t2\n" + 
				"ON\n" + 
				"    t1.sub_cat_id = t2.item_grp2\n" + 
				"LEFT JOIN(\n" + 
				"    SELECT\n" + 
				"        i.item_grp2,\n" + 
				"        SUM(c.grand_total) AS grn_qty\n" + 
				"    FROM\n" + 
				"        t_credit_note_pos c,\n" + 
				"        m_item i\n" + 
				"    WHERE\n" + 
				"        c.item_id = i.id AND i.is_saleable = 1 AND DATE_FORMAT(c.crn_date, '%Y-%m') =:month AND c.del_status = 0 AND i.del_status = 0\n" + 
				"    GROUP BY\n" + 
				"        i.item_grp2\n" + 
				") t3\n" + 
				"ON\n" + 
				"    t1.sub_cat_id = t3.item_grp2",nativeQuery=true)
		List<SalesReturnValueDao> getAdminSalesReturnValueReportCompOutlet(@Param("month")String month);
	
	
	@Query(value="SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        ( (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS grand_total\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.bill_no = t_bill_detail.bill_no  AND m_item.item_grp2 = m_cat_sub.sub_cat_id AND m_item.id = t_bill_detail.item_id\n" + 
			"    ) +( SELECT\n" + 
			"            SUM(t_sell_bill_detail.grand_total) AS grand_total\n" + 
			"        FROM\n" + 
			"            t_sell_bill_detail,\n" + 
			"            t_sell_bill_header,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(\n" + 
			"                t_sell_bill_header.bill_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_sell_bill_header.del_status = 0 AND  t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no  AND m_item.item_grp2 = m_cat_sub.sub_cat_id AND m_item.id = t_sell_bill_detail.item_id)),\n" + 
			"    0\n" + 
			"    ) AS grand_total,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS grn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_credit_note_header.is_grn = 0 AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"     m_cat_sub.del_status = 0\n" + 
			"UNION ALL\n" + 
			"SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    m_cat_sub.sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS bill_qty\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no  AND m_sp_cake.sp_id = t_bill_detail.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS bill_qty,\n" + 
			"    0 AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 0 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_sp_cake.sp_id = t_credit_note_details.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"     m_cat_sub.del_status = 0",nativeQuery=true)
	List<SalesReturnValueDao> getSalesReturnValueReportAll(@Param("month")String month,@Param("temp") List<Integer> temp);
	
	
	
	@Query(value="SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_sell_bill_detail.grand_total) AS grand_total\n" + 
			"        FROM\n" + 
			"            t_sell_bill_detail,\n" + 
			"            t_sell_bill_header,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(\n" + 
			"                t_sell_bill_header.bill_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_sell_bill_header.del_status = 0 AND  t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no  AND m_item.item_grp2 = m_cat_sub.sub_cat_id AND m_item.id = t_sell_bill_detail.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grand_total,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS grn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_item\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_credit_note_header.is_grn = 0  AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = m_cat_sub.sub_cat_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"    m_cat_sub.del_status = 0\n" + 
			"UNION ALL\n" + 
			"SELECT\n" + 
			"    CONCAT(:month, sub_cat_id) AS id,\n" + 
			"    m_cat_sub.sub_cat_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS bill_qty\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no  AND m_sp_cake.sp_id = t_bill_detail.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS bill_qty,\n" + 
			"    0 AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details,\n" + 
			"            m_sp_cake\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 0 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month  AND m_sp_cake.sp_id = t_credit_note_details.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_cat_sub\n" + 
			"WHERE\n" + 
			"   m_cat_sub.del_status = 0",nativeQuery=true)
	List<SalesReturnValueDao> getSalesReturnValueReport3(@Param("month")String month);


}
