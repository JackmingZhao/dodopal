package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

public class ProxyOffLineOrderTbQuery  extends QueryBase{
    private static final long serialVersionUID = -4093081955022107901L;
    private String posid;//Pos编号
    private String proxyid;//网点编号
    private String startdate;//查询起始时间
    private String enddate;//查询结束时间
    private String status;//状态
    private String timeRadio;//选择时间段
    
    public String getTimeRadio() {
        return timeRadio;
    }
    public void setTimeRadio(String timeRadio) {
        this.timeRadio = timeRadio;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getProxyid() {
        return proxyid;
    }
    public void setProxyid(String proxyid) {
        this.proxyid = proxyid;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
