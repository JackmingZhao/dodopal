package com.dodopal.product.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 产品库消费(收单)信息DTO(收单主订单表+收单记录表)
 * 
 */
public class ConsumeOrder extends BaseEntity {

    private static final long serialVersionUID = -831932949457836942L;

    // 订单编号:全局唯一（C + 20150428222211 +五位数据库cycle sequence（循环使用））    全局唯一    产品库自动生成。
    private String orderNum;
    
    // 应收金额:(原本价格：单位：分)
    private long originalPrice;
    
    // 实收金额:(实际收款价格：单位：分)
    private long receivedPrice;
    
    // 客户类型:(0:个人；1：企业)
    private String customerType; 
    
    // 客户号:(个人编号或者商户号)
    private String customerCode; 
 
    // 客户名称:(个人：个人名称；商户：商户名称)
    private String customerName; 
    
    // 业务类型
    private String businessType; 
    
    // 商户费率类型
    private String merRateType;
    
    // 商户费率
    private Double merRate;
    
    // 服务费率类型
    private String serviceRateType;
    
    // 服务费率
    private Double serviceRate;
    
    // 商户利润
    private Long merGain;
    
    // 订单时间:式yyyy/MM/dd HH:MM:SS,实际为订单的创建时间。
    private Date orderDate;

    // 订单日期:格式yyyyMMdd,这里实际上为订单的创建日期（有点类似订单时间）。冗余的主要目的是为了满足数据库系统的分区。
    private String orderDay;
    
    // 支付网关:一卡通收单时保存通卡code
    private String payGateway;
    
    // 支付类型:0:账户支付;1:一卡通支付;2:在线支付;3:银行支付
    private String payType;
    
    // 支付方式：一卡通收单时保存城市code
    private String payWay;
    
    // 订单状态(展示外部状态)
    private String states;
    
    // 来源
    private String source;
    
    // 清算标志:用于判别是否已经对这条订单记录进行了清分清算。
    private String clearingMark;

    // 操作员:用户的数据库ID。
    private String userId;
    
    // 备注
    private String comments;
    
    // 资金处理结果:0:没有处理（默认值）；1：处理完毕
    private String fundProcessResult;   
    
    // 城市CODE
    private String cityCode;
    
    // 通卡公司一卡通支付费率（固定类型：千分比）
    private Double yktPayRate;
    
    // 商户折扣
    private Double merDiscount;
    
    // 城市名称
    private String cityName;

    // 通卡公司名称
    private String ytkName;
    
    // 通卡公司CODE
    private String ytkCode;
       
    // 卡内号
    private String cardNum; 
    
    // 卡面号
    private String cardFaceno;     
    
    // POS号
    private String posCode; 
 
    // 支付前金额(在支付之前的公交卡内的余额。)
    private Long befbal; 
    
    // 支付后金额(在支付之后的公交卡内的余额。)
    private Long blackAmt; 
    
    // 内部状态(00:订单创建成功;01:验卡成功;02:申请消费密钥成功;10:验卡失败;11:申请消费密钥失败;12:扣款失败;20:扣款成功;30:扣款未知)
    private String innerStates;
    
    // 前内部状态
    private String beforInnerStates;
    
    // 可疑处理状态(0:不可疑:；1:可疑；)
    private String suspiciousStates;
    
    // 可疑处理说明(OSS用户处理可疑订单的时候输入。)
    private String suspiciousExplain;

    // 重发标志位（0：生单默认值；1：申请扣款指令环节产品库接收到DLL发送重发请求时更新为1）
    private String resendSign;
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getYtkCode() {
        return ytkCode;
    }

    public void setYtkCode(String ytkCode) {
        this.ytkCode = ytkCode;
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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public Long getBefbal() {
        return befbal;
    }

    public void setBefbal(Long befbal) {
        this.befbal = befbal;
    }

    public Long getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(Long blackAmt) {
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

    public Double getYktPayRate() {
        return yktPayRate;
    }

    public void setYktPayRate(Double yktPayRate) {
        this.yktPayRate = yktPayRate;
    }

    public Double getMerDiscount() {
        return merDiscount;
    }

    public void setMerDiscount(Double merDiscount) {
        this.merDiscount = merDiscount;
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

    public Double getMerRate() {
        return merRate;
    }

    public void setMerRate(Double merRate) {
        this.merRate = merRate;
    }

    public String getServiceRateType() {
        return serviceRateType;
    }

    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Long getMerGain() {
        return merGain;
    }

    public void setMerGain(Long merGain) {
        this.merGain = merGain;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
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

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getResendSign() {
        return resendSign;
    }

    public void setResendSign(String resendSign) {
        this.resendSign = resendSign;
    }

    public String getCardFaceno() {
        return cardFaceno;
    }

    public void setCardFaceno(String cardFaceno) {
        this.cardFaceno = cardFaceno;
    }
}
