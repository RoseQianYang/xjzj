package com.yunqi.jhf.vo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

public class ImageInfoBean {
	
	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("分类名称")
    private String title;
	
	@ApiModelProperty("图片路径")
    private String imgSrc;
	
	@ApiModelProperty("图片分类id")
    private int imgCateId;
	
	@ApiModelProperty("图片分类名称")
	private String imgCateName;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	
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

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public int getImgCateId() {
		return imgCateId;
	}

	public void setImgCateId(int imgCateId) {
		this.imgCateId = imgCateId;
	}

	public String getImgCateName() {
		return imgCateName;
	}

	public void setImgCateName(String imgCateName) {
		this.imgCateName = imgCateName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ImageInfoBean [id=" + id + ", title=" + title + ", imgSrc=" + imgSrc + ", imgCateId=" + imgCateId
				+ ", imgCateName=" + imgCateName + ", createTime=" + createTime + "]";
	}
}
