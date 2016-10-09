package com.dodopal.product.business.model;


public class PrdProductYktInfoForIcdcRecharge {

    /*产品编号*/
    private String proCode;

    /*产品名称*/
    private String proName;

    /*面价*/
    private long proPrice;

    /*状态0：上架、1：下架*/
    private String proStatus;

    /*业务城市code*/
    private String cityCode;

    /*业务城市名称*/
    private String cityName;

    /*一卡通编号*/
    private String yktCode;

    // 客户实付金额
    private Long customerPayAmt;   
    
    // 客户利润
    private Long customerGain;    

    public Long getCustomerPayAmt() {
        return customerPayAmt;
    }

    public void setCustomerPayAmt(Long customerPayAmt) {
        this.customerPayAmt = customerPayAmt;
    }

    public Long getCustomerGain() {
        return customerGain;
    }

    public void setCustomerGain(Long customerGain) {
        this.customerGain = customerGain;
    }
    
    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getProPrice() {
        return proPrice;
    }

    public void setProPrice(long proPrice) {
        this.proPrice = proPrice;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
