package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

public class YktPsamBatchInfo extends BaseEntity {

    private static final long serialVersionUID = -5619021026981306059L;
    
    //excel行号
    private int rowNum;
    
    //psam号
    private String samNo;

    //通卡公司code
    private String yktCode;

    //城市code
    private String cityCode;
    
    //通卡商户号
    private String mchntid;
    
    //商户号
    private String merCode;

    //终端编码 POS号
    private String posId;

    //POS类型
    private String posType;
    
    //通卡公司名称
    private String yktName;

    //城市名称
    private String cityName;
    
    //商户名称
    private String merName;
    
    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getMchntid() {
        return mchntid;
    }

    public void setMchntid(String mchntid) {
        this.mchntid = mchntid;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

}
