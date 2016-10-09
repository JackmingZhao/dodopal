package com.dodopal.product.web.param;
/**
 * @author tao
 */
public class CityFindRequest extends BaseRequest {
    // 客户类型
    private String custtype;
    //客户号
    private String custcode;
    public String getCusttype() {
        return custtype;
    }
    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }
    public String getCustcode() {
        return custcode;
    }
    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }
}
