package com.dodopal.product.business.model.query;


/**
 * 3.6  公交卡充值订单查询接口 返回参数项
 * @author dodopal
 *
 */
public class QueryProductOrderRequest {
    
    // 客户类型：设值为0 固定；商户用户：设置为 1 固定
    private String custtype;
    
    // 客户号：（都都宝平台生成的业务主键：个人用户：用户号usercode；商户用户：商户号 mercode）
    private String custcode;
    
    // 用户id：用登录时返回的userid
    private String userid;
    
    // 都都宝平台公交卡充值订单状态：（0：待付款；1：已付款；2：充值失败；3：充值中;4：充值成功;5：充值可疑）
    private String orderstates;
    
    // 起始日期:资金变动日期格式：yyyy-MM-dd建议默认日期
    private String stratdate;

    // 截止日期:资金变动日期格式：yyyy-MM-dd建议默认日期
    private String enddate;
    
    // 起始充值金额：单位：分
    private Number stratamt;
    
    // 截止充值金额：单位：分
    private Number endamt;
    
    // 订单号：都都宝平台一卡通充值订单编号
    private String ordernum;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderstates() {
        return orderstates;
    }

    public void setOrderstates(String orderstates) {
        this.orderstates = orderstates;
    }

    public String getStratdate() {
        return stratdate;
    }

    public void setStratdate(String stratdate) {
        this.stratdate = stratdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Number getStratamt() {
        return stratamt;
    }

    public void setStratamt(Number stratamt) {
        this.stratamt = stratamt;
    }

    public Number getEndamt() {
        return endamt;
    }

    public void setEndamt(Number endamt) {
        this.endamt = endamt;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
   
}
