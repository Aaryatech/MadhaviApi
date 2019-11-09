package com.ats.webapi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ItemOrderHis implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int orderId;   //advDetailId
	
	@Temporal(TemporalType.DATE)
	private Date orderDate;////orderDate
	
	
	private int frId;//frId
	
	
	private int orderType;////catId
	
	
	private int orderSubType;//subCatId
	

	private int refId; //itemId
	
	
	private String itemId;//itemId
	

	private int orderQty;//qty
	
	
	private double orderRate; //rate
	
	
	private double orderMrp; //mrp
	

	private int orderStatus; //0
	
	@Column(name="order_datetime")
	private String orderDatetime; //0
	
	@Temporal(TemporalType.DATE)
	
	private Date productionDate;//prodDate
	
	@Temporal(TemporalType.DATE)
	
	private Date deliveryDate;//deliveryDate
	
	
	private int isEdit;//0
	

	private float editQty;//0
	
	
	private int userId;//0
	

	private int isPositive;//discPer

	private int menuId; //menuId
	
	private String menuTitle;//menuTitle
	
	private String itemName;//itemName
	
	
	
 
	

	public int getOrderId() {
		return orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getFrId() {
		return frId;
	}

	public int getOrderType() {
		return orderType;
	}

	public int getOrderSubType() {
		return orderSubType;
	}

	public int getRefId() {
		return refId;
	}

	public String getItemId() {
		return itemId;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public double getOrderRate() {
		return orderRate;
	}

	public double getOrderMrp() {
		return orderMrp;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public String getOrderDatetime() {
		return orderDatetime;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public int getIsEdit() {
		return isEdit;
	}

	public float getEditQty() {
		return editQty;
	}

	public int getUserId() {
		return userId;
	}

	public int getIsPositive() {
		return isPositive;
	}

	public int getMenuId() {
		return menuId;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public String getItemName() {
		return itemName;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public void setOrderSubType(int orderSubType) {
		this.orderSubType = orderSubType;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public void setOrderRate(double orderRate) {
		this.orderRate = orderRate;
	}

	public void setOrderMrp(double orderMrp) {
		this.orderMrp = orderMrp;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}

	public void setEditQty(float editQty) {
		this.editQty = editQty;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setIsPositive(int isPositive) {
		this.isPositive = isPositive;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	
}
