package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum TransferFlagEnum {
    TRANSFER("0", "转账"), TO_EXTRACT_LINES("1", "提取额度");
    
    private static final Map<String, TransferFlagEnum> map = new HashMap<String, TransferFlagEnum>(values().length);

    static {
        for (TransferFlagEnum transferFlagEnum : values()) {
            map.put(transferFlagEnum.getCode(), transferFlagEnum);
        }
    }

    private String code;
    private String name;

    private TransferFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TransferFlagEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
