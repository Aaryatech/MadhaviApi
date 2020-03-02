package com.ats.webapi.model.reportv2;

import java.util.List;

public class AdminCrNoteRegisterList {

	List<AdminCrNoteRegItem> crNoteRegItemList;
	List<CrNoteRegSp> crNoteRegSpList;

	public List<AdminCrNoteRegItem> getCrNoteRegItemList() {
		return crNoteRegItemList;
	}

	public void setCrNoteRegItemList(List<AdminCrNoteRegItem> crNoteRegItemList) {
		this.crNoteRegItemList = crNoteRegItemList;
	}

	public List<CrNoteRegSp> getCrNoteRegSpList() {
		return crNoteRegSpList;
	}

	public void setCrNoteRegSpList(List<CrNoteRegSp> crNoteRegSpList) {
		this.crNoteRegSpList = crNoteRegSpList;
	}

	@Override
	public String toString() {
		return "AdminCrNoteRegisterList [crNoteRegItemList=" + crNoteRegItemList + ", crNoteRegSpList="
				+ crNoteRegSpList + "]";
	}

}
