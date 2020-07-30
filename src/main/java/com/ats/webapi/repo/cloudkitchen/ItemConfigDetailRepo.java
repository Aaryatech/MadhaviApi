package com.ats.webapi.repo.cloudkitchen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.cloudkitchen.ItemConfigDetail;


@Repository
public interface ItemConfigDetailRepo extends JpaRepository<ItemConfigDetail, Integer> {
	


}
