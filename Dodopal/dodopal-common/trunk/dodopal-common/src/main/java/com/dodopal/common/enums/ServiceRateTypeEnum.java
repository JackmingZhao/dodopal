package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：服务费费率类型
 */

public enum ServiceRateTypeEnum {
    SINGLE_AMOUNT("1", "单笔（元）"), PERMILLAGE("2", "费率（千分比）");

    private static final Map<String, ServiceRateTypeEnum> map = new HashMap<String, ServiceRateTypeEnum>(values().length);

    static {
        for (ServiceRateTypeEnum serviceRateTypeEnum : values()) {
            map.put(serviceRateTypeEnum.getCode(), serviceRateTypeEnum);
        }
    }

    private String code;
    private String name;

    private ServiceRateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ServiceRateTypeEnum getServiceRateTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
