package com.ats.webapi.repository.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.prod.ProdItemDeptWiseModel;

public interface ProdItemDeptWiseModelRepo extends JpaRepository<ProdItemDeptWiseModel, Integer> {
	
//	@Query(value = " SELECT\r\n"
//			+		"    UUID() AS id, d.production_detail_id, d.item_id, i.item_name, i.item_grp3 AS dept_id, dp.dept_name, c.cat_name, SUM(d.plan_qty) AS plan_qty,\r\n" + 
//		"    SUM(d.order_qty) AS order_qty,\r\n" + 
//		"    SUM(d.production_qty) AS production_qty,\r\n" + 
//		"    SUM(d.opening_qty) AS opening_qty,\r\n" + 
//		"    CASE WHEN h.is_planned = 1 THEN SUM(d.plan_qty) ELSE SUM(d.order_qty)\r\n" + 
//		"    END AS qty\r\n" + 
//		"FROM\r\n" + 
//		"    t_production_plan_header h,\r\n" + 
//		"    t_production_plan_detail d,\r\n" + 
//		"    m_category c,\r\n" + 
//		"    m_item i,\r\n" + 
//		"    m_item_dept dp\r\n" + 
//		"WHERE\r\n" + 
//		"    c.cat_id = h.cat_id AND h.production_header_id = d.production_header_id AND d.item_id = i.id AND i.item_grp1 = c.cat_id AND i.item_grp3 = dp.dept_id AND h.production_header_id IN(:headerIds) \r\n" + 
//		"GROUP BY\r\n" + 
//		"    d.item_id\r\n" + 
//		"ORDER BY\r\n" + 
//		"    dp.dept_id,\r\n" + 
//		"    i.item_grp1,\r\n" + 
//		"    i.item_grp2,\r\n" + 
//		"    i.item_name",nativeQuery=true)
//	
//	List<ProdItemDeptWiseModel> getDeptWiseItemsForProd(@Param("headerIds") String headerIds);

	
	
	@Query(value = " SELECT\r\n" + 
			"    UUID() AS id, d.production_detail_id, h.production_date, d.item_id, i.item_name, i.item_grp3 AS dept_id, dp.dept_name, c.cat_name, SUM(d.plan_qty) AS plan_qty,\r\n" + 
			"    SUM(d.order_qty) AS order_qty,\r\n" + 
			"    SUM(d.production_qty) AS production_qty,\r\n" + 
			"    SUM(d.opening_qty) AS opening_qty,\r\n" + 
			"    CASE WHEN h.is_planned = 1 THEN SUM(d.plan_qty) ELSE SUM(d.order_qty)\r\n" + 
			"    END AS qty\r\n" + 
			"FROM\r\n" + 
			"    t_production_plan_header h,\r\n" + 
			"    t_production_plan_detail d,\r\n" + 
			"    m_category c,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_dept dp\r\n" + 
			"WHERE\r\n" + 
			"    c.cat_id = h.cat_id AND h.production_header_id = d.production_header_id AND d.item_id = i.id AND i.item_grp1 = c.cat_id AND i.item_grp3 = dp.dept_id AND h.production_header_id IN(:headerIds) \r\n" + 
			"GROUP BY\r\n" + 
			"    h.production_date,\r\n" + 
			"    d.item_id\r\n" + 
			"ORDER BY\r\n" + 
			"    h.production_date,\r\n" + 
			"    dp.dept_id,\r\n" + 
			"    i.item_grp1,\r\n" + 
			"    i.item_grp2,\r\n" + 
			"    i.item_name",nativeQuery=true)
	
	List<ProdItemDeptWiseModel> getDeptWiseItemsForProd(@Param("headerIds") List<Integer> headerIds);

	
}
