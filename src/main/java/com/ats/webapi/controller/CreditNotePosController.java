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

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.CreditNotePos;
import com.ats.webapi.model.CreditNotePosHeaderDisp;
import com.ats.webapi.model.Setting;
import com.ats.webapi.model.advorder.GetAdvanceOrderList;
import com.ats.webapi.repo.CreditNotePosRepo;
import com.ats.webapi.repository.CreditNotePosHeaderDispRepo;
import com.ats.webapi.repository.SettingRepository;

@RestController
public class CreditNotePosController {

	@Autowired
	CreditNotePosRepo creditNotePosRepo;

	@Autowired
	SettingRepository settingRepository;

	@Autowired
	CreditNotePosHeaderDispRepo creditNotePosHeaderDispRepo;

	@RequestMapping(value = { "/savePosCreditNote" }, method = RequestMethod.POST)
	public @ResponseBody CreditNotePos savePosCreditNote(@RequestBody CreditNotePos creditNotePos) {

		CreditNotePos res = null;
		int id = creditNotePos.getCrnDetailNo();

		try {

			res = creditNotePosRepo.save(creditNotePos);

			if (id == 0) {
				if (res != null) {
					Setting setting = settingRepository.findBySettingId(58);
					int val = setting.getSettingValue() + 1;

					int value = settingRepository.udatekeyvalueForId(58, val);

				}
			}

		} catch (Exception e) {
			res = new CreditNotePos();
		}
		return res;

	}

	@RequestMapping(value = { "/saveNewPosCreditNoteList" }, method = RequestMethod.POST)
	public @ResponseBody List<CreditNotePos> savePosCreditNote(@RequestBody List<CreditNotePos> creditNotePos) {

		List<CreditNotePos> res = null;

		try {

			res = creditNotePosRepo.save(creditNotePos);

			if (res != null) {
				Setting setting = settingRepository.findBySettingId(58);
				int val = setting.getSettingValue() + 1;

				int value = settingRepository.udatekeyvalueForId(58, val);
				System.err.println("UPDATE CRN NO --------------------- " + value);

			}

		} catch (Exception e) {
			res = new ArrayList<>();
		}
		return res;

	}

	@RequestMapping(value = { "/getPosCreditNoteHeaderDisp" }, method = RequestMethod.POST)
	public @ResponseBody List<CreditNotePosHeaderDisp> getPosCreditNoteHeaderDisp(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam int custId) {
		List<CreditNotePosHeaderDisp> advList = new ArrayList<CreditNotePosHeaderDisp>();

		try {
			advList = creditNotePosHeaderDispRepo.getCrnPosHeader(fromDate, toDate, custId);

		} catch (Exception e) {
			System.out.println("Exce in getPosCreditNoteHeaderDisp  " + e.getMessage());
			e.printStackTrace();
		}

		return advList;
	}

	@RequestMapping(value = { "/getPosCreditNoteListByCrnNo" }, method = RequestMethod.POST)
	public @ResponseBody List<CreditNotePos> getPosCreditNoteListByCrnNo(@RequestParam int crnNo) {
		List<CreditNotePos> advList = new ArrayList<CreditNotePos>();

		try {
			advList = creditNotePosRepo.getCrnDetailsByCrnNo(crnNo);

		} catch (Exception e) {
			System.out.println("Exce in getPosCreditNoteListByCrnNo  " + e.getMessage());
			e.printStackTrace();
		}

		return advList;
	}

	@RequestMapping(value = { "/editNewPosCreditNoteList" }, method = RequestMethod.POST)
	public @ResponseBody List<CreditNotePos> editPosCreditNote(@RequestBody List<CreditNotePos> creditNotePos) {

		List<CreditNotePos> res = null;

		try {

			res = creditNotePosRepo.save(creditNotePos);

		} catch (Exception e) {
			res = new ArrayList<>();
		}
		return res;

	}

}
