package com.ats.webapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.GrandTotalBillWise;
import com.ats.webapi.model.report.frpurchase.SalesReportBillwise;
import com.ats.webapi.model.report.frpurchase.SalesReportBillwiseAllFr;
import com.ats.webapi.model.report.frpurchase.SalesReportFranchisee;
import com.ats.webapi.model.report.frpurchase.SalesReportItemwise;
import com.ats.webapi.model.report.frpurchase.SalesReportRoyalty;
import com.ats.webapi.model.report.frpurchase.SalesReportRoyaltyFr;
import com.ats.webapi.model.salesvaluereport.SalesReturnItemDaoList;
import com.ats.webapi.model.salesvaluereport.SalesReturnQtyDao;
import com.ats.webapi.model.salesvaluereport.SalesReturnQtyReportList;
import com.ats.webapi.model.salesvaluereport.SalesReturnValueDao;
import com.ats.webapi.model.salesvaluereport.SalesReturnValueDaoList;
import com.ats.webapi.model.salesvaluereport.SalesReturnValueItemDao;
import com.ats.webapi.model.taxreport.NonRegFrTaxDao;
import com.ats.webapi.model.taxreport.Tax1Report;
import com.ats.webapi.model.taxreport.Tax2Report;
import com.ats.webapi.repo.GrandTotalBillWiseRepository;
import com.ats.webapi.repository.frpurchasereport.SaleReportBillwiseAllFrRepo;
import com.ats.webapi.repository.frpurchasereport.SaleReportBillwiseRepo;
import com.ats.webapi.repository.frpurchasereport.SaleReportItemwiseRepo;
import com.ats.webapi.repository.frpurchasereport.SalesReportFranchiseeRepo;
import com.ats.webapi.repository.frpurchasereport.SalesReportRoyaltyFrRepo;
import com.ats.webapi.repository.frpurchasereport.SalesReportRoyaltyRepo;
import com.ats.webapi.repository.salesreturnreport.SalesReturnQtyDaoRepository;
import com.ats.webapi.repository.salesreturnreport.SalesReturnValueDaoRepository;
import com.ats.webapi.repository.salesreturnreport.SalesReturnValueItemDaoRepo;
import com.ats.webapi.repository.taxreport.NonRegFrTaxDaoRepository;
import com.ats.webapi.repository.taxreport.Tax1ReportRepository;
import com.ats.webapi.repository.taxreport.Tax2ReportRepository;

@RestController
public class SalesReportController {

	@Autowired
	SaleReportBillwiseRepo saleReportBillwiseRepo;

	@Autowired
	SalesReportRoyaltyRepo salesReportRoyaltyRepo;

	@Autowired
	SalesReportRoyaltyFrRepo salesReportRoyaltyFrRepo;

	@Autowired
	SaleReportBillwiseAllFrRepo saleReportBillwiseAllFrRepo;// report 7

	@Autowired
	SaleReportItemwiseRepo saleReportItemwiseRepo; // report 8

	@Autowired
	Tax1ReportRepository tax1ReportRepository;

	@Autowired
	Tax2ReportRepository tax2ReportRepository;
	@Autowired
	SalesReturnQtyDaoRepository salesReturnQtyDaoRepository;
	@Autowired
	SalesReturnValueDaoRepository salesReturnValueDaoRepository;

	@Autowired
	SalesReturnValueItemDaoRepo salesReturnValueItemDaoRepo;

	@Autowired
	SalesReportFranchiseeRepo salesReportFranchiseeRepo;

	@Autowired
	NonRegFrTaxDaoRepository nonRegFrTaxDaoRepository;

	@Autowired
	GrandTotalBillWiseRepository grandTotalBillWiseRepository;

