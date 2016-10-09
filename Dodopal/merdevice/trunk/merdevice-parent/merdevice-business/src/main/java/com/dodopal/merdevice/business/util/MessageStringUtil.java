package com.dodopal.merdevice.business.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dodopal.api.merdevice.crdConstants.CardServiceConstants;

public class MessageStringUtil {
private static Logger log = Logger.getLogger(MessageStringUtil.class);
    
    /**
     * 字符串从后向前替换，如果新的大于旧的，返回null
     * @param oldString
     * @param newString
     * @return
     */
    public static String replaceHQ(String oldString, String newString)
    {
        if(oldString.length()>=newString.length())
        {
            return new String(oldString.substring(0, oldString.length()-newString.length()))+newString;
        }else
        {
            return null;
        }
    }
    
    /**
     * 字符串根据长度，后补空格
     * @param oldString
     * @param newString
     * @return
     */
    public static String replaceKG(String oldString, int le)
    {
        String re=oldString;
        int r=le-oldString.length();
        for(int i=0;i<r;i++)
        {
            re=re+" ";
        }
        return re;
    }   
    
    
    /**
     * 把数组转化为字符串报文
     * @param arr
     * @return
     */
    public static String stringAToString(String[] arr)
    {
        String re="";
        if(arr!=null)
        {
            for(int i=0;i!=arr.length;i++)
            {
                re=re+arr[i];
            }
        }
        
        return re;
    }
    /**
     * 把字符串报文转化为数组
     * @param baowen:要转化的报文
     * @param arrl：转化的数组模版
     * @return
     */
    public static String[] StringTostringA(String baowen,String[] arrl)
    {
        int j=0;
        if(arrl!=null&&baowen!=null)
        {
//          System.out.println(arrl.length);
            for(int i=0;i!=arrl.length;i++)
            {
                
                    arrl[i]=new String(baowen.substring(j, j+arrl[i].length()));
                    j=j+arrl[i].length();               
            }
        }       
        return arrl;
    }
    /**
     * 把json的数据转化为Map容器，支持单层的
     * @param jsonString
     * @return
     */
    public static Map jsonStringToMap(String jsonString)
    {
        Map map=new HashMap();
        String[] si=jsonString.split(",");
        for(int i=0;i!=si.length;i++)
        {
            String[] sr=si[i].split(":");
            map.put(sr[0], sr[1]);
        }
        return map;
    }
    
    /** 乘法运算。 
    * @param sMath1 被乘数 
    * @param sMath2 乘数 
    * @return 两个参数的积 
     * @throws NumberFormatException 数字转换异常
    */ 
    public static String mul(String sMath1, String sMath2) throws NumberFormatException { 
        BigDecimal b1 = new BigDecimal(sMath1); 
        BigDecimal b2 = new BigDecimal(sMath2); 
        return b1.multiply(b2).toString(); 
    } 
    
    /** 四舍五入到指定精度
     * @param sMath 需要四舍五入的数字
     * @param scale 指定需要的精度
     * @return 四舍五入后的结果
     * @throws NumberFormatException 数字转换异常
    */  
    public static String round(String sMath, int scale) throws NumberFormatException { 
        BigDecimal b = new BigDecimal(sMath); 
        BigDecimal one = new BigDecimal("1"); 
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString(); 
    }
    
    public static String getValueOrDefault(String source, String defaultVal) {
        return getValueOrDefault(source, defaultVal, CardServiceConstants.LEFT_PAD_SPACE);
    }
    
    public static String getValueOrDefault(String source, String defaultVal, int processType) {
        String result = source;
        if(StringUtils.isNotEmpty(source)) {
            if(source.length() > defaultVal.length()) {
                result = source.substring(0, defaultVal.length());
            }
            
            if(source.length() < defaultVal.length()) {
                if(CardServiceConstants.LEFT_PAD_SPACE == processType) {
                    result = StringUtils.leftPad(source, defaultVal.length(), " ");
                }
                if(CardServiceConstants.LEFT_PAD_ZERO == processType) {
                    result = StringUtils.leftPad(source, defaultVal.length(), "0");
                }
                if(CardServiceConstants.RIGHT_PAD_SPACE == processType) {
                    result = StringUtils.rightPad(source, defaultVal.length(), " ");
                }
                if(CardServiceConstants.RIGHT_PAD_ZERO == processType) {
                    result = StringUtils.rightPad(source, defaultVal.length(), "0");
                }
            }
            
            return result;
        } else {
            return defaultVal;
        }
    }
    
        
    public static void main(String[] str)
    {
        String s="588";
        System.out.println(s.substring(0, 3));
    }
    
}
