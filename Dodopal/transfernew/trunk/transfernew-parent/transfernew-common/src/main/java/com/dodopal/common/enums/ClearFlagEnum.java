package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 下午6:50:02
 */
public enum ClearFlagEnum {
    NO("0", "未清算"), YES("1", "清算");
    private static final Map<String, ClearFlagEnum> map = new HashMap<String, ClearFlagEnum>(values().length);

    static {
        for (ClearFlagEnum clearFlagEnum : values()) {
            map.put(clearFlagEnum.getCode(), clearFlagEnum);
        }
    }

    private String code;
    private String name;

    private ClearFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ClearFlagEnum getBindByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
