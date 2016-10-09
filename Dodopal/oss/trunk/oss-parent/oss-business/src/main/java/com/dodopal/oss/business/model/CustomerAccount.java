package com.dodopal.oss.business.model;

import java.io.Serializable;

import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;

/**
 * 2.6 调账申请单中客户和账户信息
 * @author Mango
 */
public class CustomerAccount implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    private String customerId ;
    
    // 客户类型    单选框 用户选择    长度=1    个人、企业
    private String customerType;

    // 客户号 只读  通过查询界面进行选择  长度<40
    private String customerNo;

    // 客户名称    只读  根据选择的客户号自动复制    长度<128 冗余保存
    private String customerName;

    private String fundType;
    
    //  账户号 只读  根据用户在UI上选择的客户以及账户类型，自动显示对应的账户号。 长度<40   
    private String fundAccountCode;
    
    private String merType;

    public String getFundTypeStr() {
        return FundTypeEnum.getFundTypeNameByCode(fundType);
    }
    
    public String getCustomerTypeStr() {
        return MerUserTypeEnum.getMerUserTypeNameByCode(customerType);
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    
}
