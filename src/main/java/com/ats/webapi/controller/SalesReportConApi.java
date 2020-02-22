package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.report.frpurchase.AdminCompOutletDateWiseSale;
import com.ats.webapi.model.report.frpurchase.SalesReportBillwise;
import com.ats.webapi.model.report.frpurchase.SalesReportDMCredit;
import com.ats.webapi.model.report.frpurchase.SalesReportDateMonth;
import com.ats.webapi.repository.frpurchasereport.AdminCompOutletDateWiseSaleRepo;
import com.ats.webapi.repository.frpurchasereport.SaleReportBillwiseRepo;
import com.ats.webapi.repository.frpurchasereport.SalesReportDMCreditRepo;

@RestController
public class SalesReportConApi {
	@Autowired
	SalesReportDMCreditRepo salesReportDMCreditRepo;

	@Autowired
	SaleReportBillwiseRepo saleReportBillwiseRepo;

	@Autowired
	AdminCompOutletDateWiseSaleRepo adminCompOutletDateWiseSaleRepo;

	@RequestMapping(value = { "/getDatewiseReport" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportDateMonth> getDatewiseReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<String> frIdList,
			@RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReportDateMonth> salesReportDateMonthList = new ArrayList<>();
		List<SalesReportBillwise> salesReportBillwiseList = new ArrayList<>();
		List<SalesReportDMCredit> grnList = null;
		List<SalesReportDMCredit> gvnList = null;

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize = typeIdList.size();
			List<Integer> itmList = new ArrayList<Integer>();
			System.out.println("type len" + typeIdList.size());
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("1") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12(frIdList, fromDate,
						toDate, itmList);

				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12(frIdList, fromDate,
						toDate, itmList);

				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateOutlet(frIdList, fromDate,
						toDate);

			}

			grnList = salesReportDMCreditRepo.getDataGRN(frIdList, fromDate, toDate);

			gvnList = salesReportDMCreditRepo.getDataGVN(frIdList, fromDate, toDate);

			for (int i = 0; i < salesReportBillwiseList.size(); i++) {

				SalesReportDateMonth save = new SalesReportDateMonth();

				save.setBillDate(salesReportBillwiseList.get(i).getBillDate());
				save.setFrId(salesReportBillwiseList.get(i).getFrId());
				save.setGrandTotal(salesReportBillwiseList.get(i).getGrandTotal());
				save.setTaxableAmt(salesReportBillwiseList.get(i).getTaxableAmt());
				save.setTotalTax(salesReportBillwiseList.get(i).getTotalTax());

				salesReportDateMonthList.add(save);

			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < grnList.size(); j++) {

					if (salesReportDateMonthList.get(i).getBillDate().compareTo(grnList.get(j).getCrnDate()) == 0) {

						salesReportDateMonthList.get(i).setCrnDate(grnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGrnTaxableAmt(grnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGrnTotalTax(grnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGrnGrandTotal(grnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGrnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGrnTotalTax(0);
						salesReportDateMonthList.get(i).setGrnGrandTotal(0);
					}

				}
			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < gvnList.size(); j++) {

					if (salesReportDateMonthList.get(i).getBillDate().compareTo(grnList.get(j).getCrnDate()) == 0) {

						salesReportDateMonthList.get(i).setCrnDate(gvnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGvnTaxableAmt(gvnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGvnTotalTax(gvnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGvnGrandTotal(gvnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGvnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGvnTotalTax(0);
						salesReportDateMonthList.get(i).setGvnGrandTotal(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportDateMonthList;
	}

	@RequestMapping(value = { "/getDatewiseReportWithDairyMart" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportDateMonth> getDatewiseReportWithDairyMart(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("typeIdList") List<String> typeIdList,
			@RequestParam("dairyList") List<String> dairyList) {

		List<SalesReportDateMonth> salesReportDateMonthList = new ArrayList<>();
		List<SalesReportBillwise> salesReportBillwiseList = new ArrayList<>();
		List<SalesReportDMCredit> grnList = null;
		List<SalesReportDMCredit> gvnList = null;

		List<Integer> dairyMartList = new ArrayList<Integer>();

		if (dairyList.contains("1") && dairyList.contains("2")) {
			dairyMartList = new ArrayList<Integer>();
			dairyMartList.add(1);
			dairyMartList.add(2);
		} else if (dairyList.contains("1")) {
			dairyMartList = new ArrayList<Integer>();
			dairyMartList.add(1);
		} else if (dairyList.contains("2")) {
			dairyMartList = new ArrayList<Integer>();
			dairyMartList.add(2);
		}

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize = typeIdList.size();
			List<Integer> itmList = new ArrayList<Integer>();
			System.out.println("type len" + typeIdList.size());
			if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dairyMartList);

			} else if (typeIdList.contains("1") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dairyMartList);

				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dairyMartList);

				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateOutlet(frIdList, fromDate,
						toDate);

			}

			grnList = salesReportDMCreditRepo.getDataGRN(frIdList, fromDate, toDate);

			gvnList = salesReportDMCreditRepo.getDataGVN(frIdList, fromDate, toDate);

			for (int i = 0; i < salesReportBillwiseList.size(); i++) {

				SalesReportDateMonth save = new SalesReportDateMonth();

				save.setBillDate(salesReportBillwiseList.get(i).getBillDate());
				save.setFrId(salesReportBillwiseList.get(i).getFrId());
				save.setGrandTotal(salesReportBillwiseList.get(i).getGrandTotal());
				save.setTaxableAmt(salesReportBillwiseList.get(i).getTaxableAmt());
				save.setTotalTax(salesReportBillwiseList.get(i).getTotalTax());

				salesReportDateMonthList.add(save);

			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < grnList.size(); j++) {

					if (salesReportDateMonthList.get(i).getBillDate().compareTo(grnList.get(j).getCrnDate()) == 0) {

						salesReportDateMonthList.get(i).setCrnDate(grnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGrnTaxableAmt(grnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGrnTotalTax(grnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGrnGrandTotal(grnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGrnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGrnTotalTax(0);
						salesReportDateMonthList.get(i).setGrnGrandTotal(0);
					}

				}
			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < gvnList.size(); j++) {

					if (salesReportDateMonthList.get(i).getBillDate().compareTo(grnList.get(j).getCrnDate()) == 0) {

						salesReportDateMonthList.get(i).setCrnDate(gvnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGvnTaxableAmt(gvnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGvnTotalTax(gvnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGvnGrandTotal(gvnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGvnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGvnTotalTax(0);
						salesReportDateMonthList.get(i).setGvnGrandTotal(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportDateMonthList;
	}

	@RequestMapping(value = { "/getMonthwiseReport" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportDateMonth> getMonthwiseReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<String> frIdList,
			@RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReportDateMonth> salesReportDateMonthList = new ArrayList<>();
		List<SalesReportBillwise> salesReportBillwiseList = new ArrayList<>();
		List<SalesReportDMCredit> grnList = null;
		List<SalesReportDMCredit> gvnList = null;

		System.out.println("type list" + typeIdList.toString());
		List<Integer> itmList = new ArrayList<Integer>();
		int listSize = typeIdList.size();

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				System.err.println("all");
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonthAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonthAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonthAll(frIdList, fromDate,
						toDate, itmList);

			} else if (typeIdList.contains("2") && listSize == 1) {
				itmList = new ArrayList<Integer>();

				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12(frIdList, fromDate,
						toDate, itmList);
				System.err.println(" 2");

			} else if (typeIdList.contains("1") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12(frIdList, fromDate,
						toDate, itmList);
				System.err.println(" 1");

			} else {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateOutlet(frIdList, fromDate,
						toDate);

			}

			grnList = salesReportDMCreditRepo.getDataGRNForMonth(frIdList, fromDate, toDate);

			gvnList = salesReportDMCreditRepo.getDataGVNForMonth(frIdList, fromDate, toDate);

			for (int i = 0; i < salesReportBillwiseList.size(); i++) {

				SalesReportDateMonth save = new SalesReportDateMonth();

				save.setBillDate(salesReportBillwiseList.get(i).getBillDate());
				save.setFrId(salesReportBillwiseList.get(i).getFrId());
				save.setGrandTotal(salesReportBillwiseList.get(i).getGrandTotal());
				save.setTaxableAmt(salesReportBillwiseList.get(i).getTaxableAmt());
				save.setTotalTax(salesReportBillwiseList.get(i).getTotalTax());
				save.setMonth(salesReportBillwiseList.get(i).getMonth());

				salesReportDateMonthList.add(save);

			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < grnList.size(); j++) {

					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(salesReportDateMonthList.get(i).getBillDate());
					int month = cal.get(Calendar.MONTH);

					Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal1.setTime(grnList.get(j).getCrnDate());
					int month1 = cal1.get(Calendar.MONTH);

					// System.out.println(month + " " + month1);
					if (month == month1) {

						salesReportDateMonthList.get(i).setCrnDate(grnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGrnTaxableAmt(grnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGrnTotalTax(grnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGrnGrandTotal(grnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGrnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGrnTotalTax(0);
						salesReportDateMonthList.get(i).setGrnGrandTotal(0);
					}

				}
			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < gvnList.size(); j++) {

					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(salesReportDateMonthList.get(i).getBillDate());
					int month = cal.get(Calendar.MONTH);

					Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal1.setTime(gvnList.get(j).getCrnDate());
					int month1 = cal1.get(Calendar.MONTH);

					System.out.println(month + " " + month1);
					if (month == month1) {

						salesReportDateMonthList.get(i).setCrnDate(gvnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGvnTaxableAmt(gvnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGvnTotalTax(gvnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGvnGrandTotal(gvnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGvnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGvnTotalTax(0);
						salesReportDateMonthList.get(i).setGvnGrandTotal(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportDateMonthList;
	}
	
	
	
	//Anmol------21-2-2020----ADMIN MONTH WISE SALES------------
	@RequestMapping(value = { "/getMonthwiseReportWithDairyMart" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportDateMonth> getMonthwiseReportWithDairyMart(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<String> frIdList,
			@RequestParam("typeIdList") List<String> typeIdList,@RequestParam("dairyList") List<String> dairyList) {

		List<SalesReportDateMonth> salesReportDateMonthList = new ArrayList<>();
		List<SalesReportBillwise> salesReportBillwiseList = new ArrayList<>();
		List<SalesReportDMCredit> grnList = null;
		List<SalesReportDMCredit> gvnList = null;

		System.out.println("type list" + typeIdList.toString());
		List<Integer> itmList = new ArrayList<Integer>();
		int listSize = typeIdList.size();
		
		List<Integer> dmList = new ArrayList<Integer>();
		if (dairyList.contains("1") && dairyList.contains("2")) {
			dmList = new ArrayList<Integer>();
			dmList.add(1);
			dmList.add(2);
		}else if(dairyList.contains("1")) {
			dmList = new ArrayList<Integer>();
			dmList.add(1);
		}else if(dairyList.contains("2")) {
			dmList = new ArrayList<Integer>();
			dmList.add(2);
		}
		

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dmList);

			}else if (typeIdList.contains("2") && listSize == 1) {
				itmList = new ArrayList<Integer>();

				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dmList);
				System.err.println(" 2");

			} else if (typeIdList.contains("1") && listSize == 1) {
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth12WithDairyMart(frIdList, fromDate,
						toDate, itmList,dmList);
				System.err.println(" 1");

			} else {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateOutlet(frIdList, fromDate,
						toDate);

			}

			grnList = salesReportDMCreditRepo.getDataGRNForMonth(frIdList, fromDate, toDate);

			gvnList = salesReportDMCreditRepo.getDataGVNForMonth(frIdList, fromDate, toDate);

			for (int i = 0; i < salesReportBillwiseList.size(); i++) {

				SalesReportDateMonth save = new SalesReportDateMonth();

				save.setBillDate(salesReportBillwiseList.get(i).getBillDate());
				save.setFrId(salesReportBillwiseList.get(i).getFrId());
				save.setGrandTotal(salesReportBillwiseList.get(i).getGrandTotal());
				save.setTaxableAmt(salesReportBillwiseList.get(i).getTaxableAmt());
				save.setTotalTax(salesReportBillwiseList.get(i).getTotalTax());
				save.setMonth(salesReportBillwiseList.get(i).getMonth());

				salesReportDateMonthList.add(save);

			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < grnList.size(); j++) {

					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(salesReportDateMonthList.get(i).getBillDate());
					int month = cal.get(Calendar.MONTH);

					Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal1.setTime(grnList.get(j).getCrnDate());
					int month1 = cal1.get(Calendar.MONTH);

					// System.out.println(month + " " + month1);
					if (month == month1) {

						salesReportDateMonthList.get(i).setCrnDate(grnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGrnTaxableAmt(grnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGrnTotalTax(grnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGrnGrandTotal(grnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGrnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGrnTotalTax(0);
						salesReportDateMonthList.get(i).setGrnGrandTotal(0);
					}

				}
			}

			for (int i = 0; i < salesReportDateMonthList.size(); i++) {
				for (int j = 0; j < gvnList.size(); j++) {

					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(salesReportDateMonthList.get(i).getBillDate());
					int month = cal.get(Calendar.MONTH);

					Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal1.setTime(gvnList.get(j).getCrnDate());
					int month1 = cal1.get(Calendar.MONTH);

					System.out.println(month + " " + month1);
					if (month == month1) {

						salesReportDateMonthList.get(i).setCrnDate(gvnList.get(j).getCrnDate());
						salesReportDateMonthList.get(i).setGvnTaxableAmt(gvnList.get(j).getTaxableAmt());
						salesReportDateMonthList.get(i).setGvnTotalTax(gvnList.get(j).getCrnTotalTax());
						salesReportDateMonthList.get(i).setGvnGrandTotal(gvnList.get(j).getGrandTotal());
						break;

					} else {

						salesReportDateMonthList.get(i).setGvnTaxableAmt(0);
						salesReportDateMonthList.get(i).setGvnTotalTax(0);
						salesReportDateMonthList.get(i).setGvnGrandTotal(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportDateMonthList;
	}
	

	// Anmol-------------20-2-2020----ADMIN COMPANY OUTLET DATE WISE SALE--------

	@RequestMapping(value = { "/getAdminDateWiseCompOutletReport" }, method = RequestMethod.POST)
	public @ResponseBody List<AdminCompOutletDateWiseSale> getAdminDateWiseCompOutletReport(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("frIdList") List<String> frIdList) {

		List<AdminCompOutletDateWiseSale> result = new ArrayList<>();

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			result = adminCompOutletDateWiseSaleRepo.getAdminDateWiseCompOutletSale(frIdList, fromDate, toDate);

		} catch (Exception e) {
			System.out.println(" Exce in getAdminDateWiseCompOutletReport Report " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	
	// Anmol-------------20-2-2020----ADMIN COMPANY OUTLET MONTH WISE SALE--------

		@RequestMapping(value = { "/getAdminMonthWiseCompOutletReport" }, method = RequestMethod.POST)
		public @ResponseBody List<AdminCompOutletDateWiseSale> getAdminMonthWiseCompOutletReport(
				@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
				@RequestParam("frIdList") List<String> frIdList) {

			List<AdminCompOutletDateWiseSale> result = new ArrayList<>();

			try {
				fromDate = Common.convertToYMD(fromDate);
				toDate = Common.convertToYMD(toDate);

				result = adminCompOutletDateWiseSaleRepo.getAdminMonthWiseCompOutletSale(frIdList, fromDate, toDate);

			} catch (Exception e) {
				System.out.println(" Exce in getAdminDateWiseCompOutletReport Report " + e.getMessage());
				e.printStackTrace();
			}
			return result;
		}

}
