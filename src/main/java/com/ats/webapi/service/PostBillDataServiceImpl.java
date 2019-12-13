package com.ats.webapi.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import com.ats.webapi.model.FrItemStockConfigureList;
import com.ats.webapi.model.PostBillDetail;
import com.ats.webapi.model.PostBillHeader;
import com.ats.webapi.model.bill.BillTransaction;
import com.ats.webapi.model.bill.Company;
import com.ats.webapi.repo.BillTransationRepo;
import com.ats.webapi.repository.CompanyRepository;
import com.ats.webapi.repository.FrItemStockConfigureRepository;
import com.ats.webapi.repository.OrderRepository;
import com.ats.webapi.repository.PostBillDetailRepository;
import com.ats.webapi.repository.PostBillHeaderRepository;
import com.ats.webapi.repository.RegularSpCkOrderRepository;
import com.ats.webapi.repository.SpCakeOrdersRepository;
import com.ats.webapi.repository.UpdateBillDetailForGrnGvnRepository;
import com.ats.webapi.repository.UpdateSeetingForPBRepo;

@Service
public class PostBillDataServiceImpl implements PostBillDataService {

	@Autowired
	PostBillHeaderRepository postBillHeaderRepository;

	@Autowired
	PostBillDetailRepository postBillDetailRepository;

	@Autowired
	OrderRepository orderRepository;
 
	@Autowired
	SpCakeOrdersRepository spCakeOrdersRepository;
	
	@Autowired
	RegularSpCkOrderRepository  regularSpCkOrderRepository;
	
	
	@Autowired
	UpdateBillDetailForGrnGvnRepository updateBillDetailForGrnGvnRepository;
	
	@Autowired//added here on 3 march
	UpdateSeetingForPBRepo updateSeetingForPBRepo;
	
	@Autowired// added here 3 march
	FrItemStockConfigureRepository frItemStockConfRepo;

	
	
	/*
	 * @Override public List<PostBillDetail> saveBillDetails(List<PostBillDetail>
	 * postBillDetail) {
	 * 
	 * List<PostBillDetail> billDetail=new ArrayList<PostBillDetail>();
	 * for(PostBillDetail pBDetails:postBillDetail) {
	 * 
	 * billDetail=postBillDataService.saveBillDetails(postBillDetail);
	 * 
	 * }
	 * 
	 * return billDetail; }
	 */
	
	@Autowired
	BillTransationRepo billTransationRepo;
	
	
	
	@Autowired
	CompanyRepository companyRepository;
	@Override
	public List<PostBillHeader> saveBillHeader(List<PostBillHeader> postBillHeader) {
		
	 
		List<PostBillHeader> pbHeaderList=new ArrayList<>();
		PostBillHeader postBillHeaders = new PostBillHeader();
		
		for (int i = 0; i < postBillHeader.size(); i++) {
			
			String invoiceNo = null;
			int settingValue=0;
			Company company=new Company();
		try {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(postBillHeader.get(i).getBillDate());
		
			 company=companyRepository.findByBillDate(date);
			 
			 if(postBillHeader.get(i).getExVarchar2().equals("1"))
			  invoiceNo=company.getDelChalanPrefix();
			 else
			  invoiceNo=company.getExVar4(); 
			 
			}catch (Exception e) {
			}
			if(postBillHeader.get(i).getExVarchar2().equals("1")) {
				
            settingValue=frItemStockConfRepo.findBySettingKey("DC");
            
		    }else
		    {
		    settingValue=frItemStockConfRepo.findBySettingKey("PB");
		    }
			System.out.println("Setting Value Received " + settingValue);

			invoiceNo =invoiceNo+""+String.format("%06d" , settingValue);		
			postBillHeader.get(i).setInvoiceNo(invoiceNo);
			postBillHeaders = postBillHeaderRepository.save(postBillHeader.get(i));
			
			//save Bill transaction starts 
			
			if(postBillHeaders!=null && postBillHeaders.getBillNo()>0 ) {
			BillTransaction bt =new BillTransaction();
			bt.setBillAmt(String.valueOf(postBillHeaders.getGrandTotal()));
			bt.setBillHeadId(0);
			bt.setBillNo(String.valueOf(postBillHeaders.getInvoiceNo()));
			bt.setExInt1(0);
			bt.setExInt2(0);
			bt.setExInt3(0);
			bt.setExInt4(0);
			bt.setExVar1("NA");
			bt.setExVar2("NA");
			bt.setExVar3("NA");
			bt.setExVar4("NA");
			bt.setFrId(postBillHeaders.getFrId());
			bt.setIsClosed(0);
			bt.setPaidAmt("0");
			bt.setPendingAmt(String.valueOf(postBillHeaders.getGrandTotal()));
			bt.setBillDate(new Date());
			BillTransaction jsonResult = billTransationRepo.save(bt);
			//save Bill transaction ends 
			}
			if(postBillHeaders!=null && postBillHeaders.getBillNo()>0 ) {
				
				settingValue=settingValue+1;
				 if(postBillHeader.get(i).getExVarchar2().equals("1")) {
						int result = updateSeetingForPBRepo.updateSeetingForPurBill(settingValue, "DC");
						System.err.println("DC setting value updated "+ result);
				 }else {
				     int result = updateSeetingForPBRepo.updateSeetingForPurBill(settingValue, "PB");
				     System.err.println("PB setting value updated "+ result);
				 }
			}
			int billNo = postBillHeader.get(i).getBillNo();
			List<PostBillDetail> postBillDetailList = postBillHeader.get(i).getPostBillDetailsList();

			for (int j = 0; j < postBillDetailList.size(); j++) {
				PostBillDetail billDetail = postBillDetailList.get(j);
				billDetail.setBillNo(billNo);
				
				if(billDetail.getOrderQty()==0) {
					int  updateOrderStatus = orderRepository.updateBillStatus(billDetail.getOrderId(), 1);
				}
				else {
				postBillDetailRepository.save(billDetail);				
				int res=0;
				if (billDetail.getCatId() != 5) { 
					
					if(billDetail.getMenuId()!= 42) {//item						
					 res = orderRepository.updateBillStatus(billDetail.getOrderId(), 2);					
					}else { // regular sp cake
						regularSpCkOrderRepository.updateRegSpCakeBillStatus(billDetail.getOrderId(), 2);
					}
					
				} else if (billDetail.getCatId() == 5){ //special cake
					 res = spCakeOrdersRepository.updateSpBillStatus(billDetail.getOrderId(), 2);
				}
			}
			}
			pbHeaderList.add(postBillHeaders);
		}
		return pbHeaderList;
	}
    @Override
	public List<PostBillHeader> updateBillHeader(List<PostBillHeader> postBillHeader) {
		
		
		List<PostBillHeader> postBillHeaders = new ArrayList<PostBillHeader>();
		for (int i = 0; i < postBillHeader.size(); i++) {

			postBillHeaders = postBillHeaderRepository.save(postBillHeader);

			int billNo = postBillHeader.get(i).getBillNo();

			List<PostBillDetail> postBillDetailList = postBillHeader.get(i).getPostBillDetailsList();

			for (int j = 0; j < postBillDetailList.size(); j++) {

				PostBillDetail billDetail = postBillDetailList.get(j);

				billDetail.setBillNo(billNo);

				postBillDetailRepository.save(billDetail);
			}
		}
		return postBillHeaders;
	}

}
