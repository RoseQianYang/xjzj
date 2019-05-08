package com.yunqi.jhf.vo;

import java.sql.Timestamp;


import io.swagger.annotations.ApiModelProperty;

public class SalesVolumeStatsBean {
	
	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("商品ID")
    private int productId;
	
	@ApiModelProperty("商品名称")
	private String productName;
	
	@ApiModelProperty("商品分类ID")
    private int productCateId;
	
	@ApiModelProperty("商品分类名称")
	private String productCateName;
	
	@ApiModelProperty("商品品牌ID")
    private int productBrandId;
	
	@ApiModelProperty("商品品牌名称")
	private String productBrandName;
	
	@ApiModelProperty("商品数量")
    private int productCount;
	
	@ApiModelProperty("本次购买的价格")
    private int productPrice;
	
	@ApiModelProperty("订单状态")
    private String orderStatus;
	
	@ApiModelProperty("商品颜色")
    private String productColor;
	
	@ApiModelProperty("商品尺码")
    private String productSize;
	
	@ApiModelProperty("柱状图时间")
	private String grpName;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCateId() {
		return productCateId;
	}

	public void setProductCateId(int productCateId) {
		this.productCateId = productCateId;
	}

	public String getProductCateName() {
		return productCateName;
	}

	public void setProductCateName(String productCateName) {
		this.productCateName = productCateName;
	}

	public int getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(int productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SalesVolumeStatsBean [id=" + id + ", productId=" + productId + ", productName=" + productName
				+ ", productCateId=" + productCateId + ", productCateName=" + productCateName + ", productBrandId="
				+ productBrandId + ", productBrandName=" + productBrandName + ", productCount=" + productCount
				+ ", productPrice=" + productPrice + ", orderStatus=" + orderStatus + ", productColor=" + productColor
				+ ", productSize=" + productSize + ", grpName=" + grpName + ", createTime=" + createTime + "]";
	}
	
}
