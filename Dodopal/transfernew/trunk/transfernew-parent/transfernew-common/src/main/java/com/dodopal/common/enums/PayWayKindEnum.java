package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月17日 上午10:01:15
 */
public enum PayWayKindEnum {
    GW_OUT("GW_OUT", "外接支付方式"),

    GW_ALL("GW_ALL", "通用支付方式");

    private String code;

    private String name;

    private static final Map<String, PayWayKindEnum> map = new HashMap<String, PayWayKindEnum>(values().length);

    static {
        for (PayWayKindEnum payWayKindEnum : values()) {
            map.put(payWayKindEnum.getCode(), payWayKindEnum);
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

    PayWayKindEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static PayWayKindEnum getPayWayKindEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getPayWayKindNameByCode(String code) {
        PayWayKindEnum way = getPayWayKindEnumByCode(code);
        if(way != null) {
            return way.getName();
        }
        return "";
    }
}
