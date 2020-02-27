package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.ItemRes;
@Repository
public interface ItemResponseRepository extends JpaRepository<ItemRes, Integer>{

	@Query(value="Select m_item.*,m_item_sup.item_uom,m_item_sup.uom_id from m_item,m_item_sup where m_item.del_status=:delStatus and m_item.item_grp2=:subCatId and m_item.id=m_item_sup.item_id order by m_item.item_grp2,m_item.item_name Asc",nativeQuery=true)
	List<ItemRes> findByItemGrp2AndDelStatusOrderByItemGrp2AscItemNameAsc(@Param("subCatId")int subCatId,@Param("delStatus") int i);

	@Query(value = " Select\n" + 
			"        m_item.*,\n" + 
			"        m_item_sup.item_uom,\n" + 
			"        m_item_sup.uom_id \n" + 
			"    from\n" + 
			"        m_item,\n" + 
			"        m_item_sup \n" + 
			"    where\n" + 
			"        m_item.del_status=0 \n" + 
			"        and m_item.id=m_item_sup.item_id \n" + 
			"    order by\n" + 
			"        m_item.item_grp2,\n" + 
			"        m_item.item_name Asc",nativeQuery=true)
	List<ItemRes> findByAllItemGrp2OrderByItemGrp2AscItemNameAsc();

	@Query(value="Select m_item.*,m_item_sup.item_uom,m_item_sup.uom_id from m_item,m_item_sup where m_item.del_status=:delStatus and "
			+ "m_item.item_grp2=:subCatId and m_item.id=m_item_sup.item_id and  m_item.item_grp1=:catId order by m_item.item_grp2,m_item.item_name Asc",nativeQuery=true)
	List<ItemRes> findByItemsByItemGrp1AndItemGrp2(@Param("catId")int catId, @Param("subCatId")int subCatId,@Param("delStatus") int i);

	@Query(value="Select\n" + 
			"        m_item.*,\n" + 
			"        m_item_sup.item_uom,\n" + 
			"        m_item_sup.uom_id \n" + 
			"    from\n" + 
			"        m_item,\n" + 
			"        m_item_sup \n" + 
			"    where\n" + 
			"        m_item.del_status=:delStatus\n" + 
			"        and m_item.id=m_item_sup.item_id \n" + 
			"        and  m_item.item_grp1=:catId \n" + 
			"    order by\n" + 
			"        m_item.item_grp1,\n" + 
			"        m_item.item_name Asc", nativeQuery=true)
	List<ItemRes> findByItemsByItemGrp1(@Param("catId")int catId, @Param("delStatus") int i);
}
