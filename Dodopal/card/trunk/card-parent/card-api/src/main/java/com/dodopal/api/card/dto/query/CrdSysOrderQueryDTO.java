package com.dodopal.api.card.dto.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author Administrator
 *
 */
public class CrdSysOrderQueryDTO extends QueryBase {

    /**
     * 
     */
    private static final long serialVersionUID = 4734797354060327561L;
    //卡服务订单号
    private String crdOrderNum;
    //产品库主订单号
    private String proOrderNum;
    //产品编号
    private String proCode;
    //产品名称
    private String proName;
    //商户编号
    private String merCode;
    //商户订单号
    private String merOrderCode;
    //卡服务订单交易状态
    private String crdOrderStates;
    //上次卡服务订单交易状态
    private String crdBeforeorderStates;
    
    public String getCrdBeforeorderStates() {
        return crdBeforeorderStates;
    }
    public void setCrdBeforeorderStates(String crdBeforeorderStates) {
        this.crdBeforeorderStates = crdBeforeorderStates;
    }
    //POS号
    private String posCode;
    //卡号(验卡卡号)
    private String checkCardNo;
    //卡类型
    private String cardType;
    //交易时间
    private String txnDataTimEnd;
    private String txnDataTimStart;
    //交易金额
    private String txnAmtStart;
    private String txnAmtEnd;
    
    public String getTxnAmtStart() {
        return txnAmtStart;
    }
    public String getTxnAmtEnd() {
        return txnAmtEnd;
    }
    public void setTxnAmtStart(String txnAmtStart) {
        this.txnAmtStart = txnAmtStart;
    }
    public void setTxnAmtEnd(String txnAmtEnd) {
        this.txnAmtEnd = txnAmtEnd;
    }
    public String getPosCode() {
        return posCode;
    }
    public String getCheckCardNo() {
        return checkCardNo;
    }
    public String getCardType() {
        return cardType;
    }
    public String getTxnDataTimEnd() {
        return txnDataTimEnd;
    }
    public String getTxnDataTimStart() {
        return txnDataTimStart;
    }
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
    public void setCheckCardNo(String checkCardNo) {
        this.checkCardNo = checkCardNo;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public void setTxnDataTimEnd(String txnDataTimEnd) {
        this.txnDataTimEnd = txnDataTimEnd;
    }
    public void setTxnDataTimStart(String txnDataTimStart) {
        this.txnDataTimStart = txnDataTimStart;
    }
    public String getCrdOrderNum() {
        return crdOrderNum;
    }
    public void setCrdOrderNum(String crdOrderNum) {
        this.crdOrderNum = crdOrderNum;
    }
    public String getProOrderNum() {
        return proOrderNum;
    }
    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getMerOrderCode() {
        return merOrderCode;
    }
    public void setMerOrderCode(String merOrderCode) {
        this.merOrderCode = merOrderCode;
    }
    public String getCrdOrderStates() {
        return crdOrderStates;
    }
    public void setCrdOrderStates(String crdOrderStates) {
        this.crdOrderStates = crdOrderStates;
    }
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
    
  
}
