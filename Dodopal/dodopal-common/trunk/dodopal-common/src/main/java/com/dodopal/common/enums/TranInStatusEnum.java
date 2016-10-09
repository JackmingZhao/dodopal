package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 下午3:06:30
 * 
 * 交易流水内部状态
 */
public enum TranInStatusEnum {

    TO_DO("0", "待处理"), 
    PROCESSED("1", "处理中"), 
    ACCOUNT_FROZEN_SUCCESS("3", "账户冻结成功"),
    ACCOUNT_FROZEN_FAIL("4", "账户冻结失败"),
    ACCOUNT_UNFROZEN_SUCCESS("5", "账户解冻成功"),
    ACCOUNT_UNFROZEN_FAIL("6", "账户解冻失败"),
    ACCOUNT_DEBIT_SUCCESS("7", "账户扣款成功"),
    ACCOUNT_DEBIT_FAIL("8", "账户扣款失败"),
    ACCOUNT_VALUE_ADDED_SUCCESS("10", "账户加值成功"),
    ACCOUNT_VALUE_ADDED_FAIL("11", "账户加值失败"),
    REFUND_SUCCESS("12", "退款成功"),
    REFUND_FAIL("13", "退款失败");

    private static final Map<String, TranInStatusEnum> map = new HashMap<String, TranInStatusEnum>(values().length);

    static {
        for (TranInStatusEnum tranInStatusEnum : values()) {
            map.put(tranInStatusEnum.getCode(), tranInStatusEnum);
        }
    }

    private String code;
    private String name;

    private TranInStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TranInStatusEnum getTranInStatusByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
