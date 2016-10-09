package com.dodopal.oss.delegate.bean;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.oss.delegate.util.CustomDateSerializer;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by dodopal on 2015/7/22.
 */
public class IcdcPrdBean extends BaseEntity {
    private String name;
    private String code;
    private String city;
    private String cityId;
    private String supplier;
    private String supplierCode;
    private Float rate;
    private String costPrice;
    private String valuePrice;
    private String saleUporDown;
    private String comments;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Override
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateDate() {

        return super.getCreateDate();
    }

    @Override
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getUpdateDate() {

        return super.getUpdateDate();
    }
}
