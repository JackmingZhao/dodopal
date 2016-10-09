package com.dodopal.product.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 通卡系统：账户消费/积分消费用model
 * 
 * @author dodopal
 *
 */
public class YktConsumeRecord extends BaseEntity {
    
    private static final long serialVersionUID = 7835152044218233014L;

    // 订单编号:(账户消费：Z ;积分消费：J)+ 20150428222211 +五位数据库cycle sequence（循环使用）
    private String orderNum;

    // 通卡编码
    private String yktCode;
    
    // 通卡名称
    private String yktName;
    
    // 城市编码
    private String cityCode;
    
    // 城市名称
    private String cityName;

    // DDP商户号
    private String merCode;
    
    // DDP商户名称
    private String merName;
    
    // DDP商户类型
    private String merType;
    
    // 业务类型(账户消费；积分消费)
    private String businessType;
    
    // 卡号
    private String cardNum;
    
    // pos号
    private String posCode;
    
    // pos类型（0：NFC手机;1：都都宝家用机;2：都都宝商用机）
    private String posType;
    
    // 通卡账户消费金额(单位：分)
    private Long yktAcountAmt;
    
    // 通卡积分消费额度
    private Long yktPointAmt;
    
    // 状态
    private String states;
    
    // 前状态
    private String beforeStates;
    
    // 订单时间(yyyy/MM/dd HH:MM:SS)
    private Date orderDate;

    // 订单日期(yyyyMMdd)
    private String orderDay;

    // 操作员ID
    private String operId;
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public Long getYktAcountAmt() {
        return yktAcountAmt;
    }

    public void setYktAcountAmt(Long yktAcountAmt) {
        this.yktAcountAmt = yktAcountAmt;
    }

    public Long getYktPointAmt() {
        return yktPointAmt;
    }

    public void setYktPointAmt(Long yktPointAmt) {
        this.yktPointAmt = yktPointAmt;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getBeforeStates() {
        return beforeStates;
    }

    public void setBeforeStates(String beforeStates) {
        this.beforeStates = beforeStates;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }
    
}
