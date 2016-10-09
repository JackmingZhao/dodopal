package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * POS类型
 * @author guanjinglun
 */
public enum CardPosTypeEnum {
    POS_TYPE_NFC("0", "NFC手机"),
    POS_TYPE_V60("1", "V60或V61"), 
    POS_TYPE_V70("2", "V70");

    private String code;

    private String name;

    private static final Map<String, CardPosTypeEnum> map = new HashMap<String, CardPosTypeEnum>(values().length);

    static {
        for (CardPosTypeEnum cardPosTypeEnum : values()) {
            map.put(cardPosTypeEnum.getCode(), cardPosTypeEnum);
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

    CardPosTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CardPosTypeEnum getPosTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
