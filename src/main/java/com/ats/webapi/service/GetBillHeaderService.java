package com.ats.webapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ats.webapi.model.GetBillHeader;
import com.ats.webapi.model.GetBillHeaderList;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.UpdateBillStatus;


public interface GetBillHeaderService {
	
	  
	UpdateBillStatus updateBillStatus(UpdateBillStatus updateBillStatus);

  
//***********************
//All fr
	
	GetBillHeaderList getBillHeaderForAllFr(String fromDate, String toDate,List<Integer> templist);
	
	GetBillHeaderList getSaleReportBillwiseFrType1N2(String fromDate, String toDate,List<Integer> templist);
	
	
	GetBillHeaderList getSaleReportBillwiseFrOutletType3(String fromDate, String toDate);

 
	//1GetBillHeaderList getSaleReportBillwiseAllFrType1O2O3(String fromDate, String toDate, int i);


	//1GetBillHeaderList getSaleReportBillwiseFrType1O2(String fromDate, String toDate, int i);



//sel fr
	GetBillHeaderList getBillHeaderForFrAllSel(List<String> frId, String fromDate, String toDate,List<Integer> templist);


	GetBillHeaderList getSaleReportBillwiseFr1N2(List<String> frId, String fromDate, String toDate,List<Integer> templist);

	GetBillHeaderList getSaleReportBillwiseFrType3(List<String> frId, String fromDate, String toDate);

	//1GetBillHeaderList getSaleReportBillwiseFrType1O2O3(List<String> frId, String fromDate, String toDate, int i);


	//1GetBillHeaderList getSaleReportBillwiseFr1O2(List<String> frId, String fromDate, String toDate, int i);



	
	
	
	
}
