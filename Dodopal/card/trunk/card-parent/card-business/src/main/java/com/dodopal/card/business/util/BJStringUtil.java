package com.dodopal.card.business.util;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.TlpPosMenuTb;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.util.DDPStringUtil;

public class BJStringUtil {

    private static final Character[] OxChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};// 16进制的数字

    /*
     * 高低位转换
     * 16进制转10进制
     */
    public static String format16to10(String changeNo) {
        String str1 = changeNo.substring(0, 2);
        String str2 = changeNo.substring(2, 4);
        String str3 = changeNo.substring(4, 6);
        String str4 = changeNo.substring(6, 8);
        StringBuilder sbr = new StringBuilder(8);
        sbr.append(str4);
        sbr.append(str3);
        sbr.append(str2);
        sbr.append(str1);
        changeNo = Long.parseLong(sbr.toString(), 16) + "";
        return changeNo;
    }

    /*
     * 高低位转换
     * 10转16
     */
    public static String format10to16(String changeNo) {
        changeNo = Long.toHexString(Long.valueOf(changeNo)) + "";
        while (changeNo.length() < 8) {
            changeNo = "0" + changeNo;
        }

        String str1 = changeNo.substring(0, 2);
        String str2 = changeNo.substring(2, 4);
        String str3 = changeNo.substring(4, 6);
        String str4 = changeNo.substring(6, 8);

        StringBuilder sbr = new StringBuilder(8);
        sbr.append(str4);
        sbr.append(str3);
        sbr.append(str2);
        sbr.append(str1);

        return sbr.toString();
    }

    public static String checkSamInfo(String samNo, SamSignInOffTb inOffTb) {
        //sam卡签到签退信息不合法
        if (inOffTb == null || StringUtils.isEmpty(inOffTb.getPosIcSeq()) || StringUtils.isEmpty(inOffTb.getPosCommSeq())) {
            return ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR;
        }
        //sam卡号不对应
        if (!samNo.equals(inOffTb.getSamNo())) {
            return ResponseCode.CARD_SAM_ERROR;
        }
        //pos未签到
        Date currentTime = new Date();
        if (StringUtils.isEmpty(inOffTb.getSignInFlag()) || !"1".equals(inOffTb.getSignInFlag()) || (DateUtil.strToDateLongs(inOffTb.getLimitTime()).before(currentTime))) {
            return ResponseCode.CARD_POS_SIGN_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    public static String checkMenu(TlpPosMenuTb posMenuTb) {
        Pattern pattern = Pattern.compile("[0-1]{4}[0-9]{4,32}[F]{4}[0-9]{4,28}");
        if (posMenuTb == null || StringUtils.isBlank(posMenuTb.getMenufristactionset()) || !pattern.matcher(posMenuTb.getMenufristactionset()).matches()) {
            return ResponseCode.CARD_POSMENU_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    public static String checkOffline(String offlineType) {
        if (StringUtils.isBlank(offlineType) || (!"01".equals(offlineType) && !"02".equals(offlineType) && !"03".equals(offlineType))) {
            return ResponseCode.CARD_OFFLINETYPE_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    public static String startFix(String str, String fixStr, int length) {
        while (str.length() < length) {
            str = fixStr + str;
        }
        return str;
    }

    //加解密校验
    public static String checkEncrypt(String specdata, String cipheraction) {
        if ("01".equals(specdata.substring(0, 2))) {
            if (StringUtils.isBlank(cipheraction)) {
                return ResponseCode.CARD_ENCRYPT_NULL;
            }
        }
        return ResponseCode.SUCCESS;
    }

    //验证是否是全0
    public static boolean isZeroStr(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^0+$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static String removeVerZero(String ver) {
        if (StringUtils.startsWith(ver, "0")) {
            return DDPStringUtil.removeFrist(ver, "0");
        } else {
            return ver;
        }
    }

    public static String toBigInt16(String hex) {
        BigInteger bi = new BigInteger(hex, 16);
        String str = bi.intValue() + "";
        return str;
    }

    @SuppressWarnings("rawtypes")
    public static String pinEntity(Object obj, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String retStr = "";
        for (int i = 1; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            try {
                retStr += f.getName() + "=" + f.get(obj);
                if (i != (fields.length - 1)) {
                    retStr += "&";
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return retStr;
    }

    public static String getHexToDec(String toDealString) {
        toDealString = toDealString.toLowerCase();
        if (ifIlegal(toDealString, OxChars)) {
            return null;
        } else {
            return Integer.valueOf(toDealString, 16).toString();
        }
    }

    public static boolean ifIlegal(String toDealString, Character[] OxChars) {
        for (int i = 0; i < toDealString.length(); i++) {
            if (Arrays.asList(OxChars).contains(toDealString.charAt(i))) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }
}
