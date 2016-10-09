package com.dodopal.portal.business.bean;

public class TransactionDetailsBean {
	// 订单号
	private String orderNo;
	// 订单时间
	private String proOrderDate;
	// 消费金额（实付金额）
	private String txnAmt;
	// 卡号
	private String orderCardno;
	// 消费前金额
    private String befbal;
	// 使用过后金额/消费后卡内余额
	private String blackAmt;
	// 订单状态
	private String proOrderState;
	
	 //城市
    private String cityName;
    //pos号
    private String posCode;
    //应付金额
    private String originalPrice;
    //商户名称
    private String merName;
    
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

	public String getBefbal() {
		return befbal;
	}

	public void setBefbal(String befbal) {
		this.befbal = befbal;
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

	public String getProOrderDate() {
		return proOrderDate;
	}

	public void setProOrderDate(String proOrderDate) {
		this.proOrderDate = proOrderDate;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getOrderCardno() {
		return orderCardno;
	}

	public void setOrderCardno(String orderCardno) {
		this.orderCardno = orderCardno;
	}

	public String getBlackAmt() {
		return blackAmt;
	}

	public void setBlackAmt(String blackAmt) {
		this.blackAmt = blackAmt;
	}

	public String getProOrderState() {
		return proOrderState;
	}

	public void setProOrderState(String proOrderState) {
		this.proOrderState = proOrderState;
	}

}
