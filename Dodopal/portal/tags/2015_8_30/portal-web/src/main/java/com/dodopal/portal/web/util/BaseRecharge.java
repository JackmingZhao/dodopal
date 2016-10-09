package com.dodopal.portal.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.portal.business.constant.PortalConstants;

public class BaseRecharge {

    public static String getRechargeUrl() {
        return DodopalAppVarPropsUtil.getStringProp(PortalConstants.DODOPAY_RECHARGE_URL);
    }
    
    public static String buildRequest(Map<String, String> sPara, String paymentUrl) {
        //待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"rechargesubmit\" name=\"rechargesubmit\" action=\"" + paymentUrl
                + "\" method=\"" + "get"+ "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append("<input type=\"text\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        sbHtml.append("</form>");
//        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
}
