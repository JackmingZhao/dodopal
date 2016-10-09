package com.dodopal.users.business.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetPageResult {
	public String writeResult(String address) {
		 Logger log = LogManager.getLogger(GetPageResult.class);
		 log.info("传过来的发短信地址："+address);
		 String result ="";
		 URL url = null;
	     HttpURLConnection client=null;
	     //向服务器发送请求：
	     try {
			url = new URL(address);
		} catch (MalformedURLException e1) {
			log.info( "k-------------:构造url对象失败");
			result="e777776";
			return result;
		}
	     try {
			client = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			log.info("k--------------:构造url连接失败");
			result="e777776";
			return result;
		}
	     try {
			client.setRequestMethod("GET");
			client.setUseCaches(false);
			client.setInstanceFollowRedirects(true);
		} catch (ProtocolException e1) {
			log.info("k--------------:设置url属性失败");
			result="e777776";
			return result;
		}
	     client.setRequestProperty("pragma", "no-cache");
//	     client.setRequestProperty("Accept-Language", "UTF-8");
	     client.setRequestProperty("Content-Type","text/xml; charset=utf-8");
	     client.setDoOutput(true);
	     client.setDoInput(true);
	     client.setConnectTimeout(60000);
	     client.setReadTimeout(60000);
	     try {
			client.connect();
		} catch (IOException e1) {
			log.info("k---------------:url连接失败");
			result="e777777";
			return result;
		}
	     OutputStream outStream = null;
		try {
			outStream = client.getOutputStream();
			PrintWriter out = new PrintWriter(outStream);
		     out.write("");
		     try {
				 out.flush();
				 out.close();
			} catch (RuntimeException e1) {
				log.info("k-----------:输出流关闭失败");
				result="e777776";
				return result;
			}
		} catch (IOException e1) {
			log.info("k-------------:输出流失败");
			result="e777776";
			return result;
		}
		// 读取信息
		InputStreamReader inputStream = null;
		try {
			inputStream = new InputStreamReader(client.getInputStream());
		} catch (IOException e) {
			log.info("k---------------:接收信息失败");
			result="e777777";
			return result;
		}
	     //接收服务器的返回：
	   BufferedReader in = null;
	   try {
	    in = new BufferedReader(inputStream);
	    String inputLine;
	    while ((inputLine = in.readLine()) != null) {
	     inputLine=new String(inputLine.getBytes("GBk"),"UTF-8");
	     result+=inputLine;
	    }
	   } catch (IOException e) {
		   log.info("k--------------:读取信息失败");
		   result="e777777";
		   return result;
	   }
	   try {
			inputStream.close();
		} catch (IOException e) {
			log.info("k---------------:输入流关闭失败");
			result="e777776";
			return result;
		}
		client.disconnect();
		log.info("发短信返回===="+result);
	    return result;
	}
}
