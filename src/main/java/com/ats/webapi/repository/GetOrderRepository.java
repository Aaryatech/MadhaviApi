package com.ats.webapi.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.Franchisee;
import com.ats.webapi.model.GetOrder;
import com.ats.webapi.model.Order;
@Repository
public interface GetOrderRepository extends JpaRepository<GetOrder, Integer>{
	
	
	/*@Query(value="SELECT m_franchisee.fr_name ,m_item.id ,m_item.item_name ,t_order.order_id,t_order.order_qty ,"
			+ "m_category.cat_name,t_order.delivery_date,"
			+ " t_order.is_edit, t_order.edit_qty,t_order.is_positive FROM m_franchisee ,m_category,"
			+ "m_item,t_order WHERE t_order.production_date = :date AND t_order.is_edit=0 AND t_order.fr_id IN (:frId) AND "
			+ "t_order.item_id = m_item.id AND t_order.menu_id IN (:menuId) AND t_order.fr_id = m_franchisee.fr_id AND "
			+ "t_order.order_type = m_category.cat_id ORDER BY m_franchisee.fr_id, m_category.cat_id,m_item.item_grp2, m_item.item_name",nativeQuery=true)
				List<GetOrder> findAllNative(@Param("frId")List<String>  frId,@Param("menuId") List<String> menuId,@Param("date")String date);
*/

	
	//Anmol
	@Query(value="SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    t1.order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    t1.is_edit,\n" + 
			"    t1.edit_qty,\n" + 
			"    t1.is_positive,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            m_franchisee.fr_name,\n" + 
			"            ' - ',\n" + 
			"            m_franchisee.fr_code\n" + 
			"        ) AS fr_name,\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        t_order.order_id,\n" + 
			"        t_order.order_qty,\n" + 
			"        m_category.cat_name,\n" + 
			"        t_order.delivery_date,\n" + 
			"        t_order.is_edit,\n" + 
			"        t_order.edit_qty,\n" + 
			"        t_order.is_positive,\n" + 
			"        t_order.fr_id\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        m_category,\n" + 
			"        m_item,\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0 AND t_order.fr_id IN(:frId) AND t_order.item_id = m_item.id AND t_order.menu_id IN(:menuId) AND t_order.fr_id = m_franchisee.fr_id AND t_order.order_type = m_category.cat_id\n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_category.cat_id,\n" + 
			"        m_item.item_grp2,\n" + 
			"        m_item.item_name\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.fr_id,\n" + 
			"        d.adv_detail_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.fr_id IN(:frId) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.id = t2.id AND t1.fr_id = t2.fr_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    0 AS order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    0 AS is_edit,\n" + 
			"    0 AS edit_qty,\n" + 
			"    0 AS is_positive,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        CONCAT(f.fr_name, ' - ', f.fr_code) AS fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.delivery_date, d.fr_id \n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.fr_id IN(:frId) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.cat_id IN(\n" + 
			"        SELECT\n" + 
			"            cat_id\n" + 
			"        FROM\n" + 
			"            m_fr_menu_show\n" + 
			"        WHERE\n" + 
			"            menu_id IN(:menuId)\n" + 
			"    )\n" + 
			"GROUP BY\n" + 
			"    d.item_id,\n" + 
			"    d.fr_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.id NOT IN(\n" + 
			"    SELECT\n" + 
			"       t_order.item_id\n" + 
			"    FROM\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0  AND t_order.menu_id IN(:menuId) AND t_order.fr_id IN(t1.fr_id)\n" + 
			")",nativeQuery=true)
				List<GetOrder> findAllNative(@Param("frId")List<String>  frId,@Param("menuId") List<String> menuId,@Param("date")String date);

	
	
	
	// if  all franchisees selected
	
	/*@Query(value="SELECT m_franchisee.fr_name ,m_item.id ,m_item.item_name ,t_order.order_id,t_order.order_qty ,"
			+ "m_category.cat_name,t_order.delivery_date,"
			+ " t_order.is_edit, t_order.edit_qty,t_order.is_positive FROM m_franchisee ,m_category,"
			+ "m_item,t_order WHERE t_order.production_date = :date AND t_order.is_edit=0 AND "
			+ "t_order.item_id = m_item.id AND t_order.menu_id IN (:menuId) AND t_order.fr_id = m_franchisee.fr_id AND "
			+ "t_order.order_type = m_category.cat_id ORDER BY m_franchisee.fr_id, m_category.cat_id,m_item.item_grp2,m_item.item_name",nativeQuery=true)
				List<GetOrder> findAllNativeAllFr(@Param("menuId")List<String> menuId,@Param("date")String date);*/
	
	
	
