package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.BillInfo;
import com.ats.webapi.model.ErrorMessage;
import com.ats.webapi.model.MaterialRecNote;
import com.ats.webapi.model.SupplierMaster.SupplierDetails;
import com.ats.webapi.model.rawmaterial.RawMaterialDetails;
import com.ats.webapi.model.rawmaterial.RawMaterialDetailsList;
import com.ats.webapi.model.tally.CreditNote;
import com.ats.webapi.model.tally.CreditNoteInvoiceTally;
import com.ats.webapi.model.tally.CreditNoteInvoices;
import com.ats.webapi.model.tally.CreditNoteList;
import com.ats.webapi.model.tally.FranchiseeList;
import com.ats.webapi.model.tally.ItemList;
import com.ats.webapi.model.tally.MaterialRecNoteList;
import com.ats.webapi.model.tally.MaterialReceiptNote;
import com.ats.webapi.model.tally.RawMaterialResList;
import com.ats.webapi.model.tally.SalesInvoices;
import com.ats.webapi.model.tally.SalesVoucher;
import com.ats.webapi.model.tally.SalesVoucherList;
import com.ats.webapi.model.tally.SpCakeList;
import com.ats.webapi.model.tally.SuppliersList;
import com.ats.webapi.model.tally.TallyCrnInvoicesGroupByBills;
import com.ats.webapi.model.tally.TallySalesInvoiceList;
import com.ats.webapi.model.tally.TallySalesInvoiceListGroupByBills;
import com.ats.webapi.model.tally.TallySyncModel;
import com.ats.webapi.model.tally.TallySyncModelItemAsHsn;
import com.ats.webapi.repository.PostBillHeaderRepository;
import com.ats.webapi.repository.tally.CreditNoteInvoicesRepo;
import com.ats.webapi.repository.tally.SalesInvoicesRepo;
import com.ats.webapi.repository.tally.TallyCreditNoteRepository;
import com.ats.webapi.repository.tally.TallySalesVoucherRepository;
import com.ats.webapi.repository.tally.TallySyncModelItemAsHsnRepo;
import com.ats.webapi.repository.tally.TallySyncModelRepo;
import com.ats.webapi.service.SuppilerMasterService;
import com.ats.webapi.service.MaterialRcNote.MaterialRecNoteService;
import com.ats.webapi.service.rawmaterial.RawMaterialService;
import com.ats.webapi.service.tally.CreditNoteService;
import com.ats.webapi.service.tally.FranchiseeService;
import com.ats.webapi.service.tally.SalesVoucherService;
import com.ats.webapi.service.tally.SpCakeService;
import com.ats.webapi.service.tally.TallyItemService;
import com.ats.webapi.util.JsonUtil;

@RestController
@RequestMapping("/tally")
public class TallySyncController {

	@Autowired
	FranchiseeService franchiseeService;

	@Autowired
	TallyItemService itemService;

	@Autowired
	SpCakeService specialCakeService;

	@Autowired
	SalesVoucherService salesVoucherService;

	@Autowired
	CreditNoteService creditNoteService;

	@Autowired
	SuppilerMasterService supplierMasterService;

	@Autowired
	RawMaterialService rawMaterialService;

	@Autowired
	MaterialRecNoteService materialRecNoteService;

	@Autowired
	TallySalesVoucherRepository tallySalesVoucherRepository;

	@Autowired
	TallyCreditNoteRepository tallyCreditNoteRepository;

	@Autowired
	TallySyncModelRepo tallySyncModelRepo;

	@RequestMapping(value = { "/getAllExcelFranchise" }, method = RequestMethod.GET)
	public @ResponseBody FranchiseeList getAllExcelFranchise() {

		FranchiseeList franchiseeList = franchiseeService.getAllFranchisee();

		return franchiseeList;
	}

	@RequestMapping(value = { "/getAllExcelItems" }, method = RequestMethod.GET)
	public @ResponseBody ItemList getAllExcelItems() {

		ItemList itemList = itemService.getAllItems();
		return itemList;

	}

	@RequestMapping(value = { "/getAllExcelSpCake" }, method = RequestMethod.GET)
	public @ResponseBody SpCakeList getAllExcelSpCake() {
		SpCakeList spCakeList = specialCakeService.getAllSpCake();
		return spCakeList;
	}

