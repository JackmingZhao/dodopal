package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 商户分类
 */
public enum MerClassifyEnum {
    OFFICIAL("0", "正式商户"), TEST("1", "测试商户");

    private static final Map<String, MerClassifyEnum> map = new HashMap<String, MerClassifyEnum>(values().length);

    static {
        for (MerClassifyEnum merClassify : values()) {
            map.put(merClassify.getCode(), merClassify);
        }
    }

    private String code;
    private String name;

    private MerClassifyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerClassifyEnum getMerClassifyByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
