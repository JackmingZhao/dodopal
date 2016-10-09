package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：是否测试账户
 * @author lifeng
 */

public enum UserClassifyEnum {
    MERCHANT("1", "正式商户用户"), MERCHANT_TEST("2", "测试商户用户"), PERSONAL("3", "正式个人用户"), PERSONAL_TEST("4", "个人测试用户");
    private static final Map<String, UserClassifyEnum> map = new HashMap<String, UserClassifyEnum>(values().length);

    static {
        for (UserClassifyEnum userClassifyEnum : values()) {
            map.put(userClassifyEnum.getCode(), userClassifyEnum);
        }
    }

    private String code;
    private String name;

    private UserClassifyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static UserClassifyEnum getMerClassifyByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
