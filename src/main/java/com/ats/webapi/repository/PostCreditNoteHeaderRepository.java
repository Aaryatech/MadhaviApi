package com.ats.webapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.grngvn.PostCreditNoteHeader;

public interface PostCreditNoteHeaderRepository extends JpaRepository<PostCreditNoteHeader, Integer> {
	
	PostCreditNoteHeader save(PostCreditNoteHeader postCreditNoteHeader);
	
	@Transactional
	@Modifying
	@Query("UPDATE PostCreditNoteHeader t SET t.exVarchar2 =:ewayBillNo  WHERE t.crnId=:crnId")
	int updateCNoteForEwayBill(@Param("ewayBillNo") String ewayBillNo,@Param("crnId") int crnId);
	
}
