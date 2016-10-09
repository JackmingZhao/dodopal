package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金授信账户信息列表查询
 * @author guanjinglun
 */
public class AccountMainInfo extends BaseEntity {

    private static final long serialVersionUID = 8616028845509420405L;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
