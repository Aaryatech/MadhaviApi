package com.ats.webapi.repo.ewaybill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.ewaybill.BillHeadEwayBill;

public interface BillHeadEwayBillRepo extends JpaRepository<BillHeadEwayBill, Integer>{
	
	
	@Query(value=" SELECT " + 
			"	  t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date, " + 
			"	 t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt, " + 
			"	  t_bill_header.grand_total, " + 
			"	  t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum FROM" + 
			"	  t_bill_header WHERE t_bill_header.bill_no IN(:billIdList)",nativeQuery=true)
	public List<BillHeadEwayBill>  getBillHeaderForEwayBill(@Param("billIdList") List<String> billIdList);

}
