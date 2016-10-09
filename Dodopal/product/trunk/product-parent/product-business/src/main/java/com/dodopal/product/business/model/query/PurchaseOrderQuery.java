package com.dodopal.product.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * 门户网点消费订单汇总/详细查询DTO
 */
public class PurchaseOrderQuery extends QueryBase {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    // 商户号
    private String merCode;
    
    // 消费订单状态（0：成功；1：失败；2：可疑）
    private String proOrderState;

    // 交易日期开始(订单创建时间) 日历选择框 范围  用户选择    格式： yyyy-MM-dd
    private Date orderDateStart;

    // 交易日期结束(订单创建时间) 日历选择框 范围  用户选择    格式： yyyy-MM-dd
    private Date orderDateEnd;
 
    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
  
}
