package com.dodopal.api.product.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;


/**
 * 公交卡充值订单导出项 
 * 
 */
public class ProductOrderForExport implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // 订单编号
    private String proOrderNum;
    
    // 充值产品编号
    private String proCode;
    
    // 充值产品名称
    private String proName;
    
    // 充值面额
    private String txnAmt;
    
    // 城市编号
    private String cityCode; 
    
    // 通卡公司编号
    private String yktCode; 
    
    // 城市名称
    private String cityName; 
    
    // 通卡公司名称
    private String yktName; 
    
    // 商户实付金额(DDP实收金额)
    private String receivedPrice; 
    
    // 商户充值业务费率
    private String merRate;
    
    // 商户费率类型
    private String merRateType;

    // 服务费率类型
    private String payServiceType;
    
    // 服务费率
    private String payServiceRate;
    
    // 通卡充值费率（固定类型：千分比）
    private String yktRechargeRate;
    
    // 商户进货价（DDP的出货价）
    private String merPurchaasePrice;
    
    // 订单时间
    private String proOrderDate;
    
    // 订单时间段
    private String proOrderTime;

    // 订单日期
    private String proOrderDay;
    
    // 公交卡卡内号
    private String orderCardno;
    
    // 公交卡卡面号
    private String cardFaceno;
    
    // POS号
    private String posCode;
    
    // 商户利润
    private String merGain;
    
    // 公交卡充值前金额
    private String befbal; 
    
    // 公交卡充值后金额
    private String blackAmt; 
    
    // 支付类型
    private String payType;
    
    // 支付方式
    private String payWay;
    
    // 订单状态
    private String proOrderState;
    
    // 订单内部状态
    private String proInnerState;
    
    // 订单前内部状态
    private String proBeforInnerState;
    
    // 可疑处理状态
    private String proSuspiciousState;
    
    // 可疑处理说明
    private String proSuspiciousExplain;
    
    // 客户编号
    private String merCode;
    
    // 客户名称
    private String merName;
    
    // 客户类型
    private String merUserType;  
    
    // 圈存标识
    private String loadFlag;  
   
    // 外接商户订单编号
    private String merOrderNum;
    
    // 圈存订单号
    private String loadOrderNum;

    // 来源
    private String source;
    
    // 清算标志
    private String clearingMark;

    // 操作员编号
    private String userId;
    
    // 操作员名称
    private String userName;
    
    // 备注
    private String comments;
    
    // 页面URL:页面回调通知外接商户
    private String pageCallBackURL;
    
    // 服务器URL:服务器端回调通知外接商户
    private String serviceNoticeURL;
    
    // 资金处理结果
    private String fundProcessResult;   
    
    // 用户编号
    private String userCode;
    
    // 集团商户部门ID
    private String depId;
    
    // 外接商户用户编号
    private String extUserCode;
    	
    private String createDate;

    private String updateDate;

    private String createUser;

    private String updateUser; 
    
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

    public String getProOrderNum() {
        return proOrderNum;
    }

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

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getMerRateType() {
        return merRateType;
    }

    public String getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(String receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public String getMerRate() {
        return merRate;
    }

    public void setMerRate(String merRate) {
        this.merRate = merRate;
    }

    public String getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(String payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getYktRechargeRate() {
        return yktRechargeRate;
    }

    public void setYktRechargeRate(String yktRechargeRate) {
        this.yktRechargeRate = yktRechargeRate;
    }

    public String getMerPurchaasePrice() {
        return merPurchaasePrice;
    }

    public void setMerPurchaasePrice(String merPurchaasePrice) {
        this.merPurchaasePrice = merPurchaasePrice;
    }

    public String getMerGain() {
        return merGain;
    }

    public void setMerGain(String merGain) {
        this.merGain = merGain;
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

    public void setMerRateType(String merRateType) {
        this.merRateType = merRateType;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public String getProOrderDate() {
        return proOrderDate;
    }

    public void setProOrderDate(String proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

    public String getProOrderDay() {
        return proOrderDay;
    }

    public void setProOrderDay(String proOrderDay) {
        this.proOrderDay = proOrderDay;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
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

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getProInnerState() {
        return proInnerState;
    }

    public void setProInnerState(String proInnerState) {
        this.proInnerState = proInnerState;
    }

    public String getProBeforInnerState() {
        return proBeforInnerState;
    }

    public void setProBeforInnerState(String proBeforInnerState) {
        this.proBeforInnerState = proBeforInnerState;
    }

    public String getProSuspiciousState() {
        return proSuspiciousState;
    }

    public void setProSuspiciousState(String proSuspiciousState) {
        this.proSuspiciousState = proSuspiciousState;
    }

    public String getProSuspiciousExplain() {
        return proSuspiciousExplain;
    }

    public void setProSuspiciousExplain(String proSuspiciousExplain) {
        this.proSuspiciousExplain = proSuspiciousExplain;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

    public String getLoadFlag() {
        return loadFlag;
    }

    public void setLoadFlag(String loadFlag) {
        this.loadFlag = loadFlag;
    }

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }

    public String getLoadOrderNum() {
        return loadOrderNum;
    }

    public void setLoadOrderNum(String loadOrderNum) {
        this.loadOrderNum = loadOrderNum;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPageCallBackURL() {
        return pageCallBackURL;
    }

    public void setPageCallBackURL(String pageCallBackURL) {
        this.pageCallBackURL = pageCallBackURL;
    }

    public String getServiceNoticeURL() {
        return serviceNoticeURL;
    }

    public void setServiceNoticeURL(String serviceNoticeURL) {
        this.serviceNoticeURL = serviceNoticeURL;
    }

    public String getFundProcessResult() {
        return fundProcessResult;
    }

    public void setFundProcessResult(String fundProcessResult) {
        this.fundProcessResult = fundProcessResult;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getExtUserCode() {
        return extUserCode;
    }

    public void setExtUserCode(String extUserCode) {
        this.extUserCode = extUserCode;
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

    public String getProOrderTime() {
        return proOrderTime;
    }

    public void setProOrderTime(String proOrderTime) {
        this.proOrderTime = proOrderTime;
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
