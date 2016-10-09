package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 商户用户标志
 */
public enum MerUserFlgEnum {
	ADMIN("1000", "管理员"), COMMON("1100", "普通操作员");

	private static final Map<String, MerUserFlgEnum> map = new HashMap<String, MerUserFlgEnum>(values().length);

	static {
		for (MerUserFlgEnum merUserFlg : values()) {
			map.put(merUserFlg.getCode(), merUserFlg);
		}
	}

	private String code;
	private String name;

	private MerUserFlgEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static MerUserFlgEnum getMerUserFlgByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return map.get(code);
	}
}
