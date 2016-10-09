package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 账户状态
 */
public enum AccStatusEnum {
    ENABLE("0", "启用"), DISABLE("1", "停用");

    private static final Map<String, AccStatusEnum> map = new HashMap<String, AccStatusEnum>(values().length);

    static {
        for (AccStatusEnum statusEnum : values()) {
            map.put(statusEnum.getCode(), statusEnum);
        }
    }

    private String code;
    private String name;

    private AccStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static AccStatusEnum getActivateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
