package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 商户类型
 */
public enum MerTypeEnum {
    PERSONAL("99", "个人"),
    AGENT("10","代理商户"),
    AGENT_MER("11","代理商自有网点"),
    CHAIN("12","连锁商户"),
    CHAIN_STORE_MER("13","连锁直营网点"),
    CHAIN_JOIN_MER("14","连锁加盟网点"),
    DDP_MER("15","都都宝自有网点"),
    GROUP("16","集团商户"),
    PROVIDER("17","供应商"),
    EXTERNAL("18","外接商户");

    private static final Map<String, MerTypeEnum> map = new HashMap<String, MerTypeEnum>(values().length);

    static {
        for (MerTypeEnum merType : values()) {
            map.put(merType.getCode(), merType);
        }
    }

    private String code;
    private String name;

    private MerTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerTypeEnum getMerTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

	public static boolean contains(String code) {
		return map.containsKey(code);
	}

	public static String getNameByCode(String code) {
		MerTypeEnum temp = getMerTypeByCode(code);
		if (temp != null) {
			return temp.getName();
		}
		return "";
	}
}
