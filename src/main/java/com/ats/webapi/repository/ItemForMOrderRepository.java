package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.ItemForMOrder;
@Repository
public interface ItemForMOrderRepository extends JpaRepository<ItemForMOrder, Integer>{

	//@Query(value="select m_item.*,coalesce((select disc_per from m_fr_discount where is_active=1 and category_id=:itemGrp1 and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1),0) as disc_per from m_item where m_item.item_grp1=:itemGrp1 and m_item.del_status=0",nativeQuery=true)
	@Query(value = "select m_item.*,coalesce((select disc_per from m_fr_discount where is_active=1 and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1),0) as disc_per,\n" + 
			"\n" + 
			"coalesce((SELECT order_qty FROM t_order WHERE t_order.fr_id=:frId AND t_order.is_edit=0 AND t_order.ref_id=0 and t_order.item_id=m_item.id AND t_order.production_date=:prodDate AND t_order.menu_id=:menuId\n" + 
			"),0) as order_qty\n" + 
			"from m_item where Find_in_set( m_item.id ,( SELECT m_fr_configure.item_show FROM m_fr_configure ,m_fr_menu_show  WHERE m_fr_configure.menu_id = m_fr_menu_show.menu_id AND m_fr_configure.setting_id in (select menu_id from m_fr_menu_configure where fr_id=:frId) and m_fr_configure.menu_id=:menuId)) and m_item.del_status=0 order by m_item.item_grp2,m_item.item_name", nativeQuery = true)

	List<ItemForMOrder> getItemListForMOrder(@Param("frId") int frId,@Param("menuId")int menuId,@Param("prodDate") String prodDate);

	@Query(value="select m_item.*,0 as order_qty,coalesce((select disc_per from m_fr_discount where is_active=1  and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1),0) as disc_per from m_item whereFind_in_set( m_item.id , (SELECT m_fr_configure.item_show FROM m_fr_configure ,m_fr_menu_show  WHERE m_fr_configure.menu_id = m_fr_menu_show.menu_id AND m_fr_configure.setting_id in (select menu_id from m_fr_menu_configure where fr_id=:frId) and m_fr_configure.menu_id=:menuId)) and m_item.del_status=0 order by m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<ItemForMOrder> getItemListForMOrderPrev(@Param("menuId") int menuId,@Param("frId") int frId);

	@Query(value="select m_item.*,0 as order_qty,0 as disc_per from m_item where m_item.item_grp1=:itemGrp1 and m_item.del_status=0 order by m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<ItemForMOrder> getItemListForMOrderMul(@Param("itemGrp1")int itemGrp1);

	@Query(value = "select m_item.*,coalesce((select var_1 from m_fr_discount where is_active=1  and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1),0)+ coalesce((select (select setting_value1 from t_setting_new where setting_key='disc_for_fr' and del_status=0) from m_franchisee where kg_1=0 and fr_id=:frId and del_status=0 and (select var_1 from m_fr_discount where is_active=1 and category_id=:itemGrp1 and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1)>0),0) as disc_per,\n" + 
			"\n" + 
			"coalesce((SELECT order_qty FROM t_order WHERE t_order.fr_id=:frId AND t_order.is_edit=0 AND t_order.ref_id=0 and t_order.item_id=m_item.id AND t_order.production_date=:prodDate AND t_order.menu_id=:menuId\n" + 
			"),0) as order_qty\n" + 
			"from m_item where Find_in_set( m_item.id ,( SELECT m_fr_configure.item_show FROM m_fr_configure ,m_fr_menu_show  WHERE m_fr_configure.menu_id = m_fr_menu_show.menu_id AND m_fr_configure.setting_id in (select menu_id from m_fr_menu_configure where fr_id=:frId) and m_fr_configure.menu_id=:menuId)) and m_item.del_status=0 order by m_item.item_grp2,m_item.item_name", nativeQuery = true)
	List<ItemForMOrder> getItemListForMOrderDm(@Param("frId") int frId,@Param("menuId")int menuId,@Param("prodDate") String prodDate);

	@Query(value="select m_item.*,0 as order_qty,0 as disc_per from m_item where  m_item.del_status=0 order by m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<ItemForMOrder> getItemListForMOrderMulDm(@Param("itemGrp1")int itemGrp1);

	@Query(value="select m_item.*,0 as order_qty,coalesce((select var_1 from m_fr_discount where is_active=1  and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1),0)+ coalesce((select (select setting_value1 from t_setting_new where setting_key='disc_for_fr' and del_status=0 and (select var_1 from m_fr_discount where is_active=1 and category_id=:itemGrp1 and FIND_IN_SET(m_item.id,item_id) and FIND_IN_SET(:frId,franch_id) order by disc_id desc limit 1)>0) from m_franchisee where kg_1=0 and fr_id=:frId and del_status=0),0) as disc_per from m_item where Find_in_set( m_item.id , (SELECT m_fr_configure.item_show FROM m_fr_configure ,m_fr_menu_show  WHERE m_fr_configure.menu_id = m_fr_menu_show.menu_id AND m_fr_configure.setting_id in (select menu_id from m_fr_menu_configure where fr_id=:frId) and m_fr_configure.menu_id=:menuId)) and m_item.del_status=0 order by m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<ItemForMOrder> getItemListForMOrderPrevDm(@Param("menuId")int menuId,@Param("frId") int frId);

}
