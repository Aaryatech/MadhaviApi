package com.ats.webapi.repo.salesreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport.SubCatCreditGrnRep;

public interface SubCatCreditGrnRepRepo extends JpaRepository<SubCatCreditGrnRep, Integer> {

	@Query(value = "     SELECT UUID()as id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=1 GROUP BY i.item_grp2 ORDER BY i.item_grp1 DESC", nativeQuery = true)
	List<SubCatCreditGrnRep> getDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	// Anmol--25-2-2020--
	@Query(value = " SELECT UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty, sc.sub_cat_id, sc.sub_cat_name, sc.cat_id FROM t_credit_note_header tb, t_credit_note_details td, m_cat_sub sc, m_item i WHERE tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 1 AND i.is_stockable = 1 GROUP BY i.item_grp2 ORDER BY i.item_grp1  ", nativeQuery = true)
	List<SubCatCreditGrnRep> getAdminDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "     SELECT UUID()as id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=0 GROUP BY i.item_grp2 ORDER BY i.item_grp1 DESC", nativeQuery = true)
	List<SubCatCreditGrnRep> getDataGVN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	// Anmol--25-2-2020--
	@Query(value = " SELECT UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty, sc.sub_cat_id, sc.sub_cat_name, sc.cat_id FROM t_credit_note_header tb, t_credit_note_details td, m_cat_sub sc, m_item i WHERE tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 0 AND i.is_stockable = 1 GROUP BY i.item_grp2 ORDER BY i.item_grp1  ", nativeQuery = true)
	List<SubCatCreditGrnRep> getAdminDataGVN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	// Anmol--25-2-2020--
	@Query(value = "SELECT UUID() AS id, SUM(c.grand_total) AS var_amt, SUM(c.crn_qty) AS var_qty, sc.sub_cat_id, sc.sub_cat_name, sc.cat_id FROM t_credit_note_pos c, m_cat_sub sc, m_item i WHERE c.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = c.item_id AND i.item_grp2 = sc.sub_cat_id AND i.is_stockable = 1 AND c.del_status=0 GROUP BY i.item_grp2 ORDER BY i.item_grp1   ", nativeQuery = true)
	List<SubCatCreditGrnRep> getAdminDataCRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "     SELECT UUID()as id,SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=1 AND tb.fr_id=:frId GROUP BY i.item_grp2 ", nativeQuery = true)
	List<SubCatCreditGrnRep> getDataGRNByFrId(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frId") int frId);

	@Query(value = "     SELECT UUID()as id,SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=0 AND tb.fr_id=:frId GROUP BY i.item_grp2 ", nativeQuery = true)
	List<SubCatCreditGrnRep> getDataGVNByFrId(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frId") int frId);
	
	
	// Anmol--10-4-2020-- COMP OUTLET DAIRY AND REGULAR
		@Query(value = "SELECT \n" + 
				"id,\n" + 
				"    SUM(var_amt) AS var_amt,\n" + 
				"    SUM(var_qty) AS var_qty,\n" + 
				"    sub_cat_id,\n" + 
				"    sub_cat_name,\n" + 
				"    cat_id\n" + 
				"FROM(\n" + 
				"SELECT\n" + 
				"    id,\n" + 
				"    SUM(var_amt) AS var_amt,\n" + 
				"    SUM(var_qty) AS var_qty,\n" + 
				"    sub_cat_id,\n" + 
				"    sub_cat_name,\n" + 
				"    cat_id\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt,\n" + 
				"        SUM(td.grn_gvn_qty) AS var_qty,\n" + 
				"        sc.sub_cat_id,\n" + 
				"        sc.sub_cat_name,\n" + 
				"        sc.cat_id\n" + 
				"    FROM\n" + 
				"        t_credit_note_header tb,\n" + 
				"        t_credit_note_details td,\n" + 
				"        m_cat_sub sc,\n" + 
				"        m_item i\n" + 
				"    WHERE\n" + 
				"        tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 1 AND i.is_stockable = 1\n" + 
				"    GROUP BY\n" + 
				"        i.item_grp2\n" + 
				"    UNION\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt,\n" + 
				"            SUM(td.grn_gvn_qty) AS var_qty,\n" + 
				"            sc.sub_cat_id,\n" + 
				"            sc.sub_cat_name,\n" + 
				"            sc.cat_id\n" + 
				"        FROM\n" + 
				"            t_credit_note_header tb,\n" + 
				"            t_credit_note_details td,\n" + 
				"            m_cat_sub sc,\n" + 
				"            m_item i\n" + 
				"        WHERE\n" + 
				"            tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 0 AND i.is_stockable = 1\n" + 
				"        GROUP BY\n" + 
				"            i.item_grp2\n" + 
				"        ORDER BY\n" + 
				"            i.item_grp1)\n" + 
				"    ) t1\n" + 
				"GROUP BY\n" + 
				"    sub_cat_id\n" + 
				"    \n" + 
				"    UNION(\n" + 
				"    SELECT\n" + 
				"    UUID() AS id, SUM(c.grand_total) AS var_amt,\n" + 
				"    SUM(c.crn_qty) AS var_qty,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    sc.cat_id\n" + 
				"FROM\n" + 
				"    t_credit_note_pos c,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item i\n" + 
				"WHERE\n" + 
				"    c.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = c.item_id AND i.item_grp2 = sc.sub_cat_id AND i.is_stockable = 1 AND c.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    i.item_grp2\n" + 
				"ORDER BY\n" + 
				"    i.item_grp1\n" + 
				"    )) t1 GROUP BY sub_cat_id   ", nativeQuery = true)
		List<SubCatCreditGrnRep> getAdminDataCRNCompDairyAndReg(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

		// Anmol--10-4-2020-- COMP OUTLET DAIRY 
				@Query(value = "SELECT\n" + 
						"    id,\n" + 
						"    SUM(var_amt) AS var_amt,\n" + 
						"    SUM(var_qty) AS var_qty,\n" + 
						"    sub_cat_id,\n" + 
						"    sub_cat_name,\n" + 
						"    cat_id\n" + 
						"FROM\n" + 
						"    (\n" + 
						"    SELECT\n" + 
						"        UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt,\n" + 
						"        SUM(td.grn_gvn_qty) AS var_qty,\n" + 
						"        sc.sub_cat_id,\n" + 
						"        sc.sub_cat_name,\n" + 
						"        sc.cat_id\n" + 
						"    FROM\n" + 
						"        t_credit_note_header tb,\n" + 
						"        t_credit_note_details td,\n" + 
						"        m_cat_sub sc,\n" + 
						"        m_item i\n" + 
						"    WHERE\n" + 
						"        tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 1 AND i.is_stockable = 1\n" + 
						"    GROUP BY\n" + 
						"        i.item_grp2\n" + 
						"    UNION\n" + 
						"        (\n" + 
						"        SELECT\n" + 
						"            UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt,\n" + 
						"            SUM(td.grn_gvn_qty) AS var_qty,\n" + 
						"            sc.sub_cat_id,\n" + 
						"            sc.sub_cat_name,\n" + 
						"            sc.cat_id\n" + 
						"        FROM\n" + 
						"            t_credit_note_header tb,\n" + 
						"            t_credit_note_details td,\n" + 
						"            m_cat_sub sc,\n" + 
						"            m_item i\n" + 
						"        WHERE\n" + 
						"            tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 0 AND i.is_stockable = 1\n" + 
						"        GROUP BY\n" + 
						"            i.item_grp2\n" + 
						"        ORDER BY\n" + 
						"            i.item_grp1)\n" + 
						"    ) t1\n" + 
						"GROUP BY\n" + 
						"    sub_cat_id   ", nativeQuery = true)
				List<SubCatCreditGrnRep> getAdminDataCRNCompDairy(@Param("fromDate") String fromDate, @Param("toDate") String toDate);


}
