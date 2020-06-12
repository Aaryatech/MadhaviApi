package com.ats.webapi.model.tally;

import java.util.List;

public class TallySalesInvoiceList {

	List<SalesInvoices> SalesInvoices;

	public List<SalesInvoices> getSalesInvoices() {
		return SalesInvoices;
	}

	public void setSalesInvoices(List<SalesInvoices> SalesInvoices) {
		this.SalesInvoices = SalesInvoices;
	}

	@Override
	public String toString() {
		return "TallySalesInvoiceList [SalesInvoices=" + SalesInvoices + "]";
	}

}
