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

import com.ats.webapi.model.Customer;
import com.ats.webapi.model.Info;
import com.ats.webapi.repo.CustomerRepo;

@RestController
public class PosMasterApiController {

	
	@Autowired
	CustomerRepo customerRepo;
	
	
	
	@RequestMapping(value = { "/saveCustomer" }, method = RequestMethod.POST)
	public @ResponseBody Customer saveCustomer(@RequestBody Customer service) {

		Customer serv = new Customer() ;

		try {
			serv = customerRepo.saveAndFlush(service);

		} catch (Exception e) {
			System.err.println("Exce in saving saveCustomer " + e.getMessage());
			e.printStackTrace();

		}
		return serv;
	}
	
	@RequestMapping(value = {"/getAllCustomers"}, method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAllCustomers() {
		List<Customer> servicsList = new ArrayList<Customer>();
		try {
			servicsList = customerRepo.findByDelStatusOrderByCustIdDesc(1);
		}catch(Exception e) {
			System.err.println("Exce in getAllServices " + e.getMessage());
		}
		return servicsList;
	}
	
 	 
	
	@RequestMapping(value = {"/getCustomerByCustId"}, method = RequestMethod.POST)
	public @ResponseBody Customer getCustomerByCustId(@RequestParam int custId) {
		Customer servc = new Customer() ;
		try {
			 servc = customerRepo.findByCustIdAndDelStatus(custId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return servc;
	}
	
	@RequestMapping(value = {"/getAllCustomerByFrId"}, method = RequestMethod.POST)
	public @ResponseBody List<Customer>  getAllCustomerByFrId(@RequestParam int frId) {
		List<Customer> servicsList = new ArrayList<Customer>();
		try {
			servicsList = customerRepo.findByFrIdAndDelStatus(frId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return servicsList;
	}
	
	@RequestMapping(value = { "/deleteService" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteService( @RequestParam int custId) {

		Info info = new Info();
		try
		{
			int res = customerRepo.deleteCustomer(custId);

			if (res > 0) {
				info.setError(false);
 
			} else {
				info.setError(true);
 
			}
		} catch (Exception e) {

			System.err.println("Exce in deleteService  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
 		}

		return info;

	}
	
}
