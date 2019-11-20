package com.ats.webapi.repository.taxreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.taxreport.Tax2Report;

@Repository
public interface Tax2ReportRepository extends JpaRepository<Tax2Report, Integer> {

	@Query(value = "  select\n" + 
			"        t_bill_header.invoice_no,\n" + 
			"        t_bill_header.bill_date,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        t_bill_header.bill_no,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.taxable_amt) \n" + 
			"        FROM\n" + 
			"            t_bill_detail \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=0),\n" + 
			"        0),\n" + 
			"        2) AS taxable_amt_zero,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.taxable_amt) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=5),\n" + 
			"        0),\n" + 
			"        2) AS taxable_amt_five,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.taxable_amt) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=12),\n" + 
			"        0),\n" + 
			"        2) AS taxable_amt_twelve,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.taxable_amt) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=18),\n" + 
			"        0),\n" + 
			"        2) AS taxable_amt_eighteen,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.taxable_amt) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=28),\n" + 
			"        0),\n" + 
			"        2) AS taxable_amt_twenty_eight,\n" + 
			"        t_bill_header.taxable_amt,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=0),\n" + 
			"        0),\n" + 
			"        2) AS sgst_amt_zero,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=5),\n" + 
			"        0),\n" + 
			"        2) AS sgst_amt_five,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=12),\n" + 
			"        0),\n" + 
			"        2) AS sgst_amt_twelve,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=18),\n" + 
			"        0),\n" + 
			"        2) AS sgst_amt_eighteen,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=28),\n" + 
			"        0),\n" + 
			"        2) AS sgst_amt_twenty_eight,\n" + 
			"        t_bill_header.sgst_sum,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=0),\n" + 
			"        0),\n" + 
			"        2) AS cgst_amt_zero,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=5),\n" + 
			"        0),\n" + 
			"        2) AS cgst_amt_five,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=12),\n" + 
			"        0),\n" + 
			"        2) AS cgst_amt_twelve,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=18),\n" + 
			"        0),\n" + 
			"        2) AS cgst_amt_eighteen,\n" + 
			"        ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
			"            and t_bill_detail.cgst_per+sgst_per=28),\n" + 
			"        0),\n" + 
			"        2) AS cgst_amt_twenty_eight,\n" + 
			"        t_bill_header.cgst_sum  \n" + 
			"    from\n" + 
			"        t_bill_header,\n" + 
			"        m_franchisee \n" + 
			"    where\n" + 
			"        t_bill_header.bill_date BETWEEN :fromDate AND :toDate \n" + 
			"        AND m_franchisee.fr_id=t_bill_header.fr_id  AND  t_bill_header.ex_varchar1 IN(:temp)   \n" + 
			"    order by\n" + 
			"        t_bill_header.bill_no \n" + 
			"\n"
			+ "", nativeQuery = true)
	List<Tax2Report> getTax2Report12(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	@Query(value = " SELECT\n" + 
			"    a.invoice_no,\n" + 
			"    a.bill_date,\n" + 
			"    a.fr_name,\n" + 
			"    a.fr_gst_no,\n" + 
			"    a.bill_no,\n" + 
			"    a.taxable_amt_zero,\n" + 
			"    a.taxable_amt_five,\n" + 
			"    a.taxable_amt_twelve,\n" + 
			"    a.taxable_amt_eighteen,\n" + 
			"    a.taxable_amt_twenty_eight,\n" + 
			"    a.taxable_amt,\n" + 
			"    a.sgst_amt_zero,\n" + 
			"    a.sgst_amt_five,\n" + 
			"    a.sgst_amt_twelve,\n" + 
			"    a.sgst_amt_eighteen,\n" + 
			"    a.sgst_amt_twenty_eight,\n" + 
			"    a.sgst_sum,\n" + 
			"    a.cgst_amt_twenty_eight,\n" + 
			"    a.cgst_amt_zero,\n" + 
			"    a.cgst_amt_five,\n" + 
			"    a.cgst_amt_twelve,\n" + 
			"    a.cgst_amt_eighteen,\n" + 
			"    a.cgst_sum\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_bill_header.invoice_no,\n" + 
			"        t_bill_header.bill_date,\n" + 
			"        m_franchisee.fr_name,\n" + 
			"        m_franchisee.fr_gst_no,\n" + 
			"        t_bill_header.bill_no,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.taxable_amt)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 0\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt_zero,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.taxable_amt)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 5\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt_five,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.taxable_amt)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 12\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt_twelve,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.taxable_amt)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 18\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt_eighteen,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.taxable_amt)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 28\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS taxable_amt_twenty_eight,\n" + 
			"        t_bill_header.taxable_amt,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.sgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 0\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS sgst_amt_zero,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.sgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 5\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS sgst_amt_five,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.sgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 12\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS sgst_amt_twelve,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.sgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 18\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS sgst_amt_eighteen,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.sgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 28\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS sgst_amt_twenty_eight,\n" + 
			"        t_bill_header.sgst_sum,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.cgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 0\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS cgst_amt_zero,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.cgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 5\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS cgst_amt_five,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.cgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 12\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS cgst_amt_twelve,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.cgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 18\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS cgst_amt_eighteen,\n" + 
			"        ROUND(\n" + 
			"            COALESCE(\n" + 
			"                (\n" + 
			"                SELECT\n" + 
			"                    SUM(t_bill_detail.cgst_rs)\n" + 
			"                FROM\n" + 
			"                    t_bill_detail\n" + 
			"                WHERE\n" + 
			"                    t_bill_header.bill_no = t_bill_detail.bill_no AND t_bill_detail.cgst_per + sgst_per = 28\n" + 
			"            ),\n" + 
			"            0\n" + 
			"            ),\n" + 
			"            2\n" + 
			"        ) AS cgst_amt_twenty_eight,\n" + 
			"        t_bill_header.cgst_sum\n" + 
			"    FROM\n" + 
			"        t_bill_header,\n" + 
			"        m_franchisee\n" + 
			"    WHERE\n" + 
			"        t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_bill_header.fr_id AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
			"    UNION ALL\n" + 
			"SELECT\n" + 
			"    t_sell_bill_header.invoice_no,\n" + 
			"    t_sell_bill_header.bill_date,\n" + 
			"    m_franchisee.fr_name,\n" + 
			"    m_franchisee.fr_gst_no,\n" + 
			"    t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 0\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt_zero,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 5\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt_five,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 12\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt_twelve,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 18\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt_eighteen,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 28\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS taxable_amt_twenty_eight,\n" + 
			"    t_sell_bill_header.taxable_amt,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 0\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt_zero,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 5\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt_five,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 12\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt_twelve,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 18\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt_eighteen,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 28\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS sgst_amt_twenty_eight,\n" + 
			"   ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_sell_bill_detail.sgst_rs) \n" + 
			"        FROM\n" + 
			"            t_sell_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no \n" + 
			"            GROUP By t_sell_bill_detail.sell_bill_no  ),\n" + 
			"        0),\n" + 
			"        2) AS sgst_sum,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 0\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt_zero,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 5\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt_five,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 12\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt_twelve,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 18\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt_eighteen,\n" + 
			"    ROUND(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)\n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail\n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND t_sell_bill_detail.cgst_per + sgst_per = 28\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        ),\n" + 
			"        2\n" + 
			"    ) AS cgst_amt_twenty_eight,\n" + 
			"   ROUND(COALESCE((SELECT\n" + 
			"            SUM(t_sell_bill_detail.cgst_rs) \n" + 
			"        FROM\n" + 
			"            t_sell_bill_detail   \n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no \n" + 
			"            GROUP By t_sell_bill_detail.sell_bill_no  ),\n" + 
			"        0),\n" + 
			"        2) AS cgst_sum\n" + 
			"FROM\n" + 
			"    t_sell_bill_header,\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id = t_sell_bill_header.fr_id  \n" + 
			") a " + 
			"ORDER BY " + 
			"    a.bill_no ", nativeQuery = true)
	List<Tax2Report> getTax2ReportAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("temp") List<Integer> temp);
	
	
	
	
	@Query(value = "SELECT\n" + 
			"            t_sell_bill_header.invoice_no,\n" + 
			"            t_sell_bill_header.bill_date,\n" + 
			"            m_franchisee.fr_name,\n" + 
			"            m_franchisee.fr_gst_no,\n" + 
			"            t_sell_bill_header.sell_bill_no AS bill_no,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 0         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS taxable_amt_zero,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 5         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS taxable_amt_five,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 12         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS taxable_amt_twelve,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 18         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS taxable_amt_eighteen,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.taxable_amt)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 28         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS taxable_amt_twenty_eight,\n" + 
			"            t_sell_bill_header.taxable_amt,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 0         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS sgst_amt_zero,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 5         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS sgst_amt_five,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 12         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS sgst_amt_twelve,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 18         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS sgst_amt_eighteen,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 28         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS sgst_amt_twenty_eight,\n" + 
			"            ROUND(COALESCE((SELECT\n" + 
			"                SUM(t_sell_bill_detail.sgst_rs)          \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail            \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no              \n" + 
			"            GROUP By\n" + 
			"                t_sell_bill_detail.sell_bill_no  ),\n" + 
			"            0),\n" + 
			"            2) AS sgst_sum,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 0         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS cgst_amt_zero,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 5         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS cgst_amt_five,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 12         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS cgst_amt_twelve,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 18         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS cgst_amt_eighteen,\n" + 
			"            ROUND(         COALESCE(             (             SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)             \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail             \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no \n" + 
			"                AND t_sell_bill_detail.cgst_per + sgst_per = 28         ),\n" + 
			"            0         ),\n" + 
			"            2     ) AS cgst_amt_twenty_eight,\n" + 
			"            ROUND(COALESCE((SELECT\n" + 
			"                SUM(t_sell_bill_detail.cgst_rs)          \n" + 
			"            FROM\n" + 
			"                t_sell_bill_detail            \n" + 
			"            WHERE\n" + 
			"                t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no              \n" + 
			"            GROUP By\n" + 
			"                t_sell_bill_detail.sell_bill_no  ),\n" + 
			"            0),\n" + 
			"            2) AS cgst_sum \n" + 
			"        FROM\n" + 
			"            t_sell_bill_header,\n" + 
			"            m_franchisee \n" + 
			"        WHERE\n" + 
			"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate \n" + 
			"            AND m_franchisee.fr_id = t_sell_bill_header.fr_id   " + 
			"", nativeQuery = true)
	List<Tax2Report> getTax2Report3(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	 
	
	
	//spec fr

	@Query(value = "select t_bill_header.invoice_no,t_bill_header.bill_date,m_franchisee.fr_name,m_franchisee.fr_gst_no, t_bill_header.bill_no,ROUND(COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=0),0),2) AS taxable_amt_zero,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=5),0),2) AS taxable_amt_five,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=12),0),2) AS taxable_amt_twelve,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=18),0),2) AS taxable_amt_eighteen,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=28),0),2) AS taxable_amt_twenty_eight,\n"
			+ "t_bill_header.taxable_amt,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.sgst_rs) FROM t_bill_detail WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=0),0),2) AS sgst_amt_zero,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.sgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=5),0),2) AS sgst_amt_five,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.sgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=12),0),2) AS sgst_amt_twelve,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.sgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=18),0),2) AS sgst_amt_eighteen,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.sgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=28),0),2) AS sgst_amt_twenty_eight,\n"
			+ "t_bill_header.sgst_sum,\n" + "\n"
			+ " ROUND(COALESCE((SELECT SUM(t_bill_detail.cgst_rs) FROM t_bill_detail WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=0),0),2) AS cgst_amt_zero,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.cgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=5),0),2) AS cgst_amt_five,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.cgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=12),0),2) AS cgst_amt_twelve,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.cgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=18),0),2) AS cgst_amt_eighteen,\n"
			+ "ROUND(COALESCE((SELECT SUM(t_bill_detail.cgst_rs) FROM t_bill_detail   WHERE t_bill_header.bill_no=t_bill_detail.bill_no and t_bill_detail.cgst_per+sgst_per=28),0),2) AS cgst_amt_twenty_eight,\n"
			+ "t_bill_header.cgst_sum\n"
			+ " from  t_bill_header,m_franchisee where  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND m_franchisee.fr_id=t_bill_header.fr_id AND m_franchisee.fr_id=:frId  order by t_bill_header.bill_no\n"
			+ "", nativeQuery = true)
	List<Tax2Report> getTax2ReportByFrId(@Param("frId") int frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

}
