package com.ats.webapi.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.PettyCashEmp;
@Repository
public interface PettyCashEmpRepo extends JpaRepository<PettyCashEmp, Integer> {

	public List<PettyCashEmp> findByEmpFrIdAndDelStatus(int frId,int del);
}
