package com.ats.webapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "m_fr_opening_stock_detail")
public class GetCurrentStockDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "opening_stock_detail_id")
	private int stockDetailId;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "reg_opening_stock")
	private float regOpeningStock;

	@Column(name = "sp_opening_stock")
	private float spOpeningStock;

	@Column(name = "reg_total_purchase")
	private float regTotalPurchase;

	@Column(name = "sp_total_purchase")
	private float spTotalPurchase;

	@Column(name = "reg_total_grn_gvn")
	private float regTotalGrnGvn;

	@Column(name = "reg_total_sell")
	private float regTotalSell;

	@Column(name = "sp_total_sell")
	private float spTotalSell;

	@Column(name = "opening_stock_header_id")
	private int stockHeaderId;

	@Transient
	private float currentRegStock;

	@Transient
	private float currentSpStock;

	@Transient
	private float reOrderQty;

	@Transient
	private int id;
	
	@Transient
	private float sellCreditNote;

	@PostLoad
	private void onLoad() {
		this.currentRegStock = (regOpeningStock + regTotalPurchase) - (regTotalGrnGvn + regTotalSell);

	}

	public int getStockDetailId() {
		return stockDetailId;
	}

	public void setStockDetailId(int stockDetailId) {
		this.stockDetailId = stockDetailId;
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

	public int getStockHeaderId() {
		return stockHeaderId;
	}

	public void setStockHeaderId(int stockHeaderId) {
		this.stockHeaderId = stockHeaderId;
	}

	public float getCurrentRegStock() {
		return currentRegStock;
	}

	public void setCurrentRegStock(float currentRegStock) {
		this.currentRegStock = currentRegStock;
	}

	public float getCurrentSpStock() {
		return currentSpStock;
	}

	public void setCurrentSpStock(float currentSpStock) {
		this.currentSpStock = currentSpStock;
	}

	public float getReOrderQty() {
		return reOrderQty;
	}

	public void setReOrderQty(float reOrderQty) {
		this.reOrderQty = reOrderQty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public float getSellCreditNote() {
		return sellCreditNote;
	}

	public void setSellCreditNote(float sellCreditNote) {
		this.sellCreditNote = sellCreditNote;
	}

	
	
	@Override
	public String toString() {
		return "GetCurrentStockDetails [stockDetailId=" + stockDetailId + ", itemId=" + itemId + ", itemName="
				+ itemName + ", regOpeningStock=" + regOpeningStock + ", spOpeningStock=" + spOpeningStock
				+ ", regTotalPurchase=" + regTotalPurchase + ", spTotalPurchase=" + spTotalPurchase
				+ ", regTotalGrnGvn=" + regTotalGrnGvn + ", regTotalSell=" + regTotalSell + ", spTotalSell="
				+ spTotalSell + ", stockHeaderId=" + stockHeaderId + ", currentRegStock=" + currentRegStock
				+ ", currentSpStock=" + currentSpStock + ", reOrderQty=" + reOrderQty + ", id=" + id
				+ ", sellCreditNote=" + sellCreditNote + "]";
	}

	

}
