package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class VUserInfo extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 2695553940295140375L;
    private String id;
    private String logName;
    private String nickName;
    private String source;
    public String getId() {
        return id;
    }
    public String getLogName() {
        return logName;
    }
    public String getNickName() {
        return nickName;
    }
    public String getSource() {
        return source;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setLogName(String logName) {
        this.logName = logName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
}
