package com.ats.webapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.BookedOrderMailed;

public interface BookedOrderMailedRepo extends JpaRepository<BookedOrderMailed, Integer> {

	@Query(value="SELECT \n" + 
			"	t_order.order_id,\n" + 
			"    t_order.order_date,\n" + 
			"    t_order.delivery_date,\n" + 
			"    t_order.order_qty,\n" + 
			"    t_order.order_rate,\n" + 
			"    t_order.order_mrp,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_item.item_name,\n" + 
			"    m_item_sup.item_uom\n" + 
			"    \n" + 
			"FROM \n" + 
			"	t_order,\n" + 
			"    m_franchisee,\n" + 
			"    m_item_sup,\n" + 
			"    m_item\n" + 
			"WHERE \n" + 
			"	m_item.id=m_item_sup.item_id AND\n" + 
			"    t_order.item_id=m_item.id AND\n" + 
			"    t_order.fr_id=m_franchisee.fr_id AND\n" + 
			"    t_order.order_date=:orderDate AND t_order.fr_id=:frId AND t_order.menu_id=:menuId", nativeQuery=true)
	public List<BookedOrderMailed> getOrderPlacedByOrderDate(@Param("orderDate") Date orderDate,@Param("frId") int frId,@Param("menuId") int menuId);
}
