package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：用户类型
 * @author lifeng
 */

public enum MerUserTypeEnum {
    PERSONAL("0", "个人"), MERCHANT("1", "企业");

    private static final Map<String, MerUserTypeEnum> map = new HashMap<String, MerUserTypeEnum>(values().length);

    static {
        for (MerUserTypeEnum merUserTypeEnum : values()) {
            map.put(merUserTypeEnum.getCode(), merUserTypeEnum);
        }
    }

    private String code;
    private String name;

    private MerUserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String code) {
        return map.containsKey(code);
    }
    
    public static MerUserTypeEnum getMerUserUserTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getMerUserTypeNameByCode(String code) {
        MerUserTypeEnum type = getMerUserUserTypeByCode(code);
        if(type != null) {
            return type.getName();
        }
        return "";
    }
}
