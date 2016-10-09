package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 一卡通充值圈存订单状态枚举
 */
public enum LoadOrderStatusEnum {

    ORDER_SUCCESS("0", "下单成功"),

    CHARGE_SUCCESS("1", "充值成功"),

    ORDER_FAILURE("2", "充值失败"),

    REFUNDMENT("3", "已退款"),
    
    ORDER_CREATE("4", "创建订单成功");
    
    private static final Map<String, LoadOrderStatusEnum> map = new HashMap<String, LoadOrderStatusEnum>(values().length);

    static {
        for (LoadOrderStatusEnum statusEnum : values()) {
            map.put(statusEnum.getCode(), statusEnum);
        }
    }
    
    private String code;

    private String name;

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

    LoadOrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 根据状态码获取其对应的枚举状态 **/
    public static LoadOrderStatusEnum parseByCode(String source) {
        for (LoadOrderStatusEnum codes : values()) {
            if (codes.code.equals(source)) {
                return codes;
            }
        }
        return null;
    }
    
    public static LoadOrderStatusEnum getLoadOrderStatusByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
