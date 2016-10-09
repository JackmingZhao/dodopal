package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 卡服务结束标志
 * @author guanjinglun
 *
 */
public enum CardTradeEndFlagEnum {
    TRADE_END_FLAG_TRANSPARENT("0", "交易透传"),
    TRADE_END_FLAG_TRADE_END("1", "交易结束"),
    TRADE_END_FLAG_TRADE_RESEND("2", "重发");

    private String code;

    private String name;

    private static final Map<String, CardTradeEndFlagEnum> map = new HashMap<String, CardTradeEndFlagEnum>(values().length);

    static {
        for (CardTradeEndFlagEnum cardTradeEndFlagEnum : values()) {
            map.put(cardTradeEndFlagEnum.getCode(), cardTradeEndFlagEnum);
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

    CardTradeEndFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardTradeEndFlagEnum getEndFlagByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
