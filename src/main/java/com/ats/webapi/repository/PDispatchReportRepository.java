package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.report.PDispatchReport;

@Repository
public interface PDispatchReportRepository extends JpaRepository<PDispatchReport, Integer>{

	/*@Query(value="select t_order.order_id,m_category.cat_id,m_category.cat_name,m_cat_sub.sub_cat_id,t_order.fr_id,m_franchisee.fr_name,t_order.item_id,m_item.item_name,SUM(t_order.order_qty) as order_qty,SUM(t_order.edit_qty) as edit_qty,t_order.is_bill_generated from t_order,m_category,m_franchisee,m_item,m_cat_sub\n" + 
			"	where  t_order.menu_id IN(:menuId) and t_order.order_id In(select order_id from t_order where delivery_date=:productionDateYMD and fr_id in(:frId)) And (select sub_cat_id from m_fr_menu_show where menu_id=t_order.menu_id) In :categories \n" + 
			"	And (select cat_id from m_fr_menu_show where menu_id=t_order.menu_id)=m_category.cat_id And t_order.fr_id=m_franchisee.fr_id and m_item.id=t_order.item_id And m_cat_sub.sub_cat_id=m_item.item_grp2 \n" + 
			"	group by t_order.item_id,t_order.fr_id order by t_order.fr_id,m_item.item_grp1 asc,m_item.item_grp2,m_item.item_name,m_item.item_sort_id asc,m_item.item_mrp2 asc" + 
			"",nativeQuery=true)
	List<PDispatchReport> getPDispatchItemReport(@Param("productionDateYMD")String productionDateYMD,@Param("frId") List<String> frId,@Param("categories") List<Integer> categories,@Param("menuId") List<Integer> menuId);// cat_id changed to to sub_cat_id (select sub_cat_id from m_fr_menu_show where menu_id=)13 feb 19 
	*/
	
	//Anmol  -- NOT USED
	@Query(value="SELECT\n" + 
			"    t1.order_id,\n" + 
			"    t1.cat_id,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.sub_cat_id,\n" + 
			"    t1.fr_id,\n" + 
			"    t1.fr_name,\n" + 
			"    t1.item_id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_qty AS order_qty,\n" + 
			"    t1.edit_qty AS edit_qty,\n" + 
			"    t1.is_bill_generated,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 	
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_order.order_id,\n" + 
			"        m_category.cat_id,\n" + 
			"        m_category.cat_name,\n" + 
			"        m_cat_sub.sub_cat_id,\n" + 
			"        t_order.fr_id,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        t_order.item_id,\n" + 
			"        m_item.item_name,\n" + 
			"        SUM(t_order.order_qty) AS order_qty,\n" + 
			"        SUM(t_order.edit_qty) AS edit_qty,\n" + 
			"        t_order.is_bill_generated\n" + 
			"    FROM\n" + 
			"        t_order,\n" + 
			"        m_category,\n" + 
			"        m_franchisee,\n" + 
			"        m_item,\n" + 
			"        m_cat_sub\n" + 
			"    WHERE\n" + 
			"        t_order.menu_id IN(:menuId) AND t_order.order_id IN(\n" + 
			"        SELECT\n" + 
			"            order_id\n" + 
			"        FROM\n" + 
			"            t_order\n" + 
			"        WHERE\n" + 
			"            delivery_date =:productionDateYMD AND fr_id IN(:frId)\n" + 
			"    ) AND(\n" + 
			"    SELECT\n" + 
			"        sub_cat_id\n" + 
			"    FROM\n" + 
			"        m_fr_menu_show\n" + 
			"    WHERE\n" + 
			"        menu_id = t_order.menu_id\n" + 
			") IN(:categories) AND(\n" + 
			"    SELECT\n" + 
			"        cat_id\n" + 
			"    FROM\n" + 
			"        m_fr_menu_show\n" + 
			"    WHERE\n" + 
			"        menu_id = t_order.menu_id\n" + 
			") = m_category.cat_id AND t_order.fr_id = m_franchisee.fr_id AND m_item.id = t_order.item_id AND m_cat_sub.sub_cat_id = m_item.item_grp2\n" + 
			"GROUP BY\n" + 
			"    t_order.item_id,\n" + 
			"    t_order.fr_id\n" + 
			"ORDER BY\n" + 
			"    t_order.fr_id,\n" + 
			"    m_item.item_grp1 ASC,\n" + 
			"    m_item.item_grp2,\n" + 
			"    m_item.item_name,\n" + 
			"    m_item.item_sort_id ASC,\n" + 
			"    m_item.item_mrp2 ASC\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT d.adv_detail_id,\n" + 
			"        d.item_id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.delivery_date =:productionDateYMD AND d.fr_id IN(:frId) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id\n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.item_id = t2.item_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.order_id,\n" + 
			"    t1.cat_id,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.sub_cat_id,\n" + 
			"    t1.fr_id,\n" + 
			"    t1.fr_name,\n" + 
			"    t1.item_id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_qty AS order_qty,\n" + 
			"    t1.edit_qty AS edit_qty,\n" + 
			"    t1.is_bill_generated,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        f.fr_id,\n" + 
			"        f.fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.sub_cat_id,\n" + 
			"        0 AS order_qty,\n" + 
			"        COALESCE(SUM(d.qty),0) AS edit_qty,\n" + 
			"        d.is_bill_generated,\n" + 
			"        d.cat_id\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.delivery_date =:productionDateYMD AND d.cat_id IN(:categories) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.fr_id IN(:frId)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.item_id NOT IN(\n" + 
			"    SELECT\n" + 
			"        m_item.id\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        m_category,\n" + 
			"        m_item,\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.delivery_date =:productionDateYMD AND t_order.is_edit = 0 AND t_order.item_id = m_item.id AND t_order.menu_id IN(:menuId) AND t_order.fr_id = m_franchisee.fr_id AND t_order.order_type = m_category.cat_id AND t_order.order_type IN(:categories) AND t_order.fr_id IN(:frId)\n" + 
			")" + 
			"",nativeQuery=true)
	List<PDispatchReport> getPDispatchItemReport(@Param("productionDateYMD")String productionDateYMD,@Param("frId") List<String> frId,@Param("categories") List<Integer> categories,@Param("menuId") List<Integer> menuId);// cat_id changed to to sub_cat_id (select sub_cat_id from m_fr_menu_show where menu_id=)13 feb 19 
	
	
	
