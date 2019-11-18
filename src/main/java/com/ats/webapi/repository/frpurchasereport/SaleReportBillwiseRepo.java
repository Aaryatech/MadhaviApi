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
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	
	//added by harsha 
	

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

 
	
	
	

	
	//
	// report 4 Sales Report Monthwise group by month all Fr
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id"
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
			"        m_franchisee.fr_name " + 
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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(0,1)  " + 
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
				"    m_franchisee.fr_name\n" + 
				"FROM\n" + 
				"    m_franchisee,\n" + 
				"    t_sell_bill_header,\n" + 
				"    t_sell_bill_detail\n" + 
				"WHERE\n" + 
				"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrAllType(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

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
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrOutletType3(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

		//3 all fr all type 1 or 2 selected
 
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
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrType12(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int  flag);


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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(0,1)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrType1N2(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);


//1 all fr all 1and3 or 2 and 3 selected
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
			"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag " + 
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
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_sell_bill_header,\n" + 
			"    t_sell_bill_detail\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwiseAllFrAllType1O2O3(@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int  flag);

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
				"        m_franchisee.fr_name " + 
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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(0,1)  " + 
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
				"    m_franchisee.fr_name\n" + 
				"FROM\n" + 
				"    m_franchisee,\n" + 
				"    t_sell_bill_header,\n" + 
				"    t_sell_bill_detail\n" + 
				"WHERE\n" + 
				"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseAllFrType(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);

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
				"        m_franchisee.fr_name " + 
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
				"        m_franchisee.fr_name " + 
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
				"        m_franchisee.fr_name " + 
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
				"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2 IN(0,1)  " + 
				"    GROUP BY" + 
				"       t_bill_header.bill_no",nativeQuery=true)
			
			List<SalesReportBillwise> getSaleReportBillwiseFrType1N2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList);


//1  fr all 1and3 or 2 and 3 selected
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
			"        t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList)  " + 
			
			" AND t_bill_detail.cat_id IN(" + 
			"           :catIdList" + 
			"        ) " + 
			"        AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate " + 
			"        AND t_bill_detail.bill_no=t_bill_header.bill_no" + 
			"        " + 
			"        AND t_bill_header.del_status=0 AND t_bill_detail.del_status=0 AND t_bill_header.ex_varchar2=:flag " + 
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
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_sell_bill_header,\n" + 
			"    t_sell_bill_detail\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList)  AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.del_status = 0\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_header.sell_bill_no)",nativeQuery=true)
		
		List<SalesReportBillwise> getSaleReportBillwiseAllFrType1O2O3(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("catIdList") List<String> catIdList,@Param("flag") int  flag);

 
	//Report 2 AlL fr 
	
	
	@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,SUM(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,SUM(t_bill_header.round_off) as round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  AND t_bill_header.ex_varchar2 IN(0,1) GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate12(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	

	@Query(value=" SELECT MONTHNAME(t_bill_header.bill_date)as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,SUM(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,SUM(t_bill_header.round_off) as round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0  AND t_bill_header.ex_varchar2=:flag GROUP BY t_bill_header.bill_date",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDateWithoutOutlet1O2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);
	
	
	@Query(value=" SELECT MONTHNAME(t_sell_bill_header.bill_date)as month, t_sell_bill_header.sell_bill_no as bill_no,t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no,t_sell_bill_header.fr_id,t_sell_bill_header.fr_code," + 
			" '0' as tax_applicable,SUM(t_sell_bill_header.taxable_amt) as taxable_amt ,SUM(t_sell_bill_header.total_tax) as total_tax,SUM(t_sell_bill_header.grand_total) AS grand_total ,'0' as round_off," + 
			"SUM(t_sell_bill_detail.sgst_rs)as sgst_sum ,SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,SUM(t_sell_bill_detail.igst_rs) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_sell_bill_header,t_sell_bill_detail WHERE t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) "
			+ " AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status=0 AND t_sell_bill_detail.sell_bill_no=t_sell_bill_header.sell_bill_no GROUP BY t_sell_bill_header.bill_date",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByDateOutlet(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	


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
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(0,1) \n" + 
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
			"        m_franchisee.fr_name\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_header,t_sell_bill_detail\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        t_sell_bill_header.bill_date\n" + 
			")",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDateAll(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	


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
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2=:flag \n" + 
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
			"        m_franchisee.fr_name\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_header,t_sell_bill_detail\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_detail.sell_bill_no = t_sell_bill_header.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        t_sell_bill_header.bill_date\n" + 
			")",nativeQuery=true)
 		List<SalesReportBillwise> getSaleReportBillwiseByDate1O2O3(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);
	
	
	
//Report 3 
	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2 IN(0,1) GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth12(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	@Query(value=" SELECT CONCAT(MONTHNAME(t_bill_header.bill_date),'--',YEAR(t_bill_header.bill_date) )as month, t_bill_header.bill_no,t_bill_header.bill_date,t_bill_header.invoice_no,t_bill_header.fr_id,t_bill_header.fr_code," + 
			"t_bill_header.tax_applicable,SUM(t_bill_header.taxable_amt) as taxable_amt ,sum(t_bill_header.total_tax) as total_tax,SUM(t_bill_header.grand_total) AS grand_total ,t_bill_header.round_off," + 
			"SUM(t_bill_header.sgst_sum)as sgst_sum ,SUM(t_bill_header.cgst_sum) as cgst_sum ,SUM(t_bill_header.igst_sum) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_bill_header WHERE t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) "
			+ "AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status=0 AND t_bill_header.ex_varchar2=:flag  GROUP BY month ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonth1O2(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("flag") int flag);



	@Query(value=" SELECT CONCAT(MONTHNAME(t_sell_bill_header.bill_date),'--',YEAR(t_sell_bill_header.bill_date) )as month, t_sell_bill_header.sell_bill_no as bill_no,t_sell_bill_header.bill_date,t_sell_bill_header.invoice_no,t_sell_bill_header.fr_id,t_sell_bill_header.fr_code," + 
			" '0' as tax_applicable,SUM(t_sell_bill_header.taxable_amt) as taxable_amt ,sum(t_sell_bill_header.total_tax) as total_tax,SUM(t_sell_bill_header.grand_total) AS grand_total ,'0' as round_off," + 
			"SUM(t_sell_bill_detail.sgst_rs) as sgst_sum ,SUM(t_sell_bill_detail.cgst_rs) as cgst_sum ,SUM(t_sell_bill_detail.igst_rs) as igst_sum,m_franchisee.fr_name,m_franchisee.fr_city,m_franchisee.fr_gst_no," + 
			"m_franchisee.is_same_state,m_franchisee.fr_name FROM m_franchisee,t_sell_bill_detail,t_sell_bill_header  WHERE t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) "
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
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    m_franchisee,\n" + 
			"    t_bill_header\n" + 
			"WHERE\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(0,1)\n" + 
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
			"        m_franchisee.fr_name\n" + 
			"    FROM\n" + 
			"        m_franchisee,\n" + 
			"        t_sell_bill_detail,\n" + 
			"        t_sell_bill_header\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no\n" + 
			"    GROUP BY\n" + 
			"        MONTH\n" + 
			") ",nativeQuery=true)

		List<SalesReportBillwise> getSaleReportBillwiseByMonthAll(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	
	
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
			"    m_franchisee.fr_name\n" + 
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
			"        m_franchisee.fr_name\n" + 
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
