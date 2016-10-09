package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: RechargeOrderStatesEnum
 * @Description: 产品库(外部)订单状态枚举
 * @author dodopal
 * @date 2015年8月17日
 */
public enum RechargeOrderStatesEnum {
    UNPAID("0", "待付款"), 
    PAID("1", "已付款"), 
    RECHARGE_FAILURE("2", "充值失败"), 
    RECHARGE("3", "充值中"), 
    RECHARGE_SUCCESS("4", "充值成功"), 
    RECHARGE_SUSPICIOUS("5", "充值可疑");

    private static final Map<String, RechargeOrderStatesEnum> map = new HashMap<String, RechargeOrderStatesEnum>(values().length);

    static {
        for (RechargeOrderStatesEnum rechargeOrderStatesEnum : values()) {
            map.put(rechargeOrderStatesEnum.getCode(), rechargeOrderStatesEnum);
        }
    }

    private String code;
    private String name;

    private RechargeOrderStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RechargeOrderStatesEnum getRechargeOrderStatesEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
