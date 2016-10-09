package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品库订单可疑状态枚举
 */
public enum SuspiciousStatesEnum {
    SUSPICIOUS_NO("0", "不可疑"), 
    SUSPICIOUS_UNHANDLE("1", "可疑未处理"),
    SUSPICIOUS_HANDLE("2", "可疑已处理");

    private static final Map<String, SuspiciousStatesEnum> map = new HashMap<String, SuspiciousStatesEnum>(values().length);

    static {
        for (SuspiciousStatesEnum suspiciousStatesEnum : values()) {
            map.put(suspiciousStatesEnum.getCode(), suspiciousStatesEnum);
        }
    }

    private String code;
    private String name;

    private SuspiciousStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SuspiciousStatesEnum getSuspiciousStateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
    
    public static String getSuspiciousStateNameByCode(String code) {
        SuspiciousStatesEnum state = getSuspiciousStateByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

}
