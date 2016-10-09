package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum ConsumeOrderStatesEnum {
   // 状态(0:待支付；1：支付失败；2：支付成功；3：支付中)
    
    UNPAID("0", "待支付"), 
    PAID_FAILURE("1", "支付失败"), 
    PAID_SUCCESS("2", "支付成功"), 
    PAID("3", "支付中");
    
    private static final Map<String, ConsumeOrderStatesEnum> map = new HashMap<String, ConsumeOrderStatesEnum>(values().length);

    static {
        for (ConsumeOrderStatesEnum consumeOrderStatesEnum : values()) {
            map.put(consumeOrderStatesEnum.getCode(), consumeOrderStatesEnum);
        }
    }

    private String code;
    private String name;

    private ConsumeOrderStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ConsumeOrderStatesEnum getConsumeOrderStatesEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
