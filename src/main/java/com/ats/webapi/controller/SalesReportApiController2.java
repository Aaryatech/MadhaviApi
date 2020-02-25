package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.salesreport.SubCatBillRep;
import com.ats.webapi.model.salesreport.SubCatCreditGrnFrItemRep;
import com.ats.webapi.model.salesreport.SubCatCreditGrnFrRep;
import com.ats.webapi.model.salesreport.SubCatCreditGrnRep;
import com.ats.webapi.model.salesreport.SubCatFrItemRepBill;
import com.ats.webapi.model.salesreport.SubCatFrRepBill;
import com.ats.webapi.model.salesreport.SubCatFrReport;
import com.ats.webapi.model.salesreport.SubCatItemReport;
import com.ats.webapi.model.salesreport.SubCatReport;
import com.ats.webapi.repo.salesreport.SubCatBillRepRepo;
import com.ats.webapi.repo.salesreport.SubCatCreditGrnFrItemRepRepo;
import com.ats.webapi.repo.salesreport.SubCatCreditGrnFrRepRepo;
import com.ats.webapi.repo.salesreport.SubCatCreditGrnRepRepo;
import com.ats.webapi.repo.salesreport.SubCatFrItemRepBillRepo;
import com.ats.webapi.repo.salesreport.SubCatFrRepBillRepo;
import com.ats.webapi.repo.salesreport.SubCatReportRepo;

@RestController
public class SalesReportApiController2 {

	@Autowired
	SubCatReportRepo subCatReportRepo;

	@Autowired
	SubCatBillRepRepo subCatBillRepRepo;

	@Autowired
	SubCatCreditGrnRepRepo subCatCreditGrnRepRepo;