	//DISPATCH CHECK LIST - IF ADV ORDER---------------------
	
	@Query(value="SELECT\n" + 
			"    t1.order_id,\n" + 
			"    t1.cat_id,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.sub_cat_id,\n" + 
			"    t1.fr_id,\n" + 
			"    t1.fr_name,\n" + 
			"    t1.item_id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_qty AS order_qty,\n" + 
			"    t1.edit_qty AS edit_qty,\n" + 
			"    t1.is_bill_generated,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        o.order_id,\n" + 
			"        o.fr_id,\n" + 
			"        f.fr_name,\n" + 
			"        o.item_id,\n" + 
			"        SUM(o.order_qty) AS order_qty,\n" + 
			"        SUM(o.edit_qty) AS edit_qty,\n" + 
			"        o.is_bill_generated,\n" + 
			"        c.cat_id,\n" + 
			"        c.cat_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        i.item_name\n" + 
			"    FROM\n" + 
			"        t_order o,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_cat_sub sc,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        o.delivery_date = :productionDateYMD AND o.fr_id IN(:frId) AND o.fr_id = f.fr_id AND o.order_type = c.cat_id AND o.order_sub_type = sc.sub_cat_id AND o.item_id = i.id\n" + 
			"    GROUP BY\n" + 
			"        o.item_id,\n" + 
			"        o.fr_id\n" + 
			"    ORDER BY\n" + 
			"        o.fr_id,\n" + 
			"        i.item_grp1 ASC,\n" + 
			"        i.item_grp2,\n" + 
			"        i.item_name,\n" + 
			"        i.item_sort_id ASC,\n" + 
			"        i.item_mrp2 ASC\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id,\n" + 
			"        d.item_id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty, d.fr_id\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d\n" + 
			"    WHERE\n" + 
			"        d.delivery_date = :productionDateYMD AND d.menu_id IN(:menuId) AND d.fr_id IN(:frId) AND d.del_status = 0\n" + 
			"    GROUP BY\n" + 
			"        d.item_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.item_id = t2.item_id AND t1.fr_id=t2.fr_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.order_id,\n" + 
			"    t1.cat_id,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.sub_cat_id,\n" + 
			"    t1.fr_id,\n" + 
			"    t1.fr_name,\n" + 
			"    t1.item_id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_qty AS order_qty,\n" + 
			"    t1.edit_qty AS edit_qty,\n" + 
			"    t1.is_bill_generated,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        f.fr_id,\n" + 
			"        f.fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.sub_cat_id,\n" + 
			"        0 AS order_qty,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS edit_qty,\n" + 
			"        d.is_bill_generated,\n" + 
			"        d.cat_id\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.delivery_date = :productionDateYMD AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.fr_id IN(:frId) AND d.menu_id IN(:menuId)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.item_id NOT IN(\n" + 
			"    SELECT\n" + 
			"        o.item_id\n" + 
			"    FROM\n" + 
			"        t_order o\n" + 
			"    WHERE\n" + 
			"        o.delivery_date = :productionDateYMD AND o.is_edit = 0 AND o.fr_id IN(:frId)\n" + 
			")",nativeQuery=true)
	List<PDispatchReport> getDispatchChkListIfAdvOrd(@Param("productionDateYMD")String productionDateYMD,@Param("frId") List<String> frId, @Param("menuId") List<Integer> menuId); 
	
	
	//-------------------------------------------------------
	
	
	//DISPATCH CHECK LIST - IF REG ORDER---------------------
	
