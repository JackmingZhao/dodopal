package com.dodopal.oss.business.model;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.util.DateUtils;

/**
 * 5.4 一卡通充值 对应表：CLEARING_BASIC_DATA(清分基础数据表)、PRD_ORDER(产品库公交卡充值订单信息表)
 * @author Dave
 */

public class CardRecharge extends BaseEntity {

    private static final long serialVersionUID = -1194570368562830879L;
    /* 订单号  */
    private String orderNo;
    /* 订单时间  */
    private String orderDate;
    /* 商户编号 */
    private String customerNo;
    /* 商户名称 */
    private String customerName;
    /* 商户类型 */
    private String customerType;
    /* 来源（订单来源）  */
    private String orderFrom;
    /* 订单金额 */
    private String orderAmount;
    /* 商户充值费率类型 */
    private String customerRateType;
    /* 商户充值费率 */
    private String customerRate;
    /* 商户实际支付金额  */
    private String custoerRealPayAmount;
    /* 商户应得分润  */
    private String customerShouldProfit;
    /* 服务费（应收商户支付方式服务费） */
    private String ddpGetMerchantPayFee;
    /* 商户服务费率类型  */
    private String serviceRateType;
    /* 商户服务费率 */
    private String serviceRate;
    /* 支付网关 */
    private String payGateway;
    /* 支付类型  */
    private String payType;
    /* 支付方式 */
    private String payWayName;
    /* 充值费率（DDP与供应商之间的费率） */
    private String ddpSupplierRate;
    /* 应收返点（供应商应支付DDP返点） */
    private String supplierToDdpShouldRebate;
    /* 实收返点（供应商实际支付DDP返点） */
    private String supplierToDdpRealRebate;
    /* 银行手续费率（DDP与银行的手续费率） */
    private String ddpBankRate;
    /* 银行手续费（DDP支付给银行的手续费） */
    private String ddpToBankFee;
    /* 通卡名称（供应商） */
    private String supplierName;
    /* 订单状态:
         0：待付款；
         1：已付款；
         2：充值失败；
         3：充值中；
         4：充值成功；
         5：充值可疑 
     */
    private String proOrderStates;
    /* 订单内部状态:
        00：订单创建成功；
        10：账户充值成功；
        11：账户充值失败；
        12：网银支付成功；
        20：资金冻结失败；
        21：申请充值密钥失败；
        22：卡片充值失败；
        23：资金解冻成功；
        24：资金解冻失败；
        25：网银支付失败；
        30：资金冻结成功；
        31：申请充值密钥成功；
        40：卡片充值成功；
        41：资金扣款成功；
        42：资金扣款失败；
        50：上传充值未知
     */
    private String proInnerStates;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
    	if(StringUtils.isBlank(orderDate)){
    		return "";
    	}else{
    		return DateUtils.dateToString(DateUtils.stringtoDate(orderDate, DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR);
    	}
    }

