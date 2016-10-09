package com.dodopal.api.account.dto;

import java.io.Serializable;

/**
 * 4.5  资金授信查询，返回值DTO
 * 
 * @author dodopal
 *
 */
public class FundAccountInfoDTO  implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8254201108657619167L;

    /**
     * 主账户号
     */
    private String accountCode;
    
    /**
     * 资金类别
     */
    private String fundType;

    /**
     * 资金账户号
     */
    private String fundAccountCode;
    
    /**
     * 授信账户号（没有就赋值空）
     */
    private String authorizedAccountCode;
    
    /**
     * 可用金额(单位：分。默认为0。表示该账户中可以自由使用的金额) // @By Mika
     */
    private String availableBalance;
    
    /**
     * 授信额度	// @By Mika
     */
    private String creditAmt;
    
    
    public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(String creditAmt) {
		this.creditAmt = creditAmt;
	}

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

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getAuthorizedAccountCode() {
        return authorizedAccountCode;
    }

    public void setAuthorizedAccountCode(String authorizedAccountCode) {
        this.authorizedAccountCode = authorizedAccountCode;
    }
}
