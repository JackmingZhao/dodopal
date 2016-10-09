package com.dodopal.common.service;

public interface ResponseMessageService {

    String BEAN_NAME ="responseMessageService";
    
    public String getMessgaeByCode(String code);
    
    /**
     * 重新加载map
     */
    public void doReload();

}
