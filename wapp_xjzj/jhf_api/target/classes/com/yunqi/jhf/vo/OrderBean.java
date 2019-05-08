package com.yunqi.jhf.vo;

import java.util.Date;

/**
 * 
 * @author wangsong
 *
 */
public class OrderBean {

	private int id; // 主键id

	private String orderName; //订单名称

	private int userId; //用户id
	
	private String userName; // 用户名称

	private int userAddressId; // 地址id
	
	private String orderNo; // 订单编号
	
	private String orderTotalPrice; // 订单总价格

	private String orderStatus; // 订单状态 枚举值

	private String logisticsId; // 物流公司id

	private String logisticsName; // 物流公司名称

	private String logisticsNo; // 物流编号

	private String remark; // 买家留言

	private Date paymentTime; // 完成订单的时间

	private Date createTime; // 创建时间

	private Date updateTime; // 修改时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
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

	public int getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(int userAddressId) {
		this.userAddressId = userAddressId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", orderName=" + orderName + ", userId=" + userId + ", userName=" + userName
				+ ", userAddressId=" + userAddressId + ", orderNo=" + orderNo + ", orderTotalPrice=" + orderTotalPrice
				+ ", orderStatus=" + orderStatus + ", logisticsId=" + logisticsId + ", logisticsName=" + logisticsName
				+ ", logisticsNo=" + logisticsNo + ", remark=" + remark + ", paymentTime=" + paymentTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", getId()=" + getId()
				+ ", getOrderName()=" + getOrderName() + ", getUserId()=" + getUserId() + ", getUserName()="
				+ getUserName() + ", getUserAddressId()=" + getUserAddressId() + ", getOrderNo()=" + getOrderNo()
				+ ", getOrderTotalPrice()=" + getOrderTotalPrice() + ", getOrderStatus()=" + getOrderStatus()
				+ ", getLogisticsId()=" + getLogisticsId() + ", getLogisticsName()=" + getLogisticsName()
				+ ", getLogisticsNo()=" + getLogisticsNo() + ", getRemark()=" + getRemark() + ", getPaymentTime()="
				+ getPaymentTime() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
