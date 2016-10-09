package com.dodopal.oss.business.model;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CityCusReport implements Serializable {

	private static final long serialVersionUID = 183794820177316817L;
	
	private String cityCode;		// 城市编号
	private String cityName;		// 城市名字
	private String yearMonthStr;	// 年月字符串
	private int orderNum;			// 交易笔数
	private double orderAmount;		// 交易总量
	
	private int activePosNum;		// 活跃POS机
	private int activeCardNum;		// 活跃卡数
	private int avgCardNumPerPos;	// 平均:充值卡片数/每台
	private double amountPerPos;		// 平均:充值金额/每台
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getYearMonthStr() {
		return yearMonthStr;
	}
	public void setYearMonthStr(String yearMonthStr) {
		this.yearMonthStr = yearMonthStr;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getActivePosNum() {
		return activePosNum;
	}
	public void setActivePosNum(int activePosNum) {
		this.activePosNum = activePosNum;
	}
	public int getActiveCardNum() {
		return activeCardNum;
	}
	public void setActiveCardNum(int activeCardNum) {
		this.activeCardNum = activeCardNum;
	}
	public int getAvgCardNumPerPos() {
		return avgCardNumPerPos;
	}
	public void setAvgCardNumPerPos(int avgCardNumPerPos) {
		this.avgCardNumPerPos = avgCardNumPerPos;
	}
	public double getAmountPerPos() {
		return amountPerPos;
	}
	public void setAmountPerPos(double amountPerPos) {
		this.amountPerPos = amountPerPos;
	}
	public String getYearMonthStrFmt() {
		return this.getYearMonthStr().substring(0, 4)+"年"+this.getYearMonthStr().substring(4)+"月";
	}
	public String getOrderAmountFmt() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(this.getOrderAmount()/100);
	}
	public String getAmountPerPosFmt() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(this.getAmountPerPos()/100);
	}
	
}
