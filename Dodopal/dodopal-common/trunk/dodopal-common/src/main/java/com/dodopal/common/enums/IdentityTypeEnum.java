package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：证件类型
 * @author lifeng
 */

public enum IdentityTypeEnum {
    ID_CARD("0", "身份证"), DRIVING_LICENSE("1", "驾照"), PASSPORT("2", "护照");

    private static final Map<String, IdentityTypeEnum> map = new HashMap<String, IdentityTypeEnum>(values().length);

    static {
        for (IdentityTypeEnum identityType : values()) {
            map.put(identityType.getCode(), identityType);
        }
    }

    private String code;
    private String name;

    private IdentityTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static IdentityTypeEnum getIdentityTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
