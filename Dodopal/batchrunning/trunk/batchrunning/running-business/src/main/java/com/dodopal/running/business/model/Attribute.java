package com.dodopal.running.business.model;

public class Attribute {

    private String url;

    private String code;
    
    private String requiredCheckedNodeId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequiredCheckedNodeId() {
        return requiredCheckedNodeId;
    }

    public void setRequiredCheckedNodeId(String requiredCheckedNodeId) {
        this.requiredCheckedNodeId = requiredCheckedNodeId;
    }
}