package com.yunqi.jhf.vo;

import java.sql.Timestamp;
import java.util.List;

import com.yunqi.jhf.dao.domain.TImage;

import io.swagger.annotations.ApiModelProperty;

public class ProductBean {
	
	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("产品分类名称")
    private String cateName;
	
	@ApiModelProperty("产品分类Id")
	private int cateId;
	
	@ApiModelProperty("品牌名称")
    private String brandName;
	
	@ApiModelProperty("品牌Id")
	private int brandId;
	
	@ApiModelProperty("商品素材图片id")
    private String imageId;
	
	@ApiModelProperty("商品素材图片")
	private List<TImage> imageList;
	
	@ApiModelProperty("商品名称")
    private String title;
	
	@ApiModelProperty("库存")
    private int stock;
	
	@ApiModelProperty("售价")
    private long price;
	
	@ApiModelProperty("产品封面")
    private String cover;
	
	@ApiModelProperty("描述简介")
    private String description;
	
	@ApiModelProperty("商品图文详情")
    private String content;
	
	@ApiModelProperty("商品颜色，用,分割，例如红色,紫色...")
    private String productColor;
	
	@ApiModelProperty("商品尺码，用,分割，例如大号,小号...")
    private String productSize;
	
	@ApiModelProperty("修改时间")
	private Timestamp updateTime;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	
	@ApiModelProperty("是否有颜色")
    private String hasColor;
	
	@ApiModelProperty("是否有尺寸")
    private String hasSize;
	
	@ApiModelProperty("是否商品 上架   Y上架   N下架")
	private String putaway;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public List<TImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<TImage> imageList) {
		this.imageList = imageList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getHasColor() {
		return hasColor;
	}

	public void setHasColor(String hasColor) {
		this.hasColor = hasColor;
	}

	public String getHasSize() {
		return hasSize;
	}

	public void setHasSize(String hasSize) {
		this.hasSize = hasSize;
	}

	public String getPutaway() {
		return putaway;
	}

	public void setPutaway(String putaway) {
		this.putaway = putaway;
	}

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", cateName=" + cateName + ", cateId=" + cateId + ", brandName=" + brandName
				+ ", brandId=" + brandId + ", imageId=" + imageId + ", imageList=" + imageList + ", title=" + title
				+ ", stock=" + stock + ", price=" + price + ", cover=" + cover + ", description=" + description
				+ ", content=" + content + ", productColor=" + productColor + ", productSize=" + productSize
				+ ", updateTime=" + updateTime + ", createTime=" + createTime + ", hasColor=" + hasColor + ", hasSize="
				+ hasSize + ", putaway=" + putaway + "]";
	}

}
