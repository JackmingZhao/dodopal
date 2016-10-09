/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.gateway;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.MD5;
import com.dodopal.payment.business.bean.WxRefundRequestBean;
import com.dodopal.payment.business.bean.WxRefundResponseBean;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.gateway.httpClient.HttpProtocolHandler;
import com.dodopal.payment.business.gateway.httpClient.HttpRequest;
import com.dodopal.payment.business.gateway.httpClient.HttpResponse;
import com.dodopal.payment.business.gateway.httpClient.HttpResultType;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.util.SignUtils;
import com.dodopal.payment.business.util.XMLUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @description 支付宝交易处理(即时，网银区别字段)
 * Created by lenovo on 2015/8/4.
 */
public class WxPayMode extends BasePayment{
    private static Logger log = Logger.getLogger(WxPayMode.class);
    
	//3）退款API
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	//4）退款查询API
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	//HTTPS证书的本地路径(生产环境读取不到此文件？)
	//public static String certLocalPath = WxPayMode.class.getResource("").getPath()+"apiclient_cert.p12";
	//HTTPS证书的本地路径
	public static final String certLocalPath = DodopalAppVarPropsUtil.getStringProp(PaymentConstants.WX_FILE_PATH)+"/apiclient_cert.p12";
    
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
        
     
        String  newSign= SignUtils.sign(paramToSign(requestParams), paymentConfig.getPayKey(), "utf-8");
        log.info("支付返回数据所带签名为==newSign=="+newSign);
         String mdx = paramToSign(requestParams)+"key="+ paymentConfig.getPayKey();
        
