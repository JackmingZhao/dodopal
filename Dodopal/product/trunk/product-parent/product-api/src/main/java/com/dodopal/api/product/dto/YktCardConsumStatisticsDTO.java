package com.dodopal.api.product.dto;


import com.dodopal.common.model.BaseEntity;
/**
 * 一卡通消费统计
 * @author tao
 *
 */
public class YktCardConsumStatisticsDTO extends BaseEntity{

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
    // POS备注 @by mIka
    private String comments;
    
    //利润
//    private String mergain;
//    
//    
//    public String getMergain() {
//        return mergain;
//    }
//    public void setMergain(String mergain) {
//        this.mergain = mergain;
//    }
    
    //应支付 总金额 
    private String consumeOriginalAmt;
    
    
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
        return bind;
    }
    public void setBind(String bindFlag) {
        this.bind = bindFlag;
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
