package com.ats.webapi.repository.salesreturnreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesvaluereport.SalesReturnValueDao;
import com.ats.webapi.model.salesvaluereport.SalesReturnValueItemDao;

public interface SalesReturnValueItemDaoRepo extends JpaRepository<SalesReturnValueItemDao, Integer> {

	@Query(value = "SELECT\n" + 
			"    CONCAT(:month, id) AS id,\n" + 
			"    id AS item_id,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_detail.grand_total) AS grand_total\n" + 
			"        FROM\n" + 
			"            t_bill_detail,\n" + 
			"            t_bill_header\n" + 
			"        WHERE\n" + 
			"            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cat_id != 5 AND m_item.id = t_bill_detail.item_id \n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grand_total,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS grn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_qty,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(grn_gvn_amt) AS gvn_qty\n" + 
			"        FROM\n" + 
			"            t_credit_note_header,\n" + 
			"            t_credit_note_details\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n" + 
			"                t_credit_note_header.crn_date,\n" + 
			"                '%Y-%m'\n" + 
			"            ) = :month AND t_credit_note_header.is_grn = 0 AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_qty\n" + 
			"FROM\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 ", nativeQuery = true)
	List<SalesReturnValueItemDao> getSalesReturnValueItemReport12(@Param("month") String month,
			@Param("subCatId") List<Integer> subCatId);
	
	
	
