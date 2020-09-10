package com.ats.webapi.repo.cloudkitchen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.cloudkitchen.GetOrderDisplay;

public interface GetOrderDisplayRepo extends JpaRepository<GetOrderDisplay, Integer> {

	@Query(value="SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t1.*, COALESCE(t2.offer_name, '') AS offer_name,\r\n" + 
			"        COALESCE(t2.offer_desc, '') AS offer_desc,\r\n" + 
			"        CASE WHEN t1.is_agent = 0 THEN COALESCE(t3.del_boy_name, '') ELSE COALESCE(t31.agent_name, '')\r\n" + 
			"        END AS order_delivered_by_name,\r\n" + 
			"        DATE_FORMAT(t1.delivery_date, '%d-%m-%Y') AS delivery_date_display,\r\n" + 
			"        TIME_FORMAT(t1.delivery_time, '%h:%i %p') AS delivery_time_display,\r\n" + 
			"        COALESCE(t4.trail_remark, '') AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.*,\r\n" + 
			"            c.cust_name,\r\n" + 
			"            d.order_detail_id,\r\n" + 
			"            d.item_id,\r\n" + 
			"            d.hsn_code,\r\n" + 
			"            d.qty,\r\n" + 
			"            d.mrp,\r\n" + 
			"            d.rate,\r\n" + 
			"            d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"            d.cgst_per,\r\n" + 
			"            d.sgst_per,\r\n" + 
			"            d.igst_per,\r\n" + 
			"            d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"            d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"            d.igst_amt AS detail_igst_amt,\r\n" + 
			"            d.disc_amt AS detail_disc_amt,\r\n" + 
			"            d.tax_amt AS detail_tax_amt,\r\n" + 
			"            d.total_amt AS detail_total_amt,\r\n" + 
			"            d.remark AS detail_remark,\r\n" + 
			"            d.ex_int1 AS detail_ex_int1,\r\n" + 
			"            d.ex_int2 AS detail_ex_int2,\r\n" + 
			"            d.ex_int3 AS detail_ex_int3,\r\n" + 
			"            d.ex_int4 AS detail_ex_int4,\r\n" + 
			"            d.ex_var1 AS detail_ex_var1,\r\n" + 
			"            d.ex_var2 AS detail_ex_var2,\r\n" + 
			"            d.ex_var3 AS detail_ex_var3,\r\n" + 
			"            d.ex_var4 AS detail_ex_var4,\r\n" + 
			"            d.ex_float1 AS detail_ex_float1,\r\n" + 
			"            d.ex_float2 AS detail_ex_float2,\r\n" + 
			"            d.ex_float3 AS detail_ex_float3,\r\n" + 
			"            d.ex_float4 AS detail_ex_float4,\r\n" + 
			"            i.item_name,\r\n" + 
			"            i.item_grp1 AS cat_id,\r\n" + 
			"            sup.item_uom,\r\n" + 
			"            sup.uom_id,\r\n" + 
			"            ct.city_name,\r\n" + 
			"            '' AS area_name,\r\n" + 
			"            COALESCE(\r\n" + 
			"                (\r\n" + 
			"                SELECT\r\n" + 
			"                    a.pincode\r\n" + 
			"                FROM\r\n" + 
			"                    mn_cust_address a\r\n" + 
			"                WHERE\r\n" + 
			"                    a.cust_address_id = h.address_id\r\n" + 
			"            ),\r\n" + 
			"            ''\r\n" + 
			"            ) AS pincode,\r\n" + 
			"            c.phone_number AS cust_phone,\r\n" + 
			"            c.whatsapp_no AS cust_whats_app\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_header h,\r\n" + 
			"            tn_order_detail d,\r\n" + 
			"            m_customer c,\r\n" + 
			"            m_item i,\r\n" + 
			"            m_item_sup sup,\r\n" + 
			"            mn_city ct\r\n" + 
			"        WHERE\r\n" + 
			"            h.del_status = 0 AND d.del_status = 0 AND h.order_id = d.order_id AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.fr_id = :frId AND h.delivery_date = :delDate AND h.order_status IN(:status)\r\n" + 
			"        ORDER BY\r\n" + 
			"            h.order_status\r\n" + 
			"    ) t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_id,\r\n" + 
			"        CONCAT(\r\n" + 
			"            e.fr_emp_name,\r\n" + 
			"            ' - ',\r\n" + 
			"            e.fr_emp_contact\r\n" + 
			"        ) AS del_boy_name\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t3.fr_emp_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.agent_id,\r\n" + 
			"        CONCAT(a.agent_name, ' - ', a.mobile_no) AS agent_name\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.del_status = 0\r\n" + 
			") t31\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t31.agent_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.trail_id,\r\n" + 
			"        t.order_id,\r\n" + 
			"        t.status,\r\n" + 
			"        t.ex_var1 AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_trail t\r\n" + 
			"    WHERE\r\n" + 
			"        t.trail_id IN(\r\n" + 
			"        SELECT\r\n" + 
			"            MAX(trail_id)\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_trail\r\n" + 
			"        GROUP BY\r\n" + 
			"            order_id\r\n" + 
			"    )\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.order_id = t4.order_id AND t1.order_status = t4.status) tb\r\n" + 
			"ORDER BY\r\n" + 
			"    tb.order_status",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByFrAndStatusAndDelDate(@Param("frId") int frId, @Param("delDate") String delDate, @Param("status") List<Integer> status);

	@Query(value="SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t1.*, COALESCE(t2.offer_name, '') AS offer_name,\r\n" + 
			"        COALESCE(t2.offer_desc, '') AS offer_desc,\r\n" + 
			"        CASE WHEN t1.is_agent = 0 THEN COALESCE(t3.del_boy_name, '') ELSE COALESCE(t31.agent_name, '')\r\n" + 
			"        END AS order_delivered_by_name,\r\n" + 
			"        DATE_FORMAT(t1.delivery_date, '%d-%m-%Y') AS delivery_date_display,\r\n" + 
			"        TIME_FORMAT(t1.delivery_time, '%h:%i %p') AS delivery_time_display,\r\n" + 
			"        COALESCE(t4.trail_remark, '') AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.*,\r\n" + 
			"            c.cust_name,\r\n" + 
			"            d.order_detail_id,\r\n" + 
			"            d.item_id,\r\n" + 
			"            d.hsn_code,\r\n" + 
			"            d.qty,\r\n" + 
			"            d.mrp,\r\n" + 
			"            d.rate,\r\n" + 
			"            d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"            d.cgst_per,\r\n" + 
			"            d.sgst_per,\r\n" + 
			"            d.igst_per,\r\n" + 
			"            d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"            d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"            d.igst_amt AS detail_igst_amt,\r\n" + 
			"            d.disc_amt AS detail_disc_amt,\r\n" + 
			"            d.tax_amt AS detail_tax_amt,\r\n" + 
			"            d.total_amt AS detail_total_amt,\r\n" + 
			"            d.remark AS detail_remark,\r\n" + 
			"            d.ex_int1 AS detail_ex_int1,\r\n" + 
			"            d.ex_int2 AS detail_ex_int2,\r\n" + 
			"            d.ex_int3 AS detail_ex_int3,\r\n" + 
			"            d.ex_int4 AS detail_ex_int4,\r\n" + 
			"            d.ex_var1 AS detail_ex_var1,\r\n" + 
			"            d.ex_var2 AS detail_ex_var2,\r\n" + 
			"            d.ex_var3 AS detail_ex_var3,\r\n" + 
			"            d.ex_var4 AS detail_ex_var4,\r\n" + 
			"            d.ex_float1 AS detail_ex_float1,\r\n" + 
			"            d.ex_float2 AS detail_ex_float2,\r\n" + 
			"            d.ex_float3 AS detail_ex_float3,\r\n" + 
			"            d.ex_float4 AS detail_ex_float4,\r\n" + 
			"            i.item_name,\r\n" + 
			"            i.item_grp1 AS cat_id,\r\n" + 
			"            sup.item_uom,\r\n" + 
			"            sup.uom_id,\r\n" + 
			"            ct.city_name,\r\n" + 
			"            '' AS area_name,\r\n" + 
			"            COALESCE(\r\n" + 
			"                (\r\n" + 
			"                SELECT\r\n" + 
			"                    a.pincode\r\n" + 
			"                FROM\r\n" + 
			"                    mn_cust_address a\r\n" + 
			"                WHERE\r\n" + 
			"                    a.cust_address_id = h.address_id\r\n" + 
			"            ),\r\n" + 
			"            ''\r\n" + 
			"            ) AS pincode,\r\n" + 
			"            c.phone_number AS cust_phone,\r\n" + 
			"            c.whatsapp_no AS cust_whats_app\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_header h,\r\n" + 
			"            tn_order_detail d,\r\n" + 
			"            m_customer c,\r\n" + 
			"            m_item i,\r\n" + 
			"            m_item_sup sup,\r\n" + 
			"            mn_city ct\r\n" + 
			"        WHERE\r\n" + 
			"            h.del_status = 0 AND d.del_status = 0 AND h.order_id = d.order_id AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.fr_id = :frId  AND h.order_status IN(:status)\r\n" + 
			"        ORDER BY\r\n" + 
			"            h.order_status\r\n" + 
			"    ) t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_id,\r\n" + 
			"        CONCAT(\r\n" + 
			"            e.fr_emp_name,\r\n" + 
			"            ' - ',\r\n" + 
			"            e.fr_emp_contact\r\n" + 
			"        ) AS del_boy_name\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t3.fr_emp_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.agent_id,\r\n" + 
			"        CONCAT(a.agent_name, ' - ', a.mobile_no) AS agent_name\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.del_status = 0\r\n" + 
			") t31\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t31.agent_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.trail_id,\r\n" + 
			"        t.order_id,\r\n" + 
			"        t.status,\r\n" + 
			"        t.ex_var1 AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_trail t\r\n" + 
			"    WHERE\r\n" + 
			"        t.trail_id IN(\r\n" + 
			"        SELECT\r\n" + 
			"            MAX(trail_id)\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_trail\r\n" + 
			"        GROUP BY\r\n" + 
			"            order_id\r\n" + 
			"    )\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.order_id = t4.order_id AND t1.order_status = t4.status) tb\r\n" + 
			"ORDER BY\r\n" + 
			"    tb.order_status,tb.delivery_date, tb.delivery_time ",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByFrAndStatus(@Param("frId") int frId, @Param("status") List<Integer> status);

	
	@Query(value="SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t1.*, COALESCE(t2.offer_name, '') AS offer_name,\r\n" + 
			"        COALESCE(t2.offer_desc, '') AS offer_desc,\r\n" + 
			"        CASE WHEN t1.is_agent = 0 THEN COALESCE(t3.del_boy_name, '') ELSE COALESCE(t31.agent_name, '')\r\n" + 
			"        END AS order_delivered_by_name,\r\n" + 
			"        DATE_FORMAT(t1.delivery_date, '%d-%m-%Y') AS delivery_date_display,\r\n" + 
			"        TIME_FORMAT(t1.delivery_time, '%h:%i %p') AS delivery_time_display,\r\n" + 
			"        COALESCE(t4.trail_remark, '') AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.*,\r\n" + 
			"            c.cust_name,\r\n" + 
			"            d.order_detail_id,\r\n" + 
			"            d.item_id,\r\n" + 
			"            d.hsn_code,\r\n" + 
			"            d.qty,\r\n" + 
			"            d.mrp,\r\n" + 
			"            d.rate,\r\n" + 
			"            d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"            d.cgst_per,\r\n" + 
			"            d.sgst_per,\r\n" + 
			"            d.igst_per,\r\n" + 
			"            d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"            d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"            d.igst_amt AS detail_igst_amt,\r\n" + 
			"            d.disc_amt AS detail_disc_amt,\r\n" + 
			"            d.tax_amt AS detail_tax_amt,\r\n" + 
			"            d.total_amt AS detail_total_amt,\r\n" + 
			"            d.remark AS detail_remark,\r\n" + 
			"            d.ex_int1 AS detail_ex_int1,\r\n" + 
			"            d.ex_int2 AS detail_ex_int2,\r\n" + 
			"            d.ex_int3 AS detail_ex_int3,\r\n" + 
			"            d.ex_int4 AS detail_ex_int4,\r\n" + 
			"            d.ex_var1 AS detail_ex_var1,\r\n" + 
			"            d.ex_var2 AS detail_ex_var2,\r\n" + 
			"            d.ex_var3 AS detail_ex_var3,\r\n" + 
			"            d.ex_var4 AS detail_ex_var4,\r\n" + 
			"            d.ex_float1 AS detail_ex_float1,\r\n" + 
			"            d.ex_float2 AS detail_ex_float2,\r\n" + 
			"            d.ex_float3 AS detail_ex_float3,\r\n" + 
			"            d.ex_float4 AS detail_ex_float4,\r\n" + 
			"            i.item_name,\r\n" + 
			"            i.item_grp1 AS cat_id,\r\n" + 
			"            sup.item_uom,\r\n" + 
			"            sup.uom_id,\r\n" + 
			"            ct.city_name,\r\n" + 
			"            '' AS area_name,\r\n" + 
			"            COALESCE(\r\n" + 
			"                (\r\n" + 
			"                SELECT\r\n" + 
			"                    a.pincode\r\n" + 
			"                FROM\r\n" + 
			"                    mn_cust_address a\r\n" + 
			"                WHERE\r\n" + 
			"                    a.cust_address_id = h.address_id\r\n" + 
			"            ),\r\n" + 
			"            ''\r\n" + 
			"            ) AS pincode,\r\n" + 
			"            c.phone_number AS cust_phone,\r\n" + 
			"            c.whatsapp_no AS cust_whats_app\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_header h,\r\n" + 
			"            tn_order_detail d,\r\n" + 
			"            m_customer c,\r\n" + 
			"            m_item i,\r\n" + 
			"            m_item_sup sup,\r\n" + 
			"            mn_city ct\r\n" + 
			"        WHERE\r\n" + 
			"            h.del_status = 0 AND d.del_status = 0 AND h.order_id = d.order_id AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.fr_id = :frId AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status IN(:status)\r\n" + 
			"        ORDER BY\r\n" + 
			"            h.order_status\r\n" + 
			"    ) t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_id,\r\n" + 
			"        CONCAT(\r\n" + 
			"            e.fr_emp_name,\r\n" + 
			"            ' - ',\r\n" + 
			"            e.fr_emp_contact\r\n" + 
			"        ) AS del_boy_name\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t3.fr_emp_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.agent_id,\r\n" + 
			"        CONCAT(a.agent_name, ' - ', a.mobile_no) AS agent_name\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.del_status = 0\r\n" + 
			") t31\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t31.agent_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.trail_id,\r\n" + 
			"        t.order_id,\r\n" + 
			"        t.status,\r\n" + 
			"        t.ex_var1 AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_trail t\r\n" + 
			"    WHERE\r\n" + 
			"        t.trail_id IN(\r\n" + 
			"        SELECT\r\n" + 
			"            MAX(trail_id)\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_trail\r\n" + 
			"        GROUP BY\r\n" + 
			"            order_id\r\n" + 
			"    )\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.order_id = t4.order_id AND t1.order_status = t4.status) tb\r\n" + 
			"ORDER BY\r\n" + 
			"    tb.order_status,tb.delivery_date, tb.delivery_time ",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByFrAndStatusAndDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("frId") int frId, @Param("status") List<Integer> status);

	
	
	@Query(value="SELECT\r\n" + 
			"    *\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t1.*, COALESCE(t2.offer_name, '') AS offer_name,\r\n" + 
			"        COALESCE(t2.offer_desc, '') AS offer_desc,\r\n" + 
			"        CASE WHEN t1.is_agent = 0 THEN COALESCE(t3.del_boy_name, '') ELSE COALESCE(t31.agent_name, '')\r\n" + 
			"        END AS order_delivered_by_name,\r\n" + 
			"        DATE_FORMAT(t1.delivery_date, '%d-%m-%Y') AS delivery_date_display,\r\n" + 
			"        TIME_FORMAT(t1.delivery_time, '%h:%i %p') AS delivery_time_display,\r\n" + 
			"        COALESCE(t4.trail_remark, '') AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            h.*,\r\n" + 
			"            c.cust_name,\r\n" + 
			"            d.order_detail_id,\r\n" + 
			"            d.item_id,\r\n" + 
			"            d.hsn_code,\r\n" + 
			"            d.qty,\r\n" + 
			"            d.mrp,\r\n" + 
			"            d.rate,\r\n" + 
			"            d.taxable_amt AS detail_taxable_amt,\r\n" + 
			"            d.cgst_per,\r\n" + 
			"            d.sgst_per,\r\n" + 
			"            d.igst_per,\r\n" + 
			"            d.cgst_amt AS detail_cgst_amt,\r\n" + 
			"            d.sgst_amt AS detail_sgst_amt,\r\n" + 
			"            d.igst_amt AS detail_igst_amt,\r\n" + 
			"            d.disc_amt AS detail_disc_amt,\r\n" + 
			"            d.tax_amt AS detail_tax_amt,\r\n" + 
			"            d.total_amt AS detail_total_amt,\r\n" + 
			"            d.remark AS detail_remark,\r\n" + 
			"            d.ex_int1 AS detail_ex_int1,\r\n" + 
			"            d.ex_int2 AS detail_ex_int2,\r\n" + 
			"            d.ex_int3 AS detail_ex_int3,\r\n" + 
			"            d.ex_int4 AS detail_ex_int4,\r\n" + 
			"            d.ex_var1 AS detail_ex_var1,\r\n" + 
			"            d.ex_var2 AS detail_ex_var2,\r\n" + 
			"            d.ex_var3 AS detail_ex_var3,\r\n" + 
			"            d.ex_var4 AS detail_ex_var4,\r\n" + 
			"            d.ex_float1 AS detail_ex_float1,\r\n" + 
			"            d.ex_float2 AS detail_ex_float2,\r\n" + 
			"            d.ex_float3 AS detail_ex_float3,\r\n" + 
			"            d.ex_float4 AS detail_ex_float4,\r\n" + 
			"            i.item_name,\r\n" + 
			"            i.item_grp1 AS cat_id,\r\n" + 
			"            sup.item_uom,\r\n" + 
			"            sup.uom_id,\r\n" + 
			"            ct.city_name,\r\n" + 
			"            '' AS area_name,\r\n" + 
			"            COALESCE(\r\n" + 
			"                (\r\n" + 
			"                SELECT\r\n" + 
			"                    a.pincode\r\n" + 
			"                FROM\r\n" + 
			"                    mn_cust_address a\r\n" + 
			"                WHERE\r\n" + 
			"                    a.cust_address_id = h.address_id\r\n" + 
			"            ),\r\n" + 
			"            ''\r\n" + 
			"            ) AS pincode,\r\n" + 
			"            c.phone_number AS cust_phone,\r\n" + 
			"            c.whatsapp_no AS cust_whats_app\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_header h,\r\n" + 
			"            tn_order_detail d,\r\n" + 
			"            m_customer c,\r\n" + 
			"            m_item i,\r\n" + 
			"            m_item_sup sup,\r\n" + 
			"            mn_city ct\r\n" + 
			"        WHERE\r\n" + 
			"            h.del_status = 0 AND d.del_status = 0 AND h.order_id = d.order_id AND c.del_status = 0 AND h.cust_id = c.cust_id AND d.item_id = i.id AND d.item_id = sup.item_id AND h.city_id = ct.city_id AND h.order_delivered_by = :empId AND h.is_agent = 0 AND h.order_status IN(:status) AND h.delivery_date BETWEEN :fromDate AND :toDate\r\n" + 
			"        ORDER BY\r\n" + 
			"            h.order_status\r\n" + 
			"    ) t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        *\r\n" + 
			"    FROM\r\n" + 
			"        mn_offer_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.offer_id = t2.offer_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.fr_emp_id,\r\n" + 
			"        CONCAT(\r\n" + 
			"            e.fr_emp_name,\r\n" + 
			"            ' - ',\r\n" + 
			"            e.fr_emp_contact\r\n" + 
			"        ) AS del_boy_name\r\n" + 
			"    FROM\r\n" + 
			"        m_fr_emp e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t3.fr_emp_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        a.agent_id,\r\n" + 
			"        CONCAT(a.agent_name, ' - ', a.mobile_no) AS agent_name\r\n" + 
			"    FROM\r\n" + 
			"        mn_agent a\r\n" + 
			"    WHERE\r\n" + 
			"        a.del_status = 0\r\n" + 
			") t31\r\n" + 
			"ON\r\n" + 
			"    t1.order_delivered_by = t31.agent_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t.trail_id,\r\n" + 
			"        t.order_id,\r\n" + 
			"        t.status,\r\n" + 
			"        t.ex_var1 AS trail_remark\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_trail t\r\n" + 
			"    WHERE\r\n" + 
			"        t.trail_id IN(\r\n" + 
			"        SELECT\r\n" + 
			"            MAX(trail_id)\r\n" + 
			"        FROM\r\n" + 
			"            tn_order_trail\r\n" + 
			"        GROUP BY\r\n" + 
			"            order_id\r\n" + 
			"    )\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.order_id = t4.order_id AND t1.order_status = t4.status) tb\r\n" + 
			"ORDER BY\r\n" + 
			"    tb.order_status,\r\n" + 
			"        tb.delivery_date,\r\n" + 
			"        tb.delivery_time ",nativeQuery=true)
	List<GetOrderDisplay> getAllOrdersByDeliveryBoyAndStatusAndDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("empId") int empId, @Param("status") List<Integer> status);


}
