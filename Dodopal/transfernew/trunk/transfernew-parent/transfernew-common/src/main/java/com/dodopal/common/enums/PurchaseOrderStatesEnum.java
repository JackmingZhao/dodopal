package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品库消费主订单状态
 */
public enum PurchaseOrderStatesEnum {
    PAID_WAIT("0", "待支付"), 
    PAID_FAILURE("1", "支付失败"), 
    PAID_SUCCESS("2", "支付成功"), 
    PAID_SUSPICIOUS("3", "支付中");

    private static final Map<String, PurchaseOrderStatesEnum> map = new HashMap<String, PurchaseOrderStatesEnum>(values().length);

    static {
        for (PurchaseOrderStatesEnum productConsumptionOrderStatesEnum : values()) {
            map.put(productConsumptionOrderStatesEnum.getCode(), productConsumptionOrderStatesEnum);
        }
    }

    private String code;
    private String name;

    private PurchaseOrderStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PurchaseOrderStatesEnum getProductConsumptionOrderStateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