	//Anmol--------22-2-2020
	@Query(value = "SELECT\n" + 
			"    t1.id,\n" + 
			"    t1.item_id,\n" + 
			"    COALESCE((t2.grand_total),\n" + 
			"    0) AS grand_total,\n" + 
			"    COALESCE((t3.grn_qty),\n" + 
			"    0) AS grn_qty,\n" + 
			"    COALESCE((t4.gvn_qty),\n" + 
			"    0) AS gvn_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(:month, id) AS id,\n" + 
			"        id AS item_id,\n" + 
			"        item_name\n" + 
			"    FROM\n" + 
			"        m_item\n" + 
			"    WHERE\n" + 
			"        m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 AND m_item.is_stockable =1 \n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.item_id,\n" + 
			"        SUM(d.grand_total) AS grand_total\n" + 
			"    FROM\n" + 
			"        t_bill_detail d,\n" + 
			"        t_bill_header h\n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(h.bill_date, '%Y-%m') =:month AND h.del_status = 0 AND h.bill_no = d.bill_no AND h.ex_varchar2 IN(:typeList)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.item_id = t2.item_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.item_id,\n" + 
			"        SUM(d.grn_gvn_amt) AS grn_qty\n" + 
			"    FROM\n" + 
			"        t_credit_note_header h,\n" + 
			"        t_credit_note_details d\n" + 
			"    WHERE\n" + 
			"        h.crn_id = d.crn_id AND h.is_grn = 1 AND DATE_FORMAT(h.crn_date, '%Y-%m') =:month \n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t3\n" + 
			"ON\n" + 
			"    t1.item_id = t3.item_id\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.item_id,\n" + 
			"        SUM(d.grn_gvn_amt) AS gvn_qty\n" + 
			"    FROM\n" + 
			"        t_credit_note_header h,\n" + 
			"        t_credit_note_details d\n" + 
			"    WHERE\n" + 
			"        h.crn_id = d.crn_id AND h.is_grn = 0 AND DATE_FORMAT(h.crn_date, '%Y-%m') =:month \n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t4\n" + 
			"ON\n" + 
			"    t1.item_id = t4.item_id ", nativeQuery = true)
	List<SalesReturnValueItemDao> getAdminMonthWiseSalesReport(@Param("month") String month,
			@Param("subCatId") List<Integer> subCatId,@Param("typeList") List<Integer> typeList);
	
	
	//Anmol--------22-2-2020
		@Query(value = "SELECT\n" + 
				"    t1.id,\n" + 
				"    t1.item_id,\n" + 
				"    COALESCE((t2.grand_total),\n" + 
				"    0) AS grand_total,\n" + 
				"    COALESCE((t3.grn_total),\n" + 
				"    0) AS grn_qty,\n" + 
				"    0 AS gvn_qty\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        CONCAT(:month, id) AS id,\n" + 
				"        id AS item_id,\n" + 
				"        item_name\n" + 
				"    FROM\n" + 
				"        m_item\n" + 
				"    WHERE\n" + 
				"        m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 AND m_item.is_saleable = 1\n" + 
				") t1\n" + 
				"LEFT JOIN(\n" + 
				"    SELECT\n" + 
				"        d.item_id,\n" + 
				"        SUM(d.ext_float1) AS grand_total\n" + 
				"    FROM\n" + 
				"        t_sell_bill_detail d,\n" + 
				"        t_sell_bill_header h\n" + 
				"    WHERE\n" + 
				"        DATE_FORMAT(h.bill_date, '%Y-%m') =:month AND h.del_status = 0 AND h.sell_bill_no = d.sell_bill_no AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0)) \n" + 
				"    GROUP BY\n" + 
				"        d.item_id\n" + 
				") t2\n" + 
				"ON\n" + 
				"    t1.item_id = t2.item_id\n" + 
				"LEFT JOIN(\n" + 
				"    SELECT\n" + 
				"        c.item_id,\n" + 
				"        SUM(c.grand_total) AS grn_total\n" + 
				"    FROM\n" + 
				"        t_credit_note_pos c\n" + 
				"    WHERE\n" + 
				"        DATE_FORMAT(c.crn_date, '%Y-%m') =:month AND c.del_status = 0\n" + 
				"    GROUP BY\n" + 
				"        c.item_id\n" + 
				") t3\n" + 
				"ON\n" + 
				"    t1.item_id = t3.item_id ", nativeQuery = true)
		List<SalesReturnValueItemDao> getAdminMonthWiseSalesReportComp(@Param("month") String month,
				@Param("subCatId") List<Integer> subCatId, @Param("configType") int configType);
		
		
		//Anmol--------22-2-2020 COMP OUTLET WITH DAIRY
				@Query(value = "SELECT\n" + 
						"        t1.id,\n" + 
						"        t1.item_id,\n" + 
						"        COALESCE((t2.grand_total),\n" + 
						"        0) AS grand_total,\n" + 
						"        COALESCE((t3.grn_qty + t4.gvn_qty),\n" + 
						"        0) AS grn_qty,\n" + 
						"        COALESCE((t4.gvn_qty),\n" + 
						"        0) AS gvn_qty\n" + 
						"    FROM\n" + 
						"        (\n" + 
						"        SELECT\n" + 
						"            CONCAT(:month, id) AS id,\n" + 
						"            id AS item_id,\n" + 
						"            item_name\n" + 
						"        FROM\n" + 
						"            m_item\n" + 
						"        WHERE\n" + 
						"            m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 AND m_item.is_stockable = 1\n" + 
						"    ) t1\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grand_total) AS grand_total\n" + 
						"    FROM\n" + 
						"        t_bill_detail d,\n" + 
						"        t_bill_header h\n" + 
						"    WHERE\n" + 
						"        DATE_FORMAT(h.bill_date, '%Y-%m') = :month AND h.del_status = 0 AND h.bill_no = d.bill_no AND h.is_dairy_mart = 2\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t2\n" + 
						"ON\n" + 
						"    t1.item_id = t2.item_id\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grn_gvn_amt) AS grn_qty\n" + 
						"    FROM\n" + 
						"        t_credit_note_header h,\n" + 
						"        t_credit_note_details d\n" + 
						"    WHERE\n" + 
						"        h.crn_id = d.crn_id AND h.is_grn = 1 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t3\n" + 
						"ON\n" + 
						"    t1.item_id = t3.item_id\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grn_gvn_amt) AS gvn_qty\n" + 
						"    FROM\n" + 
						"        t_credit_note_header h,\n" + 
						"        t_credit_note_details d\n" + 
						"    WHERE\n" + 
						"        h.crn_id = d.crn_id AND h.is_grn = 0 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t4\n" + 
						"ON\n" + 
						"    t1.item_id = t4.item_id ", nativeQuery = true)
				List<SalesReturnValueItemDao> getAdminMonthWiseSalesReportCompDairy(@Param("month") String month,
						@Param("subCatId") List<Integer> subCatId);
	
		
		
