package com.dodopal.portal.business.bean;

import java.io.Serializable;
import java.util.Date;

public class MerDiscountBean implements Serializable {
    private static final long serialVersionUID = 4526822938155953990L;
    //表 id
    private String id;
    //商户号
    private String merCode;
    //子商户名（直营网点）
    private String merName;
    //折扣
    private String discount;
    //启用
    private String activate;
    //排序号
    private String sort;
    //创建时间
    private Date createDate; 
    //修改时间
    private Date updateDate;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getActivate() {
        return activate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    

}
