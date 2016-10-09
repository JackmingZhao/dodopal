package com.dodopal.api.product.dto.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;
/*
 * 一卡通消费交易详细
 * @ author tao
 */

public class YktCardConsumTradeDetailQueryDTO extends QueryBase{

    private static final long serialVersionUID = 1L;
    private String merName;
    private String merManager;
    private String telphone;
    private String orderNo;
    private String startDate;
    private String endDate;
    private String proCode;
    private String cityName;
    private String orderNum;
    private String merCode;
    private String yktCode;
    //订单日期
    private String orderDate;
    
    //订单状态
    private String states;
    
    
    public String getStates() {
        return states;
    }
    public void setStates(String states) {
        this.states = states;
    }
    public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getYktCode() {
		return yktCode;
	}
	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}
	public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getMerManager() {
        return merManager;
    }
    public void setMerManager(String merManager) {
        this.merManager = merManager;
    }
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
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
    
    
}
