package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午7:47:38
 */
public enum BankGatewayTypeEnum {
    GW_ALI("0", "支付宝"), GW_TENG("1", "财付通"),GW_DDP("2", "都都宝钱包"),WEIXN("3","微信");

    private static final Map<String, BankGatewayTypeEnum> map = new HashMap<String, BankGatewayTypeEnum>(values().length);

    static {
        for (BankGatewayTypeEnum bankGatewayTypeEnum : values()) {
            map.put(bankGatewayTypeEnum.getCode(), bankGatewayTypeEnum);
        }
    }

    private String code;
    private String name;

    private BankGatewayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BankGatewayTypeEnum getBankGatewayTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
