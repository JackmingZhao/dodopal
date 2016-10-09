package com.dodopal.product.business.model;

import java.io.Serializable;

public class LoadOrderPrdInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8255097829822644979L;

    private long price;
    private String cityCode;
    private String cityName;
    private String productCode;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
