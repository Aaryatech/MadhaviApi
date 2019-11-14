package com.ats.webapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ats.webapi.model.GetBillHeader;
import com.ats.webapi.model.GetBillHeaderList;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.UpdateBillStatus;


public interface GetBillHeaderService {
	
	
	GetBillHeaderList getBillHeader(List<String>frId, String fromDate, String toDate,int flag);
	
	 
	GetBillHeaderList getBillHeaderForAllFr(String fromDate, String toDate,int flag);
	 

	
	
	
	UpdateBillStatus updateBillStatus(UpdateBillStatus updateBillStatus);

	
	
	//Company Outlet Bill
	
	
	
	GetBillHeaderList getBillHeaderForAllFr(String fromDate, String toDate);
	
	GetBillHeaderList getBillHeader(List<String>frId, String fromDate, String toDate);

	
	
	
	
}
