package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 下午3:05:53
 * 交易流水外部状态
 */
public enum TranOutStatusEnum {
    TO_BE_PAID("0", "待支付"), 
    CANCELLED("1", "已取消"),
    PAYMENT("2", "支付中"),
    HAS_PAID("3", "已支付"),
    TO_BE_REFUNDED("4", "待退款"),
    HAS_REFUND("5", "已退款"),
    TRANSFER("6", "待转帐"),
    TRANSFER_SUCCESS("7", "转帐成功"),
    CLOSE("8","关闭");

    private static final Map<String, TranOutStatusEnum> map = new HashMap<String, TranOutStatusEnum>(values().length);

    static {
        for (TranOutStatusEnum tranOutStatusEnum : values()) {
            map.put(tranOutStatusEnum.getCode(), tranOutStatusEnum);
        }
    }

    private String code;
    private String name;

    private TranOutStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TranOutStatusEnum getTranOutStatusByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
