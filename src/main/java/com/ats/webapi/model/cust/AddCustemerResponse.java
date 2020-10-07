package com.ats.webapi.model.cust;

import java.util.List;

import com.ats.webapi.model.Customer;

public class AddCustemerResponse {

	private int addCustomerId;
	private Customer cust;
	private List<Customer> customerList;
	private boolean error;
	private String msg;

	public int getAddCustomerId() {
		return addCustomerId;
	}

	public void setAddCustomerId(int addCustomerId) {
		this.addCustomerId = addCustomerId;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	@Override
	public String toString() {
		return "AddCustemerResponse [addCustomerId=" + addCustomerId + ", cust=" + cust + ", customerList="
				+ customerList + ", error=" + error + ", msg=" + msg + "]";
	}

}
