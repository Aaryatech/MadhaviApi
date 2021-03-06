package com.ats.webapi.model.opssalesreport;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SubCatDateWiseData {

	@Id
	private int sellBillDetailNo;
	private int subCatId;
	private int frId;
	private float soldAmt;
	private float soldQty;
	private String subCatName;
	private int catId;
	private float taxableAmt;
	private float totalTax;
	private float discAmt;

	public int getSellBillDetailNo() {
		return sellBillDetailNo;
	}

	public void setSellBillDetailNo(int sellBillDetailNo) {
		this.sellBillDetailNo = sellBillDetailNo;
	}

	public int getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public float getSoldAmt() {
		return soldAmt;
	}

	public void setSoldAmt(float soldAmt) {
		this.soldAmt = soldAmt;
	}

	public float getSoldQty() {
		return soldQty;
	}

	public void setSoldQty(float soldQty) {
		this.soldQty = soldQty;
	}

	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}

	public float getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}

	@Override
	public String toString() {
		return "SubCatDateWiseData [sellBillDetailNo=" + sellBillDetailNo + ", subCatId=" + subCatId + ", frId=" + frId
				+ ", soldAmt=" + soldAmt + ", soldQty=" + soldQty + ", subCatName=" + subCatName + ", catId=" + catId
				+ ", taxableAmt=" + taxableAmt + ", totalTax=" + totalTax + ", discAmt=" + discAmt + "]";
	}


}
