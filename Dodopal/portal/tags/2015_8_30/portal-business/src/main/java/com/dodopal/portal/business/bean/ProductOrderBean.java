package com.dodopal.portal.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;
/**
 * 产品库中公交卡充值订单查询结果(标准查询)
 * 订单编号、订单时间、卡号、充值金额、充值前金额、充值后金额、实付金额、利润（不适用于个人）、订单状态、POS号、城市、操作员名称、打印小票。
 */
public class ProductOrderBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -9108810547425749643L;

    // 产品库订单编号
    private String proOrderNum;

    // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;

    // 卡号:
    private String orderCardno;

    // 充值的金额
    private Long txnAmt;

    // 充值前金额:在充值之前的公交卡内的余额。
    private Long befbal;

    // 充值后金额:在充值之后的公交卡内的余额。
    private String blackAmt;

    // 实付（收）金额：商户支付给DDP的金额（一般从账户里扣）。
    private Long receivedPrice;

    // 利润（不适用于个人）
    private Long merGain;

    // 订单状态(展示外部状态)
    private String proOrderState;

    // POS号:冗余保存，提高查询效率目的。
    private String posCode;

    // 城市名称
    private String cityName;

    // 操作员:用户的数据库ID。是商户的哪个操作人操作了这个公交卡充值业务。
    private String userId;

    // 操作员名称(用户的名称)
    private String userName;

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public Date getProOrderDate() {
        return proOrderDate;
    }

    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
    }

    public Long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Long getBefbal() {
        return befbal;
    }

    public void setBefbal(Long befbal) {
        this.befbal = befbal;
    }

    public String getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(String blackAmt) {
        this.blackAmt = blackAmt;
    }

    public Long getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(Long receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public Long getMerGain() {
        return merGain;
    }

    public void setMerGain(Long merGain) {
        this.merGain = merGain;
    }

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
