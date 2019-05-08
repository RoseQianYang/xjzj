package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class IntegralConversionBean {
	
	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("用户id")
    private int userId;
	
	@ApiModelProperty("用户名称")
	private String userName;
	
	@ApiModelProperty("用户积分总数")
	private String userIntegralSum;
	
	@ApiModelProperty("兑换申请积分数")
    private int conversionNum;
	
	@ApiModelProperty("兑换申请积分说明")
    private String introduction;
	
	@ApiModelProperty("兑换申请联系电话")
    private String mobile;
	
	@ApiModelProperty("兑换申请审核状态，枚举值，Y 已审核 N 待审核")
    private String conversionStatus;
	
	@ApiModelProperty("兑换申请时间")
	private Timestamp createTime;
	
	@ApiModelProperty("审核时间")
	private Timestamp updateTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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

	public int getConversionNum() {
		return conversionNum;
	}

	public void setConversionNum(int conversionNum) {
		this.conversionNum = conversionNum;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getConversionStatus() {
		return conversionStatus;
	}

	public void setConversionStatus(String conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserIntegralSum() {
		return userIntegralSum;
	}

	public void setUserIntegralSum(String userIntegralSum) {
		this.userIntegralSum = userIntegralSum;
	}
	
}
