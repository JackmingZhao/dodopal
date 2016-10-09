package com.dodopal.payment.business.constant;
/**
 * @description 支付请求常量配置
 */
public class PaymentConstants {
    public static final String INPUT_CHARSET_UTF_8="UTF-8";
    // 支付结果显示URL
    public static final String RESULT_URL = "payresult.htm";
    //第三方支付完成回调、通知URL
    public static final String DODOPAY_PAY_RETURN_NOTIFY_URL = "dodopay.pay.return.notify.url";
    //支付回调
    public static final String DODOPAY_PAY_RETURN= "dodopay.pay.return";
    //项目
    public static final String DODOPAY_PAY_PAYMENT= "dodopay.payment";
    // 支付通知
    public static final String DODOPAY_PAY_NOTIFY= "dodopay.pay.notify";
    // 支付错误通知
    public static final String DODOPAY_PAY_ERROR="dodopay.pay.error";
    //支付宝支付请求URLr
    public static final String DODOPAY_ALIPAY_URL= "dodopay.alipay.url";
    //财付通支付请求URL
    public static final String DODOPAY_TENPAY_URL= "dodopay.tenpay.url";
    //支付请求签名方式
    public static final String SIGN_TYPE = "MD5";
    //支付宝结构类型（即时交易）
    public static final String ALIPAY_INTERFACE_TYPR= "create_direct_pay_by_user";
    // 商品显示URL
    public static final String SHOW_URL = "/";
    
    //支付宝  对应支付配置表的id
    public static final String GW_ALI = "GW_ALI";
    
    //财付通  对应支付配置表的id
    public static final String GW_TENG = "GW_TENG";
    
    //日志服务器名称
    public static final String SERVER_LOG_NAME = "server.log.name";
    
    //日志服务器路径
    public static final String SERVER_LOG_URL = "server.log.url";
    public static final String KEY = "123";
    
    // 退款通知路径
    public static final String DODOPAY_REFUND_PAY_NOTIFY= "dodopay.pay.refund.notify";
    
    // 微信证书路径
    public static final String WX_FILE_PATH= "wx.file.path";
	
}
