package com.dodopal.product.business.model;

/**
 * 自助终端参数下载返回list：支付方式参数
 * 
 * @author dodopal
 *
 */
public class PayAway {

    /**
     * 支付方式ID
     */
    private String payid;
    
    /**
     * 支付方式名称
     */
    private String payname;
    
    /**
     * 支付网关类型
     */
    private String bankgatewaytype;
    
    /**
     * 支付服务费率
     */
    private Number payservicerate;
    
    /**
     * 支付手续费率
     */
    private Number payprocerate;

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname;
    }

    public Number getPayservicerate() {
        return payservicerate;
    }

    public void setPayservicerate(Number payservicerate) {
        this.payservicerate = payservicerate;
    }

    public Number getPayprocerate() {
        return payprocerate;
    }

    public void setPayprocerate(Number payprocerate) {
        this.payprocerate = payprocerate;
    }

    public String getBankgatewaytype() {
        return bankgatewaytype;
    }

    public void setBankgatewaytype(String bankgatewaytype) {
        this.bankgatewaytype = bankgatewaytype;
    }
    
}
