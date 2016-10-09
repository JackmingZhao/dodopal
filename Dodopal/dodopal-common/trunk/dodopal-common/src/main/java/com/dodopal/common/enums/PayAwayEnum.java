package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum PayAwayEnum {

    PAY_GENERAL("0","通用支付方式"),
    PAY_EXTERNAL("1","外接支付方式");
    
    private String code;
    private String name;
    
    private static final Map<String, PayAwayEnum> map = new HashMap<String, PayAwayEnum>(values().length);

    static {
        for (PayAwayEnum payType : values()) {
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

    PayAwayEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static PayAwayEnum getPayAwayEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
