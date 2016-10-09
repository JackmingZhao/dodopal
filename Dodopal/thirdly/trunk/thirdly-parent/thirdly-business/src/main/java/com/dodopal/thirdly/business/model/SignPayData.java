package com.dodopal.thirdly.business.model;

import java.io.Serializable;

public class SignPayData implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -1142315015277163741L;
    //
    private String input_charset;
    private String trandate;
    private String ordernum;
    private String extordernum;
    
    public String getInput_charset() {
        return input_charset;
    }
    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }
    public String getTrandate() {
        return trandate;
    }
    public String getOrdernum() {
        return ordernum;
    }
    public String getExtordernum() {
        return extordernum;
    }
    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
    public void setExtordernum(String extordernum) {
        this.extordernum = extordernum;
    }
    

}
