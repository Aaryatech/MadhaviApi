package com.ats.webapi.repository.frpurchasereport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.frpurchase.AdminCompOutletDateWiseSale;

public interface AdminCompOutletDateWiseSaleRepo extends JpaRepository<AdminCompOutletDateWiseSale, Integer> {

	@Query(value=" " + 
			"SELECT\r\n" + 
			"    UUID() AS id, t1.sell_bill_no, DATE_FORMAT(t1.bill_date, '%d-%m-%Y') as bill_date, t1.fr_id, t1.fr_name, COALESCE((t1.grand_total),\r\n" + 
			"    0) AS bill_total,\r\n" + 
			"    COALESCE((t1.disc_total),\r\n" + 
			"    0) AS disc_total,\r\n" + 
			"    COALESCE((t2.tr_total),\r\n" + 
			"    0) AS tr_total,\r\n" + 
			"    COALESCE((t3.adv_total),\r\n" + 
			"    0) AS adv_total,\r\n" + 
			"    COALESCE((t4.exp_total),\r\n" + 
			"    0) AS exp_total,\r\n" + 
			"    COALESCE((t5.credit_total),\r\n" + 
			"    0) AS credit_total,\r\n" + 
			"    COALESCE((t1.withdrawl_total),\r\n" + 
			"    0) AS withdrawl_total\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.sell_bill_no,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        f.fr_id,\r\n" + 
			"        CONCAT(f.fr_name,' - ',f.fr_code) as fr_name,\r\n" + 
			"        SUM(h.grand_total) AS grand_total,\r\n" + 
			"        SUM(h.discount_amt) AS disc_total,\r\n" + 
			"        0 AS tr_total,\r\n" + 
			"        0 AS adv_total,\r\n" + 
			"        0 AS exp_total,\r\n" + 
			"        0 AS credit_total,\r\n" + 
			"        0 AS withdrawl_total\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList) \r\n" + 
			"    GROUP BY\r\n" + 
			"        h.bill_date,\r\n" + 
			"        h.fr_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.sell_bill_no,\r\n" + 
			"        d.transaction_date,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        SUM(\r\n" + 
			"            d.cash_amt + d.card_amt + d.e_pay_amt\r\n" + 
			"        ) AS tr_total\r\n" + 
			"    FROM\r\n" + 
			"        t_transaction_detail d,\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        d.transaction_date,\r\n" + 
			"        h.fr_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.bill_date = t2.transaction_date AND t1.fr_id = t2.fr_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.order_date,\r\n" + 
			"        SUM(h.advance_amt) AS adv_total\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.order_date BETWEEN :fromDate AND :toDate AND h.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.order_date,\r\n" + 
			"        h.fr_id\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t3.fr_id AND t1.bill_date = t3.order_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.exp_id,\r\n" + 
			"        e.fr_id,\r\n" + 
			"        e.exp_date,\r\n" + 
			"        SUM(e.ch_amt) AS exp_total\r\n" + 
			"    FROM\r\n" + 
			"        m_expense e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0 AND e.exp_date BETWEEN :fromDate AND :toDate AND e.fr_id IN(:frIdList) \r\n" + 
			"    GROUP BY\r\n" + 
			"        e.exp_date,\r\n" + 
			"        e.fr_id\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t4.fr_id AND t1.bill_date = t4.exp_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        c.bill_no,\r\n" + 
			"        c.crn_date,\r\n" + 
			"        SUM(c.grand_total) AS credit_total,\r\n" + 
			"        c.ex_int1\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos c\r\n" + 
			"    WHERE\r\n" + 
			"        c.del_status = 0 AND c.crn_date BETWEEN :fromDate AND :toDate AND c.ex_int1 IN(:frIdList) \r\n" + 
			"    GROUP BY\r\n" + 
			"        c.crn_date,\r\n" + 
			"        c.ex_int1\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.bill_date = t5.crn_date AND t1.fr_id = t5.ex_int1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        p.pettycash_id,\r\n" + 
			"        p.fr_id,\r\n" + 
			"        p.date,\r\n" + 
			"        SUM(p.withdrawal_amt) AS withdrawl_total\r\n" + 
			"    FROM\r\n" + 
			"        t_pettycash_mgmnt p\r\n" + 
			"    WHERE\r\n" + 
			"        p.fr_id IN(:frIdList) AND p.date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        p.date,\r\n" + 
			"        p.fr_id\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.bill_date = t6.date AND t1.fr_id = t6.fr_id\r\n" + 
			"ORDER BY\r\n" + 
			"    bill_date,\r\n" + 
			"    fr_name",nativeQuery=true)
		
