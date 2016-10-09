package com.dodopal.product.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ProductYktLimitBatchInfoQuery extends QueryBase {

	private static final long serialVersionUID = -3330346763699042960L;

    private String yktName;
    private Date applyDateStart;
    private Date applyDateEnd;
    private Date auditDateStart;
    private Date auditDateEnd;
    private Date checkDateStart;
    private Date checkDateEnd;
    private String financialPayAmtStart;
    private String financialPayAmtEnd;
    private String yktAddLimitStart;
    private String yktAddLimitEnd;
    private String applyAmtStart;
    private String applyAmtEnd;
    private String applyUserName;
    private String auditUserName;
    private String checkUserName;
    private String auditState;
    private String checkState;
    
    public Date getCheckDateStart() {
        return checkDateStart;
    }

    public void setCheckDateStart(Date checkDateStart) {
        this.checkDateStart = checkDateStart;
    }

    public Date getCheckDateEnd() {
        return checkDateEnd;
    }

    public void setCheckDateEnd(Date checkDateEnd) {
        this.checkDateEnd = checkDateEnd;
    }

    public String getFinancialPayAmtStart() {
        return financialPayAmtStart;
    }

    public void setFinancialPayAmtStart(String financialPayAmtStart) {
        this.financialPayAmtStart = financialPayAmtStart;
    }

    public String getFinancialPayAmtEnd() {
        return financialPayAmtEnd;
    }

    public void setFinancialPayAmtEnd(String financialPayAmtEnd) {
        this.financialPayAmtEnd = financialPayAmtEnd;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Date getApplyDateStart() {
        return applyDateStart;
    }

    public String getApplyAmtStart() {
        return applyAmtStart;
    }

    public void setApplyAmtStart(String applyAmtStart) {
        this.applyAmtStart = applyAmtStart;
    }

    public String getApplyAmtEnd() {
        return applyAmtEnd;
    }

    public void setApplyAmtEnd(String applyAmtEnd) {
        this.applyAmtEnd = applyAmtEnd;
    }

    public String getYktAddLimitStart() {
        return yktAddLimitStart;
    }

    public void setYktAddLimitStart(String yktAddLimitStart) {
        this.yktAddLimitStart = yktAddLimitStart;
    }

    public String getYktAddLimitEnd() {
        return yktAddLimitEnd;
    }

    public void setYktAddLimitEnd(String yktAddLimitEnd) {
        this.yktAddLimitEnd = yktAddLimitEnd;
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

    public void setApplyDateStart(Date applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public Date getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(Date applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }

    public Date getAuditDateStart() {
        return auditDateStart;
    }

    public void setAuditDateStart(Date auditDateStart) {
        this.auditDateStart = auditDateStart;
    }

    public Date getAuditDateEnd() {
        return auditDateEnd;
    }

    public void setAuditDateEnd(Date auditDateEnd) {
        this.auditDateEnd = auditDateEnd;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }


}
