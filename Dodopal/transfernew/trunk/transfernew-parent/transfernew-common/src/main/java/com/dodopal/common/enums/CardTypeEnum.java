package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 卡类型
 * @author guanjinglun
 *
 */
public enum CardTypeEnum {
    CARD_TYPE_CPU("1", "CPU"),
    CARD_TYPE_M1("2", "M1");

    private String code;

    private String name;

    private static final Map<String, CardTypeEnum> map = new HashMap<String, CardTypeEnum>(values().length);

    static {
        for (CardTypeEnum cardTypeEnum : values()) {
            map.put(cardTypeEnum.getCode(), cardTypeEnum);
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

    CardTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardTypeEnum getCardTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
