/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.gateway;

import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.gateway.httpClient.HttpProtocolHandler;
import com.dodopal.payment.business.gateway.httpClient.HttpRequest;
import com.dodopal.payment.business.gateway.httpClient.HttpResponse;
import com.dodopal.payment.business.gateway.httpClient.HttpResultType;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.util.SignUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description 支付宝交易处理(即时，网银区别字段)
 * Created by lenovo on 2015/8/4.
 */
public class AliPayMode extends BasePayment{
    private static Logger log = Logger.getLogger(AliPayMode.class);
    /**
     * 获取支付请求URL
     * @return String
     */
    @Override
    public String getPaymentUrl() {
        return DodopalAppVarPropsUtil.getStringProp(PaymentConstants.DODOPAY_ALIPAY_URL);
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
        //获取支付配置信息，以及支付所需订单信息
        String _input_charset = PaymentConstants.INPUT_CHARSET_UTF_8;// 字符集编码格式（UTF-8、GBK）
        String body = payTraTransaction.getTranName();// 订单描述
        String subject = payTraTransaction.getCommodity();// 订单的名称、标题、关键字等
        String defaultbank = paymentConfig.getDefaultBank();// 默认选择银行（当paymethod为bankPay时有效）
        String notify_url =getNotifyUrl()+"?paymentTranNo="+payTraTransaction.getTranCode();// 消息通知URL
        String return_url = getReturnUrl()+"?paymentTranNo="+payTraTransaction.getTranCode();// 回调处理URL
        String out_trade_no = payTraTransaction.getOrderNumber();// 订单号
        String partner = paymentConfig.getAnotherAccountCode();// 合作身份者ID
        String payment_type = "1";// 支付类型（固定值：1）
        String paymethod = "directPay";// 默认支付方式（bankPay：网银、cartoon：卡通、directPay：余额、CASH：网点支付）//TODO 注意封装
        String seller_id = paymentConfig.getAnotherAccountCode();;// 商家ID
        String service = PaymentConstants.ALIPAY_INTERFACE_TYPR;// 接口类型（create_direct_pay_by_user：即时交易）
        String show_url =PaymentConstants.SHOW_URL;// 商品显示URL
        String total_fee = String.valueOf((double)payTraTransaction.getRealTranMoney()/100);// 总金额（单位：元）数据库取出来的是分为单位的，在这里转换成元
        String key = paymentConfig.getPayKey();// 密钥
        //发送支付接口所需参数组装
        Map<String, String> signMap = new LinkedHashMap<String, String>();
        //根据defaultbank判断是否需要走支付宝的银行网关接口，以及paymethod所需值设置
        if(defaultbank!=null && !"".equals(defaultbank)){
            paymethod = "bankPay";
            signMap.put("defaultbank",defaultbank);
        }
        signMap.put("_input_charset", _input_charset);
        signMap.put("body", body);
        signMap.put("notify_url", notify_url);
        signMap.put("out_trade_no", out_trade_no);
        signMap.put("partner", partner);
        signMap.put("payment_type", payment_type);
        signMap.put("paymethod", paymethod);
        signMap.put("return_url", return_url);
        signMap.put("seller_id", seller_id);
        signMap.put("service", service);
        signMap.put("show_url", show_url);
        signMap.put("subject", subject);
        signMap.put("total_fee", total_fee);
        //对发送参数进行有效签名
        String sign = SignUtils.sign(SignUtils.createLinkString(signMap), key, _input_charset);
        if(log.isInfoEnabled()){
            log.info("发送支付接口签名数据为=="+sign);
        }
        signMap.put("sign", sign);
        if(log.isInfoEnabled()){
            log.info("发送支付接口参数=="+signMap.toString());
        }
        return signMap;
    }

