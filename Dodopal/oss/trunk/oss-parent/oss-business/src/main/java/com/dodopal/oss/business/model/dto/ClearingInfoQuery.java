package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class ClearingInfoQuery extends QueryBase {

    private static final long serialVersionUID = -2517006107905868791L;

    /**
     * 清分状态
     */
    private String clearingFlag;

    /**
     * 清分开始日期
     */
    private String clearingDayFrom;

    /**
     * 清分结束日期
     */
    private String clearingDayTo;

    public String getClearingFlag() {
        return clearingFlag;
    }

    public void setClearingFlag(String clearingFlag) {
        this.clearingFlag = clearingFlag;
    }

    public String getClearingDayFrom() {
        return clearingDayFrom;
    }

    public void setClearingDayFrom(String clearingDayFrom) {
        this.clearingDayFrom = clearingDayFrom;
    }

    public String getClearingDayTo() {
        return clearingDayTo;
    }

    public void setClearingDayTo(String clearingDayTo) {
        this.clearingDayTo = clearingDayTo;
    }

}
