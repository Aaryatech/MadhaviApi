package com.ats.webapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "m_item")
public class Item implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="item_id")
	private String itemId;
	
	@Column(name="item_name")
	private String itemName;

	@Column(name="item_grp1")
	private String itemGrp1;
	
	@Column(name="item_grp2")
	private String itemGrp2;
	
	@Column(name="item_grp3")
	private String itemGrp3;
	
	@Column(name="item_rate1")
	private float itemRate1;
	
	@Column(name="item_rate2")
	private float itemRate2;
	
	@Column(name="item_mrp1")
	private float itemMrp1;
	
	@Column(name="item_mrp2")
	private float itemMrp2;
	
	@Column(name="item_mrp3")
	private float itemMrp3;
	
	@Column(name="item_image")
	private String itemImage;
	
	@Column(name="item_tax1")
	private float itemTax1;
	
	@Column(name="item_tax2")
	private float itemTax2;
	
	@Column(name="item_tax3")
	private float itemTax3;
	
	@Column(name="item_is_used")
	private int itemIsUsed;
	
	@Column(name="item_sort_id")
	private float itemSortId;
	
	@Column(name="grn_two")
	private int grnTwo;
	
	@Column(name="del_status")
	private int delStatus;
		
	@Column(name="item_rate3")
	private float itemRate3;
	
	@Column(name="min_qty")
	private int minQty;
	
	@Column(name="item_shelf_life")
	private int shelfLife;
	
	@Column(name="is_saleable")
	private int isSaleable;
	
	@Column(name="is_stockable")
	private int isStockable;
	
	@Column(name="is_fact_or_fr")
	private int isFactOrFr;
	
	@Column(name="ext_int1")
	private int extInt1;
	
	@Column(name="ext_int2")
	private int extInt2;
	
	@Column(name="ext_float1")
	private float extFloat1;
	
	@Column(name="ext_float2")
	private float extFloat2;
	
	@Column(name="ext_var1")
	private String extVar1;
	
	@Column(name="ext_var2")
	private String extVar2;
	
	@Column(name="ext_var3")
	private String extVar3;

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

	public String getItemGrp1() {
		return itemGrp1;
	}

	public void setItemGrp1(String itemGrp1) {
		this.itemGrp1 = itemGrp1;
	}

	public String getItemGrp2() {
		return itemGrp2;
	}

	public void setItemGrp2(String itemGrp2) {
		this.itemGrp2 = itemGrp2;
	}

	public String getItemGrp3() {
		return itemGrp3;
	}

	public void setItemGrp3(String itemGrp3) {
		this.itemGrp3 = itemGrp3;
	}

	public float getItemRate1() {
		return itemRate1;
	}

	public void setItemRate1(float itemRate1) {
		this.itemRate1 = itemRate1;
	}

	public float getItemRate2() {
		return itemRate2;
	}

	public void setItemRate2(float itemRate2) {
		this.itemRate2 = itemRate2;
	}

	public float getItemMrp1() {
		return itemMrp1;
	}

	public void setItemMrp1(float itemMrp1) {
		this.itemMrp1 = itemMrp1;
	}

	public float getItemMrp2() {
		return itemMrp2;
	}

	public void setItemMrp2(float itemMrp2) {
		this.itemMrp2 = itemMrp2;
	}

	public float getItemMrp3() {
		return itemMrp3;
	}

	public void setItemMrp3(float itemMrp3) {
		this.itemMrp3 = itemMrp3;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public float getItemTax1() {
		return itemTax1;
	}

	public void setItemTax1(float itemTax1) {
		this.itemTax1 = itemTax1;
	}

	public float getItemTax2() {
		return itemTax2;
	}

	public void setItemTax2(float itemTax2) {
		this.itemTax2 = itemTax2;
	}

	public float getItemTax3() {
		return itemTax3;
	}

	public void setItemTax3(float itemTax3) {
		this.itemTax3 = itemTax3;
	}

	public int getItemIsUsed() {
		return itemIsUsed;
	}

	public void setItemIsUsed(int itemIsUsed) {
		this.itemIsUsed = itemIsUsed;
	}

	public float getItemSortId() {
		return itemSortId;
	}

	public void setItemSortId(float itemSortId) {
		this.itemSortId = itemSortId;
	}

	public int getGrnTwo() {
		return grnTwo;
	}

	public void setGrnTwo(int grnTwo) {
		this.grnTwo = grnTwo;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public float getItemRate3() {
		return itemRate3;
	}

	public void setItemRate3(float itemRate3) {
		this.itemRate3 = itemRate3;
	}

	public int getMinQty() {
		return minQty;
	}

	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public int getIsSaleable() {
		return isSaleable;
	}

	public void setIsSaleable(int isSaleable) {
		this.isSaleable = isSaleable;
	}

	public int getIsStockable() {
		return isStockable;
	}

	public void setIsStockable(int isStockable) {
		this.isStockable = isStockable;
	}

	public int getIsFactOrFr() {
		return isFactOrFr;
	}

	public void setIsFactOrFr(int isFactOrFr) {
		this.isFactOrFr = isFactOrFr;
	}

	public int getExtInt1() {
		return extInt1;
	}

	public void setExtInt1(int extInt1) {
		this.extInt1 = extInt1;
	}

	public int getExtInt2() {
		return extInt2;
	}

	public void setExtInt2(int extInt2) {
		this.extInt2 = extInt2;
	}

	public float getExtFloat1() {
		return extFloat1;
	}

	public void setExtFloat1(float extFloat1) {
		this.extFloat1 = extFloat1;
	}

	public float getExtFloat2() {
		return extFloat2;
	}

	public void setExtFloat2(float extFloat2) {
		this.extFloat2 = extFloat2;
	}

	public String getExtVar1() {
		return extVar1;
	}

	public void setExtVar1(String extVar1) {
		this.extVar1 = extVar1;
	}

	public String getExtVar2() {
		return extVar2;
	}

	public void setExtVar2(String extVar2) {
		this.extVar2 = extVar2;
	}

	public String getExtVar3() {
		return extVar3;
	}

	public void setExtVar3(String extVar3) {
		this.extVar3 = extVar3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", itemGrp1=" + itemGrp1
				+ ", itemGrp2=" + itemGrp2 + ", itemGrp3=" + itemGrp3 + ", itemRate1=" + itemRate1 + ", itemRate2="
				+ itemRate2 + ", itemMrp1=" + itemMrp1 + ", itemMrp2=" + itemMrp2 + ", itemMrp3=" + itemMrp3
				+ ", itemImage=" + itemImage + ", itemTax1=" + itemTax1 + ", itemTax2=" + itemTax2 + ", itemTax3="
				+ itemTax3 + ", itemIsUsed=" + itemIsUsed + ", itemSortId=" + itemSortId + ", grnTwo=" + grnTwo
				+ ", delStatus=" + delStatus + ", itemRate3=" + itemRate3 + ", minQty=" + minQty + ", shelfLife="
				+ shelfLife + ", isSaleable=" + isSaleable + ", isStockable=" + isStockable + ", isFactOrFr="
				+ isFactOrFr + ", extInt1=" + extInt1 + ", extInt2=" + extInt2 + ", extFloat1=" + extFloat1
				+ ", extFloat2=" + extFloat2 + ", extVar1=" + extVar1 + ", extVar2=" + extVar2 + ", extVar3=" + extVar3
				+ "]";
	}
	
}
