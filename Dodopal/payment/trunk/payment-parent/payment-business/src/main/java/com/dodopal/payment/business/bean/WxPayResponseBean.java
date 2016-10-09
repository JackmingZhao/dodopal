package com.dodopal.payment.business.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信支付bean XML 映射
 * @author Gao
 *
 */
@XStreamAlias("xml") 
public class WxPayResponseBean extends WxBaseBean{	

	private String openid = "";// 用户标识
	private String is_subscribe = "";// 是否关注公众账号
	private String trade_type = "";// 交易类型
	private String bank_type = "";// 付款银行
	private String total_fee = "";// 总金额
	private String coupon_fee = "";// 现金券金额
	private String fee_type = "";// 货币种类
	private String transaction_id = "";// 微信支付订单号
	private String out_trade_no = "";// 商户订单号
	private String attach = "";// 商家数据包
	private String time_end = "";// 支付完成时间
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
	
}
