package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 通卡公司分类清算界面查询条件
 * @author dodopal
 *
 */
public class YktClearingResultQuery extends QueryBase {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3906222253878297958L;

    /**
     * 通卡编号
     */
    private String yktCode;
    
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

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getClearingDayEnd() {
        return clearingDayEnd;
    }

    public void setClearingDayEnd(String clearingDayEnd) {
        this.clearingDayEnd = clearingDayEnd;
    }
    
}
