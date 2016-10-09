package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月17日 上午9:11:41
 */
public enum PayStatusEnum {
    TO_BE_PAID("0", "待支付"), 
    CANCELLED("1", "已取消"), 
    PAYMENT("2", "支付中"),
    PAID_SUCCESS("3", "支付成功"), 
    PAID_FAIL("4", "支付失败");
    
    private static final Map<String, PayStatusEnum> map = new HashMap<String, PayStatusEnum>(values().length);

    static {
        for (PayStatusEnum payStatusEnum : values()) {
            map.put(payStatusEnum.getCode(), payStatusEnum);
        }
    }

    private String code;
    private String name;

    private PayStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PayStatusEnum getPayStatusByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    
}
