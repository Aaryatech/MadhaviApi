package com.ats.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.ats.webapi.model.SellBillDetailForPos;
import com.ats.webapi.model.SellBillHeaderAndDetail; 
import com.ats.webapi.repository.SellBillDetailForPosRepository;
import com.ats.webapi.repository.SellBillHeaderRepositoryPos; 

@RestController
public class PosApiController {

	@Autowired
	SellBillHeaderRepositoryPos sellBillHeaderRepositoryPos;
	
	@Autowired
	SellBillDetailForPosRepository sellBillDetailForPosRepository;

	@RequestMapping(value = { "/getSellBillHeaderAndDetailForPos" }, method = RequestMethod.POST)
	public @ResponseBody SellBillHeaderAndDetail getSellBillHeaderAndDetailForPos(@RequestParam("billId") int billId) {

		SellBillHeaderAndDetail sellBillHeaderAndDetail = new SellBillHeaderAndDetail();
 
		try {
 
			sellBillHeaderAndDetail = sellBillHeaderRepositoryPos.getSellBillHeaderAndDetailForPos(billId); 
			List<SellBillDetailForPos> list = sellBillDetailForPosRepository.getSellBillDetailForPos(billId); 
			sellBillHeaderAndDetail.setList(list);
			 

		} catch (Exception e) {
			e.printStackTrace(); 
		}

		return sellBillHeaderAndDetail;

	}

}
