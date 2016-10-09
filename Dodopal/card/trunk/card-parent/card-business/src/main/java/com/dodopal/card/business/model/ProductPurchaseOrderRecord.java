package com.dodopal.card.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 
 * 产品库公交卡收单记录表
 * 
 *  数据库表PRD_PURCHASE_ORDER_RECORD 对应实体对象
 *
 */
public class ProductPurchaseOrderRecord extends BaseEntity{

	private static final long serialVersionUID = 6494786849110561723L;
	
	
	 // 主订单编号(关联消费主订单表的订单编号)
    private String orderNum;
    
    // 城市CODE
    private String cityCode;
    
    // 通卡公司一卡通支付费率（固定类型：千分比）
    private Double yktPayRate;
    
    // 商户折扣
    private Double merDiscount;
    
    // 城市名称
    private String cityName;

    // 通卡公司名称
    private String ytkName;
    
    // 通卡公司CODE
    private String ytkCode;
    
    // 订单时间:式yyyy/MM/dd HH:MM:SS,实际为订单的创建时间。
    private Date orderDate;

    // 订单日期:格式yyyyMMdd,这里实际上为订单的创建日期（有点类似订单时间）。冗余的主要目的是为了满足数据库系统的分区。
    private String orderDay;
    
    // 卡内号
    private String cardNum; 
    
    // 卡面号
    private String cardFaceno; 
    
    // POS号
    private String posCode; 
 
    // 支付前金额(在支付之前的公交卡内的余额。)
    private Long befbal; 
    
    // 支付后金额(在支付之后的公交卡内的余额。)
    private Long blackAmt; 
    
    // 内部状态(00:订单创建成功;01:验卡成功;02:申请消费密钥成功;10:验卡失败;11:申请消费密钥失败;12:扣款失败;20:扣款成功;30:扣款未知)
    private String innerStates;
    
    // 前内部状态
    private String beforInnerStates;
    
    // 可疑处理状态(0:不可疑:；1:可疑；)
    private String suspiciousStates;
    
    // 可疑处理说明(OSS用户处理可疑订单的时候输入。)
    private String suspiciousExplain;

    // 重发标志位（0：生单默认值；1：申请扣款指令环节产品库接收到DLL发送重发请求时更新为1）
    private String resendSign;

    // 通卡优惠标志
    private String yktDisCountSign;
    
	public String getYktDisCountSign() {
        return yktDisCountSign;
    }

    public void setYktDisCountSign(String yktDisCountSign) {
        this.yktDisCountSign = yktDisCountSign;
    }

    public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Double getYktPayRate() {
		return yktPayRate;
	}

	public void setYktPayRate(Double yktPayRate) {
		this.yktPayRate = yktPayRate;
	}

	public Double getMerDiscount() {
		return merDiscount;
	}

	public void setMerDiscount(Double merDiscount) {
		this.merDiscount = merDiscount;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getYtkName() {
		return ytkName;
	}

	public void setYtkName(String ytkName) {
		this.ytkName = ytkName;
	}

	public String getYtkCode() {
		return ytkCode;
	}

	public void setYtkCode(String ytkCode) {
		this.ytkCode = ytkCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDay() {
		return orderDay;
	}

	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardFaceno() {
		return cardFaceno;
	}

	public void setCardFaceno(String cardFaceno) {
		this.cardFaceno = cardFaceno;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public Long getBefbal() {
		return befbal;
	}

	public void setBefbal(Long befbal) {
		this.befbal = befbal;
	}

	public Long getBlackAmt() {
		return blackAmt;
	}

	public void setBlackAmt(Long blackAmt) {
		this.blackAmt = blackAmt;
	}

	public String getInnerStates() {
		return innerStates;
	}

	public void setInnerStates(String innerStates) {
		this.innerStates = innerStates;
	}

	public String getBeforInnerStates() {
		return beforInnerStates;
	}

	public void setBeforInnerStates(String beforInnerStates) {
		this.beforInnerStates = beforInnerStates;
	}

	public String getSuspiciousStates() {
		return suspiciousStates;
	}

	public void setSuspiciousStates(String suspiciousStates) {
		this.suspiciousStates = suspiciousStates;
	}

	public String getSuspiciousExplain() {
		return suspiciousExplain;
	}

	public void setSuspiciousExplain(String suspiciousExplain) {
		this.suspiciousExplain = suspiciousExplain;
	}

	public String getResendSign() {
		return resendSign;
	}

	public void setResendSign(String resendSign) {
		this.resendSign = resendSign;
	}

    
}
