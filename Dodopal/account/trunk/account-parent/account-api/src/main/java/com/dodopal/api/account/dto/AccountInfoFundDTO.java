package com.dodopal.api.account.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金授信账户信息
 * @author guanjinglun
 */
public class AccountInfoFundDTO extends BaseEntity {

    private static final long serialVersionUID = 8405724035785748805L;
    /*
     * 编号(“F” + 账户类别 + 主账户编号)
     */
    private String fundAccountCode;

    /*
     * 资金类别:(枚举：资金、授信)
     */
    private String fundType;

    /*
     * 主账户编号(表明这个资金账户从属于哪个主账户)
     */
    private String accountCode;

    /*
     * 累计总金额(单位：分。默认为0。该账户历次充值累计相加的总金额。)
     */
    private String sumTotalAmount;

    /*
     * 总余额(单位：分。默认为0。表示归属于这个资金账户的所有金额，包括冻结资金。)
     */
    private String totalBalance;

    /*
     * 可用金额(单位：分。默认为0。表示该账户中可以自由使用的金额)
     */
    private String availableBalance;

    /*
     * 冻结金额(单位：分。默认为0。表示这部分金额虽然归属于该账户，但是当前状态下不能自由使用比如在进行公交卡充值流程中，相应的充值费用被冻结。)
     */
    private String frozenAmount;

    /*
     * 最近一次变动金额（单位：分。默认为0。最近一次针对账户操作的金额（包括资金冻结和资金解冻））
     */
    private String lastChangeAmount;

    /*
     * 变动前账户总余额(最近一次针对账户操作之前的账户总余额)
     */
    private String beforeChangeTotalAmount;

    /*
     * 变动前可用余额(最近一次针对账户操作之前的账户可用余额)
     */
    private String beforeChangeAvailableAmount;

    /*
     * 变动前冻结金额(最近一次针对账户操作之前的账户冻结余额)
     */
    private String beforeChangeFrozenAmount;

    /*
     * 状态(默认为正常)
     */
    private String state;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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

    public String getLastChangeAmount() {
        return lastChangeAmount;
    }

    public void setLastChangeAmount(String lastChangeAmount) {
        this.lastChangeAmount = lastChangeAmount;
    }

    public String getBeforeChangeTotalAmount() {
        return beforeChangeTotalAmount;
    }

    public void setBeforeChangeTotalAmount(String beforeChangeTotalAmount) {
        this.beforeChangeTotalAmount = beforeChangeTotalAmount;
    }

    public String getBeforeChangeAvailableAmount() {
        return beforeChangeAvailableAmount;
    }

    public void setBeforeChangeAvailableAmount(String beforeChangeAvailableAmount) {
        this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
    }

    public String getBeforeChangeFrozenAmount() {
        return beforeChangeFrozenAmount;
    }

    public void setBeforeChangeFrozenAmount(String beforeChangeFrozenAmount) {
        this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
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
