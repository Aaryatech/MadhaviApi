package com.ats.webapi.repository.frpurchasereport;

 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ats.webapi.model.report.frpurchase.SalesReportRoyalty;

public interface SalesReportRoyaltyRepo extends JpaRepository<SalesReportRoyalty, Integer> {
	//report 5
		@Query(value=" SELECT UUID() as uid, m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id, COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty," + 
				" COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt,\n" + 
				" COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_details.del_status=0),0) AS  t_grn_qty," + 
			
				" COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_details.del_status=0),0) AS  t_grn_taxable_amt," + 
			
				" COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_details.del_status=0),0) AS  t_gvn_qty," + 
				
				" COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_details.del_status=0),0) AS  t_gvn_taxable_amt" + 
				
				"	FROM m_item,m_category WHERE m_item.item_grp1=m_category.cat_id AND m_category.cat_id NOT IN(3,7) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name ",nativeQuery=true)
			
			List<SalesReportRoyalty> getSaleReportRoyalty(@Param("frIdList") List<String> frIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		//report 5 all fr //Ok Tested changed to credit note 11 april
		@Query(value=" SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id, COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.del_status=0),0) AS t_bill_qty," + 
				"	COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND  t_bill_header.del_status=0),0) AS  t_bill_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND  t_credit_note_details.del_status=0),0) AS  t_grn_qty," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND  t_credit_note_details.del_status=0),0) AS  t_grn_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND  t_credit_note_details.del_status=0),0) AS  t_gvn_qty," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND  t_credit_note_details.del_status=0),0) AS  t_gvn_taxable_amt " + 
				"	FROM m_item,m_category WHERE m_item.item_grp1=m_category.cat_id AND m_category.cat_id NOT IN(3,7) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
			
			List<SalesReportRoyalty> getSaleReportRoyaltyAllFr(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	 
		@Query(value="SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id,COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty,COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt,\n" + 
				"\n" + 
				"COALESCE((SELECT SUM(t_grn_gvn.grn_gvn_qty) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn=1 AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_grn_qty,\n" + 
				"					COALESCE((SELECT SUM(t_grn_gvn.taxable_amt) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn=1 AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_grn_taxable_amt, \n" + 
				"						COALESCE((SELECT SUM(t_grn_gvn.grn_gvn_qty) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn In (0,2) AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_gvn_qty,\n" + 
				"					COALESCE((SELECT SUM(t_grn_gvn.taxable_amt) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn In (0,2) AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_gvn_taxable_amt \n" + 
				"						FROM m_item,m_category where m_item.item_grp1=m_category.cat_id AND m_category.cat_id IN(:catIdList) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name \n" + 
				"				",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCat(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
		
		@Query(value="SELECT UUID() as uid," + 
				"        m_sp_cake.sp_id AS  id,\n" + 
				"        m_sp_cake.sp_name AS item_name ,\n" + 
				"        5 AS cat_id ,4 as sub_cat_id,\n" + 
				"        'Special Cake' AS cat_name,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)          \n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate            \n" + 
				"            AND t_bill_header.bill_no=t_bill_detail.bill_no              \n" + 
				"            AND m_sp_cake.sp_id=t_bill_detail.item_id               \n" + 
				"            AND t_bill_header.fr_id =m_franchisee.fr_id              \n" + 
				"            AND t_bill_detail.cat_id= :catIdList         \n" + 
				"            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList)),\n" + 
				"        0) AS t_bill_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)          \n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate             \n" + 
				"            AND t_bill_header.bill_no=t_bill_detail.bill_no              \n" + 
				"            AND m_sp_cake.sp_id=t_bill_detail.item_id              \n" + 
				"            AND t_bill_header.fr_id  =m_franchisee.fr_id             \n" + 
				"            AND t_bill_detail.cat_id IN(:catIdList)             \n" + 
				"            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList)),\n" + 
				"        0) AS  t_bill_taxable_amt,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)          \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate             \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id              \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id               \n" + 
				"            AND t_grn_gvn_header.is_grn=1            \n" + 
				"            AND t_grn_gvn.cat_id IN (:catIdList)              \n" + 
				"            AND  t_grn_gvn.del_status=0              \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              \n" + 
				"            and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList)),\n" + 
				"        0) AS  t_grn_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)          \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate             \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id                \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id               \n" + 
				"            AND t_grn_gvn_header.is_grn=1            \n" + 
				"            AND  t_grn_gvn.cat_id IN(:catIdList)              \n" + 
				"            AND  t_grn_gvn.del_status=0              \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              \n" + 
				"            and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList)),\n" + 
				"        0) AS  t_grn_taxable_amt,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)          \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate            \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id                \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id                \n" + 
				"            AND t_grn_gvn_header.is_grn  In (0,2)           \n" + 
				"            AND t_grn_gvn.cat_id IN (:catIdList)              \n" + 
				"            AND  t_grn_gvn.del_status=0              \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              \n" + 
				"            and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList)),\n" + 
				"        0) AS  t_gvn_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)          \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee          \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate           \n" + 
				"            And t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id              \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id               \n" + 
				"            AND t_grn_gvn_header.is_grn  In (0,2)            \n" + 
				"            AND t_grn_gvn.cat_id = :catIdList            \n" + 
				"            AND  t_grn_gvn.del_status=0              \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              \n" + 
				"            and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList)),\n" + 
				"        0) AS  t_gvn_taxable_amt       \n" + 
				"    FROM\n" + 
				"        m_sp_cake                 \n" + 
				"    group by\n" + 
				"        m_sp_cake.sp_id      \n" + 
				"    order by\n" + 
				"        m_sp_cake.sp_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatForSp(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
 
	
		@Query(value="SELECT UUID() as uid," + 
				"        m_sp_cake.sp_id AS  id,\n" + 
				"        m_sp_cake.sp_name AS item_name ,m_sp_cake.sp_name AS item_name , 5 AS cat_id ,4 as sub_cat_id,'Special Cake' AS cat_name, \n" + 
				
				"         \n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty) \n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate" + 
				"            AND t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
				"            AND m_sp_cake.sp_id=t_bill_detail.item_id  \n" + 
				"            AND t_bill_header.fr_id =m_franchisee.fr_id \n" + 
				"            AND t_bill_detail.cat_id= :catIdList\n" + 
				"            AND t_bill_header.del_status=0),\n" + 
				"        0) AS t_bill_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt) \n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate \n" + 
				"            AND t_bill_header.bill_no=t_bill_detail.bill_no \n" + 
				"            AND m_sp_cake.sp_id=t_bill_detail.item_id \n" + 
				"            AND t_bill_header.fr_id  =m_franchisee.fr_id \n" + 
				"           AND t_bill_detail.cat_id IN (:catIdList) \n" + 
				"            AND t_bill_header.del_status=0),\n" + 
				"        0) AS  t_bill_taxable_amt,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty) \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id  \n" + 
				"            AND t_grn_gvn_header.is_grn=1 \n" + 
				"          AND t_grn_gvn.cat_id IN (:catIdList) \n" + 
				"            AND  t_grn_gvn.del_status=0 \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id \n" + 
				"            and t_grn_gvn.grn_gvn_status=6),\n" + 
				"        0) AS  t_grn_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt) \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id \n" + 
				"              AND m_sp_cake.sp_id=t_grn_gvn.item_id  \n" + 
				"            AND t_grn_gvn_header.is_grn=1 \n" + 
				"          AND  t_grn_gvn.cat_id IN (:catIdList) \n" + 
				"            AND  t_grn_gvn.del_status=0 \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id \n" + 
				"            and t_grn_gvn.grn_gvn_status=6),\n" + 
				"        0) AS  t_grn_taxable_amt,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty) \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate \n" + 
				"            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id \n" + 
				"              AND m_sp_cake.sp_id=t_grn_gvn.item_id   \n" + 
				"            AND t_grn_gvn_header.is_grn In (0,2) \n" + 
				"          AND t_grn_gvn.cat_id IN (:catIdList) \n" + 
				"            AND  t_grn_gvn.del_status=0 \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id \n" + 
				"            and t_grn_gvn.grn_gvn_status=6),\n" + 
				"        0) AS  t_gvn_qty,\n" + 
				"        COALESCE((SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt) \n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee \n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate \n" + 
				"            And t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id \n" + 
				"            AND m_sp_cake.sp_id=t_grn_gvn.item_id  \n" + 
				"            AND t_grn_gvn_header.is_grn In (0,2) \n" + 
				"           AND t_grn_gvn.cat_id = :catIdList\n" + 
				"            AND  t_grn_gvn.del_status=0 \n" + 
				"            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id \n" + 
				"            and t_grn_gvn.grn_gvn_status=6),\n" + 
				"        0) AS  t_gvn_taxable_amt  \n" + 
				"    FROM\n" + 
				"        m_sp_cake\n" + 
				"       \n" + 
				"   \n" + 
				"    group by\n" + 
				"        m_sp_cake.sp_id \n" + 
				"    order by\n" + 
				"        m_sp_cake.sp_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrForSpCake(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	   // UNION ALL
		
		//Only For Graph report no 10 :
		@Query(value=" SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id,m_category \n" + 
				"\n" + 
				"COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date \n" + 
				"BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND \n" + 
				"t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty,\n" + 
				"			\n" + 
				"COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date \n" + 
				"BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id \n" + 
				"AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt,\n" + 
				"		\n" + 
				"			COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_grn_qty," + 

	"	COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_grn_taxable_amt," + 
	"			COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_gvn_qty," + 
	"			COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=0 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_gvn_taxable_amt " +
				
				"FROM m_item,m_category where m_item.item_grp1=m_category.cat_id AND m_category.cat_id IN(:catIdList) and m_item.del_status=0 group by m_category.cat_id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name",nativeQuery=true)
			
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatForGraph(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id,COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty,COALESCE((SELECT SUM(t_bill_detail.grand_total) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_grn_gvn.grn_gvn_qty) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn=1 AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_grn_qty," + 
				"	COALESCE((SELECT SUM(t_grn_gvn.apr_grand_total) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn=1 AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_grn_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_grn_gvn.grn_gvn_qty) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn In (0,2) AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_gvn_qty," + 
				"	COALESCE((SELECT SUM(t_grn_gvn.apr_grand_total) FROM t_grn_gvn,t_grn_gvn_header WHERE t_grn_gvn_header.grngvn_date  BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id AND m_item.id=t_grn_gvn.item_id  AND t_grn_gvn_header.is_grn In (0,2) AND t_grn_gvn.cat_id IN(:catIdList) AND  t_grn_gvn.del_status=0 AND t_grn_gvn_header.fr_id IN(:frIdList)  and t_grn_gvn.grn_gvn_status=6),0) AS  t_gvn_taxable_amt" + 
				"	FROM m_item,m_category where m_item.item_grp1=m_category.cat_id AND m_category.cat_id IN(:catIdList) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name ",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatByGrandTotal(@Param("frIdList")List<String> frIdList,@Param("catIdList")List<String> catIdList,
				@Param("fromDate")String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid," + 
				"			      m_sp_cake.sp_id AS  id," + 
				"			    m_sp_cake.sp_name AS item_name ," + 
				"			      5 AS cat_id ,4 as sub_cat_id," + 
				"			        'Special Cake' AS cat_name," + 
				"			      COALESCE((SELECT" + 
				"				           SUM(t_bill_detail.bill_qty)          " + 
				"			       FROM" + 
				"				          t_bill_detail," + 
				"				          t_bill_header," + 
				"				          m_franchisee          " + 
				"				      WHERE" + 
				"				           t_bill_header.bill_date BETWEEN  :fromDate AND :toDate            " + 
				"				           AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id               " + 
				"				           AND t_bill_header.fr_id =m_franchisee.fr_id              " + 
				"				          AND t_bill_detail.cat_id= :catIdList         " + 
				"				            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"			       0) AS t_bill_qty," + 
				"				      COALESCE((SELECT" + 
				"				          SUM(t_bill_detail.grand_total)          " + 
				"				       FROM" + 
				"				           t_bill_detail," + 
				"				           t_bill_header," + 
				"				          m_franchisee          " + 
				"				       WHERE" + 
				"				           t_bill_header.bill_date BETWEEN  :fromDate AND :toDate             " + 
				"				           AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id              " + 
				"				           AND t_bill_header.fr_id  =m_franchisee.fr_id             " + 
				"				           AND t_bill_detail.cat_id IN (:catIdList)             " + 
				"				           AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"				       0) AS  t_bill_taxable_amt," + 
				"			       COALESCE((SELECT" + 
				"				           SUM(t_grn_gvn.grn_gvn_qty)          " + 
				"				       FROM" + 
				"				          t_grn_gvn," + 
				"				           t_grn_gvn_header," + 
				"				           m_franchisee          " + 
				"				        WHERE" + 
				"			           t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate             " + 
				"				            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id              " + 
				"			            AND m_sp_cake.sp_id=t_grn_gvn.item_id               " + 
				"				           AND t_grn_gvn_header.is_grn=1            " + 
				"				            AND t_grn_gvn.cat_id IN (:catIdList)              " + 
				"				           AND  t_grn_gvn.del_status=0              " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              " + 
				"				          and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList))," + 
				"			       0) AS  t_grn_qty," + 
				"			        COALESCE((SELECT" + 
				"				            SUM(t_grn_gvn.apr_grand_total)          " + 
				"				       FROM" + 
				"				           t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				           m_franchisee          " + 
				"				       WHERE" + 
				"				           t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate             " + 
				"				           AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id                " + 
				"			          AND m_sp_cake.sp_id=t_grn_gvn.item_id               " + 
				"			          AND t_grn_gvn_header.is_grn=1            " + 
				"				            AND  t_grn_gvn.cat_id IN (:catIdList)              " + 
				"				           AND  t_grn_gvn.del_status=0              " + 
				"				           AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              " + 
				"			          and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList))," + 
				"				       0) AS  t_grn_taxable_amt," + 
				"				       COALESCE((SELECT" + 
				"			           SUM(t_grn_gvn.grn_gvn_qty)          " + 
				"				        FROM" + 
				"				            t_grn_gvn," + 
				"				           t_grn_gvn_header," + 
				"				            m_franchisee          " + 
				"				      WHERE" + 
				"				            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate            " + 
				"				           AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id                " + 
				"				           AND m_sp_cake.sp_id=t_grn_gvn.item_id                " + 
				"				           AND t_grn_gvn_header.is_grn  In (0,2)           " + 
				"				           AND t_grn_gvn.cat_id IN (:catIdList)              " + 
				"				           AND  t_grn_gvn.del_status=0              " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              " + 
				"			            and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_qty," + 
				"				       COALESCE((SELECT" + 
				"				           SUM(t_grn_gvn.apr_grand_total)          " + 
				"				        FROM" + 
				"			            t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				           m_franchisee          " + 
				"				      WHERE" + 
				"			           t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate           " + 
				"				           And t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id              " + 
				"				            AND m_sp_cake.sp_id=t_grn_gvn.item_id               " + 
				"				           AND t_grn_gvn_header.is_grn  In (0,2)            " + 
				"				           AND t_grn_gvn.cat_id = :catIdList            " + 
				"				           AND  t_grn_gvn.del_status=0              " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id              " + 
				"			           and t_grn_gvn.grn_gvn_status=6 AND t_grn_gvn.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_taxable_amt       " + 
				"				    FROM" + 
				"				       m_sp_cake                 " + 
				"				    group by" + 
				"				       m_sp_cake.sp_id      " + 
				"				    order by" + 
				"			        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatForSpByGrandTotal(@Param("frIdList")List<String> frIdList,
				@Param("catIdList")List<String> spcats,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid,"  + 
				"				        m_sp_cake.sp_id AS  id," + 
				"				        m_sp_cake.sp_name AS item_name ,m_sp_cake.sp_name AS item_name , 5 AS cat_id ,4 as sub_cat_id,'Special Cake' AS cat_name, " + 
				"				\n" + 
				"				         " + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_bill_detail.bill_qty) " + 
				"				        FROM" + 
				"				            t_bill_detail," + 
				"				            t_bill_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate  \n" + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id  " + 
				"				            AND t_bill_header.fr_id =m_franchisee.fr_id " + 
				"				            AND t_bill_detail.cat_id= :catIdList" + 
				"				            AND t_bill_header.del_status=0)," + 
				"				        0) AS t_bill_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_bill_detail.grand_total) " + 
				"				        FROM" + 
				"				            t_bill_detail," + 
				"				            t_bill_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate " + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id " + 
				"				            AND t_bill_header.fr_id  =m_franchisee.fr_id " + 
				"				           AND t_bill_detail.cat_id IN (:catIdList) " + 
				"				            AND t_bill_header.del_status=0)," + 
				"				        0) AS  t_bill_taxable_amt," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_grn_gvn.grn_gvn_qty) " + 
				"				        FROM" + 
				"				            t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate " + 
				"				            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id " + 
				"				            AND m_sp_cake.sp_id=t_grn_gvn.item_id  " + 
				"				            AND t_grn_gvn_header.is_grn=1 " + 
				"				          AND t_grn_gvn.cat_id IN (:catIdList) " + 
				"				            AND  t_grn_gvn.del_status=0 " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id " + 
				"				            and t_grn_gvn.grn_gvn_status=6)," + 
				"				        0) AS  t_grn_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_grn_gvn.apr_grand_total) " + 
				"				        FROM" + 
				"				            t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate " + 
				"				            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id " + 
				"				              AND m_sp_cake.sp_id=t_grn_gvn.item_id  " + 
				"				            AND t_grn_gvn_header.is_grn=1 " + 
				"				          AND  t_grn_gvn.cat_id IN (:catIdList) " + 
				"				            AND  t_grn_gvn.del_status=0 " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id " + 
				"				            and t_grn_gvn.grn_gvn_status=6)," + 
				"				        0) AS  t_grn_taxable_amt," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_grn_gvn.grn_gvn_qty) " + 
				"				        FROM" + 
				"				            t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_grn_gvn_header.grngvn_date  BETWEEN  :fromDate AND :toDate " + 
				"				            AND t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id " + 
				"				              AND m_sp_cake.sp_id=t_grn_gvn.item_id   " + 
				"				            AND t_grn_gvn_header.is_grn In (0,2) " + 
				"				          AND t_grn_gvn.cat_id IN (:catIdList) " + 
				"				            AND  t_grn_gvn.del_status=0 " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id " + 
				"				            and t_grn_gvn.grn_gvn_status=6)," + 
				"				        0) AS  t_gvn_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_grn_gvn.apr_grand_total) " + 
				"				        FROM" + 
				"				            t_grn_gvn," + 
				"				            t_grn_gvn_header," + 
				"				            m_franchisee " + 
				"				        WHERE" + 
				"				            t_grn_gvn_header.grngvn_date BETWEEN  :fromDate AND :toDate " + 
				"				            And t_grn_gvn_header.grn_gvn_header_id=t_grn_gvn.grn_gvn_header_id " + 
				"				            AND m_sp_cake.sp_id=t_grn_gvn.item_id  " + 
				"				            AND t_grn_gvn_header.is_grn In (0,2) " + 
				"				           AND t_grn_gvn.cat_id = :catIdList" + 
				"				            AND  t_grn_gvn.del_status=0 " + 
				"				            AND t_grn_gvn_header.fr_id = m_franchisee.fr_id " + 
				"				            and t_grn_gvn.grn_gvn_status=6)," + 
				"				        0) AS  t_gvn_taxable_amt  " + 
				"				    FROM" + 
				"				        m_sp_cake" + 
				"				       " + 
				"				   " + 
				"				    group by" + 
				"				        m_sp_cake.sp_id " + 
				"				    order by" + 
				"				        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrForSpCakeByGrandTotal(@Param("catIdList")List<String> catIdList,
				@Param("fromDate")String fromDate,@Param("toDate") String toDate);


		@Query(value="SELECT UUID() as uid,\n" + 
				"				        m_sp_cake.sp_id AS  id, \n" + 
				"				        m_sp_cake.sp_name AS item_name ,m_sp_cake.sp_name AS item_name , 5 AS cat_id ,4 as sub_cat_id,'Special Cake' AS cat_name,  \n" + 
				"				\n" + 
				"				          \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_bill_detail.bill_qty)  \n" + 
				"				        FROM \n" + 
				"				            t_bill_detail, \n" + 
				"				            t_bill_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate + \n" + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no  \n" + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id   \n" + 
				"				            AND t_bill_header.fr_id =m_franchisee.fr_id  \n" + 
				"				            AND t_bill_detail.cat_id= :catIdList \n" + 
				"				            AND t_bill_header.del_status=0), \n" + 
				"				        0) AS t_bill_qty, \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_bill_detail.taxable_amt)  \n" + 
				"				        FROM \n" + 
				"				            t_bill_detail, \n" + 
				"				            t_bill_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate  \n" + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no  \n" + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id  \n" + 
				"				            AND t_bill_header.fr_id  =m_franchisee.fr_id  \n" + 
				"				           AND t_bill_detail.cat_id IN (:catIdList)  \n" + 
				"				            AND t_bill_header.del_status=0), \n" + 
				"				        0) AS  t_bill_taxable_amt, \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_credit_note_details.grn_gvn_qty)  \n" + 
				"				        FROM \n" + 
				"				            t_credit_note_details, \n" + 
				"				            t_credit_note_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  \n" + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n" + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id   \n" + 
				"				            AND t_credit_note_header.is_grn=1  \n" + 
				"				          AND t_credit_note_details.cat_id IN (:catIdList)  \n" + 
				"				            AND  t_credit_note_details.del_status=0  \n" + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id  \n" + 
				"				           ), \n" + 
				"				        0) AS  t_grn_qty, \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_credit_note_details.taxable_amt)  \n" + 
				"				        FROM \n" + 
				"				            t_credit_note_details, \n" + 
				"				            t_credit_note_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  \n" + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n" + 
				"				              AND m_sp_cake.sp_id=t_credit_note_details.item_id   \n" + 
				"				            AND t_credit_note_header.is_grn=1  \n" + 
				"				          AND t_credit_note_details.cat_id IN (:catIdList)  \n" + 
				"				            AND  t_credit_note_details.del_status=0  \n" + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id  \n" + 
				"				           ), \n" + 
				"				        0) AS  t_grn_taxable_amt, \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_credit_note_details.grn_gvn_qty)  \n" + 
				"				        FROM \n" + 
				"				            t_credit_note_details, \n" + 
				"				            t_credit_note_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  \n" + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n" + 
				"				              AND m_sp_cake.sp_id=t_credit_note_details.item_id    \n" + 
				"				            AND t_credit_note_header.is_grn In (0,2)  \n" + 
				"				          AND t_credit_note_details.cat_id In (:catIdList)  \n" + 
				"				            AND  t_credit_note_details.del_status=0  \n" + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id  \n" + 
				"				          ), \n" + 
				"				        0) AS  t_gvn_qty, \n" + 
				"				        COALESCE((SELECT \n" + 
				"				            SUM(t_credit_note_details.taxable_amt)  \n" + 
				"				        FROM \n" + 
				"				            t_credit_note_details, \n" + 
				"				            t_credit_note_header, \n" + 
				"				            m_franchisee  \n" + 
				"				        WHERE \n" + 
				"				            t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate  \n" + 
				"				            And t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n" + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id   \n" + 
				"				            AND t_credit_note_header.is_grn In (0,2)  \n" + 
				"				           AND t_credit_note_details.cat_id = :catIdList \n" + 
				"				            AND  t_credit_note_details.del_status=0  \n" + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id  \n" + 
				"				            ), \n" + 
				"				        0) AS  t_gvn_taxable_amt   \n" + 
				"				    FROM \n" + 
				"				        m_sp_cake \n" + 
				"				        \n" + 
				"				    \n" + 
				"				    group by \n" + 
				"				        m_sp_cake.sp_id  \n" + 
				"				    order by \n" + 
				"				        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrForSpCakeAndType2(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	

		@Query(value="SELECT				 UUID() as uid,       m_sp_cake.sp_id AS  id, " + 
				"							        m_sp_cake.sp_name AS item_name, 5 AS cat_id ,4 as sub_cat_id,'Special Cake' AS cat_name,  " + 
				"							        COALESCE((SELECT  SUM(t_bill_detail.bill_qty)  FROM   t_bill_detail, " + 
				"							            t_bill_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate " + 
				"							            AND t_bill_header.bill_no=t_bill_detail.bill_no  " + 
				"							            AND m_sp_cake.sp_id=t_bill_detail.item_id   " + 
				"							            AND t_bill_header.fr_id =m_franchisee.fr_id  " + 
				"							            AND t_bill_detail.cat_id= :catIdList " + 
				"							            AND t_bill_header.del_status=0), " + 
				"							        0) AS t_bill_qty, " + 
				"							        COALESCE((SELECT " + 
				"							            SUM(t_bill_detail.grand_total)  " + 
				"							        FROM " + 
				"							            t_bill_detail, " + 
				"							            t_bill_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate  " + 
				"							            AND t_bill_header.bill_no=t_bill_detail.bill_no  " + 
				"							            AND m_sp_cake.sp_id=t_bill_detail.item_id  " + 
				"							            AND t_bill_header.fr_id  =m_franchisee.fr_id  " + 
				"							           AND t_bill_detail.cat_id IN (:catIdList)  " + 
				"							            AND t_bill_header.del_status=0), " + 
				"							        0) AS  t_bill_taxable_amt, " + 
				"							        COALESCE((SELECT " + 
				"							            SUM(t_credit_note_details.grn_gvn_qty)  " + 
				"							        FROM " + 
				"							            t_credit_note_details, " + 
				"							            t_credit_note_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  " + 
				"							            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  " + 
				"							            AND m_sp_cake.sp_id=t_credit_note_details.item_id   " + 
				"							            AND t_credit_note_header.is_grn=1  " + 
				"							          AND t_credit_note_details.cat_id IN (:catIdList)  " + 
				"							            AND  t_credit_note_details.del_status=0  " + 
				"							            AND t_credit_note_header.fr_id = m_franchisee.fr_id  " + 
				"							           ), " + 
				"							        0) AS  t_grn_qty, " + 
				"							        COALESCE((SELECT " + 
				"							            SUM(t_credit_note_details.grn_gvn_amt)  " + 
				"							        FROM " + 
				"							            t_credit_note_details, " + 
				"							            t_credit_note_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  " + 
				"							            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  " + 
				"							              AND m_sp_cake.sp_id=t_credit_note_details.item_id   " + 
				"							            AND t_credit_note_header.is_grn=1  " + 
				"							          AND t_credit_note_details.cat_id IN (:catIdList)  " + 
				"							            AND  t_credit_note_details.del_status=0  " + 
				"							            AND t_credit_note_header.fr_id = m_franchisee.fr_id  " + 
				"							           ), " + 
				"							        0) AS  t_grn_taxable_amt, " + 
				"							        COALESCE((SELECT " + 
				"							            SUM(t_credit_note_details.grn_gvn_qty)  " + 
				"							        FROM " + 
				"							            t_credit_note_details, " + 
				"							            t_credit_note_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate  " + 
				"							            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id  " + 
				"							              AND m_sp_cake.sp_id=t_credit_note_details.item_id    " + 
				"							            AND t_credit_note_header.is_grn In (0,2)  " + 
				"							          AND t_credit_note_details.cat_id IN (:catIdList)  " + 
				"							            AND  t_credit_note_details.del_status=0  " + 
				"							            AND t_credit_note_header.fr_id = m_franchisee.fr_id  " + 
				"							          ), " + 
				"							        0) AS  t_gvn_qty, " + 
				"							        COALESCE((SELECT " + 
				"							            SUM(t_credit_note_details.grn_gvn_amt)  " + 
				"							        FROM " + 
				"							            t_credit_note_details, " + 
				"							            t_credit_note_header, " + 
				"							            m_franchisee  " + 
				"							        WHERE " + 
				"							            t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate  " + 
				"							            And t_credit_note_header.crn_id=t_credit_note_details.crn_id  " + 
				"							            AND m_sp_cake.sp_id=t_credit_note_details.item_id   " + 
				"							            AND t_credit_note_header.is_grn In (0,2)  " + 
				"							           AND t_credit_note_details.cat_id IN(:catIdList) " + 
				"							            AND  t_credit_note_details.del_status=0  " + 
				"							            AND t_credit_note_header.fr_id = m_franchisee.fr_id  " + 
				"							            ), " + 
				"							        0) AS  t_gvn_taxable_amt   " + 
				"							    FROM " + 
				"							        m_sp_cake " + 
				"							        " + 
				"							    " + 
				"							    group by " + 
				"							        m_sp_cake.sp_id  " + 
				"							    order by " + 
				"							        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrForSpCakeByGrandTotalAndType2(@Param("catIdList")List<String> catIdList,
				@Param("fromDate")String fromDate,@Param("toDate") String toDate);

	
		@Query(value="SELECT UUID() as uid," + 
				"				        m_sp_cake.sp_id AS  id," + 
				"				        m_sp_cake.sp_name AS item_name ," + 
				"				        5 AS cat_id ,4 as sub_cat_id," + 
				"				        'Special Cake' AS cat_name," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_bill_detail.bill_qty)          " + 
				"				        FROM" + 
				"				            t_bill_detail," + 
				"				            t_bill_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate            " + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id               " + 
				"				            AND t_bill_header.fr_id =m_franchisee.fr_id              " + 
				"				            AND t_bill_detail.cat_id= :catIdList         " + 
				"				            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"				        0) AS t_bill_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_bill_detail.taxable_amt)          " + 
				"				        FROM" + 
				"				            t_bill_detail," + 
				"				            t_bill_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_bill_header.bill_date BETWEEN  :fromDate AND :toDate             " + 
				"				            AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id              " + 
				"				            AND t_bill_header.fr_id  =m_franchisee.fr_id             " + 
				"				            AND t_bill_detail.cat_id IN (:catIdList)             " + 
				"				            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_bill_taxable_amt," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_credit_note_details.grn_gvn_qty)          " + 
				"				        FROM" + 
				"				            t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate             " + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id              " + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"				            AND t_credit_note_header.is_grn=1            " + 
				"				            AND t_credit_note_details.cat_id IN (:catIdList)              " + 
				"				            AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"				            and  t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_grn_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_credit_note_details.taxable_amt)          " + 
				"				        FROM" + 
				"				            t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate             " + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id                " + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"				            AND t_credit_note_header.is_grn=1            " + 
				"				            AND  t_credit_note_details.cat_id IN (:catIdList)              " + 
				"				            AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"				            and  t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_grn_taxable_amt," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_credit_note_details.grn_gvn_qty)          " + 
				"				        FROM" + 
				"				            t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate            " + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id                " + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id                " + 
				"				            AND t_credit_note_header.is_grn  In (0,2)           " + 
				"				            AND t_credit_note_details.cat_id IN (:catIdList)              " + 
				"				            AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"				            and t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_qty," + 
				"				        COALESCE((SELECT" + 
				"				            SUM(t_credit_note_details.taxable_amt)          " + 
				"				        FROM" + 
				"				            t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				        WHERE" + 
				"				            t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate           " + 
				"				            And t_credit_note_header.crn_id=t_credit_note_details.crn_id              " + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"				            AND t_credit_note_header.is_grn  In (0,2)            " + 
				"				            AND t_credit_note_details.cat_id = :catIdList            " + 
				"				            AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"				            and  t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_taxable_amt       " + 
				"				    FROM" + 
				"				        m_sp_cake                 " + 
				"				    group by" + 
				"				        m_sp_cake.sp_id      " + 
				"				    order by" + 
				"				        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatForSpAndType2(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id,COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty,COALESCE((SELECT SUM(t_bill_detail.taxable_amt) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt,\n" + 
				"\n" + 
				"COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList) ),0) AS  t_grn_qty,\n" + 
				"					COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList) ),0) AS  t_grn_taxable_amt, \n" + 
				"						COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn In (0,2) AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList) ),0) AS  t_gvn_qty,\n" + 
				"					COALESCE((SELECT SUM(t_credit_note_details.taxable_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn In (0,2) AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList) ),0) AS  t_gvn_taxable_amt \n" + 
				"						FROM m_item,m_category where m_item.item_grp1=m_category.cat_id AND m_category.cat_id IN(:catIdList) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name \n" + 
				"				",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAndType2(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid," + 
				"			      m_sp_cake.sp_id AS  id," + 
				"			    m_sp_cake.sp_name AS item_name ," + 
				"			      5 AS cat_id ,4 as sub_cat_id," + 
				"			        'Special Cake' AS cat_name," + 
				"			      COALESCE((SELECT" + 
				"				           SUM(t_bill_detail.bill_qty)          " + 
				"			       FROM" + 
				"				          t_bill_detail," + 
				"				          t_bill_header," + 
				"				          m_franchisee          " + 
				"				      WHERE" + 
				"				           t_bill_header.bill_date BETWEEN  :fromDate AND :toDate            " + 
				"				           AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id               " + 
				"				           AND t_bill_header.fr_id =m_franchisee.fr_id              " + 
				"				          AND t_bill_detail.cat_id= :catIdList         " + 
				"				            AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"			       0) AS t_bill_qty," + 
				"				      COALESCE((SELECT" + 
				"				          SUM(t_bill_detail.grand_total)          " + 
				"				       FROM" + 
				"				           t_bill_detail," + 
				"				           t_bill_header," + 
				"				          m_franchisee          " + 
				"				       WHERE" + 
				"				           t_bill_header.bill_date BETWEEN  :fromDate AND :toDate             " + 
				"				           AND t_bill_header.bill_no=t_bill_detail.bill_no              " + 
				"				            AND m_sp_cake.sp_id=t_bill_detail.item_id              " + 
				"				           AND t_bill_header.fr_id  =m_franchisee.fr_id             " + 
				"				           AND t_bill_detail.cat_id IN (:catIdList)             " + 
				"				           AND t_bill_header.del_status=0 AND t_bill_header.fr_id IN (:frIdList))," + 
				"				       0) AS  t_bill_taxable_amt," + 
				"			       COALESCE((SELECT" + 
				"				           SUM(t_credit_note_details.grn_gvn_qty)          " + 
				"				       FROM" + 
				"				          t_credit_note_details," + 
				"				           t_credit_note_header," + 
				"				           m_franchisee          " + 
				"				        WHERE" + 
				"			           t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate             " + 
				"				            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id              " + 
				"			            AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"				           AND t_credit_note_header.is_grn=1            " + 
				"				            AND t_credit_note_details.cat_id IN (:catIdList)              " + 
				"				           AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"				         AND t_credit_note_header.fr_id IN (:frIdList))," + 
				"			       0) AS  t_grn_qty," + 
				"			        COALESCE((SELECT" + 
				"				            SUM(t_credit_note_details.grn_gvn_amt)          " + 
				"				       FROM" + 
				"				           t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				           m_franchisee          " + 
				"				       WHERE" + 
				"				           t_credit_note_header.crn_date BETWEEN  :fromDate AND :toDate             " + 
				"				           AND t_credit_note_header.crn_id=t_credit_note_details.crn_id                " + 
				"			               AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"			               AND t_credit_note_header.is_grn=1            " + 
				"				           AND  t_credit_note_details.cat_id IN (:catIdList)              " + 
				"				           AND  t_credit_note_details.del_status=0              " + 
				"				           AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"			          AND t_credit_note_header.fr_id IN (:frIdList))," + 
				"				       0) AS  t_grn_taxable_amt," + 
				"				       COALESCE((SELECT" + 
				"			           SUM(t_credit_note_details.grn_gvn_qty)          " + 
				"				        FROM" + 
				"				            t_credit_note_details," + 
				"				           t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				      WHERE" + 
				"				          t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate             " + 
				"				          And t_credit_note_header.crn_id=t_credit_note_details.crn_id                   " + 
				"				          AND m_sp_cake.sp_id=t_credit_note_details.item_id                  " + 
				"				          AND t_credit_note_header.is_grn  In (0,2)            " + 
				"				          AND t_credit_note_details.cat_id = :catIdList           " + 
				"				          AND t_credit_note_details.del_status=0          " + 
				"				          AND t_credit_note_header.fr_id = m_franchisee.fr_id             " + 
				"			          AND t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_qty," + 
				"				       COALESCE((SELECT" + 
				"				           SUM(t_credit_note_details.grn_gvn_amt)          " + 
				"				        FROM" + 
				"			                t_credit_note_details," + 
				"				            t_credit_note_header," + 
				"				            m_franchisee          " + 
				"				      WHERE" + 
				"			               t_credit_note_header.crn_date  BETWEEN  :fromDate AND :toDate           " + 
				"				           And t_credit_note_header.crn_id=t_credit_note_details.crn_id              " + 
				"				            AND m_sp_cake.sp_id=t_credit_note_details.item_id               " + 
				"				           AND t_credit_note_header.is_grn  In (0,2)            " + 
				"				           AND t_credit_note_details.cat_id = :catIdList            " + 
				"				           AND  t_credit_note_details.del_status=0              " + 
				"				            AND t_credit_note_header.fr_id = m_franchisee.fr_id              " + 
				"			           and t_credit_note_header.fr_id IN (:frIdList))," + 
				"				        0) AS  t_gvn_taxable_amt       " + 
				"				    FROM" + 
				"				       m_sp_cake                 " + 
				"				    group by" + 
				"				       m_sp_cake.sp_id      " + 
				"				    order by" + 
				"			        m_sp_cake.sp_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatForSpByGrandTotalAndType2(@Param("frIdList")List<String> frIdList,
				@Param("catIdList")List<String> spcats,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

		@Query(value="SELECT UUID() as uid,m_item.id,m_item.item_name,m_item.item_grp2 as sub_cat_id,m_category.cat_name,m_category.cat_id,COALESCE((SELECT SUM(t_bill_detail.bill_qty) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS t_bill_qty,COALESCE((SELECT SUM(t_bill_detail.grand_total) FROM t_bill_detail,t_bill_header WHERE t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no=t_bill_detail.bill_no AND m_item.id=t_bill_detail.item_id AND t_bill_header.fr_id IN(:frIdList) AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status=0),0) AS  t_bill_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_grn_qty," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn=1 AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_grn_taxable_amt," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_qty) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn In (0,2) AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList) ),0) AS  t_gvn_qty," + 
				"	COALESCE((SELECT SUM(t_credit_note_details.grn_gvn_amt) FROM t_credit_note_details,t_credit_note_header WHERE t_credit_note_header.crn_date  BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND m_item.id=t_credit_note_details.item_id  AND t_credit_note_header.is_grn In (0,2) AND t_credit_note_details.cat_id IN(:catIdList) AND  t_credit_note_details.del_status=0 AND t_credit_note_header.fr_id IN(:frIdList)),0) AS  t_gvn_taxable_amt" + 
				"	FROM m_item,m_category where m_item.item_grp1=m_category.cat_id AND m_category.cat_id IN(:catIdList) and m_item.del_status=0 group by m_item.id order by m_item.item_grp1,m_item.item_grp2,m_item.item_name ",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatByGrandTotalAndType2(@Param("frIdList")List<String> frIdList,@Param("catIdList")List<String> catIdList,
				@Param("fromDate")String fromDate,@Param("toDate") String toDate);

//Changes By harsha 
		
		//1 starts
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr12(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr3(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
		
		

		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        ( (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0)),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        ((\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"             t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_detail.del_status = 0 )),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr123(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		 
		
		//1 ends 
		
		//2 starts
		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType212(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);

		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType23(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
		
		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        ((\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+( SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 )),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        ( (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0)),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType2All(@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		
		 
		
		//2 ends 
		
		//3 starts
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal12(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal3(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.bill_qty)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.grand_total)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal3All(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		//3 ends 
		
		//4 starts
		 
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0  AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType43(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate);
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.bill_qty)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.grand_total)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4All(@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);

		
	//spec fr changes 	
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND   t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr12Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_sell_bill_header.fr_id IN(:frIdList) AND    t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_sell_bill_header.fr_id IN(:frIdList) AND   t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr3Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
		
		
		

		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        ( (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"          t_sell_bill_header.fr_id IN(:frIdList) AND   t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0)),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        ((\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"             t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_detail.del_status = 0 )),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"         t_grn_gvn_header.fr_id IN(:frIdList) AND    t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
			List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFr123Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		 
		
		//1 ends 
		
		//2 starts
		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList)  AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList)  AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType212Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);

		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"          t_credit_note_header.fr_id IN(:frIdList)  AND   t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"          t_credit_note_header.fr_id IN(:frIdList)  AND   t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList)  AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType23Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
		
		 
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        ((\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+( SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 )),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        ( (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    )+(  SELECT\n" + 
				"            SUM(t_sell_bill_detail.taxable_amt)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0)),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList)  AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.taxable_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList)  AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrAndType2AllFran(@Param("frIdList") List<String> frIdList,@Param("catIdList") List<String> catIdList,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("temp") List<Integer> temp);
		
		 
		
		//2 ends 
		
		//3 starts
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.fr_id IN(:frIdList) AND   t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_bill_header.fr_id IN(:frIdList) AND   t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal12Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_sell_bill_header.fr_id IN(:frIdList) AND   t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND   t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal3Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate);
		
		
		@Query(value="SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.bill_qty)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.fr_id IN(:frIdList) AND   t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.grand_total)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                 t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_grn_gvn_header.fr_id IN(:frIdList) AND t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn = 1 AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.grn_gvn_qty)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_grn_gvn_header.fr_id IN(:frIdList) AND  t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_grn_gvn.apr_grand_total)\n" + 
				"        FROM\n" + 
				"            t_grn_gvn,\n" + 
				"            t_grn_gvn_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_grn_gvn_header.fr_id IN(:frIdList) AND   t_grn_gvn_header.grngvn_date BETWEEN :fromDate AND :toDate AND t_grn_gvn_header.grn_gvn_header_id = t_grn_gvn.grn_gvn_header_id AND m_item.id = t_grn_gvn.item_id AND t_grn_gvn_header.is_grn IN(0, 2) AND t_grn_gvn.cat_id IN(:catIdList) AND t_grn_gvn.del_status = 0 AND t_grn_gvn_header.fr_id = m_franchisee.fr_id AND t_grn_gvn.grn_gvn_status = 6\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotal3AllFran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		//3 ends 
		
		//4 starts
		 
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.bill_qty)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_bill_detail,\n" + 
				"            t_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0  AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType2Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);
		
		
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0 \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0  \n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"          t_credit_note_header.fr_id IN(:frIdList) AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"            t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType43Fran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate);
		@Query(value="	SELECT\n" + 
				"    UUID() AS uid, m_item.id, m_item.item_name, m_item.item_grp2 AS sub_cat_id, m_category.cat_name, m_category.cat_id, COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.bill_qty)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                 t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.qty)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"              t_sell_bill_header.fr_id IN(:frIdList) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"            (\n" + 
				"            SELECT\n" + 
				"                SUM(t_bill_detail.grand_total)\n" + 
				"            FROM\n" + 
				"                t_bill_detail,\n" + 
				"                t_bill_header,\n" + 
				"                m_franchisee\n" + 
				"            WHERE\n" + 
				"                t_bill_header.fr_id IN(:frIdList) AND  t_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_bill_header.bill_no = t_bill_detail.bill_no AND m_item.id = t_bill_detail.item_id AND t_bill_header.fr_id = m_franchisee.fr_id AND t_bill_detail.cat_id IN(:catIdList) AND t_bill_header.del_status = 0 AND t_bill_header.ex_varchar2 IN(:temp)\n" + 
				"        ) +(\n" + 
				"        SELECT\n" + 
				"            SUM(t_sell_bill_detail.grand_total)\n" + 
				"        FROM\n" + 
				"            t_sell_bill_detail,\n" + 
				"            t_sell_bill_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"             t_sell_bill_header.fr_id IN(:frIdList) AND  t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.sell_bill_no = t_sell_bill_detail.sell_bill_no AND m_item.id = t_sell_bill_detail.item_id AND t_sell_bill_header.fr_id = m_franchisee.fr_id AND t_sell_bill_detail.cat_id IN(:catIdList) AND t_sell_bill_header.del_status = 0\n" + 
				"    )\n" + 
				"        ),\n" + 
				"        0\n" + 
				"    ) AS t_bill_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn = 1 AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_grn_taxable_amt,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_qty\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"           t_credit_note_header.fr_id IN(:frIdList) AND t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_qty,\n" + 
				"    COALESCE(\n" + 
				"        (\n" + 
				"        SELECT\n" + 
				"            SUM(\n" + 
				"                t_credit_note_details.grn_gvn_amt\n" + 
				"            )\n" + 
				"        FROM\n" + 
				"            t_credit_note_details,\n" + 
				"            t_credit_note_header,\n" + 
				"            m_franchisee\n" + 
				"        WHERE\n" + 
				"          t_credit_note_header.fr_id IN(:frIdList) AND  t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate AND t_credit_note_header.crn_id = t_credit_note_details.crn_id AND m_item.id = t_credit_note_details.item_id AND t_credit_note_header.is_grn IN(0, 2) AND t_credit_note_details.cat_id IN(:catIdList) AND t_credit_note_details.del_status = 0 AND t_credit_note_header.fr_id = m_franchisee.fr_id\n" + 
				"    ),\n" + 
				"    0\n" + 
				"    ) AS t_gvn_taxable_amt\n" + 
				"FROM\n" + 
				"    m_item,\n" + 
				"    m_category\n" + 
				"WHERE\n" + 
				"    m_item.item_grp1 = m_category.cat_id AND m_category.cat_id IN(:catIdList) AND m_item.del_status = 0\n" + 
				"GROUP BY\n" + 
				"    m_item.id\n" + 
				"ORDER BY\n" + 
				"    m_item.item_grp1,\n" + 
				"    m_item.item_grp2,\n" + 
				"    m_item.item_name",nativeQuery=true)	
		List<SalesReportRoyalty> getSaleReportRoyConsoByCatAllFrByGrandTotalAndType4AllFran(@Param("frIdList") List<String> frIdList,@Param("catIdList")List<String> catIdList,@Param("fromDate") String fromDate,
				@Param("toDate")String toDate,@Param("temp")List<Integer> temp);


}
