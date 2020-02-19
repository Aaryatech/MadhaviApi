package com.ats.webapi.repositories;

import java.util.List;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.bill.GetBillListByFrIdToSettle;

public interface GetBillListByFrIdToSettleRepo extends JpaRepository<GetBillListByFrIdToSettle, Integer>{

	
	@Query(value = "SELECT\n" + 
			"    t_bill_transcation.bill_trans_id,\n" + 
			"    t_bill_transcation.bill_no,\n" + 
			"    t_bill_transcation.bill_head_id,\n" + 
			"    t_bill_transcation.bill_date,\n" + 
			"    t_bill_transcation.fr_id,\n" + 
			"    t_bill_transcation.bill_amt,\n" + 
			"    SUM(t_bill_transcation.paid_amt) as paid_amt,\n" + 
			"    (( t_bill_transcation.bill_amt) -  SUM(t_bill_transcation.paid_amt)) as pending_amt,\n" + 
			"    m_franchisee.fr_name\n" + 
			"FROM\n" + 
			"    t_bill_transcation,\n" + 
			"    m_franchisee\n" + 
			"WHERE\n" + 
			"    t_bill_transcation.fr_id = m_franchisee.fr_id AND t_bill_transcation.del_status = 0 AND t_bill_transcation.fr_id = :frId AND t_bill_transcation.is_closed=0\n" + 
			"GROUP BY\n" + 
			"    t_bill_transcation.bill_head_id",nativeQuery=true)

	public List<GetBillListByFrIdToSettle> getBills(@Param("frId") int frId);

}
