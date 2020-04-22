package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.FrItemStockConfigure;

public interface FrItemStockConfigureRepository extends JpaRepository<FrItemStockConfigure,Integer> {
	
	
	public List<FrItemStockConfigure> findAll();

	@Query(value="Select setting_value from t_setting where setting_key=:settingKey",nativeQuery=true)
	public int findBySettingKey(@Param("settingKey")String settingKey);
	
	@Query(value="Select setting_value from t_setting where setting_id=:settingId",nativeQuery=true)
	public int findBySettingId(@Param("settingId")int settingId);

	//For Stock to get Dept Ids-Sachin
	public List<FrItemStockConfigure> findBySettingKeyIn(List<String> settingKeyList);
	
}