		@Query(value="SELECT\n" + 
				"    t1.order_id,\n" + 
				"    t1.cat_id,\n" + 
				"    t1.cat_name,\n" + 
				"    t1.sub_cat_id,\n" + 
				"    t1.fr_id,\n" + 
				"    t1.fr_name,\n" + 
				"    t1.item_id,\n" + 
				"    t1.item_name,\n" + 
				"    t1.order_qty AS order_qty,\n" + 
				"    t1.edit_qty AS edit_qty,\n" + 
				"    t1.is_bill_generated,\n" + 
				"    0 AS adv_qty\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        o.order_id,\n" + 
				"        o.fr_id,\n" + 
				"        f.fr_name,\n" + 
				"        o.item_id,\n" + 
				"        SUM(o.order_qty) AS order_qty,\n" + 
				"        SUM(o.edit_qty) AS edit_qty,\n" + 
				"        o.is_bill_generated,\n" + 
				"        c.cat_id,\n" + 
				"        c.cat_name,\n" + 
				"        sc.sub_cat_id,\n" + 
				"        i.item_name\n" + 
				"    FROM\n" + 
				"        t_order o,\n" + 
				"        m_franchisee f,\n" + 
				"        m_category c,\n" + 
				"        m_cat_sub sc,\n" + 
				"        m_item i\n" + 
				"    WHERE\n" + 
				"        o.menu_id IN(:menuId) AND o.delivery_date = :productionDateYMD AND o.fr_id IN(:frId) AND o.fr_id = f.fr_id AND o.order_type = c.cat_id AND o.order_sub_type = sc.sub_cat_id AND o.item_id = i.id\n" + 
				"    GROUP BY\n" + 
				"        o.item_id,\n" + 
				"        o.fr_id\n" + 
				"    ORDER BY\n" + 
				"        o.fr_id,\n" + 
				"        i.item_grp1 ASC,\n" + 
				"        i.item_grp2,\n" + 
				"        i.item_name,\n" + 
				"        i.item_sort_id ASC,\n" + 
				"        i.item_mrp2 ASC\n" + 
				") t1",nativeQuery=true)
		List<PDispatchReport> getDispatchChkListIfRegOrd(@Param("productionDateYMD")String productionDateYMD,@Param("frId") List<String> frId, @Param("menuId") List<Integer> menuId); 
		
		
		//-------------------------------------------------------
		
	
	
	//sumit
	@Query(value="select t_order.order_id,m_category.cat_id,m_category.cat_name,m_cat_sub.sub_cat_id,t_order.fr_id,m_franchisee.fr_name,t_order.item_id,m_item.item_name,SUM(t_order.order_qty) as order_qty,SUM(t_order.edit_qty) as edit_qty,t_order.is_bill_generated, 0 as adv_qty from t_order,m_category,m_franchisee,m_item,m_cat_sub\n" + 
			"	where t_order.order_id In(select order_id from t_order where delivery_date=:productionDateYMD and fr_id in(:frId) And t_order.menu_id in(:menu) and t_order.item_id in(:ItemId) )  \n" + 
			"	And (select cat_id from m_fr_menu_show where menu_id=t_order.menu_id)=m_category.cat_id And t_order.fr_id=m_franchisee.fr_id and m_item.id=t_order.item_id And m_cat_sub.sub_cat_id=m_item.item_grp2 \n" + 
			"	group by t_order.item_id,t_order.fr_id order by t_order.fr_id,m_item.item_grp1 asc,m_item.item_grp2,m_item.item_name,m_item.item_sort_id asc,m_item.item_mrp2 asc" + 
			"",nativeQuery=true)
	List<PDispatchReport> getPDispatchItemReportMenuwise(@Param("productionDateYMD")String productionDateYMD,@Param("frId") List<String> frId,@Param("menu") List<Integer> menu,@Param("ItemId") List<Integer> ItemId);// cat_id changed to to sub_cat_id (select sub_cat_id from m_fr_menu_show where menu_id=)

//---------------------------------------------------------DispatchReport Franchise wise Special Cake ----SUMIT---19 APRIL 2019------------------------------------------------
	@Query(value="select t_sp_cake.sp_order_no AS order_id,t_sp_cake.fr_id,m_franchisee.fr_name,t_sp_cake.sp_id as item_id,t_sp_cake.item_id as item_name,COUNT(t_sp_cake.sp_order_no) as order_qty,COUNT(t_sp_cake.sp_order_no) as edit_qty,t_sp_cake.is_bill_generated,m_franchisee.fr_route_id AS cat_id, m_franchise_sup.no_in_route AS sub_cat_id,m_franchisee.fr_code as cat_name from t_sp_cake,m_franchisee,m_franchise_sup where t_sp_cake.sp_delivery_date=(:deliveryDateYMD) AND t_sp_cake.menu_id in(:menu) AND m_franchisee.fr_id=t_sp_cake.fr_id AND m_franchisee.fr_id=m_franchise_sup.fr_id AND m_franchisee.fr_id IN (:frId) GROUP BY t_sp_cake.fr_id ORDER BY m_franchisee.fr_route_id,m_franchise_sup.no_in_route" + 
			"",nativeQuery=true)
	List<PDispatchReport> getPDispatchFranchisewiseSpCake(@Param("deliveryDateYMD")String deliveryDateYMD,@Param("frId") List<String> frId,@Param("menu") List<Integer> menu);
}
