package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.GetRepTaxSell;
 
public interface RepTaxSellRepository extends JpaRepository<GetRepTaxSell, Integer>{

	@Query(value=" select h.user_gst_no, h.bill_date, d.sell_bill_detail_no,h.invoice_no as sell_bill_no, d.cgst_per+d.sgst_per as tax_per,"
			+" sum(d.taxable_amt) as tax_amount, sum(d.cgst_rs) as cgst, sum(d.sgst_rs) as sgst, sum(d.igst_rs) as igst, h.fr_id, f.fr_name, sum(d.grand_total) as bill_amount from t_sell_bill_detail d,"
			+" t_sell_bill_header h, m_franchisee f WHERE h.fr_id IN(:frId) AND h.sell_bill_no=d.sell_bill_no AND h.bill_date BETWEEN :fromDate"
			+" AND :toDate AND f.fr_id=h.fr_id group by (d.cgst_per+d.sgst_per), h.fr_id  "
			+ ""
			+ ""
			+ "UNION ALL " + 
			"        SELECT t_sp_cake. cust_gstin  as user_gst_no," + 
			"        t_sp_cake.sp_delivery_date as bill_date," + 
			"        t_sp_cake.sp_order_no as sell_bill_detail_no," + 
			"        t_sp_cake.sp_book_for_mob_no as sell_bill_no," + 
			"        t_sp_cake.tax_1+t_sp_cake.tax_2 as tax_per," + 
			"        SUM(t_sp_cake.sp_grand_total-(t_sp_cake.tax_1_amt+t_sp_cake.tax_2_amt)) as tax_amount," + 
			"        SUM(t_sp_cake.tax_1_amt) as cgst," + 
			"        SUM(t_sp_cake.tax_2_amt) as sgst," + 
			"        0 as igst," + 
			"            m_franchisee.fr_id," + 
			"            m_franchisee.fr_name," + 
			"                    sum(t_sp_cake.sp_grand_total) as bill_amount " + 
			"" + 
			"            from m_franchisee,t_sp_cake" + 
			"            WHERE t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id=t_sp_cake.fr_id" + 
			"            AND      t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate " + 
			"" + 
			"            GROUP BY (t_sp_cake.tax_1+t_sp_cake.tax_2)," + 
			"            t_sp_cake.fr_id",nativeQuery=true)
	List<GetRepTaxSell> getRepFrTaxSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);

	/***********************************************************************************************************/
	/*
	 * @Query(
	 * value=" select h.user_gst_no, h.bill_date, d.sell_bill_detail_no,h.invoice_no as sell_bill_no, d.cgst_per+d.sgst_per as tax_per,"
	 * +" sum(d.taxable_amt) as tax_amount, sum(d.cgst_rs) as cgst, sum(d.sgst_rs) as sgst, sum(d.igst_rs) as igst, h.fr_id, f.fr_name, sum(d.grand_total) as bill_amount from t_sell_bill_detail d,"
	 * +" t_sell_bill_header h, m_franchisee f WHERE h.fr_id IN(:frId) AND h.sell_bill_no=d.sell_bill_no AND h.bill_date BETWEEN :fromDate "
	 * +" AND :toDate AND f.fr_id=h.fr_id group by (d.cgst_per+d.sgst_per),h.bill_date, h.fr_id  "
	 * + "  UNION ALL   " +
	 * "			        SELECT t_sp_cake. cust_gstin  as user_gst_no,  " +
	 * "			        t_sp_cake.sp_delivery_date as bill_date,  " +
	 * "			        t_sp_cake.sp_order_no as sell_bill_detail_no,  " +
	 * "			        t_sp_cake.sp_book_for_mob_no as sell_bill_no,  " +
	 * "			        t_sp_cake.tax_1+t_sp_cake.tax_2 as tax_per,  " +
	 * "			        SUM(t_sp_cake.sp_grand_total-(t_sp_cake.tax_1_amt+t_sp_cake.tax_2_amt)) as tax_amount, "
	 * + "			        SUM(t_sp_cake.tax_1_amt) as cgst,  " +
	 * "			        SUM(t_sp_cake.tax_2_amt) as sgst,  " +
	 * "			       0 as igst, " +
	 * "			            m_franchisee.fr_id, " +
	 * "			            m_franchisee.fr_name,  " +
	 * "			                    sum(t_sp_cake.sp_grand_total) as bill_amount   "
	 * + "			  " + "			            from m_franchisee,t_sp_cake  " +
	 * "			            WHERE t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id=t_sp_cake.fr_id  "
	 * +
	 * "			            AND      t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate   "
	 * + "			" +
	 * "			            GROUP BY (t_sp_cake.tax_1+t_sp_cake.tax_2),t_sp_cake.sp_delivery_date,  "
	 * + "			            t_sp_cake.fr_id" + "",nativeQuery=true)
	 */
	
	@Query(value="SELECT\r\n" + 
			"        h.user_gst_no,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        d.sell_bill_detail_no,\r\n" + 
			"        h.invoice_no AS sell_bill_no,\r\n" + 
			"        d.cgst_per + d.sgst_per AS tax_per,\r\n" + 
			"        SUM(d.taxable_amt) AS tax_amount,\r\n" + 
			"        SUM(d.cgst_rs) AS cgst,\r\n" + 
			"        SUM(d.sgst_rs) AS sgst,\r\n" + 
			"        SUM(d.igst_rs) AS igst,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        SUM(d.grand_total) AS bill_amount  \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_franchisee f  \r\n" + 
			"    WHERE\r\n" + 
			"        h.fr_id IN(:frId) \r\n" + 
			"        AND h.sell_bill_no = d.sell_bill_no \r\n" + 
			"        AND h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"        AND f.fr_id = h.fr_id AND h.del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"    h.fr_id ,\r\n" + 
			"     h.bill_date,\r\n" + 
			"        (d.cgst_per + d.sgst_per)",nativeQuery=true)
	
	List<GetRepTaxSell> getRepFrDatewiseTaxSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);

