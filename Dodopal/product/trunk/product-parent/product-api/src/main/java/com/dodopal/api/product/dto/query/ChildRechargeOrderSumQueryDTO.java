package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class ChildRechargeOrderSumQueryDTO extends QueryBase {

    private static final long serialVersionUID = -1615919829923480003L;
    //商户编号
    private String merCode;
    //pos号
    private String posCode;
    //子商户名称
    private String merName;

    //开始日期
    private String stratDate;
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

    public String getStratDate() {
        return stratDate;
    }

    public void setStratDate(String stratDate) {
        this.stratDate = stratDate;
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
