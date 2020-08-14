package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.MCategory;
import com.ats.webapi.model.SubCategory;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
	public SubCategory save(SubCategory subCategory);

	@Query(value="Select * from m_cat_sub where del_status=:i order by cat_id,sub_cat_id, seq_no asc",nativeQuery=true)
	public List<SubCategory> findByDelStatus(@Param("i")int i);

	@Query(value="Select * from m_cat_sub where cat_id in(:catId) and del_status=0 order by seq_no asc",nativeQuery=true)
	public List<SubCategory> findByCatIdInAndDelStatus(@Param("catId")List<String> catId);

	@Query(value="Select * from m_cat_sub where del_status=0 order by cat_id,sub_cat_id ,seq_no asc",nativeQuery=true)
	public List<SubCategory> findAllSubCategories();
/*
	@Query(value="Select * from m_cat_sub where del_status=:delStatus and cat_id=:catId order by cat_id,sub_cat_id",nativeQuery=true)
	public List<SubCategory> findByCatIdAndDelStatusOrderByCatIdAndSubCatId(@Param("catId")int catId,@Param("delStatus") int delStatus);
*/
	public List<SubCategory> findByCatIdAndDelStatusOrderBySeqNo(int catId, int i);

	public List<SubCategory> findBySubCatIdInAndDelStatusOrderBySeqNo(List<Integer> catId, int i);
	
	
	@Query(value="SELECT\n" + 
			"    s.*\n" + 
			"FROM\n" + 
			"    m_cat_sub s\n" + 
			"WHERE\n" + 
			"    s.del_status = 0 AND s.cat_id IN(:catId) AND s.sub_cat_id IN(\n" + 
			"    SELECT\n" + 
			"        i.item_grp2\n" + 
			"    FROM\n" + 
			"        tn_item_config_header ch,\n" + 
			"        tn_item_config_detail cd,\n" + 
			"        m_item i\n" + 
			"    WHERE\n" + 
			"        ch.item_config_id = cd.item_config_id AND ch.del_status = 0 AND ch.is_active = 0 AND cd.is_active = 0 AND cd.del_status = 0 AND cd.item_id = i.id AND i.del_status = 0 AND ch.config_type = :configType AND ch.fr_id = :frId\n" + 
			"    GROUP BY\n" + 
			"        i.item_grp2\n" + 
			")\n" + 
			"ORDER BY\n" + 
			"    s.seq_no asc",nativeQuery=true)
	public List<SubCategory> getAllSubCatByCatAndFr(@Param("catId")List<String> catId,@Param("frId") int frId,@Param("configType") int configType);
	
	

}
