package com.dodopal.oss.business.model.dto;


import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class CardConsumeClearingQuery extends QueryBase{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //  订单号号 文本框 模糊查询
    private String orderNo;
    //    客户名称    文本框 模糊查询
    private String customerName;
    //   订单时间    日历控件    
    private Date clearingStartDate;
  
    private Date clearingEndDate;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getClearingStartDate() {
        return clearingStartDate;
    }

    public void setClearingStartDate(Date clearingStartDate) {
        this.clearingStartDate = clearingStartDate;
    }

    public Date getClearingEndDate() {
        return clearingEndDate;
    }

    public void setClearingEndDate(Date clearingEndDate) {
        this.clearingEndDate = clearingEndDate;
    }
    

}
