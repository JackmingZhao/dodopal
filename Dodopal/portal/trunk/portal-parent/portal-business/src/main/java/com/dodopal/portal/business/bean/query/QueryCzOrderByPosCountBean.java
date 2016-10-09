package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

public class QueryCzOrderByPosCountBean extends  QueryBase{
    /**
     * 
     */
    private static final long serialVersionUID = 3330546397354343779L;
    
    private String    mchnitid;    //商户号
    private String    startdate;   //查询起始时间
    private String    enddate; //查询结束时间
    public String getMchnitid() {
        return mchnitid;
    }
    public void setMchnitid(String mchnitid) {
        this.mchnitid = mchnitid;
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
