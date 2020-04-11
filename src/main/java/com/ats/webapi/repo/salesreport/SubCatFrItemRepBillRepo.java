package com.ats.webapi.repo.salesreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport.SubCatFrItemRepBill;

public interface SubCatFrItemRepBillRepo extends JpaRepository<SubCatFrItemRepBill, Integer> {

	@Query(value = "  SELECT UUID() as id," + 
			"        td.bill_detail_no,\n" + 
			"        SUM(td.grand_total) AS sold_amt,\n" + 
			"        SUM(td.bill_qty) AS sold_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name ,\n" + 
			"        f.fr_id ,\n" + 
			"        m_item.id as item_id,\n" + 
			"        m_item.item_name     \n" + 
			"    FROM\n" + 
			"        t_bill_header tb,\n" + 
			"        t_bill_detail td,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_item      \n" + 
			"    WHERE\n" + 
			"        tb.del_status=0          \n" + 
			"        AND tb.fr_id IN(\n" + 
			"           :frIdList \n" + 
			"        )          \n" + 
			"        AND tb.bill_no=td.bill_no          \n" + 
			"        AND tb.bill_date BETWEEN :fromDate AND :toDate \n" + 
			"        AND f.fr_id=tb.fr_id          \n" + 
			"        AND m_item.id=td.item_id          \n" + 
			"        AND m_item.item_grp2=sc.sub_cat_id   \n" + 
			"          AND td.cat_id!=5 \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"           :subCatIdList   \n" + 
			"        )       \n" + 
			"    GROUP BY\n" + 
			"        tb.fr_id, \n" + 
			"        m_item.id   \n" + 
			"        \n" + 
			"        \n" + 
			"        UNION All\n" + 
			"        \n" + 
			"        SELECT UUID() as id," + 
			"        td.bill_detail_no,\n" + 
			"        SUM(td.grand_total) AS sold_amt,\n" + 
			"        SUM(td.bill_qty) AS sold_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name ,\n" + 
			"        f.fr_id ,\n" + 
			"        m_sp_cake.sp_id as item_id,\n" + 
			"        m_sp_cake.sp_name as item_name     \n" + 
			"    FROM\n" + 
			"        t_bill_header tb,\n" + 
			"        t_bill_detail td,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_sp_cake      \n" + 
			"    WHERE\n" + 
			"        tb.del_status=0          \n" + 
			"        AND tb.fr_id IN(\n" + 
			"         :frIdList\n" + 
			"        )          \n" + 
			"        AND tb.bill_no=td.bill_no          \n" + 
			"        AND tb.bill_date BETWEEN :fromDate AND :toDate\n" + 
			"        AND f.fr_id=tb.fr_id          \n" + 
			"        AND m_sp_cake.sp_id=td.item_id          \n" + 
			"          \n" + 
			"          AND td.cat_id=5  AND sc.cat_id=5 \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"          :subCatIdList   \n" + 
			"        )       \n" + 
			"    GROUP BY\n" + 
			"        tb.fr_id,\n" + 
			"        m_sp_cake.sp_id   order by fr_name,sub_cat_id,item_name ", nativeQuery = true)
	List<SubCatFrItemRepBill> getData(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	
	
	//Anmol---24-02-2020--
	@Query(value = "  SELECT\n" + 
			"    UUID() AS id, td.bill_detail_no, SUM(td.grand_total) AS sold_amt,\n" + 
			"    SUM(td.bill_qty) AS sold_qty,\n" + 
			"    f.fr_name,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    f.fr_id,\n" + 
			"    m_item.id AS item_id,\n" + 
			"    m_item.item_name\n" + 
			"FROM\n" + 
			"    t_bill_header tb,\n" + 
			"    t_bill_detail td,\n" + 
			"    m_franchisee f,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    tb.del_status = 0 AND tb.fr_id IN(:frIdList) AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = tb.fr_id AND m_item.id = td.item_id AND m_item.item_grp2 = sc.sub_cat_id AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_stockable = 1 AND tb.ex_varchar2 IN(:type)\n" + 
			"GROUP BY\n" + 
			"    tb.fr_id,\n" + 
			"    m_item.id ", nativeQuery = true)
	List<SubCatFrItemRepBill> getAdminData(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList,@Param("type") List<Integer> type);

	
	//Anmol--24-02-2020--
	@Query(value = "  SELECT  \n" + 
			"			    UUID() AS id, td.sell_bill_detail_no as bill_detail_no, SUM(td.ext_float1) AS sold_amt,  \n" + 
			"			    SUM(td.qty) AS sold_qty,  \n" + 
			"			    f.fr_name,  \n" + 
			"			    sc.sub_cat_id,  \n" + 
			"			    sc.sub_cat_name,  \n" + 
			"			    f.fr_id,  \n" + 
			"			    m_item.id AS item_id,  \n" + 
			"			    m_item.item_name  \n" + 
			"			FROM  \n" + 
			"			    t_sell_bill_header tb,  \n" + 
			"			    t_sell_bill_detail td,  \n" + 
			"			    m_franchisee f,  \n" + 
			"			    m_cat_sub sc,  \n" + 
			"			    m_item  \n" + 
			"			WHERE  \n" + 
			"			    tb.del_status = 0 AND tb.fr_id IN(:frIdList) AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = tb.fr_id AND m_item.id = td.item_id AND m_item.item_grp2 = sc.sub_cat_id AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_saleable = 1 AND tb.del_status=0\n" + 
			"			GROUP BY  \n" + 
			"			    tb.fr_id,  \n" + 
			"			    m_item.id ", nativeQuery = true)
	List<SubCatFrItemRepBill> getAdminDataCompOutlet(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	
	//Anmol--10-04-2020-- COMP OUTLET DAIRY MART AND REGULAR
		@Query(value = "  SELECT \n" + 
				"id,\n" + 
				"bill_detail_no,\n" + 
				"SUM(sold_amt) as sold_amt,\n" + 
				"SUM(sold_qty) as sold_qty,\n" + 
				"fr_name,\n" + 
				"sub_cat_id,\n" + 
				"sub_cat_name,\n" + 
				"fr_id,\n" + 
				"item_id,\n" + 
				"item_name\n" + 
				"\n" + 
				"FROM\n" + 
				"(\n" + 
				"SELECT\n" + 
				"    UUID() AS id, td.bill_detail_no, SUM(td.grand_total) AS sold_amt,\n" + 
				"    SUM(td.bill_qty) AS sold_qty,\n" + 
				"    f.fr_name,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    f.fr_id,\n" + 
				"    m_item.id AS item_id,\n" + 
				"    m_item.item_name\n" + 
				"FROM\n" + 
				"    t_bill_header tb,\n" + 
				"    t_bill_detail td,\n" + 
				"    m_franchisee f,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item\n" + 
				"WHERE\n" + 
				"    tb.del_status = 0 AND tb.fr_id IN(:frIdList) AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = tb.fr_id AND m_item.id = td.item_id AND m_item.item_grp2 = sc.sub_cat_id AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_stockable = 1 AND tb.is_dairy_mart = 2\n" + 
				"GROUP BY\n" + 
				"    tb.fr_id,\n" + 
				"    m_item.id\n" + 
				"    \n" + 
				"    UNION\n" + 
				"    (\n" + 
				"        SELECT\n" + 
				"    UUID() AS id, td.sell_bill_detail_no AS bill_detail_no, SUM(td.ext_float1) AS sold_amt,\n" + 
				"    SUM(td.qty) AS sold_qty,\n" + 
				"    f.fr_name,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    f.fr_id,\n" + 
				"    m_item.id AS item_id,\n" + 
				"    m_item.item_name\n" + 
				"FROM\n" + 
				"    t_sell_bill_header tb,\n" + 
				"    t_sell_bill_detail td,\n" + 
				"    m_franchisee f,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item\n" + 
				"WHERE\n" + 
				"    tb.del_status = 0 AND tb.fr_id IN(:frIdList) AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = tb.fr_id AND m_item.id = td.item_id AND m_item.item_grp2 = sc.sub_cat_id AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_saleable = 1 AND tb.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    tb.fr_id,\n" + 
				"    m_item.id\n" + 
				"    )\n" + 
				"    ) t1 GROUP BY fr_id,item_id ", nativeQuery = true)
		List<SubCatFrItemRepBill> getAdminDataCompOutletDairyAndRegular(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
				@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);

		
		//Anmol--10-04-2020-- COMP OUTLET  DAIRY MART
		@Query(value = "  SELECT\n" + 
				"    UUID() AS id, td.bill_detail_no, SUM(td.grand_total) AS sold_amt,\n" + 
				"    SUM(td.bill_qty) AS sold_qty,\n" + 
				"    f.fr_name,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    f.fr_id,\n" + 
				"    m_item.id AS item_id,\n" + 
				"    m_item.item_name\n" + 
				"FROM\n" + 
				"    t_bill_header tb,\n" + 
				"    t_bill_detail td,\n" + 
				"    m_franchisee f,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item\n" + 
				"WHERE\n" + 
				"    tb.del_status = 0 AND tb.fr_id IN(:frIdList) AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = tb.fr_id AND m_item.id = td.item_id AND m_item.item_grp2 = sc.sub_cat_id AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_stockable = 1 AND tb.is_dairy_mart = 2\n" + 
				"GROUP BY\n" + 
				"    tb.fr_id,\n" + 
				"    m_item.id ", nativeQuery = true)
		List<SubCatFrItemRepBill> getAdminDataCompOutletDairymart(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
				@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
		
}
