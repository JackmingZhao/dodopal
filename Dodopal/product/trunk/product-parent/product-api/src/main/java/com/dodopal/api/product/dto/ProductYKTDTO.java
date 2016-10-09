package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class ProductYKTDTO extends BaseEntity {

	private static final long serialVersionUID = 8299737687863282472L;

	/**
	 * 通卡公司编号
	 */
	private String yktCode;

	/**
	 * 通卡公司名称
	 */
	private String yktName;

	/**
	 * 通卡公司所在的省份ID
	 */
	private String provinceId;

	/**
	 * 通卡公司所在的省份名称
	 */
	private String provinceName;

	/**
	 * 通卡公司所在城市ID
	 */
	private String cityId;

	/**
	 * 通卡公司所在城市名称
	 */
	private String cityName;

	/**
	 * 详细地址
	 */
	private String yktAddress;

	/**
	 * 付款方式:0:预购额度、1：后结算
	 */
	private String yktPayFlag;

	/**
	 * 启用标志:0：启用、1：禁用
	 */
	private String activate;

	/**
	 * 是否开通一卡通充值：0：开通、1：未开通
	 */
	private String yktIsRecharge;

	/**
	 * 一卡通充值费率
	 */
	private Double yktRechargeRate;

	/**
	 * 一卡通充值费率类型（0:天数（表示每隔多少天结算一次。）、1：笔数（表示距离上一次结算点，充值业务达到多少笔之后开始新一次结算。）、2：金额）
	 */
	private String yktRechargeSetType;

	/**
	 * 一卡通充值费率类型值（在指定了充值业务结算类型之后，需要指定具体的数值。）
	 */
	private String yktRechargeSetPara;

	/**
	 * 是否开通一卡通支付:0：开通、1：未开通
	 */
	private String yktIsPay;

	/**
	 * 一卡通支付费率
	 */
	private Double yktPayRate;

	/**
	 * 一卡通支付费率类型
	 * 0:天数（表示每隔多少天结算一次。）、1：笔数（表示距离上一次结算点，支付业务达到多少笔之后开始新一次结算。）、2：金额（单位：元，
	 * 表示距离上一次结算点，支付金额到达多少之后开始新一次结算。）
	 */
	private String yktPaysetType;

	/**
	 * 一卡通支付费率类型值
	 */
	private String yktPaySetPara;

	/**
	 * 卡内允许最大金额
	 */
	private Long yktCardMaxLimit;

	/**
	 * 都都宝接入通卡公司的IP地址
	 */
	private String yktIpAddress;

	/**
	 * 都都宝接入通卡公司的端口号
	 */
	private Integer yktPort;

	/**
	 * 通卡公司的对应联系人
	 */
	private String yktLinkUser;

	/**
	 * 通卡公司的对应联系人的手机号码
	 */
	private String yktMobile;

	/**
	 * 通卡公司的固定电话
	 */
	private String yktTel;

	/**
	 * 与通卡公司有关的补充说明信息，用户根据实际情况进行输入
	 */
	private String remark;

	/**
	 * 通卡公司业务城市ID（需要关联查询统考公司业务城市信息表）
	 */
	private String businessCityId;

	/**
	 * 通卡公司业务城市名称（需要关联查询统考公司业务城市信息表）
	 */
	private String businessCityName;

	// 充值限制开始时间（格式例如：19:23:12）
	private String yktRechargeLimitStartTime;
	// 充值限制结束时间（格式例如：19:23:12）
	private String yktRechargeLimitEndTime;
	// 消费限制开始时间（格式例如：19:23:12）
	private String yktConsumeLimitStartTime;
	// 消费限制结束时间（格式例如：19:23:12）
	private String yktConsumeLimitEndTime;
	
	
	private String merCode;//商户号 Time:2016-01-18 Name: Joe
	

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
