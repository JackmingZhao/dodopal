package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：费率类型
 * @author lifeng
 */

public enum RateTypeEnum {
    SINGLE_AMOUNT("1", "单笔返点金额(元)"), PERMILLAGE("2", "千分比(‰)");

    private static final Map<String, RateTypeEnum> map = new HashMap<String, RateTypeEnum>(values().length);

    static {
        for (RateTypeEnum rateTypeEnum : values()) {
            map.put(rateTypeEnum.getCode(), rateTypeEnum);
        }
    }

    private String code;
    private String name;

    private RateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RateTypeEnum getRateTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
