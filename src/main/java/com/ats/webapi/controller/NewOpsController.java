package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.SMSUtility;
import com.ats.webapi.model.CategoryList;
import com.ats.webapi.model.Customer;
import com.ats.webapi.model.ErrorMessage;
import com.ats.webapi.model.FrConfig;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.Item;
import com.ats.webapi.model.ItemResponse;
import com.ats.webapi.model.MCategory;
import com.ats.webapi.model.SubCategory;
import com.ats.webapi.model.cust.AddCustemerResponse;
import com.ats.webapi.model.dashboard.DashOrderCount;
import com.ats.webapi.model.newsetting.NewSetting;
import com.ats.webapi.model.posdashboard.BillTransactionDetailDashCount;
import com.ats.webapi.repo.CustomerRepo;
import com.ats.webapi.repo.FrEmpMasterRepo;
import com.ats.webapi.repo.cloudkitchen.FrConfigRepo;
import com.ats.webapi.repo.posdashboard.BillTransactionDetailDashCountRepo;
import com.ats.webapi.repository.CategoryRepository;
import com.ats.webapi.repository.ItemRepository;
import com.ats.webapi.repository.NewSettingRepository;
import com.ats.webapi.repository.SubCategoryRepository;
import com.ats.webapi.repository.dashboard.DashOrderCountRepo;

