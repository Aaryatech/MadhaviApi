package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.bill.BillReceiptHeader;


public interface BillReceiptHeaderRepo extends JpaRepository<BillReceiptHeader, Integer>{

}
