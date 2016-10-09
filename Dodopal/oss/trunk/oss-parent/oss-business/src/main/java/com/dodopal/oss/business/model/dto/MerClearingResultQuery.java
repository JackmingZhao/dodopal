package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 商户分类清算界面查询条件
 * @author dodopal
 *
 */
public class MerClearingResultQuery extends QueryBase {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6907777190145588705L;

    /**
     * 商户编号
     */
    private String merCode;
    
    /**
     * 清算周期开始
     */
    private String clearingDayStart;
    
    /**
     * 清算周期结束
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

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    
}
