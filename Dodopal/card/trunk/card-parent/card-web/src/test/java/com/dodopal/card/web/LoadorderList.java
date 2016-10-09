package com.dodopal.card.web;

public class LoadorderList {
    //圈存订单编号
    private String loadOrderNum;
    //圈存外部订单号
    private String loadOrderExtNum;
    //进行圈存的卡号
    private String loadOrderCardNum;
    //进行圈存的商户号
    private String loadOrderMerchantNum;

    public String getLoadOrderNum() {
        return loadOrderNum;
    }

    public void setLoadOrderNum(String loadOrderNum) {
        this.loadOrderNum = loadOrderNum;
    }

    public String getLoadOrderExtNum() {
        return loadOrderExtNum;
    }

    public void setLoadOrderExtNum(String loadOrderExtNum) {
        this.loadOrderExtNum = loadOrderExtNum;
    }

    public String getLoadOrderCardNum() {
        return loadOrderCardNum;
    }

    public void setLoadOrderCardNum(String loadOrderCardNum) {
        this.loadOrderCardNum = loadOrderCardNum;
    }

    public String getLoadOrderMerchantNum() {
        return loadOrderMerchantNum;
    }

    public void setLoadOrderMerchantNum(String loadOrderMerchantNum) {
        this.loadOrderMerchantNum = loadOrderMerchantNum;
    }

}
