package com.dodopal.common.md5;

import java.security.MessageDigest;
import java.util.List;
/** 
 * <p>Title: </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright (c) 2003</p> 
 * <p>Company: </p> 
 * @author unascribed 
 * @version 1.0 
 */ 

public class V71MD5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
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
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("GB2312")));
		} catch (Exception ex) {

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
	
	public static byte[] mergeArrayByte(List<byte[]> list) {
		int len = 0;
		for (int i = 0; i < list.size(); i++) {
			len += list.get(i).length;
		}
		if(len == 0) return null;
		int index = 0;
		byte[] result = new byte[len];
		for (int i = 0; i < list.size(); i++) {
			byte[] bt = list.get(i);
			for (int j = 0; j < bt.length; j++) {
				result[index] = bt[j];
				index++;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.err.println(MD5Encode(MD5Encode("111111")));
	}
}
