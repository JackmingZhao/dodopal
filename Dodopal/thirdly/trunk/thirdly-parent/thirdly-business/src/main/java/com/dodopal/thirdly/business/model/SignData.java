package com.dodopal.thirdly.business.model;

import java.io.Serializable;

public class SignData implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3104923410016441467L;
    //
    private String input_charset;
    //商户号
    private String  mercode;
    // 业务编码
    private String businesscode ;
    // 交易时间
    private String trandate;
    // 操作员编号
    private String operationcode;
    
    
    public String getInput_charset() {
        return input_charset;
    }
    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }
    public String getOperationcode() {
        return operationcode;
    }
    public void setOperationcode(String operationcode) {
        this.operationcode = operationcode;
    }
    public String getMercode() {
        return mercode;
    }
    public String getBusinesscode() {
        return businesscode;
    }
    public String getTrandate() {
        return trandate;
    }
    public void setMercode(String mercode) {
        this.mercode = mercode;
    }
    public void setBusinesscode(String businesscode) {
        this.businesscode = businesscode;
    }
    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }
    
    

}
