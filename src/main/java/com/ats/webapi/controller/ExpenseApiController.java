package com.ats.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.bill.Expense;
import com.ats.webapi.repo.ExpenseRepo;

@RestController
public class ExpenseApiController {

	/// harsha
	// *****************************Expense***************************************

	@Autowired
	ExpenseRepo expenseRepo;

	@RequestMapping(value = { "/saveExpense" }, method = RequestMethod.POST)
	@ResponseBody
	public Expense saveExpense(@RequestBody Expense routeMaster) {

		Expense jsonResult = expenseRepo.save(routeMaster);

		return jsonResult;
	}

	@RequestMapping(value = "/getExpenseByFrId", method = RequestMethod.POST)
	public @ResponseBody List<Expense> getExpense(@RequestParam("frId") int frId, @RequestParam("type") int type,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		List<Expense> grnItemConfigList = null;

		try {

			if (fromDate == null || toDate==null  || type==0) {
				grnItemConfigList = expenseRepo.getExpenseList(frId);

			} else {
				grnItemConfigList = expenseRepo.getExpenseList(frId, type, fromDate, toDate);

			}

			System.out.println("grn Item getItemForManualGrn  Rest: " + grnItemConfigList.toString());

		} catch (Exception e) {

			System.out.println(
					"restApi Exce for Getting Man GRN Item Conf /getItemsForManGrnByFrAndBill" + e.getMessage());
			e.printStackTrace();
		}

		return grnItemConfigList;

	}

	@RequestMapping(value = "/getAllExpense", method = RequestMethod.POST)
	public @ResponseBody List<Expense> getAllExpense(@RequestParam("type") int type,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		List<Expense> grnItemConfigList = null;

		try {

			grnItemConfigList = expenseRepo.getAllExpenseList(type, fromDate, toDate);

			System.out.println("grn Item getItemForManualGrn  Rest: " + grnItemConfigList.toString());

		} catch (Exception e) {

			System.out.println(
					"restApi Exce for Getting Man GRN Item Conf /getItemsForManGrnByFrAndBill" + e.getMessage());
			e.printStackTrace();
		}

		return grnItemConfigList;

	}
	
	@RequestMapping(value = { "/deleteExpense" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteExpense(@RequestParam("expId")int expId)
	{
 		 
		Info info = new Info();
		try {
			 
 			int delete = expenseRepo.deleteExpense(expId);
			
			
			 if(delete==1)
			 {
				 info.setError(false);
				 info.setMessage("deleted Successfully ");
			 }
			 else
			 {
				 info.setError(true);
				 info.setMessage("deleted UnSuccessfully ");
			 }
			 
		} catch (Exception e) {

			e.printStackTrace();
		}
		return info;
           
	}
	
	
	@RequestMapping(value = "/getExpenseByExpId", method = RequestMethod.POST)
	public @ResponseBody Expense getExpenseByExpId( @RequestParam("expId") int expId) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		 Expense  grnItemConfigList = null;

		try {

			grnItemConfigList = expenseRepo.findByExpId(expId);


		} catch (Exception e) {

			System.out.println(
					"restApi Exce for Getting Man GRN Item Conf /getItemsForManGrnByFrAndBill" + e.getMessage());
			e.printStackTrace();
		}

		return grnItemConfigList;

	}
	
	

}
