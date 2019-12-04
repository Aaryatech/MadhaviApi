package com.ats.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.posdashboard.BillHeaderDashCount;
import com.ats.webapi.model.posdashboard.BillTransactionDetailDashCount;
import com.ats.webapi.model.posdashboard.PosDashCounts;
import com.ats.webapi.model.posdashboard.SellBillHeaderDashCounts;
import com.ats.webapi.repo.posdashboard.BillHeaderDashCountRepo;
import com.ats.webapi.repo.posdashboard.BillTransactionDetailDashCountRepo;
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
		BillHeaderDashCount billCount = new BillHeaderDashCount();
		try {
			headcount = sellBillHeaderDashCountsRepo.getDataFordash(fromDate, toDate, frId);
			tranCount = billTransactionDetailDashCountRepo.getD1ataFordash(fromDate, toDate, frId);
			billCount = billHeaderDashCountRepo.getD1ataFordash2(fromDate, toDate, frId);

			crnReport.setAdvanceAmt(headcount.getAdvanceAmt());
			crnReport.setCardAmt(tranCount.getCardAmt());
			crnReport.setCashAmt(tranCount.getCashAmt());
			crnReport.setCreditAmt(headcount.getCreditAmt());
			crnReport.setDiscountAmt(headcount.getDiscAmt());
			crnReport.setEpayAmt(tranCount.getePayAmt());
			crnReport.setNoOfBillGenerated(headcount.getNoBillGen());
			crnReport.setSaleAmt(headcount.getSellAmt());

			crnReport.setExpenseAmt(billCount.getChAmt());
			crnReport.setProfitAmt(headcount.getProfitAmt());
			crnReport.setPurchaseAmt(billCount.getPurchaeAmt());

		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}

}
