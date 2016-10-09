package com.dodopal.users.business.model;

import java.io.Serializable;

/**
 * 类说明 ：
 * @author lifeng
 */

public class MerTypeDisableRelation implements Serializable {
    private static final long serialVersionUID = 3185179818458710497L;
    private String merType;
    private String disableRelationType;

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getDisableRelationType() {
        return disableRelationType;
    }

    public void setDisableRelationType(String disableRelationType) {
        this.disableRelationType = disableRelationType;
    }

}