@RestController
public class NewOpsController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	FrConfigRepo frConfigRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	DashOrderCountRepo dashOrderCountRepo;

	@Autowired
	FrEmpMasterRepo frEmpMasterRepo;

	@Autowired
	NewSettingRepository newSettingRepository;

	@Autowired
	CustomerRepo cust;

	@RequestMapping(value = { "/findAllOnlyCategoryPOS" }, method = RequestMethod.POST)
	public @ResponseBody CategoryList findAllOnlyCategoryPOS(@RequestParam int frId, @RequestParam int configType) {
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(0);
		List<MCategory> jsonCategoryResponse = categoryRepository.getAllCatByFr(frId, configType);
		CategoryList categoryList = new CategoryList();
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setError(false);
		errorMessage.setMessage("Success");
		categoryList.setErrorMessage(errorMessage);
		categoryList.setmCategoryList(jsonCategoryResponse);

		return categoryList;
	}

	@RequestMapping(value = { "/getSubCatListPOS" }, method = RequestMethod.POST)
	public @ResponseBody List<SubCategory> getSubCatListPOS(@RequestParam List<String> catId, @RequestParam int frId,
			@RequestParam int configType) {

		List<SubCategory> subCategoryList;
		try {
			subCategoryList = subCategoryRepository.getAllSubCatByCatAndFr(catId, frId, configType);
		} catch (Exception e) {
			subCategoryList = new ArrayList<>();
			e.printStackTrace();
		}
		return subCategoryList;

	}

	@RequestMapping(value = "/getItemsNameByIdWithOtherItemPOS", method = RequestMethod.POST)
	public @ResponseBody ItemResponse getItemsNameByIdWithOtherItemPOS(@RequestParam int frId,
			@RequestParam int configType) {

		ItemResponse itemResponse = new ItemResponse();
		ErrorMessage errorMessage = new ErrorMessage();
		List<Item> items = itemRepository.getAllItemByFrAndConfigType(frId, configType);
		if (items != null) {
			itemResponse.setItemList(items);
			errorMessage.setError(false);
			errorMessage.setMessage("Success");
		} else {
			errorMessage.setError(true);
			errorMessage.setMessage("No Items Found");
		}
		return itemResponse;

	}

	@RequestMapping(value = "/getItemsNameByIdWithOtherItemCateIdOrSubCatIdPOS", method = RequestMethod.POST)
	public @ResponseBody ItemResponse getItemsNameByIdWithOtherItemCateIdOrSubCatIdPOS(@RequestParam int frId,
			@RequestParam int configType, @RequestParam int searchBy, @RequestParam int catId) {

		ItemResponse itemResponse = new ItemResponse();
		ErrorMessage errorMessage = new ErrorMessage();
		List<Item> items = new ArrayList<>();

		if (searchBy == 1) {
			items = itemRepository.getAllItemByFrAndConfigTypeAndCat(frId, configType, catId);
		} else if (searchBy == 2) {
			items = itemRepository.getAllItemByFrAndConfigTypeAndSubCat(frId, configType, catId);
		}

		if (items != null) {
			itemResponse.setItemList(items);
			errorMessage.setError(false);
			errorMessage.setMessage("Success");
		} else {
			errorMessage.setError(true);
			errorMessage.setMessage("No Items Found");
		}
		return itemResponse;

	}

	@RequestMapping(value = "/getItemsByIdPOS", method = RequestMethod.POST)
	public @ResponseBody ItemResponse getItemListPOS(@RequestParam List<Integer> itemList, @RequestParam int frId,
			@RequestParam int configType) {

		ItemResponse itemResponse = new ItemResponse();
		ErrorMessage errorMessage = new ErrorMessage();
		List<Item> items = itemRepository.getItemByIdsAndFr(itemList, frId, configType);
		if (items != null) {
			itemResponse.setItemList(items);
			errorMessage.setError(false);
			errorMessage.setMessage("Success");
		} else {
			errorMessage.setError(true);
			errorMessage.setMessage("No Items Found");
		}
		return itemResponse;

	}

	@RequestMapping(value = { "/getFrConfigByFrId" }, method = RequestMethod.POST)
	public @ResponseBody FrConfig getFrConfigById(@RequestParam int frId) {

		FrConfig config = new FrConfig();
		try {
			config = frConfigRepo.findBydelStatusAndFrId(0, frId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	@RequestMapping(value = { "/saveCustomerPOS" }, method = RequestMethod.POST)
	public @ResponseBody Customer saveCustomerPOS(@RequestBody Customer service) {

		Customer serv = new Customer();

		int id = service.getCustId();

		try {
			serv = customerRepo.saveAndFlush(service);
			if (serv != null) {
				System.err.println("ID ============================== " + id);
				if (id == 0) {
					SMSUtility.sendAddCustomerSMS("91" + service.getPhoneNumber());
				}
			}

		} catch (Exception e) {
			System.err.println("Exce in saving saveCustomer " + e.getMessage());
			e.printStackTrace();
		}
		return serv;
	}

	@RequestMapping(value = { "/saveCustomerPOSNew" }, method = RequestMethod.POST)
	public @ResponseBody AddCustemerResponse saveCustomerPOSNew(@RequestBody Customer service) {

		AddCustemerResponse info = new AddCustemerResponse();
		
		System.err.println("IN saveCustomerPOSNew");

		List<Customer> emp = new ArrayList<Customer>();
		emp = cust.findByPhoneNumberAndDelStatus(service.getPhoneNumber(), 0);
		System.err.println("CHECKED MOBILE NO ---------");

		
		if (emp != null) {
			if (emp.size() == 0) {

				Customer serv = new Customer();
				int id = service.getCustId();

				try {
					serv = customerRepo.saveAndFlush(service);
					System.err.println("CUST SAVED ----------");

//					if (serv != null) {
//						if (id == 0) {
//							SMSUtility.sendAddCustomerSMS("91" + service.getPhoneNumber());
//							System.err.println("SMS API ------------");
//
//						}
//					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				info.setError(false);
				info.setAddCustomerId(serv.getCustId());
				info.setCust(serv);
				info.setMsg("Customer Added Successfully");
				
				//info.setCustomerList(customerRepo.findByDelStatusOrderByCustIdDesc(0));
				System.err.println("CUST LIST -----------");


			} else {
				info.setError(true);
				info.setAddCustomerId(0);
				info.setMsg("Mobile Number Already Exists!");

			}
		} else {
			info.setError(true);
			info.setAddCustomerId(0);
			info.setMsg("Unable to save!");
		}

		return info;
	}

	@RequestMapping(value = { "/getDashOrderCount" }, method = RequestMethod.POST)
	public @ResponseBody DashOrderCount getDashOrderCount(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam int frId) {

		DashOrderCount count = new DashOrderCount();

		try {
			count = dashOrderCountRepo.getOrderDashCount(fromDate, toDate, frId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@RequestMapping(value = { "/getOnlyPendingOrderCountByFr" }, method = RequestMethod.POST)
	public @ResponseBody DashOrderCount getOnlyPendingOrderCountByFr(@RequestParam int frId) {

		DashOrderCount count = new DashOrderCount();

		try {
			count = dashOrderCountRepo.getOnlyPendingCountByFr(frId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// ------UPDATE FR EMPLOYEE TOKEN------------------
	@RequestMapping(value = { "/updateFrEmpToken" }, method = RequestMethod.POST)
	public @ResponseBody Info updateFrEmpToken(@RequestParam int empId, @RequestParam String token) {

		Info info = new Info();

		try {
			int res = frEmpMasterRepo.updateEmpToken(empId, token);
			if (res > 0) {
				info.setError(false);
			} else {
				info.setError(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
		}
		return info;
	}

}
