package com.ats.webapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.GetTotalAmt;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.PettyCashEmp;
import com.ats.webapi.model.PettyCashHandover;
import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.Setting;
import com.ats.webapi.model.pettycash.FrEmpMaster;
import com.ats.webapi.model.pettycash.GetCashAdvAndExpAmt;
import com.ats.webapi.model.pettycash.OtherBillDetailAdv;
import com.ats.webapi.model.pettycash.PettyCashDao;
import com.ats.webapi.model.pettycash.PettyCashManagmt;
import com.ats.webapi.model.pettycash.SellBillDetailAdv;
import com.ats.webapi.model.pettycash.SpCakeAdv;
import com.ats.webapi.repo.FrEmpMasterRepo;
import com.ats.webapi.repo.GetTotalAmtRepo;
import com.ats.webapi.repo.OtherBillDetailAdvRepo;
import com.ats.webapi.repo.PettyCashEmpRepo;
import com.ats.webapi.repo.PettyCashHandoverRepo;
import com.ats.webapi.repo.PettyCashManagmtRepo;
import com.ats.webapi.repo.SellBillDetailAdvRepo;
import com.ats.webapi.repo.SpCakeAdvRepo;
import com.ats.webapi.repository.ExpressBillRepository;
import com.ats.webapi.repository.GetCashAdvAndExpAmtRepo;
import com.ats.webapi.repository.SettingRepository;

@RestController
public class PettyCashApiController {

	@Autowired
	PettyCashManagmtRepo pettyRepo;

	@Autowired
	SpCakeAdvRepo spRepo;

	@Autowired
	SellBillDetailAdvRepo sellBillRepo;

	@Autowired
	OtherBillDetailAdvRepo otherBillRepo;

	@Autowired
	GetCashAdvAndExpAmtRepo getCashAdvAndExpAmtRepo;

