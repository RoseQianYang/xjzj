package com.yunqi.jhf.vo;

import java.util.Date;

/**
 * 
 * @author wangsong
 *
 */
public class VehicleRentBean {


	private int id; // 主键Id

	
	private String vehicleId;
	

	private String vehicleName; // 房车名称
	
	private int functionType; // 自行式1 拖挂式2 皮卡3

	private String license; // 驾照级别

	private String bedNum; // 床位

	private String imageId; // 房车素材图片id
	
	private String cover; // 房车图片
	
	private int rentPrice; // 房车租赁价格

	private String content; // 房车图文详情
	
	private String address; // 联系地址

	
	private String phone; // 联系电话

	
	private Date createTime; // 创建时间

	
	private Date updateTime; // 修改时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return "VehicleRentBean [id=" + id + ", vehicleId=" + vehicleId + ", vehicleName=" + vehicleName
				+ ", functionType=" + functionType + ", license=" + license + ", bedNum=" + bedNum + ", imageId="
				+ imageId + ", cover=" + cover + ", rentPrice=" + rentPrice + ", content=" + content + ", address="
				+ address + ", phone=" + phone + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
