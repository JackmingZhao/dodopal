package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 订单清分清算状态枚举
 */
public enum ClearingMarkEnum {
    CLEARING_NO("0", "未清算"), CLEARING_YES("1", "已清算"),;

    private static final Map<String, ClearingMarkEnum> map = new HashMap<String, ClearingMarkEnum>(values().length);

    static {
        for (ClearingMarkEnum clearingMarkEnum : values()) {
            map.put(clearingMarkEnum.getCode(), clearingMarkEnum);
        }
    }

    private String code;
    private String name;

    private ClearingMarkEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ClearingMarkEnum getClearingMarkByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getClearingMarkNameByCode(String code) {
        ClearingMarkEnum mark = getClearingMarkByCode(code);
        if(mark != null) {
            return mark.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
