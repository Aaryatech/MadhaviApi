package com.ats.webapi.model.dashboard;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DashOrderCount {

	@Id
	private int id;

	private int pending;
	private int accepted;
	private int process;
	private int deliveryPending;
	private int delivered;
	private int rejected;
	private int returned;
	private int cancelled;
	private String cashAmt;
	private String cardAmt;
	private String ePayAmt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getDeliveryPending() {
		return deliveryPending;
	}

	public void setDeliveryPending(int deliveryPending) {
		this.deliveryPending = deliveryPending;
	}

	public int getDelivered() {
		return delivered;
	}

	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public int getReturned() {
		return returned;
	}

	public void setReturned(int returned) {
		this.returned = returned;
	}

	public int getCancelled() {
		return cancelled;
	}

	public void setCancelled(int cancelled) {
		this.cancelled = cancelled;
	}

	public String getCashAmt() {
		return cashAmt;
	}

	public void setCashAmt(String cashAmt) {
		this.cashAmt = cashAmt;
	}

	public String getCardAmt() {
		return cardAmt;
	}

	public void setCardAmt(String cardAmt) {
		this.cardAmt = cardAmt;
	}

	public String getePayAmt() {
		return ePayAmt;
	}

	public void setePayAmt(String ePayAmt) {
		this.ePayAmt = ePayAmt;
	}

	@Override
	public String toString() {
		return "DashOrderCount [id=" + id + ", pending=" + pending + ", accepted=" + accepted + ", process=" + process
				+ ", deliveryPending=" + deliveryPending + ", delivered=" + delivered + ", rejected=" + rejected
				+ ", returned=" + returned + ", cancelled=" + cancelled + ", cashAmt=" + cashAmt + ", cardAmt="
				+ cardAmt + ", ePayAmt=" + ePayAmt + "]";
	}

}
