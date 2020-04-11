package com.ats.webapi.repo.salesreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesreport.SubCatBillRep;

public interface SubCatBillRepRepo extends JpaRepository<SubCatBillRep, Integer> {

	@Query(value = "SELECT\n" + 
			"    SUM(td.grand_total) AS sold_amt,\n" + 
			"    SUM(td.bill_qty) AS sold_qty,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    c.cat_id\n" + 
			"FROM\n" + 
			"    t_bill_header tb,\n" + 
			"    t_bill_detail td,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_category c,\n" + 
			"    m_item i\n" + 
			"WHERE\n" + 
			"    tb.del_status = 0 AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND  tb.ex_varchar2 IN(:temp) \n" + 
			"GROUP BY\n" + 
			"    i.item_grp2", nativeQuery = true)
	List<SubCatBillRep> getData12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	//Anmol--25-02-2020-------
	@Query(value = "SELECT\n" + 
			"    SUM(td.grand_total) AS sold_amt,\n" + 
			"    SUM(td.bill_qty) AS sold_qty,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    c.cat_id\n" + 
			"FROM\n" + 
			"    t_bill_header tb,\n" + 
			"    t_bill_detail td,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_category c,\n" + 
			"    m_item i\n" + 
			"WHERE\n" + 
			"    tb.del_status = 0 AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND tb.ex_varchar2 IN(:temp) AND i.is_stockable = 1\n" + 
			"GROUP BY\n" + 
			"    i.item_grp2\n" + 
			"ORDER BY\n" + 
			"    i.item_grp1", nativeQuery = true)
	List<SubCatBillRep> getAdminData12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	//Anmol--25-02-2020-------
		@Query(value = "SELECT\n" + 
				"    SUM(td.ext_float1) AS sold_amt,\n" + 
				"    SUM(td.qty) AS sold_qty,\n" + 
				"    sc.sub_cat_id,\n" + 
				"    sc.sub_cat_name,\n" + 
				"    sc.cat_id\n" + 
				"FROM\n" + 
				"    t_sell_bill_header tb,\n" + 
				"    t_sell_bill_detail td,\n" + 
				"    m_cat_sub sc,\n" + 
				"    m_item i\n" + 
				"WHERE\n" + 
				"    tb.del_status = 0 AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND i.is_saleable = 1\n" + 
				"GROUP BY\n" + 
				"    i.item_grp2\n" + 
				"ORDER BY\n" + 
				"    i.item_grp1", nativeQuery = true)
		List<SubCatBillRep> getAdminDataCompOutlet(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
		
		//Anmol--10-04-2020-------COMP OUTLET DAIRY AND REGULAR
				@Query(value = "SELECT\n" + 
						"    SUM(sold_amt) AS sold_amt,\n" + 
						"    SUM(sold_qty) AS sold_qty,\n" + 
						"    sub_cat_id,\n" + 
						"    sub_cat_name,\n" + 
						"    cat_id\n" + 
						"FROM\n" + 
						"    (\n" + 
						"    SELECT\n" + 
						"        SUM(td.grand_total) AS sold_amt,\n" + 
						"        SUM(td.bill_qty) AS sold_qty,\n" + 
						"        sc.sub_cat_id,\n" + 
						"        sc.sub_cat_name,\n" + 
						"        c.cat_id\n" + 
						"    FROM\n" + 
						"        t_bill_header tb,\n" + 
						"        t_bill_detail td,\n" + 
						"        m_cat_sub sc,\n" + 
						"        m_category c,\n" + 
						"        m_item i\n" + 
						"    WHERE\n" + 
						"        tb.del_status = 0 AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND i.is_stockable = 1 AND tb.is_dairy_mart = 2\n" + 
						"    GROUP BY\n" + 
						"        i.item_grp2\n" + 
						"    UNION\n" + 
						"        (\n" + 
						"        SELECT\n" + 
						"            SUM(td.ext_float1) AS sold_amt,\n" + 
						"            SUM(td.qty) AS sold_qty,\n" + 
						"            sc.sub_cat_id,\n" + 
						"            sc.sub_cat_name,\n" + 
						"            sc.cat_id\n" + 
						"        FROM\n" + 
						"            t_sell_bill_header tb,\n" + 
						"            t_sell_bill_detail td,\n" + 
						"            m_cat_sub sc,\n" + 
						"            m_item i\n" + 
						"        WHERE\n" + 
						"            tb.del_status = 0 AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND i.is_saleable = 1\n" + 
						"        GROUP BY\n" + 
						"            i.item_grp2\n" + 
						"        ORDER BY\n" + 
						"            i.item_grp1\n" + 
						"    )\n" + 
						") t1\n" + 
						"GROUP BY\n" + 
						"    sub_cat_id", nativeQuery = true)
				List<SubCatBillRep> getAdminDataCompOutletDairyAndReg(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
				
				
				//Anmol--10-04-2020-------COMP OUTLET DAIRY MART
				@Query(value = "SELECT\n" + 
						"    SUM(td.grand_total) AS sold_amt,\n" + 
						"    SUM(td.bill_qty) AS sold_qty,\n" + 
						"    sc.sub_cat_id,\n" + 
						"    sc.sub_cat_name,\n" + 
						"    c.cat_id\n" + 
						"FROM\n" + 
						"    t_bill_header tb,\n" + 
						"    t_bill_detail td,\n" + 
						"    m_cat_sub sc,\n" + 
						"    m_category c,\n" + 
						"    m_item i\n" + 
						"WHERE\n" + 
						"    tb.del_status = 0 AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id  AND i.is_stockable = 1 AND tb.is_dairy_mart=2\n" + 
						"GROUP BY\n" + 
						"    i.item_grp2\n" + 
						"ORDER BY\n" + 
						"    i.item_grp1", nativeQuery = true)
				List<SubCatBillRep> getAdminDataCompOutletDairy(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
				
	
	
	@Query(value = "SELECT\n" + 
			"    SUM(td.grand_total) AS sold_amt,\n" + 
			"    SUM(td.qty) AS sold_qty,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    c.cat_id\n" + 
			"FROM\n" + 
			"    t_sell_bill_header tb,\n" + 
			"    t_sell_bill_detail td,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_category c,\n" + 
			"    m_item i\n" + 
			"WHERE\n" + 
			"    tb.del_status = 0 AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id \n" + 
			"GROUP BY\n" + 
			"    i.item_grp2", nativeQuery = true)
	List<SubCatBillRep> getData3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	@Query(value = "SELECT\n" + 
			"    SUM(a.sold_amt) AS sold_amt,\n" + 
			"    SUM(a.sold_qty) AS sold_qty,\n" + 
			"    a.sub_cat_id,\n" + 
			"    a.sub_cat_name,\n" + 
			"    a.cat_id\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        SUM(td.grand_total) AS sold_amt,\n" + 
			"        SUM(td.bill_qty) AS sold_qty,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        c.cat_id\n" + 
			"    FROM\n" + 
			"        t_bill_header tb,\n" + 
			"        t_bill_detail td,\n" + 
			"        m_cat_sub sc,\n" + 
			"        m_category c,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        tb.del_status = 0 AND tb.bill_no = td.bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id AND  tb.ex_varchar2 IN(:temp) \n" + 
			"    GROUP BY\n" + 
			"        i.item_grp2\n" + 
			"    UNION ALL\n" + 
			"SELECT\n" + 
			"    SUM(td.grand_total) AS sold_amt,\n" + 
			"    SUM(td.qty) AS sold_qty,\n" + 
			"    sc.sub_cat_id,\n" + 
			"    sc.sub_cat_name,\n" + 
			"    c.cat_id\n" + 
			"FROM\n" + 
			"    t_sell_bill_header tb,\n" + 
			"    t_sell_bill_detail td,\n" + 
			"    m_cat_sub sc,\n" + 
			"    m_category c,\n" + 
			"    m_item i\n" + 
			"WHERE\n" + 
			"    tb.del_status = 0 AND tb.sell_bill_no = td.sell_bill_no AND tb.bill_date BETWEEN :fromDate AND :toDate AND td.cat_id = c.cat_id AND i.id = td.item_id AND i.item_grp2 = sc.sub_cat_id\n" + 
			"GROUP BY\n" + 
			"    i.item_grp2\n" + 
			") a\n" + 
			"GROUP BY\n" + 
			"    a.sub_cat_id", nativeQuery = true)
	List<SubCatBillRep> getDataAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	
	
	
	
	
	
	

	@Query(value = " \n" + 
			" SELECT SUM(td.grand_total) AS sold_amt, SUM(td.bill_qty) AS sold_qty, sc.sub_cat_id, sc.sub_cat_name, c.cat_id FROM t_bill_header tb, t_bill_detail td ,m_cat_sub sc ,m_category c,m_item i WHERE tb.del_status=0 AND tb.bill_no=td.bill_no And tb.fr_id=:frId AND tb.bill_date BETWEEN   :fromDate  AND :toDate   AND td.cat_id=c.cat_id AND i.id=td.item_id AND i.item_grp2=sc.sub_cat_id and td.cat_id!=5 GROUP BY i.item_grp2\n" + 
			" UNION ALL\n" + 
			" SELECT SUM(td.grand_total) AS sold_amt,\n" + 
			"        SUM(td.bill_qty) AS sold_qty,\n" + 
			"        sc.sub_cat_id,\n" + 
			"        sc.sub_cat_name,\n" + 
			"        c.cat_id \n" + 
			"        FROM t_bill_header tb, t_bill_detail  td  ,m_cat_sub sc ,m_category c,m_sp_cake s  \n" + 
			"        WHERE tb.del_status=0  AND tb.bill_no=td.bill_no And tb.fr_id=:frId AND tb.bill_date BETWEEN  :fromDate  AND :toDate  AND td.cat_id=c.cat_id AND s.sp_id=td.item_id AND 5=sc.cat_id and c.cat_id=5 GROUP BY sc.cat_id ", nativeQuery = true)
	List<SubCatBillRep> getDataByFrId(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("frId") int frId);

}
