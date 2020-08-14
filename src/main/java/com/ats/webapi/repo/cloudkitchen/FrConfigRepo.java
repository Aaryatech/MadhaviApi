package com.ats.webapi.repo.cloudkitchen;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.FrConfig;


public interface FrConfigRepo extends JpaRepository<FrConfig, Integer> {

	FrConfig findBydelStatusAndFrConfigId(int del, int configId);
	
	FrConfig findBydelStatusAndFrId(int del, int frId);
	
}
