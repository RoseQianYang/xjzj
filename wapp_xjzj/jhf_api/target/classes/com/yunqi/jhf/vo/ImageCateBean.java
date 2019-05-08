package com.yunqi.jhf.vo;

import java.sql.Timestamp;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ImageCateBean {

	@ApiModelProperty("唯一标识")
    private int id;
	
	@ApiModelProperty("分类名称")
    private String title;
	
	@ApiModelProperty("子级集合")
    private List<ImageCateBean> childCate;
	
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	
	@ApiModelProperty("修改时间")
	private Timestamp updateTime;
	
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
	
	public List<ImageCateBean> getChildCate() {
		return childCate;
	}
	
	public void setChildCate(List<ImageCateBean> childCate) {
		this.childCate = childCate;
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
	
}
