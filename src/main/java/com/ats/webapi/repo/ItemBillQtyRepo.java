package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.bill.ItemBillQty;

public interface ItemBillQtyRepo extends JpaRepository<ItemBillQty, Integer> {
	
	@Query(value = " SELECT m_item.id,SUM(t_bill_detail.bill_qty) as bill_qty,m_item.item_id,m_item.item_name FROM m_item,\n" + 
			"t_bill_header,t_bill_detail WHERE t_bill_detail.bill_no=t_bill_header.bill_no AND t_bill_detail.item_id=m_item.id and t_bill_header.bill_no in (:billNos) GROUP by m_item.id", nativeQuery = true)
	
	List<ItemBillQty> getItemBillQtyListByBillNos(@Param("billNos")List<String> billNos);
	
}
