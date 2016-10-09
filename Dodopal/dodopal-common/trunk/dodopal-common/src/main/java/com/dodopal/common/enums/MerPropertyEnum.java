package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 商户属性
 */
public enum MerPropertyEnum {
    STANDARD("0", "标准商户"), CUSTOM("1", "自定义商户");

    private static final Map<String, MerPropertyEnum> map = new HashMap<String, MerPropertyEnum>(values().length);

    static {
        for (MerPropertyEnum merProperty : values()) {
            map.put(merProperty.getCode(), merProperty);
        }
    }

    private String code;
    private String name;

    private MerPropertyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerPropertyEnum getMerPropertyByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
