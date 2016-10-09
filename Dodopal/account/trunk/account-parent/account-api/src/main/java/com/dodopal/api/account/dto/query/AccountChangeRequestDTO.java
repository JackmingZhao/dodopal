package com.dodopal.api.account.dto.query;

import java.io.Serializable;

public class AccountChangeRequestDTO implements Serializable{
    private static final long serialVersionUID = -8508418379802440407L;

    //客户类型 个人用户：设值为0 固定  ，商户用户：设置为 1 固定    ，不可空
    private String custtype;

    //客户号 都都宝平台生成的业务主键  个人用户：用户号usercode, 商户用户：商户号 mercode
    private String custcode;

    //用户id 用登录时返回的userid
    private String userid;

    //变动类型  都都宝平台账户资金变动类型
    private String changetype;

    //起始日期 资金变动日期格式：yyyy-MM-dd 建议默认日期
    private String stratdate;

    //截止日期 资金变动日期格式：yyyy-MM-dd 建议默认日期
    private String enddate;

    //起始金额  单位：“分“
    private Number stratamt;

    //截止金额  单位：“分“
    private Number endamt;

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

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype;
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
    
    
    

}
