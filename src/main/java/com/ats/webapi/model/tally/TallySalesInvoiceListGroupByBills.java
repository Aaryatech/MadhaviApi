package com.ats.webapi.model.tally;

import java.util.List;


public class TallySalesInvoiceListGroupByBills {
	
	List<com.ats.webapi.model.SalesInvoices> SalesInvoices;

	public List<com.ats.webapi.model.SalesInvoices> getSalesInvoices() {
		return SalesInvoices;
	}

	public void setSalesInvoices(List<com.ats.webapi.model.SalesInvoices> salesInvoices) {
		SalesInvoices = salesInvoices;
	}

	@Override
	public String toString() {
		return "TallySalesInvoiceListGroupByBills [SalesInvoices=" + SalesInvoices + "]";
	}

}
