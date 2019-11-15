package com.ats.webapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "m_fr_opening_stock_detail")
public class PostFrItemStockDetail {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="opening_stock_detail_id")
	private int openingStockDetailId;
	
	@Column(name="opening_stock_header_id")
	private int openingStockHeaderId;

	@Column(name="item_id")
	private int itemId;
	
	@Column(name="reg_opening_stock")
	private float regOpeningStock;
	
	@Column(name="sp_opening_stock")
	private float spOpeningStock;
	
	@Column(name="physical_stock")
	private float physicalStock;
	
	@Column(name="stock_difference")
	private float stockDifference;
	
	@Column(name="reg_total_purchase")
	private float regTotalPurchase;
	
	@Column(name="sp_total_purchase")
	private float spTotalPurchase;
	
	@Column(name="reg_total_grn_gvn")
	private float regTotalGrnGvn;
	
	@Column(name="reg_total_sell")
	private float regTotalSell;
	
	@Column(name="sp_total_sell")
	private float spTotalSell;
	
	@Column(name="remark")
	private String remark;

	@Transient
	@Column(name="item_name")
	private String itemName;
	@Transient
	@Column(name="item_code")
	private String itemCode;
	public int getOpeningStockDetailId() {
		return openingStockDetailId;
	}
	public void setOpeningStockDetailId(int openingStockDetailId) {
		this.openingStockDetailId = openingStockDetailId;
	}
	public int getOpeningStockHeaderId() {
		return openingStockHeaderId;
	}
	public void setOpeningStockHeaderId(int openingStockHeaderId) {
		this.openingStockHeaderId = openingStockHeaderId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public float getRegOpeningStock() {
		return regOpeningStock;
	}
	public void setRegOpeningStock(float regOpeningStock) {
		this.regOpeningStock = regOpeningStock;
	}
	public float getSpOpeningStock() {
		return spOpeningStock;
	}
	public void setSpOpeningStock(float spOpeningStock) {
		this.spOpeningStock = spOpeningStock;
	}
	public float getPhysicalStock() {
		return physicalStock;
	}
	public void setPhysicalStock(float physicalStock) {
		this.physicalStock = physicalStock;
	}
	public float getStockDifference() {
		return stockDifference;
	}
	public void setStockDifference(float stockDifference) {
		this.stockDifference = stockDifference;
	}
	public float getRegTotalPurchase() {
		return regTotalPurchase;
	}
	public void setRegTotalPurchase(float regTotalPurchase) {
		this.regTotalPurchase = regTotalPurchase;
	}
	public float getSpTotalPurchase() {
		return spTotalPurchase;
	}
	public void setSpTotalPurchase(float spTotalPurchase) {
		this.spTotalPurchase = spTotalPurchase;
	}
	public float getRegTotalGrnGvn() {
		return regTotalGrnGvn;
	}
	public void setRegTotalGrnGvn(float regTotalGrnGvn) {
		this.regTotalGrnGvn = regTotalGrnGvn;
	}
	public float getRegTotalSell() {
		return regTotalSell;
	}
	public void setRegTotalSell(float regTotalSell) {
		this.regTotalSell = regTotalSell;
	}
	public float getSpTotalSell() {
		return spTotalSell;
	}
	public void setSpTotalSell(float spTotalSell) {
		this.spTotalSell = spTotalSell;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	@Override
	public String toString() {
		return "PostFrItemStockDetail [openingStockDetailId=" + openingStockDetailId + ", openingStockHeaderId="
				+ openingStockHeaderId + ", itemId=" + itemId + ", regOpeningStock=" + regOpeningStock
				+ ", spOpeningStock=" + spOpeningStock + ", physicalStock=" + physicalStock + ", stockDifference="
				+ stockDifference + ", regTotalPurchase=" + regTotalPurchase + ", spTotalPurchase=" + spTotalPurchase
				+ ", regTotalGrnGvn=" + regTotalGrnGvn + ", regTotalSell=" + regTotalSell + ", spTotalSell="
				+ spTotalSell + ", remark=" + remark + ", itemName=" + itemName + ", itemCode=" + itemCode + "]";
	}
	
	
	
}
