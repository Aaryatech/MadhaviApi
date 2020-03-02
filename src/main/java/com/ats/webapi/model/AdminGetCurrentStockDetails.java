package com.ats.webapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminGetCurrentStockDetails {

	@Id
	private String id;
	private int stockDetailId;
	private int frId;
	private int itemId;
	private String itemName;
	private float regOpeningStock;
	private float spOpeningStock;
	private float regTotalPurchase;
	private float spTotalPurchase;
	private float regTotalGrnGvn;
	private float regTotalSell;
	private int stockHeaderId;
	private float currentRegStock;
	private float currentSpStock;
	private float reOrderQty;
	private float sellCreditNote;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStockDetailId() {
		return stockDetailId;
	}

	public void setStockDetailId(int stockDetailId) {
		this.stockDetailId = stockDetailId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
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

	public float getSellCreditNote() {
		return sellCreditNote;
	}

	public void setSellCreditNote(float sellCreditNote) {
		this.sellCreditNote = sellCreditNote;
	}

	@Override
	public String toString() {
		return "AdminGetCurrentStockDetails [id=" + id + ", stockDetailId=" + stockDetailId + ", frId=" + frId
				+ ", itemId=" + itemId + ", itemName=" + itemName + ", regOpeningStock=" + regOpeningStock
				+ ", spOpeningStock=" + spOpeningStock + ", regTotalPurchase=" + regTotalPurchase + ", spTotalPurchase="
				+ spTotalPurchase + ", regTotalGrnGvn=" + regTotalGrnGvn + ", regTotalSell=" + regTotalSell
				+ ", stockHeaderId=" + stockHeaderId + ", currentRegStock=" + currentRegStock + ", currentSpStock="
				+ currentSpStock + ", reOrderQty=" + reOrderQty + ", sellCreditNote=" + sellCreditNote + "]";
	}

}
