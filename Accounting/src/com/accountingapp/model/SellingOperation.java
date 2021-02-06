package com.accountingapp.model;

public class SellingOperation {
	private int businessPartner;
	private int item;
	private int itemQty;
	private int cashBox;
	private double itemPrice;
	public int getBusinessPartner() {
		return businessPartner;
	}
	public void setBusinessPartner(int businessPartner) {
		this.businessPartner = businessPartner;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public int getCashBox() {
		return cashBox;
	}
	public void setCashBox(int cashBox) {
		this.cashBox = cashBox;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
}
