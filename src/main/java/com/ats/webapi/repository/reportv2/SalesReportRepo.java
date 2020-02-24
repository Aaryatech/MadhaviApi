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
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id\n" + 
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
			"    m_franchisee  WHERE   m_franchisee.kg_1 IN (:temp) AND m_franchisee.del_status=0 \n" + 
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
			"    m_franchisee WHERE m_franchisee.kg_1=1 AND m_franchisee.del_status=0\n" + 
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
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id \n" + 
			"    )+( SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header,m_franchisee fr \n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND fr.fr_id = t_sell_bill_header.fr_id AND  fr.kg_1=1)),\n" + 
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
			"    m_franchisee WHERE m_franchisee.kg_1 IN (:temp) AND m_franchisee.del_status=0\n" + 
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
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id  \n" + 
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
			"    m_franchisee   \n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList) AND m_franchisee.kg_1 IN(:temp) AND m_franchisee.del_status=0\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFr12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList,@Param("temp") List<Integer> temp);
	
	
	//Anmol---24-2-2020
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
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.is_dairy_mart IN(:dairyType) \n" + 
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
			"    m_franchisee   \n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList)  AND m_franchisee.del_status=0\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getAdminSalesReportFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList,@Param("temp") List<Integer> temp,@Param("dairyType") List<Integer> dairyType);
	
	
	

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
			"    m_franchisee.fr_id IN(:frIdList) AND  m_franchisee.kg_1=1 AND m_franchisee.del_status=0\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFr3(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList);
	
	
	//Anmol---24-2-2020
	@Query(value = "SELECT\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(h.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header h\n" + 
			"        WHERE\n" + 
			"            h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND m_franchisee.fr_id = h.fr_id\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS sale_value,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            SUM(c.grand_total)\n" + 
			"        FROM\n" + 
			"            t_credit_note_pos c\n" + 
			"        WHERE\n" + 
			"            c.crn_date BETWEEN :fromDate AND :toDate AND c.del_status = 0 AND m_franchisee.fr_id = c.ex_int1\n" + 
			"    ),\n" + 
			"    0\n" + 
			"    ) AS grn_value,\n" + 
			"    0 AS gvn_value\n" + 
			"FROM\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    m_franchisee.fr_id IN(:frIdList) AND m_franchisee.del_status = 0\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name", nativeQuery = true)

	List<SalesReport> getSalesReportFrCompOutlet(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
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
			"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND m_franchisee.fr_id = t_bill_header.fr_id  \n" + 
			"    )+(SELECT\n" + 
			"            SUM(t_sell_bill_header.grand_total)\n" + 
			"        FROM\n" + 
			"            t_sell_bill_header,m_franchisee fr       \n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND fr.fr_id = t_sell_bill_header.fr_id AND fr.kg_1=1 )),\n" + 
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
			"    m_franchisee.fr_id IN(:frIdList)  AND  m_franchisee.kg_1 IN(:temp) AND m_franchisee.del_status=0 \n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_name ", nativeQuery = true)

	List<SalesReport> getSalesReportSpecFrAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			 @Param("frIdList") List<String> frIdList,@Param("temp") List<Integer> temp);

}
