package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class EventProductBean {

	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("活动id")
	private int eventId; 
	
	@ApiModelProperty("商品名称")
    private String title;
	
	@ApiModelProperty("产品封面")
    private String cover;
	
	@ApiModelProperty("产品id")
    private int productId; 
	
	@ApiModelProperty("产品原价格")
    private int productPrice; 
	
	@ApiModelProperty("活动产品价格")
    private int eventPrice;
	
	@ApiModelProperty("活动开始的时间")
    private Timestamp startTime; 
	
	@ApiModelProperty("活动结束的时间")
	private Timestamp endTime; 
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getEventPrice() {
		return eventPrice;
	}

	public void setEventPrice(int eventPrice) {
		this.eventPrice = eventPrice;
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

	@Override
	public String toString() {
		return "EventProductBean [id=" + id + ", eventId=" + eventId + ", title=" + title + ", cover=" + cover
				+ ", productId=" + productId + ", productPrice=" + productPrice + ", eventPrice=" + eventPrice
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}

	
	
}
