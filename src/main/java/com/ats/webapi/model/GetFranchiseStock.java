package com.ats.webapi.model;

import java.util.List;

public class GetFranchiseStock {
	
	private List<OpsFrItemStock> stockList;
	private Info info;

	public List<OpsFrItemStock> getStockList() {
		return stockList;
	}

	public void setStockList(List<OpsFrItemStock> stockList) {
		this.stockList = stockList;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "GetFranchiseStock [stockList=" + stockList + ", info=" + info + "]";
	}

}
