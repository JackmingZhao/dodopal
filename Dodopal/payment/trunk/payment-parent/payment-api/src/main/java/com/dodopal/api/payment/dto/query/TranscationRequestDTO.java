package com.dodopal.api.payment.dto.query;

import java.io.Serializable;
/**
 * 查询交易记录 （手机端和VC端）
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 *
 */
public class TranscationRequestDTO implements Serializable{

    private static final long serialVersionUID = 2388835915079744274L;

    //客户类型 个人用户：设值为0 固定  ，商户用户：设置为 1 固定    ，不可空
    private String custtype;

    //客户号 都都宝平台生成的业务主键  个人用户：用户号usercode, 商户用户：商户号 mercode
    private String custcode;

    //用户id 用登录时返回的userid
    private String userid;

    //起始日期 交易日期格式：yyyy-MM-dd 建议默认日期
    private String startdate;

    //截止日期 交易日期格式：yyyy-MM-dd 建议默认日期
    private String enddate;

    //交易类型    1:账户充值,3:账户消费,5:退款, 7:转出, 9:转入
    private String trantype;
    
    //起始金额  交易金额 单位：“分“
    private Number startamt;

    //截止金额  交易金额  单位：“分“
    private Number endamt;
    
    //交易状态 0:待支付,1:已取消,2:支付中,3:已支付,4:待退款,5:已退款, 6:待转账,7:转账成功, 8:关闭
    private String  tranoutstatus;

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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public Number getStartamt() {
        return startamt;
    }

    public void setStartamt(Number startamt) {
        this.startamt = startamt;
    }

    public Number getEndamt() {
        return endamt;
    }

    public void setEndamt(Number endamt) {
        this.endamt = endamt;
    }

    public String getTranoutstatus() {
        return tranoutstatus;
    }

    public void setTranoutstatus(String tranoutstatus) {
        this.tranoutstatus = tranoutstatus;
    }
    
    
    

}
