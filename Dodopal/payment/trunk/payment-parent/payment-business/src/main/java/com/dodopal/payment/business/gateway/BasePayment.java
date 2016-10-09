/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.gateway;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.service.PayTranService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 支付基类(此处用于定义支付过程中通用参数)
 * Created by lenovo on 2015/8/5.
 */
public abstract class BasePayment {
    //支付请求币种选择
    public enum CurrencyType {
        CNY
    };
    /**
     * 获取支付请求URL
     * @return String
     */
    public abstract String getPaymentUrl();

    /**
     * @param paymentConfig
     * @return
     * @description 获取调用支付接口所需参数
     */
    public abstract Map<String, String> getParameterMap(PayConfig paymentConfig, PayTraTransaction payTraTransaction, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * @param paymentConfig
     * @param httpServletRequest
     * @return boolean
     * @description 验证参数签名是否成功
     */
    public abstract boolean verifySign(PayConfig paymentConfig,String sign,Map requestParams);

    /**
     * @param httpServletRequest
     * @return 是否支付成功
     * @description 判断是否支付成功
     */
    public abstract boolean isPaySuccess(HttpServletRequest httpServletRequest);

    /**
     * 支付回调URL
     * @return string
     */
    public static String getReturnUrl() {
        return DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_RETURN_NOTIFY_URL) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_PAYMENT) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_RETURN);

    }

    /**
     * @return String
     * @description 获取支付回调通知URL
     */
    public static String getNotifyUrl() {
        return DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_RETURN_NOTIFY_URL) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_PAYMENT) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_NOTIFY);
    }


    /**
     * @return String
     * @description 获取退款回调通知URL
     */
    public static String getRefundNotifyUrl() {       
        return DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_RETURN_NOTIFY_URL) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_PAY_PAYMENT) +
                DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_REFUND_PAY_NOTIFY);
    }
    
    /**
     * @param sPara
     * @param paymentUrl
     * @return
     * @description 建立请求，以表单HTML形式构造（默认）
     */
    public static String buildRequest(Map<String, String> sPara, String paymentUrl) {
        //待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + paymentUrl
                + "\" method=\"" + "get"+ "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        sbHtml.append("</form>");
//        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
    
    
    /**
     * 退款接口
     * @param paymentTranNo  需要退款的交易流水号
     * @param reFee 退款金额
     * @param outOrederNo  第三方订单号
     * @return
     */
    public abstract String sendRefund(PayConfig paymentConfig,String paymentTranNo,double reFee,String outOrederNo,PayTranService payTranService);
    
    
    /**7
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    public  NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
      
        return nameValuePair;
    }
    
    /**
     * 将NameValuePairs数组转变为字符串
     * 
     * @param nameValues
     * @return
     */
    protected static String toString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];

            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }
}