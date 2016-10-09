package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 城市额度购买：复核流程状态
 * @author dodopal
 *
 */
public enum CheckStateEnum {

    UN_CHECK("0", "待复核"), CHECK_ING("1", "复核中"), CHECK_DONE("2", "复核完了");

    private static final Map<String, CheckStateEnum> map = new HashMap<String, CheckStateEnum>(values().length);

    static {
        for (CheckStateEnum state : values()) {
            map.put(state.getCode(), state);
        }
    }

    private String code;
    private String name;

    private CheckStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CheckStateEnum getCheckStateEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getCheckStateNameByCode(String code) {
        CheckStateEnum state = getCheckStateEnumByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
