package com.ats.webapi.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

 import com.ats.webapi.model.Customer;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.advorder.AdvanceOrderDetail;
import com.ats.webapi.model.advorder.AdvanceOrderHeader;
import com.ats.webapi.model.rawmaterial.ItemSfHeader;
import com.ats.webapi.repo.CustomerRepo;
import com.ats.webapi.repository.advorder.AdvanceOrderDetailRepo;
import com.ats.webapi.repository.advorder.AdvanceOrderHeaderRepo;

@RestController
public class AdvanceOrderApiController {

	@Autowired
	AdvanceOrderHeaderRepo advanceOrderHeaderRepo;

	@Autowired
	AdvanceOrderDetailRepo advanceOrderDetailRepo;

	@RequestMapping(value = { "/saveAdvanceOrderHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody AdvanceOrderHeader saveAdvanceOrderHeadAndDetail(@RequestBody AdvanceOrderHeader matHeader) {

		System.err.println("inside saveAdvanceOrderHeadAndDetail");

		AdvanceOrderHeader header = new AdvanceOrderHeader();

		 try { 

			header = advanceOrderHeaderRepo.save(matHeader);

			for (int i = 0; i < header.getDetailList().size(); i++) {
				matHeader.getDetailList().get(i).setAdvHeaderId(header.getAdvHeaderId());

			}

			List<AdvanceOrderDetail> matDetailsList = advanceOrderDetailRepo.save(matHeader.getDetailList());
			header.setDetailList(matDetailsList);

		
		  } catch (Exception e) {
		  
		  System.err.println("Exce in saveAdvanceOrderHeadAndDetail" + e.getMessage());
		  
		  }
		 
		return header;

	}
	
	@Autowired
	CustomerRepo cust;

	
	@RequestMapping(value = {"/checkCustPhone"}, method = RequestMethod.POST)
	public @ResponseBody Info checkEmployeeEmail(@RequestParam String phoneNo) {
		
		Info info=new Info();
		Customer emp = new Customer();
		try { 
				
				emp = cust.findByPhoneNumber(phoneNo);
				
				if(emp!=null) {
					info.setError(false);
				}else {
					info.setError(true);
				 
			}
			
		}catch (Exception e) {
			System.err.println("Exce in checkEmployeeEmail  " + e.getMessage());
		}
		
		return info;
		
	}

}
