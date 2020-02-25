package com.ats.webapi.repo.salesreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport.SubCatCreditGrnRep;

public interface SubCatCreditGrnRepRepo extends JpaRepository<SubCatCreditGrnRep, Integer> {

	@Query(value = "     SELECT UUID()as id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=1 GROUP BY i.item_grp2 ", nativeQuery = true)
	List<SubCatCreditGrnRep> getDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	// Anmol--25-2-2020--
	@Query(value = " SELECT UUID() AS id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty, sc.sub_cat_id, sc.sub_cat_name, sc.cat_id FROM t_credit_note_header tb, t_credit_note_details td, m_cat_sub sc, m_item i WHERE tb.crn_id = td.crn_id AND tb.crn_date BETWEEN :fromDate  AND  :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND td.is_grn = 1 AND i.is_stockable = 1 GROUP BY i.item_grp2 ORDER BY i.item_grp1  ", nativeQuery = true)
	List<SubCatCreditGrnRep> getAdminDataGRN(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "     SELECT UUID()as id, SUM(td.grn_gvn_amt) AS var_amt, SUM(td.grn_gvn_qty) AS var_qty    ,sc.sub_cat_id,sc.sub_cat_name,c.cat_id FROM t_credit_note_header tb, t_credit_note_details  td  ,m_cat_sub sc ,m_category c,m_item i  WHERE    tb.crn_id=td.crn_id AND tb.crn_date BETWEEN  :fromDate  AND  :toDate  AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id AND td.is_grn=0 GROUP BY i.item_grp2 ", nativeQuery = true)
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

}
