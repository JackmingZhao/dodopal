package com.dodopal.api.account.dto.query;

import com.dodopal.common.model.QueryBase;

/**
 * 12.5 资金账户风控信息列表查询条件
 */
public class AccountControllerQueryDTO extends QueryBase {

    private static final long serialVersionUID = 6317442672694523553L;

    /*
     * 商户号（个人用户编号）模糊查询
     */
    public String custNum;

    /*
     * 商户(个人)名称；模糊查询
     */
    public String custName;
    
    /*
     * 商户类型  0:个人 1:企业
     */
    public String customerType;

    public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
