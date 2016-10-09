package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class CzOrderByPosCountBean extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 80453713530300699L;
    private int tradeCountSum;//    交易总笔数    jiaoyichenggongbishu+jiaoyishibaibishu+jiaoyikeyibishu
    private double tradeMoneySum;//  交易总金额   jiaoyichenggongjine+jiaoyishibaijine+jiaoyikeyijine
    private int tradeSucceedCountSum;//  交易成功笔数  jiaoyichenggongbishu
    private double tradeSucceedMoneySum;//  交易成功金额  jiaoyichenggongjine
    private int tradeErrorCountSum;//  交易失败笔数  jiaoyishibaibishu
    private double tradeErrorMoneySum;//  交易失败金额  jiaoyishibaijine
    private int doubtfulTradeCountSum;//  可疑交易笔数  jiaoyikeyibishu
    private double doubtfulTradeMoneySum;//  可疑交易金额  jiaoyikeyijine
    public int getTradeCountSum() {
        return tradeCountSum;
    }
    public void setTradeCountSum(int tradeCountSum) {
        this.tradeCountSum = tradeCountSum;
    }
    public double getTradeMoneySum() {
        return tradeMoneySum;
    }
    public void setTradeMoneySum(double tradeMoneySum) {
        this.tradeMoneySum = tradeMoneySum;
    }
    public int getTradeSucceedCountSum() {
        return tradeSucceedCountSum;
    }
    public void setTradeSucceedCountSum(int tradeSucceedCountSum) {
        this.tradeSucceedCountSum = tradeSucceedCountSum;
    }
    public double getTradeSucceedMoneySum() {
        return tradeSucceedMoneySum;
    }
    public void setTradeSucceedMoneySum(double tradeSucceedMoneySum) {
        this.tradeSucceedMoneySum = tradeSucceedMoneySum;
    }
    public int getTradeErrorCountSum() {
        return tradeErrorCountSum;
    }
    public void setTradeErrorCountSum(int tradeErrorCountSum) {
        this.tradeErrorCountSum = tradeErrorCountSum;
    }
    public double getTradeErrorMoneySum() {
        return tradeErrorMoneySum;
    }
    public void setTradeErrorMoneySum(double tradeErrorMoneySum) {
        this.tradeErrorMoneySum = tradeErrorMoneySum;
    }
    public int getDoubtfulTradeCountSum() {
        return doubtfulTradeCountSum;
    }
    public void setDoubtfulTradeCountSum(int doubtfulTradeCountSum) {
        this.doubtfulTradeCountSum = doubtfulTradeCountSum;
    }
    public double getDoubtfulTradeMoneySum() {
        return doubtfulTradeMoneySum;
    }
    public void setDoubtfulTradeMoneySum(double doubtfulTradeMoneySum) {
        this.doubtfulTradeMoneySum = doubtfulTradeMoneySum;
    }
   
}
