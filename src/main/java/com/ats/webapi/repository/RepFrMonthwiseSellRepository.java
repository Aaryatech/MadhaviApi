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
			"        bill_date,\r\n" + 
			"        sell_bill_no,\r\n" + 
			"        fr_id,\r\n" + 
			"        cash,\r\n" + 
			"        card,\r\n" + 
			"        other,\r\n" + 
			"        fr_name,\r\n" + 
			"        month,\r\n" + 
			"        discount_amt,\r\n" + 
			"        pending_amt,\r\n" + 
			"        adv_amt,\r\n" + 
			"        COALESCE(b.ch_amt,\r\n" + 
			"        0) AS regular,\r\n" + 
			"        COALESCE(c.ch_amt,\r\n" + 
			"        0) AS chalan,\r\n" + 
			"        COALESCE(withdrawal_amt, 0) AS withdrawal_amt,\r\n" + 
			"   		COALESCE(e.credit_note_total_amt, 0) AS credit_note_total_amt\r\n" + 
			"    FROM\r\n" + 
			"        (          SELECT\r\n" + 
			"            t_sell_bill_header.bill_date ,\r\n" + 
			"            t_sell_bill_header.sell_bill_no ,\r\n" + 
			"            t_sell_bill_header.fr_id,\r\n" + 
			"            sum(CASE                       \r\n" + 
			"                WHEN t_sell_bill_header.payment_mode = 1 THEN t_sell_bill_header.payable_amt                       \r\n" + 
			"                ELSE 0                   \r\n" + 
			"            END) as cash,\r\n" + 
			"            sum(CASE                       \r\n" + 
			"                WHEN t_sell_bill_header.payment_mode = 2 THEN t_sell_bill_header.payable_amt                       \r\n" + 
			"                ELSE 0                   \r\n" + 
			"            END) as card ,\r\n" + 
			"            sum(CASE                       \r\n" + 
			"                WHEN t_sell_bill_header.payment_mode = 3 THEN t_sell_bill_header.payable_amt                       \r\n" + 
			"                ELSE 0                   \r\n" + 
			"            END) as other,\r\n" + 
			"            m_franchisee.fr_name,\r\n" + 
			"            CONCAT(MONTHNAME(t_sell_bill_header.bill_date),\r\n" + 
			"            '-',\r\n" + 
			"            YEAR(t_sell_bill_header.bill_date)) as  month,\r\n" + 
			"            t_sell_bill_header.discount_amt,\r\n" + 
			"            t_sell_bill_header.remaining_amt AS pending_amt,\r\n" + 
			"            t_sell_bill_header.paid_amt AS adv_amt              \r\n" + 
			"        FROM\r\n" + 
			"            t_sell_bill_header,\r\n" + 
			"            m_franchisee               \r\n" + 
			"        WHERE\r\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate                 \r\n" + 
			"            AND t_sell_bill_header.fr_id IN(:frId)                   \r\n" + 
			"            AND m_franchisee.fr_id=t_sell_bill_header.fr_id               \r\n" + 
			"        GROUP BY\r\n" + 
			"            MONTH(t_sell_bill_header.bill_date),\r\n" + 
			"            t_sell_bill_header.fr_id) a                                     \r\n" + 
			"    LEFT JOIN\r\n" + 
			"        (\r\n" + 
			"            SELECT\r\n" + 
			"                exp_date,\r\n" + 
			"                exp_type,\r\n" + 
			"                SUM(ch_amt) ch_amt                    \r\n" + 
			"            FROM\r\n" + 
			"                m_expense                    \r\n" + 
			"            WHERE\r\n" + 
			"                fr_id IN(:frId )                   \r\n" + 
			"                AND exp_type = 1                    \r\n" + 
			"            GROUP BY\r\n" + 
			"                exp_date                    \r\n" + 
			"            ORDER BY\r\n" + 
			"                exp_date            \r\n" + 
			"        ) b                \r\n" + 
			"            ON      a.bill_date = b.exp_date        \r\n" + 
			"    LEFT JOIN\r\n" + 
			"        (\r\n" + 
			"            SELECT\r\n" + 
			"                exp_date,\r\n" + 
			"                exp_type,\r\n" + 
			"                SUM(ch_amt) ch_amt                    \r\n" + 
			"            FROM\r\n" + 
			"                m_expense                    \r\n" + 
			"            WHERE\r\n" + 
			"                fr_id IN(:frId )                   \r\n" + 
			"                AND exp_type = 2                    \r\n" + 
			"            GROUP BY\r\n" + 
			"                exp_date                    \r\n" + 
			"            ORDER BY\r\n" + 
			"                exp_date            \r\n" + 
			"        ) c                \r\n" + 
			"            ON      a.bill_date = c.exp_date\r\n" + 
			"            LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        SUM(withdrawal_amt) withdrawal_amt,\r\n" + 
			"        DATE\r\n" + 
			"    FROM\r\n" + 
			"        t_pettycash_mgmnt petty\r\n" + 
			"    WHERE\r\n" + 
			"        DATE BETWEEN :fromDate AND :toDate AND fr_id = :frId\r\n" + 
			"    GROUP BY\r\n" + 
			"        DATE\r\n" + 
			"    ORDER BY\r\n" + 
			"        DATE\r\n" + 
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
			"        t_credit_note_pos.bill_no = t_sell_bill_header.sell_bill_no AND t_credit_note_pos.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_pos.del_status = 0 AND t_sell_bill_header.fr_id = :frId\r\n" + 
			"    GROUP BY\r\n" + 
			"        t_credit_note_pos.crn_date\r\n" + 
			") e\r\n" + 
			"ON\r\n" + 
			"    a.bill_date = e.crn_date",nativeQuery=true)
			List<GetMonthWiseReport> getRepFrMonthwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
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