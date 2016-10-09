package com.dodopal.api.product.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class ProductYktLimitBatchInfoDTO extends BaseEntity {

    private static final long serialVersionUID = 3669852915446368405L;


    // 通卡公司编号
    private String yktCode;
    
    // 通卡公司名称
    private String yktName;
    
    // 购买批次
    private Integer batchCode;

    // 申请额度
    private Long applyAmtLimit;

    // 申请人
    private String applyUser;
    
    // 申请人姓名
    private String applyUserName;
    
    // 申请日期
    private Date applyDate;

    // 预购申请说明
    private String applyExplaination;
    
    // 财务打款额度
    private Long financialPayAmt; 
    
    // 财务打款手续费
    private Long financialPayFee; 
    
    // 财务打款日期
    private Date financialPayDate; 

    // 付款渠道
    private String paymentChannel;
    
    // 审核人
    private String auditUser;
    
    // 审核人姓名
    private String auditUserName;
    
    // 审核日期
    private Date auditDate;

    // 审核说明
    private String auditExplaination;
    
    // 审核状态：0：未审核；1：审核通过；2：审核拒绝
    private String auditState;
    
    // 通卡实际增加额度
    private Long yktAddLimit; 
    
    // 通卡加款日期
    private Date yktAddLimitDate; 
    
    // 复核人
    private String checkUser;
    
    // 复核人姓名
    private String checkUserName;
    
    // 复核日期
    private Date checkDate;

    // 复核说明
    private String checkExplaination;
    
    // 复核状态：0：未复核；1：复核中；2：复核完了
    private String checkState;
   
    // 备注
    private String remark;

    // 通卡已加额度
    private Long oldYktAddLimit; 
    
    // 通卡新加额度
    private Long newYktAddLimit;
    
    public Long getOldYktAddLimit() {
        return oldYktAddLimit;
    }

    public void setOldYktAddLimit(Long oldYktAddLimit) {
        this.oldYktAddLimit = oldYktAddLimit;
    }

    public Long getNewYktAddLimit() {
        return newYktAddLimit;
    }

    public void setNewYktAddLimit(Long newYktAddLimit) {
        this.newYktAddLimit = newYktAddLimit;
    }

    public Long getFinancialPayAmt() {
        return financialPayAmt;
    }

    public void setFinancialPayAmt(Long financialPayAmt) {
        this.financialPayAmt = financialPayAmt;
    }

    public Long getFinancialPayFee() {
        return financialPayFee;
    }

    public void setFinancialPayFee(Long financialPayFee) {
        this.financialPayFee = financialPayFee;
    }

    public Date getFinancialPayDate() {
        return financialPayDate;
    }

    public void setFinancialPayDate(Date financialPayDate) {
        this.financialPayDate = financialPayDate;
    }

    public Date getYktAddLimitDate() {
        return yktAddLimitDate;
    }

    public void setYktAddLimitDate(Date yktAddLimitDate) {
        this.yktAddLimitDate = yktAddLimitDate;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckExplaination() {
        return checkExplaination;
    }

    public void setCheckExplaination(String checkExplaination) {
        this.checkExplaination = checkExplaination;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Integer getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(Integer batchCode) {
        this.batchCode = batchCode;
    }

    public Long getApplyAmtLimit() {
        return applyAmtLimit;
    }

    public void setApplyAmtLimit(Long applyAmtLimit) {
        this.applyAmtLimit = applyAmtLimit;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyExplaination() {
        return applyExplaination;
    }

    public void setApplyExplaination(String applyExplaination) {
        this.applyExplaination = applyExplaination;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditExplaination() {
        return auditExplaination;
    }

    public void setAuditExplaination(String auditExplaination) {
        this.auditExplaination = auditExplaination;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Long getYktAddLimit() {
        return yktAddLimit;
    }

    public void setYktAddLimit(Long yktAddLimit) {
        this.yktAddLimit = yktAddLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
