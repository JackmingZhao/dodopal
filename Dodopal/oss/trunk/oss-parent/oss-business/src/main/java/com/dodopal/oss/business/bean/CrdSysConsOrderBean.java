package com.dodopal.oss.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.api.card.dto.CrdSysConsOrderDTO;
import com.dodopal.common.enums.CardChargeTypeEnum;
import com.dodopal.common.enums.CardConsumeStatesEnum;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardPosTypeEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.enums.CardTypeEnum;
import com.dodopal.common.enums.SourceEnum;

/**
 * 卡服务消费
 * @author hxc
 */
public class CrdSysConsOrderBean extends CrdSysConsOrderDTO {

    private static final long serialVersionUID = -8172517244036271992L;

    //交易前卡余额
    private String befbalView;
    //交易后卡余额
    private String blackAmtView;
    //交易金额
    private String txnAmtView;

    private String checkCardNoView;

    private String cardLimitView;

    public String getBefbalView() {
        return befbalView;
    }

    public void setBefbalView(String befbalView) {
        this.befbalView = befbalView;
    }

    public String getBlackAmtView() {
        return blackAmtView;
    }

    public void setBlackAmtView(String blackAmtView) {
        this.blackAmtView = blackAmtView;
    }

    public String getTxnAmtView() {
        return txnAmtView;
    }

    public void setTxnAmtView(String txnAmtView) {
        this.txnAmtView = txnAmtView;
    }

    public String getCheckCardNoView() {
        return checkCardNoView;
    }

    public void setCheckCardNoView(String checkCardNoView) {
        this.checkCardNoView = checkCardNoView;
    }

    public String getCardLimitView() {
        return cardLimitView;
    }

    public void setCardLimitView(String cardLimitView) {
        this.cardLimitView = cardLimitView;
    }

    //卡服务订单交易状态
    public String getCrdOrderStatesView() {
        if (StringUtils.isBlank(this.getCrdOrderStates()) || CardConsumeStatesEnum.getNameByCode(this.getCrdOrderStates()) == null) {
            return null;
        }
        return CardConsumeStatesEnum.getNameByCode(this.getCrdOrderStates());
    }

    //卡服务订单交易前状态
    public String getCrdBeforeorderStatesView() {
        if (StringUtils.isBlank(this.getCrdBeforeorderStates()) || CardOrderStatesEnum.getCardOrderStatesByCode(this.getCrdBeforeorderStates()) == null) {
            return null;
        }
        return CardOrderStatesEnum.getCardOrderStatesByCode(this.getCrdBeforeorderStates()).getName();
    }

    //卡类型
    public String getCardTypeView() {
        if (this.getCardType() == null || CardTypeEnum.getCardTypeByCode(this.getCardType().toString()) == null) {
            return null;
        }
        return CardTypeEnum.getCardTypeByCode(this.getCardType().toString()).getName();
    }

    //充值类型
    public String getChargeTypeView() {
        if (this.getChargeType() == null || CardChargeTypeEnum.getChargeTypeByCode(this.getChargeType().toString()).getName() == null) {
            return null;
        }
        return CardChargeTypeEnum.getChargeTypeByCode(this.getChargeType().toString()).getName();
    }

    //设备类型
    public String getPosTypeView() {
        if (this.getPosType() == null || CardPosTypeEnum.getPosTypeByCode(this.getPosType().toString()).getName() == null) {
            return null;
        }
        return CardPosTypeEnum.getPosTypeByCode(this.getPosType().toString()).getName();
    }

    //数据来源
    public String getSourceView() {
        if (this.getSource() == null || SourceEnum.getSourceByCode(this.getSource().toString()).getName() == null) {
            return null;
        }
        return SourceEnum.getSourceByCode(this.getSource().toString()).getName();
    }

    //交易类型
    public String getTxnTypeView() {
        if (this.getTxnType() == null || CardTxnTypeEnum.getCardTxnTypeEnumByCode(this.getTxnType().toString()).getName() == null) {
            return null;
        }
        return CardTxnTypeEnum.getCardTxnTypeEnumByCode(this.getTxnType().toString()).getName();
    }

    //交易结束标志
    public String getTradeEndFlagView() {
        if (this.getTradeEndFlag() == null || CardTradeEndFlagEnum.getEndFlagByCode(this.getTradeEndFlag().toString()).getName() == null) {
            return null;
        }
        return CardTradeEndFlagEnum.getEndFlagByCode(this.getTradeEndFlag().toString()).getName();
    }
}
