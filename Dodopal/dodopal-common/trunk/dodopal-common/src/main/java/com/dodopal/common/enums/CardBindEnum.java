package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 卡片管理  操作类型
 * 绑定和解绑
 */
public enum CardBindEnum {
    ENABLE("0", "绑定"), DISABLE("1", "解绑");

    private static final Map<String, CardBindEnum> map = new HashMap<String, CardBindEnum>(values().length);

    static {
        for (CardBindEnum cardBindEnum : values()) {
            map.put(cardBindEnum.getCode(), cardBindEnum);
        }
    }

    private String code;
    private String name;

    private CardBindEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CardBindEnum getBindByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