	@RequestMapping(value = { "/getSalesVouchersByBillNo" }, method = RequestMethod.POST)
	public @ResponseBody SalesVoucherList getSalesVouchersByBillNo(@RequestParam("billNo") List<Integer> billNo,
			@RequestParam("all") int all, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {
		SalesVoucherList salesVoucherList = new SalesVoucherList();
		List<SalesVoucher> salesVoucher = new ArrayList<SalesVoucher>();
		try {
			if (all == 1) {
				salesVoucher = tallySalesVoucherRepository.getSalesVouchersAll(fromDate, toDate);
			} else {
				salesVoucher = tallySalesVoucherRepository.getSalesVouchersByBillNo(billNo);
			}

			ErrorMessage errorMessage = new ErrorMessage();

			if (salesVoucher == null) {

				errorMessage.setError(true);
				errorMessage.setMessage("Sales Voucher Not Found");

				salesVoucherList.setErrorMessage(errorMessage);
			} else {
				errorMessage.setError(false);
				errorMessage.setMessage("Sales Voucher Found Successfully");

				for (int i = 0; i < salesVoucher.size(); i++) {

					if (salesVoucher.get(i).getErpLink().equalsIgnoreCase("0")) {
						salesVoucher.get(i).setErpLink("A");

					}
				}

				salesVoucherList.setSalesVoucherList(salesVoucher);
				salesVoucherList.setErrorMessage(errorMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salesVoucherList;
	}

	@RequestMapping(value = { "/getCreditNotesByCrnId" }, method = RequestMethod.POST)
	public @ResponseBody CreditNoteList getCreditNotesByCrnId(@RequestParam("crnId") List<Integer> crnId) {

		CreditNoteList creditNoteList = new CreditNoteList();
		try {
			List<CreditNote> creditNotes = tallyCreditNoteRepository.getCreditNotesByCrnId(crnId);

			ErrorMessage errorMessage = new ErrorMessage();

			if (creditNotes == null) {

				errorMessage.setError(true);
				errorMessage.setMessage("Credit Note Not Found");

				creditNoteList.setErrorMessage(errorMessage);
			} else {
				errorMessage.setError(false);
				errorMessage.setMessage("Credit Note Found Successfully");

				for (int i = 0; i < creditNotes.size(); i++) {

					if (creditNotes.get(i).getErpLink().equalsIgnoreCase("0")) {

						creditNotes.get(i).setErpLink("A");

					}
				}

				creditNoteList.setCreditNoteList(creditNotes);
				creditNoteList.setErrorMessage(errorMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return creditNoteList;
	}

	@RequestMapping(value = { "/getAllFranchisee" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> showAllFranchisee() {

		FranchiseeList franchiseeList = franchiseeService.getAllFranchisee();

		String regData = JsonUtil.javaToJson(franchiseeList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allFranchisee.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = { "/getAllItems" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> showAllItem() {

		ItemList itemList = itemService.getAllItems();

		String regData = JsonUtil.javaToJson(itemList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allItems.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = { "/getAllSpCake" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> showAllSpCake() {

		SpCakeList spCakeList = specialCakeService.getAllSpCake();

		String regData = JsonUtil.javaToJson(spCakeList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allSpecialCakes.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = { "/updateFranchisees" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateFranchisees(@RequestParam("customerId") int customerId,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = franchiseeService.updateFranchisees(customerId, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateFranchisees.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = { "/updateItems" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateItems(@RequestParam("id") int id,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = itemService.updateItems(id, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateItems.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = { "/updateSpCakes" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateSpCakes(@RequestParam("id") int id,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = specialCakeService.updateSpCakes(id, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateSpecialCakes.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getAllSalesVouchers" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> showAllSalesVoucher() {

		SalesVoucherList salesVoucherList = salesVoucherService.getAllSalesVoucher();

		String regData = JsonUtil.javaToJson(salesVoucherList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allSalesVouchers.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	// Sachin 30-11-2019
	@RequestMapping(value = { "/updateEwayBillNo" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateEwayBillNo(@RequestParam int billNo, @RequestParam long ewayBillNo) {
		System.err.println("Hiiii");
		ErrorMessage errorMessage = null;
		try {
			errorMessage = salesVoucherService.updateSalesVouchers(billNo, ewayBillNo);
			// errorMessage.set
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = new ErrorMessage();
		}
		return errorMessage;
	}

	/*
	 * @RequestMapping(value = { "/updateSalesVoucher" }, method =
	 * RequestMethod.GET) public @ResponseBody ResponseEntity<byte[]>
	 * updateSalesVouchers(@RequestParam("VNo") int billNo,
	 * 
	 * @RequestParam("isTallySync") int isTallySync) {
	 * 
	 * ErrorMessage errorMessage = salesVoucherService.updateSalesVouchers(billNo,
	 * isTallySync);
	 * 
	 * String regData = JsonUtil.javaToJson(errorMessage);
	 * 
	 * byte[] output = regData.getBytes();
	 * 
	 * HttpHeaders responseHeaders = new HttpHeaders();
	 * 
	 * responseHeaders.setContentType(MediaType.valueOf(
	 * "application/json;charset=UTF-8"));
	 * 
	 * responseHeaders.setContentLength(output.length);
	 * 
	 * responseHeaders.set("Content-disposition",
	 * "attachment; filename=updateSalesVouchers.json");
	 * 
	 * return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK); }
	 */
	@RequestMapping(value = { "/getAllCreditNotes" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> showAllCreditNote() {

		CreditNoteList creditNoteList = creditNoteService.getAllCreditNote();

		String regData = JsonUtil.javaToJson(creditNoteList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allCreditNotes.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = { "/updateCreditNote" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateCreditNote(@RequestParam("VNo") int crnId,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = creditNoteService.updateCreditNote(crnId, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateCreditNotes.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	// -------------------------------------------------------------------------------------
	@RequestMapping(value = { "/getAllSupplier" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getAllSupplier() {
		SuppliersList supplierList = supplierMasterService.getAllSupplierForTally();

		String regData = JsonUtil.javaToJson(supplierList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allSuppliers.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	// -------------------------------------------------------------------------------------
	// ------------------------------Get All RM
	// Master-------------------------------------
	@RequestMapping(value = { "/getAllRawMaterial" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getAllRawMaterial() {
		RawMaterialResList rawMaterialDetailsList = rawMaterialService.getAllRawMaterialForTally();

		String regData = JsonUtil.javaToJson(rawMaterialDetailsList);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allRawMaterial.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);

	}

	// ----------------------------------------------------------------------------------------
	@RequestMapping(value = { "/updateSupplier" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateSupplier(@RequestParam("suppId") int suppId,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = supplierMasterService.updateSupplier(suppId, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateSupplier.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------------------------
	@RequestMapping(value = { "/updateRawMaterial" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateRawMaterial(@RequestParam("rmId") int rmId,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = rawMaterialService.updateRawMaterial(rmId, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateRawMaterial.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getAllInwards" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getAllInwards() {

		MaterialRecNoteList materialRecNoteRes = materialRecNoteService.getAllInwards();
		String regData = JsonUtil.javaToJson(materialRecNoteRes);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=allInwards.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

	// ----------------------------------------------------------------------------------------
	@RequestMapping(value = { "/updateInward" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> updateInward(@RequestParam("mrnId") int mrnId,
			@RequestParam("isTallySync") int isTallySync) {

		ErrorMessage errorMessage = materialRecNoteService.updateInward(mrnId, isTallySync);

		String regData = JsonUtil.javaToJson(errorMessage);

		byte[] output = regData.getBytes();

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

		responseHeaders.setContentLength(output.length);

		responseHeaders.set("Content-disposition", "attachment; filename=updateInward.json");

		return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
	}

//	@RequestMapping(value = { "/getBillsForTallySync" }, method = RequestMethod.GET)
//	public @ResponseBody List<TallySyncModel> getBillsForTallySync() {
//
//		List<TallySyncModel> tallyList = new ArrayList<>();
//		tallyList = tallySyncModelRepo.getTallySyncData();
//
//		return tallyList;
//	}

	@Autowired
	SalesInvoicesRepo salesInvoiceRepo;

	@RequestMapping(value = { "/getBillsForTallySync" }, method = RequestMethod.GET)
	public @ResponseBody TallySalesInvoiceList getBillsForTallySync() {

		TallySalesInvoiceList res = new TallySalesInvoiceList();

		List<SalesInvoices> tallyList = new ArrayList<>();
		tallyList = salesInvoiceRepo.getTallySyncData();

		if (tallyList == null) {
			tallyList = new ArrayList<>();
		}

		res.setSalesInvoices(tallyList);

		return res;
	}

//	@RequestMapping(value = { "/getBillsForTallySyncGroupBy" }, method = RequestMethod.GET)
//	public @ResponseBody TallySalesInvoiceListGroupByBills getBillsForTallySyncGroupBy() {
//
//		TallySalesInvoiceListGroupByBills res = new TallySalesInvoiceListGroupByBills();
//
//		List<SalesInvoices> tallyList = new ArrayList<>();
//		tallyList = salesInvoiceRepo.getTallySyncData();
//
//		List<com.ats.webapi.model.SalesInvoices> salesList = new ArrayList<>();
//
//		if (tallyList != null) {
//
//			Set<String> invoiceSet = new HashSet<String>();
//			for (SalesInvoices bills : tallyList) {
//				invoiceSet.add(bills.getBillNo());
//			}
//
//			List<String> invList = new ArrayList<>();
//			invList.addAll(invoiceSet);
//
//			Collections.sort(invList);
//
//			for (String invoice : invList) {
//
//				com.ats.webapi.model.SalesInvoices salesModel = new com.ats.webapi.model.SalesInvoices();
//				salesModel.setBillNo(invoice);
//
//				List<BillInfo> billList = new ArrayList<>();
//
//				for (SalesInvoices bills : tallyList) {
//					if (invoice.equalsIgnoreCase(bills.getBillNo())) {
//
//						BillInfo bill = new BillInfo(bills.getBillNo(), bills.getDate(), bills.geteWayBillNo(),
//								bills.geteWayBillDate(), bills.getCustomerName(), bills.getGstNo(), bills.getAddress(),
//								bills.getState(), bills.getStateCode(), bills.getShipToCustomerName(),
//								bills.getShipToGstNo(), bills.getShipToAddress(), bills.getShipToState(),
//								bills.getShipToStateCode(), bills.getProductName(), bills.getPartNo(), bills.getQty(),
//								bills.getUnit(), bills.getHsn(), bills.getGstPer(), bills.getRate(),
//								bills.getDiscount(), bills.getAmount(), bills.getCgst(), bills.getSgst(),
//								bills.getIgst(), bills.getOtherLedger(), bills.getRoundOff(), bills.getTotalAmount());
//						billList.add(bill);
//
//					}
//				}
//				salesModel.setBillInfo(billList);
//				salesList.add(salesModel);
//			}
//
//			res.setSalesInvoices(salesList);
//
//		}
//
//		return res;
//	}

	// TALLY SYNC GROUP BY BILLS WITH SELL BILL - CASH, EPAY,CREDIT
	@RequestMapping(value = { "/getBillsForTallySyncGroupBy" }, method = RequestMethod.GET)
	public @ResponseBody TallySalesInvoiceListGroupByBills getBillsForTallySyncGroupBy(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		TallySalesInvoiceListGroupByBills res = new TallySalesInvoiceListGroupByBills();

		List<SalesInvoices> tallyList = new ArrayList<>();
		tallyList = salesInvoiceRepo.getTallySyncDataByDate(fromDate, toDate);

		List<com.ats.webapi.model.SalesInvoices> salesList = new ArrayList<>();

		if (tallyList != null) {

			Set<String> invoiceSet = new HashSet<String>();
			for (SalesInvoices bills : tallyList) {
				invoiceSet.add(bills.getBillNo());
			}

			List<String> invList = new ArrayList<>();
			invList.addAll(invoiceSet);

			Collections.sort(invList);

			for (String invoice : invList) {

				com.ats.webapi.model.SalesInvoices salesModel = new com.ats.webapi.model.SalesInvoices();
				salesModel.setBillNo(invoice);

				List<BillInfo> billList = new ArrayList<>();

				for (SalesInvoices bills : tallyList) {
					if (invoice.equalsIgnoreCase(bills.getBillNo())) {

//						if(invoice.equalsIgnoreCase("-c") || invoice.equalsIgnoreCase("-E") || invoice.equalsIgnoreCase("-P")) {
//							
//						}

						BillInfo bill = new BillInfo(bills.getBillNo(), bills.getDate(), bills.geteWayBillNo(),
								bills.geteWayBillDate(), bills.getCustomerName(), bills.getGstNo(), bills.getAddress(),
								bills.getState(), bills.getStateCode(), bills.getShipToCustomerName(),
								bills.getShipToGstNo(), bills.getShipToAddress(), bills.getShipToState(),
								bills.getShipToStateCode(), bills.getProductName(), bills.getPartNo(), bills.getQty(),
								bills.getUnit(), bills.getHsn(), bills.getGstPer(), bills.getRate(),
								bills.getDiscount(), bills.getAmount(), bills.getCgst(), bills.getSgst(),
								bills.getIgst(), bills.getOtherLedger(), bills.getRateTotal(), bills.getRoundOff(),
								bills.getTotalAmount(), bills.getAccountType());
						billList.add(bill);

					}
				}
				salesModel.setBillInfo(billList);
				salesList.add(salesModel);
			}

			res.setSalesInvoices(salesList);

		}

		return res;
	}

	@Autowired
	PostBillHeaderRepository postBillHeaderRepository;

	@RequestMapping(value = { "/updateTallySyncFlag" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateTallySync(@RequestParam("billNo") String billNo,
			@RequestParam("status") int status) {

		int res = postBillHeaderRepository.updateTallySyncFlag(billNo, status);

		ErrorMessage errorMessage = new ErrorMessage();
		if (res != 0) {
			errorMessage.setError(false);
			errorMessage.setMessage("Success");
		} else {
			errorMessage.setError(true);
			errorMessage.setMessage("Falied");
		}

		return errorMessage;
	}

	@Autowired
	TallySyncModelItemAsHsnRepo tallySyncModelItemAsHsnRepo;

	@RequestMapping(value = { "/getBillsForTallySyncItemAsHsnApi" }, method = RequestMethod.POST)
	public @ResponseBody List<TallySyncModelItemAsHsn> getBillsForTallySyncItemAsHsn() {
		System.err.println("Hiii");
		List<TallySyncModelItemAsHsn> tallyList = new ArrayList<>();
		tallyList = tallySyncModelItemAsHsnRepo.getTallySyncDataItemAsHsn();

		return tallyList;
	}

	@Autowired
	CreditNoteInvoicesRepo creditNoteInvoicesRepo;

	// TALLY SYNC CREDIT NOTE
	@RequestMapping(value = { "/getCreditNoteForTallySyncGroupBy" }, method = RequestMethod.GET)
	public @ResponseBody TallyCrnInvoicesGroupByBills getCrnForTallySyncGroupBy(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		TallyCrnInvoicesGroupByBills res = new TallyCrnInvoicesGroupByBills();

		List<CreditNoteInvoices> tallyList = new ArrayList<>();
		tallyList = creditNoteInvoicesRepo.getTallyData(fromDate, toDate);

		List<CreditNoteInvoiceTally> crnInvoices = new ArrayList<>();

		if (tallyList != null) {

			Set<String> invoiceSet = new HashSet<String>();
			for (CreditNoteInvoices bills : tallyList) {
				invoiceSet.add(bills.getCrnNo());
			}

			List<String> invList = new ArrayList<>();
			invList.addAll(invoiceSet);

			Collections.sort(invList);

			for (String invoice : invList) {

				CreditNoteInvoiceTally crnModel = new CreditNoteInvoiceTally();
				crnModel.setCrnNo(invoice);

				List<CreditNoteInvoices> billList = new ArrayList<>();

				for (CreditNoteInvoices bills : tallyList) {
					if (invoice.equalsIgnoreCase(bills.getCrnNo())) {

//							if(invoice.equalsIgnoreCase("-c") || invoice.equalsIgnoreCase("-E") || invoice.equalsIgnoreCase("-P")) {
//								
//							}

						CreditNoteInvoices bill = new CreditNoteInvoices(bills.getId(),bills.getFrId(),bills.getCrnId(), bills.getCrnNo(),
								bills.getDate(), bills.getBillNo(), bills.getBillDate(), bills.geteWayBillNo(),
								bills.geteWayBillDate(), bills.getCustomerName(), bills.getGstNo(), bills.getAddress(),
								bills.getState(), bills.getStateCode(), bills.getShipToCustomerName(),
								bills.getShipToGstNo(), bills.getShipToAddress(), bills.getShipToState(),
								bills.getShipToStateCode(), bills.getProductName(), bills.getPartNo(), bills.getQty(),
								bills.getUnit(), bills.getHsn(), bills.getGstPer(), bills.getRate(),
								bills.getDiscount(), bills.getAmount(), bills.getCgst(), bills.getSgst(),
								bills.getIgst(), bills.getOtherLedger(), bills.getRateTotal(), bills.getRetPer(),
								bills.getRoundOff(), bills.getTotalAmount());
						billList.add(bill);

					}
				}
				crnModel.setCrnInfo(billList);
				crnInvoices.add(crnModel);
			}

			res.setCreditNoteInvoices(crnInvoices);
		}

		return res;
	}

}
