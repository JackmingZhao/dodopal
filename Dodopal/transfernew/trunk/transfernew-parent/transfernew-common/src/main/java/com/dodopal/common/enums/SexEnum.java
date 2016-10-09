package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：性别
 * @author lifeng
 */

public enum SexEnum {
    MALE("0", "男"), FEMALE("1", "女");

    private static final Map<String, SexEnum> map = new HashMap<String, SexEnum>(values().length);

    static {
        for (SexEnum sexEnum : values()) {
            map.put(sexEnum.getCode(), sexEnum);
        }
    }

    private String code;
    private String name;

    private SexEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SexEnum getSexByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
