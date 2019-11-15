package com.ats.webapi.repository.frpurchasereport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.frpurchase.SalesReportBillwise;
//Sales Report No 1
public interface SaleReportBillwiseRepo extends JpaRepository<SalesReportBillwise, Integer> {
	@Query(value=" " + 
			"SELECT " + 
			"        MONTHNAME(t_bill_header.bill_date)as month," + 
			"        t_bill_header.bill_no," + 
			"        t_bill_header.bill_date," + 
			"        t_bill_header.invoice_no," + 
			"        t_bill_header.fr_id," + 
			"        t_bill_header.fr_code," + 
			"        t_bill_header.tax_applicable," + 
			"        sum(t_bill_detail.taxable_amt) as taxable_amt," + 
			"        sum(t_bill_detail.total_tax) as total_tax," + 
			"        SUM(t_bill_detail.grand_total) AS grand_total," + 
			"        t_bill_header.round_off," + 
			"        SUM(t_bill_detail.sgst_rs) as sgst_sum," + 
			"        SUM(t_bill_detail.cgst_rs) as cgst_sum ," + 
			"        SUM(t_bill_detail.igst_rs) as igst_sum," + 
			"        m_franchisee.fr_name," + 
			"        m_franchisee.fr_city," + 
			"        m_franchisee.fr_gst_no," + 
			"        m_franchisee.is_same_state," + 
			"        m_franchisee.fr_name " + 
			"    FROM" + 
			"        m_franchisee," + 
			"        t_bill_header ," + 
			"        t_bill_detail" + 
			"    WHERE" + 
			"        t_bill_header.fr_id=m_franchisee.fr_id " + 
			"        AND t_bill_header.fr_id IN(" + 
			"           :frIdList" + 
			"        ) " + 
			" AND t_bill_detail.cat_id IN(" + 
			"           :catIdList" + 
			"        ) " + 
			"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
			"        " + 
			"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 " + 
			"    GROUP BY" + 
			"       t_bill_header.bill_no",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwise(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);
//report 1 all Fr
	/*@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,"
			+ "t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,t_bill_header.taxable_amt,t_bill_header.total_tax,"
			+ "t_bill_header.grand_total,t_bill_header.round_off," + 
			"t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum,m_franchisee.fr_name,"
			+ "m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header "
			+ "WHERE t_bill_header.fr_id=m_franchisee.fr_id "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 "
			+ "ORDER BY t_bill_header.bill_date",nativeQuery=true)*/
	

	//report 2 group by fr
	/*@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,t_bill_header.total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY t_bill_header.fr_id",nativeQuery=true)*/
	
	
	
	//report 2 group by fr all Fr
	@Query(value=" SELECT   " + 
			"			        MONTHNAME(t_bill_header.bill_date)as month, " + 
			"			        t_bill_header.bill_no, " + 
			"			        t_bill_header.bill_date,  " + 
			"			        t_bill_header.invoice_no, " + 
			"			        t_bill_header.fr_id, " + 
			"			        t_bill_header.fr_code, " + 
			"			        t_bill_header.tax_applicable, " + 
			"			        SUM(t_bill_detail.taxable_amt) as taxable_amt , " + 
			"			        t_bill_header.total_tax, " + 
			"			        SUM(t_bill_detail.grand_total) AS grand_total , " + 
			"			        t_bill_header.round_off, " + 
			"			        SUM(t_bill_detail.sgst_rs)as sgst_sum , " + 
			"			        SUM(t_bill_detail.cgst_rs) as cgst_sum , " + 
			"			        SUM(t_bill_detail.igst_rs) as igst_sum, " + 
			"			        m_franchisee.fr_name, " + 
			"			        m_franchisee.fr_city, " + 
			"			        m_franchisee.fr_gst_no, " + 
			"			        m_franchisee.is_same_state, " + 
			"			        m_franchisee.fr_name  " + 
			"			    FROM " + 
			"			        m_franchisee, " + 
			"			        t_bill_header, " + 
			"			        t_bill_detail " + 
			"			    WHERE " + 
			"			        t_bill_header.fr_id=m_franchisee.fr_id " + 
			"			        AND t_bill_detail.cat_id IN (:catIdList) " + 
			"			        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate  " + 
			"			        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 " + 
			"			        AND t_bill_detail.bill_no=t_bill_header.bill_no " + 
			"			    GROUP BY " + 
			"			        t_bill_header.fr_id",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByFrAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

	// Report 3 sales report datewise group by date
	@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,SUM(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,SUM(t_bill_header.round_off) as round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  AND t_bill_header.ex_varchar2=:flag GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDateWithoutOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);
	
	
	@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,SUM(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,SUM(t_bill_header.round_off) as round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	
	//added by harsha 
	@Query(value=" SELECT MONTHNAME(t_sell_bill_header.bill_date)as month, t_sell_bill_header.sell_bill_no as bill_no,t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no,t_sell_bill_header.fr_id,t_sell_bill_header.fr_code," + 
			" '0' as tax_applicable,SUM(t_sell_bill_header.taxable_amt) as taxable_amt ,SUM(t_sell_bill_header.total_tax) as total_tax,SUM(t_sell_bill_header.grand_total) AS grand_total ,'0' as round_off," + 
			"SUM(t_sell_bill_detail.sgst_rs)as sgst_sum ,SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,SUM(t_sell_bill_detail.igst_rs) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_sell_bill_header WHERE t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) "
			+ " AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no GROUP BY t_sell_bill_header.bill_date",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByDateOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	// Report 3 sales report datewise group by date all Fr
		@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
				"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,t_bill_header.total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
				"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
				"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id "
				+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY t_bill_header.bill_date",nativeQuery=true)

			List<SalesReportBillwise> getSaleReportBillwiseByDateAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
/*Sales Report Monthwise report no 4

SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code, 
			t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,t_bill_header.total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off,
			SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no,
			m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(15,20,24) 
			AND t_bill_header.bill_date BETWEEN '2018-01-01' AND '2018-01-12' AND t_bill_header.del_status=0 GROUP BY month
 */
	
	// report 4 Sales Report Monthwise group by month
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	//changed by harsha
	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2=:flag  GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthWithoutOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);

	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_sell_bill_header.bill_date),'--',YEAR(t_sell_bill_header.bill_date) )as month, t_sell_bill_header.sell_bill_no as bill_no,t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no,t_sell_bill_header.fr_id,t_sell_bill_header.fr_code," + 
			" '0' as tax_applicable,SUM(t_sell_bill_header.taxable_amt) as taxable_amt ,sum(t_sell_bill_header.total_tax) as total_tax,SUM(t_sell_bill_header.grand_total) AS grand_total ,'0' as round_off," + 
			"SUM(t_sell_bill_detail.sgst_rs) as sgst_sum ,SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,SUM(t_sell_bill_detail.igst_rs) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_sell_bill_detail,t_sell_bill_header  WHERE t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) "
			+ "AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status=0 AND t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no  GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
	//
	// report 4 Sales Report Monthwise group by month all Fr
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id"
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY month order by t_bill_header.bill_date",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
	
	
//changed By Harsha	
	
	@Query(value=" " + 
			"SELECT " + 
			"        MONTHNAME(t_bill_header.bill_date)as month," + 
			"        t_bill_header.bill_no," + 
			"        t_bill_header.bill_date," + 
			"        t_bill_header.invoice_no," + 
			"        t_bill_header.fr_id," + 
			"        t_bill_header.fr_code," + 
			"        t_bill_header.tax_applicable," + 
			"        sum(t_bill_detail.taxable_amt) as taxable_amt," + 
			"        sum(t_bill_detail.total_tax) as total_tax," + 
			"        SUM(t_bill_detail.grand_total) AS grand_total," + 
			"        t_bill_header.round_off," + 
			"        SUM(t_bill_detail.sgst_rs) as sgst_sum," + 
			"        SUM(t_bill_detail.cgst_rs) as cgst_sum ," + 
			"        SUM(t_bill_detail.igst_rs) as igst_sum," + 
			"        m_franchisee.fr_name," + 
			"        m_franchisee.fr_city," + 
			"        m_franchisee.fr_gst_no," + 
			"        m_franchisee.is_same_state," + 
			"        m_franchisee.fr_name " + 
			"    FROM" + 
			"        m_franchisee," + 
			"        t_bill_header ," + 
			"        t_bill_detail" + 
			"    WHERE" + 
			"        t_bill_header.fr_id=m_franchisee.fr_id " + 
			
			" AND t_bill_detail.cat_id IN(" + 
			"           :catIdList" + 
			"        ) " + 
			"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
			"        " + 
			"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag  " + 
			"    GROUP BY" + 
			"       t_bill_header.bill_no",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwiseAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int flag);

	
	
	@Query(value=" SELECT " + 
			"        MONTHNAME(t_bill_header.bill_date)as month," + 
			"        t_bill_header.bill_no," + 
			"        t_bill_header.bill_date," + 
			"        t_bill_header.invoice_no," + 
			"        t_bill_header.fr_id," + 
			"        t_bill_header.fr_code," + 
			"        t_bill_header.tax_applicable," + 
			"        SUM(t_bill_detail.taxable_amt) as taxable_amt ," + 
			"        t_bill_header.total_tax," + 
			"        SUM(t_bill_detail.grand_total) AS grand_total ," + 
			"        t_bill_header.round_off," + 
			"        SUM(t_bill_detail.sgst_rs)as sgst_sum ," + 
			"        SUM(t_bill_detail.cgst_rs) as cgst_sum ," + 
			"        SUM(t_bill_detail.igst_rs) as igst_sum," + 
			"        m_franchisee.fr_name," + 
			"        m_franchisee.fr_city," + 
			"        m_franchisee.fr_gst_no," + 
			"        m_franchisee.is_same_state," + 
			"        m_franchisee.fr_name " + 
			"    FROM" + 
			"        m_franchisee," + 
			"        t_bill_header," + 
			"        t_bill_detail" + 
			"    WHERE" + 
			"        t_bill_header.fr_id=m_franchisee.fr_id" + 
			"        AND t_bill_header.fr_id IN(" + 
			"           :frIdList" + 
			"        ) " + 
			"        AND t_bill_detail.cat_id IN (:catIdList)" + 
			"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 " + 
			"        AND t_bill_detail.bill_no=t_bill_header.bill_no AND  t_bill_header.ex_varchar2=:flag " + 
			"    GROUP BY" + 
			"        t_bill_header.fr_id",nativeQuery=true)
		List<SalesReportBillwise> getSaleReportBillwiseByFr(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int flag);


	//3 all fr
		@Query(value=" " + 
				"SELECT " + 
				"        MONTHNAME(t_sell_bill_header.bill_date)as month," + 
				"        t_sell_bill_header.sell_bill_no as bill_no," + 
				"        t_sell_bill_header.bill_date," + 
				"        t_sell_bill_header.invoice_no," + 
				"        t_sell_bill_header.fr_id," + 
				"        t_sell_bill_header.fr_code," + 
				"        '0' as tax_applicable," + 
				"        sum(t_sell_bill_header.taxable_amt) as taxable_amt," + 
				"        sum(t_sell_bill_header.total_tax) as total_tax," + 
				"        SUM(t_sell_bill_header.grand_total) AS grand_total," + 
				"        '0' as round_off," + 
				"        SUM(t_sell_bill_detail.sgst_rs) as sgst_sum," + 
				"        SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ," + 
				"        SUM(t_sell_bill_detail.igst_rs) as igst_sum," + 
				"        m_franchisee.fr_name," + 
				"        m_franchisee.fr_city," + 
				"        m_franchisee.fr_gst_no," + 
				"        m_franchisee.is_same_state," + 
				"        m_franchisee.fr_name " + 
				"    FROM" + 
				"        m_franchisee," + 
				"        t_sell_bill_header ," + 
				"        t_sell_bill_detail" + 
				"    WHERE" + 
				"        t_sell_bill_header.fr_id=m_franchisee.fr_id " + 
				
				" AND t_sell_bill_detail.cat_id IN(" + 
				"           :catIdList" + 
				"        ) " + 
				"        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
				"        AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no" + 
				"        " + 
				"        AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.del_status=0 " + 
				"    GROUP BY" + 
				"     t_sell_bill_header.sell_bill_no ",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrOutlet(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);
		
		
	//3 single fr 
	@Query(value=" " + 
			"SELECT " + 
			"        MONTHNAME(t_sell_bill_header.bill_date)as month," + 
			"        t_sell_bill_header.sell_bill_no as bill_no," + 
			"        t_sell_bill_header.bill_date," + 
			"        t_sell_bill_header.invoice_no," + 
			"        t_sell_bill_header.fr_id," + 
			"        t_sell_bill_header.fr_code," + 
			"        '0' as tax_applicable," + 
			"        sum(t_sell_bill_header.taxable_amt) as taxable_amt," + 
			"        sum(t_sell_bill_header.total_tax) as total_tax," + 
			"        SUM(t_sell_bill_header.grand_total) AS grand_total," + 
			"        '0' as round_off," + 
			"        SUM(t_sell_bill_detail.sgst_rs) as sgst_sum," + 
			"        SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ," + 
			"        SUM(t_sell_bill_detail.igst_rs) as igst_sum," + 
			"        m_franchisee.fr_name," + 
			"        m_franchisee.fr_city," + 
			"        m_franchisee.fr_gst_no," + 
			"        m_franchisee.is_same_state," + 
			"        m_franchisee.fr_name " + 
			"    FROM" + 
			"        m_franchisee," + 
			"        t_sell_bill_header ," + 
			"        t_sell_bill_detail" + 
			"    WHERE" + 
			"        t_sell_bill_header.fr_id=m_franchisee.fr_id " + 
			
			" AND t_sell_bill_detail.cat_id IN(" + 
			"           :catIdList" + 
			"        ) " + 
			"        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no" + 
			"        " + 
			"        AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.del_status=0  AND t_sell_bill_header.fr_id IN(:frIdList) " + 
			"    GROUP BY" + 
			"     t_sell_bill_header.sell_bill_no ",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwiseFrOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

}