        try {
			newSign = MD5.MD5Encode(mdx.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.info("支付返回数据所带签名为==newSign=="+newSign);
        //将返回参数重新加密和返回的sign进行对比，一致则通过
        if (newSign.toUpperCase().equals(sign)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @description 验签方法，将返回来的参数重新加密和sign对比
     * @param paymentConfig
     * @param sign，requestParams
     * @return boolean
     * @comments 将返回参数重新加密和返回的sign进行对比，一致则通过
     */
    
    private String sign(String key,Map requestParams) {
        
        String sign = null;
         String mdx = paramToSign(requestParams)+"key="+ key;
        
        try {
        	sign = MD5.MD5Encode(mdx.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return sign;
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
		
		log.info("-----send to W refund start---");
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		WxRefundRequestBean wxrefund =new WxRefundRequestBean();
		String _input_charset = PaymentConstants.INPUT_CHARSET_UTF_8;// 字符集编码格式（UTF-8、GBK）
		String mch_id = paymentConfig.getAnotherAccountCode();       //合作商户号
		String key = paymentConfig.getPayKey();                      // 密钥
		String appid = "wxef828fd6297e0951";        //账户公众号 TODO
		String nonce_str = DDPStringUtil.getCharacterAndNumber(32);  //随机32位字符串
		String transaction_id = outOrederNo;                         //微信订单号
		String out_trade_no  = paymentTranNo;
		String out_refund_no = "R" + paymentTranNo;                        //退款单号
		
		//reFee = 1;//TODO  测试金额不一样
		DecimalFormat df=new DecimalFormat("0");
		String refund_fee=df.format(reFee);                          //退款金额		
		String total_fee=refund_fee;                                 //退款总金额金额 "分"
		String refund_fee_type = "CNY";                              //货币种类
		String op_user_id = mch_id;                                  //操作员帐号, 默认为商户号		
		String resultcode="S";//都都宝操作错误
		
		//XML 对象
		wxrefund.setMch_id(mch_id);
		wxrefund.setAppid(appid);
		wxrefund.setNonce_str(nonce_str);
		wxrefund.setOut_trade_no(out_trade_no);
		//wxrefund.setTransaction_id(transaction_id);
		wxrefund.setOut_refund_no(out_refund_no);
		wxrefund.setTotal_fee(total_fee);
		wxrefund.setRefund_fee(refund_fee);
		wxrefund.setOp_user_id(op_user_id);
		//wxrefund.setRefund_fee_type(refund_fee_type);
		
		//签名Map
        sParaTemp.put("mch_id", mch_id);
		sParaTemp.put("appid", appid);
		sParaTemp.put("nonce_str", nonce_str);
		sParaTemp.put("out_trade_no", out_trade_no);
		//sParaTemp.put("transaction_id", transaction_id);
		sParaTemp.put("out_refund_no", out_refund_no);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("refund_fee", refund_fee);
		//sParaTemp.put("refund_fee_type", refund_fee_type);
		sParaTemp.put("op_user_id", op_user_id);
		
		//签名
		String sign = sign(key,sParaTemp);
		//
		wxrefund.setSign(sign);
		  
		try {
			//对象转化XML
			Class<?>[] types = new Class[] { WxRefundRequestBean.class};
			String sendxml = XMLUtil.objectToXml(wxrefund,types);
			
			log.info("开始   微信退款   请求XML : "+sendxml);
			
			String retXml = new HttpsBaseRequest().postXml(REFUND_API,certLocalPath,mch_id, sendxml);
			
			log.info("结束 微信退款  响应XML :"+retXml);
			
			//解析 微信退款xml
			types = new Class[] { WxRefundResponseBean.class};
			//转化对象
			WxRefundResponseBean wxRP = (WxRefundResponseBean) XMLUtil.xmlToObject(retXml, types);
			//转化验签Map
			Map<String,String> returnMap = XMLUtil.parseXml(retXml);
			//处理通信成功
			if("SUCCESS".equals(wxRP.getReturn_code())){
				if(this.verifySign(paymentConfig, wxRP.getSign(), returnMap)){
					//申请退款成功
					if("SUCCESS".equals(wxRP.getResult_code())){
						log.info("微信申请退款   成功   交易水号 : "+wxRP.getOut_trade_no());
						//修改交易流水 状态  TODO
						//查询 原 交易流水信息
				        PayTraTransaction pt = payTranService.findTranInfoByTranCode(paymentTranNo);
				        if(!TranOutStatusEnum.CLOSE.getCode().equals(pt.getTranOutStatus())){
				        	  //原交易流水关闭
			                pt.setTranOutStatus(TranOutStatusEnum.CLOSE.getCode());// 外部状态 = 关闭
				        }				      
		                
				        payTranService.updateOutStatesByTransfer(pt);				        
				        //查询退款交易流水信息:修改退款交易流水 外部状态、内部状态
		            	PayTraTransaction refundPt = payTranService.findTranInfoByOldTranCode(pt.getTranCode(), TranTypeEnum.REFUND.getCode());              
		                if (null != refundPt && !TranOutStatusEnum.HAS_REFUND.getCode().equals(refundPt.getTranOutStatus())) {
		                	refundPt.setTranOutStatus(TranOutStatusEnum.HAS_REFUND.getCode());//  外部状态 =  已退款
		                	refundPt.setTranInStatus(TranInStatusEnum.REFUND_SUCCESS.getCode());//  内部状态 = 退款成功
		                    payTranService.updateStatesByTransfer(refundPt);
		            	}
						
					}
					//申请退款失败
					else{
						log.info("微信申请退款   失败  交易水号 : "+wxRP.getOut_trade_no());
						resultcode = "F";
						
					}
				//申请退款 验签失败
				}else{
					log.info("微信申请退款 微信返回，平台验签失败  交易流水号 : "+paymentTranNo);
					resultcode = "F";
					
				}
			//通信失败
			}else{
				log.info("微信申请退款 微信返回，通信失败  交易流水号 : "+paymentTranNo);
				resultcode = "F";
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultcode = "F";
		}		

		return resultcode;
		
		
		
		
	}


	/**
	 * 按照参数名ASCII码从小到大排序
	 * @param params
	 * @return
	 */
	private String paramToSign(Map params) {
		StringBuffer content = new StringBuffer();
 		Properties properties = new Properties();
    	for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			Object value = params.get(name);
			if (name == null || name.equalsIgnoreCase("sign")
					|| name.equalsIgnoreCase("sign_type") || name.equalsIgnoreCase("charset")) {
				continue;
			}
            if(value == null || value.toString().length() == 0){
            	continue;
            }
            properties.setProperty(name, value.toString());
		}
    	List keys = new ArrayList(properties.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = properties.getProperty(key);
            System.out.println(key + " : " + value);
			content.append(key);
			content.append("=");
			content.append(value);
			content.append("&");
		}
		return content.toString();
	}
	
//	public static void main(String[] args) {
//		BasePayment pay = new WxPayMode();
//		String mdx = "appid=wxef828fd6297e0951&attach=T040420160416153102159065&bank_type=CFT&cash_fee=1&fee_type=CNY&is_subscribe=Y&mch_id=10012149&nonce_str=y8sh3uytq4tdw27hfpik7srqoc8yu72n&openid=oCgX3jq2YQptY06XNowWSGydturI&out_trade_no=T040420160416153102159065&result_code=SUCCESS&return_code=SUCCESS&time_end=20160416153116&total_fee=1&trade_type=NATIVE&transaction_id=4006322001201604164900567010&key=dodopalwechatchengshigongjiaoykt";
//	    try {
//			String newSign = MD5.MD5Encode(mdx.getBytes("utf-8"));
//			 log.info("支付返回数据所带签名为==newSign=="+newSign);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}