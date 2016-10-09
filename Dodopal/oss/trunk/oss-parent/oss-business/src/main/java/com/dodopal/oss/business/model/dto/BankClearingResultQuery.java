package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 支付网关分类清算界面查询条件
 * @author dodopal
 *
 */
public class BankClearingResultQuery extends QueryBase {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1399308805404119662L;

    /**
     * 支付网关
     */
    private String payGateway;
    
    /**
     * 清算周期开始日期
     */
    private String clearingDayStart;
    
    /**
     * 清算周期结束日期
     */
    private String clearingDayEnd;
  
    public String getClearingDayStart() {
        return clearingDayStart;
    }

    public void setClearingDayStart(String clearingDayStart) {
        this.clearingDayStart = clearingDayStart;
    }

    public String getClearingDayEnd() {
        return clearingDayEnd;
    }

    public void setClearingDayEnd(String clearingDayEnd) {
        this.clearingDayEnd = clearingDayEnd;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }
}
