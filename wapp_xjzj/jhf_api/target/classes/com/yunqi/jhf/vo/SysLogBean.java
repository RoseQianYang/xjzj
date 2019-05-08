package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class SysLogBean {
	
	@ApiModelProperty("唯一标识符")
    private int id;
	
	@ApiModelProperty("日志类型  1 后台来源类型 2 微信来源类型 ")
    private String logType;
	
	@ApiModelProperty("日志操作类型 1 登录  2 修改密码  3支付")
    private String logAction;
	
	@ApiModelProperty("日志内容")
    private String logContent ;
	
	@ApiModelProperty("创建日期")
	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogAction() {
		return logAction;
	}

	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
