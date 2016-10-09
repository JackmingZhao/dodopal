package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.BaseEntity;

public class YktCardConsumStatisticsBean extends BaseEntity {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //pos号
    private String proCode;
    //启用标识
    private String bind;
    //商户名称
    private String merName;
    //消费交易笔数
    private String consumeCount;
    //消费交易金额
    private String consumeAmt;
    
    //城市
    private String cityName;
    //交易日期
    private String orderdate;
    //POS备注 @BY MIKA
    private String comments;
    
    //利润
//    private String mergain;
//    
//    
//    
//    public String getMergain() {
//        return mergain;
//    }
//    public void setMergain(String mergain) {
//        this.mergain = mergain;
//    }
    //应付金额
    private String consumeOriginalAmt;
    
    //折扣金额
    private String consumeDiscountAmt;
    
    
    
    public String getConsumeDiscountAmt() {
        return consumeDiscountAmt;
    }
    public void setConsumeDiscountAmt(String consumeDiscountAmt) {
        this.consumeDiscountAmt = consumeDiscountAmt;
    }
    public String getConsumeOriginalAmt() {
        return consumeOriginalAmt;
    }
    public void setConsumeOriginalAmt(String consumeOriginalAmt) {
        this.consumeOriginalAmt = consumeOriginalAmt;
    }
    
    public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getBind() {
        if (StringUtils.isBlank(this.bind)) {
            return null;
        }
       return ActivateEnum.getActivateByCode(this.bind)!=null ? ActivateEnum.getActivateByCode(this.bind).getName():"";
    }
    public void setBind(String bind) {
        this.bind = bind;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getConsumeCount() {
        return consumeCount;
    }
    public void setConsumeCount(String consumeCount) {
        this.consumeCount = consumeCount;
    }
    public String getConsumeAmt() {
        return consumeAmt;
    }
    public void setConsumeAmt(String consumeAmt) {
        this.consumeAmt = consumeAmt;
    }
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    
}

