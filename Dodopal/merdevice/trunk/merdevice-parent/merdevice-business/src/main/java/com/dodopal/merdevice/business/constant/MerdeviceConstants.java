package com.dodopal.merdevice.business.constant;

public class MerdeviceConstants {
    public static final String TCPPORT="merdevice.tcpserver.port";
    //签到返回报文类型
    public static final String SIGN_ON = "5002";
    //签到返回报文类型
    public static final String SIGN_OUT = "5004";
    //参数下载返回报文类型
    public static final String PARAMETER_DOWNLOAD = "5102";
    //签退结果上传返回报文类型
    public static final String SIGN_OUT_RESULT = "3202";
    //反签密钥
    public static final String INVERSE_KEY ="1A2B4X3Y";
    //加签名
    public static final String SIGN_KEY="3Y4X2B1A";
    //编码格式
    public static final String CHARSET = "UTF-8";
    //2011返回报文类型
    public static final String CHECK_CARD = "2012";
    //2101返回报文类型
    public static final String APPLY_FOR_KEY = "2102";
    //2201返回报文类型
    public static final String RESULT_UPLOAD = "2202";
    //3011返回报文类型
    public static final String CONSUM_CHECK_CARD = "3012";
    //3101返回报文类型3
    public static final String CONSUM_APPLY_FOR_KEY = "3102";
    //3301返回报文类型
    public static final String DISCOUNT_QUERY="3302";
    //3401返回报文类型
    public static final String ACCOUNT_CONSUME="3402";
    //3411返回报文类型
    public static final String ACCOUNT_CONSUME_REVOKE="3412";
    //3421返回报文类型
    public static final String ACCOUNT_CONSUME_RESULT="3422";
    //3501返回报文类型
    public static final String INTEGRAL_CONSUME = "3502";
    //3511返回报文类型
    public static final String INTEGRAL_CONSUME_REVOKE="3512";

    //验签失败
    public static final String CHECK_FAILURE = "020019";
    //根据版本号判断联机还是脱机结果上传
    public static final String VERSION = "01";
    //报文解析异常
    public static final String MESSAGE_ANALYZE_FAILURE = "333333";





    //黑名单参数
    public static final String BLACKLIST="01";
    //消费可用卡类型参数
    public static final String  ConsumeCardTypeParameter="02";
    //黑名单终端运营参数
    public static final String  TerminalParameter="03";
    //区域黑名单参数
    public static final String AreaBlankListParameter="04";
    //增量黑名单参数
    public static final String IncrementBlankListParameter="05";
    //终端菜单参数
    public static final String TerminalMenuParameter="06";
    //灰名单参数
    public static final String GrayListParameter="07";
    //消费折扣参数
    public static final String ConsumeDiscountParameter="33";
    //分时段消费折扣参数
    public static final String SubPeriodDiscountParameter="34";
    //判断是否充值还是查询
    public static final String QUERY_RECHARGE = "9999999999";
    //钱包充值
    public static final String CHARGE_TYPE = "0";
    //成功标识
    public static final String SUCCESS="000000";
}

