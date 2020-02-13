package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetBillHeader;

public interface GetBillHeaderRepository extends JpaRepository<GetBillHeader, Integer> {
	
	 //single 
	@Query(value=" SELECT t_bill_header.bill_no ,t_bill_header.invoice_no, t_bill_header.bill_date "
			+ ",t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.veh_no,t_bill_header.bill_time,t_bill_header.ex_varchar1,t_bill_header.ex_varchar2,"
			+ " t_bill_header.tax_applicable,t_bill_header.grand_total,t_bill_header.taxable_amt, "
			+ " t_bill_header.total_tax,t_bill_header.status,t_bill_header.remark,t_bill_header.time,t_bill_header.del_status, "
			+ " CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,t_bill_header.party_name,t_bill_header.party_gstin,t_bill_header.party_address,t_bill_header.is_tally_sync AS eway_bill_no  FROM  t_bill_header,m_franchisee WHERE t_bill_header.fr_id IN (:frId) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.del_status=0  AND  t_bill_header.ex_varchar2 IN(:temp) " + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeader1N2(@Param("frId") List<String> frId,@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("temp") List<Integer> temp);
	
 
 
 
	@Query(value=" SELECT t_sell_bill_header.sell_bill_no as bill_no ,t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date "
			+ ",t_sell_bill_header.fr_id,t_sell_bill_header.fr_code,'0' as veh_no,t_sell_bill_header.timestamp as bill_time,'NA' as ex_varchar1,'NA' as ex_varchar2,"
			+ " '0' as tax_applicable,t_sell_bill_header.grand_total,t_sell_bill_header.taxable_amt, "
			+ " t_sell_bill_header.total_tax,t_sell_bill_header.status,'NA' as remark,'0' as time,t_sell_bill_header.del_status, "
			+ " CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,t_sell_bill_header.user_name as party_name,t_sell_bill_header.user_gst_no as party_gstin,m_franchisee.fr_address as party_address,'0' AS eway_bill_no FROM t_sell_bill_header,m_franchisee WHERE "
			+ " t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.del_status=0  AND t_sell_bill_header.fr_id IN (:frId) AND (Select m_franchisee.kg_1 from m_franchisee where t_sell_bill_header.fr_id=m_franchisee.fr_id)=1 \n " + 
 			"",nativeQuery=true)
	List<GetBillHeader> getBillHeader3(@Param("frId") List<String> frId, @Param("fromDate")String fromDate, @Param("toDate")String toDate);

	
	 
	@Query(value=" SELECT\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.veh_no,\n" + 
			"    t_bill_header.bill_time,\n" + 
			"    t_bill_header.ex_varchar1,\n" + 
			"    t_bill_header.ex_varchar2,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    t_bill_header.grand_total,\n" + 
			"    t_bill_header.taxable_amt,\n" + 
			"    t_bill_header.total_tax,\n" + 
			"    t_bill_header.status,\n" + 
			"    t_bill_header.remark,\n" + 
			"    t_bill_header.time,\n" + 
			"    t_bill_header.del_status,\n" + 
			"    CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\n" + 
			"    t_bill_header.party_name,\n" + 
			"    t_bill_header.party_gstin,\n" + 
			"    t_bill_header.party_address\n"
			+ ",t_bill_header.is_tally_sync AS eway_bill_no " + 
			"FROM \n" + 
			"    t_bill_header,\n " + 
			"    m_franchisee \n " + 
			"WHERE\n" + 
			"    t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.del_status = 0 AND ex_varchar2 IN(:temp) AND t_bill_header.fr_id IN (:frId)\n" + 
			"UNION\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"        t_sell_bill_header.invoice_no,\n" + 
			"        t_sell_bill_header.bill_date,\n" + 
			"        t_sell_bill_header.fr_id,\n" + 
			"        t_sell_bill_header.fr_code,\n" + 
			"        '0' AS veh_no,\n" + 
			"        t_sell_bill_header.timestamp AS bill_time,\n" + 
			"        'NA' AS ex_varchar1,\n" + 
			"        'NA' AS ex_varchar2,\n" + 
			"        '0' AS tax_applicable,\n" + 
			"        t_sell_bill_header.grand_total,\n" + 
			"        t_sell_bill_header.taxable_amt,\n" + 
			"        t_sell_bill_header.total_tax,\n" + 
			"        t_sell_bill_header.status,\n" + 
			"        'NA' AS remark,\n" + 
			"        '0' AS time,\n"
			+ "'0' AS 	eway_bill_no," + 
			"        t_sell_bill_header.del_status,\n" + 
			"        CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\n" + 
			"        t_sell_bill_header.user_name AS party_name,\n" + 
			"        t_sell_bill_header.user_gst_no AS party_gstin,\n" + 
			"        m_franchisee.fr_address AS party_address\n" + 
			"    FROM \n" + 
			"        t_sell_bill_header,\n" + 
			"        m_franchisee\n" + 
			"    WHERE\n" + 
			"        t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.del_status = 0 AND  t_sell_bill_header.fr_id IN (:frId) AND(\n" + 
			"        SELECT\n" + 
			"            m_franchisee.kg_1\n" + 
			"        FROM\n" + 
			"            m_franchisee\n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.fr_id = m_franchisee.fr_id\n" + 
			"    ) = 1\n" + 
			") " + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeaderForFrAll(@Param("frId") List<String> frId,@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("temp") List<Integer> temp);
	
 
	
	
	//FOR OPS PURCHASE REPORT--------------------------
	@Query(value=" SELECT\n" + 
			"    t_bill_header.bill_no,\n" + 
			"    t_bill_header.invoice_no,\n" + 
			"    t_bill_header.bill_date,\n" + 
			"    t_bill_header.fr_id,\n" + 
			"    t_bill_header.fr_code,\n" + 
			"    t_bill_header.veh_no,\n" + 
			"    t_bill_header.bill_time,\n" + 
			"    t_bill_header.ex_varchar1,\n" + 
			"    t_bill_header.ex_varchar2,\n" + 
			"    t_bill_header.tax_applicable,\n" + 
			"    t_bill_header.grand_total,\n" + 
			"    t_bill_header.taxable_amt,\n" + 
			"    t_bill_header.total_tax,\n" + 
			"    t_bill_header.status,\n" + 
			"    t_bill_header.remark,\n" + 
			"    t_bill_header.time,\n" + 
			"    t_bill_header.del_status,\n" + 
			"    CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\n" + 
			"    t_bill_header.party_name,\n" + 
			"    t_bill_header.party_gstin,\n" + 
			"    t_bill_header.party_address\n"
			+ ",t_bill_header.is_tally_sync AS eway_bill_no " + 
			"FROM \n" + 
			"    t_bill_header,\n " + 
			"    m_franchisee \n " + 
			"WHERE\n" + 
			"    t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.del_status = 0 AND t_bill_header.fr_id IN (:frId) ORDER BY t_bill_header.bill_no DESC \n" + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeaderForFrAllOPS(@Param("frId") List<String> frId,@Param("fromDate")String fromDate, @Param("toDate")String toDate);
	
	
	
 	
	 //**
	
	

	//  for all FR
	
	@Query(value=" SELECT t_bill_header.bill_no ,t_bill_header.invoice_no, t_bill_header.bill_date "
			+ ",t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.veh_no,t_bill_header.bill_time,t_bill_header.ex_varchar1,t_bill_header.ex_varchar2,"
			+ " t_bill_header.tax_applicable,t_bill_header.grand_total,t_bill_header.taxable_amt, "
			+ " t_bill_header.total_tax,t_bill_header.status,t_bill_header.remark,t_bill_header.time,t_bill_header.del_status, "
			+ " CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,t_bill_header.party_name,t_bill_header.party_gstin,t_bill_header.party_address,t_bill_header.is_tally_sync AS eway_bill_no FROM t_bill_header,m_franchisee WHERE "
			+ " t_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.del_status=0 AND  ex_varchar2 IN(:temp) " + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeaderForAllFr1N2(@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("temp") List<Integer> temp);
	
	 

	@Query(value=" SELECT\n" + 
 					"    t_bill_header.bill_no,\n" + 
					"    t_bill_header.invoice_no,\n" + 
					"    t_bill_header.bill_date,\n" + 
					"    t_bill_header.fr_id,\n" + 
					"    t_bill_header.fr_code,\n" + 
					"    t_bill_header.veh_no,\n" + 
					"    t_bill_header.bill_time,\n" + 
					"    t_bill_header.ex_varchar1,\n" + 
					"    t_bill_header.ex_varchar2,\n" + 
					"    t_bill_header.tax_applicable,\n" + 
					"    t_bill_header.grand_total,\n" + 
					"    t_bill_header.taxable_amt,\n" + 
					"    t_bill_header.total_tax,\n" + 
					"    t_bill_header.status,\n" + 
					"    t_bill_header.remark,\n" + 
					"    t_bill_header.time,\n" + 
					"    t_bill_header.del_status,\n" + 
					"    CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\n" + 
					"    t_bill_header.party_name,\n" + 
					"    t_bill_header.party_gstin,\n" + 
					"    t_bill_header.party_address\n"
					+ ",t_bill_header.is_tally_sync AS eway_bill_no " +
					
					" FROM  " + 
					"    t_bill_header,\n" + 
					"    m_franchisee\n" + 
					"WHERE\n" + 
					"    t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.del_status = 0 AND ex_varchar2 IN(:temp)\n" + 
					"UNION\n" + 
					"    (\n" + 
					"    SELECT\n" + 
					"        t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
					"        t_sell_bill_header.invoice_no,\n" + 
					"        t_sell_bill_header.bill_date,\n" + 
					"        t_sell_bill_header.fr_id,\n" + 
					"        t_sell_bill_header.fr_code,\n" + 
					"        '0' AS veh_no,\n" + 
					"        t_sell_bill_header.timestamp AS bill_time,\n" + 
					"        'NA' AS ex_varchar1,\n" + 
					"        'NA' AS ex_varchar2,\n" + 
					"        '0' AS tax_applicable,\n" + 
					"        t_sell_bill_header.grand_total,\n" + 
					"        t_sell_bill_header.taxable_amt,\n" + 
					"        t_sell_bill_header.total_tax,\n" + 
					"        t_sell_bill_header.status,\n" + 
					"        'NA' AS remark,\n" + 
					"        '0' AS time,\n" + 
					 "'0' AS 	eway_bill_no, " + 
					"        t_sell_bill_header.del_status,\n" + 
					"        CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\n" + 
					"        t_sell_bill_header.user_name AS party_name,\n" + 
					"        t_sell_bill_header.user_gst_no AS party_gstin,\n" + 
					"        m_franchisee.fr_address AS party_address\n" + 
					"    FROM \n" + 
					"        t_sell_bill_header, " + 
					"        m_franchisee " + 
					"    WHERE " + 
					"        t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_header.del_status = 0 AND(\n" + 
					"        SELECT " + 
					"            m_franchisee.kg_1 " + 
					"        FROM " + 
					"            m_franchisee\n" + 
					"        WHERE " + 
					"            t_sell_bill_header.fr_id = m_franchisee.fr_id " + 
					"    ) = 1 " + 
					") " + 
					"",nativeQuery=true)
			
			List<GetBillHeader> getBillHeaderForAllFrAll(@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("temp") List<Integer> temp);
			
 
	//3 for all FR

	
	@Query(value=" SELECT t_sell_bill_header.sell_bill_no as bill_no ,t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date "
			+ ",t_sell_bill_header.fr_id,t_sell_bill_header.fr_code,'0' as veh_no,t_sell_bill_header.timestamp as bill_time,'NA' as ex_varchar1,'NA' as ex_varchar2,"
			+ " '0' as tax_applicable,t_sell_bill_header.grand_total,t_sell_bill_header.taxable_amt, "
			+ " t_sell_bill_header.total_tax,t_sell_bill_header.status,'NA' as remark,'0' as time,t_sell_bill_header.del_status, "
			+ " CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,t_sell_bill_header.user_name as party_name,t_sell_bill_header.user_gst_no as party_gstin,m_franchisee.fr_address as party_address ,'0' AS eway_bill_no FROM t_sell_bill_header,m_franchisee WHERE "
			+ " t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.del_status=0 AND (Select m_franchisee.kg_1 from m_franchisee where t_sell_bill_header.fr_id=m_franchisee.fr_id)=1 " + 
			"",nativeQuery=true)
	List<GetBillHeader> getBillHeaderForAllFr3(@Param("fromDate")String fromDate, @Param("toDate")String toDate);

}
