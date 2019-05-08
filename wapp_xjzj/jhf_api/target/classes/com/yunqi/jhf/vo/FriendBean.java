package com.yunqi.jhf.vo;

import java.sql.Timestamp;

public class FriendBean {
	
	private int id;

	private int userId;
	
	private String userName;
	
	private String avatar;
	
	private int friendId;

	private String friendName;

	private String friendAvatar;

	private Timestamp createTime;
	
	private String lv;    //好友等级

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendAvatar() {
		return friendAvatar;
	}

	public void setFriendAvatar(String friendAvatar) {
		this.friendAvatar = friendAvatar;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	
}
