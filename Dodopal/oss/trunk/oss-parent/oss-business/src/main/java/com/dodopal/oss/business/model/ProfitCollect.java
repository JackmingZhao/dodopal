/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.model;

import java.util.Date;

/**
 * Created by lenovo on 2015/10/31.
 */
public class ProfitCollect {
    private String id;//主键id
    private String collectDate;//汇总日期
    private Date collectTime;//汇总时间
    private String customerCode;//客户号
    private String customerName;//客户名称
    private String bussinessType;//业务类型
    private long tradeCount;//笔数
    private long tradeAmount;//金额
    private long profit;//商户实际分润

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public long getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public long getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }
}
