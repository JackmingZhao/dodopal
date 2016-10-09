package com.dodopal.product.business.bean;

import java.io.Serializable;
//用户绑定的卡片列表
public class CardListResultDTO implements Serializable{
    private static final long serialVersionUID = 8194352081965533465L;
    
    //绑定卡片的数据库ID
    private String id;
    
    //卡号
    private String tradecard;
    
    //卡片名称  用户为卡片设置名称
    private String tradecardname;
    
    //备注  用户卡片的备注
    private String remark;
    
    //绑定时间  yyyyMMddHHmmss
    private String binddate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getBinddate() {
        return binddate;
    }
    public void setBinddate(String binddate) {
        this.binddate = binddate;
    }
    
    

}
