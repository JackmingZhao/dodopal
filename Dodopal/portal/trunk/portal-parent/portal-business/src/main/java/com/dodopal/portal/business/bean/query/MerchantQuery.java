package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月29日 下午6:10:23
 */
public class MerchantQuery extends QueryBase{
    private static final long serialVersionUID = -9062341993115843502L;
    /*商户编码*/
    private String merCode;
    /*商户名称*/
    private String merName;
    /*上级商户类型*/
    private String merType;
    /*审核状态*/
    private String merState;
    /*启用标示*/
    private String activate;
    
    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getMerState() {
        return merState;
    }

    public void setMerState(String merState) {
        this.merState = merState;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
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

}