	@RequestMapping(value = { "/getPettyCashDetails" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashDetails(@RequestParam("frId") int frId) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByFrIdAndStatusLimit1(frId, 0);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}

	@RequestMapping(value = { "/getPettyCashList" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatus(frId, 0);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashList : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}

	@RequestMapping(value = { "/getPettyCashAmts" }, method = RequestMethod.POST)
	public PettyCashDao getPettyCashAmts(@RequestParam("frId") int frId, @RequestParam("date") String date) {
		PettyCashDao pettyDao = new PettyCashDao();
		try {
			List<SpCakeAdv> spCake = spRepo.getSpCakeAdv(frId, date);
			SellBillDetailAdv sellBillAdv = sellBillRepo.getSellBillDetailAdv(frId, date);
			OtherBillDetailAdv otherBill = otherBillRepo.getOtherBillDetailAdv(frId, date);

			pettyDao.setSpCakAdv(spCake);
			pettyDao.setSellBillAdv(sellBillAdv);
			pettyDao.setOtherBillAdv(otherBill);

			System.out.println("Date----------------" + pettyDao.toString());
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyDao;
	}

	@RequestMapping(value = { "/addPettyCash" }, method = RequestMethod.POST)
	public PettyCashManagmt addPettyCash(@RequestBody PettyCashManagmt pettycash) {
		PettyCashManagmt cash = new PettyCashManagmt();
		try {
			cash = pettyRepo.save(pettycash);
		} catch (Exception e) {
			System.err.println("Exception in addPettyCash : " + e.getMessage());
			e.printStackTrace();
		}
		return cash;
	}

	@RequestMapping(value = { "/getPettyCashById" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashById(@RequestParam("id") int id) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByPettycashId(id);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}
	
	@RequestMapping(value = { "/editPettyCashWithdrawAmt" }, method = RequestMethod.POST)
	public Info editPettyCashWithdrawAmt(@RequestParam("id") int id, @RequestParam("closeAmt") float closeAmt, 
			@RequestParam("withdrawl") float withdrawl) {
		Info info = new Info();
		try {
			int res = pettyRepo.changeWithdrawalAmt(id, closeAmt, withdrawl);
			if(res>0) {
				info.setError(false);
				info.setMessage("sucess");
			}else {
				info.setError(true);
				info.setMessage("fail");
			}
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = { "/getPettyCashListDateWise" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatusDateWise(frId, 0, fromDate, toDate);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashListDateWise : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}

	/*
	 * @Autowired PettyCashEmpRepo pettyEmpRepo;
	 * 
	 * @RequestMapping(value = { "/getAllPettyCashEmp"}, method =
	 * RequestMethod.POST) public List<PettyCashEmp>
	 * getAllPettyCashEmp(@RequestParam("frId") int frId){ List<PettyCashEmp>
	 * empList = new ArrayList<PettyCashEmp>(); try { empList =
	 * pettyEmpRepo.findByEmpFrIdAndDelStatus(frId,0); }catch (Exception e) {
	 * System.err.println("Exception in getAllPettyCashEmp : "+e.getMessage());
	 * e.printStackTrace(); } return empList; }
	 */

	@Autowired
	ExpressBillRepository expressBillRepository;

	@RequestMapping(value = { "/getPettyCashSellAmt" }, method = RequestMethod.POST)
	public SellBillHeader getPettyCashSellAmt(@RequestParam("fromTime") String fromTime,
			@RequestParam("toTime") String toTime, @RequestParam("frId") int frId) {
		SellBillHeader empList = new SellBillHeader();
		try {
			empList = expressBillRepository.getPettyCashSellingAmt(fromTime, toTime, frId);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashSellAmt : " + e.getMessage());
			e.printStackTrace();
		}
		return empList;
	}

	@Autowired
	PettyCashHandoverRepo pettyCashHandRepo;

	@RequestMapping(value = { "/getPettyCashHandOvrLastRecrd" }, method = RequestMethod.POST)
	public PettyCashHandover getPettyCashHandOvrLastRecrd(@RequestParam("frId") int frId,
			@RequestParam("lastdate") String lastdate) {
		PettyCashHandover data = new PettyCashHandover();
		try {
			data = pettyCashHandRepo.getLastRecordFrmPettyCashHndOvr(frId, lastdate);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashHandOvrLastRecrd : " + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping(value = { "/savePettyCashHandOver" }, method = RequestMethod.POST)
	public PettyCashHandover savePettyCashHandOver(@RequestBody PettyCashHandover cashHndOvr) {
		PettyCashHandover cash = new PettyCashHandover();
		try {
			cash = pettyCashHandRepo.save(cashHndOvr);
		} catch (Exception e) {
			System.err.println("Exception in savePettyCashHandOver : " + e.getMessage());
			e.printStackTrace();
		}
		return cash;
	}

	@RequestMapping(value = { "/getPettyCashHandByFrid" }, method = RequestMethod.POST)
	public List<PettyCashHandover> getPettyCashHandByFrid(@RequestParam int frId) {
		List<PettyCashHandover> list = new ArrayList<PettyCashHandover>();
		try {
			list = pettyCashHandRepo.findByFrIdAndDelStatus(frId, 0);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashHandByFrid : " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = { "/getCashHandOverTransctn" }, method = RequestMethod.POST)
	public List<PettyCashHandover> getCashHandOverTransctn(@RequestParam int frId, @RequestParam String fromDate,
			@RequestParam String toDate) {
		List<PettyCashHandover> list = new ArrayList<PettyCashHandover>();
		try {
			list = pettyCashHandRepo.findByFrIdAndDelStatusAndClosingDateBetween(frId, 0, fromDate, toDate);
			System.err.println("List-----------" + list);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashHandByFrid : " + e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	/***************************************
	 * Franchisee Employee
	 *****************************/

	@Autowired
	FrEmpMasterRepo frEmpRepo;

	@RequestMapping(value = { "/getAllFrEmpByFrid" }, method = RequestMethod.POST)
	public List<FrEmpMaster> getAllFrEmpByFrid(@RequestParam int frId) {
		List<FrEmpMaster> list = new ArrayList<FrEmpMaster>();
		try {
			list = frEmpRepo.findByFrIdAndDelStatus(frId, 0);
			System.err.println("List-----------" + list);
		} catch (Exception e) {
			System.err.println("Exception in getAllFrEmpByFrid : " + e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	@RequestMapping(value = { "/getFrEmpByEmpId" }, method = RequestMethod.POST)
	public FrEmpMaster getFrEmpByEmpId(@RequestParam int empId) {
		FrEmpMaster emp = new FrEmpMaster();
		try {
			emp = frEmpRepo.findByFrEmpId(empId);
		} catch (Exception e) {
			System.err.println("Exception in getFrEmpByEmpId : " + e.getMessage());
			e.printStackTrace();
		}
		return emp;
	}

	@Autowired
	SettingRepository settingRepository;

	@RequestMapping(value = { "/saveFrEmpDetails" }, method = RequestMethod.POST)
	public FrEmpMaster saveFrEmpDetails(@RequestBody FrEmpMaster emp) {
		FrEmpMaster frEmp = new FrEmpMaster();
		int id = emp.getFrEmpId();
		try {
			frEmp = frEmpRepo.save(emp);
			if (id == 0) {
				if (frEmp != null) {
					Setting setting = settingRepository.findBySettingId(57);
					int val = setting.getSettingValue() + 1;

					int value = settingRepository.udatekeyvalueForFrEmpCode(val);
				}
			}

		} catch (Exception e) {
			System.err.println("Exception in saveFrEmpDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return frEmp;
	}

	@RequestMapping(value = { "/delFrEmp" }, method = RequestMethod.POST)
	public Info delFrEmp(@RequestParam int empId) {
		Info info = new Info();
		try {
			int res = frEmpRepo.deleteEmpByfrEmpId(empId);
			if (res != 0) {
				info.setError(false);
				info.setMessage("Sucess");
			} else {
				info.setError(true);
				info.setMessage("Fail");
			}
		} catch (Exception e) {
			System.err.println("Exception in saveFrEmpDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = { "/checkUniqueContactNo" }, method = RequestMethod.POST)
	public Info checkUniqueContactNo(int frId, String mobNo) {
		Info info = new Info();
		try {
			FrEmpMaster emp = new FrEmpMaster();
			emp = frEmpRepo.findByFrIdAndFrEmpContactAndDelStatus(frId, mobNo, 0);
			System.out.println("Emp-------" + emp);
			if (emp != null) {
				System.out.println("Contact No. Found");
				info.setError(false);
				info.setMessage("" + emp.getFrEmpId());
			} else {
				System.out.println("Contact No. Not Found");
				info.setError(true);
				info.setMessage("0");
			}
		} catch (Exception e) {
			System.err.println("Exception in checkUniqueContactNo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = { "/getTrCashAmtAndAdvAmtAndExpAmt" }, method = RequestMethod.POST)
	public GetCashAdvAndExpAmt getTrCashAmtAndAdvAmtAndExpAmt(int frId, String date) {
		GetCashAdvAndExpAmt data = new GetCashAdvAndExpAmt();
		System.err.println("PARAM------DATE---- " + date);
		System.err.println("PARAM---------- " + frId + "---------------------------- " + date);
		try {
			data = getCashAdvAndExpAmtRepo.getAmt(frId, date);
			System.err.println("AMT--------------" + data);
		} catch (Exception e) {
			System.err.println("Exception in getTrCashAmtAndAdvAmtAndExpAmt : " + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping(value = { "/getLastCashHandover" }, method = RequestMethod.POST)
	public PettyCashHandover getLastCashHandover(@RequestParam("frId") int frId,
			@RequestParam("lastdate") String lastdate) {
		PettyCashHandover data = new PettyCashHandover();
		try {
			data = pettyCashHandRepo.getLastRecord(frId, lastdate);
		} catch (Exception e) {
			System.err.println("Exception in getLastCashHandover : " + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping(value = { "/getPettyCashAmtForApp" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashAmtForApp(@RequestParam("frId") int frId) {

		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByFrIdAndStatusLimit1(frId, 0);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(cal.getTime()); 

			if (petty != null) {

				cal = Calendar.getInstance();

				cal.setTimeInMillis(petty.getDate().getTime());

				// Add 1 day
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = sdf.format(cal.getTime());

			}

			PettyCashDao pettyDao = new PettyCashDao();
			List<SpCakeAdv> spCake = spRepo.getSpCakeAdv(frId, date);
			SellBillDetailAdv sellBillAdv = sellBillRepo.getSellBillDetailAdv(frId, date);
			OtherBillDetailAdv otherBill = otherBillRepo.getOtherBillDetailAdv(frId, date);

			pettyDao.setSpCakAdv(spCake);
			pettyDao.setSellBillAdv(sellBillAdv);
			pettyDao.setOtherBillAdv(otherBill);

			System.out.println("Date----------------" + pettyDao.toString());

			List<Float> spList = new ArrayList<>();

			List<Float> sellBillAdvList = new ArrayList<>();

			List<Float> otherBillAdvList = new ArrayList<>();

			if (pettyDao != null) {
				if (pettyDao.getSpCakAdv() != null) {
					for (int i = 0; i < pettyDao.getSpCakAdv().size(); i++) {
						spList.add(pettyDao.getSpCakAdv().get(i).getMrp());
						spList.add(pettyDao.getSpCakAdv().get(i).getAdvance());

					}
				}
			}

			System.out.println("List1-------------" + spList);

			float lastAdv = spList.get(1);
			System.out.println("LastAdv-------------" + lastAdv);

			float mrp = spList.get(2);
			System.out.println("MRP-------------" + mrp);

			float currentAdv = spList.get(3);
			System.out.println("Today-------------" + currentAdv);

			float calAdv = mrp - currentAdv;
			float amt = calAdv + lastAdv;
			System.out.println("Total Adv-------------" + amt);

			float sellBillAdv1 = pettyDao.getSellBillAdv().getSellQtyMrp();
			System.out.println("SellBillDetailAdv------------" + sellBillAdv1);

			float othrBilAdv = pettyDao.getOtherBillAdv().getBillDetailItemMrp();
			System.out.println("OtherBillDetailAdv-----------" + othrBilAdv);

			float cashAmt = amt + sellBillAdv1 + othrBilAdv;
			System.out.println("Cash Amt-------------" + cashAmt);

			GetCashAdvAndExpAmt data = new GetCashAdvAndExpAmt();
			data = getCashAdvAndExpAmtRepo.getAmt(frId, date);
			
			petty.setTotalAmt(data.getTrCashAmt()+data.getAdvAmt()-data.getExpAmt());
			
			

		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		
		return petty;
	}
	
	@Autowired
	GetTotalAmtRepo getTotalAmtRepo;
	
	
	@RequestMapping("/getTotalPOSCreditNoteAmt")
	public @ResponseBody GetTotalAmt getTotalPOSCreditNoteAmt(@RequestParam int frId, @RequestParam String date) throws ParseException {
		GetTotalAmt amt = new GetTotalAmt();
		try {
			amt = getTotalAmtRepo.getTotalPOSCreditNoteAmt(frId, date);
		} catch (Exception e) {
			System.out.println("Exc in getTotalPOSCreditNoteAmt" + e.getMessage());
			e.printStackTrace();
		}
		return amt;
	}
	
	
	@RequestMapping(value = { "/updateFrEmpPassword" }, method = RequestMethod.POST)
	public Info updateFrEmpPassword(int empId, String pass) {
		Info info = new Info();
		try {
			int res = frEmpRepo.updateEmpPass(empId, pass);

			if (res!=0) {
				info.setError(false);
				info.setMessage("Success");
			} else {
				info.setError(true);
				info.setMessage("Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
			info.setMessage("Failed");
		}
		return info;
	}
	
	

}
