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
import com.ats.webapi.model.posdashboard.PosDashCounts;
import com.ats.webapi.model.posdashboard.SellBillHeaderDashCounts;
import com.ats.webapi.repo.posdashboard.BillHeaderDashCountRepo;
import com.ats.webapi.repo.posdashboard.BillTransactionDetailDashCountRepo;
import com.ats.webapi.repo.posdashboard.CategorywiseItemSellRepo;
import com.ats.webapi.repo.posdashboard.CategorywiseSellRepo;
import com.ats.webapi.repo.posdashboard.SellBillHeaderDashCountsRepo;

@RestController
public class MadhviPosDashboardApiController {

	@Autowired
	SellBillHeaderDashCountsRepo sellBillHeaderDashCountsRepo;

	@Autowired
	BillTransactionDetailDashCountRepo billTransactionDetailDashCountRepo;

	@Autowired
	BillHeaderDashCountRepo billHeaderDashCountRepo;

	@RequestMapping(value = { "/getPosDashCounts" }, method = RequestMethod.POST)
	public @ResponseBody PosDashCounts getPosDashCounts(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId,@RequestParam("frRateCat") int frRateCat) {
		
		PosDashCounts crnReport = new PosDashCounts();

		SellBillHeaderDashCounts headcount = new SellBillHeaderDashCounts();
		BillTransactionDetailDashCount tranCount = new BillTransactionDetailDashCount();
		BillHeaderDashCount billCountch = new BillHeaderDashCount();
		BillHeaderDashCount billCountpur = new BillHeaderDashCount();
		
		System.err.println( "DashBoardReporApi data is " + fromDate+toDate+frId);
		try {
			headcount = sellBillHeaderDashCountsRepo.getDataFordash(fromDate, toDate, frId);
			tranCount = billTransactionDetailDashCountRepo.getD1ataFordash(fromDate, toDate, frId);
			billCountch = billHeaderDashCountRepo.getD1ataFordash2Ch(fromDate, toDate, frId);
			billCountpur = billHeaderDashCountRepo.getD1ataFordash2pur(fromDate, toDate, frId);
			
			//System.err.println( "DashBoardReporApi /headcount" + headcount.toString());
			
			//System.err.println( "DashBoardReporApi /tranCount" + tranCount.toString());
			//System.err.println( "DashBoardReporApi /billCountch" + billCountch.toString());
			//System.err.println( "DashBoardReporApi /billCountpur" + billCountpur.toString());

			crnReport.setAdvanceAmt(headcount.getAdvanceAmt());
			crnReport.setCardAmt(tranCount.getCardAmt());
			crnReport.setCashAmt(tranCount.getCashAmt());
			crnReport.setCreditAmt(headcount.getCreditAmt());
			crnReport.setDiscountAmt(headcount.getDiscAmt());
			crnReport.setEpayAmt(tranCount.getePayAmt());
			crnReport.setNoOfBillGenerated(headcount.getNoBillGen());
			crnReport.setSaleAmt(headcount.getSellAmt());

			crnReport.setExpenseAmt(billCountch.getChAmt());
			crnReport.setProfitAmt(headcount.getProfitAmt());
			crnReport.setPurchaseAmt(billCountpur.getPurchaeAmt());
			
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
