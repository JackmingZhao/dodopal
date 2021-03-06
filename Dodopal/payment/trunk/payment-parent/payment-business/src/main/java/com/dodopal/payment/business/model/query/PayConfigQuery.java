package com.dodopal.payment.business.model.query;


import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午5:41:56
 */
public class PayConfigQuery extends QueryBase{
    private static final long serialVersionUID = 85183677488550524L;

    /**
     *   支付类型
     */
    private String payType;
    
    /**
     * 支付方式名称
     */
    private String payWayName;
    
    /**
     * 启用标志
     */
    private String activate;
    
    /**
     * 后手续费生效时间结束
     */
    private Date afterProceRateDateEnd;
    
    /**
     * 后手续费生效时间开始
     */
    private Date afterProceRateDateStart;

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

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public Date getAfterProceRateDateEnd() {
        return afterProceRateDateEnd;
    }

    public void setAfterProceRateDateEnd(Date afterProceRateDateEnd) {
        this.afterProceRateDateEnd = afterProceRateDateEnd;
    }

    public Date getAfterProceRateDateStart() {
        return afterProceRateDateStart;
    }

    public void setAfterProceRateDateStart(Date afterProceRateDateStart) {
        this.afterProceRateDateStart = afterProceRateDateStart;
    }
    
}
