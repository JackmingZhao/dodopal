package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：业务编码(注意与【产品库业务信息表--PRD_RATE】同步维护)
 * @author lifeng
 */

public enum RateCodeEnum {
    YKT_RECHARGE("01", "一卡通充值"),
    LIFE_PAY("02", "生活缴费"),
    YKT_PAYMENT("03", "一卡通消费"),
    IC_LOAD("04", "圈存充值"),
    ACCT_RECHARGE("99","账户充值"),
    ACCT_TRANSFER("98","转账"),
	AUTO_TRANSFER("97","自动转账");
    private static final Map<String, RateCodeEnum> map = new HashMap<String, RateCodeEnum>(values().length);

    static {
        for (RateCodeEnum rateCodeEnum : values()) {
            map.put(rateCodeEnum.getCode(), rateCodeEnum);
        }
    }

    private String code;
    private String name;

    private RateCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RateCodeEnum getRateTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
