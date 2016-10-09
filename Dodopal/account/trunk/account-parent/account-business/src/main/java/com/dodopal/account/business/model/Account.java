/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * @description Account bean
 * Created by lenovo on 2015/8/21.
 */
public class Account extends BaseEntity{
    //主账户号
    private String accountCode;
    //资金类别
    private String fundType;
    //客户类型
    private String customerType;
    //客户号
    private String customerNo;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountCode='" + accountCode + '\'' +
                ", fundType='" + fundType + '\'' +
                ", customerType='" + customerType + '\'' +
                ", customerNo='" + customerNo + '\'' +
                '}';
    }
}
