package com.dodopal.product.business.model.query;


/**
 * 3.12  订单状态查询接口 返回参数项
 * @author dodopal
 *
 */
public class QueryProductOrderStatesRequest {
    
    // 客户号：String[40] 不为空 （都都宝平台生成的业务主键：个人用户：用户号usercode；商户用户：商户号 mercode）
    private String custcode;
    
    // 订单号：String[40] 不为空  (都都宝平台订单号)
    private String ordernum;

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
    
}
