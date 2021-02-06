package com.accountingapp.model;

public class CashBox {
	private Integer id;
	private String cashBoxName;
	private double cashBoxBalance;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCashBoxName() {
		return cashBoxName;
	}
	public void setCashBoxName(String cashBoxName) {
		this.cashBoxName = cashBoxName;
	}
	public double getCashBoxBalance() {
		return cashBoxBalance;
	}
	public void setCashBoxBalance(double cashBoxBalance) {
		this.cashBoxBalance = cashBoxBalance;
	}
	
}
