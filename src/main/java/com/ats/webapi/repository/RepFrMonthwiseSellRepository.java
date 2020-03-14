package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.GetMonthWiseReport;
import com.ats.webapi.model.report.GetRepMonthwiseSell;

//GetRepMonthwiseSell 
public interface RepFrMonthwiseSellRepository extends JpaRepository<GetMonthWiseReport, Integer>{
	
	@Query(value="SELECT\r\n" + 
			"    bill_date,\r\n" + 
			"    sell_bill_no,\r\n" + 
			"    a.fr_id,\r\n" + 
			"    grand_total,\r\n" + 
			"    cash,\r\n" + 
			"    card,\r\n" + 
			"    other,\r\n" + 
			"    fr_name,\r\n" + 
			"    a.MONTH,\r\n" + 
			"    discount_amt,\r\n" + 
			"    pending_amt,\r\n" + 
			"    COALESCE(adv_amt, 0) AS adv_amt,\r\n" + 
			"    COALESCE(b.ch_amt, 0) AS regular,\r\n" + 
			"    COALESCE(c.ch_amt, 0) AS chalan,\r\n" + 
			"    COALESCE(withdrawal_amt, 0) AS withdrawal_amt,\r\n" + 
			"    COALESCE(e.credit_note_total_amt, 0) AS credit_note_total_amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        t_sell_bill_header.bill_date,\r\n" + 
			"        t_sell_bill_header.sell_bill_no,\r\n" + 
			"        t_sell_bill_header.fr_id,\r\n" + 
			"        SUM(t_sell_bill_header.grand_total) AS grand_total,\r\n" + 
			"        SUM(t_transaction_detail.cash_amt) AS cash,\r\n" + 
			"        SUM(t_transaction_detail.card_amt) AS card,\r\n" + 
			"        SUM(t_transaction_detail.e_pay_amt) AS other,\r\n" + 
			"        m_franchisee.fr_name,\r\n" + 
			"        CONCAT(\r\n" + 
			"            MONTHNAME(t_sell_bill_header.bill_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(t_sell_bill_header.bill_date)\r\n" + 
			"        ) AS MONTH,\r\n" + 
			"        SUM(\r\n" + 
			"            t_sell_bill_header.discount_amt\r\n" + 
			"        ) AS discount_amt,\r\n" + 
			"        SUM(\r\n" + 
			"            t_sell_bill_header.remaining_amt\r\n" + 
			"        ) AS pending_amt\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,\r\n" + 
			"        t_transaction_detail,\r\n" + 
			"        m_franchisee\r\n" + 
			"    WHERE\r\n" + 
			"        t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.fr_id IN(:frId) AND m_franchisee.fr_id = t_sell_bill_header.fr_id\r\n" + 
			"    GROUP BY\r\n" + 
			"       \r\n" + 
			"        t_sell_bill_header.fr_id,MONTH\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"          fr_id,\r\n" + 
			"        exp_type,\r\n" + 
			"        SUM(ch_amt) ch_amt,    CONCAT(\r\n" + 
			"            MONTHNAME(exp_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(exp_date)\r\n" + 
			"        ) AS MONTH\r\n" + 
			"    FROM\r\n" + 
			"        m_expense\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frId) AND exp_type = 1 AND exp_date BETWEEN :fromDate AND :toDate AND del_status=0\r\n" + 
			"    GROUP BY\r\n" + 
			"        fr_id, MONTH\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"     a.fr_id =  b.fr_id AND  a.MONTH =  b.MONTH\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT  fr_id,\r\n" + 
			"        exp_type,\r\n" + 
			"        SUM(ch_amt) ch_amt,    CONCAT(\r\n" + 
			"            MONTHNAME(exp_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(exp_date)\r\n" + 
			"        ) AS MONTH\r\n" + 
			"    FROM\r\n" + 
			"        m_expense\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frId) AND exp_type =2 AND exp_date BETWEEN :fromDate AND :toDate AND del_status=0\r\n" + 
			"    GROUP BY\r\n" + 
			"        fr_id, MONTH\r\n" + 
			"   \r\n" + 
			") c\r\n" + 
			"ON\r\n" + 
			"      a.fr_id =  c.fr_id AND  a.MONTH = c.MONTH\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT SUM(withdrawal_amt) withdrawal_amt, fr_id,\r\n" + 
			"          CONCAT(\r\n" + 
			"            MONTHNAME(date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(date)\r\n" + 
			"        ) AS MONTH\r\n" + 
			"    FROM\r\n" + 
			"        t_pettycash_mgmnt petty\r\n" + 
			"    WHERE\r\n" + 
			"        date BETWEEN :fromDate AND :toDate AND fr_id = :frId AND status=0\r\n" + 
			"    GROUP BY\r\n" + 
			"        fr_id, MONTH\r\n" + 
			"  \r\n" + 
			") d\r\n" + 
			"ON\r\n" + 
			"     a.fr_id =  d.fr_id AND  a.MONTH = d.MONTH\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT  fr_id,\r\n" + 
			"     CONCAT(\r\n" + 
			"            MONTHNAME(t_credit_note_pos.crn_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(t_credit_note_pos.crn_date)\r\n" + 
			"        ) AS MONTH,\r\n" + 
			"        COALESCE(\r\n" + 
			"            (\r\n" + 
			"                SUM(t_credit_note_pos.grand_total)\r\n" + 
			"            ),\r\n" + 
			"            0\r\n" + 
			"        ) AS credit_note_total_amt\r\n" + 
			"    FROM\r\n" + 
			"        t_credit_note_pos,\r\n" + 
			"        t_sell_bill_header\r\n" + 
			"    WHERE\r\n" + 
			"        t_credit_note_pos.bill_no = t_sell_bill_header.sell_bill_no AND t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.del_status = 0 AND t_sell_bill_header.fr_id IN(:frId)\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_sell_bill_header.fr_id ,  MONTH\r\n" + 
			"  \r\n" + 
			") e\r\n" + 
			"ON\r\n" + 
			"       a.fr_id =  e.fr_id AND  a.MONTH = e.MONTH\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT  fr_id,\r\n" + 
			"      \r\n" + 
			"     CONCAT(\r\n" + 
			"            MONTHNAME(order_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(order_date)\r\n" + 
			"        ) AS MONTH,\r\n" + 
			"        SUM(advance_amt) AS adv_amt\r\n" + 
			"    FROM\r\n" + 
			"        t_adv_order_header\r\n" + 
			"    WHERE\r\n" + 
			"        order_date BETWEEN :fromDate AND :toDate AND fr_id IN(:frId) AND del_status = 0\r\n" + 
			"    GROUP BY\r\n" + 
			"       fr_id, MONTH\r\n" + 
			"   \r\n" + 
			") f\r\n" + 
			"ON\r\n" + 
			"    a.fr_id = f.fr_id AND  a.MONTH = f.MONTH",nativeQuery=true)
			List<GetMonthWiseReport> getRepFrMonthwiseSell(@Param("fromDate") String fromDate,
					@Param("toDate") String toDate, @Param("frId") List<String> frId);
}


