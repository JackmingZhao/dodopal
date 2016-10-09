package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * 一卡通收单环节用DTO
 *
 */
public class IcdcPurchaseDTO extends BaseEntity {

    private static final long serialVersionUID = -7530190276242507120L;
    
    /**
     * 应收金额（单位：分）
     */
    private String receivableAmt;
    
    /**
     * 实收金额（单位：分）
     */
    private String receivedAmt;
    
    /**
     * 商户编号
     */
    private String mercode;
    
    /**
     * 来源
     */
    private String source;
    
    /**
     * 操作人
     */
    private String userid;
    
    /**
     * 收单业务所在城市编号
     */
    private String citycode;
    
    /**
     * 产品库订单号
     */
    private String prdordernum;
    
    /**
     * posid
     */
    private String posid;
    
    /**
     * 一卡通编号
     */
    private String yktcode;
    
    /**
     * 业务类型
     */
    private String txntype;
    
    /**
     * 支付类型
     */
    private String paytype;
    
    /**
     * 支付方式
     */
    private String payway;
    
    private String m_sign;
    
    private String d_sign;
    
    private String p_sign;
    
    /**
     * 商户折扣
     */
    private String merdiscount;
    
    public String getReceivableAmt() {
        return receivableAmt;
    }

    public void setReceivableAmt(String receivableAmt) {
        this.receivableAmt = receivableAmt;
    }

    public String getReceivedAmt() {
        return receivedAmt;
    }

    public void setReceivedAmt(String receivedAmt) {
        this.receivedAmt = receivedAmt;
    }

    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPrdordernum() {
        return prdordernum;
    }

    public void setPrdordernum(String prdordernum) {
        this.prdordernum = prdordernum;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getYktcode() {
        return yktcode;
    }

    public void setYktcode(String yktcode) {
        this.yktcode = yktcode;
    }
    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getM_sign() {
        return m_sign;
    }

    public void setM_sign(String m_sign) {
        this.m_sign = m_sign;
    }

    public String getD_sign() {
        return d_sign;
    }

    public void setD_sign(String d_sign) {
        this.d_sign = d_sign;
    }

    public String getP_sign() {
        return p_sign;
    }

    public void setP_sign(String p_sign) {
        this.p_sign = p_sign;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getMerdiscount() {
        return merdiscount;
    }

    public void setMerdiscount(String merdiscount) {
        this.merdiscount = merdiscount;
    }
    
}
