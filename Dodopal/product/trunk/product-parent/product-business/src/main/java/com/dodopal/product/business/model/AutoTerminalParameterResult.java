package com.dodopal.product.business.model;

import java.util.List;

import com.dodopal.product.business.model.PayAway;
import com.dodopal.product.business.model.Product;

/**
 * 自助终端参数下载     返回参数项
 * 
 * @author dodopal
 *
 */
public class AutoTerminalParameterResult {

    /**
     * 产品版本号
     */
    private String proversion;
    
    /**
     * 终端商户号
     */
    private String mercode;
    
    /**
     * 下载参数编号
     */
    private String parno;
    
    /**
     * 各个参数编号对应的对象集合
     */
    private List<Object> listpars;
    
    /**
     * 产品参数对象
     */
    private List<Product> listproduct;
    
    /**
     * 支付方式参数对象
     */
    private List<PayAway> listpayaway;

    public String getProversion() {
        return proversion;
    }

    public void setProversion(String proversion) {
        this.proversion = proversion;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
    }

    public String getParno() {
        return parno;
    }

    public void setParno(String parno) {
        this.parno = parno;
    }

    public List<Object> getListpars() {
        return listpars;
    }

    public void setListpars(List<Object> listpars) {
        this.listpars = listpars;
    }

    public List<Product> getListproduct() {
        return listproduct;
    }

    public void setListproduct(List<Product> listproduct) {
        this.listproduct = listproduct;
    }

    public List<PayAway> getListpayaway() {
        return listpayaway;
    }

    public void setListpayaway(List<PayAway> listpayaway) {
        this.listpayaway = listpayaway;
    }

}
