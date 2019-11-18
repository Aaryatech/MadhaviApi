package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.Item;
import com.ats.webapi.model.ItemIdOnly;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	public Item save(Item item);

	public Item findOne(int id);

	public List<Item> findByItemGrp1AndDelStatusOrderByItemGrp2Asc(String itemGrp1, int i);
	
	public List<Item> findByItemGrp1AndDelStatusAndIsSaleableOrderByItemGrp2Asc(String itemGrp1, int i, int salable);
	
	public List<Item> findByItemGrp1AndDelStatusAndIsStockableOrderByItemGrp2Asc(String itemGrp1, int i, int stock);

	@Query(value = "\n"
			+ "select i.id,i.item_id,s.short_name as item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life,i.is_saleable, i.is_stockable,i.is_fact_or_fr,i.ext_int1,i.ext_int2,i.ext_float1,i.ext_float2,i.ext_var1,i.ext_var2,i.ext_var3 from m_item i,m_item_sup s where s.item_id=i.id and i.id IN (:itemList) AND i.del_status=0", nativeQuery = true)
	public List<Item> findByDelStatusAndItemIdIn(@Param("itemList") List<Integer> itemList);

	public List<Item> findByDelStatusOrderByItemGrp2(int i);// changed to order by subcatId 21/Apr

	@Query(value = "select MAX(CAST(SUBSTRING(item_id,1,LENGTH(item_id)-0) AS SIGNED))+1  from m_item\n"
			+ "", nativeQuery = true)
	public int findMaxId();

	public List<Item> findByItemGrp1AndDelStatusOrderByItemGrp1AscItemGrp2AscItemNameAsc(String itemGrp1, int i);

