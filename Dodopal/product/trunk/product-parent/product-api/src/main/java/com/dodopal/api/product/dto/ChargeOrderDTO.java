package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年3月30日 下午2:35:53 
  */
public class ChargeOrderDTO extends BaseEntity {
    private static final long serialVersionUID = 6002015467534129221L;
    /**商户名称 */
    private String mchnitname;
    /**用户名称 */
    private String username;
    /**商户订单号 */
    private String transaction_id;
    /**交易金额(元) */
    private String amt;
    /**返利金额(元) */
    private String rebatesamt;
    /**实收金额(元) */
    private String paidamt;
    /**pos号 */
    private String posid;
    /**充值卡号 */
    private String cardno;
    /**订单时间 */
    private String sendtime;
    /**订单状态*/
    private String status;
    public String getMchnitname() {
        return mchnitname;
    }
    public void setMchnitname(String mchnitname) {
        this.mchnitname = mchnitname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getRebatesamt() {
        return rebatesamt;
    }
    public void setRebatesamt(String rebatesamt) {
        this.rebatesamt = rebatesamt;
    }
    public String getPaidamt() {
        return paidamt;
    }
    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getSendtime() {
        return sendtime;
    }
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    
}
