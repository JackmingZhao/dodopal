package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class ProxyInfoResponse extends BaseResponse {
	// 商户名称(proxyname)
	private String mername;
	// 所在城市(cityno)
	private String citycode;
	// 网点类型(proxytype)
	private String mertype;
	// 网点地址(proxyaddress)
	private String meraddress;
	// 网点电话(proxytel)
	private String mertel;
	// 网点传真(proxyfax)
	private String merfax;
	// 网点邮政编码(proxyzipcode)
	private String merzip;
	// 网点注册时间(proxyregisttime)
	private String merregtime;
	// 网点状态(proxystatus)
	private String merstate;
	// 网点是否为定制状态: 0：停用1：启用-1:取状态出错(dzstatus)
	private String meractivate;
	// 是否线下收单标示0否，1是
	private String payflag;
	// 结算周期(例如：30天)
	private String cycletype;
	// 费率(含义未知)
	private String amtfee;
	// 充值费率
	private String rateamt;

	public String getMername() {
		return mername;
	}

	public void setMername(String mername) {
		this.mername = mername;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getMertype() {
		return mertype;
	}

	public void setMertype(String mertype) {
		this.mertype = mertype;
	}

	public String getMeraddress() {
		return meraddress;
	}

	public void setMeraddress(String meraddress) {
		this.meraddress = meraddress;
	}

	public String getMertel() {
		return mertel;
	}

	public void setMertel(String mertel) {
		this.mertel = mertel;
	}

	public String getMerfax() {
		return merfax;
	}

	public void setMerfax(String merfax) {
		this.merfax = merfax;
	}

	public String getMerzip() {
		return merzip;
	}

	public void setMerzip(String merzip) {
		this.merzip = merzip;
	}

	public String getMerregtime() {
		return merregtime;
	}

	public void setMerregtime(String merregtime) {
		this.merregtime = merregtime;
	}

	public String getMerstate() {
		return merstate;
	}

	public void setMerstate(String merstate) {
		this.merstate = merstate;
	}

	public String getMeractivate() {
		return meractivate;
	}

	public void setMeractivate(String meractivate) {
		this.meractivate = meractivate;
	}

	public String getPayflag() {
		return payflag;
	}

	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}

	public String getCycletype() {
		return cycletype;
	}

	public void setCycletype(String cycletype) {
		this.cycletype = cycletype;
	}

	public String getAmtfee() {
		return amtfee;
	}

	public void setAmtfee(String amtfee) {
		this.amtfee = amtfee;
	}

	public String getRateamt() {
		return rateamt;
	}

	public void setRateamt(String rateamt) {
		this.rateamt = rateamt;
	}

}
