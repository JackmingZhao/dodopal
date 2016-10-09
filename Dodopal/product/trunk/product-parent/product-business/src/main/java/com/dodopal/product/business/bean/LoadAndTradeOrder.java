package com.dodopal.product.business.bean;

import java.io.Serializable;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年4月20日 下午8:04:55 
  * @version 1.0 
  * @parameter     自助终端取圈存流水号实体
  */
public class LoadAndTradeOrder implements Serializable {
    private static final long serialVersionUID = -8985333180183997121L;
    /**
     * 圈存订单
     */
    private String orderNumber;
    
    /**
     * 支付方式id
     */
    private String payid;
    /**
     * 流水号
     */
    private String tradeNum;
    
    
    /**
     * 支付方式类型
     */
    private String paytype;
    
    /**
     * 金额
     */
    private Number amont;
    
    public Number getAmont() {
        return amont;
    }
    public void setAmont(Number amont) {
        this.amont = amont;
    }
    public String getPaytype() {
        return paytype;
    }
    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getPayid() {
        return payid;
    }
    public void setPayid(String payid) {
        this.payid = payid;
    }
    public String getTradeNum() {
        return tradeNum;
    }
    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }
    
    
}
