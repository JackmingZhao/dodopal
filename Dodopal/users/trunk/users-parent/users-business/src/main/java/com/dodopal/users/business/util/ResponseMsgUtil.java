package com.dodopal.users.business.util;

import java.util.List;

/**
 * 类说明 ：
 * @author lifeng
 */

public class ResponseMsgUtil {
    public static String formatMerBusRateMsg(List<String> msg) {
        StringBuilder sb = new StringBuilder();
        if (msg.size() > 0) {
            for (String temp : msg) {
                sb.append(";" + temp);
            }
            return sb.toString().substring(1);
        }
        return sb.toString();
    }
}
