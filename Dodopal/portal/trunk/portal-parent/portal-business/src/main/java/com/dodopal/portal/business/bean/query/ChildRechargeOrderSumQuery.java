package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

public class ChildRechargeOrderSumQuery extends QueryBase {

    private static final long serialVersionUID = 7602349323700447582L;
    //商户编号
    private String merCode;
    //pos号
    private String posCode;
    //子商户名称
    private String merName;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //城市
    private String cityName;

    //充值订单状态
    private String proOrderState;
    
    
    
    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
