package com.ats.webapi.repository.frpurchasereport;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ats.webapi.model.report.frpurchase.SalesReportItemwise;
//sales report no 8
public interface SaleReportItemwiseRepo extends JpaRepository<SalesReportItemwise, Integer> {
	 
//Report 4  New work 
 

//other 

			@Query(value=" SELECT  t_bill_detail.hsn_code as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
					"sum(t_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_bill_detail.cgst_rs) AS cgst_rs_sum," + 
					"sum(t_bill_detail.igst_rs) AS igst_rs_sum, " + 
					"SUM(t_bill_detail.bill_qty) AS bill_qty_sum FROM t_bill_header,t_bill_detail,m_item" + 
					" WHERE t_bill_header.bill_no=t_bill_detail.bill_no AND " + 
					"t_bill_detail.item_id=m_item.id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.del_status=0 and t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp) GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
				List<SalesReportItemwise> getSaleReportItemwiseExceptTradingPacking1N2(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);


			@Query(value=" SELECT t_sell_bill_detail.remark  as item_hsncd,m_item.id,m_item.item_tax1,m_item.item_tax2,m_item. item_tax3,m_item.item_name," + 
					"sum(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum ,sum(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,sum(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum," + 
					"sum(t_sell_bill_detail.igst_rs) AS igst_rs_sum, " + 
					"SUM(t_sell_bill_detail.qty) AS bill_qty_sum FROM t_sell_bill_header,t_sell_bill_detail,m_item" + 
					" WHERE t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no  AND " + 
					"t_sell_bill_detail.item_id=m_item.id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate And t_sell_bill_header.del_status=0 and t_sell_bill_detail.del_status=0  GROUP BY m_item.item_name order by m_item.item_grp1, m_item.item_grp2,m_item.item_name",nativeQuery=true)
				List<SalesReportItemwise> getSaleReportItemwiseExceptTradingPackingOutlet3(@Param("fromDate") String fromDate,@Param("toDate") String toDate);
			
			
			@Query(value="SELECT a.item_hsncd,a.id,a.item_tax1,a.item_tax2,a.item_tax3,a.item_name,a.taxable_amt_sum,a.sgst_rs_sum,a.cgst_rs_sum,a.igst_rs_sum,a.bill_qty_sum FROM ( SELECT\n" + 
					"    t_bill_detail.hsn_code AS item_hsncd,\n" + 
					"    m_item.id,\n" + 
					"    m_item.item_tax1,\n" + 
					"    m_item.item_tax2,\n" + 
					"    m_item.item_tax3,\n" + 
					"    m_item.item_name,\n" + 
					"    SUM(t_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
					"    SUM(t_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
					"    SUM(t_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
					"    SUM(t_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
					"    SUM(t_bill_detail.bill_qty) AS bill_qty_sum,m_item.item_grp1,m_item.item_grp2 \n" + 
					"FROM\n" + 
					"    t_bill_header,\n" + 
					"    t_bill_detail,\n" + 
					"    m_item\n" + 
					"WHERE\n" + 
					"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.item_id = m_item.id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id != 5 AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
					"GROUP BY\n" + 
					"    m_item.item_name\n" + 
 					"UNION ALL \n" + 
 					"    SELECT\n" + 
					"        t_sell_bill_detail.remark AS item_hsncd,\n" + 
					"        m_item.id,\n" + 
					"        m_item.item_tax1,\n" + 
					"        m_item.item_tax2,\n" + 
					"        m_item.item_tax3,\n" + 
					"        m_item.item_name,\n" + 
					"        SUM(t_sell_bill_detail.taxable_amt) AS taxable_amt_sum,\n" + 
					"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_rs_sum,\n" + 
					"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_rs_sum,\n" + 
					"        SUM(t_sell_bill_detail.igst_rs) AS igst_rs_sum,\n" + 
					"        SUM(t_sell_bill_detail.qty) AS bill_qty_sum,m_item.item_grp1,m_item.item_grp2 \n" + 
					"    FROM\n" + 
					"        t_sell_bill_header,\n" + 
					"        t_sell_bill_detail,\n" + 
					"        m_item\n" + 
					"    WHERE\n" + 
					"        t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_detail.item_id = m_item.id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
					"    GROUP BY\n" + 
					"        m_item.item_name) a ORDER BY  a.item_grp1,a.item_grp2,a.item_name \n" + 
 					"",nativeQuery=true)
				List<SalesReportItemwise> getSaleReportItemwiseExceptTradingPackingOutletAll(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
			
			 

}
