package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 下午1:04:42
 * 交易类型枚举
 */
public enum TranTypeEnum {
    ACCOUNT_RECHARGE("1", "账户充值"), 
    ACCOUNT_CONSUMPTION("3", "账户消费"), 
    REFUND("5", "退款"),
    TURN_OUT("7", "转出"), 
    TURN_INTO("9", "转入");
    
    private static final Map<String, TranTypeEnum> map = new HashMap<String, TranTypeEnum>(values().length);

    static {
        for (TranTypeEnum tranTypeEnum : values()) {
            map.put(tranTypeEnum.getCode(), tranTypeEnum);
        }
    }

    private String code;
    private String name;

    private TranTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TranTypeEnum getTranTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
