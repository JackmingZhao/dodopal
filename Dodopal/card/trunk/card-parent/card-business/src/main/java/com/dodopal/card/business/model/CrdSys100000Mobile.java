package com.dodopal.card.business.model;

import com.dodopal.common.model.BaseEntity;

public class CrdSys100000Mobile extends BaseEntity {
    private static final long serialVersionUID = -7072092996430002755L;
    //卡服务订单号
    private String crdOrderNum;
    //产品库订单号
    private String prdOrderNum;
    //请求序号
    private String requestno;
    //手机号
    private String mobileno;
    //手机类型
    private String mobiletype;
    //手机IMEI号
    private String mobileimei;
    //手机系统版本
    private String mobilesysver;
    //卡片透支金额
    private String cardoverdraft;
    //卡片启用日期
    private String cardstartdate;
    //卡片失效日期
    private String cardenddate;
    //卡启用标志
    private String cardstartflag;
    //手机imsi号
    private String imsi;
    //卡片处理方式
    private String dealtype;
    //资金来源
    private String paysource;
    //应答序号
    private String responseno;
    //卡号
    private String cardNo;

    public String getCrdOrderNum() {
        return crdOrderNum;
    }

    public void setCrdOrderNum(String crdOrderNum) {
        this.crdOrderNum = crdOrderNum;
    }

    public String getPrdOrderNum() {
        return prdOrderNum;
    }

    public void setPrdOrderNum(String prdOrderNum) {
        this.prdOrderNum = prdOrderNum;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMobiletype() {
        return mobiletype;
    }

    public void setMobiletype(String mobiletype) {
        this.mobiletype = mobiletype;
    }

    public String getMobileimei() {
        return mobileimei;
    }

    public void setMobileimei(String mobileimei) {
        this.mobileimei = mobileimei;
    }

    public String getMobilesysver() {
        return mobilesysver;
    }

    public void setMobilesysver(String mobilesysver) {
        this.mobilesysver = mobilesysver;
    }

    public String getCardoverdraft() {
        return cardoverdraft;
    }

    public void setCardoverdraft(String cardoverdraft) {
        this.cardoverdraft = cardoverdraft;
    }

    public String getCardstartdate() {
        return cardstartdate;
    }

    public void setCardstartdate(String cardstartdate) {
        this.cardstartdate = cardstartdate;
    }

    public String getCardenddate() {
        return cardenddate;
    }

    public void setCardenddate(String cardenddate) {
        this.cardenddate = cardenddate;
    }

    public String getCardstartflag() {
        return cardstartflag;
    }

    public void setCardstartflag(String cardstartflag) {
        this.cardstartflag = cardstartflag;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDealtype() {
        return dealtype;
    }

    public void setDealtype(String dealtype) {
        this.dealtype = dealtype;
    }

    public String getPaysource() {
        return paysource;
    }

    public void setPaysource(String paysource) {
        this.paysource = paysource;
    }

    public String getResponseno() {
        return responseno;
    }

    public void setResponseno(String responseno) {
        this.responseno = responseno;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}
