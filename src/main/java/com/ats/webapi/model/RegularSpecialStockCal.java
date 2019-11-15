package com.ats.webapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_fr_opening_stock_detail")
public class RegularSpecialStockCal {
	
	
	@Id
	@Column(name="reg")
	private float reg;
	
	@Column(name="sp")
	private float sp;

	public float getReg() {
		return reg;
	}

	public void setReg(float reg) {
		this.reg = reg;
	}

	public float getSp() {
		return sp;
	}

	public void setSp(float sp) {
		this.sp = sp;
	}

	
	@Override
	public String toString() {
		return "RegularSpecialStockCal [reg=" + reg + ", sp=" + sp + "]";
	}
	

}
