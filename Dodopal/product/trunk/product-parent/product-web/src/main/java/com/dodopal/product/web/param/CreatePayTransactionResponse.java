package com.dodopal.product.web.param;

public class CreatePayTransactionResponse extends BaseResponse{
    
    //交易流水号  都都宝平台生成的交易流水号
    private String paymenttranNo;

    public String getPaymenttranNo() {
        return paymenttranNo;
    }

    public void setPaymenttranNo(String paymenttranNo) {
        this.paymenttranNo = paymenttranNo;
    }
    
    

}
