package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.cloudkitchen.ItemConfigDetail;


@Repository
public interface ItemConfigDetailRepo extends JpaRepository<ItemConfigDetail, Integer> {
	
	@Transactional
	@Modifying
	@Query(value="Delete from tn_item_config_detail WHERE item_config_detail_id IN(:configDetailIds)",nativeQuery=true)
	public int deleteItemConfigDetail(@Param("configDetailIds") List<Integer> configDetailIds);

}
