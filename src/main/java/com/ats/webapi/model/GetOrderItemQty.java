package com.ats.webapi.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class GetOrderItemQty implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	int orderId;

	private float qty;
	private float advQty;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "menu_id")
	private int menuId;

	@Column(name = "production_date")
	private Date productionDate;

	@Column(name = "item_grp1")
	private int itemGrp1;

	@Column(name = "item_name")
	private String itemName;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getAdvQty() {
		return advQty;
	}

	public void setAdvQty(float advQty) {
		this.advQty = advQty;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public int getItemGrp1() {
		return itemGrp1;
	}

	public void setItemGrp1(int itemGrp1) {
		this.itemGrp1 = itemGrp1;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "GetOrderItemQty [orderId=" + orderId + ", qty=" + qty + ", advQty=" + advQty + ", itemId=" + itemId
				+ ", menuId=" + menuId + ", productionDate=" + productionDate + ", itemGrp1=" + itemGrp1 + ", itemName="
				+ itemName + "]";
	}

}

	 
	 
	
	

	
	
	
	

