package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 卡服务交易状态
 * @author guanjinglun
 *
 */
public enum CardTxnStatEnum {
    TXN_STAT_SUCCESS("0", "成功"),
    TXN_STAT_FAIL("1", "失败"),
    TXN_STAT_UNKNOW("2","未知"),
    TXN_STAT_CANCEL("3","取消");

    private String code;

    private String name;

    private static final Map<String, CardTxnStatEnum> map = new HashMap<String, CardTxnStatEnum>(values().length);

    static {
        for (CardTxnStatEnum cardTxnStatEnum : values()) {
            map.put(cardTxnStatEnum.getCode(), cardTxnStatEnum);
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

    CardTxnStatEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardTxnStatEnum getCardTxnStatEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
