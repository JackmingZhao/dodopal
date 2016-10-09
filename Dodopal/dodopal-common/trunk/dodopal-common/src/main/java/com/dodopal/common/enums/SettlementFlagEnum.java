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
public enum  SettlementFlagEnum {
    // 0—已结算；1—未结算
    NOT_SETTLEMENT("0", "未结算"),
    ALREADY_SETTLEMENT("1", "已经结算");
    private static final Map<String, SettlementFlagEnum> map = new HashMap<String, SettlementFlagEnum>(values().length);

    static {
        for (SettlementFlagEnum settlementFlagEnum : values()) {
            map.put(settlementFlagEnum.getCode(), settlementFlagEnum);
        }
    }

    private String code;
    private String name;

    private SettlementFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SettlementFlagEnum getSettlementFlagByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}