package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum IsAutoDistributeEnum {
    IS_AUTO_DISTRIBUTE("0", "是"),
    NO_AUTO_DISTRIBUTE("1", "否");
    private static final Map<String, IsAutoDistributeEnum> map = new HashMap<String, IsAutoDistributeEnum>(values().length);
    static {
        for (IsAutoDistributeEnum isAutoDistributeEnum : values()) {
            map.put(isAutoDistributeEnum.getCode(), isAutoDistributeEnum);
        }
    }
    
    private String code;
    private String name;
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    private IsAutoDistributeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static IsAutoDistributeEnum getIsAutoDistributeEnumCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
