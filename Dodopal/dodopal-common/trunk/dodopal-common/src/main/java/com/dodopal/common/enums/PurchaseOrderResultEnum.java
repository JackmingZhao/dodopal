package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 产品库订单消费结果枚举
 * @author dodopal
 *
 */
public enum PurchaseOrderResultEnum {
    SUCCESS("0", "成功"),FAILURE("1", "失败"), SUSPICIOUS("2", "未知");

    private static final Map<String, PurchaseOrderResultEnum> map = new HashMap<String, PurchaseOrderResultEnum>(values().length);

    static {
        for (PurchaseOrderResultEnum purchaseOrderResultEnum : values()) {
            map.put(purchaseOrderResultEnum.getCode(), purchaseOrderResultEnum);
        }
    }

    private String code;
    private String name;

    private PurchaseOrderResultEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PurchaseOrderResultEnum getPurchaseOrderResultEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
