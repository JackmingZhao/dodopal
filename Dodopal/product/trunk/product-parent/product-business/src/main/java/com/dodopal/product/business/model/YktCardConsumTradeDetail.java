package com.dodopal.product.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class YktCardConsumTradeDetail extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
  //订单编号
    private String orderNo;
    //消费金额（实付金额）
    private String consumeAmt;
    //卡号
    private String cardNo;
    // 消费前金额
    private String befbal;
    //消费后卡内余额
    private String blackAmt;
    //订单状态
    private String orderStates;
    //消费时间
     // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;
    
    //城市
    private String cityName;
    //pos号
    private String posCode;
    //应付金额
    private String originalPrice;
    private String merName ;
    
    //pos备注
    private String posComments;
    
    
    public String getPosComments() {
        return posComments;
    }
    public void setPosComments(String posComments) {
        this.posComments = posComments;
    }
    public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getConsumeAmt() {
        return consumeAmt;
    }
    public void setConsumeAmt(String consumeAmt) {
        this.consumeAmt = consumeAmt;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getBefbal() {
        return befbal;
    }
    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }
    public String getBlackAmt() {
        return blackAmt;
    }
    public void setBlackAmt(String blackAmt) {
        this.blackAmt = blackAmt;
    }
    public String getOrderStates() {
        return orderStates;
    }
    public void setOrderStates(String orderStates) {
        this.orderStates = orderStates;
    }
    public Date getProOrderDate() {
        return proOrderDate;
    }
    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

}
