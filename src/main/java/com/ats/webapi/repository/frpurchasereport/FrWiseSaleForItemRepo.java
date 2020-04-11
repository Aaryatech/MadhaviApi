package com.ats.webapi.repository.frpurchasereport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.frpurchase.DateWiseSaleForItem;
import com.ats.webapi.model.report.frpurchase.FrWiseSaleForItem;

public interface FrWiseSaleForItemRepo extends JpaRepository<FrWiseSaleForItem, Integer> {

	@Query(value="	SELECT\r\n" + 
			"    f.fr_id,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    SUM(d.grand_total) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header h,\r\n" + 
			"    t_bill_detail d,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId AND h.ex_varchar2 IN(:temp) AND h.fr_id = f.fr_id\r\n" + 
			"GROUP BY\r\n" + 
			"    h.fr_id\r\n" + 
			"ORDER BY\r\n" + 
			"    h.fr_id",nativeQuery=true)	
	List<FrWiseSaleForItem> getFrWiseSaleFrAndCDC(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate,@Param("temp")List<Integer> temp,@Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    f.fr_id,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    SUM(d.ext_float1) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND h.fr_id IN(:frIdList) AND d.item_id =:itemId AND h.fr_id = f.fr_id\r\n" + 
			"GROUP BY\r\n" + 
			"    h.fr_id",nativeQuery=true)	
	List<FrWiseSaleForItem> getFrWiseSaleCompOutletRegular(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate, @Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    f.fr_id,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    SUM(d.grand_total) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header h,\r\n" + 
			"    t_bill_detail d,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId AND h.fr_id = f.fr_id AND h.is_dairy_mart = 2\r\n" + 
			"GROUP BY\r\n" + 
			"    h.fr_id\r\n" + 
			"ORDER BY\r\n" + 
			"    h.fr_id",nativeQuery=true)	
	List<FrWiseSaleForItem> getFrWiseSaleCompOutletDairymart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate, @Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    fr_id,\r\n" + 
			"    fr_name,\r\n" + 
			"    SUM(total) AS total\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        f.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        SUM(d.grand_total) AS total\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId AND h.fr_id = f.fr_id AND h.is_dairy_mart = 2\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id\r\n" + 
			"    UNION\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            f.fr_id,\r\n" + 
			"            f.fr_name,\r\n" + 
			"            SUM(d.ext_float1) AS total\r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d,\r\n" + 
			"            m_franchisee f\r\n" + 
			"        WHERE\r\n" + 
			"            h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND h.fr_id IN(:frIdList) AND d.item_id = :itemId AND h.fr_id = f.fr_id\r\n" + 
			"        GROUP BY\r\n" + 
			"            h.fr_id\r\n" + 
			"    )\r\n" + 
			") t1\r\n" + 
			"GROUP BY\r\n" + 
			"    fr_id",nativeQuery=true)	
	List<FrWiseSaleForItem> getFrWiseSaleCompOutletDairymartAndRegular(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate,@Param("itemId") int itemId);
	
}
