package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 启用标志
 */
public enum BindEnum {
    ENABLE("0", "绑定"), DISABLE("1", "未绑定");

    private static final Map<String, BindEnum> map = new HashMap<String, BindEnum>(values().length);

    static {
        for (BindEnum bindEnum : values()) {
            map.put(bindEnum.getCode(), bindEnum);
        }
    }

    private String code;
    private String name;

    private BindEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BindEnum getBindByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
