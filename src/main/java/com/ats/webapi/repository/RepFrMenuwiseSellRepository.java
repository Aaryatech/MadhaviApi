package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import com.ats.webapi.model.report.GetRepMenuwiseSell;

public interface RepFrMenuwiseSellRepository extends JpaRepository<GetRepMenuwiseSell, Integer>{
	
	@Query(value="select\r\n" + 
			"        d.sell_bill_detail_no,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        c.cat_name,\r\n" + 
			"        d.cat_id,\r\n" + 
			"        sum(d.qty) as qty,\r\n" + 
			"        sum(d.disc_amt) as disc_amt,\r\n" + 
			"        sum(d.disc_amt+d.ext_float1) as payable_amt,\r\n" + 
			"        sum(d.mrp*d.qty) as amount \r\n" + 
			"    from\r\n" + 
			"        t_sell_bill_detail d,\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_category c,\r\n" + 
			"        m_franchisee f \r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate  \r\n" + 
			"        AND c.cat_id=d.cat_id \r\n" + 
			"        AND h.sell_bill_no=d.sell_bill_no \r\n" + 
			"        AND h.fr_id IN(:frId) \r\n" + 
			"        AND h.fr_id=f.fr_id \r\n" + 
			"    GROUP BY\r\n" + 
			"        d.cat_id,\r\n" + 
			"        h.fr_id", nativeQuery=true)
	List<GetRepMenuwiseSell> getRepFrMenuwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
	
	/*@Query(value="select d.sell_bill_detail_no, h.fr_id, f.fr_name, c.cat_name, d.cat_id, sum(d.qty) as qty,"
			+" sum(d.mrp*d.qty) as amount from t_sell_bill_detail d, t_sell_bill_header h, m_category c,"
			+" m_franchisee f WHERE h.bill_date BETWEEN :fromDate AND :toDate "
			+" AND c.cat_id=d.cat_id AND h.sell_bill_no=d.sell_bill_no AND h.fr_id IN(:frId) AND"
			+" h.fr_id=f.fr_id GROUP BY d.cat_id, h.fr_id "
			+ ""
			+ "UNION ALL" + 
			"        " + 
			"        SELECT t_sp_cake.sp_order_no as sell_bill_detail_no," + 
			"        " + 
			"        t_sp_cake.fr_id," + 
			"        m_franchisee.fr_name," + 
			"'Special Cake' as cat_name," + 
			"5 as cat_id," + 
			"COUNT(t_sp_cake.sp_order_no) as qty," + 
			"        SUM(t_sp_cake.sp_grand_total) as amount" + 
			"        FROM t_sp_cake,m_franchisee" + 
			"        WHERE " + 
			"         t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate and t_sp_cake.sp_book_for_mob_no != '0' " + 
			"        AND t_sp_cake.fr_id IN(:frId) "+
			"        AND m_franchisee.fr_id=t_sp_cake.fr_id GROUP BY t_sp_cake.fr_id "
 ,nativeQuery=true)*/
}
