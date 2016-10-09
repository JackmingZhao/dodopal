/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.web.param;

/**
 * Created by lenovo on 2015/11/2.
 */
public class AccountRecharge extends BaseRequest {
    private String custcode;//客户编号
    private String custtype;//客户类型
    private String source;//来源
    private  String amont;//交易金额
    private String payid;//支付方式id
    private String userid;//用户id

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAmont() {
        return amont;
    }

    public void setAmont(String amont) {
        this.amont = amont;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
