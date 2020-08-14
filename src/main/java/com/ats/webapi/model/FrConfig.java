package com.ats.webapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tn_fr_config")
public class FrConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "fr_config_id")
	private int frConfigId;
	
	@Column(name = "fr_id")
	private int frId;
	
	@Column(name = "fr_type")
	private int frType;
	
	@Column(name = "city_ids")
	private String cityIds;
	
	@Column(name = "area_ids")
	private String areaIds;
	
	@Column(name = "pincodes")
	private String pincodes;
	
	@Column(name = "from_latitude")
	private double fromLatitude;
	
	@Column(name = "to_latitude")
	private double toLatitude;
	
	@Column(name = "from_longitude")
	private double fromLongitude;
	
	@Column(name = "to_longitude")
	private double toLongitude;	
	
	@Column(name = "km_area_covered")
	private float kmAreaCovered;
	
	@Column(name = "comp_id")
	private int compId;
	
	@Column(name = "is_active")
	private int isActive;
	
	@Column(name = "del_status")
	private int delStatus;
	
	@Column(name = "ex_int1")
	private int exInt1;
	
	@Column(name = "ex_int2")
	private int exInt2;
	
	@Column(name = "ex_int3")
	private int exInt3;
	
	@Column(name = "ex_var1")
	private String exVar1;
	
	@Column(name = "ex_var2")
	private String exVar2;
	
	@Column(name = "ex_var3")
	private String exVar3;
	
	@Column(name = "ex_float1")
	private float exFloat1;
	
	@Column(name = "ex_float2")
	private float exFloat2;
	
	@Column(name = "ex_float3")
	private float exFloat3;

	public int getFrConfigId() {
		return frConfigId;
	}

	public void setFrConfigId(int frConfigId) {
		this.frConfigId = frConfigId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public int getFrType() {
		return frType;
	}

	public void setFrType(int frType) {
		this.frType = frType;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getPincodes() {
		return pincodes;
	}

	public void setPincodes(String pincodes) {
		this.pincodes = pincodes;
	}

	public double getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(double fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public double getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(double toLatitude) {
		this.toLatitude = toLatitude;
	}

	public double getFromLongitude() {
		return fromLongitude;
	}

	public void setFromLongitude(double fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	public double getToLongitude() {
		return toLongitude;
	}

	public void setToLongitude(double toLongitude) {
		this.toLongitude = toLongitude;
	}

	public float getKmAreaCovered() {
		return kmAreaCovered;
	}

	public void setKmAreaCovered(float kmAreaCovered) {
		this.kmAreaCovered = kmAreaCovered;
	}

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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

	@Override
	public String toString() {
		return "FrConfig [frConfigId=" + frConfigId + ", frId=" + frId + ", frType=" + frType + ", cityIds=" + cityIds
				+ ", areaIds=" + areaIds + ", pincodes=" + pincodes + ", fromLatitude=" + fromLatitude + ", toLatitude="
				+ toLatitude + ", fromLongitude=" + fromLongitude + ", toLongitude=" + toLongitude + ", kmAreaCovered="
				+ kmAreaCovered + ", compId=" + compId + ", isActive=" + isActive + ", delStatus=" + delStatus
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2
				+ ", exFloat3=" + exFloat3 + "]";
	}
	
}
