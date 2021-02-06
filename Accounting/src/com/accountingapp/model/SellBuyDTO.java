package com.accountingapp.model;

public class SellBuyDTO {
	private int id;
	private String bpName;
	private int bpId;
	private int operationType;
	private String itemName;
	private String itemCode;
	private int itemId;
	private double balance;
	private int itemQty;
	private double itemUnitPrice;
	private double totalPrice;
	private String operationTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBpName() {
		return bpName;
	}
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	public int getBpId() {
		return bpId;
	}
	public void setBpId(int bpId) {
		this.bpId = bpId;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public double getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	
}
