package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 启用标志
 */
public enum ActivateEnum {
    ENABLE("0", "启用"), DISABLE("1", "停用");

    private static final Map<String, ActivateEnum> map = new HashMap<String, ActivateEnum>(values().length);

    static {
        for (ActivateEnum activateEnum : values()) {
            map.put(activateEnum.getCode(), activateEnum);
        }
    }

    private String code;
    private String name;

    private ActivateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ActivateEnum getActivateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
