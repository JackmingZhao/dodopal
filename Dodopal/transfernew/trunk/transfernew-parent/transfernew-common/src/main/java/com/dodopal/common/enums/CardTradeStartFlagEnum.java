package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 卡服务开始标志
 * @author guanjinglun
 *
 */
public enum CardTradeStartFlagEnum {
    TRADE_START_FLAG_TRANSPARENT("0", "交易透传"),
    TRADE_START_FLAG_TRADE_START("1", "交易开始"),
    TRADE_START_FLAG_TRADE_RESEND("2", "重发");
    

    private String code;

    private String name;

    private static final Map<String, CardTradeStartFlagEnum> map = new HashMap<String, CardTradeStartFlagEnum>(values().length);

    static {
        for (CardTradeStartFlagEnum cardTradeStartFlagEnum : values()) {
            map.put(cardTradeStartFlagEnum.getCode(), cardTradeStartFlagEnum);
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

    CardTradeStartFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardTradeStartFlagEnum getStartFlagByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
