package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 支付网关枚举
 * 0: 支付宝
 * 1：财付通
 * 2：都都宝钱包
 * 其他: 根据code 取得通卡公司名称
 *
 */
public enum PayGatewayEnum {

    PAY_GATEWAY_ALIPAY("0","支付宝"),
    PAY_GATEWAY_TENPAY("1","财付通"),
    PAY_GATEWAY_DODOPALPAY("2","都都宝钱包"),
    PAY_GATEWAY_WINXI("3","微信");
    
    private String code;
    private String name;
    
    private static final Map<String, PayGatewayEnum> map = new HashMap<String, PayGatewayEnum>(values().length);

    static {
        for (PayGatewayEnum payGatewayEnum : values()) {
            map.put(payGatewayEnum.getCode(), payGatewayEnum);
        }
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    PayGatewayEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static PayGatewayEnum getPayGatewayEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
