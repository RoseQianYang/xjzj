package com.yunqi.jhf.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.yunqi.jhf.dao.domain.TImage;

import io.swagger.annotations.ApiModelProperty;

public class CampsiteBean {

	@ApiModelProperty("唯一标识")
    private int id ;
	
	@ApiModelProperty("营地名称")
    private String title ;
	
	@ApiModelProperty("营地分类（自由营地：1，合作营地：2）")
    private int categoryId ;
	
	@ApiModelProperty("营地基础功能，以逗号区分。")
    private String campsiteFunction ;
	
	@ApiModelProperty("营地地址")
    private String address ;
	
	@ApiModelProperty("地理位置纬度")
    private BigDecimal latitude ;
	
	@ApiModelProperty("地址位置经度")
    private BigDecimal longitude ;
	
	@ApiModelProperty("营地封面")
    private String cover ;
	
	@ApiModelProperty("营地图片id")
    private String imageId ;
	
	@ApiModelProperty("营地图片集合")
	private List<TImage> images;
	
	@ApiModelProperty("营地联系电话")
    private String phone ;
	
	@ApiModelProperty("是否有效  Y有效  N无效")
    private String isDelete ;
	
	@ApiModelProperty("营地图文详情")
    private String content ;
	
	@ApiModelProperty("营地简介")
    private String introduction ;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime ;
	
	@ApiModelProperty("修改时间")
	private Timestamp updateTime ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCampsiteFunction() {
		return campsiteFunction;
	}

	public void setCampsiteFunction(String campsiteFunction) {
		this.campsiteFunction = campsiteFunction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public List<TImage> getImages() {
		return images;
	}

	public void setImages(List<TImage> images) {
		this.images = images;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	@Override
	public String toString() {
		return "CampsiteBean [id=" + id + ", title=" + title + ", categoryId=" + categoryId + ", campsiteFunction="
				+ campsiteFunction + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", cover=" + cover + ", imageId=" + imageId + ", images=" + images + ", phone=" + phone
				+ ", isDelete=" + isDelete + ", content=" + content + ", introduction=" + introduction + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	
}
