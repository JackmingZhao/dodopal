package com.dodopal.merdevice.business.util;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DataTranUtil {
    /*
     * 高低位转换
     * 16进制转10进制
     */
    public static String format16to10(String changeNo) {
        String str1 = changeNo.substring(0, 2);
        String str2 = changeNo.substring(2, 4);
        StringBuilder sbr = new StringBuilder(4);
        sbr.append(str2);
        sbr.append(str1);
        changeNo = Integer.parseInt(sbr.toString(), 16) + "";
        return changeNo;
    }
    /*
     * 带负数的16进制转换成10进制
     */
    public static String MinusToBigInt16(String hex) {
        BigInteger bi = new BigInteger(hex, 16);
        String str = bi.intValue() + "";
        return str;
    }

    public static String noTRansferFormat16to10(String changeNo) {
        changeNo = Integer.parseInt(changeNo, 16) + "";
        return changeNo;
    }

    /**
     * @param str
     * @return
     * @description 字符串去掉前边的0
     */
    public static String dropFirst0(String str) {
        return str.replaceFirst("^0*", "");
    }

    /**
     * @return
     * @description 字符串前边补0
     */
    public static String leftAdd0(String str, int len) {
        StringBuffer sb = null;
        while (str.length() < len) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左(前)补0
            // sb.append(str).append("0");//右(后)补0
            str = sb.toString();
        }
        return str;
    }
    /**
     * @return
     * @description 字符串后边补0
     */
    public static String rightAdd0(String str, int len) {
        StringBuffer sb = null;
        while (str.length() < len) {
            sb = new StringBuffer();
            sb.append(str).append("0");// 左(前)补0
            str = sb.toString();
        }
        return str;
    }

    /**
     * @return jdk1.6及以上
     * @description 字符串转成字符后面补空格
     */
    public static String rightAddSpace(String str, int len) throws UnsupportedEncodingException {
//        String osname = System.getProperties().getProperty("os.name");
//        if (!osname.startsWith("Windows")) {
//            len = returnLen(str, len);
//        }
        StringBuffer sb = new StringBuffer(len);
        sb.append(str);
        String teststr = URLDecoder.decode(str, "gbk");
        while (teststr.getBytes("gbk").length < len) {
            sb.append(" ");
            str = sb.toString();
            teststr = URLDecoder.decode(str,"gbk");
        }
        return str;
    }

    /**
     * @param str
     * @param len
     * @return
     * @description 为了linux 下汉字多一个字符处理添加字符串
     */
    public static int returnLen(String str, int len) {
        for (int j = 0; j < str.length(); j++) {
            String tests = str.substring(j, j + 1);
            if (isChineseChar(tests)) {
                len = len + 1;
            } else {
                len = len + 0;
            }
        }
        return len;
    }

    /**
     * @param str
     * @return
     * @description 判断是否是汉字
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * @param str
     * @return
     * @description 字符串以占位形式补0
     */
    public static String add0(String str, int len) {
        return String.format("%-" + len + "s", str);
    }
}