    public void setOrderDate(String orderDate) {
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
        if (StringUtils.isBlank(this.customerType)) {
            return "";
        } else if (MerTypeEnum.getMerTypeByCode(this.customerType) == null) {
            return "";
        } else {
            return MerTypeEnum.getMerTypeByCode(this.customerType).getName();
        }
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getOrderFrom() {
        return customerType;
    }

    public String getOrderFromView() {
        if (StringUtils.isBlank(this.orderFrom)) {
            return "";
        } else if (SourceEnum.getSourceByCode(this.orderFrom) == null) {
            return "";
        } else {
            return SourceEnum.getSourceByCode(this.orderFrom).getName();
        }
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderAmount() {
        return orderAmount;
    }
    public String getOrderAmountView() {
        return df(Double.valueOf(orderAmount) / 100)+" 元" ;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCustomerRateType() {
        return customerRateType;
    }

    public String getCustomerRateTypeView() {
        if (StringUtils.isBlank(this.customerRateType)) {
            return "";
        } else if (RateTypeEnum.getRateTypeByCode(this.customerRateType) == null) {
            return "";
        } else {
            return RateTypeEnum.getRateTypeByCode(this.customerRateType).getName();
        }
    }

    public void setCustomerRateType(String customerRateType) {
        this.customerRateType = customerRateType;
    }

    public String getCustomerRate() {
        return customerRate;
    }

    public String getCustomerRateView() {
       if("千分比(‰)".equals(getCustomerRateTypeView())){
           if(StringUtils.isBlank(this.customerRate)){
               return "0 ‰";
           }else if(this.customerRate.startsWith(".")){
               return "0"+customerRate+" ‰";
           }else{
               return customerRate+" ‰";
           }
       }else if("单笔返点金额(元)".equals(getCustomerRateTypeView())){
           if(StringUtils.isBlank(this.customerRate) || "0".equals(this.customerRate)){
               return "0.00 元";
           }else{
               return df((Double.valueOf(this.customerRate)) / 100)+" 元";
           }
       }else{
           return customerRate;
       }
    }

    public void setCustomerRate(String customerRate) {
        this.customerRate = customerRate;
    }

    public String getCustoerRealPayAmount() {
        return custoerRealPayAmount;
    }
    public String getCustoerRealPayAmountView() {
        if(StringUtils.isBlank(this.custoerRealPayAmount) || "0".equals(this.custoerRealPayAmount)){
            return "0.00 元";
        }else{
            return df(Double.valueOf(custoerRealPayAmount) / 100) +" 元";
        }
    }

    public void setCustoerRealPayAmount(String custoerRealPayAmount) {
        this.custoerRealPayAmount = custoerRealPayAmount;
    }

    public String getCustomerShouldProfit() {
        return customerShouldProfit;
    }
    public String getCustomerShouldProfitView() {
        if(StringUtils.isBlank(this.customerShouldProfit) || "0".equals(this.customerShouldProfit)){
            return "0.00 元";
        }else{
            return df((Double.valueOf(this.customerShouldProfit)) / 100)+" 元";
        }
    }

    public void setCustomerShouldProfit(String customerShouldProfit) {
        this.customerShouldProfit = customerShouldProfit;
    }

    public String getDdpGetMerchantPayFee() {
        return ddpGetMerchantPayFee;
    }
    
    public String getDdpGetMerchantPayFeeView() {
        if(StringUtils.isBlank(this.ddpGetMerchantPayFee) || "0".equals(this.ddpGetMerchantPayFee)){
            return "0.00 元";
        }else{
            return df(Double.valueOf(this.ddpGetMerchantPayFee) /100) +" 元";
        }
    }

    public void setDdpGetMerchantPayFee(String ddpGetMerchantPayFee) {
        this.ddpGetMerchantPayFee = ddpGetMerchantPayFee;
    }

    public String getServiceRateType() {
        return serviceRateType;
    }

    public String getServiceRateTypeView() {
        if (StringUtils.isBlank(this.serviceRateType)) {
            return "";
        } else if (ServiceRateTypeEnum.getServiceRateTypeByCode(this.serviceRateType) == null) {
            return "";
        } else {
            return ServiceRateTypeEnum.getServiceRateTypeByCode(this.serviceRateType).getName();
        }
    }

    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }
    
    public String getServiceRate() {
        return serviceRate; 
    }
    
    public String getServiceRateView() {
        if("费率（千分比）".equals(getServiceRateTypeView())){
            if(StringUtils.isBlank(this.serviceRate)){
                return "0 ‰";
            }else if(this.serviceRate.startsWith(".")){
                return "0"+serviceRate+" ‰";
            }else{
                return serviceRate+" ‰";
            }
        }else if("单笔（元）".equals(getServiceRateTypeView())){
            if(StringUtils.isBlank(this.serviceRate) || "0".equals(this.serviceRate)){
                return "0.00 元";
            }else{
                return df((Double.valueOf(this.serviceRate)) / 100)+" 元";
            }
        }else{
            return serviceRate; 
        }
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public String getPayGatewayView() {
        if (StringUtils.isBlank(this.payGateway)) {
            return "";
        } else if (PayGatewayEnum.getPayGatewayEnumByCode(this.payGateway) == null) {
            return "";
        } else {
            return PayGatewayEnum.getPayGatewayEnumByCode(this.payGateway).getName();
        }
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getPayType() {
        return payType;
    }

    public String getPayTypeView() {
        if (StringUtils.isBlank(this.payType)) {
            return "";
        } else if (PayTypeEnum.getPayTypeEnumByCode(this.payType) == null) {
            return "";
        } else {
            return PayTypeEnum.getPayTypeEnumByCode(this.payType).getName();
        }
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getDdpSupplierRate() {
        return ddpSupplierRate;
    }
    
    public String getDdpSupplierRateView() {
        if (StringUtils.isBlank(this.ddpSupplierRate) || "0".equals(this.ddpSupplierRate)) {
            return "0 ‰";
        } else {
            if(this.ddpSupplierRate.startsWith(".")){
                return "0"+ddpSupplierRate+" ‰";
            }else{
                return ddpSupplierRate+" ‰";
            }
        }
    }

    public void setDdpSupplierRate(String ddpSupplierRate) {
        this.ddpSupplierRate = ddpSupplierRate;
    }

    public String getSupplierToDdpShouldRebate() {
        return supplierToDdpShouldRebate;
    }
    
    public String getSupplierToDdpShouldRebateView() {
        if(StringUtils.isBlank(this.supplierToDdpShouldRebate) ||"0".equals(this.supplierToDdpShouldRebate)){
            return "0.00 元";
        }else{
            return df((Double.valueOf(this.supplierToDdpShouldRebate)) / 100)+" 元";
        }
    }
    
    public void setSupplierToDdpShouldRebate(String supplierToDdpShouldRebate) {
        this.supplierToDdpShouldRebate = supplierToDdpShouldRebate;
    }

    public String getSupplierToDdpRealRebate() {
        return supplierToDdpRealRebate;
    }
    public String getSupplierToDdpRealRebateView() {
        if(StringUtils.isBlank(this.supplierToDdpRealRebate) ||"0".equals(this.supplierToDdpRealRebate)){
            return "0.00 元";
        }else{
            return df((Double.valueOf(this.supplierToDdpRealRebate)) / 100)+" 元";
        }
    }

    public void setSupplierToDdpRealRebate(String supplierToDdpRealRebate) {
        this.supplierToDdpRealRebate = supplierToDdpRealRebate;
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
    
    public String getDdpToBankFeeView() {
        if(StringUtils.isBlank(this.ddpToBankFee) || "0".equals(this.ddpToBankFee)){
            return "0.00 元";
        }else{
            return df((Double.valueOf(ddpToBankFee) / 100))+" 元" ;
        }
        
    }
    
    public void setDdpToBankFee(String ddpToBankFee) {
        this.ddpToBankFee = ddpToBankFee;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProOrderStates() {
        return proOrderStates;
    }

    public String getProOrderStatesView() {
        if (StringUtils.isBlank(this.proOrderStates)) {
            return "";
        } else if (RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(this.proOrderStates) == null) {
            return "";
        } else {
            return RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(this.proOrderStates).getName();
        }
    }

    public void setProOrderStates(String proOrderStates) {
        this.proOrderStates = proOrderStates;
    }

    public String getProInnerStates() {
        return proInnerStates;
    }

    public String getProInnerStatesView() {
        if (StringUtils.isBlank(this.proInnerStates)) {
            return "";
        } else if (RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(this.proInnerStates) == null) {
            return "";
        } else {
            return RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(this.proInnerStates).getName();
        }
    }

    public void setProInnerStates(String proInnerStates) {
        this.proInnerStates = proInnerStates;
    }

    private static String df(double dou){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(dou);
    }
    
//    public static String delZero(String src) {
//        if (src.endsWith("0"))
//            return delZero(src.substring(0, src.length() - 1));
//        else
//            return src;
//    }
}
