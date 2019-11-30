package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.GrandTotalBillWise;
import com.ats.webapi.model.bill.ItemListForCustomerBill;

public interface ItemListForCustomerBillRepo extends JpaRepository<ItemListForCustomerBill, Integer> {

	@Query(value = "SELECT\n" + 
			"    t_adv_order_detail.item_id,\n" + 
			"    t_adv_order_detail.rate,\n" + 
			"    t_adv_order_detail.mrp AS orignal_mrp,\n" + 
			"    t_adv_order_detail.qty,\n" + 
			"    m_item.item_name,\n" + 
			"    m_item.item_tax1 as tax_per,0 as taxable_amt,0 as tax_amt,0 as total\n" + 
			"FROM\n" + 
			"    t_adv_order_detail,\n" + 
			"    m_item\n" + 
			"WHERE\n" + 
			"    t_adv_order_detail.adv_header_id =:advHeadId AND t_adv_order_detail.item_id = m_item.id AND t_adv_order_detail.del_status = 0", nativeQuery = true)
	List<ItemListForCustomerBill> getItem(@Param("advHeadId") int advHeadId);

	
	
}
