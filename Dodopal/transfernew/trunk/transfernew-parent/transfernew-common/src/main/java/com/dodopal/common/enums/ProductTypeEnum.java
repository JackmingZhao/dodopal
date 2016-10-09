package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品类别（0：标准产品;1：自定义产品）
 * @author dodopal
 *
 */
public enum ProductTypeEnum {
    STANDARD("0", "标准"), USER_DEFINED("1", "自定义");

    private static final Map<String, ProductTypeEnum> map = new HashMap<String, ProductTypeEnum>(values().length);

    static {
        for (ProductTypeEnum productTypeEnum : values()) {
            map.put(productTypeEnum.getCode(), productTypeEnum);
        }
    }

    private String code;
    private String name;

    private ProductTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static ProductTypeEnum getProductTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
