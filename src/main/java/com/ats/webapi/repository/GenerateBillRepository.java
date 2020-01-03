package com.ats.webapi.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GenerateBill;

public interface GenerateBillRepository extends JpaRepository<GenerateBill, Integer> {
	
	
	@Query(value=" SELECT t_order.order_id ,t_order.is_positive,coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,t_order.fr_id, t_order.menu_id,t_order.grn_type ,t_order.item_id,t_order.edit_qty as order_qty,"
			+ " t_order.order_rate,t_order.order_mrp ,t_order.delivery_date, m_franchisee.fr_name,m_franchisee.is_same_state,m_franchisee.fr_code,m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,"
			+ " m_item.item_name,m_item.item_grp1,m_item.item_grp2, m_item.item_tax1, m_item.item_tax2, m_item.item_tax3,m_item.item_shelf_life,m_franchisee.fr_name as party_name,m_franchisee.fr_address as party_address,m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr from t_order,m_franchisee, m_fr_menu_show ,"
			+ " m_item where t_order.fr_id IN (:frId) AND t_order.delivery_date=:delDate "
			+ " AND t_order.menu_id IN (:menuId) AND m_franchisee.fr_id=t_order.fr_id AND "
			+ " t_order.menu_id=m_fr_menu_show.menu_id AND t_order.item_id=m_item.id AND t_order.is_bill_generated IN(0,1) AND t_order.edit_qty!=0 ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> generateBill(@Param("frId")List<String> frId,@Param("menuId")List<String> menuId,@Param("delDate")String delDate);

	 
	@Query(value=" SELECT t_order.order_id ,t_order.is_positive,coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,t_order.fr_id, t_order.menu_id,t_order.grn_type ,t_order.item_id,t_order.edit_qty as order_qty,"
			+ " t_order.order_rate,t_order.order_mrp ,t_order.delivery_date, m_franchisee.fr_name,m_franchisee.is_same_state,m_franchisee.fr_code,m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,"
			+ " m_item.item_name,m_item.item_grp1,m_item.item_grp2,m_item.item_tax1, m_item.item_tax2, m_item.item_tax3,m_item.item_shelf_life,m_franchisee.fr_name as party_name,m_franchisee.fr_address as party_address,m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr from t_order,m_franchisee, m_fr_menu_show ,"
			+ " m_item where t_order.delivery_date=:delDate "
			+ " AND t_order.menu_id IN (:menuId) AND m_franchisee.fr_id=t_order.fr_id AND "
			+ " t_order.menu_id=m_fr_menu_show.menu_id AND t_order.item_id=m_item.id AND t_order.is_bill_generated IN(0,1) AND t_order.edit_qty!=0 ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> generateBillForAllFr(@Param("menuId")List<String> menuId,@Param("delDate")String delDate);

	
	
	
	@Query(value=" SELECT t_order.order_id, t_order.is_positive,coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,t_order.fr_id, t_order.menu_id,t_order.grn_type ,t_order.item_id,t_order.edit_qty as order_qty,"
			+ " t_order.order_rate,t_order.order_mrp ,t_order.delivery_date, m_franchisee.fr_name,m_franchisee.is_same_state,m_franchisee.fr_code,m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,"
			+ " m_item.item_name,m_item.item_grp1,m_item.item_grp2,m_item.item_tax1, m_item.item_tax2, m_item.item_tax3,m_item.item_shelf_life,m_franchisee.fr_name as party_name,m_franchisee.fr_address as party_address,m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr from t_order,m_franchisee, m_fr_menu_show ,"
			+ " m_item where t_order.delivery_date=:delDate "
			+ "  AND m_franchisee.fr_id=t_order.fr_id AND "
			+ " t_order.menu_id=m_fr_menu_show.menu_id AND t_order.item_id=m_item.id AND t_order.is_bill_generated IN(0,1) AND t_order.edit_qty!=0 ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> generateBillForAllFrAllMenu(@Param("delDate")String delDate);

	
	
	
	@Query(value=" SELECT t_order.order_id ,t_order.is_positive,coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,t_order.fr_id, t_order.menu_id,t_order.grn_type,t_order.item_id,t_order.edit_qty as order_qty,"
			+ " t_order.order_rate,t_order.order_mrp ,t_order.delivery_date, m_franchisee.fr_name,m_franchisee.fr_code,m_franchisee.is_same_state,m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,"
			+ " m_item.item_name,m_item.item_grp1,m_item.item_grp2,m_item.item_tax1, m_item.item_tax2, m_item.item_tax3,m_item.item_shelf_life,m_franchisee.fr_name as party_name,m_franchisee.fr_address as party_address,m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr from t_order,m_franchisee, m_fr_menu_show ,"
			+ " m_item where t_order.fr_id IN (:frId) AND t_order.delivery_date=:delDate "
			+ " AND m_franchisee.fr_id=t_order.fr_id AND "
			+ " t_order.menu_id=m_fr_menu_show.menu_id AND t_order.item_id=m_item.id AND t_order.is_bill_generated IN(0,1) AND t_order.edit_qty!=0 ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> generateBillForAllMenu(@Param("frId")List<String> frId,@Param("delDate")String delDate);

	@Query(value=" SELECT\n" + 
			"    t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"    t_adv_order_detail.disc_per as is_positive,\n" + 
			"    COALESCE(\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            item_hsncd\n" + 
			"        FROM\n" + 
			"            m_item_sup\n" + 
			"        WHERE\n" + 
			"            m_item_sup.item_id = m_item.id AND m_item_sup.del_status = 0\n" + 
			"    ),\n" + 
			"    '-'\n" + 
			"    ) AS hsn_code,\n" + 
			"    t_adv_order_detail.fr_id,\n" + 
			"    t_adv_order_detail.menu_id,\n" + 
			"    t_adv_order_detail.grn_type,\n" + 
			"    t_adv_order_detail.item_id,\n" + 
			"    t_adv_order_detail.qty AS order_qty,\n" + 
			"    t_adv_order_detail.rate as order_rate,\n" + 
			"    t_adv_order_detail.mrp as order_mrp,\n" + 
			"    t_adv_order_detail.delivery_date,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_code,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_rate_cat,\n" + 
			"    m_fr_menu_show.menu_title,\n" + 
			"    m_item.item_name,\n" + 
			"    m_item.item_grp1,\n" + 
			"    m_item.item_grp2,\n" + 
			"    m_item.item_tax1,\n" + 
			"    m_item.item_tax2,\n" + 
			"    m_item.item_tax3,\n" + 
			"    m_item.item_shelf_life,\n" + 
			"    m_franchisee.fr_name AS party_name,\n" + 
			"    m_franchisee.fr_address AS party_address,\n" + 
			"    m_franchisee.fr_gst_no AS party_gstin,\n" + 
			"    m_franchisee.kg_1 AS is_own_fr\n" + 
			"FROM\n" + 
			"    t_adv_order_detail,\n" + 
			"    m_franchisee,\n" + 
			"    m_fr_menu_show,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    t_adv_order_detail.adv_detail_id IN(:idList) AND m_franchisee.fr_id = t_adv_order_detail.fr_id AND t_adv_order_detail.menu_id = m_fr_menu_show.menu_id AND t_adv_order_detail.item_id = m_item.id AND t_adv_order_detail.is_bill_generated IN(0, 1) AND t_adv_order_detail.qty != 0\n" + 
			"ORDER BY\n" + 
			"    m_franchisee.fr_id,\n" + 
			"    m_item.item_grp1,\n" + 
			"    m_item.item_grp2,\n" + 
			"    m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> getBillOfAdvOrder(@Param("idList")ArrayList<Integer> list);
	
	
	@Query(value=" SELECT t_order.order_id ,t_order.is_positive,coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,t_order.fr_id, t_order.menu_id,t_order.grn_type,t_order.item_id,t_order.edit_qty as order_qty,"
			+ " t_order.order_rate,t_order.order_mrp ,t_order.delivery_date, m_franchisee.fr_name,m_franchisee.fr_code,m_franchisee.is_same_state,m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,"
			+ " m_item.item_name,m_item.item_grp1,m_item.item_grp2,m_item.item_tax1, m_item.item_tax2, m_item.item_tax3,m_item.item_shelf_life,m_franchisee.fr_name as party_name,m_franchisee.fr_address as party_address,m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr from t_order,m_franchisee, m_fr_menu_show ,"
			+ " m_item where  t_order.order_id IN (:idList) and  m_franchisee.fr_id=t_order.fr_id AND "
			+ " t_order.menu_id=m_fr_menu_show.menu_id AND t_order.item_id=m_item.id AND t_order.is_bill_generated IN(0,1) AND t_order.edit_qty!=0 ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name " + 
			"",nativeQuery=true)
	List<GenerateBill> getBillOfOrder(@Param("idList")ArrayList<Integer> list);

	@Query(value="SELECT t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"t_adv_order_detail.disc_per as  is_positive,\n" + 
			"coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,\n" + 
			"t_adv_order_detail.fr_id,\n" + 
			" t_adv_order_detail.menu_id,\n" + 
			"t_adv_order_detail.grn_type,\n" + 
			"t_adv_order_detail.item_id,\n" + 
			"t_adv_order_detail.qty as order_qty,\n" + 
			"t_adv_order_detail.rate as order_rate,\n" + 
			"t_adv_order_detail.mrp as order_mrp,\n" + 
			"t_adv_order_detail.delivery_date,\n" + 
			"m_franchisee.fr_name,\n" + 
			"m_franchisee.is_same_state,\n" + 
			"m_franchisee.fr_code,\n" + 
			"m_franchisee.fr_rate_cat,\n" + 
			"m_fr_menu_show.menu_title,\n" + 
			"m_item.item_name,\n" + 
			"m_item.item_grp1,\n" + 
			"m_item.item_grp2,\n" + 
			"m_item.item_tax1,\n" + 
			"m_item.item_tax2,\n" + 
			"m_item.item_tax3,\n" + 
			"m_item.item_shelf_life,\n" + 
			"m_franchisee.fr_name as party_name,\n" + 
			"m_franchisee.fr_address as party_address,\n" + 
			"m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr \n" + 
			"from t_adv_order_detail,m_franchisee, m_fr_menu_show ,\n" + 
			"m_item where t_adv_order_detail.fr_id IN (:frId) AND t_adv_order_detail.delivery_date=:delDate " + 
			"AND t_adv_order_detail.menu_id IN (:menuId) AND m_franchisee.fr_id=t_adv_order_detail.fr_id AND " + 
			"t_adv_order_detail.menu_id=m_fr_menu_show.menu_id AND t_adv_order_detail.item_id=m_item.id AND t_adv_order_detail.is_bill_generated IN(0,1) ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<GenerateBill> generateBillOfAdvOrder(@Param("frId")List<String> frId,@Param("menuId")List<String> menuId,@Param("delDate")String delDate);

	@Query(value="SELECT t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"t_adv_order_detail.disc_per as  is_positive,\n" + 
			"coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,\n" + 
			"t_adv_order_detail.fr_id,\n" + 
			" t_adv_order_detail.menu_id,\n" + 
			"t_adv_order_detail.grn_type,\n" + 
			"t_adv_order_detail.item_id,\n" + 
			"t_adv_order_detail.qty as order_qty,\n" + 
			"t_adv_order_detail.rate as order_rate,\n" + 
			"t_adv_order_detail.mrp as order_mrp,\n" + 
			"t_adv_order_detail.delivery_date,\n" + 
			"m_franchisee.fr_name,\n" + 
			"m_franchisee.is_same_state,\n" + 
			"m_franchisee.fr_code,\n" + 
			"m_franchisee.fr_rate_cat,\n" + 
			"m_fr_menu_show.menu_title,\n" + 
			"m_item.item_name,\n" + 
			"m_item.item_grp1,\n" + 
			"m_item.item_grp2,\n" + 
			"m_item.item_tax1,\n" + 
			"m_item.item_tax2,\n" + 
			"m_item.item_tax3,\n" + 
			"m_item.item_shelf_life,\n" + 
			"m_franchisee.fr_name as party_name,\n" + 
			"m_franchisee.fr_address as party_address,\n" + 
			"m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr \n" + 
			"from t_adv_order_detail,m_franchisee, m_fr_menu_show ,\n" + 
			"m_item where  t_adv_order_detail.delivery_date=:delDate " + 
			"AND t_adv_order_detail.menu_id IN (:menuId) AND m_franchisee.fr_id=t_adv_order_detail.fr_id AND " + 
			"t_adv_order_detail.menu_id=m_fr_menu_show.menu_id AND t_adv_order_detail.item_id=m_item.id AND t_adv_order_detail.is_bill_generated IN(0,1) ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<GenerateBill> generateBillForAllFrAdvOrder(@Param("menuId")List<String> menuId,@Param("delDate")String delDate);

	@Query(value="SELECT t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"t_adv_order_detail.disc_per as  is_positive,\n" + 
			"coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,\n" + 
			"t_adv_order_detail.fr_id,\n" + 
			" t_adv_order_detail.menu_id,\n" + 
			"t_adv_order_detail.grn_type,\n" + 
			"t_adv_order_detail.item_id,\n" + 
			"t_adv_order_detail.qty as order_qty,\n" + 
			"t_adv_order_detail.rate as order_rate,\n" + 
			"t_adv_order_detail.mrp as order_mrp,\n" + 
			"t_adv_order_detail.delivery_date,\n" + 
			"m_franchisee.fr_name,\n" + 
			"m_franchisee.is_same_state,\n" + 
			"m_franchisee.fr_code,\n" + 
			"m_franchisee.fr_rate_cat,\n" + 
			"m_fr_menu_show.menu_title,\n" + 
			"m_item.item_name,\n" + 
			"m_item.item_grp1,\n" + 
			"m_item.item_grp2,\n" + 
			"m_item.item_tax1,\n" + 
			"m_item.item_tax2,\n" + 
			"m_item.item_tax3,\n" + 
			"m_item.item_shelf_life,\n" + 
			"m_franchisee.fr_name as party_name,\n" + 
			"m_franchisee.fr_address as party_address,\n" + 
			"m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr \n" + 
			"from t_adv_order_detail,m_franchisee, m_fr_menu_show ,\n" + 
			"m_item where  t_adv_order_detail.delivery_date=:delDate " + 
			"AND m_franchisee.fr_id=t_adv_order_detail.fr_id AND " + 
			"t_adv_order_detail.menu_id=m_fr_menu_show.menu_id AND t_adv_order_detail.item_id=m_item.id AND t_adv_order_detail.is_bill_generated IN(0,1) ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
		List<GenerateBill> generateBillForAllFrAllMenuAdvOrder(@Param("delDate")String delDate);

	@Query(value="SELECT t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"t_adv_order_detail.disc_per as  is_positive,\n" + 
			"coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,\n" + 
			"t_adv_order_detail.fr_id,\n" + 
			" t_adv_order_detail.menu_id,\n" + 
			"t_adv_order_detail.grn_type,\n" + 
			"t_adv_order_detail.item_id,\n" + 
			"t_adv_order_detail.qty as order_qty,\n" + 
			"t_adv_order_detail.rate as order_rate,\n" + 
			"t_adv_order_detail.mrp as order_mrp,\n" + 
			"t_adv_order_detail.delivery_date,\n" + 
			"m_franchisee.fr_name,\n" + 
			"m_franchisee.is_same_state,\n" + 
			"m_franchisee.fr_code,\n" + 
			"m_franchisee.fr_rate_cat,\n" + 
			"m_fr_menu_show.menu_title,\n" + 
			"m_item.item_name,\n" + 
			"m_item.item_grp1,\n" + 
			"m_item.item_grp2,\n" + 
			"m_item.item_tax1,\n" + 
			"m_item.item_tax2,\n" + 
			"m_item.item_tax3,\n" + 
			"m_item.item_shelf_life,\n" + 
			"m_franchisee.fr_name as party_name,\n" + 
			"m_franchisee.fr_address as party_address,\n" + 
			"m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr \n" + 
			"from t_adv_order_detail,m_franchisee, m_fr_menu_show ,\n" + 
			"m_item where t_adv_order_detail.fr_id IN (:frId) AND t_adv_order_detail.delivery_date=:delDate " + 
			"AND m_franchisee.fr_id=t_adv_order_detail.fr_id AND " + 
			"t_adv_order_detail.menu_id=m_fr_menu_show.menu_id AND t_adv_order_detail.item_id=m_item.id AND t_adv_order_detail.is_bill_generated IN(0,1) ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
		List<GenerateBill> generateBillForAllMenuForAdvOrder(@Param("frId")List<String> frId,@Param("delDate") String delDate);

//	@Query(value="SELECT t_sp_cake.sp_order_no ,t_sp_cake.fr_id, t_sp_cake.sp_id,t_sp_cake.menu_id, t_sp_cake.sp_backend_rate, t_sp_cake.sp_grand_total, t_sp_cake.fr_code,t_sp_cake.sp_delivery_date ,"
//			+ " m_franchisee.fr_name, m_franchisee.fr_rate_cat, m_fr_menu_show.menu_title,m_fr_menu_show.cat_id , m_sp_cake.sp_tax1, m_sp_cake.sp_tax2,m_sp_cake.sp_tax3, m_sp_cake.sp_name ,m_cat_sub.sub_cat_id "
//			+ "FROM t_sp_cake , m_franchisee , m_fr_menu_show , m_sp_cake,m_cat_sub WHERE t_sp_cake.fr_id IN (:frId) AND t_sp_cake.sp_delivery_date='2017-10-18' "
//			+ "AND m_franchisee.fr_id=t_sp_cake.fr_id AND m_fr_menu_show.menu_id=t_sp_cake.menu_id AND m_sp_cake.sp_id=t_sp_cake.sp_id AND m_cat_sub.cat_id=m_fr_menu_show.cat_id",nativeQuery=true)
//	List<GenerateBill> generateSpBillForAllMenu(@Param("frId")List<String> frId,@Param("delDate")String delDate);

	//Sachin 2-01-2019
	
	@Query(value="SELECT t_adv_order_detail.adv_detail_id as order_id,\n" + 
			"t_adv_order_detail.disc_per as  is_positive,\n" + 
			"coalesce((select item_hsncd from m_item_sup where m_item_sup.item_id=m_item.id and m_item_sup.del_status=0),'-') as hsn_code,\n" + 
			"t_adv_order_detail.fr_id,\n" + 
			" t_adv_order_detail.menu_id,\n" + 
			"t_adv_order_detail.grn_type,\n" + 
			"t_adv_order_detail.item_id,\n" + 
			"t_adv_order_detail.qty as order_qty,\n" + 
			"t_adv_order_detail.rate as order_rate,\n" + 
			"t_adv_order_detail.mrp as order_mrp,\n" + 
			"t_adv_order_detail.delivery_date,\n" + 
			"m_franchisee.fr_name,\n" + 
			"m_franchisee.is_same_state,\n" + 
			"m_franchisee.fr_code,\n" + 
			"m_franchisee.fr_rate_cat,\n" + 
			"m_fr_menu_show.menu_title,\n" + 
			"m_item.item_name,\n" + 
			"m_item.item_grp1,\n" + 
			"m_item.item_grp2,\n" + 
			"m_item.item_tax1,\n" + 
			"m_item.item_tax2,\n" + 
			"m_item.item_tax3,\n" + 
			"m_item.item_shelf_life,\n" + 
			"m_franchisee.fr_name as party_name,\n" + 
			"m_franchisee.fr_address as party_address,\n" + 
			"m_franchisee.fr_gst_no as party_gstin,m_franchisee.kg_1 as is_own_fr \n" + 
			"from t_adv_order_detail,m_franchisee, m_fr_menu_show ,\n" + 
			"m_item where    " + 
			"    m_franchisee.fr_id=t_adv_order_detail.fr_id AND " + 
			"t_adv_order_detail.menu_id=m_fr_menu_show.menu_id AND t_adv_order_detail.item_id=m_item.id AND t_adv_order_detail.is_bill_generated IN(0,1) and t_adv_order_detail.adv_header_id=:advOrderId ORDER BY m_franchisee.fr_id,m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
	List<GenerateBill> generateBillOfAdvOrderByAdvOrderId(@Param("advOrderId")int  advOrderId);

	
	
	
}
