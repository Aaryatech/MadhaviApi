package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.SalesReport;

public interface SalesReportRepo extends JpaRepository<SalesReport, Integer> {

	
	//All Fr
	@Query(value = " SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_bill_header\n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN (:temp) \n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportAllFr12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	@Query(value = " SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header\n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND m_franchisee.fr_id = t_sell_bill_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportAllFr3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	
	@Query(value = "SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        ( (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_bill_header\n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN (:temp)\n" + 
			"    )+( SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header\n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND m_franchisee.fr_id = t_sell_bill_header.fr_id)),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportAllFrAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	// 1 fr
	 
	

	@Query(value = " SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_bill_header\n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList)\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFr12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList,@Param("temp") List<Integer> temp);
	
	

	@Query(value = " SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header\n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND m_franchisee.fr_id = t_sell_bill_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList)\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFr3(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList);
	
	
	

	@Query(value = "SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        ( (\n" + 
			"        SELECT\n" + 
			"            SUM(t_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_bill_header\n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN (:temp) \n" + 
			"    )+(SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header\n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND m_franchisee.fr_id = t_sell_bill_header.fr_id)),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 1 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(\n" + 
			"                t_credit_note_header.crn_grand_total\n" + 
			"            )\n" + 
			"        FROM\n" + 
			"            t_credit_note_header\n" + 
			"        WHERE\n" + 
			"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.is_grn = 0 AND m_franchisee.fr_id = t_credit_note_header.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList)\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name ", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFrAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList,@Param("temp") List<Integer> temp);

}
