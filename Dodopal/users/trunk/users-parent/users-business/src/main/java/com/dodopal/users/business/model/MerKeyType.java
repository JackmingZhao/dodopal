package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

public class MerKeyType extends BaseEntity{
    private static final long serialVersionUID = -6347069973395109050L;

    /**
     * 商户编码
     */
    private String merCode;
    
    /**
     * 商户密钥类型
     */
    private String merKeyType;
    
    /**
     * 商户MD5签名秘钥
     */
    private String merMD5PayPwd;
    
    /**
     * 商户MD5验签秘钥
     */
    private String merMD5BackPayPWD;
    
    /**
     * 商户RSA私钥
     */
    private String merRSAPri;
    
    /**
     * 商户RSA公钥
     */
    private String merRSAPub;
    
    /**
     * 商户DSA私钥
     */
    private String merDSAPri;
    
    /**
     * 商户DSA公钥
     */
    private String merDSAPub;
    
    /**
     * 内部签名秘钥
     */
    private String innerPayPWD;
    
    /**
     * 内部验签秘钥
     */
    private String innerBackPayPWD;
    
    /**
     * 启用标志
     */
    private String activate;

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerKeyType() {
        return merKeyType;
    }

    public void setMerKeyType(String merKeyType) {
        this.merKeyType = merKeyType;
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

    public String getMerRSAPri() {
        return merRSAPri;
    }

    public void setMerRSAPri(String merRSAPri) {
        this.merRSAPri = merRSAPri;
    }

    public String getMerRSAPub() {
        return merRSAPub;
    }

    public void setMerRSAPub(String merRSAPub) {
        this.merRSAPub = merRSAPub;
    }

    public String getMerDSAPri() {
        return merDSAPri;
    }

    public void setMerDSAPri(String merDSAPri) {
        this.merDSAPri = merDSAPri;
    }

    public String getMerDSAPub() {
        return merDSAPub;
    }

    public void setMerDSAPub(String merDSAPub) {
        this.merDSAPub = merDSAPub;
    }

    public String getInnerPayPWD() {
        return innerPayPWD;
    }

    public void setInnerPayPWD(String innerPayPWD) {
        this.innerPayPWD = innerPayPWD;
    }

    public String getInnerBackPayPWD() {
        return innerBackPayPWD;
    }

    public void setInnerBackPayPWD(String innerBackPayPWD) {
        this.innerBackPayPWD = innerBackPayPWD;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public MerKeyType(String merCode, String merKeyType, String merMD5PayPwd, String merMD5BackPayPWD, String merRSAPri, String merRSAPub, String merDSAPri, String merDSAPub, String innerPayPWD, String innerBackPayPWD, String activate) {
        super();
        this.merCode = merCode;
        this.merKeyType = merKeyType;
        this.merMD5PayPwd = merMD5PayPwd;
        this.merMD5BackPayPWD = merMD5BackPayPWD;
        this.merRSAPri = merRSAPri;
        this.merRSAPub = merRSAPub;
        this.merDSAPri = merDSAPri;
        this.merDSAPub = merDSAPub;
        this.innerPayPWD = innerPayPWD;
        this.innerBackPayPWD = innerBackPayPWD;
        this.activate = activate;
    }

    public MerKeyType() {
        super();
    }
    
    
    
}
