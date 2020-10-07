package com.ats.webapi.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.SellBillDetailForPos;
import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.SellBillHeaderAndDetail;
import com.ats.webapi.model.TaxLabListForPos;
import com.ats.webapi.model.cloudkitchen.SellBillDataForPrint;
import com.ats.webapi.repo.cloudkitchen.SellBillDataForPrintRepo;
import com.ats.webapi.repository.SellBillDetailForPosRepository;
import com.ats.webapi.repository.SellBillHeaderRepository;
import com.ats.webapi.repository.SellBillHeaderRepositoryPos;
import com.ats.webapi.repository.TaxLabListForPosPosRepository;

@RestController
public class PosApiController {

	@Autowired
	SellBillHeaderRepositoryPos sellBillHeaderRepositoryPos;

	@Autowired
	SellBillDetailForPosRepository sellBillDetailForPosRepository;

	@Autowired
	TaxLabListForPosPosRepository taxLabListForPosPosRepository;

	@Autowired
	SellBillHeaderRepository sellBillHeaderRepository;

	@Autowired
	SellBillDataForPrintRepo sellBillDataForPrintRepo;

	@RequestMapping(value = { "/getSellBillHeaderAndDetailForPos" }, method = RequestMethod.POST)
	public @ResponseBody SellBillHeaderAndDetail getSellBillHeaderAndDetailForPos(@RequestParam("billId") int billId,
			@RequestParam("flag") int flag) {

		SellBillHeaderAndDetail sellBillHeaderAndDetail = new SellBillHeaderAndDetail();

		try {

			sellBillHeaderAndDetail = sellBillHeaderRepositoryPos.getSellBillHeaderAndDetailForPos(billId);
			System.err.println("Header = " + sellBillHeaderAndDetail);
			List<SellBillDetailForPos> list = sellBillDetailForPosRepository.getSellBillDetailForPos(billId);
			System.err.println("DETAIL = " + list);
			sellBillHeaderAndDetail.setList(list);
			// System.out.println(flag);
			if (flag == 1) {
				List<TaxLabListForPos> taxLabListForPosList = taxLabListForPosPosRepository
						.taxLabListForPosList(billId);
				sellBillHeaderAndDetail.setTaxlabList(taxLabListForPosList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sellBillHeaderAndDetail;

	}

	@RequestMapping(value = { "/getSellBillHeaderAndDetailForPosDetail" }, method = RequestMethod.POST)
	public @ResponseBody SellBillHeaderAndDetail getSellBillHeaderAndDetailForPosDetail(
			@RequestParam("billId") int billId, @RequestParam("billDetailNoList") List<Integer> billDetailNoList,
			@RequestParam("flag") int flag) {

		SellBillHeaderAndDetail sellBillHeaderAndDetail = new SellBillHeaderAndDetail();

		try {

			sellBillHeaderAndDetail = sellBillHeaderRepositoryPos.getSellBillHeaderAndDetailForPos(billId);
			List<SellBillDetailForPos> list = sellBillDetailForPosRepository
					.getSellBillDetailForPosDetail(billDetailNoList);
			sellBillHeaderAndDetail.setList(list);
			// System.out.println(flag);
			if (flag == 1) {
				List<TaxLabListForPos> taxLabListForPosList = taxLabListForPosPosRepository
						.taxLabDetailsListForPosList(billDetailNoList);
				sellBillHeaderAndDetail.setTaxlabList(taxLabListForPosList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sellBillHeaderAndDetail;

	}

	@RequestMapping(value = { "/getSellBillHeaderByOrderId" }, method = RequestMethod.POST)
	public @ResponseBody SellBillHeader getSellBillHeaderByOrderId(@RequestParam("orderId") int orderId)
			throws ParseException {
		SellBillHeader res = null;
		System.err.println("sellBillNo-------- is ---------" + orderId);
		try {
			res = sellBillHeaderRepository.getBillHeaderByOrderId(orderId);
			if (res == null) {
				res = new SellBillHeader();
			}

			System.err.println("data is" + res.toString());

		} catch (Exception e) {
			System.out.println("Exc in getBillHeaderByOrderId" + e.getMessage());
			// e.printStackTrace();
		}

		return res;

	}

	@RequestMapping(value = { "/getSellBillForPrintByOrderId" }, method = RequestMethod.POST)
	public @ResponseBody SellBillDataForPrint getSellBillForPrintByOrderId(@RequestParam("orderId") int orderId)
			throws ParseException {
		SellBillDataForPrint res = null;
		System.err.println("sellBillNo-------- is ---------" + orderId);
		try {
			res = sellBillDataForPrintRepo.getBillHeaderByOrderId(orderId);
			if (res == null) {
				res = new SellBillDataForPrint();
			}else {
				
				String delInstTxt=sellBillDataForPrintRepo.getDeliveryInstByOrder(orderId);
				res.setExtVar2(delInstTxt);
			}

			List<SellBillDetailForPos> list = sellBillDetailForPosRepository
					.getSellBillDetailForPos(res.getSellBillNo());
			System.err.println("DETAIL = " + list);
			res.setList(list);
			// System.out.println(flag);
			List<TaxLabListForPos> taxLabListForPosList = taxLabListForPosPosRepository.taxLabListForPosList(res.getSellBillNo());
			res.setTaxlabList(taxLabListForPosList);

			System.err.println("data is" + res.toString());

		} catch (Exception e) {
			System.out.println("Exc in getSellBillForPrintByOrderId" + e.getMessage());
			// e.printStackTrace();
		}

		return res;

	}

}
