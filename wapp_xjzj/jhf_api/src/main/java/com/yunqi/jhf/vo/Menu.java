package com.yunqi.jhf.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 菜单类
 */

@ApiModel(description = "菜单类")
public class Menu {
	@ApiModelProperty(value = "菜单码 如：report")
	private String code = null;
	@ApiModelProperty(value = "菜单名 如：报表统计")
	private String name = null;
	@ApiModelProperty(value = "子菜单")
	private List<Menu> submenu = null;

	public Menu(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public Menu() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<Menu> submenu) {
		this.submenu = submenu;
	}

}
