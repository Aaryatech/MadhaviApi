package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.ewaybill.BillHeadEwayBill;
import com.ats.webapi.model.ewaybill.EwayItemList;
import com.ats.webapi.repo.ewaybill.BillHeadEwayBillRepo;
import com.ats.webapi.repo.ewaybill.EwayItemListRepo;

@RestController
public class EwayBillController {

	@Autowired
	BillHeadEwayBillRepo billHeadEwayBillRepo;
	
	@Autowired
	EwayItemListRepo ewayItemListRepo;
	
	
	
	@RequestMapping(value = { "/getBillListForEwaybill" }, method = RequestMethod.POST)
	public @ResponseBody List<BillHeadEwayBill> getBillListForEwaybill(@RequestParam("billIdList") List<String> billIdList)  {
System.err.println("In getBillListForEwaybill");
		List<BillHeadEwayBill> billHeadList = new ArrayList<>();
		try {

			billHeadList = billHeadEwayBillRepo.getBillHeaderForEwayBill(billIdList);

			for (int i = 0; i < billHeadList.size(); i++) {
				try {
					List<EwayItemList> billDetail=ewayItemListRepo.getBillDetailForEwayBill(billHeadList.get(i).getBillNo());
					
					billHeadList.get(i).setItemList(billDetail);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return billHeadList;
	}
	
}
