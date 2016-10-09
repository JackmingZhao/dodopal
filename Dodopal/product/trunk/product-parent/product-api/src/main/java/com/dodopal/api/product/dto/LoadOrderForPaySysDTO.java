package com.dodopal.api.product.dto;

import java.io.Serializable;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年4月13日 上午11:56:16 
  * @version 1.0 
  * @parameter  自助终端生成圈存订单的DTO
  */
public class LoadOrderForPaySysDTO  implements Serializable{
    private static final long serialVersionUID = 5517967318030172836L;
    // 交易流水号
    private String sourceOrderNum;
    
    // 一卡通卡面号
    private String cardNum;
    
    // 产品编号
    private String productNum;
    
    // 商户编号
    private String customerNum;
    // 来源
    private String source;
    public String getSourceOrderNum() {
        return sourceOrderNum;
    }
    public void setSourceOrderNum(String sourceOrderNum) {
        this.sourceOrderNum = sourceOrderNum;
    }
    public String getCardNum() {
        return cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public String getProductNum() {
        return productNum;
    }
    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
    public String getCustomerNum() {
        return customerNum;
    }
    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
}
