package com.dodopal.oss.business.bean;


/**
 *  通卡公司基础信息导出excel项
 * @author 
 *
 */
public class ProductYktInfoForExport {

    // 通卡公司编号
    private String yktCode;

    // 通卡公司名称
    private String yktName;

    // 所在省份
    private String provinceName;
    
    // 所在城市
    private String cityName;

    // 详细地址
    private String yktAddress;
    
    // 业务城市
    private String businessCityName;
    
    // 付款方式
    private String yktPayFlag;

    // 启用标识
    private String activate;

    // 充值业务标识
    private String yktIsRecharge;

    // 充值业务费率
    private String yktRechargeRate;

    // 充值业务结算类型
    private String yktRechargeSetType;

    // 充值业务结算类型值
    private String yktRechargeSetPara;

    // 充值业务限制开始时间
    private String yktRechargeLimitStartTime;
    
    // 充值业务限制结束时间
    private String yktRechargeLimitEndTime;
    
    // 支付业务标识
    private String yktIsPay;

    // 支付业务费率
    private String yktPayRate;

    // 支付业务结算类型
    private String yktPaysetType;

    // 支付业务结算类型值
    private String yktPaySetPara;

    // 消费业务限制开始时间
    private String yktConsumeLimitStartTime;
    
    // 消费业务限制结束时间
    private String yktConsumeLimitEndTime;
    
    // 卡内最大限额
    private String yktCardMaxLimit;

    // 都都宝接入通卡公司的IP地址
    private String yktIpAddress;

    // 都都宝接入通卡公司的端口号
    private String yktPort;

    // 通卡公司的对应联系人
    private String yktLinkUser;

    // 通卡公司的对应联系人的手机号码
    private String yktMobile;

    // 通卡公司的固定电话
    private String yktTel;

	// 备注
    private String remark;

    private String createDate;

    private String updateDate;

    private String createUser;

    private String updateUser;    
    
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public String getYktAddress() {
        return yktAddress;
    }

    public void setYktAddress(String yktAddress) {
        this.yktAddress = yktAddress;
    }

    public String getBusinessCityName() {
        return businessCityName;
    }

    public void setBusinessCityName(String businessCityName) {
        this.businessCityName = businessCityName;
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

    public String getYktRechargeRate() {
        return yktRechargeRate;
    }

    public void setYktRechargeRate(String yktRechargeRate) {
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

    public String getYktIsPay() {
        return yktIsPay;
    }

    public void setYktIsPay(String yktIsPay) {
        this.yktIsPay = yktIsPay;
    }

    public String getYktPayRate() {
        return yktPayRate;
    }

    public void setYktPayRate(String yktPayRate) {
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

    public String getYktCardMaxLimit() {
        return yktCardMaxLimit;
    }

    public void setYktCardMaxLimit(String yktCardMaxLimit) {
        this.yktCardMaxLimit = yktCardMaxLimit;
    }

    public String getYktIpAddress() {
        return yktIpAddress;
    }

    public void setYktIpAddress(String yktIpAddress) {
        this.yktIpAddress = yktIpAddress;
    }

    public String getYktPort() {
        return yktPort;
    }

    public void setYktPort(String yktPort) {
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
   
   
    
}
