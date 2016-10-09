package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午7:58:03
 * 支付类型枚举
 * 
 */
public enum PayTypeEnum {
    PAY_ACT("0", "账户支付"),

    PAY_CARD("1", "一卡通支付"),

    PAY_ONLINE("2", "在线支付"),

    PAY_BANK("3", "银行支付");

   // PAY_PREPAID_CARD("4", "预付费卡");

    private String code;

    private String name;

    private static final Map<String, PayTypeEnum> map = new HashMap<String, PayTypeEnum>(values().length);

    static {
        for (PayTypeEnum payType : values()) {
            map.put(payType.getCode(), payType);
        }
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    PayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static PayTypeEnum getPayTypeEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getPayTypeNameByCode(String code) {
        PayTypeEnum paytype = getPayTypeEnumByCode(code);
        if(paytype != null) {
            return paytype.getName();
        }
        return "";
    }
}
