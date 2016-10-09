package com.dodopal.oss.business.bean.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * 数据中心__财务报表__购买通卡额度明细报表
 * @author 
 * @version 
 */
public class YktLimitBatchReportQuery extends QueryBase{

    private static final long serialVersionUID = -7361835197318621370L;

    // 通卡名称
    private String yktName;
    
    // 财务打款日期（起始日期）
    private Date financialPayDateStart;
    
    // 财务打款日期（终止日期）
    private Date financialPayDateEnd;
    
    // 通卡加款日期（起始日期）
    private Date yktAddLimitDateStart;
    
    // 通卡加款日期（终止日期）
    private Date yktAddLimitDateEnd;

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Date getFinancialPayDateStart() {
        return financialPayDateStart;
    }

    public void setFinancialPayDateStart(Date financialPayDateStart) {
        this.financialPayDateStart = financialPayDateStart;
    }

    public Date getFinancialPayDateEnd() {
        return financialPayDateEnd;
    }

    public void setFinancialPayDateEnd(Date financialPayDateEnd) {
        this.financialPayDateEnd = financialPayDateEnd;
    }

    public Date getYktAddLimitDateStart() {
        return yktAddLimitDateStart;
    }

    public void setYktAddLimitDateStart(Date yktAddLimitDateStart) {
        this.yktAddLimitDateStart = yktAddLimitDateStart;
    }

    public Date getYktAddLimitDateEnd() {
        return yktAddLimitDateEnd;
    }

    public void setYktAddLimitDateEnd(Date yktAddLimitDateEnd) {
        this.yktAddLimitDateEnd = yktAddLimitDateEnd;
    }

}

