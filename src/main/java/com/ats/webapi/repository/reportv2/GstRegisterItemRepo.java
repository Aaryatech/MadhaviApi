package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.GstRegisterItem;

public interface GstRegisterItemRepo extends JpaRepository<GstRegisterItem, Integer> {

	// for m -ite hsn code
//all fr 
	@Query(value = " select t_bill_detail.bill_detail_no, t_bill_header.invoice_no,"
			+ "	  t_bill_header.bill_date, t_bill_header.party_name as fr_name, t_bill_header.party_gstin as fr_gst_no,"
			+ "	  t_bill_detail.bill_no, t_bill_detail.cgst_per, t_bill_detail.sgst_per,"
			+ "	  t_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_bill_detail.bill_qty), 2) as bill_qty, t_bill_detail.hsn_code as "
			+ "	  hsn_code from t_bill_detail, t_bill_header, m_franchisee,m_item"
			+ "	  where t_bill_header.bill_no=t_bill_detail.bill_no AND " + "	  m_item.id=t_bill_detail.item_id AND "
			+ "	  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.ex_varchar1 IN (:temp) AND "
			+ "	  m_franchisee.fr_id=t_bill_header.fr_id GROUP BY t_bill_detail.bill_no,hsn_code   order by t_bill_header.invoice_no", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItem12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);

	
	
	
	@Query(value = " select t_sell_bill_detail.sell_bill_detail_no as bill_detail_no, t_sell_bill_header.invoice_no,"
			+ "	  t_sell_bill_header.bill_date, t_sell_bill_header.user_name as fr_name, t_sell_bill_header.user_gst_no as fr_gst_no,"
			+ "	  t_sell_bill_detail.sell_bill_no as bill_no, t_sell_bill_detail.cgst_per, t_sell_bill_detail.sgst_per,"
			+ "	  t_sell_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_sell_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_sell_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_sell_bill_detail.qty), 2) as bill_qty, t_sell_bill_detail.remark as "
			+ "	  hsn_code from t_sell_bill_detail, t_sell_bill_header, m_franchisee,m_item"
			+ "	  where t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND " + "	  m_item.id=t_sell_bill_detail.item_id AND "
			+ "	  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id!=5  AND "
			+ "	  m_franchisee.fr_id=t_sell_bill_header.fr_id GROUP BY t_sell_bill_detail.sell_bill_no,hsn_code   order by t_sell_bill_header.invoice_no", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItem3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	
	
	@Query(value = "SELECT a.bill_date,a.bill_no,a.fr_name,a.fr_gst_no,a.cgst_per,a.sgst_per,a.tax_per,a.taxable_amt,a.cgst_amt,a.sgst_amt,a.total_tax,a.grand_total,a.bill_qty,a.hsn_code ,a.bill_detail_no,a.invoice_no FROM( select t_bill_detail.bill_detail_no, t_bill_header.invoice_no,"
			+ "	  t_bill_header.bill_date, t_bill_header.party_name as fr_name, t_bill_header.party_gstin as fr_gst_no,"
			+ "	  t_bill_detail.cgst_per, t_bill_detail.sgst_per,"
			+ "	  t_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_bill_detail.bill_qty), 2) as bill_qty, t_bill_detail.hsn_code as "
			+ "	  hsn_code, t_bill_detail.bill_no from t_bill_detail, t_bill_header, m_franchisee,m_item"
			+ "	  where t_bill_header.bill_no=t_bill_detail.bill_no AND " + "	  m_item.id=t_bill_detail.item_id AND "
			+ "	  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.ex_varchar1 IN (:temp) AND "
			+ "	  m_franchisee.fr_id=t_bill_header.fr_id GROUP BY t_bill_detail.bill_no,hsn_code  UNION ALL  SELECT\n" + 
			"    t_sell_bill_detail.sell_bill_detail_no as bill_detail_no ,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    t_sell_bill_header.user_name AS fr_name,\n" + 
			"    t_sell_bill_header.user_gst_no AS fr_gst_no,\n" + 
			"    t_sell_bill_detail.sell_bill_no as bill_no,\n" + 
			"    t_sell_bill_detail.cgst_per,\n" + 
			"    t_sell_bill_detail.sgst_per,\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.total_tax),\n" + 
			"        2\n" + 
			"    ) AS total_tax,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.grand_total),\n" + 
			"        2\n" + 
			"    ) AS grand_total,\n" + 
			"    ROUND(SUM(t_sell_bill_detail.qty),\n" + 
			"    2) AS bill_qty,\n" + 
			"    t_sell_bill_detail.remark AS hsn_code\n" + 
			"FROM\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_franchisee,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id != 5 AND m_franchisee.fr_id = t_sell_bill_header.fr_id\n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_detail.sell_bill_no,\n" + 
			"    hsn_code\n" + 
 			"  ) a ORDER BY a.invoice_no"
		 
 			+ "", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItemAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	
	
	//1 fr
	 
	
	
	@Query(value = " select t_bill_detail.bill_detail_no, t_bill_header.invoice_no,"
			+ "	  t_bill_header.bill_date, t_bill_header.party_name as fr_name, t_bill_header.party_gstin as fr_gst_no,"
			+ "	  t_bill_detail.bill_no, t_bill_detail.cgst_per, t_bill_detail.sgst_per,"
			+ "	  t_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_bill_detail.bill_qty), 2) as bill_qty, t_bill_detail.hsn_code as "
			+ "	  hsn_code from t_bill_detail, t_bill_header, m_franchisee,m_item"
			+ "	  where t_bill_header.bill_no=t_bill_detail.bill_no AND " + "	  m_item.id=t_bill_detail.item_id AND "
			+ "	  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.ex_varchar1 IN (:temp) AND "
			+ "	  m_franchisee.fr_id=t_bill_header.fr_id  AND m_franchisee.fr_id IN (:frIdList) GROUP BY t_bill_detail.bill_no,hsn_code   order by t_bill_header.invoice_no", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItem12Fr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp,@Param("frIdList")  List<String> frIdList);

	
	
	
	@Query(value = " select t_sell_bill_detail.sell_bill_detail_no as bill_detail_no, t_sell_bill_header.invoice_no,"
			+ "	  t_sell_bill_header.bill_date, t_sell_bill_header.user_name as fr_name, t_sell_bill_header.user_gst_no as fr_gst_no,"
			+ "	  t_sell_bill_detail.sell_bill_no as bill_no, t_sell_bill_detail.cgst_per, t_sell_bill_detail.sgst_per,"
			+ "	  t_sell_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_sell_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_sell_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_sell_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_sell_bill_detail.qty), 2) as bill_qty, t_sell_bill_detail.remark as "
			+ "	  hsn_code from t_sell_bill_detail, t_sell_bill_header, m_franchisee,m_item"
			+ "	  where t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND " + "	  m_item.id=t_sell_bill_detail.item_id AND "
			+ "	  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id!=5  AND "
			+ "	  m_franchisee.fr_id=t_sell_bill_header.fr_id AND m_franchisee.fr_id IN (:frIdList)  GROUP BY t_sell_bill_detail.sell_bill_no,hsn_code   order by t_sell_bill_header.invoice_no", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItem3Fr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("frIdList")  List<String> frIdList);

	
	
	@Query(value = "SELECT a.bill_date,a.bill_no,a.fr_name,a.fr_gst_no,a.cgst_per,a.sgst_per,a.tax_per,a.taxable_amt,a.cgst_amt,a.sgst_amt,a.total_tax,a.grand_total,a.bill_qty,a.hsn_code ,a.bill_detail_no,a.invoice_no FROM( select t_bill_detail.bill_detail_no, t_bill_header.invoice_no,"
			+ "	  t_bill_header.bill_date, t_bill_header.party_name as fr_name, t_bill_header.party_gstin as fr_gst_no,"
			+ "	  t_bill_detail.cgst_per, t_bill_detail.sgst_per,"
			+ "	  t_bill_detail.cgst_per+sgst_per as tax_per,"
			+ "	  ROUND(SUM(t_bill_detail.taxable_amt), 2) as taxable_amt,"
			+ "	  ROUND(SUM(t_bill_detail.cgst_rs), 2) as cgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.sgst_rs), 2) as sgst_amt,"
			+ "	  ROUND(SUM(t_bill_detail.total_tax), 2) as total_tax,"
			+ "	  ROUND(SUM(t_bill_detail.grand_total), 2) as grand_total," + "	  "
			+ "	  ROUND(SUM(t_bill_detail.bill_qty), 2) as bill_qty, t_bill_detail.hsn_code as "
			+ "	  hsn_code, t_bill_detail.bill_no from t_bill_detail, t_bill_header, m_franchisee,m_item"
			+ "	  where t_bill_header.bill_no=t_bill_detail.bill_no AND " + "	  m_item.id=t_bill_detail.item_id AND "
			+ "	  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_detail.cat_id!=5 and t_bill_header.ex_varchar1 IN (:temp) AND "
			+ "	  m_franchisee.fr_id=t_bill_header.fr_id AND m_franchisee.fr_id IN (:frIdList)  GROUP BY t_bill_detail.bill_no,hsn_code  UNION ALL  SELECT\n" + 
			"    t_sell_bill_detail.sell_bill_detail_no as bill_detail_no ,\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    t_sell_bill_header.user_name AS fr_name,\n" + 
			"    t_sell_bill_header.user_gst_no AS fr_gst_no,\n" + 
			"    t_sell_bill_detail.sell_bill_no as bill_no,\n" + 
			"    t_sell_bill_detail.cgst_per,\n" + 
			"    t_sell_bill_detail.sgst_per,\n" + 
			"    t_sell_bill_detail.cgst_per + sgst_per AS tax_per,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.total_tax),\n" + 
			"        2\n" + 
			"    ) AS total_tax,\n" + 
			"    ROUND(\n" + 
			"        SUM(t_sell_bill_detail.grand_total),\n" + 
			"        2\n" + 
			"    ) AS grand_total,\n" + 
			"    ROUND(SUM(t_sell_bill_detail.qty),\n" + 
			"    2) AS bill_qty,\n" + 
			"    t_sell_bill_detail.remark AS hsn_code\n" + 
			"FROM\n" + 
			"    t_sell_bill_detail,\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_franchisee,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_detail.cat_id != 5 AND m_franchisee.fr_id = t_sell_bill_header.fr_id AND m_franchisee.fr_id IN (:frIdList) \n" + 
			"GROUP BY\n" + 
			"    t_sell_bill_detail.sell_bill_no,\n" + 
			"    hsn_code\n" + 
 			"  ) a ORDER BY a.invoice_no"
		 
 			+ "", nativeQuery = true)

	List<GstRegisterItem> getGstRegisterAllFrItemAllFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp,@Param("frIdList") List<String> frIdList);
	
	

}
