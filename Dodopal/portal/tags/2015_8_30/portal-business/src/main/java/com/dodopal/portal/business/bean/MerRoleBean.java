package com.dodopal.portal.business.bean;

import java.util.List;

import com.dodopal.common.model.BaseEntity;

public class MerRoleBean extends BaseEntity {

	private static final long serialVersionUID = 1994125629997475191L;

	private String merCode;
	private String merRoleCode;
	private String merRoleName;
	private String description;
	private String activate;
	private List<MerFunctionInfoBean> merRoleFunDTOList;
	private List<TreeNode> merRoleFunTreeList;

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerRoleCode() {
		return merRoleCode;
	}

	public void setMerRoleCode(String merRoleCode) {
		this.merRoleCode = merRoleCode;
	}

	public String getMerRoleName() {
		return merRoleName;
	}

	public void setMerRoleName(String merRoleName) {
		this.merRoleName = merRoleName;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public List<MerFunctionInfoBean> getMerRoleFunDTOList() {
		return merRoleFunDTOList;
	}

	public void setMerRoleFunDTOList(List<MerFunctionInfoBean> merRoleFunDTOList) {
		this.merRoleFunDTOList = merRoleFunDTOList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TreeNode> getMerRoleFunTreeList() {
		return merRoleFunTreeList;
	}

	public void setMerRoleFunTreeList(List<TreeNode> merRoleFunTreeList) {
		this.merRoleFunTreeList = merRoleFunTreeList;
	}

}
