package com.ats.webapi.repository.advorder;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.advorder.AdvanceOrderHeader;

public interface AdvanceOrderHeaderRepo extends JpaRepository<AdvanceOrderHeader, Integer> {

	List<AdvanceOrderHeader> findByDeliveryDateAndFrIdAndDelStatus(Date deliveryDate, int frId, int i);

	 
	List<AdvanceOrderHeader> findByFrIdAndDelStatusAndIsSellBillGenerated(int frId, int i,int j);

} 
