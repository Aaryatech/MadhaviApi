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
import com.ats.webapi.model.GrandTotalBillWise;
import com.ats.webapi.model.GrandTotalCreditnoteWise;
import com.ats.webapi.model.report.B2BSalesReport;
import com.ats.webapi.model.reportv2.AdminCrNoteRegItem;
import com.ats.webapi.model.reportv2.AdminCrNoteRegisterList;
import com.ats.webapi.model.reportv2.CrNoteRegItem;
import com.ats.webapi.model.reportv2.CrNoteRegSp;
import com.ats.webapi.model.reportv2.CrNoteRegisterList;
import com.ats.webapi.model.reportv2.GstRegisterItem;
import com.ats.webapi.model.reportv2.GstRegisterList;
import com.ats.webapi.model.reportv2.GstRegisterSp;
import com.ats.webapi.model.reportv2.HSNItemWiseReport;
import com.ats.webapi.model.reportv2.HSNWiseReport;
import com.ats.webapi.model.reportv2.SalesReport;
import com.ats.webapi.repo.GrandTotalCreditnoteWiseRepository;
import com.ats.webapi.repository.reportv2.AdminCrNoteRegItemRepo;
import com.ats.webapi.repository.reportv2.B2BSalesReportRepo;
import com.ats.webapi.repository.reportv2.CrNoteRegItemRepo;
import com.ats.webapi.repository.reportv2.CrNoteRegSpRepo;
import com.ats.webapi.repository.reportv2.GstRegisterItemRepo;
import com.ats.webapi.repository.reportv2.GstRegisterSpRepo;
import com.ats.webapi.repository.reportv2.HSNItemWiseReportRepo;
import com.ats.webapi.repository.reportv2.HSNWiseReportRepo;
import com.ats.webapi.repository.reportv2.SalesReportRepo;

@RestController
public class ReportControllerV2 {

	@Autowired
	SalesReportRepo getSalesReportRepo;
	@Autowired
	GstRegisterItemRepo getGstRegisterItemRepo;

	@Autowired
	GstRegisterSpRepo getGstRegisterSpRepo;

	@Autowired
	CrNoteRegSpRepo getCrNoteRegSpRepo;
	@Autowired
	CrNoteRegItemRepo getCrNoteRegItemRepo;

	@Autowired
	HSNWiseReportRepo hSNWiseReportRepo;

	@Autowired
	GrandTotalCreditnoteWiseRepository grandTotalCreditnoteWiseRepository;
	
	@Autowired
	B2BSalesReportRepo b2bSalesRepo;

