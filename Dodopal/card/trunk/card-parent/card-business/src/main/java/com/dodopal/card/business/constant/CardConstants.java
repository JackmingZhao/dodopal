package com.dodopal.card.business.constant;

public class CardConstants {

    //交易类型充值
    public final static int CARD_LOG_TXNTYPE_CHARGE = 1;
    //交易类型消费
    public final static int CARD_LOG_TXNTYPE_CONSUME = 2;
    //交易类型其他
    public final static int CARD_LOG_TXNTYPE_OTHER = 3;
    //apdudata失败
    public final static String CARD_APDUDATA_FAIL = "FFFFFFFFFFFFFFFF";

    public final static String BJ_CARD_APDUDATA_FAIL = "FFFFFFFF";
    //卡服务名称
    public final static String SYSTEM_NAME_CARD = "CARD";
    //昆山一卡通代码
    public final static String KUNSHAN_YKTCODE = "215300";

    //北京nfc前置url
    public final static String BJ_NFC_CITY_FRONT_URL = "http://localhost:8080/nfcservice/charge/100000";//本地测试

    //北京nfc用户终端信息登记
    public final static String BJ_NFC_USER_TEMINAL_REGISTER = "/userTeminalRegister.do?";
    //北京nfc充值验卡
    public final static String BJ_NFC_CHARGE_VALIDATE_CARD = "/chargeValidateCard.do?";
    //北京nfc充值起始
    public final static String BJ_NFC_CHARGE_START = "/chargeStart.do?";
    //北京nfc充值后续
    public final static String BJ_NFC_CHARGE_FOLLOW = "/chargeFollow.do?";
    //北京nfc结果上传
    public final static String BJ_NFC_CHARGE_RESULTUP = "/chargeResultUp.do?";
    //北京nfc补充值申请起始
    public final static String BJ_NFC_RECHARGE_APPLY_START = "/reChargeApplyStart.do?";
    //北京nfc补充值申请后续
    public final static String BJ_NFC_RECHARGE_APPLY_FOLLOW = "/reChargeApplyFollow.do?";
}
