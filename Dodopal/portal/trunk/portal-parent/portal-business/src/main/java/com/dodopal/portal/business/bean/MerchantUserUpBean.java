package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class MerchantUserUpBean extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 7026604905335195587L;
    //商户编码
    private String merCode;
    //商户用户登录密码
    private String merUserPWD;
    //商户用户更新密码
    private String merUserUpPWD;
    // 商户MD5签名秘钥
    private String merMD5PayPwd;
    //商户MD5验签秘钥
    private String merMD5BackPayPWD;
    //更改商户MD5签名秘钥
    private String upmerMD5PayPwd;
    //更改商户MD5验签秘钥
    private String upmerMD5BackPayPWD;
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getMerUserPWD() {
        return merUserPWD;
    }
    public void setMerUserPWD(String merUserPWD) {
        this.merUserPWD = merUserPWD;
    }
    public String getMerUserUpPWD() {
        return merUserUpPWD;
    }
    public void setMerUserUpPWD(String merUserUpPWD) {
        this.merUserUpPWD = merUserUpPWD;
    }
    public String getMerMD5PayPwd() {
        return merMD5PayPwd;
    }
    public void setMerMD5PayPwd(String merMD5PayPwd) {
        this.merMD5PayPwd = merMD5PayPwd;
    }
    public String getMerMD5BackPayPWD() {
        return merMD5BackPayPWD;
    }
    public void setMerMD5BackPayPWD(String merMD5BackPayPWD) {
        this.merMD5BackPayPWD = merMD5BackPayPWD;
    }
    public String getUpmerMD5PayPwd() {
        return upmerMD5PayPwd;
    }
    public void setUpmerMD5PayPwd(String upmerMD5PayPwd) {
        this.upmerMD5PayPwd = upmerMD5PayPwd;
    }
    public String getUpmerMD5BackPayPWD() {
        return upmerMD5BackPayPWD;
    }
    public void setUpmerMD5BackPayPWD(String upmerMD5BackPayPWD) {
        this.upmerMD5BackPayPWD = upmerMD5BackPayPWD;
    }

}
