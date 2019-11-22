package com.ats.webapi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.TransactionDetail;
@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Serializable>{

}
