package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class AccountAdjustmentQuery extends QueryBase {

    /**
     * 
     */
    private static final long serialVersionUID = 5542027624792738297L;

    //    客户号 文本框 模糊查询
    private String customerNo;
    //    客户名称    文本框 模糊查询
    private String customerName;
    //    申请时间段   日历控件    
    private Date accountAdjustApplyStartDate;
    
    private Date accountAdjustApplyEndDate;
    //    审核时间    日历控件    
    private Date accountAdjustAuditStartDate;
    
    private Date accountAdjustAuditEndDate;
    //    状态  下拉框 默认全部
    private String accountAdjustState;
    //    账户类型    下拉框 默认全部
    private String fundType;
    //    调账类型    下拉框 默认全部
    private String accountAdjustType;
    //审核人
    private String accountAdjustAuditUser;
    //申请人
    private String accountAdjustApplyUser;
    
    public String getAccountAdjustApplyUser() {
		return accountAdjustApplyUser;
	}

	public void setAccountAdjustApplyUser(String accountAdjustApplyUser) {
		this.accountAdjustApplyUser = accountAdjustApplyUser;
	}

	public String getAccountAdjustAuditUser() {
		return accountAdjustAuditUser;
	}

	public void setAccountAdjustAuditUser(String accountAdjustAuditUser) {
		this.accountAdjustAuditUser = accountAdjustAuditUser;
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

    public Date getAccountAdjustApplyStartDate() {
        return accountAdjustApplyStartDate;
    }

    public void setAccountAdjustApplyStartDate(Date accountAdjustApplyStartDate) {
        this.accountAdjustApplyStartDate = accountAdjustApplyStartDate;
    }

    public Date getAccountAdjustApplyEndDate() {
        return accountAdjustApplyEndDate;
    }

    public void setAccountAdjustApplyEndDate(Date accountAdjustApplyEndDate) {
        this.accountAdjustApplyEndDate = accountAdjustApplyEndDate;
    }

    public Date getAccountAdjustAuditStartDate() {
        return accountAdjustAuditStartDate;
    }

    public void setAccountAdjustAuditStartDate(Date accountAdjustAuditStartDate) {
        this.accountAdjustAuditStartDate = accountAdjustAuditStartDate;
    }

    public Date getAccountAdjustAuditEndDate() {
        return accountAdjustAuditEndDate;
    }

    public void setAccountAdjustAuditEndDate(Date accountAdjustAuditEndDate) {
        this.accountAdjustAuditEndDate = accountAdjustAuditEndDate;
    }

    public String getAccountAdjustState() {
        return accountAdjustState;
    }

    public void setAccountAdjustState(String accountAdjustState) {
        this.accountAdjustState = accountAdjustState;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAccountAdjustType() {
        return accountAdjustType;
    }

    public void setAccountAdjustType(String accountAdjustType) {
        this.accountAdjustType = accountAdjustType;
    }

}
