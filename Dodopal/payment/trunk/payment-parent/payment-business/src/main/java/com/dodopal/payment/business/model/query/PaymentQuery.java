package com.dodopal.payment.business.model.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月23日 下午1:49:52
 */
public class PaymentQuery extends QueryBase{
    private static final long serialVersionUID = -6539596930537211091L;
    /**
     * 交易流水号
     */
    private String tranCode;    
    /**
     *   支付类型
     */
    private String payType;
    
    /**
     * 支付方式名称
     */
    private String payWayName;
    
    /**
     * 支付状态
     */
    private String payStatus;

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


    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

}
