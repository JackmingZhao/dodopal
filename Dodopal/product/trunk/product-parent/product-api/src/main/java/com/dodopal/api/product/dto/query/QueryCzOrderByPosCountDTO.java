package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class QueryCzOrderByPosCountDTO extends  QueryBase{
    /**
     * 
     */
    private static final long serialVersionUID = -8912278420441657151L;
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
