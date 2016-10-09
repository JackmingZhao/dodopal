package com.dodopal.card.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ParameterQuery extends QueryBase {

    private static final long serialVersionUID = 328991064958325179L;
    
    //商户号
    private String merCode;
    //当天的日期
    private Date today;
    //今天周几
    private String todayWeek;
    //明天的日期
    private Date tomorrow;
    //明天周几
    private String tomorrowWeek;
   //sam卡号
    private String samno;
    
    public String getSamno() {
        return samno;
    }

    public void setSamno(String samno) {
        this.samno = samno;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public String getTodayWeek() {
        return todayWeek;
    }

    public void setTodayWeek(String todayWeek) {
        this.todayWeek = todayWeek;
    }

    public Date getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(Date tomorrow) {
        this.tomorrow = tomorrow;
    }

    public String getTomorrowWeek() {
        return tomorrowWeek;
    }

    public void setTomorrowWeek(String tomorrowWeek) {
        this.tomorrowWeek = tomorrowWeek;
    }
    
}
