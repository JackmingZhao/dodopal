package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum BJConsumptionOrderStatusEnum {
    ACCOUNTING("0", "订单记账"),
    SUCCESS("1", "成功订单"), 
    FAIL("2", "失败订单"),
    SUSPICIOUS("3", "可疑订单");
    private static final Map<String, BJConsumptionOrderStatusEnum> map = new HashMap<String, BJConsumptionOrderStatusEnum>(values().length);

    static {
        for (BJConsumptionOrderStatusEnum temp : values()) {
            map.put(temp.getCode(), temp);
        }
    }

    private String code;
    private String name;

    private BJConsumptionOrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BJConsumptionOrderStatusEnum getEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean contains(String code) {
        return map.containsKey(code);
    }

    public static String getNameByCode(String code) {
        BJConsumptionOrderStatusEnum temp = getEnumByCode(code);
        if (temp != null) {
            return temp.getName();
        }
        return "";
    }
}
