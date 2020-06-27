package com.ats.webapi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.webapi.model.PostBillHeader;
import com.ats.webapi.model.bill.Company;
import com.ats.webapi.model.grngvn.PostCreditNoteDetails;
import com.ats.webapi.model.grngvn.PostCreditNoteHeader;
import com.ats.webapi.repository.CompanyRepository;
import com.ats.webapi.repository.FrItemStockConfigureRepository;
import com.ats.webapi.repository.PostBillHeaderRepository;
import com.ats.webapi.repository.PostCreditNoteDetailsRepository;
import com.ats.webapi.repository.PostCreditNoteHeaderRepository;
import com.ats.webapi.repository.UpdateGrnGvnForCreditNoteRepository;
import com.ats.webapi.repository.UpdateSeetingForPBRepo;
import com.ats.webapi.repository.grngvnheader.UpdateGrnGvnHeaderForCNRepo;

@Service
public class PostCreditNoteServiceImpl implements PostCreditNoteService {

	@Autowired
	PostCreditNoteHeaderRepository postCreditNoteHeaderRepository;

	@Autowired
	PostCreditNoteDetailsRepository postCreditNoteDetailsRepository;

	@Autowired
	UpdateGrnGvnForCreditNoteRepository updateGrnGvnForCreditNoteRepository;

	@Autowired
	UpdateGrnGvnHeaderForCNRepo updateGrnGvnHeaderForCNRepo;

	@Autowired
	FrItemStockConfigureRepository frItemStockConfRepo;

	@Autowired
	UpdateSeetingForPBRepo updateSeetingForPBRepo;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	PostBillHeaderRepository postBillHeaderRepository;

