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

	List<AdvanceOrderHeader> findByDeliveryDateAndFrIdAndDelStatusOrderByOrderDateDesc(Date deliveryDate, int frId, int i);

	 
	List<AdvanceOrderHeader> findByFrIdAndDelStatusAndIsSellBillGeneratedOrderByOrderDateDesc(int frId, int i,int j);

	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_adv_order_header SET is_sell_bill_generated=1  WHERE adv_header_id=:advHeadId",nativeQuery=true)
	int updateIsSellBillGen(@Param("advHeadId") int advHeadId);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_adv_order_header SET is_bill_generated=2  WHERE adv_header_id=:advHeadId",nativeQuery=true)
	int updateIsBillGen(@Param("advHeadId") int advHeadId);//Sachin 06-01-2020


	List<AdvanceOrderHeader> findByCustIdAndIsSellBillGeneratedAndDelStatus(int custId, int i, int j);


	AdvanceOrderHeader findByAdvHeaderIdAndDelStatus(int headId, int i);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_adv_order_header SET del_status=1  WHERE adv_header_id=:ordHeaderId",nativeQuery=true)
	int deleteAdvOrder(@Param("ordHeaderId") int ordHeaderId);
} 
