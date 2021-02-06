package com.accountingapp.model;

public class InventoryItem extends InventoryGroup{
	private Integer itemId;
	private String itemName;
	private String itemCode;
	private Integer itemGroup;
	private Integer itemQty;
	private Double itemPrice;
	
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(Integer itemGroup) {
		this.itemGroup = itemGroup;
	}
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
}
