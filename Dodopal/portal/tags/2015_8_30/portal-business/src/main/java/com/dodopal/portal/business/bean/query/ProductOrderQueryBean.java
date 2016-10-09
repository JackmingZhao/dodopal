package com.dodopal.portal.business.bean.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ProductOrderQueryBean extends QueryBase{

    /**
     * 
     */
    private static final long serialVersionUID = -2113439842584007196L;
 // 订单编号 文本框 精确  用户输入
    private String proOrderNum;

    // 订单状态 下拉框 精确  用户选择    这里指的是订单外部状态
    private String proOrderState;

    // 订单创建时间 日历选择框 范围  用户选择    格式： yyyy-MM-dd HH:MM:SS
    private Date orderDateStart;

    private Date orderDateEnd;

    //卡号    文本框 精确  用户输入 
    private String orderCardno;

    // 业务城市    精确  用户选择
    private String cityName;

    // 充值金额（起/止）   文本输入    范围  用户输入    正数值
    private Long txnAmtStart;

    private Long txnAmtEnd;

    // POS 文本框 精确  用户输入    商户用于充值的POS机编号，仅适用于商户。
    private String posCode;

    // 外部订单号   文本框 精确  用户输入    仅在商户类型为外接商户的时候显示。
    private String merOrderNum;

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getTxnAmtStart() {
        return txnAmtStart;
    }

    public void setTxnAmtStart(Long txnAmtStart) {
        this.txnAmtStart = txnAmtStart;
    }

    public Long getTxnAmtEnd() {
        return txnAmtEnd;
    }

    public void setTxnAmtEnd(Long txnAmtEnd) {
        this.txnAmtEnd = txnAmtEnd;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }
    
}
