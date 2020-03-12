package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.CatWiseItemWiseSale;
import com.ats.webapi.model.report.GetRepItemwiseSell;

public interface CatWiseItemWiseSaleRepo extends JpaRepository<CatWiseItemWiseSale, Integer> {

	
	@Query(value="select\n" + 
			"        d.sell_bill_detail_no,\n" + 
			"        d.item_id,\n" + 
			"        h.fr_id,\n" + 
			"        f.fr_name,\n" + 
			"        t.item_name,\n" + 
			"        c.cat_name,\n" + 
			"        d.cat_id,\n" + 
			"        h.bill_date,\n" + 
			"        d.mrp_base_rate as rate,\n" + 
			"        d.mrp as mrp,\n" + 
			"        sum(d.qty) as qty,\n" + 
			"        sum(d.mrp*d.qty) as amount,0 as disc_amt, 0 as payable_amt \n" + 
			"    from\n" + 
			"        t_sell_bill_detail d,\n" + 
			"        t_sell_bill_header h,\n" + 
			"        m_category c,\n" + 
			"        m_item t,\n" + 
			"        m_franchisee f \n" + 
			"    WHERE\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate\n" + 
			"        AND d.cat_id IN(:catId ) \n" + 
			"        AND c.cat_id=d.cat_id \n" + 
			"        AND d.item_id=t.id \n" + 
			"        AND h.sell_bill_no=d.sell_bill_no \n" + 
			"        and h.del_status=0 \n" + 
			"        AND h.fr_id IN(:frId) \n" + 
			"        AND h.fr_id=f.fr_id \n" + 
			"    GROUP BY\n" + 
			"        d.item_id,\n" + 
			"        h.fr_id ORDER BY d.cat_id", nativeQuery=true)
	List<CatWiseItemWiseSale> getItemWiseSellReportDetails(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("frId") List<String> frId, @Param("catId") List<String> catId);

	@Query(value = "select\r\n" + 
			"        d.sell_bill_detail_no,\r\n" + 
			"        d.item_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        t.item_name,\r\n" + 
			"        c.cat_name,\r\n" + 
			"        d.cat_id,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        d.mrp_base_rate as rate,\n" + 
			"        d.mrp as mrp, sum(d.disc_amt) as disc_amt, sum(d.disc_amt+d.ext_float1) as payable_amt,\n" + 
			"        sum(d.qty) as qty,\r\n" + 
			"        sum(d.mrp*d.qty) as amount \r\n" + 
			"    from\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_category c,\r\n" + 
			"        m_item t,\r\n" + 
			"        m_franchisee f \r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"        AND c.cat_id=d.cat_id \r\n" + 
			"        AND d.item_id=t.id \r\n" + 
			"        AND h.sell_bill_no=d.sell_bill_no \r\n" + 
			"        and h.del_status=0 \r\n" + 
			"        AND h.fr_id IN(:frId) \r\n" + 
			"        AND h.fr_id=f.fr_id \r\n" + 
			"    GROUP BY\r\n" + 
			"        d.item_id,\r\n" + 
			"        h.fr_id ORDER BY d.cat_id",nativeQuery=true)
	List<CatWiseItemWiseSale> getAllItemWiseSellDetails(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("frId") List<String> frId);
}
