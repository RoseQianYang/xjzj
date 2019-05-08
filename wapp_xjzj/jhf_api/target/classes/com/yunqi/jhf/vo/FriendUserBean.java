package com.yunqi.jhf.vo;

public class FriendUserBean {
	
	// 根据当前 friendId 查询到 对应的 2级和1级
	
    private int userIdOne; // friend当前上级 好友Id 2级
	
	private String friendNameOne; // 2级 好友名字
	
	private int userIdTwo; // friend当前上级的上级（1级） 好友Id
		
	private String friendNameTwo; // （1级） 好友名字

	public int getUserIdOne() {
		return userIdOne;
	}

	public void setUserIdOne(int userIdOne) {
		this.userIdOne = userIdOne;
	}

	public String getFriendNameOne() {
		return friendNameOne;
	}

	public void setFriendNameOne(String friendNameOne) {
		this.friendNameOne = friendNameOne;
	}

	public int getUserIdTwo() {
		return userIdTwo;
	}

	public void setUserIdTwo(int userIdTwo) {
		this.userIdTwo = userIdTwo;
	}

	public String getFriendNameTwo() {
		return friendNameTwo;
	}

	public void setFriendNameTwo(String friendNameTwo) {
		this.friendNameTwo = friendNameTwo;
	}

	@Override
	public String toString() {
		return "FriendUserBean [userIdOne=" + userIdOne + ", friendNameOne=" + friendNameOne + ", userIdTwo="
				+ userIdTwo + ", friendNameTwo=" + friendNameTwo + "]";
	}

}