	@Override
	public List<PostCreditNoteHeader> savePostCreditNote(List<PostCreditNoteHeader> postCreditNoteHeader) {

		PostCreditNoteHeader creditNoteHeader = null;

		List<PostCreditNoteHeader> postCreditNoteHeaderList = new ArrayList<PostCreditNoteHeader>();
		for (int i = 0; i < postCreditNoteHeader.size(); i++) {

			creditNoteHeader = new PostCreditNoteHeader();
			int isgrn = postCreditNoteHeader.get(i).getIsGrn();
			int crnSrNo = 0;
			String invoiceNo = null;

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			Company company = new Company();
			String date = simpleDateFormat.format(postCreditNoteHeader.get(i).getCrnDate());
			company = companyRepository.findByBillDate(date);

			int billNo = postCreditNoteHeader.get(i).getExInt1();

			// getPurchaseBillHeaderById
			PostBillHeader billHeader = null;

			try {
				billHeader = postBillHeaderRepository.findByBillNo(billNo);

			} catch (Exception e) {

				System.out.println("Exc in getPurchaseBillHeaderById rest Api " + e.getMessage());
				e.printStackTrace();
			}

			if (billHeader.getExVarchar2().equals("1")) {

				crnSrNo = frItemStockConfRepo.findBySettingKey("CRE_NOTE_CDC");

				int year = Calendar.getInstance().get(Calendar.YEAR);

				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

				SimpleDateFormat sdf=new SimpleDateFormat("yy");
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
				
				Calendar cal=Calendar.getInstance();
				
				String yearStr = "";

				if (month < 3) {
					
					Calendar cal1=Calendar.getInstance();
					cal1.add(Calendar.YEAR, -1);
					
					yearStr = sdf1.format(cal1.getTime()) + "-" + sdf.format(cal.getTime());
					
					//yearStr = year - 1 + "-" + year;
				} else {
					
					
					Calendar cal1=Calendar.getInstance();
					cal1.add(Calendar.YEAR, 1);
					
					yearStr = sdf1.format(cal.getTime()) + "-" + sdf.format(cal1.getTime());
					
					//yearStr = year + "-" + (year + 1);
				}

				invoiceNo = "CDC-R/" + yearStr + "/" + crnSrNo;
				postCreditNoteHeader.get(i).setCrnNo("" + invoiceNo);
				postCreditNoteHeader.get(i).setExInt2(1);

			} else {

				if (isgrn == 1) {

					crnSrNo = frItemStockConfRepo.findBySettingKey("CRE_NOTE_NO");

					invoiceNo = company.getExVar5();
					invoiceNo = invoiceNo + crnSrNo;

					postCreditNoteHeader.get(i).setCrnNo("" + invoiceNo);
				} else {

					crnSrNo = frItemStockConfRepo.findBySettingKey("CRE_NOTE_NO_GVN");
					invoiceNo = company.getExVar6();
					invoiceNo = invoiceNo + crnSrNo;

					postCreditNoteHeader.get(i).setCrnNo("" + invoiceNo);
				}
				
				postCreditNoteHeader.get(i).setExInt2(0);


			}

			// System.err.println("crnSrNo"+crnSrNo);

			creditNoteHeader = postCreditNoteHeaderRepository.save(postCreditNoteHeader.get(i));

			if (creditNoteHeader.getCrnId() != 0) {
				/*
				 * int result=
				 * updateGrnGvnForCreditNoteRepository.updateGrnGvnForCreditNoteInsert(
				 * creditNoteHeader.getGrnGvnId(), 1);
				 */
				System.err.println("crnSrNo  while update " + crnSrNo);
				int result = 0;

				if (billHeader.getExVarchar2().equals("1")) {
					result = updateSeetingForPBRepo.updateSeetingForPurBill(crnSrNo + 1, "CRE_NOTE_CDC");
				} else {
					if (isgrn == 1) {
						result = updateSeetingForPBRepo.updateSeetingForPurBill(crnSrNo + 1, "CRE_NOTE_NO");
					} else {
						result = updateSeetingForPBRepo.updateSeetingForPurBill(crnSrNo + 1, "CRE_NOTE_NO_GVN");
					}
				}

			}

			postCreditNoteHeaderList.add(creditNoteHeader);

			int res = 0;

			int crnId = creditNoteHeader.getCrnId();

			List<PostCreditNoteDetails> postCreditNoteDetailsList = postCreditNoteHeader.get(i)
					.getPostCreditNoteDetails();

			for (int j = 0; j < postCreditNoteDetailsList.size(); j++) {

				PostCreditNoteDetails postCreditNoteDetails = postCreditNoteDetailsList.get(j);

				postCreditNoteDetails.setCrnId(crnId);

				postCreditNoteDetailsRepository.save(postCreditNoteDetails);

				int result = updateGrnGvnForCreditNoteRepository
						.updateGrnGvnForCreditNoteInsert(postCreditNoteDetails.getGrnGvnId(), 1);

				int isCrnNoPresent = 0;
				try {
					isCrnNoPresent = updateGrnGvnHeaderForCNRepo.isCrnNoPresent(crnSrNo,
							postCreditNoteDetails.getGrnGvnHeaderId());
				} catch (Exception e) {
					isCrnNoPresent = 0;
				}
				if (isCrnNoPresent != 0) {
					res = updateGrnGvnHeaderForCNRepo.updateGrnGvnHeaderForCN(crnSrNo, 1,
							postCreditNoteDetails.getGrnGvnHeaderId());
				}
			}
		}

		return postCreditNoteHeaderList;
	}

	@Override
	public List<PostCreditNoteHeader> postCreditNoteForUpdate(List<PostCreditNoteHeader> postCreditNoteHeader) {

		PostCreditNoteHeader creditNoteHeader = null;

		List<PostCreditNoteHeader> postCreditNoteHeaderList = new ArrayList<PostCreditNoteHeader>();
		for (int i = 0; i < postCreditNoteHeader.size(); i++) {

			creditNoteHeader = new PostCreditNoteHeader();

			creditNoteHeader = postCreditNoteHeaderRepository.save(postCreditNoteHeader.get(i));

			postCreditNoteHeaderList.add(creditNoteHeader);

			int crnId = creditNoteHeader.getCrnId();

			List<PostCreditNoteDetails> postCreditNoteDetailsList = postCreditNoteHeader.get(i)
					.getPostCreditNoteDetails();

			for (int j = 0; j < postCreditNoteDetailsList.size(); j++) {

				PostCreditNoteDetails postCreditNoteDetails = postCreditNoteDetailsList.get(j);

				postCreditNoteDetails.setCrnId(crnId);

				postCreditNoteDetailsRepository.save(postCreditNoteDetails);

			}
		}

		return postCreditNoteHeaderList;
	}

}
