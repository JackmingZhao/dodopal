package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum SettlementTypeEnum {
    DAYS_NUMBER("0", "天数"),
    PEN_NUMBER("1", "笔数"),
    MONEY("2", "金额");
    private static final Map<String, SettlementTypeEnum> map = new HashMap<String, SettlementTypeEnum>(values().length);

    static {
        for (SettlementTypeEnum settlementTypeEnum : values()) {
            map.put(settlementTypeEnum.getCode(), settlementTypeEnum);
        }
    }

    private String code;
    private String name;

    private SettlementTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SettlementTypeEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
