package com.ats.webapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Customer;
import com.ats.webapi.model.CustomerAmounts;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.ItemOrderList;
import com.ats.webapi.model.ItemResponse;
import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.advorder.AdvanceOrderDetail;
import com.ats.webapi.model.advorder.AdvanceOrderHeader;
import com.ats.webapi.model.bill.ItemListForCustomerBill;
import com.ats.webapi.model.rawmaterial.ItemSfHeader;
import com.ats.webapi.repo.CustomerRepo;
import com.ats.webapi.repo.ItemListForCustomerBillRepo;
import com.ats.webapi.repository.CustomerAmountsRepo;
import com.ats.webapi.repository.SellBillHeaderRepository;
import com.ats.webapi.repository.advorder.AdvanceOrderDetailRepo;
import com.ats.webapi.repository.advorder.AdvanceOrderHeaderRepo;
import com.ats.webapi.service.OrderService;

@RestController
public class AdvanceOrderApiController {

	@Autowired
	AdvanceOrderHeaderRepo advanceOrderHeaderRepo;

	@Autowired
	AdvanceOrderDetailRepo advanceOrderDetailRepo;

	@RequestMapping(value = { "/saveAdvanceOrderHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody AdvanceOrderHeader saveAdvanceOrderHeadAndDetail(@RequestBody AdvanceOrderHeader matHeader) {

		System.err.println("inside saveAdvanceOrderHeadAndDetail" + matHeader.toString());

		AdvanceOrderHeader header = new AdvanceOrderHeader();

		try {

			header = advanceOrderHeaderRepo.save(matHeader);

			for (int i = 0; i < header.getDetailList().size(); i++) {
				matHeader.getDetailList().get(i).setAdvHeaderId(header.getAdvHeaderId());

			}

			List<AdvanceOrderDetail> matDetailsList = advanceOrderDetailRepo.save(matHeader.getDetailList());
			header.setDetailList(matDetailsList);

		} catch (Exception e) {

			System.err.println("Exce in saveAdvanceOrderHeadAndDetail" + e.getMessage());

		}

		return header;

	}

	@Autowired
	CustomerRepo cust;

	@RequestMapping(value = { "/checkCustPhone" }, method = RequestMethod.POST)
	public @ResponseBody Info checkEmployeeEmail(@RequestParam String phoneNo) {

		Info info = new Info();
		Customer emp = new Customer();
		try {

			emp = cust.findByPhoneNumber(phoneNo);

			if (emp != null) {
				info.setError(false);
			} else {
				info.setError(true);

			}

		} catch (Exception e) {
			System.err.println("Exce in checkEmployeeEmail  " + e.getMessage());
		}

		return info;

	}

	@Autowired
	private OrderService orderService;

	// Search Advance Order History
	@RequestMapping("/advanceOrderHistoryDetail")
	public @ResponseBody ItemOrderList searchAdvOrderHistory(@RequestParam int headId, @RequestParam String deliveryDt,
			@RequestParam int frId) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(deliveryDt);
		java.sql.Date deliveryDate = new java.sql.Date(date.getTime());

		ItemOrderList orderList = orderService.searchAdvOrderHistory(headId, deliveryDate, frId);

		return orderList;

	}

	@RequestMapping("/advanceOrderHistoryHeader")
	public @ResponseBody List<AdvanceOrderHeader> advanceOrderHistoryHeader(@RequestParam int flag,@RequestParam String deliveryDt,
			@RequestParam int frId) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(deliveryDt);
		java.sql.Date deliveryDate = new java.sql.Date(date.getTime());
		List<AdvanceOrderHeader> orderList = new ArrayList<AdvanceOrderHeader>();
		System.out.println("flag is " + flag);
		try {
			
			if(flag==1) {
				orderList = advanceOrderHeaderRepo.findByDeliveryDateAndFrIdAndDelStatus(deliveryDate, frId, 0);

			}else {
				orderList = advanceOrderHeaderRepo.findByFrIdAndDelStatusAndIsSellBillGenerated(frId, 0,0);

			}

		} catch (Exception e) {
			System.out.println("Exc in advanceOrderHistoryHeader" + e.getMessage());
			e.printStackTrace();
		}

