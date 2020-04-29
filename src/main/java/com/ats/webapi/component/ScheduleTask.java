package com.ats.webapi.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.ats.webapi.commons.Common;
import com.ats.webapi.commons.EmailUtility;
import com.ats.webapi.commons.Firebase;
import com.ats.webapi.controller.PettyCashApiController;
import com.ats.webapi.controller.UserUtilApi;
import com.ats.webapi.model.FranchiseSup;
import com.ats.webapi.model.FranchiseSupList;
import com.ats.webapi.model.Franchisee;
import com.ats.webapi.model.GetFranchiseSup;
import com.ats.webapi.model.GetTotalAmt;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.ShopAnivData;
import com.ats.webapi.model.pettycash.GetCashAdvAndExpAmt;
import com.ats.webapi.model.pettycash.OtherBillDetailAdv;
import com.ats.webapi.model.pettycash.PettyCashDao;
import com.ats.webapi.model.pettycash.PettyCashManagmt;
import com.ats.webapi.model.pettycash.SellBillDetailAdv;
import com.ats.webapi.model.pettycash.SpCakeAdv;
import com.ats.webapi.model.posdashboard.BillHeaderDashCount;
import com.ats.webapi.model.posdashboard.BillTransactionDetailDashCount;
import com.ats.webapi.model.posdashboard.CreaditAmtDash;
import com.ats.webapi.model.posdashboard.DashAdvanceOrderCounts;
import com.ats.webapi.model.posdashboard.PosDashCounts;
import com.ats.webapi.model.posdashboard.SellBillHeaderDashCounts;
import com.ats.webapi.repo.GetTotalAmtRepo;
import com.ats.webapi.repo.OtherBillDetailAdvRepo;
import com.ats.webapi.repo.PettyCashManagmtRepo;
import com.ats.webapi.repo.SellBillDetailAdvRepo;
import com.ats.webapi.repo.SpCakeAdvRepo;
import com.ats.webapi.repo.posdashboard.BillHeaderDashCountRepo;
import com.ats.webapi.repo.posdashboard.BillTransactionDetailDashCountRepo;
import com.ats.webapi.repo.posdashboard.CreaditAmtDashRepo;
import com.ats.webapi.repo.posdashboard.DashAdvanceOrderCountsRepo;
import com.ats.webapi.repo.posdashboard.SellBillHeaderDashCountsRepo;
import com.ats.webapi.repository.FrAniversaryRepository;
import com.ats.webapi.repository.FranchiseSupRepository;
import com.ats.webapi.repository.FranchiseeRepository;
import com.ats.webapi.repository.GetCashAdvAndExpAmtRepo;
import com.ats.webapi.repository.GetFranchiseSupRepository;
import com.ats.webapi.repository.SellBillHeaderRepository;
import com.ats.webapi.service.FranchiseeService;

import ch.qos.logback.classic.pattern.DateConverter;

@Component
public class ScheduleTask {

	@Autowired
	FranchiseSupRepository franchiseSupRepository;