//coalesce((select m_item_sup.short_name from m_item_sup where m_item_sup.item_id=i.id),0) as
	// Sachin 25 FEB
	@Query(value = "SELECT m_item.* FROM m_item WHERE  FIND_IN_SET(m_item.id ,(SELECT "
			+ "        m_fr_configure.item_show " + "        FROM " + "        m_fr_configure     WHERE "
			+ "        m_fr_configure.cat_id=:catId AND m_fr_configure.is_del=0 "
			+ "        AND m_fr_configure.setting_id in ( " + "            select " + "                menu_id  "
			+ "            from " + "                m_fr_menu_configure " + "            where "
			+ "                fr_id=:frId" + "        ))  )", nativeQuery = true)
	public List<Item> getOtherItemsForFr(@Param("frId") int frId, @Param("catId") int catId);

	//
	@Query(value = "select i.id,i.item_id,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,"
			+ "i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,"
			+ "i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life "
			+ " ,coalesce((select m_item_sup.short_name from m_item_sup where m_item_sup.item_id=i.id),0) "
			+ "as item_name,i.is_saleable, i.is_stockable,i.is_fact_or_fr, i.ext_int1, i.ext_int2, i.ext_float1, i.ext_float2, i.ext_var1, i.ext_var2, i.ext_var3 from m_item i  where i.del_status=:delStatus order by i.item_grp2 asc ,i.item_sort_id asc ", nativeQuery = true)
	public List<Item> findByDelStatusOrderByItemGrp2AscItemSortIdAsc(@Param("delStatus") int i);

	@Query(value = "select * from m_item where m_item.id IN (Select m_item_sup.item_id from m_item_sup where m_item_sup.is_allow_bday=:isAllowBday) AND m_item.del_status=:delStatus", nativeQuery = true)
	public List<Item> findByIsAllowBirthayAndDelStatus(@Param("isAllowBday") int isAllowBday,
			@Param("delStatus") int delStatus);

	@Query(value = "select m_item.* from m_item where m_item.del_status=0 And m_item.item_grp1=:itemGrp1 And m_item.id not in(select m_item_sup.item_id from m_item_sup where m_item_sup.del_status=0) order by m_item.item_name ", nativeQuery = true)
	public List<Item> findByItemGrp1(@Param("itemGrp1") String itemGrp1);

	@Query(value = "select * from m_item where m_item.id IN (:itemList) Order By item_grp1,item_grp2,item_name", nativeQuery = true)
	public List<Item> findAllItems(@Param("itemList") List<Integer> itemList);

	/*
	 * public List<Item>
	 * findByItemGrp1InAndDelStatusOrderByItemGrp2AscItemSortIdAsc(List<String>
	 * catIdList, int i);
	 */
	public List<Item> findByItemGrp2AndDelStatusOrderByItemGrp2AscItemNameAsc(String subCatId, int delStatus);

	public List<Item> findByIdIn(List<String> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET i.delStatus=1  WHERE i.id IN (:idList)")
	public int deleteItems(@Param("idList") List<Integer> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET i.itemIsUsed=4  WHERE i.id IN (:idList)")
	public int inactivateItems(@Param("idList") List<Integer> id);

	@Transactional
	@Modifying
	@Query("UPDATE Item i SET itemTax1=:itemTax1,itemTax2=:itemTax2,itemTax3=:itemTax3  WHERE i.id=:id")
	public int updateItemHsnAndPerInItem(@Param("id") Integer id, @Param("itemTax1") double itemTax1,
			@Param("itemTax2") double itemTax2, @Param("itemTax3") double itemTax3);

	public List<Item> findByDelStatusOrderByItemGrp1AscItemGrp2AscItemNameAsc(int i);

	public List<Item> findByItemGrp1InAndDelStatusOrderByItemGrp2AscItemNameAsc(List<String> catIdList, int i);

	public List<Item> findByDelStatusAndIdIn(int i, List<Integer> itemids);

	@Query(value = "select item_mrp2 from m_item where del_status=:delStatus group by item_mrp2", nativeQuery = true)
	public List<Double> itemListGroupByStationNo(@Param("delStatus") int delStatus);

//	@Query(value="select * from m_item where m_item.item_grp2 IN (:catIdList) and m_item.del_status=:i",nativeQuery=true)
	public List<Item> findByItemGrp2InAndDelStatusOrderByItemGrp2AscItemNameAsc(List<String> catIdList, int delStatus);

	@Query(value = "\n"
			+ "select i.id,i.item_id,i.item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,i.item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.grn_two,i.del_status,i.min_qty,i.item_shelf_life,i.is_saleable, i.is_stockable,i.is_fact_or_fr,i.ext_int1,i.ext_int2,i.ext_float1,i.ext_float2,i.ext_var1,i.ext_var2,i.ext_var3 from m_item i where  i.id IN (:itemList) AND i.del_status=0  ORDER BY \n" + 
			"        i.item_grp1,i.item_grp2,i.item_name", nativeQuery = true)
	public List<Item> findItemsNameByItemId(@Param("itemList") List<Integer> itemList);
	

	/*@Query(value = "select id,item_id,item_name,item_grp1,item_grp2,item_grp3,item_rate1,item_rate2,item_rate3,item_mrp1,item_mrp2,item_mrp3,item_image,item_tax1,item_tax2,item_tax3,item_is_used,item_sort_id,grn_two,del_status,min_qty,item_shelf_life,is_saleable, is_stockable,is_fact_or_fr,ext_int1, ext_int2, ext_float1, ext_float2, ext_var1, ext_var2, ext_var3  \n" + 
			"			from  \n" + 
			"			(select i.id,i.item_id,i.item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,s.item_hsncd as item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.del_status,i.min_qty,i.item_shelf_life,i.grn_two,i.is_saleable, i.is_stockable,i.is_fact_or_fr, i.ext_int1, i.ext_int2, i.ext_float1, i.ext_float2, i.ext_var1, i.ext_var2, i.ext_var3 from m_item i,m_item_sup s where  s.item_id=i.id and i.id IN (:itemList) AND i.del_status=0    \n" + 
			"			UNION ALL  \n" + 
			"			  \n" + 
			"			select i.id,i.item_id,i.item_name,i.item_grp1,i.item_grp2,i.item_grp3,i.item_rate1,i.item_rate2,i.item_rate3,i.item_mrp1,i.item_mrp2,i.item_mrp3,s.item_hsncd as item_image,i.item_tax1,i.item_tax2,i.item_tax3,i.item_is_used,i.item_sort_id,i.del_status,i.min_qty,i.item_shelf_life,i.grn_two,i.is_saleable, i.is_stockable,i.is_fact_or_fr, i.ext_int1, i.ext_int2, i.ext_float1, i.ext_float2, i.ext_var1, i.ext_var2, i.ext_var3 from m_item i,m_item_sup s where s.item_id=i.id and  i.del_status=0 and i.item_grp1=:catId and i.item_rate2=:frId) a   ORDER BY a.item_grp1,a.item_grp2,a.item_name", nativeQuery = true)*/
	@Query(value="select\n" + 
			"        id,\n" + 
			"        item_id,\n" + 
			"        item_name,\n" + 
			"        item_grp1,\n" + 
			"        item_grp2,\n" + 
			"        item_grp3,\n" + 
			"        item_rate1,\n" + 
			"        item_rate2,\n" + 
			"        item_rate3,\n" + 
			"        item_mrp1,\n" + 
			"        item_mrp2,\n" + 
			"        item_mrp3,\n" + 
			"        item_image,\n" + 
			"        item_tax1,\n" + 
			"        item_tax2,\n" + 
			"        item_tax3,\n" + 
			"        item_is_used,\n" + 
			"        item_sort_id,\n" + 
			"        grn_two,\n" + 
			"        del_status,\n" + 
			"        min_qty,\n" + 
			"        item_shelf_life,\n" + 
			"        is_saleable,\n" + 
			"        is_stockable,\n" + 
			"        is_fact_or_fr,\n" + 
			"        ext_int1,\n" + 
			"        ext_int2,\n" + 
			"        ext_float1,\n" + 
			"        ext_float2,\n" + 
			"        ext_var1,\n" + 
			"        ext_var2,\n" + 
			"        ext_var3      \n" + 
			"    from\n" + 
			"        (select\n" + 
			"            i.id,\n" + 
			"            i.item_id,\n" + 
			"            i.item_name,\n" + 
			"            i.item_grp1,\n" + 
			"            i.item_grp2,\n" + 
			"            i.item_grp3,\n" + 
			"            i.item_rate1,\n" + 
			"            i.item_rate2,\n" + 
			"            i.item_rate3,\n" + 
			"            i.item_mrp1,\n" + 
			"            i.item_mrp2,\n" + 
			"            i.item_mrp3,\n" + 
			"            s.item_hsncd as item_image,\n" + 
			"            i.item_tax1,\n" + 
			"            i.item_tax2,\n" + 
			"            i.item_tax3,\n" + 
			"            i.item_is_used,\n" + 
			"            i.item_sort_id,\n" + 
			"            i.del_status,\n" + 
			"            i.min_qty,\n" + 
			"            i.item_shelf_life,\n" + 
			"            i.grn_two,\n" + 
			"            i.is_saleable,\n" + 
			"            i.is_stockable,\n" + 
			"            i.is_fact_or_fr,\n" + 
			"            i.ext_int1,\n" + 
			"            i.ext_int2,\n" + 
			"            i.ext_float1,\n" + 
			"            i.ext_float2,\n" + 
			"            i.ext_var1,\n" + 
			"            i.ext_var2,\n" + 
			"            i.ext_var3 \n" + 
			"        from\n" + 
			"            m_item i,\n" + 
			"            m_item_sup s \n" + 
			"        where\n" + 
			"            s.item_id=i.id \n" + 
			"            and i.id IN (:itemList) \n" + 
			"            AND i.del_status=0 \n" + 
			"        	AND i.is_saleable=1\n" + 
			"        UNION\n" + 
			"        ALL            select\n" + 
			"            i.id,\n" + 
			"            i.item_id,\n" + 
			"            i.item_name,\n" + 
			"            i.item_grp1,\n" + 
			"            i.item_grp2,\n" + 
			"            i.item_grp3,\n" + 
			"            i.item_rate1,\n" + 
			"            i.item_rate2,\n" + 
			"            i.item_rate3,\n" + 
			"            i.item_mrp1,\n" + 
			"            i.item_mrp2,\n" + 
			"            i.item_mrp3,\n" + 
			"            s.item_hsncd as item_image,\n" + 
			"            i.item_tax1,\n" + 
			"            i.item_tax2,\n" + 
			"            i.item_tax3,\n" + 
			"            i.item_is_used,\n" + 
			"            i.item_sort_id,\n" + 
			"            i.del_status,\n" + 
			"            i.min_qty,\n" + 
			"            i.item_shelf_life,\n" + 
			"            i.grn_two,\n" + 
			"            i.is_saleable,\n" + 
			"            i.is_stockable,\n" + 
			"            i.is_fact_or_fr,\n" + 
			"            i.ext_int1,\n" + 
			"            i.ext_int2,\n" + 
			"            i.ext_float1,\n" + 
			"            i.ext_float2,\n" + 
			"            i.ext_var1,\n" + 
			"            i.ext_var2,\n" + 
			"            i.ext_var3 \n" + 
			"        from\n" + 
			"            m_item i,\n" + 
			"            m_item_sup s \n" + 
			"        where\n" + 
			"            s.item_id=i.id \n" + 
			"            and  i.del_status=0 \n" + 
			"            and i.item_grp1=:catId \n" +
			"            and i.item_rate2=:frId\n" + 
			"         	and i.is_saleable=1\n" + 
			"    ) a  \n" + 
			"ORDER BY\n" + 
			"    a.item_grp1,\n" + 
			"    a.item_grp2,\n" + 
			"    a.item_name",nativeQuery=true)
	public List<Item> getItemsNameByIdWithOtherItem(@Param("itemList") List<Integer> itemList,@Param("catId")int catId,@Param("frId")int frId);

	public List<Item> findByItemGrp1AndItemRate2AndDelStatus(String i, double frId, int j);

	public List<Item> findByDelStatusAndIsStockableOrderByItemGrp1AscItemGrp2AscItemNameAsc(int i, int j);

	@Transactional
	@Modifying
	@Query(value="UPDATE Item SET ext_var1=:itmId WHERE id=:itmId")
	public int updateBillableItem(@Param("itmId") int itmId);
}
