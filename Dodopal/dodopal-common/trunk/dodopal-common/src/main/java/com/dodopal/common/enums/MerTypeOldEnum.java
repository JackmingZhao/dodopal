package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 老系统商户类型（新平台定义，跟老平台无关）
 * 
 * @author lifeng@dodopal.com
 */

public enum MerTypeOldEnum {
	PERSONAL("0", "个人"), GROUP("1", "集团"), PROXY("2", "网点"), MERCHANT("3", "商户");

	private static final Map<String, MerTypeOldEnum> map = new HashMap<String, MerTypeOldEnum>(values().length);
	private static final Map<String, String> authorityMap = new HashMap<String, String>();

	static {
		for (MerTypeOldEnum merType : values()) {
			map.put(merType.getCode(), merType);
		}

		authorityMap.put(PERSONAL.getCode(), "tran.history.personal");
		authorityMap.put(PROXY.getCode(), "tran.history.proxy");
		authorityMap.put(MERCHANT.getCode(), "tran.history.merchant");
	}

	private String code;
	private String name;

	private MerTypeOldEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static MerTypeOldEnum getByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return map.get(code);
	}

	public static boolean contains(String code) {
		return map.containsKey(code);
	}

	public static String getNameByCode(String code) {
		MerTypeOldEnum temp = getByCode(code);
		if (temp != null) {
			return temp.getName();
		}
		return "";
	}

	public static String getAuthorityCodeByType(String type) {
		return authorityMap.get(type);
	}
}