	@RequestMapping(value = { "/getSubCatReportApi" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatReport> getSubCatReportApi(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("typeIdList") List<String> typeIdList) {

		List<SubCatReport> catReportList = new ArrayList<>();
		List<SubCatBillRep> catReportBill = null;

		List<SubCatCreditGrnRep> subCatCreditGrnRep = new ArrayList<>();
		List<SubCatCreditGrnRep> subCatCreditGvnRep = new ArrayList<>();
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			int listSize = typeIdList.size();
			List<Integer> itmList = new ArrayList<Integer>();
			System.err.println("type list" + typeIdList.toString());

			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				catReportBill = subCatBillRepRepo.getDataAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				catReportBill = subCatBillRepRepo.getData12(fromDate, toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				catReportBill = subCatBillRepRepo.getDataAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				catReportBill = subCatBillRepRepo.getDataAll(fromDate, toDate, itmList);
			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				catReportBill = subCatBillRepRepo.getData12(fromDate, toDate, itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				catReportBill = subCatBillRepRepo.getData12(fromDate, toDate, itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				catReportBill = subCatBillRepRepo.getData3(fromDate, toDate);

			}

			subCatCreditGrnRep = subCatCreditGrnRepRepo.getDataGRN(fromDate, toDate);

			System.err.println("Matched -------------------- " + subCatCreditGrnRep);

			subCatCreditGvnRep = subCatCreditGrnRepRepo.getDataGVN(fromDate, toDate);
			System.err.println("Matched -------------------- " + subCatCreditGvnRep);

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatReport subCatReport = new SubCatReport();

				subCatReport.setCatId(catReportBill.get(i).getCatId());
				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());

				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()) {

						System.err.println("Matched -------------------- " + subCatCreditGrnRep.get(j).getSubCatId());

						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());

						System.err.println("GRN " + subCatCreditGrnRep.get(j).getVarQty());
						System.err.println("GRN AMT  " + subCatCreditGrnRep.get(j).getVarAmt());

						break;

					} else {

						catReportList.get(i).setRetAmt(0);
						catReportList.get(i).setRetQty(0);
					}

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGvnRep.get(j).getSubCatId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						System.err.println("GVN " + subCatCreditGvnRep.get(j).getVarQty());
						System.err.println("GVN AMT  " + subCatCreditGvnRep.get(j).getVarAmt());

						break;

					} else {

						catReportList.get(i).setVarAmt(0);
						catReportList.get(i).setVarQty(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

	// Anmol--25-02-2020------
	@RequestMapping(value = { "/getAdminSubCatReportApi" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatReport> getAdminSubCatReportApi(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("typeIdList") List<String> typeIdList,
			@RequestParam("billType") int billType) {

		List<SubCatReport> catReportList = new ArrayList<>();
		List<SubCatBillRep> catReportBill = null;

		List<SubCatCreditGrnRep> subCatCreditGrnRep = new ArrayList<>();
		List<SubCatCreditGrnRep> subCatCreditGvnRep = new ArrayList<>();
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			int listSize = typeIdList.size();
			List<Integer> itmList = new ArrayList<Integer>();
			System.err.println("type list" + typeIdList.toString());

			if (billType == 1) {

				if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

					System.err.println("1 2");
					itmList = new ArrayList<Integer>();
					itmList.add(0);
					itmList.add(1);
					catReportBill = subCatBillRepRepo.getAdminData12(fromDate, toDate, itmList);

				} else if (typeIdList.contains("1") && listSize == 1) {

					itmList = new ArrayList<Integer>();
					itmList.add(0);
					catReportBill = subCatBillRepRepo.getAdminData12(fromDate, toDate, itmList);
					System.err.println(" 1");

				} else if (typeIdList.contains("2") && listSize == 1) {

					itmList = new ArrayList<Integer>();
					itmList.add(1);
					catReportBill = subCatBillRepRepo.getAdminData12(fromDate, toDate, itmList);
					System.err.println(" 2");

				}

				subCatCreditGrnRep = subCatCreditGrnRepRepo.getAdminDataGRN(fromDate, toDate);

				System.err.println("Matched -------------------- " + subCatCreditGrnRep);

				subCatCreditGvnRep = subCatCreditGrnRepRepo.getAdminDataGVN(fromDate, toDate);
				System.err.println("Matched -------------------- " + subCatCreditGvnRep);

			} else {
				catReportBill = subCatBillRepRepo.getAdminDataCompOutlet(fromDate, toDate);
				subCatCreditGrnRep = subCatCreditGrnRepRepo.getAdminDataCRN(fromDate, toDate);
			}

			if (catReportBill == null) {
				catReportBill = new ArrayList<>();
			}

			if (subCatCreditGrnRep == null) {
				subCatCreditGrnRep = new ArrayList<>();
			}

			if (subCatCreditGvnRep == null) {
				subCatCreditGvnRep = new ArrayList<>();
			}

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatReport subCatReport = new SubCatReport();

				subCatReport.setCatId(catReportBill.get(i).getCatId());
				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());

				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()) {

						System.err.println("Matched -------------------- " + subCatCreditGrnRep.get(j).getSubCatId());

						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());

						System.err.println("GRN " + subCatCreditGrnRep.get(j).getVarQty());
						System.err.println("GRN AMT  " + subCatCreditGrnRep.get(j).getVarAmt());

						break;

					} else {

						catReportList.get(i).setRetAmt(0);
						catReportList.get(i).setRetQty(0);
					}

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGvnRep.get(j).getSubCatId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						System.err.println("GVN " + subCatCreditGvnRep.get(j).getVarQty());
						System.err.println("GVN AMT  " + subCatCreditGvnRep.get(j).getVarAmt());

						break;

					} else {

						catReportList.get(i).setVarAmt(0);
						catReportList.get(i).setVarQty(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

	@RequestMapping(value = { "/getSubCatReportApiByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatReport> getSubCatReportApiByFrId(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId) {

		List<SubCatReport> catReportList = new ArrayList<>();
		List<SubCatBillRep> catReportBill = null;

		List<SubCatCreditGrnRep> subCatCreditGrnRep = null;
		List<SubCatCreditGrnRep> subCatCreditGvnRep = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			// catReportList = subCatReportRepo.getData(fromDate, toDate);

			catReportBill = subCatBillRepRepo.getDataByFrId(fromDate, toDate, frId);

			subCatCreditGrnRep = subCatCreditGrnRepRepo.getDataGRN(fromDate, toDate);

			subCatCreditGvnRep = subCatCreditGrnRepRepo.getDataGVN(fromDate, toDate);

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatReport subCatReport = new SubCatReport();

				subCatReport.setCatId(catReportBill.get(i).getCatId());
				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());

				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()) {

						System.err.println("Matched -------------------- " + subCatCreditGrnRep.get(j).getSubCatId());

						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());

						break;

					} else {

						catReportList.get(i).setRetAmt(0);
						catReportList.get(i).setRetQty(0);
					}

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getSubCatId() == subCatCreditGvnRep.get(j).getSubCatId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						break;

					} else {

						catReportList.get(i).setVarAmt(0);
						catReportList.get(i).setVarQty(0);
					}

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

	@Autowired
	SubCatCreditGrnFrRepRepo subCatCreditGrnFrRepRepo;

	@Autowired
	SubCatFrRepBillRepo subCatFrRepBillRepo;

	@RequestMapping(value = { "/getSubCatFrReportApi" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatFrReport> getSubCatFrReportApi(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<Integer> frIdList,
			@RequestParam("subCatIdList") List<Integer> subCatIdList) {

		List<SubCatFrReport> catReportList = new ArrayList<>();
		List<SubCatFrRepBill> catReportBill = null;

		List<SubCatCreditGrnFrRep> subCatCreditGrnRep = null;
		List<SubCatCreditGrnFrRep> subCatCreditGvnRep = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			// catReportList = subCatReportRepo.getData(fromDate, toDate);

			catReportBill = subCatFrRepBillRepo.getData(fromDate, toDate, frIdList, subCatIdList);

			subCatCreditGrnRep = subCatCreditGrnFrRepRepo.getDataGRN(fromDate, toDate, frIdList, subCatIdList);

			subCatCreditGvnRep = subCatCreditGrnFrRepRepo.getDataGVN(fromDate, toDate, frIdList, subCatIdList);

			System.out.println(catReportBill.toString());
			System.out.println(subCatCreditGrnRep.toString());
			System.out.println(subCatCreditGvnRep.toString());

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatFrReport subCatReport = new SubCatFrReport();

				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());
				subCatReport.setFrId(catReportBill.get(i).getFrId());
				subCatReport.setFrName(catReportBill.get(i).getFrName());
				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());
				subCatReport.setBillDetailNo(catReportBill.get(i).getBillDetailNo());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()) {

						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());
						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						break;

					} /*
						 * else {
						 * 
						 * catReportList.get(i).setRetAmt(0); catReportList.get(i).setRetQty(0); }
						 */

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						break;

					} /*
						 * else {
						 * 
						 * catReportList.get(i).setVarAmt(0); catReportList.get(i).setVarQty(0); }
						 */

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

	@Autowired
	SubCatCreditGrnFrItemRepRepo subCatCreditGrnFrItemRepRepo;

	@Autowired
	SubCatFrItemRepBillRepo subCatFrItemRepBillRepo;

	@RequestMapping(value = { "/getSubCatFrItemReportApi" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatItemReport> getSubCatFrItemReportApi(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<Integer> frIdList,
			@RequestParam("subCatIdList") List<Integer> subCatIdList) {

		List<SubCatItemReport> catReportList = new ArrayList<>();
		List<SubCatFrItemRepBill> catReportBill = null;

		List<SubCatCreditGrnFrItemRep> subCatCreditGrnRep = null;
		List<SubCatCreditGrnFrItemRep> subCatCreditGvnRep = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			// catReportList = subCatReportRepo.getData(fromDate, toDate);

			catReportBill = subCatFrItemRepBillRepo.getData(fromDate, toDate, frIdList, subCatIdList);

			subCatCreditGrnRep = subCatCreditGrnFrItemRepRepo.getDataGRN(fromDate, toDate, frIdList, subCatIdList);

			subCatCreditGvnRep = subCatCreditGrnFrItemRepRepo.getDataGVN(fromDate, toDate, frIdList, subCatIdList);

			System.out.println(catReportBill.toString());
			System.out.println(subCatCreditGrnRep.toString());
			System.out.println(subCatCreditGvnRep.toString());

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatItemReport subCatReport = new SubCatItemReport();

				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());
				subCatReport.setFrId(catReportBill.get(i).getFrId());
				subCatReport.setFrName(catReportBill.get(i).getFrName());
				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());
				subCatReport.setItemId(catReportBill.get(i).getItemId());
				subCatReport.setItemName(catReportBill.get(i).getItemName());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getItemId() == subCatCreditGrnRep.get(j).getItemId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()
							&& catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()) {

						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());
						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						break;

					} /*
						 * else {
						 * 
						 * catReportList.get(i).setRetAmt(0); catReportList.get(i).setRetQty(0); }
						 */

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getItemId() == subCatCreditGrnRep.get(j).getItemId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()
							&& catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						break;

					} /*
						 * else {
						 * 
						 * catReportList.get(i).setVarAmt(0); catReportList.get(i).setVarQty(0); }
						 */

				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

	// Anmol---24-02-2020
	@RequestMapping(value = { "/getAdminSubCatFrItemReportApi" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCatItemReport> getAdminSubCatFrItemReportApi(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<Integer> frIdList,
			@RequestParam("subCatIdList") List<Integer> subCatIdList,
			@RequestParam("typeIdList") List<String> typeIdList, @RequestParam("billType") int billType) {

		List<SubCatItemReport> catReportList = new ArrayList<>();
		List<SubCatFrItemRepBill> catReportBill = null;

		List<SubCatCreditGrnFrItemRep> subCatCreditGrnRep = null;
		List<SubCatCreditGrnFrItemRep> subCatCreditGvnRep = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			List<Integer> type = new ArrayList<>();
			if (typeIdList.contains("1") && typeIdList.contains("2")) {
				type.clear();
				type.add(0);
				type.add(1);
			} else if (typeIdList.contains("1")) {
				type.clear();
				type.add(0);
			} else if (typeIdList.contains("2")) {
				type.clear();
				type.add(1);
			}

			if (billType == 1) {
				catReportBill = subCatFrItemRepBillRepo.getAdminData(fromDate, toDate, frIdList, subCatIdList, type);

				subCatCreditGrnRep = subCatCreditGrnFrItemRepRepo.getAdminDataGRN(fromDate, toDate, frIdList,
						subCatIdList);

				subCatCreditGvnRep = subCatCreditGrnFrItemRepRepo.getAdminDataGVN(fromDate, toDate, frIdList,
						subCatIdList);
			} else {
				catReportBill = subCatFrItemRepBillRepo.getAdminDataCompOutlet(fromDate, toDate, frIdList,
						subCatIdList);

				subCatCreditGrnRep = subCatCreditGrnFrItemRepRepo.getAdminDataCRNCompOutlet(fromDate, toDate, frIdList,
						subCatIdList);

			}

			if (catReportBill == null) {
				catReportBill = new ArrayList<>();
			}

			if (subCatCreditGrnRep == null) {
				subCatCreditGrnRep = new ArrayList<>();
			}

			if (subCatCreditGvnRep == null) {
				subCatCreditGvnRep = new ArrayList<>();
			}

			System.out.println(catReportBill.toString());
			System.out.println(subCatCreditGrnRep.toString());
			System.out.println(subCatCreditGvnRep.toString());

			for (int i = 0; i < catReportBill.size(); i++) {

				SubCatItemReport subCatReport = new SubCatItemReport();

				subCatReport.setSubCatId(catReportBill.get(i).getSubCatId());
				subCatReport.setSubCatName(catReportBill.get(i).getSubCatName());
				subCatReport.setFrId(catReportBill.get(i).getFrId());
				subCatReport.setFrName(catReportBill.get(i).getFrName());
				subCatReport.setSoldAmt(catReportBill.get(i).getSoldAmt());
				subCatReport.setSoldQty(catReportBill.get(i).getSoldQty());
				subCatReport.setItemId(catReportBill.get(i).getItemId());
				subCatReport.setItemName(catReportBill.get(i).getItemName());

				catReportList.add(subCatReport);

			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGrnRep.size(); j++) {

					if (catReportList.get(i).getItemId() == subCatCreditGrnRep.get(j).getItemId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()
							&& catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()) {

						catReportList.get(i).setRetAmt(subCatCreditGrnRep.get(j).getVarAmt());
						catReportList.get(i).setRetQty(subCatCreditGrnRep.get(j).getVarQty());
						break;

					}

				}
			}

			for (int i = 0; i < catReportList.size(); i++) {
				for (int j = 0; j < subCatCreditGvnRep.size(); j++) {

					if (catReportList.get(i).getItemId() == subCatCreditGrnRep.get(j).getItemId()
							&& catReportList.get(i).getSubCatId() == subCatCreditGrnRep.get(j).getSubCatId()
							&& catReportList.get(i).getFrId() == subCatCreditGrnRep.get(j).getFrId()) {

						catReportList.get(i).setVarAmt(subCatCreditGvnRep.get(j).getVarAmt());
						catReportList.get(i).setVarQty(subCatCreditGvnRep.get(j).getVarQty());
						break;

					}
				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return catReportList;
	}

}
