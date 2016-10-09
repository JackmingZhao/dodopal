package com.dodopal.common.model;


import java.io.Serializable;

public class DodopalResponse<T> implements Serializable {

    private static final long serialVersionUID = -2213923053913105250L;

    /**
     * 响应体
     */
    private T responseEntity;

    /**
     * 响应码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    public T getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(T responseEntity) {
        this.responseEntity = responseEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
