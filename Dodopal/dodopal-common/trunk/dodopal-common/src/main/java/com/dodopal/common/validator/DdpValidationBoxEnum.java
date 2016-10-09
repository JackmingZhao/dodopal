package com.dodopal.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum DdpValidationBoxEnum {
	MOBILE("mobile", "^[1][3-8]+\\d{9}", "手机号码不正确"), 
	EMAIL("email", "^[_a-zA-Z\\d\\-\\./]+@[_a-zA-Z\\d\\-]+(\\.[_a-zA-Z\\d\\-]+)*(\\.(info|biz|com|edu|gov|net|am|bz|cn|cx|hk|jp|tw|vc|vn))", ""), 
	EN_CN("enCn", "^[a-zA-Z\\u4e00-\\u9fa5]*", "只能中文,英文"), //中文， 英文
	CN_EN_NO_US("cnEnNoUs", "^[a-zA-Z0-9_\\u4e00-\\u9fa5]*", "只能中文,数字,英文,下划线"), //中文数字英文下划线
	EN_NO_US("enNoUs", "^[a-zA-Z0-9_]*", "只能数字,英文,下划线"), //英文 数字 下划线
	EN_NO("enNo", "^[a-zA-Z0-9]*", "只能数字,英文"), //英文 数字
	NO("no", "^[0-9]*", "只能数字"), //数字
	CN_EN_NO("cnEnNo", "^[a-zA-Z0-9\\u4e00-\\u9fa5]*", "只能中文、数字、英文"),
	ID_CARD("idCard","^[1-9]\\d{13,16}[a-zA-Z0-9]{1}","身份证不合法"),
	PHONE("phone", "^0\\d{2,3}-\\d{5,9}|0\\d{2,3}-\\d{5,9}", " ");

	//    //联系电话 数字、+、-号 长度最长20位
	//    phone : function (value) {
	//        return /^[\\+\\d\\-]{0,20}$/.test(value);
	//    },
	//    // 传真 数字、+、-号 长度<=20
	//    fax: function (value, param) {
	//        return   /^0\\d{2,3}-\\d{5,9}|0\\d{2,3}-\\d{5,9}$/.test(value);
	//    },
	//    zip: function (value) {
	//        return /^[1-9]\\d{5}(?!\\d)/.test(value);
	//    },

	private static final Map<String, DdpValidationBoxEnum> map = new HashMap<String, DdpValidationBoxEnum>(values().length);

	static {
		for (DdpValidationBoxEnum validator : values()) {
			map.put(validator.getCode(), validator);
		}
	}

	private String code;
	private String reg;
	private String message;

	private DdpValidationBoxEnum(String code, String reg, String message) {
		this.code = code;
		this.reg = reg;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getReg() {
		return reg;
	}

	public String getMessage() {
		return message;
	}

	public static DdpValidationBoxEnum getValidatorByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return map.get(code);
	}

	public static boolean checkCodeExist(String code) {
		return map.containsKey(code);
	}
}
