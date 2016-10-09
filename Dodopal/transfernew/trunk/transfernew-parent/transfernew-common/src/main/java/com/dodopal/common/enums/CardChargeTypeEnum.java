package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 充值类型
 * @author guanjinglun
 *
 */
public enum CardChargeTypeEnum {
    CHARGE_TYPE_PURSE("0", "钱包"),
    CHARGE_TYPE_TICKET("1", "月票");

    private String code;

    private String name;

    private static final Map<String, CardChargeTypeEnum> map = new HashMap<String, CardChargeTypeEnum>(values().length);

    static {
        for (CardChargeTypeEnum cardChargeTypeEnum : values()) {
            map.put(cardChargeTypeEnum.getCode(), cardChargeTypeEnum);
        }
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    CardChargeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardChargeTypeEnum getChargeTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
