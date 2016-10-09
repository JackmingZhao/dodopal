package com.dodopal.portal.business.bean.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ProductOrderQueryBean extends QueryBase{

    /**
     * 
     */
    private static final long serialVersionUID = -2113439842584007196L;
    // 订单编号 文本框 精确  用户输入
    private String proOrderNum;

    // 订单状态 下拉框 精确  用户选择    这里指的是订单外部状态
    private String proOrderState;

    // 订单创建时间 日历选择框 范围  用户选择    格式： yyyy-MM-dd HH:MM:SS
    private Date orderDateStart;

    private Date orderDateEnd;
    
    //创建日期
    private String prdOrderDay;

    //卡号    文本框 精确  用户输入 
    private String orderCardno;

    // 业务城市    精确  用户选择
    private String cityName;

    // 充值金额（起/止）   文本输入    范围  用户输入    正数值
    private String txnAmtStart;

    private String txnAmtEnd;
    
    // POS 文本框 精确  用户输入    商户用于充值的POS机编号，仅适用于商户。
    private String posCode;

    // 外部订单号   文本框 精确  用户输入    仅在商户类型为外接商户的时候显示。
    private String merOrderNum;

    // 下面是针对oss的查询
    //  订单内部状态  下拉框 精确  用户选择 
    private String proInnerState;

    //    商户名称    AutoComplete    模糊查询    用户输入    考虑到数据库查询效率，这里要尽量使用AutoComplete的下拉框。
    private String merName;
    // 卡类型 下拉框 精确  用户选择    TODO
    //    充值方式    下拉框 精确  用户选择
    private Integer payWay;

    //    支付类型    下拉框 精确  用户选择    
    private Integer payType;

    //商户号
    private String merCode;
    //通卡公司code
    private String yktCode;
    
    public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getPrdOrderDay() {
        return prdOrderDay;
    }

    public void setPrdOrderDay(String prdOrderDay) {
        this.prdOrderDay = prdOrderDay;
    }

    public String getTxnAmtStart() {
        return txnAmtStart;
    }

    public String getTxnAmtEnd() {
        return txnAmtEnd;
    }

    public void setTxnAmtStart(String txnAmtStart) {
        this.txnAmtStart = txnAmtStart;
    }

    public void setTxnAmtEnd(String txnAmtEnd) {
        this.txnAmtEnd = txnAmtEnd;
    }

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
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

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }

    public String getProInnerState() {
        return proInnerState;
    }

    public void setProInnerState(String proInnerState) {
        this.proInnerState = proInnerState;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    
}
