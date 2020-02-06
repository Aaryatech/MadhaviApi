package com.ats.webapi.repository.tally;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.webapi.model.tally.TallySyncModel;

public interface TallySyncModelRepo extends JpaRepository<TallySyncModel, Long>{
	
	/*@Query(value="",nativeQuery=true)
	List<TallySyncModel> getTallySyncData();*/

}
