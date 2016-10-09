package com.dodopal.oss.business.bean;

import java.io.Serializable;


/**
 * 通卡公司额度购买财务报表导出用DTO
 * 
 */
public class YktLimitBatchReportForExport implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // 通卡公司编号
    private String yktCode;
    
    // 通卡公司名称
    private String yktName;
    
    // 财务打款额度
    private String financialPayAmt;

    // 付款渠道
    private String paymentChannel;
    
    // 通卡增加额度
    private String yktAddLimit;
    
    // 通卡加款日期
    private String yktAddLimitDate;

    // 通卡未增加额度
    private String yktUnaddLimit;
    
    // 财务打款日期
    private String financialPayDate; 
    
    // 财务打款手续费
    private String financialPlayFee;

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getFinancialPayAmt() {
        return financialPayAmt;
    }

    public void setFinancialPayAmt(String financialPayAmt) {
        this.financialPayAmt = financialPayAmt;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getYktAddLimit() {
        return yktAddLimit;
    }

    public void setYktAddLimit(String yktAddLimit) {
        this.yktAddLimit = yktAddLimit;
    }

    public String getYktAddLimitDate() {
        return yktAddLimitDate;
    }

    public void setYktAddLimitDate(String yktAddLimitDate) {
        this.yktAddLimitDate = yktAddLimitDate;
    }

    public String getYktUnaddLimit() {
        return yktUnaddLimit;
    }

    public void setYktUnaddLimit(String yktUnaddLimit) {
        this.yktUnaddLimit = yktUnaddLimit;
    }

    public String getFinancialPayDate() {
        return financialPayDate;
    }

    public void setFinancialPayDate(String financialPayDate) {
        this.financialPayDate = financialPayDate;
    }

    public String getFinancialPlayFee() {
        return financialPlayFee;
    }

    public void setFinancialPlayFee(String financialPlayFee) {
        this.financialPlayFee = financialPlayFee;
    } 
    
}
