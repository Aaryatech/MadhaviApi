package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.PettyCashEmp;
import com.ats.webapi.model.PettyCashHandover;
import com.ats.webapi.model.SellBillHeader;
import com.ats.webapi.model.pettycash.OtherBillDetailAdv;
import com.ats.webapi.model.pettycash.PettyCashDao;
import com.ats.webapi.model.pettycash.PettyCashManagmt;
import com.ats.webapi.model.pettycash.SellBillDetailAdv;
import com.ats.webapi.model.pettycash.SpCakeAdv;
import com.ats.webapi.repo.OtherBillDetailAdvRepo;
import com.ats.webapi.repo.PettyCashEmpRepo;
import com.ats.webapi.repo.PettyCashHandoverRepo;
import com.ats.webapi.repo.PettyCashManagmtRepo;
import com.ats.webapi.repo.SellBillDetailAdvRepo;
import com.ats.webapi.repo.SpCakeAdvRepo;
import com.ats.webapi.repository.ExpressBillRepository;

@RestController
public class PettyCashApiController {

	@Autowired PettyCashManagmtRepo pettyRepo;
	
	@Autowired SpCakeAdvRepo spRepo;
	
	@Autowired SellBillDetailAdvRepo sellBillRepo;
	
	@Autowired OtherBillDetailAdvRepo otherBillRepo;
	
	@RequestMapping(value = { "/getPettyCashDetails" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashDetails(@RequestParam("frId") int frId) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByFrIdAndStatusLimit1(frId, 0);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : "+e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}
	
	@RequestMapping(value = { "/getPettyCashList" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatus(frId, 0);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashList : "+e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}
	
	
	@RequestMapping(value = { "/getPettyCashAmts" }, method = RequestMethod.POST)
	public PettyCashDao getPettyCashAmts(@RequestParam("frId") int frId, @RequestParam("date") String date) {
		PettyCashDao pettyDao = new PettyCashDao();
		try {
			List<SpCakeAdv>  spCake = spRepo.getSpCakeAdv(frId, date);
			SellBillDetailAdv sellBillAdv = sellBillRepo.getSellBillDetailAdv(frId, date);
		    OtherBillDetailAdv otherBill = otherBillRepo.getOtherBillDetailAdv(frId, date);
		
		    pettyDao.setSpCakAdv(spCake);
			pettyDao.setSellBillAdv(sellBillAdv);
			pettyDao.setOtherBillAdv(otherBill);
			
			System.out.println("Date----------------"+pettyDao.toString());
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : "+e.getMessage());
			e.printStackTrace();
		}
		return pettyDao;
	}
	
	@RequestMapping(value = { "/addPettyCash" }, method = RequestMethod.POST)
	public PettyCashManagmt addPettyCash(@RequestBody PettyCashManagmt pettycash) {
		PettyCashManagmt cash = new PettyCashManagmt();
		try {
			cash=pettyRepo.save(pettycash);
		}catch (Exception e) {
			System.err.println("Exception in addPettyCash : "+e.getMessage());
			e.printStackTrace();
		}
		return cash;
	}
	
	
	@RequestMapping(value = { "/getPettyCashById" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashById(@RequestParam("id") int id) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByPettycashId(id);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : "+e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}
	
	@RequestMapping(value = { "/getPettyCashListDateWise" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatusDateWise(frId, 0, fromDate, toDate);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashListDateWise : "+e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}
	
	@Autowired PettyCashEmpRepo pettyEmpRepo;
	@RequestMapping(value = { "/getAllPettyCashEmp"}, method = RequestMethod.POST)
	public List<PettyCashEmp> getAllPettyCashEmp(@RequestParam("frId") int frId){
		List<PettyCashEmp> empList = new ArrayList<PettyCashEmp>();
		try {
			empList = pettyEmpRepo.findByEmpFrIdAndDelStatus(frId,0);
		}catch (Exception e) {
			System.err.println("Exception in getAllPettyCashEmp : "+e.getMessage());
			e.printStackTrace();
		}
		return empList;
	}
	
	@Autowired ExpressBillRepository  expressBillRepository;
	@RequestMapping(value = { "/getPettyCashSellAmt"}, method = RequestMethod.POST)
	public SellBillHeader getPettyCashSellAmt(@RequestParam("fromTime") String fromTime, @RequestParam("toTime") String toTime,
			@RequestParam("frId") int frId){
		SellBillHeader empList = new SellBillHeader();
		try {
			empList = expressBillRepository.getPettyCashSellingAmt(fromTime, toTime, frId);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashSellAmt : "+e.getMessage());
			e.printStackTrace();
		}
		return empList;
	}
	
	@Autowired PettyCashHandoverRepo pettyCashHandRepo;
	
	@RequestMapping(value = { "/getPettyCashHandOvrLastRecrd" }, method = RequestMethod.POST)
	public PettyCashHandover getPettyCashHandOvrLastRecrd(@RequestParam("frId") int frId, @RequestParam("lastdate") String lastdate) {
		PettyCashHandover data = new PettyCashHandover();
		try {
			data = pettyCashHandRepo.getLastRecordFrmPettyCashHndOvr(frId, lastdate);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashHandOvrLastRecrd : "+e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = { "/savePettyCashHandOver" }, method = RequestMethod.POST)
	public PettyCashHandover savePettyCashHandOver(@RequestBody PettyCashHandover cashHndOvr) {
		PettyCashHandover cash = new PettyCashHandover();
		try {
			cash = pettyCashHandRepo.save(cashHndOvr);
		}catch (Exception e) {
			System.err.println("Exception in savePettyCashHandOver : "+e.getMessage());
			e.printStackTrace();
		}
		return cash;
	}
	
	@RequestMapping(value = { "/getPettyCashHandByFrid" }, method = RequestMethod.POST)
	public List<PettyCashHandover> getPettyCashHandByFrid(@RequestParam int frId) {
		List<PettyCashHandover> list = new ArrayList<PettyCashHandover>();
		try {
			list = pettyCashHandRepo.findByFrIdAndDelStatus(frId, 0);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashHandByFrid : "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value= {"/getCashHandOverTransctn"}, method=RequestMethod.POST)
	public List<PettyCashHandover> getCashHandOverTransctn(@RequestParam int frId, @RequestParam String fromDate, @RequestParam String toDate){
		List<PettyCashHandover> list = new ArrayList<PettyCashHandover>();
		try {
			list = pettyCashHandRepo.findByFrIdAndDelStatusAndClosingDateBetween(frId, 0, fromDate, toDate);
		System.err.println("List-----------"+list);
		}catch (Exception e) {
			System.err.println("Exception in getPettyCashHandByFrid : "+e.getMessage());
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	
}


