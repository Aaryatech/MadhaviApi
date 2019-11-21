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
