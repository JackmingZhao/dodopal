package com.dodopal.product.business.model;

import java.util.Date;
import java.util.List;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.product.business.bean.MerchantBean;

public class ProductConsumeOrder extends BaseEntity {
    private static final long serialVersionUID = 7835152044218233014L;

    // 订单编号:
    private String orderNum;

    // 卡号   CARD_NUM
    private String cardNum;

    // 卡面号
    private String cardFaceno;

    // 城市名称
    private String cityName;

    // POS号:冗余保存，提高查询效率目的。
    private String posCode;

    //应收金额
    private long originalPrice;

    //实收金额
    private long receivedPrice;

    // 操作员:用户的数据库ID。是商户的哪个操作人操作了这个公交卡充值业务。
    private String userId;

    // 操作员名称(用户的名称)
    private String userName;

    // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;

    // 订单日期:格式yyyyMMdd,这里实际上为订单的创建日期（有点类似订单时间）。冗余的主要目的是为了满足数据库系统的分区。
    private String proOrderDay;

    // 消费前金额
    private String befbal;

    // 消费后金额
    private String blackAmt;

    // 商户利润
    private long merGain;

    //订单状态 
    private String states;

    // 通卡公司名称
    private String yktName;

    // 商户费率:在进行这一笔公交卡充值的时候，商户与DDP之间的公交卡充值业务费率。
    private double merRate;

    //商户折扣
    private String merDiscount;

    //商户名称
    private String merName;
    
    //商户类型
    private String merType;

    //内部状态
    private String innerStates;
    // 客户编码
    private String customerCode;
    
    // 客户类型
    private String customerType;

    /**************** 新添字段 *******************/
    private List<MerchantBean> merchantbean;

    //add by guanjl 20150107 start

    //服务费率类型
    private String payServiceType;

    //服务费率
    private String payServiceRate;

    //add by guanjl 20150107 end
    
    //pos的备注
    private String comments;
    
    // 通卡优惠标志
    private String yktDisCountSign;
    
    public String getYktDisCountSign() {
        return yktDisCountSign;
    }

    public void setYktDisCountSign(String yktDisCountSign) {
        this.yktDisCountSign = yktDisCountSign;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<MerchantBean> getMerchantbean() {
        return merchantbean;
    }

    public void setMerchantbean(List<MerchantBean> merchantbean) {
        this.merchantbean = merchantbean;
    }

    public String getInnerStates() {
        return innerStates;
    }

    public void setInnerStates(String innerStates) {
        this.innerStates = innerStates;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerDiscount() {
        return merDiscount;
    }

    public void setMerDiscount(String merDiscount) {
        this.merDiscount = merDiscount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(long receivedPrice) {
        this.receivedPrice = receivedPrice;
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

    public Date getProOrderDate() {
        return proOrderDate;
    }

    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

    public String getProOrderDay() {
        return proOrderDay;
    }

    public void setProOrderDay(String proOrderDay) {
        this.proOrderDay = proOrderDay;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(String blackAmt) {
        this.blackAmt = blackAmt;
    }

    public long getMerGain() {
        return merGain;
    }

    public void setMerGain(long merGain) {
        this.merGain = merGain;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public double getMerRate() {
        return merRate;
    }

    public void setMerRate(double merRate) {
        this.merRate = merRate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCardFaceno() {
        return cardFaceno;
    }

    public void setCardFaceno(String cardFaceno) {
        this.cardFaceno = cardFaceno;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public String getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(String payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

}