	//Anmol all fr selected
	@Query(value="SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    t1.order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    t1.is_edit,\n" + 
			"    t1.edit_qty,\n" + 
			"    t1.is_positive,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            m_franchisee.fr_name,\n" + 
			"            ' - ',\n" + 
			"            m_franchisee.fr_code\n" + 
			"        ) AS fr_name,\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        t_order.order_id,\n" + 
			"        t_order.order_qty,\n" + 
			"        m_category.cat_name,\n" + 
			"        t_order.delivery_date,\n" + 
			"        t_order.is_edit,\n" + 
			"        t_order.edit_qty,\n" + 
			"        t_order.is_positive,\n" + 
			"        t_order.fr_id\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        m_category,\n" + 
			"        m_item,\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0 AND t_order.item_id = m_item.id AND t_order.menu_id IN(:menuId) AND t_order.fr_id = m_franchisee.fr_id AND t_order.order_type = m_category.cat_id\n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_category.cat_id,\n" + 
			"        m_item.item_grp2,\n" + 
			"        m_item.item_name\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.fr_id,\n" + 
			"        d.adv_detail_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.id = t2.id AND t1.fr_id = t2.fr_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    0 AS order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    0 AS is_edit,\n" + 
			"    0 AS edit_qty,\n" + 
			"    0 AS is_positive,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        CONCAT(f.fr_name, ' - ', f.fr_code) AS fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.delivery_date, d.fr_id \n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.cat_id IN(\n" + 
			"        SELECT\n" + 
			"            cat_id\n" + 
			"        FROM\n" + 
			"            m_fr_menu_show\n" + 
			"        WHERE\n" + 
			"            menu_id IN(:menuId)\n" + 
			"    )\n" + 
			"GROUP BY\n" + 
			"    d.item_id,\n" + 
			"    d.fr_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.id NOT IN(\n" + 
			"    SELECT\n" + 
			"       t_order.item_id\n" + 
			"    FROM\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0  AND t_order.menu_id IN(:menuId)  AND t_order.fr_id IN(t1.fr_id)\n" + 
			")",nativeQuery=true)
				List<GetOrder> findAllNativeAllFr(@Param("menuId")List<String> menuId,@Param("date")String date);
	
	

	//Mahesh -2 APR-Pune
	/*@Query(value="SELECT m_franchisee.fr_name ,m_item.id ,m_item.item_name ,t_order.order_id,t_order.order_qty ,"
			+ "m_category.cat_name,t_order.delivery_date,"
			+ " t_order.is_edit, t_order.edit_qty,t_order.is_positive FROM m_franchisee ,m_category,"
			+ "m_item,t_order WHERE t_order.production_date = :date AND t_order.is_edit=0 AND "
			+ "t_order.item_id = m_item.id AND t_order.menu_id IN (:menuId) AND t_order.item_id IN(:itemId) AND t_order.fr_id = m_franchisee.fr_id AND "
			+ "t_order.order_type = m_category.cat_id ORDER BY m_franchisee.fr_id, m_category.cat_id,m_item.item_grp2,m_item.item_name",nativeQuery=true)
	
	List<GetOrder> findAllNativeAllFrByItem(@Param("menuId")List<String> menuId,@Param("date") String date,@Param("itemId") List<Integer> itemId);
	*/
	
	
	//Anmol
	@Query(value="SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    t1.order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    t1.is_edit,\n" + 
			"    t1.edit_qty,\n" + 
			"    t1.is_positive,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            m_franchisee.fr_name,\n" + 
			"            ' - ',\n" + 
			"            m_franchisee.fr_code\n" + 
			"        ) AS fr_name,\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        t_order.order_id,\n" + 
			"        t_order.order_qty,\n" + 
			"        m_category.cat_name,\n" + 
			"        t_order.delivery_date,\n" + 
			"        t_order.is_edit,\n" + 
			"        t_order.edit_qty,\n" + 
			"        t_order.is_positive,\n" + 
			"        t_order.fr_id\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        m_category,\n" + 
			"        m_item,\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0 AND t_order.item_id = m_item.id AND t_order.menu_id IN(:menuId) AND t_order.item_id IN(:itemId) AND t_order.fr_id = m_franchisee.fr_id AND t_order.order_type = m_category.cat_id\n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_category.cat_id,\n" + 
			"        m_item.item_grp2,\n" + 
			"        m_item.item_name\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.fr_id,\n" + 
			"        d.adv_detail_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.item_id IN(:itemId)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.id = t2.id AND t1.fr_id = t2.fr_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    0 AS order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    0 AS is_edit,\n" + 
			"    0 AS edit_qty,\n" + 
			"    0 AS is_positive,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        CONCAT(f.fr_name, ' - ', f.fr_code) AS fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.delivery_date, d.fr_id \n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.item_id IN(:itemId)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,d.fr_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.id NOT IN(\n" + 
			"    SELECT\n" + 
			"        t_order.item_id\n" + 
			"    FROM\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = '2020-06-18' AND t_order.is_edit = 0 AND t_order.menu_id IN(:menuId) AND t_order.fr_id IN(t1.fr_id) AND t_order.item_id IN(:itemId)\n" + 
			")",nativeQuery=true)
	
	List<GetOrder> findAllNativeAllFrByItem(@Param("menuId")List<String> menuId,@Param("date") String date,@Param("itemId") List<Integer> itemId);
	
	
	
