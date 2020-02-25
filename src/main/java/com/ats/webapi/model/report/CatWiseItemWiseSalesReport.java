package com.ats.webapi.model.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class CatWiseItemWiseSalesReport {

	@Id
	private int catId;
	private int frId;
	private String frName;
	private String catName;	
	private int sellQty; //qty;	
	private float sellAmt;  //amount;
	private int purchaseQty;
	private float purchaseAmt;
	private float grnQty;
	private float grnAmt;
	private float gvnQty;
	private float gvnAmt;
	private float crnQty;
	private float crnAmt;
	
	
	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getSellQty() {
		return sellQty;
	}

	public void setSellQty(int sellQty) {
		this.sellQty = sellQty;
	}

	public float getSellAmt() {
		return sellAmt;
	}

	public void setSellAmt(float sellAmt) {
		this.sellAmt = sellAmt;
	}

	public int getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(int purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public float getPurchaseAmt() {
		return purchaseAmt;
	}

	public void setPurchaseAmt(float purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}

	public float getGrnQty() {
		return grnQty;
	}

	public void setGrnQty(float grnQty) {
		this.grnQty = grnQty;
	}

	public float getGrnAmt() {
		return grnAmt;
	}

	public void setGrnAmt(float grnAmt) {
		this.grnAmt = grnAmt;
	}

	public float getGvnQty() {
		return gvnQty;
	}

	public void setGvnQty(float gvnQty) {
		this.gvnQty = gvnQty;
	}

	public float getGvnAmt() {
		return gvnAmt;
	}

	public void setGvnAmt(float gvnAmt) {
		this.gvnAmt = gvnAmt;
	}

	public float getCrnQty() {
		return crnQty;
	}

	public void setCrnQty(float crnQty) {
		this.crnQty = crnQty;
	}

	public float getCrnAmt() {
		return crnAmt;
	}

	public void setCrnAmt(float crnAmt) {
		this.crnAmt = crnAmt;
	}

	@Override
	public String toString() {
		return "CatWiseItemWiseSalesReport [catId=" + catId + ", frId=" + frId + ", frName=" + frName + ", catName="
				+ catName + ", sellQty=" + sellQty + ", sellAmt=" + sellAmt + ", purchaseQty=" + purchaseQty
				+ ", purchaseAmt=" + purchaseAmt + ", grnQty=" + grnQty + ", grnAmt=" + grnAmt + ", gvnQty=" + gvnQty
				+ ", gvnAmt=" + gvnAmt + ", crnQty=" + crnQty + ", crnAmt=" + crnAmt + "]";
	}

	

	
}
