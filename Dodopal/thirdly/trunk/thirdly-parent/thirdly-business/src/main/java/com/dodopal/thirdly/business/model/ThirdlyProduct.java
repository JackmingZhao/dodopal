package com.dodopal.thirdly.business.model;

import java.io.Serializable;

public class ThirdlyProduct implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 729513937687009147L;
    //订单号
    private String prdordernum;
    //外部订单号
    private String extordernum;
    //订单时间 
    private String orderdate;
    //系统回调时间 
    private String returndate;
    //产品编号
    private String productcode;
    //产品名称
    private String productname;
    //卡号
    private String tradecard;
    //POS号
    private String posid;
    //卡余额
    private String befbal;
    //充值金额
    private String tranamt;
    
    
    public String getExtordernum() {
        return extordernum;
    }
    public String getReturndate() {
        return returndate;
    }
    public void setExtordernum(String extordernum) {
        this.extordernum = extordernum;
    }
    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
    public String getPrdordernum() {
        return prdordernum;
    }
    public String getOrderdate() {
        return orderdate;
    }
    public String getProductcode() {
        return productcode;
    }
    public String getProductname() {
        return productname;
    }
    public String getTradecard() {
        return tradecard;
    }
    public String getPosid() {
        return posid;
    }
    public String getBefbal() {
        return befbal;
    }
    public String getTranamt() {
        return tranamt;
    }
    public void setPrdordernum(String prdordernum) {
        this.prdordernum = prdordernum;
    }
    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
    public void setProductname(String productname) {
        this.productname = productname;
    }
    public void setTradecard(String tradecard) {
        this.tradecard = tradecard;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }
    public void setTranamt(String tranamt) {
        this.tranamt = tranamt;
    }
    

}
