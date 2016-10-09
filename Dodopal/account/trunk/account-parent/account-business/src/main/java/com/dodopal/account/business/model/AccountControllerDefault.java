/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * Created by lenovo on 2015/9/8.
 */
public class AccountControllerDefault extends BaseEntity {
    //客户类型
    private String customerType;
    //日消费交易单笔限额
    private long dayConsumeSingleLimit;
    //日消费交易累计限额
    private long dayConsumeSumLimit;
    //日充值交易单笔限额
    private long dayRechargeSingleLimit;
    //日充值交易累计限额
    private long dayRechargeSumLimit;
    //日转账交易最大次数
    private long dayTransferMax;
    //日转账交易单笔限额
    private long dayTransferSingleLimit;
    //日转账交易累计限额
    private long dayTransferSumLimit;
    // 默认授信额度阈值(CREDIT_AMT) 字段  类型： NUMBER(10) 单位：分，默认为20000 ___@Mika
    private long creditAmt; 	// ___@Mika
    

    public long getCreditAmt() {
		return creditAmt;
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
}
