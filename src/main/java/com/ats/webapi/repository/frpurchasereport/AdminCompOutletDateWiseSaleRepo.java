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
			"    0) AS withdrawl_total, t1.date_str \r\n" + 
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
			"        0 AS withdrawl_total, h.bill_date as date_str \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList)  AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0) ) \r\n" + 
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
			"        d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0  AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0) )  \r\n" + 
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
		
		List<AdminCompOutletDateWiseSale> getAdminDateWiseCompOutletSale(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("configType") int configType);

	
	
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
			"    0) AS withdrawl_total, t1.date_str \r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.sell_bill_no,\r\n" + 
			"        h.bill_date,\r\n" + 
			"        YEAR(h.bill_date) as yr,\r\n" + 
			"        MONTH(h.bill_date) as mnt,\r\n" + 
			"        f.fr_id,\r\n" + 
			"        CONCAT(f.fr_name,' - ',f.fr_code) as fr_name,\r\n" + 
			"        SUM(h.grand_total) AS grand_total,\r\n" + 
			"        SUM(h.discount_amt) AS disc_total,\r\n" + 
			"        0 AS tr_total,\r\n" + 
			"        0 AS adv_total,\r\n" + 
			"        0 AS exp_total,\r\n" + 
			"        0 AS credit_total,\r\n" + 
			"        0 AS withdrawl_total, h.bill_date as date_str \r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h,\r\n" + 
			"        m_franchisee f\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList) AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0)) \r\n" + 
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
			"        d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0 AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0)) \r\n" + 
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
			"    date_str,\r\n" + 
			"    fr_name",nativeQuery=true)
		
		List<AdminCompOutletDateWiseSale> getAdminMonthWiseCompOutletSale(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("configType") int configType);

	
	
	//COMP OUTLET WITH DAIRY MART
	@Query(value=" " + 
			"SELECT\r\n" + 
			"    UUID() AS id, t_bill_header.bill_no AS sell_bill_no, DATE_FORMAT(\r\n" + 
			"        t_bill_header.bill_date,\r\n" + 
			"        '%d-%m-%Y'\r\n" + 
			"    ) AS bill_date,\r\n" + 
			"    t_bill_header.fr_id,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    COALESCE((t_bill_header.grand_total),\r\n" + 
			"    0) AS bill_total,\r\n" + 
			"    COALESCE((t_bill_header.disc_amt),\r\n" + 
			"    0) AS disc_total,\r\n" + 
			"    0 tr_total,\r\n" + 
			"    0 AS adv_total,\r\n" + 
			"    0 AS exp_total,\r\n" + 
			"    0 AS credit_total,\r\n" + 
			"    0 AS withdrawl_total, t_bill_header.bill_date as date_str\r\n" + 
			"FROM\r\n" + 
			"    m_franchisee,\r\n" + 
			"    t_bill_header\r\n" + 
			"WHERE\r\n" + 
			"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.is_dairy_mart = 2\r\n" + 
			"GROUP BY\r\n" + 
			"    t_bill_header.bill_date\r\n" + 
			"ORDER BY\r\n" + 
			"    t_bill_header.bill_date,\r\n" + 
			"    m_franchisee.fr_name",nativeQuery=true)
		
		List<AdminCompOutletDateWiseSale> getAdminDateWiseCompOutletSaleDairymart(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	
	//COMP OUTLET WITH DAIRY MART AND REGULAR
		@Query(value=" " + 
				"SELECT\r\n" + 
				"    id,\r\n" + 
				"    sell_bill_no,\r\n" + 
				"    bill_date,\r\n" + 
				"    fr_id,\r\n" + 
				"    fr_name,\r\n" + 
				"    SUM(bill_total) AS bill_total,\r\n" + 
				"    SUM(disc_total) AS disc_total,\r\n" + 
				"    SUM(tr_total) AS tr_total,\r\n" + 
				"    SUM(adv_total) AS adv_total,\r\n" + 
				"    SUM(exp_total) AS exp_total,\r\n" + 
				"    SUM(credit_total) AS credit_total,\r\n" + 
				"    SUM(withdrawl_total) AS withdrawl_total, '' as date_str\r\n" + 
				"FROM\r\n" + 
				"    (\r\n" + 
				"    SELECT\r\n" + 
				"        UUID() AS id, t_bill_header.bill_no AS sell_bill_no, DATE_FORMAT(\r\n" + 
				"            t_bill_header.bill_date,\r\n" + 
				"            '%d-%m-%Y'\r\n" + 
				"        ) AS bill_date,\r\n" + 
				"        t_bill_header.fr_id,\r\n" + 
				"        m_franchisee.fr_name,\r\n" + 
				"        COALESCE((t_bill_header.grand_total),\r\n" + 
				"        0) AS bill_total,\r\n" + 
				"        COALESCE((t_bill_header.disc_amt),\r\n" + 
				"        0) AS disc_total,\r\n" + 
				"        0 tr_total,\r\n" + 
				"        0 AS adv_total,\r\n" + 
				"        0 AS exp_total,\r\n" + 
				"        0 AS credit_total,\r\n" + 
				"        0 AS withdrawl_total\r\n" + 
				"    FROM\r\n" + 
				"        m_franchisee,\r\n" + 
				"        t_bill_header\r\n" + 
				"    WHERE\r\n" + 
				"        t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.is_dairy_mart = 2\r\n" + 
				"    GROUP BY\r\n" + 
				"        t_bill_header.bill_date, t_bill_header.fr_id\r\n" + 
				"    UNION\r\n" + 
				"        (\r\n" + 
				"        SELECT\r\n" + 
				"            UUID() AS id, t1.sell_bill_no, DATE_FORMAT(t1.bill_date, '%d-%m-%Y') AS bill_date,\r\n" + 
				"            t1.fr_id,\r\n" + 
				"            t1.fr_name,\r\n" + 
				"            COALESCE((t1.grand_total),\r\n" + 
				"            0) AS bill_total,\r\n" + 
				"            COALESCE((t1.disc_total),\r\n" + 
				"            0) AS disc_total,\r\n" + 
				"            COALESCE((t2.tr_total),\r\n" + 
				"            0) AS tr_total,\r\n" + 
				"            COALESCE((t3.adv_total),\r\n" + 
				"            0) AS adv_total,\r\n" + 
				"            COALESCE((t4.exp_total),\r\n" + 
				"            0) AS exp_total,\r\n" + 
				"            COALESCE((t5.credit_total),\r\n" + 
				"            0) AS credit_total,\r\n" + 
				"            COALESCE((t1.withdrawl_total),\r\n" + 
				"            0) AS withdrawl_total\r\n" + 
				"        FROM\r\n" + 
				"            (\r\n" + 
				"            SELECT\r\n" + 
				"                h.sell_bill_no,\r\n" + 
				"                h.bill_date,\r\n" + 
				"                f.fr_id,\r\n" + 
				"                CONCAT(f.fr_name, ' - ', f.fr_code) AS fr_name,\r\n" + 
				"                SUM(h.grand_total) AS grand_total,\r\n" + 
				"                SUM(h.discount_amt) AS disc_total,\r\n" + 
				"                0 AS tr_total,\r\n" + 
				"                0 AS adv_total,\r\n" + 
				"                0 AS exp_total,\r\n" + 
				"                0 AS credit_total,\r\n" + 
				"                0 AS withdrawl_total\r\n" + 
				"            FROM\r\n" + 
				"                t_sell_bill_header h,\r\n" + 
				"                m_franchisee f\r\n" + 
				"            WHERE\r\n" + 
				"                h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList) AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0) )  \r\n" + 
				"            GROUP BY\r\n" + 
				"                h.bill_date,\r\n" + 
				"                h.fr_id\r\n" + 
				"        ) t1\r\n" + 
				"    LEFT JOIN(\r\n" + 
				"        SELECT\r\n" + 
				"            d.sell_bill_no,\r\n" + 
				"            d.transaction_date,\r\n" + 
				"            h.fr_id,\r\n" + 
				"            SUM(\r\n" + 
				"                d.cash_amt + d.card_amt + d.e_pay_amt\r\n" + 
				"            ) AS tr_total\r\n" + 
				"        FROM\r\n" + 
				"            t_transaction_detail d,\r\n" + 
				"            t_sell_bill_header h\r\n" + 
				"        WHERE\r\n" + 
				"            d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0  AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0) ) \r\n" + 
				"        GROUP BY\r\n" + 
				"            d.transaction_date,\r\n" + 
				"            h.fr_id\r\n" + 
				"    ) t2\r\n" + 
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
				"        e.del_status = 0 AND e.exp_date BETWEEN :fromDate AND :toDate AND e.fr_id IN(:frIdList)\r\n" + 
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
				"        c.del_status = 0 AND c.crn_date BETWEEN :fromDate AND :toDate AND c.ex_int1 IN(:frIdList)\r\n" + 
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
				"    fr_name)\r\n" + 
				"    ) t1\r\n" + 
				"GROUP BY\r\n" + 
				"    bill_date,\r\n" + 
				"    fr_id\r\n" + 
				"ORDER BY\r\n" + 
				"    bill_date,\r\n" + 
				"    fr_name",nativeQuery=true)
			
			List<AdminCompOutletDateWiseSale> getAdminDateWiseCompOutletSaleDairymartAndReg(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("configType") int configType);

		
		
		// MONTHLY REPORT COMPOUTLET WITH DAIRY ND REG
		@Query(value=" " + 
				"SELECT\r\n" + 
				"    id,\r\n" + 
				"    sell_bill_no,\r\n" + 
				"    bill_date,\r\n" + 
				"    fr_id,\r\n" + 
				"    fr_name,\r\n" + 
				"    SUM(bill_total) AS bill_total,\r\n" + 
				"    SUM(disc_total) AS disc_total,\r\n" + 
				"    SUM(tr_total) AS tr_total,\r\n" + 
				"    SUM(adv_total) AS adv_total,\r\n" + 
				"    SUM(exp_total) AS exp_total,\r\n" + 
				"    SUM(credit_total) AS credit_total,\r\n" + 
				"    SUM(withdrawl_total) AS withdrawl_total, date_str \r\n" + 
				"FROM\r\n" + 
				"    (\r\n" + 
				"    SELECT\r\n" + 
				"        UUID() AS id, t_bill_header.bill_no AS sell_bill_no, CONCAT(\r\n" + 
				"            MONTHNAME(t_bill_header.bill_date),\r\n" + 
				"            '--',\r\n" + 
				"            YEAR(t_bill_header.bill_date)\r\n" + 
				"        ) AS bill_date,\r\n" + 
				"        t_bill_header.fr_id,\r\n" + 
				"        CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\r\n" + 
				"        SUM(t_bill_header.grand_total) AS bill_total,\r\n" + 
				"        SUM(t_bill_header.disc_amt) AS disc_total,\r\n" + 
				"        0 AS tr_total,\r\n" + 
				"        0 AS adv_total,\r\n" + 
				"        0 AS exp_total,\r\n" + 
				"        0 AS credit_total,\r\n" + 
				"        0 AS withdrawl_total, t_bill_header.bill_date as date_str\r\n" + 
				"    FROM\r\n" + 
				"        m_franchisee,\r\n" + 
				"        t_bill_header\r\n" + 
				"    WHERE\r\n" + 
				"        t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.is_dairy_mart = 2\r\n" + 
				"    GROUP BY\r\n" + 
				"        YEAR(t_bill_header.bill_date),\r\n" + 
				"        MONTH(t_bill_header.bill_date),\r\n" + 
				"        t_bill_header.fr_id\r\n" + 
				"    UNION\r\n" + 
				"        (\r\n" + 
				"        SELECT\r\n" + 
				"            UUID() AS id, t1.sell_bill_no, CONCAT(\r\n" + 
				"                MONTHNAME(t1.bill_date),\r\n" + 
				"                '--',\r\n" + 
				"                YEAR(t1.bill_date)\r\n" + 
				"            ) AS bill_date,\r\n" + 
				"            t1.fr_id,\r\n" + 
				"            t1.fr_name,\r\n" + 
				"            COALESCE((t1.grand_total),\r\n" + 
				"            0) AS bill_total,\r\n" + 
				"            COALESCE((t1.disc_total),\r\n" + 
				"            0) AS disc_total,\r\n" + 
				"            COALESCE((t2.tr_total),\r\n" + 
				"            0) AS tr_total,\r\n" + 
				"            COALESCE((t3.adv_total),\r\n" + 
				"            0) AS adv_total,\r\n" + 
				"            COALESCE((t4.exp_total),\r\n" + 
				"            0) AS exp_total,\r\n" + 
				"            COALESCE((t5.credit_total),\r\n" + 
				"            0) AS credit_total,\r\n" + 
				"            COALESCE((t1.withdrawl_total),\r\n" + 
				"            0) AS withdrawl_total, t1.bill_date as date_str \r\n" + 
				"        FROM\r\n" + 
				"            (\r\n" + 
				"            SELECT\r\n" + 
				"                h.sell_bill_no,\r\n" + 
				"                h.bill_date,\r\n" + 
				"                YEAR(h.bill_date) AS yr,\r\n" + 
				"                MONTH(h.bill_date) AS mnt,\r\n" + 
				"                f.fr_id,\r\n" + 
				"                CONCAT(f.fr_name,' - ',f.fr_code) as fr_name,\r\n" + 
				"                SUM(h.grand_total) AS grand_total,\r\n" + 
				"                SUM(h.discount_amt) AS disc_total,\r\n" + 
				"                0 AS tr_total,\r\n" + 
				"                0 AS adv_total,\r\n" + 
				"                0 AS exp_total,\r\n" + 
				"                0 AS credit_total,\r\n" + 
				"                0 AS withdrawl_total\r\n" + 
				"            FROM\r\n" + 
				"                t_sell_bill_header h,\r\n" + 
				"                m_franchisee f\r\n" + 
				"            WHERE\r\n" + 
				"                h.del_status = 0 AND h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = f.fr_id AND h.fr_id IN(:frIdList) AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0))  \r\n" + 
				"            GROUP BY\r\n" + 
				"                YEAR(h.bill_date),\r\n" + 
				"                MONTH(h.bill_date),\r\n" + 
				"                h.fr_id\r\n" + 
				"        ) t1\r\n" + 
				"    LEFT JOIN(\r\n" + 
				"        SELECT\r\n" + 
				"            d.sell_bill_no,\r\n" + 
				"            d.transaction_date,\r\n" + 
				"            YEAR(d.transaction_date) AS yr,\r\n" + 
				"            MONTH(d.transaction_date) AS mnt,\r\n" + 
				"            h.fr_id,\r\n" + 
				"            SUM(\r\n" + 
				"                d.cash_amt + d.card_amt + d.e_pay_amt\r\n" + 
				"            ) AS tr_total\r\n" + 
				"        FROM\r\n" + 
				"            t_transaction_detail d,\r\n" + 
				"            t_sell_bill_header h\r\n" + 
				"        WHERE\r\n" + 
				"            d.transaction_date BETWEEN :fromDate AND :toDate AND d.sell_bill_no = h.sell_bill_no AND h.fr_id IN(:frIdList) AND h.del_status = 0 AND IF(:configType=0 , h.ext_int2>=0, IF(:configType=1 , h.ext_int2=0, h.ext_int2>0)) \r\n" + 
				"        GROUP BY\r\n" + 
				"            YEAR(d.transaction_date),\r\n" + 
				"            MONTH(d.transaction_date),\r\n" + 
				"            h.fr_id\r\n" + 
				"    ) t2\r\n" + 
				"ON\r\n" + 
				"    t1.yr = t2.yr AND t1.mnt = t2.mnt AND t1.fr_id = t2.fr_id\r\n" + 
				"LEFT JOIN(\r\n" + 
				"    SELECT\r\n" + 
				"        h.fr_id,\r\n" + 
				"        h.order_date,\r\n" + 
				"        YEAR(h.order_date) AS yr,\r\n" + 
				"        MONTH(h.order_date) AS mnt,\r\n" + 
				"        SUM(h.advance_amt) AS adv_total\r\n" + 
				"    FROM\r\n" + 
				"        t_adv_order_header h\r\n" + 
				"    WHERE\r\n" + 
				"        h.order_date BETWEEN :fromDate AND :toDate AND h.del_status = 0\r\n" + 
				"    GROUP BY\r\n" + 
				"        YEAR(h.order_date),\r\n" + 
				"        MONTH(h.order_date),\r\n" + 
				"        h.fr_id\r\n" + 
				") t3\r\n" + 
				"ON\r\n" + 
				"    t1.yr = t3.yr AND t1.mnt = t3.mnt AND t1.fr_id = t3.fr_id\r\n" + 
				"LEFT JOIN(\r\n" + 
				"    SELECT\r\n" + 
				"        e.exp_id,\r\n" + 
				"        e.fr_id,\r\n" + 
				"        e.exp_date,\r\n" + 
				"        YEAR(e.exp_date) AS yr,\r\n" + 
				"        MONTH(e.exp_date) AS mnt,\r\n" + 
				"        SUM(e.ch_amt) AS exp_total\r\n" + 
				"    FROM\r\n" + 
				"        m_expense e\r\n" + 
				"    WHERE\r\n" + 
				"        e.del_status = 0 AND e.exp_date BETWEEN :fromDate AND :toDate AND e.fr_id IN(:frIdList)\r\n" + 
				"    GROUP BY\r\n" + 
				"        YEAR(e.exp_date),\r\n" + 
				"        MONTH(e.exp_date),\r\n" + 
				"        e.fr_id\r\n" + 
				") t4\r\n" + 
				"ON\r\n" + 
				"    t1.fr_id = t4.fr_id AND t1.yr = t4.yr AND t1.mnt = t4.mnt\r\n" + 
				"LEFT JOIN(\r\n" + 
				"    SELECT\r\n" + 
				"        c.bill_no,\r\n" + 
				"        c.crn_date,\r\n" + 
				"        YEAR(c.crn_date) AS yr,\r\n" + 
				"        MONTH(c.crn_date) AS mnt,\r\n" + 
				"        SUM(c.grand_total) AS credit_total,\r\n" + 
				"        c.ex_int1\r\n" + 
				"    FROM\r\n" + 
				"        t_credit_note_pos c\r\n" + 
				"    WHERE\r\n" + 
				"        c.del_status = 0 AND c.crn_date BETWEEN :fromDate AND :toDate AND c.ex_int1 IN(:frIdList)\r\n" + 
				"    GROUP BY\r\n" + 
				"        YEAR(c.crn_date),\r\n" + 
				"        MONTH(c.crn_date),\r\n" + 
				"        c.ex_int1\r\n" + 
				") t5\r\n" + 
				"ON\r\n" + 
				"    t1.yr = t5.yr AND t1.mnt = t5.mnt AND t1.fr_id = t5.ex_int1\r\n" + 
				"LEFT JOIN(\r\n" + 
				"    SELECT\r\n" + 
				"        p.pettycash_id,\r\n" + 
				"        p.fr_id,\r\n" + 
				"        p.date,\r\n" + 
				"        YEAR(p.date) AS yr,\r\n" + 
				"        MONTH(p.date) AS mnt,\r\n" + 
				"        SUM(p.withdrawal_amt) AS withdrawl_total\r\n" + 
				"    FROM\r\n" + 
				"        t_pettycash_mgmnt p\r\n" + 
				"    WHERE\r\n" + 
				"        p.fr_id IN(:frIdList) AND p.date BETWEEN :fromDate AND :toDate\r\n" + 
				"    GROUP BY\r\n" + 
				"        YEAR(p.date),\r\n" + 
				"        MONTH(p.date),\r\n" + 
				"        p.fr_id\r\n" + 
				") t6\r\n" + 
				"ON\r\n" + 
				"    t1.yr = t6.yr AND t1.mnt = t6.mnt AND t1.fr_id = t6.fr_id\r\n" + 
				"ORDER BY\r\n" + 
				"    sell_bill_no)\r\n" + 
				"    ) t1\r\n" + 
				"GROUP BY\r\n" + 
				"    bill_date,\r\n" + 
				"    fr_id\r\n" + 
				"ORDER BY\r\n" + 
				"    date_str",nativeQuery=true)
			
			List<AdminCompOutletDateWiseSale> getAdminMonthWiseCompOutletSaleDairyAndRegular(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("configType") int configType );

		
		// MONTHLY REPORT COMPOUTLET WITH DAIRY
		@Query(value=" " + 
				"SELECT\r\n" + 
				"    UUID() AS id, t_bill_header.bill_no AS sell_bill_no, CONCAT(\r\n" + 
				"        MONTHNAME(t_bill_header.bill_date),\r\n" + 
				"        '--',\r\n" + 
				"        YEAR(t_bill_header.bill_date)\r\n" + 
				"    ) AS bill_date,\r\n" + 
				"    t_bill_header.fr_id,\r\n" + 
				"    CONCAT(m_franchisee.fr_name,' - ',m_franchisee.fr_code) as fr_name,\r\n" + 
				"    SUM(t_bill_header.grand_total) AS bill_total,\r\n" + 
				"    SUM(t_bill_header.disc_amt) AS disc_total,\r\n" + 
				"    0 AS tr_total,\r\n" + 
				"    0 AS adv_total,\r\n" + 
				"    0 AS exp_total,\r\n" + 
				"    0 AS credit_total,\r\n" + 
				"    0 AS withdrawl_total\r\n" + 
				"FROM\r\n" + 
				"    m_franchisee,\r\n" + 
				"    t_bill_header\r\n" + 
				"WHERE\r\n" + 
				"    t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.del_status = 0 AND t_bill_header.is_dairy_mart = 2\r\n" + 
				"GROUP BY\r\n" + 
				"    YEAR(t_bill_header.bill_date),\r\n" + 
				"    MONTH(t_bill_header.bill_date),\r\n" + 
				"    t_bill_header.fr_id\r\n" + 
				"ORDER BY\r\n" + 
				"    t_bill_header.bill_date",nativeQuery=true)
			
			List<AdminCompOutletDateWiseSale> getAdminMonthWiseCompOutletSaleDairy(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		
}
