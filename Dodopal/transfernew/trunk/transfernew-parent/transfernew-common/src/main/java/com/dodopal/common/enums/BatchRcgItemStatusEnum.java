package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/** 
 * 批次单充值项状态
 * @author lifeng@dodopal.com
 */

public enum BatchRcgItemStatusEnum {
	UN_RECHARGE("0", "未充值");

	private static final Map<String, BatchRcgItemStatusEnum> map = new HashMap<String, BatchRcgItemStatusEnum>(values().length);

	static {
		for (BatchRcgItemStatusEnum temp : values()) {
			map.put(temp.getCode(), temp);
		}
	}

	private String code;
	private String name;

	private BatchRcgItemStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static BatchRcgItemStatusEnum getEnumByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return map.get(code);
	}

	public static boolean contains(String code) {
		return map.containsKey(code);
	}

	public static String getNameByCode(String code) {
		BatchRcgItemStatusEnum temp = getEnumByCode(code);
		if (temp != null) {
			return temp.getName();
		}
		return "";
	}
}
