package com.ats.webapi.model.bill;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class ItemBillQty {

	@Id
	private int id;
	private String itemId;
	private String itemName;
	private float billQty;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getBillQty() {
		return billQty;
	}
	public void setBillQty(float billQty) {
		this.billQty = billQty;
	}
	@Override
	public String toString() {
		return "ItemBillQty [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", billQty=" + billQty + "]";
	}
	
	
}
