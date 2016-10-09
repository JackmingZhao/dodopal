package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class CzOrderByPosBean extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -5958532151139816542L;
    private String posid;//    posid    POS号
    private String username;//    username    用户名称
    private int tradeCount;//    jiaoyichenggongbishu+jiaoyishibaibishu+jiaoyikeyibishu  交易总笔数
    private double tradeMoney;//    jiaoyichenggongjine+jiaoyishibaijine+jiaoyikeyijine 交易总金额
    private int tradeSucceedCount;//    jiaoyichenggongbishu    交易成功笔数
    private double tradeSucceedMoney;//    jiaoyichenggongjine 交易成功金额
    private int tradeErrorCount;//    jiaoyishibaibishu   交易失败笔数
    private double tradeErrorMoney;//    jiaoyishibaijine    交易失败金额
    private int doubtfulTradeCount;//    jiaoyikeyibishu 可疑交易笔数
    private double doubtfulTradeMoney;//    jiaoyikeyijine  可疑交易金额
    
    
    
 
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getTradeCount() {
        return tradeCount;
    }
    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }
    public double getTradeMoney() {
        return tradeMoney;
    }
    public void setTradeMoney(double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }
    public int getTradeSucceedCount() {
        return tradeSucceedCount;
    }
    public void setTradeSucceedCount(int tradeSucceedCount) {
        this.tradeSucceedCount = tradeSucceedCount;
    }
    public double getTradeSucceedMoney() {
        return tradeSucceedMoney;
    }
    public void setTradeSucceedMoney(double tradeSucceedMoney) {
        this.tradeSucceedMoney = tradeSucceedMoney;
    }
    public int getTradeErrorCount() {
        return tradeErrorCount;
    }
    public void setTradeErrorCount(int tradeErrorCount) {
        this.tradeErrorCount = tradeErrorCount;
    }
    public double getTradeErrorMoney() {
        return tradeErrorMoney;
    }
    public void setTradeErrorMoney(double tradeErrorMoney) {
        this.tradeErrorMoney = tradeErrorMoney;
    }
    public int getDoubtfulTradeCount() {
        return doubtfulTradeCount;
    }
    public void setDoubtfulTradeCount(int doubtfulTradeCount) {
        this.doubtfulTradeCount = doubtfulTradeCount;
    }
    public double getDoubtfulTradeMoney() {
        return doubtfulTradeMoney;
    }
    public void setDoubtfulTradeMoney(double doubtfulTradeMoney) {
        this.doubtfulTradeMoney = doubtfulTradeMoney;
    }
  
}
