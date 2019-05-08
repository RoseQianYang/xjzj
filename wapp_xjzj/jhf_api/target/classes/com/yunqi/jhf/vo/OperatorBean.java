package com.yunqi.jhf.vo;

public class OperatorBean {

	private int id; // 主键
	
	private String accountName; //用户名
	
	private String realName; //真实姓名
	
	private String mobile; //手机号
	
	private int roleId; //角色ID
	
	private String roleName; //角色名称
	
	private String remark; //操作员备注
	
	private String isEnabled; //是否冻结

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "OperatorBean [id=" + id + ", accountName=" + accountName + ", realName=" + realName + ", mobile="
				+ mobile + ", roleId=" + roleId + ", roleName=" + roleName + ", remark=" + remark + ", isEnabled="
				+ isEnabled + "]";
	}

}
