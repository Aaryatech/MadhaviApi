package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.HSNWiseReport;

public interface HSNWiseReportRepo extends JpaRepository<HSNWiseReport, Integer> {

	@Query(value = "SELECT\r\n" + 
			"    t_bill_detail.hsn_code AS id,\r\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
			"    t_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"    t_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header,\r\n" + 
			"    t_bill_detail\r\n" + 
			"WHERE\r\n" + 
			"   t_bill_header.ex_varchar1 IN (:temp) AND  t_bill_detail.cat_id != 5 AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReport12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	//Anmol--27-02-2020--
	@Query(value = "SELECT\r\n" + 
			"    t_bill_detail.hsn_code AS id,\r\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
			"    t_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"    t_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header,\r\n" + 
			"    t_bill_detail\r\n" + 
			"WHERE\r\n" + 
			"    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.ex_varchar2=0 \r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd\r\n" + 
			"ORDER BY\r\n" + 
			"    item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getAdminReportBillWiseFrWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	//Anmol--27-02-2020--
		@Query(value = "SELECT\r\n" + 
				"    d.ext_var1 AS id,\r\n" + 
				"    d.ext_var1 AS item_hsncd,\r\n" + 
				"    d.sgst_per AS item_tax1,\r\n" + 
				"    d.cgst_per AS item_tax2,\r\n" + 
				"    SUM(d.qty) AS bill_qty,\r\n" + 
				"    SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
				"    SUM(d.cgst_rs) AS cgst_rs,\r\n" + 
				"    SUM(d.sgst_rs) AS sgst_rs\r\n" + 
				"FROM\r\n" + 
				"    t_sell_bill_header h,\r\n" + 
				"    t_sell_bill_detail d,\r\n" + 
				"    m_franchisee f\r\n" + 
				"WHERE\r\n" + 
				"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND h.del_status=0\r\n" + 
				"GROUP BY\r\n" + 
				"    item_hsncd\r\n" + 
				"ORDER BY\r\n" + 
				"    item_hsncd", nativeQuery = true)

		List<HSNWiseReport> getAdminReportBillWiseCompOutletWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	
	
	@Query(value = "SELECT\r\n" + 
			"    t_sell_bill_detail.remark AS id,\r\n" + 
			"    t_sell_bill_detail.remark AS item_hsncd,\r\n" + 
			"    t_sell_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"    t_sell_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"    SUM(t_sell_bill_detail.qty) AS bill_qty,\r\n" + 
			"    SUM(t_sell_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(t_sell_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_sell_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    t_sell_bill_detail\r\n" + 
			"WHERE\r\n" + 
			"    t_sell_bill_detail.cat_id != 5 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate  AND t_sell_bill_header.del_status=0\r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReport3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	
	
	@Query(value = "SELECT\r\n" + 
			"    t_bill_detail.hsn_code AS id,\r\n" + 
			"    t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
			"    t_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"    t_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"    SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
			"    SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"    SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_bill_header,\r\n" + 
			"    t_bill_detail\r\n" + 
			"WHERE\r\n" + 
			"   t_bill_header.ex_varchar1 IN (:temp) AND  t_bill_detail.cat_id != 5 AND t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd\r\n" + 
			"UNION\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_sell_bill_detail.remark AS id,\r\n" + 
			"        t_sell_bill_detail.remark AS item_hsncd,\r\n" + 
			"        t_sell_bill_detail.sgst_per AS item_tax1,\r\n" + 
			"        t_sell_bill_detail.cgst_per AS item_tax2,\r\n" + 
			"        SUM(t_sell_bill_detail.qty) AS bill_qty,\r\n" + 
			"        SUM(t_sell_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
			"        SUM(t_sell_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
			"        SUM(t_sell_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,\r\n" + 
			"        t_sell_bill_detail\r\n" + 
			"    WHERE\r\n" + 
			"        t_sell_bill_detail.cat_id != 5 AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status=0\r\n" + 
			"    GROUP BY\r\n" + 
			"        item_hsncd\r\n" + 
			")", nativeQuery = true)

	List<HSNWiseReport> getReportAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	 
	
	//changed
	
	
	@Query(value = "SELECT" + "            t_bill_detail.hsn_code as id,"
			+ "            t_bill_detail.hsn_code as item_hsncd," + "            t_bill_detail.sgst_per as item_tax1,"
			+ "             t_bill_detail.cgst_per as item_tax2,"
			+ "            SUM(t_bill_detail.bill_qty) as bill_qty,"
			+ "            SUM(t_bill_detail.taxable_amt) as taxable_amt,"
			+ "            SUM(t_bill_detail.cgst_rs) as cgst_rs," + "            SUM(t_bill_detail.sgst_rs) as sgst_rs"
			+ "        FROM t_bill_header," + "            t_bill_detail" + "        WHERE      t_bill_detail.cat_id!=5"
			+ "            AND t_bill_header.bill_no=t_bill_detail.bill_no"
			+ "            AND      t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.fr_id=:frId "
			+ "        GROUP BY" + "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = "SELECT" + "        t_credit_note_details.hsn_code AS id,"
			+ "           t_credit_note_details.hsn_code as item_hsncd,"
			+ "            t_credit_note_details.sgst_per as item_tax1,"
			+ "            t_credit_note_details.cgst_per as item_tax2,"
			+ "            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,"
			+ "            SUM(t_credit_note_details.taxable_amt) as taxable_amt,"
			+ "            SUM(t_credit_note_details.cgst_rs) as cgst_rs,"
			+ "            SUM(t_credit_note_details.sgst_rs) as sgst_rs" + "        FROM t_credit_note_details,"
			+ "            t_credit_note_header" + "        WHERE" + "              t_credit_note_details.cat_id!=5"
			+ "            and t_credit_note_header.crn_id=t_credit_note_details.crn_id"
			+ "            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate" + "        GROUP BY"
			+ "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportHsn(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	//Anmol--27-02-2020--
	@Query(value = "SELECT\r\n" + 
			"    t_credit_note_details.hsn_code AS id,\r\n" + 
			"    t_credit_note_details.hsn_code AS item_hsncd,\r\n" + 
			"    t_credit_note_details.sgst_per AS item_tax1,\r\n" + 
			"    t_credit_note_details.cgst_per AS item_tax2,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.grn_gvn_qty\r\n" + 
			"    ) AS bill_qty,\r\n" + 
			"    SUM(\r\n" + 
			"        t_credit_note_details.taxable_amt\r\n" + 
			"    ) AS taxable_amt,\r\n" + 
			"    SUM(t_credit_note_details.cgst_rs) AS cgst_rs,\r\n" + 
			"    SUM(t_credit_note_details.sgst_rs) AS sgst_rs\r\n" + 
			"FROM\r\n" + 
			"    t_credit_note_details,\r\n" + 
			"    t_credit_note_header\r\n" + 
			"WHERE\r\n" + 
			"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate \r\n" + 
			"GROUP BY\r\n" + 
			"    item_hsncd\r\n" + 
			"ORDER BY\r\n" + 
			"    item_hsncd", nativeQuery = true)
	List<HSNWiseReport> getAdminReportHsnCrFrWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	//Anmol--27-02-2020--
		@Query(value = "SELECT\r\n" + 
				"    d.ext_var1 AS id,\r\n" + 
				"    d.ext_var1 AS item_hsncd,\r\n" + 
				"    c.sgst_per AS item_tax1,\r\n" + 
				"    c.cgst_per AS item_tax2,\r\n" + 
				"    SUM(\r\n" + 
				"        c.crn_qty\r\n" + 
				"    ) AS bill_qty,\r\n" + 
				"    SUM(\r\n" + 
				"        c.taxable_amt\r\n" + 
				"    ) AS taxable_amt,\r\n" + 
				"    SUM(c.cgst_amt) AS cgst_rs,\r\n" + 
				"    SUM(c.sgst_amt) AS sgst_rs\r\n" + 
				"FROM\r\n" + 
				"    t_credit_note_pos c,\r\n" + 
				"    t_sell_bill_header h,\r\n" + 
				"    t_sell_bill_detail d\r\n" + 
				"WHERE\r\n" + 
				"    c.bill_no = h.sell_bill_no AND h.sell_bill_no=d.sell_bill_no AND c.crn_date BETWEEN :fromDate AND :toDate \r\n" + 
				"GROUP BY\r\n" + 
				"    item_hsncd\r\n" + 
				"ORDER BY\r\n" + 
				"    item_hsncd", nativeQuery = true)
		List<HSNWiseReport> getAdminReportHsnCrCompOutletWise(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	

	@Query(value = "SELECT" + "        t_credit_note_details.hsn_code AS id,"
			+ "           t_credit_note_details.hsn_code as item_hsncd,"
			+ "            t_credit_note_details.sgst_per as item_tax1,"
			+ "            t_credit_note_details.cgst_per as item_tax2,"
			+ "            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,"
			+ "            SUM(t_credit_note_details.taxable_amt) as taxable_amt,"
			+ "            SUM(t_credit_note_details.cgst_rs) as cgst_rs,"
			+ "            SUM(t_credit_note_details.sgst_rs) as sgst_rs" + "        FROM t_credit_note_details,"
			+ "            t_credit_note_header" + "        WHERE" + "              t_credit_note_details.cat_id!=5"
			+ "            and t_credit_note_header.crn_id=t_credit_note_details.crn_id"
			+ "            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.fr_id=:frId "
			+ "        GROUP BY" + "            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportHsnByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	
	
	//Anmol--10-03-2020--
			@Query(value = "SELECT\r\n" + 
					"    id,\r\n" + 
					"    item_hsncd,\r\n" + 
					"    item_tax1,\r\n" + 
					"    item_tax2,\r\n" + 
					"    SUM(bill_qty) AS bill_qty,\r\n" + 
					"    SUM(taxable_amt) AS taxable_amt,\r\n" + 
					"    SUM(cgst_rs) AS cgst_rs,\r\n" + 
					"    SUM(sgst_rs) AS sgst_rs\r\n" + 
					"FROM\r\n" + 
					"    (\r\n" + 
					"    SELECT\r\n" + 
					"        t_bill_detail.hsn_code AS id,\r\n" + 
					"        t_bill_detail.hsn_code AS item_hsncd,\r\n" + 
					"        t_bill_detail.sgst_per AS item_tax1,\r\n" + 
					"        t_bill_detail.cgst_per AS item_tax2,\r\n" + 
					"        SUM(t_bill_detail.bill_qty) AS bill_qty,\r\n" + 
					"        SUM(t_bill_detail.taxable_amt) AS taxable_amt,\r\n" + 
					"        SUM(t_bill_detail.cgst_rs) AS cgst_rs,\r\n" + 
					"        SUM(t_bill_detail.sgst_rs) AS sgst_rs\r\n" + 
					"    FROM\r\n" + 
					"        t_bill_header,\r\n" + 
					"        t_bill_detail\r\n" + 
					"    WHERE\r\n" + 
					"        t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.ex_varchar2=0 \r\n" + 
					"    GROUP BY\r\n" + 
					"        item_hsncd\r\n" + 
					"    UNION ALL\r\n" + 
					"SELECT\r\n" + 
					"    d.ext_var1 AS id,\r\n" + 
					"    d.ext_var1 AS item_hsncd,\r\n" + 
					"    d.sgst_per AS item_tax1,\r\n" + 
					"    d.cgst_per AS item_tax2,\r\n" + 
					"    SUM(d.qty) AS bill_qty,\r\n" + 
					"    SUM(d.taxable_amt) AS taxable_amt,\r\n" + 
					"    SUM(d.cgst_rs) AS cgst_rs,\r\n" + 
					"    SUM(d.sgst_rs) AS sgst_rs\r\n" + 
					"FROM\r\n" + 
					"    t_sell_bill_header h,\r\n" + 
					"    t_sell_bill_detail d,\r\n" + 
					"    m_franchisee f\r\n" + 
					"WHERE\r\n" + 
					"    h.sell_bill_no = d.sell_bill_no AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND f.kg_1 = 1 AND h.del_status=0\r\n" + 
					"GROUP BY\r\n" + 
					"    item_hsncd\r\n" + 
					") t1\r\n" + 
					"GROUP BY\r\n" + 
					"    item_hsncd\r\n" + 
					"ORDER BY\r\n" + 
					"    item_hsncd", nativeQuery = true)

			List<HSNWiseReport> getAdminReportAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
	
			
			//Anmol--10-03-2020--
			@Query(value = "SELECT\r\n" + 
					"\r\n" + 
					"id,item_hsncd,item_tax1,item_tax2,SUM(taxable_amt) as taxable_amt, SUM(cgst_rs) as cgst_rs,\r\n" + 
					"SUM(sgst_rs) as sgst_rs, bill_qty \r\n" + 
					"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"FROM (\r\n" + 
					"\r\n" + 
					"SELECT\r\n" + 
					"    t_credit_note_details.hsn_code AS id,\r\n" + 
					"    t_credit_note_details.hsn_code AS item_hsncd,\r\n" + 
					"    t_credit_note_details.sgst_per AS item_tax1,\r\n" + 
					"    t_credit_note_details.cgst_per AS item_tax2,\r\n" + 
					"    SUM(\r\n" + 
					"        t_credit_note_details.grn_gvn_qty\r\n" + 
					"    ) AS bill_qty,\r\n" + 
					"    SUM(\r\n" + 
					"        t_credit_note_details.taxable_amt\r\n" + 
					"    ) AS taxable_amt,\r\n" + 
					"    SUM(t_credit_note_details.cgst_rs) AS cgst_rs,\r\n" + 
					"    SUM(t_credit_note_details.sgst_rs) AS sgst_rs\r\n" + 
					"FROM\r\n" + 
					"    t_credit_note_details,\r\n" + 
					"    t_credit_note_header\r\n" + 
					"WHERE\r\n" + 
					"    t_credit_note_header.crn_id = t_credit_note_details.crn_id AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
					"GROUP BY\r\n" + 
					"    item_hsncd\r\n" + 
					"\r\n" + 
					"    UNION ALL\r\n" + 
					"    \r\n" + 
					"    SELECT\r\n" + 
					"    d.ext_var1 AS id,\r\n" + 
					"    d.ext_var1 AS item_hsncd,\r\n" + 
					"    c.sgst_per AS item_tax1,\r\n" + 
					"    c.cgst_per AS item_tax2,\r\n" + 
					"    SUM(c.crn_qty) AS bill_qty,\r\n" + 
					"    SUM(c.taxable_amt) AS taxable_amt,\r\n" + 
					"    SUM(c.cgst_amt) AS cgst_rs,\r\n" + 
					"    SUM(c.sgst_amt) AS sgst_rs\r\n" + 
					"FROM\r\n" + 
					"    t_credit_note_pos c,\r\n" + 
					"    t_sell_bill_header h,\r\n" + 
					"    t_sell_bill_detail d\r\n" + 
					"WHERE\r\n" + 
					"    c.bill_no = h.sell_bill_no AND h.sell_bill_no = d.sell_bill_no AND c.crn_date BETWEEN :fromDate AND :toDate AND h.del_status=0\r\n" + 
					"GROUP BY\r\n" + 
					"    item_hsncd\r\n" + 
					"\r\n" + 
					"    \r\n" + 
					") t1\r\n" + 
					"GROUP BY\r\n" + 
					"    item_hsncd\r\n" + 
					"ORDER BY\r\n" + 
					"    item_hsncd\r\n" + 
					"    \r\n" + 
					"    ", nativeQuery = true)

			List<HSNWiseReport> getAdminReportCRNAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
		
}
