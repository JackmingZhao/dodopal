package com.dodopal.oss.business.bean;



/**
 *  额度购买批次申请单信息
 * @author 
 *
 */
public class ProductYktLimitBatchInfoForExport {

    // 通卡公司编号
    private String yktCode;
    
    // 通卡公司名称
    private String yktName;
    
    // 预购申请批次
    private String batchCode;

    // 预购申请额度
    private String applyAmtLimit;
    
    // 申请人姓名
    private String applyUserName;
    
    // 申请日期
    private String applyDate;

    // 预购申请说明
    private String applyExplaination;
    
    // 财务打款额度
    private String financialPayAmt; 
    
    // 财务打款日期
    private String financialPlayDate; 
    
    // 财务打款手续费
    private String financialPlayFee; 
    
    // 付款渠道
    private String paymentChannel;
    
    // 审核人姓名
    private String auditUserName;
    
    // 审核日期
    private String auditDate;

    // 审核说明
    private String auditExplaination;
    
    // 审核状态：0：未审核；1：审核通过；2：审核拒绝
    private String auditState;
    
    // 通卡实际增加额度
    private String yktAddLimit; 
    
    // 通卡加款日期
    private String yktAddLimitDate; 

    // 复核人姓名
    private String checkUserName;
    
    // 复核日期
    private String checkDate;

    // 复核说明
    private String checkExplaination;
    
    // 复核状态：0：未复核；1：复核中；2：复核完了
    private String checkState;
    
    // 备注
    private String remark;
    
    private String createDate;

    private String updateDate;

    private String createUser;

    private String updateUser;  
    
    public String getFinancialPayAmt() {
        return financialPayAmt;
    }

    public void setFinancialPayAmt(String financialPayAmt) {
        this.financialPayAmt = financialPayAmt;
    }

    public String getYktAddLimitDate() {
        return yktAddLimitDate;
    }

    public void setYktAddLimitDate(String yktAddLimitDate) {
        this.yktAddLimitDate = yktAddLimitDate;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getApplyAmtLimit() {
        return applyAmtLimit;
    }

    public void setApplyAmtLimit(String applyAmtLimit) {
        this.applyAmtLimit = applyAmtLimit;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyExplaination() {
        return applyExplaination;
    }

    public void setApplyExplaination(String applyExplaination) {
        this.applyExplaination = applyExplaination;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
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

    public String getYktAddLimit() {
        return yktAddLimit;
    }

    public void setYktAddLimit(String yktAddLimit) {
        this.yktAddLimit = yktAddLimit;
    }

    public String getFinancialPlayDate() {
        return financialPlayDate;
    }

    public void setFinancialPlayDate(String financialPlayDate) {
        this.financialPlayDate = financialPlayDate;
    }

    public String getFinancialPlayFee() {
        return financialPlayFee;
    }

    public void setFinancialPlayFee(String financialPlayFee) {
        this.financialPlayFee = financialPlayFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    
    
}
