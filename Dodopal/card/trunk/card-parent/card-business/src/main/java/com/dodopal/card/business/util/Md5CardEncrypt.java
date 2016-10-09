package com.dodopal.card.business.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Md5CardEncrypt {
    private static Logger logger = Logger.getLogger(Md5CardEncrypt.class);
    
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回32位签名值,通过加密后，小写字母的
     * @param merCode 商户号
     * @param digestValue 摘要值
     * @return
     */
    public static String getSignValue(String merCode,String digestValue) {
        String encryptSign = null;
        logger.info("Incoming parameters merCode :"+merCode+"  and digestValue:"+digestValue);
        try {
            encryptSign = new String(merCode+digestValue);
            MessageDigest md = MessageDigest.getInstance("MD5");
            encryptSign = byteArrayToHexString(md.digest(encryptSign.getBytes()));
            logger.info("After the encryption parameters:"+encryptSign);
        }
        catch (NoSuchAlgorithmException ex) {
            logger.info("NoSuchAlgorithmException: " + ex);
        }
        return encryptSign;
    }
    
    /**
     * 对比签名值是否一致
     * @param merCode 商户号
     * @param digestValue 摘要值
     * @param encryptSignValue 签名值
     * @return
     */
    public static boolean checkSign(String merCode,String digestValue,String encryptSignValue){
        logger.info("Incoming parameters merCode :"+merCode+" \r\n digestValue:"+digestValue +" \r\n encryptSignValue: "+encryptSignValue);
        boolean checkSignvalue= false;
        String encryptSign = null;//获取签名值
        if(StringUtils.isNotBlank(merCode)&&StringUtils.isNotBlank(digestValue)){
            encryptSign  = getSignValue(merCode,digestValue);
        }
        if(StringUtils.isNotBlank(encryptSignValue)){
            System.out.println(encryptSign);
            if(encryptSignValue.equals(encryptSign)){
                checkSignvalue = true;
            }
        }
        return checkSignvalue;
    }

    
    public static void main(String[] args) {
        String merCode="010012321252";
        String digestValue ="dafagaga";
        String encryptSignValue="a28e01d767c2f44df7cdd29c92ab86f5";
        String encryptSign =  getSignValue(merCode,digestValue);
        System.out.println(encryptSign);
        boolean checkSignvalue= false;
        checkSignvalue =checkSign(merCode,digestValue,encryptSignValue);
        System.out.println(checkSignvalue);
        
    }
}
