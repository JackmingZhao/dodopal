package com.dodopal.product.web.param;



/**
 * 3.12  订单状态查询接口 返回参数
 * @author dodopal
 *
 */
public class QueryProductOrderStatesResponse extends BaseResponse {
    
    // 订单状态：都都宝平台公交卡充值订单状态
    private String proorderstates;
    
    // 订单内部状态：都都宝平台订单内部状态
    private String proinnerstates;

    public String getProorderstates() {
        return proorderstates;
    }

    public void setProorderstates(String proorderstates) {
        this.proorderstates = proorderstates;
    }

    public String getProinnerstates() {
        return proinnerstates;
    }

    public void setProinnerstates(String proinnerstates) {
        this.proinnerstates = proinnerstates;
    }
}
