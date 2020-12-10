package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.B2BSalesReport;

public interface B2BSalesReportRepo extends JpaRepository<B2BSalesReport, Integer> {
	
	@Query(value="SELECT \n" + 
			"    sh.sell_bill_no,\n" + 
			"    sh.invoice_no,\n" + 
			"    sh.bill_date,\n" + 
			"    sh.grand_total,\n" + 
			"    sh.remaining_amt,\n" + 
			"    sh.user_gst_no,\n" + 
			"    sh.payment_mode,\n" + 
			"    sh.cust_id,\n" + 
			"    sh.user_name,\n" + 
			"    sh.user_phone\n" + 
			"from \n" + 
			"    t_sell_bill_header sh\n" + 
			"Where\n" + 
			"    sh.bill_date between :fromDate and :toDate and\n" + 
			"    sh.fr_id=:frId and\n" + 
			"    sh.user_gst_no IS NOT NULL and\n" + 
			"    TRIM(sh.user_gst_no) <> '' and\n" + 
			"    sh.del_status=0\n" + 
			"    ORDER BY bill_date DESC\n" + 
			"    ",nativeQuery=true)
	public List<B2BSalesReport> getB2BSalesReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate, 
			@Param("frId") int frId);
}
