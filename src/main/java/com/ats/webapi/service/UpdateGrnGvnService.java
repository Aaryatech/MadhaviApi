package com.ats.webapi.service;

import java.util.List;

public interface UpdateGrnGvnService {
	
	public int updateGrnForGate( int approvedLoginGate, float aprQtyGate,String approveimedDateTimeGate,
			String approvedRemarkGate,int grnGvnStatus,int grnGvnId);


	public int updateGrnForAcc( int approvedLoginAcc,float aprQtyAcc,String approvedDateTimeAcc,
			String approvedRemarkAcc,int grnGvnStatus,float aprTaxableAmt,float aprTotalTax,float aprSgstRs,float aprCgstRs,float aprIgstRs,
			float aprGrandTotal,float aprROff,int grnGvnId);

	
	public int updateGrnGvnForStore( int approvedLoginStore,float aprQtyStore,String approvedDateTimeStore,
			String approvedRemarkStore,int grnGvnStatus,int grnGvnId);

	
	
}
