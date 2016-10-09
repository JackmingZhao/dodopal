package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum IsShowErrorMsgEnum {
    IS_SHOW_ERROR_MSG("0", "是"),
    NO_SHOW_ERROR_MSG("1", "否");
    private static final Map<String, IsShowErrorMsgEnum> map = new HashMap<String, IsShowErrorMsgEnum>(values().length);
    static {
        for (IsShowErrorMsgEnum isShowErrorMsgEnum : values()) {
            map.put(isShowErrorMsgEnum.getCode(), isShowErrorMsgEnum);
        }
    }
    
    private String code;
    private String name;
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    private IsShowErrorMsgEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static IsShowErrorMsgEnum getIsShowErrorMsgCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
