package com.ats.webapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "t_production_plan_detail")
public class PostProductionDetail {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="production_detail_id")
	private int productionDetailId;
	
	@Column(name="production_header_id")
	private int productionHeaderId;

	@Column(name="production_qty")
	private float productionQty;
	 
	@Column(name="item_id")
	private int itemId;

	@Column(name="production_date")
	private Date productionDate;

	@Column(name="production_batch")
	private String productionBatch;
	
	@Column(name="plan_qty")
	private float planQty;
	
	@Column(name="order_qty")
	private float orderQty;
	
	@Column(name="opening_qty")
	private float openingQty;
	
	@Column(name="rejected_qty")
	private float rejectedQty;
	
 
	
	
	public int getProductionDetailId() {
		return productionDetailId;
	}

	public void setProductionDetailId(int productionDetailId) {
		this.productionDetailId = productionDetailId;
	}

	public int getProductionHeaderId() {
		return productionHeaderId;
	}

	public void setProductionHeaderId(int productionHeaderId) {
		this.productionHeaderId = productionHeaderId;
	}

	

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	

	

	public float getProductionQty() {
		return productionQty;
	}

	public void setProductionQty(float productionQty) {
		this.productionQty = productionQty;
	}

	public float getPlanQty() {
		return planQty;
	}

	public void setPlanQty(float planQty) {
		this.planQty = planQty;
	}

	public float getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(float orderQty) {
		this.orderQty = orderQty;
	}

	public float getOpeningQty() {
		return openingQty;
	}

	public void setOpeningQty(float openingQty) {
		this.openingQty = openingQty;
	}

	public float getRejectedQty() {
		return rejectedQty;
	}

	public void setRejectedQty(float rejectedQty) {
		this.rejectedQty = rejectedQty;
	}

	@Override
	public String toString() {
		return "PostProductionDetail [productionDetailId=" + productionDetailId + ", productionHeaderId="
				+ productionHeaderId + ", productionQty=" + productionQty + ", itemId=" + itemId + ", productionDate="
				+ productionDate + ", productionBatch=" + productionBatch + ", planQty=" + planQty + ", orderQty="
				+ orderQty + ", openingQty=" + openingQty + ", rejectedQty=" + rejectedQty + "]";
	}

	 
	
	
}
