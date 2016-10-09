/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.account.dto;

import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;

public class AccountControllerCustomerDTO extends AccountControlDTO{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String accountControllerId;
    
    private String customerNo;
    private String customerName;
    private String customerType;
    private String fundType;
    private String accountCode;
    private long creditAmt;
    
    public long getCreditAmt() {
        return creditAmt;
    }
    public String getCreditAmtStr() {
        return getText(Long.toString(creditAmt));
    }
    public void setCreditAmt(long creditAmt) {
        this.creditAmt = creditAmt;
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
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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
    public String getAccountControllerId() {
        return accountControllerId;
    }
    public void setAccountControllerId(String accountControllerId) {
        this.accountControllerId = accountControllerId;
    }
    
    public String getCustomerTypeStr() {
        return MerUserTypeEnum.getMerUserTypeNameByCode(customerType);
    }
    
    public String getFundTypeStr() {
        return FundTypeEnum.getFundTypeNameByCode(fundType);
    }
}
