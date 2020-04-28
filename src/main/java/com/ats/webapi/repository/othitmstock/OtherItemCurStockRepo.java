package com.ats.webapi.repository.othitmstock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.otheritemstock.OtherItemCurStock;

public interface OtherItemCurStockRepo extends JpaRepository<OtherItemCurStock, Integer> {
	
	
	@Query(value=" SELECT m_item.id,m_item.item_name,m_item.item_id, m_franchisee.fr_name," + 
			" COALESCE((SELECT other_item_stock_detail.opening_stock FROM other_item_stock_detail,other_item_stock_header "
			+ " WHERE other_item_stock_header.month=:month AND m_franchisee.fr_id=other_item_stock_header.fr_id AND "
			+ " other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id AND other_item_stock_header.status=0 AND "
		
			+ " other_item_stock_detail.other_item_id=m_item.id),0) as opening_stock , " + 
			" COALESCE((SELECT sum(other_item_stock_detail.damage_stock) FROM other_item_stock_detail,other_item_stock_header "
			+ " WHERE other_item_stock_header.month=:month AND m_franchisee.fr_id=other_item_stock_header.fr_id AND other_item_stock_header.status=0 AND "
			+ " other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id AND "
			+ " other_item_stock_detail.other_item_id=m_item.id),0) as damaged_stock ," + 
			" COALESCE((SELECT SUM(t_other_bill_detail.bill_qty) FROM t_other_bill_header,t_other_bill_detail WHERE " + 
			"       t_other_bill_header.bill_no=t_other_bill_detail.bill_no AND t_other_bill_header.fr_id=m_franchisee.fr_id AND "
			+ " t_other_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_other_bill_detail.item_id=m_item.id),0)"
			+ " AS purchase_qty," + 
			" COALESCE((SELECT SUM(t_sell_bill_detail.qty) FROM t_sell_bill_detail,t_sell_bill_header WHERE " + 
			" t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no "
			+ " AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_sell_bill_detail.item_id=m_item.id),0) AS sell_qty " + 
			" FROM m_item,m_franchisee " + 
			" WHERE m_item.item_grp1=:catId AND m_franchisee.fr_id=:frId AND m_item.item_rate2=:frId and m_item.del_status=0 GROUP by m_item.id",nativeQuery=true)
	List<OtherItemCurStock> getCurOtherItemCurStock(@Param("catId") int catId,@Param("month") int month, @Param("frId") int frId, @Param("fromDate")String fromDate,@Param("toDate")String toDate);

@Query(value="SELECT\n" + 
		"        m_item.id,\n" + 
		"        m_item.item_name,\n" + 
		"        m_item.item_id,\n" + 
		"        m_franchisee.fr_name,\n" + 
		"        COALESCE((SELECT\n" + 
		"            other_item_stock_detail.opening_stock \n" + 
		"        FROM\n" + 
		"            other_item_stock_detail,\n" + 
		"            other_item_stock_header  \n" + 
		"        WHERE\n" + 
		"            other_item_stock_header.month=? \n" + 
		"            AND m_franchisee.fr_id=other_item_stock_header.fr_id \n" + 
		"            AND  other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id \n" + 
		"            AND other_item_stock_header.status=0 \n" + 
		"            AND  other_item_stock_detail.other_item_id=m_item.id),\n" + 
		"        0) as opening_stock ,\n" + 
		"        COALESCE((SELECT\n" + 
		"            sum(other_item_stock_detail.damage_stock) \n" + 
		"        FROM\n" + 
		"            other_item_stock_detail,\n" + 
		"            other_item_stock_header  \n" + 
		"        WHERE\n" + 
		"            other_item_stock_header.month=? \n" + 
		"            AND m_franchisee.fr_id=other_item_stock_header.fr_id \n" + 
		"            AND other_item_stock_header.status=0 \n" + 
		"            AND  other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id \n" + 
		"            AND  other_item_stock_detail.other_item_id=m_item.id),\n" + 
		"        0) as damaged_stock ,\n" + 
		"        COALESCE((SELECT\n" + 
		"            SUM(t_other_bill_detail.bill_qty) \n" + 
		"        FROM\n" + 
		"            t_other_bill_header,\n" + 
		"            t_other_bill_detail \n" + 
		"        WHERE\n" + 
		"            t_other_bill_header.bill_no=t_other_bill_detail.bill_no \n" + 
		"            AND t_other_bill_header.fr_id=m_franchisee.fr_id \n" + 
		"            AND  t_other_bill_header.bill_date BETWEEN ? AND ? \n" + 
		"            AND t_other_bill_detail.item_id=m_item.id),\n" + 
		"        0) AS purchase_qty,\n" + 
		"        COALESCE((SELECT\n" + 
		"            SUM(t_sell_bill_detail.qty) \n" + 
		"        FROM\n" + 
		"            t_sell_bill_detail,\n" + 
		"            t_sell_bill_header \n" + 
		"        WHERE\n" + 
		"            t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no  \n" + 
		"            AND t_sell_bill_header.fr_id=m_franchisee.fr_id \n" + 
		"            AND t_sell_bill_header.bill_date BETWEEN ? AND ?  \n" + 
		"            AND t_sell_bill_detail.item_id=m_item.id),\n" + 
		"        0) AS sell_qty  \n" + 
		"    FROM\n" + 
		"        m_item,\n" + 
		"        m_franchisee  \n" + 
		"    WHERE\n" + 
		"        m_item.item_grp1=? \n" + 
		"        and m_item.del_status=0 \n" + 
		"    GROUP by\n" + 
		"        m_item.id", nativeQuery=true)
List<OtherItemCurStock> getCurOtherItemCurStock(@Param("catId") int catId,@Param("month") int month, @Param("fromDate")String fromDate,@Param("toDate")String toDate);

}
