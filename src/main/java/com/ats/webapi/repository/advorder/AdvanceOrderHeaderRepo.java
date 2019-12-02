package com.ats.webapi.repository.advorder;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.advorder.AdvanceOrderHeader;

public interface AdvanceOrderHeaderRepo extends JpaRepository<AdvanceOrderHeader, Integer> {

	List<AdvanceOrderHeader> findByDeliveryDateAndFrIdAndDelStatus(Date deliveryDate, int frId, int i);

	 
	List<AdvanceOrderHeader> findByFrIdAndDelStatusAndIsSellBillGenerated(int frId, int i,int j);

	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_adv_order_header SET is_sell_bill_generated=1  WHERE adv_header_id=:advHeadId",nativeQuery=true)
	int updateIsSellBillGen(@Param("advHeadId") int advHeadId);
 
} 
