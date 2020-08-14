package com.ats.webapi.model.cloudkitchen;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetDeliveryBoyOrAgentData {

	@Id
	private String uId;
	private int id;
	private String name;
	private String mobile;
	private int frId;
	private String cityIds;
	private int isAgent;

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public int getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(int isAgent) {
		this.isAgent = isAgent;
	}
	
	

	@Override
	public String toString() {
		return "GetDeliveryBoyOrAgentData [uId=" + uId + ", id=" + id + ", name=" + name + ", mobile=" + mobile
				+ ", frId=" + frId + ", cityIds=" + cityIds + ", isAgent=" + isAgent + "]";
	}

}
