package com.dodopal.product.web.param;

public class LoadOrderResponse extends BaseResponse{
    
    //圈存订单号
    private String loadorderNum;
    
    /**
     * 支付方式
     */
    private String payid;
    
    /**
     * 交易流水号
     */
    private String tradeNum;
    
    /**
     * 支付方式类型
     */
    private String paytype;
    
    /**
     * 金额
     */
    private Number amont;
    

    public Number getAmont() {
        return amont;
    }

    public void setAmont(Number amont) {
        this.amont = amont;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getLoadorderNum() {
		return loadorderNum;
	}

	public void setLoadorderNum(String loadorderNum) {
		this.loadorderNum = loadorderNum;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LoadOrderResponse [loadorderNum=" + loadorderNum + ", payid=" + payid + ", tradeNum=" + tradeNum + ", paytype=" + paytype + ", amont=" + amont + "]";
    }
    
    

}
