package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.GetRepFrDatewiseSell;
import com.ats.webapi.model.report.GetRepFrDatewiseSellReport;
 

//GetRepFrDatewiseSell
public interface RepFrDatewiseSellRepository extends JpaRepository<GetRepFrDatewiseSellReport, Integer>{
	
/*@Query(value="SELECT day,bill_date,sell_bill_no,fr_id,cash,card,other,fr_name\r\n" + 
		"       from\r\n" + 
		"        (SELECT DAYNAME(t_sell_bill_header.bill_date) as day,\r\n" + 
		"        t_sell_bill_header.bill_date ,\r\n" + 
		"        t_sell_bill_header.sell_bill_no ,\r\n" + 
		"        t_sell_bill_header.fr_id,\r\n" + 
		"        sum(CASE \r\n" + 
		"            WHEN t_sell_bill_header.payment_mode = 1 THEN t_sell_bill_header.payable_amt \r\n" + 
		"            ELSE 0 \r\n" + 
		"        END) as cash,\r\n" + 
		"        sum(CASE \r\n" + 
		"            WHEN t_sell_bill_header.payment_mode = 2 THEN t_sell_bill_header.payable_amt \r\n" + 
		"            ELSE 0 \r\n" + 
		"        END) as card ,\r\n" + 
		"        sum(CASE \r\n" + 
		"            WHEN t_sell_bill_header.payment_mode = 3 THEN t_sell_bill_header.payable_amt \r\n" + 
		"            ELSE 0 \r\n" + 
		"        END) as other,\r\n" + 
		"        m_franchisee.fr_name \r\n" + 
		"    FROM\r\n" + 
		"        t_sell_bill_header,\r\n" + 
		"        m_franchisee \r\n" + 
		"    WHERE\r\n" + 
		"        t_sell_bill_header.bill_date BETWEEN  :fromDate AND  :toDate  \r\n" + 
		"        AND t_sell_bill_header.fr_id IN(\r\n" + 
		"        :frId  \r\n" + 
		"        ) \r\n" + 
		"        AND m_franchisee.fr_id=t_sell_bill_header.fr_id \r\n" + 
		"    GROUP BY\r\n" + 
		"        t_sell_bill_header.bill_date,\r\n" + 
		"        t_sell_bill_header.fr_id  \r\n" + 
		"    UNION\r\n" + 
		"    ALL       SELECT\r\n" + 
		"        concat(DAYNAME(t_sp_cake.sp_delivery_date),\r\n" + 
		"        '-',\r\n" + 
		"        'SP') as day,\r\n" + 
		"        t_sp_cake.sp_delivery_date as bill_date,\r\n" + 
		"        t_sp_cake.sp_order_no as bill_no,\r\n" + 
		"        t_sp_cake.fr_id,\r\n" + 
		"        sum(t_sp_cake.sp_grand_total) as cash,\r\n" + 
		"        0 as card,\r\n" + 
		"        0 as other,\r\n" + 
		"        m_franchisee.fr_name       \r\n" + 
		"    from\r\n" + 
		"        t_sp_cake,\r\n" + 
		"        m_franchisee        \r\n" + 
		"    WHERE\r\n" + 
		"        t_sp_cake.sp_delivery_date BETWEEN :fromDate AND  :toDate       \r\n" + 
		"        AND t_sp_cake.fr_id IN(\r\n" + 
		"       :frId  \r\n" + 
		"        )         \r\n" + 
		"        AND m_franchisee.fr_id=t_sp_cake.fr_id     \r\n" + 
		"    GROUP BY\r\n" + 
		"        t_sp_cake.sp_delivery_date,\r\n" + 
		"        t_sp_cake.fr_id) a order by bill_date",nativeQuery=true)*/
	
