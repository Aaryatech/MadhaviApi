package com.ats.webapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class BookedOrderMailed {
@Id
	private int orderId;
	private Date orderDate;
	private Date deliveryDate;
	private double orderQty;
	private double orderRate;
	private double orderMrp;
	private String frName;
	private String frCode;
	private String itemName;
	private String itemUom;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public double getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}
	public double getOrderRate() {
		return orderRate;
	}
	public void setOrderRate(double orderRate) {
		this.orderRate = orderRate;
	}
	public double getOrderMrp() {
		return orderMrp;
	}
	public void setOrderMrp(double orderMrp) {
		this.orderMrp = orderMrp;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public String getFrCode() {
		return frCode;
	}
	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUom() {
		return itemUom;
	}
	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}
	@Override
	public String toString() {
		return "BookedOrderMailed [orderId=" + orderId + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate
				+ ", orderQty=" + orderQty + ", orderRate=" + orderRate + ", orderMrp=" + orderMrp + ", frName="
				+ frName + ", frCode=" + frCode + ", itemName=" + itemName + ", itemUom=" + itemUom + "]";
	}
	
	
}
