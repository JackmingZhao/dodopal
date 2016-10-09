package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品库消费收单记录状态
 */
public enum PurchaseOrderRedordStatesEnum {
    
    // 消费主订单状态（0:待支付）时收单记录状态如下：
    ORDER_CREATION_SUCCESS("00", "订单创建成功"),
    CARD_CHECK_SUCCESS("01", "验卡成功"),
    KEY_APPLY_SUCCESS("02", "申请消费密钥成功"),
    
    // 消费主订单状态（1:支付失败）时收单记录状态如下：
    CARD_CHECK_FAILURE("10", "验卡失败"),
    KEY_APPLY_FAILURE("11", "申请消费密钥失败"),
    DEDUCT_MONEY_FAILURE("12", "扣款失败"),
    
    // 消费主订单状态（2:支付成功）时收单记录状态如下：
    DEDUCT_MONEY_SUCCESS("20", "扣款成功"),
    
    // 消费主订单状态（3:支付中）时收单记录状态如下：
    DEDUCT_MONEY_SUSPICIOUS("30", "扣款未知");

    private static final Map<String, PurchaseOrderRedordStatesEnum> map = new HashMap<String, PurchaseOrderRedordStatesEnum>(values().length);

    static {
        for (PurchaseOrderRedordStatesEnum productConsumptionOrderRedordStatesEnum : values()) {
            map.put(productConsumptionOrderRedordStatesEnum.getCode(), productConsumptionOrderRedordStatesEnum);
        }
    }

    private String code;
    private String name;

    private PurchaseOrderRedordStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PurchaseOrderRedordStatesEnum getProductConsumptionOrderRedordStateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
