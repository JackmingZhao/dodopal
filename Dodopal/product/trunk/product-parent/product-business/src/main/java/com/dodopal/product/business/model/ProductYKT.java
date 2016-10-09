package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;

public class ProductYKT extends BaseEntity {

	private static final long serialVersionUID = 8299737687863282472L;

	private String yktCode;

	private String yktName;

	private String yktAddress;

	private String yktPayFlag;

	private String activate;

	private String yktIsRecharge;

	private Double yktRechargeRate;

	private String yktRechargeSetType;

	private String yktRechargeSetPara;

	private String yktIsPay;

	private Double yktPayRate;

	private String yktPaysetType;

	private String yktPaySetPara;

	private Long yktCardMaxLimit;

	private String yktIpAddress;

	private Integer yktPort;

	private String yktLinkUser;

	private String yktMobile;

	private String yktTel;

	private String remark;

	private String provinceId;
	private String cityId;
	private String provinceName;
	private String cityName;

	private String businessCityId;
	private String businessCityName;

	/******************** 2015年12月1日 13:49:21 维护通卡公司在某个时间区间不能交易 begin*********/
	//充值限制开始时间（格式例如：19:23:12）
	private String yktRechargeLimitStartTime;
	//充值限制结束时间（格式例如：19:23:12）
	private String yktRechargeLimitEndTime;
	//消费限制开始时间（格式例如：19:23:12）
	private String yktConsumeLimitStartTime;
	//消费限制结束时间（格式例如：19:23:12）
	private String yktConsumeLimitEndTime;
	/**************************** end ******************************/
	private String merCode; //供应商商户号  Time: 2016-01-12 Name:Joe
	
	
	public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getYktName() {
		return yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

	public String getYktAddress() {
		return yktAddress;
	}

	public void setYktAddress(String yktAddress) {
		this.yktAddress = yktAddress;
	}

	public String getYktPayFlag() {
		return yktPayFlag;
	}

	public void setYktPayFlag(String yktPayFlag) {
		this.yktPayFlag = yktPayFlag;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public String getYktIsRecharge() {
		return yktIsRecharge;
	}

	public void setYktIsRecharge(String yktIsRecharge) {
		this.yktIsRecharge = yktIsRecharge;
	}

	public Double getYktRechargeRate() {
		return yktRechargeRate;
	}

	public void setYktRechargeRate(Double yktRechargeRate) {
		this.yktRechargeRate = yktRechargeRate;
	}

	public String getYktRechargeSetType() {
		return yktRechargeSetType;
	}

	public void setYktRechargeSetType(String yktRechargeSetType) {
		this.yktRechargeSetType = yktRechargeSetType;
	}

	public String getYktRechargeSetPara() {
		return yktRechargeSetPara;
	}

	public void setYktRechargeSetPara(String yktRechargeSetPara) {
		this.yktRechargeSetPara = yktRechargeSetPara;
	}

	public String getYktIsPay() {
		return yktIsPay;
	}

	public void setYktIsPay(String yktIsPay) {
		this.yktIsPay = yktIsPay;
	}

	public Double getYktPayRate() {
		return yktPayRate;
	}

	public void setYktPayRate(Double yktPayRate) {
		this.yktPayRate = yktPayRate;
	}

	public String getYktPaysetType() {
		return yktPaysetType;
	}

	public void setYktPaysetType(String yktPaysetType) {
		this.yktPaysetType = yktPaysetType;
	}

	public String getYktPaySetPara() {
		return yktPaySetPara;
	}

	public void setYktPaySetPara(String yktPaySetPara) {
		this.yktPaySetPara = yktPaySetPara;
	}

	public String getYktIpAddress() {
		return yktIpAddress;
	}

	public void setYktIpAddress(String yktIpAddress) {
		this.yktIpAddress = yktIpAddress;
	}

	public Integer getYktPort() {
		return yktPort;
	}

	public void setYktPort(Integer yktPort) {
		this.yktPort = yktPort;
	}

	public String getYktLinkUser() {
		return yktLinkUser;
	}

	public void setYktLinkUser(String yktLinkUser) {
		this.yktLinkUser = yktLinkUser;
	}

	public String getYktMobile() {
		return yktMobile;
	}

	public void setYktMobile(String yktMobile) {
		this.yktMobile = yktMobile;
	}

	public String getYktTel() {
		return yktTel;
	}

	public void setYktTel(String yktTel) {
		this.yktTel = yktTel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBusinessCityId() {
		return businessCityId;
	}

	public void setBusinessCityId(String businessCityId) {
		this.businessCityId = businessCityId;
	}

	public String getBusinessCityName() {
		return businessCityName;
	}

	public void setBusinessCityName(String businessCityName) {
		this.businessCityName = businessCityName;
	}

	public Long getYktCardMaxLimit() {
		return yktCardMaxLimit;
	}

	public void setYktCardMaxLimit(Long yktCardMaxLimit) {
		this.yktCardMaxLimit = yktCardMaxLimit;
	}

	public String getYktRechargeLimitStartTime() {
		return yktRechargeLimitStartTime;
	}

	public void setYktRechargeLimitStartTime(String yktRechargeLimitStartTime) {
		this.yktRechargeLimitStartTime = yktRechargeLimitStartTime;
	}

	public String getYktRechargeLimitEndTime() {
		return yktRechargeLimitEndTime;
	}

	public void setYktRechargeLimitEndTime(String yktRechargeLimitEndTime) {
		this.yktRechargeLimitEndTime = yktRechargeLimitEndTime;
	}

	public String getYktConsumeLimitStartTime() {
		return yktConsumeLimitStartTime;
	}

	public void setYktConsumeLimitStartTime(String yktConsumeLimitStartTime) {
		this.yktConsumeLimitStartTime = yktConsumeLimitStartTime;
	}

	public String getYktConsumeLimitEndTime() {
		return yktConsumeLimitEndTime;
	}

	public void setYktConsumeLimitEndTime(String yktConsumeLimitEndTime) {
		this.yktConsumeLimitEndTime = yktConsumeLimitEndTime;
	}



}
