package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class ConsumptionOrderDTO extends BaseEntity{
    private static final long serialVersionUID = -7724553583217482274L;
    /**订单号 */
    private String orderid;
    /**商户名称 */
    private String mchnitname;
    /**用户名称 */
    private String username;
    /**pos号 */
    private String posid;
    /**订单状态*/
    private String status;
    /**充值卡号 */
    private String cardno;
    /**交易前余额(元)*/
    private String proamt;
    /**应收金额(元)*/
    private String facevalue;
    /**用户折扣*/
    private String sale;
    /**实收金额(元) */
    private String amt;
    /**交易后余额(元)*/
    private String blackamt;
    /**结算折扣*/
    private String setsale;
    /**结算金额(元)*/
    private String setamt;
    /**结算手续费(元) */
    private String setfee;
    /**交易时间*/
    private String checkcarddate;
    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getMchnitname() {
        return mchnitname;
    }
    public void setMchnitname(String mchnitname) {
        this.mchnitname = mchnitname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getProamt() {
        return proamt;
    }
    public void setProamt(String proamt) {
        this.proamt = proamt;
    }
    public String getFacevalue() {
        return facevalue;
    }
    public void setFacevalue(String facevalue) {
        this.facevalue = facevalue;
    }
    public String getSale() {
        return sale;
    }
    public void setSale(String sale) {
        this.sale = sale;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getBlackamt() {
        return blackamt;
    }
    public void setBlackamt(String blackamt) {
        this.blackamt = blackamt;
    }
    public String getSetsale() {
        return setsale;
    }
    public void setSetsale(String setsale) {
        this.setsale = setsale;
    }
    public String getSetamt() {
        return setamt;
    }
    public void setSetamt(String setamt) {
        this.setamt = setamt;
    }
    public String getSetfee() {
        return setfee;
    }
    public void setSetfee(String setfee) {
        this.setfee = setfee;
    }
    public String getCheckcarddate() {
        return checkcarddate;
    }
    public void setCheckcarddate(String checkcarddate) {
        this.checkcarddate = checkcarddate;
    }

    
}
