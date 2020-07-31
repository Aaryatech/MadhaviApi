package com.ats.webapi.model.cloudkitchen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tn_order_header")
public class OrderHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "order_date")
	private String orderDate;

	@Column(name = "fr_id")
	private int frId;

	@Column(name = "cust_id")
	private int custId;

	@Column(name = "status")
	private int status;

	@Column(name = "taxable_amt")
	private float taxableAmt;

	@Column(name = "cgst_amt")
	private float cgstAmt;

	@Column(name = "sgst_amt")
	private float sgstAmt;

	@Column(name = "igst_amt")
	private float igstAmt;

	@Column(name = "disc_amt")
	private float discAmt;

	@Column(name = "item_disc_amt")
	private float itemDiscAmt;

	@Column(name = "tax_amt")
	private float taxAmt;

	@Column(name = "total_amt")
	private float totalAmt;

	@Column(name = "order_status")
	private int orderStatus;

	@Column(name = "paid_status")
	private int paidStatus;

	@Column(name = "payment_method")
	private int paymentMethod;

	@Column(name = "payment_remark")
	private String paymentRemark;

	@Column(name = "city_id")
	private int cityId;

	@Column(name = "area_id")
	private int areaId;

	@Column(name = "address_id")
	private int addressId;

	@Column(name = "address")
	private String address;

	@Column(name = "whatsapp_no")
	private String whatsappNo;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "delivery_date")
	private String deliveryDate;

	@Column(name = "delivery_time")
	private String deliveryTime;

	@Column(name = "insert_date_time")
	private String insertDateTime;

	@Column(name = "insert_user_id")
	private int insertUserId;

	@Column(name = "order_platform")
	private int orderPlatform;

	@Column(name = "del_status")
	private int delStatus;

	@Column(name = "offer_id")
	private int offerId;

	@Column(name = "remark")
	private String remark;

	@Column(name = "order_delivered_by")
	private int orderDeliveredBy;

	@Column(name = "ex_int1")
	private int exInt1;

	@Column(name = "ex_int2")
	private int exInt2;

	@Column(name = "ex_int3")
	private int exInt3;

	@Column(name = "ex_int4")
	private int exInt4;

	@Column(name = "ex_var1")
	private String exVar1;

	@Column(name = "ex_var2")
	private String exVar2;

	@Column(name = "ex_var3")
	private String exVar3;

	@Column(name = "ex_var4")
	private String exVar4;

	@Column(name = "ex_float1")
	private float exFloat1;

	@Column(name = "ex_float2")
	private float exFloat2;

	@Column(name = "ex_float3")
	private float exFloat3;

	@Column(name = "ex_float4")
	private float exFloat4;

	@Column(name = "ex_date1")
	private String exDate1;

	@Column(name = "ex_date2")
	private String exDate2;

	@Column(name = "billing_name")
	private String billingName;

	@Column(name = "billing_address")
	private String billingAddress;

	@Column(name = "customer_gstn_no")
	private String customerGstnNo;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getCgstAmt() {
		return cgstAmt;
	}

	public void setCgstAmt(float cgstAmt) {
		this.cgstAmt = cgstAmt;
	}

	public float getSgstAmt() {
		return sgstAmt;
	}

	public void setSgstAmt(float sgstAmt) {
		this.sgstAmt = sgstAmt;
	}

	public float getIgstAmt() {
		return igstAmt;
	}

	public void setIgstAmt(float igstAmt) {
		this.igstAmt = igstAmt;
	}

	public float getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}

	public float getItemDiscAmt() {
		return itemDiscAmt;
	}

	public void setItemDiscAmt(float itemDiscAmt) {
		this.itemDiscAmt = itemDiscAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(int paidStatus) {
		this.paidStatus = paidStatus;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentRemark() {
		return paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWhatsappNo() {
		return whatsappNo;
	}

	public void setWhatsappNo(String whatsappNo) {
		this.whatsappNo = whatsappNo;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(String insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public int getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}

	public int getOrderPlatform() {
		return orderPlatform;
	}

	public void setOrderPlatform(int orderPlatform) {
		this.orderPlatform = orderPlatform;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrderDeliveredBy() {
		return orderDeliveredBy;
	}

	public void setOrderDeliveredBy(int orderDeliveredBy) {
		this.orderDeliveredBy = orderDeliveredBy;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public int getExInt4() {
		return exInt4;
	}

	public void setExInt4(int exInt4) {
		this.exInt4 = exInt4;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public String getExVar4() {
		return exVar4;
	}

	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public float getExFloat3() {
		return exFloat3;
	}

	public void setExFloat3(float exFloat3) {
		this.exFloat3 = exFloat3;
	}

	public float getExFloat4() {
		return exFloat4;
	}

	public void setExFloat4(float exFloat4) {
		this.exFloat4 = exFloat4;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public String getExDate2() {
		return exDate2;
	}

	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
	}

	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCustomerGstnNo() {
		return customerGstnNo;
	}

	public void setCustomerGstnNo(String customerGstnNo) {
		this.customerGstnNo = customerGstnNo;
	}

	@Override
	public String toString() {
		return "OrderHeader [orderId=" + orderId + ", orderNo=" + orderNo + ", orderDate=" + orderDate + ", frId="
				+ frId + ", custId=" + custId + ", status=" + status + ", taxableAmt=" + taxableAmt + ", cgstAmt="
				+ cgstAmt + ", sgstAmt=" + sgstAmt + ", igstAmt=" + igstAmt + ", discAmt=" + discAmt + ", itemDiscAmt="
				+ itemDiscAmt + ", taxAmt=" + taxAmt + ", totalAmt=" + totalAmt + ", orderStatus=" + orderStatus
				+ ", paidStatus=" + paidStatus + ", paymentMethod=" + paymentMethod + ", paymentRemark=" + paymentRemark
				+ ", cityId=" + cityId + ", areaId=" + areaId + ", addressId=" + addressId + ", address=" + address
				+ ", whatsappNo=" + whatsappNo + ", landmark=" + landmark + ", deliveryDate=" + deliveryDate
				+ ", deliveryTime=" + deliveryTime + ", insertDateTime=" + insertDateTime + ", insertUserId="
				+ insertUserId + ", orderPlatform=" + orderPlatform + ", delStatus=" + delStatus + ", offerId="
				+ offerId + ", remark=" + remark + ", orderDeliveredBy=" + orderDeliveredBy + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exInt4=" + exInt4 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exVar4=" + exVar4 + ", exFloat1=" + exFloat1
				+ ", exFloat2=" + exFloat2 + ", exFloat3=" + exFloat3 + ", exFloat4=" + exFloat4 + ", exDate1="
				+ exDate1 + ", exDate2=" + exDate2 + ", billingName=" + billingName + ", billingAddress="
				+ billingAddress + ", customerGstnNo=" + customerGstnNo + "]";
	}

}
