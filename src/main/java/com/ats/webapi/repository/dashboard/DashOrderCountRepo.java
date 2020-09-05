package com.ats.webapi.repository.dashboard;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.dashboard.DashOrderCount;

public interface DashOrderCountRepo extends JpaRepository<DashOrderCount, Integer>{
	
	
	@Query(value="SELECT\r\n" + 
			"    t1.id,\r\n" + 
			"    t2.pending,\r\n" + 
			"    t3.accepted,\r\n" + 
			"    t4.process,\r\n" + 
			"    t5.delivery_pending,\r\n" + 
			"    t6.delivered,\r\n" + 
			"    t7.rejected,\r\n" + 
			"    t8.returned,\r\n" + 
			"    t9.cancelled,\r\n" + 
			"    t10.cash_amt,\r\n" + 
			"    t10.card_amt,\r\n" + 
			"    t10.e_pay_amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"SELECT\r\n" + 
			"    1 AS id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS pending\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.order_status = 1 AND h.fr_id = :frId\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.id = t2.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS accepted\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.order_status = 2 AND h.fr_id = :frId\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.id = t3.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS process\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 3 AND h.fr_id = :frId\r\n" + 
			") t4\r\n" + 
			"ON\r\n" + 
			"    t1.id = t4.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS delivery_pending\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 4 AND h.fr_id = :frId\r\n" + 
			") t5\r\n" + 
			"ON\r\n" + 
			"    t1.id = t5.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS delivered\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 5 AND h.fr_id = :frId\r\n" + 
			") t6\r\n" + 
			"ON\r\n" + 
			"    t1.id = t6.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS rejected\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 6 AND h.fr_id = :frId\r\n" + 
			") t7\r\n" + 
			"ON\r\n" + 
			"    t1.id = t7.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS returned\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 7 AND h.fr_id = :frId\r\n" + 
			") t8\r\n" + 
			"ON\r\n" + 
			"    t1.id = t8.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS cancelled\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.delivery_date BETWEEN :fromDate AND :toDate AND h.order_status = 8 AND h.fr_id = :frId\r\n" + 
			") t9\r\n" + 
			"ON\r\n" + 
			"    t1.id = t9.id\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COALESCE(SUM(td.cash_amt),\r\n" + 
			"        0) AS cash_amt,\r\n" + 
			"        COALESCE(SUM(td.card_amt),\r\n" + 
			"        0) AS card_amt,\r\n" + 
			"        COALESCE(SUM(td.e_pay_amt),\r\n" + 
			"        0) AS e_pay_amt\r\n" + 
			"    FROM\r\n" + 
			"        t_transaction_detail td,\r\n" + 
			"        t_sell_bill_header bh\r\n" + 
			"    WHERE\r\n" + 
			"        td.transaction_date BETWEEN :fromDate AND :toDate AND td.sell_bill_no = bh.sell_bill_no AND bh.fr_id = :frId AND td.del_status = 0 AND bh.ext_int2 > 0\r\n" + 
			") t10\r\n" + 
			"ON\r\n" + 
			"    t1.id = t10.id",nativeQuery=true)
	DashOrderCount getOrderDashCount(@Param("fromDate")String fromDate,@Param("toDate")String toDate,@Param("frId")int frId);

	
	@Query(value="SELECT\r\n" + 
			"    t1.id,\r\n" + 
			"    t2.pending,\r\n" + 
			"    0 as accepted,\r\n" + 
			"    0 as process,\r\n" + 
			"    0 as delivery_pending,\r\n" + 
			"    0 as delivered,\r\n" + 
			"    0 as rejected,\r\n" + 
			"    0 as returned,\r\n" + 
			"    0 as cancelled,\r\n" + 
			"    0 as cash_amt,\r\n" + 
			"    0 as card_amt,\r\n" + 
			"    0 as e_pay_amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"SELECT\r\n" + 
			"    1 AS id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS id,\r\n" + 
			"        COUNT(*) AS pending\r\n" + 
			"    FROM\r\n" + 
			"        tn_order_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.del_status = 0 AND h.order_status = 1 AND h.fr_id = :frId\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.id = t2.id ",nativeQuery=true)
	DashOrderCount getOnlyPendingCountByFr(@Param("frId")int frId);

	
}