	@Autowired
	FrAniversaryRepository frAniversaryRepository;

	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(cron = "0 0 7 * * *")
	public void scheduleTaskWithCronExpression() {
		logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		List<String> frTokens = franchiseSupRepository.findTokensByBirthdate(new Date());
		logger.info("frTokens" + frTokens);
		List<ShopAnivData> frOPTokens = frAniversaryRepository.findTokensByFrOpeningDate(new Date());
		logger.info("frOPTokens" + frOPTokens);
		// -----------------------For Notification-----------------

		if (!frTokens.isEmpty()) {

			try {
				for (String token : frTokens) {
					Firebase.sendPushNotifForCommunication(token, "HAPPY BIRTHDAY",
							"Team Monginis wishes you a very happy birthday and many many happy returns of the day.",
							"inbox");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		if (!frOPTokens.isEmpty()) {

			try {
				for (ShopAnivData token : frOPTokens) {
					Firebase.sendPushNotifForCommunication(token.getToken(), "Shop Anniversary",
							"Congratulations on successful completion of " + token.getNoOfYears()
									+ "years with Monginis. Thank you for being part of our family. Team Monginis",
							"inbox");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		// -----------------------------------------------------
	}

	@Scheduled(cron = "0 0 6 1 * ?")
	public void scheduleTaskWithCronExpressionOnMonthStart() {
		logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		List<String> frTokens = franchiseSupRepository.findTokens();

		if (!frTokens.isEmpty()) {

			try {
				for (String token : frTokens) {
					Firebase.sendPushNotifForCommunication(token, "Close Your Month",
							"Since today is first day of the month, please close the last month in your software.",
							"inbox");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

	@Autowired
	FranchiseeRepository franchiseeRepository;

	@Autowired
	GetTotalAmtRepo getTotalAmtRepo;

	@Autowired
	GetCashAdvAndExpAmtRepo getCashAdvAndExpAmtRepo;

	@Autowired
	PettyCashManagmtRepo pettyRepo;

	@Autowired
	SpCakeAdvRepo spRepo;

	@Autowired
	SellBillDetailAdvRepo sellBillRepo;

	@Autowired
	OtherBillDetailAdvRepo otherBillRepo;
	
	@Autowired
	SellBillHeaderRepository sellBillHeaderRepository;

	// Petty Cash Day End Process every morning 6.00 am
	//@Scheduled(cron = "6 * * * * *")
	@Scheduled(cron = "0 0 7 * * *")
	public void crownForPettyCashDayEnd() {

		List<Franchisee> franchisee = new ArrayList<Franchisee>();
		franchisee = franchiseeRepository.findAllByDelStatusOrderByFrNameAsc(0);

		if (franchisee != null) {

			for (int j = 0; j < franchisee.size(); j++) {

				Franchisee fr = franchisee.get(j);
				System.err.println("FRA ------------------"+fr);
				
				int empId=0;
				try {
					SellBillHeader res = sellBillHeaderRepository.getLastBillHeaderByFrId(fr.getFrId());
					if(res!=null) {
						empId=res.getExtInt1();
					}
				}catch (Exception e) {
					e.printStackTrace();
					empId=0;
				}

				PettyCashManagmt petty = new PettyCashManagmt();
				try {
					petty = pettyRepo.findByFrIdAndStatusLimit1(fr.getFrId(), 0);

					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(cal.getTime());

					if (petty != null) {

						cal = Calendar.getInstance();

						cal.setTime(petty.getDate());

						// Add 1 day
						cal.add(Calendar.DAY_OF_MONTH, 1);
						date = sdf.format(cal.getTime());
 
					}

					GetCashAdvAndExpAmt data = new GetCashAdvAndExpAmt();
					data = getCashAdvAndExpAmtRepo.getAmt(fr.getFrId(), date);

					GetTotalAmt creditNoteAmt = new GetTotalAmt();
					creditNoteAmt = getTotalAmtRepo.getTotalPOSCreditNoteAmt(fr.getFrId(), date);
					
					float creditNtAmt=0;
					if(creditNoteAmt!=null) {
						creditNtAmt=creditNoteAmt.getTotalAmt();
					}

					petty.setTotalAmt(data.getTrCashAmt() + data.getAdvAmt() - data.getExpAmt()-creditNtAmt);

					
					System.err.println("PETTY ------------------ "+petty);
					
					Calendar cal2=Calendar.getInstance();
					
					System.err.println("DATE 1 --------------------- "+sdf.format(cal.getTime()));
					System.err.println("DATE 2 --------------------- "+sdf.format(cal2.getTime()));
					
					if(cal.compareTo(cal2)<=0) {
						
						String d1=sdf.format(cal.getTime());
						String d2=sdf.format(cal2.getTime());
								
						if(!d1.equalsIgnoreCase(d2)) {
							
							
							PettyCashManagmt pettycash = new PettyCashManagmt();
							
							float cashAmt = 0;
							float closAmt = 0;
							float withdrawAmt = 0;
							float opnAmt = 0;
							float cashEdtAmt = 0;
							try {

								cashAmt = petty.getTotalAmt();
								withdrawAmt = cashAmt;
								opnAmt = petty.getClosingAmt();
								cashEdtAmt = petty.getCashAmt();
								closAmt = opnAmt+cashAmt-withdrawAmt;
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							//String date1 = DateConvertor.convertToYMD(date);
							
							SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
							Calendar cal1=Calendar.getInstance();

							pettycash.setPettycashId(0);
							pettycash.setCardAmt(0);
							pettycash.setCashAmt(cashAmt);
							pettycash.setClosingAmt(closAmt);
							pettycash.setDate(sdf.parse(date));
							pettycash.setExFloat1(0);
							pettycash.setExInt1(empId);
							pettycash.setExVar1(""+sdf1.format(cal1.getTime()));
							pettycash.setExVar2("NA");
							pettycash.setFrId(fr.getFrId());
							pettycash.setOpeningAmt(opnAmt);
							pettycash.setCardEditAmt(0);
							pettycash.setTtlEditAmt(0);
							pettycash.setOtherAmt(0);
							pettycash.setStatus(0);
							pettycash.setTotalAmt(0);
							pettycash.setTtlEditAmt(0);
							pettycash.setWithdrawalAmt(withdrawAmt);
							pettycash.setOpnEditAmt(0);
							pettycash.setCashEditAmt(cashEdtAmt);
							pettycash.setExFloat1(0);
							
							PettyCashManagmt cash = new PettyCashManagmt();
							try {
								cash = pettyRepo.save(pettycash);
								if(cash!=null) {

									String senderEmail = UserUtilApi.senderEmail;
									String senderPassword = UserUtilApi.senderPassword;
									
									Franchisee frDetails =  franchiseeRepository.findOne(fr.getFrId());
									
									String fromDate =  sdf.format(cal.getTime());
									String toDate =  sdf.format(cal.getTime());
									
									PosDashCounts posDetails = getPosDashData(fromDate, toDate, fr.getFrId(), frDetails.getFrRateCat());
									System.out.println("POS Details----------"+posDetails);
									
									String msg = "Total summary for ("+frDetails.getFrCode()+") at ("+Common.convertToDMY(fromDate)+")\n" + 
											"E-Pay - ("+posDetails.getEpayAmt()+")\n" + 
											"Cash - ("+posDetails.getCardAmt()+")\n" + 
											"Card - ("+posDetails.getCardAmt()+")\n" + 
											"Sales - ("+posDetails.getSaleAmt()+")\n" + 
											"Discount - ("+posDetails.getDiscountAmt()+")\n" + 
											"Purchase  - ("+posDetails.getPurchaseAmt()+")\n" + 
											"Advance - ("+posDetails.getAdvanceAmt()+")\n" + 
											"Credit Bill - ("+posDetails.getCreditAmt()+")\n" + 
											"Expenses - ("+posDetails.getExpenseAmt()+")";
									
									String mailSubject = "Total summary for ("+frDetails.getFrCode()+") at ("+Common.convertToDMY(fromDate)+")";
									String defPass="";
									
									System.err.println("Send Mail---------"+fr.getFrId()+" "+fr.getFrCode()+" "+fromDate+" - "+toDate);
									Info info  = EmailUtility.sendEmail(senderEmail, senderPassword, frDetails.getFrEmail(), mailSubject, msg, defPass);
									
									if(info.isError()==false) {
										EmailUtility.send(frDetails.getFrMob(), msg);
									}
								}
							} catch (Exception e) {
								System.err.println("Exception in addPettyCash : " + e.getMessage());
								e.printStackTrace();
							}
							
						}
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}
	
	/*****************************************************************/
	@Autowired
	SellBillHeaderDashCountsRepo sellBillHeaderDashCountsRepo;

	@Autowired
	BillTransactionDetailDashCountRepo billTransactionDetailDashCountRepo;

	@Autowired
	BillHeaderDashCountRepo billHeaderDashCountRepo;

	@Autowired
	CreaditAmtDashRepo creaditAmtDashRepo;

	@Autowired
	DashAdvanceOrderCountsRepo dashAdvanceOrderCountsRepo;
	
	public PosDashCounts getPosDashData(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frId") int frId,
			@RequestParam("frRateCat") int frRateCat) {

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = df.format(date);
		PosDashCounts crnReport = new PosDashCounts();

		SellBillHeaderDashCounts headcount = new SellBillHeaderDashCounts();
		BillTransactionDetailDashCount tranCount = new BillTransactionDetailDashCount();
		BillHeaderDashCount billCountch = new BillHeaderDashCount();
		BillHeaderDashCount billCountpur = new BillHeaderDashCount();
		CreaditAmtDash daseqe = new CreaditAmtDash();

		List<DashAdvanceOrderCounts> dailyList = new ArrayList<DashAdvanceOrderCounts>();
		List<DashAdvanceOrderCounts> advOrderList = new ArrayList<DashAdvanceOrderCounts>();

		System.err.println("PARAM ------ " + fromDate + "       " + toDate + "         " + frId);

		System.err.println("DashBoardReporApi data is " + fromDate + toDate + frId);
		try {
			
			try {
				headcount = sellBillHeaderDashCountsRepo.getDataFordash(fromDate, toDate, frId);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				tranCount = billTransactionDetailDashCountRepo.getD1ataFordash(fromDate, toDate, frId);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				billCountch = billHeaderDashCountRepo.getD1ataFordash2Ch(fromDate, toDate, frId);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				billCountpur = billHeaderDashCountRepo.getD1ataFordash2pur(fromDate, toDate, frId);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				daseqe = creaditAmtDashRepo.getDataFordash(fromDate, toDate, frId);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				dailyList = dashAdvanceOrderCountsRepo.getAdvDetail(currentDate, frId, 2);
			}catch (Exception e) {
				e.getMessage();
			}
			
			try {
				advOrderList = dashAdvanceOrderCountsRepo.getAdvDetail(currentDate, frId, 1);
			}catch (Exception e) {
				e.getMessage();
			}
			
			System.err.println("DashBoardReporApi ***" + daseqe.toString());
			crnReport.setDailyMartList(dailyList);
			crnReport.setAdvOrderList(advOrderList);

			System.err.println("PURCHASE ====================== " + billCountpur);

			GetTotalAmt getAdvAmt = getTotalAmtRepo.getTotalAmount(frId, fromDate, toDate);
			float advAmt = 0;
			if (getAdvAmt != null) {
				advAmt = getAdvAmt.getTotalAmt();
			}

			GetTotalAmt getProfitAmt = getTotalAmtRepo.getTotalProfit(frId, fromDate, toDate);
			float profitAmt = 0;
			if (getProfitAmt != null) {
				profitAmt = getProfitAmt.getTotalAmt();
			}

			crnReport.setProfitAmt((int) profitAmt);

			// System.err.println( "DashBoardReporApi /tranCount" + tranCount.toString());
			// System.err.println( "DashBoardReporApi /billCountch" +
			// billCountch.toString());
			// System.err.println( "DashBoardReporApi /billCountpur" +
			// billCountpur.toString());

			// crnReport.setAdvanceAmt(headcount.getAdvanceAmt());
			crnReport.setAdvanceAmt(advAmt);

			if (tranCount.getCardAmt() == "" || tranCount.getCardAmt() == null) {
				crnReport.setCardAmt(0);
			} else {
				crnReport.setCardAmt(Float.parseFloat(tranCount.getCardAmt()));
			}
			if (tranCount.getCashAmt() == "" || tranCount.getCashAmt() == null) {
				crnReport.setCashAmt(0);
			} else {
				crnReport.setCashAmt(Float.parseFloat(tranCount.getCashAmt()));
			}

			if (tranCount.getePayAmt() == "" || tranCount.getePayAmt() == null) {
				crnReport.setEpayAmt(0);
			} else {
				crnReport.setEpayAmt(Float.parseFloat(tranCount.getePayAmt()));
			}

			if (daseqe.getCreditAmt() == "" || daseqe.getCreditAmt() == null) {
				crnReport.setCreditAmt(0);
			} else {
				// crnReport.setEpayAmt(Float.parseFloat(tranCount.getePayAmt()));
				crnReport.setCreditAmt(Float.parseFloat(daseqe.getCreditAmt()));
			}

			crnReport.setDiscountAmt(headcount.getDiscAmt());

			crnReport.setNoOfBillGenerated(headcount.getNoBillGen());
			crnReport.setSaleAmt(headcount.getSellAmt());

			// crnReport.setProfitAmt(headcount.getProfitAmt());

			try {
				crnReport.setPurchaseAmt(Float.parseFloat(billCountpur.getPurchaeAmt()));
			} catch (Exception e) {
				crnReport.setPurchaseAmt(0);
			}
			/*if (billCountpur.getPurchaeAmt() == "" || billCountpur.getPurchaeAmt() == null
					|| billCountpur.getPurchaeAmt() == "0") {
				crnReport.setPurchaseAmt(0);
			} else {
				crnReport.setPurchaseAmt(Float.parseFloat(billCountpur.getPurchaeAmt()));
			}*/

			if (billCountch.getChAmt() == "" || billCountch.getChAmt() == null || billCountch.getChAmt() == "0") {
				crnReport.setExpenseAmt(0);
			} else {
				crnReport.setExpenseAmt(Float.parseFloat(billCountch.getChAmt()));
			}

			System.err.println("DashBoardReporApi /getCredNoteReport" + crnReport.toString());

		} catch (Exception e) {

			System.err.println("Exception in DashBoardReporApi /getCredNoteReport" + e.getMessage());

			e.printStackTrace();
		}

		return crnReport;
	}

	@Autowired
	GetFranchiseSupRepository franchiseeSupRepo;
	//Send mail and SMS to  all franchise when there license are expired
		//@Scheduled(cron = "0 1 * * * * ")	
		//@Scheduled(cron = "*/5 * * * * ?")
		//@Scheduled(cron = "0 0 7 * * *")
		@Scheduled(cron = "0 0 11 * * *")
		public void crownForLicencesExpiry() {
			try {
				 	Date date = new Date();  
				    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
				    String currDate= formatter.format(date);  
				    System.out.println("Today's Date-----------------------"+currDate);  
				    
				    Date fdaLiscExpDate = new Date();  
				    Date weighingScaleDate1 = new Date();
				    Date weighingScaleDate2 = new Date();
				    Date shopEstbActDate = new Date();
				    Date profTaxDate = new Date();
				    Date frExpiryDate = new Date();
				    
				    List<FranchiseSup> franchiseSupList = new ArrayList<FranchiseSup>();
				 
					List<Franchisee> franchisee = new ArrayList<Franchisee>();
					franchisee = franchiseeRepository.findAllByDelStatusOrderByFrNameAsc(0);
					
					String senderEmail = UserUtilApi.senderEmail;
					String senderPassword = UserUtilApi.senderPassword;
					String mailsubject = ""; 
					String mailText = ""; 
					String frCode = "";
					
					String fdaLic = "FDA License";
					String weighingScale1Lisc = "Weighing Scale License 1";
					String weighingScale2Lisc = "Weighing Scale License 2";
					String shopEstActLisc = " Shops Estb. License";
					String proTaxLisc = "Professional Tax License";
					String frExpiryAgreement = "Madhvi Franchise Agreement";
					
					Date currentDate = formatter.parse(currDate);
					
					 franchiseSupList = franchiseSupRepository.findByDelStatus(0);
					/********************************FDA License****************************/
					for (int i = 0; i < franchisee.size(); i++) {
						
						if(franchisee.get(i).getFrAgreementDate()!=null) {
							
							fdaLiscExpDate = franchisee.get(i).getFrAgreementDate();
							frCode = franchisee.get(i).getFrCode();
							System.out.println("FDA Lisc Date ------------"+fdaLiscExpDate+" "+franchisee.get(i).getFrId()+" "+frCode);
							
							
						    
						       long difference = fdaLiscExpDate.getTime() - currentDate.getTime();
						       long daysBetween = (difference / (1000*60*60*24));					               
						       System.out.println("FDA Number of Days between dates: "+daysBetween);
						       
						       
						       if(daysBetween>=1 && daysBetween<=15) {
						    	   mailsubject = fdaLic+" License will expiry soon for Outlet : "+frCode;
								   mailText = "Hello Sir/Madam,\n"+
												"<br>&nbsp;&nbsp; Your "+fdaLic+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(fdaLiscExpDate)+".\n"+
												"<br>&nbsp;&nbsp; Please renew your license.";
						    	   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
						    	   System.out.println("INFO Mail--------"+info);
						    	   if(info.isError()==false) {
						    		   String msg ="Welcome to Madhvi!\n" + 
						    		   		""+frCode+" your "+fdaLic+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(fdaLiscExpDate)+".\n" + 
						    		   		"Please renew your license.";
						    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
						    		   
						    		   System.out.println("INFO SMS---------"+inf);
						    	   }
						       }else {
						    	   System.err.println("FDA Date is More than 15-----------"+frCode);
						       }
							}
							
					}
					
					 /********************************Weighing Scale License Date1****************************/
					
				      for (int j = 0; j < franchiseSupList.size(); j++) {
				    	   
				    	   for (int i = 0; i < franchisee.size(); i++) {
				    		   
				    		   if(franchiseSupList.get(j).getFrId()==franchisee.get(i).getFrId()) {
				    			   
				    	System.out.println("Fr Supp Found Weigh Scal1-------------"+franchiseSupList.get(j).getFrId());
				    	
				    		   if(franchiseSupList.get(j).getPass2()!=null) {
				    			   
				    			   weighingScaleDate1 = franchiseSupList.get(j).getPass2();
				    			 
							       long difference = weighingScaleDate1.getTime() - currentDate.getTime();
							       long daysBetween = (difference / (1000*60*60*24));
							       
							       if(daysBetween>=1 && daysBetween<=15) {
							    	   frCode = franchisee.get(i).getFrCode();
							    	    
							    	   mailsubject = weighingScale1Lisc+" will expiry soon for Outlet : "+frCode;
									   mailText = "Hello Sir/Madam,\n"+
													"<br>&nbsp;&nbsp; Your "+weighingScale1Lisc+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(weighingScaleDate1)+".\n"+
													"<br>&nbsp;&nbsp; Please renew your license.";
							    	  
									   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
							    	   System.out.println("INFO Mail--------"+info);
							    	   
							    	   if(info.isError()==false) {
							    		   String msg ="Welcome to Madhvi!\n" + 
							    		   		""+frCode+" your "+weighingScale1Lisc+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(weighingScaleDate1)+".\n" + 
							    		   		"Please renew your license.";
							    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
							    		   System.out.println("Resp----------"+franchisee.get(i).getFrMob()+"   "+weighingScaleDate1);
							    		   System.out.println("INFO SMS---------"+inf);
							    	   }
							       }else {
							    	   System.err.println(weighingScale1Lisc+" Date is More than 15-----------"+frCode);
							       }
						              
							       System.out.println("Weighing Scale 1 Number of Days between dates: "+daysBetween);
				    		   }
				    		 }
				       }
					}
				       
				       /********************************Weighing Scale License Date2****************************/
						 
					      for (int j = 0; j < franchiseSupList.size(); j++) {
					    	   
					    	   for (int i = 0; i < franchisee.size(); i++) {
					    		   
					    		   if(franchiseSupList.get(j).getFrId()==franchisee.get(i).getFrId()) {
					    			   
					    	System.out.println("Fr Supp Found Weigh Scal2------------"+franchiseSupList.get(j).getFrId());
					    	
					    		   if(franchiseSupList.get(j).getPass3()!=null) {
					    			   
					    			   weighingScaleDate2 = franchiseSupList.get(j).getPass3();
					    			 
								       long difference = weighingScaleDate2.getTime() - currentDate.getTime();
								       long daysBetween = (difference / (1000*60*60*24));
								       
								       if(daysBetween>=1 && daysBetween<=15) {
								    	   frCode = franchisee.get(i).getFrCode();
								    	    
								    	   mailsubject = weighingScale2Lisc+" will expiry soon for Outlet : "+frCode;
										   mailText = "Hello Sir/Madam,\n"+
														"<br>&nbsp;&nbsp; Your "+weighingScale2Lisc+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(weighingScaleDate2)+".\n"+
														"<br>&nbsp;&nbsp; Please renew your license.";
								    	  
										   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
								    	   System.out.println("INFO Mail--------"+info);
								    	   
								    	   if(info.isError()==false) {
								    		   String msg ="Welcome to Madhvi!\n" + 
								    		   		""+frCode+" your "+weighingScale2Lisc+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(weighingScaleDate2)+".\n" + 
								    		   		"Please renew your license.";
								    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
								    		   System.out.println("Resp----------"+franchisee.get(i).getFrMob()+"   "+weighingScaleDate2);
								    		   System.out.println("INFO SMS---------"+inf);
								    	   }
								       }else {
								    	   System.err.println(weighingScale2Lisc+" Date is More than 15-----------"+frCode);
								       }
							              
								       System.out.println("Weighing Scale 2 Number of Days between dates: "+daysBetween);
					    		   }
					    		 }
					       }
						}
					
					       /*******************************License under Shops & Establishment Act***************************/
					       for (int j = 0; j < franchiseSupList.size(); j++) {
					    	   
					    	   for (int i = 0; i < franchisee.size(); i++) {
					    		   
					    		   if(franchiseSupList.get(j).getFrId()==franchisee.get(i).getFrId()) {
					    			   frCode = franchisee.get(i).getFrCode();
					    			  
					    	
					    		   if(franchiseSupList.get(j).getPass4()!=null) {
					    			   
					    			   shopEstbActDate = franchiseSupList.get(j).getPass4();
					    			   
					    			   System.out.println("Shop Estb Act Lisc------------"+shopEstbActDate+"**********"+franchisee.get(i).getFrId()+"**********"+frCode);
					    			 
								       long difference = shopEstbActDate.getTime() - currentDate.getTime();
								       long daysBetween = (difference / (1000*60*60*24));
								       
								       if(daysBetween>=1 && daysBetween<=15) {
								    	   
								    	    
								    	   mailsubject = shopEstActLisc+" will expiry soon for Outlet : "+frCode;
										   mailText = "Hello Sir/Madam,\n"+
														"<br>&nbsp;&nbsp; Your "+shopEstActLisc+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(shopEstbActDate)+".\n"+
														"<br>&nbsp;&nbsp; Please renew your license.";
								    	  
										   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
								    	   System.out.println("INFO Mail--------"+info);
								    	   
								    	   if(info.isError()==false) {
								    		   String msg ="Welcome to Madhvi!\n" + 
								    		   		""+frCode+" your "+shopEstActLisc+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(shopEstbActDate)+".\n" + 
								    		   		"Please renew your license.";
								    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
								    		   System.out.println("Resp----------"+franchisee.get(i).getFrMob()+"   "+shopEstbActDate);
								    		   System.out.println("INFO SMS---------"+inf);
								    	   }
								       }else {
								    	   System.err.println(shopEstActLisc+" Date is More than 15-----------"+frCode);
								       }
							              
								       System.out.println("Shop Estb Act Number of Days between dates: "+daysBetween);
					    		   }
					    		 }
					       }
						}
			
					       /********************************Professional Tax License****************************/
							 
						       for (int j = 0; j < franchiseSupList.size(); j++) {
						    	   
						    	   for (int i = 0; i < franchisee.size(); i++) {
						    		   
						    		   if(franchiseSupList.get(j).getFrId()==franchisee.get(i).getFrId()) {
						    			   
						    	System.out.println("Fr Supp Prof Tax------------"+franchiseSupList.get(j).getFrId());
						    	
						    		   if(franchiseSupList.get(j).getRemainderDate()!=null) {
						    			   
						    			   profTaxDate = franchiseSupList.get(j).getRemainderDate();
						    			 
									       long difference = profTaxDate.getTime() - currentDate.getTime();
									       long daysBetween = (difference / (1000*60*60*24));
									       
									       if(daysBetween>=1 && daysBetween<=15) {
									    	   frCode = franchisee.get(i).getFrCode();
									    	    
									    	   mailsubject = proTaxLisc+" will expiry soon for Outlet : "+frCode;
											   mailText = "Hello Sir/Madam,\n"+
															"<br>&nbsp;&nbsp; Your "+proTaxLisc+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(profTaxDate)+".\n"+
															"<br>&nbsp;&nbsp; Please renew your license.";
									    	  
											   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
									    	   System.out.println("INFO Mail--------"+info);
									    	   
									    	   if(info.isError()==false) {
									    		   String msg ="Welcome to Madhvi!\n" + 
									    		   		""+frCode+" your "+proTaxLisc+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(profTaxDate)+".\n" + 
									    		   		"Please renew your license.";
									    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
									    		   System.out.println("Resp----------"+franchisee.get(i).getFrMob()+"   "+profTaxDate);
									    		   System.out.println("INFO SMS---------"+inf);
									    	   }
									       }else {
									    	   System.err.println(proTaxLisc+" Date is More than 15-----------"+frCode);
									       }
								              
									       System.out.println("Prof Tax Number of Days between dates: "+daysBetween);
						    		   }
						    		 }
						       }
							}
						       
						       /********************************Madhvi Franchisee Agreement Expiry****************************/
								
							       for (int j = 0; j < franchiseSupList.size(); j++) {
							    	   
							    	   for (int i = 0; i < franchisee.size(); i++) {
							    		   
							    		   if(franchiseSupList.get(j).getFrId()==franchisee.get(i).getFrId()) {	
							    			   
							    			   //System.out.println("Fr Agreement------------"+franchiseSupList.get(j).getFrId());
							    	
							    		   if(franchiseSupList.get(j).getPestControlDate()!=null) {
							    			   frCode = franchisee.get(i).getFrCode();
							    			   frExpiryDate = franchiseSupList.get(j).getPestControlDate();
							    			   
							    			   System.out.println("Madhvi Franchisee Agreement Date------------"+frExpiryDate+"**********"+franchisee.get(i).getFrId()+"**********"+frCode);
								    			 
										       long difference = frExpiryDate.getTime() - currentDate.getTime();
										       long daysBetween = (difference / (1000*60*60*24));
										       
										       if(daysBetween>=1 && daysBetween<=15) {
										    	   
										    	    
										    	   mailsubject = frExpiryAgreement+" will expiry soon for Outlet : "+frCode;
												   mailText = "Hello Sir/Madam,\n"+
																"<br>&nbsp;&nbsp; Your "+frExpiryAgreement+" will expiry soon for Outlet : "+frCode+" on "+new SimpleDateFormat("dd-MM-yyyy").format(frExpiryDate)+".\n"+
																"<br>&nbsp;&nbsp; Please renew your license.";
										    	  
												   Info info = EmailUtility.sendOrderEmail(senderEmail, senderPassword, franchisee.get(i).getFrEmail(), mailsubject, mailText);
										    	   System.out.println("INFO Mail--------"+info);
										    	   
										    	   if(info.isError()==false) {
										    		   String msg ="Welcome to Madhvi!\n" + 
										    		   		""+frCode+" your "+frExpiryAgreement+" will expire on "+new SimpleDateFormat("dd-MM-yyyy").format(frExpiryDate)+".\n" + 
										    		   		"Please renew your license.";
										    		   Info inf = EmailUtility.send(franchisee.get(i).getFrMob(), msg);
										    		   System.out.println("Resp----------"+franchisee.get(i).getFrMob()+"   "+frExpiryDate);
										    		   System.out.println("INFO SMS---------"+inf);
										    	   }
										       }else {
										    	   System.err.println(frExpiryAgreement+" Date is More than 15-----------"+frCode+"----"+daysBetween);
										       }
									              
										       System.out.println("Franchisee Agreement Number of Days between dates: "+daysBetween);
							    		   }
							    		 }
							       }
								}
			System.err.println("***********************************END*****************************************");
			
			}catch (Exception e) {
				System.err.println("Ex in crownForLicencesExpiry : "+e.getMessage());
				e.printStackTrace();
			}
			
		}

}
