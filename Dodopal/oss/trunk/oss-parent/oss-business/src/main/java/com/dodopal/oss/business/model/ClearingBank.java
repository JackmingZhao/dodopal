package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.model.PrdYktInfo;
import com.dodopal.common.service.PrdYktInfoService;
import com.dodopal.common.util.SpringBeanUtil;

/**
 * 支付网关分类清算表CLEARING_BANK对应实体类
 * @author dodopal
 *
 */
public class ClearingBank extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6740390873291609172L;
    
    /**
     * 清算日期
     */
    private String clearingDay;
    
    /**
     * 清算时间
     */
    private Date clearingDate;
    
    /**
     * 支付网关
     */
    private String payGateway;
    
    /**
     * 支付网关界面显示
     */
    @SuppressWarnings("unused")
    private String payGatewayView;
    
    /**
     * 交易笔数
     */
    private long tradeCount;
    
    /**
     * 交易金额(单位：元，数据库为分)
     */
    private double tradeAmount;
    
    /**
     * 银行手续费(单位：元，数据库为分)
     */
    private double bankFee;
    
    /**
     * 转账金额(单位：元，数据库为分)
     */
    private double transferAmount;

    public String getClearingDay() {
        return clearingDay;
    }
    
    public String getClearingDayStr() {
    	String yyyy = this.clearingDay.substring(0, 3);
    	String MM = this.clearingDay.substring(4, 5);
    	String dd = this.clearingDay.substring(6, 7);
    	return yyyy + "-" + MM + "-" + dd;
    }

    public void setClearingDay(String clearingDay) {
        this.clearingDay = clearingDay;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    
    public long getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public double getBankFee() {
        return bankFee;
    }

    public void setBankFee(double bankFee) {
        this.bankFee = bankFee;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getPayGatewayView() {
        String payGatewayView = "";
        if (!StringUtils.isBlank(this.payGateway)) {
            PayGatewayEnum payGatewayEnum = PayGatewayEnum.getPayGatewayEnumByCode(this.payGateway);
            if (payGatewayEnum == null) {
                PrdYktInfoService yktInfoService = (PrdYktInfoService) SpringBeanUtil.getBean("prdYktInfoService");
                PrdYktInfo  yktInfo = yktInfoService.findPrdYktInfoYktCode(this.payGateway);
                if (yktInfo != null) {
                    payGatewayView = yktInfo.getYktName();
                }
            } else {
                payGatewayView = payGatewayEnum.getName();
            }
        }
        return payGatewayView;
    }

    public void setPayGatewayView(String payGatewayView) {
        this.payGatewayView = payGatewayView;
    }



}
