package com.ats.webapi.component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ats.webapi.commons.Firebase;
import com.ats.webapi.model.Franchisee;
import com.ats.webapi.model.GetTotalAmt;
import com.ats.webapi.model.ShopAnivData;
import com.ats.webapi.model.pettycash.GetCashAdvAndExpAmt;
import com.ats.webapi.model.pettycash.OtherBillDetailAdv;
import com.ats.webapi.model.pettycash.PettyCashDao;
import com.ats.webapi.model.pettycash.PettyCashManagmt;
import com.ats.webapi.model.pettycash.SellBillDetailAdv;
import com.ats.webapi.model.pettycash.SpCakeAdv;
import com.ats.webapi.repo.GetTotalAmtRepo;
import com.ats.webapi.repo.OtherBillDetailAdvRepo;
import com.ats.webapi.repo.PettyCashManagmtRepo;
import com.ats.webapi.repo.SellBillDetailAdvRepo;
import com.ats.webapi.repo.SpCakeAdvRepo;
import com.ats.webapi.repository.FrAniversaryRepository;
import com.ats.webapi.repository.FranchiseSupRepository;
import com.ats.webapi.repository.FranchiseeRepository;
import com.ats.webapi.repository.GetCashAdvAndExpAmtRepo;

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

	// Petty Cash Day End Process every morning 6.00 am
	//@Scheduled(cron = "2 * * * * *")
	@Scheduled(cron = "0 0 7 * * *")
	public void crownForPettyCashDayEnd() {

		List<Franchisee> franchisee = new ArrayList<Franchisee>();
		franchisee = franchiseeRepository.findAllByDelStatusOrderByFrNameAsc(0);

		if (franchisee != null) {

			for (int j = 0; j < franchisee.size(); j++) {

				Franchisee fr = franchisee.get(j);
				System.err.println("FRA ------------------ "+fr);

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
							int pettyCashId = 0;
							try {

								cashAmt = petty.getTotalAmt();
								withdrawAmt = petty.getWithdrawalAmt();
								opnAmt = petty.getClosingAmt();
								cashEdtAmt = petty.getCashAmt();
								closAmt = opnAmt+cashAmt-withdrawAmt;
								pettyCashId = petty.getPettycashId();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							//String date1 = DateConvertor.convertToYMD(date);

							pettycash.setPettycashId(0);
							pettycash.setCardAmt(0);
							pettycash.setCashAmt(cashAmt);
							pettycash.setClosingAmt(closAmt);
							pettycash.setDate(sdf.parse(date));
							pettycash.setExFloat1(0);
							pettycash.setExInt1(0);
							pettycash.setExVar1("NA");
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

}
