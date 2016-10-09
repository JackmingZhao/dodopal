package com.dodopal.product.business.model;

/**
 * 自助终端参数下载返回list：产品库参数
 * 
 * @author dodopal
 *
 */
public class Product {

    /**
     * 产品编号
     */
    private String procode;
    
    /**
     * 产品名称
     */
    private String proname;
    
    /**
     * 产品面价
     */
    private Number proprice;
    
    /**
     * 城市编码
     */
    private String citycode;

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public Number getProprice() {
        return proprice;
    }

    public void setProprice(Number proprice) {
        this.proprice = proprice;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
    
}
