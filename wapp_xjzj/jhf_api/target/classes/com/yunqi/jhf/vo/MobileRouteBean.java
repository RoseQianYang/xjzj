package com.yunqi.jhf.vo;

import java.sql.Timestamp;
import java.util.List;

import com.yunqi.jhf.dao.domain.TImage;

import io.swagger.annotations.ApiModelProperty;

public class MobileRouteBean {


	@ApiModelProperty("唯一标识")
    private int id ;
	
	@ApiModelProperty("路线名称")
    private String title ;
	
	@ApiModelProperty("路线地址")
    private String address ;
	
	@ApiModelProperty("路线封面")
    private String cover ;
	
	@ApiModelProperty("路线图片id")
    private String imageId ;
	
	@ApiModelProperty("路线图片集合")
	private List<TImage> images;
	
	@ApiModelProperty("路线联系电话")
    private String phone ;
	
	@ApiModelProperty("是否有效  Y有效  N无效")
    private String isDelete ;
	
	@ApiModelProperty("路线图文详情")
    private String content ;
	
	@ApiModelProperty("路线简介")
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "MobileRouteBean [id=" + id + ", title=" + title + ", address=" + address + ", cover=" + cover
				+ ", imageId=" + imageId + ", images=" + images + ", phone=" + phone + ", isDelete=" + isDelete
				+ ", content=" + content + ", introduction=" + introduction + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
