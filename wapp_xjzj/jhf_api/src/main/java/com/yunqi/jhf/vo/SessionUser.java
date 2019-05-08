package com.yunqi.jhf.vo;

import java.util.List;

import com.yunqi.jhf.dao.domain.TPermissions;

public class SessionUser {

	private int id;
	
	private String accountName;
	
	private String realName;
	
	private String mobile;
	
	private String remark;
	
	private String menus;
	
	private List<TPermissions> permissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public List<TPermissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<TPermissions> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "SessionUser [id=" + id + ", accountName=" + accountName + ", realName=" + realName 
				+ ", mobile=" + mobile + ", remark=" + remark + ", menus=" + menus + ", permissions=" 
				+ permissions + "]";
	}
}