	@RequestMapping(value = { "/getSaleReportFrwiseSummery" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportFranchisee> getSaleReportFrwiseSummery(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<SalesReportFranchisee> salesReportFranchisee = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			// System.out.println("Input received " + fromDate + "" + toDate + "" +
			// frIdList);
			salesReportFranchisee = salesReportFranchiseeRepo.getSaleReportBillwise(frIdList, fromDate, toDate);

		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportFranchisee;
	}

	@RequestMapping(value = { "/getTax1Report" }, method = RequestMethod.POST)
	public @ResponseBody List<Tax1Report> getTax1Report(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,@RequestParam("typeIdList") List<String> typeIdList) {

		List<Tax1Report> tax1ReportList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.err.println("type list"+typeIdList.toString());
			
			
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				tax1ReportList = tax1ReportRepository.getTax1ReportAll(fromDate, toDate,
						itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				tax1ReportList = tax1ReportRepository.getTax1Report12(fromDate, toDate,
						itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				tax1ReportList = tax1ReportRepository.getTax1ReportAll(fromDate,
						toDate,itmList );

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				System.err.println(" 1 3");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				tax1ReportList = tax1ReportRepository.getTax1ReportAll(fromDate,
						toDate,itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				tax1ReportList = tax1ReportRepository.getTax1Report12(fromDate, toDate,
						itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				tax1ReportList = tax1ReportRepository.getTax1Report12(fromDate, toDate,
						itmList);
				System.err.println(" 2");

			} else   {
				System.err.println(" 3");

				tax1ReportList = tax1ReportRepository.getTax1Report3(fromDate, toDate);

			}  
		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	@RequestMapping(value = { "/getGrandTotalBillWise" }, method = RequestMethod.POST)
	public @ResponseBody List<GrandTotalBillWise> getGrandTotalBillWise(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<GrandTotalBillWise> tax1ReportList = new ArrayList<>();
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			tax1ReportList = grandTotalBillWiseRepository.getGrandTotalBillWise(fromDate, toDate);
		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	@RequestMapping(value = { "/getTax1ReportByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<Tax1Report> getTax1ReportByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<Tax1Report> tax1ReportList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			tax1ReportList = tax1ReportRepository.getTax1ReportByFrId(frId, fromDate, toDate);
		} catch (Exception e) {
			System.out.println(" Exce in Tax1 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	@RequestMapping(value = { "/getTax2ReportByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<Tax2Report> getTax2ReportByFrId(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<Tax2Report> tax1ReportList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			tax1ReportList = tax2ReportRepository.getTax2ReportByFrId(frId, fromDate, toDate);
		} catch (Exception e) {
			System.out.println(" Exce in Tax2 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	@RequestMapping(value = { "/getTax2Report" }, method = RequestMethod.POST)
	public @ResponseBody List<Tax2Report> getTax2Report(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,@RequestParam("typeIdList") List<String> typeIdList) {

		List<Tax2Report> tax1ReportList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			/*
			 * tax1ReportList = tax2ReportRepository.getTax2Report(fromDate, toDate);
			 */			
			
			
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.err.println("type list"+typeIdList.toString());
			
			
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				tax1ReportList = tax2ReportRepository.getTax2ReportAll(fromDate, toDate,
						itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				tax1ReportList = tax2ReportRepository.getTax2Report12(fromDate, toDate,
						itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				tax1ReportList = tax2ReportRepository.getTax2ReportAll(fromDate,
						toDate,itmList );

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				System.err.println(" 1 3");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				tax1ReportList = tax2ReportRepository.getTax2ReportAll(fromDate,
						toDate,itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				tax1ReportList = tax2ReportRepository.getTax2Report12(fromDate, toDate,
						itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				tax1ReportList = tax2ReportRepository.getTax2Report12(fromDate, toDate,
						itmList);
				System.err.println(" 2");

			} else   {
				System.err.println(" 3");

				tax1ReportList = tax2ReportRepository.getTax2Report3(fromDate, toDate);

			}  
		} catch (Exception e) {
			System.out.println(" Exce in Tax2 Report " + e.getMessage());
			e.printStackTrace();
		}
		return tax1ReportList;
	}

	@RequestMapping(value = { "/getNonRegFrTaxReport" }, method = RequestMethod.POST)
	public @ResponseBody List<NonRegFrTaxDao> getNonRegFrTaxReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<NonRegFrTaxDao> taxReportList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			taxReportList = nonRegFrTaxDaoRepository.getNonRegFrTaxReport(fromDate, toDate);
		} catch (Exception e) {
			System.out.println(" Exce in getNonRegFrTaxReport Report " + e.getMessage());
			e.printStackTrace();
		}
		return taxReportList;
	}

	// Report 1 sales report bill wise order by date
	@RequestMapping(value = { "/getSaleReportBillwise" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwise(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("catIdList") List<String> catIdList,
			@RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReportBillwise> salesReportBillwiseList = null;

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize=typeIdList.size();
			
			System.out.println("type list"+typeIdList.toString());
			List<Integer> itmList=new ArrayList<Integer>();
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				System.err.println("all");
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType(frIdList,fromDate, toDate,
						catIdList,itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseFrType1N2(frIdList,fromDate, toDate,
						catIdList,itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				
				
				itmList=new ArrayList<Integer>();
 				itmList.add(1);
				System.err.println("all");
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType(frIdList,fromDate, toDate,
						catIdList,itmList);
				 

			}
			 else if (typeIdList.contains("1") && typeIdList.contains("3") &&  listSize==2) {
					System.err.println(" 1 3");
					
					
					itmList=new ArrayList<Integer>();
	 				itmList.add(0);
					System.err.println("all");
					salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType(frIdList,fromDate, toDate,
							catIdList,itmList);
					 

				}

			 else if (typeIdList.contains("2") &&  listSize==1 ) {
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseFrType1N2(frIdList,fromDate, toDate,
						catIdList,itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseFrType1N2(frIdList,fromDate, toDate,
						catIdList,itmList);
				System.err.println(" 2");

			} else {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseFrOutletType3(frIdList,fromDate, toDate,
						catIdList);

			} 

			System.out.println("getSaleReportBillwise" + salesReportBillwiseList.toString());

		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// Report 1 sales report bill wise order by date All Fr
	@RequestMapping(value = { "/getSaleReportBillwiseAllFrSelected" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseAllFrSelected(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("catIdList") List<String> catIdList, @RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {

			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
		 System.out.println("type len"+typeIdList.size());
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrAllType(fromDate, toDate,
						catIdList,itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType1N2(fromDate, toDate,
						catIdList,itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrAllType(fromDate,
						toDate, catIdList,itmList );

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				System.err.println(" 1 3");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrAllType(fromDate,
						toDate, catIdList,itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType1N2(fromDate, toDate,
						catIdList,itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrType1N2(fromDate, toDate,
						catIdList, itmList);
				System.err.println(" 2");

			} else   {
				System.err.println(" 3");

				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseAllFrOutletType3(fromDate, toDate,
						catIdList);

			}  

		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 2 sales report Summary Group By Party ie Fr Name
	@RequestMapping(value = { "/getSaleReportBillwiseByFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByFr(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("catIdList") List<String> catIdList,
			@RequestParam("typeId") int typeId) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		int flag = -1;
		if (typeId == 1) {
			flag = 0;
		} else if (typeId == 2) {
			flag = 1;
		} else {
			flag = -1;
		}

		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "" + frIdList);
			if (flag != -1) {
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByFr(frIdList, fromDate, toDate,
						catIdList, flag);
			} else {
				salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseFrOutlet(frIdList, fromDate,
						toDate, catIdList);

			}
			System.out.println("getSaleReportBillwiseByFr" + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise  by Fr " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 2 sales report Summary Group By Party ie Fr Name All Fr Selected
	@RequestMapping(value = { "/getSaleReportBillwiseByFrAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByFrAllFrSel(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("catIdList") List<String> catIdList) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "");

			salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByFrAllFr(fromDate, toDate,
					catIdList);
			System.out.println("getSaleReportBillwiseByFr" + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise  by Fr " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 3 sales report datewise group by date
	@RequestMapping(value = { "/getSaleReportBillwiseByDate" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByDate(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "" + frIdList);

			salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDate(frIdList, fromDate, toDate);
			System.out.println("getSaleReportBillwiseByDate" + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise by Date " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 3 sales report datewise group by date all Fr Se
	@RequestMapping(value = { "/getSaleReportBillwiseByDateAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByDate(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "");

			salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByDateAllFr(fromDate, toDate);
			System.out.println("getSaleReportBillwiseByDate" + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise by Date " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 4
	@RequestMapping(value = { "/getSaleReportBillwiseByMonth" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByMonth(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "" + frIdList);

			salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonth(frIdList, fromDate, toDate);
			System.out.println("getSaleReportBillwiseByMonth " + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise by Month " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// report 4 all Fr selected
	@RequestMapping(value = { "/getSaleReportBillwiseByMonthAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwise> getSaleReportBillwiseByMonthAllFrSel(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReportBillwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "");

			salesReportBillwiseList = saleReportBillwiseRepo.getSaleReportBillwiseByMonthAllFr(fromDate, toDate);
			System.out.println("getSaleReportBillwiseByMonth " + salesReportBillwiseList.toString());
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise by Month " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	// Royalty report Started
	// Report 5
	@RequestMapping(value = { "/getSalesReportRoyalty" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyalty> getSalesReportRoyalty(@RequestParam("frIdList") List<String> frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReportRoyalty> salesReportRoyaltyList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "" + frIdList);
			salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyalty(frIdList, fromDate, toDate);
			System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());

		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyList;
	}

	// Report 5 all Fr Selected//Ok tested
	@RequestMapping(value = { "/getSalesReportRoyaltyAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyalty> getSalesReportRoyaltyAllFrSelc(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReportRoyalty> salesReportRoyaltyList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate);
			salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyaltyAllFr(fromDate, toDate);
			System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());

		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty all fr " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyList;
	}

	// report no 6
	@RequestMapping(value = { "/getSalesReportRoyaltyFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyaltyFr> getSalesReportRoyaltyFr(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<SalesReportRoyaltyFr> salesReportRoyaltyFrList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate + "" + frIdList);
			salesReportRoyaltyFrList = salesReportRoyaltyFrRepo.getSaleReportRoyaltyFr(frIdList, fromDate, toDate);
			System.out.println("sale sReportRoyalty Fr List" + salesReportRoyaltyFrList.toString());

		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty Fr  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyFrList;
	}

	// report no 6 All Fr //Ok tested
	@RequestMapping(value = { "/getSalesReportRoyaltyFrAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyaltyFr> getSalesReportRoyaltyFrAllFr(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		System.err.println("in method getSalesReportRoyaltyFrAllFr salesReportController");
		List<SalesReportRoyaltyFr> salesReportRoyaltyFrList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received " + fromDate + "" + toDate);
			salesReportRoyaltyFrList = salesReportRoyaltyFrRepo.getSaleReportRoyaltyFrAllFrSel(fromDate, toDate);
			System.out.println("sale getSalesReportRoyaltyFrAllFr Fr List" + salesReportRoyaltyFrList.toString());

		} catch (Exception e) {
			System.out.println(
					" Exce in sales Report Royalty Fr all fr sel /getSalesReportRoyaltyFrAllFr " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyFrList;
	}

	// report 7 by default All Fr report

	@RequestMapping(value = { "/getSaleReportBillwiseAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportBillwiseAllFr> getSaleReportBillwiseAllFr(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate ,@RequestParam("typeIdList") List<String> typeIdList) {
		System.err.println("item are"+typeIdList.toString());
		List<SalesReportBillwiseAllFr> salesReportBillwiseAllFr = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.err.println("item are"+typeIdList.toString());
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
		 System.out.println("type len"+typeIdList.size());
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFrAll(fromDate,
						toDate,itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFr1N2(
						fromDate, toDate,itmList);

			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				 
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFrAll(fromDate,
						toDate,itmList);


			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFrAll(fromDate,
						toDate,itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				itmList=new ArrayList<Integer>();
				itmList.add(0);
 				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFr1N2(fromDate,
						toDate,itmList);

				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				itmList=new ArrayList<Integer>();
				itmList.add(1);
 				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFr1N2(fromDate,
						toDate,itmList);

				System.err.println(" 2");

			} else  {
				System.err.println(" 3");

				salesReportBillwiseAllFr = saleReportBillwiseAllFrRepo.getSaleReportBillwiseAllFr1N3(fromDate,
						toDate);
		}
		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwise all fr  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseAllFr;
	}

	@RequestMapping(value = { "/getSaleReportItemwise" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportItemwise> getSaleReportItemwise(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("catId") int catId,
			@RequestParam("typeIdList") List<String> typeIdList) {

		List<SalesReportItemwise> salesReportBillwiseList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.out.println("Input received " + fromDate + "" + toDate);
 
	 
				 
				 if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						System.err.println("all");
						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPackingOutletAll(fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						System.err.println("1 2");
						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPacking1N2(fromDate, toDate
								,itmList);

					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						
						itmList=new ArrayList<Integer>();
 						itmList.add(1);
						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPackingOutletAll(fromDate,
								toDate, itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
 						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPackingOutletAll(fromDate,
								toDate, itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						itmList=new ArrayList<Integer>();
						itmList.add(0);
 						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPacking1N2(fromDate, toDate,
 								itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1 ) {
						itmList=new ArrayList<Integer>();
 						itmList.add(1);
						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPacking1N2(fromDate, toDate,
								itmList);
						System.err.println(" 2");

					} else {
						System.err.println(" 3");

						salesReportBillwiseList = saleReportItemwiseRepo.getSaleReportItemwiseExceptTradingPackingOutlet3(fromDate, toDate
								);

					}  
				 
		 

		} catch (Exception e) {
			System.out.println(" Exce in sale Report Billwisesales Report Itemwise " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportBillwiseList;
	}

	
	@RequestMapping(value = { "/getSaleReportRoyConsoByCatAllFr" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr(
			@RequestParam("catIdList") List<String> catIdList, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("getBy") int getBy, @RequestParam("type") int type,@RequestParam("typeIdList") List<String> typeIdList) {
		System.out.println("getBy" + getBy);
		List<SalesReportRoyalty> salesReportRoyaltyList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received for report 10 roy by category all fr Selected " + fromDate + "" + toDate
					+ "" + "cat=" + catIdList);
			
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.err.println("type list"+typeIdList.toString());
			

			if (catIdList.contains("0")) {

				System.err.println("Cat ID List contains zero ");
				catIdList.clear();
				catIdList.add("1");
				catIdList.add("2");
				catIdList.add("3");
				catIdList.add("4");
				catIdList.add("5");
				catIdList.add("6");
				System.err.println("New cat ID List" + catIdList);

			}
			if (getBy == 1) {
				if (type == 1) {
					 //1st
					  
						if (typeIdList.contains("-1")
								|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

							System.err.println("all");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							itmList.add(1);

							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123(catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

							System.err.println("1 2");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12(catIdList,
									fromDate, toDate,itmList);
						} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
							System.err.println(" 2 3");
							itmList=new ArrayList<Integer>();
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123(catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
							System.err.println(" 1 3");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123(catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") &&  listSize==1 ) {
							
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12(catIdList,
									fromDate, toDate,itmList);
							System.err.println(" 1");

						} else if (typeIdList.contains("2") &&  listSize==1) {
							
							itmList=new ArrayList<Integer>();
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12(catIdList,
									fromDate, toDate,itmList);
							System.err.println(" 2");

						} else   {
							System.err.println(" 3");
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr3(catIdList,
									fromDate, toDate);

						}  
						 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					 
				} else if (type == 2) {
					 //2nd
 					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212(catIdList,
								fromDate, toDate,itmList);
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType23(catIdList,
								fromDate, toDate);

					}  
					 
				}
			} else {
				if (type == 1) {
					 
					//3 
					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12(catIdList,
								fromDate, toDate,itmList);
						
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3(catIdList,
								fromDate, toDate);

					}  
					 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					 
				} else if (type == 2) {
//4
					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2(catIdList,
								fromDate, toDate,itmList);
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4All(catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2(catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType43(catIdList,
								fromDate, toDate);

					}  
					 
 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					
				}
			}

		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty  By Category " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyList;
	}

	
	
	// report 10 AS OF REPORT 5
	@RequestMapping(value = { "/getSaleReportRoyConsoByCat" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyalty> getSaleReportRoyConsoByCat(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("catIdList") List<String> catIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("getBy") int getBy, @RequestParam("type") int type,@RequestParam("typeIdList") List<String> typeIdList) {
		System.out.println("getBy" + getBy);
		List<SalesReportRoyalty> salesReportRoyaltyList = new ArrayList<>();
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);

			System.out.println("Input received for report 10 roy by category few fr Selected " + fromDate + "" + toDate
					+ "" + frIdList + "cat=" + catIdList);
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.err.println("type list"+typeIdList.toString());
			

			if (catIdList.contains("0")) {

				System.err.println("Cat ID List contains zero ");
				catIdList.clear();
				catIdList.add("1");
				catIdList.add("2");
				catIdList.add("3");
				catIdList.add("4");
				catIdList.add("8");
				catIdList.add("6");
				System.err.println("New cat ID List" + catIdList);

			}
			if (getBy == 1) {
				if (type == 1) {
					 //1st
					  
						if (typeIdList.contains("-1")
								|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

							System.err.println("all");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							itmList.add(1);

							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123Fran(frIdList,catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

							System.err.println("1 2");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12Fran(frIdList,catIdList,
									fromDate, toDate,itmList);
						} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
							System.err.println(" 2 3");
							itmList=new ArrayList<Integer>();
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123Fran(frIdList,catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
							System.err.println(" 1 3");
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr123Fran(frIdList,catIdList,
									fromDate, toDate,itmList);

						} else if (typeIdList.contains("1") &&  listSize==1 ) {
							
							itmList=new ArrayList<Integer>();
							itmList.add(0);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12Fran(frIdList,catIdList,
									fromDate, toDate,itmList);
							System.err.println(" 1");

						} else if (typeIdList.contains("2") &&  listSize==1) {
							
							itmList=new ArrayList<Integer>();
							itmList.add(1);
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr12Fran(frIdList,catIdList,
									fromDate, toDate,itmList);
							System.err.println(" 2");

						} else   {
							System.err.println(" 3");
							salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFr3Fran(frIdList,catIdList,
									fromDate, toDate);

						}  
						 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					 
				} else if (type == 2) {
					 //2nd
 					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType2AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType212Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrAndType23Fran(frIdList,catIdList,
								fromDate, toDate);

					}  
					 
				}
			} else {
				if (type == 1) {
					 
					//3 
					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal12Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotal3Fran(frIdList,catIdList,
								fromDate, toDate);

					}  
					 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					 
				} else if (type == 2) {
//4
					if (typeIdList.contains("-1")
							|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

						System.err.println("all");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);

						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

						System.err.println("1 2");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
					} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
						System.err.println(" 2 3");
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
						System.err.println(" 1 3");
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4AllFran(frIdList,catIdList,
								fromDate, toDate,itmList);

					} else if (typeIdList.contains("1") &&  listSize==1 ) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(0);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 1");

					} else if (typeIdList.contains("2") &&  listSize==1) {
						
						itmList=new ArrayList<Integer>();
						itmList.add(1);
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2Fran(frIdList,catIdList,
								fromDate, toDate,itmList);
						System.err.println(" 2");

					} else   {
						System.err.println(" 3");
						salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatAllFrByGrandTotalAndType43Fran(frIdList,catIdList,
								fromDate, toDate);

					}  
					 
 
						System.out.println("getSaleReportBillwise" + salesReportRoyaltyList.toString());
					
				}
			}
		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty  By Category " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyList;
	}

	// report no 10 all fr and multiple category


	// report 10 AS OF REPORT 5 for Graph
	@RequestMapping(value = { "/getSaleReportRoyConsoByCatForGraph" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReportRoyalty> getSaleReportRoyConsoByCatForGraph(
			@RequestParam("frIdList") List<String> frIdList, @RequestParam("catIdList") List<String> catIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReportRoyalty> salesReportRoyaltyList = null;
		try {
			fromDate = Common.convertToYMD(fromDate);
			toDate = Common.convertToYMD(toDate);
			System.out.println("Input received for report 10 roy by category " + fromDate + "" + toDate + "" + frIdList
					+ "cat=" + catIdList);
			salesReportRoyaltyList = salesReportRoyaltyRepo.getSaleReportRoyConsoByCatForGraph(frIdList, catIdList,
					fromDate, toDate);
			System.out.println("getSaleReportBillwise for Graph r 10 " + salesReportRoyaltyList.toString());

		} catch (Exception e) {
			System.out.println(" Exce in sales Report Royalty  By Category For Graph  " + e.getMessage());
			e.printStackTrace();
		}
		return salesReportRoyaltyList;
	}

	// -------------------------------------------Monthly Sales Return Qty wise
	// Report----------------------------------------------
	@RequestMapping(value = { "/getSalesReturnQtyReport" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReturnQtyReportList> getSalesReturnQtyReport(@RequestParam("fromYear") int fromYear,
			@RequestParam("toYear") int toYear, @RequestParam("typeIdList") List<String> typeIdList) throws ParseException {

		List<SalesReturnQtyReportList> repList = new ArrayList<>();
		List<String> months = new ArrayList<String>();
		months.add(fromYear + "-04");
		months.add(fromYear + "-05");
		months.add(fromYear + "-06");
		months.add(fromYear + "-07");
		months.add(fromYear + "-08");
		months.add(fromYear + "-09");
		months.add(fromYear + "-10");
		months.add(fromYear + "-11");
		months.add(fromYear + "-12");
		months.add(toYear + "-01");
		months.add(toYear + "-02");
		months.add(toYear + "-03");

		for (int i = 0; i < months.size(); i++) {
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM");
			// output format: yyyy-MM-dd
			SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String month = formatter.format(parser.parse(months.get(i)));

			System.err.println("months" + months.get(i));
			SalesReturnQtyReportList salesReturnQtyReportList = new SalesReturnQtyReportList();
			salesReturnQtyReportList.setMonth(month);
			int listSize=typeIdList.size();
			List<Integer> itmList=new ArrayList<Integer>();
			System.err.println("type list"+typeIdList.toString());
			
			List<SalesReturnQtyDao> salesReturnQtyDao = new ArrayList<SalesReturnQtyDao>();
			
 			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReturnQtyDao  =salesReturnQtyDaoRepository.getSalesReturnQtyReportAll(months.get(i),
						itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReport12(months.get(i),
						itmList);
			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReportAll(months.get(i),
						itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				System.err.println(" 1 3");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReportAll(months.get(i),
						itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReport12(months.get(i),
						itmList);
				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReport12(months.get(i),
						itmList);
				System.err.println(" 2");

			} else   {
				System.err.println(" 3");

				salesReturnQtyDao = salesReturnQtyDaoRepository.getSalesReturnQtyReport3(months.get(i)
						);

			}   
			 
			salesReturnQtyReportList.setSalesReturnQtyDaoList(salesReturnQtyDao);
			repList.add(salesReturnQtyReportList);
		}
		return repList;

	}

	@RequestMapping(value = { "/getSalesReturnValueReport" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReturnValueDaoList> getSalesReturnValueReport(@RequestParam("fromYear") int fromYear,
			@RequestParam("toYear") int toYear,@RequestParam("typeIdList") List<String> typeIdList) throws ParseException {

		List<SalesReturnValueDaoList> repList = new ArrayList<>();
		List<String> months = new ArrayList<String>();
		months.add(fromYear + "-04");
		months.add(fromYear + "-05");
		months.add(fromYear + "-06");
		months.add(fromYear + "-07");
		months.add(fromYear + "-08");
		months.add(fromYear + "-09");
		months.add(fromYear + "-10");
		months.add(fromYear + "-11");
		months.add(fromYear + "-12");
		months.add(toYear + "-01");
		months.add(toYear + "-02");
		months.add(toYear + "-03");
		
		int listSize=typeIdList.size();
		List<Integer> itmList=new ArrayList<Integer>();
		System.err.println("type list"+typeIdList.toString());
		

		for (int i = 0; i < months.size(); i++) {
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM");
			// output format: yyyy-MM-dd
			SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String month = formatter.format(parser.parse(months.get(i)));
			SalesReturnValueDaoList salesReturnValueReportList = new SalesReturnValueDaoList();
			salesReturnValueReportList.setMonth(month);
			List<SalesReturnValueDao> salesReturnValueDao = new ArrayList<SalesReturnValueDao>();
			  
			if (typeIdList.contains("-1")
					|| (typeIdList.contains("1") && typeIdList.contains("2") && typeIdList.contains("3"))) {

				System.err.println("all");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReportAll(months.get(i),itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("2") && listSize==2) {

				System.err.println("1 2");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				itmList.add(1);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReport12(months.get(i),itmList);


			} else if (typeIdList.contains("2") && typeIdList.contains("3") &&  listSize==2) {
				System.err.println(" 2 3");
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReportAll(months.get(i),itmList);

			} else if (typeIdList.contains("1") && typeIdList.contains("3") && listSize==2) {
				System.err.println(" 1 3");
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReportAll(months.get(i),itmList);

			} else if (typeIdList.contains("1") &&  listSize==1 ) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(0);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReport12(months.get(i),itmList);

				System.err.println(" 1");

			} else if (typeIdList.contains("2") &&  listSize==1) {
				
				itmList=new ArrayList<Integer>();
				itmList.add(1);
				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReport12(months.get(i),itmList);

				System.err.println(" 2");

			} else   {
				System.err.println(" 3");

				  salesReturnValueDao = salesReturnValueDaoRepository.getSalesReturnValueReport3(months.get(i));


			}  
			 
			
			
			
			
			
			
			
			
			
			salesReturnValueReportList.setSalesReturnQtyValueList(salesReturnValueDao);
			repList.add(salesReturnValueReportList);
		}
		
		
		
		
		return repList;

	}

	@RequestMapping(value = { "/getSalesReturnValueItemReport" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReturnItemDaoList> getSalesReturnValueItemReport(
			@RequestParam("fromYear") int fromYear, @RequestParam("toYear") int toYear,
			@RequestParam("subCatId") List<Integer> subCatId) throws ParseException {

		List<SalesReturnItemDaoList> repList = new ArrayList<>();
		List<String> months = new ArrayList<String>();
		months.add(fromYear + "-04");
		months.add(fromYear + "-05");
		months.add(fromYear + "-06");
		months.add(fromYear + "-07");
		months.add(fromYear + "-08");
		months.add(fromYear + "-09");
		months.add(fromYear + "-10");
		months.add(fromYear + "-11");
		months.add(fromYear + "-12");
		months.add(toYear + "-01");
		months.add(toYear + "-02");
		months.add(toYear + "-03");

		for (int i = 0; i < months.size(); i++) {
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM");
			// output format: yyyy-MM-dd
			SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String month = formatter.format(parser.parse(months.get(i)));
			SalesReturnItemDaoList salesReturnItemDaoList = new SalesReturnItemDaoList();
			salesReturnItemDaoList.setMonth(month);
			List<SalesReturnValueItemDao> salesReturnValueDao = null;
	  
					salesReturnValueDao = salesReturnValueItemDaoRepo.getSalesReturnValueItemReport12(months.get(i),subCatId
							);
 
			salesReturnItemDaoList.setSalesReturnValueItemDao(salesReturnValueDao);
			repList.add(salesReturnItemDaoList);
			System.out.println(months.toString());
		}

		System.out.println("repListrepListrepListrepListrepListrepList" + repList.toString());
		return repList;

	}
	// ------------------------------------------------------------------------------------------------------------------------

}
