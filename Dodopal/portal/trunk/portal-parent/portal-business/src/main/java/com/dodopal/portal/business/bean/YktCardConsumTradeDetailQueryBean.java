package com.dodopal.portal.business.bean;


import com.dodopal.common.model.QueryBase;

public class YktCardConsumTradeDetailQueryBean extends QueryBase{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String merName;
    private String merManager;
    private String merCode;
    private String orderNo;
    private String startDate;
    private String endDate;
    private String proCode;
    private String cityname;
    

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
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
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
    public String getCityname() {
        return cityname;
    }
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

}
