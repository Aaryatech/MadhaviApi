package com.ats.webapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.webapi.model.OpsFrItemStock;
import com.ats.webapi.repository.OpsFrItemStockRepo;

@Service
public class OpsFrItemStockServiceImpl implements OpsFrItemStockService {

	@Autowired
	OpsFrItemStockRepo opsFrItemStockRepo;

	@Override
	public List<OpsFrItemStock> getFranchiseStock(int frId, String fromDate, String toDate, int month, int year) {

		List<OpsFrItemStock> res = opsFrItemStockRepo.getOpsFrCurrStock(frId, fromDate, toDate, month, year, 1, 1);

		return res;
	}

}
