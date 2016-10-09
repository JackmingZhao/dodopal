package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author dodopal
 *	支付确认信息:0.为显示弹出信息；1.为不显示弹出信息,默认为0
 */
public enum PayWarnFlagEnum {
		ON_WARN("0", "显示弹出信息"),

		OFF_WARN("1", "不显示弹出信息");
	    private static final Map<String, PayWarnFlagEnum> map = new HashMap<String, PayWarnFlagEnum>(values().length);

	    static {
	        for (PayWarnFlagEnum identityType : values()) {
	            map.put(identityType.getCode(), identityType);
	        }
	    }

	    private String code;
	    private String name;

	    private PayWarnFlagEnum(String code, String name) {
	        this.code = code;
	        this.name = name;
	    }

	    public String getCode() {
	        return code;
	    }

	    public String getName() {
	        return name;
	    }

	    public static PayWarnFlagEnum getPayWarnFlagByCode(String code) {
	        if (StringUtils.isBlank(code)) {
	            return null;
	        }
	        return map.get(code);
	    }
}
