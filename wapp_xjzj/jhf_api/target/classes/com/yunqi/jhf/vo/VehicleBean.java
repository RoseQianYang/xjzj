package com.yunqi.jhf.vo;

import java.util.Date;

/**
 * 
 * @author wangsong
 *
 */
public class VehicleBean {

	private int id; // 主键Id

	private int vehicleBrandId; // 外键 房车品牌id

	private String brandName; // 房车品牌名称

	private String vehicleName; // 房车名称

	private String cover; // 房车图片

	private String imageId; // 房车素材图片id

	private String introduction; // 房车简介

	private String content; // 房车图文详情

	private Long price; // 预定价格

	private String spec; // 房车规格型号

	private String gears; // 档位

	private String isSencond; // 是否是二手，Y N

	private String isSales; // 是否是促销, Y N

	private int functionType; // 自行式1 拖挂式2 皮卡3

	private String license; // 驾照级别

	private String bedNum; // 床位

	private Date createTime; // 创建时间

	private Date updateTime; // 修改时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVehicleBrandId() {
		return vehicleBrandId;
	}

	public void setVehicleBrandId(int vehicleBrandId) {
		this.vehicleBrandId = vehicleBrandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getGears() {
		return gears;
	}

	public void setGears(String gears) {
		this.gears = gears;
	}

	public String getIsSencond() {
		return isSencond;
	}

	public void setIsSencond(String isSencond) {
		this.isSencond = isSencond;
	}

	public String getIsSales() {
		return isSales;
	}

	public void setIsSales(String isSales) {
		this.isSales = isSales;
	}

	public int getFunctionType() {
		return functionType;
	}

	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
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
		return "VehicleBean [id=" + id + ", vehicleBrandId=" + vehicleBrandId + ", brandName=" + brandName
				+ ", vehicleName=" + vehicleName + ", cover=" + cover + ", imageId=" + imageId + ", introduction="
				+ introduction + ", content=" + content + ", price=" + price + ", spec=" + spec + ", gears=" + gears
				+ ", isSencond=" + isSencond + ", isSales=" + isSales + ", functionType=" + functionType + ", license="
				+ license + ", bedNum=" + bedNum + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
