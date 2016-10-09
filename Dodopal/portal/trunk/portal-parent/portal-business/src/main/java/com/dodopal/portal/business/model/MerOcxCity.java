package com.dodopal.portal.business.model;

import java.util.List;

import com.dodopal.common.model.Area;
import com.dodopal.common.model.BaseEntity;

public class MerOcxCity extends BaseEntity {
	private static final long serialVersionUID = -4309310829715283365L;
	private List<Area> areaList;
	private String cityName;
	private String cityCode;
	
	private String ocxName;
	
	private String ocxVersionId;
	
	private String ocxCLSID;
	
	private String userId;
	
	private String merCode;
	
	private String userCode;

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOcxName() {
		return ocxName;
	}

	public void setOcxName(String ocxName) {
		this.ocxName = ocxName;
	}

	public String getOcxVersionId() {
		return ocxVersionId;
	}

	public void setOcxVersionId(String ocxVersionId) {
		this.ocxVersionId = ocxVersionId;
	}

	public String getOcxCLSID() {
		return ocxCLSID;
	}

	public void setOcxCLSID(String ocxCLSID) {
		this.ocxCLSID = ocxCLSID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}
