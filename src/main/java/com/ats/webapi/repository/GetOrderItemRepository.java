package com.ats.webapi.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetOrderItemQty;
import com.ats.webapi.model.GetRegSpCakeOrderQty;


public interface GetOrderItemRepository extends JpaRepository<GetOrderItemQty, Integer>
{
//
//	@Query(value = " SELECT t_order.order_id, SUM(t_order.order_qty) as qty, t_order.item_id, m_item.item_grp1, t_order.menu_id FROM t_order, m_item WHERE t_order.order_date =:orderDate AND t_order.is_bill_generated=0 AND t_order.menu_id=:menuId AND m_item.id=t_order.item_id ", nativeQuery = true)
//	GetOrderItemQty getOrderItemQty(@Param("itemId") int itemId, @Param("orderDate") String orderDate, @Param("menuId") int menuId);

	
	//30-1-2020
//	@Query(value = " SELECT t_order.order_id, t_order.production_date, SUM(t_order.order_qty) as qty, t_order.item_id, m_item.item_grp1, m_item.item_name, t_order.menu_id, 0 as adv_qty FROM t_order, m_item WHERE t_order.production_date =:productionDate AND t_order.is_bill_generated=0 AND t_order.menu_id IN(:menuId) AND m_item.id=t_order.item_id GROUP BY t_order.item_id order by item_grp2 Asc,item_sort_id Asc", nativeQuery = true)
//	List<GetOrderItemQty> getOrderAllItemQty(@Param("productionDate") String productionDate, @Param("menuId") List<String> menuId);

	
//	
//	@Query(value = "SELECT t_order.item_id, t_order.order_id FROM t_order WHERE t_order.order_date=:orderDate AND t_order.item_id=:itemId" , nativeQuery=true)
//	GetOrderItemQty getOrderItemQty(@Param("itemId") int itemId, @Param("orderDate") String orderDate );
	
	
	
/*	@Query(value = " SELECT\r\n" + 
			"    t1.order_id,\r\n" + 
			"    t1.production_date,\r\n" + 
			"    t1.qty,\r\n" + 
			"    t1.item_id,\r\n" + 
			"    t1.item_grp1,\r\n" + 
			"    t1.item_name,\r\n" + 
			"    t1.menu_id,\r\n" + 
			"    COALESCE((t2.adv_qty),\r\n" + 
			"    0) AS adv_qty\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_order.order_id,\r\n" + 
			"        t_order.production_date,\r\n" + 
			"        SUM(t_order.order_qty) AS qty,\r\n" + 
			"        t_order.item_id,\r\n" + 
			"        m_item.item_grp1,\r\n" + 
			"        m_item.item_name,\r\n" + 
			"        t_order.menu_id,\r\n" + 
			"        m_item.item_grp2,\r\n" + 
			"        m_item.item_sort_id\r\n" + 
			"    FROM\r\n" + 
			"        t_order,\r\n" + 
			"        m_item\r\n" + 
			"    WHERE\r\n" + 
			"        t_order.production_date = :productionDate AND t_order.is_bill_generated = 0 AND t_order.menu_id IN(:menuId) AND m_item.id = t_order.item_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_order.item_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.adv_detail_id,\r\n" + 
			"        d.item_id,\r\n" + 
			"        SUM(d.qty) AS adv_qty\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        d.prod_date = :productionDate AND d.del_status = 0 AND d.is_bill_generated = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.item_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    t1.item_grp2 ASC,\r\n" + 
			"    t1.item_sort_id ASC", nativeQuery = true)
	List<GetOrderItemQty> getOrderAllItemQty(@Param("productionDate") String productionDate, @Param("menuId") List<String> menuId);
*/


	@Query(value = " SELECT\r\n" + 
			"    t1.order_id,\r\n" + 
			"    t1.production_date,\r\n" + 
			"    t1.qty,\r\n" + 
			"    t1.item_id,\r\n" + 
			"    t1.item_grp1,\r\n" + 
			"    t1.item_name,\r\n" + 
			"    t1.menu_id,\r\n" + 
			"    COALESCE((t2.adv_qty),\r\n" + 
			"    0) AS adv_qty\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_order.order_id,\r\n" + 
			"        t_order.production_date,\r\n" + 
			"        SUM(t_order.order_qty) AS qty,\r\n" + 
			"        t_order.item_id,\r\n" + 
			"        m_item.item_grp1,\r\n" + 
			"        m_item.item_name,\r\n" + 
			"        t_order.menu_id,\r\n" + 
			"        m_item.item_grp2,\r\n" + 
			"        m_item.item_sort_id\r\n" + 
			"    FROM\r\n" + 
			"        t_order,\r\n" + 
			"        m_item\r\n" + 
			"    WHERE\r\n" + 
			"        t_order.production_date =:productionDate AND t_order.is_bill_generated = 0 AND t_order.menu_id IN(:menuId) AND m_item.id = t_order.item_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_order.item_id\r\n" + 
			"    ORDER BY\r\n" + 
			"        m_item.item_grp2 ASC,\r\n" + 
			"        m_item.item_sort_id ASC\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.adv_detail_id,\r\n" + 
			"        d.item_id,\r\n" + 
			"        SUM(d.qty) AS adv_qty\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_detail d\r\n" + 
			"    WHERE\r\n" + 
			"        d.prod_date =:productionDate AND d.del_status = 0 AND d.is_bill_generated = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.item_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.item_id = t2.item_id\r\n" + 
			"UNION\r\n" + 
			"SELECT\r\n" + 
			"    t1.order_id,\r\n" + 
			"    t1.production_date,\r\n" + 
			"    t1.qty,\r\n" + 
			"    t1.item_id,\r\n" + 
			"    t1.item_grp1,\r\n" + 
			"    t1.item_name,\r\n" + 
			"    t1.menu_id,\r\n" + 
			"    COALESCE((t1.adv_qty),\r\n" + 
			"    0) AS adv_qty\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        d.adv_detail_id AS order_id,\r\n" + 
			"        d.prod_date AS production_date,\r\n" + 
			"        SUM(d.qty) AS adv_qty,\r\n" + 
			"        d.item_id,\r\n" + 
			"        d.cat_id AS item_grp1,\r\n" + 
			"        i.item_name,\r\n" + 
			"        d.menu_id,\r\n" + 
			"        d.sub_cat_id AS item_grp2,\r\n" + 
			"        i.item_sort_id,\r\n" + 
			"        0 AS qty\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_detail d,\r\n" + 
			"		 t_adv_order_header h, " +		
			"        m_item i\r\n" + 
			"    WHERE\r\n" + 
			"        d.prod_date =:productionDate AND h.adv_header_id=d.adv_detail_id AND h.del_status=0 AND d.is_bill_generated = 0 AND i.id = d.item_id AND d.cat_id IN(SELECT cat_id from m_fr_menu_show WHERE menu_id IN(:menuId))\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.item_id\r\n" + 
			") t1\r\n" + 
			"WHERE\r\n" + 
			"    t1.item_id NOT IN(\r\n" + 
			"    SELECT\r\n" + 
			"        item_id\r\n" + 
			"    FROM\r\n" + 
			"        t_order\r\n" + 
			"    WHERE\r\n" + 
			"        production_date =:productionDate AND is_bill_generated = 0 AND menu_id IN(:menuId)\r\n" + 
			")", nativeQuery = true)
	List<GetOrderItemQty> getOrderAllItemQty(@Param("productionDate") String productionDate, @Param("menuId") List<String> menuId);


}
