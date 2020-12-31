package com.ats.webapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ats.webapi.model.OpsFrItemStock;

@Service
public interface OpsFrItemStockService {

	List<OpsFrItemStock> getFranchiseStock(int frId, String fromDate, String toDate, int month, int year);

}
