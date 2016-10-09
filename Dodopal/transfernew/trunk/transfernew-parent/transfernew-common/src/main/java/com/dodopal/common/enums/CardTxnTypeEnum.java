package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 卡服务交易类型
 * @author guanjinglun
 *
 */
public enum CardTxnTypeEnum {
    TXN_TYPE_QUERY("0", "查询"),
    TXN_TYPE_RECHARGE("1", "充值"),
    TXN_TYPE_CONSUME("2","消费");

    private String code;

    private String name;

    private static final Map<String, CardTxnTypeEnum> map = new HashMap<String, CardTxnTypeEnum>(values().length);

    static {
        for (CardTxnTypeEnum cardTxnTypeEnum : values()) {
            map.put(cardTxnTypeEnum.getCode(), cardTxnTypeEnum);
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

    CardTxnTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardTxnTypeEnum getCardTxnTypeEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
