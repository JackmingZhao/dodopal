package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 开通标识
 * @author lifeng@dodopal.com
 */

public enum OpenSignEnum {
	OPENED("0", "开通"), CLOSED("1", "未开通");
	
	private static final Map<String, OpenSignEnum> map = new HashMap<String, OpenSignEnum>(values().length);

    static {
        for (OpenSignEnum openSignEnum : values()) {
            map.put(openSignEnum.getCode(), openSignEnum);
        }
    }

    private String code;
    private String name;

    private OpenSignEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static OpenSignEnum getOpenSignByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
