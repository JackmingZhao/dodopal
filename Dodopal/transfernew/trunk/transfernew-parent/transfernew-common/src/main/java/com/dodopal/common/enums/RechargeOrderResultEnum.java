package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品库订单充值结果枚举
 * @author dodopal
 *
 */
public enum RechargeOrderResultEnum {
    RECHARGE_SUCCESS("0", "充值成功"),RECHARGE_FAILURE("1", "充值失败"), RECHARGE_SUSPICIOUS("2", "充值可疑");

    private static final Map<String, RechargeOrderResultEnum> map = new HashMap<String, RechargeOrderResultEnum>(values().length);

    static {
        for (RechargeOrderResultEnum rechargeResultEnum : values()) {
            map.put(rechargeResultEnum.getCode(), rechargeResultEnum);
        }
    }

    private String code;
    private String name;

    private RechargeOrderResultEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RechargeOrderResultEnum getProductOrderRechargeResultByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
