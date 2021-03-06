package com.ats.webapi.repo.salesreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport.SubCatCreditGrnFrItemRep;

public interface SubCatCreditGrnFrItemRepRepo extends JpaRepository<SubCatCreditGrnFrItemRep, Integer> {

	@Query(value = " SELECT UUID() as id," + 
			"        t_credit_note_details.crnd_id,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_amt) AS var_amt,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_qty) AS var_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        f.fr_id ,\n" + 
			"        m_item.id as item_id,\n" + 
			"        m_item.item_name     \n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_item      \n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id          \n" + 
			"        AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate         \n" + 
			"        AND f.fr_id=t_credit_note_header.fr_id          \n" + 
			"        AND m_item.id=t_credit_note_details.item_id          \n" + 
			"        AND m_item.item_grp2=sc.sub_cat_id          \n" + 
			"        AND t_credit_note_details.cat_id !=5           \n" + 
			"        AND t_credit_note_header.fr_id IN(\n" + 
			"           :frIdList \n" + 
			"        )          \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"           :subCatIdList   \n" + 
			"        )          \n" + 
			"        AND t_credit_note_details.is_grn=0            \n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_header.fr_id,\n" + 
			"        m_item.id\n" + 
			"        \n" + 
			"        \n" + 
			"        UNION\n" + 
			"    ALL   \n" + 
			"    \n" + 
			"    \n" + 
			"     SELECT UUID() as id," + 
			"        t_credit_note_details.crnd_id,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_amt) AS var_amt,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_qty) AS var_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        f.fr_id ,\n" + 
			"        m_sp_cake.sp_id as item_id,\n" + 
			"        m_sp_cake.sp_name     \n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_sp_cake      \n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id          \n" + 
			"        AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND f.fr_id=t_credit_note_header.fr_id          \n" + 
			"        AND m_sp_cake.sp_id=t_credit_note_details.item_id    \n" + 
			"          AND t_credit_note_details.cat_id =5      \n" + 
			"      \n" + 
			"          \n" + 
			"        AND t_credit_note_header.fr_id IN(\n" + 
			"           :frIdList \n" + 
			"        )     AND sc.cat_id=5      \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"           :subCatIdList   \n" + 
			"        )          \n" + 
			"        AND t_credit_note_details.is_grn=0              \n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_header.fr_id,\n" + 
			"        m_sp_cake.sp_id  order by fr_name,sub_cat_id,item_name ", nativeQuery = true)
	List<SubCatCreditGrnFrItemRep> getDataGVN(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	
	//Anmol--24-02-2020
	@Query(value = " SELECT UUID() as id," + 
			"        t_credit_note_details.crnd_id,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_amt) AS var_amt,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_qty) AS var_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        f.fr_id ,\n" + 
			"        m_item.id as item_id,\n" + 
			"        m_item.item_name     \n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_item      \n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id          \n" + 
			"        AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate         \n" + 
			"        AND f.fr_id=t_credit_note_header.fr_id          \n" + 
			"        AND m_item.id=t_credit_note_details.item_id          \n" + 
			"        AND m_item.item_grp2=sc.sub_cat_id          \n" + 
			"        AND t_credit_note_header.fr_id IN(\n" + 
			"           :frIdList \n" + 
			"        )          \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"           :subCatIdList   \n" + 
			"        )          \n" + 
			"        AND t_credit_note_details.is_grn=0    AND m_item.is_stockable = 1        \n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_header.fr_id,\n" + 
			"        m_item.id " , nativeQuery = true)
	List<SubCatCreditGrnFrItemRep> getAdminDataGVN(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	

	@Query(value = " SELECT UUID() as id," + 
			"        t_credit_note_details.crnd_id,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_amt) AS var_amt,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_qty) AS var_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        f.fr_id ,\n" + 
			"        m_item.id as item_id,\n" + 
			"        m_item.item_name     \n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_item      \n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id          \n" + 
			"        AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND f.fr_id=t_credit_note_header.fr_id          \n" + 
			"        AND m_item.id=t_credit_note_details.item_id          \n" + 
			"        AND m_item.item_grp2=sc.sub_cat_id          \n" + 
			"        AND t_credit_note_details.cat_id !=5           \n" + 
			"        AND t_credit_note_header.fr_id IN(\n" + 
			"           :frIdList \n" + 
			"        )          \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"           :subCatIdList   \n" + 
			"        )          \n" + 
			"        AND t_credit_note_details.is_grn=1              \n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_header.fr_id,\n" + 
			"        m_item.id\n" + 
			"        \n" + 
			"        \n" + 
			"        UNION\n" + 
			"    ALL   \n" + 
			"    \n" + 
			"    \n" + 
			"     SELECT UUID() as id," + 
			"        t_credit_note_details.crnd_id,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_amt) AS var_amt,\n" + 
			"        SUM(t_credit_note_details.grn_gvn_qty) AS var_qty ,\n" + 
			"        f.fr_name,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        f.fr_id ,\n" + 
			"        m_sp_cake.sp_id as item_id,\n" + 
			"        m_sp_cake.sp_name     \n" + 
			"    FROM\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details,\n" + 
			"        m_franchisee f ,\n" + 
			"        m_cat_sub sc ,\n" + 
			"        m_sp_cake      \n" + 
			"    WHERE\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id          \n" + 
			"        AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND f.fr_id=t_credit_note_header.fr_id          \n" + 
			"        AND m_sp_cake.sp_id=t_credit_note_details.item_id    \n" + 
			"          AND t_credit_note_details.cat_id =5      \n" + 
			"      \n" + 
			"          \n" + 
			"        AND t_credit_note_header.fr_id IN(\n" + 
			"            :frIdList  \n" + 
			"        )     AND sc.cat_id=5      \n" + 
			"        AND sc.sub_cat_id IN(\n" + 
			"          :subCatIdList  \n" + 
			"        )          \n" + 
			"        AND t_credit_note_details.is_grn=1              \n" + 
			"    GROUP BY\n" + 
			"        t_credit_note_header.fr_id,\n" + 
			"        m_sp_cake.sp_id  order by fr_name,sub_cat_id,item_name ", nativeQuery = true)
	List<SubCatCreditGrnFrItemRep> getDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	//Anmol--24-02-2020--
	@Query(value = " SELECT\n" + 
			"    UUID() AS id, t_credit_note_details.crnd_id, SUM(\n" + 
			"        t_credit_note_details.grn_gvn_amt\n" + 
			"    ) AS var_amt,\n" + 
			"    SUM(\n" + 
			"        t_credit_note_details.grn_gvn_qty\n" + 
			"    ) AS var_qty,\n" + 
			"    f.fr_name,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    f.fr_id,\n" + 
			"    m_item.id AS item_id,\n" + 
			"    m_item.item_name\n" + 
			"FROM\n" + 
			"    t_credit_note_header,\n" + 
			"    t_credit_note_details,\n" + 
			"    m_franchisee f,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = t_credit_note_header.fr_id AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = sc.sub_cat_id AND t_credit_note_header.fr_id IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND t_credit_note_details.is_grn = 1 AND m_item.is_stockable = 1\n" + 
			"GROUP BY\n" + 
			"    t_credit_note_header.fr_id,\n" + 
			"    m_item.id ", nativeQuery = true)
	List<SubCatCreditGrnFrItemRep> getAdminDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	//Anmol--24-02-2020--
	@Query(value = " SELECT\n" + 
			"    UUID() AS id, c.crn_detail_no as crnd_id, SUM(c.grand_total) AS var_amt,\n" + 
			"    SUM(c.crn_qty) AS var_qty,\n" + 
			"    f.fr_name,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    f.fr_id,\n" + 
			"    m_item.id AS item_id,\n" + 
			"    m_item.item_name\n" + 
			"FROM\n" + 
			"    t_credit_note_pos c,\n" + 
			"    m_franchisee f,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    c.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = c.ex_int1 AND m_item.id = c.item_id AND m_item.item_grp2 = sc.sub_cat_id AND c.ex_int1 IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_saleable = 1\n" + 
			"GROUP BY\n" + 
			"    c.ex_int1,\n" + 
			"    m_item.id ", nativeQuery = true)
	List<SubCatCreditGrnFrItemRep> getAdminDataCRNCompOutlet(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	
	//Anmol--10-04-2020--  COMP OUTLET  DAIRY MART
		@Query(value = " SELECT\n" + 
				"    id,\n" + 
				"    crnd_id,\n" + 
				"    SUM(var_amt) AS var_amt,\n" + 
				"    SUM(var_qty) AS var_qty,\n" + 
				"    fr_name,\n" + 
				"    sub_cat_id,\n" + 
				"    sub_cat_name,\n" + 
				"    fr_id,\n" + 
				"    item_id,\n" + 
				"    item_name\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        UUID() AS id, t_credit_note_details.crnd_id, SUM(\n" + 
				"            t_credit_note_details.grn_gvn_amt\n" + 
				"        ) AS var_amt,\n" + 
				"        SUM(\n" + 
				"            t_credit_note_details.grn_gvn_qty\n" + 
				"        ) AS var_qty,\n" + 
				"        f.fr_name,\n" + 
				"        sc.sub_cat_id,\n" + 
				"        sc.sub_cat_name,\n" + 
				"        f.fr_id,\n" + 
				"        m_item.id AS item_id,\n" + 
				"        m_item.item_name\n" + 
				"    FROM\n" + 
				"        t_credit_note_header,\n" + 
				"        t_credit_note_details,\n" + 
				"        m_franchisee f,\n" + 
				"        m_cat_sub sc,\n" + 
				"        m_item,\n" + 
				"        t_bill_header\n" + 
				"    WHERE\n" + 
				"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = t_credit_note_header.fr_id AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = sc.sub_cat_id AND t_credit_note_header.fr_id IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND t_credit_note_details.is_grn = 1 AND m_item.is_stockable = 1 AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.is_dairy_mart = 2\n" + 
				"    GROUP BY\n" + 
				"        t_credit_note_header.fr_id,\n" + 
				"        m_item.id\n" + 
				"    UNION\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            UUID() AS id, t_credit_note_details.crnd_id, SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            ) AS var_amt,\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            ) AS var_qty,\n" + 
				"            f.fr_name,\n" + 
				"            sc.sub_cat_id,\n" + 
				"            sc.sub_cat_name,\n" + 
				"            f.fr_id,\n" + 
				"            m_item.id AS item_id,\n" + 
				"            m_item.item_name\n" + 
				"        FROM\n" + 
				"            t_credit_note_header,\n" + 
				"            t_credit_note_details,\n" + 
				"            m_franchisee f,\n" + 
				"            m_cat_sub sc,\n" + 
				"            m_item,\n" + 
				"            t_bill_header\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = t_credit_note_header.fr_id AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = sc.sub_cat_id AND t_credit_note_header.fr_id IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND t_credit_note_details.is_grn = 0 AND m_item.is_stockable = 1 AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.is_dairy_mart = 2\n" + 
				"        GROUP BY\n" + 
				"            t_credit_note_header.fr_id,\n" + 
				"            m_item.id)\n" + 
				"    ) t1\n" + 
				"GROUP BY\n" + 
				"    fr_id,\n" + 
				"    item_id ", nativeQuery = true)
		List<SubCatCreditGrnFrItemRep> getAdminDataCRNCompOutletDairymart(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
				@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);
	
	
		//Anmol--10-04-2020-- COMP OUTLET DAIRY MART AND REGULAR
		@Query(value = " SELECT\n" + 
				"    id,\n" + 
				"    crnd_id,\n" + 
				"    SUM(var_amt) AS var_amt,\n" + 
				"    SUM(var_qty) AS var_qty,\n" + 
				"    fr_name,\n" + 
				"    sub_cat_id,\n" + 
				"    sub_cat_name,\n" + 
				"    fr_id,\n" + 
				"    item_id,\n" + 
				"    item_name\n" + 
				"FROM\n" + 
				"(\n" + 
				"SELECT\n" + 
				"    id,\n" + 
				"    crnd_id,\n" + 
				"    SUM(var_amt) AS var_amt,\n" + 
				"    SUM(var_qty) AS var_qty,\n" + 
				"    fr_name,\n" + 
				"    sub_cat_id,\n" + 
				"    sub_cat_name,\n" + 
				"    fr_id,\n" + 
				"    item_id,\n" + 
				"    item_name\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        UUID() AS id, t_credit_note_details.crnd_id, SUM(\n" + 
				"            t_credit_note_details.grn_gvn_amt\n" + 
				"        ) AS var_amt,\n" + 
				"        SUM(\n" + 
				"            t_credit_note_details.grn_gvn_qty\n" + 
				"        ) AS var_qty,\n" + 
				"        f.fr_name,\n" + 
				"        sc.sub_cat_id,\n" + 
				"        sc.sub_cat_name,\n" + 
				"        f.fr_id,\n" + 
				"        m_item.id AS item_id,\n" + 
				"        m_item.item_name\n" + 
				"    FROM\n" + 
				"        t_credit_note_header,\n" + 
				"        t_credit_note_details,\n" + 
				"        m_franchisee f,\n" + 
				"        m_cat_sub sc,\n" + 
				"        m_item,\n" + 
				"        t_bill_header\n" + 
				"    WHERE\n" + 
				"        t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = t_credit_note_header.fr_id AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = sc.sub_cat_id AND t_credit_note_header.fr_id IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND t_credit_note_details.is_grn = 1 AND m_item.is_stockable = 1 AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.is_dairy_mart = 2\n" + 
				"    GROUP BY\n" + 
				"        t_credit_note_header.fr_id,\n" + 
				"        m_item.id\n" + 
				"    UNION\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            UUID() AS id, t_credit_note_details.crnd_id, SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            ) AS var_amt,\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            ) AS var_qty,\n" + 
				"            f.fr_name,\n" + 
				"            sc.sub_cat_id,\n" + 
				"            sc.sub_cat_name,\n" + 
				"            f.fr_id,\n" + 
				"            m_item.id AS item_id,\n" + 
				"            m_item.item_name\n" + 
				"        FROM\n" + 
				"            t_credit_note_header,\n" + 
				"            t_credit_note_details,\n" + 
				"            m_franchisee f,\n" + 
				"            m_cat_sub sc,\n" + 
				"            m_item,\n" + 
				"            t_bill_header\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = t_credit_note_header.fr_id AND m_item.id = t_credit_note_details.item_id AND m_item.item_grp2 = sc.sub_cat_id AND t_credit_note_header.fr_id IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND t_credit_note_details.is_grn = 0 AND m_item.is_stockable = 1 AND t_bill_header.bill_no = t_credit_note_header.ex_int1 AND t_bill_header.is_dairy_mart =2\n" + 
				"        GROUP BY\n" + 
				"            t_credit_note_header.fr_id,\n" + 
				"            m_item.id)\n" + 
				"    ) t1\n" + 
				"GROUP BY\n" + 
				"    fr_id,\n" + 
				"    item_id\n" + 
				"    \n" + 
				"    UNION(\n" + 
				"    SELECT\n" + 
				"    UUID() AS id, c.crn_detail_no AS crnd_id, SUM(c.grand_total) AS var_amt,\n" + 
				"    SUM(c.crn_qty) AS var_qty,\n" + 
				"    f.fr_name,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    f.fr_id,\n" + 
				"    m_item.id AS item_id,\n" + 
				"    m_item.item_name\n" + 
				"FROM\n" + 
				"    t_credit_note_pos c,\n" + 
				"    m_franchisee f,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item\n" + 
				"WHERE\n" + 
				"    c.crn_date BETWEEN :fromDate AND :toDate AND f.fr_id = c.ex_int1 AND m_item.id = c.item_id AND m_item.item_grp2 = sc.sub_cat_id AND c.ex_int1 IN(:frIdList) AND sc.sub_cat_id IN(:subCatIdList) AND m_item.is_saleable = 1\n" + 
				"GROUP BY\n" + 
				"    c.ex_int1,\n" + 
				"    m_item.id\n" + 
				"    )) t1 GROUP BY fr_id,item_id\n" + 
				"     ", nativeQuery = true)
		List<SubCatCreditGrnFrItemRep> getAdminDataCRNCompOutletDairyAndRegular(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
				@Param("frIdList") List<Integer> frIdList, @Param("subCatIdList") List<Integer> subCatIdList);

}