		List<AdminCompOutletDateWiseSale> getAdminDateWiseCompOutletSale(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
	
	@Query(value=" " + 
			"SELECT\r\n" + 
			"    UUID() AS id, t1.sell_bill_no, \r\n" + 
			"    CONCAT(MONTHNAME(t1.bill_date),'--',YEAR(t1.bill_date)) AS bill_date, \r\n" + 
			"    t1.fr_id, t1.fr_name, COALESCE((t1.grand_total),\r\n" + 
			"    0) AS bill_total,\r\n" + 
			"    COALESCE((t1.disc_total),\r\n" + 
			"    0) AS disc_total,\r\n" + 
			"    COALESCE((t2.tr_total),\r\n" + 
			"    0) AS tr_total,\r\n" + 
			"    COALESCE((t3.adv_total),\r\n" + 
			"    0) AS adv_total,\r\n" + 
			"    COALESCE((t4.exp_total),\r\n" + 
			"    0) AS exp_total,\r\n" + 
			"    COALESCE((t5.credit_total),\r\n" + 
			"    0) AS credit_total,\r\n" + 
			"    COALESCE((t1.withdrawl_total),\r\n" + 
			"    0) AS withdrawl_total\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.sell_bill_no,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        YEAR(h.bill_date) as yr,\r\n" + 
			"        MONTH(h.bill_date) as mnt,\r\n" + 
			"        f.fr_id,\r\n" + 
			"        f.fr_name,\r\n" + 
			"        SUM(h.grand_total) AS grand_total,\r\n" + 
			"        SUM(h.discount_amt) AS disc_total,\r\n" + 
			"        0 AS tr_total,\r\n" + 
			"        0 AS adv_total,\r\n" + 
			"        0 AS exp_total,\r\n" + 
			"        0 AS credit_total,\r\n" + 
			"        0 AS withdrawl_total\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList)\r\n" + 
			"    GROUP BY\r\n" + 
			"        YEAR(h.bill_date),MONTH(h.bill_date),\r\n" + 
			"        h.fr_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        d.sell_bill_no,\r\n" + 
			"        d.transaction_date,\r\n" + 
			"     	YEAR(d.transaction_date) as yr,\r\n" + 
			"        MONTH(d.transaction_date) as mnt,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        SUM(\r\n" + 
			"            d.cash_amt + d.card_amt + d.e_pay_amt\r\n" + 
			"        ) AS tr_total\r\n" + 
			"    FROM\r\n" + 
			"        t_transaction_detail d,\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        YEAR(d.transaction_date),MONTH(d.transaction_date),\r\n" + 
			"        h.fr_id\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.yr = t2.yr AND t1.mnt=t2.mnt AND t1.fr_id = t2.fr_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.fr_id,\r\n" + 
			"        h.order_date,\r\n" + 
			"	    YEAR(h.order_date) as yr,\r\n" + 
			"        MONTH(h.order_date) as mnt,\r\n" + 
			"        SUM(h.advance_amt) AS adv_total\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.order_date BETWEEN :fromDate AND :toDate AND h.del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"        YEAR(h.order_date),MONTH(h.order_date),\r\n" + 
			"        h.fr_id\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.yr = t3.yr AND t1.mnt=t3.mnt AND t1.fr_id = t3.fr_id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        e.exp_id,\r\n" + 
			"        e.fr_id,\r\n" + 
			"        e.exp_date,\r\n" + 
			"	    YEAR(e.exp_date) as yr,\r\n" + 
			"        MONTH(e.exp_date) as mnt,\r\n" + 
			"        SUM(e.ch_amt) AS exp_total\r\n" + 
			"    FROM\r\n" + 
			"        m_expense e\r\n" + 
			"    WHERE\r\n" + 
			"        e.del_status = 0 AND e.exp_date BETWEEN :fromDate AND :toDate AND e.fr_id IN(:frIdList) \r\n" + 
			"    GROUP BY\r\n" + 
			"         YEAR(e.exp_date),MONTH(e.exp_date),\r\n" + 
			"        e.fr_id\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t4.fr_id AND t1.yr = t4.yr AND t1.mnt=t4.mnt\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        c.bill_no,\r\n" + 
			"        c.crn_date,\r\n" + 
			"	    YEAR(c.crn_date) as yr,\r\n" + 
			"        MONTH(c.crn_date) as mnt,\r\n" + 
			"        SUM(c.grand_total) AS credit_total,\r\n" + 
			"        c.ex_int1\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos c\r\n" + 
			"    WHERE\r\n" + 
			"        c.del_status = 0 AND c.crn_date BETWEEN :fromDate AND :toDate AND c.ex_int1 IN(:frIdList)\r\n" + 
			"    GROUP BY\r\n" + 
			"        YEAR(c.crn_date),MONTH(c.crn_date),\r\n" + 
			"        c.ex_int1\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"     t1.yr = t5.yr AND t1.mnt=t5.mnt AND t1.fr_id = t5.ex_int1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        p.pettycash_id,\r\n" + 
			"        p.fr_id,\r\n" + 
			"        p.date,\r\n" + 
			"	    YEAR(p.date) as yr,\r\n" + 
			"        MONTH(p.date) as mnt,\r\n" + 
			"        SUM(p.withdrawal_amt) AS withdrawl_total\r\n" + 
			"    FROM\r\n" + 
			"        t_pettycash_mgmnt p\r\n" + 
			"    WHERE\r\n" + 
			"        p.fr_id IN(:frIdList) AND p.date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        YEAR(p.date),MONTH(p.date),\r\n" + 
			"        p.fr_id\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.yr = t6.yr AND t1.mnt=t6.mnt AND t1.fr_id = t6.fr_id\r\n" + 
			"ORDER BY\r\n" + 
			"    bill_date,\r\n" + 
			"    fr_name",nativeQuery=true)
		
		List<AdminCompOutletDateWiseSale> getAdminMonthWiseCompOutletSale(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
}