package com.ats.webapi.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.webapi.model.GetBillHeader;
import com.ats.webapi.model.GetBillHeaderList;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.UpdateBillStatus;
import com.ats.webapi.repository.GetBillHeaderRepository;
import com.ats.webapi.repository.UpdateBillStatusRepository;

@Service
public class GetBillHeaderServiceImpl implements GetBillHeaderService {

	@Autowired
	GetBillHeaderRepository getBillHeaderRepository;

	@Autowired
	UpdateBillStatusRepository updateBillStatusRepository;

	
	
	//ALl FR methods Start 
	
	
	@Override
	public GetBillHeaderList getBillHeaderForAllFr(String fromDate, String toDate,List<Integer> tempList) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeaderForAllFrAll(fromDate, toDate,tempList);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	@Override
	public GetBillHeaderList getSaleReportBillwiseFrType1N2( String fromDate, String toDate,List<Integer> tempList) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeaderForAllFr1N2(fromDate, toDate,tempList);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	
	 
	
	 
	
	

	@Override
	public GetBillHeaderList getSaleReportBillwiseFrOutletType3(String fromDate, String toDate) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeaderForAllFr3(fromDate, toDate);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	//ALl FR methods end  
	
	//sel FR methods starts  
 

	@Override
	public GetBillHeaderList getBillHeaderForFrAllSel(List<String> frId,String fromDate, String toDate,List<Integer> tempList) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeaderForFrAll(frId,fromDate, toDate,tempList);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	
	 

	@Override
	public GetBillHeaderList getSaleReportBillwiseFr1N2(List<String> frId,String fromDate, String toDate,List<Integer> tempList) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeader1N2(frId,fromDate, toDate,tempList);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	
	 
	
	@Override
	public GetBillHeaderList getSaleReportBillwiseFrType3(List<String> frId,String fromDate, String toDate) {

		GetBillHeaderList getBillHeaderList = new GetBillHeaderList();

		List<GetBillHeader> billHeaders = getBillHeaderRepository.getBillHeader3(frId,fromDate, toDate);

		Info info = new Info();

		if (billHeaders != null) {

			getBillHeaderList.setGetBillHeaders(billHeaders);

			info.setError(false);
			info.setMessage("Bill header List received successfully");

		}

		else {

			info.setError(true);
			info.setMessage("Error: failed to receive Bill Header List ");
		}

		getBillHeaderList.setInfo(info);

		return getBillHeaderList;
	}
	
	 
	
	 
	//sel FR methods ends   
	
	 
	
	
	@Override
	public UpdateBillStatus updateBillStatus(UpdateBillStatus updateBillStatus) {

		UpdateBillStatus updateBillStatusRes = updateBillStatusRepository.save(updateBillStatus);

		return updateBillStatusRes;
	}

}
