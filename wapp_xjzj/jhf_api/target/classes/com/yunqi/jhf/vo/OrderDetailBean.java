package com.yunqi.jhf.vo;

import java.sql.Timestamp;

/**
 * 
 * @author wangsong
 *
 */
public class OrderDetailBean {

	private int id; // 主键id

	private int orderId; // 订单id

	private String orderName; // 订单名称
	
	private int orderTotalPrice; // 订单总价格
	
	private String orderNo; // 订单编号
	
	private String orderStatus; // 订单状态
	
	private int userId; // 用户id
	
	private String userName; // 用户名称
	
	private String userAddress; // 用户地址
	
	private String userConsignee; // 用户联系方式
	
	private int productId; // 产品id
	
	private String productTitle; // 产品名称
	
	private String productCover; // 产品封面
	
	private int productCateId; // 产品分类id
	
	private String productCateTitle; // 产品分类名称
	
    private int productBrandId; // 产品品牌id
	
	private String productBrandTitle; // 产品品牌名称
	
	private int productCount; // 产品数量
	
	private int productPrice; // 产品价格
	
	private String productColor; // 产品颜色
	
	private String productSize; // 产品尺码
	
	private Timestamp createTime; //创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	public int getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(int orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserConsignee() {
		return userConsignee;
	}

	public void setUserConsignee(String userConsignee) {
		this.userConsignee = userConsignee;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}
	
	public int getProductCateId() {
		return productCateId;
	}

	public void setProductCateId(int productCateId) {
		this.productCateId = productCateId;
	}

	public String getProductCateTitle() {
		return productCateTitle;
	}

	public void setProductCateTitle(String productCateTitle) {
		this.productCateTitle = productCateTitle;
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(int productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getProductBrandTitle() {
		return productBrandTitle;
	}

	public void setProductBrandTitle(String productBrandTitle) {
		this.productBrandTitle = productBrandTitle;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrderDetailBean [id=" + id + ", orderId=" + orderId + ", orderName=" + orderName + ", orderTotalPrice="
				+ orderTotalPrice + ", orderNo=" + orderNo + ", orderStatus=" + orderStatus + ", userId=" + userId
				+ ", userName=" + userName + ", userAddress=" + userAddress + ", userConsignee=" + userConsignee
				+ ", productId=" + productId + ", productTitle=" + productTitle + ", productCover=" + productCover
				+ ", productCateId=" + productCateId + ", productCateTitle=" + productCateTitle + ", productBrandId="
				+ productBrandId + ", productBrandTitle=" + productBrandTitle + ", productCount=" + productCount
				+ ", productPrice=" + productPrice + ", productColor=" + productColor + ", productSize=" + productSize
				+ ", createTime=" + createTime + "]";
	}

}
