package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

public class AccountMainInfoBean extends BaseEntity {

    private static final long serialVersionUID = 7802250150240436694L;
    /*
     * 主账户id
     */
    private String acid;
    /*
     * 主账户号
     */
    private String accountCode;
    /*
     * 客户号
     */
    private String customerNo;
    /*
     * 客户名称
     */
    private String cuName;
    /*
     * 账户类别
     */
    private String fundType;
    /*
     * 账户类别展示
     */
    private String fundTypeView;
    /*
     * 累计总金额
     */
    private String sumTotalAmount;
    /*
     * 总余额
     */
    private String totalBalance;
    /*
     * 可用金额
     */
    private String availableBalance;
    /*
     * 冻结金额
     */
    private String frozenAmount;
    /*
     * 状态
     */
    private String state;

    /*
     * 状态view
     */
    private String stateView;

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(String sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateView() {
        return stateView;
    }

    public void setStateView(String stateView) {
        this.stateView = stateView;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFundTypeView() {
        return fundTypeView;
    }

    public void setFundTypeView(String fundTypeView) {
        this.fundTypeView = fundTypeView;
    }

}
