package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;

public class TransferMerchant implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2306715418441566615L;
    private String mchntid;
    private String fundType;
    public String getMchntid() {
        return mchntid;
    }
    public void setMchntid(String mchntid) {
        this.mchntid = mchntid;
    }
    public String getFundType() {
        return fundType;
    }
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }


}
