package com.dodopal.payment.business.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;


/**
 * xml解析
 * 
 *
 */
public class XMLUtil {
	
	
	
	private static XStream xstream;

	 static {
	  xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

	 }

	 /**
	  * XML to object.
	  * 
	  * @param inputXml
	  *            XML string.
	  * @param types
	  *            annotation classes.
	  * @return
	  * @throws Exception
	  */
	 public static Object xmlToObject(String inputXml, Class<?>[] types) throws Exception {

	  if (null == inputXml || "".equals(inputXml)) {
	   return null;
	  }
	  xstream.processAnnotations(types);
	  return xstream.fromXML(inputXml);

	 }

	 /**
	  * object to XML.
	  * 
	  * @param ro
	  *            Object.
	  * @param types
	  *            annotation classes.
	  * @return
	  * @throws Exception
	  */
	 public static String objectToXml(Object ro, Class<?>[] types) throws Exception {
	  if (null == ro) {
	   return null;
	  }
	  xstream.processAnnotations(types);

	  return xstream.toXML(ro);
	 }


	
	/**
	 * @param xml
	 * @return Map
	 */
	public static Map<String,String> parseXml(String xml){
		Map<String,String> map=new HashMap<String,String>();
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(xml);
			Element root=doc.getRootElement();
			Iterator alipays=root.elementIterator();
			while(alipays.hasNext()){
				Element e=(Element) alipays.next();
				map.put(e.getName(), e.getText());
				}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) throws Exception {
//		try {
//			//WxPayBean wx = new WxPayBean();
//			Map<String ,String> wx = new HashMap<String, String>();
//			wx.put("wx", "222");
//			   Class<?>[] types = new Class[] { WxPayBean.class};
//			   String resultXml = XMLUtil.objectToXml(wx, types);//其中ro是要转换的对象
//			   System.out.println(resultXml);
//			  } catch (Exception e) {
//			   System.out.println(e.getMessage());
//			   e.printStackTrace();
//			  }
//		
//		//WxPayBean io = (WxPayBean) XMLUtil.xml2Object("", new Class[] { WxPayBean.class });
	}
}
