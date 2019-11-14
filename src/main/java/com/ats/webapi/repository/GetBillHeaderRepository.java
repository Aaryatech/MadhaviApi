package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GetBillHeader;

public interface GetBillHeaderRepository extends JpaRepository<GetBillHeader, Integer> {
	
	// 1 or 2 for single FR
	@Query(value=" SELECT t_bill_header.bill_no ,t_bill_header.invoice_no, t_bill_header.bill_date "
			+ ",t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.veh_no,t_bill_header.bill_time,t_bill_header.ex_varchar1,t_bill_header.ex_varchar2,"
			+ " t_bill_header.tax_applicable,t_bill_header.grand_total,t_bill_header.taxable_amt, "
			+ " t_bill_header.total_tax,t_bill_header.status,t_bill_header.remark,t_bill_header.time,t_bill_header.del_status, "
			+ " m_franchisee.fr_name,t_bill_header.party_name,t_bill_header.party_gstin,t_bill_header.party_address FROM t_bill_header,m_franchisee WHERE t_bill_header.fr_id IN (:frId) "
			+ " AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.del_status=0  AND  ex_varchar1=:flag " + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeader(@Param("frId") List<String> frId,@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("flag") Integer flag);

	
	//1 or 2 for all FR
	
	@Query(value=" SELECT t_bill_header.bill_no ,t_bill_header.invoice_no, t_bill_header.bill_date "
			+ ",t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.veh_no,t_bill_header.bill_time,t_bill_header.ex_varchar1,t_bill_header.ex_varchar2,"
			+ " t_bill_header.tax_applicable,t_bill_header.grand_total,t_bill_header.taxable_amt, "
			+ " t_bill_header.total_tax,t_bill_header.status,t_bill_header.remark,t_bill_header.time,t_bill_header.del_status, "
			+ " m_franchisee.fr_name,t_bill_header.party_name,t_bill_header.party_gstin,t_bill_header.party_address FROM t_bill_header,m_franchisee WHERE "
			+ " t_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_bill_header.fr_id=m_franchisee.fr_id AND t_bill_header.del_status=0 AND  ex_varchar1=:flag " + 
			"",nativeQuery=true)
	
	List<GetBillHeader> getBillHeaderForAllFr(@Param("fromDate")String fromDate, @Param("toDate")String toDate,@Param("flag") Integer flag);

 	
	
	//3 for all FR

	
	@Query(value=" SELECT t_sell_bill_header.sell_bill_no as bill_no ,t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date "
			+ ",t_sell_bill_header.fr_id,t_sell_bill_header.fr_code,'0' as veh_no,t_sell_bill_header.timestamp as bill_time,'NA' as ex_varchar1,'NA' as ex_varchar2,"
			+ " '0' as tax_applicable,t_sell_bill_header.grand_total,t_sell_bill_header.taxable_amt, "
			+ " t_sell_bill_header.total_tax,t_sell_bill_header.status,'NA' as remark,'0' as time,t_sell_bill_header.del_status, "
			+ " m_franchisee.fr_name,t_sell_bill_header.user_name as party_name,t_sell_bill_header.user_gst_no as party_gstin,m_franchisee.fr_address as party_address FROM t_sell_bill_header,m_franchisee WHERE "
			+ " t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.del_status=0 AND (Select m_franchisee.kg_1 from m_franchisee where t_sell_bill_header.fr_id=m_franchisee.fr_id)=1 " + 
			"",nativeQuery=true)
	List<GetBillHeader> getBillHeaderForAllFr(@Param("fromDate")String fromDate, @Param("toDate")String toDate);



	//3 for single FR
 
	@Query(value=" SELECT t_sell_bill_header.sell_bill_no as bill_no ,t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date "
			+ ",t_sell_bill_header.fr_id,t_sell_bill_header.fr_code,'0' as veh_no,t_sell_bill_header.timestamp as bill_time,'NA' as ex_varchar1,'NA' as ex_varchar2,"
			+ " '0' as tax_applicable,t_sell_bill_header.grand_total,t_sell_bill_header.taxable_amt, "
			+ " t_sell_bill_header.total_tax,t_sell_bill_header.status,'NA' as remark,'0' as time,t_sell_bill_header.del_status, "
			+ " m_franchisee.fr_name,t_sell_bill_header.user_name as party_name,t_sell_bill_header.user_gst_no as party_gstin,m_franchisee.fr_address as party_address FROM t_sell_bill_header,m_franchisee WHERE "
			+ " t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate "
			+ " AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.del_status=0  AND t_sell_bill_header.fr_id IN (:frId) AND (Select m_franchisee.kg_1 from m_franchisee where t_sell_bill_header.fr_id=m_franchisee.fr_id)=1 \n " + 
 			"",nativeQuery=true)
	List<GetBillHeader> getBillHeader(@Param("frId") List<String> frId, @Param("fromDate")String fromDate, @Param("toDate")String toDate);


}
