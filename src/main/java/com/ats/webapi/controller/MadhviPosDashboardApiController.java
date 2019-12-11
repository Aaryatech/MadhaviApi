package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.posdashboard.BillHeaderDashCount;
import com.ats.webapi.model.posdashboard.BillTransactionDetailDashCount;
import com.ats.webapi.model.posdashboard.CategorywiseItemSell;
import com.ats.webapi.model.posdashboard.CategorywiseSell;
import com.ats.webapi.model.posdashboard.CreaditAmtDash;
import com.ats.webapi.model.posdashboard.DatewiseSellGraph;
import com.ats.webapi.model.posdashboard.PosDashCounts;
import com.ats.webapi.model.posdashboard.SellBillHeaderDashCounts;
import com.ats.webapi.repo.posdashboard.BillHeaderDashCountRepo;
import com.ats.webapi.repo.posdashboard.BillTransactionDetailDashCountRepo;
import com.ats.webapi.repo.posdashboard.CategorywiseItemSellRepo;
import com.ats.webapi.repo.posdashboard.CategorywiseSellRepo;
import com.ats.webapi.repo.posdashboard.CreaditAmtDashRepo;
import com.ats.webapi.repo.posdashboard.DatewiseSellGraphRepo;
import com.ats.webapi.repo.posdashboard.SellBillHeaderDashCountsRepo;

@RestController
public class MadhviPosDashboardApiController {

	@Autowired
	SellBillHeaderDashCountsRepo sellBillHeaderDashCountsRepo;

	@Autowired
	BillTransactionDetailDashCountRepo billTransactionDetailDashCountRepo;

	@Autowired
	BillHeaderDashCountRepo billHeaderDashCountRepo;
	
	
	@Autowired
	CreaditAmtDashRepo creaditAmtDashRepo;

	@RequestMapping(value = { "/getPosDashCounts" }, method = RequestMethod.POST)
	public @ResponseBody PosDashCounts getPosDashCounts(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId,@RequestParam("frRateCat") int frRateCat) {
		
		PosDashCounts crnReport = new PosDashCounts();

		SellBillHeaderDashCounts headcount = new SellBillHeaderDashCounts();
		BillTransactionDetailDashCount tranCount = new BillTransactionDetailDashCount();
		BillHeaderDashCount billCountch = new BillHeaderDashCount();
		BillHeaderDashCount billCountpur = new BillHeaderDashCount();
		CreaditAmtDash daseqe=new CreaditAmtDash();
		System.err.println( "DashBoardReporApi data is " + fromDate+toDate+frId);
		try {
			headcount = sellBillHeaderDashCountsRepo.getDataFordash(fromDate, toDate, frId);
			tranCount = billTransactionDetailDashCountRepo.getD1ataFordash(fromDate, toDate, frId);
			billCountch = billHeaderDashCountRepo.getD1ataFordash2Ch(fromDate, toDate, frId);
			billCountpur = billHeaderDashCountRepo.getD1ataFordash2pur(fromDate, toDate, frId);
			daseqe=creaditAmtDashRepo.getDataFordash(fromDate, toDate, frId);
			//System.err.println( "DashBoardReporApi /headcount" + headcount.toString());
			
			//System.err.println( "DashBoardReporApi /tranCount" + tranCount.toString());
			//System.err.println( "DashBoardReporApi /billCountch" + billCountch.toString());
			//System.err.println( "DashBoardReporApi /billCountpur" + billCountpur.toString());

			crnReport.setAdvanceAmt(headcount.getAdvanceAmt());
			
			if(tranCount.getCardAmt()=="" || tranCount.getCardAmt()==null) {
				crnReport.setCardAmt(0);
			}else {
				crnReport.setCardAmt(Float.parseFloat(tranCount.getCardAmt()));
			}
			if(tranCount.getCashAmt()=="" || tranCount.getCashAmt()==null) {
				crnReport.setCashAmt(0);
			}else {
 				crnReport.setCashAmt(Float.parseFloat(tranCount.getCashAmt()));
			}
			
			if(tranCount.getePayAmt()=="" || tranCount.getePayAmt()==null) {
				crnReport.setEpayAmt(0);
			}else {
 				crnReport.setEpayAmt(Float.parseFloat(tranCount.getePayAmt()));
			}
			
		
			crnReport.setCreditAmt(daseqe.getCreditAmt());
			crnReport.setDiscountAmt(headcount.getDiscAmt());
			
			crnReport.setNoOfBillGenerated(headcount.getNoBillGen());
			crnReport.setSaleAmt(headcount.getSellAmt());

			
			crnReport.setProfitAmt(headcount.getProfitAmt());
			
			
			if(billCountpur.getPurchaeAmt()=="" || billCountpur.getPurchaeAmt()==null ||billCountpur.getPurchaeAmt()=="0") {
				crnReport.setPurchaseAmt(0);
			}else {
 				crnReport.setPurchaseAmt(Float.parseFloat(billCountpur.getPurchaeAmt()));
			}
			
			if(billCountch.getChAmt()=="" || billCountch.getChAmt()==null || billCountch.getChAmt()=="0") {
				crnReport.setPurchaseAmt(0);
			}else {
 				crnReport.setExpenseAmt(Float.parseFloat(billCountch.getChAmt()));
			}
			
			 
			
			System.err.println( "DashBoardReporApi /getCredNoteReport" + crnReport.toString());

		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}
	
	@Autowired
	CategorywiseSellRepo categorywiseSellRepo;
	
	
	@RequestMapping(value = { "/getCatwiseSell" }, method = RequestMethod.POST)
	public @ResponseBody List<CategorywiseSell> getCatwiseSell(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId) {
		
		List<CategorywiseSell> crnReport = new  ArrayList<CategorywiseSell>();

 		 
		try {
			
			crnReport = categorywiseSellRepo.getCategorywiseSell(fromDate, toDate, frId);
			 
		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}
	
	@Autowired
	DatewiseSellGraphRepo datewiseSellGraphRepo;
	@RequestMapping(value = { "/getDatewiseSell" }, method = RequestMethod.POST)
	public @ResponseBody List<DatewiseSellGraph> getDatewiseSell(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId) {
		
		List<DatewiseSellGraph> crnReport = new  ArrayList<DatewiseSellGraph>();

 		 
		try {
			
			crnReport = datewiseSellGraphRepo.getD1ataFordashBarChart(fromDate, toDate, frId);
			 
		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}
	
	@Autowired
	CategorywiseItemSellRepo categorywiseItemSellRepo;
	
	
	@RequestMapping(value = { "/getCatwiseItemSell" }, method = RequestMethod.POST)
	public @ResponseBody List<CategorywiseItemSell> getCatwiseItemSell(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId,@RequestParam("catId") int catId,@RequestParam("flag") int flag) {
		
		List<CategorywiseItemSell> crnReport = new ArrayList<CategorywiseItemSell>();

 		 
		try {
			if(flag==1) {
				crnReport = categorywiseItemSellRepo.getCategorywiseItemSellDesc(fromDate, toDate, frId,catId);
			}else if(flag==2) {
				crnReport = categorywiseItemSellRepo.getCategorywiseItemSellAsc(fromDate, toDate, frId,catId);
			}else {
				crnReport = categorywiseItemSellRepo.getCategorywiseItemSellAll(fromDate, toDate, frId,catId);
			}
			
			 
		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}

}
