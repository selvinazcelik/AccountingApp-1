package com.accountingapp.model;

import java.util.List;

public class UserMenuDTO {
	private Integer id;
	private String menuLabel;
	private String menuSrc;
	private List<Integer> roles;
	private int role;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenuLabel() {
		return menuLabel;
	}
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	public String getMenuSrc() {
		return menuSrc;
	}
	public void setMenuSrc(String menuSrc) {
		this.menuSrc = menuSrc;
	}
	public List<Integer> getRoles() {
		return roles;
	}
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
}
