package com.accountingapp.model;

public class CashBoxTransferDTO {
	private int currentBoxId;
	private int transferedBoxId;
	private double transferBalance;
	public int getCurrentBoxId() {
		return currentBoxId;
	}
	public void setCurrentBoxId(int currentBoxId) {
		this.currentBoxId = currentBoxId;
	}
	public int getTransferedBoxId() {
		return transferedBoxId;
	}
	public void setTransferedBoxId(int transferedBoxId) {
		this.transferedBoxId = transferedBoxId;
	}
	public double getTransferBalance() {
		return transferBalance;
	}
	public void setTransferBalance(double transferBalance) {
		this.transferBalance = transferBalance;
	}
	
}
