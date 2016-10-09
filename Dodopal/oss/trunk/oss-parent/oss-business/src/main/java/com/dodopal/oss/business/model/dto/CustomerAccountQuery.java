package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class CustomerAccountQuery extends QueryBase {
    private static final long serialVersionUID = 4745183696369858981L;
    // 客户类型    单选框 用户选择    长度=1    个人、企业
    private String customerType;
    /*客户名称*/
    private String customerName;
    /*客户编码*/
    private String customerNo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
