/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2015/10/21.
 */
public enum  ClearingFlagEnum {
    //0—不用清分，1—已经清分，2—应该清分但没有清分
    NO_CLEARING("0", "不清分"),
    ALREADY_CLEARING("1", "已清分"),
    YES_BUT_NOT_CLEARING("2", "待确认");
    private static final Map<String, ClearingFlagEnum> map = new HashMap<String, ClearingFlagEnum>(values().length);

    static {
        for (ClearingFlagEnum clearingFlagEnum : values()) {
            map.put(clearingFlagEnum.getCode(), clearingFlagEnum);
        }
    }

    private String code;
    private String name;

    private ClearingFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ClearingFlagEnum getClearingFlagByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}