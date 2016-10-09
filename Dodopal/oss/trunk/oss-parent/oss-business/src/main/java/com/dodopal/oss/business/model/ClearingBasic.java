package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class ClearingBasic extends BaseEntity {

    private static final long serialVersionUID = -7749490978440117991L;

    //订单交易号
    private String orderNo;
    //订单时间
    private String orderDate;
    //订单日期
    private String orderDay;
    //商户编号
    private String customerNo;
    //商户名称
    private String customerName;
    //商户类型
    private String customerType;
    //业务类型
    private String businessType;
    //商户业务费率类型
    private String customerRateType;
    //商户业务费率
    private String customerRate;
    //订单金额
    private String orderAmount;
    //商户实际支付金额
    private String custoerRealPayAmount;
    //商户应得分润
    private String customerShouldProfit;
    //商户实际分润
    private String customerRealProfit;
    //商户账户应加值
    private String customerAccountShouldAmount;
    //商户账户实际加值
    private String customerAccountRealAmount;
    //与客户清分状态
    private String customerClearingFlag;
    //与客户清分时间
    private String customerClearingTime;
    //与客户结算状态
    private String customerSettlementFlag;
    //与客户结算时间
    private String customerSettlementTime;
    //DDP应收商户支付方式服务费
    private String ddpGetMerchantPayFee;
    //DDP实际转给商户业务费用
    private String ddpToCustomerRealFee;
    //下级商户编号
    private String subMerchantCode;
    //下级商户名称
    private String subMerchantName;
    //下级商户应得分润
    private String subMerchantShouldProfit;
    //支付网关
    private String payGateway;
    //支付类型
    private String payType;
    //支付方式
    private String payWay;
    //支付方式名称
    private String payWayName;
    //服务费率类型
    private String serviceRateType;
    //服务费率
    private String serviceRate;
    //DDP与银行的手续费率
    private String ddpBankRate;
    //DDP支付给银行的手续费
    private String ddpToBankFee;
    //DDP从银行应收业务费用
    private String ddpFromBankShouldFee;
    //DDP从银行实收业务费用
    private String ddpFromBankRealFee;
    //与银行清分状态
    private String bankClearingFlag;
    //与银行清分时间
    private String bankClearingTime;
    //与银行结算状态
    private String bankSettlementFlag;
    //与银行结算时间
    private String banksettlementTime;
    //供应商
    private String supplierCode;
    //供应商名称
    private String supplierName;
    //城市编码
    private String cityCode;
    //城市名称
    private String cityName;
    //DDP应支付供应商本金
    private String ddpToSupplierShouldAmount;
    //DDP实际支付供应商本金
    private String ddpToSupplierRealAmount;
    //DDP与供应商之间的费率
    private String ddpSupplierRate;
    //供应商应支付DDP返点
    private String supplierToDdpShouldRebate;
    //供应商实际支付DDP返点
    private String supplierToDdpRealRebate;
    //与供应商清分状态
    private String supplierClearingFlag;
    //与供应商清分时间
    private String supplierClearingTime;
    //与供应商结算状态
    private String supplierSettlementFlag;
    //与供应商结算时间
    private String supplierSettlementTime;
    //订单来源
    private String orderFrom;
    //是否圈存订单
    private String orderCircle;
    //上级商户分润计算状态
    private String topMerchantProfitFlag;

    
    
    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCustomerRateType() {
        return customerRateType;
    }

    public void setCustomerRateType(String customerRateType) {
        this.customerRateType = customerRateType;
    }

    public String getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(String customerRate) {
        this.customerRate = customerRate;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCustoerRealPayAmount() {
        return custoerRealPayAmount;
    }

    public void setCustoerRealPayAmount(String custoerRealPayAmount) {
        this.custoerRealPayAmount = custoerRealPayAmount;
    }

    public String getCustomerShouldProfit() {
        return customerShouldProfit;
    }

    public void setCustomerShouldProfit(String customerShouldProfit) {
        this.customerShouldProfit = customerShouldProfit;
    }

    public String getCustomerRealProfit() {
        return customerRealProfit;
    }

    public void setCustomerRealProfit(String customerRealProfit) {
        this.customerRealProfit = customerRealProfit;
    }

    public String getCustomerAccountShouldAmount() {
        return customerAccountShouldAmount;
    }

    public void setCustomerAccountShouldAmount(String customerAccountShouldAmount) {
        this.customerAccountShouldAmount = customerAccountShouldAmount;
    }

    public String getCustomerAccountRealAmount() {
        return customerAccountRealAmount;
    }

    public void setCustomerAccountRealAmount(String customerAccountRealAmount) {
        this.customerAccountRealAmount = customerAccountRealAmount;
    }

    public String getCustomerClearingFlag() {
        return customerClearingFlag;
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

    public String getDdpGetMerchantPayFee() {
        return ddpGetMerchantPayFee;
    }

    public void setDdpGetMerchantPayFee(String ddpGetMerchantPayFee) {
        this.ddpGetMerchantPayFee = ddpGetMerchantPayFee;
    }

    public String getDdpToCustomerRealFee() {
        return ddpToCustomerRealFee;
    }

    public void setDdpToCustomerRealFee(String ddpToCustomerRealFee) {
        this.ddpToCustomerRealFee = ddpToCustomerRealFee;
    }

    public String getSubMerchantCode() {
        return subMerchantCode;
    }

    public void setSubMerchantCode(String subMerchantCode) {
        this.subMerchantCode = subMerchantCode;
    }

    public String getSubMerchantName() {
        return subMerchantName;
    }

    public void setSubMerchantName(String subMerchantName) {
        this.subMerchantName = subMerchantName;
    }

    public String getSubMerchantShouldProfit() {
        return subMerchantShouldProfit;
    }

    public void setSubMerchantShouldProfit(String subMerchantShouldProfit) {
        this.subMerchantShouldProfit = subMerchantShouldProfit;
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

    public String getDdpBankRate() {
        return ddpBankRate;
    }

    public void setDdpBankRate(String ddpBankRate) {
        this.ddpBankRate = ddpBankRate;
    }

    public String getDdpToBankFee() {
        return ddpToBankFee;
    }

    public void setDdpToBankFee(String ddpToBankFee) {
        this.ddpToBankFee = ddpToBankFee;
    }

    public String getDdpFromBankShouldFee() {
        return ddpFromBankShouldFee;
    }

    public void setDdpFromBankShouldFee(String ddpFromBankShouldFee) {
        this.ddpFromBankShouldFee = ddpFromBankShouldFee;
    }

    public String getDdpFromBankRealFee() {
        return ddpFromBankRealFee;
    }

    public void setDdpFromBankRealFee(String ddpFromBankRealFee) {
        this.ddpFromBankRealFee = ddpFromBankRealFee;
    }

    public String getBankClearingFlag() {
        return bankClearingFlag;
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

    public String getBankSettlementFlag() {
        return bankSettlementFlag;
    }

    public void setBankSettlementFlag(String bankSettlementFlag) {
        this.bankSettlementFlag = bankSettlementFlag;
    }

    public String getBanksettlementTime() {
        return banksettlementTime;
    }

    public void setBanksettlementTime(String banksettlementTime) {
        this.banksettlementTime = banksettlementTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDdpToSupplierShouldAmount() {
        return ddpToSupplierShouldAmount;
    }

    public void setDdpToSupplierShouldAmount(String ddpToSupplierShouldAmount) {
        this.ddpToSupplierShouldAmount = ddpToSupplierShouldAmount;
    }

    public String getDdpToSupplierRealAmount() {
        return ddpToSupplierRealAmount;
    }

    public void setDdpToSupplierRealAmount(String ddpToSupplierRealAmount) {
        this.ddpToSupplierRealAmount = ddpToSupplierRealAmount;
    }

    public String getDdpSupplierRate() {
        return ddpSupplierRate;
    }

    public void setDdpSupplierRate(String ddpSupplierRate) {
        this.ddpSupplierRate = ddpSupplierRate;
    }

    public String getSupplierToDdpShouldRebate() {
        return supplierToDdpShouldRebate;
    }

    public void setSupplierToDdpShouldRebate(String supplierToDdpShouldRebate) {
        this.supplierToDdpShouldRebate = supplierToDdpShouldRebate;
    }

    public String getSupplierToDdpRealRebate() {
        return supplierToDdpRealRebate;
    }

    public void setSupplierToDdpRealRebate(String supplierToDdpRealRebate) {
        this.supplierToDdpRealRebate = supplierToDdpRealRebate;
    }

    public String getSupplierClearingFlag() {
        return supplierClearingFlag;
    }

    public void setSupplierClearingFlag(String supplierClearingFlag) {
        this.supplierClearingFlag = supplierClearingFlag;
    }

    public String getSupplierClearingTime() {
        return supplierClearingTime;
    }

    public void setSupplierClearingTime(String supplierClearingTime) {
        this.supplierClearingTime = supplierClearingTime;
    }

    public String getSupplierSettlementFlag() {
        return supplierSettlementFlag;
    }

    public void setSupplierSettlementFlag(String supplierSettlementFlag) {
        this.supplierSettlementFlag = supplierSettlementFlag;
    }

    public String getSupplierSettlementTime() {
        return supplierSettlementTime;
    }

    public void setSupplierSettlementTime(String supplierSettlementTime) {
        this.supplierSettlementTime = supplierSettlementTime;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderCircle() {
        return orderCircle;
    }

    public void setOrderCircle(String orderCircle) {
        this.orderCircle = orderCircle;
    }

    public String getTopMerchantProfitFlag() {
        return topMerchantProfitFlag;
    }

    public void setTopMerchantProfitFlag(String topMerchantProfitFlag) {
        this.topMerchantProfitFlag = topMerchantProfitFlag;
    }

}
