package com.dodopal.api.account.dto;

import java.math.BigDecimal;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * AccountControllerDefault
 * @author Mango
 */
public class AccountControllerDefaultDTO extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;
    // 客户类型
    private String customerType;

    // 日消费交易单笔限额
    private long dayConsumeSingleLimit;

    // 日消费交易累计限额
    private long dayConsumeSumLimit;

    // 日充值交易单笔限额
    private long dayRechargeSingleLimit;

    // 日充值交易累计限额
    private long dayRechargeSumLimit;

    // 日转账交易最大次数
    private long dayTransferMax;

    // 日转账交易单笔限额
    private long dayTransferSingleLimit;

    // 日转账交易累计限额
    private long dayTransferSumLimit;
    
    // 默认授信额度阈值 @By Mika
    private long creditAmt;
    

    public long getCreditAmt() {
		return creditAmt;
	}
    
    public String getCreditAmtStr() {
        return getText(creditAmt);
    }
	public void setCreditAmt(long creditAmt) {
		this.creditAmt = creditAmt;
	}

	public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public long getDayConsumeSingleLimit() {
        return dayConsumeSingleLimit;
    }

    public void setDayConsumeSingleLimit(long dayConsumeSingleLimit) {
        this.dayConsumeSingleLimit = dayConsumeSingleLimit;
    }

    public long getDayConsumeSumLimit() {
        return dayConsumeSumLimit;
    }

    public void setDayConsumeSumLimit(long dayConsumeSumLimit) {
        this.dayConsumeSumLimit = dayConsumeSumLimit;
    }

    public long getDayRechargeSingleLimit() {
        return dayRechargeSingleLimit;
    }

    public void setDayRechargeSingleLimit(long dayRechargeSingleLimit) {
        this.dayRechargeSingleLimit = dayRechargeSingleLimit;
    }

    public long getDayRechargeSumLimit() {
        return dayRechargeSumLimit;
    }

    public void setDayRechargeSumLimit(long dayRechargeSumLimit) {
        this.dayRechargeSumLimit = dayRechargeSumLimit;
    }

    public long getDayTransferMax() {
        return dayTransferMax;
    }

    public void setDayTransferMax(long dayTransferMax) {
        this.dayTransferMax = dayTransferMax;
    }

    public long getDayTransferSingleLimit() {
        return dayTransferSingleLimit;
    }

    public void setDayTransferSingleLimit(long dayTransferSingleLimit) {
        this.dayTransferSingleLimit = dayTransferSingleLimit;
    }

    public long getDayTransferSumLimit() {
        return dayTransferSumLimit;
    }

    public void setDayTransferSumLimit(long dayTransferSumLimit) {
        this.dayTransferSumLimit = dayTransferSumLimit;
    }
    
    public String getCustomerTypeName() {
        return MerTypeEnum.getNameByCode(customerType);
    }
    
    public String getAcctDefaultId() {
        return super.getId();
    }

    public String getDayConsumeSingleLimitStr() {
        return getText(dayConsumeSingleLimit);
    }

    public String getDayConsumeSumLimitStr() {
        return getText(dayConsumeSumLimit);
    }
    public String getDayRechargeSingleLimitStr() {
        return getText(dayRechargeSingleLimit);
    }
    public String getDayRechargeSumLimitStr() {
        return getText(dayRechargeSumLimit);
    }
    public String getDayTransferSingleLimitStr() {
        return getText(dayTransferSingleLimit);
    }

    public String getDayTransferSumLimitStr() {
        return getText(dayTransferSumLimit);
    }
    private String getText(long number) {
        BigDecimal amt = new BigDecimal(number);
        return amt.divide(new BigDecimal(100)).toPlainString();
    }

}