//24-02-2020 Mahendra
/* 
		SELECT t_sell_bill_header.bill_date ,t_sell_bill_header.sell_bill_no ,t_sell_bill_header.fr_id,"
			+" sum(CASE WHEN t_sell_bill_header.payment_mode = 1 THEN t_sell_bill_header.payable_amt ELSE 0 END) as cash,"
					+" sum(CASE WHEN t_sell_bill_header.payment_mode = 2 THEN t_sell_bill_header.payable_amt ELSE 0 END) as card ,"
			+ " sum(CASE WHEN t_sell_bill_header.payment_mode = 3 THEN t_sell_bill_header.payable_amt ELSE 0 END) as other,"
					+" m_franchisee.fr_name, CONCAT(MONTHNAME(t_sell_bill_header.bill_date),'-',YEAR(t_sell_bill_header.bill_date)) as  month FROM t_sell_bill_header, m_franchisee WHERE t_sell_bill_header.bill_date BETWEEN :fromDate "
			+" AND :toDate AND t_sell_bill_header.fr_id IN(:frId) AND m_franchisee.fr_id=t_sell_bill_header.fr_id GROUP BY"
					+" MONTH(t_sell_bill_header.bill_date),t_sell_bill_header.fr_id   "
					+ ""
					+ " UNION ALL" + 
					"       SELECT t_sp_cake.sp_delivery_date as bill_date," + 
					"       t_sp_cake.sp_order_no as bill_no," + 
					"       t_sp_cake.fr_id," + 
					"       sum(t_sp_cake.sp_grand_total) as cash," + 
					"       0 as card," + 
					"       0 as other," + 
					"       m_franchisee.fr_name," + 
					"         CONCAT(MONTHNAME(t_sp_cake.sp_delivery_date)," + 
					"        '-'," + 
					"        YEAR(t_sp_cake.sp_delivery_date)) as  month " + 
					"       from t_sp_cake,m_franchisee" + 
					"        WHERE" + 
					"        t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate and t_sp_cake.sp_book_for_mob_no != '0' " + 
					"        AND t_sp_cake.fr_id IN(" + 
					"           :frId) " + 
					"        AND m_franchisee.fr_id=t_sp_cake.fr_id " + 
					"    GROUP BY" + 
					"       MONTH(t_sp_cake.sp_delivery_date)," + 
					"        t_sp_cake.fr_id"
					+ ""
					+ ""
*/
//List<GetRepMonthwiseSell> getRepFrMonthwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);