package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ConsumeOrderInternalStatesEnum;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;

public class CardConsumeClearing extends BaseEntity{
    /**
     * @author dodopal
     */
    private static final long serialVersionUID = 1L;
    
    private String orderNo;
    private Date orderDate;
    private String customerNo;
    private String customerName;
    private String customerType;
    private String orderFrom;
    private String orderAmount;
    private String dDPToCustomerRealFee;
    private String dDPGetMerchantPayFee;
    private String serviceRateType;
    private String serviceFee;
    private String payGateWay;
    private String payType;
    private String payWay;
    private String payWayName;
    private String dDPSupplierRate;
    private String dDPToSupplierShouldAmount;
    private String dDPFromBankShouldFee;
    private String dDPFromBankRealFee;
    private String supplierName;
    private String tranOutStatus;
    private String tranInStatus;
    private String dDPBankRate;
    private String dDPToBankFee;
    
    
    public String getdDPBankRate() {
        return dDPBankRate+"‰";
    }
    public void setdDPBankRate(String dDPBankRate) {
        this.dDPBankRate = dDPBankRate;
    }
    public String getdDPToBankFee() {
        return String.format("%.2f", Double.valueOf(dDPToBankFee==null?"0.00":dDPToBankFee));
    }
    public void setdDPToBankFee(String dDPToBankFee) {
        this.dDPToBankFee = dDPToBankFee;
    }
    public String getPayWayName() {
        return payWayName;
    }
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
 
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getTranOutStatus() {
        return tranOutStatus;
    }
    public String getTranOutStatusView() {
        if (StringUtils.isBlank(tranOutStatus)) {
            return null;
        }       
        if(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus)==null){
            return null;
        }else{
            return ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus).getName();
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
        if(ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus)==null){
            return null;
        }else{
            return ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus) .getName();
        }
    }
    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
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
    public String getOrderAmount() {
        return String.format("%.2f", Double.valueOf(orderAmount==null?"0.00":orderAmount));
    }
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }
    public String getdDPToCustomerRealFee() {
        return String.format("%.2f", Double.valueOf(dDPToCustomerRealFee==null?"0.00":dDPToCustomerRealFee));
    }
    public void setdDPToCustomerRealFee(String dDPToCustomerRealFee) {
        this.dDPToCustomerRealFee = dDPToCustomerRealFee;
    }
    public String getdDPGetMerchantPayFee() {
        
        return String.format("%.2f", Double.valueOf(dDPGetMerchantPayFee==null?"0.00":dDPGetMerchantPayFee));
    }
    public void setdDPGetMerchantPayFee(String dDPGetMerchantPayFee) {
        this.dDPGetMerchantPayFee = dDPGetMerchantPayFee;
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
        
    }
    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
    public String getPayGateWay() {
        return payGateWay;
    }
    public String getPayGateWayView() {
        if (StringUtils.isBlank(payGateWay)) {
            return null;
        }       
        if(PayGatewayEnum.getPayGatewayEnumByCode(this.payGateWay)==null){
            return payGateWay;
        }else{
            return PayGatewayEnum.getPayGatewayEnumByCode(this.payGateWay).getName();
        }
    }
    public void setPayGateWay(String payGateWay) {
        this.payGateWay = payGateWay;
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
    public String getdDPSupplierRate() {
        return dDPSupplierRate+"‰";
    }
    public void setdDPSupplierRate(String dDPSupplierRate) {
        this.dDPSupplierRate = dDPSupplierRate;
    }
    public String getdDPToSupplierShouldAmount() {
        return String.format("%.2f", Double.valueOf(dDPToSupplierShouldAmount==null?"0.00":dDPToSupplierShouldAmount));
    }
    public void setdDPToSupplierShouldAmount(String dDPToSupplierShouldAmount) {
        this.dDPToSupplierShouldAmount = dDPToSupplierShouldAmount;
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
}
