package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.GetOrderDisplay;

public interface GetOrderDisplayRepo extends JpaRepository<GetOrderDisplay, Integer> {

	@Query(value="SELECT\r\n" + 
			"	uuid() as id,\r\n" + 
			"    t1.*,\r\n" + 
			"    COALESCE(t2.offer_name,'') AS offer_name,\r\n" + 
			"    COALESCE(t2.offer_desc,'') AS offer_desc\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.*,\r\n" + 
			"        c.cust_name,\r\n" + 
			"        d.order_detail_id,\r\n" + 
			"        d.item_id,\r\n" + 
			"        d.hsn_code,\r\n" + 
			"        d.qty,\r\n" + 
			"        d.mrp,\r\n" + 
			"        d.rate,\r\n" + 
			"        d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"        d.cgst_per,\r\n" + 
			"        d.sgst_per,\r\n" + 
			"        d.igst_per,\r\n" + 
			"        d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"        d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"        d.igst_amt AS detail_igst_amt,\r\n" + 
			"        d.disc_amt AS detail_disc_amt,\r\n" + 
			"        d.tax_amt AS detail_tax_amt,\r\n" + 
			"        d.total_amt AS detail_total_amt,\r\n" + 
			"        d.remark AS detail_remark,\r\n" + 
			"        d.ex_int1 AS detail_ex_int1,\r\n" + 
			"        d.ex_int2 AS detail_ex_int2,\r\n" + 
			"        d.ex_int3 AS detail_ex_int3,\r\n" + 
			"        d.ex_int4 AS detail_ex_int4,\r\n" + 
			"        d.ex_var1 AS detail_ex_var1,\r\n" + 
			"        d.ex_var2 AS detail_ex_var2,\r\n" + 
			"        d.ex_var3 AS detail_ex_var3,\r\n" + 
			"        d.ex_var4 AS detail_ex_var4,\r\n" + 
			"        d.ex_float1 AS detail_ex_float1,\r\n" + 
			"        d.ex_float2 AS detail_ex_float2,\r\n" + 
			"        d.ex_float3 AS detail_ex_float3,\r\n" + 
			"        d.ex_float4 AS detail_ex_float4,\r\n" + 
			"        i.item_name,\r\n" + 
			"        sup.item_uom,\r\n" + 
			"        sup.uom_id,\r\n" + 
			"        ct.city_name,\r\n" + 
			"        a.area_name,  a.pincode\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h,\r\n" + 
			"        tn_order_detail d,\r\n" + 
			"        mn_customer c,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_item_sup sup,\r\n" + 
			"        mn_city ct,\r\n" + 
			"        mn_area a\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND d.del_status = 0 AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.area_id = a.area_id AND h.fr_id = :frId AND h.delivery_date = :delDate AND h.order_status IN(:status) \r\n" + 
			"    ORDER BY\r\n" + 
			"        h.status\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByFrAndStatusAndDelDate(@Param("frId") int frId, @Param("delDate") String delDate, @Param("status") List<Integer> status);

	@Query(value="SELECT\r\n" + 
			"	uuid() as id,\r\n" + 
			"    t1.*,\r\n" + 
			"    COALESCE(t2.offer_name,'') AS offer_name,\r\n" + 
			"    COALESCE(t2.offer_desc,'') AS offer_desc\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.*,\r\n" + 
			"        c.cust_name,\r\n" + 
			"        d.order_detail_id,\r\n" + 
			"        d.item_id,\r\n" + 
			"        d.hsn_code,\r\n" + 
			"        d.qty,\r\n" + 
			"        d.mrp,\r\n" + 
			"        d.rate,\r\n" + 
			"        d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"        d.cgst_per,\r\n" + 
			"        d.sgst_per,\r\n" + 
			"        d.igst_per,\r\n" + 
			"        d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"        d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"        d.igst_amt AS detail_igst_amt,\r\n" + 
			"        d.disc_amt AS detail_disc_amt,\r\n" + 
			"        d.tax_amt AS detail_tax_amt,\r\n" + 
			"        d.total_amt AS detail_total_amt,\r\n" + 
			"        d.remark AS detail_remark,\r\n" + 
			"        d.ex_int1 AS detail_ex_int1,\r\n" + 
			"        d.ex_int2 AS detail_ex_int2,\r\n" + 
			"        d.ex_int3 AS detail_ex_int3,\r\n" + 
			"        d.ex_int4 AS detail_ex_int4,\r\n" + 
			"        d.ex_var1 AS detail_ex_var1,\r\n" + 
			"        d.ex_var2 AS detail_ex_var2,\r\n" + 
			"        d.ex_var3 AS detail_ex_var3,\r\n" + 
			"        d.ex_var4 AS detail_ex_var4,\r\n" + 
			"        d.ex_float1 AS detail_ex_float1,\r\n" + 
			"        d.ex_float2 AS detail_ex_float2,\r\n" + 
			"        d.ex_float3 AS detail_ex_float3,\r\n" + 
			"        d.ex_float4 AS detail_ex_float4,\r\n" + 
			"        i.item_name,\r\n" + 
			"        sup.item_uom,\r\n" + 
			"        sup.uom_id,\r\n" + 
			"        ct.city_name,\r\n" + 
			"        a.area_name,  a.pincode\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h,\r\n" + 
			"        tn_order_detail d,\r\n" + 
			"        mn_customer c,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_item_sup sup,\r\n" + 
			"        mn_city ct,\r\n" + 
			"        mn_area a\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND d.del_status = 0 AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.area_id = a.area_id AND h.fr_id = :frId AND h.order_status IN(:status) \r\n" + 
			"    ORDER BY\r\n" + 
			"        h.status\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByFrAndStatus(@Param("frId") int frId, @Param("status") List<Integer> status);


}
