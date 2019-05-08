package com.yunqi.jhf.vo;

public class UserStatisticsBean {
	
	private String grpName; //分组名
	
	private int sumun; //数量

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public int getSumun() {
		return sumun;
	}

	public void setSumun(int sumun) {
		this.sumun = sumun;
	}

	@Override
	public String toString() {
		return "UserStatisticsBean [grpName=" + grpName + ", sumun=" + sumun + "]";
	}
	
}