/*	SELECT\r\n" + 
	"    h.user_gst_no,\r\n" + 
	"    h.bill_date,\r\n" + 
	"    d.sell_bill_detail_no,\r\n" + 
	"    h.invoice_no AS sell_bill_no,\r\n" + 
	"    d.cgst_per + d.sgst_per AS tax_per,\r\n" + 
	"    SUM(d.taxable_amt) AS tax_amount,\r\n" + 
	"    SUM(d.cgst_rs) AS cgst,\r\n" + 
	"    SUM(d.sgst_rs) AS sgst,\r\n" + 
	"    SUM(d.igst_rs) AS igst,\r\n" + 
	"    h.fr_id,\r\n" + 
	"    f.fr_name,\r\n" + 
	"    SUM(d.grand_total) AS bill_amount\r\n" + 
	"FROM\r\n" + 
	"    t_sell_bill_detail d,\r\n" + 
	"    t_sell_bill_header h,\r\n" + 
	"    m_franchisee f\r\n" + 
	"WHERE\r\n" + 
	"    h.fr_id IN(:frId) AND h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id\r\n" + 
	"GROUP BY\r\n" + 
	"    (d.cgst_per + d.sgst_per),\r\n" + 
	"    h.bill_date,\r\n" + 
	"    h.fr_id\r\n" + 
	"UNION \r\n" + 
	"SELECT\r\n" + 
	"    t_sp_cake.cust_gstin AS user_gst_no,\r\n" + 
	"    t_sp_cake.sp_delivery_date AS bill_date,\r\n" + 
	"    t_sp_cake.sp_order_no AS sell_bill_detail_no,\r\n" + 
	"    t_sp_cake.sp_book_for_mob_no AS sell_bill_no,\r\n" + 
	"    t_sp_cake.tax_1 + t_sp_cake.tax_2 AS tax_per,\r\n" + 
	"    SUM(\r\n" + 
	"        t_sp_cake.sp_grand_total -(\r\n" + 
	"            t_sp_cake.tax_1_amt + t_sp_cake.tax_2_amt\r\n" + 
	"        )\r\n" + 
	"    ) AS tax_amount,\r\n" + 
	"    SUM(t_sp_cake.tax_1_amt) AS cgst,\r\n" + 
	"    SUM(t_sp_cake.tax_2_amt) AS sgst,\r\n" + 
	"    0 AS igst,\r\n" + 
	"    m_franchisee.fr_id,\r\n" + 
	"    m_franchisee.fr_name,\r\n" + 
	"    SUM(t_sp_cake.sp_grand_total) AS bill_amount\r\n" + 
	"FROM\r\n" + 
	"    m_franchisee,\r\n" + 
	"    t_sp_cake\r\n" + 
	"WHERE\r\n" + 
	"    t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id = t_sp_cake.fr_id AND t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate\r\n" + 
	"GROUP BY\r\n" + 
	"    (\r\n" + 
	"        t_sp_cake.tax_1 + t_sp_cake.tax_2\r\n" + 
	"    ),\r\n" + 
	"    t_sp_cake.sp_delivery_date,\r\n" + 
	"    t_sp_cake.fr_id\r\n" + 
	"ORDER BY\r\n" + 
	"    bill_date DESC*/	
	
	/*******************************************************************************************************************************************/
	
	@Query(value=" SELECT\r\n" + 
			"    h.user_gst_no,\r\n" + 
			"    h.bill_date,\r\n" + 
			"    d.sell_bill_detail_no,\r\n" + 
			"    h.invoice_no AS sell_bill_no,\r\n" + 
			"    d.cgst_per+d.sgst_per AS tax_per,\r\n" + 
			"    SUM(d.taxable_amt) AS tax_amount,\r\n" + 
			"    SUM(d.cgst_rs) AS cgst,\r\n" + 
			"    SUM(d.sgst_rs) AS sgst,\r\n" + 
			"    SUM(d.igst_rs) AS igst,\r\n" + 
			"    h.fr_id,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    SUM(d.grand_total) AS bill_amount\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    h.fr_id IN(:frId) AND h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND f.fr_id = h.fr_id AND h.user_gst_no !='' AND h.del_status=0 \r\n" + 
			"GROUP BY\r\n" + 
			"    (d.cgst_per+d.sgst_per),\r\n" + 
			"    h.sell_bill_no,\r\n" + 
			"    h.fr_id\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    t_sp_cake.cust_gstin AS user_gst_no,\r\n" + 
			"    t_sp_cake.sp_delivery_date AS bill_date,\r\n" + 
			"    t_sp_cake.sp_order_no AS sell_bill_detail_no,\r\n" + 
			"    t_sp_cake.sp_book_for_mob_no AS sell_bill_no,\r\n" + 
			"    t_sp_cake.tax_1+t_sp_cake.tax_2 AS tax_per,\r\n" + 
			"    SUM(\r\n" + 
			"        t_sp_cake.sp_grand_total -(\r\n" + 
			"            t_sp_cake.tax_1_amt+t_sp_cake.tax_2_amt\r\n" + 
			"        )\r\n" + 
			"    ) AS tax_amount,\r\n" + 
			"    SUM(t_sp_cake.tax_1_amt) AS cgst,\r\n" + 
			"    SUM(t_sp_cake.tax_2_amt) AS sgst,\r\n" + 
			"    0 AS igst,\r\n" + 
			"    m_franchisee.fr_id,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    SUM(t_sp_cake.sp_grand_total) AS bill_amount\r\n" + 
			"FROM\r\n" + 
			"    m_franchisee,\r\n" + 
			"    t_sp_cake\r\n" + 
			"WHERE\r\n" + 
			"    t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id = t_sp_cake.fr_id AND t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate  AND t_sp_cake.sp_book_for_mob_no != '0'\r\n" + 
			"GROUP BY\r\n" + 
			"    (t_sp_cake.tax_1+t_sp_cake.tax_2),\r\n" + 
			"    t_sp_cake.sp_order_no,\r\n" + 
			"    t_sp_cake.fr_id\r\n" + 
			"ORDER BY\r\n" + 
			"    tax_per"
			+ "",nativeQuery=true)
	List<GetRepTaxSell> getRepFrBillwiseTaxSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);

}