		return orderList;

	}

	@Autowired
	ItemListForCustomerBillRepo itemListForCustomerBillRepo;

	@RequestMapping("/getAdvanceOrderItemsByHeadId")
	public @ResponseBody List<ItemListForCustomerBill> getAdvanceOrderItemsByHeadId(@RequestParam int headId)
			throws ParseException {
		List<ItemListForCustomerBill> itm = null;
		System.err.println("data is"+headId);
		try {
			itm = itemListForCustomerBillRepo.getItem(headId);
			
			for(int i=0;i<itm.size();i++) {
				ItemListForCustomerBill temp=itm.get(i)	;
				
				float total=temp.getOrignalMrp()*temp.getQty();
				Float taxableAmt = (total* 100) / (100 + temp.getTaxPer());
				temp.setTaxAmt(total-taxableAmt);
				temp.setTaxableAmt(taxableAmt);
				temp.setTotal(total);
				
			}
			 
			
			System.err.println("data is"+itm.toString());

		} catch (Exception e) {
			System.out.println("Exc in advanceOrderHistoryHeader" + e.getMessage());
			e.printStackTrace();
		}

		return itm;

	}
	
	
	
	@RequestMapping(value = { "/updateAdvOrderHeadAndDetail" }, method = RequestMethod.POST)
	public @ResponseBody Info updateFrSettingBillNo(@RequestParam("advHeadId") int advHeadId
		) {

		Info info = new Info();
		int updateResponse = 0;
		int updateResponse1 = 0;
		try {

			 
			updateResponse = advanceOrderHeaderRepo.updateIsSellBillGen(advHeadId);
			
			
			if(updateResponse > 0) {
				updateResponse1 = advanceOrderDetailRepo.updateIsSellBillGen(advHeadId);
			}

			if (updateResponse > 0 && updateResponse1 > 0) {

				info.setError(false);
				info.setMessage("success Updating fr seting sell bill no ");

			} else {
				info.setError(true);
				info.setMessage("failure");
			}

		} catch (Exception e) {

			System.out.println(
					" /updateFrSettingBillNo Exce in Saving/Update fr Setting /FrSettingController " + e.getMessage());
			e.printStackTrace();
		}

		return info;

	}
	
	
	@Autowired
	CustomerAmountsRepo customerAmountsRepo;
	@RequestMapping("/getCustomerAmounts")
	public @ResponseBody CustomerAmounts getCustomerAmounts(@RequestParam int custId,@RequestParam int frId
		) throws ParseException {
		CustomerAmounts orderList=new CustomerAmounts();
		CustomerAmounts orderList1=new CustomerAmounts();
		CustomerAmounts orderList2=new CustomerAmounts();
		System.err.println("data is"+custId);

		orderList1 = customerAmountsRepo.findPendingAmt(custId,frId);
		orderList2 = customerAmountsRepo.findAadvAmt(custId,frId);
		
		
		orderList.setCreaditAmt(orderList1.getCreaditAmt());
		
		orderList.setAdvanceAmt(orderList2.getAdvanceAmt());
		orderList.setCustId(custId);
		return orderList;

	}
	
	@Autowired
	SellBillHeaderRepository sellBillHeaderRepository;
	
	@RequestMapping("/getSellBillByCustId")
	public @ResponseBody List<SellBillHeader> getSellBillByCustId(@RequestParam int custId,@RequestParam int frId)
			throws ParseException {
		List<SellBillHeader> itm = null;
 		try {
			itm = sellBillHeaderRepository.getSellBillHeader(custId,frId);
			 
			 
			
			System.err.println("data is"+itm.toString());

		} catch (Exception e) {
			System.out.println("Exc in advanceOrderHistoryHeader" + e.getMessage());
			e.printStackTrace();
		}

		return itm;

	}
	
	@RequestMapping("/advanceOrderHistoryHeaderByCustId")
	public @ResponseBody List<AdvanceOrderHeader> advanceOrderHistoryHeaderByCustId(@RequestParam int custId,
			@RequestParam int frId) throws ParseException {

	 
		List<AdvanceOrderHeader> orderList = new ArrayList<AdvanceOrderHeader>();
 		try {
		 
				orderList = advanceOrderHeaderRepo.findByCustIdAndIsSellBillGeneratedAndDelStatus(custId, 0, 0);
 

		} catch (Exception e) {
			System.out.println("Exc in advanceOrderHistoryHeader" + e.getMessage());
			e.printStackTrace();
		}

		return orderList;

	}


}
