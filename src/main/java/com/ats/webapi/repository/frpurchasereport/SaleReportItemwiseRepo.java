package com.ats.webapi.repository.frpurchasereport;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ats.webapi.model.report.frpurchase.SalesReportItemwise;
//sales report no 8
public interface SaleReportItemwiseRepo extends JpaRepository<SalesReportItemwise, Integer> {
	@Query(value=" SELECT t_bill_detail.hsn_code as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
			"sum(t_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_bill_detail.cgst_rs) AS cgst_rs_sum," + 
			"sum(t_bill_detail.igst_rs) AS igst_rs_sum, " + 
			"SUM(t_bill_detail.bill_qty) AS bill_qty_sum FROM t_bill_header,t_bill_detail,m_item" + 
			" WHERE t_bill_header.bill_no=t_bill_detail.bill_no  AND " + 
			"t_bill_detail.item_id=m_item.id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id=:catId And t_bill_header.del_status=0 and t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
		
		List<SalesReportItemwise> getSaleReportItemwise(@Param("catId")int catId ,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);
	
	
	@Query(value=" SELECT  t_bill_detail.hsn_code as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
			"sum(t_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_bill_detail.cgst_rs) AS cgst_rs_sum," + 
			"sum(t_bill_detail.igst_rs) AS igst_rs_sum, " + 
			"SUM(t_bill_detail.bill_qty) AS bill_qty_sum FROM t_bill_header,t_bill_detail,m_item" + 
			" WHERE t_bill_header.bill_no=t_bill_detail.bill_no AND " + 
			"t_bill_detail.item_id=m_item.id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.del_status=0 and t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
		List<SalesReportItemwise> getSaleReportItemwiseExceptTradingPacking(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);

	@Query(value="SELECT t_bill_detail.hsn_code as item_hsncd,m_sp_cake.sp_id as id,m_sp_cake.sp_tax1 as item_tax1,m_sp_cake.sp_tax2 as item_tax2,m_sp_cake.sp_tax3 as item_tax3,m_sp_cake.sp_name as item_name,sum(t_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_bill_detail.cgst_rs) AS cgst_rs_sum,sum(t_bill_detail.igst_rs) AS igst_rs_sum,SUM(t_bill_detail.bill_qty) AS bill_qty_sum FROM t_bill_header,t_bill_detail,m_sp_cake WHERE t_bill_header.bill_no=t_bill_detail.bill_no AND t_bill_detail.item_id=m_sp_cake.sp_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate and t_bill_detail.cat_id=5 and t_bill_header.del_status=0 and t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag GROUP BY t_bill_detail.item_id order by item_name",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewise(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);

	
	//fr outlet added by harsha
	
	@Query(value=" SELECT t_sell_bill_detail.remark  as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
			"sum(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum," + 
			"sum(t_sell_bill_detail.igst_rs) AS igst_rs_sum, " + 
			"SUM(t_sell_bill_detail.qty) AS bill_qty_sum FROM t_sell_bill_header,t_sell_bill_detail,m_item" + 
			" WHERE t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no  AND " + 
			"t_sell_bill_detail.item_id=m_item.id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id=:catId And t_sell_bill_header.del_status=0 and t_sell_bill_detail.del_status=0  GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
		
		List<SalesReportItemwise> getSaleReportItemwiseOutlet(@Param("catId")int catId ,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	@Query(value=" SELECT t_sell_bill_detail.remark  as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
			"sum(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum," + 
			"sum(t_sell_bill_detail.igst_rs) AS igst_rs_sum, " + 
			"SUM(t_sell_bill_detail.qty) AS bill_qty_sum FROM t_sell_bill_header,t_sell_bill_detail,m_item" + 
			" WHERE t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no  AND " + 
			"t_sell_bill_detail.item_id=m_item.id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate And t_sell_bill_header.del_status=0 and t_sell_bill_detail.del_status=0  GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
		List<SalesReportItemwise> getSaleReportItemwiseExceptTradingPackingOutlet(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

 


//Report 4  New work 

 

	@Query(value=" SELECT\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\n" + 
			"    m_sp_cake.sp_id AS id,\n" + 
			"    m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"    m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"    m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"    m_sp_cake.sp_name AS item_name,\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty_sum\n" + 
			"FROM\n" + 
			"    t_bill_header,\n" + 
			"    t_bill_detail,\n" + 
			"    m_sp_cake\n" + 
			"WHERE\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.item_id = m_sp_cake.sp_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id = 5 AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.ex_varchar2 IN(0,1)\n" + 
			"GROUP BY\n" + 
			"    t_bill_detail.item_id\n" + 
			"ORDER BY\n" + 
			"    item_name\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_sell_bill_detail.remark AS item_hsncd,\n" + 
			"        m_sp_cake.sp_id AS id,\n" + 
			"        m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"        m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"        m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"        m_sp_cake.sp_name AS item_name,\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.qty) AS bill_qty_sum\n" + 
			"    FROM\n" + 
			"        t_sell_bill_header,\n" + 
			"        t_sell_bill_detail,\n" + 
			"        m_sp_cake\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_detail.item_id = m_sp_cake.sp_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id = 5 AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
			"    GROUP BY\n" + 
			"        t_sell_bill_detail.item_id\n" + 
			"    ORDER BY\n" + 
			"        item_name\n" + 
			")",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewiseAll(@Param("fromDate") String fromDate,@Param("toDate") String toDate);


	@Query(value="SELECT\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\n" + 
			"    m_sp_cake.sp_id AS id,\n" + 
			"    m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"    m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"    m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"    m_sp_cake.sp_name AS item_name,\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty_sum\n" + 
			"FROM\n" + 
			"    t_bill_header,\n" + 
			"    t_bill_detail,\n" + 
			"    m_sp_cake\n" + 
			"WHERE\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.item_id = m_sp_cake.sp_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id = 5 AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.ex_varchar2 IN (0,1)\n" + 
			"GROUP BY\n" + 
			"    t_bill_detail.item_id\n" + 
			"ORDER BY\n" + 
			"    item_name",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewise1N2(@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	@Query(value="SELECT\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\n" + 
			"    m_sp_cake.sp_id AS id,\n" + 
			"    m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"    m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"    m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"    m_sp_cake.sp_name AS item_name,\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty_sum\n" + 
			"FROM\n" + 
			"    t_bill_header,\n" + 
			"    t_bill_detail,\n" + 
			"    m_sp_cake\n" + 
			"WHERE\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.item_id = m_sp_cake.sp_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id = 5 AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.ex_varchar2=:flag \n" + 
			"GROUP BY\n" + 
			"    t_bill_detail.item_id\n" + 
			"ORDER BY\n" + 
			"    item_name",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewise1O2(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);




	@Query(value=" SELECT\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\n" + 
			"    m_sp_cake.sp_id AS id,\n" + 
			"    m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"    m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"    m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"    m_sp_cake.sp_name AS item_name,\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"    SUM(t_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty_sum\n" + 
			"FROM\n" + 
			"    t_bill_header,\n" + 
			"    t_bill_detail,\n" + 
			"    m_sp_cake\n" + 
			"WHERE\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.item_id = m_sp_cake.sp_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id = 5 AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.ex_varchar2=:flag \n" + 
			"GROUP BY\n" + 
			"    t_bill_detail.item_id\n" + 
			"ORDER BY\n" + 
			"    item_name\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_sell_bill_detail.remark AS item_hsncd,\n" + 
			"        m_sp_cake.sp_id AS id,\n" + 
			"        m_sp_cake.sp_tax1 AS item_tax1,\n" + 
			"        m_sp_cake.sp_tax2 AS item_tax2,\n" + 
			"        m_sp_cake.sp_tax3 AS item_tax3,\n" + 
			"        m_sp_cake.sp_name AS item_name,\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
			"        SUM(t_sell_bill_detail.qty) AS bill_qty_sum\n" + 
			"    FROM\n" + 
			"        t_sell_bill_header,\n" + 
			"        t_sell_bill_detail,\n" + 
			"        m_sp_cake\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_detail.item_id = m_sp_cake.sp_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id = 5 AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
			"    GROUP BY\n" + 
			"        t_sell_bill_detail.item_id\n" + 
			"    ORDER BY\n" + 
			"        item_name\n" + 
			")",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewise1O2O3(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);


	
	@Query(value="SELECT t_sell_bill_detail.remark as item_hsncd,m_sp_cake.sp_id as id,m_sp_cake.sp_tax1 as item_tax1,m_sp_cake.sp_tax2 as item_tax2,m_sp_cake.sp_tax3 as item_tax3,m_sp_cake.sp_name as item_name,sum(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum,sum(t_sell_bill_detail.igst_rs) AS igst_rs_sum,SUM(t_sell_bill_detail.qty) AS bill_qty_sum FROM t_sell_bill_header,t_sell_bill_detail,m_sp_cake WHERE t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no AND t_sell_bill_detail.item_id=m_sp_cake.sp_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate and t_sell_bill_detail.cat_id=5 and t_sell_bill_header.del_status=0 and t_sell_bill_detail.del_status=0  GROUP BY t_sell_bill_detail.item_id order by item_name",nativeQuery=true)
	List<SalesReportItemwise> getSaleReportSpcakewiseOutlet(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

}
