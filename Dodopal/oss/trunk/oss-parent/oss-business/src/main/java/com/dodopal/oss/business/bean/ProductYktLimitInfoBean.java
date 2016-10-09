package com.dodopal.oss.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 *  额度信息
 * @author 
 *
 */
public class ProductYktLimitInfoBean extends BaseEntity{

    private static final long serialVersionUID = 3669852915446368405L;

    private String yktCode;
    
    private String yktName;

    private Long yktWarnLimit;

    private Long yktStopLimit;

    private Date yktExpireDate;

    private Long totalAmtLimit;

    private Long surPlusLimit;

    private Long usedLimit;

    private String remark;

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Long getYktWarnLimit() {
        return yktWarnLimit;
    }

    public void setYktWarnLimit(Long yktWarnLimit) {
        this.yktWarnLimit = yktWarnLimit;
    }

    public Long getYktStopLimit() {
        return yktStopLimit;
    }

    public void setYktStopLimit(Long yktStopLimit) {
        this.yktStopLimit = yktStopLimit;
    }

    public Long getTotalAmtLimit() {
        return totalAmtLimit;
    }

    public void setTotalAmtLimit(Long totalAmtLimit) {
        this.totalAmtLimit = totalAmtLimit;
    }

    public Long getSurPlusLimit() {
        return surPlusLimit;
    }

    public void setSurPlusLimit(Long surPlusLimit) {
        this.surPlusLimit = surPlusLimit;
    }

    public Long getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(Long usedLimit) {
        this.usedLimit = usedLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getYktExpireDate() {
        return yktExpireDate;
    }

    public void setYktExpireDate(Date yktExpireDate) {
        this.yktExpireDate = yktExpireDate;
    }

}
