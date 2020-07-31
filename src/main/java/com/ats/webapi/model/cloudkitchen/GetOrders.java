package com.ats.webapi.model.cloudkitchen;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetOrders {

	@Id
	private int orderId;

	private String orderNo;
	
	private Date orderDate;
	private int frId;
	private int custId;
	private String custName;
	private int status;
	private int orderStatus;
	private int orderPlatform;
	private Date deliveryDate;
	private String deliveryTime;
	private String whatsappNo;
	private float totalAmt;
	private float km;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getOrderDate() {
		return orderDate;
	}

	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderPlatform() {
		return orderPlatform;
	}

	public void setOrderPlatform(int orderPlatform) {
		this.orderPlatform = orderPlatform;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getWhatsappNo() {
		return whatsappNo;
	}

	public void setWhatsappNo(String whatsappNo) {
		this.whatsappNo = whatsappNo;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	@Override
	public String toString() {
		return "GetOrders [orderId=" + orderId + ", orderNo=" + orderNo + ", orderDate=" + orderDate + ", frId=" + frId
				+ ", custId=" + custId + ", custName=" + custName + ", status=" + status + ", orderStatus="
				+ orderStatus + ", orderPlatform=" + orderPlatform + ", deliveryDate=" + deliveryDate
				+ ", deliveryTime=" + deliveryTime + ", whatsappNo=" + whatsappNo + ", totalAmt=" + totalAmt + ", km="
				+ km + "]";
	}

}
