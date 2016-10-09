package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class CardRechargeQuery extends QueryBase {

    private static final long serialVersionUID = -5124820605288895908L;

    /* 通卡清分状态  */
    private String supplierClearingFlag = "2";
    /* 客户清分状态  */
    private String customerClearingFlag = "2";
    /* 业务类型 */
    private String businessType = "01";
    /*订单时间 对应清算表中字段    ORDER_DATE */
    /* 开始时间 */
    private Date sDate;
    /* 结束时间 */
    private Date eDate;
    /* 客户名称   */
    private String customerName;
    /* 订单号(清分表中的 订单交易号 对应  订单表中的    订单编号) */
    private String orderNo;

    public String getSupplierClearingFlag() {
        return supplierClearingFlag;
    }

    public void setSupplierClearingFlag(String supplierClearingFlag) {
        this.supplierClearingFlag = supplierClearingFlag;
    }

    public String getCustomerClearingFlag() {
        return customerClearingFlag;
    }

    public void setCustomerClearingFlag(String customerClearingFlag) {
        this.customerClearingFlag = customerClearingFlag;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
