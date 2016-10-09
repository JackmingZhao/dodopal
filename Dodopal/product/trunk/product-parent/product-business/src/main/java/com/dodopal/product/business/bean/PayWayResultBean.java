package com.dodopal.product.business.bean;

/**
 * @author hxc
 */
public class PayWayResultBean {

	/**
     * PAY_ACT：账户支付
     * PAY_CARD：一卡通支付
     * PAY_ONLINE：在线支付
     * PAY_BANK：银行支付
     * 具体code 参照枚举 PayTypeEnum
     */
    private String paytype;
    
    /**
     * 支付方式名称
     */
    private String paywayname;
    
    /**
     * 支付方式id
     */
    private String paywayid;
    
    /**
     * 支付方式图标名称
     */
    private String paylogoname;

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getPaywayname() {
		return paywayname;
	}

	public void setPaywayname(String paywayname) {
		this.paywayname = paywayname;
	}

	public String getPaywayid() {
		return paywayid;
	}

	public void setPaywayid(String paywayid) {
		this.paywayid = paywayid;
	}

	public String getPaylogoname() {
		return paylogoname;
	}

	public void setPaylogoname(String paylogoname) {
		this.paylogoname = paylogoname;
	}
    
}
