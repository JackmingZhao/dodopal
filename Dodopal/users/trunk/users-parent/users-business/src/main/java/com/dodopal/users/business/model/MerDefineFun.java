package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：
 * @author lifeng
 */

public class MerDefineFun extends BaseEntity {
    private static final long serialVersionUID = -2367841255237147661L;
    /*商户编码*/
    private String merCode;
    /*商户功能编码*/
    private String merFunCode;
    /*功能名称*/
    private String merFunName;
    /*启用标志*/
    private String activate;
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getMerFunCode() {
        return merFunCode;
    }
    public void setMerFunCode(String merFunCode) {
        this.merFunCode = merFunCode;
    }
    public String getMerFunName() {
        return merFunName;
    }
    public void setMerFunName(String merFunName) {
        this.merFunName = merFunName;
    }
    public String getActivate() {
        return activate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }
    
}
