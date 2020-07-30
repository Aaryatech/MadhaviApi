package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.GetOrderDetail;

public interface GetOrderDetailRepo extends JpaRepository<GetOrderDetail, Integer> {

	@Query(value="SELECT\r\n" + 
			"    d.order_detail_id,\r\n" + 
			"    d.order_id,\r\n" + 
			"    d.item_id,\r\n" + 
			"    i.item_name,\r\n" + 
			"    d.qty,\r\n" + 
			"    d.mrp,\r\n" + 
			"    d.rate,\r\n" + 
			"    d.taxable_amt,\r\n" + 
			"    d.cgst_per,\r\n" + 
			"    d.sgst_per,\r\n" + 
			"    d.igst_per,\r\n" + 
			"    d.cgst_amt,\r\n" + 
			"    d.sgst_amt,\r\n" + 
			"    d.igst_amt,\r\n" + 
			"    d.disc_amt,\r\n" + 
			"    d.tax_amt,\r\n" + 
			"    d.total_amt,\r\n" + 
			"    d.remark,\r\n" + 
			"    s.uom_id,\r\n" + 
			"    s.item_uom\r\n" + 
			"FROM\r\n" + 
			"    tn_order_detail d,\r\n" + 
			"    m_item i,\r\n" + 
			"    m_item_sup s\r\n" + 
			"WHERE\r\n" + 
			"    d.order_id = :orderId AND d.del_status = 0 AND d.item_id = i.id AND d.item_id = s.item_id",nativeQuery=true)
	List<GetOrderDetail> getOrderDetailByOrderId(@Param("orderId") int orderId);

}
