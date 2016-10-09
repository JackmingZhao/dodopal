package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: RechargeOrderInternalStatesEnum
 * @Description: 产品库(内部)订单状态枚举
 * @author dodopal
 * @date 2015年8月17日
 */
public enum RechargeOrderInternalStatesEnum {
    // 主状态（0:待付款）下内部状态如下：
    ORDER_CREATION_SUCCESS("00", "订单创建成功"), //来自圈存时，其主状态是已付款
    ONLINE_BANKING_PAY_FAILED("01", "网银支付失败"), 
    
    // 主状态（1:已付款）下内部状态如下：
    ACCOUNT_RECHARGE_SUCCESS("10", "账户充值成功"), 
    ACCOUNT_RECHARGE_FAILED("11", "账户充值失败"), 
    ONLINE_BANKING_PAY_SUCCESS("12", "网银支付成功"), //仅仅是账户充值结果的前内部状态
    
    // 主状态（2:充值失败）下内部状态如下：
    FUND_FROZEN_FAILED("20", "资金冻结失败"), 
    APPLY_RECHARGE_SECRET_KEY_FAILED("21", "申请充值密钥失败"), 
    CARD_RECHARGE_FAILED("22", "卡片充值失败"),
    FUND_UNBOLCK_SUCCESS("23", "资金解冻成功"), 
    FUND_UNBOLCK_FAILED("24", "资金解冻失败"),
    
    // 主状态（3:充值中）下内部状态如下：
    FUND_FROZEN_SUCCESS("30", "资金冻结成功"), 
    APPLY_RECHARGE_SECRET_KEY_SUCCESS("31", "申请充值密钥成功"),
    
    // 主状态（4:充值成功）下内部状态如下：
    CARD_RECHARGE_SUCCESS("40", "卡片充值成功"), 
    FUND_DEDUCT_SUCCESS("41", "资金扣款成功"), 
    FUND_DEDUCT_FAILED("42", "资金扣款失败"),
    
    // 主状态（5:充值可疑）下内部状态如下：
    RECHARGE_RESULT_UNKNOWN("50", "上传充值未知");

    private static final Map<String, RechargeOrderInternalStatesEnum> map = new HashMap<String, RechargeOrderInternalStatesEnum>(values().length);

    static {
        for (RechargeOrderInternalStatesEnum rechargeOrderInternalStatesEnum : values()) {
            map.put(rechargeOrderInternalStatesEnum.getCode(), rechargeOrderInternalStatesEnum);
        }
    }

    private String code;
    private String name;

    private RechargeOrderInternalStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RechargeOrderInternalStatesEnum getRechargeOrderInternalStatesEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
    
    public static String getRechargeOrderInternalStatesNameByCode(String code) {
        RechargeOrderInternalStatesEnum state = getRechargeOrderInternalStatesEnumByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

}
