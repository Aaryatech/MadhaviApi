package com.ats.webapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CustListBtwenDate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int custId;
	private String custName;
	private String phoneNumber;
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "CustListBtwenDate [custId=" + custId + ", custName=" + custName + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
}
