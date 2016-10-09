package com.dodopal.api.payment.dto;

import com.dodopal.common.model.BaseEntity;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年4月13日 上午11:16:08 
  * @version 1.0 
  * @parameter  生成交易流水
  */
public class CreateTranDTO extends BaseEntity {
    private static final long serialVersionUID = -3997118982294571675L;
    
    /**
     * 来源
     */
    private String source;
    
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 客户类型
     */
    private String custtype;
    
    /**
     * 客户号
     */
    private String custcode;
    
    /**
     * 充值金额
     */
    private long amont;
    
    /**
     * 支付方式id
     */
    private String payid;
    
    /**
     * 产品编号ID
     */
    private String procode;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 卡面号
     */
    private String cardnum;
    
    /**
     * 支付服务费率
     */
    private double payservicerate;
    
    /**
     * 支付手续费率
     */
    private double payprocerate;
    
    /**
     * 圈存订单号
     * @return
     */
    
    private String orderNum;
    
    /**
     * 回调通知URL
     * @return
     */
    
    private String notifyUrl;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }


    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }


    public long getAmont() {
        return amont;
    }

    public void setAmont(long amont) {
        this.amont = amont;
    }

    public double getPayservicerate() {
        return payservicerate;
    }

    public void setPayservicerate(double payservicerate) {
        this.payservicerate = payservicerate;
    }

    public double getPayprocerate() {
        return payprocerate;
    }

    public void setPayprocerate(double payprocerate) {
        this.payprocerate = payprocerate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
    
    
}
