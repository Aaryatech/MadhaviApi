package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.SellBillHeaderAndDetail;

public interface SellBillHeaderRepositoryPos extends JpaRepository<SellBillHeaderAndDetail, Integer>{

	
	@Query(value="select * from t_sell_bill_header where sell_bill_no=:billId ",nativeQuery=true)
	SellBillHeaderAndDetail getSellBillHeaderAndDetailForPos(@Param("billId") int billId);

}
