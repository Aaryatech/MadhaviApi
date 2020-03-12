package com.ats.webapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.AdminInvoiceIssued;

public interface AdminInvoiceIssuedRepo extends JpaRepository<AdminInvoiceIssued, Integer>{

	/*@Query(value = "SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id,0) as fr_id, t1.from_invoice, t1.to_invoice, COALESCE(t1.total_number, 0) AS total_number,\r\n" + 
			"    COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 0\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 0 AND h.del_status = 1\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.temp_id = t2.temp_id\r\n" + 
			"UNION\r\n" + 
			"SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id,0) as fr_id, t1.from_invoice, t1.to_invoice, t1.total_number, COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate \r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 1\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t2.fr_id", nativeQuery = true)
	List<AdminInvoiceIssued> getInvoicesIssued(@Param("fromDate") String fromDate, @Param("toDate") String toDate);*/

	
	
	@Query(value = "SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id, 0) AS fr_id,\r\n" + 
			"    t1.from_invoice,\r\n" + 
			"    t1.to_invoice,\r\n" + 
			"    COALESCE(t1.total_number, 0) AS total_number,\r\n" + 
			"    COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 1\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 1 AND h.del_status = 1\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.temp_id = t2.temp_id\r\n" + 
			"UNION\r\n" + 
			"SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id, 0) AS fr_id,\r\n" + 
			"    t1.from_invoice,\r\n" + 
			"    t1.to_invoice,\r\n" + 
			"    COALESCE(t1.total_number, 0) AS total_number,\r\n" + 
			"    COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 0\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.ex_varchar2 = 0 AND h.del_status = 1\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.temp_id = t2.temp_id\r\n" + 
			"UNION\r\n" + 
			"SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id, 0) AS fr_id,\r\n" + 
			"    t1.from_invoice,\r\n" + 
			"    t1.to_invoice,\r\n" + 
			"    t1.total_number,\r\n" + 
			"    COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 1\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t2.fr_id", nativeQuery = true)
	List<AdminInvoiceIssued> getInvoicesIssued(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	
	@Query(value = "SELECT\r\n" + 
			"    UUID() AS id, COALESCE(t1.fr_id, 0) AS fr_id,\r\n" + 
			"    t1.from_invoice,\r\n" + 
			"    t1.to_invoice,\r\n" + 
			"    t1.total_number,\r\n" + 
			"    COALESCE(t2.total_deleted, 0) AS total_deleted,\r\n" + 
			"    COALESCE(t2.deleted_invoice, '') AS deleted_invoice,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"            t1.total_number - t2.total_deleted\r\n" + 
			"        ),\r\n" + 
			"        0\r\n" + 
			"    ) AS net_issued\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        MIN(h.invoice_no) AS from_invoice,\r\n" + 
			"        MAX(h.invoice_no) AS to_invoice,\r\n" + 
			"        COUNT(h.invoice_no) AS total_number\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.fr_id = :frId\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.fr_id\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        1 AS temp_id,\r\n" + 
			"        h.fr_id,\r\n" + 
			"        COUNT(*) AS total_deleted,\r\n" + 
			"        GROUP_CONCAT(h.invoice_no SEPARATOR ', ') AS deleted_invoice\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header h\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_date BETWEEN :fromDate AND :toDate AND h.del_status = 1 AND h.fr_id = :frId\r\n" + 
			") t2\r\n" + 
			"ON\r\n" + 
			"    t1.fr_id = t2.fr_id", nativeQuery = true)
	List<AdminInvoiceIssued> getInvoicesIssuedForFr(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("frId") int frId);
	

}
