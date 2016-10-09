package com.dodopal.payment.business.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信退款响应 bean XML 映射
 * @author Gao
 *
 */
@XStreamAlias("xml") 
public class WxRefundResponseBean extends WxBaseBean{	

	private String transaction_id = "";// 微信支付订单号
	private String out_trade_no = "";// 商户订单号
	private String out_refund_no = "";// 退款订单号
	private String refund_id = "";// 微信退款订单号
	private String refund_channel = "";// 退款渠道
	private String total_fee = "";// 总金额
	private String refund_fee = "";// 退款金额
	private String coupon_refund_fee = "";// 用户标识		
	private String cash_fee = "";// 用户标识
	private String coupon_refund_count = "";// 用户标识
	private String cash_refund_fee = "";// 用户标识
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
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getRefund_channel() {
		return refund_channel;
	}
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getCoupon_refund_fee() {
		return coupon_refund_fee;
	}
	public void setCoupon_refund_fee(String coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCoupon_refund_count() {
		return coupon_refund_count;
	}
	public void setCoupon_refund_count(String coupon_refund_count) {
		this.coupon_refund_count = coupon_refund_count;
	}
	public String getCash_refund_fee() {
		return cash_refund_fee;
	}
	public void setCash_refund_fee(String cash_refund_fee) {
		this.cash_refund_fee = cash_refund_fee;
	}

	
}
