package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.ItemDepartment;

public interface ItemDepartmentRepo extends JpaRepository<ItemDepartment, Integer> {

	List<ItemDepartment> findAllByIsActiveAndDelStatus(int i, int j);

	ItemDepartment findByDeptId(int i);

	@Transactional
	@Modifying
	@Query(" UPDATE ItemDepartment  SET delStatus=:status   WHERE  deptId=:deptId ")
	int deleteByDeptId(@Param("deptId") int deptId, @Param("status") int status);
	
	@Transactional
	@Modifying
	@Query(" UPDATE ItemDepartment  SET isActive=:status   WHERE  deptId=:deptId ")
	int activeByDeptId(@Param("deptId") int deptId, @Param("status") int status);

}
