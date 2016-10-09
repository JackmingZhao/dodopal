package com.dodopal.product.web.param;

//用户绑卡
public class CardBindRequest extends BaseRequest{
    
    //用户名  个人注册的用户名（登录帐号）
    private String username;
    
    //来源 手机端：设值为2 , VC端：设值为3
    private String source;
    
    // 卡号 读取的一卡通卡号
    private String tradecard;
    
    //卡片名称 用户可以为卡片设置名称
    private String tradecardname; 
    
    //备注  用户帮卡时可以注释备注
    private String remark;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTradecard() {
        return tradecard;
    }

    public void setTradecard(String tradecard) {
        this.tradecard = tradecard;
    }

    public String getTradecardname() {
        return tradecardname;
    }

    public void setTradecardname(String tradecardname) {
        this.tradecardname = tradecardname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    

}
