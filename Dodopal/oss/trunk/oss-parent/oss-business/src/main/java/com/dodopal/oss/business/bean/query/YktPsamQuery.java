package com.dodopal.oss.business.bean.query;

import com.dodopal.common.model.QueryBase;

public class YktPsamQuery extends QueryBase{

    private static final long serialVersionUID = -921477790554805870L;


    //Sam卡号
    private String samNo;
    
    
    //通卡公司code
    private String yktCode;

    //通卡公司名称
    private String yktName;
    
    //通卡商户号
    private String mchntid;
    
    //终端编码 POS号
    private String posId;

    public String getSamNo() {
        return samNo;
    }

    public void setSamNo(String samNo) {
        this.samNo = samNo;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getMchntid() {
        return mchntid;
    }

    public void setMchntid(String mchntid) {
        this.mchntid = mchntid;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
    
    
}
