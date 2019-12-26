package com.ats.webapi.repository.advorder;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.advorder.AdvanceOrderDetail;

public interface AdvanceOrderDetailRepo extends JpaRepository<AdvanceOrderDetail, Integer>{

 
	@Transactional
	@Modifying
	@Query(value="UPDATE t_adv_order_detail SET is_sell_bill_generated=1  WHERE adv_header_id=:advHeadId",nativeQuery=true)
	int updateIsSellBillGen(@Param("advHeadId") int advHeadId);

	
	List<AdvanceOrderDetail> findByAdvHeaderIdAndDelStatus( int advHeadId, int delStatus);
	
}
