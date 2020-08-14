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
			"        sum(d.grand_total) as payable_amt,\r\n" + 
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
			"        AND h.fr_id=f.fr_id AND h.del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        d.cat_id,\r\n" + 
			"        h.fr_id", nativeQuery=true)
	List<GetRepMenuwiseSell> getRepFrMenuwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
	
	@Query(value="select\r\n" + 
			"        d.sell_bill_detail_no,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        c.cat_name,\r\n" + 
			"        d.cat_id,\r\n" + 
			"        sum(d.qty) as qty,\r\n" + 
			"        sum(d.disc_amt) as disc_amt,\r\n" + 
			"        sum(d.grand_total) as payable_amt,\r\n" + 
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
			"        AND h.fr_id=f.fr_id AND h.del_status=0 AND h.ext_int2>0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        d.cat_id,\r\n" + 
			"        h.fr_id", nativeQuery=true)
	List<GetRepMenuwiseSell> getRepFrMenuwiseSellForOnline(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
	
	@Query(value="select\r\n" + 
			"        d.sell_bill_detail_no,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        c.cat_name,\r\n" + 
			"        d.cat_id,\r\n" + 
			"        sum(d.qty) as qty,\r\n" + 
			"        sum(d.disc_amt) as disc_amt,\r\n" + 
			"        sum(d.grand_total) as payable_amt,\r\n" + 
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
			"        AND h.fr_id=f.fr_id AND h.del_status=0 AND h.ext_int2=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        d.cat_id,\r\n" + 
			"        h.fr_id", nativeQuery=true)
	List<GetRepMenuwiseSell> getRepFrMenuwiseSellForPOS(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
	
}
