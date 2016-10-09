package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：删除标志
 * @author lifeng
 */

public enum DelFlgEnum {
    NORMAL("0", "正常"), DELETE("1", "删除");

    private static final Map<String, DelFlgEnum> map = new HashMap<String, DelFlgEnum>(values().length);

    static {
        for (DelFlgEnum delFlgEnum : values()) {
            map.put(delFlgEnum.getCode(), delFlgEnum);
        }
    }

    private String code;
    private String name;

    private DelFlgEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DelFlgEnum getActivateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
