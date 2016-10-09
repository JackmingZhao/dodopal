package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * @author dodopal
 */

public class AcctReChargeClearing extends BaseEntity{
    private static final long serialVersionUID = 1L;
    //交易号
  
    private String orderNo;
    private Date orderDate;
    private String customerNo;
    private String customerName;
    private String customerType;
    private String orderAmount;
    private String customerRealPay;
    private String payCode;
    private String payType;
    private String payWay;
    private String payWayName;
    private String serviceRateType;
    private String serviceFee;
    private String bankRate;
    private String bankFee;
    private String bankClearingFlag;
    private String bankClearingTime;
    private String customerClearingFlag;
    private String customerClearingTime;
    private String bankSettlementFlag;
    private String bankSettlementTime;
    private String customerSettlementFlag;
    private String customerSettlementTime;
    private String tranOutStatus;
    private String tranInStatus;
    private String payGateWay;
    private String orderFrom;
    private String customerShouldPayAmount;
    private String customerAccountRealAmount;
    private String supplierSettlementFlag;
    private String supplierSettlementTime;
    private String customerRealPayAmount;
    private String dDPBankRate;
    private String dDPToBankFee;
    private String dDPFromBankShouldFee;
    private String dDPFromBankRealFee;
    private String dDPGetMerchantPayFee;
    public String getPayWayName() {
        return payWayName;
    }
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
  
    public String getdDPBankRate() {
        return dDPBankRate+"‰";
    }
    public void setdDPBankRate(String dDPBankRate) {
        this.dDPBankRate = dDPBankRate;
    }
    public String getdDPToBankFee() {
        return String.format("%.2f", Double.valueOf(dDPToBankFee==null?"0.00":dDPToBankFee));
        //return dDPToBankFee;
    }
    public void setdDPToBankFee(String dDPToBankFee) {
        this.dDPToBankFee = dDPToBankFee;
    }
    public String getdDPFromBankShouldFee() {
        return String.format("%.2f", Double.valueOf(dDPFromBankShouldFee==null?"0.00":dDPFromBankShouldFee));
    }
    public void setdDPFromBankShouldFee(String dDPFromBankShouldFee) {
        this.dDPFromBankShouldFee = dDPFromBankShouldFee;
    }
    public String getdDPFromBankRealFee() {
        return String.format("%.2f", Double.valueOf(dDPFromBankRealFee==null?"0.00":dDPFromBankRealFee));
    }
    public void setdDPFromBankRealFee(String dDPFromBankRealFee) {
        this.dDPFromBankRealFee = dDPFromBankRealFee;
    }
    public String getdDPGetMerchantPayFee() {
        return String.format("%.2f", Double.valueOf(dDPGetMerchantPayFee==null?"0.00":dDPGetMerchantPayFee));
    }
    public void setdDPGetMerchantPayFee(String dDPGetMerchantPayFee) {
        this.dDPGetMerchantPayFee = dDPGetMerchantPayFee;
    }
    public String getCustomerRealPayAmount() {
        return String.format("%.2f", Double.valueOf(customerRealPayAmount==null?"0.00":customerRealPayAmount));
    }
    public void setCustomerRealPayAmount(String customerRealPayAmount) {
        this.customerRealPayAmount = customerRealPayAmount;
    }
    public String getSupplierSettlementTime() {
        return supplierSettlementTime;
    }
    public void setSupplierSettlementTime(String supplierSettlementTime) {
        this.supplierSettlementTime = supplierSettlementTime;
    }
    public String getSupplierSettlementFlag() {
        return supplierSettlementFlag;
    }
    public void setSupplierSettlementFlag(String supplierSettlementFlag) {
        this.supplierSettlementFlag = supplierSettlementFlag;
    }
    
