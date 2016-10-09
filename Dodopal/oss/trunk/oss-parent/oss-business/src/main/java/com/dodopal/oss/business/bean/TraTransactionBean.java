package com.dodopal.oss.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ClearFlagEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 交易流水信息
 *  @author 
 *
 */
public class TraTransactionBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -4620945385034369426L;
    /**
     * 交易流水号
     */
    private String tranCode;

    /**
     * 交易名称
     */
    private String tranName;

    /**
     * 外部交易状态
     * 0待支付1已取消2支付中3已支付4待退款5已退款6待转帐7转帐成功
     */
    private String tranOutStatus;
    
    /**
     * 内部交易状态
     * 0待处理 1已处理 10账户更新失败 11内部更新失败
     */
    private String tranInStatus;
    
    /**
     * 备注
     */
    private String comments;
    
    /**
     * 页面回调地址
     */
    private String pageCallbackUrl;

    /**
     * 服务器端通知地址
     */
    private String serviceNoticeUrl;
    
    /**
     * 金额   “元”为单位
     */
    private double amountMoney;
    
    /**
     * 交易类型
     * 1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
     */
    private String tranType;
    
    /**
     * 用户类型
     * 0：个人  1：商户
     */
    private String userType;
    
    /**
     * 用户号/商户号
     */
    private String merOrUserCode;
    
    /**
     * 用户号/商户名
     */
    private String merOrUserName;
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    /**
     * 商品名称
     */
    private String commodity;
    
    /**
     * 业务类型代码
     */
    private String businessType;
    
    /**
     * 来源
     */
    private String source;
    
    /**
     * 订单时间
     */
    private Date orderDate;
    
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 支付方式
     */
    private String payWay;
    
    /**
     * 支付服务费率
     *  数字 两位小数  默认：0
     */
    private double payServiceRate;
    
    /**
     * 支付服务费
     */
    private double payServiceFee;
    
    /**
     * 支付手续费率
     */
    private double payProceRate;
    
    /**
     * 支付手续费
     */
    private double payProceFee;
    
    /**
     * 实际交易金额(元)
     */
    private double realTranMoney;
    
    /**
     * 转出商户号
     */
    private String turnOutMerCode;
   
    /**
     * 转入商户号
     */
    private String turnIntoMerCode;
    
    /**
     * 原交易流水号
     */
    private String oldTranCode;
    
    /**
     * 清算标志位 0：未清算  1：已清算
     */
    private String clearFlag;
    
    /**
     * 接受密文
     */
    private String acceptCiphertext;
    
    /**
     * 发送密文
     */
    private String sendCiphertext;
    
    /**
     * 完成时间
     */
    private Date finishDate;
    /**
     * 网关
     */
    private String payGateway;
    /**
     * 服务费率类型
     */
    private String payServiceType;
  
    //支付方式名称
    private String payWayName;
    
    /**
     * 打款人
     */
    private String merPayMoneyUser;
    //上级商户名称
    private String merParentName;
    
    //客户所在城市
    private String merCityName;
    
    public String getMerParentName() {
        return merParentName;
    }

    public void setMerParentName(String merParentName) {
        this.merParentName = merParentName;
    }

    public String getMerCityName() {
        return merCityName;
    }

    public void setMerCityName(String merCityName) {
        this.merCityName = merCityName;
    }

    public String getMerPayMoneyUser() {
        return merPayMoneyUser;
    }

    public void setMerPayMoneyUser(String merPayMoneyUser) {
        this.merPayMoneyUser = merPayMoneyUser;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getPayServiceType() {
        if (StringUtils.isBlank(this.payServiceType)) {
            return null;
        }else if(ServiceRateTypeEnum.getServiceRateTypeByCode(this.payServiceType)==null){
            return null;
        }else{
            return ServiceRateTypeEnum.getServiceRateTypeByCode(this.payServiceType).getName();
        }    
    }

    public void setPayServiceType(String payServiceType) {
        
        this.payServiceType = payServiceType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    //前端展示
    public String getTranOutStatus() {
        if (StringUtils.isBlank(this.tranOutStatus)) {
            return null;
        }else if(TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus)==null){
            return null;
        }
        return TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus).getName();
    }
    
    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }
    
    //前端展示
    public String getTranInStatus() {
        if (StringUtils.isBlank(this.tranInStatus)) {
            return null;
        }else if(TranInStatusEnum.getTranInStatusByCode(this.tranInStatus)==null){
            return null;
        }
        return TranInStatusEnum.getTranInStatusByCode(this.tranInStatus).getName();
    }

    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPageCallbackUrl() {
        return pageCallbackUrl;
    }

    public void setPageCallbackUrl(String pageCallbackUrl) {
        this.pageCallbackUrl = pageCallbackUrl;
    }

    public String getServiceNoticeUrl() {
        return serviceNoticeUrl;
    }

    public void setServiceNoticeUrl(String serviceNoticeUrl) {
        this.serviceNoticeUrl = serviceNoticeUrl;
    }


    //前端展示
    public String getTranType() {
        if (StringUtils.isBlank(this.tranType)) {
            return null;
        }else if(TranTypeEnum.getTranTypeByCode(this.tranType)==null){
            return null;
        }
        return TranTypeEnum.getTranTypeByCode(this.tranType).getName();
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    //前端展示
    public String getUserType() {
        if (StringUtils.isBlank(this.userType)) {
            return null;
        }else if(MerUserTypeEnum.getMerUserUserTypeByCode(this.userType)==null){
            return null;
        }
        return MerUserTypeEnum.getMerUserUserTypeByCode(this.userType).getName();
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMerOrUserCode() {
        return merOrUserCode;
    }

    public void setMerOrUserCode(String merOrUserCode) {
        this.merOrUserCode = merOrUserCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
    //前端展示
    public String getBusinessType() {
        if (StringUtils.isBlank(this.businessType)) {
            return null;
        }else if(RateCodeEnum.getRateTypeByCode(this.businessType)==null){
            return null;
        }
        return RateCodeEnum.getRateTypeByCode(this.businessType).getName();
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
  //前端展示
    public String getSource() {
        if (StringUtils.isBlank(this.source)) {
            return null;
        }else if(SourceEnum.getSourceByCode(this.source)==null){
            return null;
        }
        return SourceEnum.getSourceByCode(this.source).getName();
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
  //前端展示
    public String getPayType() {
        if (StringUtils.isBlank(this.payType)) {
            return null;
        }else if(PayTypeEnum.getPayTypeEnumByCode(this.payType)==null){
            return null;
        }
        return PayTypeEnum.getPayTypeEnumByCode(this.payType).getName();
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

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public double getPayProceRate() {
        return payProceRate;
    }

    public void setPayProceRate(double payProceRate) {
        this.payProceRate = payProceRate;
    }


    public double getPayServiceFee() {
        return payServiceFee;
    }

    public void setPayServiceFee(double payServiceFee) {
        this.payServiceFee = payServiceFee;
    }

    public double getPayProceFee() {
        return payProceFee;
    }

    public void setPayProceFee(double payProceFee) {
        this.payProceFee = payProceFee;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public double getRealTranMoney() {
        return realTranMoney;
    }

    public void setRealTranMoney(double realTranMoney) {
        this.realTranMoney = realTranMoney;
    }

    public String getTurnOutMerCode() {
        return turnOutMerCode;
    }

    public void setTurnOutMerCode(String turnOutMerCode) {
        this.turnOutMerCode = turnOutMerCode;
    }

    public String getTurnIntoMerCode() {
        return turnIntoMerCode;
    }

    public void setTurnIntoMerCode(String turnIntoMerCode) {
        this.turnIntoMerCode = turnIntoMerCode;
    }

    public String getOldTranCode() {
        return oldTranCode;
    }

    public void setOldTranCode(String oldTranCode) {
        this.oldTranCode = oldTranCode;
    }

    public String getClearFlag() {
        return clearFlag;
    }
    
    public String getClearFlagView() {
        if (StringUtils.isBlank(this.clearFlag)) {
            return null;
        }
        if (ClearFlagEnum.getBindByCode(this.clearFlag) ==null) {
            return null;
        }
        return ClearFlagEnum.getBindByCode(this.clearFlag).getName();
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getAcceptCiphertext() {
        return acceptCiphertext;
    }

    public void setAcceptCiphertext(String acceptCiphertext) {
        this.acceptCiphertext = acceptCiphertext;
    }

    public String getSendCiphertext() {
        return sendCiphertext;
    }

    public void setSendCiphertext(String sendCiphertext) {
        this.sendCiphertext = sendCiphertext;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getMerOrUserName() {
        return merOrUserName;
    }

    public void setMerOrUserName(String merOrUserName) {
        this.merOrUserName = merOrUserName;
    }

        
        
}
