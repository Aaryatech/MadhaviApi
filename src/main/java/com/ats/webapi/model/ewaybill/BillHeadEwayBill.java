package com.ats.webapi.model.ewaybill;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class BillHeadEwayBill {
	
	
	@Id
	private int billNo;
	
	private String invoiceNo;
	
	private Date billDate;
	private int frId;
	
	private String frCode;
	
	private double taxableAmt;
	private double grandTotal;
	
	private double sgstSum;
	private double cgstSum;
	private double igstSum;
	
	@Transient
	List<EwayItemList> itemList;
			
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd/MM/yyyy")
	public Date getBillDate() {
		return billDate;
	}
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd/MM/yyyy")
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public String getFrCode() {
		return frCode;
	}
	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}
	public double getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public double getSgstSum() {
		return sgstSum;
	}
	public void setSgstSum(double sgstSum) {
		this.sgstSum = sgstSum;
	}
	public double getCgstSum() {
		return cgstSum;
	}
	public void setCgstSum(double cgstSum) {
		this.cgstSum = cgstSum;
	}
	public double getIgstSum() {
		return igstSum;
	}
	public void setIgstSum(double igstSum) {
		this.igstSum = igstSum;
	}
	
	public List<EwayItemList> getItemList() {
		return itemList;
	}
	public void setItemList(List<EwayItemList> itemList) {
		this.itemList = itemList;
	}
	
	@Override
	public String toString() {
		return "BillHeadEwayBill [billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frId="
				+ frId + ", frCode=" + frCode + ", taxableAmt=" + taxableAmt + ", grandTotal=" + grandTotal
				+ ", sgstSum=" + sgstSum + ", cgstSum=" + cgstSum + ", igstSum=" + igstSum + "]";
	}
	
	
	/*
	 * SELECT
	 * t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date,
	 * t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt,
	 * t_bill_header.grand_total,
	 * t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum FROM
	 * t_bill_header WHERE t_bill_header.bill_no IN(1)
	 */

}
