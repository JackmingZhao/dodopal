package com.dodopal.portal.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 查询子商户变更记录
 * @author hxc
 *
 */
public class ChildMerchantAccountChangeBean extends BaseEntity{
    
	private static final long serialVersionUID = -2108307442983264047L;

	// 商户编号
    private String merCode;
    
     //商户名称
    private String merName;
 
    
     //商户类型
    private String merType;

    //所属上级商户编码
    private String merParentCode;
    //资金账户号
    private String fundAccountCode;
    //资金类别
    private String fundType;
    //时间戳
    private String accountChangeTime;
	//交易流水号
    private String tranCode;
    //变动类型
    private String changeType;
    //变动金额
    private double changeAmount;
    //变动前账户总余额
    private double beforeChangeAmount;
    //变动前可用余额
    private double beforeChangeAvailableAmount;
    //变动前冻结金额
    private double beforeChangeFrozenAmount;
    //变动日期
    private String changeDate;

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getMerParentCode() {
        return merParentCode;
    }

    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getFundType() {
		 if (StringUtils.isBlank(this.fundType)) {
	            return null;
	        }
	        return FundTypeEnum.getFundTypeByCode(this.fundType).getName();
	}

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public void setAccountChangeTime(String accountChangeTime) {
		this.accountChangeTime = accountChangeTime;
	}
    public String getAccountChangeTime() {
		return accountChangeTime;
	}
    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getChangeType() {
    	 if (StringUtils.isBlank(this.changeType)) {
	            return null;
	        }
	        return AccTranTypeEnum.getTranTypeNameByCode(this.changeType);
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public double getBeforeChangeAmount() {
        return beforeChangeAmount;
    }

    public void setBeforeChangeAmount(double beforeChangeAmount) {
        this.beforeChangeAmount = beforeChangeAmount;
    }

    public double getBeforeChangeAvailableAmount() {
        return beforeChangeAvailableAmount;
    }

    public void setBeforeChangeAvailableAmount(double beforeChangeAvailableAmount) {
        this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
    }

    public double getBeforeChangeFrozenAmount() {
        return beforeChangeFrozenAmount;
    }

    public void setBeforeChangeFrozenAmount(double beforeChangeFrozenAmount) {
        this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
    
}
