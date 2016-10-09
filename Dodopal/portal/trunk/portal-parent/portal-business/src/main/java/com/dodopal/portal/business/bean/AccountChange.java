package com.dodopal.portal.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 资金变更记录
 * @author hxc
 *
 */
public class AccountChange extends BaseEntity{

	private static final long serialVersionUID = 289140909939833159L;
	
	//资金账户号
    private String fundAccountCode;
    //资金类别
    private String fundType;
    //时间戳
    private Date accountChangeTime;
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
	//变动后可用余额
	private double afterChangeAvailableAmount;
	//变动后冻结金额
	private double afterChangeFrozenAmount;

	public double getAfterChangeAvailableAmount() {
		return afterChangeAvailableAmount;
	}

	public void setAfterChangeAvailableAmount(double afterChangeAvailableAmount) {
		this.afterChangeAvailableAmount = afterChangeAvailableAmount;
	}

	public double getAfterChangeFrozenAmount() {
		return afterChangeFrozenAmount;
	}

	public void setAfterChangeFrozenAmount(double afterChangeFrozenAmount) {
		this.afterChangeFrozenAmount = afterChangeFrozenAmount;
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
	public Date getAccountChangeTime() {
		return accountChangeTime;
	}
	public void setAccountChangeTime(Date accountChangeTime) {
		this.accountChangeTime = accountChangeTime;
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
