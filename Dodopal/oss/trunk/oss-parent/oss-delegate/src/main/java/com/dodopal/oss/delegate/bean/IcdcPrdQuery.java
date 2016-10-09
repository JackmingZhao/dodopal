package com.dodopal.oss.delegate.bean;

import com.dodopal.common.model.QueryBase;

/**
 * Created by dodopal on 2015/7/22.
 */
public class IcdcPrdQuery extends QueryBase {
    private String nameQuery;
    private String cityQuery;
    private String supplierQuery;
    private String valueQuery;
    private String saleUporDownQuery;

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getCityQuery() {
        return cityQuery;
    }

    public void setCityQuery(String cityQuery) {
        this.cityQuery = cityQuery;
    }

    public String getSupplierQuery() {
        return supplierQuery;
    }

    public void setSupplierQuery(String supplierQuery) {
        this.supplierQuery = supplierQuery;
    }

    public String getValueQuery() {
        return valueQuery;
    }

    public void setValueQuery(String valueQuery) {
        this.valueQuery = valueQuery;
    }
    public String getSaleUporDownQuery() {
        return saleUporDownQuery;
    }

    public void setSaleUporDownQuery(String saleUporDownQuery) {
        this.saleUporDownQuery = saleUporDownQuery;
    }
}