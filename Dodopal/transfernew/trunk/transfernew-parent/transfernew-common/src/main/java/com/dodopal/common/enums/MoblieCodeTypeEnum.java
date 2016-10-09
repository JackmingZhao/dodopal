package com.dodopal.common.enums;

/**
 * 手机接收短信类型
 * 
 */
public enum MoblieCodeTypeEnum {

	 USER_RG("1","都都宝用户注册"),
	 
	 USER_DE_UP("4","个人变更终端设备"),
	 
	 USER_PWD("5","个人找回密码"),
	 
	 MER_RG("6","网点客户端注册");
	
	private String code;
	
	private String name;
	

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
	
	MoblieCodeTypeEnum(String code, String name){
		this.code = code;
		this.name = name;
	}

	/** 根据状态码获取其对应的枚举状态 **/
	public static MoblieCodeTypeEnum parseByCode(String source) {
		for (MoblieCodeTypeEnum codes : values()) {
			if (codes.code.equals(source)) {
				return codes;
			}
		}
		return null;
	}
	
}
