package com.dodopal.api.account.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * 主账户信息
 * @author guanjinglun
 */
public class AccountDTO extends BaseEntity {

    private static final long serialVersionUID = -2670879461451652815L;

    /*
     * 编号(A + 20150428222211 +五位数据库cycle sequence（循环使用）)
     */
    private String accountCode;

    /*
     * 资金类别:枚举：资金、授信
     */
    private String fundType;

    /*
     * 类型:个人和商户
     */
    private String customerType;

    /*
     * 客户号(商户号或个人用户号 如果类型是企业，则为商户号；如果是个人，则为用户号)
     */
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
