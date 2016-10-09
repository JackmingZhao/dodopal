package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/** 
 * @author lifeng@dodopal.com
 */

public enum LoadFlagEnum {
	LOAD_YES("0", "圈存订单"), LOAD_NO("1", "非圈存订单");

    private static final Map<String, LoadFlagEnum> map = new HashMap<String, LoadFlagEnum>(values().length);

    static {
        for (LoadFlagEnum loadFlagEnum : values()) {
            map.put(loadFlagEnum.getCode(), loadFlagEnum);
        }
    }

    private String code;
    private String name;

    private LoadFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static LoadFlagEnum getLoadFlagByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static String getLoadFlagNameByCode(String code) {
        LoadFlagEnum flag = getLoadFlagByCode(code);
        if(flag != null) {
            return flag.getName();
        }
        return "";
    }
    
    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
