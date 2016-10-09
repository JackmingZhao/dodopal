package com.dodopal.oss.business.bean.query;


import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.QueryBase;
import com.dodopal.oss.business.util.CustomDateSerializer;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午5:41:56
 */
public class PayConfigQuery extends QueryBase{
    private static final long serialVersionUID = 1526246628405922243L;

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
    @JsonSerialize(using = CustomDateSerializer.class) 
    public void setAfterProceRateDateEnd(Date afterProceRateDateEnd) {
        this.afterProceRateDateEnd = afterProceRateDateEnd;
    }

    public Date getAfterProceRateDateStart() {
        return afterProceRateDateStart;
    }
    @JsonSerialize(using = CustomDateSerializer.class) 
    public void setAfterProceRateDateStart(Date afterProceRateDateStart) {
        this.afterProceRateDateStart = afterProceRateDateStart;
    }
    
}