	//Mahesh -2 APR-Pune
	/*@Query(value="SELECT m_franchisee.fr_name ,m_item.id ,m_item.item_name ,t_order.order_id,t_order.order_qty ,"
			+ "m_category.cat_name,t_order.delivery_date,"
			+ " t_order.is_edit, t_order.edit_qty,t_order.is_positive FROM m_franchisee ,m_category,"
			+ "m_item,t_order WHERE t_order.production_date = :date AND t_order.is_edit=0 AND t_order.fr_id IN (:frId) AND "
			+ "t_order.item_id = m_item.id AND t_order.menu_id IN (:menuId) And t_order.item_id IN(:itemId) AND t_order.fr_id = m_franchisee.fr_id AND "
			+ "t_order.order_type = m_category.cat_id ORDER BY m_franchisee.fr_id, m_category.cat_id,m_item.item_grp2, m_item.item_name",nativeQuery=true)
	
	List<GetOrder> findAllNativeByItemId(@Param("frId")List<String> frId,@Param("menuId") List<String> menuId,@Param("date") String date,@Param("itemId") List<Integer> itemId);
*/
	
	
	@Query(value="SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    t1.order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    t1.is_edit,\n" + 
			"    t1.edit_qty,\n" + 
			"    t1.is_positive,\n" + 
			"    COALESCE((t2.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            m_franchisee.fr_name,\n" + 
			"            ' - ',\n" + 
			"            m_franchisee.fr_code\n" + 
			"        ) AS fr_name,\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        t_order.order_id,\n" + 
			"        t_order.order_qty,\n" + 
			"        m_category.cat_name,\n" + 
			"        t_order.delivery_date,\n" + 
			"        t_order.is_edit,\n" + 
			"        t_order.edit_qty,\n" + 
			"        t_order.is_positive,\n" + 
			"        t_order.fr_id\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        m_category,\n" + 
			"        m_item,\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0 AND t_order.fr_id IN(:frId) AND t_order.item_id = m_item.id AND t_order.menu_id IN(:menuId) AND t_order.item_id IN(:itemId) AND t_order.fr_id = m_franchisee.fr_id AND t_order.order_type = m_category.cat_id\n" + 
			"    ORDER BY\n" + 
			"        m_franchisee.fr_id,\n" + 
			"        m_category.cat_id,\n" + 
			"        m_item.item_grp2,\n" + 
			"        m_item.item_name\n" + 
			") t1\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        d.fr_id,\n" + 
			"        d.adv_detail_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty\n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.fr_id IN(:frId) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id AND d.item_id IN(:itemId)\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t2\n" + 
			"ON\n" + 
			"    t1.id = t2.id AND t1.fr_id = t2.fr_id\n" + 
			"UNION\n" + 
			"SELECT\n" + 
			"    t1.fr_name,\n" + 
			"    t1.id,\n" + 
			"    t1.item_name,\n" + 
			"    t1.order_id,\n" + 
			"    0 AS order_qty,\n" + 
			"    t1.cat_name,\n" + 
			"    t1.delivery_date,\n" + 
			"    0 AS is_edit,\n" + 
			"    0 AS edit_qty,\n" + 
			"    0 AS is_positive,\n" + 
			"    COALESCE((t1.adv_qty),\n" + 
			"    0) AS adv_qty\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        d.adv_detail_id AS order_id,\n" + 
			"        d.item_id AS id,\n" + 
			"        COALESCE(SUM(d.qty),\n" + 
			"        0) AS adv_qty,\n" + 
			"        CONCAT(f.fr_name, ' - ', f.fr_code) AS fr_name,\n" + 
			"        i.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.delivery_date, d.fr_id \n" + 
			"    FROM\n" + 
			"        t_adv_order_detail d,\n" + 
			"        m_franchisee f,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        d.prod_date = :date AND d.item_id IN(:itemId) AND d.fr_id IN(:frId) AND d.del_status = 0 AND d.item_id = i.id AND d.fr_id = f.fr_id AND d.cat_id = c.cat_id\n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        d.fr_id\n" + 
			") t1\n" + 
			"WHERE\n" + 
			"    t1.id NOT IN(\n" + 
			"    SELECT\n" + 
			"        t_order.item_id\n" + 
			"    FROM\n" + 
			"        t_order\n" + 
			"    WHERE\n" + 
			"        t_order.production_date = :date AND t_order.is_edit = 0 AND t_order.menu_id IN(:menuId) AND t_order.item_id IN(:itemId) AND t_order.fr_id IN(t1.fr_id)\n" + 
			")",nativeQuery=true)
	List<GetOrder> findAllNativeByItemId(@Param("frId")List<String> frId,@Param("menuId") List<String> menuId,@Param("date") String date,@Param("itemId") List<Integer> itemId);
	
	
}

//SELECT m_franchisee.fr_name , m_category.cat_name ,m_item.item_id ,m_item.item_name ,t_order.order_qty , t_order.delivery_date, t_order.is_edit, t_order.edit_qty,t_order.is_positive FROM m_franchisee ,m_category,m_item,t_order WHERE t_order.production_date = '2017-01-01' AND t_order.order_type=1 AND t_order.fr_id IN (10,11) AND t_order.item_id = m_item.item_id AND t_order.order_type = m_category.cat_id AND t_order.fr_id = m_franchisee.fr_id 