		//Anmol--------22-2-2020 COMP OUTLET WITH DAIRY AND REG
				@Query(value = "SELECT\n" + 
						"    id,\n" + 
						"    item_id,\n" + 
						"    SUM(grand_total) AS grand_total,\n" + 
						"    SUM(grn_qty) AS grn_qty,\n" + 
						"    SUM(gvn_qty) AS gvn_qty\n" + 
						"FROM\n" + 
						"    (\n" + 
						"    SELECT\n" + 
						"        t1.id,\n" + 
						"        t1.item_id,\n" + 
						"        COALESCE((t2.grand_total),\n" + 
						"        0) AS grand_total,\n" + 
						"        COALESCE((t3.grn_qty + t4.gvn_qty),\n" + 
						"        0) AS grn_qty,\n" + 
						"        COALESCE((t4.gvn_qty),\n" + 
						"        0) AS gvn_qty\n" + 
						"    FROM\n" + 
						"        (\n" + 
						"        SELECT\n" + 
						"            CONCAT(:month, id) AS id,\n" + 
						"            id AS item_id,\n" + 
						"            item_name\n" + 
						"        FROM\n" + 
						"            m_item\n" + 
						"        WHERE\n" + 
						"            m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 AND m_item.is_stockable = 1\n" + 
						"    ) t1\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grand_total) AS grand_total\n" + 
						"    FROM\n" + 
						"        t_bill_detail d,\n" + 
						"        t_bill_header h\n" + 
						"    WHERE\n" + 
						"        DATE_FORMAT(h.bill_date, '%Y-%m') = :month AND h.del_status = 0 AND h.bill_no = d.bill_no AND h.is_dairy_mart = 2\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t2\n" + 
						"ON\n" + 
						"    t1.item_id = t2.item_id\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grn_gvn_amt) AS grn_qty\n" + 
						"    FROM\n" + 
						"        t_credit_note_header h,\n" + 
						"        t_credit_note_details d\n" + 
						"    WHERE\n" + 
						"        h.crn_id = d.crn_id AND h.is_grn = 1 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t3\n" + 
						"ON\n" + 
						"    t1.item_id = t3.item_id\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.grn_gvn_amt) AS gvn_qty\n" + 
						"    FROM\n" + 
						"        t_credit_note_header h,\n" + 
						"        t_credit_note_details d\n" + 
						"    WHERE\n" + 
						"        h.crn_id = d.crn_id AND h.is_grn = 0 AND DATE_FORMAT(h.crn_date, '%Y-%m') = :month\n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t4\n" + 
						"ON\n" + 
						"    t1.item_id = t4.item_id\n" + 
						"UNION\n" + 
						"    (\n" + 
						"    SELECT\n" + 
						"        t1.id,\n" + 
						"        t1.item_id,\n" + 
						"        COALESCE((t2.grand_total),\n" + 
						"        0) AS grand_total,\n" + 
						"        COALESCE((t3.grn_total),\n" + 
						"        0) AS grn_qty,\n" + 
						"        0 AS gvn_qty\n" + 
						"    FROM\n" + 
						"        (\n" + 
						"        SELECT\n" + 
						"            CONCAT(:month, id) AS id,\n" + 
						"            id AS item_id,\n" + 
						"            item_name\n" + 
						"        FROM\n" + 
						"            m_item\n" + 
						"        WHERE\n" + 
						"            m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0 AND m_item.is_saleable = 1\n" + 
						"    ) t1\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        d.item_id,\n" + 
						"        SUM(d.ext_float1) AS grand_total\n" + 
						"    FROM\n" + 
						"        t_sell_bill_detail d,\n" + 
						"        t_sell_bill_header h\n" + 
						"    WHERE\n" + 
						"        DATE_FORMAT(h.bill_date, '%Y-%m') = :month AND h.del_status = 0 AND h.sell_bill_no = d.sell_bill_no AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0)) \n" + 
						"    GROUP BY\n" + 
						"        d.item_id\n" + 
						") t2\n" + 
						"ON\n" + 
						"    t1.item_id = t2.item_id\n" + 
						"LEFT JOIN(\n" + 
						"    SELECT\n" + 
						"        c.item_id,\n" + 
						"        SUM(c.grand_total) AS grn_total\n" + 
						"    FROM\n" + 
						"        t_credit_note_pos c\n" + 
						"    WHERE\n" + 
						"        DATE_FORMAT(c.crn_date, '%Y-%m') = :month AND c.del_status = 0\n" + 
						"    GROUP BY\n" + 
						"        c.item_id\n" + 
						") t3\n" + 
						"ON\n" + 
						"    t1.item_id = t3.item_id\n" + 
						")\n" + 
						") t1\n" + 
						"GROUP BY\n" + 
						"    item_id ", nativeQuery = true)
				List<SalesReturnValueItemDao> getAdminMonthWiseSalesReportCompDairyAndReg(@Param("month") String month,
						@Param("subCatId") List<Integer> subCatId, @Param("configType") int configType);
				
	
	
