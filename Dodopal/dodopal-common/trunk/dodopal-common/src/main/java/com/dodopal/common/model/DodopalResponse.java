package com.dodopal.common.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.service.ResponseMessageService;
import com.dodopal.common.util.SpringBeanUtil;

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

    private String version;
    
    
    public String getCode() {
        return code;
    }

    public T getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(T responseEntity) {
        this.responseEntity = responseEntity;
    }

    public void setCode(String code) {
        this.code = code;
        ResponseMessageService service = (ResponseMessageService) SpringBeanUtil.getBean("responseMessageService");
        if (service != null) {
            setMessage(service.getMessgaeByCode(code));
        } else {
            setMessage(null);
        }
    }
    // 北京通卡错误码显示处理
    public void setBJNewMessage(String code, String message) {
        ResponseMessageService service = (ResponseMessageService) SpringBeanUtil.getBean("responseMessageService");
        if (service != null) {
            // 通卡错误码（范围：000001~010000）
            if (StringUtils.isNotBlank(this.code) && Integer.parseInt(this.code)<=10000){
                // 充值失败！（无效的消息类型码，错误码：0001）
                String code16 = Integer.toHexString(Integer.parseInt(this.code));
                if (code16.length()<4) {
                    for (int i = 3;i>0;i--) {
                        code16 = "0"+code16;
                        if (code16.length() == 4) {
                            break;
                        }
                    }
                }
                setMessage(service.getMessgaeByCode(code) +"！（"+message+"，错误码:" + code16+"）");
            } else {
                setMessage(service.getMessgaeByCode(code) +"错误码:" + this.code);
            }
        } else {
            setMessage(null);
        }      
    }
    public void setNewMessage(String code) {
        ResponseMessageService service = (ResponseMessageService) SpringBeanUtil.getBean("responseMessageService");
        if (service != null) {
            setMessage(service.getMessgaeByCode(code) +"错误码:" + this.code);
        } else {
            setMessage(null);
        }      
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public boolean isSuccessCode(){
        return ResponseCode.SUCCESS.equals(this.code);
    }
    
    @Override
    public String toString() {
        return "DodopalResponse [responseEntity=" + responseEntity + ", code=" + code + ", message=" + message + "]";
    }

}
