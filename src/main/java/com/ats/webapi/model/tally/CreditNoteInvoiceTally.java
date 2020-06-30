package com.ats.webapi.model.tally;

import java.util.List;

import com.ats.webapi.model.BillInfo;

public class CreditNoteInvoiceTally {
	private String crnNo;
	List<CreditNoteInvoices> crnInfo;

	public String getCrnNo() {
		return crnNo;
	}

	public void setCrnNo(String crnNo) {
		this.crnNo = crnNo;
	}

	public List<CreditNoteInvoices> getCrnInfo() {
		return crnInfo;
	}

	public void setCrnInfo(List<CreditNoteInvoices> crnInfo) {
		this.crnInfo = crnInfo;
	}

	@Override
	public String toString() {
		return "CreditNoteInvoiceTally [crnNo=" + crnNo + ", crnInfo=" + crnInfo + "]";
	}

}
