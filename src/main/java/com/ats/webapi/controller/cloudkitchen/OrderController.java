package com.ats.webapi.controller.cloudkitchen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.cloudkitchen.GetDeliveryBoyOrAgentData;
import com.ats.webapi.model.cloudkitchen.GetItemsForConfig;
import com.ats.webapi.model.cloudkitchen.GetOrderDetail;
import com.ats.webapi.model.cloudkitchen.GetOrderDetailDisplay;
import com.ats.webapi.model.cloudkitchen.GetOrderDisplay;
import com.ats.webapi.model.cloudkitchen.GetOrderHeaderDisplay;
import com.ats.webapi.repo.cloudkitchen.GetDeliveryBoyOrAgentDataRepo;
import com.ats.webapi.repo.cloudkitchen.GetItemsForConfigRepo;
import com.ats.webapi.repo.cloudkitchen.GetOrderDetailRepo;
import com.ats.webapi.repo.cloudkitchen.GetOrderDisplayRepo;
import com.ats.webapi.repo.cloudkitchen.GetOrdersRepo;

@RestController
public class OrderController {

	@Autowired
	GetItemsForConfigRepo getItemsForConfigRepo;

	@Autowired
	GetOrdersRepo getOrdersRepo;

	@Autowired
	GetOrderDetailRepo getOrderDetailRepo;

	@Autowired
	GetOrderDisplayRepo getOrderDisplayRepo;

	@Autowired
	GetDeliveryBoyOrAgentDataRepo getDeliveryBoyOrAgentDataRepo;