	/*
	 * @Query(value = "SELECT\n" + "    CONCAT(:month, id) AS id,\n" +
	 * "    id AS item_id,\n" + "    COALESCE(\n" + "        (\n" +
	 * "        SELECT\n" +
	 * "            SUM(t_sell_bill_detail.grand_total) AS grand_total\n" +
	 * "        FROM\n" + "            t_sell_bill_detail,\n" +
	 * "            t_sell_bill_header\n" + "        WHERE\n" +
	 * "            DATE_FORMAT(\n" +
	 * "                t_sell_bill_header.bill_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cat_id != 5 AND m_item.id = t_sell_bill_detail.item_id\n"
	 * + "    ),\n" + "    0\n" + "    ) AS grand_total,\n" + "    COALESCE(\n" +
	 * "        (\n" + "        SELECT\n" +
	 * "            SUM(grn_gvn_amt) AS grn_qty\n" + "        FROM\n" +
	 * "            t_credit_note_header,\n" + "            t_credit_note_details\n"
	 * + "        WHERE\n" +
	 * "            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n"
	 * + "                t_credit_note_header.crn_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n"
	 * + "    ),\n" + "    0\n" + "    ) AS grn_qty,\n" + "    COALESCE(\n" +
	 * "        (\n" + "        SELECT\n" +
	 * "            SUM(grn_gvn_amt) AS gvn_qty\n" + "        FROM\n" +
	 * "            t_credit_note_header,\n" + "            t_credit_note_details\n"
	 * + "        WHERE\n" +
	 * "            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n"
	 * + "                t_credit_note_header.crn_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_credit_note_header.is_grn = 0 AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n"
	 * + "    ),\n" + "    0\n" + "    ) AS gvn_qty\n" + "FROM\n" + "    m_item\n" +
	 * "WHERE\n" + "    m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0",
	 * nativeQuery = true) List<SalesReturnValueItemDao>
	 * getSalesReturnValueItemReport3(@Param("month") String month,
	 * 
	 * @Param("subCatId") List<Integer> subCatId);
	 * 
	 * 
	 * @Query(value = "SELECT\n" + "    CONCAT(:month, id) AS id,\n" +
	 * "    id AS item_id,\n" + "    COALESCE(\n" + "        ((\n" +
	 * "        SELECT\n" +
	 * "            SUM(t_bill_detail.grand_total) AS grand_total\n" +
	 * "        FROM\n" + "            t_bill_detail,\n" +
	 * "            t_bill_header\n" + "        WHERE\n" +
	 * "            DATE_FORMAT(t_bill_header.bill_date, '%Y-%m') = :month AND t_bill_header.del_status = 0 AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cat_id != 5 AND m_item.id = t_bill_detail.item_id AND t_bill_header.ex_varchar1 IN (:itmList)\n"
	 * + "    )+( SELECT\n" +
	 * "            SUM(t_sell_bill_detail.grand_total) AS grand_total\n" +
	 * "        FROM\n" + "            t_sell_bill_detail,\n" +
	 * "            t_sell_bill_header\n" + "        WHERE\n" +
	 * "            DATE_FORMAT(\n" +
	 * "                t_sell_bill_header.bill_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cat_id != 5 AND m_item.id = t_sell_bill_detail.item_id)),\n"
	 * + "    0\n" + "    ) AS grand_total,\n" + "    COALESCE(\n" + "        (\n" +
	 * "        SELECT\n" + "            SUM(grn_gvn_amt) AS grn_qty\n" +
	 * "        FROM\n" + "            t_credit_note_header,\n" +
	 * "            t_credit_note_details\n" + "        WHERE\n" +
	 * "            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.is_grn = 1 AND DATE_FORMAT(\n"
	 * + "                t_credit_note_header.crn_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n"
	 * + "    ),\n" + "    0\n" + "    ) AS grn_qty,\n" + "    COALESCE(\n" +
	 * "        (\n" + "        SELECT\n" +
	 * "            SUM(grn_gvn_amt) AS gvn_qty\n" + "        FROM\n" +
	 * "            t_credit_note_header,\n" + "            t_credit_note_details\n"
	 * + "        WHERE\n" +
	 * "            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND DATE_FORMAT(\n"
	 * + "                t_credit_note_header.crn_date,\n" +
	 * "                '%Y-%m'\n" +
	 * "            ) = :month AND t_credit_note_header.is_grn = 0 AND t_credit_note_details.cat_id != 5 AND m_item.id = t_credit_note_details.item_id\n"
	 * + "    ),\n" + "    0\n" + "    ) AS gvn_qty\n" + "FROM\n" + "    m_item\n" +
	 * "WHERE\n" + "    m_item.item_grp2 IN(:subCatId) AND m_item.del_status = 0",
	 * nativeQuery = true) List<SalesReturnValueItemDao>
	 * getSalesReturnValueItemReportAll(@Param("month") String month,
	 * 
	 * @Param("subCatId") List<Integer> subCatId,@Param("itmList") List<Integer>
	 * itmList);
	 */

}
