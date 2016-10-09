package com.dodopal.running.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金授信账户表ACCOUNT_FUND对应实体类
 */
public class AccountFund extends BaseEntity{
    
    private static final long serialVersionUID = -6918707888236253109L;
    
    /**
     *资金授信编号 
     */
    private String fundAccountCode;
    
    /**
     *资金类型 
     */
    private String fundType;
    
    /**
     *主账户编号 
     */
    private String accountCode;
    
    /**
     *累计总金额 
     */
    private long sumTotalAmount;
    
    /**
     *总余额 
     */
    private long totalBalance;
    
    /**
     *可用金额 
     */
    private long availableBalance;
    
    /**
     *冻结金额 
     */
    private long frozenAmount;
    
    /**
     *最近一次变动金额 
     */
    private long lastChangeAmount;
    
    /**
     *变动前账户总余额 
     */
    private long beforeChangeTotalAmount;
    
    /**
     *变动前可用余额 
     */
    private long beforeChangeAvailableAmount;
    
    /**
     *变动前冻结金额 
     */
    private long beforeChangeFrozenAmount;
    
    /**
     *状态 
     */
    private String state;
    
    /**
     *支付密码(预留) 
     */
    private String payPassword;
    
    /**
     *支付密码是否启用(预留) 
     */
    private String useable;
    
    /**
     *加密字段(预留) 
     */
    private String ciphertext;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(long frozenAmount) {
        this.frozenAmount = frozenAmount;
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

	public long getSumTotalAmount() {
		return sumTotalAmount;
	}

	public void setSumTotalAmount(long sumTotalAmount) {
		this.sumTotalAmount = sumTotalAmount;
	}

	public long getLastChangeAmount() {
		return lastChangeAmount;
	}

	public void setLastChangeAmount(long lastChangeAmount) {
		this.lastChangeAmount = lastChangeAmount;
	}

	public long getBeforeChangeTotalAmount() {
		return beforeChangeTotalAmount;
	}

	public void setBeforeChangeTotalAmount(long beforeChangeTotalAmount) {
		this.beforeChangeTotalAmount = beforeChangeTotalAmount;
	}

	public long getBeforeChangeAvailableAmount() {
		return beforeChangeAvailableAmount;
	}

	public void setBeforeChangeAvailableAmount(long beforeChangeAvailableAmount) {
		this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
	}

	public long getBeforeChangeFrozenAmount() {
		return beforeChangeFrozenAmount;
	}

	public void setBeforeChangeFrozenAmount(long beforeChangeFrozenAmount) {
		this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
}
