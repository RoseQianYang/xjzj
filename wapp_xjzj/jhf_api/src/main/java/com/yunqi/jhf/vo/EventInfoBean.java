package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class EventInfoBean {

	@ApiModelProperty("促销活动id")
    private int id;
	
	@ApiModelProperty("活动分类名称")
	private String eventCateName;
	
	@ApiModelProperty("活动分类Id")
	private int eventCateId;
	
	@ApiModelProperty("活动名称")
    private String title;
	
	@ApiModelProperty("活动封面")
    private String cover;
	
	@ApiModelProperty("活动模板展示内容 16MB")
    private String content;
	
	@ApiModelProperty("是否首页显示，枚举型， Y 显示活动时间和名称 N 将过期的活动设为不显示")
    private String isShow;

	@ApiModelProperty("活动开始时间")
	private Timestamp startTime;
	
	@ApiModelProperty("活动结束时间")
	private Timestamp endTime;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	
	@ApiModelProperty("修改时间")
	private Timestamp updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventCateName() {
		return eventCateName;
	}

	public void setEventCateName(String eventCateName) {
		this.eventCateName = eventCateName;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	public int getEventCateId() {
		return eventCateId;
	}

	public void setEventCateId(int eventCateId) {
		this.eventCateId = eventCateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

}
