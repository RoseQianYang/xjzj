package com.yunqi.jhf.vo;

import java.sql.Timestamp;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ShareCommentBean {

	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("评论的用户ID")
    private int userId;
	
	@ApiModelProperty("分享id")
    private int shareId;
	
	@ApiModelProperty("评论的上一级评论,相当于本条是回复评论")
    private int parentComment;
	
	@ApiModelProperty("评论的上一级评论,相当于本条是回复评论名称")
    private String parentCommentName;
	
	@ApiModelProperty("评价的用户名字")
    private String userName;
	
	@ApiModelProperty("评论内容")
    private String commentContent;
	
	@ApiModelProperty("评论时间")
	private Timestamp commentTime;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	
	@ApiModelProperty("修改时间")
	private Timestamp updateTime;
	
	@ApiModelProperty("修改时间")
	private List<ShareCommentBean> sonShareComment;

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

	public int getShareId() {
		return shareId;
	}

	public void setShareId(int shareId) {
		this.shareId = shareId;
	}

	public int getParentComment() {
		return parentComment;
	}

	public void setParentComment(int parentComment) {
		this.parentComment = parentComment;
	}

	public String getParentCommentName() {
		return parentCommentName;
	}

	public void setParentCommentName(String parentCommentName) {
		this.parentCommentName = parentCommentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
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

	public List<ShareCommentBean> getSonShareComment() {
		return sonShareComment;
	}

	public void setSonShareComment(List<ShareCommentBean> sonShareComment) {
		this.sonShareComment = sonShareComment;
	}

	@Override
	public String toString() {
		return "ShareCommentBean [id=" + id + ", userId=" + userId + ", shareId=" + shareId + ", parentComment="
				+ parentComment + ", parentCommentName=" + parentCommentName + ", userName=" + userName
				+ ", commentContent=" + commentContent + ", commentTime=" + commentTime + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", sonShareComment=" + sonShareComment + "]";
	}

}
