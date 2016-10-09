package com.dodopal.api.product.dto.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ConsumptionOrderQueryDTO extends QueryBase{

    private static final long serialVersionUID = 8521021060170146324L;
    /** 商户号*/
    private String mchnitid;
    /** 商户订单号*/
    private String transactionid;
    /** 查询起始时间*/
    private Date startdate;
    /** 查询结束时间*/
    private Date enddate;
    /** Pos编号*/
    private String posid;
    /** 卡号 */
    private String cardno;
    /** 用户名称 */
    private String username;
    /** 订单状态 */
    private String status;
    
    public String getMchnitid() {
        return mchnitid;
    }
    public void setMchnitid(String mchnitid) {
        this.mchnitid = mchnitid;
    }
    public String getTransactionid() {
        return transactionid;
    }
    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
    public Date getStartdate() {
        return startdate;
    }
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public Date getEnddate() {
        return enddate;
    }
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
