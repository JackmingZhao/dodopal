package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class BJConsumeDiscountHeadDTO extends BaseEntity{
    private static final long serialVersionUID = 6712668992776735787L;

    /**  MessageHeader begin */
    // 消息长度4
    private String messagelen;
    
    // 消息类型size4  U:3401  L:3402
    private String messagetype;
     
    // 版本号size2
    private String ver;
    
    // 发送时间YYYYMMDDhhmmss 
    private String sysdatetime;
    
    // 特殊域启用标志 size1
    private String istsused;
    
    // 特殊域长度 size4
    private String cmdlen;
    
    // 应答码 size6
    private String respcode;
    
    //交易结束标志
    private String tradeendflag;
    /**  MessageHeader end */

    public String getMessagelen() {
        return messagelen;
    }

    public String getTradeendflag() {
        return tradeendflag;
    }

    public void setTradeendflag(String tradeendflag) {
        this.tradeendflag = tradeendflag;
    }

    public void setMessagelen(String messagelen) {
        this.messagelen = messagelen;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSysdatetime() {
        return sysdatetime;
    }

    public void setSysdatetime(String sysdatetime) {
        this.sysdatetime = sysdatetime;
    }

    public String getIstsused() {
        return istsused;
    }

    public void setIstsused(String istsused) {
        this.istsused = istsused;
    }

    public String getCmdlen() {
        return cmdlen;
    }

    public void setCmdlen(String cmdlen) {
        this.cmdlen = cmdlen;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    @Override
    public String toString() {
        return "BJConsumeDiscountHeadDTO [messagelen=" + messagelen + ", messagetype=" + messagetype + ", ver=" + ver + ", sysdatetime=" + sysdatetime + ", istsused=" + istsused + ", cmdlen=" + cmdlen + ", respcode=" + respcode + ", tradeendflag=" + tradeendflag + "]";
    }

    
    
}
