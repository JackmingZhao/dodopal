package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 订单资金处理结果枚举
 */
public enum FundProcessResultEnum {
    UNDONE("0", "没有处理"), DONE("1", "处理完毕"),;

    private static final Map<String, FundProcessResultEnum> map = new HashMap<String, FundProcessResultEnum>(values().length);

    static {
        for (FundProcessResultEnum fundProcessResultEnum : values()) {
            map.put(fundProcessResultEnum.getCode(), fundProcessResultEnum);
        }
    }

    private String code;
    private String name;

    private FundProcessResultEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static FundProcessResultEnum getFundProcessResultByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
