package com.dodopal.product.web.param;

/**
 * 3.12  订单状态查询接口 请求参数
 * @author dodopal
 *
 */
public class QueryProductOrderStatesRequestDTO extends BaseRequest {
	
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
