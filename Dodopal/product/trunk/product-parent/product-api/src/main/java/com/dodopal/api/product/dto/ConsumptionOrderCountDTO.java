package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class ConsumptionOrderCountDTO extends BaseEntity{
    private static final long serialVersionUID = -3137980236959155239L;

    /**pos号 */
    private String posid;
    /**用户名称 */
    private String username;
    /**交易成功笔数 */
    private String jiaoyichenggongbishu;
    /**交易成功总金额(元)*/
    private String jiaoyichenggongzongjine;
    /**实收总金额(元)*/
    private String shishouzongjine;
    /**结算总金额(元)*/
    private String jiesuanzongjine;
    /**结算总手续费(元)*/
    private String jiesuanzongshouxufei;
    
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getJiaoyichenggongbishu() {
        return jiaoyichenggongbishu;
    }
    public void setJiaoyichenggongbishu(String jiaoyichenggongbishu) {
        this.jiaoyichenggongbishu = jiaoyichenggongbishu;
    }
    public String getJiaoyichenggongzongjine() {
        return jiaoyichenggongzongjine;
    }
    public void setJiaoyichenggongzongjine(String jiaoyichenggongzongjine) {
        this.jiaoyichenggongzongjine = jiaoyichenggongzongjine;
    }
    public String getShishouzongjine() {
        return shishouzongjine;
    }
    public void setShishouzongjine(String shishouzongjine) {
        this.shishouzongjine = shishouzongjine;
    }
    public String getJiesuanzongjine() {
        return jiesuanzongjine;
    }
    public void setJiesuanzongjine(String jiesuanzongjine) {
        this.jiesuanzongjine = jiesuanzongjine;
    }
    public String getJiesuanzongshouxufei() {
        return jiesuanzongshouxufei;
    }
    public void setJiesuanzongshouxufei(String jiesuanzongshouxufei) {
        this.jiesuanzongshouxufei = jiesuanzongshouxufei;
    }
}
