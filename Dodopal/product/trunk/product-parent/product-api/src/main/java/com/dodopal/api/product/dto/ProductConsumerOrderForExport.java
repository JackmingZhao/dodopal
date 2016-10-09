package com.dodopal.api.product.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

/**
 * 公交卡消费订单导出项
 * 
 */
public class ProductConsumerOrderForExport implements Serializable {

	private static final long serialVersionUID = -7206811807919449333L;

    // 订单编号
    private String orderNum;
    
    // 应收金额
    private String originalPrice;
    
    // 实收金额
    private String receivedPrice;
    
    // 客户类型
    private String customerType; 
    
    // 客户号
    private String customerCode; 
 
    // 客户名称
    private String customerName; 
    
    // 业务类型
    private String businessType; 
    
    // 商户费率类型
    private String merRateType;
    
    // 商户费率
    private String merRate;
    
    // 服务费率类型
    private String serviceRateType;
    
    // 服务费率
    private String serviceRate;
    
    // 商户利润
    private String merGain;
    
    // 订单时间
    private String orderDate;
    
    // 订单时间段
    private String orderTime;
    
    // 订单日期
    private String orderDay;
    
    // 支付网关
    private String payGateway;
    
    // 支付类型
    private String payType;
    
    // 支付方式
    private String payWay;
    
    // 订单状态
    private String states;
    
    // 来源
    private String source;
    
    // 清算标志
    private String clearingMark;

    // 操作员ID
    private String userId;
    
    // 备注
    private String comments;
    
    // 资金处理结果
    private String fundProcessResult;   
    
    // 城市编号
    private String cityCode;
    
    // 通卡一卡通支付费率（固定类型：千分比）
    private String yktPayRate;
    
    // 商户折扣
    private String merDiscount;
    
    // 城市名称
    private String cityName;

    // 通卡公司名称
    private String ytkName;
    
    // 通卡公司编号
    private String ytkCode;
       
    // 卡内号
    private String cardNum; 
    
    // 卡面号
    private String cardFaceno;     
    
    // POS号
    private String posCode; 
 
    // 支付前金额
    private String befbal; 
    
    // 支付后金额
    private String blackAmt; 
    
    // 内部状态
    private String innerStates;
    
    // 前内部状态
    private String beforInnerStates;
    
    // 可疑处理状态
    private String suspiciousStates;
    
    // 可疑处理说明
    private String suspiciousExplain;
    
    /**********************************************************/
    // 上级商户
    private String merParentName;

    // 商圈
    private String tradeArea;
    
    // 经营范围
    private String merBusinessScopeId;
    
    //星期
    private String weekDay;
    /**********************************************************/

    private String createDate;

    private String updateDate;

    private String createUser;

    private String updateUser; 
    
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(String receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMerRateType() {
        return merRateType;
    }

    public void setMerRateType(String merRateType) {
        this.merRateType = merRateType;
    }

    public String getMerRate() {
        return merRate;
    }

    public void setMerRate(String merRate) {
        this.merRate = merRate;
    }

    public String getServiceRateType() {
        return serviceRateType;
    }

    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getMerGain() {
        return merGain;
    }

    public void setMerGain(String merGain) {
        this.merGain = merGain;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClearingMark() {
        return clearingMark;
    }

    public void setClearingMark(String clearingMark) {
        this.clearingMark = clearingMark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFundProcessResult() {
        return fundProcessResult;
    }

    public void setFundProcessResult(String fundProcessResult) {
        this.fundProcessResult = fundProcessResult;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getYktPayRate() {
        return yktPayRate;
    }

    public void setYktPayRate(String yktPayRate) {
        this.yktPayRate = yktPayRate;
    }

    public String getMerDiscount() {
        return merDiscount;
    }

    public void setMerDiscount(String merDiscount) {
        this.merDiscount = merDiscount;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getYtkName() {
        return ytkName;
    }

    public void setYtkName(String ytkName) {
        this.ytkName = ytkName;
    }

    public String getYtkCode() {
        return ytkCode;
    }

    public void setYtkCode(String ytkCode) {
        this.ytkCode = ytkCode;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardFaceno() {
        return cardFaceno;
    }

    public void setCardFaceno(String cardFaceno) {
        this.cardFaceno = cardFaceno;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
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

    public String getInnerStates() {
        return innerStates;
    }

    public void setInnerStates(String innerStates) {
        this.innerStates = innerStates;
    }

    public String getBeforInnerStates() {
        return beforInnerStates;
    }

    public void setBeforInnerStates(String beforInnerStates) {
        this.beforInnerStates = beforInnerStates;
    }

    public String getSuspiciousStates() {
        return suspiciousStates;
    }

    public void setSuspiciousStates(String suspiciousStates) {
        this.suspiciousStates = suspiciousStates;
    }

    public String getSuspiciousExplain() {
        return suspiciousExplain;
    }

    public void setSuspiciousExplain(String suspiciousExplain) {
        this.suspiciousExplain = suspiciousExplain;
    }

    public String getMerParentName() {
        return merParentName;
    }

    public void setMerParentName(String merParentName) {
        this.merParentName = merParentName;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getMerBusinessScopeId() {
        return merBusinessScopeId;
    }

    public void setMerBusinessScopeId(String merBusinessScopeId) {
        this.merBusinessScopeId = merBusinessScopeId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    
    //经营范围
    public String getMerBusinessScopeIdView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.merBusinessScopeId)) {
            return null;
        }
        return ddicService.getDdicNameByCode("BUSINESS_SCOPE", this.merBusinessScopeId).toString();
    }
}
