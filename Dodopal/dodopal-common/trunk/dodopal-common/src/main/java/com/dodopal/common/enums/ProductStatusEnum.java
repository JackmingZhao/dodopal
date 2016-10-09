package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum ProductStatusEnum {
    ENABLE("0", "上架"), DISABLE("1", "下架");

    private static final Map<String, ProductStatusEnum> map = new HashMap<String, ProductStatusEnum>(values().length);

    static {
        for (ProductStatusEnum productStatusEnum : values()) {
            map.put(productStatusEnum.getCode(), productStatusEnum);
        }
    }

    private String code;
    private String name;

    private ProductStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static ProductStatusEnum getProductStatusByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
