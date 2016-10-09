package com.dodopal.users.business.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 商户折扣
 * @author xiongzhijing@dodopal.com
 *
 */
public class MerDiscount implements Serializable {
    private static final long serialVersionUID = -4981979179103224625L;
    //表 id
    private String id;
    //商户号
    private String merCode;
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
