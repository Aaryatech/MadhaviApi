package com.ats.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.advorder.AdvanceOrderDetail;
import com.ats.webapi.model.advorder.AdvanceOrderHeader;
import com.ats.webapi.model.rawmaterial.ItemSfHeader;
import com.ats.webapi.repository.advorder.AdvanceOrderDetailRepo;
import com.ats.webapi.repository.advorder.AdvanceOrderHeaderRepo;

@RestController
public class AdvanceOrderApiController {
	
	
	@Autowired
	AdvanceOrderHeaderRepo advanceOrderHeaderRepo;	
	
	
	@Autowired
	AdvanceOrderDetailRepo advanceOrderDetailRepo;	
	
	 
	
	@RequestMapping(value = { "/saveAdvanceOrderHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody AdvanceOrderHeader saveMatIssueVehicle(@RequestBody AdvanceOrderHeader matHeader) {

		Info errorMessage = new Info();
		AdvanceOrderHeader header = new AdvanceOrderHeader();

		try {

			header = advanceOrderHeaderRepo.save(matHeader);

			for (int i = 0; i < header.getDetailList().size(); i++) {
				matHeader.getDetailList().get(i).setAdvHeaderId(header.getAdvHeaderId());

			}

			List<AdvanceOrderDetail> matDetailsList = advanceOrderDetailRepo.save(matHeader.getDetailList());
			header.setDetailList(matDetailsList);
			errorMessage.setError(false);
			errorMessage.setMessage("successfully Saved ");

		} catch (Exception e) {

			e.printStackTrace();
			errorMessage.setError(true);
			errorMessage.setMessage("failed to Save ");

		}
		return header;

	}
	 
	
	
	
	
	

}
