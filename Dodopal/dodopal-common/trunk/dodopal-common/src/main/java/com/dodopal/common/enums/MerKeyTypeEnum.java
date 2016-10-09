package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum MerKeyTypeEnum {
    MD5("01", "MD5"),
    RSA("02","RSA"),
    DSA("03","DSA");
    

    private static final Map<String, MerKeyTypeEnum> map = new HashMap<String, MerKeyTypeEnum>(values().length);

    static {
        for (MerKeyTypeEnum merKeyType : values()) {
            map.put(merKeyType.getCode(), merKeyType);
        }
    }

    private String code;
    private String name;

    private MerKeyTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerKeyTypeEnum getMerKeyTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean contains(String code) {
        return map.containsKey(code);
    }

    public static String getNameByCode(String code) {
        MerKeyTypeEnum temp = getMerKeyTypeByCode(code);
        if (temp != null) {
            return temp.getName();
        }
        return "";
    }
}
