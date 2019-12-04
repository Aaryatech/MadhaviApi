package com.ats.webapi.model.posdashboard;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class SellBillHeaderDashCounts {
	
	@Id
 	private String uid;
	
	private String  sellAmt;
	
	private String discAmt;
	
	private String noBillGen;		
	
	private String advanceAmt;
	
	private String  creditAmt;
	
	private String profitAmt;

 
	public String getProfitAmt() {
		return profitAmt;
	}

	public void setProfitAmt(String profitAmt) {
		this.profitAmt = profitAmt;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSellAmt() {
		return sellAmt;
	}

	public void setSellAmt(String sellAmt) {
		this.sellAmt = sellAmt;
	}

	public String getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(String discAmt) {
		this.discAmt = discAmt;
	}

	public String getNoBillGen() {
		return noBillGen;
	}

	public void setNoBillGen(String noBillGen) {
		this.noBillGen = noBillGen;
	}

	public String getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(String advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public String getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(String creditAmt) {
		this.creditAmt = creditAmt;
	}

	@Override
	public String toString() {
		return "SellBillHeaderDashCounts [uid=" + uid + ", sellAmt=" + sellAmt + ", discAmt=" + discAmt + ", noBillGen="
				+ noBillGen + ", advanceAmt=" + advanceAmt + ", creditAmt=" + creditAmt + ", profitAmt=" + profitAmt
				+ "]";
	}

	 
	 
}
