package com.accountingapp.model;

public class FinancialMovement {
	private Integer id;
	private Integer operationType;
	private long operationTime;
	private Integer operationItem;
	private Integer operationQty;
	private Integer operationCustomer;
	private double itemUnitPrice;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public long getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}
	public Integer getOperationItem() {
		return operationItem;
	}
	public void setOperationItem(Integer operationItem) {
		this.operationItem = operationItem;
	}
	public Integer getOperationQty() {
		return operationQty;
	}
	public void setOperationQty(Integer operationQty) {
		this.operationQty = operationQty;
	}
	public Integer getOperationCustomer() {
		return operationCustomer;
	}
	public void setOperationCustomer(Integer operationCustomer) {
		this.operationCustomer = operationCustomer;
	}
	public double getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	
}
