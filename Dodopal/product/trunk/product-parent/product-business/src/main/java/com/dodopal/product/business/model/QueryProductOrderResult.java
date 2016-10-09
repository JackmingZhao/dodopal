package com.dodopal.product.business.model;


/**
 * 3.6  公交卡充值订单查询接口 返回参数项
 * @author dodopal
 *
 */
public class QueryProductOrderResult {
    
	// 订单状态：都都宝平台公交卡充值订单状态
	private String orderstates;
	
	// 订单编号：都都宝平台一卡通充值订单编号
    private String ordernum;
    
    // 城市：业务城市
    private String cityname;
    
    // 充值金额：单位：分
    private String txnamt;
    
    // 客户实付金额（DDP实收金额）：单位：分
    private String receivedprice;
    
    // 充值前金额：单位：分
    private String befbal;
    
    // 充值后金额：单位：分
    private String blackamt;
    
    // 卡号：充值公交卡号
    private String cardnum;
    
    // 订单创建时间：格式：yyyy-MM-dd mm:ss
    private String orderdate;

    public String getOrderstates() {
        return orderstates;
    }

    public void setOrderstates(String orderstates) {
        this.orderstates = orderstates;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getReceivedprice() {
        return receivedprice;
    }

    public void setReceivedprice(String receivedprice) {
        this.receivedprice = receivedprice;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getBlackamt() {
        return blackamt;
    }

    public void setBlackamt(String blackamt) {
        this.blackamt = blackamt;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
}
