package com.ats.webapi.repository.frpurchasereport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.frpurchase.DateWiseSaleForItem;
import com.ats.webapi.model.report.frpurchase.SalesReportRoyalty;

public interface DateWiseSaleForItemRepo extends JpaRepository<DateWiseSaleForItem, Integer> {

	
	@Query(value="	SELECT\r\n" + 
			"    UUID() AS uid, h.bill_date, SUM(d.grand_total) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header h,\r\n" + 
			"    t_bill_detail d\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId AND h.ex_varchar2 IN(:temp)\r\n" + 
			"GROUP BY\r\n" + 
			"    h.bill_date",nativeQuery=true)	
	List<DateWiseSaleForItem> getDateWiseSaleFrAndCDC(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate,@Param("temp")List<Integer> temp,@Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    UUID() AS uid, h.bill_date, SUM(d.ext_float1) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    t_sell_bill_detail d\r\n" + 
			"WHERE\r\n" + 
			"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND h.fr_id IN(:frIdList) AND d.item_id = :itemId\r\n" + 
			"GROUP BY\r\n" + 
			"    h.bill_date",nativeQuery=true)	
	List<DateWiseSaleForItem> getDateWiseSaleCompOutletRegular(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate, @Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    UUID() AS uid, h.bill_date, SUM(d.grand_total) AS total\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header h,\r\n" + 
			"    t_bill_detail d\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId  AND h.is_dairy_mart=2 \r\n" + 
			"GROUP BY\r\n" + 
			"    h.bill_date",nativeQuery=true)	
	List<DateWiseSaleForItem> getDateWiseSaleCompOutletDairymart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate, @Param("itemId") int itemId);
	
	
	@Query(value="	SELECT\r\n" + 
			"    t1.uid,\r\n" + 
			"    t1.bill_date,\r\n" + 
			"    SUM(t1.total) AS total\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS uid, h.bill_date, SUM(d.grand_total) AS total\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h,\r\n" + 
			"        t_bill_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_no = d.bill_no AND h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id IN(:frIdList) AND d.item_id = :itemId  AND h.is_dairy_mart = 2\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.bill_date\r\n" + 
			"    UNION\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            UUID() AS uid, h.bill_date, SUM(d.ext_float1) AS total\r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header h,\r\n" + 
			"            t_sell_bill_detail d\r\n" + 
			"        WHERE\r\n" + 
			"            h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 0 AND h.fr_id IN(:frIdList) AND d.item_id = :itemId\r\n" + 
			"        GROUP BY\r\n" + 
			"            h.bill_date)\r\n" + 
			"    ) t1\r\n" + 
			"GROUP BY\r\n" + 
			"    t1.bill_date",nativeQuery=true)	
	List<DateWiseSaleForItem> getDateWiseSaleCompOutletDairymartAndRegular(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,
			@Param("toDate")String toDate,@Param("itemId") int itemId);
	
}