	@RequestMapping(value = { "/getItemsForConfigByFrId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetItemsForConfig> getItemsForConfigByFrId(@RequestParam("frId") int frId,
			@RequestParam("configType") int configType) {

		List<GetItemsForConfig> itemList = null;
		try {
			itemList = getItemsForConfigRepo.getItemsForConfigByFrId(frId, configType);
			if (itemList == null) {
				new ArrayList<GetItemsForConfig>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemList;
	}

	@RequestMapping(value = { "/getOrdersByFrAndDeliveryDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetOrderHeaderDisplay> getOrdersByFrAndDeliveryDate(@RequestParam("frId") int frId,
			@RequestParam("delDate") String delDate, @RequestParam("status") List<Integer> status) {

		List<GetOrderHeaderDisplay> orderList = new ArrayList<>();
		try {
			System.err.println("FR ID = " + frId + "      DEL DATE = " + delDate + "      STATUS = " + status);

			List<GetOrderDisplay> res = getOrderDisplayRepo.getAllOrdersByFrAndStatusAndDelDate(frId, delDate, status);

			if (res != null) {

				Set<Integer> setOrderIds = new HashSet<Integer>();
				for (GetOrderDisplay order : res) {
					setOrderIds.add(order.getOrderId());
				}

				List<Integer> orderIds = new ArrayList<>();
				orderIds.addAll(setOrderIds);

				if (orderIds.size() > 0) {

					// ----------HEADER LIST---------
					for (int i = 0; i < orderIds.size(); i++) {
						for (GetOrderDisplay order : res) {

							if (orderIds.get(i) == order.getOrderId()) {

								List<GetOrderDetailDisplay> detailList = new ArrayList<>();

								GetOrderHeaderDisplay header = new GetOrderHeaderDisplay(order.getOrderId(),
										order.getOrderNo(), order.getOrderDate().toString(), order.getFrId(),
										order.getCustId(), order.getStatus(), order.getTaxableAmt(), order.getCgstAmt(),
										order.getSgstAmt(), order.getIgstAmt(), order.getDiscAmt(),
										order.getItemDiscAmt(), order.getTaxAmt(), order.getTotalAmt(),
										order.getOrderStatus(), order.getPaidStatus(), order.getPaymentMethod(),
										order.getPaymentRemark(), order.getCityId(), order.getAreaId(),
										order.getAddressId(), order.getAddress(), order.getWhatsappNo(),
										order.getLandmark(), order.getDeliveryDate().toString(),
										order.getDeliveryTime(), order.getInsertDateTime().toString(),
										order.getInsertUserId(), order.getOrderPlatform(), order.getDelStatus(),
										order.getOfferId(), order.getRemark(), order.getOrderDeliveredBy(),
										order.getExInt1(), order.getExInt2(), order.getExInt3(), order.getExInt4(),
										order.getExVar1(), order.getExVar2(), order.getExVar3(), order.getExVar4(),
										order.getExFloat1(), order.getExFloat2(), order.getExFloat3(),
										order.getExFloat4(), order.getExDate1(), order.getExDate2(),
										order.getBillingName(), order.getBillingAddress(), order.getCustomerGstnNo(),
										order.getDeliveryType(), order.getDeliveryInstId(), order.getDeliveryInstText(),
										order.getDeliveryKm(), order.getCustName(), order.getCityName(),
										order.getAreaName(), order.getPincode(), order.getDeliveryCharges(),
										order.getPaymentSubMode(), order.getIsAgent(), order.getOrderDeliveredByName(),
										order.getUuidNo(), order.getCustPhone(), order.getCustWhatsApp(),
										order.getDeliveryDateDisplay(), order.getDeliveryTimeDisplay(),
										order.getTrailRemark(), detailList);

								orderList.add(header);

								break;

							}

						}
					}

					// ----------DETAIL LIST--------------
					for (int i = 0; i < orderList.size(); i++) {

						List<GetOrderDetailDisplay> detailList = new ArrayList<>();

						for (GetOrderDisplay order : res) {

							if (orderList.get(i).getOrderId() == order.getOrderId()) {

								GetOrderDetailDisplay detail = new GetOrderDetailDisplay(order.getOrderDetailId(),
										order.getOrderId(), order.getItemId(), order.getHsnCode(), order.getQty(),
										order.getMrp(), order.getRate(), order.getDetailTaxableAmt(),
										order.getCgstPer(), order.getSgstPer(), order.getIgstPer(),
										order.getDetailCgstAmt(), order.getDetailSgstAmt(), order.getDetailIgstAmt(),
										order.getDetailDiscAmt(), order.getDetailTaxAmt(), order.getDetailTotalAmt(),
										order.getDelStatus(), order.getDetailRemark(), order.getDetailExInt1(),
										order.getDetailExInt2(), order.getDetailExInt3(), order.getDetailExInt4(),
										order.getDetailExVar1(), order.getDetailExVar2(), order.getDetailExVar3(),
										order.getDetailExVar4(), order.getDetailExFloat1(), order.getDetailExFloat2(),
										order.getDetailExFloat3(), order.getDetailExFloat4(), order.getItemName(),
										order.getItemUom(), order.getUomId(), order.getCatId());
								detailList.add(detail);

							}

						}

						orderList.get(i).setOrderDetailList(detailList);
					}

				}

			}

			orderList.sort(new OrderSorter());

			// System.err.println("ORDERS = " + orderList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@RequestMapping(value = { "/getOrdersByFrAndStatus" }, method = RequestMethod.POST)
	public @ResponseBody List<GetOrderHeaderDisplay> getOrdersByFrAndStatus(@RequestParam("frId") int frId,
			@RequestParam("status") List<Integer> status) {

		List<GetOrderHeaderDisplay> orderList = new ArrayList<>();
		try {
			//System.err.println("FR ID = " + frId + "      STATUS = " + status);

			List<GetOrderDisplay> res = getOrderDisplayRepo.getAllOrdersByFrAndStatus(frId, status);

			if (res != null) {

				Set<Integer> setOrderIds = new HashSet<Integer>();
				for (GetOrderDisplay order : res) {
					setOrderIds.add(order.getOrderId());
				}

				List<Integer> orderIds = new ArrayList<>();
				orderIds.addAll(setOrderIds);

				if (orderIds.size() > 0) {

					// ----------HEADER LIST---------
					for (int i = 0; i < orderIds.size(); i++) {
						for (GetOrderDisplay order : res) {

							if (orderIds.get(i) == order.getOrderId()) {

								List<GetOrderDetailDisplay> detailList = new ArrayList<>();

								GetOrderHeaderDisplay header = new GetOrderHeaderDisplay(order.getOrderId(),
										order.getOrderNo(), order.getOrderDate().toString(), order.getFrId(),
										order.getCustId(), order.getStatus(), order.getTaxableAmt(), order.getCgstAmt(),
										order.getSgstAmt(), order.getIgstAmt(), order.getDiscAmt(),
										order.getItemDiscAmt(), order.getTaxAmt(), order.getTotalAmt(),
										order.getOrderStatus(), order.getPaidStatus(), order.getPaymentMethod(),
										order.getPaymentRemark(), order.getCityId(), order.getAreaId(),
										order.getAddressId(), order.getAddress(), order.getWhatsappNo(),
										order.getLandmark(), order.getDeliveryDate().toString(),
										order.getDeliveryTime(), order.getInsertDateTime().toString(),
										order.getInsertUserId(), order.getOrderPlatform(), order.getDelStatus(),
										order.getOfferId(), order.getRemark(), order.getOrderDeliveredBy(),
										order.getExInt1(), order.getExInt2(), order.getExInt3(), order.getExInt4(),
										order.getExVar1(), order.getExVar2(), order.getExVar3(), order.getExVar4(),
										order.getExFloat1(), order.getExFloat2(), order.getExFloat3(),
										order.getExFloat4(), order.getExDate1(), order.getExDate2(),
										order.getBillingName(), order.getBillingAddress(), order.getCustomerGstnNo(),
										order.getDeliveryType(), order.getDeliveryInstId(), order.getDeliveryInstText(),
										order.getDeliveryKm(), order.getCustName(), order.getCityName(),
										order.getAreaName(), order.getPincode(), order.getDeliveryCharges(),
										order.getPaymentSubMode(), order.getIsAgent(), order.getOrderDeliveredByName(),
										order.getUuidNo(), order.getCustPhone(), order.getCustWhatsApp(),
										order.getDeliveryDateDisplay(), order.getDeliveryTimeDisplay(),
										order.getTrailRemark(), detailList);

								orderList.add(header);

								break;

							}

						}
					}

					// ----------DETAIL LIST--------------
					for (int i = 0; i < orderList.size(); i++) {

						List<GetOrderDetailDisplay> detailList = new ArrayList<>();

						for (GetOrderDisplay order : res) {

							if (orderList.get(i).getOrderId() == order.getOrderId()) {
								
								if(order.getOrderId()==14){
									System.err.println("14 => "+order.toString());
								}

								GetOrderDetailDisplay detail = new GetOrderDetailDisplay(order.getOrderDetailId(),
										order.getOrderId(), order.getItemId(), order.getHsnCode(), order.getQty(),
										order.getMrp(), order.getRate(), order.getDetailTaxableAmt(),
										order.getCgstPer(), order.getSgstPer(), order.getIgstPer(),
										order.getDetailCgstAmt(), order.getDetailSgstAmt(), order.getDetailIgstAmt(),
										order.getDetailDiscAmt(), order.getDetailTaxAmt(), order.getDetailTotalAmt(),
										order.getDelStatus(), order.getDetailRemark(), order.getDetailExInt1(),
										order.getDetailExInt2(), order.getDetailExInt3(), order.getDetailExInt4(),
										order.getDetailExVar1(), order.getDetailExVar2(), order.getDetailExVar3(),
										order.getDetailExVar4(), order.getDetailExFloat1(), order.getDetailExFloat2(),
										order.getDetailExFloat3(), order.getDetailExFloat4(), order.getItemName(),
										order.getItemUom(), order.getUomId(), order.getCatId());
								detailList.add(detail);

							}

						}

						orderList.get(i).setOrderDetailList(detailList);
					}

				}

			}

			orderList.sort(new OrderSorterByDate());
			// System.err.println("ORDERS = " + orderList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@RequestMapping(value = { "/getOrdersByFrAndStatusAndDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetOrderHeaderDisplay> getOrdersByFrAndStatus(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId,
			@RequestParam("status") List<Integer> status) {

		List<GetOrderHeaderDisplay> orderList = new ArrayList<>();
		try {
			//System.err.println("FR ID = " + frId + "      STATUS = " + status);

			List<GetOrderDisplay> res = getOrderDisplayRepo.getAllOrdersByFrAndStatusAndDate(fromDate,toDate,frId, status);

			if (res != null) {

				Set<Integer> setOrderIds = new HashSet<Integer>();
				for (GetOrderDisplay order : res) {
					setOrderIds.add(order.getOrderId());
				}

				List<Integer> orderIds = new ArrayList<>();
				orderIds.addAll(setOrderIds);

				if (orderIds.size() > 0) {

					// ----------HEADER LIST---------
					for (int i = 0; i < orderIds.size(); i++) {
						for (GetOrderDisplay order : res) {

							if (orderIds.get(i) == order.getOrderId()) {

								List<GetOrderDetailDisplay> detailList = new ArrayList<>();

								GetOrderHeaderDisplay header = new GetOrderHeaderDisplay(order.getOrderId(),
										order.getOrderNo(), order.getOrderDate().toString(), order.getFrId(),
										order.getCustId(), order.getStatus(), order.getTaxableAmt(), order.getCgstAmt(),
										order.getSgstAmt(), order.getIgstAmt(), order.getDiscAmt(),
										order.getItemDiscAmt(), order.getTaxAmt(), order.getTotalAmt(),
										order.getOrderStatus(), order.getPaidStatus(), order.getPaymentMethod(),
										order.getPaymentRemark(), order.getCityId(), order.getAreaId(),
										order.getAddressId(), order.getAddress(), order.getWhatsappNo(),
										order.getLandmark(), order.getDeliveryDate().toString(),
										order.getDeliveryTime(), order.getInsertDateTime().toString(),
										order.getInsertUserId(), order.getOrderPlatform(), order.getDelStatus(),
										order.getOfferId(), order.getRemark(), order.getOrderDeliveredBy(),
										order.getExInt1(), order.getExInt2(), order.getExInt3(), order.getExInt4(),
										order.getExVar1(), order.getExVar2(), order.getExVar3(), order.getExVar4(),
										order.getExFloat1(), order.getExFloat2(), order.getExFloat3(),
										order.getExFloat4(), order.getExDate1(), order.getExDate2(),
										order.getBillingName(), order.getBillingAddress(), order.getCustomerGstnNo(),
										order.getDeliveryType(), order.getDeliveryInstId(), order.getDeliveryInstText(),
										order.getDeliveryKm(), order.getCustName(), order.getCityName(),
										order.getAreaName(), order.getPincode(), order.getDeliveryCharges(),
										order.getPaymentSubMode(), order.getIsAgent(), order.getOrderDeliveredByName(),
										order.getUuidNo(), order.getCustPhone(), order.getCustWhatsApp(),
										order.getDeliveryDateDisplay(), order.getDeliveryTimeDisplay(),
										order.getTrailRemark(), detailList);

								orderList.add(header);

								break;

							}

						}
					}

					// ----------DETAIL LIST--------------
					for (int i = 0; i < orderList.size(); i++) {

						List<GetOrderDetailDisplay> detailList = new ArrayList<>();

						for (GetOrderDisplay order : res) {

							if (orderList.get(i).getOrderId() == order.getOrderId()) {

								GetOrderDetailDisplay detail = new GetOrderDetailDisplay(order.getOrderDetailId(),
										order.getOrderId(), order.getItemId(), order.getHsnCode(), order.getQty(),
										order.getMrp(), order.getRate(), order.getDetailTaxableAmt(),
										order.getCgstPer(), order.getSgstPer(), order.getIgstPer(),
										order.getDetailCgstAmt(), order.getDetailSgstAmt(), order.getDetailIgstAmt(),
										order.getDetailDiscAmt(), order.getDetailTaxAmt(), order.getDetailTotalAmt(),
										order.getDelStatus(), order.getDetailRemark(), order.getDetailExInt1(),
										order.getDetailExInt2(), order.getDetailExInt3(), order.getDetailExInt4(),
										order.getDetailExVar1(), order.getDetailExVar2(), order.getDetailExVar3(),
										order.getDetailExVar4(), order.getDetailExFloat1(), order.getDetailExFloat2(),
										order.getDetailExFloat3(), order.getDetailExFloat4(), order.getItemName(),
										order.getItemUom(), order.getUomId(), order.getCatId());
								detailList.add(detail);

							}

						}

						orderList.get(i).setOrderDetailList(detailList);
					}

				}

			}

			orderList.sort(new OrderSorterByDate());
			// System.err.println("ORDERS = " + orderList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@RequestMapping(value = { "/getOrderDetailByOrderId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetOrderDetail> getOrderDetailByOrderId(@RequestParam("orderId") int orderId) {

		List<GetOrderDetail> orderList = null;
		try {
			orderList = getOrderDetailRepo.getOrderDetailByOrderId(orderId);
			if (orderList == null) {
				new ArrayList<GetOrderDetail>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public class OrderSorter implements Comparator<GetOrderHeaderDisplay> {
		@Override
		public int compare(GetOrderHeaderDisplay o1, GetOrderHeaderDisplay o2) {
			// return o2.getOrderStatus().compareTo(o1.getOrderStatus());
			return Integer.compare(o1.getOrderStatus(), o2.getOrderStatus());
		}
	}
	
	public class OrderSorterByDate implements Comparator<GetOrderHeaderDisplay> {
		@Override
		public int compare(GetOrderHeaderDisplay o1, GetOrderHeaderDisplay o2) {
			// return o2.getOrderStatus().compareTo(o1.getOrderStatus());
			return o1.getDeliveryDate().compareTo(o2.getDeliveryDate());
		}
	}

	// GET DELIVERY BOY AND AGENT LIST
	@RequestMapping(value = { "/getDelBoyAndAgentListByFrAndCity" }, method = RequestMethod.POST)
	public @ResponseBody List<GetDeliveryBoyOrAgentData> getDelBoyAndAgentListByFrAndCity(
			@RequestParam("frId") int frId, @RequestParam("custAddId") int custAddId,
			@RequestParam("isAgent") int isAgent) {

		List<GetDeliveryBoyOrAgentData> res = new ArrayList<>();
		try {
			System.err.println("FR ID = " + frId + "  CUST ADD ID =" + custAddId);

			if (isAgent == 0) {
				res = getDeliveryBoyOrAgentDataRepo.getDeliveryBoyListByFr(frId);
			} else {
				res = getDeliveryBoyOrAgentDataRepo.getAgentListByFr(frId, custAddId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	@RequestMapping(value = { "/getOrdersByDelBoyAndStatusAndDate" }, method = RequestMethod.POST)
	public @ResponseBody List<GetOrderHeaderDisplay> getOrdersByDelBoyAndStatusAndDate(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("empId") int empId,
			@RequestParam("status") List<Integer> status) {

		List<GetOrderHeaderDisplay> orderList = new ArrayList<>();
		try {
			//System.err.println("FR ID = " + frId + "      STATUS = " + status);

			List<GetOrderDisplay> res = getOrderDisplayRepo.getAllOrdersByDeliveryBoyAndStatusAndDate(fromDate,toDate,empId, status);

			if (res != null) {
					
				Set<Integer> setOrderIds = new HashSet<Integer>();
				for (GetOrderDisplay order : res) {
					setOrderIds.add(order.getOrderId());
				}

				List<Integer> orderIds = new ArrayList<>();
				orderIds.addAll(setOrderIds);

				if (orderIds.size() > 0) {

					// ----------HEADER LIST---------
					for (int i = 0; i < orderIds.size(); i++) {
						for (GetOrderDisplay order : res) {

							if (orderIds.get(i) == order.getOrderId()) {

								List<GetOrderDetailDisplay> detailList = new ArrayList<>();

								GetOrderHeaderDisplay header = new GetOrderHeaderDisplay(order.getOrderId(),
										order.getOrderNo(), order.getOrderDate().toString(), order.getFrId(),
										order.getCustId(), order.getStatus(), order.getTaxableAmt(), order.getCgstAmt(),
										order.getSgstAmt(), order.getIgstAmt(), order.getDiscAmt(),
										order.getItemDiscAmt(), order.getTaxAmt(), order.getTotalAmt(),
										order.getOrderStatus(), order.getPaidStatus(), order.getPaymentMethod(),
										order.getPaymentRemark(), order.getCityId(), order.getAreaId(),
										order.getAddressId(), order.getAddress(), order.getWhatsappNo(),
										order.getLandmark(), order.getDeliveryDate().toString(),
										order.getDeliveryTime(), order.getInsertDateTime().toString(),
										order.getInsertUserId(), order.getOrderPlatform(), order.getDelStatus(),
										order.getOfferId(), order.getRemark(), order.getOrderDeliveredBy(),
										order.getExInt1(), order.getExInt2(), order.getExInt3(), order.getExInt4(),
										order.getExVar1(), order.getExVar2(), order.getExVar3(), order.getExVar4(),
										order.getExFloat1(), order.getExFloat2(), order.getExFloat3(),
										order.getExFloat4(), order.getExDate1(), order.getExDate2(),
										order.getBillingName(), order.getBillingAddress(), order.getCustomerGstnNo(),
										order.getDeliveryType(), order.getDeliveryInstId(), order.getDeliveryInstText(),
										order.getDeliveryKm(), order.getCustName(), order.getCityName(),
										order.getAreaName(), order.getPincode(), order.getDeliveryCharges(),
										order.getPaymentSubMode(), order.getIsAgent(), order.getOrderDeliveredByName(),
										order.getUuidNo(), order.getCustPhone(), order.getCustWhatsApp(),
										order.getDeliveryDateDisplay(), order.getDeliveryTimeDisplay(),
										order.getTrailRemark(), detailList);

								orderList.add(header);

								break;

							}

						}
					}

					// ----------DETAIL LIST--------------
					for (int i = 0; i < orderList.size(); i++) {

						List<GetOrderDetailDisplay> detailList = new ArrayList<>();

						for (GetOrderDisplay order : res) {

							if (orderList.get(i).getOrderId() == order.getOrderId()) {

								GetOrderDetailDisplay detail = new GetOrderDetailDisplay(order.getOrderDetailId(),
										order.getOrderId(), order.getItemId(), order.getHsnCode(), order.getQty(),
										order.getMrp(), order.getRate(), order.getDetailTaxableAmt(),
										order.getCgstPer(), order.getSgstPer(), order.getIgstPer(),
										order.getDetailCgstAmt(), order.getDetailSgstAmt(), order.getDetailIgstAmt(),
										order.getDetailDiscAmt(), order.getDetailTaxAmt(), order.getDetailTotalAmt(),
										order.getDelStatus(), order.getDetailRemark(), order.getDetailExInt1(),
										order.getDetailExInt2(), order.getDetailExInt3(), order.getDetailExInt4(),
										order.getDetailExVar1(), order.getDetailExVar2(), order.getDetailExVar3(),
										order.getDetailExVar4(), order.getDetailExFloat1(), order.getDetailExFloat2(),
										order.getDetailExFloat3(), order.getDetailExFloat4(), order.getItemName(),
										order.getItemUom(), order.getUomId(), order.getCatId());
								detailList.add(detail);

							}

						}

						orderList.get(i).setOrderDetailList(detailList);
					}

				}

			}

			orderList.sort(new OrderSorterByDate());
			// System.err.println("ORDERS = " + orderList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}
	

}