	@Query(value="SELECT\r\n" + 
			"    day,\r\n" + 
			"    bill_date,\r\n" + 
			"    sell_bill_no,\r\n" + 
			"    fr_id,\r\n" + 
			"    grand_total,\r\n" + 
			"    discount_amt,\r\n" + 
			"    pending_amt,\r\n" + 
			"    COALESCE(adv_amt,0) as adv_amt,\r\n" + 
			"    cash,\r\n" + 
			"    card,\r\n" + 
			"    other,\r\n" + 
			"    COALESCE(b.ch_amt, 0) AS regular,\r\n" + 
			"    COALESCE(c.ch_amt, 0) AS chalan,\r\n" + 
			"    fr_name,\r\n" + 
			"    COALESCE(withdrawal_amt, 0) AS withdrawal_amt ,\r\n" + 
			"    COALESCE(e.credit_note_total_amt, 0) AS credit_note_total_amt,\r\n" + 
			"    round_off \r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        DAYNAME(t_sell_bill_header.bill_date) AS DAY,\r\n" + 
			"        t_sell_bill_header.bill_date,\r\n" + 
			"        t_sell_bill_header.sell_bill_no,\r\n" + 
			"        t_sell_bill_header.fr_id,\r\n" +
			"        SUM(t_sell_bill_header.grand_total) AS grand_total,\r\n" + 
			"        SUM(t_sell_bill_header.discount_amt) AS discount_amt,\r\n" + 
			"        SUM(t_sell_bill_header.remaining_amt) AS pending_amt,\r\n" + 
			"        SUM(t_transaction_detail.cash_amt) AS cash,\r\n" + 
			"        SUM(t_transaction_detail.card_amt) AS card,\r\n" + 
			"        SUM(t_transaction_detail.e_pay_amt) AS other,\r\n" + 
			"        SUM(t_sell_bill_header.ext_float1) AS round_off,\r\n" + 
			"        m_franchisee.fr_name\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header,\r\n" + 
			"        t_transaction_detail,\r\n" + 
			"        m_franchisee\r\n" + 
			"    WHERE\r\n" + 
			"        t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_transaction_detail.sell_bill_no = t_sell_bill_header.sell_bill_no AND t_sell_bill_header.del_status = 0 AND t_sell_bill_header.fr_id IN(:frId) AND m_franchisee.fr_id = t_sell_bill_header.fr_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        bill_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        bill_date DESC\r\n" + 
			") a\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        exp_date,\r\n" + 
			"        exp_type,\r\n" + 
			"        SUM(ch_amt) ch_amt\r\n" + 
			"    FROM\r\n" + 
			"        m_expense\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frId) AND exp_type = 1\r\n" + 
			"    GROUP BY\r\n" + 
			"        exp_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        exp_date DESC\r\n" + 
			") b\r\n" + 
			"ON\r\n" + 
			"    a.bill_date = b.exp_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        exp_date,\r\n" + 
			"        exp_type,\r\n" + 
			"        SUM(ch_amt) ch_amt\r\n" + 
			"    FROM\r\n" + 
			"        m_expense\r\n" + 
			"    WHERE\r\n" + 
			"        fr_id IN(:frId) AND exp_type = 2 AND del_status=0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        exp_date\r\n" + 
			"    ORDER BY\r\n" + 
			"        exp_date DESC\r\n" + 
			") c\r\n" + 
			"ON\r\n" + 
			"    a.bill_date = c.exp_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        SUM(withdrawal_amt) withdrawal_amt,\r\n" + 
			"        DATE\r\n" + 
			"    FROM\r\n" + 
			"        t_pettycash_mgmnt petty\r\n" + 
			"    WHERE\r\n" + 
			"        DATE BETWEEN :fromDate AND :toDate AND fr_id =:frId\r\n" + 
			"    GROUP BY\r\n" + 
			"        DATE\r\n" + 
			"    ORDER BY\r\n" + 
			"        DATE DESC\r\n" + 
			") d\r\n" + 
			"ON\r\n" + 
			"    d.date = a.bill_date\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        t_credit_note_pos.crn_date,\r\n" + 
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
			"        t_credit_note_pos.bill_no = t_sell_bill_header.sell_bill_no AND t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.del_status = 0 AND t_sell_bill_header.fr_id=:frId AND t_sell_bill_header.del_status = 0 \r\n" + 
			"    GROUP BY\r\n" + 
			"        t_credit_note_pos.crn_date ORDER BY t_credit_note_pos.crn_date DESC\r\n" + 
			") e\r\n" + 
			"ON\r\n" + 
			"    a.bill_date = e.crn_date" +
			" LEFT JOIN\r\n" + 
			"     (SELECT\r\n" + 
			"          		order_date,\r\n" + 
			"                SUM(advance_amt) AS adv_amt\r\n" + 
			"  FROM\r\n" + 
			"                t_adv_order_header\r\n" + 
			"  WHERE\r\n" + 
			"                order_date BETWEEN :fromDate AND :toDate AND \r\n" + 
			"                fr_id = :frId AND \r\n" + 
			"                del_status = 0\r\n" + 
			"  GROUP BY\r\n" + 
			"            order_date\r\n" + 
			"  ORDER BY\r\n" + 
			"            order_date DESC) f \r\n" + 
			"  ON f.order_date=a.bill_date  ORDER BY bill_date",nativeQuery=true)
List<GetRepFrDatewiseSellReport> getRepFrDatewiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);


}


//22-02-2020 mahendra

/*SELECT\r\n" + 
"            DAYNAME(t_sell_bill_header.bill_date) as day,\r\n" + 
"            t_sell_bill_header.bill_date ,\r\n" + 
"            t_sell_bill_header.sell_bill_no ,\r\n" + 
"            t_sell_bill_header.fr_id,\r\n" + 
"            SUM(t_transaction_detail.cash_amt) as cash,\r\n" + 
"              SUM(t_transaction_detail.card_amt)   as card ,\r\n" + 
"              SUM(t_transaction_detail.e_pay_amt) as other,\r\n" + 
"              m_franchisee.fr_name                 \r\n" + 
"        FROM\r\n" + 
"            t_sell_bill_header,t_transaction_detail,\r\n" + 
"            m_franchisee                 \r\n" + 
"        WHERE\r\n" + 
"            t_sell_bill_header.bill_date BETWEEN :fromDate AND  :toDate    and t_transaction_detail.sell_bill_no=t_sell_bill_header.sell_bill_no       and t_sell_bill_header.del_status=0                    \r\n" + 
"            AND t_sell_bill_header.fr_id IN(\r\n" + 
"              :frId                            \r\n" + 
"            )                         \r\n" + 
"            AND m_franchisee.fr_id=t_sell_bill_header.fr_id     group by\r\n" + 
"    bill_date  \r\n" + 
"order by\r\n" + 
"    bill_date              \r\n" + 
"   "*/
