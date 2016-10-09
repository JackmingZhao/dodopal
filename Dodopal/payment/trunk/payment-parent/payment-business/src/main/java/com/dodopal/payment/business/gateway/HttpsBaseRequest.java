package com.dodopal.payment.business.gateway;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



/**
 * 微信退款 请求处理
 * <p>Title: HttpsBaseRequest</p>
 * <p>Description: </p>
 * <p>Company: www.dodopal.com </p> 
 * @author     
 * @date	2016-4-24下午8:14:22
 * @version 1.0
 */
public class HttpsBaseRequest {

	Logger log	= LogManager.getLogger(HttpsBaseRequest.class);
	
	public String postXml(String url,String keyStorePath,String storepassword,String postXmlData) throws Exception{
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		
		
		// 证书文件(微信商户平台-账户设置-API安全-API证书-下载证书)
		String storePath  = keyStorePath;
		// 证书密码（默认为商户ID）
		String password = storepassword;
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("PKCS12");  
		// 获得密钥库文件流
		FileInputStream fis = new FileInputStream(storePath);  
		// 加载密钥库
		ks.load(fis, password.toCharArray());
		log.info("微信退款加载密钥库！");
		// 关闭密钥库文件流
		fis.close();
		 
		// 实例化密钥库
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());  
		// 初始化密钥工厂
		kmf.init(ks, password.toCharArray());
		
		// 创建SSLContext
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(kmf.getKeyManagers(),trustAllCerts , new SecureRandom());
		// 获取SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(ssf);
		
		// 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				log.info("忽略HTTPS请求的SSL证书Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		
		
		URL myURL = new URL(url);
		log.info("微信接口地址："+url);
		// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
		
		httpsConn.setSSLSocketFactory(ssf);
		
		httpsConn.setRequestMethod("POST");
		httpsConn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据 
		httpsConn.setConnectTimeout(15000);//连接超时15秒
		httpsConn.setReadTimeout(20000);//连接超时20秒
		        
	 httpsConn.setRequestProperty("Content-Type", "text/xml,charset=utf-8");  
	 httpsConn.setRequestProperty("Content-Length", String.valueOf(postXmlData.length()));  
	 
	 OutputStream outputStream = null;
	 OutputStreamWriter outputStreamWriter = null;
	 InputStream inputStream = null;
	 InputStreamReader inputStreamReader = null;
	 BufferedReader reader = null;
	 StringBuffer resultBuffer = new StringBuffer();
	 String tempLine = null;
	 
	 try {
	     outputStream = httpsConn.getOutputStream();
	     outputStreamWriter = new OutputStreamWriter(outputStream);
	     
	     outputStreamWriter.write(postXmlData.toString());
	     outputStreamWriter.flush();
	     
	     if (httpsConn.getResponseCode() >= 300) {
	         throw new Exception("HTTP Request is not success, Response code is " + httpsConn.getResponseCode());
	     }
	     
	     inputStream = httpsConn.getInputStream();
	     inputStreamReader = new InputStreamReader(inputStream,"utf-8");
	     reader = new BufferedReader(inputStreamReader);
	     
	     while ((tempLine = reader.readLine()) != null) {
	         resultBuffer.append(tempLine);
	     }
	     
	 } finally {
	     
	     if (outputStreamWriter != null) {
	         outputStreamWriter.close();
	     }
	     
	     if (outputStream != null) {
	         outputStream.close();
	     }
	     
	     if (reader != null) {
	         reader.close();
	     }
	     
	     if (inputStreamReader != null) {
	         inputStreamReader.close();
	     }
	     
	     if (inputStream != null) {
	         inputStream.close();
	     }
	 }
	 
		        return resultBuffer.toString();
	}
	
  
	
	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		
		sc.init(null, trustAllCerts, null);
		
	}

	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			System.out.println("getAcceptedIssuers");
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			System.out.println("isServerTrusted");
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			System.out.println("isClientTrusted");
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			System.out.println("checkServerTrusted");
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			System.out.println("checkClientTrusted");
			return;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
//		String xml = "<xml><appid>wxef828fd6297e0951</appid><mch_id>10012149</mch_id><device_info>1</device_info><nonce_str>7f6c50cbcd8d4d45a1829d208621cc76</nonce_str><transaction_id></transaction_id><out_trade_no>100001000235</out_trade_no><out_refund_no></out_refund_no><refund_id></refund_id><sign>1114745D2DC469B7FE6EFE77A2FDCC82</sign></xml>";
		String xml = "<xml><appid>wxef828fd6297e0951</appid><mch_id>10012149</mch_id><device_info>1001</device_info><nonce_str>d631b1694a7a493496853ff11d596463</nonce_str><sign>5844D163C6D3FC3BE04CA3E7919E704E</sign><transaction_id></transaction_id><out_trade_no>100001000236</out_trade_no><out_refund_no>10000000000000000409</out_refund_no><total_fee>1000</total_fee><refund_fee>1000</refund_fee>" +
				" <refund_fee_type>CNY</refund_fee_type> <op_user_id>wxef828fd6297e0951</op_user_id></xml>";
//		String x = new HttpsBaseRequest().postXml(Configure.REFUND_QUERY_API, xml);
		
		String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		
		String x = new HttpsBaseRequest().postXml(url,WxPayMode.certLocalPath,"10012149", xml);
		System.out.println("响应：");
		System.out.println(x);
	}

}

 

