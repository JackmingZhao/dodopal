package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 批充单状态
 * @author lifeng@dodopal.com
 */

public enum BatchRcgOrderStatusEnum {
	CREATE("0", "已创建"), EXECUTE("1", "已执行");

	private static final Map<String, BatchRcgOrderStatusEnum> map = new HashMap<String, BatchRcgOrderStatusEnum>(values().length);

	static {
		for (BatchRcgOrderStatusEnum temp : values()) {
			map.put(temp.getCode(), temp);
		}
	}

	private String code;
	private String name;

	private BatchRcgOrderStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static BatchRcgOrderStatusEnum getEnumByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return map.get(code);
	}

	public static boolean contains(String code) {
		return map.containsKey(code);
	}

	public static String getNameByCode(String code) {
		BatchRcgOrderStatusEnum temp = getEnumByCode(code);
		if (temp != null) {
			return temp.getName();
		}
		return "";
	}
}
