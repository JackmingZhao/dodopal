/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.gateway;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.util.SignUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description 支付宝交易处理(即时，网银区别字段)
 * Created by lenovo on 2015/8/4.
 */
public class TenPayMode extends BasePayment{
    private static Logger log = Logger.getLogger(TenPayMode.class);
    /**
     * 获取支付请求URL
     * @return String
     */
    @Override
    public String getPaymentUrl() {
        return DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_TENPAY_URL);
    }
    /**
     * @description 组装支付所需参数
     * @param paymentConfig
     * @param httpServletRequest
     * @param payTraTransaction
     * @param httpServletResponse
     * @return Map
     * @comments 将支付接口所需参数封装并加密连带密文信息一起
     */
    @Override
    public Map<String, String> getParameterMap(PayConfig paymentConfig, PayTraTransaction payTraTransaction, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String _input_charset = PaymentConstants.INPUT_CHARSET_UTF_8;
        String transactionId = buildTenpayTransactionId(paymentConfig.getAnotherAccountCode(), payTraTransaction.getTranCode());//支付编号
//        String date = payTraTransaction.getOrderDate().toString();// 订单提交时间
        String bank_type = "0";// 银行类型（0：财付通）
        String body = payTraTransaction.getTranName();// 订单描述
        String subject = payTraTransaction.getTranName();// 订单的名称、标题、关键字等
//      String purchaser_id = "";// 客户财付通帐户
        String partner = paymentConfig.getAnotherAccountCode();// 商户号
        String transaction_id = transactionId;// 交易号
        String out_trade_no = payTraTransaction.getOrderNumber();// 订单号
        String total_fee =  String.valueOf(payTraTransaction.getRealTranMoney());// 总金额（单位：分）从数据库取出来的直接时候分为单位，此处不做处理
        String fee_type = "1";// 支付币种（1：人民币）
        String notify_url =getNotifyUrl()+"?paymentTranNo="+payTraTransaction.getTranCode();// 消息通知URL
        String return_url =getReturnUrl() +"?paymentTranNo="+payTraTransaction.getTranCode();// 消息通知URL
        String attach = "dodopal";// 商户数据
        String spbill_create_ip = httpServletRequest.getRemoteAddr();// 客户IP
        String key = paymentConfig.getPayKey();// 密钥

        // 生成签名
        Map<String, String> signMap = new LinkedHashMap<String, String>();
//        if(defaultbank!=null && !"".equals(defaultbank)){
//            paymethod = "bankPay";
//            signMap.put("defaultbank",defaultbank);
//        }
        signMap.put("attach", attach);
        signMap.put("body", body);
        signMap.put("fee_type", fee_type);
        signMap.put("input_charset", _input_charset);
        signMap.put("notify_url", notify_url);
        signMap.put("out_trade_no", out_trade_no);
        signMap.put("partner", partner);
        signMap.put("return_url", return_url);
        signMap.put("spbill_create_ip", spbill_create_ip);
        signMap.put("subject", subject);
        signMap.put("total_fee", total_fee);
        String sign = SignUtils.sign(SignUtils.createLinkString(signMap), "&key="+key,_input_charset);
        // 参数处理
        signMap.put("sign", sign);
        return signMap;
    }

    /**
     * @description 验签方法，将返回来的参数重新加密和sign对比
     * @param paymentConfig
     * @param sign ,requesParams
     * @return boolean
     * @comments 将返回参数重新加密和返回的sign进行对比，一致则通过
     */
    @Override
    public boolean verifySign(PayConfig paymentConfig,String sign,Map requestParams) {
        if (SignUtils.verify(SignUtils.createLinkString(requestParams, new String[]{"sign","paymentTranNo"})+"&key="+paymentConfig.getPayKey(), sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @description 判断支付是否成功
     * @param httpServletRequest
     * @return boolean
     */
    @Override
    public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return false;
        }
        String payResult = httpServletRequest.getParameter("trade_state");
        if (StringUtils.equals(payResult, "0")) {
            return true;
        } else {
            return false;
        }
    }
    // 根据商户号、支付编号生成财付通交易号
    private String buildTenpayTransactionId (String bargainorId, String paymentSn) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = simpleDateFormat.format(new Date());
        int count = 10 - paymentSn.length();
        if (count > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < count; i ++) {
                stringBuffer.append("0");
            }
            stringBuffer.append(paymentSn);
            paymentSn = stringBuffer.toString();
        } else {
            paymentSn = StringUtils.substring(paymentSn, count);
        }
        return bargainorId + dateString + paymentSn;
    }
    
    
    
	@Override
	public String sendRefund(PayConfig paymentConfig,String paymentTranNo, double reFee,
			String outOrederNo,PayTranService payTranService) {
		// TODO Auto-generated method stub
		return null;
	}
}