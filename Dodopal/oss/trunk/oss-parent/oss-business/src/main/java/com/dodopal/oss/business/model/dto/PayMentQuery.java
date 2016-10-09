package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 
 * @author hxc
 *
 */
public class PayMentQuery extends QueryBase{
    
    private static final long serialVersionUID = -1181910663598336079L;

    /**
     * 交易流水号
     */
    private String tranCode;
    
    /**
     *   支付类型
     */
    private String payType;
    
    /**
     * 支付状态
     */
    private String payStatus;
    
    /**
     * 支付方式名称
     */
    private String payWayName;

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
    
    
}
