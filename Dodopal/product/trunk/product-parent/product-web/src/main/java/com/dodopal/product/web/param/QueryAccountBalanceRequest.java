package com.dodopal.product.web.param;

public class QueryAccountBalanceRequest extends BaseRequest{
  
    //客户类型  个人用户：设值为0 固定   ,商户用户：设置为 1 固定
    private String  custtype;
    
    //客户号   都都宝平台生成的业务主键, 个人用户：用户号usercode、 商户用户：商户号 mercode
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
