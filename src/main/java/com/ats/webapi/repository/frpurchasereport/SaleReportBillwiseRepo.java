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
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id ,t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per" + 
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
			"			        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id,t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per  " + 
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
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	
	//added by harsha 
	

	// Report 3 sales report datewise group by date all Fr
		@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
				"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,t_bill_header.total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
				"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
				"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id "
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
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

 
	
	
	

	
	//
	// report 4 Sales Report Monthwise group by month all Fr
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id"
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 GROUP BY month order by t_bill_header.bill_date",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
	


	
	
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
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id,t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per " + 
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
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id,t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt " + 
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

	
	
	///
	
	


	@Query(value=" " + 
			"SELECT " + 
			"        MONTHNAME(t_sell_bill_header.bill_date)as month," + 
			"        t_sell_bill_header.sell_bill_no as bill_no," + 
			"        t_sell_bill_header.bill_date," + 
			"        t_sell_bill_header.invoice_no," + 
			"        t_sell_bill_header.fr_id," + 
			"        t_sell_bill_header.fr_code," + 
			"       '0' as tax_applicable," + 
			"        sum(t_sell_bill_detail.taxable_amt) as taxable_amt," + 
			"        sum(t_sell_bill_detail.total_tax) as total_tax," + 
			"        SUM(t_sell_bill_detail.grand_total) AS grand_total," + 
			"       '0' as  round_off," + 
			"        SUM(t_sell_bill_detail.sgst_rs) as sgst_sum," + 
			"        SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ," + 
			"        SUM(t_sell_bill_detail.igst_rs) as igst_sum," + 
			"        m_franchisee.fr_name," + 
			"        m_franchisee.fr_city," + 
			"        m_franchisee.fr_gst_no," + 
			"        m_franchisee.is_same_state," + 
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt " + 
			"    FROM" + 
			"        m_franchisee," + 
			"        t_sell_bill_header ," + 
			"        t_sell_bill_detail" + 
			"    WHERE" + 
			"        t_sell_bill_header.fr_id=m_franchisee.fr_id " + 
			"        AND t_sell_bill_header.fr_id IN(" + 
			"           :frIdList" + 
			"        ) " + 
			" AND t_sell_bill_detail.cat_id IN(" + 
			"           :catIdList" + 
			"        ) " + 
			"        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no" + 
			"        " + 
			"        AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.del_status=0 " + 
			"    GROUP BY" + 
			"       t_sell_bill_header.sell_bill_no",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwiseWithOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);


	
	//changed By Harsha 7 condition for all fr	
		
	
	//1 all fr all types selected
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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
				" " + 
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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no UNION (SELECT\n" + 
				"    MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
				"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
				"    t_sell_bill_header.bill_date,\n" + 
				"    t_sell_bill_header.invoice_no,\n" + 
				"    t_sell_bill_header.fr_id,\n" + 
				"    t_sell_bill_header.fr_code,\n" + 
				"    '0' AS tax_applicable,\n" + 
				"    SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
				"    SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
				"    SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
				"    '0' AS round_off,\n" + 
				"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
				"    m_franchisee.fr_name,\n" + 
				"    m_franchisee.fr_city,\n" + 
				"    m_franchisee.fr_gst_no,\n" + 
				"    m_franchisee.is_same_state,\n" + 
				"    m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
				"FROM\n" + 
				"    m_franchisee,\n" + 
				"    t_sell_bill_header,\n" + 
				"    t_sell_bill_detail\n" + 
				"WHERE\n" + 
				"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrAllType(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("temp") List<Integer> temp);

//2 all fr all type 3 selected
		
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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id,t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt " + 
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
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrOutletType3(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

		//Anmol
		@Query(value=" " + 
				"SELECT\n" + 
				"    MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
				"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
				"    t_sell_bill_header.bill_date,\n" + 
				"    t_sell_bill_header.invoice_no,\n" + 
				"    t_sell_bill_header.fr_id,\n" + 
				"    t_sell_bill_header.fr_code,\n" + 
				"    '0' AS tax_applicable,\n" + 
				"    SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
				"    SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
				"    SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
				"    '0' AS round_off,\n" + 
				"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
				"    m_franchisee.fr_name,\n" + 
				"    m_franchisee.fr_city,\n" + 
				"    m_franchisee.fr_gst_no,\n" + 
				"    m_franchisee.is_same_state,\n" + 
				"    m_customer.cust_name,\n" + 
				"    m_customer.cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt\n" + 
				"FROM\n" + 
				"    m_franchisee,\n" + 
				"    t_sell_bill_header,\n" + 
				"    t_sell_bill_detail,\n" + 
				"    m_customer\n" + 
				"WHERE\n" + 
				"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0 AND t_sell_bill_header.cust_id = m_customer.cust_id\n" + 
				"GROUP BY\n" + 
				"    t_sell_bill_header.sell_bill_no\n" + 
				"ORDER BY\n" + 
				"    t_sell_bill_header.bill_date,\n" + 
				"    t_sell_bill_header.invoice_no ",nativeQuery=true)
			
			List<SalesReportBillwise> getAdminSaleReportBillwiseAllFrOutletType3(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		
		
		
		//Comp outlet bills and dairy mart
		//Anmol
		@Query(value="SELECT\n" + 
				"    t1.*\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        MONTHNAME(t_bill_header.bill_date) AS MONTH,\n" + 
				"        t_bill_header.bill_no,\n" + 
				"        t_bill_header.bill_date,\n" + 
				"        t_bill_header.invoice_no,\n" + 
				"        t_bill_header.fr_id,\n" + 
				"        t_bill_header.fr_code,\n" + 
				"        t_bill_header.tax_applicable,\n" + 
				"        SUM(t_bill_detail.taxable_amt) AS taxable_amt,\n" + 
				"        SUM(t_bill_detail.total_tax) AS total_tax,\n" + 
				"        SUM(t_bill_detail.grand_total) AS grand_total,\n" + 
				"        t_bill_header.round_off,\n" + 
				"        SUM(t_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"        SUM(t_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"        SUM(t_bill_detail.igst_rs) AS igst_sum,\n" + 
				"        m_franchisee.fr_name,\n" + 
				"        m_franchisee.fr_city,\n" + 
				"        m_franchisee.fr_gst_no,\n" + 
				"        m_franchisee.is_same_state,\n" + 
				"        'cust' AS cust_name,\n" + 
				"        0 AS cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per \n" + 
				"\n" + 
				"    FROM\n" + 
				"        m_franchisee,\n" + 
				"        t_bill_header,\n" + 
				"        t_bill_detail\n" + 
				"    WHERE\n" + 
				"        t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.bill_no = t_bill_header.bill_no AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0  AND t_bill_header.is_dairy_mart IN(:dairy)\n" + 
				"    GROUP BY\n" + 
				"        t_bill_header.bill_no\n" + 
				"    UNION\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
				"            t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
				"            t_sell_bill_header.bill_date,\n" + 
				"            t_sell_bill_header.invoice_no,\n" + 
				"            t_sell_bill_header.fr_id,\n" + 
				"            t_sell_bill_header.fr_code,\n" + 
				"            '0' AS tax_applicable,\n" + 
				"            SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
				"            SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
				"            SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
				"            '0' AS round_off,\n" + 
				"            SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"            SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"            SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
				"            m_franchisee.fr_name,\n" + 
				"            m_franchisee.fr_city,\n" + 
				"            m_franchisee.fr_gst_no,\n" + 
				"            m_franchisee.is_same_state,\n" + 
				"            m_customer.cust_name,\n" + 
				"            m_customer.cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt " + 
				"        FROM\n" + 
				"            m_franchisee,\n" + 
				"            t_sell_bill_header,\n" + 
				"            t_sell_bill_detail,\n" + 
				"            m_customer\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0 AND t_sell_bill_header.cust_id = m_customer.cust_id\n" + 
				"        GROUP BY\n" + 
				"            t_sell_bill_header.sell_bill_no\n" + 
				"        ORDER BY\n" + 
				"            t_sell_bill_header.bill_date,\n" + 
				"            t_sell_bill_header.invoice_no\n" + 
				"    )) t1\n" + 
				"        \n" + 
				"       ORDER BY bill_date,invoice_no,fr_id ",nativeQuery=true)
			
			List<SalesReportBillwise> getAdminSaleReportBillwiseAllFrOutletType3withDairymart(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("dairy") List<Integer> dairy);

		
		
		
		//3 all fr all type 1 or 2 selected
  

//4 all fr all type 1 and  2 selected

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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
				" " + 
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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrType1N2(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("temp") List<Integer> temp);


//Anmol
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
		"        m_franchisee.is_same_state, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
		" " + 
		"    FROM" + 
		"        m_franchisee," + 
		"        t_bill_header ," + 
		"        t_bill_detail" + 
		"    WHERE" + 
		"        t_bill_header.fr_id=m_franchisee.fr_id " + 
		"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
		"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
		"        " + 
		"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.is_dairy_mart IN(:dairy)  " + 
		"    GROUP BY" + 
		"       t_bill_header.bill_no ORDER BY t_bill_header.bill_date,t_bill_header.invoice_no ",nativeQuery=true)
	
	List<SalesReportBillwise> getAdminSaleReportBillwiseAllFrType1N2(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp,@Param("dairy") List<Integer> dairy);



//1 all fr all 1and3 or 2 and 3 selected
	 

//all fr ends 
	
	
	//few fr 

	 
	//1 all fr all types selected
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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
				" " + 
				"    FROM" + 
				"        m_franchisee," + 
				"        t_bill_header ," + 
				"        t_bill_detail" + 
				"    WHERE" + 
				"        t_bill_header.fr_id=m_franchisee.fr_id  AND t_bill_header.fr_id IN(:frIdList) \n" + 
				" " + 
				
				" AND t_bill_detail.cat_id IN(" + 
				"           :catIdList" + 
				"        ) " + 
				"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
				"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
				"        " + 
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no UNION (SELECT\n" + 
				"    MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
				"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
				"    t_sell_bill_header.bill_date,\n" + 
				"    t_sell_bill_header.invoice_no,\n" + 
				"    t_sell_bill_header.fr_id,\n" + 
				"    t_sell_bill_header.fr_code,\n" + 
				"    '0' AS tax_applicable,\n" + 
				"    SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
				"    SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
				"    SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
				"    '0' AS round_off,\n" + 
				"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"    SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
				"    m_franchisee.fr_name,\n" + 
				"    m_franchisee.fr_city,\n" + 
				"    m_franchisee.fr_gst_no,\n" + 
				"    m_franchisee.is_same_state,\n" + 
				"    m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
				"FROM\n" + 
				"    m_franchisee,\n" + 
				"    t_sell_bill_header,\n" + 
				"    t_sell_bill_detail\n" + 
				"WHERE\n" + 
				"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrType(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("temp") List<Integer> temp);

//2 all fr  type 3 selected
		
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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id,t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt  " + 
				"    FROM" + 
				"        m_franchisee," + 
				"        t_sell_bill_header ," + 
				"        t_sell_bill_detail" + 
				"    WHERE" + 
				"        t_sell_bill_header.fr_id=m_franchisee.fr_id  AND t_sell_bill_header.fr_id IN(:frIdList) " + 
				
				" AND t_sell_bill_detail.cat_id IN(" + 
				"           :catIdList" + 
				"        ) " + 
				"        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
				"        AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no" + 
				"        " + 
				"        AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.del_status=0 " + 
				"    GROUP BY" + 
				"     t_sell_bill_header.sell_bill_no ",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseFrOutletType3(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

		
		//Anmol
		@Query(value=" " + 
				"SELECT   \n" + 
				"				        MONTHNAME(t_sell_bill_header.bill_date)as month,  \n" + 
				"				        t_sell_bill_header.sell_bill_no as bill_no,  \n" + 
				"				        t_sell_bill_header.bill_date,  \n" + 
				"				        t_sell_bill_header.invoice_no,  \n" + 
				"				        t_sell_bill_header.fr_id,  \n" + 
				"				        t_sell_bill_header.fr_code,  \n" + 
				"				        '0' as tax_applicable,  \n" + 
				"				        t_sell_bill_header.taxable_amt as taxable_amt,  \n" + 
				"				        t_sell_bill_header.total_tax as total_tax,  \n" + 
				"				        t_sell_bill_header.grand_total AS grand_total,  \n" + 
				"				        '0' as round_off,  \n" + 
				"				        SUM(t_sell_bill_detail.sgst_rs) as sgst_sum,  \n" + 
				"				        SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,  \n" + 
				"				        SUM(t_sell_bill_detail.igst_rs) as igst_sum,  \n" + 
				"				        m_franchisee.fr_name,  \n" + 
				"				        m_franchisee.fr_city,  \n" + 
				"				        m_franchisee.fr_gst_no,  \n" + 
				"				        m_franchisee.is_same_state,\n" + 
				"                        m_customer.cust_name,\n" + 
				"                        m_customer.cust_id, t_sell_bill_header.discount_per as disc_per,\n" + 
				"    t_sell_bill_header.discount_amt as disc_amt" + 
				"				    FROM  \n" + 
				"				        m_franchisee,  \n" + 
				"				        t_sell_bill_header ,  \n" + 
				"				        t_sell_bill_detail,\n" + 
				"                        m_customer\n" + 
				"				    WHERE  \n" + 
				"				        t_sell_bill_header.fr_id=m_franchisee.fr_id  AND t_sell_bill_header.fr_id IN(:frIdList)   \n" + 
				"				        AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate   \n" + 
				"				        AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no  \n" + 
				"				           \n" + 
				"				        AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.del_status=0 AND\n" + 
				"                        t_sell_bill_header.cust_id=m_customer.cust_id\n" + 
				"				    GROUP BY  \n" + 
				"				     t_sell_bill_header.sell_bill_no\n" + 
				"                     ORDER BY t_sell_bill_header.bill_date, t_sell_bill_header.invoice_no ",nativeQuery=true)
			
			List<SalesReportBillwise> getAdminSaleReportBillwiseFrOutletType3(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		
		//Anmol
		//company outlet bill dairy mart
		@Query(value="SELECT\n" + 
				"    *\n" + 
				"FROM\n" + 
				"    (\n" + 
				"    SELECT\n" + 
				"        MONTHNAME(t_bill_header.bill_date) AS MONTH,\n" + 
				"        t_bill_header.bill_no,\n" + 
				"        t_bill_header.bill_date,\n" + 
				"        t_bill_header.invoice_no,\n" + 
				"        t_bill_header.fr_id,\n" + 
				"        t_bill_header.fr_code,\n" + 
				"        t_bill_header.tax_applicable,\n" + 
				"        SUM(t_bill_detail.taxable_amt) AS taxable_amt,\n" + 
				"        SUM(t_bill_detail.total_tax) AS total_tax,\n" + 
				"        SUM(t_bill_detail.grand_total) AS grand_total,\n" + 
				"        t_bill_header.round_off,\n" + 
				"        SUM(t_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"        SUM(t_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"        SUM(t_bill_detail.igst_rs) AS igst_sum,\n" + 
				"        m_franchisee.fr_name,\n" + 
				"        m_franchisee.fr_city,\n" + 
				"        m_franchisee.fr_gst_no,\n" + 
				"        m_franchisee.is_same_state,\n" + 
				"        'cust' AS cust_name,\n" + 
				"        0 AS cust_id, t_bill_header.disc_amt,\n" + 
				"(t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per" + 
				"    FROM\n" + 
				"        m_franchisee,\n" + 
				"        t_bill_header,\n" + 
				"        t_bill_detail\n" + 
				"    WHERE\n" + 
				"        t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.bill_no = t_bill_header.bill_no AND t_bill_header.del_status = 0 AND t_bill_detail.del_status = 0 AND t_bill_header.is_dairy_mart=2\n" + 
				"    GROUP BY\n" + 
				"        t_bill_header.bill_no\n" + 
				"    UNION\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
				"            t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
				"            t_sell_bill_header.bill_date,\n" + 
				"            t_sell_bill_header.invoice_no,\n" + 
				"            t_sell_bill_header.fr_id,\n" + 
				"            t_sell_bill_header.fr_code,\n" + 
				"            '0' AS tax_applicable,\n" + 
				"            t_sell_bill_header.taxable_amt AS taxable_amt,\n" + 
				"            t_sell_bill_header.total_tax AS total_tax,\n" + 
				"            t_sell_bill_header.grand_total AS grand_total,\n" + 
				"            '0' AS round_off,\n" + 
				"            SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
				"            SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
				"            SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
				"            m_franchisee.fr_name,\n" + 
				"            m_franchisee.fr_city,\n" + 
				"            m_franchisee.fr_gst_no,\n" + 
				"            m_franchisee.is_same_state,\n" + 
				"            m_customer.cust_name,\n" + 
				"            m_customer.cust_id, t_sell_bill_header.discount_per as disc_per,t_sell_bill_header.discount_amt as disc_amt" + 
				"        FROM\n" + 
				"            m_franchisee,\n" + 
				"            t_sell_bill_header,\n" + 
				"            t_sell_bill_detail,\n" + 
				"            m_customer\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0 AND t_sell_bill_header.cust_id = m_customer.cust_id\n" + 
				"        GROUP BY\n" + 
				"            t_sell_bill_header.sell_bill_no\n" + 
				"        ORDER BY\n" + 
				"            t_sell_bill_header.bill_date,\n" + 
				"            t_sell_bill_header.invoice_no\n" + 
				"    )\n" + 
				") t2\n" + 
				"\n" + 
				"ORDER BY bill_date,invoice_no,fr_id ",nativeQuery=true)
			
			List<SalesReportBillwise> getAdminSaleReportBillwiseFrOutletType3WithDairymart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		
		
		
		//3  fr all type 1 or 2 selected
 
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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
				" " + 
				"    FROM" + 
				"        m_franchisee," + 
				"        t_bill_header ," + 
				"        t_bill_detail" + 
				"    WHERE" + 
				"        t_bill_header.fr_id=m_franchisee.fr_id AND  t_bill_header.fr_id IN(:frIdList) " + 
				
				" AND t_bill_detail.cat_id IN(" + 
				"           :catIdList" + 
				"        ) " + 
				"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
				"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
				"        " + 
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseFrType12(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int  flag);


//4  fr all type 1 and  2 selected

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
				"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
				" " + 
				"    FROM" + 
				"        m_franchisee," + 
				"        t_bill_header ," + 
				"        t_bill_detail" + 
				"    WHERE" + 
				"        t_bill_header.fr_id=m_franchisee.fr_id AND  t_bill_header.fr_id IN(:frIdList) " + 
				
				" AND t_bill_detail.cat_id IN(" + 
				"           :catIdList" + 
				"        ) " + 
				"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
				"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
				"        " + 
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseFrType1N2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("temp") List<Integer> temp);



//Anmol
@Query(value=" " + 
		"SELECT " + 
		"        MONTHNAME(t_bill_header.bill_date)as month," + 
		"        t_bill_header.bill_no," + 
		"        t_bill_header.bill_date," + 
		"        t_bill_header.invoice_no," + 
		"        t_bill_header.fr_id," + 
		"        t_bill_header.fr_code," + 
		"        t_bill_header.tax_applicable," + 
		"        SUM(t_bill_detail.taxable_amt) as taxable_amt," + 
		"        SUM(t_bill_detail.total_tax) as total_tax," + 
		"        SUM(t_bill_detail.grand_total) AS grand_total," + 
		"        t_bill_header.round_off," + 
		"        SUM(t_bill_detail.sgst_rs) as sgst_sum," + 
		"        SUM(t_bill_detail.cgst_rs) as cgst_sum ," + 
		"        SUM(t_bill_detail.igst_rs) as igst_sum," + 
		"        m_franchisee.fr_name," + 
		"        m_franchisee.fr_city," + 
		"        m_franchisee.fr_gst_no," + 
		"        m_franchisee.is_same_state, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt,\n" + 
		"    (t_bill_header.disc_amt * 100) /(\n" + 
		"        t_bill_header.grand_total + t_bill_header.disc_amt\n" + 
		"    ) AS disc_per " + 
		"    FROM" + 
		"        m_franchisee," + 
		"        t_bill_header ," + 
		"        t_bill_detail" + 
		"    WHERE" + 
		"        t_bill_header.fr_id=m_franchisee.fr_id AND  t_bill_header.fr_id IN(:frIdList) " + 
		"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
		"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
		"        " + 
		"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.is_dairy_mart IN(:dairy)  " + 
		"    GROUP BY" + 
		"       t_bill_header.bill_no ORDER BY t_bill_header.bill_date,t_bill_header.invoice_no ",nativeQuery=true)
	
	List<SalesReportBillwise> getAdminSaleReportBillwiseFrType1N2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp,@Param("dairy") List<Integer> dairy);



//1  fr all 1and3 or 2 and 3 selected
	 
 
	//Report 2 AlL fr 
	
	
	@Query(value=" SELECT\n" + 
			"    MONTHNAME(t_bill_header.bill_date) AS MONTH,\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    SUM(t_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_bill_header.grand_total) AS grand_total,\n" + 
			"    SUM(t_bill_header.round_off) AS round_off,\n" + 
			"    SUM(t_bill_header.sgst_sum) AS sgst_sum,\n" + 
			"    SUM(t_bill_header.cgst_sum) AS cgst_sum,\n" + 
			"    SUM(t_bill_header.igst_sum) AS igst_sum,\n" + 
			"    t_bill_header.ex_varchar3 as fr_name,\n" + 
			"    t_bill_header.ex_varchar5 as fr_city,\n" + 
			"    t_bill_header.ex_varchar4 as fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    'cust' AS cust_name,\n" + 
			"    0 AS cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			"\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
			"GROUP BY\n" + 
			"    t_bill_header.bill_date\n" + 
			"    ORDER BY t_bill_header.bill_date,t_bill_header.invoice_no",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate12(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer>  temp);
	
	
	//ANMOL -- 21-2-2020
	@Query(value=" SELECT\n" + 
			"    MONTHNAME(t_bill_header.bill_date) AS MONTH,\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    SUM(t_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_bill_header.grand_total) AS grand_total,\n" + 
			"    SUM(t_bill_header.round_off) AS round_off,\n" + 
			"    SUM(t_bill_header.sgst_sum) AS sgst_sum,\n" + 
			"    SUM(t_bill_header.cgst_sum) AS cgst_sum,\n" + 
			"    SUM(t_bill_header.igst_sum) AS igst_sum,\n" + 
			"    t_bill_header.ex_varchar3 as fr_name,\n" + 
			"    t_bill_header.ex_varchar5 as fr_city,\n" + 
			"    t_bill_header.ex_varchar4 as fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    'cust' AS cust_name,\n" + 
			"    0 AS cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			"\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.is_dairy_mart IN(:dairy)\n" + 
			"GROUP BY\n" + 
			"    t_bill_header.bill_date\n" + 
			"    ORDER BY t_bill_header.bill_date,t_bill_header.invoice_no",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate12WithDairyMart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer>  temp,@Param("dairy") List<Integer>  dairy);
	
 
	
	@Query(value=" \n" + 
			"        SELECT\n" + 
			"    CONCAT(\n" + 
			"        MONTHNAME(t_sell_bill_header.bill_date),\n" + 
			"        '--',\n" + 
			"        YEAR(t_sell_bill_header.bill_date)\n" + 
			"    ) AS MONTH,\n" + 
			"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.fr_id,\n" + 
			"    t_sell_bill_header.fr_code,\n" + 
			"    '0' AS tax_applicable,\n" + 
			"    SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
			"    '0' AS round_off,\n" + 
			"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
			"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
			"    SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_customer.cust_name,\n" + 
			"    m_customer.cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_customer\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no\n" + 
			"    AND t_sell_bill_header.cust_id=m_customer.cust_id\n" + 
			"GROUP BY\n" + 
			"    MONTH\n" + 
			"    ORDER BY t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByDateOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	

	//Anmol-------21-2-2020
	@Query(value=" \n" + 
			"        SELECT\n" + 
			"    CONCAT(\n" + 
			"        MONTHNAME(t_sell_bill_header.bill_date),\n" + 
			"        '--',\n" + 
			"        YEAR(t_sell_bill_header.bill_date)\n" + 
			"    ) AS MONTH,\n" + 
			"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.fr_id,\n" + 
			"    t_sell_bill_header.fr_code,\n" + 
			"    '0' AS tax_applicable,\n" + 
			"    SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
			"    '0' AS round_off,\n" + 
			"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
			"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
			"    SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_customer.cust_name,\n" + 
			"    m_customer.cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_customer\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no\n" + 
			"    AND t_sell_bill_header.cust_id=m_customer.cust_id\n" + 
			"GROUP BY\n" + 
			"    MONTH\n" + 
			"    ORDER BY t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no ",nativeQuery=true)

		List<SalesReportBillwise> getAdminMonthWiseCompBills(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	

	@Query(value=" SELECT\n" + 
			"    MONTHNAME(t_bill_header.bill_date) AS MONTH,\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    SUM(t_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_bill_header.grand_total) AS grand_total,\n" + 
			"    SUM(t_bill_header.round_off) AS round_off,\n" + 
			"    SUM(t_bill_header.sgst_sum) AS sgst_sum,\n" + 
			"    SUM(t_bill_header.cgst_sum) AS cgst_sum,\n" + 
			"    SUM(t_bill_header.igst_sum) AS igst_sum,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			" \n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp) \n" + 
			"GROUP BY\n" + 
			"    t_bill_header.bill_date\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        MONTHNAME(t_sell_bill_header.bill_date) AS MONTH,\n" + 
			"        t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"        t_sell_bill_header.bill_date,\n" + 
			"        t_sell_bill_header.invoice_no,\n" + 
			"        t_sell_bill_header.fr_id,\n" + 
			"        t_sell_bill_header.fr_code,\n" + 
			"        '0' AS tax_applicable,\n" + 
			"        SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"        SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
			"        SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
			"        '0' AS round_off,\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_city,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        m_franchisee.is_same_state,\n" + 
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_header,t_sell_bill_detail\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        t_sell_bill_header.bill_date\n" + 
			")",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDateAll(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer>  temp);
	

//rep2 ends 	
	
//Report 3 
	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			"  FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp) GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth12(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	//Anmol---21-2-2020
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			"  FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2 IN(:temp) AND t_bill_header.is_dairy_mart IN(:dairy) GROUP BY month ORDER BY month",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth12WithDairyMart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp,@Param("dairy") List<Integer> dairy);
	
	
	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			" FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2=:flag  GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth1O2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);



	@Query(value=" SELECT CONCAT(MONTHNAME(t_sell_bill_header.bill_date),'--',YEAR(t_sell_bill_header.bill_date) )as month, t_sell_bill_header.sell_bill_no as bill_no,t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no,t_sell_bill_header.fr_id,t_sell_bill_header.fr_code," + 
			" '0' as tax_applicable,SUM(t_sell_bill_header.taxable_amt) as taxable_amt ,sum(t_sell_bill_header.total_tax) as total_tax,SUM(t_sell_bill_header.grand_total) AS grand_total ,'0' as round_off," + 
			"SUM(t_sell_bill_detail.sgst_rs) as sgst_sum ,SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,SUM(t_sell_bill_detail.igst_rs) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			"  FROM m_franchisee,t_sell_bill_detail,t_sell_bill_header  WHERE t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) "
			+ "AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status=0 AND t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no  GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	@Query(value="SELECT\n" + 
			"    CONCAT(\n" + 
			"        MONTHNAME(t_bill_header.bill_date),\n" + 
			"        '--',\n" + 
			"        YEAR(t_bill_header.bill_date)\n" + 
			"    ) AS MONTH,\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    SUM(t_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_bill_header.grand_total) AS grand_total,\n" + 
			"    t_bill_header.round_off,\n" + 
			"    SUM(t_bill_header.sgst_sum) AS sgst_sum,\n" + 
			"    SUM(t_bill_header.cgst_sum) AS cgst_sum,\n" + 
			"    SUM(t_bill_header.igst_sum) AS igst_sum,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			" \n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
			"GROUP BY\n" + 
			"    MONTH\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            MONTHNAME(t_sell_bill_header.bill_date),\n" + 
			"            '--',\n" + 
			"            YEAR(t_sell_bill_header.bill_date)\n" + 
			"        ) AS MONTH,\n" + 
			"        t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"        t_sell_bill_header.bill_date,\n" + 
			"        t_sell_bill_header.invoice_no,\n" + 
			"        t_sell_bill_header.fr_id,\n" + 
			"        t_sell_bill_header.fr_code,\n" + 
			"        '0' AS tax_applicable,\n" + 
			"        SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"        SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
			"        SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
			"        '0' AS round_off,\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_city,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        m_franchisee.is_same_state,\n" + 
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id , t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_detail,\n" + 
			"        t_sell_bill_header\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        MONTH\n" + 
			") ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthAll(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	@Query(value="SELECT\n" + 
			"    CONCAT(\n" + 
			"        MONTHNAME(t_bill_header.bill_date),\n" + 
			"        '--',\n" + 
			"        YEAR(t_bill_header.bill_date)\n" + 
			"    ) AS MONTH,\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    SUM(t_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"    SUM(t_bill_header.total_tax) AS total_tax,\n" + 
			"    SUM(t_bill_header.grand_total) AS grand_total,\n" + 
			"    t_bill_header.round_off,\n" + 
			"    SUM(t_bill_header.sgst_sum) AS sgst_sum,\n" + 
			"    SUM(t_bill_header.cgst_sum) AS cgst_sum,\n" + 
			"    SUM(t_bill_header.igst_sum) AS igst_sum,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_city,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    m_franchisee.is_same_state,\n" + 
			"    m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_bill_header.disc_amt, (t_bill_header.disc_amt * 100) /(t_bill_header.grand_total + t_bill_header.disc_amt) AS disc_per\n" + 
			" \n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2=:flag \n" + 
			"GROUP BY\n" + 
			"    MONTH\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            MONTHNAME(t_sell_bill_header.bill_date),\n" + 
			"            '--',\n" + 
			"            YEAR(t_sell_bill_header.bill_date)\n" + 
			"        ) AS MONTH,\n" + 
			"        t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"        t_sell_bill_header.bill_date,\n" + 
			"        t_sell_bill_header.invoice_no,\n" + 
			"        t_sell_bill_header.fr_id,\n" + 
			"        t_sell_bill_header.fr_code,\n" + 
			"        '0' AS tax_applicable,\n" + 
			"        SUM(t_sell_bill_header.taxable_amt) AS taxable_amt,\n" + 
			"        SUM(t_sell_bill_header.total_tax) AS total_tax,\n" + 
			"        SUM(t_sell_bill_header.grand_total) AS grand_total,\n" + 
			"        '0' AS round_off,\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_sum,\n" + 
			"        SUM(t_sell_bill_detail.igst_rs) AS igst_sum,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_city,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        m_franchisee.is_same_state,\n" + 
			"        m_franchisee.fr_name, 'cust' as cust_name, 0 as cust_id, t_sell_bill_header.discount_per as disc_per, t_sell_bill_header.discount_amt as disc_amt \n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_detail,\n" + 
			"        t_sell_bill_header\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        MONTH\n" + 
			") ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth1O2O3(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);

}
