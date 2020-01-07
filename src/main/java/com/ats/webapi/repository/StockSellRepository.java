package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.StockRegSpSell;

@Repository
public interface StockSellRepository  extends JpaRepository<StockRegSpSell ,Integer>{
	
	 @Query(value = "select CEIL(coalesce(sum((b.sell_qty*a.rm_qty)/a.no_pieces_per_item),0)) as reg,0 as sp from\n" + 
	 		"		(select item_id,rm_id,rm_qty,no_pieces_per_item from m_item_detail where rm_id=:itemId and del_status=0) a \n" + 
	 		"			LEFT JOIN  \n" + 
	 		"		(SELECT t_sell_bill_detail.item_id,COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as sell_qty FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id in(select item_id from m_item_detail where rm_id=:itemId and del_status=0) AND  \n" + 
	 		"		t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate) group by t_sell_bill_detail.item_id) b ON a.item_id=b.item_id ", nativeQuery = true)
	/*@Query(value = "select coalesce((SELECT SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END) as reg FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id in(:itemId) AND \n" + 
			"			t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND  :toDate) group by t_sell_bill_detail.item_id),0) reg,0 as sp from dual", nativeQuery = true)
	*/
	StockRegSpSell getRegTotalSell(@Param("frId") int frId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("itemId") int itemId);
	
	/*	@Query(value = "\n" + 
			"select coalesce(sum((b.sell_qty*a.rm_qty)/a.no_pieces_per_item),0) as reg,0 as sp from\n" + 
			"(select item_id,rm_id,rm_qty,no_pieces_per_item from m_item_detail where rm_id=:itemId and del_status=0) a\n" + 
			"LEFT JOIN \n" + 
			"(SELECT t_sell_bill_detail.item_id,COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as sell_qty FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id in(select item_id from m_item_detail where rm_id=:itemId and del_status=0) AND \n" + 
			"t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate) group by t_sell_bill_detail.item_id) b ON a.item_id=b.item_id ", nativeQuery = true)
			
			"SELECT COALESCE(SUM(CASE WHEN bill_stock_type = 1 THEN qty ELSE 0 END),0) as reg , COALESCE(SUM(CASE WHEN bill_stock_type = 2 THEN qty ELSE 0 END),0) as sp FROM t_sell_bill_detail WHERE t_sell_bill_detail.item_id =:itemId AND "
	+ "t_sell_bill_detail.sell_bill_no IN (SELECT t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate)
*/}
