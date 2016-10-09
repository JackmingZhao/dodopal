package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 商户审核状态
 */
public enum MerStateEnum {
    NO_AUDIT("0", "未审核"), THROUGH("1", "审核通过"), REJECT("2", "审核未通过");

    private static final Map<String, MerStateEnum> map = new HashMap<String, MerStateEnum>(values().length);

    static {
        for (MerStateEnum merState : values()) {
            map.put(merState.getCode(), merState);
        }
    }

    private String code;
    private String name;

    private MerStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerStateEnum getMerStateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