    public String getCustomerAccountRealAmount() {
        return String.format("%.2f", Double.valueOf(customerAccountRealAmount==null?"0.00":customerAccountRealAmount));
    }
    public void setCustomerAccountRealAmount(String customerAccountRealAmount) {
        this.customerAccountRealAmount = customerAccountRealAmount;
    }
    public String getCustomerShouldPayAmount() {
        return String.format("%.2f", Double.valueOf(customerShouldPayAmount==null?"0.00":customerShouldPayAmount));
    }
    public void setCustomerShouldPayAmount(String customerShouldPayAmount) {
        this.customerShouldPayAmount = customerShouldPayAmount;
    }
    public String getOrderFrom() {
        return orderFrom;
    }
    public String getOrderFromView() {
        if (StringUtils.isBlank(orderFrom)) {
            return null;
        }       
        if(SourceEnum.getSourceByCode(this.orderFrom)==null){
            return null;
        }else{
            return SourceEnum.getSourceByCode(this.orderFrom).getName();
        }
    
    }
    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }
    public String getTranOutStatus() {
        return tranOutStatus;
    }
    public String getTranOutStatusView() {
        if (StringUtils.isBlank(tranOutStatus)) {
            return null;
        }       
        if(TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus)==null){
            return null;
        }else{
            return TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus).getName();
        }
    }
    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }
    public String getTranInStatus() {
        return tranInStatus;
    }
    public String getTranInStatusView() {
        if (StringUtils.isBlank(tranInStatus)) {
            return null;
        }       
        if(TranInStatusEnum.getTranInStatusByCode(this.tranInStatus)==null){
            return null;
        }else{
            return TranInStatusEnum.getTranInStatusByCode(this.tranInStatus).getName();
        }
    }
    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }
    public String getPayGateWay() {
        return payGateWay;
    }
    public String getPayGateWayView() {
        if (StringUtils.isBlank(payGateWay)) {
            return null;
        }       
        if(PayGatewayEnum.getPayGatewayEnumByCode(this.payGateWay)==null){
            return null;
        }else{
            return PayGatewayEnum.getPayGatewayEnumByCode(this.payGateWay).getName();
        }
    }
    public void setPayGateWay(String payGateWay) {
        this.payGateWay = payGateWay;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getCustomerNo() {
        return customerNo;
    }
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerType() {
        return customerType;
    }
    public String getCustomerTypeView() {
        if (StringUtils.isBlank(customerType)) {
            return null;
        }       
        if(MerTypeEnum.getMerTypeByCode(this.customerType)==null){
            return null;
        }else{
            return MerTypeEnum.getMerTypeByCode(this.customerType).getName();
        }
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getOrderAmount() {
        return String.format("%.2f", Double.valueOf(orderAmount==null?"0.00":orderAmount));
    }
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }
    public String getCustomerRealPay() {
        return customerRealPay;
    }
    public void setCustomerRealPay(String customerRealPay) {
        this.customerRealPay = customerRealPay;
    }
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getPayType() {
        return payType;
    }
    public String getPayTypeView() {
        if (StringUtils.isBlank(payType)) {
            return null;
        }       
        if(PayTypeEnum.getPayTypeEnumByCode(this.payType)==null){
            return null;
        }else{
            return PayTypeEnum.getPayTypeEnumByCode(this.payType).getName();
        }
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
    public String getServiceRateType() {
        return serviceRateType;
    }
    public String getServiceRateTypeView() {

        if (StringUtils.isBlank(serviceRateType)) {
            return null;
        }       
        if(RateTypeEnum.getRateTypeByCode(this.serviceRateType)==null){
            return null;
        }else{
            return RateTypeEnum.getRateTypeByCode(this.serviceRateType).getName();
        }
    }
    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }
    public String getServiceFee() {
        if("单笔返点金额(元)".equals(getServiceRateTypeView())){
            return String.format("%.2f", Double.valueOf(serviceFee==null?"0.00":serviceFee)/100)+"元";
            
        }else if("千分比(‰)".equals(getServiceRateTypeView())){
            return serviceFee+"‰";
            
        }else{
            return String.format("%.2f", Double.valueOf(serviceFee==null?"0.00":serviceFee));
            
        }
        //return serviceFee;
    
    }
    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
    public String getBankRate() {
        return bankRate;
    }
    public void setBankRate(String bankRate) {
        this.bankRate = bankRate;
    }
    public String getBankFee() {
        return bankFee;
    }
    public void setBankFee(String bankFee) {
        this.bankFee = bankFee;
    }
  
    public String getBankClearingFlag() {
        return bankClearingFlag; 
    }
    public String getBankClearingFlagView() {
        if (StringUtils.isBlank(bankClearingFlag)) {
            return null;
        }       
        if(ClearingFlagEnum.getClearingFlagByCode(this.bankClearingFlag)==null){
            return null;
        }else{
            return ClearingFlagEnum.getClearingFlagByCode(this.bankClearingFlag).getName(); 
        }
    }
    public void setBankClearingFlag(String bankClearingFlag) {
        this.bankClearingFlag = bankClearingFlag;
    }
    public String getBankClearingTime() {
        return bankClearingTime;
    }
    public void setBankClearingTime(String bankClearingTime) {
        this.bankClearingTime = bankClearingTime;
    }
    public String getCustomerClearingFlag() {

        if (StringUtils.isBlank(customerClearingFlag)) {
            return null;
        }       
        if(ClearingFlagEnum.getClearingFlagByCode(this.customerClearingFlag)==null){
            return null;
        }else{
            return ClearingFlagEnum.getClearingFlagByCode(this.customerClearingFlag).getName();  
        }
    }
    public void setCustomerClearingFlag(String customerClearingFlag) {
        this.customerClearingFlag = customerClearingFlag;
    }
    public String getCustomerClearingTime() {
        return customerClearingTime;
    }
    public void setCustomerClearingTime(String customerClearingTime) {
        this.customerClearingTime = customerClearingTime;
    }
    public String getBankSettlementFlag() {
        return bankSettlementFlag;
    }
    public void setBankSettlementFlag(String bankSettlementFlag) {
        this.bankSettlementFlag = bankSettlementFlag;
    }
    public String getBankSettlementTime() {
        return bankSettlementTime;
    }
    public void setBankSettlementTime(String bankSettlementTime) {
        this.bankSettlementTime = bankSettlementTime;
    }
    public String getCustomerSettlementFlag() {
        return customerSettlementFlag;
    }
    public void setCustomerSettlementFlag(String customerSettlementFlag) {
        this.customerSettlementFlag = customerSettlementFlag;
    }
    public String getCustomerSettlementTime() {
        return customerSettlementTime;
    }
    public void setCustomerSettlementTime(String customerSettlementTime) {
        this.customerSettlementTime = customerSettlementTime;
    }
}
