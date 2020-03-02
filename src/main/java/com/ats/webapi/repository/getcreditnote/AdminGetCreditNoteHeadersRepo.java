package com.ats.webapi.repository.getcreditnote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.grngvn.AdminGetCreditNoteHeaders;

public interface AdminGetCreditNoteHeadersRepo extends JpaRepository<AdminGetCreditNoteHeaders, Integer> {

	@Query(value = " SELECT\r\n" + 
			"    ch.crn_id,\r\n" + 
			"    ch.ex_int1,\r\n" + 
			"    ch.grn_gvn_sr_no_list,\r\n" + 
			"    ch.ex_varchar1,\r\n" + 
			"    ch.ex_varchar2,\r\n" + 
			"    ch.crn_date,\r\n" + 
			"    ch.fr_id,\r\n" + 
			"    ch.crn_taxable_amt,\r\n" + 
			"    ch.crn_total_tax,\r\n" + 
			"    ch.crn_grand_total,\r\n" + 
			"    ch.round_off,\r\n" + 
			"    ch.is_tally_sync,\r\n" + 
			"    ch.crn_no,\r\n" + 
			"    ch.grn_gvn_sr_no_list,\r\n" + 
			"    ch.is_deposited,\r\n" + 
			"    h.ex_varchar3 AS fr_name,\r\n" + 
			"    ch.created_date_time,\r\n" + 
			"    h.ex_varchar5 AS fr_address,\r\n" + 
			"    h.ex_varchar4 AS fr_gst_no,\r\n" + 
			"    fr.is_same_state,\r\n" + 
			"    ch.is_grn,\r\n" + 
			"    DATE_FORMAT(h.bill_date, '%d-%m-%Y') AS invoice_date\r\n" + 
			"FROM\r\n" + 
			"    m_franchisee fr,\r\n" + 
			"    t_credit_note_header ch,\r\n" + 
			"    t_bill_header h\r\n" + 
			"WHERE\r\n" + 
			"    ch.crn_id IN(:crnIdList) AND fr.fr_id = ch.fr_id AND ch.ex_int1 = h.bill_no\r\n" + 
			"ORDER BY\r\n" + 
			"    ch.fr_id  ", nativeQuery = true)
	List<AdminGetCreditNoteHeaders> getAdminCreditHeadersByHeaderIds(@Param("crnIdList") List<String> crnIdList);
	
}
