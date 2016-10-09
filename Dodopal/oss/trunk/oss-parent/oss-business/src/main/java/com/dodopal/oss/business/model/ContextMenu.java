package com.dodopal.oss.business.model;


public class ContextMenu {
	
	public String menuId;
	
	public String menuCode;
	
	public String menuName;
	
	public String icon;
	
	public String url;
	
	public ContextMenu[] menus;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ContextMenu[] getMenus() {
		return menus;
	}

	public void setMenus(ContextMenu[] menus) {
		this.menus = menus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
}