	@RequestMapping(value = { "/getHsnBillReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnBillReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("typeIdList") List<String> typeIdList) {
		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			/*
			 * saleList = hSNWiseReportRepo.getReport(fromDate, toDate);
			 */

			int listSize = typeIdList.size();
			List<Integer> itmList = new ArrayList<Integer>();
			System.err.println("type list" + typeIdList.toString());

			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = hSNWiseReportRepo.getReportAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = hSNWiseReportRepo.getReport12(fromDate, toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = hSNWiseReportRepo.getReportAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = hSNWiseReportRepo.getReportAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = hSNWiseReportRepo.getReport12(fromDate, toDate, itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = hSNWiseReportRepo.getReport12(fromDate, toDate, itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				saleList = hSNWiseReportRepo.getReport3(fromDate, toDate);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saleList;
	}

	// Anmol--27-02-2020----
	@RequestMapping(value = { "/getAdminHsnBillReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getAdminHsnBillReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("bTypeId") int bTypeId) {
		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			if (bTypeId == 0) {

				saleList = hSNWiseReportRepo.getAdminReportAll(fromDate, toDate);

			} else if (bTypeId == 1) {

				saleList = hSNWiseReportRepo.getAdminReportBillWiseFrWise(fromDate, toDate);

			} else if (bTypeId == 3) {

				saleList = hSNWiseReportRepo.getAdminReportBillWiseCompOutletWise(fromDate, toDate);

			}
		} catch (Exception e) {
		}
		return saleList;
	}

	@Autowired
	HSNItemWiseReportRepo hSNItemWiseReportRepo;

	// Anmol--11-03-2020----
	@RequestMapping(value = { "/getAdminHsnItemWiseBillReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNItemWiseReport> getAdminHsnItemWiseBillReport(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("bTypeId") int bTypeId) {
		List<HSNItemWiseReport> saleList = new ArrayList<>();
		try {

			if (bTypeId == 0) {

				saleList = hSNItemWiseReportRepo.getAdminReportAll(fromDate, toDate);

			} else if (bTypeId == 1) {

				saleList = hSNItemWiseReportRepo.getAdminReportBillWiseFrWise(fromDate, toDate);

			} else if (bTypeId == 3) {

				saleList = hSNItemWiseReportRepo.getAdminReportBillWiseCompOutletWise(fromDate, toDate);

			}
		} catch (Exception e) {
		}
		return saleList;
	}

	// Anmol--12-03-2020----
	@RequestMapping(value = { "/getOPSHsnItemWiseBillReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNItemWiseReport> getOPSHsnItemWiseBillReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId) {
		List<HSNItemWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNItemWiseReportRepo.getOPSReportBillWiseCompOutletWiseFr(fromDate, toDate,frId);

		} catch (Exception e) {
		}
		return saleList;
	}

	@RequestMapping(value = { "/getHsnReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNWiseReportRepo.getReportHsn(fromDate, toDate);
			System.out.println(saleList.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return saleList;
	}

	// Anmol--27-02-2020--
	@RequestMapping(value = { "/getAdminHsnReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("bTypeId") int bTypeId) {

		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			if (bTypeId == 0) {
				saleList = hSNWiseReportRepo.getAdminReportCRNAll(fromDate, toDate);
			} else if (bTypeId == 1) {
				saleList = hSNWiseReportRepo.getAdminReportHsnCrFrWise(fromDate, toDate);
			} else if (bTypeId == 3) {
				saleList = hSNWiseReportRepo.getAdminReportHsnCrCompOutletWise(fromDate, toDate);
			}

			System.out.println(saleList.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return saleList;
	}

	// Anmol--11-03-2020--
	@RequestMapping(value = { "/getAdminHsnItemReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNItemWiseReport> getAdminHsnItemReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("bTypeId") int bTypeId) {

		List<HSNItemWiseReport> saleList = new ArrayList<>();
		try {

			if (bTypeId == 0) {
				saleList = hSNItemWiseReportRepo.getAdminReportCRNAll(fromDate, toDate);
			} else if (bTypeId == 1) {
				saleList = hSNItemWiseReportRepo.getAdminReportHsnCrFrWise(fromDate, toDate);
			} else if (bTypeId == 3) {
				saleList = hSNItemWiseReportRepo.getAdminReportHsnCrCompOutletWise(fromDate, toDate);
			}

			System.out.println(saleList.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return saleList;
	}
	
	
	// Anmol--12-03-2020--
		@RequestMapping(value = { "/getOPSHsnItemReport" }, method = RequestMethod.POST)
		public @ResponseBody List<HSNItemWiseReport> getOPSHsnItemReport(@RequestParam("fromDate") String fromDate,
				@RequestParam("toDate") String toDate, @RequestParam("frId") int frId) {

			List<HSNItemWiseReport> saleList = new ArrayList<>();
			try {

					saleList = hSNItemWiseReportRepo.getOPSReportHsnCrCompOutletWise(fromDate, toDate,frId);

				System.out.println(saleList.toString());

			} catch (Exception e) {
				// TODO: handle exception
			}

			return saleList;
		}

	@RequestMapping(value = { "/getHsnBillReportByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnBillReportByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNWiseReportRepo.getReportByFrId(frId, fromDate, toDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saleList;
	}

	@RequestMapping(value = { "/getHsnReportByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnReportByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNWiseReportRepo.getReportHsnByFrId(frId, fromDate, toDate);
			System.out.println(saleList.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return saleList;
	}

	@RequestMapping(value = { "/getSalesReportV2" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReport> getSalesReportV2(@RequestParam("frIdList") List<String> frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReport> saleList = new ArrayList<>();

		int listSize = typeIdList.size();
		List<Integer> itmList = new ArrayList<Integer>();
		System.err.println("type list" + typeIdList.toString());

		if (frIdList.contains("-1")) {

			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportAllFrAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportAllFr12(fromDate, toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportAllFrAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = getSalesReportRepo.getSalesReportAllFrAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = getSalesReportRepo.getSalesReportAllFr12(fromDate, toDate, itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportAllFr12(fromDate, toDate, itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				saleList = getSalesReportRepo.getSalesReportAllFr3(fromDate, toDate);

			}

		} else {

			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportSpecFrAll(fromDate, toDate, frIdList, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportSpecFr12(fromDate, toDate, frIdList, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportSpecFrAll(fromDate, toDate, frIdList, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = getSalesReportRepo.getSalesReportSpecFrAll(fromDate, toDate, frIdList, itmList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = getSalesReportRepo.getSalesReportSpecFr12(fromDate, toDate, frIdList, itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = getSalesReportRepo.getSalesReportSpecFr12(fromDate, toDate, frIdList, itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				saleList = getSalesReportRepo.getSalesReportSpecFr3(fromDate, toDate, frIdList);

			}

		}

		return saleList;
	}

	@RequestMapping(value = { "/getAdminSalesReportV2" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReport> getAdminSalesReportV2(@RequestParam("frIdList") List<String> frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("typeIdList") List<String> typeIdList, @RequestParam("billType") int billType,
			@RequestParam("dairy") List<String> dairy, @RequestParam("configType") int configType) {

		List<SalesReport> saleList = new ArrayList<>();

		int listSize = typeIdList.size();
		List<Integer> itmList = new ArrayList<Integer>();
		System.err.println("type list" + typeIdList.toString());

		List<Integer> dairyList = new ArrayList<Integer>();
		if (dairy.contains("1") && dairy.contains("2")) {
			dairyList.clear();
			dairyList.add(1);
			dairyList.add(2);
		} else if (dairy.contains("1")) {
			dairyList.clear();
			dairyList.add(1);
		} else if (dairy.contains("2")) {
			dairyList.clear();
			dairyList.add(2);
		}

		if (billType == 1) {

			if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList = getSalesReportRepo.getAdminSalesReportFr(fromDate, toDate, frIdList, itmList, dairyList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList = getSalesReportRepo.getAdminSalesReportFr(fromDate, toDate, frIdList, itmList, dairyList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList = getSalesReportRepo.getAdminSalesReportFr(fromDate, toDate, frIdList, itmList, dairyList);
				System.err.println(" 2");

			}

		} else {
			
			if (dairy.contains("1") && dairy.contains("2")) {
				saleList = getSalesReportRepo.getSalesReportFrCompOutletDairyAndReg(fromDate, toDate, frIdList, configType);
				
			} else if (dairy.contains("1")) {
				saleList = getSalesReportRepo.getSalesReportFrCompOutlet(fromDate, toDate, frIdList, configType);
				
			} else if (dairy.contains("2")) {
				saleList = getSalesReportRepo.getSalesReportFrCompOutletDairy(fromDate, toDate, frIdList);
				
			}
			
			
		}

		return saleList;
	}

	@RequestMapping(value = { "/getGstRegister" }, method = RequestMethod.POST)
	public @ResponseBody GstRegisterList getGstRegister(@RequestParam("frIdList") List<String> frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("typeIdList") List<String> typeIdList) {

		GstRegisterList gstList = new GstRegisterList();
		System.err.println("fr list" + frIdList.toString());

		int listSize = typeIdList.size();
		List<Integer> itmList = new ArrayList<Integer>();
		if (frIdList.contains("-1")) {
			// all fr

			System.err.println("all Fr");
			List<GstRegisterItem> saleList1 = new ArrayList<>();

			System.out.println("type len" + typeIdList.size());
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12(fromDate, toDate, itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAll(fromDate, toDate, itmList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12(fromDate, toDate, itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12(fromDate, toDate, itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem3(fromDate, toDate);

			}
			gstList.setGstRegItemList(saleList1);

		}

		else {
			// spec fr

			System.err.println("single  Fr");
			List<GstRegisterItem> saleList1 = new ArrayList<>();

			System.out.println("type len" + typeIdList.size());
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAllFr(fromDate, toDate, itmList, frIdList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize == 2) {

				System.err.println("1 2");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12Fr(fromDate, toDate, itmList, frIdList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 2 3");
				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAllFr(fromDate, toDate, itmList, frIdList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize == 2) {
				System.err.println(" 1 3");
				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItemAllFr(fromDate, toDate, itmList, frIdList);

			} else if (typeIdList.contains("1") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(0);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12Fr(fromDate, toDate, itmList, frIdList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") && listSize == 1) {

				itmList = new ArrayList<Integer>();
				itmList.add(1);
				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem12Fr(fromDate, toDate, itmList, frIdList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem3Fr(fromDate, toDate, frIdList);

			}
			gstList.setGstRegItemList(saleList1);

		}
		System.err.println("size Item  gstList " + gstList.getGstRegItemList().size());

		return gstList;
	}

	@RequestMapping(value = { "/getCrNoteRegister" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegister(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList = new ArrayList<CrNoteRegSp>();

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItem(fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		// crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSp(fromDate, toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}

	// neha
	@RequestMapping(value = { "/getCrNoteRegisterByFrId" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegisterByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList = new ArrayList<CrNoteRegSp>();

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItemByFrId(frId, fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		// crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSpByFrId(frId, fromDate,
		// toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}

	@RequestMapping(value = { "/getCrNoteRegisterDone" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegisterDone(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList = new ArrayList<>();

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItemDone(fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		// crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSpDone(fromDate, toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}

	@Autowired
	AdminCrNoteRegItemRepo adminCrNoteRegItemRepo;

	// Anmol--26-02-2020-----------
	@RequestMapping(value = { "/getAdminCrNoteRegisterDone" }, method = RequestMethod.POST)
	public @ResponseBody AdminCrNoteRegisterList getAdminCrNoteRegisterDone(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("typeId") int typeId,
			@RequestParam("bType") int bType) {

		AdminCrNoteRegisterList crNoteList = new AdminCrNoteRegisterList();

		List<AdminCrNoteRegItem> crNoteRegItemList = null;
		List<CrNoteRegSp> crNoteRegSpList = new ArrayList<>();

		if (typeId == 0 && bType == 1) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getAllB2B(fromDate, toDate);
		} else if (typeId == 0 && bType == 2) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getAllB2C(fromDate, toDate);
		} else if (typeId == 1 && bType == 1) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getCrNoteRegItemDoneB2B(fromDate, toDate);
		} else if (typeId == 1 && bType == 2) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getCrNoteRegItemDoneB2C(fromDate, toDate);
		} else if (typeId == 3 && bType == 1) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getCompCrNoteB2B(fromDate, toDate);
		} else if (typeId == 3 && bType == 2) {
			crNoteRegItemList = adminCrNoteRegItemRepo.getCompCrNoteB2C(fromDate, toDate);
		}

		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		// crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSpDone(fromDate, toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}

	@RequestMapping(value = { "/getGrandTotalCreditnotewise" }, method = RequestMethod.POST)
	public @ResponseBody List<GrandTotalCreditnoteWise> getGrandTotalCreditnotewise(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<GrandTotalCreditnoteWise> tax1ReportList = new ArrayList<>();
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			tax1ReportList = grandTotalCreditnoteWiseRepository.getGrandTotalCreditnotewise(fromDate, toDate);

		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	// neha
	@RequestMapping(value = { "/getCrNoteRegisterDoneByFrId" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegisterDoneByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList = new ArrayList<CrNoteRegSp>();

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItemDoneByFrId(frId, fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		// crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSpDoneByFrId(frId, fromDate,
		// toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}
	
	
	
	@RequestMapping(value = { "/getB2BSalesReportBetDate" }, method = RequestMethod.POST)
	public @ResponseBody List<B2BSalesReport> getCB2BSalesReportFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<B2BSalesReport> rep = new ArrayList<B2BSalesReport>();
		try {
		rep = b2bSalesRepo.getB2BSalesReport(fromDate, toDate, frId);	
		}catch (Exception e) {
			System.err.println("Excep in /getCB2BSalesReportFrId" + e.getMessage());
		}

		return rep;
	}
}
