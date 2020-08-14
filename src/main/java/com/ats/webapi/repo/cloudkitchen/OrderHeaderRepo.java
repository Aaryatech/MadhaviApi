package com.ats.webapi.repo.cloudkitchen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.cloudkitchen.OrderHeader;

public interface OrderHeaderRepo extends JpaRepository<OrderHeader, Integer> {

	@Transactional
	@Modifying
	@Query(" UPDATE OrderHeader SET order_status=:status WHERE order_id=:orderId")
	int updateStatus(@Param("orderId") int orderId, @Param("status") int status);
	
	@Transactional
	@Modifying
	@Query(" UPDATE OrderHeader SET order_delivered_by=:delBoyId WHERE order_id=:orderId")
	int updateDeliveryBoy(@Param("orderId") int orderId, @Param("delBoyId") int delBoyId);

}
