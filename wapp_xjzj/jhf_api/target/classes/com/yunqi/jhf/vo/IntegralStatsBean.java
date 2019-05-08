package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class IntegralStatsBean {
	
	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("用户id")
    private int userId;
	
	@ApiModelProperty("用户名称")
	private String userName;
	
	@ApiModelProperty("积分数")
    private int integralNum;
	
	@ApiModelProperty("积分说明")
    private String introduction;
	
	@ApiModelProperty("创建时间")
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

	@Override
	public String toString() {
		return "IntegralStatsBean [id=" + id + ", userId=" + userId + ", userName=" + userName + ", integralNum="
				+ integralNum + ", introduction=" + introduction + ", createTime=" + createTime + "]";
	}
	
}
