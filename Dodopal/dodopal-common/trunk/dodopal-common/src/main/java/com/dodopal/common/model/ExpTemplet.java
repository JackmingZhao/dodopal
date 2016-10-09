package com.dodopal.common.model;

/**
 * @author Mikaelyan
 * 让用户选择的 要导出的Excel 的列
 */
public class ExpTemplet {
	
	// 自增行
	private String id;
	
	// 列表code
	private String templetCode;
	
	// 列表名称
	private String templetName;
	
	// 属性
	private String propertyCode;
	
	// 属性名称
	private String propertyName;
	
	// 选择状态，0:选中；1：未选择
	private String isChecked;
	
	// 排序号
	private int sortList;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTempletCode() {
		return templetCode;
	}
	public void setTempletCode(String templetCode) {
		this.templetCode = templetCode;
	}
	public String getTempletName() {
		return templetName;
	}
	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public int getSortList() {
		return sortList;
	}
	public void setSortList(int sortList) {
		this.sortList = sortList;
	}

}
