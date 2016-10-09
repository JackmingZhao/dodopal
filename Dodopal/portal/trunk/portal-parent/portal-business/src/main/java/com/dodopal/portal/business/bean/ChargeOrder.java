package com.dodopal.portal.business.bean;

import java.util.Arrays;

import com.dodopal.common.model.BaseEntity;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年3月30日 下午2:35:53 
  */
public class ChargeOrder extends BaseEntity {
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
    public String getStatusStr() {
        String [] success = {"3","4","9","1000000006","1000000007","1000000023"};
        String [] fail = {"-1","0","1","2","5","6","8","10","1000000001","1000000003","1000000022","1000000024"};        
        String [] suspicious = {"7","1000000004"};
        if(Arrays.asList(success).contains(status)){
            return "充值成功";
        }else if(Arrays.asList(fail).contains(status)){
            return "充值失败";
        }else if(Arrays.asList(suspicious).contains(status)){
            return "充值可疑";
        }
        return "";
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
