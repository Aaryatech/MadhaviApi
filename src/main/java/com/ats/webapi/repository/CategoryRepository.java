package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.ItemIdOnly;
import com.ats.webapi.model.MCategory;

public interface CategoryRepository extends JpaRepository<MCategory, Integer>{
	
	public List<MCategory> findByDelStatusOrderBySeqNoAsc(int i);

	public List<MCategory> findByCatIdOrderBySeqNoAsc(int catId);
	
	@Query(value="SELECT m_category.cat_id, m_category.cat_name,m_category.is_same_day,m_category.del_status FROM m_category,m_fr_menu_show where m_category.cat_id=m_fr_menu_show.cat_id and m_fr_menu_show.menu_id=:menuId order By m_category.seq_no asc",nativeQuery=true)
	public  List<MCategory> findCatidByMenuIdIn(@Param("menuId") int menuId);

	@Query(value="SELECT m_category.cat_id, m_category.cat_name,m_category.is_same_day,m_category.del_status FROM m_category,m_fr_menu_show where m_category.cat_id=m_fr_menu_show.cat_id and m_fr_menu_show.menu_id IN(:menuId) order By m_category.seq_no asc",nativeQuery=true)
	public  List<MCategory> findCatIdByMenuIdList(@Param("menuId")List<Integer> menuId);

	
	public List<MCategory> findByDelStatusAndIsSameDayInOrderBySeqNoAsc(int i, List<Integer> list);
	
	
	@Query(value="SELECT  \n" + 
			"			   c.*\n" + 
			"			FROM  \n" + 
			"			    m_category c  \n" + 
			"			WHERE  \n" + 
			"			    c.del_status = 0 AND c.cat_id IN(  \n" + 
			"			    SELECT  \n" + 
			"			        i.item_grp1  \n" + 
			"			    FROM  \n" + 
			"			        tn_item_config_header ch,  \n" + 
			"			        tn_item_config_detail cd,  \n" + 
			"			        m_item i  \n" + 
			"			    WHERE  \n" + 
			"			        ch.item_config_id = cd.item_config_id AND ch.del_status = 0 AND ch.is_active = 0 AND cd.is_active = 0 AND cd.del_status = 0 AND cd.item_id = i.id AND i.del_status = 0 AND ch.config_type=:configType AND ch.fr_id = :frId   \n" + 
			"			    GROUP BY  \n" + 
			"			        i.item_grp1  \n" + 
			"			)  \n" + 
			"			ORDER BY  \n" + 
			"			    c.seq_no,  \n" + 
			"			    c.cat_name",nativeQuery=true)
	public  List<MCategory> getAllCatByFr(@Param("frId") int frId,@Param("configType") int configType);


}
