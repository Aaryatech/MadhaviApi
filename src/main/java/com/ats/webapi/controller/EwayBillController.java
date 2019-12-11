package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.ErrorMessage;
import com.ats.webapi.model.ewaybill.BillHeadEwayBill;
import com.ats.webapi.model.ewaybill.EwayItemList;
import com.ats.webapi.repo.ewaybill.BillHeadEwayBillRepo;
import com.ats.webapi.repo.ewaybill.EwayItemListRepo;
import com.ats.webapi.repository.PostCreditNoteHeaderRepository;

@RestController
public class EwayBillController {

	@Autowired
	BillHeadEwayBillRepo billHeadEwayBillRepo;

	@Autowired
	EwayItemListRepo ewayItemListRepo;

	@RequestMapping(value = { "/getBillListForEwaybill" }, method = RequestMethod.POST)
	public @ResponseBody List<BillHeadEwayBill> getBillListForEwaybill(
			@RequestParam("billIdList") List<String> billIdList) {
		System.err.println("In getBillListForEwaybill");
		List<BillHeadEwayBill> billHeadList = new ArrayList<>();
		try {

			billHeadList = billHeadEwayBillRepo.getBillHeaderForEwayBill(billIdList);

			for (int i = 0; i < billHeadList.size(); i++) {
				try {

					List<EwayItemList> billDetail = ewayItemListRepo
							.getBillDetailForEwayBill(billHeadList.get(i).getBillNo());

					if (billHeadList.get(i).getIgstSum() == 0) {

						for (int j = 0; j < billDetail.size(); j++) {

							billDetail.get(j).setIgstRate(0);

						}

					} else {

						for (int j = 0; j < billDetail.size(); j++) {

							billDetail.get(j).setCgstRate(0);
							billDetail.get(j).setSgstRate(0);

						}

					}

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

	@RequestMapping(value = { "/getCreditListForEwaybill" }, method = RequestMethod.POST)
	public @ResponseBody List<BillHeadEwayBill> getCreditListForEwaybill(
			@RequestParam("crnIdList") List<String> crnIdList) {
		System.err.println("In getCreditListForEwaybill");
		List<BillHeadEwayBill> billHeadList = new ArrayList<>();
		try {

			billHeadList = billHeadEwayBillRepo.getCreditNoteHeaderForEwayBill(crnIdList);

			for (int i = 0; i < billHeadList.size(); i++) {
				try {

					List<EwayItemList> billDetail = ewayItemListRepo
							.getCreditNoteDetailForEwayBill(billHeadList.get(i).getBillNo());

					if (billHeadList.get(i).getIgstSum() == 0) {

						for (int j = 0; j < billDetail.size(); j++) {

							billDetail.get(j).setIgstRate(0);

						}

					} else {

						for (int j = 0; j < billDetail.size(); j++) {

							billDetail.get(j).setCgstRate(0);
							billDetail.get(j).setSgstRate(0);

						}

					}

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

	@Autowired
	PostCreditNoteHeaderRepository cNoteHeaderRepo;

	@RequestMapping(value = { "/updateEwayBillNoInCNote" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateEwayBillNoInCNote(@RequestParam int crnId,
			@RequestParam String ewayBillNo) {
		System.err.println("Hiiii");
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setError(true);
		try {
			int x = 0;
			x = cNoteHeaderRepo.updateCNoteForEwayBill(ewayBillNo, crnId);
			if (x > 0) {
				errorMessage = new ErrorMessage();
				errorMessage.setError(false);
				errorMessage.setMessage("success");
			}
			// errorMessage.set
		} catch (Exception e) {
			errorMessage = new ErrorMessage();
			errorMessage.setError(true);
			errorMessage.setMessage("exception " + e.getMessage());
			e.printStackTrace();
			
		}
		return errorMessage;
	}

}