    /**
     * @description 验签方法，将返回来的参数重新加密和sign对比
     * @param paymentConfig
     * @param sign，requestParams
     * @return boolean
     * @comments 将返回参数重新加密和返回的sign进行对比，一致则通过
     */
    @Override
    public boolean verifySign(PayConfig paymentConfig,String sign,Map requestParams) {
        if(log.isInfoEnabled()){
            log.info("支付返回数据所带签名为=="+sign);
        }
        //将返回参数重新加密和返回的sign进行对比，一致则通过
        if (SignUtils.verify(SignUtils.createLinkString(requestParams, new String[]{"sign_type","sign","paymentTranNo"})+paymentConfig.getPayKey(), sign)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @description 判断支付是否成功
     * @param httpServletRequest
     * @return
     * @comments 返回的trade_status为TRADE_FINISHED或者TRADE_SUCCESS时认为支付已经成功
     */
    @Override
    public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return false;
        }
        //获取支付状态
        String tradeStatus = httpServletRequest.getParameter("trade_status");
        if(log.isInfoEnabled()){
            log.info("支付返回状态为:"+tradeStatus);
        }
        if (StringUtils.equals(tradeStatus, "TRADE_FINISHED") || StringUtils.equals(tradeStatus, "TRADE_SUCCESS")) {
            return true;
        } else {
            return false;
        }
    }

	@Override
	public String sendRefund(PayConfig paymentConfig,String paymentTranNo, double reFee,
			String outOrederNo,PayTranService payTranService) {
		
		
		log.info("-----send to alipay refund start---");
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		String _input_charset = PaymentConstants.INPUT_CHARSET_UTF_8;// 字符集编码格式（UTF-8、GBK）
		String partner = paymentConfig.getAnotherAccountCode();//合作商户号
		 String key = paymentConfig.getPayKey();// 密钥
		String resultcode="S";//都都宝操作错误
		String notify_url =getRefundNotifyUrl()+"?paymentTranNo="+paymentTranNo+"";// 消息通知URL
		DecimalFormat df=new DecimalFormat("0.00");
		String refund_fee=df.format(reFee/100);//
		
		//refund_fee="0.01";// TODO
		
		String detail_data=outOrederNo+"^"+refund_fee+"^协商退款"; //支付宝返回交易流水号^金额^描述
		
		String batch_no =DateUtils.getCurrSdTime()+DateUtils.getThree();
		String refund_date=DateUtils.getDateTime();
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", partner);
        sParaTemp.put("_input_charset", _input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", detail_data);
		//调用支付宝接口
		String sHtmlText;
		//签名
		String sign = SignUtils.sign(SignUtils.createLinkString(sParaTemp), key, _input_charset);
		sParaTemp.put("sign", sign);
		
		try {
			//发送请求
			sHtmlText = buildHttpRequest(sParaTemp);
			log.info("---------支付宝返回 alipay back xml sHtmlText:"+sHtmlText);
			if(DDPStringUtil.isPopulated(sHtmlText)){
				Map map=this.parseXml(sHtmlText);
				
				if(map!=null){
					if("T".equals(map.get("recode"))){
						resultcode="T";
						log.info("----支付宝退款成功 alipay back xml is_success:"+map.get("recode"));
					}else if("F".equals(map.get("recode"))){
						resultcode="F";
						log.info("----支付宝退款失败 alipay back xml is_success:"+map.get("recode")+";---错误码为error code："+map.get("reinfo"));
					}
				}else{
					log.info("----支付宝返回xml有误 parse alipay xml is error");
					resultcode="F";
				}
			}else{
				log.info("---------向支付宝发送超时、返回数据超时或其他错误send time out or back xml error");
				resultcode="F";
			}
			
		} catch (Exception e) {
			resultcode="F";
			log.info("---------向支付宝发送超时、返回数据超时或其他错误 send time out or back xml error");
		}
		
		return resultcode;
	}
    
    
    /**
     * 1
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    public  String buildHttpRequest(Map<String, String> sParaTemp) throws Exception {
       

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(PaymentConstants.INPUT_CHARSET_UTF_8);

        request.setParameters(generatNameValuePair(sParaTemp));
        request.setUrl(this.getPaymentUrl());
        log.info("-------向支付宝请求的url:"+request.getUrl());
        HttpProtocolHandler handler =HttpProtocolHandler.getInstance();
        HttpResponse response = handler.execute(request);
        if (response == null) {
            return null;
        }
        
        String strResult = response.getStringResult();

        return strResult;
    }
    
	/**
	 * 解析支付宝返回的xml结果
	 * @param xml
	 * @return
	 */
	public Map parseXml(String xml){
		Map map=new HashMap();
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(xml);
			Element root=doc.getRootElement();
			Iterator alipays=root.elementIterator();
			while(alipays.hasNext()){
				Element e=(Element) alipays.next();
				if("is_success".equals(e.getName())){
					map.put("recode", e.getText());
				}
				if("error".equals(e.getName())){
					 map.put("reinfo", e.getText());
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}
}