package com.dodopal.oss.delegate.bean;


/**
 * 一卡通充值产品导出项
 */
public class IcdcPrductInfoForExport {
    
    // 产品编号
    private String productCode;
    
    // 产品名称
    private String productName;
    
    // 通卡名称
    private String supplierName;
    
    // 城市名称
    private String cityName;
    
    // 费率
    private String rate;
    
    // 面价
    private String costPrice;
    
    // 成本价
    private String valuePrice;
    
    // 状态
    private String saleUporDown;
    
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getValuePrice() {
        return valuePrice;
    }

    public void setValuePrice(String valuePrice) {
        this.valuePrice = valuePrice;
    }

    public String getSaleUporDown() {
        return saleUporDown;
    }

    public void setSaleUporDown(String saleUporDown) {
        this.saleUporDown = saleUporDown;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
