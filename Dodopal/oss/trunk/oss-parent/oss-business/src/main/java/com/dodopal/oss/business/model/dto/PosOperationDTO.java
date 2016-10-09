package com.dodopal.oss.business.model.dto;

import java.io.Serializable;

public class PosOperationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 220579019756931290L;

    private String operation;

    private String[] pos;

    private String merCode;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getPos() {
        return pos;
    }

    public void setPos(String[] pos) {
        this.pos = pos;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

}
