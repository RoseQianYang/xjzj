package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class ProductAttributeBean {

	@ApiModelProperty("唯一标识")
	private int id = 0;

	@ApiModelProperty("商品素材图片id")
	private String imageId = "";
	@ApiModelProperty("商品名称")
	private String title = "";
	@ApiModelProperty("售价")
	private int price = 0;
	@ApiModelProperty("产品封面")
	private String cover = "";
	@ApiModelProperty("描述简介")
	private String description = "";
	@ApiModelProperty("商品图文详情")
	private String content = "";
	@ApiModelProperty("创建时间")
	private Timestamp createTime = null;
	@ApiModelProperty("库存")
	private int stock = 0;
	@ApiModelProperty("商品颜色")
	private String productColor = "";
	@ApiModelProperty("商品尺码")
	private String productSize = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
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

}
