package com.yunqi.jhf.vo;

import java.sql.Timestamp;
import java.util.List;

public class ShareBean {

	private int id; // 主键id

	private int suserId; // 分享用户id
	
	private String suserName; // 分享用户名称
	
	private String avatar; // 分享用户头像
	
	private int categoryId;//分享类型
	
	private String isShow;//分享类型
	
	private String isEnabled; //分享用户是否挂起 默认为 N
	
	private String contentText;//分享内容
	
	private String contentImage;//分享图片
	
	private String publishAddress;//发布地址
	
	private Timestamp  publishTime;//发布时间
	
	private List<String> images;//图片集合
	
	private int commentCount;//评论总数
	
	private List<ShareCommentBean> sharecomms;//分享评论集合
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public int getSuserId() {
		return suserId;
	}

	public void setSuserId(int suserId) {
		this.suserId = suserId;
	}

	public String getSuserName() {
		return suserName;
	}

	public void setSuserName(String suserName) {
		this.suserName = suserName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getContentImage() {
		return contentImage;
	}

	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}

	public String getPublishAddress() {
		return publishAddress;
	}

	public void setPublishAddress(String publishAddress) {
		this.publishAddress = publishAddress;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<ShareCommentBean> getSharecomms() {
		return sharecomms;
	}

	public void setSharecomms(List<ShareCommentBean> sharecomms) {
		this.sharecomms = sharecomms;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	
}
