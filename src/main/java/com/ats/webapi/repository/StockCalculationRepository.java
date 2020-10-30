package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ats.webapi.model.RegularSpecialStockCal;

@Repository
public interface StockCalculationRepository extends JpaRepository<RegularSpecialStockCal, Integer> {
	
	@Query(value = "SELECT  COALESCE(SUM(CASE WHEN grn_type != 3 THEN bill_qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN grn_type = 3 THEN bill_qty ELSE 0 END),0) as sp  "
				+ "  FROM t_bill_detail WHERE t_bill_detail.item_id=:itemId AND t_bill_detail.bill_no"
				+ " IN(SELECT t_bill_header.bill_no FROM t_bill_header WHERE t_bill_header.fr_id=:frId  AND t_bill_header.status =2  AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate )", nativeQuery = true)
	RegularSpecialStockCal getTotalPurchase(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("itemId") int itemId);

	@Query(value = "SELECT  COALESCE(SUM(t_grn_gvn.grn_gvn_qty),0) FROM t_grn_gvn WHERE t_grn_gvn.fr_id=:frId AND t_grn_gvn.item_id=:itemId AND t_grn_gvn.grn_gvn_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
	float getRegTotalGrnGvn(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("itemId") int itemId);
	
	

//	@Query(value = "SELECT COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN bill_stock_type = 2 THEN qty ELSE 0 END),0) as sp FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id =:itemId AND "
//			+ "t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate)", nativeQuery = true)
//	RegularSpecialStockCal getRegTotalSell(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
//			@Param("itemId") int itemId);
	
	
	@Query(value = "SELECT\n" + 
			"    ROUND(\n" + 
			"        (\n" + 
			"            COALESCE(\n" + 
			"                SUM(\n" + 
			"                    (b.sell_qty * a.rm_qty) / a.no_pieces_per_item\n" + 
			"                ),\n" + 
			"                0\n" + 
			"            )\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS reg,\n" + 
			"    0 AS sp\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        item_id,\n" + 
			"        rm_id,\n" + 
			"        rm_qty,\n" + 
			"        no_pieces_per_item\n" + 
			"    FROM\n" + 
			"        m_item_detail\n" + 
			"    WHERE\n" + 
			"        rm_id = :itemId AND del_status = 0\n" + 
			") a\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        t_sell_bill_detail.item_id,\n" + 
			"        COALESCE(\n" + 
			"            SUM(\n" + 
			"                CASE WHEN bill_stock_type = 1 THEN qty ELSE 0\n" + 
			"            END\n" + 
			"        ),\n" + 
			"        0\n" + 
			") AS sell_qty\n" + 
			"FROM\n" + 
			"    t_sell_bill_detail\n" + 
			"WHERE\n" + 
			"    t_sell_bill_detail.item_id IN(\n" + 
			"    SELECT\n" + 
			"        item_id\n" + 
			"    FROM\n" + 
			"        m_item_detail\n" + 
			"    WHERE\n" + 
			"        rm_id = :itemId AND del_status = 0\n" + 
			") AND t_sell_bill_detail.sell_bill_no IN(\n" + 
			"    SELECT\n" + 
			"        t_sell_bill_header.sell_bill_no\n" + 
			"    FROM\n" + 
			"        t_sell_bill_header\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = :frId AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate\n" + 
			")\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_detail.item_id\n" + 
			") b\n" + 
			"ON\n" + 
			"    a.item_id = b.item_id", nativeQuery = true)
	RegularSpecialStockCal getRegTotalSell(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("itemId") int itemId);
	
	
		
	// grn/gvn
		@Query(value = "SELECT COALESCE(SUM(t_grn_gvn.grn_gvn_qty),0) FROM t_grn_gvn WHERE t_grn_gvn.fr_id=:frId AND t_grn_gvn.item_id=:itemId AND"
				+ " t_grn_gvn.grn_gvn_entry_datetime BETWEEN :fromDateTime AND :toDateTime ", nativeQuery = true)
		float getTotalGrnGvnUptoDateTime(@Param("frId") int frId, @Param("fromDateTime") String fromDateTime, @Param("toDateTime") String toDateTime,
				@Param("itemId") int itemId);
	// purchase
	@Query(value = "SELECT  COALESCE(SUM(CASE WHEN grn_type != 3  THEN bill_qty ELSE 0 END),0) as reg ,"
			+ " COALESCE(SUM(CASE WHEN grn_type = 3 THEN bill_qty ELSE 0 END),0) as sp  FROM t_bill_detail "
			+ "WHERE t_bill_detail.item_id=:itemId AND t_bill_detail.bill_no IN(SELECT t_bill_header.bill_no "
			+ "FROM t_bill_header WHERE t_bill_header.fr_id=:frId  AND t_bill_header.status =2 "
			+ " AND t_bill_header.bill_date_time BETWEEN :fromDateTime AND :toDateTime )" 
			, nativeQuery = true)
	RegularSpecialStockCal	getTotalPurchaseUptoDateTime(@Param("frId") int frId, @Param("fromDateTime") String fromDateTime, @Param("toDateTime") String toDateTime,
			@Param("itemId") int itemId);
	// sell
	@Query(value = "SELECT COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN bill_stock_type = 2 THEN qty ELSE 0 END),0) as sp"
			+ " FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id =:itemId AND t_sell_bill_detail.sell_bill_no "
			+ "IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.timestamp "
			+ "BETWEEN :fromDateTime AND :toDateTime)" + 
			"", nativeQuery = true)
	RegularSpecialStockCal getTotalSellUpToDateTime(@Param("frId") int frId, @Param("fromDateTime") String fromDateTime, @Param("toDateTime") String toDateTime,
			@Param("itemId") int itemId);

	// purchase of particular date
	@Query(value = "	SELECT COALESCE(SUM(CASE WHEN grn_type != 3 AND grn_type!=4 THEN bill_qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN grn_type = 4 THEN bill_qty ELSE 0 END),0) as sp "
			+ "FROM t_bill_detail WHERE t_bill_detail.item_id=:itemId AND "
			+ "t_bill_detail.bill_no "
			+ "IN(SELECT t_bill_header.bill_no FROM t_bill_header WHERE t_bill_header.fr_id=:frId  AND t_bill_header.status =2  AND t_bill_header.bill_date = :fromDate)" + 
			"", nativeQuery = true)
	RegularSpecialStockCal getTotalPurchaseOfDate(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("itemId") int itemId);

	// Sell between date time
	@Query(value = "SELECT COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN bill_stock_type = 2 THEN qty ELSE 0 END),0) as sp "
			+ "FROM t_sell_bill_detail "
			+ "WHERE t_sell_bill_detail.item_id =:itemId AND t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no "
			+ "FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.timestamp "
			+ " BETWEEN :fromDateTime AND :toDateTime )", nativeQuery = true)
	RegularSpecialStockCal getRegTotalSellBetweenDateTime(@Param("frId") int frId, @Param("fromDateTime") String fromDate, @Param("toDateTime") String toDate,
			@Param("itemId") int itemId);
	/*
	GRN/GVN-
	SELECT  COALESCE(SUM(t_grn_gvn.grn_gvn_qty),0) FROM t_grn_gvn WHERE t_grn_gvn.fr_id=15 AND t_grn_gvn.item_id=123 AND t_grn_gvn.grn_gvn_entry_datetime BETWEEN '2017-11-14 00:00:00' AND '2017-11-15 15:00:00'
	Purchase-
	SELECT  COALESCE(SUM(CASE WHEN grn_type != 3 AND grn_type!=4 THEN bill_qty ELSE 0 END),0) as pur_qty , COALESCE(SUM(CASE WHEN grn_type = 4 THEN bill_qty ELSE 0 END),0) as push_qty  FROM t_bill_detail WHERE t_bill_detail.item_id=147 AND t_bill_detail.bill_no IN(SELECT t_bill_header.bill_no FROM t_bill_header WHERE t_bill_header.fr_id=15  AND t_bill_header.status =2  AND t_bill_header.bill_date_time BETWEEN '2017-11-01 00:00:00' AND '2017-11-23 22:00:00' )
	Sell
	SELECT COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN bill_stock_type = 2 THEN qty ELSE 0 END),0) as sp FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id =2 AND t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=15 AND t_sell_bill_header.timestamp BETWEEN '2017-11-01 00:00:00' AND '2017-11-23 23:00:00' )
*/
	
	
	//SELL_CREDIT_NOTE
	@Query(value = "SELECT COALESCE((SUM(p.crn_qty)),0) FROM t_credit_note_pos p WHERE p.is_stockable=1 AND p.ex_int1=:frId AND p.crn_date BETWEEN :fromDate AND :toDate AND p.item_id=:itemId", nativeQuery = true)
	float getTotalSellCreditNote(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("itemId") int itemId);
		
}
