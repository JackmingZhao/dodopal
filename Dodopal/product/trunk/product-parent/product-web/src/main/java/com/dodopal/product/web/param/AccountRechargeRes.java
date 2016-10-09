/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.web.param;

/**
 * Created by lenovo on 2015/11/6.
 */
public class AccountRechargeRes extends BaseResponse {
    private String paymentTranNo;//交易流水号

    public String getPaymentTranNo() {
        return paymentTranNo;
    }

    public void setPaymentTranNo(String paymentTranNo) {
        this.paymentTranNo = paymentTranNo;
    }
}
