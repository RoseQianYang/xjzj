package com.yunqi.jhf.vo;

import java.sql.Timestamp;

public class IntegralBean {
	
	  private int id; // 唯一标识
	  
	  private int userId; // 用户id
	  
	  private String userName; // 用户名称
	  
	  private int integralNum; // 积分数
	  
	  private String introduction; // 积分说明
	  
	  private String integralSource; // 积分来源
	  
	  private String integralType; // 积分类型
	  
	  private int userIntegralSum; // 用户积分总数
	  
	  private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIntegralNum() {
		return integralNum;
	}

	public void setIntegralNum(int integralNum) {
		this.integralNum = integralNum;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getIntegralSource() {
		return integralSource;
	}

	public void setIntegralSource(String integralSource) {
		this.integralSource = integralSource;
	}
	
	public String getIntegralType() {
		return integralType;
	}

	public void setIntegralType(String integralType) {
		this.integralType = integralType;
	}

	public int getUserIntegralSum() {
		return userIntegralSum;
	}

	public void setUserIntegralSum(int userIntegralSum) {
		this.userIntegralSum = userIntegralSum;
	}

	@Override
	public String toString() {
		return "IntegralBean [id=" + id + ", userId=" + userId + ", userName=" + userName + ", integralNum="
				+ integralNum + ", introduction=" + introduction + ", integralSource=" + integralSource
				+ ", userIntegralSum=" + userIntegralSum + ", createTime=" + createTime + "]";
	}
}
