package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class ProxyCardAddQueryDTO extends QueryBase {

    private static final long serialVersionUID = 600114313381225889L;
    //网点编号
    private String proxyid;
    //订单号
    private String proxyorderno;
    //卡号
    private String cardno;
    //Pos编号
    private String posid;
    //城市
    private String cityno;
    //订单状态
    private String status;
    
    //查询起始时间
    private String startdate;
    //查询结束时间
    private String enddate;
    public String getProxyid() {
        return proxyid;
    }
    public void setProxyid(String proxyid) {
        this.proxyid = proxyid;
    }
    public String getProxyorderno() {
        return proxyorderno;
    }
    public void setProxyorderno(String proxyorderno) {
        this.proxyorderno = proxyorderno;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getCityno() {
        return cityno;
    }
    public void setCityno(String cityno) {
        this.cityno = cityno;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    
    
}
