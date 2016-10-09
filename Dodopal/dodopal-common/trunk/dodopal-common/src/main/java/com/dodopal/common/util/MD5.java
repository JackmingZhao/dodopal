package com.dodopal.common.util;

import java.security.MessageDigest;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MD5 {

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

    public static String MD5Encode(String origin) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex) {

        }
        return resultString;
    }
    
    public static String MD5Encode(byte[] origin) {
  		String resultString = null;

  		try {
  			MessageDigest md = MessageDigest.getInstance("MD5");
  			resultString = byteArrayToHexString(md.digest(origin));
  		} catch (Exception ex) {

  		}
  		return resultString;
  	}

    public static void main(String[] args) {
        //	  System.err.println(MD5Encode("zhaoshixiao@cnicdc.com9db06bcff9248837f86d1a6bcf41c9e72011-11-09RTD56U8IKLB52E21D4BB1F477717DD00"));
        //System.err.println(MD5Encode("kh9db06bcff9248837f86d1a6bcf41c9e72010-04-20 06:38:27RTD56U8IKLB52E21D4BB1F477717DD00")); 
        // System.err.println(MD5Encode(MD5Encode("111111")));
        // System.out.println(MD5Encode("411101001000006%E8%BE%9B%E6%AD%A3%E5%8C%979db06bcff9248837f86d1a6bcf41c9e71000000001039049948548470397%E8%BE%9B%E6%AD%A3%E5%8C%971123@qq.com112445323451jjj13%E7%BD%91%E7%82%B91112210011792266000-1532129345678"));
    } //de37875d24a8ad12ac31b61e30a933a9
}
