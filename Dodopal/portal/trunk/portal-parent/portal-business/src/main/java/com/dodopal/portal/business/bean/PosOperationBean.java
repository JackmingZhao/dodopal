package com.dodopal.portal.business.bean;

import java.io.Serializable;

import com.dodopal.common.enums.PosOperTypeEnum;

public class PosOperationBean implements Serializable {
    private static final long serialVersionUID = 5127912705578698538L;

    private PosOperTypeEnum operation;

    private String []pos;

    private String merCode;

    private String remarks; 
    

    public String[] getPos() {
        return pos;
    }

    public void setPos(String[] pos) {
        this.pos = pos;
    }

    public PosOperTypeEnum getOperation() {
        return operation;
    }

    public void setOperation(PosOperTypeEnum operation) {
        this.operation = operation;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
